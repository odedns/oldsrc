/*
 * Created on 20/04/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.operationsteps;

import mataf.desktop.beans.MatafTaskInfo;
import mataf.logger.GLogger;

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
public class MapTaskAttributes extends OperationStep {
	private DSETaskButton dseTaskButton;
	private MatafTaskInfo taskInfo;
	/**
	 * 
	 */
	public MapTaskAttributes() {
		super();
		try {
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
		try {
			setValueAt("taskAttributes.branchStatus", taskInfo.getBranchStatus());
			setValueAt("taskAttributes.cashRegisterStatus", taskInfo.getCashRegisterStatus());
			setValueAt("taskAttributes.cashRegisterType", taskInfo.getCashRegisterType());
			setValueAt("taskAttributes.connectionToHost", taskInfo.getConnectionToHost());
			setValueAt("taskAttributes.connectionToPrinter", taskInfo.getConnectionToPrinter());
			setValueAt("taskAttributes.workstationStatus", taskInfo.getWorkstationStatus());
			setValueAt("taskAttributes.transactionType", taskInfo.getTransactionType());
			setValueAt("taskAttributes.operationType", taskInfo.getOperationType());
			setValueAt("taskAttributes.trxId", taskInfo.getCode());
		} catch (Exception e) {
			GLogger.error(this.getClass() ,null,null,e,false);
			return RC_ERROR;
		}
		
		return RC_OK;
	}

}
