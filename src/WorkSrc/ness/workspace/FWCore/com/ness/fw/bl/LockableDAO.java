/*
 * Author: yifat har-nof
 * @version $Id: LockableDAO.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.Lockable;
import com.ness.fw.persistence.Transaction;
import com.ness.fw.persistence.exceptions.LockException;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;

/**
 * A super class for a DAO that could be locked in the DB.
* 
 * <p>
 * A Business Object must inherit this class so that it can participate
 * in this package transactions scheme and should lock the object in the boundaries 
 * in the transaction.
 * </p>
 */
public abstract class LockableDAO extends DAO implements Lockable
{

	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = "BL: LOCKABLE DAO";

	/**
	 * The current lock id.
	 */
	private int lockId;

	/**
	 * returns the current lock id.
	 * @return int The current lock id
	 */
	public final int getLockId() 
	{
		return lockId;
	}

	/**
	 * Sets the current lock id.
	 * @param lockId
	 */
	public final void setLockId(int lockId)
	{
		this.lockId = lockId;
		Logger.debug(LOGGER_CONTEXT, "set lock id [" + lockId + "] to the LockableDAO [" + this.getClass().getName() + "]");
	}

	/**
	 * Get the current lock id with locking the record.
	 * Compare the given lock id with the current lock id from the record.
	 * If they are not equal, throw lockException.
	 * If they are equal, update the record with lock id + 1.
	 * @param Transaction The transaction to use.
	 * @param readOnlyLocking Indicates whether to change the lockId while locking.
	 * @throws LockException Any LockException that may occur.
	 */
	public final void lock(Transaction transaction, boolean readOnlyLocking)
		throws LockException
	{
		Logger.debug(LOGGER_CONTEXT, "Execute lock in DAO [" + this.getClass().getName() + "]");
		
		try
		{
			if (isLockingRequired())
			{
				LockParameters lockParameters = getLockParameters(); 
			
				lockParameters.setCurrentLockingValues(getLockId(), transaction);
			
				int newLockId = LockingService.lock(lockParameters, readOnlyLocking);
				
				// register the Lockable in the transaction.
				((Transaction)transaction).registerLockable(this, newLockId);
			
				lockParameters.clearCurrentLockingValues();
			}
		}
		catch (LockException le)
		{
			throw le;
		}
		catch (ObjectNotFoundException e)
		{
			throw new LockException("Object to lock not found", e.getMessagesContainer());	
		}
		catch (PersistenceException e)
		{
			throw new LockException ("Error while locking the DAO", e);
		}
	}

	/** Check if the object can be locked (exist in the DB).
	 * @return boolean Return true if the object can be locked.
	 */
	public final boolean isLockingRequired()
	{
		return getObjectState() == EXISTING_DIRTY
			|| getObjectState() == EXISTING_NON_DIRTY
			|| getObjectState() == DELETE_EXISTING;
	}

	/**
	 * Should be implemented by the sub classes.
	 * Returns The <code>LockParameters</code> object with the parameters 
	 * for performing the lock by the {@link LockingService}.
	 * @return LockParameters
	 */
	protected abstract LockParameters getLockParameters ();

}
