/*
 * Created on 25/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.transactions.checks;

import mataf.desktop.beans.MatafTaskInfo;
import mataf.operations.CheckConnectionToPrinter;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.OperationStep;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValidateConnectionToPrinter extends TransactionValidator {
	
	public ValidateConnectionToPrinter(OperationStep mainOpStep){
		super(mainOpStep);
	}
	
	public boolean validate(MatafTaskInfo taskInfo) throws Exception{
		String connectionToPrinter = taskInfo.getConnectionToPrinter();
		if (!"dontCare".equalsIgnoreCase(connectionToPrinter)) {
			CheckConnectionToPrinter operation = (CheckConnectionToPrinter) DSEClientOperation.readObject("checkConnectionToPrinterClientOp");
			try {
				operation.execute();
			} catch (Exception e) {
				e.printStackTrace();
//				the following message should be taken from a table
				closeTask("פעולה לא ניתנת לביצוע תקלה בבדיקת קשר למדפסת");
				return false;
			}

			String returnCode = (String) operation.getValueAt("returnCode");
			operation.close();

			if (("true".equalsIgnoreCase(connectionToPrinter)) & (returnCode.equals("1"))) {				
				// the following message should be taken from a table
				closeTask("פעולה לא ניתנת לביצוע ללא קשר למדפסת");
				return false;
			} else if (("false".equalsIgnoreCase(connectionToPrinter)) & (returnCode.equals("0"))) {
				// the following message should be taken from a table
				closeTask("פעולה לא ניתנת לביצוע כאשר מדפסת מחוברת");
				return false;
			}
		}
		return true;
	}

}
