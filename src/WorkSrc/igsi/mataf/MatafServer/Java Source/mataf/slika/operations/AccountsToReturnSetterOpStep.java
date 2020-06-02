package mataf.slika.operations;

import java.util.Arrays;

import mataf.data.VisualDataField;
import mataf.general.operations.*;

import com.ibm.dse.base.FormatElement;
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
public class AccountsToReturnSetterOpStep extends MatafOperationStep {

	/**
	 * Constructor for AccountsToReturnSetterOpStep.
	 */
	public AccountsToReturnSetterOpStep() {
		super();
	}
	
	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String accountTypeAndNumber = ((String)getValueAt("AccountType")).concat((String)getValueAt("AccountNumber"));
		String trxId = (String)getValueAt("TrxId");
		String bankId = (String)getValueAt("GLSG_GLBL.GL_BANK");
		String sugBank = OperationsUtil.getSugBank(getRefTablesService(), bankId);
		
		String accounts2returnKey = trxId.concat(sugBank).concat(accountTypeAndNumber);
		
		setAccountsByKey(accounts2returnKey);
		
		return RC_OK;
	}
	
	private void setAccountsByKey(String accounts2returnKey) throws Exception {
		
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("CZST_CH_AHZAROT", "CZ_CH", accounts2returnKey);
		
		if(resultsInIcoll.size() > 0) {
			IndexedCollection accountsToReturnList = (IndexedCollection) getElementAt("AccountsToReturnList");
			accountsToReturnList.removeAll();
			String[] arrayOfFieldsNames = {"CH_CH_RTRN","CH_TEUR_CH_RTRN"};
			OperationsUtil.setTableData(accountsToReturnList, resultsInIcoll, "AccountsToReturnRecord", arrayOfFieldsNames);
			((VisualDataField) getElementAt("AccountsToReturn")).setIsEnabled(Boolean.valueOf("true"));
		}
	}
	
}
