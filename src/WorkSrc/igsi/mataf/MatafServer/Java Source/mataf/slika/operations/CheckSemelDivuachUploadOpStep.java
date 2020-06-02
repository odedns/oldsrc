package mataf.slika.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;
import mataf.general.operations.OperationsUtil;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckSemelDivuachUploadOpStep extends MatafOperationStep {
	
	public static final String ACCOUNT_TYPE_WITH_SEMEL_DIVUACH = "105";
	
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String accountType = (String) getValueAt("AccountType");
				
		if( isAccountTypeWithSemelDivuachAccordingToTable(accountType) && 
			accountType.equals(ACCOUNT_TYPE_WITH_SEMEL_DIVUACH) &&
			isAccountPropertyWithSemelDivuach()) 
		{
			setSemelDivuachData();
			((VisualDataField) getElementAt("TL_DIVUACH_HOST")).setIsEnabled(Boolean.TRUE);
		}
		
		return RC_OK;
	}
	
	private boolean isAccountPropertyWithSemelDivuach() throws Exception {
		String kodIfyun = ((String)getValueAt("AccountBalanceHostReplyData.GKSG_IFYUN")).trim();
		IndexedCollection resultsIcoll = getRefTablesService().getByKey("GLST_IFYUN_2", "showSemelDivuach", "true");
		String currentIfyun = null;
		for(int counter=0 ; counter<resultsIcoll.size() ; counter++) {
			currentIfyun = (String) ((KeyedCollection) resultsIcoll.getElementAt(counter)).getValueAt("GL_IFYUN");
			if(kodIfyun.indexOf(currentIfyun) != -1) {
				return true;
			}
		}
		return false;
	}
		
	private void setSemelDivuachData() throws Exception {
		String zchutChovaAction = getZchutChovaAction();
		String semelDivuachTableName = getSemelDivuachTableName(zchutChovaAction);
		IndexedCollection tlstPaltashIcoll = getRefTablesService().getByKey("TLST_PALTASH", "TL_FLAG_ZCHUT_HOVA", zchutChovaAction);
		KeyedCollection tlstPaltashKcoll = (KeyedCollection) tlstPaltashIcoll.getElementAt(0);
		
		float totalAmount = getAmountAsFloat((String) getValueAt("TotalAmount"));
		float tl_schum_gvul1 = getAmountAsFloat((String) tlstPaltashKcoll.getValueAt("TL_SCHUM_GVUL1"));
		// if totalAmount is greater then or equal to  tl_schum_gvul1 then...
		IndexedCollection icollFromTable = null;
		if((totalAmount-tl_schum_gvul1)>=0) {
			icollFromTable = getRefTablesService().getByKey(semelDivuachTableName, "TL_FLAG_SCHUM_GVUL", "1");
			setValueAt("TL_DIVUACH_HOST", "");
		} else {
			setDefaultValueForSemelDivuach(zchutChovaAction);
			icollFromTable = getRefTablesService().getAll(semelDivuachTableName);
		}
		
		IndexedCollection icoll2set = (IndexedCollection) getElementAt("SemelDivuachList");
		String fieldNames[] = {"TL_ASMACHTA_001", "TL_TEUR_ASM_142"};
		icoll2set.removeAll();
		OperationsUtil.setTableData(icoll2set, icollFromTable, "SemelDivuachRecord", fieldNames);
	}
	
	private void setDefaultValueForSemelDivuach(String zchutChovaAction) throws Exception {
		String semelDivuach = (String) getValueAt("TL_DIVUACH_HOST");
		if((semelDivuach==null) || (semelDivuach.length()==0)) {
			String defaultValue = (String) getRefTablesService().getByKey("TLST_PALTASH", "TL_FLAG_ZCHUT_HOVA", zchutChovaAction, "TL_DEFAULT_VALUE");
			setValueAt("TL_DIVUACH_HOST", defaultValue);
		}
	}
	
	private String getSemelDivuachTableName(String zchutChovaAction) {
		if(zchutChovaAction.equals(PEULAT_HOVA)) {
			return "TLST_ASM_142_DB";
		} else { // peulat zchut
			return "TLST_ASM_142_CR";
		}
	}
	
	private float getAmountAsFloat(String amount2conv) {
		float amount = 0;
		if((amount2conv==null) || (amount2conv.trim().length()==0)) {
			return amount;
		} else {
			return Float.parseFloat(amount2conv);			
		}
	}
	
	private String getZchutChovaAction() throws Exception {
		String kodPeula = (String) getValueAt("GLSF_MAZAV.GKSG_PEULA.GL_KOD_PEULA");
		String nosePeula = (String) getValueAt("KodNosePeulaEn");
		return (String) getRefTablesService().getByKey("PEULOT", "GL_PEULA", nosePeula+kodPeula, "TL_FLAG_ZCHUT_CHOVA");
	}

	private boolean isAccountTypeWithSemelDivuachAccordingToTable(String accountType) throws Exception {
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("GLST_SCH", "GL_SCH",accountType);
		KeyedCollection kcoll = (KeyedCollection) resultsInIcoll.getElementAt(0);
		return Integer.parseInt((String)kcoll.getValueAt("GL_SW_ASM_SEMEL_DIV")) == 0;
	}

}
