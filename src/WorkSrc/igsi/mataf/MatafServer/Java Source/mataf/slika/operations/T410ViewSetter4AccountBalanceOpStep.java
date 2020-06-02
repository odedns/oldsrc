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
public class T410ViewSetter4AccountBalanceOpStep extends MatafOperationStep {

	/**
	 * Constructor for T410ViewSetter4AccountBalanceOpStep.
	 */
	public T410ViewSetter4AccountBalanceOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		// enable/disable fields in screen T410
		((VisualDataField) getElementAt("BranchIdInput")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("AccountNumber")).setIsEnabled(Boolean.valueOf("false"));		
		((VisualDataField) getElementAt("AccountType")).setIsEnabled(Boolean.valueOf("false"));		
		((VisualDataField) getElementAt("AccountsToReturn")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("BeneficiaryBranchId")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("BeneficiaryAccountType")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("BeneficiaryAccountNumber")).setIsEnabled(Boolean.valueOf("true"));
		
		((VisualDataField) getElementAt("TotalAmount")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("ContinueAction")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("DepositSource")).setIsEnabled(Boolean.valueOf("true"));
		((VisualDataField) getElementAt("Asmachta")).setIsEnabled(Boolean.valueOf("true"));
		
		return RC_OK;
	}

}
