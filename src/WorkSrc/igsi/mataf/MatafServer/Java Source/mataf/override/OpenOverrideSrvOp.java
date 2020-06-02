package mataf.override;

import mataf.logger.GLogger;

import com.ibm.dse.services.jdbc.JDBCTable;
import com.ibm.dse.base.*;
import java.util.Enumeration;

/**
 * @author Oded Nissan 18/11/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OpenOverrideSrvOp extends DSEServerOperation {

	public void execute() throws Exception
	{
		GLogger.debug("OpenOverrideSrvOp");
		GLogger.debug("opening request : " + getValueAt("trxUuid"));
		JDBCTable tblSvc = (JDBCTable) getService("overrideTable");
		Context ctx = (Context) Context.readObject("matafOverrideTableCtx");
		HashtableFormat fmt = (HashtableFormat) FormatElement.readObject("matafOverrideTableFmt");			
		String id = (String) getValueAt("trxUuid");
		GLogger.debug("updating trxuuid = " + id);	
		Vector v = tblSvc.retrieveRecordsMatching("IDNUMBER =" + id);
		if(!v.isEmpty()) {
			Enumeration enum = v.elements();
			Hashtable ht = (Hashtable) enum.nextElement();
			fmt.unformat(ht,ctx);
			ctx.setValueAt("STATUS", new Integer(OverrideConstants.OVERRIDE_OPEN));
			tblSvc.updateRecordsMatching("IDNUMBER=" + id,ctx,fmt);
			GLogger.info("opened  override request:" + id);
		}
				
				
	} // execute

}
