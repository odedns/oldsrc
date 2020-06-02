
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Insert the type's description here.
 * Creation date: (10/12/02 11:13:01)
 * @author: Oded
 */
public class DBtest {
/**
 * DBtest constructor comment.
 */
public DBtest() {
	super();
}


static Connection getConnection2() throws Exception
{
	
	
      Driver drv = (Driver) Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
      Connection c =java.sql.DriverManager.getConnection("jdbc:db2:HARELTST","db2admin","db2admin");
        return(c);
	
}

static Connection getConnection() throws Exception
{
	//String ctxFactory = "com.ibm.ejs.ns.jndi.CNInitialContextFactory";
	String ctxFactory = "com.ibm.websphere.naming.WsnInitialContextFactory";
	javax.naming.InitialContext context = null;
	java.util.Hashtable hash = new java.util.Hashtable();
	hash.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, ctxFactory);
	hash.put(javax.naming.Context.PROVIDER_URL, "iiop://10.1.19.159:2809");
	context = new javax.naming.InitialContext(hash);
	javax.sql.DataSource dataSource = (javax.sql.DataSource) context.lookup("jdbc/ofekds");	
	

	System.out.println("got Datasource");
	Connection conn = dataSource.getConnection();
	
	
	//Connection conn = dataSource.getConnection();
	return(conn);
}

static void testDb()
{
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	try {
		conn = getConnection();
		System.out.println("testDb - gotConnection");
		st = conn.createStatement();
		String sql = "select * from ADDRESS";
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
		System.out.println("version = " + System.getProperty("java.version"));
		System.out.println("DBTest.main");
		testDb();
		
	}
}
