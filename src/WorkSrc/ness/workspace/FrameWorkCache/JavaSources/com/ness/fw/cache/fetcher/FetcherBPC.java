/*
 * Created on: 31/01/2005
 * Author:  Alexey Levin
 * @version $Id: FetcherBPC.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.fetcher;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.common.auth.UserAuthData;

/**
 * Container for remote datasource fetcher.
 */
public class FetcherBPC extends BusinessProcessContainer
{
	/**
	 * Entity name.
	 */
	private String entityName;
	
	/**
	 * Key - paramater for fetchById.
	 */
	private Object key; 
	
	/**
	 * @param userAuthData
	 */
	public FetcherBPC(UserAuthData userAuthData, String entityName)
	{
		super(userAuthData);
		
		this.entityName = entityName;
	}
	
	public FetcherBPC(UserAuthData userAuthData, String entityName, Object key)
	{
		this(userAuthData, entityName);
		
		this.key = key;
	}

	/**
	 * @return
	 */
	public Object getKey()
	{
		return key;
	}

	/**
	 * @param object
	 */
	public void setKey(Object object)
	{
		key = object;
	}

	/**
	 * @return
	 */
	public String getEntityName()
	{
		return entityName;
	}

	/**
	 * @param string
	 */
	public void setEntityName(String string)
	{
		entityName = string;
	}
}
