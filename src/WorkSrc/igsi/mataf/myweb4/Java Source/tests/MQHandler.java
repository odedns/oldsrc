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


public class MQHandler extends ConnectionHandler {

	public Handler message(DSEEventObject e)
	{
		System.out.println("got event : " + e.toString());
		Hashtable ht = e.getParameters();
		System.out.println("got params : " + ht.toString());		
		try {
			MQMessage mqs = (MQMessage) ht.get("message");
			System.out.println("message = " + mqs.readLine());
			
		} catch(Exception ex) {
			ex.printStackTrace();	
		}
		return(this);	
	}



	
	
	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}

	public static void main(String[] args) 
	{
		
		byte[] id;
		try {
			init();						
			// Instantiate an MQMessage for the received message:
			MQMessage msg = new MQMessage();
			Context ctx = (Context) Context.readObject("mqSrvCtx");
			// Get the service from the context:
			MQConnection service = (MQConnection) ctx.getService("mqconn");			
			MQHandler handler = new MQHandler();						
			service.addHandler(handler,"message");			
			//EventManager.
			handler.handleEvent("allEvents","mqconn",ctx);
			/*
			MyListener mlistener = new MyListener();
			service.addQueueListener(mlistener);
			*/
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
