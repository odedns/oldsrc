package onjlib.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created on 07/06/2005
 * @author Oded Nissan
 *
 * DriverConnection manager. Implementation of ConnectionManager using
 * a JDBC driver.
 */
public class DriverConnectionManager extends ConnectionManager {

	private Properties m_props;

	/**
	 * empty constructor.
	 */
	public DriverConnectionManager()
	{		
	}
	
	/**
	 * initialize the ConnectionManager.
	 * @param props the Properties to use.
	 */
	public void init(Properties props) throws DbException
	{
		m_props = props;
		try {
			Class.forName(m_props.getProperty("jdbc.driver"));
		} catch(ClassNotFoundException ce) {
			throw new DbException("Error loading driver.", ce);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see onjlib.db.ConnectionManager#getConnection()
	 */
	public Connection getConnection() throws DbException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(m_props.getProperty("jdbc.url"));
		} catch(SQLException se) {
			throw new DbException("Error getting connection",se);
		}
		return (conn);
	}

	/**
	 * @return Returns the m_props.
	 */
	public Properties getProperties() {
		return m_props;
	}
	/**
	 * @param m_props The m_props to set.
	 */
	public void setProperties(Properties props) {
		this.m_props = props;
	}
}
