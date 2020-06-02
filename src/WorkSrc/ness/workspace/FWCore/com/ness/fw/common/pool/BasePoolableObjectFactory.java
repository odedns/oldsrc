/*
 * Created on 14/04/2004
 * Author: baruch hizkya
 * @version $Id: BasePoolableObjectFactory.java,v 1.2 2005/04/18 14:23:30 baruch Exp $ 
 */
 
package com.ness.fw.common.pool;

/**
 * A base implemenation of {@link PoolableObjectFactory <tt>PoolableObjectFactory</tt>}.
 * <p>
 * All operations defined here are essentially no-op's.
 *
 */

public abstract class BasePoolableObjectFactory implements PoolableObjectFactory
{
	/**
	 * Creates an instance that can be returned by the pool.
	 * @return an instance that can be returned by the pool.
	 */
	public abstract Object makeObject() throws PoolException;

	/**
	 * Destroys an instance no longer needed by the pool.
	 * @param obj the instance to be destroyed
	 */
	public void destroyObject(Object obj) throws PoolException
	{
	}

	/**
	 * Ensures that the instance is safe to be returned by the pool.
	 * Returns <tt>false</tt> if this object should be destroyed.
	 * @param obj the instance to be validated
	 * @return <tt>false</tt> if this <i>obj</i> is not valid and should
	 *         be dropped from the pool, <tt>true</tt> otherwise.
	 */
	public boolean validateObject(Object obj)
	{
		return true;
	}

	/**
	 * Reinitialize an instance to be returned by the pool.
	 * @param obj the instance to be activated
	 */
	public void activateObject(Object obj) throws PoolException
	{
	}

	/**
	 * Uninitialize an instance to be returned to the pool.
	 * @param obj the instance to be passivated
	 */
	public void passivateObject(Object obj) throws PoolException
	{
	}
}
