/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: MenuGroupList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Provides implementation of list of menu items. 
 * Based on <code>ArrayList</code> implementation.
 * <br>
 * Used to collect all menu items from all the flows in the current hierarchy.
 */
public class MenuGroupList
{
	private ArrayList list;

	public MenuGroupList()
	{
		list = new ArrayList();
	}

	public MenuGroup getMenuGroup(int index)
	{
		return (MenuGroup) list.get(index);
	}

	public void addMenuGroup(MenuGroup menuGroup)
	{
		list.add(menuGroup);
	}

	public int getMenuGroupsCount()
	{
		return list.size();
	}
}
