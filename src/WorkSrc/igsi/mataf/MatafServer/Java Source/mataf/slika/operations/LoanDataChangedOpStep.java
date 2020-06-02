package mataf.slika.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LoanDataChangedOpStep extends MatafOperationStep {

	/**
	 * Constructor for LoanDataChangedOpStep.
	 */
	public LoanDataChangedOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		// setting the view
		((VisualDataField) getElementAt("HazramatHamchaotButton")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("VadeHalvaotButton")).setIsEnabled(Boolean.TRUE);
		return RC_OK;
	}

}
