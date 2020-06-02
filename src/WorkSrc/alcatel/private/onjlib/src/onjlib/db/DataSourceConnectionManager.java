/*
 * Created on 09/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package onjlib.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import onjlib.utils.GWrappingException;

/**
 * @author Oded Nissan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */			
public class DataSourceConnectionManager extends ConnectionManager {

	private static DataSource m_ds = null;
	/* (non-Javadoc)
	 * @see onjlib.db.ConnectionManager#init(java.util.Properties)
	 */
	public void init(Properties props) throws DbException {
		// TODO Auto-generated method stub
		try {
			InitialContext ctx = new InitialContext();
			String dsName = props.getProperty("jdbc.datasource");
			if(null == dsName) {
				throw new GWrappingException("No DataSource name specified");
			}
			m_ds = (DataSource)ctx.lookup(dsName);
		} catch(Exception e) {
			throw new DbException("Error initializing DataSource",e);
		}

	}

	/* (non-Javadoc)
	 * @see onjlib.db.ConnectionManager#getConnection()
	 */
	public Connection getConnection() throws DbException {
		// TODO Auto-generated method stub
		Connection conn = null;
		if(m_ds == null) {
			return(null);
		}
		try {
			conn = m_ds.getConnection();
		} catch(SQLException se){
			throw new DbException("Error getting connection from Datasource",se);
		}
		return (conn);
	}

}
