/*
 * Created on: 16/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheConfigManager.java,v 1.2 2005/05/16 15:53:07 alexey Exp $
 */
package com.ness.fw.cache.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.common.logger.Logger;

/**
 * Cache configuration manager provides access to cache and cache entity configurations. 
 */
abstract public class CacheConfigManager
{
	public static final String NUMBER_OF_CACHE_DEFINITIONS_KEY = "cache.numberOfCacheDefinitions"; 
	public static final String NUMBER_OF_ENTIIES_KEY = "cache.numberOfEntities";
	 
	/**
	 * Maps cache to definition.
	 */
	protected Map cache2Def;
	
	/**
	 * Maps entity to its configuration.
	 */
	protected Map entity2Config;
	
	/**
	 * Default cache entity configuration.
	 */
	protected CacheEntityConfig defaultEntityConfig;
	
	/**
	 * List contains cache names which should initialize on start.
	 */
	protected ArrayList lsCreateOnStart;
	
	/**
	 * Flag tells whether cache is initialized.
	 */
	protected boolean initialized;
	
	/**
	 * Initializes cache configuration.
	 * @param lsInitData
	 * @throws CacheException
	 */
	abstract public void initialize(ArrayList lsInitData) throws CacheException;
	
	/**
	 * Returns the cache definition specified by cache name.
	 * @param cacheName cache name.
	 * @return cache definition.
	 */
	public CacheDefinition getCacheDefinition(String cacheName) throws CacheException
	{
		if(!isCacheInitialized())
		{
			throw new CacheException("cache not initialized");
		}
		
		//Get cache definition
		CacheDefinition def = (CacheDefinition)cache2Def.get(cacheName);
		
		if(def == null)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "cache definition not found for cache [" + cacheName + "]");
			throw new CacheException("cache definition not found for cache [" + cacheName + "]");
		}
		
		return def;
	}
	
	/**
	 * Return a cache entity configuration.
	 * @param entity a cache entity name.
	 * @param activeOnly tells whether get active entity only
	 * @return a cache entity configuration.
	 */
	public CacheEntityConfig getCacheEntityConfig(String entity, boolean activeOnly) throws CacheException
	{
		if(!isCacheInitialized())
		{
			throw new CacheException("cache not initialized");
		}
		
		CacheEntityConfig conf = (CacheEntityConfig)entity2Config.get(entity);
		
		if(conf == null || (!conf.isActive() && activeOnly))
		{
			Logger.debug(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".getCacheEntityConfig configuration not found for entity [" + entity + "]. Will used default configuration.");
			
			//Try to get default entity configuration
			conf = defaultEntityConfig;
			
			if(conf == null)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".getCacheEntityConfig default cache entity configuration not found");
				throw new CacheException("entity configuration not found for entity [" + entity + "]");
			}
		}
		
		return conf;
	}
	
	/**
	 * Returns a collection iterator of the defined cache entity configurations 
	 * @return a collection iterator of the defined cache entity configurations
	 */
	public Iterator getCacheEntityConfigIterator() throws CacheException
	{
		if(!isCacheInitialized())
		{
			throw new CacheException("cache not initialized");
		}
		
		return entity2Config.values().iterator();
	}
	
	public ArrayList getCreateOnStartCacheNames() throws CacheException
	{
		if(!isCacheInitialized())
		{
			throw new CacheException("cache not initialized");
		}
		
		return lsCreateOnStart;
	}
	
	/**
	 * 
	 * @return true if cache is initialized, false otherwise.
	 */
	public boolean isCacheInitialized()
	{
		return initialized;
	}
}
