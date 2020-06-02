package ofek.common.general.util;

import javax.naming.*;
import javax.sql.*;

import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.persistence.*;

import java.sql.*;

/**
 * @author Oded Nissan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class VersionUtils
{

	
	public static String getCurrentSchema() throws Exception
	{		
		InitialContext ic = new InitialContext();
		DataSource ds = (DataSource) ic.lookup("jdbc/ofekds");
		Connection conn = ds.getConnection();
		CallableStatement cs = conn.prepareCall("{call GETCS(?)}");
		cs.registerOutParameter(1,java.sql.Types.VARCHAR);
		cs.execute();
		String currSchema = cs.getString(1);
		return(currSchema); 
	}
	
	public static String getCurrentSchemaFw() throws Exception 
	{
		Transaction trans = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(null));
		trans.begin();
		String procName = "{call GETCS(?)}";
  		StoredProcedureService sps = new StoredProcedureService(procName);
		sps.addOutputParameter(StoredProcedureService.STRING);
		StoredProcedureResults spr = trans.execute(sps);    		
		String currSchema = (String) spr.getOutputValue(0);
		trans.commit();
		return(currSchema);
		
	}

}
