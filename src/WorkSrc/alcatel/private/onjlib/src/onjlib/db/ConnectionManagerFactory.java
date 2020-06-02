package onjlib.db;

import java.util.Properties;
import onjlib.utils.PropertyReader;

/**
 * Created on 07/06/2005
 * @author  P0006439  TODO To change the template for this generated type comment go to  Window - Preferences - Java - Code Style - Code Templates
 */
public class ConnectionManagerFactory {

	private static ConnectionManager m_connMng = null;
	private static Properties m_props = null;
	
	static {
		try {
			m_props = PropertyReader.read("/jdbc.properties");
			String clazzName = m_props.getProperty("default.connectionmanager");
			m_connMng = (ConnectionManager) Class.forName(clazzName).newInstance();
			m_connMng.init(m_props);
		} catch(Exception e) {
			m_connMng = null;
			System.out.println("Error on ConnectionManagerFactory initialization");
			e.printStackTrace();
		}
	}
	
	/**
	 * retrieve a ConnectionManager instance.
	 * @return ConnectionManager the current ConnectionManager.
	 */
	public static ConnectionManager getConnectionManager()
	{		
		return(m_connMng);
	}
	
	
	/**
	 * set the current connection manager to use.
	 * @param mng The ConnectionManager to use.
	 */
	public static void setConnectionManager(ConnectionManager mng)
	{
		m_connMng= mng;
	}
}
