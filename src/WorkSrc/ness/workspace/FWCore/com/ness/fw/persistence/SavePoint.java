/*
 * Author: yifat har-nof
 * @version $Id: SavePoint.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.common.exceptions.FatalException;

/**
 * The representation of a savepoint, which is a point within the current 
 * transaction that can be referenced from the Connection.rollback method. 
 * When a transaction is rolled back to a savepoint all changes made after 
 * that savepoint are undone. 
 */
public class SavePoint
{

	/**
	 * The name of the savePoint. 
	 */
	private String name;

	/**
	 * Indicates whether the save point is active.
	 */
	private boolean isActive = true;

	/** A list of <code>StateManaged</code> objects that saved in the current savePoint. 
	 */
	private StateManagedRegistry stateManagedList = null;

	/** A list of <code>LockingData</code> objects that saved in the current savePoint. 
	 */
	private LockingDataList lockingDataList = null;

	/**
	 * Constructor for SavePoint.
	 * @param name The name of the savePoint.
	 */
	protected SavePoint(String name)
	{
		super();
		this.name = name;
		stateManagedList = new StateManagedRegistry();
		lockingDataList = new LockingDataList();
	}

	/**
	 * Returns the name of the savePoint
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the current savePoint as inactive.
	 */
	protected void setInactive()
	{
		this.isActive = false;
	}

	/**
	 * Indicates whether the save point is active.
	 * @return boolean
	 */
	public boolean isActive()
	{
		return isActive;
	}

	/**
	 * Add a <code>StateManaged</code> to the end of the list.
	 * @param stateManaged
	 */
	protected void addStateManaged(StateManaged stateManaged)
	{
		stateManagedList.add(stateManaged);
	}

	/**
	 * Add a <code>StateManagedList</code> to the end of the list.
	 * @param stateManagedList
	 */
	protected void addStateManagedList(StateManagedList newList)
	{
		stateManagedList.add(newList);
	}

	/**
	 * Add a <code>LockingData</code> to the end of the list.
	 * @param lockingData
	 */
	protected void addLockingData(LockingData lockingData)
	{
		lockingDataList.add(lockingData);
	}

	/**
	 * Clears the state of the <code>StateManaged</code> objects.
	 */
	protected void clearManagedObjectsState() throws FatalException
	{
		stateManagedList.clearObjectsState();
	}

	/**
	 * Refresh the lockId of the <code>Lockable</code> objects.
	 */
	protected void refreshObjectsLockId()
	{
		lockingDataList.refreshObjectsLockId();
	}

	/**
	 * remove all the <code>StateManaged</code> objects from the list.
	 */
	protected void removeManagedObjects()
	{
		stateManagedList.removeAll();
	}

	/**
	 * remove all the <code>LockingData</code> objects from the list.
	 */
	protected void removeLockingDataObjects()
	{
		lockingDataList.removeAll();
	}

	/**
	 * @return The <code>StateManaged</code> objects list.
	 */
	protected StateManagedRegistry getStateManagedList()
	{
		return stateManagedList;
	}

	/**
	 * @return The <code>LockingData</code> objects list.
	 */
	protected LockingDataList getLockingDataList()
	{
		return lockingDataList;
	}

}
