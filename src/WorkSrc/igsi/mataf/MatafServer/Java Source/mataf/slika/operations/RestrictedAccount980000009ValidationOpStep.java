package mataf.slika.operations;

import java.awt.Color;
import java.util.Arrays;

import mataf.data.VisualDataField;
import mataf.general.operations.*;

import com.ibm.dse.base.OperationStep;
import com.ibm.dse.gui.Settings;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RestrictedAccount980000009ValidationOpStep extends MatafOperationStep {

	/**
	 * Constructor for RestrictedAccount980000009ValidationOpStep.
	 */
	public RestrictedAccount980000009ValidationOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		String account2cmpr = accountType.concat(accountNumber);
				
		if(account2cmpr.equals("980000009")) {
			String params[] = {"980000009"};
			setError(Arrays.asList(params));
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}

}
