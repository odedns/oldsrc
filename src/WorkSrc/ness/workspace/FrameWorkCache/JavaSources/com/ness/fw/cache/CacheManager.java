/*
 * Created on 22/12/2004
 * 
 * @author Alexey Levin
 * @version $Id: CacheManager.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.common.logger.Logger;

/**
 * CacheManager is a facade for all cache operations defined in <code>Cache<code> interface.
 * A general task of CacheManager is receive request for cache service, switch it to the specific cache
 * and optionally return result.
 * CacheManager is only one way to get cache service.
 */
public class CacheManager
{
	final static public String LOGGER_CONTEXT = "CACHE";
	
	/**
     * Returns all the cached objects from the specified entity cache.
	 * 
	 * @param entityId entity id.
	 * @return container with all cached objects
	 */
	public static List getAll(String entity)
	{
		ArrayList all = null;
		
		try
		{
			all = new ArrayList(getEntityCache(entity).getAll(entity).getAll());
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, "entity cache not found for entity [" + entity + "]", e);
		}
		
		return all;
	}

	/**
	 * Returns object by id from the entity cache specified by entityId.
	 * @param key cache item key
	 * @param entity entity name
	 * @return
	 */
	public static Object get(String entity, Object key)
	{
		Object value = null;
		
		try
		{
			value = getEntityCache(entity).get(entity, key);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, "entity cache not found for entity [" + entity + "]", e);
		}
		
		return value;
	}

	/**
	 * Puts item into entity cache specified by entity.
	 * @param key cache item key
	 * @param item item to be put
	 * @param entity
	 */
	public static void put(String entity, Object key, Object value)
	{
		//Check input: both key and value cannot be null
		if(key == null || value == null)
		{
			throw new NullPointerException("cannot put into the cache null key or value");
		}
		
		try
		{
			getEntityCache(entity).put(entity, key, value);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, "entity cache not found for entity [" + entity + "]", e);
		}
	}
	
	/**
	 * Copies all of the mappings from the specified map to this cache. 
	 * The effect of this call is equivalent to that of calling put(entity, key, value) on this cache 
	 * once for each mapping from key to value in the specified map
	 * @param entity
	 * @param items
	 */
	public void put(String entity, Map items)
	{
		//Check input: cannot put null key or value into the cache
		if(items.containsKey(null) || items.containsValue(null))
		{
			throw new NullPointerException("cannot put into the cache null key or value");
		}
		
		try
		{
			getEntityCache(entity).put(entity, items);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, getClass().getName() + ".put failed: entity cache not found for entity [" + entity + "]", e);
		}
	}

	/**
	 * Removes an object specified by key from entity cache specified by entity.
	 * @param key item key
	 * @param entity entity
	 */
	public static void remove(String entity, Object key)
	{
		try
		{
			getEntityCache(entity).remove(entity, key);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, "entity cache not found for entity [" + entity + "]", e);
		}
	}

	/**
	 * Removes an object specified by key from entity cache specified by entity.
	 * Returns the removed object.
	 * @param key cache item key
	 * @param entity entity
	 * @return
	 */
	public static Object take(String entity, Object key)
	{
		Object value = null;
		
		try
		{
			value = getEntityCache(entity).take(entity, key);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, "entity cache not found for entity [" + entity + "]", e);
		}
		
		return value;
	}

	/**
	 * Removes the old content of the cache entity and puts all items from <code>items<code> into the cache entry specified by entity.
	 * @param itemsContainer cache items container to be put.
	 * @param entity entity.
	 */
	public static void putAll(String entity, Map items)
	{
		//Check input: cannot put null key or value into the cache
		if(items.containsKey(null) || items.containsValue(null))
		{
			throw new NullPointerException("cannot put into the cache null key or value");
		}
		
		try
		{
			//Get entity cache
			Cache cache = getEntityCache(entity);
			
			//TODO two next lines should be in critical section
			
			//Clear the entity
			cache.clear(entity);
			
			//Put all
			cache.put(entity, items);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, "entity cache not found for entity [" + entity + "]", e);
		}
	}

	/**
	 * Clears the cache specified by entityId.
	 * @param entityId entity id
	 */
	public static void clear(String entity)
	{
		try
		{
			getEntityCache(entity).clear(entity);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, "entity cache not found for entity [" + entity + "]", e);
		}
	}
	
	/**
	 * Returns true if the cache container specified by <code>entity<code> contains a mapping for the specified key.
	 * @param entity
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String entity, Object key)
	{
		boolean res = false;
		
		try
		{
			res = getEntityCache(entity).containsKey(entity, key);
		}
		catch (CacheException e)
		{
			Logger.error(LOGGER_CONTEXT, CacheManager.class.getName() + ".containsKey failed. Entity cache not found for entity [" + entity + "]", e);
		}
		
		return res;
	}
	
	/**
	 * Returns the entity cache.
	 * @param entity
	 * @return
	 */
	private static Cache getEntityCache(String entity) throws CacheException
	{
		return CacheFactory.getInstance().getEntityCache(entity); 
	}
}
