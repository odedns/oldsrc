/*
 * Created on 10/01/2005
 * @author Alexey Levin
 * @version $Id: DistributionManager.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.notification;

import java.util.Map;
import java.util.Set;

import com.ness.fw.cache.CacheEventHandler;
import com.ness.fw.cache.implementation.CacheEntityContainer;

/**
 * DistributionManager provides notification methods about cache activity.
 * This interface should be implemented by notification mechanism for distributed cache.
 */
public interface DistributionManager
{
	/**
	 * Sends notification about put operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @param items
	 */
	public void notifyPut(String entity, Map items);
	
	/**
	 * Sends notification about put operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @param key
	 * @param value
	 */
	public void notifyPut(String entity, Set keySet);
	
	/**
	 * Sends notification about put operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @param key
	 * @param value
	 */
	public void notifyPut(String entity, Object key, Object value);
	
	/**
	 * Sends notification about putAll operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @param container
	 */
	public void notifyPutAll(String entity, CacheEntityContainer container);
	
	/**
	 * Sends notification about putAll operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @param container
	 */
	public void notifyPutAll(String entity, Set keySet);
	
	/**
	 * Sends notification about get operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @param key
	 * @return value associated with this key. 
	 */
	public Object notifyGet(String entity, Object key);
	
	/**
	 * Sends notification about getAll operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @return
	 */
	public CacheEntityContainer notifyGetAll(String entity);
	
	/**
	 * Sends notification about remove operation to all the instances of this cache entity.
	 * @param entity entity name
	 * @param key
	 */
	public void notifyRemove(String entity, Object key);
	
	/**
	 * Sends notification about clear operation to all the instances of this cache entity.
	 * @param entity entity name
	 */
	public void notifyClear(String entity);
	
	/**
	 * Sends notification about keySet operation. 
	 * @param entity entity name
	 * @return
	 */
	public Set notifyKeySet(String entity);
	
	/**
	 * Sets cache event handler.
	 * @param entity entity name
	 * @param handler
	 */
	public void setCacheEventHandler(CacheEventHandler handler);
}
