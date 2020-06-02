/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyCommandNotFoundException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;


/**
 * Exception thrown from the Legacy layer.
 */
public class LegacyCommandNotFoundException extends LegacyCommandException
{

	/**
	 * create new LegacyCommandNotFoundException Object
	 * @param error
	 */
	public LegacyCommandNotFoundException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new LegacyCommandNotFoundException Object
	 * @param messageText
	 * @param error
	 */
	public LegacyCommandNotFoundException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new LegacyCommandNotFoundException Object
	 * @param messageText
	 */
	public LegacyCommandNotFoundException(String messageText)
	{
		super(messageText);
	}

}
