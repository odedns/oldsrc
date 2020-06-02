/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ActivityException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.exceptions.*;

/**
 * The exception thrown from activity when problem occures.
 */
public class ActivityException extends GeneralException
{
	/**
	 * create new ActivityException Object
	 */
	public ActivityException(String message)
	{
		super(message);
	}

	/**
	 * create new ActivityException Object
	 */
	public ActivityException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
