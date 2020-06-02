package mataf.general.operations;

import java.io.IOException;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.services.reftables.RefTables;
import mataf.utils.MatafUtilities;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StartUpTablesLoaderOpStep extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		RefTables refTables = (RefTables) Service.readObject("refTables");
		
		// Init banks table
		IndexedCollection banksList = (IndexedCollection) getElementAt("banksList");
		banksList.removeAll();
		IndexedCollection banksTable = refTables.getAll("GLST_BANK");
		String[] arrayOfBankFieldsNames = {"GL_BANK","GL_SHEM_BANK"};
		MatafUtilities.setTableData(banksList, banksTable, "bankData", arrayOfBankFieldsNames);
		
		// init snifim table
		String bankNumber = (String) getValueAt("GLSG_GLBL.GL_BANK");
		IndexedCollection sugBankIcoll = refTables.getByKey("GLST_BANK", "GL_BANK", bankNumber);
		String bankType = new String();
		if(sugBankIcoll.size()>0) {
			bankType = (String) ((KeyedCollection) sugBankIcoll.getElementAt(0)).getValueAt("GL_SUG_BANK");
			setValueAt("sugBank", bankType);
		}
		
		JDBCTable tableService = (JDBCTable) getService("snifin");
		String aSearchCondition = "";
		if(bankType.equals(MatafUtilities.PAGI_BANK_ID) || bankType.equals(MatafUtilities.BENLEUMI_BANK_ID)) {
			aSearchCondition= " GL_IFYUN_SNIF LIKE '"+bankType+"%'";
		}
		Vector aDataVector = tableService.retrieveRecordsMatching(aSearchCondition.toString());
		IndexedCollection snifList = (IndexedCollection) getElementAt("snifList");
		snifList.removeAll();
		String[] arrayOfFieldsNames1 = {"GL_SNIF","GL_SHEM_SNIF"};
		MatafUtilities.setTableData(snifList, aDataVector, "snifData", arrayOfFieldsNames1);
		
		// loading pagi branchs
		loadSnifimByBank(MatafUtilities.PAGI_BANK_ID, "snifListPagi", tableService);
		
		// loading benleumi branchs
		loadSnifimByBank(MatafUtilities.BENLEUMI_BANK_ID, "snifListBenleumi", tableService);
		
		// init account types table
		IndexedCollection accountTypesList = (IndexedCollection) getElementAt("accountTypesList");
		accountTypesList.removeAll();
		IndexedCollection accountTypesTable = refTables.getAll("GLST_SCH");
		String[] arrayOfFieldsNames = {"GL_SCH","GL_SHEM_SCH"};
		MatafUtilities.setTableData(accountTypesList, accountTypesTable, "accountTypeData", arrayOfFieldsNames);
		return RC_OK;
	}
	
	private void loadSnifimByBank(String bank, String icollToLoadInto, JDBCTable tableService) 
													throws DSEException, IOException {
		String aSearchCondition= " GL_IFYUN_SNIF LIKE '"+bank+"%'";
		Vector aDataVector = tableService.retrieveRecordsMatching(aSearchCondition.toString());
		IndexedCollection snifList = (IndexedCollection) getElementAt(icollToLoadInto);
		snifList.removeAll();
		String[] arrayOfFieldsNames1 = {"GL_SNIF","GL_SHEM_SNIF"};
		MatafUtilities.setTableData(snifList, aDataVector, "snifData", arrayOfFieldsNames1);
	}

}
