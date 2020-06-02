package mataf.override;

import mataf.logger.GLogger;
import mataf.operations.general.GenericSrvOp;

import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 * @author Oded Nissan 29/9/2003
 *
 * This class handles the override request sent from the teller.
 * On the server side it updates the override tables with the appropriate
 * status and reason for the override request.
 */
public class OverrideReqServerOp extends GenericSrvOp {

	/**
	 * execute the operation.
	 * Pack the context and request data into an MQ 
	 * message and send it to the manager.
	 * Update the override table on the server.
	 * @throws Exception in case of error.
	 */
	public void execute()
		throws Exception		
	{
		GLogger.debug("In OverrideReqServerOp.execute()");
		
		JDBCTable tblSvc = (JDBCTable) getService("overrideTable");
		Context ctx = (Context) Context.readObject("matafOverrideTableCtx");
		HashtableFormat fmt = (HashtableFormat) FormatElement.readObject("matafOverrideTableFmt");			
		GLogger.debug("ctx = " + ctx.toString());
		ctx.setValueAt("IDNUMBER",getValueAt("trxUuid"));
		ctx.setValueAt("TRXID",getValueAt("trxId"));
		ctx.setValueAt("STATUS",new Integer(OverrideConstants.OVERRIDE_REQUEST));
		ctx.setValueAt("REASON",getValueAt("reason"));
		ctx.setValueAt("OR_DATE", new java.sql.Date(System.currentTimeMillis()));
		Hashtable ht = new Hashtable();
		ht = fmt.format(ctx);
		GLogger.debug("adding record ht = " + ht.toString());
		tblSvc.addRecord(ht);
		GLogger.debug("record added...");
		
	}
}
