/*
 * Author: yifat har-nof
 * @version $Id: DataViewContainer.java,v 1.2 2005/04/14 14:01:45 yifat Exp $
 */
package com.ness.fw.bl;

import com.ness.fw.common.auth.UserAuthData;

/**
 * Data View Object Container.
 * 
 * <p>
 * A container for the data returned from the data view methods to the BusinessProcess's.
 * </p>
 */
public abstract class DataViewContainer extends BasicContainer 
{

	/**
	 * Creates new DataViewContainer Object
	 */
	public DataViewContainer()
	{
		super();
	}

	/**
	 * Creates new DataViewContainer Object
	 * @param userAuthData Contains the basic authorization data of the current user.
	 */
	public DataViewContainer(UserAuthData userAuthData)
	{
		super(userAuthData);
	}

	/**
	 * Creates new DataViewContainer Object
	 * @param basicContainer The <code>BasicContainer</code> to pass the basic parameters.
	 */
	public DataViewContainer(BasicContainer basicContainer)
	{
		super(basicContainer);
	}


}
