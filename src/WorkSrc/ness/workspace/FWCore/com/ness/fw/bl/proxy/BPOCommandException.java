/*
 * Created on: 02/08/2004
 * Author: yifat har-nof
 * @version $Id: BPOCommandException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Exception thrown from the BPOCommands dispathcer.
 */
public class BPOCommandException extends GeneralException
{

	/**
	 * create new BPOCommandException Object
	 * @param error
	 */
	public BPOCommandException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 * @param error
	 */
	public BPOCommandException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 */
	public BPOCommandException(String messageText)
	{
		super(messageText);
	}

}
