/*
 * Created on 25/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.transactions.checks;

import mataf.desktop.beans.MatafTaskInfo;

import com.ibm.dse.base.OperationStep;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValidateWorkstationStatus extends TransactionValidator {

	/**
	 * @param mainOpStep
	 */
	public ValidateWorkstationStatus(OperationStep mainOpStep) {
		super(mainOpStep);
	}

	/* (non-Javadoc)
	 * @see mataf.common.transactions.checks.TransactionValidator#validate(mataf.desktop.beans.MatafTaskInfo)
	 */
	public boolean validate(MatafTaskInfo taskInfo) throws Exception {
		String workstationStatus = taskInfo.getBranchStatus();
		if (!"dontCare".equalsIgnoreCase(workstationStatus)) {			
			String workstationStatusFromGlobal = (String) mainStep.getValueAt("GLSF_GLBL.GL_TAHANA_PTUCHA");
			if (("open".equalsIgnoreCase(workstationStatus)) & ("C".equalsIgnoreCase(workstationStatusFromGlobal))) {
				// the following message should be taken from a table
				closeTask("לצורך ביצוע הפעולה התחנה צריכה להיות פתוחה");
				return false;
			} else if (("close".equalsIgnoreCase(workstationStatus)) & ("O".equalsIgnoreCase(workstationStatusFromGlobal))) {
				// the following message should be taken from a table
				closeTask("לצורך ביצוע הפעולה התחנה צריכה להיות סגורה");
				return false;
			}
		}
		return true;
	}

}
