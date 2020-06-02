package mataf.halbanathon.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCService;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.general.operations.MatafOperationStep;
import mataf.general.operations.OperationsUtil;

/**
 * This opstep checks wether we need to open the Halbant Hon Screen or not.
 * 
 * @author Nati Dykstein. Creation Date : (27/10/2003 16:56:06).  
 */
public class CheckIsHalbanatHonNeededForAccountOpStep extends MatafOperationStep 
{
	
	private static final String PRATIM_KVUIM = "1";
	
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		if(isBankPagiOrBenleumi() && isAccountExistsInTable()) 
		{
			setValueAt("GLSX_K86P_RESULT.HA_SW_HAZRAMA_CHOVA","0");
			setValueAt("GLSX_K86P_RESULT.HA_SW_DIVUACH_BUTZA","3");
			String pratinKvuim = (String)getValueAt("GLSX_K86P_PARAMS.HA_TLR_MCHZ_P_KVUIM");
			if(pratinKvuim.equals(PRATIM_KVUIM))
			{
				addToErrorListFromXML();
				return RC_ERROR;
			}
		}
		return RC_OK;
	}
	
	private boolean isBankPagiOrBenleumi() throws Exception {
		String bankId = (String)getValueAt("GLSX_K86P_PARAMS.HA_BANK");
		String sugBank = OperationsUtil.getSugBank(getRefTablesService(), bankId);
		return (sugBank.equals(OperationsUtil.PAGI_BANK_ID) || sugBank.equals(OperationsUtil.BENLEUMI_BANK_ID));
	}
	
	public boolean isAccountExistsInTable() throws Exception
	{
		String snifNumber	 = (String)getValueAt("GLSX_K86P_PARAMS.HA_SNIF");
		String accountType 	 = (String)getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		String accountNumber = (String)getValueAt("GLSX_K86P_PARAMS.HA_CH");
						
		JDBCTable service = (JDBCTable)getService("HAST_CH_LO_MEDUVACH");
		String SQLCondition = "HA_SNIF="    + snifNumber   +
							 " AND HA_SCH=" + accountType  +
							 " AND HA_CH="  + accountNumber;
		Vector aDataVector = service.retrieveRecordsMatching(SQLCondition.toString());
		return aDataVector.size() == 1;
	}
}
