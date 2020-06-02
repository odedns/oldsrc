package mataf.general.operations;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FieldValueSetterOpStep extends MatafOperationStep {

	/**
	 * Constructor for FieldvalueSetterOpStep.
	 */
	public FieldValueSetterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String fieldName2set = (String) getParams().getValueAt(FIELD_2_SET_PARAM_NAME);
		String value2set = (String) getParams().getValueAt(VALUE_2_SET_PARAM_NAME);
		setValueAt(fieldName2set, value2set);
		return RC_OK;
	}

}
