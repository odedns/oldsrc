package tests;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.services.cics.CICSConnection;
import com.ibm.dse.services.comms.CCMessage;
import com.ibm.dse.services.comms.CommonCommunicationsService;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CicsTestServerOp extends DSEServerOperation {
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception {
		// Get the service instance from the operation context. 
		CICSConnection cics = (CICSConnection) getService("testCICSService");
		//Check if the session is established.
		if (cics.getStatus().equals(CommonCommunicationsService.CLOSED)) {
			try {
				// Set the gateway name and port if they have not been included 
				// in the service definition.
				//cics.setGatewayName(aGatewayName);
				//cics.setGatewayPort(aPort);

				// Establish the session
				cics.ccOpen();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Set the transaction attributes if they have not been included in the 
		// service definition.
		cics.setUserId("CICSUXT");
		cics.setPassword("383272");
		cics.setProgramName("EC15");
		cics.setTransactionId("EC15");
		cics.setSynchronousMode(true);
		cics.initCommArea(19);
		String str ="ddd";
		CCMessage message = cics.ccSendReceive(str, 60000);
		String dataReceive = message.getDataReceived();		

	}

}
