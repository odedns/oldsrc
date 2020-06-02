/*
 * Created on 14/04/2004
 * Author: baruch hizkya
 * @version $Id: PoolException.java,v 1.2 2005/04/18 10:58:19 baruch Exp $ 
 */
 
package com.ness.fw.common.pool;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Exception thrown when pool problem occures.
 */
public class PoolException extends GeneralException
{

	/**
	 * @param error
	 */
	public PoolException(Throwable error)
	{
		super(error);
	}

	/**
	 * @param messageText
	 * @param error
	 */
	public PoolException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public PoolException(String messageText)
	{
		super(messageText);
	}

}
