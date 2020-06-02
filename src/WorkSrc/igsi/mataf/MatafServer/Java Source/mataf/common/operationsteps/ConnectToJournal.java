/*
 * Created on 23/02/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.operationsteps;

import com.ibm.dse.base.OperationStep;
import com.ibm.dse.services.jdbc.JournalService;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConnectToJournal extends OperationStep {

	/* (non-Javadoc)
	 * @see com.ibm.dse.base.OperationStepInterface#execute()
	 */
	public int execute() throws Exception {
		// TODO this step should use the security manager functionality
		try {
			JournalService journal = (JournalService) getService("journalService");
			//Initializes and opens the journal service.
			if (journal == null) {
				return RC_ERROR;
			}
			if (! journal.isActive())
				journal.connect(journal.getDataSourceName(), "mataf", "mataf");
			if (journal.isConnected()) {
				journal.openForEntity("SLIKA_JOURNAL");
			}

			return RC_OK;
		} catch (Exception e) {
			e.printStackTrace();
			return RC_ERROR;
		}
	}

}
