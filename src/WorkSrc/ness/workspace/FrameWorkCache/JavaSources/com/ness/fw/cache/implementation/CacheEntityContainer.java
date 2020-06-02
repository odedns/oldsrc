/*
 * Created on: 24/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheEntityContainer.java,v 1.1 2005/02/24 08:42:02 alexey Exp $
 */
package com.ness.fw.cache.implementation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.config.CacheEntityConfig;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.common.lock.MultipleReadersSingleWriterLock;
import com.ness.fw.common.lock.SynchronizationLockException;
import com.ness.fw.common.logger.Logger;

/**
 * CacheEntityContainer served as container for storing cache items.
 * Provides access to specific item and to all the items. For improving perfomance and storing items order,
 * maintains two copies of items, one in a map for accessing item by id,  and second in a list
 * for accessing all the items.
 * Synchronized. 
 */
public class CacheEntityContainer implements Serializable
{
	/**
	 * Entity name.
	 */
	private String entityName;
	
	/**
	* Objects map associates object id with object.
	* Provides access to specific object by id.
	*/
	private Map itemsMap;

	/**
	* Contains all the objects.
	* Provides access to the all cached objects and keeps object order.
	*/
	private ArrayList itemsList;
	
	/**
	 * Tells whether container contains all the items which 
	 * can present into it. 
	 */
	private boolean isFull;
	
	/**
	 * Tells whether an items list is sorted. 
	 */
	private boolean sorted; 
	
	/**
	 * The lock object that manage the concurrency synchronization.
	 */
	private MultipleReadersSingleWriterLock lockObject = new MultipleReadersSingleWriterLock();
	
	/**
	 * Creates a new instance.
	 *
	 */
	public CacheEntityContainer(String entityName)
	{
		this.entityName = entityName;
		itemsMap = new HashMap();
		
		//Get entity config
		CacheEntityConfig entityConfig = getEntityConfig();
		
		//Create an items list only if there is comparator for the entity and compareOn=put
		if(entityConfig != null && entityConfig.isComparatorExists() && entityConfig.isCompareOnPut())
		{
			this.itemsList = new ArrayList();
		}
		
		sorted = false;
	}
	
	/**
	 * Creates a new instance.
	 * @param container
	 */
	public CacheEntityContainer(CacheEntityContainer container)
	{
		this.entityName = container.entityName;
		
		//Create an items map
		this.itemsMap = new HashMap(container.itemsMap);
		
		//Get entity config
		CacheEntityConfig entityConfig = getEntityConfig();
		
		//Create an items list only if there is comparator for the entity and compareOn=put
		if(entityConfig != null && entityConfig.isComparatorExists() && entityConfig.isCompareOnPut())
		{
			this.itemsList = new ArrayList(container.itemsList);
		}
		
		sorted = container.sorted;
	}

	/**
	* Returns object specified by key from itemsMap.
	*/
	public Object get(Object key)
	{
		Object value = null;
	
		try
		{
			lockObject.getReadLock();
			
			value = itemsMap.get(key);
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".get error occured");
		}
		finally
		{
			try
			{
				lockObject.releaseReadLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".get synchronization error", e);
			}
		}
		
		return value;
	}

	/**
	* Returns list of all cached objects in order they was added to the cache.
	*/
	public List getAll()
	{
		ArrayList res = null;
		
		try
		{
			lockObject.getReadLock();
			
			//Get cache entity configuration
			CacheEntityConfig config = getEntityConfig();
			
			if(config != null && config.isComparatorExists())
			{
				if(config.isCompareOnGetAll())
				{
					//Create items list
					res = new ArrayList(itemsMap.values());
					
					//Sort the list
					Collections.sort(res, config.getComparator());
				}
				else
				{
					//List should be sorted
					res = new ArrayList(itemsList);
					
					//Sort items list if not sorted yet
					if(!sorted)
					{
						sortItemsList();
					}
				}
			}
			else
			{
				//Create items list
				res = new ArrayList(itemsMap.values());
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".getAll error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseReadLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".getAll synchronization error", e);
			}
		}

		return res;
	}
	
	/**
	 * Puts a new item into cache entity.
	 * @param key item key
	 * @param value a value to be put
	 */
	public void put(Object key, Object value)
	{
		try
		{
			lockObject.getWriteLock();
				
			if(itemsMap.containsKey(key))
			{
				//Get old value
				Object oldValue = itemsMap.get(key);
			
				//Remove old value from the list
				removeFromItemsList(oldValue);
			}
		
			//Put into the map
			itemsMap.put(key, value);
		
			//Add to the list
			addToItemsList(value, true);
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseWriteLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put synchronization error", e);
			}
		}
	}
	
	/**
	 * Copies all of the mappings from the specified map to this cache entity container. 
	 * The effect of this call is equivalent to that of calling put(key, value) on this cache 
	 * once for each mapping from key to value in the specified map
	 * @param entity
	 * @param items
	 */
	public void put(Map items)
	{
		try
		{
			lockObject.getWriteLock();
				
			//Get entity config
			CacheEntityConfig config = getEntityConfig();
			
			if(config != null && config.isComparatorExists() && config.isCompareOnPut())
			{
				sorted = false;
				
				Iterator iter = items.keySet().iterator();
				while(iter.hasNext())
				{
					Object key = iter.next();
					Object value = items.get(key);
				
					if(itemsMap.containsKey(key))
					{
						//Get old value
						Object oldValue = itemsMap.get(key);
			
						//Remove old value from the list
						removeFromItemsList(oldValue);
					}
		
					//Put into the map
					itemsMap.put(key, value);
		
					//Add to the list
					addToItemsList(value, false);
				}
				
				//Sort the items list
				sortItemsList();
			}
			else
			{
				itemsMap.putAll(items);
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseWriteLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put synchronization error", e);
			}
		}
	}
	
	/**
	 * The effect of this call is equivalent to that of calling put(Map) where
	 * in the map all values are null.
	 * @param entity
	 * @param items
	 */
	public void put(Set keySet)
	{
		try
		{
			lockObject.getWriteLock();
				
			Iterator iter = keySet.iterator();
			while(iter.hasNext())
			{
				Object key = iter.next();
			
				if(itemsMap.containsKey(key))
				{
					//Get old value
					Object oldValue = itemsMap.get(key);
		
					//Remove old value from the list
					removeFromItemsList(oldValue);
				}
	
				//Put into the map
				itemsMap.put(key, null);
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseWriteLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".put synchronization error", e);
			}
		}
	}
	
	/**
	 * Replace the container.
	 * @param container
	 */
	public void putAll(CacheEntityContainer container)
	{
		try
		{
			lockObject.getWriteLock();
			
			sorted = container.sorted;
		
			//TODO assign or create a new one?
			//     if before putAll specially create the container then assignation is enough,
			//     but if using the container after putAll, this may be dangerous.
			itemsMap = container.itemsMap;
		
			//Get entity config
			CacheEntityConfig entityConfig = getEntityConfig();
		
			if(entityConfig != null && entityConfig.isComparatorExists() && entityConfig.isCompareOnPut())
			{
				itemsList = container.itemsList;
			}
			
			//Now container is full
			isFull = true;
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".putAll error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseWriteLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".putAll synchronization error", e);
			}
		}
	}
	
	/**
	 * Replace the container.
	 * @param container
	 */
	public void putAll(Set keySet)
	{
		try
		{
			lockObject.getWriteLock();
		
			//Update map
			itemsMap.clear();
			Iterator iter = keySet.iterator();
			while(iter.hasNext())
			{
				itemsMap.put(iter.next(), null);
			}
		
			//Get entity config
			CacheEntityConfig entityConfig = getEntityConfig();
		
			if(entityConfig != null && entityConfig.isComparatorExists() && entityConfig.isCompareOnPut())
			{
				//Clean the list
				itemsList.clear();
			}
			
			//Now container is full
			isFull = true;
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".putAll error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseWriteLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".putAll synchronization error", e);
			}
		}
	}
	
	/**
	 * Removes item specified by key from the cache entity.
	 * Returns the removed item.
	 * @param key
	 * @return removed item.
	 */
	public Object remove(Object key)
	{
		Object removed = null;
		
		try
		{
			lockObject.getWriteLock();
		
			//Remove from the map
			removed = itemsMap.get(key);
		
			//Get entity config
			CacheEntityConfig entityConfig = getEntityConfig();
		
			//Remove from the list
			removeFromItemsList(removed);
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".remove error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseWriteLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".remove synchronization error", e);
			}
		}
		
		return removed;
	}
	
	/**
	 * Clears the container.
	 */
	public void clear()
	{
		try
		{
			lockObject.getWriteLock();
			
			//Clear the map
			itemsMap.clear();
		
			//Get entity config
			CacheEntityConfig entityConfig = getEntityConfig();
		
			if(entityConfig != null && entityConfig.isComparatorExists() && entityConfig.isCompareOnPut())
			{
				//Clear the list
				itemsList.clear();
			}
			
			isFull = false;
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".clear() error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseWriteLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".clear() synchronization error", e);
			}
		}
	}
	
	/**
	 * Returns true if this map contains a mapping for the specified key.
	 * @param key key whose presence in this map is to be tested.
	 * @return
	 */
	public boolean containsKey(Object key)
	{
		boolean res = false;
		
		try
		{
			lockObject.getReadLock();
			
			res = itemsMap.containsKey(key);
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".containsKey error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseReadLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".containsKey synchronization error", e);
			}
		}
			
		return res;
	}
	
	/**
	 * Returns true if this map maps one or more keys to the specified value.
	 * @param value value whose presence in this map is to be tested.
	 * @return
	 */
	public boolean containsValue(Object value)
	{
		boolean res = false;
		
		try
		{
			lockObject.getReadLock();
			
			res = itemsMap.containsValue(value);
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".containsValue error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseReadLock();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".containsValue synchronization error", e);
			}
		}
			
		return res;
	}
	
	/**
	 * Returns a set view of the keys contained in this map.
	 * @return
	 */
	public Set keySet()
	{
		Set res = null;
		
		try
		{
			lockObject.getReadLock();
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".keySet error occured", e);
		}
		finally
		{
			try
			{
				lockObject.releaseReadLock();
				
				res = itemsMap.keySet();
			}
			catch (SynchronizationLockException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".keySet synchronization error", e);
			}
		}
			
		return res;
	}
	
	/**
	 * @return cache entity configuration.
	 */
	private CacheEntityConfig getEntityConfig()
	{
		CacheEntityConfig res = null;
		
		try
		{
			res = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entityName, true);
		}
		catch (CacheException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".getEntityConfig() failed to get entity configuration of entity [" + entityName + "]", e);
		}
		
		return res;
	}
	
	/**
	 * Adds a value to the items list and sorts if <code>sort<code> is true.
	 * @param value
	 * @param sort
	 */
	private void addToItemsList(Object value, boolean sort)
	{
		if(value != null)
		{
			//Get entity config
			CacheEntityConfig config = getEntityConfig();
			
			if(config != null && config.isComparatorExists() && config.isCompareOnPut())
			{
				//Add to the list
				itemsList.add(value);
			
				if(sort)
				{
					//Sort the list
					sortItemsList();
				}
			}
		}
	}
	
	/**
	 * Remove a specified value from the items list.
	 * @param value
	 */
	private void removeFromItemsList(Object value)
	{
		if(value != null)
		{
			//Get entity config
			CacheEntityConfig config = getEntityConfig();
		
			if(config != null && config.isComparatorExists() && config.isCompareOnPut())
			{
				//Remove from the list
				itemsList.remove(value);
			}
		}
	}
	
	/**
	 * Sorts the items list.
	 */
	private void sortItemsList()
	{
		//Get entity config
		CacheEntityConfig config = getEntityConfig();
		
		if(config != null && config.isComparatorExists() && config.isCompareOnPut())
		{
			//Sort the list
			Collections.sort(itemsList, config.getComparator());
			
			sorted = true;
		}
	}
	
	/**
	 * @return
	 */
	protected boolean isFull()
	{
		return isFull;
	}

	/**
	 * @param b
	 */
	protected void setFull(boolean b)
	{
		isFull = b;
	}

}
