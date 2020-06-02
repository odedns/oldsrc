package mataf.slika.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.*;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InitSlikaFieldsOpStep extends MatafOperationStep {

	/**
	 * Constructor for InitSlikaFieldsOpStep.
	 */
	public InitSlikaFieldsOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		// enable/disable fields in screen T410
		((VisualDataField) getElementAt("BranchIdInput")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("AccountType")).setIsEnabled(Boolean.valueOf("true"));		
		((VisualDataField) getElementAt("AccountNumber")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("AccountsToReturn")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("BeneficiaryBranchId")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("BeneficiaryAccountType")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("BeneficiaryAccountNumber")).setIsEnabled(Boolean.valueOf("false"));
		
		((VisualDataField) getElementAt("TotalAmount")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("ContinueAction")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("DepositSource")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("Asmachta")).setIsEnabled(Boolean.valueOf("false"));
		
		return RC_OK;
	}

}
