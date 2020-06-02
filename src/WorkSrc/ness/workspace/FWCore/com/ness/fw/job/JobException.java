/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Exception thrown when job problem occures.
 */
public class JobException extends GeneralException
{
	
	/**
	 * create new JobException Object
	 */
	public JobException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new JobException Object
	 */
	public JobException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new JobException Object
	 */
	public JobException(String messageText)
	{
		super(messageText);
	}
	
}
