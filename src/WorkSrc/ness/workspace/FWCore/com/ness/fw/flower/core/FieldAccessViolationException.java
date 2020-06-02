/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FieldAccessViolationException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown when attempting to change private field from child context.
 */
public class FieldAccessViolationException extends ContextException
{
	public FieldAccessViolationException(String message)
	{
		super(message);
	}

	public FieldAccessViolationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
