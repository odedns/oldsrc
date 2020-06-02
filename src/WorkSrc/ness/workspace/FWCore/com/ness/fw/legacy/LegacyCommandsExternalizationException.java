/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyCommandsExternalizationException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;


/**
 * Exception thrown from the Legacy layer.
 */
public class LegacyCommandsExternalizationException extends LegacyCommandException
{

	/**
	 * create new LegacyCommandsExternalizationException Object
	 * @param error
	 */
	public LegacyCommandsExternalizationException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new LegacyCommandsExternalizationException Object
	 * @param messageText
	 * @param error
	 */
	public LegacyCommandsExternalizationException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new LegacyCommandsExternalizationException Object
	 * @param messageText
	 */
	public LegacyCommandsExternalizationException(String messageText)
	{
		super(messageText);
	}

}
