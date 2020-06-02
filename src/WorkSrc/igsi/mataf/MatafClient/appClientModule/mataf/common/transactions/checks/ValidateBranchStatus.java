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
public class ValidateBranchStatus extends TransactionValidator {

	public ValidateBranchStatus(OperationStep mainOpStep) {
		super(mainOpStep);
	}
	public boolean validate(MatafTaskInfo taskInfo) throws Exception {
		String branchStatus = taskInfo.getBranchStatus();
		if (!"dontCare".equalsIgnoreCase(branchStatus)) {
			String branchStatusFromGlobal = (String) mainStep.getValueAt("GLSG_GLBL.GL_SNIF_PATUACH");
			if (("open".equalsIgnoreCase(branchStatus)) & ("C".equalsIgnoreCase(branchStatusFromGlobal))) {
				// the following message should be taken from a table
				
				closeTask("לצורך ביצוע הפעולה הסניף צריך להיות פתוח");
				return false;
			} else if (("close".equalsIgnoreCase(branchStatus)) & ("O".equalsIgnoreCase(branchStatusFromGlobal))) {
				// the following message should be taken from a table
				closeTask("לצורך ביצוע הפעולה הסניף צריך להיות סגור");
				return false;			
			}
		}
		return true;
	}

}
