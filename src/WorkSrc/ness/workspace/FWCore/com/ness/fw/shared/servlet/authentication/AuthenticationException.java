/*
 * Created on: 13/04/2005
 * Author:  user name
 * @version $Id: AuthenticationException.java,v 1.1 2005/04/14 08:27:20 shay Exp $
 */
package com.ness.fw.shared.servlet.authentication;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * @author srancus
 * 
 * The AuthenticationException is thrown when the user authentication fails.
 */
public class AuthenticationException extends GeneralException 
{
	/**
	 * @param messageText
	 * @param error
	 */
	public AuthenticationException(String messageText, Throwable error) 
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public AuthenticationException(String messageText) 
	{
		super(messageText);
	}

	/**
	 * @param error
	 */
	public AuthenticationException(Throwable error) 
	{
		super(error);
	}
}
