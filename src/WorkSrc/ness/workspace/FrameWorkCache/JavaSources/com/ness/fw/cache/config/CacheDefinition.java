/*
 * Created on: 16/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheDefinition.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.config;

/**
 * Cache definition interface provides access to specific cache definition.
 */
public class CacheDefinition
{
	//Cache type definition
	public static final int SINGLE_CACHE_TYPE = 1;
	public static final int DISTRIBUTED_CACHE_TYPE = 2;
	public static final int CENTRAL_CACHE_TYPE = 3;
	
	//Distribution manager type definition
	public static final int DISTRIBUTION_MANAGER_JGROUPS = 1;
	
	//Cache location definition
	public static final int LOCAL_LOCATION = 1;
	public static final int REMOTE_LOCATION = 2;
	
	/**
	 * Definition name.
	 */
	private String defName;
	
	/**
	 * Cache type - local, distributed or central.
	 */
	private int cacheType;
	
	/**
	 * A distribution manager type.
	 * Relevant only for distributed or central cache type
	 * 1 - jgroups
	 */
	private int distributionManagerType;
	
	/**
	 * Cache location reletive to the data source - local or remote.
	 */
	private int cacheLocation;
	
	/**
	 * Cache implementation class name.
	 */
	private String implClassName;
	
	/**
	 * Tells whether a cache is central node in central cache.
	 * Used with central cache type only.
	 */
	private boolean isCentralNode;
	
	/**
	 * BPO server definition name, which used by
	 * BPOProxy if location is remote.
	 */
	private String bpoServerDefinitionName;
	
	/**
	 * Returns a definition name.
	 * @return a definition name.
	 */
	public String getDefName()
	{
		return defName;
	}

	/**
	 * Returns a cache type.
	 * @return a cache type.
	 */
	public int getCacheType()
	{
		return cacheType;
	}
	
	/**
	 * Returns a distribution manager type.
	 * Relevant only for distributed or central cache type
	 * 1 - jgroups
	 * @return
	 */
	public int getDistributionManagerType()
	{
		return distributionManagerType;
	}
	
	/**
	 * Returns a cache location.
	 * @return a cache location.
	 */
	public int getCacheLocation()
	{
		return cacheLocation;
	}
	
	/**
	 * @return Cache implementation class name.
	 */
	public String getImplClassName()
	{
		return implClassName;
	}
	
	/**
	 * Tells whether a cache type is single.
	 * @return true if a cache type is single, false otherwise.
	 */
	public boolean isSingle()
	{
		return (cacheType == SINGLE_CACHE_TYPE);
	}
	
	/**
	 * Tells wheteher a cache type is distributed.
	 * @return true if a cache type is distributed, false otherwise.
	 */
	public boolean isDistributed()
	{
		return (cacheType == DISTRIBUTED_CACHE_TYPE);
	}
	
	/**
	 * Tells whether a cache type is central.
	 * @return true if a cache type is central, false otherwise.
	 */
	public boolean isCentral()
	{
		return (cacheType == CENTRAL_CACHE_TYPE);
	}
	
	/**
	 * Tells whether a cache is central node in central cache.
	 * Used with central cache type only.
	 * @return true if a cache is central node in central cache, false otherwise.
	 */
	public boolean isCentralNode()
	{
		return isCentralNode;
	}
	
	/**
	 * Returns BPO server definition name, which used by
	 * BPOProxy if location is remote. 
	 * @return
	 */
	public String getBpoServerDefinitionName()
	{
		return bpoServerDefinitionName;
	}
	/**
	 * @param string
	 */
	public void setBpoServerDefinitionName(String string)
	{
		bpoServerDefinitionName = string;
	}

	/**
	 * @param i
	 */
	public void setCacheLocation(int i)
	{
		cacheLocation = i;
	}

	/**
	 * @param i
	 */
	public void setCacheType(int i)
	{
		cacheType = i;
	}

	/**
	 * @param string
	 */
	public void setDefName(String string)
	{
		defName = string;
	}

	/**
	 * @param i
	 */
	public void setDistributionManagerType(int i)
	{
		distributionManagerType = i;
	}

	/**
	 * @param string
	 */
	public void setImplClassName(String string)
	{
		implClassName = string;
	}

	/**
	 * @param b
	 */
	public void setCentralNode(boolean b)
	{
		isCentralNode = b;
	}

}
