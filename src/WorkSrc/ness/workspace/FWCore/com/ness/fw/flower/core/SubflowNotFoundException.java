/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: SubflowNotFoundException.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Thrown when event arrived to not existing subflow
 */
public class SubflowNotFoundException extends FlowerException
{
	public SubflowNotFoundException(String s)
	{
		super(s);
	}
}
