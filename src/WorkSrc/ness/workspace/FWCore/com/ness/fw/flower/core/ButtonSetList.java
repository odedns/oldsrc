/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ButtonSetList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Implelents list of ButtonSets based on <code>ArrayList</code> implementation
 */
public class ButtonSetList
{
	private ArrayList list;

	public ButtonSetList()
	{
		list = new ArrayList();
	}

	public ButtonSet getButtonSet(int index)
	{
		return (ButtonSet) list.get(index);
	}

	public void addButtonSet(ButtonSet buttonSet)
	{
		list.add(buttonSet);
	}

	public int getButtonSetCount()
	{
		return list.size();
	}

	/**
	 * Retruns all the buttons from the ButtonSets declared in the state, 
	 * according to given group name. 
	 * If the group is not supplied, returns all the buttons without the group declaration.
	 * @param group
	 * @return ButtonSet
	 */	
	public ButtonSet getButtons (String group)
	{
		ButtonSet result = new ButtonSet("");
		for (int i=0 ; i < list.size() ; i++)
		{
			result.addAll(getButtonSet(i).getButtons(group));
		}
		return result;
	}
	
	
}
