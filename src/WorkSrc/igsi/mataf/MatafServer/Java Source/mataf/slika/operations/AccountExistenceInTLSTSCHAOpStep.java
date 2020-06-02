package mataf.slika.operations;

import mataf.general.operations.*;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.OperationStep;
import com.ibm.dse.base.Service;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AccountExistenceInTLSTSCHAOpStep extends MatafOperationStep {

	/**
	 * Constructor for AccountExistenceInTLSTCHAOpStep.
	 */
	public AccountExistenceInTLSTSCHAOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		String account2cmpr = accountType.concat(accountNumber);
		
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("TLST_ASM_MOD11", "TL_SCH_CH", account2cmpr);
		
		if(resultsInIcoll.size()>0) {
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}

}
