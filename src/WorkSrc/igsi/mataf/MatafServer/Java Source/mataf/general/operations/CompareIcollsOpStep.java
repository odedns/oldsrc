package mataf.general.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;

import com.ibm.dse.base.DSEObjectNotFoundException;
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
public class CompareIcollsOpStep extends MatafOperationStep {

	/**
	 * Constructor for CompareChecksSentWithChecksRecivedOpStep.
	 */
	public CompareIcollsOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String checksFromHostName = (String) getParams().getValueAt(ICOLL_1_PARAM_NAME);
		String checksFromClientName = (String) getParams().getValueAt(ICOLL_2_PARAM_NAME);
		
		IndexedCollection checksFromHost = (IndexedCollection) getElementAt(checksFromHostName);
		IndexedCollection checksFromClient = (IndexedCollection) getElementAt(checksFromClientName);
		List listOfFieldsNames = getListOfFieldsNames();
		VisualDataField vFieldFromHost = null, vFieldFromClient = null;
		
		for( int counter=0 ; counter<checksFromClient.size() ; counter++) {
			for( int innerCounter=0 ; innerCounter<listOfFieldsNames.size() ; innerCounter++ ) {				
				vFieldFromHost = getFieldFromIcoll(checksFromHost, counter, (String) listOfFieldsNames.get(innerCounter));
				vFieldFromClient = getFieldFromIcoll(checksFromClient, counter, (String) listOfFieldsNames.get(innerCounter));
				if(!compare(vFieldFromHost, vFieldFromClient)) {
					addToErrorListFromXML();
					return RC_ERROR;
				}
			}
		}
		
		return RC_OK;
	}
	
	private boolean compare(VisualDataField vFieldFromHost, VisualDataField vFieldFromClient) throws Exception {
		
		if(vFieldFromClient.getValue().toString().equals((String) vFieldFromHost.getValue())) {
			return true;
		} else {
			String msg = getErrorMsg();
			vFieldFromClient.setErrorFromServer(msg);
			return false;
		}
		
	}
	
	private List getListOfFieldsNames() throws DSEObjectNotFoundException {
		List listOfFieldsNames = new ArrayList();
		
		String fieldsNames2cmpr = (String) getParams().getValueAt(FIELDS_NAMES_2_CMPR_PARAM_NAME);
		String delim = (String) getParams().getValueAt(DELIM_PARAM_NAME);
		StringTokenizer tokenizer = new StringTokenizer(fieldsNames2cmpr, delim);
		
		while(tokenizer.hasMoreTokens()) {
			listOfFieldsNames.add(tokenizer.nextToken());
		}
		
		return listOfFieldsNames;
	}
	
	private VisualDataField getFieldFromIcoll(IndexedCollection icoll, int index, String fieldName) 
												throws DSEObjectNotFoundException {
		KeyedCollection kcoll = (KeyedCollection) icoll.getElementAt(index);
		return (VisualDataField) kcoll.getElementAt(fieldName);
	}

}
