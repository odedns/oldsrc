/* Created on 13/03/2006 */
package hoshen.utils.listener;

import javax.jms.Message;

import org.apache.log4j.Logger;

/**
 * 
 * @author Odedn
 */
public class TestHandler implements MessageHandler
{
	private static Logger log = Logger.getLogger(TestHandler.class);
	/* (non-Javadoc)
	 * @see hoshen.utils.listener.MessageHandler#handleMessage(javax.jms.Message)
	 */
	public void handleMessage(Message msg)
	{
		// TODO Auto-generated method stub
		log.info("> TestHandler.handleMessage()");
		log.debug("msg: " + msg.toString());

	}

}
