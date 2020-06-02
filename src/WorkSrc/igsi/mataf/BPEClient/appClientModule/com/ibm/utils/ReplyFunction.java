package com.ibm.utils;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.wsif.WSIFException;
import org.apache.wsif.WSIFMessage;

import com.ibm.bpe.api.ReplyContext;
import com.ibm.bpe.api.SendReplyErrorException;
/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ReplyFunction implements ReplyContext {
	/**
	 * @see com.ibm.bpe.api.ReplyContext#replyProcessResult(int, String, Object)
	 */
	public void replyProcessResult(int processState, java.lang.String processInstanceName, java.lang.Object resultMessage) throws SendReplyErrorException {
		String theResult = "replyProcessResult, State: "+Utility.getProcessState(processState)+" Name: "+processInstanceName+" Result message";
		System.out.println(theResult);
		String[] theResults=null;
		try {
			theResults = WSDLHelper.convertMessage((WSIFMessage)resultMessage);
		} catch (WSIFException e) {
			e.printStackTrace();
		}
		try {
			System.out.println("Sending result");
			Context initialContext = new InitialContext();
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext.lookup("jms/BPECF");
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
			Queue replyToQueue = (Queue) initialContext.lookup("jms/receiverQ");
			
			QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueSender queueSender = queueSession.createSender(replyToQueue);

			// Start sending messages
			queueConnection.start();

			// Create message
			ObjectMessage message = queueSession.createObjectMessage();
			message.setObject(theResults);
				// Send message			
			queueSender.send(message);
			System.out.println("Result sent");			
			queueConnection.close();
			} catch (Exception e) {
				e.printStackTrace();
		}			
		
	}
	/**
	 * @see com.ibm.bpe.api.ReplyContext#replyException(int, String, Exception)
	 */
	public void replyException(int processState, java.lang.String processInstanceName, java.lang.Exception exception) throws SendReplyErrorException {
		System.out.println("replyException, State: "+Utility.getProcessState(processState)+" Name: "+processInstanceName+" Exception");				
		exception.printStackTrace();
	}
}
