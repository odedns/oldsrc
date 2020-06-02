/*
 * Author: yifat har-nof
 * @version $Id: BusinessLogic.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

/**
 * Business Logic Object.
 * 
 * <p>
 * For complicated business logic methods or methods that combine data/logic 
 * from more then one DAO.
 * </p>
 * 
 * <p>
 * The object should be stateless and all the methods in the subclasses 
 * should be declared as static.
 * </p>
 */
public abstract class BusinessLogic
{

	/**
	 * 
	 */
	public BusinessLogic()
	{
		super();
	}

}
