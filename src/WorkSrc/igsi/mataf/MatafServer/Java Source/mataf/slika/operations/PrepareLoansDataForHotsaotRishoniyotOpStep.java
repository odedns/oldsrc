package mataf.slika.operations;

import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrepareLoansDataForHotsaotRishoniyotOpStep extends MatafOperationStep {

	/**
	 * Constructor for PrepareLoansDataForHotsaotRishoniyotOpStep.
	 */
	public PrepareLoansDataForHotsaotRishoniyotOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		setValueAt("LoansList.0.loanNumber", getValueAt("HotsaotRishoniotLoanNumber"));
		setValueAt("LoansList.0.loanAmount", getValueAt("HotsaotRishoniotLoanAmmount"));
		return RC_OK;
	}

}
