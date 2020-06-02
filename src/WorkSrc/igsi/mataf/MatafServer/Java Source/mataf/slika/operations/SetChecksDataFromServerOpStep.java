package mataf.slika.operations;



import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.FormatElement;
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
public class SetChecksDataFromServerOpStep extends MatafOperationStep {

	/**
	 * Constructor for SetChecksDataFromServerOpStep.
	 */
	public SetChecksDataFromServerOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String[] fields2map = {"CH_KOD_MSG1", "CH_KOD_MSG2", "CH_MSG1", "CH_MSG2", "CH_KOD_TKINUT"};
		
		IndexedCollection checksFromUser = (IndexedCollection) getElementAt("CZSS_T110_LIST");
		IndexedCollection checksFromHost = (IndexedCollection) getElementAt("sendCheckDetailsTrxReplyData.CZSS_T110_LIST");
		KeyedCollection currentRecordFromUser = null;
		KeyedCollection currentRecordFromHost = null;
		
		for(int counter=0 ; counter<checksFromUser.size() ; counter++) {
			currentRecordFromUser = (KeyedCollection) checksFromUser.getElementAt(counter);
			currentRecordFromHost = (KeyedCollection) checksFromHost.getElementAt(counter);
			if(!isRecordEmpty(currentRecordFromUser)) {
				mapContents(currentRecordFromHost, currentRecordFromUser, fields2map);
			} else {
				break;
			}
		}
		
		return RC_OK;
	}
	
	private void mapContents(KeyedCollection source, KeyedCollection target, String[] fields2map) 
								throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		String currentSourceValue;
		for(int counter=0; counter<fields2map.length ; counter++ ) {
			currentSourceValue = (String) source.getValueAt(fields2map[counter]);
			if((currentSourceValue == null) || (currentSourceValue.length()==0))
				currentSourceValue = " ";
				
			target.setValueAt(fields2map[counter], currentSourceValue);
		}
	}
	
	private boolean isRecordEmpty(KeyedCollection kcoll2check) throws DSEObjectNotFoundException {
		String field2check = (String) kcoll2check.getValueAt("CH_MISPAR_CHEQ");
		if(field2check.trim().length()==0)
			return true;
		else
			return false;
	}
	
	private void setFields2map() {
	}

}
