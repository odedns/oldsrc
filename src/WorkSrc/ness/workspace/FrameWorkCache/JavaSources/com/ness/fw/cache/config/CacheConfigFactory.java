/*
 * Created on: 14/02/2005
 * Author:  Alexey Levin
 * @version $Id: CacheConfigFactory.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.config;

import java.util.ArrayList;

import com.ness.fw.cache.exceptions.CacheException;

/**
 * Factory for creating specific implementation of CacheConfigManager.
 */
public class CacheConfigFactory
{
	/**
	 * The singleton cache configuration instance.
	 */
	private static CacheConfigManager cacheConfig;
	
	static
	{
		cacheConfig = new CacheConfigManagerXmlImpl();
	}
	
	/**
	 * Initializes cache config.
	 * @param lsInitData
	 * @throws CacheException
	 */
	public static void initialize(ArrayList lsInitData) throws CacheException
	{
		cacheConfig.initialize(lsInitData);
	}
	
	public static CacheConfigManager getCacheConfigManager()
	{
		return cacheConfig;
	}
}
