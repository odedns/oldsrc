/*
 * Created on 14/04/2004
 * Author: yifat har-nof
 * @version $Id: FatalException.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */
package com.ness.fw.common.exceptions;

/**
 * Exception thrown when severe error occures.
 */
public class FatalException extends GeneralException
{

	/**
	 * @param error
	 */
	public FatalException(Throwable error)
	{
		super(error);
	}

	/**
	 * @param messageText
	 * @param error
	 */
	public FatalException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public FatalException(String messageText)
	{
		super(messageText);
	}

}
