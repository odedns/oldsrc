/*
 * Author: yifat har-nof
 * @version $Id: ObjectNotFoundException.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.persistence.exceptions;

import com.ness.fw.util.Message;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Exception thrown from the Business Logic layer 
 * and indicates that the object not found in the DB.
 */
public class ObjectNotFoundException extends PersistenceException
{
	/**
	 * create new ObjectNotFoundException Object
	 */
	public ObjectNotFoundException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new ObjectNotFoundException Object
	 */
	public ObjectNotFoundException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new ObjectNotFoundException Object
	 */
	public ObjectNotFoundException(String messageText, Message message)
	{
		super(messageText, message);
	}

	/**
	 * create new ObjectNotFoundException Object
	 */
	public ObjectNotFoundException(
		String messageText,
		Throwable error,
		Message message)
	{
		super(messageText, error, message);
	}

	/**
	 * create new ObjectNotFoundException Object
	 */
	public ObjectNotFoundException(String messageText)
	{
		super(messageText);
	}

}
