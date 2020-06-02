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
public class SendCheckDetailsSetterOpStep extends MatafOperationStep {

	/**
	 * Constructor for SendCheckDetailsSetterOpStep.
	 */
	public SendCheckDetailsSetterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		((VisualDataField) getElementAt("VadeNetunimButton")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("CheckReaderButton")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("ChecksTable")).setIsEnabled(Boolean.FALSE);
		return RC_OK;
	}

}
