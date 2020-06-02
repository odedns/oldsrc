package tests;

import java.io.BufferedInputStream;

import com.ibm.dse.base.ClientOperation;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEOperation;
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
		try {
			ClientOpTester tester = new ClientOpTester();
			tester.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void init() throws Exception {
//		BufferedInputStream DSE_INI_stream = new BufferedInputStream(this.getClass().getResourceAsStream("C:/Program Files/IBM/WebSphere Studio/workspace/MatafServer/Web Content/dse/dse.ini"), 200000);
		Settings.reset("C:/Program Files/IBM/WebSphere Studio/workspace/MatafServer/Web Content/dse/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY);

/*		Context context = (Context) Context.readObject("workstationCtx");
		((CSClientService) context.getService("CSClient")).establishSession();
		ClientOperation startupClientOp = (ClientOperation) DSEOperation.readObject("startupClientOp");
		startupClientOp.execute();*/

		ClientOperation testClientOp = (ClientOperation) DSEOperation.readObject("browserClientOp");
		
		testClientOp.execute();
	}

}
