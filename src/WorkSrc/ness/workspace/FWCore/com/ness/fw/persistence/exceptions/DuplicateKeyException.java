/*
 * Author: yifat har-nof
 * @version $Id: DuplicateKeyException.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.persistence.exceptions;

import com.ness.fw.util.Message;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Exception thrown from the Business Logic layer and represent a duplicate key insertion error.
 */
public class DuplicateKeyException extends PersistenceException
{

	/**
	 * create new DuplicateKeyException Object
	 */
	public DuplicateKeyException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new DuplicateKeyException Object
	 */
	public DuplicateKeyException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new DuplicateKeyException Object
	 */
	public DuplicateKeyException(String messageText)
	{
		super(messageText);
	}

	/**
	 * create new DuplicateKeyException Object
	 */
	public DuplicateKeyException(String messageText, Message message)
	{
		super(messageText, message);
	}

	/**
	 * create new DuplicateKeyException Object
	 */
	public DuplicateKeyException(String messageText, Throwable error, Message message)
	{
		super(messageText, error, message);
	}

}