/*
 * Created on: 02/08/2004
 * Author: yifat har-nof
 * @version $Id: LanguageException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.common;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Exception thrown from the BPOCommands dispathcer.
 */
public class LanguageException extends GeneralException
{

	/**
	 * create new BPOCommandException Object
	 * @param error
	 */
	public LanguageException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 * @param error
	 */
	public LanguageException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new BPOCommandException Object
	 * @param messageText
	 */
	public LanguageException(String messageText)
	{
		super(messageText);
	}

}
