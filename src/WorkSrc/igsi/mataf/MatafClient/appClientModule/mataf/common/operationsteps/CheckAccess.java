/*
 * Created on 15/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.operationsteps;

import java.util.Vector;

import mataf.common.transactions.checks.TransactionValidator;
import mataf.common.transactions.checks.ValidateBranchStatus;
import mataf.common.transactions.checks.ValidateCashRegisterStatus;
import mataf.common.transactions.checks.ValidateConnectionToHost;
import mataf.common.transactions.checks.ValidateConnectionToPrinter;
import mataf.common.transactions.checks.ValidateNumberOfTrx;
import mataf.common.transactions.checks.ValidateWorkstationStatus;
import mataf.desktop.beans.MatafTaskInfo;
import mataf.services.proxy.ProxyService;

import com.ibm.dse.base.OperationStep;
import com.ibm.dse.desktop.DSETaskButton;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.TaskButton;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CheckAccess extends OperationStep {
	private Vector checksVector;
	private ProxyService proxy;
	private DSETaskButton dseTaskButton;
	private MatafTaskInfo taskInfo;

	public CheckAccess() {
		super();
		try {
			checksVector = new Vector();
			TaskButton taskButton = Desktop.getDesktop().getTaskArea().getCurrentTask();
			if (taskButton instanceof DSETaskButton) {
				dseTaskButton = (DSETaskButton) taskButton;
				if (dseTaskButton == null)
					throw new Exception("Can not activate transaction, the TaskButton is null " + this);
			}
			taskInfo = (MatafTaskInfo) Desktop.getDesktop().getTaskInfo(dseTaskButton.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int execute() throws Exception {
		buildCecksVector();
		boolean isValid = false;
		for (int i = 0; i < checksVector.size(); i++) {
			TransactionValidator validator = (TransactionValidator) checksVector.get(i);
			isValid = taskInfo.accept(validator);
			if (!isValid) {
				//should be taken from error table in the future
				return RC_ERROR;
			}

		}
		// Activate runtime security policy checks
		try {
			proxy = (ProxyService) getService("proxyService");
			boolean canActivate = proxy.checkAccess(taskInfo.getTaskName());
			if (! canActivate)
				return RC_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return RC_OK;
	}

	private void buildCecksVector() {
		checksVector.add(new ValidateConnectionToHost(this));
		checksVector.add(new ValidateBranchStatus(this));
		checksVector.add(new ValidateWorkstationStatus(this));
		checksVector.add(new ValidateConnectionToPrinter(this));
		checksVector.add(new ValidateCashRegisterStatus(this));
		checksVector.add(new ValidateNumberOfTrx(this));
	}

	public MatafTaskInfo getTaskInfo() {
		return taskInfo;
	}

	public DSETaskButton getDseTaskButton() {
		return dseTaskButton;
	}

}
