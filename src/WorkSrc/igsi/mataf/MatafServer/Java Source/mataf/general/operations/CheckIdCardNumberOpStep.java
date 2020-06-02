package mataf.general.operations;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;
import mataf.validators.IDNumberValidator;
import mataf.validators.MatafValidator;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckIdCardNumberOpStep extends MatafOperationStep {

	/**
	 * Constructor for CheckIdCardNumberOpStep.
	 */
	public CheckIdCardNumberOpStep() {
		super();
	}
	
	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String idCardNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		
		MatafValidator validator = new IDNumberValidator();
		Map parameters = new Hashtable();
		parameters.put(IDNumberValidator.ID_CARD_NUMBER_PARAM_NAME, idCardNumber);
		if(validator.isValid(parameters)) {
			return RC_OK;
		} else {
			// At 1st, the error was part of the field.
			// Nirit Tomer said that sometimes idCardNumber that is valid seems to be invalid
			// because a check digit is missing
//			setError();
			addToErrorListFromXML();
			return RC_ERROR;
		}
	}
}
