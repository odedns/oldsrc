/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ParametersMissingException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown while creating <code>Event</code> if one of mandatory parameters is missing
 */
public class ParametersMissingException extends FlowerException
{
	public ParametersMissingException(String message)
	{
		super(message);
	}

	public ParametersMissingException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
