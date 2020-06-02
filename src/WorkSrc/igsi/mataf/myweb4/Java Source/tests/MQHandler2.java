package tests;

import com.ibm.dse.services.mq.*;
import com.ibm.dse.base.*;
import com.ibm.mq.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */


public class MQHandler2 extends ConnectionHandler {

	
	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}

	public static void main(String[] args) 
	{
		
		byte []id;
		try {
			init();						
			// Instantiate an MQMessage for the received message:
			MQMessage msg = new MQMessage();
			// Get the service from the context:
			MQConnection service = (MQConnection) Service.readObject("MQC");			
			// Establish the connection with MQManager. Open the queues:
			service.establishConnection();
			// Format the message to be sent to the partner:
			String messageToSend = "some msg";
			// Send the message to the partner, and store the correlationId to identify the response:
			//Thread th = new Thread(service);
			//th.start();
			msg.writeString(messageToSend);
			id = service.send(msg);
			System.out.println("msg sent ...");			
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
