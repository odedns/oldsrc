package mataf.utils;
import java.sql.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class DBConnectionManager {

	public static Connection getConnection(String dbName) throws Exception
	{		
      Driver drv = (Driver) Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
      Connection c =java.sql.DriverManager.getConnection("jdbc:db2:" + dbName);
       return(c);
	
	}
	
	public static void main(String argv[])
	{
		try {
			System.out.println("connecting to db...");
			Connection conn = DBConnectionManager.getConnection("WKSLOCAL");
			System.out.println("connected !!...");
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();	
		}
			
	}

}
