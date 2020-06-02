/*
 * Author: yifat har-nof
 * @version $Id: ResourceException.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */

package com.ness.fw.common.exceptions;

/**
 * Exception thrown from the Rule Engine.
 */
public class ResourceException extends GeneralException
{

	/**
	 * create new RuleException Object
	 */
	public ResourceException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new RuleException Object
	 */
	public ResourceException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new RuleException Object
	 */
	public ResourceException(String messageText)
	{
		super(messageText);
	}

}
