package work;

import com.ibm.dse.base.*;
import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.model.startup.*;
import com.ibm.dse.dw.model.db2.*;
import java.io.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class NWKSTest {

	/**
	 * Constructor for WKSTest.
	 */
	public NWKSTest() {
		super();
	}
	
	static void initDb2Repository() throws Exception
	{
		//Startup.getDefault().start(new File("d:/work/repository/").toURL());
	
		Repository rep = new Db2Repository();
		Hashtable ht = new Hashtable();
		ht.put(Db2Repository.URL,"jdbc:db2:WKSLOCAL");
		ht.put(Db2Repository.APPLICATION_USER,"odedn");
		ht.put(Db2Repository.SYSTEM_USER,"odedn");
		ht.put(Db2Repository.PASSWORD,"odedn01");
		rep.setParams(ht);
		rep.open();
		System.out.println("initDb2Repository: open");		
		String s[] = rep.getAllDomainNames();
		for(int i=0; i < s.length; ++i) {
			System.out.println("domain["+ i+ "]=" + s[i]);	
		}
		rep.close();
		
	}
	
	static void initRepository() throws Exception
	{
		//Startup.getDefault().start(new File("d:/work/repository/").toURL());
		
		RepositoryConnection conn = new RepositoryConnection();
		conn.setApplicationUser("odedn");
		conn.setSystemUser("odedn");
		conn.setSystemPassword("odedn01");
		conn.setApplicationUserName("odedn");
		conn.setUrl("jdbc:db2:WKSLOCAL");
		conn.setImplementor("com.ibm.dse.dw.model.db2.Db2Repository");
		conn.setConfigurationName("Default Repository");
		conn.connect("odedn01");
		Repository rep = conn.getRepository();
		System.out.println("connected to repository");
		/*
		String g[] = rep.getAllEntityNames();
		
		for(int i=0; i < g.length; ++i) {
			System.out.println("Entity["+ i+ "]=" + g[i].toString());	
		}
		*/		
		Entity e = rep.getEntity("Data field",null);
		if(e != null) {
			System.out.println("e = " + e.toString());
				
		}
		conn.disconnect();
		
		
	}
	static void initWorkSpace()	throws Exception
	{

	/*
		WorkspaceConnection connection = new WorkspaceConnection();
		connection.setSystemUser("odedn");
		connection.setApplicationUser("odedn");
		connection.setApplicationUserName("odedn");
		connection.setUrl("jdbc:db2:WKSLOCAL");
		connection.setConfigurationName("Default Workspace"); 
		connection.setImplementor("com.ibm.dse.dw.model.db2.Db2Workspace");
		connection.connect("odedn01");
		Workspace wks = connection.getWorkspace();		
	*/	
		// To initialize the Startup process from a new Plug-in
		
		DefaultStartup.initialize();
		DefaultStartup.getDefault().setBaseURL(new File("d:/work/repository/4.3/").toURL());
		DefaultStartup.getDefault().start();
		
		// To create a new instance of the Default Workspace implementor
		
		WorkspaceConfiguration conf = 
			 DefaultStartup.getDefault().getConfigurationRegistry().getWorkspaceConfiguration("Default Workspace"); 
		Workspace wks = (Workspace) StartupClssLoaderRegistry.getDefault().forName(conf.getImplementor()).newInstance();
		//configure the connection parameters
		java.util.Hashtable params = new java.util.Hashtable(); 
		params.put(ConnectionParameters.URL, "jdbc:db2:WKSLOCAL"); 
		params.put(ConnectionParameters.APPLICATION_USER, "odedn"); 
		params.put(ConnectionParameters.PASSWORD, "odedn01"); 
		params.put(ConnectionParameters.CONFIGURATION, conf); 
		wks.setParams(params); 

		
		wks.open();
		
		Group g[] = wks.getRootGroups();
		for(int i=0; i < g.length; ++i) {
			System.out.println("Group: " + g[i].getName());	
		}
		System.out.println("Getting Data Field instances for group");
		Group grp = wks.getRootGroup("DSE Sample Services.Client.Data");
		if(null == grp) {
			throw new Exception("group is null");	
		} else {
			System.out.println("got group : " + grp.getPath());	
		}
		GroupComposition gc = grp.getComposition();
		
		Instance ins[] = gc.getInstances();
		for(int i=0; i < ins.length; ++i ) {
			System.out.println(ins[i].getName());	
		}
		/*
		Entity e = gc.getEntity("Data field");
		if(e != null) {
			System.out.println("e = " + e.toString());
				
		} else {
			System.out.println("e is null");
		}
		Instance ins[] = e.getInstances();
		if(null != ins) {
			for(int i=0; i < ins.length; ++i) {
				System.out.println(ins[i].getName());	
			}
		}
		*/
		
		Entity ent = wks.getEntity("Data field");
		Instance myIns = wks.getModelFactoryImpl().createInstance();
		myIns.setName("myIns3");
		myIns.setEntity(ent);
		myIns.setOwner(wks.getUser("odedn"));
		myIns.setUsersGroup(wks.getUsersGroup("default"));
		myIns.setParent(wks.getGroup(new DefaultGroupPath("Mataf.Instances.Data")));
		myIns.setValue(ent.getAttribute("id"),"myIns3");
		myIns.save();
		wks.close();
		
		
		
	}
	public static void main(String args[])
	{
		try {
			//initDb2Repository();
			initWorkSpace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
