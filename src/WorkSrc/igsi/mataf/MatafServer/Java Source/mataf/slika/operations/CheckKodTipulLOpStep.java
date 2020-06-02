package mataf.slika.operations;

import java.awt.Color;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.gui.Settings;

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
public class CheckKodTipulLOpStep extends MatafOperationStep {

	public static final String FIELD_2_SET_TEXT_ATT_NAME = "field2setText";
	
	/**
	 * Constructor for CheckKodTipulLOpStep.
	 */
	public CheckKodTipulLOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String kodTipul = (String) getValueAt("HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL");
		
		if(kodTipul.equals("L")) {
			disableAccountFields();
			setError();
			return RC_ERROR;
		}
		
		return RC_OK;
	}
	
	private void disableAccountFields() throws DSEObjectNotFoundException {
		
		((VisualDataField) getElementAt("BranchIdInput")).setIsEnabled(Boolean.valueOf("false"));
		((VisualDataField) getElementAt("AccountType")).setIsEnabled(Boolean.valueOf("false"));
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
