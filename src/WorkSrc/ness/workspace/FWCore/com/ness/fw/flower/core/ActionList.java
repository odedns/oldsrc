/*
 * Created on: 25/6/2003
 * Author: yifat har-nof
 * @version $Id: ActionList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * The class provides implementation of a list of {@link Action}s. Based on standard
 * {@link ArrayList} implementation.
 */
public class ActionList
{
	/**
	 * The list of actions.
	 */
    private ArrayList list;

	/**
	 * create new ActionList Object
	 */
	public ActionList()
	{
		list = new ArrayList();
	}

	/**
	 * returns an {@link Action} according to the given index.  
	 * @param index The index of the Action.
	 * @return Action
	 */
	public Action getAction(int index)
	{
		return (Action) list.get(index);
	}

	/**
	 * adds an {@link Action} to the end of the actions list.
	 * @param Action The action object to add.
	 */
	public void addAction(Action action)
	{
		list.add(action);
	}

	/**
	 * returns the count of the actions in the list.
	 * @return int count
	 */
	public int getActionsCount()
	{
		return list.size();
	}
}
