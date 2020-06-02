/*
 * Created on: 19/01/2005
 * Author:  Alexey Levin
 * @version $Id: LocalDataSourceFetcher.java,v 1.3 2005/05/04 08:46:49 alexey Exp $
 */
package com.ness.fw.cache.fetcher;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ness.fw.bl.Identifiable;
import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.config.CacheEntityLoader;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.cache.implementation.CacheEntityContainer;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.ConnectionSequence;

/**
 * This implementation of Fetcher is responsible for fetching data directly
 * from datasource. 
 */
public class LocalDataSourceFetcher implements Fetcher
{
	/**
	 * @see com.ness.fw.cache.fetcher.Fetcher#fetchById(java.lang.Object)
	 */
	public Object fetchById(String entityName, Object key)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "Loading cache entity item from data source (entity = [" + entityName + "], item key = [" + key + "])...");
		
		Object value = null;
		
		ConnectionSequence seq = null;
		try
		{
			//Get cache entity loader
			CacheEntityLoader loader = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entityName, false).getEntityLoader();

			if(loader != null)
			{
				//Get findById method
				Method findByIdMethod = loader.getFindByIdMethod(key.getClass());
			
				//If method is not static need loader instance
				Object loaderInstance = null;
				if(!Modifier.isStatic(findByIdMethod.getModifiers()))
				{
					loaderInstance = loader.getLoaderInstance();
				}
				
				if(loader.isNeedConnectionProvider())
				{
					//Set connection manager name
					String connManagerName = loader.getConnectionManagerName();
					if(connManagerName != null && connManagerName.equals(""))
					{
						connManagerName = null;
					}
					
					//Create connection provider
					seq = ConnectionSequence.beginSequence(connManagerName);
					
					//Invode finder
					value = findByIdMethod.invoke(loaderInstance, new Object[] {key, seq});
				}
				else
				{
					//Invode finder
					value = findByIdMethod.invoke(loaderInstance, new Object[] {key});
				}
			}
			else
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, "loader not found for entity [" + entityName + "]");
				throw new CacheException("loader not found for entity [" + entityName + "]");
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".fecthById() failed for entity [" + entityName + "]", e);
		}
		finally
		{
			if(seq != null)
			{
				//Close sequence
				try
				{
					seq.end();
				}
				catch (PersistenceException e) 
				{
					Logger.error(CacheManager.LOGGER_CONTEXT, "failed to close connection sequence", e);
				}
			}
		}
		
		return value;
	}
	
	/**
	 * @see com.ness.fw.cache.fetcher.Fetcher#fetchAll()
	 */
	public CacheEntityContainer fetchAll(String entityName)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "Loading cache entity [" + entityName + "] from data source...");
		
		CacheEntityContainer res = null;
		
		ConnectionSequence seq = null;
		try
		{
			//Get cache entity loader
			CacheEntityLoader loader = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entityName, false).getEntityLoader();
			
			if(loader != null)
			{
				//Get findAll method
				Method findAllMethod = loader.getFindAllMethod();

				//If method is not static need loader instance
				Object loaderInstance = null;
				if(!Modifier.isStatic(findAllMethod.getModifiers()))
				{
					loaderInstance = loader.getLoaderInstance();
				}
				
				Object values = null;
				if(loader.isNeedConnectionProvider())
				{
					//Set connection manager name
					String connManagerName = loader.getConnectionManagerName();
					if(connManagerName != null && connManagerName.equals(""))
					{
						connManagerName = null;
					}
					
					//Create connection provider
					seq = ConnectionSequence.beginSequence(connManagerName);
					
					//Invode finder
					values = findAllMethod.invoke(loaderInstance, new Object[] {seq});
				}
				else
				{
					//Invode finder
					values = findAllMethod.invoke(loaderInstance, new Object[] {});
				}
			
				//Create result cache entity container
				if(values != null)
				{
					if(values instanceof List)
					{
						//Create cache entity container from List
						res = createCacheEntityContainer(entityName, (List)values);
					}
					else if(values instanceof CacheEntityContainer)
					{
						// TODO check if should create new instance
						res = (CacheEntityContainer)values;
					}
					else if(values instanceof Map)
					{
						//Create cache entity container from Map
						res = createCacheEntityContainer(entityName, (Map)values);
					}
					else
					{
						throw new CacheException("unknown retvalue type");
					}
				}
			}
			else
			{
				throw new CacheException("loader not found");
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".fetchAll() failed", e);
		}
		finally
		{
			if(seq != null)
			{
				//Close sequence
				try
				{
					seq.end();
				}
				catch (PersistenceException e) {}
			}
		}
		
		return res;
	}
	
	/**
	 * Creates cache entity container from Map.
	 * @param values
	 * @return cache entity container
	 */
	private CacheEntityContainer createCacheEntityContainer(String entityName, Map map)
	{
		//Create a result container
		CacheEntityContainer container = new CacheEntityContainer(entityName);
		
		Iterator iter = map.keySet().iterator();
		Object key = null;
		while(iter.hasNext())
		{
			//Get key
			key = iter.next();
			
			//Put on container
			container.put(key, map.get(key));
		}
		
		return container;
	}

	/**
	 * Creates cache entity container from ValueObjectList.
	 * @param values
	 * @return cache entity container
	 */
	private CacheEntityContainer createCacheEntityContainer(String entityName, List values)
	{
		//Create a result container
		CacheEntityContainer container = new CacheEntityContainer(entityName);
		
		int count = values.size();
		Identifiable item = null;
		for(int i = 0; i < count; i++)
		{
			//Get identifiable from value object list
			item = (Identifiable)values.get(i);
			
			//Put identifiable to the container
			container.put(item.getId(), item);
		}

		return container;
	}

	/**
	 * @see com.ness.fw.cache.fetcher.Fetcher#fetchByCriteria(java.util.Map)
	 */
	public Object fetchByCriteria(String entityName, Map criteria)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
