package mataf.override;

import java.util.Enumeration;
import mataf.utils.*;
import mataf.logger.*;
import mataf.operations.general.GenericSrvOp;
import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.JDBCTable;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.*;

/**
 * @author Oded Nissan 29/9/2003
 * This class handles the override reply sent from the manager.
 * On the server side it updates the override tables with the appropriate
 * status and reason for the override request.
 */
public class OverrideReplyServerOp extends GenericSrvOp {

	/**
	 * execute the operation.
	 * @throws Exception in case of error.
	 */
	public void execute()
		throws Exception
		
	{
		GLogger.debug("In OverrideReplyServerOp.execute()");
		JDBCTable tblSvc = (JDBCTable) getService("overrideTable");
		Context ctx = (Context) Context.readObject("matafOverrideTableCtx");
		HashtableFormat fmt = (HashtableFormat) FormatElement.readObject("matafOverrideTableFmt");			
		String id = (String) getValueAt("trxUuid");		
		Vector v = tblSvc.retrieveRecordsMatching("IDNUMBER =" + id);
		if(!v.isEmpty()) {
			Enumeration enum = v.elements();
			Hashtable ht = (Hashtable) enum.nextElement();
			fmt.unformat(ht,ctx);
			ctx.setValueAt("STATUS", new Integer(OverrideConstants.OVERRIDE_REPLY));
			tblSvc.updateRecordsMatching("IDNUMBER=" + id,ctx,fmt);
			GLogger.info("updated db override reply for request:" + id);
		}

	}
	
	
	
	
}
