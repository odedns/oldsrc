
import java.sql.*;         // Package for JDBC core API
import javax.sql.*;        // Package for JDBC extension API
import oracle.jdbc.pool.*; // Package for connection pooling.

public class PoolTest {

	static final int MAX = 15;


	public static void main(String argv[])
	{
		OracleConnectionCacheImpl m_connectionPool = null;
		Connection connVec[]  = new Connection[MAX];
		try {

			// Create a OracleConnectionPoolDataSource instance
			OracleConnectionPoolDataSource l_ocpds =
				new OracleConnectionPoolDataSource();
			// Set connection parameters
			String l_url="jdbc:oracle:thin:@199.203.21.23:1521:O816";
			// Sets the connection URL
			l_ocpds.setURL(l_url);
			// Sets the user name
			l_ocpds.setUser("ginshoouser");

			// Sets the password
			l_ocpds.setPassword("homer");

			// Create a  connection  pool
			m_connectionPool = new OracleConnectionCacheImpl(l_ocpds);
			m_connectionPool.setMaxLimit (20);

			for(int i=0; i<MAX; ++i) {
                        	connVec[i] = m_connectionPool.getConnection();
				System.out.println("open connection : " + i);
			}
			for(int i=0; i<MAX; ++i) {
				System.out.println("close connection : " + i);
				connVec[i].close();
			}
			m_connectionPool.close();
                } catch (Exception e) {
			e.printStackTrace();
                }


	}

}


