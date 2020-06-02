/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: BackgroundFlowException.java,v 1.1 2005/05/08 12:49:16 yifat Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown by <code>BackgroundFlowExecutionManager</code>
 */
public class BackgroundFlowException extends FlowerException
{
	public BackgroundFlowException(String message)
	{
		super(message);
	}

	public BackgroundFlowException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
