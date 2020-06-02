/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ServiceException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown while service execution
 */
public class ServiceException extends FlowerException
{
	public ServiceException(String message)
	{
		super(message);
	}

	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
