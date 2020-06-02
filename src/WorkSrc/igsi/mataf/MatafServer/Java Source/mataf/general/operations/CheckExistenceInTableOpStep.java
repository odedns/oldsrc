package mataf.general.operations;


import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckExistenceInTableOpStep extends MatafOperationStep {

	/**
	 * Constructor for CheckExistenceInTableOpStep.
	 */
	public CheckExistenceInTableOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String value2check = (String) getValueAt(getFieldName2check());
		String tableName = (String) getParams().getValueAt(TABLE_NAME_PARAM_NAME);
		String tableKey = (String) getParams().getValueAt(TABLE_KEY_PARAM_NAME);
		
		IndexedCollection queryResultsIcoll = (IndexedCollection) getRefTablesService().getByKey(tableName, tableKey, value2check);
		if(queryResultsIcoll.size() > 0) {
			return RC_OK;
		} else {
			setError();
			return RC_ERROR;
		}
	}

}
