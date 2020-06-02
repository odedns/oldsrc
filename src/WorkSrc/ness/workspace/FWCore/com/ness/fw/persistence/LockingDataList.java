/*
 * Created on 12/05/2004
 * Author: yifat har-nof
 * @version $Id: LockingDataList.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.ArrayList;

/**
 * A List of <code>LockingData</code> objects.
 */
public class LockingDataList
{

	/**
	 * The List of <code>LockingData</code> objects.
	 */
	private ArrayList list;

	/**
	 * Create new LockingDataList object.
	 */
	public LockingDataList()
	{
		list = new ArrayList();
	}

	/**
	 * Add a <code>Lockable</code> object to the list.
	 * @param lockable The Lockable object.
	 * @param  newLockId The new lockId to update in the lockable
	 */
	public void add(Lockable lockable, int newLockId)
	{
		list.add(new LockingData(lockable, newLockId));
	}

	/**
	 * Add a <code>LockingData</code> object to the list.
	 * @param lockableData The LockingData object.
	 */
	public void add(LockingData lockableData)
	{
		list.add(lockableData);
	}

	/**
	 * Returns the <code>LockingData</code> object according to given index.
	 * @return lockableData The LockingData object.
	 */
	public LockingData get(int index)
	{
		return (LockingData) list.get(index);
	}

	/**
	 * returns the LockingData objects count.
	 * @return int
	 */
	public int getCount()
	{
		return list.size();
	}

	/**
	 * remove all the <code>LockingData</code> objects from the list.
	 */
	public void removeAll()
	{
		list.clear();
	}

	/**
	 * Refresh the lockId of the <code>Lockable</code> objects.
	 */
	public void refreshObjectsLockId()
	{
		int count = list.size();
		for (int index = 0; index < count; index++)
		{
			LockingData lockableData = get(index);
			lockableData.getLockable().setLockId(lockableData.getNewLockId());
		}
	}

	/**
	 * add all the <code>LockingData</code> objects to the list.
	 * @param newList
	 */
	public void addAll(LockingDataList newList)
	{
		int count = newList.getCount();
		for (int index = 0; index < count; index++)
		{
			add(newList.get(index));
		}
	}

}
