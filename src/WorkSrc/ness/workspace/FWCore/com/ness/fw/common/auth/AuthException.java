/*
 * Created on: 23/12/2004
 * Author: yifat har-nof
 * @version $Id: AuthException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.auth;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * 
 */
public class AuthException extends GeneralException
{

	/**
	 * @param error
	 */
	public AuthException(Throwable error)
	{
		super(error);
	}

	/**
	 * @param messageText
	 * @param error
	 */
	public AuthException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public AuthException(String messageText)
	{
		super(messageText);
	}

}
