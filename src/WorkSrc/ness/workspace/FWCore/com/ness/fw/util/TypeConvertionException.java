/*
 * Created on: 11/11/2004
 * Author: yifat har-nof
 * @version $Id: TypeConvertionException.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import com.ness.fw.common.exceptions.GeneralException;


/**
 * Exception thrown TypeConverter
 */
public class TypeConvertionException extends GeneralException
{

	/**
	 * create new TypeConvertionException Object
	 * @param error
	 */
	public TypeConvertionException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new TypeConvertionException Object
	 * @param messageText
	 * @param error
	 */
	public TypeConvertionException(
		String messageText,
		Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new TypeConvertionException Object
	 * @param messageText
	 */
	public TypeConvertionException(String messageText)
	{
		super(messageText);
	}

}
