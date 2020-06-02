package mataf.slika.operations;

import java.awt.Color;

import com.ibm.dse.gui.Settings;

import mataf.data.VisualDataField;
import mataf.format.VisualFieldFormat;
import mataf.general.operations.*;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckMazavLakochNoDataOpStep extends MatafOperationStep {

	public static final int NO_DATA = 1;
	
	public static final String FIELD_NAME_2_SET_TEXT_ATT_NAME = "fieldName2setText";
	
	/**
	 * Constructor for CheckMazavLakochNoDataOpStep.
	 */
	public CheckMazavLakochNoDataOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		int mazavLakoach = Integer.parseInt((String) getValueAt("AccountBalanceHostReplyData.GL_MAZAV_LAKOACH"));
				
		if(mazavLakoach == NO_DATA) {
			String fieldName2setText = (String) getParams().getValueAt(FIELD_NAME_2_SET_TEXT_ATT_NAME);
			VisualDataField field2set = (VisualDataField) getElementAt(fieldName2setText);
			String tableId = (String) getParams().getValueAt(MSG_TABLE_ID_PARAM_NAME);
			String msgNumber = (String) getParams().getValueAt(ERR_NO_PARAM_NAME);	
			String msg = getMessagesHandlerService().getMsgFromTable(tableId, msgNumber, getRefTablesService());
			field2set.setValue(msg);
			field2set.setForeground((Color) Settings.getValueAt("errorForegroundColor"));
			return RC_ERROR;
		}
		
		return RC_OK;
	}

}
