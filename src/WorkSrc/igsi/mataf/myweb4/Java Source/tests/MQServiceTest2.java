package tests;

import com.ibm.dse.base.*;
import com.ibm.mq.*;
import composer.services.*;
import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQServiceTest2 {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	public static void main(String[] args) {
		
		try {
	
			init();				
			
			MQServiceHandler handler = new MQServiceHandler();
			Context ctx = (Context) Context.readObject("mqServiceCtx");
			MQService service = (MQService) ctx.getService("mqsrv");
			service.addHandler(handler,"message");			
			handler.handleEvent("allEvents","mqsrv",ctx);

	
			MQMessage msg = null;	
			String user = "oded2";	
			service.setUser(user);
			
			
	/*
			for(int i=0; i < 5; ++i) {
				msg =(MQMessage)service.receive();				
				System.out.println("msg received : " + msg.readLine());
			}		
			*/
			// service.terminate();
			
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}
}
