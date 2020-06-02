package mataf.slika.operations;

import java.awt.Color;
import java.util.Arrays;

import mataf.data.VisualDataField;
import mataf.general.operations.*;

import com.ibm.dse.base.OperationStep;
import com.ibm.dse.gui.Settings;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RestrictedAccount960990305ValidationOpStep extends MatafOperationStep {

	/**
	 * Constructor for RestrictedAccount960990305ValidationOpStep.
	 */
	public RestrictedAccount960990305ValidationOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String bankId = (String) getValueAt("GLSG_GLBL.GL_BANK");
		String branchId = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		String accountNumber = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME));
		String account2cmpr = accountType.concat(accountNumber);
		
		if(account2cmpr.equals("960990305")) {
			// check if bank is Benleumi
			if(OperationsUtil.compareWithTable(getRefTablesService(), "GLST_BANK","GL_BANK", bankId, "GL_SUG_BANK", OperationsUtil.BENLEUMI_BANK_ID)) {
				if(branchId.equals("060") || branchId.equals("297") || branchId.equals("298")) {
					return RC_OK;
				}
			}
			String params[] = {"960990305"};
			setError(Arrays.asList(params));
			return RC_ERROR;			
		} else {
			return RC_OK;
		}
	}

}
