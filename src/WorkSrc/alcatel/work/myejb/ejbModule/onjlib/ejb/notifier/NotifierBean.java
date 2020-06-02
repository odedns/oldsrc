package onjlib.ejb.notifier;

import java.util.EventObject;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import onjlib.events.EventHandler;
import onjlib.events.EventHandlerIF;
import onjlib.events.EventManager;

/**
 * Bean implementation class for Enterprise Bean: Notifier
 */
public class NotifierBean
	implements
		javax.ejb.MessageDrivenBean,
		javax.jms.MessageListener {
	private javax.ejb.MessageDrivenContext fMessageDrivenCtx;
	/**
	 * getMessageDrivenContext
	 */
	public javax.ejb.MessageDrivenContext getMessageDrivenContext() {
		return fMessageDrivenCtx;
	}
	/**
	 * setMessageDrivenContext
	 */
	public void setMessageDrivenContext(javax.ejb.MessageDrivenContext ctx) {
		fMessageDrivenCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() {
	}
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
		 	EventManager.addHandler("oded.xsm",h);
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
	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
}
