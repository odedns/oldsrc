package mataf.override;

import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */  
public class TestPanelOp extends DSEClientOperation {

	public void execute()
		throws Exception
		
	{
		GLogger.debug("in TestPanelOp.execute()");
		Context ctx = getContext();
		setValueAt("AccountNumber","123456789");
		setValueAt("BranchIdInput","999");
		setValueAt("trxORData.trxUuid", new String(new Long(System.currentTimeMillis()).toString()));
		setValueAt("trxORData.trxId","T410");
		setValueAt("trxORData.trxName","סריקת צקים");		
		setValueAt("trxORData.viewName","mataf.slika.panels.SlikaSummaryPanel");
		setValueAt("trxORData.ctxName","slikaCtx");
		GLogger.debug("ctx = " + ctx.toString());		
		fireHandleOperationRepliedEvent(new com.ibm.dse.base.OperationRepliedEvent(this));
	}
}
