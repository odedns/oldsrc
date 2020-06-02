/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Exception to be thrown while performing context operation
 */
public class ContextException extends FlowerException
{
	/**
	 * create new ContextException Object
	 */
	public ContextException(String message)
	{
		super(message);
	}

	/**
	 * create new ContextException Object
	 */
	public ContextException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
