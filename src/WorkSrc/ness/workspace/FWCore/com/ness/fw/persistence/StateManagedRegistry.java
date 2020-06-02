/*
 * Created on 12/05/2004
 * Author: yifat har-nof
 * @version $Id: StateManagedRegistry.java,v 1.2 2005/04/04 09:37:34 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.ArrayList;

import com.ness.fw.common.exceptions.FatalException;

/**
 * A List of <code>StateManaged</code> objects.
 */
class StateManagedRegistry implements StateManagedList
{

	/**
	 * The List of <code>StateManaged</code> objects.
	 */
	private ArrayList stateManagedObjects;

	/**
	 * The List of <code>StateManagedList</code> objects.
	 */
	private ArrayList stateManagedListObjects;

	/**
	 * Create new StateManagedList object.
	 */
	StateManagedRegistry()
	{
	}

	/**
	 * Add a <code>StateManaged</code> object to the stateManagedObjects.
	 * @param stateManaged The StateManaged object.
	 */
	public void add(StateManaged stateManaged)
	{
		if (stateManagedObjects == null)
		{
			stateManagedObjects = new ArrayList(1);
		}
		stateManagedObjects.add(stateManaged);
	}

	/**
	 * Add a <code>StateManagedList</code> object to the stateManagedListObjects.
	 * @param stateManagedList The StateManagedList object.
	 */
	public void add(StateManagedList stateManagedList)
	{
		if (stateManagedListObjects == null)
		{
			stateManagedListObjects = new ArrayList(1);
		}
		stateManagedListObjects.add(stateManagedList);
	}

	/**
	 * Returns all the <code>StateManagedList</code> objects
	 * @return ArrayList
	 */
	protected ArrayList getAllStateManagedListObjects()
	{
		return stateManagedListObjects;
	}

	/**
	 * Returns all the <code>StateManaged</code> objects
	 * @return ArrayList
	 */
	protected ArrayList getAllStateManagedObjects()
	{
		return stateManagedObjects;
	}

	/**
	 * add all the <code>StateManaged</code> objects to the stateManagedObjects.
	 * @param newList
	 */
	public void addAll(StateManagedRegistry newList)
	{
		if (newList.getAllStateManagedObjects() != null)
		{
			if (stateManagedObjects == null)
			{
				stateManagedObjects =
					new ArrayList(newList.getAllStateManagedObjects().size());
			}
			stateManagedObjects.addAll(newList.getAllStateManagedObjects());
		}

		if (newList.getAllStateManagedListObjects() != null)
		{
			if (stateManagedListObjects == null)
			{
				stateManagedListObjects =
					new ArrayList(
						newList.getAllStateManagedListObjects().size());
			}
			stateManagedListObjects.addAll(
				newList.getAllStateManagedListObjects());
		}
	}

	/**
	 * remove all the <code>StateManaged</code> objects from the stateManagedObjects.
	 */
	public void removeAll()
	{
		if (stateManagedObjects != null)
			stateManagedObjects.clear();
		if (stateManagedListObjects != null)
			stateManagedListObjects.clear();
	}

	/**
	 * Clears the state of the <code>StateManaged</code> objects.
	 */
	public void clearObjectsState() throws FatalException
	{
		if (stateManagedObjects != null)
		{
			int count = stateManagedObjects.size();
			for (int index = 0; index < count; index++)
			{
				StateManaged stateManaged =
					(StateManaged) stateManagedObjects.get(index);
				stateManaged.clearObjectState();
			}
		}

		if (stateManagedListObjects != null)
		{
			int count = stateManagedListObjects.size();
			for (int index = 0; index < count; index++)
			{
				StateManagedList stateManagedList =
					(StateManagedList) stateManagedListObjects.get(index);
				stateManagedList.clearObjectsState();
			}
		}
	}

}
