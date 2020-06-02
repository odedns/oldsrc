package mataf.slika.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (15/10/2003 12:48:38).  
 */
public class LoansFieldsSetterServerOpStep extends MatafOperationStep {

	/**
	 * Constructor for LansFieldsSetterServerOpStep.
	 */
	public LoansFieldsSetterServerOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		// setting the view
		((VisualDataField) getElementAt("HazramatHamchaotButton")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt("VadeHalvaotButton")).setIsEnabled(Boolean.FALSE);
		
		// setting the loan owner name
		String msg = getErrorMsg();
		String msgFromHost = (String) getValueAt("AccountBalanceHostReplyData.GL_SHEM");
		addErrorToErrorList(msg.concat(msgFromHost));
		
		return RC_OK;
	}

}
