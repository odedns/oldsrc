/*
 * Author: yifat har-nof
 * @version $Id: BusinessProcess.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl;

/**
 * Business Process Object.
 * 
 * <p>
 * Provide an interface to the business processes of the module 
 * for the operational layer.
 * </p> 
 *   
 * <p>
 * The methods in the subclasses should work with DAO / BusinessLogic objects 
 * of the same module.
 * </p> 
 * 
 * <p>
 * The object should be stateless and all the methods in the subclasses 
 * should be declared as static.
 * </p> 
 */
public abstract class BusinessProcess
{

	/**
	 * 
	 */
	public BusinessProcess()
	{
		super();
	}

}
