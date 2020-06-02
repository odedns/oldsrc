package mataf.dwplugin;
import java.sql.*;
import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.model.db2.*;
import com.ibm.dse.dw.model.db2.base.DefaultRepository;
import com.ibm.dse.dw.plugin.DevelopmentWorkbenchPlugin;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class RepositoryDBConnection  {

	static java.sql.Connection m_conn = null;
	public static java.sql.Connection getConnection() throws Exception
	{		
	  if(null == m_conn) {
	  	
	      Driver drv = (Driver) Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
    	  ConnectionManager cm = DevelopmentWorkbenchPlugin.getDefault().getConnectionManager();
	      RepositoryConnection rconn = (RepositoryConnection) cm.getActiveRepositoryConnection();
    	  String url = rconn.getUrl();
	      m_conn =java.sql.DriverManager.getConnection(url);	    
	      LogFile.getInstance().log("got JDBC Connection");
	  }
       return(m_conn);
	
	}
	
	
	public static void close() throws Exception
	{
		m_conn.close();
		
	}
	

	
	public static void main(String argv[])
	{
		try {
			System.out.println("connecting to db...");
			RepositoryConnection rconn = new RepositoryConnection();
			rconn.setUrl("jdbc:db2:REPLOCAL");
			java.sql.Connection conn = RepositoryDBConnection.getConnection();
			System.out.println("connected !!...");
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();	
		}
			
	}

}
