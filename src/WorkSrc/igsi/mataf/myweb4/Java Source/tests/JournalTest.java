package tests;

import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.*;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalTest {

	public static void main(String[] args) {
		Context jctx;
	JournalService journal;
	HashtableFormat journalFormat;

	//Sets the toolkit environment.
	try {
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY); 

		//Instantiates the context and the journal service. 
	
		jctx=(Context)Context.readObject("myOperationContext");
		//journal=(JournalService) jctx.getService("myJournal"); 
		journal=(JournalService) Service.readObject("myJournal"); 
		//Initializes and opens the journal service.
		System.out.println("connecting to db ..");
//		journal.loadDriver("COM.ibm.db2.jdbc.app.DB2Driver");
		journal.loadDriver("com.ibm.db2.jcc.DB2Driver");
		journal.connect("jdbc:db2://localhost:50000/WKSLOCAL","odedn","odedn01");
		System.out.println("connected..");
		journal.openForEntity( "UserA"); 
	
		//Initializes the fields to be stored in the journal. 
	
		jctx.setValueAt("accountNumber.branchNumber","1234");
		jctx.setValueAt("accountNumber.agreementNumber",new Integer(102));
		jctx.setValueAt("dueDate",new java.sql.Date(System.currentTimeMillis())); 
	
		//Adds two records (with the same contents).
	
		journal.addRecord(jctx,"journalFormatName");
		journal.addRecord(jctx,"journalFormatName"); 
	
		//Updates the first record with a new date.
	
		jctx.setValueAt("dueDate",new java.sql.Date(System.currentTimeMillis()));
		journal.updateRecord(1, jctx, "journalFormatName"); 
		
		//Retrieves the second record (the last one) and updates the 
		//context with the retrieved information.
		//The dueDate data field value has to be again 22/06/98.
	
		journalFormat=(HashtableFormat)FormatElement.readObject(
		    "journalFormatName");
		journalFormat.unformat(journal.retrieveLastRecord(),jctx);
		java.util.Date myDate;
		myDate=(java.util.Date)jctx.getValueAt("dueDate");
		System.out.println(myDate.toString());
		
		//After any journal updating, the changes to the database must be
		//explicitly committed (if autoCommit is set to false).
		
		journal.commit();
		
		//When the application is done with the database, the connection
		//and the current journal have to be closed. 
		
		journal.disconnect();
		journal.close(); 		
		
		System.out.println("JournalTest done ...");
		
		
	} catch(Exception e) {
		e.printStackTrace();	
	}

	} // main
}
