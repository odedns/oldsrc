package com.ibm;

import javax.jms.*;

/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BusinessProcessJMS {
	public QueueConnection 	theConnection 	= null;
	public Queue 			theQueue		= null;
	public Queue 			theReplyQueue	= null;
	
	public BusinessProcessJMS(QueueConnection  aConnection, Queue abpeQueue, Queue aReplyQueue) {
		theConnection 	= aConnection;
		theQueue		= abpeQueue;
		theReplyQueue	= aReplyQueue;
		
	}

	/**
	 * Returns the theConnection.
	 * @return QueueConnection
	 */
	public QueueConnection getTheConnection() {
		return theConnection;
	}

	/**
	 * Returns the theQueue.
	 * @return Queue
	 */
	public Queue getTheQueue() {
		return theQueue;
	}

	/**
	 * Returns the theReplyQueue.
	 * @return Queue
	 */
	public Queue getTheReplyQueue() {
		return theReplyQueue;
	}

}
