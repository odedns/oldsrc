/*
 * Created on 14/04/2004
 * Author: baruch hizkya
 * @version $Id: GenericPool.java,v 1.1 2005/04/17 11:18:56 baruch Exp $ 
 */
 
package com.ness.fw.common.pool;

/**
 * A configurable pooling interface.  
 */

public interface GenericPool extends Pool
{
	/**
	* Returns the cap on the total number of active instances from my pool.
	* @return the cap on the total number of active instances from my pool.
	*/
	public int getMaxActive();

	/**
	* Sets the cap on the total number of active instances from my pool.
	* @param maxActive The cap on the total number of active instances from my pool.
	*                  Use a negative value for an infinite number of instances.
	* @see #getMaxActive
	*/
	public void setMaxActive(int maxActive);

	/**
	* Returns the action to take when the {@link #borrowObject} method
	* is invoked when the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*
	* @return one of BLOCK, FAIL, GROW
	*/
	public byte getWhenExhaustedAction();

	/**
	* Sets the action to take when the {@link #borrowObject} method
	* is invoked when the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*
	* @param whenExhaustedAction the action code, which must be one of
	* one of BLOCK, FAIL, GROW
	*/
	public void setWhenExhaustedAction(byte whenExhaustedAction);

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
	public long getMaxWait();

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
	public void setMaxWait(long maxWait);

	/**
	* Returns the cap on the number of "idle" instances in the pool.
	* @return the cap on the number of "idle" instances in the pool.
	* @see #setMaxIdle
	*/
	public int getMaxIdle();

	/**
	* Sets the cap on the number of "idle" instances in the pool.
	* @param maxIdle The cap on the number of "idle" instances in the pool.
	*                Use a negative value to indicate an unlimited number
	*                of idle instances.
	* @see #getMaxIdle
	*/
	public void setMaxIdle(int maxIdle);

	/**
	* Sets the minimum number of objects allowed in the pool
	* before the evictor thread (if active) spawns new objects.
	* (Note no objects are created when: numActive + numIdle >= maxActive)
	*
	* @param minIdle The minimum number of objects.
	* @see #getMinIdle
	*/
	public void setMinIdle(int minIdle);

	/**
	* Returns the minimum number of objects allowed in the pool
	* before the evictor thread (if active) spawns new objects.
	* (Note no objects are created when: numActive + numIdle >= maxActive)
	*
	* @return The minimum number of objects.
	* @see #setMinIdle
	*/
	public int getMinIdle();

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
	public boolean getTestOnBorrow();

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
	public void setTestOnBorrow(boolean testOnBorrow);

	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* before being returned to the pool within the
	* {@link #returnObject}.
	*
	* @see #setTestOnReturn
	*/
	public boolean getTestOnReturn();

	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* before being returned to the pool within the
	* {@link #returnObject}.
	*
	* @see #getTestOnReturn
	*/
	public void setTestOnReturn(boolean testOnReturn);

	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* by the idle object evictor (if any).  If an object
	* fails to validate, it will be dropped from the pool.
	*
	* @see #setTestWhileIdle
	* @see #setTimeBetweenEvictionRunsMillis
	*/
	public boolean getTestWhileIdle();

	/**
	* When <tt>true</tt>, objects will be
	* {@link PoolableObjectFactory#validateObject validated}
	* by the idle object evictor (if any).  If an object
	* fails to validate, it will be dropped from the pool.
	*
	* @see #getTestWhileIdle
	* @see #setTimeBetweenEvictionRunsMillis
	*/
	public void setTestWhileIdle(boolean testWhileIdle);
	
	
	/**
	* Gets the failed action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public byte getFailedExhaustedAction();

	/**
	* Gets the block action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public byte getBlockExhaustedAction();

	/**
	* Gets the grow action to take when the {@link #borrowObject} method
	* is invoked and the pool is exhausted (the maximum number
	* of "active" objects has been reached).
	*/
	public byte getGrowExhaustedAction();

	

}