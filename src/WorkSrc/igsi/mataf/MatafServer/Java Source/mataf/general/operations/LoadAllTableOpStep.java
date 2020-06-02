package mataf.general.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.omg.CORBA.FieldNameHelper;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;

import mataf.services.reftables.RefTables;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LoadAllTableOpStep extends MatafOperationStep {

	/**
	 * Constructor for LoadAllTableOpStep.
	 */
	public LoadAllTableOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String serviceName = (String) getParams().getValueAt(SERVICE_NAME_ATT_NAME);
		RefTables refTables = (RefTables) getService(serviceName);
		
		String listName = (String) getParams().getValueAt(ICOLL_1_ATT_NAME);
		IndexedCollection accountTypesList = (IndexedCollection) getElementAt(listName);
		accountTypesList.removeAll();
		
		String tableName = (String) getParams().getValueAt(TABLE_NAME_PARAM_NAME);
		IndexedCollection accountTypesTable = refTables.getAll(tableName);
		
		String kcollName = (String) getParams().getValueAt(KCOLL_NAME_ATT_NAME);
		OperationsUtil.setTableData(accountTypesList, accountTypesTable, kcollName, getArrayOfFieldNames());
		
		return RC_OK;
	}
	
	private String[] getArrayOfFieldNames() throws DSEObjectNotFoundException {
		String fieldsNames = (String) getParams().getValueAt(FIELDS_NAMES_2_LOAD_ATT_NAME);
		String delim = (String) getParams().getValueAt(DELIM_ATT_NAME);
		
		StringTokenizer tokenizer = new StringTokenizer(fieldsNames, delim);
		int arrayLength = tokenizer.countTokens();
		String strArray[] = new String[arrayLength];
		for(int counter=0 ; tokenizer.hasMoreTokens() ; counter++) {
			strArray[counter] = tokenizer.nextToken();
		}
		return strArray;
	}
	
}
