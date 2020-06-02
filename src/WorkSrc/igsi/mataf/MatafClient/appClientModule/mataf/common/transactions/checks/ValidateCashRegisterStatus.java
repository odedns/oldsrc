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
public class ValidateCashRegisterStatus extends TransactionValidator {

	public ValidateCashRegisterStatus(OperationStep mainOpStep) {
		super(mainOpStep);
	}
	public boolean validate(MatafTaskInfo taskInfo) throws Exception {
		String cashRegisterStatus = taskInfo.getCashRegisterStatus();
		String cashRegisterType = taskInfo.getCashRegisterType();
		if (!"dontCare".equalsIgnoreCase(cashRegisterStatus)) {
			String cashRegisterFromGlobal = null;
			if ("teller".equalsIgnoreCase(cashRegisterType)) {
				cashRegisterFromGlobal = (String) mainStep.getValueAt("GLSF_GLBL.GL_SW_KUPA_TL");
			} else if ("foreignCurrency".equalsIgnoreCase(cashRegisterType)) {
				cashRegisterFromGlobal = (String) mainStep.getValueAt("GLSF_GLBL.GL_SW_KUPA_MZ");
			}
			if (("open".equalsIgnoreCase(cashRegisterStatus)) & ("C".equalsIgnoreCase(cashRegisterFromGlobal))) {
//				// the following message should be taken from a table
				closeTask("לצורך ביצוע הפעולה הקופה צריכה להיות פתוחה");
				return false;
			} else if (("close".equalsIgnoreCase(cashRegisterStatus)) & ("O".equalsIgnoreCase(cashRegisterFromGlobal))) {
				// the following message should be taken from a table
				closeTask("לצורך ביצוע הפעולה הקופה צריכה להיות סגורה");
				return false;			
			}
		}
		return true;
	}

}
