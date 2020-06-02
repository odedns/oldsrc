package mataf.slika.operations;

import java.awt.Color;
import java.util.Arrays;

import mataf.data.VisualDataField;
import mataf.general.operations.*;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Operation;
import com.ibm.dse.base.OperationStep;
import com.ibm.dse.base.Service;
import com.ibm.dse.gui.Settings;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RestrictedAccountKupotGemelValidationOpStep extends MatafOperationStep {

	/**
	 * Constructor for RestrictedAccountKupotGemelValidationOpStep.
	 */
	public RestrictedAccountKupotGemelValidationOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String branchId = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		String account2cmpr = accountType.concat(accountNumber);
		
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("TLST_SG_KG_MURCHAV", "GL_SSCH", account2cmpr);
		
		if(resultsInIcoll.size()>0) {
			String params[] = {branchId.concat(accountType.concat(accountNumber))};
			setError(Arrays.asList(params));
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}
}