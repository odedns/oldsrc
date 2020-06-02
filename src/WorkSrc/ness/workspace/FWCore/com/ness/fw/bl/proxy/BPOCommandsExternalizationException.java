/*
 * Created on: 02/08/2004
 * Author: yifat har-nof
 * @version $Id: BPOCommandsExternalizationException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;


/**
 * Exception thrown from the BPOCommands dispathcer.
 */
public class BPOCommandsExternalizationException extends BPOCommandException
{

	/**
	 * create new BPOCommandsExternalizationException Object
	 * @param error
	 */
	public BPOCommandsExternalizationException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new BPOCommandsExternalizationException Object
	 * @param messageText
	 * @param error
	 */
	public BPOCommandsExternalizationException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new BPOCommandsExternalizationException Object
	 * @param messageText
	 */
	public BPOCommandsExternalizationException(String messageText)
	{
		super(messageText);
	}

}
