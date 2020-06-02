/* Created on 30/07/2006 */
package jmonitor;

import java.util.*;

import javax.management.*;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import com.ibm.websphere.management.*;
import com.ibm.websphere.management.application.AppManagement;
import com.ibm.websphere.management.application.AppManagementProxy;
import com.ibm.websphere.management.configservice.ConfigServiceProxy;

import javax.management.j2ee.statistics.*;

/**
 * 
 * @author odedn
 */
public class WasAdminTest {

	public static void main(String[] args)
	{
		try {
			
			AdminClient admin = createAdminClient();
			//appManagement(admin);
			//ConfigServiceProxy config = new ConfigServiceProxy(admin);
			//String v[]= config.getSupportedConfigObjectTypes();
			//for(int i=0; i < v.length; ++i) {
				//System.out.println(v[i]);
			//}
			getJDBCProvider(admin);
			//getNameSpace(admin);
			
			/*
			ObjectName name = getServerMbean(admin,"N813Node03Cell");
			showServerInfo(admin,name);
			*/
			ObjectName jvm = getJVMMbean(admin,"N813Node03Cell");
			
			//showJvmInfo(admin,jvm);
			//sysMetrics(admin);
			//perfMon(admin);
			
		} catch(Exception e) {
			e.printStackTrace();			
		}
	}
	
	
	
	private static void getNameSpace(AdminClient adminClient) throws Exception
	{
		String partialName = "WebSphere:*,j2eeType=JNDIResource";
	    ObjectName partialObjectName = new ObjectName(partialName);
	    ObjectName on = null;
	    Set list = adminClient.queryNames(partialObjectName, null);
	    if (!list.isEmpty()) {
		      on = (ObjectName) list.iterator().next();
		      adminClient.invoke(on,"dumpJavaNameSpace",null,null);
	    }
	}
	
	private static void getJDBCProvider(AdminClient adminClient) throws Exception
	{
	    String partialName = "WebSphere:*,j2eeType=JDBCResource";
	    ObjectName partialObjectName = new ObjectName(partialName);
	    ObjectName on = null;
	    Set list = adminClient.queryNames(partialObjectName, null);
	    Iterator iter = list.iterator();
	    while (iter.hasNext()) {
		     on = (ObjectName) iter.next();
		     System.out.println("name= " + on.getCanonicalName());
		      Stats st = (Stats) adminClient.getAttribute(on,"stats");
		      Statistic v[] = st.getStatistics();
		      for(int i=0; i < v.length; ++i) {
		      		System.out.println(v[i].getDescription());
		      	
		      }
	    }
		
		
	}
	
	
	private static void appManagement(AdminClient adminClient) throws Exception
	{
		
		AppManagement mng = AppManagementProxy.getJMXProxyForClient(adminClient);
		Vector v = mng.listApplications(null,null);
		for(int i=0; i < v.size(); ++i) {
			String s = (String) v.get(i);
			System.out.println(s);
			String partialName = "WebSphere:*,J2EEName=" + s;
			 ObjectName partialObjectName = new ObjectName(partialName);
			 Set list = adminClient.queryNames(partialObjectName, null);
			 if (!list.isEmpty()) {
			 	ObjectName on = (ObjectName) list.iterator().next();
			 	System.out.println(on.getCanonicalName());
			 	try {
			 		Object o =  adminClient.getAttribute(on,"state");			 		
			 		System.out.println("got attribute= " + o);
			 	} catch(AttributeNotFoundException ae) {
			 		System.out.println("getState failed: " + ae);
			 	}
			 	/*
			 	MBeanInfo info = adminClient.getMBeanInfo(on);
			 	MBeanAttributeInfo attrInfo[] = info.getAttributes();
			 	for(int j=0; j < attrInfo.length; ++j) {
			 		System.out.println(attrInfo[j].getName() + "\t" + attrInfo[j].getType());
-			 		
			 	}
			 	*/
			 
			 	
			 	
			 }
		}
		
		
	}
	
	private static void sysMetrics(AdminClient adminClient)throws Exception
	{
	    String partialName = "WebSphere:*,type=SystemMetrics";
	    ObjectName partialObjectName = new ObjectName(partialName);
	    ObjectName on = null;

	    Set list = adminClient.queryNames(partialObjectName, null);
	    if (!list.isEmpty()) {
		      on = (ObjectName) list.iterator().next();
		      System.out.println("name= " + on.getCanonicalName());
		      Stats st = (Stats) adminClient.getAttribute(on,"stats");
		      System.out.println("stats = " + st.toString());
	    } else {
	    	System.out.println("cannot get SystemMetrics");
	    }
		
		
	}
	
	
	private static void perfMon(AdminClient adminClient) throws Exception
	{
		System.out.println("pefrMon");
		String partialName = "WebSphere:*,type=Perf";
	    ObjectName partialObjectName = new ObjectName(partialName);
	    ObjectName on = null;

	    Set list = adminClient.queryNames(partialObjectName, null);
	    if (!list.isEmpty()) {
		      on = (ObjectName) list.iterator().next();
		      System.out.println("name= " + on.getCanonicalName());
		      
	    } else {
	    	System.out.println("cannot get Perf Mbean");
	    	return;
	    }	    	    	    		
		
	}
	
	private static void showJvmInfo(AdminClient adminClient, ObjectName jvmMbean) throws Exception
	{
		
		String s = (String) adminClient.getAttribute(jvmMbean,"heapSize");
		System.out.println("heapSize=" + s);
		s = (String) adminClient.getAttribute(jvmMbean,"freeMemory");
		System.out.println("freeMemory=" + s);
		Stats st = (Stats) adminClient.getAttribute(jvmMbean,"stats");
		System.out.println("stats = " + st.toString());
	}
	
	public static void showServerInfo(AdminClient adminClient, ObjectName serverMbean) throws Exception
	{
		String opName = "getVersionsForAllProducts";
		String sig[] = { "java.lang.String" };
		String params[] = { "Server" };
		String ver[] = (String[]) adminClient.invoke(serverMbean,opName,null,null);
		for(int i=0; i < ver.length; ++i) {
			System.out.println(ver[i]);
		}		
		String s = (String) adminClient.getAttribute(serverMbean,"pid");
		System.out.println("pid=" + s);		
		s = (String) adminClient.getAttribute(serverMbean,"name");
		System.out.println("name=" + s);		
		s = (String) adminClient.getAttribute(serverMbean,"nodeName");
		System.out.println("NodeName=" + s);
		s = (String) adminClient.getAttribute(serverMbean,"platformName");
		System.out.println("platformName=" + s);
		s = (String) adminClient.getAttribute(serverMbean,"platformVersion");
		System.out.println("platformVersion=" + s);
		s = (String) adminClient.getAttribute(serverMbean,"state");
		System.out.println("state=" + s);
		String v[] = (String []) adminClient.getAttribute(serverMbean,"deployedObjects");
		for(int i=0; i < v.length; ++i) {
			System.out.println(v[i]);
		}
		
	}

	public static void showStats(AdminClient adminClient) throws Exception
	{
		String partialName = "WebSphere:*,type=SystemMetrics";
		ObjectName partialObjectName = new ObjectName(partialName);
	    ObjectName name = null;

	    Set list = adminClient.queryNames(partialObjectName, null);
	    if (!list.isEmpty()) {
		      name = (ObjectName) list.iterator().next();
		      System.out.println("got= " + name.getCanonicalName());		  
	    }
	    String opName = "getStats";
		Stats st = (Stats) adminClient.invoke(name,opName,null,null);
		System.out.println(st.toString());
	}
	
	
	/**
	 * create an AdminClient
	 * @return
	 * @throws Exception
	 */
	private static AdminClient createAdminClient() throws Exception 
	{

		  Properties connectProps = new Properties();
		  connectProps.setProperty(AdminClient.CONNECTOR_TYPE,
		    AdminClient.CONNECTOR_TYPE_SOAP);
		  connectProps.setProperty(AdminClient.CONNECTOR_HOST, "localhost");
		  connectProps.setProperty(AdminClient.CONNECTOR_PORT, "8880");

		   AdminClient adminClient = AdminClientFactory.createAdminClient(connectProps);
		   return(adminClient);
	}
	
	private static ObjectName getServerMbean(AdminClient adminClient,String nodeName) throws Exception
	{

	    String partialName = "WebSphere:*,type=Server,j2eeType=J2EEServer";
	    ObjectName partialObjectName = new ObjectName(partialName);
	    ObjectName server = null;

	    Set list = adminClient.queryNames(partialObjectName, null);
	    if (!list.isEmpty()) {
		      server = (ObjectName) list.iterator().next();
		      System.out.println("server= " + server.getCanonicalName());		  
	    } else {
	    	System.out.println("no node servers found");
	      return(null);
	    }

		 return(server);

		 
	}

	private static ObjectName getJVMMbean(AdminClient adminClient,String nodeName) throws Exception
	{

	    String partialName = "WebSphere:*,j2eeType=JVM";
	    ObjectName partialObjectName = new ObjectName(partialName);
	    ObjectName jvm = null;

	    Set list = adminClient.queryNames(partialObjectName, null);
	    if (!list.isEmpty()) {
		      jvm = (ObjectName) list.iterator().next();
		      System.out.println("server= " + jvm.getCanonicalName());		  
	    } else {
	    	System.out.println("no node jvms found");
	      return(null);
	    }

		 return(jvm);

		 
	}

}
