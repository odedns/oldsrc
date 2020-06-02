/*
 * Created on 26/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.sql.*;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CloudscapeTest
{

	static Connection getConnection() throws Exception
	{
	
		Class.forName("com.ibm.db2j.jdbc.DB2jDriver");
		Connection c = java.sql.DriverManager.getConnection("jdbc:db2j:c:/temp/cloudscape/mydb;create=false");

		
			return(c);
	
	}
	
	static Connection getDSConnection() throws Exception
	{
		String url       = "iiop://localhost:2809";
		String factory = "com.ibm.websphere.naming.WsnInitialContextFactory";
		
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,factory);
		prop.put(Context.PROVIDER_URL, url);
		Context ctx       = new InitialContext(prop);
		Object o = ctx.lookup("jdbc/mydb");
		DataSource dataSource = (DataSource) javax.rmi.PortableRemoteObject.narrow(o, DataSource.class);	
	

		System.out.println("got Datasource");
		Connection conn = dataSource.getConnection();
	
	
		//Connection conn = dataSource.getConnection();
		return(conn);
	}

	
	
	
	public static void main(String[] args)
	{
		Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			
			try {
				conn = getDSConnection();
				System.out.println("testDb - gotConnection");
				st = conn.createStatement();
				String sql = "select * from messages";
				rs = st.executeQuery(sql);
				if(rs.next()) {
					System.out.println("rs = " + rs.toString());	
				}

		

			} catch(Exception e) {
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
