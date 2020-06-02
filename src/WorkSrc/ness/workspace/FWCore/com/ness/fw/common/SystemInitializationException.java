/*
 * Created on: 02/08/2004
 * Author: yifat har-nof
 * @version $Id: SystemInitializationException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Exception thrown from the BPOCommands dispathcer.
 */
public class SystemInitializationException extends GeneralException
{

	/**
	 * create new BPOCommandException Object
	 * @param error
	 */
	public SystemInitializationException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 * @param error
	 */
	public SystemInitializationException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 */
	public SystemInitializationException(String messageText)
	{
		super(messageText);
	}

}
