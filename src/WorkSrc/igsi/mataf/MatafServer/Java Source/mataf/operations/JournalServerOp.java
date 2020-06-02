package mataf.operations;

import java.io.IOException;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInternalErrorException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.HashtableFormat;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.DSESQLException;
import com.ibm.dse.services.jdbc.JournalService;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalServerOp extends DSEServerOperation {
	private JournalService journal;
	private HashtableFormat journalFormat;

	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception {
		
//		try {
//			journal = (JournalService) getService("testJournal");
//			
//			//Initializes and opens the journal service.
//			journal.connect("jdbc/matafdb", "mataf", "mataf");
//			journal.openForEntity("GSKJ_HDR");
//			
//			//Initializes the fields to be stored in the journal. 
//			
//			setValueAt("GL_BANK", "12");
//			setValueAt("GL_SNIF", "100");
//			//Adds two records (with the same contents).
//			
//				journal.addRecord(getContext(), "toJournalFmt");
//				journal.addRecord(getContext(), "toJournalFmt");
//			
//			
//			//Updates the first record with a new date.
//			setValueAt("GL_BANK", "15");
//			journal.updateRecord(1, getContext(), "toJournalFmt");
//			
//			//Retrieves the second record (the last one) and updates the 
//			//context with the retrieved information.
//			//The dueDate data field value has to be again 22/06/98.
//			
//			journalFormat = (HashtableFormat) FormatElement.readObject("toJournalFmt");
//			journalFormat.unformat(journal.retrieveLastRecord(), getContext());
//			
//			//After any journal updating, the changes to the database must be
//			//explicitly committed (if autoCommit is set to false).
//			journal.commit();
//			
////			Vector v = journal.retrieveRecords("select * from GSKJ_HDR");		
//			
//		} finally {
//			//When the application is done with the database, the connection
//			//and the current journal have to be closed. 
//			journal.disconnect();
//			journal.close();
//		}
		
		
	}

}
