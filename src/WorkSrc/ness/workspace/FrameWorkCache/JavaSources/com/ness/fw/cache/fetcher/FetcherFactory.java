/*
 * Created on: 19/01/2005
 * Author:  Alexey Levin
 * @version $Id: FetcherFactory.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.fetcher;

import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.config.CacheDefinition;
import com.ness.fw.common.logger.Logger;

/**
 * Factory for creating cache entity fecther according to cache location.
 */
public class FetcherFactory
{
	/**
	 * Local fetcher.
	 */
	private static Fetcher localFetcher;
	
	/**
	 * Remote fetcher.
	 */
	private static Fetcher remoteFetcher;
	
	/**
	 * Returns fetcher.
	 * @param cacheLocation
	 * @return returns fetcher.
	 */
	public static Fetcher getFetcher(int cacheLocation)
	{
		Fetcher fetcher;
		
		if(cacheLocation == CacheDefinition.REMOTE_LOCATION)
		{
			fetcher = getRemoteFetcher();
		}
		else if(cacheLocation == CacheDefinition.LOCAL_LOCATION)
		{
			fetcher = getLocalFetcher();
		}
		else
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, FetcherFactory.class.getName() + ".getFetcher error occured: unknown cache location [" + cacheLocation + "]. A default fetcher will returned.");
			
			//Default
			fetcher = getLocalFetcher();
		}
		
		return fetcher;
	}
	
	/**
	 * Returns local fetcher.
	 * If not exist yet, creates it.
	 * @return
	 */
	synchronized private static Fetcher getLocalFetcher()
	{
		if(localFetcher == null)
		{
			localFetcher = new LocalDataSourceFetcher();
		}
		
		return localFetcher;
	}

	/**
	 * Returns remote fetcher.
	 * If not exist yet, creates it.
	 * @return
	 */
	synchronized private static Fetcher getRemoteFetcher()
	{
		if(remoteFetcher == null)
		{
			remoteFetcher = new RemoteDataSourceFetcher();
		}
		
		return remoteFetcher;
	}
}
