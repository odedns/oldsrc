/*
 * Created on 22/12/2004
 * 
 * @author Alexey Levin
 * @version $Id: CacheFactory.java,v 1.2 2005/05/04 08:46:49 alexey Exp $
 */
package com.ness.fw.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ness.fw.cache.config.CacheConfigManager;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.config.CacheDefinition;
import com.ness.fw.cache.config.CacheEntityConfig;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

/**
 * CacheFactory is a factory for all kinds of cache. Each cache can be defined 
 * as createOnStart and such cache will be created by CacheFactory on system starting.
 * In the same way any cache entity can be defined as loadOnStart and such entity will be
 * created and loaded on system starting.
 * CacheFactory maintains map of all existing caches.
 * 
 */
public class CacheFactory
{
	/**
	* Contains entity id with its entity cache association.
	* Some keys may point to the same value.
	*/ 
	private Map allCaches;
	
	/**
	 * A singleton instance.
	 */
	private static CacheFactory instance;
	
	static
	{
		//Create a singleton instance
		instance = new CacheFactory();
	}
	
	/**
	 * Create a new instance.
	 */
	private CacheFactory()
	{
		//Get number of cache definitions
		int numberOfCacheDefinitions;
		try
		{
			numberOfCacheDefinitions = SystemResources.getInstance().getInteger(CacheConfigManager.NUMBER_OF_CACHE_DEFINITIONS_KEY);
		}
		catch (ResourceException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".initialize failed to get system property [" + CacheConfigManager.NUMBER_OF_CACHE_DEFINITIONS_KEY + "]");
			
			numberOfCacheDefinitions = 10;
		}
		
		allCaches = new HashMap(numberOfCacheDefinitions + 1, 1);
	}
	
	/**
	 * Returns CacheFactory instance. 
	 * @return
	 */
	public static CacheFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * Initializes cache.
	 * @param confFilesRoot
	 * @throws CacheException
	 */
	public void initialize(ArrayList confFilesRoot) throws CacheException
	{
		//Init cache configuration
		CacheConfigFactory.initialize(confFilesRoot);
		
		synchronized(allCaches)
		{
			//Clear the allCaches map
			allCaches.clear();
		}
		
		//Create caches defined as createOnStart
		ArrayList lsInitOnStart = CacheConfigFactory.getCacheConfigManager().getCreateOnStartCacheNames();
		if(lsInitOnStart != null)
		{
			int size = lsInitOnStart.size();
			String cacheName;
			Cache cache;
			for(int i = 0; i < size; i++)
			{
				//Get cache name
				cacheName = (String)lsInitOnStart.get(i);
				
				synchronized(allCaches)
				{
					//Create cache
					cache = createCache(cacheName);
			
					//Put it on all caches map
					allCaches.put(cacheName, cache);
				}
			}
		}
		
		//Load entities that should loaded on start
		//Get cache entity config iterator
		Iterator iter = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfigIterator();
		CacheEntityConfig entityConfig;
		while(iter.hasNext())
		{
			//Get cache entity config 
			entityConfig = (CacheEntityConfig)iter.next();
			
			if(entityConfig.isLoadOnStart())
			{
				Logger.info(CacheManager.LOGGER_CONTEXT, "Loading on start entity [" + entityConfig.getEntityName() + "]...");
				
				//Load the entity
				CacheManager.getAll(entityConfig.getEntityName());
			}
		}
	}

	/**
	 * Searches specific cache into allCaches map and returns the entity cache.
	 * If the cache for specified entity not found then returns default cache implementation. 
	 * @param entity
	 * @return
	 * @throws CacheException
	 */	
	protected Cache getEntityCache(String entity) throws CacheException
	{
		//Get cache entity configuration
		CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
		
		//Get entity cache name 
		String cacheName = entityConfig.getCacheName();
		
		Cache cache;
		
		synchronized(allCaches)
		{
			//Get entity cache
			cache = (Cache)allCaches.get(cacheName);
			if(cache == null)
			{
				//Create cache
				cache = createCache(cacheName);
			
				//Put it on all caches map
				allCaches.put(cacheName, cache);
			}
		}
		
		return cache;
	}

	/**
	 * Creates cache.
	 * @param entity
	 * @return
	 */
	private Cache createCache(String cacheName) throws CacheException
	{
		Logger.info(CacheManager.LOGGER_CONTEXT, "creating cache [" + cacheName + "]...");
		
		//Get cache definition
		CacheDefinition cacheDefinition = CacheConfigFactory.getCacheConfigManager().getCacheDefinition(cacheName);
		
		//Get impl class name
		String implClassName = cacheDefinition.getImplClassName();
		
		Cache cache;
		try
		{
			//Create a new cache instance
			cache = (Cache)Class.forName(implClassName).newInstance();
			
			//Init cache
			cache.initalize(cacheName, cacheDefinition);
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "failed to create cache instance of [" + cacheName + "] from implementation [" + implClassName + "]", e);
			throw new CacheException("failed to create cache instance of [" + cacheName + "] from implementation [" + implClassName + "]", e);
		}
		
		return cache;
	}
}
