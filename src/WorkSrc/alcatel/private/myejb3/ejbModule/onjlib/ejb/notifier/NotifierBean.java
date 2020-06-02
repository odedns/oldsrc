package onjlib.ejb.notifier;

import java.util.EventObject;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import onjlib.events.EventHandler;
import onjlib.events.EventHandlerIF;
import onjlib.events.EventManager;

/**
 * Bean implementation class for Enterprise Bean: Notifier
 */

@MessageDriven(activationConfig =
{
  @ActivationConfigProperty(propertyName="destinationType",
    propertyValue="javax.jms.Queue"),
  @ActivationConfigProperty(propertyName="destination",
    propertyValue="queue/A")
})
public class NotifierBean
	implements	javax.jms.MessageListener {
	
	/**
	 * onMessage
	 * @throws JMSException
	 */
	public void onMessage(javax.jms.Message msg)  {

		 ObjectMessage objMsg = (ObjectMessage) msg;
		 try {
		 	/*
		 	 * initialize some test handler.
		 	 */
		 	EventHandler h = new EventHandler();
		 	EventManager.addHandler("oded",h);
		 	/*
		 	 * now this is the generic code.
		 	 */
			String dest = (String) objMsg.getStringProperty("destination");
			EventHandlerIF handler = EventManager.findHandler(dest);
			if(null == handler) {
				System.out.println("no handler found for: " + dest);
				return;
			}
			Object o = objMsg.getObject();
			EventObject event = new EventObject(o);
			handler.handleEvent(event);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 	      
		
	}
	
}
