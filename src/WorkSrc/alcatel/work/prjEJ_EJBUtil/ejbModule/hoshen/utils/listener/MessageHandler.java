/* Created on 13/03/2006 */
package hoshen.utils.listener;

import javax.jms.Message;

/**
 * This is the message handler interface. All message handlers should 
 * implement this interface.
 * The message handler will be dynamically invoked by the HoshenListener MDB, 
 * according to the JMS message type received. Each handler will be able to 
 * handle a different kind of a message.
 * @author Oded Nissan
 */
public interface MessageHandler
{

	/**
	 * handle the message received by the HoshenListener MDB.
	 * @param msg the JMS message received.
	 */
	public void handleMessage(Message msg);
		
}
