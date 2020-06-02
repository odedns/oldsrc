/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: IncompatibleFieldTypeException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown when attempt to set field with data that is incompatible with the field type definition
 */
public class IncompatibleFieldTypeException extends ContextException
{
	public IncompatibleFieldTypeException(String message)
	{
		super(message);
	}

	public IncompatibleFieldTypeException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
