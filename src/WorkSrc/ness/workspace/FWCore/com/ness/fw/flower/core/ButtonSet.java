/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: ButtonSet.java,v 1.2 2005/04/18 05:54:20 shay Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * The class implements list of {@link ButtonData} objects
 */
public class ButtonSet
{
	/**
	 * The name of the button set.
	 */
	private String buttonSetName;

	/**
	 * The list of {@link ButtonData} elements.
	 */
	private ArrayList list;

	/**
	 * Create new ButtonSet object.
	 * @param buttonSetName The name of the button set.
	 */
	public ButtonSet(String buttonSetName)
	{
		this.buttonSetName = buttonSetName;
		list = new ArrayList();
	}

	/**
	 * Create new ButtonSet object.
	 */
	public ButtonSet()
	{
		this(null);
	}

	/**
	 * Returns the list of button data
	 * @return ArrayList of buttons
	 */
	public ArrayList getButtons()
	{
		return list;
	}

	/**
	 * Returns ButtonData element according to the given index. 
	 * @param index The index of the button.
	 * @return ButtonData
	 */
	public ButtonData getButtonData(int index)
	{
		return (ButtonData) list.get(index);
	}

	/**
	 * adds an {@link ButtonData} to the end of the button list.
	 * @param ButtonData
	 */
	public void addButtonData(ButtonData buttonData)
	{
		list.add(buttonData);
	}

	/**
	 * returns the count of the buttons in the list.
	 * @return int count
	 */
	public int getCount()
	{
		return list.size();
	}

	/**
	 * Returns the name of the button set. 
	 * @return String name
	 */
	public String getButtonSetName()
	{
		return buttonSetName;
	}

	/**
	 * returns the buttons in the list.
	 * @return List
	 */
	protected List getAllButtons()
	{
		return list;
	}
	
	/**
	 * adds the {@link ButtonSet} buttons to the end of the button list.
	 * @param ButtonSet
	 */
	protected void addAll(ButtonSet newButtons)
	{
		list.addAll(newButtons.getAllButtons());
	}
	
	/**
	 * Returns the buttons according to given group name. 
	 * If the group is not supplied, returns all the buttons without the group declaration.
	 * @param group
	 * @return ButtonSet
	 */
	protected ButtonSet getButtons (String group)
	{
		ButtonSet groupList = new ButtonSet();
		
		for (int i=0 ; i < list.size() ; i++)
		{
			ButtonData button = (ButtonData) list.get(i);
			if((group == null && button.getButtonGroup() == null) ||   
			   (button.getButtonGroup() != null && button.getButtonGroup().equals(group)))
			{
				groupList.addButtonData(button);
			}
		}
		
		return groupList;
	}
}
