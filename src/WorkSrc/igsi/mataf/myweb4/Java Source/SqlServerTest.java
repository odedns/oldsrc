
import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * Insert the type's description here.
 * Creation date: (10/12/02 11:13:01)
 * @author: Oded
 */
public class SqlServerTest {
/**
 * DBtest constructor comment.
 */


static Connection getConnection2() throws Exception
{	
	  String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";	
	  String url = "jdbc:microsoft:sqlserver://10.11.103.216:1433";
      Class.forName(driver).newInstance();
      Properties props = new Properties();
      props.put("DatabaseName","MATAFDB");            
      props.put("User","mataf");      
      props.put("Password","mataf");            
      Connection c =java.sql.DriverManager.getConnection(url,props);
      return(c);	
}

static Connection getConnection() throws Exception
{
	//String ctxFactory = "com.ibm.ejs.ns.jndi.CNInitialContextFactory";
	String ctxFactory = "com.ibm.websphere.naming.WsnInitialContextFactory";
	javax.naming.InitialContext context = null;
	java.util.Hashtable hash = new java.util.Hashtable();
	hash.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, ctxFactory);
	hash.put(javax.naming.Context.PROVIDER_URL, "iiop://10.11.103.216:2809");
	context = new javax.naming.InitialContext(hash);
	javax.sql.DataSource dataSource = (javax.sql.DataSource) context.lookup("jdbc/wasmatafdb");	

	System.out.println("got Datasource ds = " + dataSource.toString());
	//Connection conn = dataSource.getConnection("mataf","mataf");
	
	
	Connection conn = dataSource.getConnection();
	return(conn);
}

static void testDb()
{
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	try {
		System.out.println("encoding = " + System.getProperty("file.encoding"));
		conn = getConnection();
		System.out.println("testDb - gotConnection");
		st = conn.createStatement();
		String sql = "select * from glst_snif";
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
public static void main(String args[])
	{
		
		System.out.println("SqlServerTest.main");
		testDb();
		
	}
}

