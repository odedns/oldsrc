/*
 * Created on: 25/6/2003
 * Author: yifat har-nof
 * @version $Id: ActionException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * The exception that {@link Action} implementators throws witin Action's code.
 */
public class ActionException extends FlowerException
{
	/**
	 * create new ActionException Object
	 */
	public ActionException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * create new ActionException Object
	 */
	public ActionException(String message)
	{
		super(message);
	}
}
