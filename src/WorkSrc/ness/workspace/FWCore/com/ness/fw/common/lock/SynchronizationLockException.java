/*
 * Created on: 01/11/2004
 * Author: yifat har-nof
 * @version $Id: SynchronizationLockException.java,v 1.2 2005/04/04 07:01:55 yifat Exp $
 */
package com.ness.fw.common.lock;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * SynchronizationLockException
 */
public class SynchronizationLockException extends GeneralException
{

	/**
	 * @param error
	 */
	public SynchronizationLockException(Throwable error)
	{
		super(error);
	}

	/**
	 * @param messageText
	 * @param error
	 */
	public SynchronizationLockException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public SynchronizationLockException(String messageText)
	{
		super(messageText);
	}

}
