package tests;
import com.ibm.dse.base.*;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.*;


/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQMFSender {


	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	public static void main(String[] args) {
		try {
			byte[] id;
			init();
				
		
			// Instantiate an MQMessage for the received message:
			MQMessage msg = new MQMessage();
			// Get the service from the context:
			MQConnection service = (MQConnection) Service.readObject("MQ_OR");
			
			// Establish the connection with MQManager. Open the queues:
			service.establishConnection();

						
			msg.writeInt((int)1);
			msg.writeUTF("some message");
			System.out.println("sending message: ");
			service.send(msg);
			System.out.println("message sent ...");
			// Close the connection: 
			service.closeConnection();
				
		}catch(Exception e) {
			e.printStackTrace();	
		}
		System.exit(1);
	}
	
	
	static void dumpCorrId(byte b[])
	{
		for(int i=0; i<b.length; ++i) {
			System.out.print(b[i]);	
		}	
		System.out.println('\n');
	}
}



