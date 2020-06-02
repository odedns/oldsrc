/*
 * Created on 14/04/2004
 * Author: baruch hizkya
 * @version $Id: PoolableObjectFactory.java,v 1.2 2005/04/18 14:23:17 baruch Exp $ 
 */
 
package com.ness.fw.common.pool;


/**
 * An interface defining life-cycle methods for
 * instances to be used in an
 * {@link ObjectPool ObjectPool}.
 * <p>
 * By contract, when an {@link Pool Pool}
 * delegates to a <tt>PoolableObjectFactory</tt>,
 * <ol>
 *  <li>
 *   {@link #makeObject makeObject} 
 *   is called  whenever a new instance is needed.
 *  </li>
 *  <li>
 *   {@link #activateObject activateObject} 
 *   is invoked on every instance before it is returned from the
 *   pool.
 *  </li>
 *  <li>
 *   {@link #passivateObject passivateObject} 
 *   is invoked on every instance when it is returned to the
 *   pool.
 *  </li>
 *  <li>
 *   {@link #destroyObject destroyObject} 
 *   is invoked on every instance when it is being "dropped" from the
 *   pool (whether due to the response from
 *   {@link #validateObject validateObject}, or
 *   for reasons specific to the pool implementation.)
 *  </li>
 *  <li>
 *   {@link #validateObject validateObject} 
 *   is invoked in an implementation-specific fashion to determine if an instance
 *   is still valid to be returned by the pool.
 *   It will only be invoked on an {@link #activateObject "activated"}
 *   instance.
 *  </li>
 * </ol>
 *
 */

public interface PoolableObjectFactory
{
	/**
	 * Creates an instance that can be returned by the pool.
	 * @return an instance that can be returned by the pool.
	 */
	Object makeObject() throws PoolException;

	/**
	 * Destroys an instance no longer needed by the pool.
	 * @param obj the instance to be destroyed
	 */
	void destroyObject(Object obj) throws PoolException;

	/**
	 * Ensures that the instance is safe to be returned by the pool.
	 * Returns <tt>false</tt> if this object should be destroyed.
	 * @param obj the instance to be validated
	 * @return <tt>false</tt> if this <i>obj</i> is not valid and should
	 *         be dropped from the pool, <tt>true</tt> otherwise.
	 */
	boolean validateObject(Object obj);

	/**
	 * Reinitialize an instance to be returned by the pool.
	 * @param obj the instance to be activated
	 */
	void activateObject(Object obj) throws PoolException;

	/**
	 * Uninitialize an instance to be returned to the pool.
	 * @param obj the instance to be passivated
	 */
	void passivateObject(Object obj) throws PoolException;
}
