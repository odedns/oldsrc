package tests;

import mataf.utils.*;
import com.ibm.dse.base.*;
import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.model.startup.*;
import com.ibm.dse.dw.model.db2.*;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RepTest {

	/**
	 * Constructor for RepTest.
	 */
	public RepTest() {
		super();
	}

	static Repository initRepository() throws Exception
	{
		Startup.getDefault().start(new File("d:/work/repository/").toURL());
		
		RepositoryConnection conn = new RepositoryConnection();
		conn.setApplicationUser("odedn");
		conn.setSystemUser("odedn");
		conn.setSystemPassword("odedn01");
		conn.setApplicationUserName("odedn");
		conn.setUrl("jdbc:db2:REPLOCAL");
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
	//	conn.disconnect();
		return(rep);
		
		
	}
	
	
	public static Instance[] getInstancesForGroup(Repository rep, Group grp) 
			
	{
		String ver = grp.getVersion();
		String path = grp.getPath().toString();
		Statement st = null;
		ResultSet rs = null;
		Instance ins=null;
		ArrayList ar = new ArrayList();

		try {
			java.sql.Connection conn = DBConnectionManager.getConnection("REPLOCAL");
			st = conn.createStatement();
			String sql = "SELECT DISTINCT PATH FROM INSTANCES WHERE PATH LIKE '%"+path + "%'";

			System.out.println("sql = " +sql);
			rs = st.executeQuery(sql);
			while(rs.next()) {
				path = rs.getString(1);
				System.out.println("got path = " + path);
				GroupPath gPath = grp.getPath();

				ins = rep.getInstance(new DefaultInstancePath(path),null);
//				System.out.println("got ins = " + ins.getPath() + " version =  " + ins.getVersion());
				ar.add(ins);
			}
	
		} catch(Exception e) {
			
			e.printStackTrace();		
		}	 finally {
         try {
         	if(null != rs) {
	            rs.close();
         	}
         	if(null != st) {
	            st.close();
         	}
          } catch(SQLException se) {
            se.printStackTrace();
          }
    } //finally
		Instance v[] = new Instance[ar.size()];
		v = (Instance[]) ar.toArray(v);
		return(v);	
	}
	public static void main(String[] args) {
		try {
			Repository rep = initRepository();

			Group grp = rep.getGroup(new DefaultGroupPath("Mygrp.grp2"),null);
			System.out.println("got group = " + grp.getName() + " vers = " + grp.getVersion());
			Instance ins[] = getInstancesForGroup(rep,grp);
			for(int i=0; i < ins.length; ++i ) {
				System.out.println("got instance: " + ins[i].getName() + " version: " + ins[i].getVersion());	
			}
		} catch(Exception e) {
			e.printStackTrace();	
	    }  

	}
}
