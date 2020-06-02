/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: MenuGroup.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.common.MenuItem;

/**
 * Provides abstraction for group of menu items.
 * Supplied by <code>ExtendedTransitionSupplierFactory</code> and used by JSP tag for building of menu.
 */
public interface MenuGroup
{
	/**
	 * Used to retrieve number of menu items
	 *
	 * @return number of menu items
	 */
	public int getMenusCount();

	/**
	 * @param index of menu item in list	 
	 */
	public MenuItem getMenuItem(int index);

	public String getName();
}
