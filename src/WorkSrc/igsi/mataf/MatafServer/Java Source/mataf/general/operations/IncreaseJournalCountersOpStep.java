package mataf.general.operations;

import java.text.DecimalFormat;

import mataf.logger.GLogger;
import mataf.services.MessagesHandlerService;
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
public class IncreaseJournalCountersOpStep extends MatafOperationStep {

	/**
	 * Constructor for SetRecordsToInquireOpStep.
	 */
	public IncreaseJournalCountersOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {		
		MatafJournalService journal = null;
		String journalServiceName = null;
		
		try {
			journalServiceName = (String) getParams().getValueAt(SERVICE_NAME_PARAM_NAME);
			journal = (MatafJournalService) getService(journalServiceName);
			journal.increaseCounters(getContext());
			
			return RC_OK;
		} catch(Exception ex) {
			MatafUtilities.setErrorMessageInGLogger(this.getClass(), getContext(), ex, null);
			addToErrorListFromXML();
			return RC_ERROR;
		}
	}

}
