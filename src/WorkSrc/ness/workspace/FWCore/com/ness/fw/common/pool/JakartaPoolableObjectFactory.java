/*
 * Created on 14/04/2004
 * Author: baruch hizkya
 * @version $Id: JakartaPoolableObjectFactory.java,v 1.2 2005/04/18 17:15:45 baruch Exp $ 
 */
 
package com.ness.fw.common.pool;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * A wrapper class for the jakarta PoolableObjectFactory class
 * @author bhizkya
 *
 */
public class JakartaPoolableObjectFactory implements PoolableObjectFactory
{

	private com.ness.fw.common.pool.PoolableObjectFactory factory;
	
	/**
	 * Constructor
	 * @param factory
	 */
	protected JakartaPoolableObjectFactory(com.ness.fw.common.pool.PoolableObjectFactory factory)	
	{
		this.factory  = factory;
	}

	/**
	 * Creates an instance that can be returned by the pool.
	 * @return an instance that can be returned by the pool.
	 */
	public Object makeObject() throws Exception
	{
		return factory.makeObject();
	}

	/**
	 * Destroys an instance no longer needed by the pool.
	 * @param obj the instance to be destroyed
	 */
	public void destroyObject(Object obj) throws Exception
	{
		factory.destroyObject(obj);
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
		return factory.validateObject(obj);
	}

	/**
	 * Reinitialize an instance to be returned by the pool.
	 * @param obj the instance to be activated
	 */
	public void activateObject(Object obj) throws Exception
	{
		factory.activateObject(obj);
	}

	/**
	 * Uninitialize an instance to be returned to the pool.
	 * @param obj the instance to be passivated
	 */
	public void passivateObject(Object obj) throws Exception
	{
		factory.passivateObject(obj);
	}
}
