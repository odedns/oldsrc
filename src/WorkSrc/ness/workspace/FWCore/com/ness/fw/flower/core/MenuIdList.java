/*
 * Created on: 15/12/04
 * Author: yifat har-nof
 * @version $$Id: MenuIdList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $$
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Provides implementation of list of menu item groups. Based on <code>ArrayList</code> implementation.
 * <br>
 * Used to collect all menu item groups from all flows
 */
public class MenuIdList
{
	/**
	 * the menu id list
	 */
	private ArrayList menuIdList;
	
	/**
	 * the list with the flows responsible for the menu ids.
	 */
	private ArrayList menuIdFlowList;
	
	/**
	 * add the menu ids to the list and chain it to the responsible flow.
	 * @param menuIds The menu id list
	 * @param flow The responsible flow
	 */
	public void add(List menuIds, Flow flow)
	{
		if(menuIds != null)
		{
			initLists();
			for(int i = 0 ; i < menuIds.size() ; i++)
			{
				String menuId = (String)menuIds.get(i);
				if(! menuIdList.contains(menuId))
				{
					menuIdList.add(menuId);
					menuIdFlowList.add(flow);
				}
			}
		}
	}

	/**
	 * init the lists when neccessary.
	 */
	private void initLists ()
	{
		if(menuIdList == null)
		{
			menuIdList = new ArrayList();
			menuIdFlowList = new ArrayList();
		}
	}

	/**
	 * Return the menu id according to given index
	 * @param index
	 * @return String menu id
	 */
	public String getMenuId(int index)
	{
		if(menuIdList != null)
			return (String) menuIdList.get(index);
		return null;
	}

	/**
	 * Return the responsible flow from the given index
	 * @param index
	 * @return Flow
	 */
	public Flow getMenuIdFlow(int index)
	{
		if(menuIdFlowList != null)
			return (Flow) menuIdFlowList.get(index);
		return null;
	}

	/**
	 * Returns the size of the list 
	 * @return int
	 */
	public int size()
	{
		return menuIdList == null ? 0 : menuIdList.size();
	}
	
}
