/*
 * Created on 22/12/2004
 * @author Alexey Levin
 * @version $Id: FwCacheImpl.java,v 1.3 2005/05/04 08:46:49 alexey Exp $
 */
package com.ness.fw.cache.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ness.fw.cache.Cache;
import com.ness.fw.cache.CacheEventHandler;
import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.config.CacheDefinition;
import com.ness.fw.cache.config.CacheEntityConfig;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.cache.fetcher.FetcherFactory;
import com.ness.fw.cache.notification.DistributionManager;
import com.ness.fw.cache.notification.DistributionManagerFactory;
import com.ness.fw.common.logger.Logger;

/**
 * Cache implementation.
 * Maintains a map of all entityies stored in this cache,
 * sends and handles a cache notifications.
 */
public class FwCacheImpl implements Cache, CacheEventHandler
{
	/**
	 * Map of all entities.
	 */
	private HashMap allEntities;
	
	/**
	 * Cache name.
	 */
	private String cacheName;
	
	/**
	 * Cache definition.
	 */
	private CacheDefinition cacheDefinition;
	
	/**
	 * Notifies another instances of the same cache entity about changes such as remove, put or clear.
	 * Also used by distributed fetcher.
	 */
	private DistributionManager distributionManager;
	
	/**
	 * Creates a new instance.
	 *
	 */
	public FwCacheImpl()
	{
		//Create an all entities map
		allEntities = new HashMap();
	}
	
	/**
	 * Returns an entity container.
	 * If the entity container yet not exist then create and initialize a new one. 
	 */
	private CacheEntityContainer getCacheEntityContainer(String entity)
	{
		CacheEntityContainer container = null;
		
		synchronized(allEntities)
		{
			//Get the entity container
			container = (CacheEntityContainer)allEntities.get(entity);
		
			//If the entity container yet not exist then create and initialize a new one
			if(container == null)
			{
				//Create a new container
				container = createCacheEntityContainer(entity);

				//Put a newly created container to the all entities map
				allEntities.put(entity, container);		
			}
		}
				
		return container;
	}

	/**
	 * Creates a new cache entity container.
	 * @param entityName entity name.
	 * @return a new cache entity container.
	 */
	private CacheEntityContainer createCacheEntityContainer(String entityName)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "creating container for entity [" + entityName + "]...");
		
		//Create a new cache entity container
		CacheEntityContainer container = new CacheEntityContainer(entityName);
			
		//Initialize cache entity container
		try
		{
			//Get entity config
			CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entityName, true);
		
			if(!entityConfig.isSizeZero())
			{
				if(((cacheDefinition.isDistributed() || cacheDefinition.isCentral()) && (entityConfig.getNotificationType() == CacheEntityConfig.FULL_DISTRIBUTION_NOTIFICATION_TYPE))
					|| entityConfig.isLoadOnStart())
				{
					//Load all items to the entity
					loadEntity(entityName, container);
				}
				else if((cacheDefinition.isDistributed() || cacheDefinition.isCentral()) 
						&& (entityConfig.getNotificationType() == CacheEntityConfig.INVALIDATE_NOTIFICATION_TYPE))
				{
					//Get load keys only from another instances
					loadKeys(entityName, container);
				}
			}
		}
		catch (CacheException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "error occured while initializing cache entity [" + entityName + "]. Entity may not been initialized properly.");
		}

		return container;
	}
	
	/**
	 * Loads key set to the cache
	 * @param entityName
	 * @param container
	 */
	private void loadKeys(String entityName, CacheEntityContainer container)
	{
		//Get key set
		Set keySet = distributionManager.notifyKeySet(entityName);
		
		if(keySet != null)
		{
			//Put keys only
			container.putAll(keySet);
		}
	}

	/**
	 * @param entity
	 * @param key
	 * @return
	 */
	private Object getFromDistributedCaches(String entity, Object key)
	{
		Object value = null;
		
		//First, if the cache is distributed or central, look for container in other instances of cache.
		if(cacheDefinition.isCentral() || cacheDefinition.isDistributed())
		{
			value = distributionManager.notifyGet(entity, key);
		}

		return value;
	}

	/**
	 * Fetches cache entity container from other instances of the cache.
	 * @param entity
	 * @return cache entity container.
	 */
	private CacheEntityContainer getAllFromDistributedCaches(String entity)
	{
		CacheEntityContainer all = null;
		
		//First, if the cache is distributed or central, look for container in other instances of cache.
		if(cacheDefinition.isCentral() || cacheDefinition.isDistributed())
		{
			all = distributionManager.notifyGetAll(entity);
		}

		return all;
	}

	/**
	 * @return
	 */
	private boolean isEntityLoaderExist(String entity)
	{
		boolean res = true;
		
		try
		{
			res = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true).isLoaderExist();
		}
		catch (CacheException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".needLoadFromDatasource failed to get entity configuration", e);
		}
		
		return res;
	}

	/**
	 * Loads all the entity items.
	 * @param entityName
	 * @param container
	 */
	private void loadEntity(String entityName, CacheEntityContainer container)
	{
		//Try to fetch from other instances of this cache
		CacheEntityContainer all = getAllFromDistributedCaches(entityName);
				
		//Need fetch from datasource
		if(all == null)
		{
			//Fetch all from datasource
			all = FetcherFactory.getFetcher(cacheDefinition.getCacheLocation()).fetchAll(entityName);
		}
			
		if(all != null)
		{
			//Fill the cache
			container.putAll(all);
		}
	}
	
	/**
	 * Tells whether all the entity items are valid.
	 * @param entity
	 * @return true if all the entity items are valid, false otherwise.
	 */
	private boolean isEntityValid(String entity, CacheEntityContainer container, CacheEntityConfig cacheEntityConfig)
	{
		boolean res = false;
		
		try
		{
			if(container.isFull() && 
				(cacheEntityConfig.getNotificationType() == CacheEntityConfig.FULL_DISTRIBUTION_NOTIFICATION_TYPE || 
					!container.containsValue(null)) && 
				cacheEntityConfig.isSizeUnlimited())
			{
				res = true;
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".isEntityValid failed", e);
		}
		
		return res;
	}
	
	/**
	 * Tells whether an entity specified by <code>entityName<code> already exists in the cache.
	 * @param entityName entity name.
	 * @return true if an entity specified by <code>entityName<code> already exists in the cache, false otherwise.
	 */
	private boolean isEntityExists(String entityName)
	{
		boolean exists;
		
		synchronized(allEntities)
		{
			exists = allEntities.containsKey(entityName);
		}
		
		return exists;
	}
	
 
	//-------------------- Cache implementation ------------------------
	/**
	 * @see com.ness.fw.cache.Cache#initalize(String cacheName)
	 */
	public void initalize(String cacheName, CacheDefinition cacheDefinition) throws CacheException
	{
		//Set cache name
		this.cacheName = cacheName;
		
		//Set cache definition
		this.cacheDefinition = cacheDefinition;

		//Create a distribution manager if cache is distrinbuted or central
		if(cacheDefinition.isDistributed() || cacheDefinition.isCentral())
		{
			//Get distribution manager type
			int distributionManagerType = cacheDefinition.getDistributionManagerType();
			
			//Create distribution manager
			distributionManager = DistributionManagerFactory.create(distributionManagerType, cacheName, cacheDefinition);
			
			//Set cache event handler to the distribution manager
			distributionManager.setCacheEventHandler(this);
		}
	}
	
	/**
	 * @see com.ness.fw.cache.Cache#get(java.lang.String, java.lang.Object)
	 */
	public Object get(String entity, Object key)
	{
		Object value = null;
		
		//Get entity container
		CacheEntityContainer container = getCacheEntityContainer(entity);
	
		//Get the value
		value = container.get(key);
	
		//If value not found try to fetch
		if(value == null)
		{
			//Try to fetch from other instances of this cache
			value = getFromDistributedCaches(entity, key);
			
			//Need fetch from datasource
			if(value == null && isEntityLoaderExist(entity))
			{
				//Fetch the value from datasource
				value = FetcherFactory.getFetcher(cacheDefinition.getCacheLocation()).fetchById(entity, key);
			
				//If succeeded to fetch
				if(value != null)
				{
					//Update cache
					put(entity, key, value); 
				}
			}
		}
		
		return value;
	}

	/**
	 * @see com.ness.fw.cache.Cache#getAll(java.lang.String)
	 */
	public CacheEntityContainer getAll(String entity)
	{
		CacheEntityContainer res = null;
			
		//Get entity container
		CacheEntityContainer container = getCacheEntityContainer(entity);
		
		try
		{
			//Get cache entity config
			CacheEntityConfig cacheEntityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
			
			if(!isEntityValid(entity, container, cacheEntityConfig))
			{
				//Try to fetch from other instances of this cache
				res = getAllFromDistributedCaches(entity);
				
				if(res != null)
				{
					if(!cacheEntityConfig.isSizeZero())
					{
						//update local cache
						container.putAll(res);
					}
				}
				else if(isEntityLoaderExist(entity))
				{
					//Try to fetch from datasource
					res = FetcherFactory.getFetcher(cacheDefinition.getCacheLocation()).fetchAll(entity);
			
					if(res != null)
					{
						//Update all caches
						putAll(entity, res);
					}
				}
			}
		
			if(res == null)
			{
				container.setFull(true);
			
				res = container;
			}
		}
		catch (CacheException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".getAll failed to update cache", e);
			
			res = container;
		}
		
		//TODO Return this container or its copy
		return res;
	}

	/**
	 * @see com.ness.fw.cache.Cache#put(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void put(String entity, Object key, Object value)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "Putting to cache [" + cacheName + "] entity [" + entity + "] key = [" + key + "] value = [" + value + "]...");
		
		//Get the entity container
		CacheEntityContainer container = getCacheEntityContainer(entity);
		
		try
		{
			//Get entity config
			CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
		
			if(!entityConfig.isSizeZero())//TODO if limited?
			{
				//Put
				container.put(key, value);
			}
			
			//Notify put if a cache type is distributed or central
			if(cacheDefinition.isDistributed() || cacheDefinition.isCentral())
			{
				if(entityConfig.getNotificationType() == CacheEntityConfig.FULL_DISTRIBUTION_NOTIFICATION_TYPE)
				{
					distributionManager.notifyPut(entity, key, value);
				}
				else if(entityConfig.getNotificationType() == CacheEntityConfig.INVALIDATE_NOTIFICATION_TYPE)
				{
					distributionManager.notifyPut(entity, key, null);
				}
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put error occured while putting value by key=" + key + " into entity [" + entity + "]", e);
		}
	}

	/**
	 * @see com.ness.fw.cache.Cache#put(java.lang.String, java.util.Map)
	 */
	public void put(String entity, Map items)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "Putting to cache [" + cacheName + "] entity [" + entity + "] items map...");
		
		//Get the entity container
		CacheEntityContainer container = getCacheEntityContainer(entity);
		
		try
		{
			//Get entity config
			CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
		
			if(!entityConfig.isSizeZero())//TODO if limited?
			{
				//Put
				container.put(items);
			}
			
			//Notify put if a cache type is distributed or central
			if(cacheDefinition.isDistributed() || cacheDefinition.isCentral())
			{
				if(entityConfig.getNotificationType() == CacheEntityConfig.FULL_DISTRIBUTION_NOTIFICATION_TYPE)
				{
					distributionManager.notifyPut(entity, items);
				}
				else if(entityConfig.getNotificationType() == CacheEntityConfig.INVALIDATE_NOTIFICATION_TYPE)
				{
					distributionManager.notifyPut(entity, items.keySet());
				}
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put error occured while putting items map into entity [" + entity + "]", e);
		}
	}

	/**
	 * @see com.ness.fw.cache.Cache#remove(java.lang.String, java.lang.Object)
	 */
	public void remove(String entity, Object key)
	{
		take(entity, key);
	}
	
	/**
	 * @see com.ness.fw.cache.Cache#take(java.lang.String, java.lang.Object)
	 */
	public Object take(String entity, Object key)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "removing key = [" + key + "] from cache [" + cacheName + "] entity [" + entity + "]...");
		
		Object value = null;
		
		//Get the entity container
		CacheEntityContainer container = getCacheEntityContainer(entity);
		
		if(container.containsKey(key))
		{
			//Get the value
			value = container.remove(key);
		
			//Notify put if a cache type is distributed or central
			if(cacheDefinition.isDistributed() || cacheDefinition.isCentral())
			{
				distributionManager.notifyRemove(entity, key);
			}
		}
		
		return value;
	}
	
	/**
	 * @see com.ness.fw.cache.Cache#putAll(java.lang.String, com.ness.fw.cache.implementation.CacheEntity)
	 */
	public void putAll(String entity, CacheEntityContainer entityContainer)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "Replacing cache [" + cacheName + "] entity [" + entity + "] by putAll...");
		
		//Get entity container
		CacheEntityContainer container = getCacheEntityContainer(entity);
				
		try
		{
			//Get entity config
			CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
			
			if(!entityConfig.isSizeZero())//TODO if limited?
			{
				//Put all
				container.putAll(entityContainer);
			}
			
			//Notify put if a cache type is distributed or central
			if(cacheDefinition.isDistributed() || cacheDefinition.isCentral())
			{
				if(entityConfig.getNotificationType() == CacheEntityConfig.FULL_DISTRIBUTION_NOTIFICATION_TYPE)
				{
					distributionManager.notifyPutAll(entity, container);
				}
				else if(entityConfig.getNotificationType() == CacheEntityConfig.INVALIDATE_NOTIFICATION_TYPE)
				{
					distributionManager.notifyPutAll(entity, new HashSet(container.keySet()));
				}
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".putAll error occured while sending notification about put cache entity container for entity [" + entity + "]", e);
		}
	}
	
	/**
	 * @see com.ness.fw.cache.Cache#clear(java.lang.String)
	 */
	public void clear(String entity)
	{
		//Get entity container
		CacheEntityContainer container = getCacheEntityContainer(entity);
		
		//Clear
		container.clear();
		
		//Notify put if a cache type is distributed or central
		if(cacheDefinition.isDistributed() || cacheDefinition.isCentral())
		{
			distributionManager.notifyClear(entity);
		}
	}

	/**
	 * @see com.ness.fw.cache.Cache#containsKey(java.lang.String, java.lang.Object)
	 */
	public boolean containsKey(String entity, Object key)
	{
		return getCacheEntityContainer(entity).containsKey(key);
	}
	//------------------------- end of Cache implementation ----------------------


	//-------------------- CacheEventHandler implementation ---------------------------//
	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handlePut(java.lang.Object, java.lang.Object)
	 */
	public void handlePut(String entity, Object key, Object value)
	{
		try
		{
			if(isEntityExists(entity))
			{
				//Get entity config
				CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
			
				if(!entityConfig.isSizeZero())
				{
					//Get container
					CacheEntityContainer container = getCacheEntityContainer(entity);
			
					//Put into container
					container.put(key, value);
				}
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handlePut(String entity, Object key, Object value) error occured while handling put event key=" + key + ", entity [" + entity + "]", e);
		}
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handlePut(java.lang.String, java.util.Map)
	 */
	public void handlePut(String entity, Map items)
	{
		try
		{
			if(isEntityExists(entity))
			{
				//Get entity config
				CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
			
				if(!entityConfig.isSizeZero())
				{
					//Get container
					CacheEntityContainer container = getCacheEntityContainer(entity);
			
					//Put into container
					container.put(items);
				}
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handlePut(String entity, Map items) error occured while handling put event, entity [" + entity + "]", e);
		}
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handlePut(java.lang.String, java.util.Set)
	 */
	public void handlePut(String entity, Set keySet)
	{
		try
		{
			if(isEntityExists(entity))
			{
				//Get entity config
				CacheEntityConfig entityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
			
				if(!entityConfig.isSizeZero())
				{
					//Get container
					CacheEntityContainer container = getCacheEntityContainer(entity);
			
					//Put into container
					container.put(keySet);
				}
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handlePut(String entity, Set keySet) error occured while handling put event, entity [" + entity + "]", e);
		}
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handlePutAll(com.ness.fw.cache.implementation.CacheEntityContainer)
	 */
	public void handlePutAll(String entity, CacheEntityContainer container)
	{
		try
		{
			if(isEntityExists(entity) && !CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true).isSizeZero())
			{
				//Put all
				getCacheEntityContainer(entity).putAll(container);
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handlePutAll error occured while handling putAll event entity [" + entity + "]", e);
		}
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handlePutAll(java.util.Set)
	 */
	public void handlePutAll(String entity, Set keySet)
	{
		try
		{
			if(isEntityExists(entity) && !CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true).isSizeZero())
			{
				//Put all
				getCacheEntityContainer(entity).putAll(keySet);
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handlePutAll(String entity, Set keySet) error occured while handling putAll event entity [" + entity + "]", e);
		}
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handleGet(java.lang.Object)
	 */
	public Object handleGet(String entity, Object key)
	{
		Object value = null;
		
		if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
		{
			//TODO may cause deadlock since bug in central
			//Fetch even not exist in this instance
			value = get(entity, key);
		}
		else
		{
			if(isEntityExists(entity))
			{
				//Get from container
				value = getCacheEntityContainer(entity).get(key);
			}
		}
		
		return value;
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handleGetAll()
	 */
	public CacheEntityContainer handleGetAll(String entity)
	{
		CacheEntityContainer all = null;
		
		if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
		{
			//Fetch anyway
			all = getAll(entity);
		}
		else if(isEntityExists(entity))
		{
			//Get entity container
			CacheEntityContainer container = getCacheEntityContainer(entity);
			
			//Get cache entity config
			CacheEntityConfig cacheEntityConfig;
			try
			{
				cacheEntityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
	
				if(isEntityValid(entity, container, cacheEntityConfig))
				{
					//Return the container
					all = container;
				}
			}
			catch (CacheException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handleGetAll failed", e);
			}
		}
		
		return all;
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handleRemove(java.lang.Object)
	 */
	public void handleRemove(String entity, Object key)
	{
		if(isEntityExists(entity))
		{
			getCacheEntityContainer(entity).remove(key);
		}
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handleClear()
	 */
	public void handleClear(String entity)
	{
		if(isEntityExists(entity))
		{
			getCacheEntityContainer(entity).clear();
		}
	}

	/**
	 * @see com.ness.fw.cache.CacheEventHandler#handleKeySet()
	 */
	public Set handleKeySet(String entity)
	{
		Set res = null;
		
		if(isEntityExists(entity))
		{
			//Get entity container
			CacheEntityContainer container = getCacheEntityContainer(entity);
		
			try
			{
				//Get cache entity config
				CacheEntityConfig cacheEntityConfig = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entity, true);
			
//				if(isEntityValid(entity, container, cacheEntityConfig) && cacheEntityConfig.isSizeUnlimited())
				//For keySet it is not necessary that entity will valid 
				if(cacheEntityConfig.isSizeUnlimited())
				{
					res = new HashSet(getCacheEntityContainer(entity).keySet());
				}
				else if(cacheDefinition.isCentral() && cacheDefinition.isCentralNode())
				{
					//Get anyway
					res = distributionManager.notifyKeySet(entity);
				}
			}
			catch (Throwable e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".handleKeySet error occured while handling keySet event, entity [" + entity + "]", e);
			}
		}
		
		return res;
	}
	//-------------------- end of CacheEventHandler implementation ---------------------------//
}
