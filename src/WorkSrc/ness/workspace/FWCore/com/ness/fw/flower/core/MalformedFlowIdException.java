/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: MalformedFlowIdException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown when <code>FlowPath</code> unable to parse <code>flowId</code> string
 */
public class MalformedFlowIdException extends Exception
{
	public MalformedFlowIdException(String message)
	{
		super(message);
	}
}
