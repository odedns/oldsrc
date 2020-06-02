package tests;
import mataf.services.electronicjournal.JournalWrapClientOp;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Settings;
import com.ibm.dse.clientserver.CSClientService;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalCSTest {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	
	
	public static void main(String[] args) {
		try {		
			init();
			Context ctx = (Context) Context.readObject("startupClientCtx");			
			CSClientService csrv = (CSClientService) ctx.getService("CSClient");
			csrv.establishSession();				
			DSEClientOperation startOp =  (DSEClientOperation) DSEOperation.readObject("startupClientOp");
			startOp.execute();			
			JournalWrapClientOp op = (JournalWrapClientOp) DSEServerOperation.readObject("journalWrapClientOp");		
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

