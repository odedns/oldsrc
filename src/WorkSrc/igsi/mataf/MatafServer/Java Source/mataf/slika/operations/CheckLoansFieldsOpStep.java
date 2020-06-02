package mataf.slika.operations;

import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckLoansFieldsOpStep extends MatafOperationStep {

	/**
	 * Constructor for CheckLoansFieldsOpStep.
	 */
	public CheckLoansFieldsOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		VisualDataField idCardNumber = (VisualDataField) getElementAt("IdCardNumber");
		String idCardNumberValue = (String) idCardNumber.getValue();
		if((idCardNumberValue==null) || (idCardNumberValue.length()==0) || idCardNumber.isInErrorFromServer()) {
			return RC_ERROR;
		}
		
		int mahutTashlum = Integer.parseInt((String) getValueAt("MahutTashlum"));
		if(((mahutTashlum==CheckMahutTashlumOpStep.HOTSAOT_RISHONYOT) && isLoanDataForHotsaotRishoniyotValid()) ||
			((mahutTashlum==CheckMahutTashlumOpStep.TAKBULIM) && isLoanDataForTakbulimValid())) {
				return RC_OK;
		} else {
			return RC_ERROR;
		}
	}
	
	private boolean isLoanDataForHotsaotRishoniyotValid() throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		VisualDataField loanNumber = (VisualDataField) getElementAt("HotsaotRishoniotLoanNumber");
		VisualDataField loanAmmount = (VisualDataField) getElementAt("HotsaotRishoniotLoanAmmount");
		String loanNumberValue = (String) loanNumber.getValue();
		String loanAmmountValue = (String) loanAmmount.getValue();
		if((loanAmmountValue.length()>0) && (loanNumberValue.length()>0)) {
			if(loanAmmount.isInErrorFromServer() || loanNumber.isInErrorFromServer()) {
				return false;
			}
		}
		setValueAt("numberOfLoans", "1");
		return true;
	}
	
	private boolean isLoanDataForTakbulimValid() throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		IndexedCollection icoll = (IndexedCollection) getElementAt("LoansList");
		VisualDataField loanNumberField, loanAmountField;
		String loanNumberVal, loanAmountVal;
		int counter=0;
		for( ; counter<icoll.size() ; counter++ ) {
			loanNumberField = (VisualDataField) ((KeyedCollection) icoll.getElementAt(counter)).getElementAt("loanNumber");
			loanAmountField = (VisualDataField) ((KeyedCollection) icoll.getElementAt(counter)).getElementAt("loanAmount");
			loanNumberVal = (String) loanNumberField.getValue();
			loanAmountVal = (String) loanAmountField.getValue();
			if((loanNumberVal.length()>0) && (loanAmountVal.length()>0)) {
				if(loanNumberField.isInErrorFromServer() || loanAmountField.isInErrorFromServer()) {
					return false;
				}
			} else {
				break;
			}
		}
		
		setValueAt("numberOfLoans", String.valueOf(counter));
		
		if(counter==0) { // there are no loans 2 b sent 2 the host
			return false;
		} else {
			return true;
		}
	}

}
