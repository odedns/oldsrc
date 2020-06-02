/*
 * Created on 25/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.transactions.checks;

import mataf.desktop.beans.MatafTaskInfo;
import mataf.operations.CheckConnectionToHost;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.OperationStep;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValidateConnectionToHost extends TransactionValidator {
	
	public ValidateConnectionToHost(OperationStep mainOpStep){
		super(mainOpStep);
	}
	
	public boolean validate(MatafTaskInfo taskInfo) throws Exception{
		String connectionToHost = taskInfo.getConnectionToHost();
		if (!"dontCare".equalsIgnoreCase(connectionToHost)) {
			CheckConnectionToHost operation = (CheckConnectionToHost) DSEClientOperation.readObject("checkConnectionToHostClientOp");
			operation.execute();

			String returnCode = (String) operation.getValueAt("returnCode");
			operation.close();

			if (("true".equalsIgnoreCase(connectionToHost)) & (returnCode.equals("1"))) {				
				// the following message should be taken from a table
				closeTask("פעולה לא ניתנת לביצוע במצב נתק");
				return false;
			} else if (("false".equalsIgnoreCase(connectionToHost)) & (returnCode.equals("0"))) {
				// the following message should be taken from a table
				closeTask("פעולה לא ניתנת לביצוע במצב קשר");
				return false;
			}
		}
		return true;
	}

}
