/*
 * Author: yifat har-nof
 * @version $Id: BusinessLogicException.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */

package com.ness.fw.common.exceptions;

import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;

/**
 * Exception thrown from the Business Logic layer.
 */
public class BusinessLogicException extends GeneralException
{

	/**
	 * The {@link MessageContainer} that conatines the application messages 
	 * related to the current Exception. 
	 */
	private MessagesContainer messagesContainer;

	/**
	 * create new PersistenceException Object
	 */
	public BusinessLogicException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new PersistenceException Object
	 */
	public BusinessLogicException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new PersistenceException Object
	 */
	public BusinessLogicException(String messageText)
	{
		super(messageText);
	}

	/**
	 * create new BusinessLogicException Object
	 */
	public BusinessLogicException (String messageText, Message message) 
	{
		super(messageText);
		addMessage(message);
	}

	/**
	 * create new BusinessLogicException Object
	 */
	public BusinessLogicException (String messageText, Throwable error, Message message) 
	{
		super(messageText, error);
		addMessage(message);
	}

	/**
	 * create new PersistenceException Object
	 */
	public BusinessLogicException()
	{
		super("");
	}

	/**
	 * create new BusinessLogicException Object
	 */
	public BusinessLogicException (String messageText, MessagesContainer container) 
	{
		super(messageText);
		addMessages(container);
	}


	/**
	 * add a new message to the message container. 
	 */
	public void addMessage(Message message)
	{
		if (messagesContainer == null)
			messagesContainer = new MessagesContainer();

		messagesContainer.addMessage(message);
	}

	/**
	 * add all the messages from the container to the current one. 
	 */
	public void addMessages(MessagesContainer container)
	{
		if(container != null)
		{
			if (messagesContainer == null)
				messagesContainer = new MessagesContainer();

			messagesContainer.merge(container);			
		}
	}


	/**
	 * @return
	 */
	public MessagesContainer getMessagesContainer()
	{		
		return messagesContainer;
	}
	
	/**
	 * @return
	 */
	public int getMessagesCount()
	{
		if(messagesContainer != null)
			return messagesContainer.getMessagesCount();
		return 0;
	}
	

}
