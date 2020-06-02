package mataf.general.operations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;
import mataf.services.reftables.RefTables;
import mataf.validators.AccountnumberValidator;
import mataf.validators.MatafValidator;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationStep;
import com.ibm.dse.base.Service;
import com.ibm.dse.gui.Settings;

/**
 * @author yossid
 *
 * This operation step execute modulo check for account number.
 * It's use AccountnumberValidator to validate the account number.
 * Parameters:
 * 		fieldName2check - account number field name
 * 		accountTypeFieldName - account type field name
 * 		msgTableId - messages table id to get the error message from
 * 		errorNo - error key in the messages table if there is an error
 * Returns:
 * 		- RC_OK if this account number is valid
 * 		- RC_ERROR if this account number is invalid
 */			  
public class AccountNumberModuluCheckOpStep extends MatafOperationStep {
	
	/**
	 * Constructor for AccountNumberModuluCheckOpStep.
	 */
	public AccountNumberModuluCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_PARAM_NAME));
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		
		Map paramsMap = new Hashtable();
		paramsMap.put(AccountnumberValidator.ACCOUNT_NUMBER_PARAM_NAME, accountNumber);
		paramsMap.put(AccountnumberValidator.ACCOUNT_TYPE_PARAM_NAME, accountType);
		paramsMap.put(AccountnumberValidator.REF_TABLES_SRV_PARAM_NAME, getRefTablesService());
		MatafValidator validator = new AccountnumberValidator();
				
		if(validator.isValid(paramsMap)) {
			return RC_OK;
		} else {
			setError();
			return RC_ERROR;
		}
	}

}
