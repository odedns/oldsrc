/*
 * Created on 22/12/2004
 * 
 * @author Alexey Levin
 * @version $Id: Cache.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache;

import java.util.Map;

import com.ness.fw.cache.config.CacheDefinition;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.cache.implementation.CacheEntityContainer;

/**
 * Cache interface provides all cache operation.
 */
public interface Cache
{
	/**
	 * Initializes cache.
	 * @throws CacheException
	 */
	public void initalize(String cacheName, CacheDefinition cacheDefinition) throws CacheException;
	
	/**
	* Returns object by key and entity from the cache.
	*/
	public Object get(String entity, Object key);

	/**
	* Returns all the cached objects from the specified entity cache.
	*/
	public CacheEntityContainer getAll(String entity);

	/**
	* Puts value specified by key and entity into the cache.
	*/
	public void put(String entity, Object key, Object value);
	
	/**
	 * Copies all of the mappings from the specified map to this cache. 
	 * The effect of this call is equivalent to that of calling put(entity, key, value) on this cache 
	 * once for each mapping from key to value in the specified map
	 * @param entity
	 * @param items
	 */
	public void put(String entity, Map items);

	/**
	* Removes an object specified by key and entity from the cache.
	*/
	public void remove(String entity, Object key);

	/**
	* Removes an object specified by key and entity from the cache.
	* Returns the removed object.
	*/
	public Object take(String entity, Object key);

	/**
	* Puts entityContainer into the cache entry specified by entity.
	* The old container stored into this cache entry will be replaced by itemsContainer.
	*/
	public void putAll(String entity, CacheEntityContainer entityContainer);

	/**
	* Clears the cache entry specified by entity.
	*/
	public void clear(String entity);
	
	/**
	 * Returns true if the cache container specified by <code>entity<code> contains a mapping for the specified key.
	 * @param entity
	 * @param key
	 * @return
	 */	
	public boolean containsKey(String entity, Object key);
}
