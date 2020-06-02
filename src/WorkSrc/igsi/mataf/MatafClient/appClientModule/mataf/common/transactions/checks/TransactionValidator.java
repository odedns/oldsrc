/*
 * Created on 25/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.transactions.checks;

import javax.swing.JOptionPane;

import mataf.common.operationsteps.CheckAccess;
import mataf.desktop.beans.MatafTaskInfo;
import mataf.types.MatafOptionPane;

import com.ibm.dse.base.OperationStep;
import com.ibm.dse.desktop.Desktop;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class TransactionValidator {
	protected CheckAccess mainStep;

	public TransactionValidator(OperationStep mainOpStep) {
		this.mainStep = (CheckAccess) mainOpStep;
	}
	protected void closeTask(String errorMessage) {
		mainStep.getDseTaskButton().closeTask();
		showDialog(errorMessage);

	}

	private void showDialog(String errorMessage) {
		//mainStep.getService("");
		MatafOptionPane.showMessageDialog(Desktop.getDesktop(), errorMessage, "שגיאה", JOptionPane.ERROR_MESSAGE);
	}
	public abstract boolean validate(MatafTaskInfo info) throws Exception;
}
