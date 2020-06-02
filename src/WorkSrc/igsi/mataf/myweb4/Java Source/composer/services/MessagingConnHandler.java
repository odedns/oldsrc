package composer.services;

import com.ibm.dse.base.*;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.MQMessage;
import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MessagingConnHandler extends ConnectionHandler {
	public Handler message(DSEEventObject e)
	{
		Hashtable ht = e.getParameters();
		MQMessage msg = (MQMessage) ht.get("message");
		try {						
			Object o = msg.readLine();		
			System.out.println("ConnHandler message: " + o.toString());
		} catch(Exception ie) {
			ie.printStackTrace();
		}
		return(this);	
	}

}
