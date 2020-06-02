/*
 * Created on 10/01/2005
 * 
 * @author Alexey Levin
 * @version $Id: Fetcher.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.fetcher;

import java.util.Map;

import com.ness.fw.cache.implementation.CacheEntityContainer;

/**
 * Fetcher is interface that contains methods for fetching the data.
 */
public interface Fetcher
{
	/**
	 * Fetches object specified by key.
	 * @param key
	 * @return fetched object.
	 */
	public Object fetchById(String entityName, Object key);
	
	/**
	 * Fetches all the data.
	 * @return fetched data. The return type may be list, array or any other type.
	 */
	public CacheEntityContainer fetchAll(String entityName);
	
	/**
	 * Fetches data by criteria.
	 * @param criteria
	 * @return Fetched data.
	 */
	public Object fetchByCriteria(String entityName, Map criteria);
}
