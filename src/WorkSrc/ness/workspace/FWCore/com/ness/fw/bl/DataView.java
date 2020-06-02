/*
 * Author: yifat har-nof
 * @version $Id: DataView.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

/**
 * Data View Object.
 * 
 * <p>
 * Provide an interface to the queries that combine data from DAO's 
 * which are not specifically related and there is no containment relation 
 * between them.
 * </p> 
 *   
 * <p>
 * The object should be stateless and all the methods in the subclasses 
 * should be declared as static.
 * </p> 
 */
public abstract class DataView
{

	/**
	 * 
	 */
	public DataView()
	{
		super();
	}

}
