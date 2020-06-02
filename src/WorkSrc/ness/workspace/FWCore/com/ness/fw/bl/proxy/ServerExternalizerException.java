/*
 * Created on: 02/08/2004
 * Author: yifat har-nof
 * @version $Id: ServerExternalizerException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Exception thrown from the BPOCommands dispathcer.
 */
public class ServerExternalizerException extends GeneralException
{

	/**
	 * create new BPOCommandException Object
	 * @param error
	 */
	public ServerExternalizerException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 * @param error
	 */
	public ServerExternalizerException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 */
	public ServerExternalizerException(String messageText)
	{
		super(messageText);
	}

}
