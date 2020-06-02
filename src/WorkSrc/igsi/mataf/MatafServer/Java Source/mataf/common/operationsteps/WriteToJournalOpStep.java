package mataf.common.operationsteps;

import com.ibm.dse.services.jdbc.JournalService;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WriteToJournalOpStep extends AbstractOpStep {
	
	public static final String USER_NAME = "mataf";
	public static final String PASS = "mataf";
	
	
	private JournalService journal;

	/**
	 * @see com.ibm.dse.base.OperationStep#execute()
	 */
	public int execute() throws Exception {
		try {
			preExecute();

			initJournal();

//			Add a record
			journal.addRecord(getContext(), "toJournalFmt");

//			After any journal updating, the changes to the database must be
//			explicitly committed (if autoCommit is set to false).
			journal.commit();
			postExecute();
			
		} finally {
			//When the application is done with the database, the connection
			//and the current journal have to be closed. 
			journal.disconnect();
			journal.close();
		}
		return RC_OK;
	}
	
	/**
	 * @see mataf.common.operationsteps.AbstractOpStep#preExecute()
	 */
	public void preExecute() throws Exception {
//		Initializes the test fields to be stored in the journal. 
		setValueAt("GL_BANK", "12");
		setValueAt("GL_SNIF", "100");
	}
	
	public JournalService initJournal() throws Exception {
		journal = (JournalService) getService("testJournal");
		//Initializes and opens the journal service.
		journal.connect(journal.getDataSourceName(), "mataf", "mataf");
		if (journal.isConnected()) {
			journal.openForEntity("GSKJ_HDR");
			return journal;
		}
		return null;
	}

	/**
	 * @see mataf.common.operationsteps.AbstractOpStep#postExecute()
	 */
	public void postExecute() throws Exception {
	}
	
	/**
	 * @see mataf.common.operationsteps.AbstractOpStep#terminateTheStep()
	 */
	public void terminateTheStep() throws Exception {
		
	}

	/**
	 * Returns the journal.
	 * @return JournalService
	 */
	public JournalService getJournal() {
		return journal;
	}

}
