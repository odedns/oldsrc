package mataf.general.operations;

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
public class SetFieldValueFromTableOpStep extends MatafOperationStep {

	/**
	 * Constructor for SetFieldValueFromTableOpStep.
	 */
	public SetFieldValueFromTableOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		// get operation parameters
		String fieldNameToSetValue = (String) getParams().getValueAt(FIELD_2_SET_PARAM_NAME);
		String tableName = (String) getParams().getValueAt(TABLE_NAME_PARAM_NAME);
		String columnName2getValue = (String) getParams().getValueAt(TABLE_KEY_PARAM_NAME);
		
		// get the value from table (1 row table)
		IndexedCollection resultsIcoll = (IndexedCollection) getRefTablesService().getAll(tableName);
		String maxCheckAmmountStr = ((KeyedCollection) resultsIcoll.getElementAt(0)).getValueAt(columnName2getValue).toString();
		
		// set the value in context
		setValueAt(fieldNameToSetValue, maxCheckAmmountStr);
		
		return RC_OK;
	}

}
