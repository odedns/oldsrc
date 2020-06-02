/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FormatterException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown in several cases while formatting
 */
public class FormatterException extends FlowerException
{
	public FormatterException(String message)
	{
		super(message);
	}

	public FormatterException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
