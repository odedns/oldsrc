package tests;
import mataf.services.JdbcConnectionService;
import mataf.services.electronicjournal.JournalWrapSrvOp;

import com.ibm.dse.base.*;
import java.sql.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalWrapTest {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	
	
	public static void main(String[] args) {
		try {		
			init();
			JournalWrapSrvOp op = (JournalWrapSrvOp) DSEServerOperation.readObject("journalWrapSrvOp");		
			IndexedCollection ic = (IndexedCollection) op.getElementAt("journalEntityList");
			for(int i=0; i < 3; ++i) {
				KeyedCollection kc = (KeyedCollection) DataElement.readObject("journalEntity");
				kc.setValueAt("name","entity-" + i);
				ic.addElement(kc);				
			}
			op.addElement(ic);
			op.execute();
			System.out.println("Journal test " );
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}
}

