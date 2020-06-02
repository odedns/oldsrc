package mataf.services.electronicjournal;

import com.ibm.dse.base.*;

import mataf.logger.*;
import mataf.operations.general.GenericSrvOp;
import mataf.services.JdbcConnectionService;
import mataf.utils.*;
import java.sql.*;
import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalWrapSrvOp extends GenericSrvOp {

	private static final String JOURNAL_ENTITY = "SLIKA_JOURNAL";
		
	/**
	 * execute the operation.
	 * wrap the slika journal.
	 */
	public void execute() throws Exception
	{
		GLogger.info("JournalWrapSrvOp : wrapping journal ...");
		IndexedCollection ic = (IndexedCollection) getElementAt("journalEntityList");
//		GLogger.debug("ic = " + ic.toString());
		String entities[] = IndexedColUtils.toStringArray(ic,"name");
		wrapJournal(entities);
		
	}
	
	private String currDate()
    {
        StringBuffer stringbuffer = new StringBuffer();
        GregorianCalendar gregoriancalendar = new GregorianCalendar();
        String s = String.valueOf(gregoriancalendar.get(2) + 1);
        if(s.length() < 2)
            s = "0" + s;
        String s1 = String.valueOf(gregoriancalendar.get(5));
        if(s1.length() < 2)
            s1 = "0" + s1;
        String s2 = String.valueOf(gregoriancalendar.get(1));
        stringbuffer.append(s2);
        stringbuffer.append(s);
        stringbuffer.append(s1);
        String s3 = stringbuffer.toString();
        return s3;
    }
	
	private void wrapJournal(String entities[]) throws Exception
	{
		JdbcConnectionService srv = (JdbcConnectionService) Service.readObject("jdbcDSConnection");
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		int maxWrapNum = 0;
		int wrapNum = 0;
		
		try {
			conn = srv.getConnection();
			st = conn.createStatement();
			String sql = "SELECT WRAP_N FROM DSEJOUCT WHERE ENTITY_NAME='DSEJOU'";	
			rs = st.executeQuery(sql);
			if(rs.next()) {
				maxWrapNum = rs.getInt(1);
			}						
			rs.close();
			
			for(int i=0; i < entities.length; ++i) {
				GLogger.debug("processing entity : " + entities[i]);
				sql = "SELECT WRAP_N FROM DSEJOUCT WHERE ENTITY_NAME='" + entities[i] + "'";
				rs = st.executeQuery(sql);
				if(rs.next()) {
					wrapNum = rs.getInt(1);
				}		
			
				++wrapNum;
				wrapNum = wrapNum > maxWrapNum ? 0 : wrapNum;
			
				sql = "UPDATE DSEJOUCT SET WRAP_N=" + wrapNum + ",LAST_DATE=" + currDate() + " WHERE ENTITY_NAME='" + entities[i] + "'";
				st.executeUpdate(sql);
				rs.close();	
			}
			
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(st != null) {
					st.close();	
				}
				if(conn != null) {
					conn.close();	
				}
				
			} catch(Exception e) {				
				e.printStackTrace();	
			}
			
		}
			
		
	}
}
