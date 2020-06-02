/*
 * Created on: 31/01/2005
 * Author:  Alexey Levin
 * @version $Id: RemoteDataSourceFetcher.java,v 1.3 2005/05/08 13:48:27 yifat Exp $
 */
package com.ness.fw.cache.fetcher;

import java.util.Map;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.implementation.CacheEntityContainer;
import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.logger.Logger;

/**
 * This implementation of Fetcher is responsible for fetching data 
 * from datasource using BPO. 
 */
public class RemoteDataSourceFetcher implements Fetcher
{
	public static final String FETCH_BY_ID_CMD = "fetchByIdCmd";
	public static final String FETCH_ALL_CMD = "fetchAllCmd";
	
	/**
	 * @see com.ness.fw.cache.fetcher.Fetcher#fetchById(java.lang.Object, boolean)
	 */
	public Object fetchById(String entityName, Object key)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "Loading cache entity item from data source (entity = [" + entityName + "], item key = [" + key + "])...");
		
		Object value = null;
		
		//Create BPC
		FetcherBPC bpc = new FetcherBPC(UserAuthDataFactory.getEmptyUserAuthData(), entityName, key);
		
		try
		{
			//Get BPO server definition name
			String bpoServerDefinitionName = CacheConfigFactory.getCacheConfigManager().getCacheEntityConfig(entityName, true).getCacheDefinition().getBpoServerDefinitionName();

			//TODO define command constant
			//Fetch the value
			if(bpoServerDefinitionName == null || bpoServerDefinitionName.equals(""))
			{
				value = BPOProxy.execute(FETCH_BY_ID_CMD, bpc);
			}
			else
			{
				value = BPOProxy.execute(FETCH_BY_ID_CMD, bpc, bpoServerDefinitionName);
			}
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".fetchById failed", e);
		}
		
		return value;
	}
	
	/**
	 * @see com.ness.fw.cache.fetcher.Fetcher#fetchAll()
	 */
	public CacheEntityContainer fetchAll(String entityName)
	{
		Logger.debug(CacheManager.LOGGER_CONTEXT, "Loading cache entity [" + entityName + "] from data source...");
		
		CacheEntityContainer container = null;
		
		//Create BPC
		FetcherBPC bpc = new FetcherBPC(UserAuthDataFactory.getEmptyUserAuthData(), entityName);
		
		try
		{
			//Fetch cache entity container
			container = (CacheEntityContainer)BPOProxy.execute(FETCH_ALL_CMD, bpc);
		}
		catch (Throwable e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".fetchAll failed", e);
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
