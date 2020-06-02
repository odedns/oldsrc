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
public class RestrictedAccount970ValidationOpStep extends MatafOperationStep {

	/**
	 * Constructor for RestrictedAccount970ValidationOpStep.
	 */
	public RestrictedAccount970ValidationOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String bankId = (String) getValueAt("GLSG_GLBL.GL_BANK");
		String branchId = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		
		if(accountType.equals("970")) {
			// check if bank is Pagi
			if(OperationsUtil.compareWithTable(getRefTablesService(), "GLST_BANK","GL_BANK", bankId, "GL_SUG_BANK", OperationsUtil.PAGI_BANK_ID)) {
				if(branchId.equals("190")) {
					return RC_OK;
				}
			// check if bank is Benleumi
			} else if(OperationsUtil.compareWithTable(getRefTablesService(), "GLST_BANK","GL_BANK", bankId, "GL_SUG_BANK", OperationsUtil.BENLEUMI_BANK_ID)) {
				if(branchId.equals("297")) {
					return RC_OK;
				}
			}
			String params[] = {"970"};
			setError(Arrays.asList(params));
			return RC_ERROR;			
		} else {
			return RC_OK;
		}
	}
}