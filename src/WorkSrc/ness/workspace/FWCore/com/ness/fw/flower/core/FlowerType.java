/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowerType.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * The interface should be implemented by all custom types to support automatic conersion by <code>Context</code>
 */
public interface FlowerType
{
	/**
	 * Provides string representation of data
	 *
	 * @param mask optionally output mast supplied by user
	 * @return <code>String</code> representation of data
	 */
	public String getFormattedValue(String mask);

	/**
	 * For futural use
	 */
	public String[] getInputMasks();
}
