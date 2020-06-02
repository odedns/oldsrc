/*
 * Created on: 02/08/2004
 * Author: yifat har-nof
 * @version $Id: LegacyCommandException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Exception thrown from the Legacy layer.
 */
public class LegacyCommandException extends GeneralException
{

	/**
	 * create new LegacyCommandException Object
	 * @param error
	 */
	public LegacyCommandException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new LegacyCommandException Object
	 * @param messageText
	 * @param error
	 */
	public LegacyCommandException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new LegacyCommandException Object
	 * @param messageText
	 */
	public LegacyCommandException(String messageText)
	{
		super(messageText);
	}

}
