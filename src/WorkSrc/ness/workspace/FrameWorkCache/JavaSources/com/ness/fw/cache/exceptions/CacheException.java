/*
 * Created on: 19/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheException.java,v 1.1 2005/02/24 08:42:02 alexey Exp $
 */
package com.ness.fw.cache.exceptions;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * This exception thrown when cache error occurs.
 */
public class CacheException extends GeneralException
{
	/**
	 * @param error
	 */
	public CacheException(Throwable error)
	{
		super(error);
	}

	/**
	 * @param messageText
	 * @param error
	 */
	public CacheException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * @param messageText
	 */
	public CacheException(String messageText)
	{
		super(messageText);
	}

}
