/*
 * Author: yifat har-nof
 * @version $Id: RuleException.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */
package com.ness.fw.common.exceptions;

/**
 * Exception thrown from the Rule Engine.
 */
public class RuleException extends GeneralException
{

	/**
	 * create new RuleException Object
	 */
	public RuleException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new RuleException Object
	 */
	public RuleException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new RuleException Object
	 */
	public RuleException(String messageText)
	{
		super(messageText);
	}

}
