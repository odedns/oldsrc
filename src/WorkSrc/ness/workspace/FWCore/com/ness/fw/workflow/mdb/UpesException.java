/*
 * Created on 26/12/2004
 *
 * Author: Amit Mendelson
 * @version $Id: UpesException.java,v 1.1 2005/03/15 11:11:45 amit Exp $
 */
package com.ness.fw.workflow.mdb;

import com.ness.fw.common.exceptions.GeneralException;
/**
 * @author Amit Mendelson
 *
 * General exception for exceptions thrown from the MDB project.
 */
public class UpesException extends GeneralException
{

	/**
	 * create new UpesException Object
	 * @param error
	 */
	public UpesException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new UpesException Object
	 * @param messageText
	 * @param error
	 */
	public UpesException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new UpesException Object
	 * @param messageText
	 */
	public UpesException(String messageText)
	{
		super(messageText);
	}
}
