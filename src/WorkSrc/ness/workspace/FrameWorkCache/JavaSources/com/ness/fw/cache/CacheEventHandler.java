/*
 * Created on 10/01/2005
 * 
 * @author Alexey Levin
 * @version $Id: CacheEventHandler.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache;

import java.util.Map;
import java.util.Set;

import com.ness.fw.cache.implementation.CacheEntityContainer;

/**
 * Interface for handling cache events, such as put, remove, get, clear, ...
 */
public interface CacheEventHandler
{
	/**
	 * Handle put event
	 * @param entity entity name
	 * @param key 
	 * @param value
	 */
	public void handlePut(String entity, Object key, Object value);
	
	/**
	 * Handle put event
	 * @param entity entity name
	 * @param entity 
	 * @param items
	 */
	public void handlePut(String entity, Map items);
	
	/**
	 * Handle put event
	 * @param entity entity name
	 * @param entity
	 * @param keySet
	 */
	public void handlePut(String entity, Set keySet);
	
	/**
	 * Handle putAll event for fullDistribution notification type.
	 * @param container 
	 */
	public void handlePutAll(String entity, CacheEntityContainer container);
	
	/**
	 * Handle putAll event for invalidate notification type.
	 * @param entity entity name
	 * @param container 
	 */
	public void handlePutAll(String entity, Set keySet);
	
	/**
	 * Handle get event.
	 * @param entity entity name
	 * @param key
	 * @return
	 */
	public Object handleGet(String entity, Object key);
	
	/**
	 * Handle getAll event.
	 * @param entity entity name
	 * @return
	 */
	public CacheEntityContainer handleGetAll(String entity);
	
	/**
	 * Handle remove event.
	 * @param entity entity name
	 * @param key
	 */
	public void handleRemove(String entity, Object key);
	
	/**
	 * Handle clear event.
	 * @param entity entity name
	 */
	public void handleClear(String entity);
	
	/**
	 * Handle keySet event.
	 * @param entity entity name
	 * @return
	 */
	public Set handleKeySet(String entity);
}
