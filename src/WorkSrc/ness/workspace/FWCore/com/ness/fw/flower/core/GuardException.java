/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: GuardException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown while checking guard condition
 */
public class GuardException extends FlowerException
{
	public GuardException(String message)
	{
		super(message);
	}

	public GuardException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
