/* Created on 03/08/2006 */
package jmonitor;

import java.util.*;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.management.AttributeNotFoundException;
import javax.management.ObjectName;
import javax.management.j2ee.statistics.Stats;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.AdminClientFactory;
import com.ibm.websphere.management.application.AppManagement;
import com.ibm.websphere.management.application.AppManagementProxy;

/**
 * This is a utility class that provides method for accessing WAS 
 * JMX beans and retrieving information.
 * @author odedn
 */
public class WasJmxUtils {

	public static final String OP_STOP = "stop";
	public static final String OP_RESTART = "restart";
	/**
	 * create an AdminClient object.
	 * @param host the host to connecto to
	 * @param port the SOAP port to connect to.
	 * @return AdminClient object.
	 * @throws Exception in case of error.
	 */
	public static AdminClient createAdminClient(String host, String port) throws Exception 
	{

		  Properties connectProps = new Properties();
		  connectProps.setProperty(AdminClient.CONNECTOR_TYPE,
		    AdminClient.CONNECTOR_TYPE_SOAP);
		  connectProps.setProperty(AdminClient.CONNECTOR_HOST, host);
		  connectProps.setProperty(AdminClient.CONNECTOR_PORT, port);
		   AdminClient adminClient = AdminClientFactory.createAdminClient(connectProps);
		   return(adminClient);
	}
	
	
	public static synchronized void retrieveServerData(Server srv) throws Exception
	{
		AdminClient adminClient = srv.getAdminClient();
		ObjectName srvMbean = getServerMbean(adminClient);
		String s = (String) adminClient.getAttribute(srvMbean,"pid");
		srv.setPid(Long.parseLong(s));
		s = (String) adminClient.getAttribute(srvMbean,"name");
		srv.setServerName(s);
		s = (String) adminClient.getAttribute(srvMbean,"state");
		srv.setState(s);		
		s = (String) adminClient.getAttribute(srvMbean,"cellName");
		srv.setCellName(s);
		s = (String) adminClient.getAttribute(srvMbean,"nodeName");
		srv.setNodeName(s);
		s = (String) adminClient.getAttribute(srvMbean,"platformName");
		srv.setPlatformName(s);
		s = (String) adminClient.getAttribute(srvMbean,"platformVersion");
		srv.setPlatformVersion(s);
		s = (String) adminClient.getAttribute(srvMbean,"serverVendor");
		srv.setServerVendor(s);
		
	}
	
	/**
	 * get the Server Mbean.
	 * @param adminClient
	 * @return ObjectName representing the MBean.
	 * @throws Exception
	 */
	private static ObjectName getServerMbean(AdminClient adminClient) throws Exception
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

	/**
	 * get the JVM bean.
	 * @param adminClient the adminClientBean
	 * @return
	 * @throws Exception
	 */
	private static ObjectName getJVMMbean(AdminClient adminClient) throws Exception
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
	
	
	
	public static JVMInfo getJVMInfo(AdminClient adminClient) throws Exception
	{
		ObjectName jvmMbean = getJVMMbean(adminClient);
		JVMInfo info = new JVMInfo();
		String s = (String) adminClient.getAttribute(jvmMbean,"heapSize");
		long heapSize = Long.parseLong(s);
		info.setHeapSize(heapSize);
		s = (String) adminClient.getAttribute(jvmMbean,"freeMemory");
		long freeMem = Long.parseLong(s);
		info.setFreeMemory(freeMem);
		s = (String)adminClient.getAttribute(jvmMbean,"javaVendor");
		info.setJavaVendor(s);
		s = (String)adminClient.getAttribute(jvmMbean,"javaVersion");
		info.setJavaVersion(s);
		return(info);
			
	}
	/**
	 * stop the server
	 * @param adminClient the adminClient object
	 * @param serverMBean the server JMX bean
	 * @param opName the operation to perform stop or restart.
	 * @throws Exception in case of error.
	 */
	public static void serverControll(AdminClient adminClient, ObjectName serverMBean, String opName) throws Exception
	{
		adminClient.invoke(serverMBean,opName,null,null);
	}
	
	/**
	 * stop the server
	 * @param srv the Server object to stop.
	 * @throws Exception in case of error.
	 */
	public static void serverControll(Server srv,String opName) throws Exception
	{
		AdminClient adminClient = srv.getAdminClient();
		ObjectName serverMBean = getServerMbean(adminClient);
		serverControll(adminClient,serverMBean,opName);
	}
	
	/**
	 * get a list of AppInfo objects from the server.
	 * @param srv the Server object.
	 * @return List of AppInfo objects.
	 * @throws Exception in case of error.
	 */
	public static List getApplications(Server srv) throws Exception
	{
		AdminClient adminClient = srv.getAdminClient();
		AppManagement mng = AppManagementProxy.getJMXProxyForClient(adminClient);
		LinkedList lst = null;
		Vector v = mng.listApplications(null,null);
		if(v.size() > 0) {
			lst = new LinkedList();
		}
		for(int i=0; i < v.size(); ++i) {
			AppInfo app = new AppInfo();
			String s = (String) v.get(i);
			app.setName(s);
			String partialName = "WebSphere:*,J2EEName=" + s;
			 ObjectName partialObjectName = new ObjectName(partialName);
			 Set list = adminClient.queryNames(partialObjectName, null);
			 if (!list.isEmpty()) {
			 	ObjectName on = (ObjectName) list.iterator().next();
			 	System.out.println(on.getCanonicalName());
			 	try {
			 		Integer state =  (Integer)adminClient.getAttribute(on,"state");
			 		app.setState(state.intValue());
			 	} catch(AttributeNotFoundException ae) {
			 		System.out.println("getState failed: " + ae);
			 	}
			 	lst.add(app);
			 	
			 	
			 } // if
		} // for
		return(lst);	
	}
	
}
