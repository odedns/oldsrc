/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: SimpleFlow.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * The commonly used kind of flow
 */
public class SimpleFlow extends FlowImpl
{
	public SimpleFlow(FlowDefinition flowDefinition)
	{
		super(flowDefinition);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Abstract methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	protected ExtendedTransitionSupplier createGlobalTransitionSupplier()
	{
		return null;
	}

	/**
	 * Indicates whether the type is menu.
	 * @return boolean
	 */
	public boolean isMenuFlow ()
	{
		return false;
	}

}
