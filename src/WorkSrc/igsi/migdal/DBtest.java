package work;
import java.sql.*;
import migdal.common.mechira.MechiraDataLayer;
import java.util.*;

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


static Connection getConnection() throws Exception
{
	String ctxFactory = "com.ibm.ejs.ns.jndi.CNInitialContextFactory";
	javax.naming.InitialContext context = null;
	java.util.Hashtable hash = new java.util.Hashtable();
	hash.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, ctxFactory);
	hash.put(javax.naming.Context.PROVIDER_URL, "iiop://localhost");
	context = new javax.naming.InitialContext(hash);
	javax.sql.DataSource dataSource = (javax.sql.DataSource) context.lookup("jdbc/migdal");	
	Connection conn = dataSource.getConnection("pilot","pilot");
	return(conn);
}

static void testDb()
{
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	try {
		conn = getConnection();
		st = conn.createStatement();
		String sql = "select * from t0021 where kod_chevra=1";
		rs = st.executeQuery(sql);
		int i = 0;
		/*
		while(rs.next()) {
			++i;
		}
		System.out.println("got : " + i + " rows");
		*/

	} catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		try {
			/*
			if(null != conn) {
				conn.close();
			}
			if(null != st) {
				st.close();
			}
			if(null != rs) {
				rs.close();
			}
			*/
		} catch(Exception e) {
			e.printStackTrace();
			
		}

	}

}
public static void main(String args[])
	{
		long mem = Runtime.getRuntime().freeMemory();
		System.out.println("mem before: " + mem);
		for(int i=0; i < 10; ++i) {
			testDb();
		}
		mem = Runtime.getRuntime().freeMemory();
		System.out.println("mem after: " + mem);
	}
}
