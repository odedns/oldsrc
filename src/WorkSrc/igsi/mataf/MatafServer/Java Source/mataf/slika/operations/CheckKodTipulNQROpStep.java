package mataf.slika.operations;

import java.awt.Color;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import mataf.data.VisualDataField;
import mataf.general.operations.*;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckKodTipulNQROpStep extends MatafOperationStep {

	public static final String FIELD_2_SET_TEXT_ATT_NAME = "field2setText";
	
	/**
	 * Constructor for CheckKodTipulNQROpStep.
	 */
	public CheckKodTipulNQROpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String kodTipul = (String) getValueAt("HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL");
		
		if(kodTipul.equals("N") || kodTipul.equals("Q") || kodTipul.equals("R")) {
			if(isAccountExistInNoDataAccountsTable()) {
				setAccountFields();
				setTextInField();
				setValueAt("shouldRequestIshurMenahel", "true");
			} else if(kodTipul.equals("Q") || kodTipul.equals("R")) {
				String field2setInErr = (String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME);
				((VisualDataField) getElementAt(field2setInErr)).setErrorFromServer(""); 
				return RC_ERROR;
			}
		}
		
		return RC_OK;
	}
	
	private boolean isAccountExistInNoDataAccountsTable() throws DSEObjectNotFoundException, DSEException {
		StringBuffer aSearchCondition = new StringBuffer();
		aSearchCondition.append("bankId=");
		aSearchCondition.append((String) getValueAt("GLSG_GLBL.GL_BANK"));
		aSearchCondition.append(" and branchId=");
		aSearchCondition.append((String) getValueAt((String) getParams().getValueAt(BRANCH_ATT_NAME)));
		aSearchCondition.append(" and accountType=");
		aSearchCondition.append((String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME)));
		aSearchCondition.append(" and accountNumber=");
		aSearchCondition.append((String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME)));
		
		JDBCTable tableService = (JDBCTable) getService("noDataAccount");
		Vector aDataVector = tableService.retrieveRecordsMatching(aSearchCondition.toString());
		return aDataVector.size()>0;
	}
	
	private void setAccountFields() throws DSEObjectNotFoundException {
		
		((VisualDataField) getElementAt("BranchIdInput")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("AccountType")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt("AccountNumber")).setIsEnabled(Boolean.FALSE);
		
		((VisualDataField) getElementAt("BeneficiaryBranchId")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt("BeneficiaryAccountType")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt("BeneficiaryAccountNumber")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt("AccountsToReturn")).setIsEnabled(Boolean.TRUE);
	}
	
	private void setTextInField() throws Exception {
		String tableId = (String) getParams().getValueAt(MSG_TABLE_ID_PARAM_NAME);
		String msgNumber = (String) getParams().getValueAt(ERR_NO_PARAM_NAME);
		String msg = getMessagesHandlerService().getMsgFromTable(tableId, msgNumber, getRefTablesService());
		
		String field2setText = (String) getParams().getValueAt(FIELD_2_SET_TEXT_ATT_NAME);
		((VisualDataField) getElementAt(field2setText)).setValue(msg);
		((VisualDataField) getElementAt(field2setText)).setForeground((Color) Settings.getValueAt("errorForegroundColor"));
	}

}
