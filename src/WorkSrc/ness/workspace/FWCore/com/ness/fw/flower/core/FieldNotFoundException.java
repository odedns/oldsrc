/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FieldNotFoundException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown when attempt to set or get field that is not defined in the context
 */
public class FieldNotFoundException extends ContextException
{
	public FieldNotFoundException(String message)
	{
		super(message);
	}

	public FieldNotFoundException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
