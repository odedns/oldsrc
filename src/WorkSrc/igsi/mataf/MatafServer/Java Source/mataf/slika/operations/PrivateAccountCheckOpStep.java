package mataf.slika.operations;

import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;

import mataf.data.VisualDataField;
import mataf.general.operations.*;
import mataf.services.reftables.RefTables;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrivateAccountCheckOpStep extends MatafOperationStep {

	/**
	 * Constructor for PrivateAccountCheckOpStep.
	 */
	public PrivateAccountCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		IndexedCollection iColl = getRefTablesService().getByKey("GLST_SCH", "GL_SCH", (String) getValueAt("AccountType"));
		boolean isPrivateAccount = ((KeyedCollection) iColl.getElementAt(0)).getValueAt("GL_SW_SB_CH").equals("1");
		
		if(isPrivateAccount) {
			String branchId = (String) getValueAt("BranchIdInput");
			String accountType = (String) getValueAt("AccountType");
			String accountNumber = (String) getValueAt("AccountNumber");
			
			setValueAt("BeneficiaryBranchId", branchId);
			setValueAt("BeneficiaryAccountType", accountType);
			setValueAt("BeneficiaryAccountNumber", accountNumber);
			
			((VisualDataField) getElementAt("AccountsToReturn")).setIsEnabled(Boolean.valueOf("false"));
			((VisualDataField) getElementAt("BeneficiaryBranchId")).setIsEnabled(Boolean.valueOf("false"));
			((VisualDataField) getElementAt("BeneficiaryAccountType")).setIsEnabled(Boolean.valueOf("false"));
			((VisualDataField) getElementAt("BeneficiaryAccountNumber")).setIsEnabled(Boolean.valueOf("false"));			
		} else {
			((VisualDataField) getElementAt("AccountsToReturn")).setIsEnabled(Boolean.valueOf("true"));
			((VisualDataField) getElementAt("BeneficiaryBranchId")).setIsEnabled(Boolean.valueOf("true"));
			((VisualDataField) getElementAt("BeneficiaryAccountType")).setIsEnabled(Boolean.valueOf("true"));
			((VisualDataField) getElementAt("BeneficiaryAccountNumber")).setIsEnabled(Boolean.valueOf("true"));
		}
		
		return RC_OK;
	}

}
