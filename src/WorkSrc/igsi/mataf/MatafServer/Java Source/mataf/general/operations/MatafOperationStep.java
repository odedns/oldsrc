package mataf.general.operations;

import java.util.ArrayList;
import java.util.List;

import mataf.data.VisualDataField;
import mataf.logger.GLogger;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;
import mataf.utils.MatafUtilities;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.OperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
abstract public class MatafOperationStep extends OperationStep {
	
	public static final int ON_OTHER_DO_VALUE = 2;
	
	// params in the operation definition
	public static final String FIELD_NAME_2_CHECK_PARAM_NAME = "fieldName2check";
	public static final String MSG_TABLE_ID_PARAM_NAME = "msgTableId";
	public static final String ERR_NO_PARAM_NAME = "errorNo";
	public static final String TABLE_NAME_PARAM_NAME = "tableName";
	public static final String TABLE_KEY_PARAM_NAME = "tableKey";
	public static final String ACCOUNT_PARAM_NAME = "accountFieldName";
	public static final String ACCOUNT_TYPE_PARAM_NAME = "accountTypeFieldName";
	public static final String BRANCH_PARAM_NAME = "branchFieldName";
	public static final String BANK_PARAM_NAME = "bankFieldName";
	public static final String FIELD_NAME_2_CMPR_PARAM_NAME = "fieldNameToCmpr";
	public static final String VALUE_2_CMPR_PARAM_NAME = "value2cmpr";
	public static final String FIELD_2_SET_PARAM_NAME = "fieldToSet";
	public static final String VALUE_2_SET_PARAM_NAME = "valueToSet";
	public static final String COMPARISON_TYPE_PARAM_NAME = "comparisonType";
	public static final String ICOLL_2_CHECK_PARAM_NAME = "icoll2check";
	public static final String AMOUNT_FIELD_NAME_PARAM_NAME = "fieldNameInIcoll";
	public static final String SET_ERROR_IN_FIELD_PARAM_NAME = "setErrorInField";
	public static final String INDEX_FIELD_PARAM_NAME = "indexFieldName";
	
	public static final String ICOLL_1_PARAM_NAME = "icoll1name";
	public static final String ICOLL_2_PARAM_NAME = "icoll2name";
	public static final String FIELDS_NAMES_2_CMPR_PARAM_NAME = "fieldsNames2cmpr";
	public static final String DELIM_PARAM_NAME = "delim";
	
	public static final String FIELDS_NAMES_2_LOAD_PARAM_NAME = "fieldsNames2load";
	public static final String SERVICE_NAME_PARAM_NAME = "serviceName";
	public static final String KCOLL_NAME_PARAM_NAME = "kcollName";
	
	public static final String DATA_MAPPER_PARAM_NAME = "dataMapperName";
	
	public static final String JOURNAL_ENTITY_NAME_PARAM_NAME = "entityName";
	public static final String FORMAT_NAME_PARAM_NAME = "formatName";
	
	public static final String TEXT_FORMAT_PARAM_NAME = "textFormat";
	public static final String ACTION_PARAM_NAME = "action";
		
	public static final int GREATER_COMPARISON_TYPE = 1;
	public static final int LESS_COMPARISON_TYPE = 2;
	public static final int EQUALS_COMPARISON_TYPE = 3;
	
	private RefTables refTables;
	private MessagesHandlerService msgHandler;
	
	/**
	 * Constructor for MatafOperationStep.
	 */
	public MatafOperationStep() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.OperationStepInterface#execute()
	 */
	public int execute() throws Exception {
		String className = getClass().getName();
		try {			
			GLogger.debug("\nIn class: "+className);
			removeError();
			long timeBefore = System.currentTimeMillis();
			int returnedValue = executeOp();
			long timeAfter = System.currentTimeMillis();
			GLogger.debug("\nExecution time of "+className+": "+(timeAfter-timeBefore)+" msec");
			return returnedValue;
		} catch(Exception e) {
			String errMsg = "Exception has been thrown in server operation step '"+this.getName()+"'";
			MatafUtilities.setErrorMessageInGLogger(this.getClass(), getContext(), e, errMsg);
			
			// TODO - set the next two rows as a remark (for debug only)
			MatafUtilities.addBusinessMessage(getContext(), "Error in opStep '"+className+"' : ");
			MatafUtilities.addBusinessMessage(getContext(), e.getMessage());
			
			return RC_ERROR;
		}
	}
	
	abstract public int executeOp() throws Exception;

	protected RefTables getRefTablesService() throws DSEObjectNotFoundException {
		if(refTables == null) {
			refTables = (RefTables) getService("refTablesService");
		}
		return refTables;
	}
	
	protected MessagesHandlerService getMessagesHandlerService() throws DSEObjectNotFoundException {
		if(msgHandler == null) {
			msgHandler = (MessagesHandlerService) getService("msgsHandlerService");
		}
		return msgHandler;
	}
	
	protected void setError(VisualDataField vField, List msgParams) throws Exception {
		String msg = getErrorMsg(msgParams);
		if(vField==null) {
			MatafUtilities.addBusinessMessage(getContext(), msg);
		} else {
			vField.setErrorFromServer(msg);
		}
	}
	
	protected void setError(VisualDataField vField) throws Exception {
		setError(vField, new ArrayList());
	}
	
	protected void setError(List msgParams) throws Exception {
		setError(getField2setMsg(), msgParams);
	}
		
	protected void setError() throws Exception {
		setError(getField2setMsg(), new ArrayList());
	}
	
	/**
	 * Returns - the field to set in error message only if the parameter FIELD_NAME_2_CHECK_PARAM_NAME 
	 * 			 has been defined in the operation definition in the dseoper.xml, otherwise, return null.
	 */
	protected VisualDataField getField2setMsg() throws DSEObjectNotFoundException {
		// check if FIELD_NAME_2_CHECK_PARAM_NAME has been defined,
		DataElement param = getParams().tryGetElementAt(FIELD_NAME_2_CHECK_PARAM_NAME);
		if(param!=null) {
			// by default - should set the error message in the Field2check
			// but this property can be set to 'false' in SET_ERROR_IN_FIELD_ATT_NAME			
			DataElement param2 = getParams().tryGetElementAt(SET_ERROR_IN_FIELD_PARAM_NAME);			
			boolean isSetErrorInField2check = true;
			if(param2!=null) {
				isSetErrorInField2check = Boolean.valueOf((String) param2.getValue()).booleanValue();
			}
			if(isSetErrorInField2check) {
				String fieldName2setInError = getFieldName2check();
				
				// finally, seting the message in the field
				return (VisualDataField) getElementAt(fieldName2setInError);
			}			
		}
		return null;
	}
	
	// there are cases where the field2setError is in an IndexedCollection,
	// thats why we do the next check
	protected String getFieldName2check() throws DSEObjectNotFoundException {
		String fieldName2check = (String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME);
		String indexedCollectionName = (String) getParams().tryGetValueAt(ICOLL_2_CHECK_PARAM_NAME);
		String indexInIcollParam = (String) getParams().tryGetValueAt(INDEX_FIELD_PARAM_NAME);
		if((indexedCollectionName!=null) && (indexInIcollParam!=null)) {
			String indexInIcoll = (String) getValueAt(indexInIcollParam);
			if(indexInIcoll==null)return null;
			fieldName2check = indexedCollectionName+"."+indexInIcoll+"."+fieldName2check;
		}
		return fieldName2check;
	}
	
	protected String getErrorMsg() throws Exception {
		return getErrorMsg(new ArrayList());
	}
	
	protected String getErrorMsg(List msgParams) throws Exception {
		String tableId = (String) getParams().tryGetValueAt(MSG_TABLE_ID_PARAM_NAME);
		if(tableId==null)
			tableId = MessagesHandlerService.DEFUALT_MSG_SERVICE_NAME;
			
		String msgNumber = (String) getParams().getValueAt(ERR_NO_PARAM_NAME);
		return getMessagesHandlerService().getMsgFromTable(tableId, msgNumber, getRefTablesService(), msgParams);
	}
	
	protected void removeError() throws DSEObjectNotFoundException {
		String fieldName2setInError = (String) getParams().tryGetValueAt(FIELD_NAME_2_CHECK_PARAM_NAME);
		if(fieldName2setInError!=null) {
			String indexedCollectionName = (String) getParams().tryGetValueAt(ICOLL_2_CHECK_PARAM_NAME);
			String indexInIcollParam = (String) getParams().tryGetValueAt(INDEX_FIELD_PARAM_NAME);
			if((indexedCollectionName!=null) && (indexInIcollParam!=null)) {
				String indexInIcoll = (String) getValueAt(indexInIcollParam);
				if(indexInIcoll==null)return;
				fieldName2setInError = indexedCollectionName+"."+indexInIcoll+"."+fieldName2setInError;
			}
			((VisualDataField) getElementAt(fieldName2setInError)).removeError();
		}
	}
	
	protected void removeError(VisualDataField vField) {
		vField.removeError();
	}
	
	public void addToErrorListFromXML() throws Exception {
		MatafUtilities.addBusinessMessage(getContext(), getErrorMsg());
	}

	protected void setMessage(String fieldName2setMsg, String tableId, String msgNumber) throws Exception {
		setMessage(fieldName2setMsg, tableId, msgNumber, new ArrayList());
	}
	
	protected void setMessage(String fieldName2setMsg, String tableId, String msgNumber, List msgParams) throws Exception {
		String msg2set = getMessagesHandlerService().getMsgFromTable(tableId, msgNumber, getRefTablesService(), msgParams);
		setValueAt(fieldName2setMsg, msg2set);
	}
	
	/**
	 * Convenient method to get a String from the context.
	 */
	public String getStringAt(String key) throws DSEObjectNotFoundException
	{
		return (String) getValueAt(key);
	}
}
