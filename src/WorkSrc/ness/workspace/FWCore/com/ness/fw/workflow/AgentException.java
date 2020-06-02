/*
 * Created on: 06/06/2004
 * Author Amit Mendelson
 * @version $Id: AgentException.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.workflow;

/**
 * This exception is thrown in the com.ness.fw.workflow module in case of any
 * exception thrown during work with agent (wraps mostly the workflow relevant
 * exceptions).
 */
public class AgentException extends WorkFlowException
{

	/**
	 * create new AgentException Object
	 * @param error
	 */
	public AgentException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new AgentException Object
	 * @param messageText
	 * @param error
	 */
	public AgentException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new AgentException Object
	 * @param messageText
	 */
	public AgentException(String messageText)
	{
		super(messageText);
	}

}
