package work;

import java.sql.*;

import java.util.*;
import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DB2Magic {

	public static void main(String[] args) {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
			
	try {	
		/*
		Driver drv = (Driver) Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
    	String url = "jdbc:db2://10.11.103.216:50000/MAGICDB2";
    	conn =java.sql.DriverManager.getConnection(url,"db2admin","db2admin");		
    	*/
    	System.out.println("encoding = " + System.getProperty("file.encoding"));
 	  	Driver drv = (Driver) Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
        String url = "jdbc:db2:MAGICDB2";
        conn =java.sql.DriverManager.getConnection(url,"db2admin","db2admin");		
		System.out.println("testDb - gotConnection");
		st = conn.createStatement();
		String sql = "select IS_SRC_MASHOV from MAGIC.ASSIGND";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			String s = rs.getString(1);
			String n = new String (s.getBytes(),"Cp862");
			System.out.println("IS_SRC_MASHOV: " + n);
		}
	}  catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		try {
			
			if(null != conn) {
				conn.close();
			}
			if(null != st) {
				st.close();
			}
			if(null != rs) {
				rs.close();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}

	}

	}	
}
