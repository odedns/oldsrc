/*
 * Created on: 16/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheEntityConfig.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.config;

import java.util.Comparator;

import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.common.logger.Logger;

/**
 * Cache entity config interface. Provides access to cache entity configuration.
 */
public class CacheEntityConfig
{
	//Notification type definition
	public static final int INVALIDATE_NOTIFICATION_TYPE = 1;
	public static final int FULL_DISTRIBUTION_NOTIFICATION_TYPE = 2;
	
	//Cache algorithm definition
	public static final int LRU_CACHE_ALGORITHM = 1;
	public static final int FIFO_CACHE_ALGORITHM = 2;
	
	//Cache size
	public static final int ENTITY_SIZE_UNLIMITED = -1;
	public static final int ENTITY_SIZE_NO_CACHE = 0;
	
	//Compare on
	public static final int COMPARE_ON_GETALL = 1;
	public static final int COMPARE_ON_PUT = 2;
	
	/**
	 * Cache entity name.
	 */
	private String entityName;
	
	/**
	 * A logic cache name to which a cache definition is mapped.
	 */
	private String cacheName;
	
	/**
	 * A notification type:
	 * 1 - invalidate
	 * 2 - full distribution
	 */
	private int notificationType;
	
	/**
	 * Tells whether the cache entity should be loaded on start.
	 */
	private boolean isLoadOnStart;
	
	/**
	 * Cache entity loader.
	 */
	private CacheEntityLoader loader;
	
	/**
	 * A size of cache entity.
	 * If size is 0 then no data will stored in this cache, always need to fetch the data.
	 * If size is -1 then the entity is unlimited.
	 */
	private int size;
	
	/**
	 * A cache algorithm.
	 * 1 - LRU
	 * 2 - FIFO
	 */
	private int cacheAlgorithm;
	
	/**
	 * An items comporator class name.
	 */
	private String itemsComparatorClassName;
	
	/**
	 * An items comparator instance.
	 */
	private Comparator comparator;
	
	/**
	 * CompareOn flag.
	 */
	private int compareOn;
	
	/**
	 * Tells whether entity is active.
	 */
	private boolean active;
	
	/**
	 * Tells whether a cache entity configuration is default.
	 */
	private boolean isDefault;

	/**
	 * Returns an entity name.
	 * @return an entity name.
	 */
	public String getEntityName()
	{
		return entityName;
	}
	
	/**
	 * Returns a logic cache name to which a cache definition is mapped.
	 * @return a logic cache name to which a cache definition is mapped.
	 */
	public String getCacheName()
	{
		return cacheName;
	}
	
	/**
	 * Returns an entity's cache definition.
	 * @return an entity's cache definition.
	 */
	public CacheDefinition getCacheDefinition() throws CacheException
	{
		return CacheConfigFactory.getCacheConfigManager().getCacheDefinition(cacheName);
	}
	
	/**
	 * Returns the notification type:
	 * 1 - invalidate
	 * 2 - full distribution
	 * @return the notification type.
	 */
	public int getNotificationType()
	{
		return notificationType;
	}
	
	/**
	 * Tells whether the cache entity should be loaded on start.
	 * @return true if cache entity should be loaded on start, false otherwise.
	 */
	public boolean isLoadOnStart()
	{
		return isLoadOnStart;
	}
	
	/**
	 * Returns a cache entity loader.
	 * @return a cache entity loader.
	 */
	public CacheEntityLoader getEntityLoader()
	{
		return loader;
	}
	
	/**
	 * Returns a size of cache entity.
	 * If size is 0 then no data will stored in this cache, always need to fetch the data.
	 * If size is -1 then the entity is unlimited.
	 * @return a size of cache entity.
	 */
	public int getEntitySize()
	{
		return size;
	}
	
	/**
	 * Returns a cache algorithm.
	 * 1 - LRU
	 * 2 - FIFO
	 * @return a cache algorithm.
	 */
	public int getCacheAlgorithm()
	{
		return cacheAlgorithm;
	}
	
	/**
	 * Tells whether the cache, which contains this entity, is sigle type.
	 * @return true if the cache, which contains this entity, is sigle type, false otherwise.
	 */
	public boolean isSingleCache()
	{
		boolean res;
		
		try
		{
			res = CacheConfigFactory.getCacheConfigManager().getCacheDefinition(cacheName).isSingle();
		}
		catch (CacheException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "failed to get cache definition", e);
			
			//TODO Set default value
			res = true;
		}
		
		return res;
	}
	
	/**
	 * Tells whether the cache, which contains this entity, is distributed type.
	 * @return true if the cache, which contains this entity, is distributed type, false otherwise.
	 */
	public boolean isDistributedCache()
	{
		boolean res;
		
		try
		{
			res = CacheConfigFactory.getCacheConfigManager().getCacheDefinition(cacheName).isDistributed();
		}
		catch (CacheException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "failed to get cache definition", e);
			
			//TODO Set default value
			res = false;
		}
		
		return res;
	}
	
	/**
	 * Tells whether the cache, which contains this entity, is central type.
	 * @return true if the cache, which contains this entity, is central type, false otherwise.
	 */
	public boolean isCentralCache()
	{
		boolean res;
		
		try
		{
			res = CacheConfigFactory.getCacheConfigManager().getCacheDefinition(cacheName).isCentral();
		}
		catch (CacheException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "failed to get cache definition", e);
			
			//TODO Set default value
			res = false;
		}
		
		return res;
	}

	/**
	 * @return true if entity size is limited, false otherwise.
	 */
	public boolean isSizeLimited()
	{
		return (!isSizeUnlimited() && !isSizeZero());
	}
	
	/**
	 * @return true if entity suze is unlimited, false otherwise.
	 */
	public boolean isSizeUnlimited()
	{
		return (size == CacheEntityConfig.ENTITY_SIZE_UNLIMITED);
	}
	
	/**
	 * @return true if entity size is 0, false otherwise
	 */
	public boolean isSizeZero()
	{
		return (size == CacheEntityConfig.ENTITY_SIZE_NO_CACHE);
	}
	
	/**
	 * @return cache entity items comporator.
	 */
	synchronized public Comparator getComparator()
	{
		if(comparator == null && !itemsComparatorClassName.equals(""))
		{
			//Create a comparator instance
			try
			{
				comparator = (Comparator)Class.forName(itemsComparatorClassName).newInstance();
				
				//Free itemsComparatorClassName
				itemsComparatorClassName = null;
			}
			catch (Throwable e)
			{
				//TODO throw exception?
				Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".getComparator failed to create a comparator instance", e);
			}
		}
		
		return comparator;
	}
	
	/**
	 * @return true if comparator exists, false otherwise.
	 */
	public boolean isComparatorExists()
	{
		return ((itemsComparatorClassName != null && !itemsComparatorClassName.equals("")) || comparator != null);
	}
	
	/**
	 * @return true if compare on getAll, false otherwise.
	 */
	public boolean isCompareOnGetAll()
	{
		return (compareOn == COMPARE_ON_GETALL);
	}
	
	/**
	 * @return true if compare on put, false otherwise.
	 */
	public boolean isCompareOnPut()
	{
		return (compareOn == COMPARE_ON_PUT);
	}
	
	/**
	 * Tells whether entity is active.
	 * @return
	 */
	public boolean isActive()
	{
		return active;
	}
	
	/**
	 * Tells whether cache entity loader exist.
	 * @return
	 */
	public boolean isLoaderExist()
	{
		return (loader != null);
	}
	
	/**
	 * Tells whether a cache entity configuration is default.
	 * @return
	 */
	public boolean isDefault()
	{
		return isDefault;
	}
	/**
	 * @param b
	 */
	public void setActive(boolean b)
	{
		active = b;
	}

	/**
	 * @param i
	 */
	public void setCacheAlgorithm(int i)
	{
		cacheAlgorithm = i;
	}

	/**
	 * @param string
	 */
	public void setCacheName(String string)
	{
		cacheName = string;
	}

	/**
	 * @param i
	 */
	public void setCompareOn(int i)
	{
		compareOn = i;
	}

	/**
	 * @param string
	 */
	public void setEntityName(String string)
	{
		entityName = string;
	}

	/**
	 * @param b
	 */
	public void setDefault(boolean b)
	{
		isDefault = b;
	}

	/**
	 * @param b
	 */
	public void setLoadOnStart(boolean b)
	{
		isLoadOnStart = b;
	}

	/**
	 * @param string
	 */
	public void setItemsComparatorClassName(String string)
	{
		itemsComparatorClassName = string;
	}

	/**
	 * @param loader
	 */
	public void setLoader(CacheEntityLoader loader)
	{
		this.loader = loader;
	}

	/**
	 * @param i
	 */
	public void setNotificationType(int i)
	{
		notificationType = i;
	}

	/**
	 * @param i
	 */
	public void setSize(int i)
	{
		size = i;
	}

}