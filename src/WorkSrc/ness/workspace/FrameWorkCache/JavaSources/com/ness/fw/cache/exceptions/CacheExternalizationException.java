/*
 * Created on: 19/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheExternalizationException.java,v 1.1 2005/02/24 08:42:02 alexey Exp $
 */
package com.ness.fw.cache.exceptions;

/**
 * This exception thrown when cache externalization occurs.
 */
public class CacheExternalizationException extends CacheException
{
	/**
	 * @param error
	 */
	public CacheExternalizationException(Throwable error)
	{
		super(error);
	}
	/**
	 * @param messageText
	 * @param error
	 */
	public CacheExternalizationException(String messageText, Throwable error)
	{
		super(messageText, error);
	}
	/**
	 * @param messageText
	 */
	public CacheExternalizationException(String messageText)
	{
		super(messageText);
	}
}
