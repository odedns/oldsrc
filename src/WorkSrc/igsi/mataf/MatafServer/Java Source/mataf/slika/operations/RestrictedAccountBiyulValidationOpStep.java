package mataf.slika.operations;

import java.awt.Color;
import java.util.Arrays;

import mataf.data.VisualDataField;
import mataf.general.operations.*;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.IndexedCollection;
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
public class RestrictedAccountBiyulValidationOpStep extends MatafOperationStep {

	public static final String BIYUL_ACCOUNT_DELIM = "-";
	/**
	 * Constructor for RestrictedAccountBiyulValidationOpStep.
	 */
	public RestrictedAccountBiyulValidationOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		
		StringBuffer account2cmpr = new StringBuffer();
		account2cmpr.append((String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF"));
		account2cmpr.append(BIYUL_ACCOUNT_DELIM);
		account2cmpr.append(accountType);
		account2cmpr.append(BIYUL_ACCOUNT_DELIM);
		account2cmpr.append(accountNumber);
		
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("TLST_CH_BIYUL", "TL_CH", account2cmpr.toString());
		
		if(resultsInIcoll.size()>0) {
			String params[] = {accountType.concat(accountNumber)};
			setError(Arrays.asList(params));
			return RC_ERROR;
		} else {
			return RC_OK;
		}
	}
}