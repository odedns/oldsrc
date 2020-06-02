/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationException.java,v 1.2 2005/03/29 14:57:08 yifat Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown when performing validation
 */
public class ValidationException extends FlowerException
{
	private boolean severeErrorFound;
	
	public ValidationException()
	{
		this (false);
	}
	
	public ValidationException(boolean severeErrorFound)
	{
		super("Validation exception was thrown. See messages for details");
		this.severeErrorFound = severeErrorFound;
	}

	/**
	 * @return
	 */
	public boolean isSevereErrorFound()
	{
		return severeErrorFound;
	}

}
