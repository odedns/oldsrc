/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: OperationExecutionException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown while execution of <code>Operation</code>
 */
public class OperationExecutionException extends FlowerException
{
	public OperationExecutionException(String message)
	{
		super(message);
	}

	public OperationExecutionException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
