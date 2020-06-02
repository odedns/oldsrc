package mataf.slika.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;
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
public class CheckReaderPropertySetterOpStep extends MatafOperationStep {

	public static final String CHECK_DETAILS_INSERTED_MANUALY = "1";
	
	/**
	 * Constructor for CheckReaderPropertySetterOpStep.
	 */
	public CheckReaderPropertySetterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		IndexedCollection checksIcoll = (IndexedCollection) getElementAt("CZSS_T110_LIST");
		KeyedCollection currentKcoll = null;
		
		for(int counter=0 ; counter<checksIcoll.size() ; counter++ ) {
			currentKcoll = (KeyedCollection) checksIcoll.getElementAt(counter);
			if(isRowEmpty(currentKcoll)) { // all the rest of the rows should b empty 2
				return RC_OK;
			} else {
				currentKcoll.setValueAt("CH_READ_CHEQ", CHECK_DETAILS_INSERTED_MANUALY);
			}
		}
		 
		return RC_OK;
	}
	
	private boolean isRowEmpty(KeyedCollection kcoll) throws DSEObjectNotFoundException {
		return ((String) kcoll.getValueAt("CH_MISPAR_CHEQ")).trim().length()==0;
	}

}