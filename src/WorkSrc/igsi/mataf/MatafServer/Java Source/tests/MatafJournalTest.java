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
public class MatafJournalTest {

	public static void main(String[] args) {
		Context jctx;
	JournalService journal;
	HashtableFormat journalFormat;

	//Sets the toolkit environment.
	try {
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY); 

		//Instantiates the context and the journal service. 
	
		jctx=(Context)Context.readObject("slikaCtx");
		
		//journal=(JournalService) jctx.getService("myJournal"); 
		journal=(JournalService) jctx.getService("journalService"); 
		//Initializes and opens the journal service.
		System.out.println("connecting to db ..");
		journal.connect(journal.getDataSourceName(), "mataf", "mataf");
		System.out.println("connected..");
		journal.openForEntity( "SLIKA_JOURNAL"); 
	
		//Initializes the fields to be stored in the journal. 
		/*
		jctx.setValueAt("GL_BANK", "12");
		jctx.setValueAt("GL_SNIF", "100");
		*/
		Hashtable ht = new Hashtable();
		Long l = new Long(System.currentTimeMillis());
		ht.put("GL_KEY","112");
		ht.put("GL_BANK", "12");
		ht.put("GL_SNIF", "100");
		ht.put("CONTEXT", "SOME CTX DATA");
	
		//Adds two records (with the same contents).
	
//		journal.addRecord(jctx,"toJournalFmt");
		journal.addRecord(ht);
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
