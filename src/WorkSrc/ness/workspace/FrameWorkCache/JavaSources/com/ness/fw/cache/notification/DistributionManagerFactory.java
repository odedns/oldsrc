/*
 * Created on: 20/01/2005
 * Author:  Alexey Levin
 * @version $Id: DistributionManagerFactory.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.notification;

import com.ness.fw.cache.config.CacheDefinition;
import com.ness.fw.cache.exceptions.CacheException;

/**
 * Factory for creating distribution manager.
 */
public class DistributionManagerFactory
{
	/**
	 * Creates a distribution manager.
	 * @param distributionManagerType
	 * @param cacheName
	 * @return distribution manager.
	 */
	public static DistributionManager create(int distributionManagerType, String cacheName, CacheDefinition cacheDefinition) throws CacheException
	{
		DistributionManager mngr;
		
		if(distributionManagerType == CacheDefinition.DISTRIBUTION_MANAGER_JGROUPS)
		{
			mngr = new JGroupsDistributionManager(cacheName, cacheDefinition);	
		}
		else
		{
			//Create default
			//TODO default should be defined as external parameter 
			mngr = new JGroupsDistributionManager(cacheName, cacheDefinition);	
		}
		
		return mngr;
	}
}
