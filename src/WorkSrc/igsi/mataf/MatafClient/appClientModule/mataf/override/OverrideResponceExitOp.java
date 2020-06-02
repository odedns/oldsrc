/*
 * Created on 03/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.override;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.OperationRepliedEvent;

import mataf.desktop.beans.MatafWorkingArea;
import mataf.dse.appl.OpenDesktop;
import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.types.MatafTextField;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OverrideResponceExitOp extends BasicClientOp
{
		/**
		 * Constructor for OverrideResponceExitOp.
		 */
		public OverrideResponceExitOp()
		{
			super();
		}
		
	/**
		 * @see com.ibm.dse.base.Operation#execute()
		 */
		public void execute() throws Exception
		{
			try {
				//Context ctxCurrentContext = getParent().getContextNamed("overrideCtx");			
				//OverrideView ovCurrent = (OverrideView)OpenDesktop.getActiveTransactionView();
				OverrideView ovCurrent = (OverrideView)MatafWorkingArea.getActiveTransactionView();
				Context ctxCurrentContext =ovCurrent.getContext();
				
				//MatafTextField sptField=ovCurrent.getOverridePanel().getOverrideResponcePanel().getASpTextField();
				ctxCurrentContext.setValueAt("status",new Integer(OverrideConstants.OVERRIDE_EXIT_REPLY));
				//ctxCurrentContext.setValueAt("comment",sptField.getText());		
				OverrideReplyClientOp clientOp = (OverrideReplyClientOp) DSEClientOperation.readObject("overrideReplyClientOp");
				clientOp.setContext(ctxCurrentContext);
				clientOp.execute();
				clientOp.close();
				fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
			} catch(Exception e) {
				GLogger.error(this.getClass(), null, "Exception in OverrideResponseRefuseOp  ", e,false);								
				setError(" שגיאה בשליחת תשובה לאישור מנהל מרוחק  : " + e.getMessage());	
			}
	
		}
		
}
