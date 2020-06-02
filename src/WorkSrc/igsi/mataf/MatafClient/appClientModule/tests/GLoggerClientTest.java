package tests;

import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEOperation;
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
public class GLoggerClientTest {

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
		} catch(Exception e) {
			e.printStackTrace();
		}
		GLogger.configure("http://localhost/MatafServer/log4j.properties", GLogger.SERVER_LOG);			 
		GLogger.warn("warning msg");
		GLogger.debug("debug msg");
		GLogger.error("error msg");
		GLogger.setTrxId("T410");
		GLogger.info(GLoggerClientTest.class,"GL068","info msg",false);
		GLogger.warn(GLoggerClientTest.class,"GL068","warning msg",false);
		try {
			throw new Exception("some exception");	
			
		} catch(Exception e) {
			GLogger.error(GLoggerClientTest.class,"GL085","error message", e,false);
			GLogger.fatal(GLoggerClientTest.class,"GL085","fatal message", e,false);
		}
	}
}



	