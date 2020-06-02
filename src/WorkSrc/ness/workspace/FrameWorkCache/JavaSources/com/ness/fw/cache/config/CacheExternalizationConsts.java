/*
 * Created on: 18/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheExternalizationConsts.java,v 1.1 2005/02/24 08:42:01 alexey Exp $
 */
package com.ness.fw.cache.config;

/**
 * The constants for XML parsing of cache definition files.
 */
public interface CacheExternalizationConsts
{
	//CacheDefinition element definitions
	public static final String CACHE_DEF_TAG_NAME = "CacheDefinition";
	public static final String CACHE_DEF_ATTR_NAME = "name";
	public static final String CACHE_DEF_ATTR_TYPE = "type";
	public static final String CACHE_DEF_ATTR_DISTRIB_MNGR_TYPE = "distributionManagerType";
	public static final String CACHE_DEF_ATTR_IMPL = "implClass";
	public static final String CACHE_DEF_ATTR_LOCATION = "location";
	public static final String CACHE_DEF_ATTR_IS_CENTRAL_NODE = "isCentralNode";
	public static final String CACHE_DEF_ATTR_BPO_SERVER_DEFINITION_NAME = "BPOServerDefintionName";
	
	//cache element definition
	public static final String CACHE_TAG_NAME = "cache";
	public static final String CACHE_ATTR_NAME = "name";
	public static final String CACHE_ATTR_DEF = "def";
	public static final String CACHE_ATTR_CREATE_ON_START = "createOnStart";
	
	//entity element definition
	public static final String ENTITY_TAG_NAME = "entity";
	public static final String ENTITY_ATTR_NAME = "name";
	public static final String ENTITY_ATTR_CACHE = "cache";
	public static final String ENTITY_ATTR_NOTIFICATION = "notification";
	public static final String ENTITY_ATTR_LOAD_ON_START = "loadOnStart";
	public static final String ENTITY_ATTR_SIZE = "size";
	public static final String ENTITY_ATTR_ALGORITHM = "algorithm";
	public static final String ENTITY_ATTR_COMPARATOR_CLASS = "itemsComparatorClass";
	public static final String ENTITY_ATTR_COMPARE_ON = "compareOn";
	public static final String ENTITY_ATTR_ACTIVE = "active";
	public static final String ENTITY_ATTR_IS_DEFAULT = "default";
	
	//loader element definition
	public static final String LOADER_TAG_NAME = "loader";
	public static final String LOADER_ATTR_CLASS = "class";
	public static final String LOADER_ATTR_NEED_CP = "needConectionProvider";
	public static final String LOADER_ATTR_CONN_MNGR_NAME = "connectionManagerName";
	public static final String LOADER_ATTR_FIND_BY_ID = "findByIdMethod";
	public static final String LOADER_ATTR_FIND_ALL = "findAllMethod";
	public static final String LOADER_ATTR_FIND_BY_CRITERIA = "findByCriteria";

	//Cache type definition
	public static final String CACHE_TYPE_SINGLE = "single";
	public static final String CACHE_TYPE_DISTRIBUTED = "distributed";
	public static final String CACHE_TYPE_CENTRAL = "central";
	
	//Cache location definition
	public static final String CACHE_LOCATION_LOCAL = "local";
	public static final String CACHE_LOCATION_REMOTE = "remote";

	//Notification type definition
	public static final String NOTIFICATION_TYPE_INVALIDATE = "invalidate";
	public static final String NOTIFICATION_TYPE_FULL_DISTRIBUTION = "fullDistribution";
	
	//Cache algorithm definition
	public static final String CACHE_ALGORITHM_LRU = "LRU";
	public static final String CACHE_ALGORITHM_FIFO = "FIFO";
	
	//Distribution manager type definition
	public static final String DISTRIBUTION_MNGR_JGROUPS = "jgroups";
	
	//CompateOn definition
	public static final String COMPARE_ON_GETALL = "getAll";
	public static final String COMPARE_ON_PUT = "put";
}
