package tests;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
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
public class CTest {

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
		DSEClientOperation cop = (DSEClientOperation) DSEOperation.readObject("clientOp");
		//DSEClientOperation cop = (DSEClientOperation) DSEOperation.readObject("genericClientOp");
		
		cop.execute();
		cop.close();
		/*
		cop = (DSEClientOperation) DSEOperation.readObject("clientOp2");
		cop.execute();
		cop.close();
		*/

		
	}
	static void runSrvOp() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
		DSEServerOperation op =  (DSEServerOperation) DSEOperation.readObject("serverOp");	
		op.execute();
		op.close();
		
	}
	
	public static void main(String argv[])
	{
		try {
			init();
			DataField	df = (DataField) DataElement.readObject("UserId");
			df.setValue("foo");
			System.out.println("UserId = " + df.getValue());
			
			runCS();
	//		runSrvOp();			
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
