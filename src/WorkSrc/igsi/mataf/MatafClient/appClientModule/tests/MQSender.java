
package tests;
import mataf.utils.ContextFormatter;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Settings;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQMessage;


/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQSender {


	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost:9080/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	public static void main(String[] args) {
		try {
			byte[] id;
			init();
				
		
			// Instantiate an MQMessage for the received message:
			MQMessage msg = new MQMessage();
			// Get the service from the context:
			MQConnection service = (MQConnection) Service.readObject("MQC");
			
			// Establish the connection with MQManager. Open the queues:
			service.establishConnection();
			// Format the message to be sent to the partner:
//			String messageToSend = ((FormatElement) getHostSendFormat()).format(getContext());


			// Context myctx = (Context) Context.readObject("slikaCtx");
			/*
			myctx.setValueAt("UserId","o000131");
			myctx.setValueAt("Name","Oded");
			myctx.setValueAt("Balance","10000");
			myctx.setValueAt("MaxAmount","999999");
*/

			Context ctx = (Context) Context.readObject("overrideCtx");			
			
			IndexedCollection ic = (IndexedCollection) ctx.getElementAt("managersList");
			ic.removeAll();			
			ic = (IndexedCollection) ctx.getElementAt("managersComboList");
			ic.removeAll();
			String chosenMgr = (String) ctx.getValueAt("chosenManagerName");
	
			ctx.setValueAt("mgrUserId","111114");
			
			ctx.setValueAt("tellerUserId","Moshe moshe");
			
			ctx.setValueAt("trxId","106");
			ctx.setValueAt("trxName","kdkdkd");		
			ctx.setValueAt("ctxData","dddd");
			ctx.setValueAt("status",""); 
			ctx.setValueAt("reason",""); 
			ctx.setValueAt("comment",""); 

			String tmp = (String) ctx.getValueAt("mgrUserId");		
			
			msg.correlationId = tmp.getBytes();
			msg.writeInt((int)1);
			String s = ContextFormatter.formatContext(ctx);
//			msg.writeObject(ctx);
			msg.writeUTF(s);
			System.out.println("sending message: " + s);
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


