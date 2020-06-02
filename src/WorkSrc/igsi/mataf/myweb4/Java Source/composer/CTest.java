package composer;

import com.ibm.dse.base.*;
import com.ibm.dse.clientserver.*;
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
		Settings.reset("http://localhost/myweb4/dse/client/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
		
		
	}
	
	static void runCS() throws Exception
	{
		init();	
		Context ctx = (Context) Context.readObject("startupClientCtx");	
		
		CSClientService csrv = (CSClientService) Context.getCSClient();
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
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
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
