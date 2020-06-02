package mataf.general.operations;

import java.awt.Color;
import java.util.StringTokenizer;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Service;
import com.ibm.dse.gui.Settings;

import mataf.data.VisualDataField;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;
import mataf.utils.HebrewConverter;
import mataf.utils.MatafUtilities;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HostMessageSetterOpStep extends MatafOperationStep {

	public static final String OFI_SHEDER_FOR_ERR_MSG = "S";
	public static final String MSG_FROM_HOST_IND = "H";
	
	/**
	 * Constructor for HostMessageSetterOpStep.
	 */
	public HostMessageSetterOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String ofiSheder = (String) getValueAt("*.GL_OFI_SHEDER");		
		String noseShgia = (String) getValueAt("*.GL_NOSE_SGIA");
		String kodShgia = (String) getValueAt("*.GL_KOD_SGIA");
		String msgFromHost = (String) getValueAt("*.GL_HODAA");
		
		if((ofiSheder != null) && (ofiSheder.equals(OFI_SHEDER_FOR_ERR_MSG))) {
			String errorKey = noseShgia.concat(MSG_FROM_HOST_IND).concat(kodShgia);
			String errMsgFromTable = getMessagesHandlerService().getMsgFromTable(errorKey, getRefTablesService());
			MatafUtilities.addBusinessMessage(getContext(), errMsgFromTable.concat(msgFromHost));
		}
		
		return RC_OK;
	}

}
