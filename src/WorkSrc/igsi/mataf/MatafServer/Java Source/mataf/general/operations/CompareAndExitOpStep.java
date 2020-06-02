package mataf.general.operations;

import mataf.data.VisualDataField;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CompareAndExitOpStep extends MatafOperationStep {

	/**
	 * Constructor for CompareAndExitOpStep.
	 */
	public CompareAndExitOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String fieldValue2cmpr = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CMPR_PARAM_NAME));
		String value2cmpr = (String) getParams().getValueAt(VALUE_2_CMPR_PARAM_NAME);
		VisualDataField vField = (VisualDataField) getElementAt((String) getParams().getValueAt(FIELD_2_SET_PARAM_NAME));
		
		if(fieldValue2cmpr.equals(value2cmpr)) {
			vField.setIsEnabled(new Boolean(false));
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}

}
