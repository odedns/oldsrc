/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowElementsFactoryException.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.flower.core.*;

/**
 * Thrown by <code>FlowElementsFactory</code>
 */
public class FlowElementsFactoryException extends FlowerException
{
	public FlowElementsFactoryException(String message)
	{
		super(message);
	}

	public FlowElementsFactoryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
