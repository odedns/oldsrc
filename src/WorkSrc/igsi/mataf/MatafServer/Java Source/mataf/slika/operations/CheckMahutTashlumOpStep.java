package mataf.slika.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.*;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckMahutTashlumOpStep extends MatafOperationStep {

	public static final int HOTSAOT_RISHONYOT = 1;
	
	public static final int TAKBULIM = 2;
	
	/**
	 * Constructor for CheckMahutTashlumOpStep.
	 */
	public CheckMahutTashlumOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		int mahutTashlum = Integer.parseInt((String) getValueAt("MahutTashlum"));
		
		IndexedCollection loansList = (IndexedCollection) getElementAt("LoansList");
		
		if(mahutTashlum == HOTSAOT_RISHONYOT) {
			setLoansList4hotsaotRishonyot(loansList);
		} else {
			setLoansList4takbulim(loansList);
		}
		
		return RC_OK;
	}
	
	private void setLoansList4takbulim(IndexedCollection loansList) 
							throws DSEInvalidArgumentException, DSEObjectNotFoundException {
		((VisualDataField) getElementAt("HotsaotRishoniotLoanNumber")).setIsVisible(Boolean.FALSE);
		((VisualDataField) getElementAt("HotsaotRishoniotLoanAmmount")).setIsVisible(Boolean.FALSE);
		((VisualDataField) getElementAt("HotsaotRishoniotLoanNumberTitle")).setIsVisible(Boolean.FALSE);
		((VisualDataField) getElementAt("HotsaotRishoniotLoanAmmountTitle")).setIsVisible(Boolean.FALSE);
		((VisualDataField) getElementAt("HalvaotTable")).setIsVisible(Boolean.TRUE);
	}
	
	private void setLoansList4hotsaotRishonyot(IndexedCollection loansList) throws Exception {
		
		IndexedCollection resultsFromTable = getRefTablesService().getByKey("TLST_CH_MASHKANTAOT", "TL_CH", getFullAccountNumber());
		
		((VisualDataField) getElementAt("HotsaotRishoniotLoanNumber")).setIsVisible(Boolean.TRUE);
		((VisualDataField) getElementAt("HotsaotRishoniotLoanAmmount")).setIsVisible(Boolean.TRUE);
		((VisualDataField) getElementAt("HotsaotRishoniotLoanNumberTitle")).setIsVisible(Boolean.TRUE);
		((VisualDataField) getElementAt("HotsaotRishoniotLoanAmmountTitle")).setIsVisible(Boolean.TRUE);
		((VisualDataField) getElementAt("HalvaotTable")).setIsVisible(Boolean.FALSE);
		
		if(resultsFromTable.size() > 0) {
			String loanNumber = (String) ((KeyedCollection) resultsFromTable.getElementAt(0)).getValueAt("TL_MISPAR_HALVAA");
			setValueAt("HotsaotRishoniotLoanNumber", loanNumber);
		} else {
			setValueAt("HotsaotRishoniotLoanNumber", "");
			setValueAt("HotsaotRishoniotLoanAmmount", "");
		}

//		if(resultsFromTable.size() > 0) {
//			String loanNumber = (String) ((KeyedCollection) resultsFromTable.getElementAt(0)).getValueAt("TL_MISPAR_HALVAA");
//			setValueAt("LoansList.0.loanNumber", loanNumber);
//		}
//		
//		for(int counter=1 ; counter<loansList.size() ; counter++ ) {
//			setValueAt("LoansList."+counter+".loanNumber", "");
//			setValueAt("LoansList."+counter+".loanAmount", "");
//		}
//		
//		setValueAt("LoansList_MetaData.maxRows", "1");
	}
	
	private String getFullAccountNumber() throws DSEObjectNotFoundException {
		String branchId = (String) getValueAt("BranchIdInput");
		String accountType = (String) getValueAt("AccountType");
		String accountNumber = (String) getValueAt("AccountNumber");
		return branchId.concat(accountType).concat(accountNumber);
	}

}
