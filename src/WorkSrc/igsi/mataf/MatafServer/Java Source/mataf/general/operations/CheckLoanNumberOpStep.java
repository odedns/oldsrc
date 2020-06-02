package mataf.general.operations;

import mataf.data.VisualDataField;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckLoanNumberOpStep extends MatafOperationStep {
		
	/**
	 * Constructor for CheckLoanNumberOpStep.
	 */
	public CheckLoanNumberOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String loanNumberFieldName = getFieldName2check();
		
		// if the index of the field didn't set then don't do the check
		if(loanNumberFieldName==null) return RC_OK;
		
		VisualDataField vField = (VisualDataField) getElementAt(loanNumberFieldName);
		String loanNumber = vField.getValue().toString();
		
		if(loanNumber.length()<4)
		{			
			return RC_ERROR;
		}
		
		if((!is3rdCharValid(loanNumber)) || (!isModuleCheckValid(loanNumber))) {
			setError(vField);
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}
	
	/**
	 * Returns:
	 * 		true if the accountNumber is 046106770124 and the third char of the loan numebr is '9'
	 * 		or if accountNumber is 046106770345 and the third char of the loanNumber is '1',
	 * 		otherwise, return false.
	 */
	private boolean is3rdCharValid(String loanNumber) throws DSEObjectNotFoundException {
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_PARAM_NAME));
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_PARAM_NAME));
		String branchNumber = (String) getValueAt((String) getParams().getValueAt(BRANCH_PARAM_NAME));
		String account = branchNumber+accountType+accountNumber;
		
		if(account.equals("046106770124")) {
			return loanNumber.charAt(2)=='9';
		} else if(account.equals("046106770345")) {
			return loanNumber.charAt(2)=='1';
		} else {
			return false;
		}		
	}
	
	private boolean isModuleCheckValid(String loanNumber) {
		
		int sum=0;
		int intAtIndex12 = Integer.parseInt(loanNumber.substring(11,13));
		int currentInt=0;
		
		for( int counter=1 ; counter<12 ; counter++ ) {
			currentInt = Character.getNumericValue(loanNumber.charAt(counter-1));
			if((counter%2)==0) {
				sum += (2*currentInt);
			} else {
				sum += currentInt;
			}
		}
		
		return ((100-sum) == intAtIndex12);
	}

}
