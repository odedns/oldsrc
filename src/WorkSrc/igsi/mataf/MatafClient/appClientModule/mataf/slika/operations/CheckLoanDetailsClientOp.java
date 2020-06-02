package mataf.slika.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafClientOp;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckLoanDetailsClientOp extends MatafClientOp {
	
	public static final int ROW_EMPTY = 1;
	public static final int REQUIRED_FIELDS_EMPTY = 2;
	public static final int ERRORS_IN_FIELDS = 3;
	public static final int ROW_IS_VALID = 4;

	private boolean isMoreThenOneRow = false;
	
	/**
	 * @see mataf.general.operations.MatafClientOp#preSend2hostValidation()
	 */
	public boolean preSend2hostValidation() throws Exception {
		if(isLoansRowsValid() && isMoreThenOneRow) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isLoansRowsValid() throws DSEObjectNotFoundException {
		VisualDataField loanNumber, loanAmount;
		IndexedCollection icoll = (IndexedCollection) getElementAt("LoansList");
		for( int counter=0 ; counter<icoll.size() ; counter++ ) {
			loanNumber = (VisualDataField) getElementAt("LoansList."+counter+".loanNumber");
			loanAmount = (VisualDataField) getElementAt("LoansList."+counter+".loanAmount");
			
			int rowInd = checkRow(loanNumber, loanAmount);
			if(rowInd==ROW_EMPTY) {
				break;
			} else if(rowInd==REQUIRED_FIELDS_EMPTY) {
				return false;
			} else if(rowInd==ERRORS_IN_FIELDS) {
				return false;
			}
			isMoreThenOneRow = true;			
		}
		return true;		
	}
	
	/** In future this method will b removed because only rows that shown in screen
	 *  will b in the IndexedCollection. 
	 *  Today, there is final size of 50 checks in the IndexedCollection.
	 */
	private int checkRow(VisualDataField loanNumberField, VisualDataField loanAmountField) throws DSEObjectNotFoundException {
				
		boolean isLoanNumberEmpty = ((String) loanNumberField.getValue()).trim().length()==0;
		String ammountStr = ((String) loanAmountField.getValue()).trim();
		double ammount = 0;
		boolean isLoanAmountEmpty = true;
		if(ammountStr.length()>0) {
			ammount = Double.parseDouble(ammountStr);
			if(ammount-0>0.01) {
				isLoanAmountEmpty = false;
			}			
		}
		
		if(isLoanNumberEmpty && isLoanAmountEmpty) {
			return ROW_EMPTY;
		}
		if((isLoanNumberEmpty && loanNumberField.isRequired()) || (isLoanAmountEmpty && loanAmountField.isRequired())) {
			return REQUIRED_FIELDS_EMPTY;
		}
		if(loanNumberField.isInErrorFromServer() || loanAmountField.isInErrorFromServer()) {
			return ERRORS_IN_FIELDS;
		}		
		
		return ROW_IS_VALID;
	}
}
