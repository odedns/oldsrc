/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationProcessException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * com.ness.fw.flower.core
 * ${CLASS_NAME}
 */
public class ValidationProcessException extends FlowerException
{
	public ValidationProcessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ValidationProcessException(String s)
	{
		super(s);
	}
}
