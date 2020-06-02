/*
 * Created on: 12/08/2004
 * Author: yifat har-nof
 * @version $Id: LockingData.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

/**
 * Holds the data about the lockable object to update the new lockId after 
 * commiting the transaction.  
 */
class LockingData
{
	/**
	 * The lockable object to update after commit
	 */
	private Lockable lockable = null;

	/**
	 * The new lockId to update in the lockable
	 */
	private int newLockId = 0;

	/**
	 * Creates new LockingData object.
	 * @param lockable The lockable object to update after commit
	 * @param newLockId The new lockId to update in the lockable
	 */
	public LockingData(Lockable lockable, int newLockId)
	{
		this.lockable = lockable;
		this.newLockId = newLockId;
	}

	/**
	 * The lockable object to update after commit
	 * @return Lockable
	 */
	public Lockable getLockable()
	{
		return lockable;
	}

	/**
	 *  The new lockId to update in the lockable
	 * @return int
	 */
	public int getNewLockId()
	{
		return newLockId;
	}

}
