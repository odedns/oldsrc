package tests;

import com.ibm.dse.base.*;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.*;
import com.ibm.mqservices.Trace;


/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQReceiver {


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

			Trace.isOn = false;						
		
			// Instantiate an MQMessage for the received message:
			MQMessage msg = null;
			// Get the service from the context:
			MQConnection service = (MQConnection) Service.readObject("MQC");
			
			// Establish the connection with MQManager. Open the queues:
			service.establishConnection();
	
			String user = "111111";				
			msg =(MQMessage)service.receive(user.getBytes(),4000);				
			int i = msg.readInt();
			System.out.println("got int : " + i);
			Context ctx = (Context) msg.readObject();
			System.out.println("msg received : " + ctx.toString());
			System.out.println("TellerId = " + ctx.getValueAt("tellerUserId"));
			Context trxCtx = (Context) ctx.getValueAt("trxCtx");
			System.out.println("trxCtx : " + trxCtx.toString());
			System.out.println("trxId = " + trxCtx.getValueAt("trxId"));
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

