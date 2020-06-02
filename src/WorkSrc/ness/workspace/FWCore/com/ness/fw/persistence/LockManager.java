/*
 * Author: yifat har-nof
 * @version $Id: LockManager.java,v 1.3 2005/04/14 14:02:13 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.persistence.exceptions.LockException;

/**
 * A List of Lockable objects that holds the data to use for 
 * locking a Lockable object.
 */
public class LockManager
{
	/**
	 * A List of LockData objects.
	 */
	private ArrayList lockDataList;

	/**
	 * Create new LockManager object.
	 */
	public LockManager()
	{
		lockDataList = new ArrayList(1);
	}

	/**
	 * Add a Lockable object to the locking objects list.
	 * @param lockable The Lockable object to lock.
	 * @param readOnlyLocking Indicates whether to change the lockId while locking.
	 */
	public void addLockable(Lockable lockable, boolean readOnlyLocking)
	{
		boolean found = false;
		for (int index = 0 ; !found && index < lockDataList.size() ; index++)
		{
			LockData lockData = getLockData(index);
			if(lockData.lockable.equals(lockable))
			{
				found = true;
				if(lockData.readOnlyLocking && !readOnlyLocking)
				{
					lockData.readOnlyLocking = readOnlyLocking;
				}
			}
		}
		
		if(!found)
		{
			lockDataList.add(new LockData(lockable, readOnlyLocking));
		}
	}

	/**
	 * Add a Lockable object to the locking objects list. 
	 * The locking mode is not read only and the lockId will be changed while locking.
	 * @param lockable The Lockable object to lock.
	 */
	public void addLockable(Lockable lockable)
	{
		addLockable(lockable, false);
	}

	private LockData getLockData(int index)
	{
		return (LockData) lockDataList.get(index);
	}

	/**
	 * Add a List of Lockable objects to the locking objects list. 
	 * The locking mode is not read only and the lockId will be changed while locking.
	 * @param lockables The Lockable objects List to lock.
	 */
	public void addLockableList(List lockables)
	{
		if (lockables != null)
		{
			for (int i = 0; i < lockables.size(); i++)
			{
				addLockable((Lockable) lockables.get(i), false);
			}
		}
	}

	/**
	 * returns lockables count.
	 * @return int
	 */
	public int getCount()
	{
		return lockDataList.size();
	}

	/**
	 * Lock the <code>Lockable</code> objects.
	 * @param transaction The transaction to use.
	 * @throws LockException Any LockException that may occur.
	 */
	public void lock(Transaction transaction) throws LockException
	{
		for (int index = 0; index < lockDataList.size(); index++)
		{
			LockData data = getLockData(index);
			data.lockable.lock(transaction, data.readOnlyLocking);
		}
	}

	private static class LockData
	{
		/**
		 * The Lockable object to lock.
		 */
		private Lockable lockable;

		/**
		 * Indicates whether to change the lockId while locking.
		 */
		private boolean readOnlyLocking;

		/**
		 * Create new LockingData object.
		 * @param lockable The Lockable object to lock.
		 * @param readOnlyLocking Indicates whether to change the lockId while locking.
		 */
		public LockData(Lockable lockable, boolean readOnlyLocking)
		{
			this.lockable = lockable;
			this.readOnlyLocking = readOnlyLocking;
		}
	}

}
