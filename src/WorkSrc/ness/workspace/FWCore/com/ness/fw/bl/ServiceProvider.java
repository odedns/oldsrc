/*
 * Author: yifat har-nof
 * @version $Id: ServiceProvider.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

/**
 * Service Provider Object.
 * 
 * <p>
 * Provide an interface to the business processes of the module 
 * for other modules.
 * </p> 
 *   
 * <p>
 * The methods in the subclasses should call to methods in BusinessProcess's in the 
 * same module.
 * </p> 
 * 
 * <p>
 * The object should be stateless and all the methods in the subclasses 
 * should be declared as static.
 * </p> 
 */
public abstract class ServiceProvider
{

	/**
	 * 
	 */
	public ServiceProvider()
	{
		super();
	}

}
