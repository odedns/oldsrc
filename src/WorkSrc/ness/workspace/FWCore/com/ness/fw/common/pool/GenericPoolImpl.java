/*
 * Created on 14/04/2004
 * Author: baruch hizkya
 * @version $Id: GenericPoolImpl.java,v 1.3 2005/04/18 17:15:34 baruch Exp $ 
 */
 
package com.ness.fw.common.pool;

import org.apache.commons.pool.impl.GenericObjectPool;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.resources.SystemResources;

/**
 * A configurable {@link ObjectPool} implementation.
 * <p>
 * When coupled with the appropriate {@link PoolableObjectFactory},
 * <tt>GenericObjectPool</tt> provides robust pooling functionality for
 * arbitrary objects.
 * <p>
 * GenericObjectPool is not usable without a {@link PoolableObjectFactory}.  A
 * non-<code>null</code> factory must be provided either as a constructor argument
 * or via a call to {@link #setFactory} before the pool is used.
 *
 */

public class GenericPoolImpl implements GenericPool 
{

	/**
	 * The default cap on the total number of active instances from the pool. 
	 */
	protected static final int DEFAULT_MAX_ACTIVE = 10;

	/**
	 * The default cap on the number of "sleeping" instances in the pool. 
	 */
	protected static final int DEFAULT_MAX_IDLE   = 10;
		
	/**
	 * The default maximum amount of time (in millis) the borrowObject() 
	 * method should block before throwing an exception when the pool is 
	 * exhausted and the "when exhausted" action is WHEN_EXHAUSTED_BLOCK. 
	 */
	protected static final long DEFAULT_MAX_WAIT = 2000;
	
	/**
	 * The default "when exhausted action" for the pool. 
	 */
	protected static final byte DEFAULT_WHEN_EXHAUSTED_ACTION = GenericObjectPool.WHEN_EXHAUSTED_GROW;
	

	private static int NOT_INITIALIZED = Integer.MIN_VALUE;
	
	private static final String MAX_ACTIVE 				= "pool.maxActive";
	private static final String MAX_IDLE 				= "pool.maxIdle";
	private static final String MAX_WAIT 				= "pool.maxWait";
	private static final String WHEN_EXHAUSTED_ACTION   = "pool.whenExhausetedAction";


	private GenericObjectPool pool;
	
	protected int maxIdle   = DEFAULT_MAX_IDLE;
	protected int maxActive = DEFAULT_MAX_ACTIVE;
	protected long maxWait  = DEFAULT_MAX_WAIT;
	protected byte action   = DEFAULT_WHEN_EXHAUSTED_ACTION;

	protected GenericPoolImpl(JakartaPoolableObjectFactory factory) throws PoolException
	{
		this(factory,NOT_INITIALIZED,NOT_INITIALIZED,NOT_INITIALIZED);
	}

	protected GenericPoolImpl(JakartaPoolableObjectFactory factory, int maxActive) throws PoolException
	{
		this(factory, NOT_INITIALIZED, maxActive, NOT_INITIALIZED);
	}

	protected GenericPoolImpl(JakartaPoolableObjectFactory factory, int action, int maxActive, long maxWait) throws PoolException
	{		
		// Creating the pool
		pool = new GenericObjectPool(factory);		

		// Loading default properties
		try
		{
			initProperties();
		}
		catch (ResourceException e)
		{
			throw new PoolException("error while initizalizing pool properties", e);
		}

		// Setting parameters
		if (action != NOT_INITIALIZED)
		{
			this.action = (byte)action;
		}
		pool.setWhenExhaustedAction(this.action);
		
		if (maxActive != NOT_INITIALIZED)
		{
			this.maxActive = maxActive;
		}
		pool.setMaxActive(this.maxActive);

		if (maxActive != NOT_INITIALIZED)
		{
			this.maxWait = maxWait;
		}
		pool.setMaxWait(this.maxWait);
	}

	/**
	 * Init defaults from property files
	 */
	private void initProperties() throws ResourceException
	{
		SystemResources resources = SystemResources.getInstance();

		if (resources.getProperty(MAX_ACTIVE) != null)
		{
			maxActive = resources.getInteger(MAX_ACTIVE);
		} 

		if (resources.getProperty(MAX_IDLE) != null)
		{
			maxIdle = resources.getInteger(MAX_IDLE);
		} 

		if (resources.getProperty(MAX_WAIT) != null)
		{
			maxWait = resources.getLong(MAX_WAIT);
		} 

		if (resources.getProperty(WHEN_EXHAUSTED_ACTION) != null)
		{
			action = (byte)resources.getInteger(WHEN_EXHAUSTED_ACTION);
		} 
	}

	/**
	* Returns the cap on the total number of active instances from my pool.
	* @return the cap on the total number of active instances from my pool.
	*/
	public int getMaxActive()
	{
		return pool.getMaxActive();
	}


	/**
	* Sets the cap on the total number of active instances from my pool.
	* @param maxActive The cap on the total number of active instances from my pool.
	*                  Use a negative value for an infinite number of instances.
	* @see #getMaxActive
	*/
	public void setMaxActive(int maxActive)
	{
		pool.setMaxActive(maxActive);
	}


	/**
	* Returns the action to take when the {@link #borrowObject} method
	* is invoked when the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*
	* @return one of BLOCK, FAIL, GROW
	*/
	public byte getWhenExhaustedAction()
	{
		return pool.getWhenExhaustedAction();
	}

	/**
	* Sets the action to take when the {@link #borrowObject} method
	* is invoked when the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*
	* @param whenExhaustedAction the action code, which must be one of
	* one of BLOCK, FAIL, GROW
	*/
	public void setWhenExhaustedAction(byte whenExhaustedAction)
	{
		pool.setWhenExhaustedAction(whenExhaustedAction);		
	}

	/**
		* Returns the maximum amount of time (in milliseconds) the
		* {@link #borrowObject} method should block before throwing
		* an exception when the pool is exhausted and the
		* {@link #setWhenExhaustedAction "when exhausted" action} is BLOCK.
		*
		* When less than 0, the {@link #borrowObject} method
		* may block indefinitely.
		*
		*/
	public long getMaxWait()
	{
		return pool.getMaxWait();
	}

	/**
	* Sets the maximum amount of time (in milliseconds) the
	* {@link #borrowObject} method should block before throwing
	* an exception when the pool is exhausted and the
	* {@link #setWhenExhaustedAction "when exhausted" action} is BLOCK.
	*
	* When less than 0, the {@link #borrowObject} method
	* may block indefinitely.
	*
	*/
	public void setMaxWait(long maxWait)
	{
		pool.setMaxWait(maxWait);
	}

	/**
	* Returns the cap on the number of "idle" instances in the pool.
	* @return the cap on the number of "idle" instances in the pool.
	* @see #setMaxIdle
	*/
	public int getMaxIdle()
	{
		return pool.getMaxIdle();
	}

	/**
	* Sets the cap on the number of "idle" instances in the pool.
	* @param maxIdle The cap on the number of "idle" instances in the pool.
	*                Use a negative value to indicate an unlimited number
	*                of idle instances.
	* @see #getMaxIdle
	*/
	public void setMaxIdle(int maxIdle)
	{
		pool.setMaxIdle(maxIdle);
	}

	/**
	* Sets the minimum number of objects allowed in the pool
	* before the evictor thread (if active) spawns new objects.
	* (Note no objects are created when: numActive + numIdle >= maxActive)
	*
	* @param minIdle The minimum number of objects.
	* @see #getMinIdle
	*/
	public void setMinIdle(int minIdle)
	{
		pool.setMaxIdle(minIdle);
	}


	/**
	* Returns the minimum number of objects allowed in the pool
	* before the evictor thread (if active) spawns new objects.
	* (Note no objects are created when: numActive + numIdle >= maxActive)
	*
	* @return The minimum number of objects.
	* @see #setMinIdle
	*/
	public int getMinIdle()
	{
		return pool.getMinIdle();
	}


	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* before being returned by the {@link #borrowObject}
	* method.  If the object fails to validate,
	* it will be dropped from the pool, and we will attempt
	* to borrow another.
	*
	* @see #setTestOnBorrow
	*/
	public boolean getTestOnBorrow()
	{
		return pool.getTestOnBorrow();
	}


	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* before being returned by the {@link #borrowObject}
	* method.  If the object fails to validate,
	* it will be dropped from the pool, and we will attempt
	* to borrow another.
	*
	* @see #getTestOnBorrow
	*/
	public void setTestOnBorrow(boolean testOnBorrow)
	{
		pool.setTestOnBorrow(testOnBorrow);		
	}


	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* before being returned to the pool within the
	* {@link #returnObject}.
	*
	* @see #setTestOnReturn
	*/
	public boolean getTestOnReturn()
	{
		return pool.getTestOnReturn();
	}


	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* before being returned to the pool within the
	* {@link #returnObject}.
	*
	* @see #getTestOnReturn
	*/
	public void setTestOnReturn(boolean testOnReturn)
	{
		pool.setTestOnReturn(testOnReturn);		
	}

	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* by the idle object evictor (if any).  If an object
	* fails to validate, it will be dropped from the pool.
	*
	* @see #setTestWhileIdle
	* @see #setTimeBetweenEvictionRunsMillis
	*/
	public boolean getTestWhileIdle()
	{
		return pool.getTestWhileIdle();
	}

	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* by the idle object evictor (if any).  If an object
	* fails to validate, it will be dropped from the pool.
	*
	* @see #getTestWhileIdle
	* @see #setTimeBetweenEvictionRunsMillis
	*/
	public void setTestWhileIdle(boolean testWhileIdle)
	{
		pool.setTestWhileIdle(testWhileIdle);
	}

	/**
	* Gets the failed action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public Object borrowObject() throws PoolException
	{
		try
		{
			return pool.borrowObject();
		}
		catch (Exception e)
		{
			throw new PoolException("borrowObject failed",e);
		}
	}

	/**
	* Gets the block action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public void returnObject(Object obj) throws PoolException
	{
		try
		{
			pool.returnObject(obj);
		}
		catch (Exception e)
		{
			throw new PoolException("returnObject failed",e);
		}		
	}


	/**
	* Gets the grow action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public void invalidateObject(Object obj) throws PoolException
	{
		try
		{
			pool.invalidateObject(obj);
		}
		catch (Exception e)
		{
			throw new PoolException("invalidateObject failed",e);
		}		
	}


	/**
	 * Create an object using my {@link #setFactory factory} or other
	 * implementation dependent mechanism, and place it into the pool.
	 * addObject() is useful for "pre-loading" a pool with idle objects.
	 * (Optional operation).
	 */
	public void addObject() throws PoolException
	{
		try
		{
			pool.addObject();
		}
		catch (Exception e)
		{
			throw new PoolException("addObject failed",e);
		}		
	}


	/**
	 * Return the number of instances
	 * currently idle in my pool (optional operation).  
	 * This may be considered an approximation of the number
	 * of objects that can be {@link #borrowObject borrowed}
	 * without creating any new instances.
	 *
	 * @return the number of instances currently idle in my pool
	 * @throws UnsupportedOperationException if this implementation does not support the operation
	 */
	public int getNumIdle() throws PoolException
	{
		try
		{
			return pool.getNumIdle();
		}
		catch (UnsupportedOperationException e)
		{
			throw new PoolException("setFactory Failed", e);
		}

	}

	/**
	 * Return the number of instances
	 * currently borrowed from my pool 
	 * (optional operation).
	 *
	 * @return the number of instances currently borrowed in my pool
	 * @throws UnsupportedOperationException if this implementation does not support the operation
	 */
	public int getNumActive() throws PoolException
	{
		try
		{
			return pool.getNumActive();
		}
		catch (UnsupportedOperationException e)
		{
			throw new PoolException("setFactory Failed", e);
		}

	}

	/**
	 * Clears any objects sitting idle in the pool, releasing any
	 * associated resources (optional operation).
	 *
	 * @throws UnsupportedOperationException if this implementation does not support the operation
	 */
	public void clear() throws PoolException
	{
		try
		{
			pool.clear();
		}		
		catch (UnsupportedOperationException e)
		{
			throw new PoolException("setFactory Failed", e);
		}


	}

	/**
	 * Close this pool, and free any resources associated with it.
	 */
	public void close() throws PoolException
	{
		try
		{
			pool.close();
		}
		catch (Exception e)
		{
			throw new PoolException("close failed",e);
		}
	}

	/**
	 * Sets the {@link PoolableObjectFactory factory} I use
	 * to create new instances (optional operation).
	 * @param factory the {@link PoolableObjectFactory} I use to create new instances.
	 *
	 * @throws IllegalStateException when the factory cannot be set at this time
	 * @throws UnsupportedOperationException if this implementation does not support the operation
	 */
	public void setFactory(PoolableObjectFactory factory) throws PoolException
	{
		try
		{
			JakartaPoolableObjectFactory jakartaFactory = new JakartaPoolableObjectFactory(factory);
			pool.setFactory(jakartaFactory);
		}
		catch (IllegalStateException e)
		{
			throw new PoolException("setFactory Failed", e);
		}

		catch (UnsupportedOperationException e)
		{
			throw new PoolException("setFactory Failed", e);
		}
		
	}

	/**
	* Gets the failed action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public byte getFailedExhaustedAction()
	{
		return GenericObjectPool.WHEN_EXHAUSTED_FAIL;
	}


	/**
	* Gets the block action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public byte getBlockExhaustedAction()
	{
		return GenericObjectPool.WHEN_EXHAUSTED_BLOCK;
	}

	/**
	* Gets the grow action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public byte getGrowExhaustedAction()
	{
		return GenericObjectPool.WHEN_EXHAUSTED_GROW;
	}

}
