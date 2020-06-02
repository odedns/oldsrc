/*
 * Created on 04/01/2005
 * @author Alexey Levin
 * @version $Id: MessageBody.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.notification;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.ness.fw.cache.implementation.CacheEntityContainer;

/**
 * Notification message body.
 */
public class MessageBody implements Serializable
{
	/**
	 * Entity name.
	 */
	private String entity;
	
	/**
	 * The operation type: get, put, remove or clear.
	 */
	private String operation;
	
	/**
	 * Cache entry key.
	 */
	private Object key;
	
	/**
	 * Cache entry value associated with key.
	 */
	private Object value;
	
	/**
	 * Cache entity container.
	 */
	private CacheEntityContainer container;
	
	/**
	 * Key set.
	 */
	private Set keySet;
	
	/**
	 * Items map.
	 */
	private Map items;
	
	/**
	 * Creates a new instance.
	 *
	 */
	public MessageBody()
	{
	}
	
	/**
	 * Creates a new instance.
	 * @param operation
	 * @param key
	 * @param value
	 */
	public MessageBody(String entity, String operation, Object key, Object value)
	{
		this.entity = entity;
		this.operation = operation;
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Creates a new instance.
	 * @param operation
	 * @param container
	 */
	public MessageBody(String entity, String operation, CacheEntityContainer container)
	{
		this.entity = entity;
		this.operation = operation;
		this.container = container;
	}
	
	/**
	 * Creates a new instance.
	 * @param operation
	 * @param keySet
	 */
	public MessageBody(String entity, String operation, Set keySet)
	{
		this.entity = entity;
		this.operation = operation;
		this.keySet = keySet;
	}
	
	/**
	 * Creates a new instance.
	 * @param operation
	 * @param items
	 */
	public MessageBody(String entity, String operation, Map items)
	{
		this.entity = entity;
		this.operation = operation;
		this.items = items;
	}

	/**
	 * @return
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param string
	 */
	public void setOperation(String string)
	{
		operation = string;
	}

	/**
	 * @return
	 */
	public Object getKey()
	{
		return key;
	}

	/**
	 * @return
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * @param object
	 */
	public void setKey(Object object)
	{
		key = object;
	}

	/**
	 * @param object
	 */
	public void setValue(Object object)
	{
		value = object;
	}

	/**
	 * @return
	 */
	public CacheEntityContainer getContainer()
	{
		return container;
	}

	/**
	 * @param container
	 */
	public void setContainer(CacheEntityContainer container)
	{
		this.container = container;
	}

	/**
	 * @return
	 */
	public Set getKeySet()
	{
		return keySet;
	}

	/**
	 * @param set
	 */
	public void setKeySet(Set set)
	{
		keySet = set;
	}

	/**
	 * @return
	 */
	public String getEntity()
	{
		return entity;
	}

	/**
	 * @param string
	 */
	public void setEntity(String string)
	{
		entity = string;
	}

	/**
	 * @return
	 */
	public Map getItems()
	{
		return items;
	}

	/**
	 * @param map
	 */
	public void setItems(Map map)
	{
		items = map;
	}

}
