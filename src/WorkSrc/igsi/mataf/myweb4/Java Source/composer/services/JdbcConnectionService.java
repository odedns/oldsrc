package composer.services;

import com.ibm.dse.base.*;
import javax.sql.*;
import javax.naming.*;
import java.sql.*;
import java.util.*;

/**
 * This service enables the user to get a JDBC connection.
 * The connection can be retrieved from the driver manager or from 
 * a DataSource. If the useDatasource attribute is set to false in the 
 * xml file defining the service instance then the connection will be 
 * retrieved using the driver manager.
 * When using a datasource to get a connection we can specify the jdni url if we want 
 * to access the datasource from a non-server context.
 * @author odedn
 * 
 * 			
 * Example of using the service with the Driver: 
 * 
 *    	 <JdbcConnectionService id="jdbcDriverConnection" JDBCDriver="COM.ibm.db2.jdbc.app.DB2Driver"  
 *    		password="odedn01" user="odedn" databaseURL="jdbc:db2:WKSLOCAL"
 *    		useDatasource="false" />
 *
 * Example of using the service with a Datasource:
 * 
 *    	 <JdbcConnectionService id="jdbcDSConnection" 
 *    		password="odedn01" user="odedn" useDatasource="true"
 *    		jndiURL="iiop://localhost:900"	dataSourceName="jdbc/db2"/>
 *    		
 */
public class JdbcConnectionService extends Service {
	static final String PROVIDER_FACTORY="com.ibm.ejs.ns.jndi.CNInitialContextFactory";
	String m_dsName;
	String m_user;
	String m_pass;
	String m_driver;
	String m_url;
	String m_jndiUrl=null;
	boolean m_useDs;	
	
	
	public JdbcConnectionService()
	{
		System.out.println("JdbcConnectionService()");
	}
	
		/**
	 * set the jdbc user.
	 * @param user the jdbc user.
	 */
	public void setUser(String user)
	{
		m_user = user;
	}
	
	/**
	 * set the jdbc password.
	 * @param pass the jdbc password.
	 */
	public void setPassword(String pass)
	{
		m_pass = pass;	
	}
	/**
	 * set the jdbc url to use.
	 * @param url the jdbc url.
	 */
	public void setJdbcUrl(String url)
	{
		m_url = url;	
	}
	
	/**
	 * get the jdbc url.
	 * @return url the jdbc url.
	 */
	public String getJdbcUrl()
	{
		return(m_url);
	}
	/**
	 * set the jdbc driver.
	 * @param driver the name of the driver class.
	 */
	public void setJdbcDriver(String driver)
	{
		m_driver = driver;
	}	
	
	/**
	 * get the jdbc driver class.
	 * @return String the jdbc driver
	 * class.
	 */
	public String getJdbcDriver()
	{
		return(m_driver);
	}
	/**
	 * set the jdni url.
	 * @param url the jdni url.
	 */
	public void setJndiUrl(String url)
	{
		m_jndiUrl = url;	
	}
	
	/**
	 * get the jndi url.
	 * @return String the jndi url.
	 */
	public String getJndiUrl()
	{
		return(m_jndiUrl);	
	}
	/**
	 * set the Data source name to use.
	 * @param ds the name of the data source 
	 * to use.
	 */
	public void setDataSource(String ds)
	{
		m_dsName = ds;	
	}
	
	/**
	 * get the data source name.
	 * @return String the data source name.
	 */
	public String getDataSource()
	{
		return(m_dsName);
	}

		
	/**
	 * get a connection.
	 * Get the connection from datasource or get it 
	 * directly from the driver, depending on the m_useDs var.	
	 * @param user the db user.
	 * @param pass the db user password.
	 * @return the JDBC Connection.
	 */
	public Connection getConnection(String user, String pass) throws Exception
	{
		m_user = user;
		m_pass = pass;
		return(getConnection());
	}
	/**
	 * get a connection.
	 * Get the connection from datasource or get it 
	 * directly from the driver, depending on the m_useDs var.	
	 * @return the JDBC Connection.
	 */
	public Connection getConnection() throws Exception
	{
		Connection conn = null;
		if(m_useDs) {
			conn = getDSConnection();	
		} else {
			conn = getDriverConnection();	
		}
		return(conn);	
	}


	/**
	 * get a connection using the driver manager.
	 */
	private Connection getDriverConnection() throws Exception
	{
		
      	Class.forName(m_driver).newInstance();
      	Connection conn =java.sql.DriverManager.getConnection(m_url);
		return(conn);
	}
	
	/**
	 * get a JDBC Connection using the datasource name.
	 * @return Connection the JDBC connection from the datasource.
	 * @exception 
	 */
	private Connection getDSConnection() throws Exception
	{	
		InitialContext ctx = null;	
		if(null != m_jndiUrl) {
			Properties prop = new Properties();
			prop.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,PROVIDER_FACTORY);
			prop.put(javax.naming.Context.PROVIDER_URL, m_jndiUrl);	
			ctx = new InitialContext(prop);
		} else {
			ctx = new InitialContext();
		}
		DataSource ds = (DataSource) ctx.lookup(m_dsName);		
		Connection conn = ds.getConnection();
		return(conn);
	}	
	
	

	/**
	 * initialize from externalizer.
	 */
	public java.lang.Object initializeFrom(Tag aTag)
                                throws java.io.IOException,
                                       DSEException
	{
		super.initializeFrom(aTag);
		System.out.println("Tag = " + aTag.toString());
		String name = null;
		String value = null;
		
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			name = attribute.getName();
			value = (String) attribute.getValue();
			if (name.equals("dataSourceName")) {
				System.out.println("ds = " + value);
				m_dsName = value;
				continue;
			}
			if (name.equals("user")) {			
				m_user = value;
				continue;
			}
			if (name.equals("password")) {			
				m_pass = value;
				continue;
			}
			if (name.equals("JDBCDriver")) {			
				m_driver = value;
				continue;
			}
			if (name.equals("databaseURL")) {			
				m_url = value;
				continue;
			}
			if (name.equals("jndiURL")) {			
				m_jndiUrl = value;
				continue;
			}

			if (name.equals("useDatasource")) {			
				if(value.equalsIgnoreCase("false")) {
					m_useDs = false;	
				} else {
					m_useDs = true;	
				}							
			}
	
		}		
		return(this);

	}

}
