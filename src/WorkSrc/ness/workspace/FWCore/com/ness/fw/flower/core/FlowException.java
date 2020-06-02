/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Used to be thrown by flow
 */
public class FlowException extends FlowerException
{
	public FlowException(String message)
	{
		super(message);
	}

	public FlowException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
