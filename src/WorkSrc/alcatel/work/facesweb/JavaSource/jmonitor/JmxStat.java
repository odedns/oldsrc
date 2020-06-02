
package jmonitor;

import java.util.*;
import java.io.*;
import com.ibm.websphere.management.statistics.*;
import com.ibm.websphere.management.*;
import com.ibm.websphere.management.exception.*;
import javax.management.ObjectName;
import com.ibm.ws.pmi.client.*;
import com.ibm.websphere.pmi.*;
import com.ibm.websphere.pmi.client.*;
import com.ibm.websphere.pmi.stat.MBeanStatDescriptor;
import com.ibm.websphere.pmi.stat.StatDescriptor;
import com.ibm.websphere.pmi.stat.MBeanLevelSpec;


/**
    JmxStat is a simple command line interface to demonstrate how to get PMI data via JMX interface.
    
    @version 1.0
 */

public class JmxStat implements PmiConstants
{
    private static AdminClient ac;                // An instance of AdminClient

    private static String host = "localhost";     // default host
    private static String port = "8880";          // default port number
    private static String connector = "SOAP";     // default JMX connector

    private static String mynode = null;
    private static String myserver = null;
    private static ObjectName perfMbean = null;   // the PerfMBean in the selected server
    private static ObjectName srvMbean = null;    // the server MBean for the selected server

    private static int INTERACTIVE = 1;
    private static int CMD_LINE = 2;
    private static final String MAIN_MENU = "main menu";
    private static final String GO_EXIT = "exit";

    private static final int NUM_MBEANS_PER_SCREEN = 10; 

    private static int mode = INTERACTIVE;
    private static int refreshInterval = 0;    //in seconds

    public static void main(String[] args)
    {
        ArrayList statsMbeanTypes = null;
        ArrayList allMbeans = null;
        ObjectName selectedMbean = null;
        PmiModuleConfig[] configs = null;

        processArgs (args);
        System.out.println ("Connect to " + host + ":" + port + ":" + connector);

        // Step 1: Create an instance of AdminClient
        ac = getAdminClient(host, port, connector);

        ObjectName myMbean = null;


        // You will choose node, server, and MBean to monitor the data
        // list all the nodes and choose one
        ArrayList nodes = listNodes();
        mynode = selectOne("Choose one of the nodes", nodes);

        // list all the servers on the node and choose one
        ArrayList servers = listServers(mynode);
        myserver = selectOne("Choose one of the servers", servers);

        // Step 2: Find PerfMBean and other MBeans
        // You have to enable PMI and restart the appserver in order to get the PerfMBean
        perfMbean = getMbean(mynode, myserver, "Perf");
        if(perfMbean == null)
        {
            System.out.println("No PerfMBean is found for the selected server. Make sure PMI service is enabled on the server.");
            System.exit(1);
        }

        // find the server MBean
        srvMbean = getMbean(mynode, myserver, "Server");
        
        // Get the config data for this server and you will need to cache the config data for future usage
        // The config data will be used at Step 6: Retrieve static configuration information for the data
        try
        {
            configs = (PmiModuleConfig[])ac.invoke(perfMbean, "getConfigs", null, null);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Error: cannot get the config data from the server");
            System.exit(1);
        }


        Stats mystats = null;

        // Uncomment the following code to get all PMI data for the whole server.
        // NOTE: This could include lots of PMI data depending on the instrumentation level
        /*
        mystats = collectStatsViaPerfMbean(perfMbean, srvMbean, true);  // true will return all the data under the server
        bindConfig(mystats, srvMbean, configs);
        displayStats(mystats);
        */

        // Uncomment the following code to test with MBeanStatDescriptor
        /*
        MBeanStatDescriptor msd = createMBeanStatDescriptor(srvMbean, new String[]{PmiConstants.THREADPOOL_MODULE});
        mystats = collectStatsViaPerfMbean(perfMbean, msd, false);  // false means only returning data for the ThreadPool module
        bindConfig(mystats, msd, configs);
        displayStats(mystats);
        */

        // The following code list all the Mbeans that provide PMI data. This is still step 2 to find the MBeans.
        allMbeans = listMbeans(mynode, myserver, true);

        // categorize with MbeanTypes
        statsMbeanTypes = getMbeanTypes(allMbeans);

        // This ArrayList is used to control if you want to continue the monitoring
        ArrayList continueChoices = new ArrayList();
        continueChoices.add("Continue to monitor the MBean (do NOT get PMI data in sub-stats)");
        continueChoices.add("Continue to monitor the MBean (get PMI data in sub-stats)");
        continueChoices.add("Choose a different MBean");
        continueChoices.add("Exit");

        // The following loop allows you to choose an MBean to monitor, optionally set the instrumenation level, and get the data.
        while(true)
        {
            String myMbeanType = selectOne("Choose one of the Mbean types", statsMbeanTypes);
            ArrayList myMbeans = getMbeans(allMbeans, myMbeanType);
            selectedMbean = selectMbean("Choose one of the Mbeans:", myMbeans);

            // get the instrumentation level
            int level = getInstrumentationLevel(perfMbean, selectedMbean);
            String lvlStr = "none";
            switch(level)
            {
                case PmiConstants.LEVEL_NONE: lvlStr = "none"; break;
                case PmiConstants.LEVEL_LOW: lvlStr = "low"; break;
                case PmiConstants.LEVEL_MEDIUM: lvlStr = "medium"; break;
                case PmiConstants.LEVEL_HIGH: lvlStr = "high"; break;
                case PmiConstants.LEVEL_MAX: lvlStr = "maximum"; break;
                default:lvlStr = "undefined";
            }

            System.out.println("\nThe current instrumentation level is " + lvlStr);

            // allow user to input their instrumentation level
            ArrayList levelNames = new ArrayList();
            levelNames.add("no change");
            levelNames.add("low");
            levelNames.add("medium");
            levelNames.add("high");
            levelNames.add("maximum");
            String levelStr= selectOne("Select the instrumentation level you want to set: ", levelNames);         
            if(levelStr.equals("no change"))
                System.out.println("level is not changed");
            else
            {
                if(levelStr.startsWith("low"))
                    level = PmiConstants.LEVEL_LOW;
                else if(levelStr.startsWith("medium"))
                    level = PmiConstants.LEVEL_MEDIUM;
                else if(levelStr.startsWith("high"))
                    level = PmiConstants.LEVEL_HIGH;
                else if(levelStr.startsWith("maximum"))
                    level = PmiConstants.LEVEL_MAX;

                // Step 3: set instrumentation level 
                setInstrumentationLevel(perfMbean, selectedMbean, level);
            }

            // uncomment it if you want the program to sleep before getting PMI data 
            /*
            if(refreshInterval == 0)
                getRefreshRate();
            */

            // you have two options to get PMI data: 
            // Option 1: from individual MBeans - i.e., myMbeans. In this case, you have to invoke an JMX getAttribute call for each individual MBean
            // Option 2: from PerfMbean. In this case, you can invoke one JMX call to get Stats for multiple MBeans.

            // Step 4: Get performance data from an individual MBean - option 1
            mystats = collectStatsAttribute(selectedMbean);
            // Step 6: Retrieve static configuration information for the data
            bindConfig(mystats, selectedMbean, configs);
            // Step 7: Get individual PMI counters from a Stats object
            displayStats(mystats);

            // Step 5: Get performance data from Perf MBean - option 2
            mystats = collectStatsViaPerfMbean(perfMbean, selectedMbean, false);  // for the first get, pass false so we do not get PMI data from sub-stats
            // Step 6: Retrieve static configuration information for the data
            bindConfig(mystats, selectedMbean, configs);
            // Step 7: Get individual PMI counters from a Stats object
            displayStats(mystats);

            // Option 2 is more efficient for multiple Mbeans since it uses a single JMX 
            // remote call while option 1 has to use multiple JMX calls.
            // Therefore, we use option 2 to repeatly collect Stats.
            while(true)
            {
                boolean recursive = true;
                String choice = selectOne("Continue to monitor this MBean or choose a different MBean: ",
                                          continueChoices);
                if(choice.startsWith("Choose"))
                    break;
                else if(choice.startsWith("Exit"))
                    System.exit(0);
                else if (choice.indexOf("do NOT") > 0)
                    recursive = false;

                // sleep for an interval 
                try
                {
                    if(refreshInterval > 0)
                        Thread.sleep (refreshInterval * 1000);   // convert to milliseconds
                }
                catch(Exception e)
                {}

                mystats = collectStatsViaPerfMbean(perfMbean, selectedMbean, recursive);
                bindConfig(mystats, selectedMbean, configs);
                displayStats(mystats);
            }
        }
    }


    private static void processArgs (String[] arg)
    {
        int argLen = arg.length;
        if(argLen == 0)
        {
            System.out.println ("Usage: JmxStat [-host hostName -port port# -connector [RMI/SOAP] -refreshRate refInt]");
            return;
        }

        int i = 0;
        try
        {
            while(i < argLen)
            {
                if(arg[i].equalsIgnoreCase ("-host"))
                {
                    host = arg[++i];
                }
                else if(arg[i].equalsIgnoreCase ("-port"))
                {
                    port = arg[++i];
                }
                else if(arg[i].equalsIgnoreCase ("-connector"))
                {
                    connector = arg[++i];
                }
                else if(arg[i].equalsIgnoreCase ("-refreshRate"))
                {
                    try
                    {
                        refreshInterval = Integer.parseInt (arg[++i]);
                        if(refreshInterval < 1)
                            throw new Exception ();
                    }
                    catch(Exception e)
                    {
                        refreshInterval = 10;
                    }
                }

                ++i;
            }
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
            System.out.println ("Usage: wsstat [-host hostName -port port# -connector [RMI/SOAP] -refreshRate refInt]");
            System.exit(0);
        }
    }

    /**
     * get AdminClient using the given host, port, and connector
     */
    public static AdminClient getAdminClient(String hostStr, String portStr, String connector)
    {
        AdminClient adminc = null;
        java.util.Properties props = new java.util.Properties();
        props.put(AdminClient.CONNECTOR_TYPE, connector);
        props.put(AdminClient.CONNECTOR_HOST, hostStr);
        props.put(AdminClient.CONNECTOR_PORT, portStr);

        try
        {
            adminc = AdminClientFactory.createAdminClient(props);
        }
        catch(Exception ex)
        {
            new AdminException(ex).printStackTrace();
            System.out.println("getAdminClient: exception");
            System.exit(1);
        }
        return adminc;
    }

    private static Set queryObjectNames(String queryString)
    {

        Set oSet = null; 
        try
        {

            if(ac != null)
            {
                oSet= ac.queryNames(new ObjectName(queryString), null);
            }
        }
        catch(ConnectorException ce)
        {
            ce.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return oSet;
    }

    /** It will only list the running nodes
     */
    public static ArrayList listNodes()
    {

        Set oSet = queryObjectNames("WebSphere:*,type=Server");
        if(oSet == null) return null;

        Iterator it = oSet.iterator();

        HashSet nodeSet=new HashSet();
        ObjectName on;
        while(it.hasNext())
        {
            on = (ObjectName)it.next();
            // filter out dmgr
            if(!on.getKeyProperty("processType").equals(AdminConstants.DEPLOYMENT_MANAGER_PROCESS))
            {
                nodeSet.add(on.getKeyProperty("node"));
            }
        }

        if(nodeSet.size()==0)
        {
            return null;
        }

        Object[] genericNodeArray=nodeSet.toArray();
        ArrayList ret = new ArrayList(genericNodeArray.length);
        for(int i=0; i<genericNodeArray.length; i++)
            ret.add(genericNodeArray[i]);

        return ret;
    }

    /** only list the running servers
     * @param nodeName node name
     */
    public static ArrayList listServers(String nodeName)
    {
        ArrayList servers=new ArrayList();

        Set oSet = queryObjectNames("WebSphere:*,type=Server,node=" + nodeName); 
        if(oSet == null) return null;

        Iterator it = oSet.iterator();
        ObjectName on;
        while(it.hasNext())
        {
            on = (ObjectName)it.next();
            servers.add(on.getKeyProperty("process"));
        }

        return servers;
    }


    /** Get the MBean ObjectName for a specific mbean type from a specific server.
     *  If there are multiple instances of such MBean, it will return the first one. 
     *  It will return null if there is no such type of MBean in the server.
     *  If you need to have all the MBean instances for the MBean type, you need to slightly
     *  modify this method.
     *
     * @param node node name
     * @param server server name
     * @param mbeanType the type of the MBean
     */
    public static ObjectName getMbean(String node, String server, String mbeanType)
    {
        try
        {
            String queryStr = "WebSphere:*,type=" + mbeanType + ",node=" + node + ",process=" + server;
            Set oSet = ac.queryNames(new ObjectName(queryStr), null);
            Iterator it= oSet.iterator();
            if(it.hasNext())
                return(ObjectName)it.next();
            else
            {
                System.out.println("Cannot find PerfMBean for node=" + node + ", server=" + server + ", type=" + mbeanType);
                return null;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /**
     * List the mbeans on this server.
     * 
     * @param node node name
     * @param server server name
     * @param statsProviderOnly When the boolean is true, the method only returns MBeans that have PMI data.
     *                          When the boolean is false, the method returns all the MBeans in the server.
     */
    public static ArrayList listMbeans(String node, String server, boolean statsProviderOnly)
    {
        HashMap mbeanTypeMap = new HashMap();

        try
        {

            // Get a list of object names
            String queryString = "WebSphere:*,node=" + node + ",process=" + server;

            // get all objectnames for this server
            Set objectNameSet= ac.queryNames(new ObjectName(queryString), null);

            System.out.println("\nSearching for all the MBean types that provide PMI data. This may take a while ......");
            if(objectNameSet != null)
            {
                String type = null;
                Iterator i = objectNameSet.iterator();
                ArrayList mbeans = new ArrayList();
                ObjectName on = null;
                Stats oneStats = null;
                String oneType = null;
                String isStats = null;
                while(i.hasNext())
                {
                    on = (ObjectName)i.next();
                    //System.out.println("on: " + on.toString());
                    if(!statsProviderOnly)
                    {
                        mbeans.add(on);
                    }
                    else
                    {
                        // if we already know the MBean has PMI data or not, add it to the the mbeans list directly
                        oneType = on.getKeyProperty("type");
                        isStats = (String)mbeanTypeMap.get(oneType);
                        if(isStats != null && isStats.equals("no"))
                        {
                            // do not have Stats attribute in this case
                            continue;
                        }
                        else if(isStats != null && isStats.equals("yes"))
                        {
                            // have Stats attribute in this case
                            mbeans.add(on);
                            continue;
                        }

                        // If we get here, it is the first time we exam this MBean type
                        try
                        {
                            // Check if there is PMI data for this MBean on. Note that some MBeans provide PMI data,
                            // but do not implement the stats attribute. In this case, you will need to get the related PMI
                            // data via PerfMBean.
                       
                            int level = getInstrumentationLevel(perfMbean, on);
                            if(level != PmiConstants.LEVEL_DISABLE)
                            {
                                // it has PMI data (i.e., Stats attribute) in this case
                                mbeans.add(on);
                                mbeanTypeMap.put(oneType, "yes");
                            }
                            else
                            {
                                mbeanTypeMap.put(oneType, "no");
                            }

                        }
                        catch(Exception e)
                        {
                            // If exception happens, the MBean does not have Stats attributes
                            mbeanTypeMap.put(oneType, "no");
                        }
                    }
                }

                return mbeans;
            }
            else
            {
                System.err.println("main: ERROR: no object names found");
                System.exit(2);
            }

        }
        catch(Exception ex)
        {
            new AdminException(ex).printStackTrace();
            ex.printStackTrace();
        }
        return null;

    }

    /** return the types of all the MBeans in the parameter
     *
     * @param onames an ArrayList of MBean ObjectNames
     */
    public static ArrayList getMbeanTypes(ArrayList onames)
    {
        HashSet typeSet = new HashSet();

        for(int i=0; i<onames.size(); i++)
        {
            ObjectName on = (ObjectName)onames.get(i);
            String type = (String)on.getKeyProperty("type");
            typeSet.add(type);
        }


        if(typeSet.size()==0)
        {
            return null;
        }

        Object[] genericArray=typeSet.toArray();
        ArrayList ret = new ArrayList(genericArray.length);
        for(int i=0; i<genericArray.length; i++)
            ret.add(genericArray[i]);

        return ret;
    }

    /** return the MBean ObjectNames with a specific MBean type
     *
     * @param onames an ArrayList of MBean ObjectNames
     * @param specificType the type of the MBean
     */
    public static ArrayList getMbeans(ArrayList onames, String specificType)
    {
        ArrayList ret = new ArrayList();

        for(int i=0; i<onames.size(); i++)
        {
            ObjectName on = (ObjectName)onames.get(i);
            String type = (String)on.getKeyProperty("type");
            if(type.equals(specificType))
                ret.add(onames.get(i));
        }

        return ret;

    }

    // This method is used to select a choice from multiple choices.
    // names must have a list of String objects
    private static String selectOne(String note, ArrayList names)
    {

        if(names == null || names.size()==0)
        {
            System.out.println("\n\n[WARNING] The Arraylist for " + note + " is null");
        }

        byte[] input;
        int modNo;
        StringBuffer nameStr = new StringBuffer();

        do
        {
            try
            {
                input = new byte[100];

                System.out.println("\n");
                for(int i=0; i<names.size(); i++)
                {
                    System.out.println((i+1) + " - " + (String)names.get(i));
                }

                System.out.print (note + " [1.." + names.size() + "]> ");
                System.in.read (input);
                modNo = Integer.parseInt (new String (input).trim());
            }
            catch(Exception e)
            {
                modNo = 0;
            }


        } while(modNo > names.size() || modNo < 1);

        return(String)names.get(modNo-1);
    }


    // This method is used to select a MBean from multiple choices.
    // names must be an arraylist of ObjectNames
    private static ObjectName selectMbean(String note, ArrayList names)
    {
        byte[] input;
        int modNo = 0;
        int numScreens = names.size() / NUM_MBEANS_PER_SCREEN;  // we only display a limited numberf of MBeans each time 
        int firstNo=0, lastNo = 0;

        do
        {

            for(int screenCnt=0; screenCnt<=numScreens; screenCnt++)
            {

                try
                {
                    input = new byte[100];

                    firstNo = screenCnt * NUM_MBEANS_PER_SCREEN;
                    lastNo = (screenCnt+1) * NUM_MBEANS_PER_SCREEN;
                    if(lastNo > names.size())
                        lastNo = names.size();

                    for(int i=firstNo; i<lastNo; i++)
                    {
                        if(i >= names.size())
                            break;
                        ObjectName  on = (ObjectName)names.get(i);
                        System.out.println("\n" + (i+1) + " - " + on.toString());
                    }

                    if(numScreens > 0)
                    {
                        System.out.println("\n0 - See more MBeans");
                        System.out.print (note + " [" + (firstNo+1) + ".." + lastNo + ", 0]> ");
                    }
                    else
                    {
                        System.out.print (note + " [" + (firstNo+1) + ".." + lastNo + "]> ");
                    }

                    System.in.read (input);
                    modNo = Integer.parseInt (new String (input).trim());
                }
                catch(Exception e)
                {
                    modNo = 0;
                }

                if(modNo>firstNo && modNo<=lastNo)   // choose one
                    break;

            }

        } while(modNo > names.size() || modNo < 1);

        return(ObjectName)names.get(modNo-1);
    }


    /** Return the instrumentation level for a given ObjectName. It will return LEVEL_DISABLE if there is no PMI data in such a MBean
     *
     * @param perfOName the MBean ObjectName of PerfMBean
     * @param oname the MBean ObjectName that may have PMI data
     */
    public static int getInstrumentationLevel(ObjectName perfOName, ObjectName oname)
    {


        try
        {
            Object[] params = new Object[]{oname, new Boolean(false)};
            String[] signatures = new String[]{"javax.management.ObjectName", "java.lang.Boolean"};
            MBeanLevelSpec[] mlss = (MBeanLevelSpec[])ac.invoke(perfMbean, "getInstrumentationLevel", params, signatures);

            if(mlss == null || mlss.length == 0)
            {
                return PmiConstants.LEVEL_DISABLE;
            }

            if(mlss[0] != null)
                return mlss[0].getLevel();
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
        }

        return PmiConstants.LEVEL_DISABLE;
    }


    /** You can only use the PerfMBean to set instrumentation level.
     * This method will allow you to set level for an MBean.
     *
     * @param perfOName the MBean ObjectName of PerfMBean
     * @param oname the MBean ObjectName
     * @param level the new level to be set for the MBean
     *
     */
    public static void setInstrumentationLevel(ObjectName perfOName, ObjectName oname, int level)
    {
        try
        {
            MBeanLevelSpec mls = new MBeanLevelSpec(oname, null, level);
            Object[] params= { mls, new Boolean(true)};
            String[] signature= { "com.ibm.websphere.pmi.stat.MBeanLevelSpec", "java.lang.Boolean"};

            ac.invoke(perfOName, "setInstrumentationLevel", params, signature);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /** You can only use the PerfMBean to set instrumentation level.
     * This method will allow you to set level for an array of ObjectName
     *
     * @param perfOName the MBean ObjectName of PerfMBean
     * @param onames an array of the MBean ObjectNames
     * @param level the new level to be set for the MBean
     *
     */
    public static void setInstrumentationLevel(ObjectName perfOName, ObjectName[] onames, int level)
    {
        try
        {
            MBeanLevelSpec[] mlss = new MBeanLevelSpec[onames.length];
            for(int i=0; i<onames.length; i++)
                mlss[i] = new MBeanLevelSpec(onames[i], null, level);

            Object[] params= { mlss, new Boolean(true)};
            String[] signature= { "[Lcom.ibm.websphere.pmi.stat.MBeanLevelSpec;", "java.lang.Boolean"};

            ac.invoke(perfOName, "setInstrumentationLevel", params, signature);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Return the Stats attribute. Some MBean may not have Stats attribute. In that case,
     * you have to call collectStatsVia PerfMBean to get the Stats.
     *
     * @param oname the MBean ObjectName
     */
    public static Stats collectStatsAttribute(ObjectName oname)
    {
        System.out.println("\nget Stats object via the stats Attribute");
        try
        {
            return(Stats)ac.getAttribute(oname, "stats");
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
            // if you are getting here, it means that you cannot get the "stats" attribute
            // from this MBean - i.e., this MBean does not have Stats attribute.
            System.out.println("\nThis MBean has PMI data, but does not provide the stats attribute. Need to get Stats from PerfMBean");
            return null;
        }
    }

    /**
     * If you want to get the Stats attribute for multiple MBeans, you will have to
     * call getAttribute multiple times. Multiple JMX calls are expensive than
     * combining them in one JMX calls via PerfMBean.
     *
     * @param onames an array of the MBean ObjectNames
     */
    public static Stats[] collectStatsAttribute(ObjectName[] onames)
    {
        if(onames == null || onames.length == 0)
            return null;

        Stats[] ret = new Stats[onames.length];
        for(int i=0; i<ret.length; i++)
            ret[i] = collectStatsAttribute(onames[i]);

        return ret;
    }

    /** Return Stats object via PerfMBean for a MBean. You will get the Stats object as long as
     * the related component has PMI instrumentation.
     *
     * @param perfOName the MBean ObjectName of PerfMBean
     * @param oname the MBean ObjectName which has PMI data
     * @param recursive a boolean to indicate if PMI data in the PMI subtree will be returned. 
     *        When recursive=true, it will return the PMI data in the subtree.
     *        When recursive=false, it will only return the PMI data for the MBean.
     *
     */
    public static Stats collectStatsViaPerfMbean(ObjectName perfOname, ObjectName oname, boolean recursive)
    {
        try
        {
            System.out.println("\nget Stats object via the PerfMBean");
            Object[] params = new Object[]{oname, new Boolean(recursive)};
            String[] sigs = new String[]{"javax.management.ObjectName", "java.lang.Boolean"};
            return(Stats)ac.invoke(perfOname, "getStatsObject", params, sigs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /** Return an array of Stats objects via PerfMBean for an array of MBeans. 
     *  This is done via a single JMX call so it is more efficient.
     *
     * @param perfOName the MBean ObjectName of PerfMBean
     * @param onames an array of the MBean ObjectNames
     * @param recursive a boolean to indicate if PMI data in the PMI subtree will be returned. 
     *        When recursive=true, it will return the PMI data in the subtree.
     *        When recursive=false, it will only return the PMI data for the MBean.
     *
     */
    public static Stats[] collectStatsViaPerfMbean(ObjectName perfOname, ObjectName[] onames, boolean recursive)
    {
        try
        {
            System.out.println("\nget Stats objects via the PerfMBean");
            Object[] params = new Object[]{onames, new Boolean(recursive)};
            String[] sigs = new String[]{"[Ljavax.management.ObjectName;", "java.lang.Boolean"};
            return(Stats[])ac.invoke(perfOname, "getStatsArray", params, sigs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }


    /** Return Stats object via PerfMBean for a MBeanStatDescriptor. You will get the Stats object as long as
     * the related component has PMI instrumentation.
     *
     * @param perfOName the MBean ObjectName of PerfMBean
     * @param oname the MBean ObjectName which has PMI data
     * @param recursive a boolean to indicate if PMI data in the PMI subtree will be returned. 
     *        When recursive=true, it will return the PMI data in the subtree.
     *        When recursive=false, it will only return the PMI data for the MBean.
     *
     */
    public static Stats collectStatsViaPerfMbean(ObjectName perfOname, MBeanStatDescriptor msd, boolean recursive)
    {
        try
        {
            System.out.println("\nget Stats object via the PerfMBean");
            Object[] params = new Object[]{msd, new Boolean(recursive)};
            String[] sigs = new String[]{"com.ibm.websphere.pmi.stat.MBeanStatDescriptor", "java.lang.Boolean"};
            return(Stats)ac.invoke(perfOname, "getStatsObject", params, sigs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }


    /** Return an array of Stats objects via PerfMBean for an array of MBeanStatDescriptors. 
     *  This is done via a single JMX call so it is more efficient.
     *
     * @param perfOName the MBean ObjectName of PerfMBean
     * @param onames an array of the MBean ObjectNames
     * @param recursive a boolean to indicate if PMI data in the PMI subtree will be returned. 
     *        When recursive=true, it will return the PMI data in the subtree.
     *        When recursive=false, it will only return the PMI data for the MBean.
     *
     */
    public static Stats[] collectStatsViaPerfMbean(ObjectName perfOname, MBeanStatDescriptor[] msds, boolean recursive)
    {
        try
        {
            System.out.println("\nget Stats objects via the PerfMBean");
            Object[] params = new Object[]{msds, new Boolean(recursive)};
            String[] sigs = new String[]{"[Lcom.ibm.websphere.pmi.stat.MBeanStatDescriptor;", "java.lang.Boolean"};
            return(Stats[])ac.invoke(perfOname, "getStatsArray", params, sigs);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }


    /** Return an MBeanStatDescriptor. This is used for those PMI modules/submodules that have no mapped MBean.
     *  For example, the ThreadPool PMI module is a logic aggregation of all the thread pools and there is no MBean for it.
     *  Therefore, you can call createMBeanStatDescriptor(serverObjectName, new String[]{PmiConstants.THREADPOOL_MODULE})
     *  to create the MBeanStatDescriptor. 
     *
     * @param on the MBean ObjectName which is the base for the MBeanStatDescriptor. It cannot be null.
     * @param subPath the sub-path for the MBeanStatDescriptor within the MBean
     * 
     */
    public static MBeanStatDescriptor createMBeanStatDescriptor(ObjectName on, String[] subPath)
    {
        if(on == null)
            return null;

        if(subPath == null)
            return new MBeanStatDescriptor(on, null);
        else
            return new MBeanStatDescriptor(on, new StatDescriptor(subPath));
    }


    /** Return the MBeanStatDescriptors for a given ObjectName in the PMI tree. The returned array
     *  contains all the MBeanStatDescriptors for the PMI submodules of the given ObjectName. You will need
     *  to parse through each MBeanStatDescriptor to find the one you need. The method is used when
     *  you do not know the subPath for a MBeanStatDescriptor.
     *
     *  It returns null if there is no submodule in the PMI tree under the given ObjectName.
     *
     * @param perfOName The PerfMBean which will be invoked for JMX operation 
     * @param mName the MBean ObjectName 
     */
    public static MBeanStatDescriptor[] listMBeanStatDescriptor(ObjectName perfOName, ObjectName mName)
    {
        if(mName == null)
            return null;

        try
        {
            Object[] params = new Object[]{mName};
            String[] signature= new String[]{"javax.management.ObjectName"};
            MBeanStatDescriptor[] msds = (MBeanStatDescriptor[])ac.invoke(perfOName, "listStatMembers", params, signature);
            return msds;
        }
        catch(Exception e)
        {
            new AdminException(e).printStackTrace();
            System.out.println("listStatMembers: Exception Thrown");
            return null;
        }
    }


    /** Return the MBeanStatDescriptors for a given MBeanStatDescriptor in the PMI tree. The returned array
     *  contains all the MBeanStatDescriptors for the PMI submodules of the given MBeanStatDescriptor. You will need
     *  to parse through each MBeanStatDescriptor to find the one you need. 
     *  The method is most useful when you need to navigate the PMI tree to find a specific PMI submodule.
     *  You can recusive call this method until you get all the MBeanStatDescriptors.
     *
     *  It returns null if there is no submodule in the PMI tree under the given MBeanStatDescriptor.
     *
     * @param perfOName The PerfMBean which will be invoked for JMX operation 
     * @param msd the MBeanStatDescriptor 
     */
    public static MBeanStatDescriptor[] listMBeanStatDescriptor(ObjectName perfOName, MBeanStatDescriptor msd)
    {
        if(msd == null)
            return null;

        try
        {
            Object[] params = new Object[]{msd};
            String[] signature= new String[]{"com.ibm.websphere.pmi.stat.MBeanStatDescriptor"};
            MBeanStatDescriptor[] msds = (MBeanStatDescriptor[])ac.invoke(perfOName, "listStatMembers", params, signature);
            return msds;
        }
        catch(Exception e)
        {
            new AdminException(e).printStackTrace();
            System.out.println("listStatMembers: Exception Thrown");
            return null;
        }
    }

    /**
     * Bind the static config info to the Stats object since the config data 
     * are not transferred with data access in order to reduce communication overhead.
     *
     * @param OneStats an Stats object
     * @param mbean the MBean for this Stats object
     * @param configs the static PMI configuration data for the server 
     */
    public static void bindConfig(Stats oneStats, ObjectName mbean, PmiModuleConfig[] configs)
    {
        if(oneStats == null || configs == null || mbean == null)
            return;

        // need to cast it to PMI extended Stats interface since it provides methods to get sub-stats and bind the config info
        com.ibm.websphere.pmi.stat.Stats pmiStats = (com.ibm.websphere.pmi.stat.Stats)oneStats;

        if(mbean.getKeyProperty("type").equals("Server"))
        {
            // in this case, oneStats is for the whole server, so we need to bind the substats for each PMI module
            com.ibm.websphere.pmi.stat.Stats[] statList = pmiStats.getSubStats();
            if(statList == null || statList.length == 0)
                return; // no sub-stats, so return

            for(int i=0; i<statList.length; i++)
            {  // for each sub-stat
                if(statList[i] == null) continue;
                statList[i].setConfig(PmiClient.findConfig(configs, statList[i].getName()));
            }
        }
        else
        {
            // in this case, use the mbean to find the config
            pmiStats.setConfig(PmiClient.findConfig(configs, mbean));
        }
    }


    /**
     * Bind the static config info to the Stats object since the config data 
     * are not transferred with data access in order to reduce communication overhead.
     *
     * @param OneStats an Stats object
     * @param msd the MBeanStatDescriptor for this Stats object
     * @param configs the static PMI configuration data for the server 
     */
    public static void bindConfig(Stats oneStats, MBeanStatDescriptor msd, PmiModuleConfig[] configs)
    {
        if(oneStats == null || configs == null || msd == null)
            return;

        // need to cast it to PMI extended Stats interface since it provides methods to get sub-stats and bind the config info
        com.ibm.websphere.pmi.stat.Stats pmiStats = (com.ibm.websphere.pmi.stat.Stats)oneStats;
        
        ObjectName mbean = msd.getObjectName();
        StatDescriptor sd = msd.getStatDescriptor();
        if(mbean.getKeyProperty("type").equals("Server") && (sd != null))
        {
            // in this case, we have to use the module name to find the config
            String moduleName = sd.getPath()[0];
            pmiStats.setConfig(PmiClient.findConfig(configs, moduleName));
        }
        else if(mbean.getKeyProperty("type").equals("Server") && (sd == null))
        {
            // in this case, call the other bindConfig method to set the config for the server-level Stats 
            bindConfig(oneStats, mbean, configs);
        }
        else
        {
            // in this case, use the mbean to find the config
            pmiStats.setConfig(PmiClient.findConfig(configs, mbean));
        }
    }


    /**
     * Sample code to navigate and get the data value from the Stats object.
     */
    public static void displayStats(Stats stat)
    {
        displayStats(stat, "");
    }

    /**
     * Sample code to navigate and get the data value from the Stats and Statistic object.
     */
    public static void displayStats(Stats stat, String indent)
    {
        if(stat == null)  return;

        System.out.println("\n");

        // get name of the Stats
        // We need to cast it to PMI extended Stats interface since it provides additional methods
        com.ibm.websphere.pmi.stat.Stats pmiStat = (com.ibm.websphere.pmi.stat.Stats)stat;
        System.out.println(indent + "Stats name=" + pmiStat.getName());

        // uncomment the following code to get the data names
        /*
        String[] dataNames = stat.getStatisticNames();
        for (int i=0; i<dataNames.length; i++)
            System.out.println(indent + "    " + "data name=" + dataNames[i]);
        System.out.println("");
        */

        // In the following, we get the Statistic data from the Stats object returned by the JMX interface (either via PerfMBean or individual MBean).
        // Note that in 5.0, we are using interfaces in com.ibm.websphere.management.statistics. In 6.0, these
        // interfaces will be replaced the J2EE statistics.

        // list all data, this API returns the com.ibm.websphere.management.statistics.Statistic[]
        Statistic[] dataMembers = stat.getStatistics();

        // If you know the data name, you can pass a String to myDataName in the following line to get that data
        // Statistic myData = stat.getStatistic(myDataName);

        if(dataMembers != null)
        {
            for(int i=0; i<dataMembers.length; i++)
            {
                // For each data, cast it to be the real Statistic type so that we can have get the value from each Statistic.
                System.out.print(indent + "    " + "data name=" + dataMembers[i].getName()
                                 + ", description=" + dataMembers[i].getDescription()
                                 + ", startTime=" + dataMembers[i].getStartTime()
                                 + ", lastSampleTime=" + dataMembers[i].getLastSampleTime());

                // check the data type and cast the data to the right type
                if(dataMembers[i] instanceof CountStatistic)
                {
                    System.out.println(", count=" + ((CountStatistic)dataMembers[i]).getCount());
                }
                else if(dataMembers[i] instanceof TimeStatistic)
                {
                    TimeStatistic data = (TimeStatistic)dataMembers[i];
                    System.out.println(", count=" + data.getCount()
                                       + ", total=" + data.getTotalTime()
                                       + ", min=" + data.getMinTime()
                                       + ", max=" + data.getMaxTime());
                }
                else if(dataMembers[i] instanceof RangeStatistic)
                {
                    RangeStatistic data = (RangeStatistic)dataMembers[i];
                    System.out.println(", current=" + data.getCurrent()
                                       + ", lowWaterMark=" + data.getLowWaterMark()
                                       + ", highWaterMark=" + data.getHighWaterMark());
                }
            }
        }

        // Recursively navigate the sub-stats. 
        // We need to use pmiStat here.
        com.ibm.websphere.pmi.stat.Stats[] substats = pmiStat.getSubStats();
        if(substats == null || substats.length == 0)
            return;
        for(int i=0; i<substats.length; i++)
        {
            displayStats(substats[i], indent + "    ");
        }
    }



    private static void getRefreshRate ()
    {
        byte[] input;
        do
        {
            try
            {
                input = new byte[100];
                System.out.print ("\nInput refresh rate in seconds: ");
                System.in.read (input);
                refreshInterval = Integer.parseInt (new String (input).trim());
            }
            catch(Exception e)
            {
                System.out.println ("Using default refresh rate (10 seconds)");
                refreshInterval = 10;
            }
        } while(refreshInterval < 1);                
    }

}
