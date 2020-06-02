/*
 * Created on: 04/04/2005
 * Author: yifat har-nof
 * @version $Id: SynchronizationLockTimeOutException.java,v 1.1 2005/04/04 07:01:55 yifat Exp $
 */
package com.ness.fw.common.lock;

/**
 * SynchronizationLockTimeOutException
 */
public class SynchronizationLockTimeOutException
	extends SynchronizationLockException
{

	/**
	 * @param error
	 */
	public SynchronizationLockTimeOutException(Throwable error)
	{
		super(error);
	}

	/**
	 * @param messageText
	 * @param error
	 */
	public SynchronizationLockTimeOutException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public SynchronizationLockTimeOutException(String messageText)
	{
		super(messageText);
	}

}
