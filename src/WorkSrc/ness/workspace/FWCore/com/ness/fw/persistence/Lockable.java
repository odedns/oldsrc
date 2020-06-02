/*
 * Author: yifat har-nof
 * @version $Id: Lockable.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.persistence.exceptions.LockException;

/**
 * Generic locking interface.
 * Every business object that should support locking should implement this interface.
 */
public interface Lockable
{

	/**
	 * returns the current lock id.
	 * @return int The current lock id
	 */
	public int getLockId();

	/**
	 * Get the current lock id with locking the record.
	 * Compare the given lock id with the current lock id from the record.
	 * If they are not equal, throw lockException.
	 * If they are equal, update the record with lock id + 1.
	 * @param Transaction The transaction to use.
	 * @param readOnlyLocking Indicates whether to change the lockId while locking.
	 * @throws LockException Any LockException that may occur.
	 */
	public void lock(Transaction transaction, boolean readOnlyLocking)
		throws LockException;

	/** Check if the object can be locked (exist in the DB).
	 * @return boolean Return true if the object can be locked.
	 */
	public boolean isLockingRequired();

	/**
	 * Sets the current lock id.
	 * @param lockId
	 */
	public void setLockId(int lockId);

}
