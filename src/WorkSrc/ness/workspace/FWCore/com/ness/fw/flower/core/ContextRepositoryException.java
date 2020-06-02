/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextRepositoryException.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown by <code>ContextRepository</code>
 */
public class ContextRepositoryException extends FlowerException
{
	/**
	 * create new ContextRepositoryException object.
	 * @param message
	 */
	public ContextRepositoryException(String message)
	{
		super(message);
	}
}
