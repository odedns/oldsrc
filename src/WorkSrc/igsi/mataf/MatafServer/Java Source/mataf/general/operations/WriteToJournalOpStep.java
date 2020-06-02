package mataf.general.operations;

import mataf.services.MessagesHandlerService;
import mataf.services.electronicjournal.JournalSchemaManagement;
import mataf.services.electronicjournal.MatafJournalService;
import mataf.utils.MatafUtilities;

import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.HashtableFormat;
import com.ibm.dse.services.jdbc.JournalService;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WriteToJournalOpStep extends MatafOperationStep {

	/**
	 * Constructor for WriteToJournalOpStep.
	 */
	public WriteToJournalOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		JournalService journalService = null;
		String journalServiceName = null;
		String formatName = null;
		
		try {
			journalServiceName = (String) getParams().getValueAt(SERVICE_NAME_PARAM_NAME);
			formatName = (String) getParams().getValueAt(FORMAT_NAME_PARAM_NAME);
			
			journalService = (JournalService) getService(journalServiceName); 			
			
			journalService.addRecord(getContext(), formatName);
			
			journalService.commit();
			
			return RC_OK;
		} catch(Exception ex) {
			MatafUtilities.setErrorMessageInGLogger(this.getClass(), getContext(), ex, null);
			addToErrorListFromXML();
			return RC_ERROR;
		}
	}
	
}
