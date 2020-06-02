package tests;
import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.*;
import java.sql.*;

import composer.services.*;
import composer.utils.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JdbcTest {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	public static void main(String[] args) {
	
		try {	
			init();
			
			JdbcConnectionService srv = (JdbcConnectionService) Service.readObject("jdbcDSConnection");
			
			System.out.println("got service: " + srv.toString());
			
			

			Connection conn = srv.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from APP_MSG");
			String s = null;
			while(rs.next()) {
				s = rs.getString(2);
				System.out.println("s = " + s);
			}
			rs.close();
			st.close();
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
