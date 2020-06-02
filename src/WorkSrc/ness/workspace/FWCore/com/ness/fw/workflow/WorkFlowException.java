/*
 * Created on: 01/06/2004
 * Author Amit Mendelson
 * @version $Id: WorkFlowException.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * General Exception that wraps various Exceptions thrown in the com.ness.fw.workflow
 * module.
 */
public class WorkFlowException extends GeneralException
{

	/**
	 * create new WorkFlowException Object
	 * @param error
	 */
	public WorkFlowException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new WorkFlowException Object
	 * @param messageText
	 * @param error
	 */
	public WorkFlowException(String messageText, Throwable error)
	{

		super(messageText, error);
	}

	/**
	 * create new WorkFlowException Object
	 * @param messageText
	 */
	public WorkFlowException(String messageText)
	{
		super(messageText);
	}

}
