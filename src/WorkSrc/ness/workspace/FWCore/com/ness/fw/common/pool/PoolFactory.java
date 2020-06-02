/*
 * Created on 14/04/2004
 * Author: baruch hizkya
 * @version $Id: PoolFactory.java,v 1.3 2005/04/18 17:15:45 baruch Exp $ 
 */
 
package com.ness.fw.common.pool;

/**
 * A factory for pools
 * An application should use this factory to get a specif pool
 */

public class PoolFactory
{
	/**
	 * Creates a generic pool instance with defaults values
	 * @param factory subclass of {@link PoolableObjectFactory} which 
	 * implement the life cycle of the object the will be in the pool
	 * @return GenericPool
	 */
	public static GenericPool createGenericPool(PoolableObjectFactory factory) throws PoolException
	{
		JakartaPoolableObjectFactory objectFactory = new JakartaPoolableObjectFactory(factory);
		return new GenericPoolImpl(objectFactory);		
	}

	/**
	 * Creates a generic pool instance, initialized with maxIdle, maxActive
	 * @param factory subclass of {@link PoolableObjectFactory} which 
	 * implement the life cycle of the object the will be in the pool
	 * @param maxActive. The maximum objects in the pool
	 * @return GenericPool
	 */
	public static GenericPool createGenericPool(PoolableObjectFactory factory, int maxActive) throws PoolException
	{
		JakartaPoolableObjectFactory objectFactory = new JakartaPoolableObjectFactory(factory);
		return new GenericPoolImpl(objectFactory,maxActive);
	}

	/**
	 * Creates a generic pool instance, initialized with maxIdle, maxActive, maxWait
	 * @param factory subclass of {@link PoolableObjectFactory} which 
	 * implement the life cycle of the object the will be in the pool
	 * @param maxActive The maximum objects in the pool
	 * @param maxWait. How much time to wait when the pool is exhausted and
	 *  the relevant action is BLOCKED, before to throw an exception 
	 * @return GenericPool
	 */
	public static GenericPool createGenericPool(PoolableObjectFactory factory, int action, int maxActive, long maxWait) throws PoolException
	{
		JakartaPoolableObjectFactory objectFactory = new JakartaPoolableObjectFactory(factory);
		return new GenericPoolImpl(objectFactory,action,maxActive,maxWait);
	}
}
