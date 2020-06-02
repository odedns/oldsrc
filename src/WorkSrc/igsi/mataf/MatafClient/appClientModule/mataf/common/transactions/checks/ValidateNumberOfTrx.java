/*
 * Created on 25/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.transactions.checks;

import java.util.Vector;

import mataf.desktop.MatafTaskButton;
import mataf.desktop.beans.MatafTaskInfo;

import com.ibm.dse.base.OperationStep;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.TaskArea;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValidateNumberOfTrx extends TransactionValidator {

	public ValidateNumberOfTrx(OperationStep mainOpStep) {
		super(mainOpStep);
	}
	public boolean validate(MatafTaskInfo taskInfo) throws Exception {
		if (getNumberOfTransactions()-1 > 0) {
			closeTask("קיימת עסקה פתוחה, לא ניתן לפתוח עסקה נוספת"); 
			return false;
		}
		return true;
	}
	
	private int getNumberOfTransactions() {
		int numberOfTransactions = 0;
		TaskArea taskArea = Desktop.getDesktop().getTaskArea();
		MatafTaskButton taskButton = null;
		Vector tasks = taskArea.getTasks();
		String operationType = null;
		for (int i = 0; i<tasks.size(); i++) {
			try {
				taskButton = (MatafTaskButton)tasks.elementAt(i);
			} catch (Exception e) {
				// We could not cast so it is not a new Trx and we do nothing
				continue;
			}
			operationType = ((MatafTaskInfo)Desktop.getDesktop().getTaskInfo(taskButton.getName())).getOperationType();
			if (operationType.equalsIgnoreCase("transaction"))
				numberOfTransactions ++;
		}
		return numberOfTransactions;
	}
}
