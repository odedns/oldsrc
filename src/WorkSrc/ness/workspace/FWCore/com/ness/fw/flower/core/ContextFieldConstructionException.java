/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextFieldConstructionException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.util.*;

/**
 * Thrown when context field can not be constructed
 */
public class ContextFieldConstructionException extends ContextException
{
	/**
	 * The {@link MessagesContainer} that contains the messages for the user 
	 * that was added to the Exception.
	 */
	private MessagesContainer messagesContainer = null;

	/**
	 * create new ContextFieldConstructionException Object
	 */
	public ContextFieldConstructionException(String messageStr)
	{
		super(messageStr);
	}

	/**
	 * create new ContextFieldConstructionException Object
	 */
	public ContextFieldConstructionException(String messageStr, Throwable cause)
	{
		super(messageStr, cause);
	}

	/**
	 * create new ContextFieldConstructionException Object
	 */
	public ContextFieldConstructionException(String messageStr, Throwable cause, Message message)
	{
		super(messageStr, cause);
		addMessage(message);
	}

	/**
	 * create new ContextFieldConstructionException Object
	 */
	public ContextFieldConstructionException(String messageStr, Message message)
	{
		super(messageStr);
		addMessage(message);
	}

	/**
	 * create new ContextFieldConstructionException Object
	 */
	public ContextFieldConstructionException(Message message)
	{
		super("");
		addMessage(message);
	}

	/**
	 * create new ContextFieldConstructionException Object
	 */
	public void addMessage(Message message)
	{
		if (messagesContainer == null)
		{
			this.messagesContainer = new MessagesContainer();
		}

		messagesContainer.addMessage(message);
	}

	/**
	 * Returns the MessagesContainer that contains the messages for the user 
	 * that was added to the Exception.
	 * @return MessagesContainer
	 */
	public MessagesContainer getMessagesContainer()
	{
		return messagesContainer;
	}
}
