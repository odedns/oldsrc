package mataf.slika.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class NumberOfChecksSetterOpStep extends MatafOperationStep {

	/**
	 * Constructor for NumberOfChecksSetterOpStep.
	 */
	public NumberOfChecksSetterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		int numberOfChecks = getNumberOfChecks();
		setValueAt("NumberOfCheques", String.valueOf(numberOfChecks));
		return RC_OK;
	}
	
	private int getNumberOfChecks() throws DSEObjectNotFoundException {
		IndexedCollection icoll = (IndexedCollection) getElementAt("CZSS_T110_LIST");
		KeyedCollection kcoll = null;
		String element2checkIfEmpty = null;
		int counter=0;
		for( ; counter<icoll.size() ; counter++ ) {
			kcoll = (KeyedCollection) icoll.getElementAt(counter);
			element2checkIfEmpty = (String) kcoll.getValueAt("CH_BANK_CHOTEM");
			if(element2checkIfEmpty.length()==0)
				return counter;
		}
		return counter;
	}

}
