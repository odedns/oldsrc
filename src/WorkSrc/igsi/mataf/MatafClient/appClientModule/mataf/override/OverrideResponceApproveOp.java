package mataf.override;

import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.types.MatafTextField;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.OperationRepliedEvent;

import mataf.desktop.beans.MatafWorkingArea;
import mataf.dse.appl.OpenDesktop;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OverrideResponceApproveOp extends BasicClientOp
{

	/**
	 * Constructor for OverrideResponceApproveOp.
	 */
	public OverrideResponceApproveOp()
	{
		super();
	}

	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute() throws Exception
	{
		try {
			//Context ctxCurrentContext = getContextNamed("overrideCtx");
			OverrideView ovCurrent = (OverrideView)MatafWorkingArea.getActiveTransactionView();
			Context ctxCurrentContext =ovCurrent.getContext();
			MatafTextField sptField=ovCurrent.getOverridePanel().getOverrideResponcePanel().getASpTextField();		
			ctxCurrentContext.setValueAt("status",new Integer(OverrideConstants.OVERRIDE_ACCEPT_REPLY));
			ctxCurrentContext.setValueAt("comment",sptField.getText());		
			OverrideReplyClientOp clientOp = (OverrideReplyClientOp) DSEClientOperation.readObject("overrideReplyClientOp");
			clientOp.setContext(ctxCurrentContext);
			clientOp.execute();
			clientOp.close();
			fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
		} catch(Exception e) {
			GLogger.error(this.getClass(), null, "Exception in OverrideResponseApproveOp : ",e,false);								
			setError(" ����� ������ ����� ������ ���� �����  : " + e.getMessage());		
		}
	}

}
