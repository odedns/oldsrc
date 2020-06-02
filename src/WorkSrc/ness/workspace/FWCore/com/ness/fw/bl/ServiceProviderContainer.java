/*
 * Author: yifat har-nof
 * @version $Id: ServiceProviderContainer.java,v 1.1 2005/04/14 14:01:45 yifat Exp $
 */
package com.ness.fw.bl;

import com.ness.fw.common.auth.UserAuthData;

/**
 * Service Provider Object Container.
 * 
 * <p>
 * A container for the data required to the service provider methods (ServiceProvider's).
 * </p>
 */
public abstract class ServiceProviderContainer extends BasicContainer
{
	/**
	 * Creates new ServiceProviderContainer Object
	 */
	public ServiceProviderContainer()
	{
		super();
	}

	/**
	 * Creates new ServiceProviderContainer Object
	 * @param userAuthData Contains the basic authorization data of the current user.
	 */
	public ServiceProviderContainer(UserAuthData userAuthData)
	{
		super(userAuthData);
	}

	/**
	 * Creates new ServiceProviderContainer Object
	 * @param basicContainer The <code>BasicContainer</code> to pass the basic parameters.
	 */
	public ServiceProviderContainer(BasicContainer basicContainer)
	{
		super(basicContainer);
	}

}
