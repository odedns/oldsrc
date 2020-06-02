/*
 * Created on: 02/08/2004
 * Author: yifat har-nof
 * @version $Id: BPOCommandNotFoundException.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;


/**
 * Exception thrown from the BPOCommands dispathcer.
 */
public class BPOCommandNotFoundException extends BPOCommandException
{

	/**
	 * create new BPOCommandNotFoundException Object
	 * @param error
	 */
	public BPOCommandNotFoundException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new BPOCommandNotFoundException Object
	 * @param messageText
	 * @param error
	 */
	public BPOCommandNotFoundException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new BPOCommandNotFoundException Object
	 * @param messageText
	 */
	public BPOCommandNotFoundException(String messageText)
	{
		super(messageText);
	}

}
