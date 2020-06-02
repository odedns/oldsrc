/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowAuthorizationException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.exceptions.AuthorizationException;

/**
 * Thrown by <code>Flow</code> that is not authorized to start.
 */
public class FlowAuthorizationException extends AuthorizationException
{
	/**
	 * create new FlowAuthorizationException object.
	 * @param message
	 */
	public FlowAuthorizationException(String message)
	{
		super(message);
	}
	
	/**
	 * create new FlowAuthorizationException object.
	 * @param message
	 * @param cause
	 */
	public FlowAuthorizationException(String message, Throwable cause)
	{
		super(message, cause);
		
	}
	
}
