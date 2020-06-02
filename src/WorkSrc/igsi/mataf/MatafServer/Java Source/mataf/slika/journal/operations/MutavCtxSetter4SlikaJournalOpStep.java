package mataf.slika.journal.operations;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.services.ldap.MapFormat;

import mataf.general.operations.MatafOperationStep;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/10/2003 16:35:17).  
 */
public class MutavCtxSetter4SlikaJournalOpStep extends MatafOperationStep {

	public static final int NUMBER_OF_CHECKS_2_COMPARE = 7;
	public static final String TYPE_OF_RECORD_IN_JOURNAL = "X";
	public static final String NUMBER_OF_RECORD_IN_JOURNAL = "00";
	
	/**
	 * Constructor for MutavCtxSetter4SlikaJournalOpStep.
	 */
	public MutavCtxSetter4SlikaJournalOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String branch = (String) getValueAt("BranchIdInput");
		String accountType = (String) getValueAt("AccountType");
		String accountNumber = (String) getValueAt("AccountNumber");
		String beneficiaryBranchId = (String) getValueAt("BeneficiaryBranchId");
		
//		setMessage("CZSJ_REC.CZSJ_MUTAV.GL_MELEL1", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5008");
		setMessage("CZSJ_REC.GL_MELEL1", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5008");
		setMelelHeshbonMutav();
		setPrintingData();
		checkDeposit2otherBranch();
		checkNumberOfChecks();
		setMutavJournalKey();
//		setValueAt("CZSJ_REC.GKSJ_HDR.GL_PEULA_MISHNIT", "410");
		setValueAt("CZSJ_REC.GL_PEULA_MISHNIT", "T410");
		mapContents("CZAJ_MKRT_MapperFmt");
		checkAccount046106770124(branch, accountType, accountNumber);
		checkAccount046106770345(branch, accountType, accountNumber);
		setLoansNumber();
		mapContents("CZAJ_T110_MapperFmt");
//		setValueAt("CZSJ_REC.GKSJ_HDR.GL_SW_LVARER", "1");
		setValueAt("CZSJ_REC.GL_SW_LVARER", "1");
//		setValueAt("CZSJ_REC.CZSJ_MUTAV.CH_SNIF_MUTAV", beneficiaryBranchId);
		setValueAt("CZSJ_REC.MU_SNIF_MUTAV", beneficiaryBranchId);
		setBeneficiaryBranchIdDesc(beneficiaryBranchId);
		setKotSnif();
		
		// Here should handle (if needed) printing data (paragraph tt)
		
//		setValueAt("CZSJ_REC.GKSJ_HDR.GL_PEULA", "410");
//		setValueAt("CZSJ_REC.CZSJ_MUTAV.CZSI_PEULA_MEKORIT.GL_PEULA", "410");
		setValueAt("CZSJ_REC.CZSI_PEULA_MEKORIT.GL_PEULA", "T410");
		
		setDescFields();
		
		return RC_OK;
	}
	
	private void setDescFields() throws Exception {
		// set account type desc
		setDescField("GLST_SCH", "GL_SCH", (String) getValueAt("CZSJ_REC.MU_SCH"), "GL_SHEM_SCH", "CZSJ_REC.MU_SCH_DESC");
		// set snif desc
		setDescField("GLST_SNIF", "GL_SNIF", (String) getValueAt("CZSJ_REC.MU_SNIF_MUTAV"), "GL_SHEM_SNIF", "CZSJ_REC.MU_SNIF_MUTAV_DESC");
	}
	
	private void setDescField(String tableName, String keyColumnName, String keyValue, String valueColumnName, String fieldName2set) 
									throws Exception {
		IndexedCollection iColl = getRefTablesService().getByKey(tableName, keyColumnName, keyValue);
		String value2set = "";
		if(iColl.size()>0) {
			value2set = (String) ((KeyedCollection)iColl.getElementAt(0)).getValueAt(valueColumnName);
		}
		setValueAt(fieldName2set, value2set);
	}
	
	private void setKotSnif() throws Exception {
		String branch = (String) getValueAt("BranchIdInput");
		String pakidBranch = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		if(branch.equals(pakidBranch)) {
//			setMessage("CZSJ_REC.CZSJ_MUTAV.CH_KOT_SNIF", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5013");
			setMessage("CZSJ_REC.MU_KOT_SNIF", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5013");
		}
	}
	
	private void setBeneficiaryBranchIdDesc(String beneficiaryBranchId) throws Exception {
		IndexedCollection resultsIcoll = (IndexedCollection) getRefTablesService().getByKey("GLST_SNIF", "GL_SNIF", beneficiaryBranchId);
//		setValueAt("CZSJ_REC.CZSJ_MUTAV.CH_SNIF_MUTAV_DESC", ((KeyedCollection) resultsIcoll.getElementAt(0)).getValueAt("GL_SHEM_SNIF"));
		setValueAt("CZSJ_REC.MU_SNIF_MUTAV_DESC", ((KeyedCollection) resultsIcoll.getElementAt(0)).getValueAt("GL_SHEM_SNIF"));
	}
	
	private void setLoansNumber() throws Exception {
		int numberOfLoans = Integer.parseInt((String) getValueAt("numberOfLoans"));
		if(numberOfLoans>1) {
			String[] msgParams = {String.valueOf(numberOfLoans)};
//			setMessage("CZSJ_REC.CZSJ_MUTAV.TL_MELEL_HALVAOT", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5012", Arrays.asList(msgParams));
			setMessage("CZSJ_REC.TL_MELEL_HALVAOT", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5012", Arrays.asList(msgParams));
		}
//		setValueAt("CZSJ_REC.CZSJ_MUTAV.TL_MISPAR_HALVAOT", String.valueOf(numberOfLoans));
		setValueAt("CZSJ_REC.TL_MISPAR_HALVAOT", String.valueOf(numberOfLoans));
	}
	
	private void checkAccount046106770345(String branch, String accountType, String accountNumber) throws Exception {
		if(branch.equals("046") && accountType.equals("106") && accountNumber.equals("770345")) {
//			setMessage("CZSJ_REC.CZSJ_MUTAV.GL_MELEL4", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5001");
			setMessage("CZSJ_REC.GL_MELEL4", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5001");
		}
	}
	
	private void checkAccount046106770124(String branch, String accountType, String accountNumber) throws Exception {
		if(branch.equals("046") && accountType.equals("106") && accountNumber.equals("770124")) {
//			setMessage("CZSJ_REC.CZSJ_MUTAV.GL_MELEL4", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5000");
			setMessage("CZSJ_REC.GL_MELEL4", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5000");
		}
	}
	
	private void mapContents(String mapperName) 
				throws DSEInvalidRequestException, DSEInvalidClassException, DSEInvalidArgumentException {
		DataMapperFormat dmf = (DataMapperFormat) getFormat(mapperName);
		dmf.mapContents(getContext(), getContext());
	}
	
	private void setMutavJournalKey() throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		StringBuffer mutavJournalKey = new StringBuffer();
		mutavJournalKey.append((String) getValueAt("GLSF_GLBL.GL_MISPAR_TAHANA"));
		mutavJournalKey.append((String) getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_CZ"));
		mutavJournalKey.append(TYPE_OF_RECORD_IN_JOURNAL);
		mutavJournalKey.append(NUMBER_OF_RECORD_IN_JOURNAL);
		setValueAt("CZSS_T110.mutavJournalKey", mutavJournalKey);		
	}
	
	private void checkNumberOfChecks() throws Exception {
		int numberOfChecks = Integer.parseInt((String) getValueAt("NumberOfCheques"));
		if(numberOfChecks>NUMBER_OF_CHECKS_2_COMPARE)
			setMessage("CZSS_T110.CH_MAKRO_SIGN", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5010");
		else
			setMessage("CZSS_T110.CH_MAKRO_SIGN", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5011");			
	}
	
	private void checkDeposit2otherBranch() throws Exception {
		String systemBranch = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		String BranchIdInput = (String) getValueAt("BranchIdInput");
		if(!systemBranch.equals(BranchIdInput))
			setMessage("CZSS_T110.CH_KOTERET", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5009");
	}
	
	// this method was writen because the '&' character could cause problems in the XML
	private void setPrintingData() throws Exception {
		setMessage("CZSS_T110.CH_MAKRO_KOT1", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5002");
		setMessage("CZSS_T110.CZ_MAKRO_50", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5003");
		setMessage("CZSS_T110.CH_MAKRO_DETAIL", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5004");
		setMessage("CZSS_T110.CH_YAAD", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5005");
	}
	
	private void setMelelHeshbonMutav() throws Exception {
		String bankOfSnifHachzara = (String) getBankBySnif("BeneficiaryBranchId");
		String bankOfSnifMaarechet = (String) getBankBySnif("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		if(bankOfSnifHachzara.equals(bankOfSnifMaarechet)) {
//			setMessage("CZSJ_REC.CZSJ_MUTAV.CZ_MELEL_HESHBON", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5006");
			setMessage("CZSJ_REC.CZ_MELEL_HESHBON", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5006");
		} else {
//			setMessage("CZSJ_REC.CZSJ_MUTAV.CZ_MELEL_HESHBON", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5007");
			setMessage("CZSJ_REC.CZ_MELEL_HESHBON", MessagesHandlerService.APP_MSG_SERVICE_NAME, "5007");
		}
	}
	
	private String getBankBySnif(String snifFieldName) throws Exception {
		String snif = (String) getValueAt(snifFieldName);
		RefTables refTablesSrvs = getRefTablesService();
		IndexedCollection resultsInIcoll = refTablesSrvs.getByKey("GLST_SNIF", "GL_SNIF", snif);
		String ifyunSnif = (String) ((KeyedCollection) resultsInIcoll.getElementAt(0)).getValueAt("GL_IFYUN_SNIF");
		return ifyunSnif.substring(0,1);
	}
	
}
