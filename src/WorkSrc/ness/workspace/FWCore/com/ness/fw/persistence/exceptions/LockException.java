/*
 * Author: yifat har-nof
 * @version $Id: LockException.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.persistence.exceptions;

import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Exception thrown from the Business Logic layer and represent a locking error.
 */
public class LockException extends PersistenceException
{

	/**
	 * create new LockException Object
	 */
	public LockException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new LockException Object
	 */
	public LockException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new LockException Object
	 */
	public LockException(String messageText)
	{
		super(messageText);
	}

	/**
	 * create new LockException Object
	 */
	public LockException(String messageText, Message message)
	{
		super(messageText, message);
	}

	/**
	 * create new LockException Object
	 */
	public LockException(String messageText, Throwable error, Message message)
	{
		super(messageText, error, message);
	}

	/**
	 * create new LockException Object
	 */
	public LockException (String messageText, MessagesContainer container) 
	{
		super(messageText);
		addMessages(container);
	}
	

}