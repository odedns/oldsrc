
import java.sql.*;
import java.util.Properties;


/**
 * Connection manager class
 * creates a JDBC connection to the database.
 */
public abstract class ConnectionManager {
	static Connection m_conn=null;

	/**
	 * get a JDBC Connection.
	 * If the Connection is connected
	 * return the active connection.
	 */
	public static Connection getConnection()
	{
		if(m_conn == null) {
			m_conn = dbConnect();
		}
		return(m_conn);
	}

	/**
	 * connect to DB.
	 */
	static Connection dbConnect()
	{
		Connection conn=null;

		Properties props = new Properties();
		props.put("user", "sa");
		props.put("password", "enfopus");
		props.put("server", "nachal"); // yam
		props.put("db","northwind");
		props.put("weblogic.codeset","cp1255");

		try {
			Driver myDriver = (Driver)Class.forName("weblogic.jdbc.mssqlserver4.Driver").newInstance();
		    conn = myDriver.connect("jdbc:weblogic:mssqlserver4", props);
		    conn.setAutoCommit(true);

		} catch (Exception e) {
		    e.printStackTrace();
			conn = null;
		}
		return(conn);
	}
	/**
	 * close the JDBC connection.
	 */
	public static void close()
	{

		try {
		    m_conn.close();
		    m_conn = null;
		} catch (SQLException ex) {
		    ex.printStackTrace();
		}
	}



	/**
	 * test program
	 */
	public static void main(String argv[])
	{
		Connection conn = ConnectionManager.getConnection();
		conn = ConnectionManager.getConnection();


	}


}
