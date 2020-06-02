/*
 * Created on: 15/12/04
 * Author: yifat har-nof
 * @version $$Id: MenuItemList.java,v 1.1 2005/02/21 15:07:10 baruch Exp $$
 */
package com.ness.fw.flower.common;

import java.util.*;

/**
 * Provides implementation of list of menu item groups. Based on <code>ArrayList</code> implementation.
 * <br>
 * Used to collect all menu item groups from all flows
 */
public class MenuItemList
{
	private ArrayList menuItemList;
	private ArrayList menuIdFlowPathList;
	
	public MenuItemList()
	{
		menuItemList = new ArrayList(15);
		menuIdFlowPathList = new ArrayList(2);
	}

	public int getCount()
	{
		return menuItemList.size();
	}

	public MenuItem getMenuItem(int index)
	{
		return (MenuItem) menuItemList.get(index);
	}

	public void addMenuItem(MenuItem menuItem, String menuIdFlowPath)
	{
		menuItemList.add(menuItem);
		if(!menuIdFlowPathList.contains(menuIdFlowPath))
		{
			menuIdFlowPathList.add(menuIdFlowPath);
		}
	}

	public ArrayList getMenuIdFlowPathList()
	{
		return menuIdFlowPathList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("MenuItemList: \n");

		for(int i = 0 ; i < menuItemList.size(); i++)
		{
			MenuItem menuItem = (MenuItem)menuItemList.get(i);
			sb.append(menuItem.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

}
