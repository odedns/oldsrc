package mataf.operations;

import com.ibm.dse.base.ClientOperation;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.Settings;
import com.ibm.dse.clientserver.CSClientService;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ClientOpTester {

	public static void main(String[] args) {
		ClientOpTester tester = new ClientOpTester();
		try {
			tester.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			System.exit(0);
		}		
	}
	public void run() throws Exception{
		Context.reset();
		Settings.reset("http://127.0.0.1:80/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY);
		
		Context ctx = (Context) Context.readObject("workstationCtx");
		((CSClientService) ctx.getService("CSClient")).establishSession();
		
		ClientOperation startup = (ClientOperation) DSEClientOperation.readObject("startupClientOp");
		startup.execute();
		
		ClientOperation oper = (DSEClientOperation) DSEClientOperation.readObject("testTrxClientOp");
		oper.execute();	
		
//		RefTablesService refTables = (RefTablesClient) ctx.getService("refTablesService");
//		IndexedCollection ic = refTables.getAll("GLST_SNIF");
//		
//		ClientOperation oper = (DSEClientOperation) DSEClientOperation.readObject("JDBCClientOp");
//		oper.execute();

//		ClientOperation oper = (DSEClientOperation) DSEClientOperation.readObject("printerClientOp");
//		oper.execute();
		
//		ClientOperation oper = (DSEClientOperation) DSEClientOperation.readObject("journalClientOp");
//		oper.execute();
	}	
	
}
