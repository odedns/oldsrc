/*
 * Author: yifat har-nof
 * @version $Id: MissingSavePointException.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.persistence.exceptions;

import com.ness.fw.util.Message;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Exception thrown when a SavePoint is missing in the DB.
 */
public class MissingSavePointException extends PersistenceException
{

	/**
	 * create new MissingSavePointException Object
	 */
	public MissingSavePointException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new MissingSavePointException Object
	 */
	public MissingSavePointException(String messageText)
	{
		super(messageText);
	}

	/**
	 * create new MissingSavePointException Object
	 */
	public MissingSavePointException(String messageText, Message message)
	{
		super(messageText, message);
	}

	/**
	 * create new MissingSavePointException Object
	 */
	public MissingSavePointException(String messageText, Throwable error, Message message)
	{
		super(messageText, error, message);
	}

}