/*
 * Author: yifat har-nof
 * @version $Id: BusinessProcessContainer.java,v 1.2 2005/04/14 14:01:45 yifat Exp $
 */
package com.ness.fw.bl;

import com.ness.fw.common.auth.UserAuthData;

/**
 * Business Process Object Container.
 * 
 * <p>
 * A container for the data required to the business process methods (BusinessProcess's).
 * </p>
 */
public abstract class BusinessProcessContainer extends BasicContainer
{
	/**
	 * Creates new BusinessProcessContainer Object
	 */
	public BusinessProcessContainer()
	{
		super();
	}

	/**
	 * Creates new BusinessProcessContainer Object
	 * @param userAuthData Contains the basic authorization data of the current user.
	 */
	public BusinessProcessContainer(UserAuthData userAuthData)
	{
		super(userAuthData);
	}

	/**
	 * Creates new BusinessProcessContainer Object
	 * @param basicContainer The <code>BasicContainer</code> to pass the basic parameters.
	 */
	public BusinessProcessContainer(BasicContainer basicContainer)
	{
		super(basicContainer);
	}

}
