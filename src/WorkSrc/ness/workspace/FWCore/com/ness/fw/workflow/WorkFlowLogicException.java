/*
 * Created on: 26/08/2004
 *
 * Author Amit Mendelson
 * @version $Id: WorkFlowLogicException.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;

/**
 * General Exception that wraps various logic Exceptions thrown in the 
 * com.ness.fw.workflow module (Only Exceptions that are not serious
 * and should not cause the whole system to collapse).
 */
public class WorkFlowLogicException extends WorkFlowException
{

	/**
	 * The {@link MessageContainer} that conatins the application messages 
	 * related to the current Exception. 
	 */
	private MessagesContainer messagesContainer;

	/**
	 * create new WorkFlowLogicException Object
	 * @param error
	 */
	public WorkFlowLogicException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new WorkFlowLogicException Object
	 * @param messageText
	 * @param error
	 */
	public WorkFlowLogicException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new WorkFlowLogicException Object
	 * @param messageText
	 */
	public WorkFlowLogicException(String messageText)
	{
		super(messageText);
	}

	/**
	 * create new WorkFlowLogicException Object
	 * @param messageText
	 * @param message
	 */
	public WorkFlowLogicException(String messageText, Message message)
	{
		super(messageText);
		addMessage(message);
	}

	/**
	 * create new WorkFlowLogicException Object
	 * @param messageText
	 * @param error
	 * @param message
	 */
	public WorkFlowLogicException(
		String messageText,
		Throwable error,
		Message message)
	{
		super(messageText, error);
		addMessage(message);
	}

	/**
	 * create new WorkFlowLogicException Object
	 * @param messageText
	 * @param container
	 */
	public WorkFlowLogicException(
		String messageText,
		MessagesContainer container)
	{
		super(messageText);
		addMessages(container);
	}

	/**
	 * add a new message to the message container.
	 * @param message
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
		if (messagesContainer == null)
			messagesContainer = new MessagesContainer();

		messagesContainer.merge(container);
	}

	/**
	 * @return MessagesContainer
	 */
	public MessagesContainer getMessagesContainer()
	{
		return messagesContainer;
	}

	/**
	 * @return messagesCount
	 */
	public int getMessagesCount()
	{
		if (messagesContainer != null)
			return messagesContainer.getMessagesCount();
		return 0;
	}
}
