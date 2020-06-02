/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: MenuFlow.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.factory.*;

/**
 * Special kind of flow that supports global extended transitions supplier
 */
public class MenuFlow extends FlowImpl
{
	public MenuFlow(FlowDefinition flowDefinition)
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
		return ExtendedTransitionSupplierFactory.getInstance().getGlobalTransitionSupplier();
	}

	/**
	 * Indicates whether the type is menu.
	 * @return boolean
	 */
	public boolean isMenuFlow ()
	{
		return true;
	}

}
