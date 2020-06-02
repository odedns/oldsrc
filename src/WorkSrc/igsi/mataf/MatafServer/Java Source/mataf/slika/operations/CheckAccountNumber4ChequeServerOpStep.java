package mataf.slika.operations;

import java.util.Hashtable;
import java.util.Map;

import mataf.general.operations.MatafOperationStep;
import mataf.validators.AccountnumberValidator;
import mataf.validators.MatafValidator;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckAccountNumber4ChequeServerOpStep extends MatafOperationStep {

	private static final String ACCOUNT_NUMBER_PREFIX = "0";
	
	private static final int ACCOUNT_NUMBER_LENGTH = 10;
	
	/**
	 * Constructor for CheckAccountNumber4ChequeServerOpStep.
	 */
	public CheckAccountNumber4ChequeServerOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String fieldName2check = (String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME);
		String indexedCollectionName = (String) getParams().tryGetValueAt(ICOLL_2_CHECK_ATT_NAME);
		if(indexedCollectionName!=null) {
			String indexInIcoll = (String) getValueAt((String) getParams().getValueAt(INDEX_FIELD_ATT_NAME));
			fieldName2check = indexedCollectionName+"."+indexInIcoll+"."+fieldName2check;
		}
		String accountNumberAndType = (String) getValueAt(fieldName2check);
		
		if(accountNumberAndType.length()==(ACCOUNT_NUMBER_LENGTH-1)) {
			accountNumberAndType = ACCOUNT_NUMBER_PREFIX.concat(accountNumberAndType);
			setValueAt(fieldName2check, accountNumberAndType);
		}
		
		if(accountNumberAndType.length()!=ACCOUNT_NUMBER_LENGTH) {
			setError();
			return RC_ERROR;
		}
		
		String prefix = accountNumberAndType.substring(0,1);
		String accountType = accountNumberAndType.substring(1,4);
		String accountNumber = accountNumberAndType.substring(4);
		
		Map paramsMap = new Hashtable();
		paramsMap.put(AccountnumberValidator.ACCOUNT_NUMBER_PARAM_NAME, accountNumber);
		paramsMap.put(AccountnumberValidator.ACCOUNT_TYPE_PARAM_NAME, accountType);
		paramsMap.put(AccountnumberValidator.REF_TABLES_SRV_PARAM_NAME, getRefTablesService());
		
		MatafValidator validator = new AccountnumberValidator();
		
		if(prefix.equals(ACCOUNT_NUMBER_PREFIX) && validator.isValid(paramsMap)) {
			return RC_OK;
		} else {
			setError();
			return RC_ERROR;
		}
	}

}
