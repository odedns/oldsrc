package mataf.general.operations;


/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SetMsgAccordingToValueOpStep extends MatafOperationStep {

	/**
	 * Constructor for SetMsgAccordingToValueOpStep.
	 */
	public SetMsgAccordingToValueOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String fieldValue2cmpr = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CMPR_PARAM_NAME));
		String value2cmpr = (String) getParams().getValueAt(VALUE_2_CMPR_PARAM_NAME);
		
		if(fieldValue2cmpr.equals(value2cmpr)) {
			setError();
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}

}
