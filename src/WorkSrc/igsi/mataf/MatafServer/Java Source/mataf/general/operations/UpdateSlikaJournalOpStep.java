package mataf.general.operations;

import java.text.DecimalFormat;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.HashtableFormat;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JournalService;

import mataf.services.MessagesHandlerService;
import mataf.services.electronicjournal.JournalSchemaManagement;
import mataf.services.electronicjournal.MatafJournalService;
import mataf.utils.MatafUtilities;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UpdateSlikaJournalOpStep extends MatafOperationStep {

	/**
	 * Constructor for UpdateSlikaJournalOpStep.
	 */
	public UpdateSlikaJournalOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		// update slika journal
		JournalService journalService = null;
		String journalServiceName = null;
		String formatName = null;
		
		try {
			journalServiceName = (String) getParams().getValueAt(SERVICE_NAME_PARAM_NAME);
			formatName = (String) getParams().getValueAt(FORMAT_NAME_PARAM_NAME);
			journalService = (MatafJournalService) getService(journalServiceName); 			
			
			HashtableFormat updateFormat = (HashtableFormat) getFormat(formatName);
			int numberOfRowsUpdated = journalService.updateRecords(getCondition(), getContext(), updateFormat, getColumns2bUpdated());
			if(numberOfRowsUpdated<1) {
				addToErrorListFromXML();
				return RC_ERROR;
			}
			
			journalService.commit();
			
			return RC_OK;
		} catch(Exception ex) {
			MatafUtilities.setErrorMessageInGLogger(this.getClass(), getContext(), ex, null);
			addToErrorListFromXML();
			return RC_ERROR;
		}
	}
	
	private Vector getColumns2bUpdated() {
		Vector vec2return = new Vector();
		vec2return.add("GL_SW_HUSHLEMA");
		vec2return.add("GL_SW_LVARER");
		return vec2return;
	}
	
	private String getCondition() throws DSEObjectNotFoundException {
		int siduriTahana = Integer.parseInt((String) getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_CZ"));
		DecimalFormat decimalFormat = new DecimalFormat("0000");
		
		StringBuffer sqlCondition = new StringBuffer();
		sqlCondition.append("CZSI_PEULA_MEKORIT_GL_MISPAR_TAHANA='");
		sqlCondition.append((String) getValueAt("GLSF_GLBL.GL_MISPAR_TAHANA"));
		sqlCondition.append("' AND CZSI_PEULA_MEKORIT_GL_SIDURI_TAHANA='");
		sqlCondition.append(decimalFormat.format(siduriTahana));
		sqlCondition.append("'");
		
		return sqlCondition.toString();
	}

}
