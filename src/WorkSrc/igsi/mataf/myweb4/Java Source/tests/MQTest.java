package tests;
import com.ibm.dse.base.*;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQTest {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	static void sendMsg(MQConnection service, String user, String s)
		throws Exception
	{
		MQMessage msg = new MQMessage();
		msg.correlationId = user.getBytes();
		msg.writeString(s);
		service.send(msg);
	}
	
	
	public static void main(String[] args) {
		try {
			byte[] id;
			init();

						
		
			// Instantiate an MQMessage for the received message:
			MQMessage msg = null;
			// Get the service from the context:
			MQConnection service = (MQConnection) Service.readObject("MQC");
			
			// Establish the connection with MQManager. Open the queues:
			service.establishConnection();
			// Format the message to be sent to the partner:
//			String messageToSend = ((FormatElement) getHostSendFormat()).format(getContext());
	
			String user = "oded";	
			
			for(int i=0; i < 5; ++i) {
				sendMsg(service,user,"message-" + user + "-" + i);				
			}	
			String user2 = "poo";		
			sendMsg(service,user2,"message-" + user2 + "-1");				
	
			for(int i=0; i < 6; ++i) {
				msg =(MQMessage)service.receive(user.getBytes());				
				System.out.println("msg received : " + msg.readLine());
			}		
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
