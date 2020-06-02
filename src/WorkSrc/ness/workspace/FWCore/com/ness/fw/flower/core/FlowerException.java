/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowerException.java,v 1.2 2005/04/12 13:44:33 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * the common ansestor for all exceptions used in the Flower code.
 */
public class FlowerException extends GeneralException
{	
	public FlowerException(String message, Throwable cause)
	{
		super(message, cause);
		
	}

	public FlowerException(String s) 
	{
		super(s);
	}
	
//	public Throwable getCause()
//	{
//		return error;
//	}

}
