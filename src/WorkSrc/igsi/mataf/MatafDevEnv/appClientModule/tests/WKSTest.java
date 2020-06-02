package tests;

import mataf.utils.*;
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
public class WKSTest {

	/**
	 * Constructor for WKSTest.
	 */
	public WKSTest() {
		super();
	}
	
	static void initDb2Repository() throws Exception
	{
		Startup.getDefault().start(new File("d:/work/repository/").toURL());
	
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
		Startup.getDefault().start(new File("d:/work/repository/").toURL());
		
		RepositoryConnection conn = new RepositoryConnection();
		conn.setApplicationUser("odedn");
		conn.setSystemUser("odedn");
		conn.setSystemPassword("odedn01");
		conn.setApplicationUserName("odedn");
		conn.setUrl("jdbc:db2:WKSLOCAL");
		conn.setImplementor("com.ibm.dse.dw.model.db2.Db2Repository");
		conn.connect("odedn01");
		Repository rep = conn.getRepository();
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
		Startup.getDefault().start(new File("d:/work/repository/").toURL());
		
		Workspace wks = new Db2Workspace();
		Hashtable ht = new Hashtable();
		ht.put(Db2Workspace.URL,"jdbc:db2:WKSLOCAL");
		ht.put(Db2Workspace.APPLICATION_USER,"db2admin");
		ht.put(Db2Workspace.SYSTEM_USER,"db2admin");
		ht.put(Db2Workspace.PASSWORD,"db2admin");
		wks.setParams(ht);
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
		
		Entity e = wks.getEntity("Mataf response field");
		if(e != null) {
			System.out.println("e = " + e.toString());
				
		} else {
			System.out.println("e is null");
		}
		Instance ins2[] = e.getInstances();
		if(null != ins2) {
			for(int i=0; i < ins2.length; ++i) {
				System.out.println(ins2[i].getName());	
			}
		}
		
		/*
		Entity ent = wks.getEntity("Data field");
		Instance myIns = wks.getModelFactoryImpl().createInstance();
		myIns.setName("myIns3");
		myIns.setEntity(ent);
		myIns.setOwner(wks.getUser("odedn"));
		myIns.setUsersGroup(wks.getUsersGroup("default"));
		myIns.setParent(wks.getGroup(new DefaultGroupPath("MatafDev.Data.Convert")));
		myIns.setValue(ent.getAttribute("id"),"myIns3");
		myIns.save();
		*/
/*
		DefaultInstanceRelativePath irPath = new DefaultInstanceRelativePath("1L_TEST");
		DefaultGroupPath pPath = new DefaultGroupPath("MatafDev.Data.Fields.Other");
		
		DefaultInstancePath iPath = new DefaultInstancePath(pPath,irPath);
		
		Instance ins2 = (Instance) wks.getInstance(iPath);
		if(null == ins2) {
			throw new Exception("instance not found : " + iPath);	
		}
		System.out.println("got ins = " + ins2.toString());
		*/
		/*
		DefaultInstanceRelativePath irPath = new DefaultInstanceRelativePath("11_STRUC");
		DefaultGroupPath pPath = new DefaultGroupPath("MatafDev.Tests");
		
		DefaultInstancePath iPath = new DefaultInstancePath(pPath,irPath);
		
		Instance ins2 = (Instance) wks.getInstance(iPath);
		if(null == ins2) {
			throw new Exception("instance not found : " + iPath);	
		}
		Entity ent = wks.getEntity("Parameter");		
		Instance myIns = wks.getModelFactoryImpl().createInstance();
		myIns.setEntity(ent);
		myIns.setName("param1");
		myIns.setOwner(wks.getUser("odedn"));
		myIns.setUsersGroup(wks.getUsersGroup("default"));		
		myIns.setParent(ins2);
		myIns.setValue(ent.getAttribute("id"),"param1");
		myIns.setValue(ent.getAttribute("value"), "value1");
		myIns.save();	
		
		*/
		DefaultGroupPath grp2 = new DefaultGroupPath("MatafDev.Tests.Fields.Common");
		System.out.println("grp2 = " + grp2.toString());
		Group g2 = wks.getGroup(grp2);
		System.out.println("g2 = " + g2.toString());
		wks.close();
		
		
		
	}
	
	private static Group getFullGroup(String baseGroup,String group, String name)
		throws Exception
	{
		int len = name.length();
		int lastIndex = ( 4 < len ? 4 : len);
		String prefix = name.substring(0,lastIndex);
		String grpName = baseGroup + '.' + group;
		DefaultGroupPath grpPath = new DefaultGroupPath(grpName + '.' + prefix);
		System.out.println("group = " + grpPath.toString());
		System.out.println("parent = " + grpName);
		Workspace wks = WBConnectionManager.getWorkspace();											
		Group grp = wks.getGroup(grpPath);
		if(null == grp) {
			grp = wks.getModelFactoryImpl().createGroup();
			grp.setName(prefix);
			grp.setParent(wks.getGroup(new DefaultGroupPath(grpName)));
			grp.setDescription("description for " + prefix);
			grp.setOwner(wks.getUser("odedn"));
			grp.setUsersGroup(wks.getUsersGroup("default"));					
			grp.setStereoType("Server");
			grp.save();
		}
		return(grp);	
	}
	public static void main(String args[])
	{
		try {
			//initDb2Repository();
			initWorkSpace();
			//Group grp = getFullGroup("MatafDev.Tests.Fields","Common","GKSN_0000");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
