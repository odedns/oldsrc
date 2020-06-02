package mataf.validators;

import java.util.Map;

import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.services.reftables.RefTables;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AccountnumberValidator implements MatafValidator {
	
	public static final int DIVIDER = 11;
	
	public static final int MODULE_6_TYPE = 6;
	public static final int MODULE_9_TYPE = 9;
	
	public static final int MODULE_6_INDICATOR = 1;
	
	public static final String ACCOUNT_TYPE_PARAM_NAME = "accountType";
	public static final String ACCOUNT_NUMBER_PARAM_NAME = "accountNumber";
	public static final String REF_TABLES_SRV_PARAM_NAME = "refTables";
	
	/**
	 * Constructor for AccountnumberValidator.
	 */
	public AccountnumberValidator() {
		super();
	}

	/**
	 * @see mataf.validators.MatafValidator#isValid(Map)
	 */
	public boolean isValid(Map parameters) throws ValidationException {
		try {
			String accountType = (String) parameters.get(ACCOUNT_TYPE_PARAM_NAME);
			String accountNumber = (String) parameters.get(ACCOUNT_NUMBER_PARAM_NAME);
			RefTables refTablesSrv = (RefTables) parameters.get(REF_TABLES_SRV_PARAM_NAME);
			
			int moduluType2commit = getModuluType2commit(refTablesSrv, accountType);
			
			if(isModulusCheckValid(moduluType2commit, accountType, accountNumber)) {
				return true;
			} else {
				return false;
			}
		}catch(Exception ex) {
			throw new ValidationException(ex.getMessage());
		}
	}
	
	/**
	 * Return MODULE_6_TYPE if table GLST_SCH indicates that this is the type of module
	 * that should be done, otherwise return MODULE_6_TYPE.
	 */

	private int getModuluType2commit(RefTables refTblSrv, String accountType) throws Exception 
	{
		IndexedCollection resultsInIcoll = refTblSrv.getByKey("GLST_SCH", "GL_SCH",accountType);
		KeyedCollection kcoll = (KeyedCollection) resultsInIcoll.getElementAt(0);
		int modulIndicator = Integer.parseInt((String)kcoll.getValueAt("GL_SW_SB_CH"));
	
		if(modulIndicator==MODULE_6_INDICATOR) {
			return MODULE_6_TYPE;
		} else {
			return MODULE_9_TYPE;
		}
	}

	/**
	 * Return the module check according the typeOfModulu passed as argument.
	 */
	private boolean isModulusCheckValid(int typeOfModulu, String accountType, String accountNumber) {
		
		
		String typeConcatNumber = accountNumber;
		if(typeOfModulu == MODULE_9_TYPE) {
			typeConcatNumber = accountType.concat(typeConcatNumber);
		}
		
		int currentNumber;
		int sum = 0;
		
		for(int counter=1 ; counter<=typeConcatNumber.length() ; counter++ ) {
			currentNumber = Integer.parseInt(String.valueOf(typeConcatNumber.charAt(counter-1)));
			sum += currentNumber*(typeConcatNumber.length()-counter+1);
		}
				
		int remainder = sum % DIVIDER;
		
		if(remainder == 0) { // check is OK
			return true;
		} else { // check is NOT OK
			return false;
		}
	}
	
}
