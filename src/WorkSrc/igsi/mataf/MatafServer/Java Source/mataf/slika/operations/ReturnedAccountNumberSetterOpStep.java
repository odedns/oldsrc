package mataf.slika.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;
import mataf.services.reftables.RefTables;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (14/10/2003 10:53:00).  
 */
public class ReturnedAccountNumberSetterOpStep extends MatafOperationStep {

	/**
	 * Constructor for ReturnedAccountNumberSetterOpStep.
	 */
	public ReturnedAccountNumberSetterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		((VisualDataField) getElementAt("BeneficiaryAccountNumber")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("BeneficiaryAccountType")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("BeneficiaryBranchId")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("AccountsToReturn")).setIsEnabled(Boolean.FALSE);
		
		if(isAccountExistInTable("AccountNumber", "AccountType", "BranchIdInput") || 
		   isAccountExistInTable("BeneficiaryAccountNumber", "BeneficiaryAccountType", "BeneficiaryBranchId")) 
		{
			((VisualDataField) getElementAt("PerutHalvaotButton")).setIsEnabled(Boolean.TRUE);
		}
		else
		{
			((VisualDataField) getElementAt("PerutHalvaotButton")).setIsEnabled(Boolean.FALSE);
			((VisualDataField) getElementAt("HazramatHamchaotButton")).setIsEnabled(Boolean.TRUE);
		}
		return RC_OK;
	}
	
	private boolean isAccountExistInTable(String accountNumberFieldName,
							  			    String accountTypeFieldName,
							  			    String branchFieldName) 
							  			  	  throws Exception
	{
		String accountNumber = ((String) getValueAt(accountNumberFieldName)).trim();
		String accountType = ((String) getValueAt(accountTypeFieldName)).trim();
		String branch = ((String) getValueAt(branchFieldName)).trim();
		String account = branch.concat(accountType).concat(accountNumber);
		IndexedCollection resultsIcoll = getRefTablesService().getByKey("TLST_CH_MASHKANTAOT", "TL_CH", account);
		return resultsIcoll.size()>0;		
	}
							   	

}
