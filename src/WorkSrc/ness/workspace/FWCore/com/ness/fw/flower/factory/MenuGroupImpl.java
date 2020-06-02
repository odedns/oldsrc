/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: MenuGroupImpl.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.common.MenuItem;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.externalization.*;

import java.util.*;

/**
 * Implementation for both <code>MenuGroup</code> and <code>ExtendedTransitionSupplier</code>.
 * <br>
 * Used by JSP tag as <code>MenuGroup</code> and by <code>Flow</code> as <code>ExtendedTransitionSupplier</code>
 *
 */
public class MenuGroupImpl implements MenuGroup, ExtendedTransitionSupplier
{
	private String name;

	/**
	 * List of menu items
	 */
	private ArrayList menuItems;

	/**
	 * list of transitions
	 */
	private TransitionsListMap transitionsListMap;

	public MenuGroupImpl(String name)
	{
		this.name = name;

		menuItems = new ArrayList();
		transitionsListMap = new TransitionsListMap();
	}

	/**
	 * Adds menu item and creates appropriate transition
	 *
	 * @param menuItem
	 */
	public void addMenuItem(MenuItem menuItem) throws ExternalizerNotInitializedException, ExternalizationException
	{
		menuItems.add(menuItem);
			
		//create transition
		Transition transition = MenuTransitionFactory.createMenuTransition(menuItem); 
		transitionsListMap.addTransition(transition);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// implement ExtendedTransitionSupplier methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Called by <code>Flow</code> to ask for additional transitions.
	 *
	 * @param clickEventName name of event foe which transitions should be suppplied
	 * @return list of transitions
	 */
	public TransitionList getTransitionList(String eventName)
	{
		return transitionsListMap.getTransitionList(eventName);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// implement MenuGroup methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Used to retrieve number of menu items
	 *
	 * @return number of menu items
	 */
	public int getMenusCount()
	{
		return menuItems.size();
	}

	/**
	 * @param index of menu item in list
	 */
	public MenuItem getMenuItem(int index)
	{
		return (MenuItem) menuItems.get(index);
	}

	public String getName()
	{
		return name;
	}
}
