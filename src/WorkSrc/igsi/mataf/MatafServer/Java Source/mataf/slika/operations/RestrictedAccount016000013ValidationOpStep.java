package mataf.slika.operations;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mataf.data.VisualDataField;
import mataf.format.VisualFieldFormat;
import mataf.general.operations.*;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationStep;
import com.ibm.dse.base.Service;
import com.ibm.dse.gui.Settings;

/**
 * @author yossi dahan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RestrictedAccount016000013ValidationOpStep extends MatafOperationStep {

	public static final int MANAGEMENT_BRANCH_ID = 1;
	/**
	 * Constructor for RestrictedAccount016000013ValidationOpStep.
	 */
	public RestrictedAccount016000013ValidationOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		// only 4 test
//		fillInTestParamsInAccountBalance()
		////////////////
		
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		String account2cmpr = accountType.concat(accountNumber);
		String systemBranch = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		
		if(account2cmpr.equals("016000013") && (!isManagementBranch(systemBranch))) {
			String params[] = {"016000013"};
			setError(Arrays.asList(params));
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}
	
	private boolean isManagementBranch(String systemBranch) throws Exception {
		
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("GLST_SNIF", "GL_SNIF", systemBranch);
		String ifyunSnifStr = (String) ((KeyedCollection) resultsInIcoll.getElementAt(0)).getValueAt("GL_IFYUN_SNIF");
		String managementBranchIfyun = ifyunSnifStr.substring(1,2);
		int ifyunSnifInt = Integer.parseInt(managementBranchIfyun);
		
		return (ifyunSnifInt==MANAGEMENT_BRANCH_ID);
	}
	
//	private void fillInTestParamsInAccountBalance() throws DSEObjectNotFoundException, IOException {
//		IndexedCollection icoll2set = (IndexedCollection) getElementAt("AccountBalanceData.AccountBalanceFeedbackList");
//		int sizeOfIcoll = icoll2set.size();
//		icoll2set.removeAll();
//		for( int counter=0 ; counter<sizeOfIcoll ; counter++ ) {
//			KeyedCollection kcoll2add = (KeyedCollection) DataElement.readObject("AccountBalanceRecord");
//			icoll2set.addElement(kcoll2add);
//		}
//	}

	
}
