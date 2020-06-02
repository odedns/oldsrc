package mataf.slika.operations;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.general.operations.MatafOperationStep;
import mataf.general.operations.OperationsUtil;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckBranchIdAccording2bankOpStep extends MatafOperationStep {

	/**
	 * Constructor for CheckBranchIdAccording2bankOpStep.
	 */
	public CheckBranchIdAccording2bankOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String branchId = getFieldValue(FIELD_NAME_2_CHECK_PARAM_NAME);
		String bank = getBank();
		if(isBankValid(bank, branchId)) {
			return RC_OK;
		} else {
			setError();
			return RC_ERROR;
		}
	}
	
	private boolean isBankValid(String bank, String branchId) throws DSEObjectNotFoundException, DSEException {
		if(bank.equals(OperationsUtil.PAGI_BANK_ID) || bank.equals(OperationsUtil.BENLEUMI_BANK_ID)) {
			JDBCTable tableService = (JDBCTable) getService("snifin");
			String aSearchCondition= " GL_IFYUN_SNIF LIKE '"+bank+"%' AND GL_SNIF='"+branchId+"'";
			Vector aDataVector = tableService.retrieveRecordsMatching(aSearchCondition);
			return aDataVector.size()>0;
		} else {
			return true;
		}		
	}
	
	private String getBank() throws Exception {
		String bankId = getFieldValue(FIELD_NAME_2_CMPR_ATT_NAME);
		IndexedCollection resultsIcoll = getRefTablesService().getByKey("GLST_BANK", "GL_BANK", bankId);
		if(resultsIcoll.size()>0) {
			return (String) ((KeyedCollection) resultsIcoll.getElementAt(0)).getValueAt("GL_SUG_BANK");
		} else {
			return "";
		}
	}
	
	private String getFieldValue(String opParamName) throws DSEObjectNotFoundException {
		String fieldName = (String) getParams().getValueAt(opParamName);
		String indexedCollectionName = (String) getParams().tryGetValueAt(ICOLL_2_CHECK_ATT_NAME);
		if(indexedCollectionName!=null) {
			String indexInIcoll = (String) getValueAt((String) getParams().getValueAt(INDEX_FIELD_ATT_NAME));
			fieldName = indexedCollectionName+"."+indexInIcoll+"."+fieldName;
		}
		return (String) getValueAt(fieldName);
	}

}
