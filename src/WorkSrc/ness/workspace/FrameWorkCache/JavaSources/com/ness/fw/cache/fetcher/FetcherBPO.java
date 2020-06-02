/*
 * Created on: 31/01/2005
 * Author:  Alexey Levin
 * @version $Id: FetcherBPO.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.fetcher;

import com.ness.fw.bl.BusinessProcess;
import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.config.CacheDefinition;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.cache.implementation.CacheEntityContainer;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.logger.Logger;

/**
 * The fecther business processes that executes the fecther calls. 
 */
public class FetcherBPO extends BusinessProcess
{
	/**
	 * Fetches a value using local datasource fetcher.
	 * @param bpc
	 * @return
	 * @throws BusinessLogicException
	 */
	public static Object fetchById(FetcherBPC bpc) throws BusinessLogicException
	{
		//Get key from bpc
		Object key = bpc.getKey();
		
		if(key == null)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, FetcherBPO.class.getName() + ".fetchById failed: cannot perform fetchById: key is null");
			throw new BusinessLogicException("cannot perform fetchById: key is null");
		}
		
		CacheDefinition cacheDefinition;
		try
		{
			cacheDefinition = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(bpc.getEntityName(), false).getCacheDefinition();

			//Location must be local
			if(cacheDefinition.getCacheLocation() != CacheDefinition.LOCAL_LOCATION)
			{
				throw new CacheException("Cache location must be local");
			}
		}
		catch (CacheException e)
		{
			throw new BusinessLogicException("failed to get cache name of entity [" + bpc.getEntityName() + "]", e);
		}
		
		//Fetch the value
		Object value = FetcherFactory.getFetcher(cacheDefinition.getCacheLocation()).fetchById(bpc.getEntityName(), key);
		
		return value;
	}
	
	/**
	 * Fetches cache entity container using local datasource fetcher. 
	 * @param bpc
	 * @return
	 * @throws BusinessLogicException
	 */
	public static CacheEntityContainer fetchAll(FetcherBPC bpc) throws BusinessLogicException
	{
		CacheDefinition cacheDefinition;
		try
		{
			cacheDefinition = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(bpc.getEntityName(), false).getCacheDefinition();

			//Location must be local
			if(cacheDefinition.getCacheLocation() != CacheDefinition.LOCAL_LOCATION)
			{
				throw new CacheException("Cache location must be local");
			}
		}
		catch (CacheException e)
		{
			throw new BusinessLogicException("failed to get cache name of entity [" + bpc.getEntityName() + "]", e);
		}
		
		return FetcherFactory.getFetcher(cacheDefinition.getCacheLocation()).fetchAll(bpc.getEntityName());
	}
}
