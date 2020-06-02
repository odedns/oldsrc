package tests;

import mataf.utils.ContextFormatter;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEHandler;
import com.ibm.dse.base.Handler;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Settings;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;


/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
class MyHandler extends DSEHandler {

	/**
	 * Constructor for MyHandler.
	 */
	MyHandler() {
		super();
		System.out.println("MyHandler()");
	}
	
	
	public Handler dispatchEvent(DSEEventObject event)
	{
		System.out.println("got event: " + event.toString());
		Hashtable ht = event.getParameters();
		MQException mqe = (MQException) ht.get("exception");
		System.out.println("mQException = " + mqe.toString());
		MQReceiver.mqErr = true;
		return(null);	
	}
}

public class MQReceiver {

	static boolean mqErr = false;
	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	
	
	public static void main(String[] args) {
		try {
			byte[] id;
			init();

						
			MyHandler handler = new MyHandler();
			// Instantiate an MQMessage for the received message:
			MQMessage msg = null;
			// Get the service from the context:
			MQConnection service = (MQConnection) Service.readObject("MQC");
			service.addHandler(handler,"errorReceived");
			// Establish the connection with MQManager. Open the queues:
			service.establishConnection();
			String user = "111114";				
			msg =(MQMessage)service.receive(user.getBytes(),4000);				
			if(mqErr) {
				System.out.println("got error ...closing");
				service.closeConnection();
				System.exit(1);	
			}
			System.out.println("feedBack = " + msg.feedback);
			System.out.println("length = " + msg.getDataLength());
			int i = msg.readInt();
			System.out.println("got int : " + i);
	//		Context ctx = (Context) msg.readObject();
			String s = msg.readUTF();
			System.out.println("got s = " + s);
			Context ctx = ContextFormatter.unformatContext(s,"overrideCtx");
			System.out.println("msg received : " + ctx.toString());
			System.out.println("TellerUserId = " + ctx.getValueAt("tellerUserId"));
			Context trxCtx = (Context) ctx.getValueAt("trxCtx");
			System.out.println("trxCtx : " + trxCtx.toString());
			System.out.println("UserId = " + trxCtx.getValueAt("UserId"));
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

