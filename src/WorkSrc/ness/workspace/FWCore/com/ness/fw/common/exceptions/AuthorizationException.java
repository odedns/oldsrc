/*
 * Created on 14/04/2004
 * Author: yifat har-nof
 * @version $Id: AuthorizationException.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */
package com.ness.fw.common.exceptions;

/**
 * Exception thrown when authorization problem occures.
 */
public class AuthorizationException extends GeneralException
{

	/**
	 * @param error
	 */
	public AuthorizationException(Throwable error)
	{
		super(error);
	}

	/**
	 * @param messageText
	 * @param error
	 */
	public AuthorizationException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public AuthorizationException(String messageText)
	{
		super(messageText);
	}

}
