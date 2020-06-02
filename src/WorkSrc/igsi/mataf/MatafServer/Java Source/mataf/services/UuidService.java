package mataf.services;

import com.ibm.dse.base.*;
import javax.activation.DataSource;
import javax.sql.*;
import java.sql.*;
import java.io.IOException;

/**
 * @author Oded Nissan 30/09/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UuidService extends Service {
	
	private static final String DEF_DOMAIN = "DEFAULT";
	private static final String UUID_QUERY = "SELECT IDNUMBER FROM MATAF_UUID WHERE DOMAIN=?";
	private static final String UUID_UPDATE = "UPDATE MATAF_UUID SET IDNUMBER=? WHERE DOMAIN=?";
	String m_jdbcServiceName = null;
	JdbcConnectionService m_jdbcSrv = null;
	int m_chunkSize = 1;
		
	/**
	 * initialize from external definition.
	 */	
	public java.lang.Object initializeFrom(Tag aTag)
               throws java.io.IOException, DSEException
	{
		super.initializeFrom(aTag);		
		String name = null;
		String value = null;
		
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			name = attribute.getName();
			value = (String) attribute.getValue();
			if (name.equals("jdbcService")) {	
				m_jdbcServiceName = value;
				continue;
			}
			if (name.equals("chunkSize")) {	
				m_chunkSize = Integer.parseInt(value);
				continue;
			}

	
		}	// for
		init();
		return(this);

	}


	/**
	 * get the jdbc service object.
	 */
	void init() throws IOException
	{
		m_jdbcSrv = (JdbcConnectionService) Service.readObject(m_jdbcServiceName);	
	}	

	/**
	 * get the next unique uid from the table.
	 * @return int the next available unique uid.
	 */
	public int getUuid()
		throws Exception
	{
		return(getUuid(DEF_DOMAIN));	
	}
	
	/**
	 * get the next unique uid from the table.
	 * @param domain the domain on which to update the uuid.
	 * @return int the next available unique uid.
	 */
	public int getUuid(String domain)
		throws Exception
	{
		int uuid = -1;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			conn = m_jdbcSrv.getConnection();
			conn.setAutoCommit(false);
			st = conn.prepareStatement(UUID_QUERY);
			st.setString(1,domain);
			rs = st.executeQuery();
			if(rs.next()) {
				uuid = rs.getInt(1);			
			}
			st.close();
			st = conn.prepareStatement(UUID_UPDATE);
			st.setInt(1,uuid+1);
			st.setString(2,domain);
			st.execute();
			conn.commit();
			conn.setAutoCommit(true);
		} finally {
			try {
				if(null != rs) {
					rs.close();
				}
				if(st != null) {
					st.close();
				}
				if(conn != null) {
					conn.close();	
				}				

		    } catch(java.sql.SQLException se) {
				se.printStackTrace();
		    }
		} //finally	
		
		return(uuid);		
	}
}
