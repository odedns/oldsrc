package mataf.general.operations;

import java.util.StringTokenizer;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.data.VisualDataField;
import mataf.general.operations.*;
import mataf.services.MessagesHandlerService;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CompareTotalAmountsOpStep extends MatafOperationStep {
	
	/**
	 * Constructor for CompareTotalAmountsOpStep.
	 */
	public CompareTotalAmountsOpStep() {
		super();
	}
	
	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String icollName = (String) getParams().getValueAt(ICOLL_2_CHECK_PARAM_NAME);
		String amountInIcollName = (String) getParams().getValueAt(AMOUNT_FIELD_NAME_PARAM_NAME);
		
		double totalAmount = getTotalAmount();
		double sumOfAmounts = getSumOfAmounts(amountInIcollName, icollName);
		
		if(totalAmount != sumOfAmounts) {
			addToErrorListFromXML();
			return RC_ERROR;
		}		
		
		return RC_OK;
	}
	
	private double getTotalAmount() throws DSEObjectNotFoundException {
		String totalAmountStr = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		if(totalAmountStr.length() == 0) {
			return 0;
		} else {
			return Double.parseDouble(totalAmountStr);
		}
	}
	
	private double getSumOfAmounts(String amountInIcollName, String icollName) throws DSEObjectNotFoundException {
		
		double sumOfAmounts = 0;
		
		IndexedCollection amountsIcoll = (IndexedCollection) getElementAt(icollName);
		KeyedCollection currentKcoll = null;
		String currentAmountStr = null;
		double currentAmount = 0;
		for( int counter=0 ; counter<amountsIcoll.size() ; counter++ ) {
			currentKcoll = (KeyedCollection) amountsIcoll.getElementAt(counter);
			currentAmountStr = (String) currentKcoll.getValueAt(amountInIcollName);
			if(currentAmountStr.length()>0) {
				currentAmount = Double.parseDouble(currentAmountStr);
				sumOfAmounts += currentAmount;
			} else {
				break;
			}
		}
		
		return sumOfAmounts;
	}
	
}
