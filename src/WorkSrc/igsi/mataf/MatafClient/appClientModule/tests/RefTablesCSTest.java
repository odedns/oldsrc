package tests;
import mataf.logger.GLogger;
import mataf.services.reftables.RefTablesClient;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;
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
public class RefTablesCSTest {
	
	public static void init() throws Exception 
	{

		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);				
	}
	
	static void runCS() throws Exception
	{
		init();	
		Context ctx = (Context) Context.readObject("startupClientCtx");			
		CSClientService csrv = (CSClientService) ctx.getService("CSClient");
		csrv.establishSession();	
		DSEClientOperation startOp =  (DSEClientOperation) DSEOperation.readObject("startupClientOp");
		startOp.execute();
		DSEClientOperation cop = (DSEClientOperation) DSEOperation.readObject("refTablesClientOp");		
		cop.execute();
		cop.close();
	}
	
	
	static void runSrvOp() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
		DSEServerOperation op =  (DSEServerOperation) DSEOperation.readObject("refTablesServerOp");					
		Context ctx = (Context) Context.readObject("refTablesCtx");			
		ctx.setValueAt("refTablesSearch.tableName","GLST_TESHUVA");
		ctx.setValueAt("refTablesSearch.key","GL_KOD_TESHUVA");
		ctx.setValueAt("refTablesSearch.value","*");
		op.setContext(ctx);		
		op.execute();
		op.close();		
	}
	
	static void runService() throws Exception
	{
		init();	
		
		Context ctx = (Context) Context.readObject("startupClientCtx");			
		CSClientService csrv = (CSClientService) ctx.getService("CSClient");
		csrv.establishSession();			
		DSEClientOperation startOp =  (DSEClientOperation) DSEOperation.readObject("startupClientOp");
		startOp.execute();
		RefTablesClient service = (RefTablesClient) Service.readObject("refTables");
		//RefTablesService service = (RefTablesService) Service.readObject("refTables");
		IndexedCollection ic = service.getAll("GLST_SNIF");
		// IndexedCollection ic = service.getByKey("GLST_SNIF","GL_SNIF","%1%");
		System.out.println("got from getAll: " );
		for(int i=0; i < ic.size(); ++i) {
			KeyedCollection kc = (KeyedCollection) ic.getElementAt(i);
			System.out.println("kc " + kc.toString());
		}	
	}
	
	public static void main(String argv[])
	{
		try {
			init();	
			// GLogger.configure("http://localhost/MatafServer/log4j.properties","MatafClient");
			GLogger.debug("after init");		
		//	runCS();
		//	runSrvOp();			
			runService();
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
