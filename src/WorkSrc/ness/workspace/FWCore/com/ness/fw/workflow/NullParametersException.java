/*
 * Created on: 07/07/2004
 * Author: Amit Mendelson
 * @version $Id: NullParametersException.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;

/**
 * This exception is thrown in the com.ness.fw.workflow module, 
 * in case a parameter is missing or null (for any method).
 * This exception is a special case of WorkFlowLogicException.
 */
public class NullParametersException extends WorkFlowLogicException
{

	/**
	 * The {@link MessageContainer} that conatins the application messages 
	 * related to the current Exception. 
	 */
	private MessagesContainer messagesContainer;

	/**
	 * create new NullParametersException Object
	 */
	public NullParametersException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new NullParametersException Object
	 */
	public NullParametersException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new NullParametersException Object
	 */
	public NullParametersException(String messageText)
	{
		super(messageText);
	}

	/**
	 * create new NullParametersException Object
	 */
	public NullParametersException(String messageText, Message message)
	{
		super(messageText, message);
		//super(messageText);
		//addMessage(message);
	}

	/**
	 * create new NullParametersException Object
	 */
	public NullParametersException(
		String messageText,
		Throwable error,
		Message message)
	{
		super(messageText, error, message);
		//super(messageText, error);
		//addMessage(message);
	}

	/**
	 * create new NullParametersException Object
	 */
	public NullParametersException(
		String messageText,
		MessagesContainer container)
	{
		super(messageText, container);
		//super(messageText);
		//addMessages(container);
	}

}
