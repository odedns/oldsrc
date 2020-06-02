package hoshen.utils.listener;

import javax.jms.JMSException;


import org.apache.log4j.Logger;

/**
 * Bean implementation class for Enterprise Bean: HoshenListener
 */
public class HoshenListenerBean
	implements
		javax.ejb.MessageDrivenBean,
		javax.jms.MessageListener {
	private javax.ejb.MessageDrivenContext fMessageDrivenCtx;
	private static Logger log = Logger.getLogger(HoshenListenerBean.class);
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
	 * Try to get the appropriate message handler for the 
	 * specific message type and activate it.
	 */
	public void onMessage(javax.jms.Message msg) {
		log.info("> HoshenListenerBean.onMessage()");
	
		String type = null;
		
		try {
			log.debug("got message: " + msg.getJMSMessageID());
			type = msg.getJMSType();
		} catch(JMSException je) {
			je.printStackTrace();
			log.error("Error getting message type: " + je);
			type = null;
		}
		MessageHandlerManager handlerManager =MessageHandlerManager.getInstance();
		MessageHandler handler = handlerManager.findMessageHandler(type);
		if(handler != null) {
			handler.handleMessage(msg);
		} else {
			log.error("No appropriate message handler found");
		}
	}
	
	
	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
}
