/*
 * Created on: 16/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheConfigManagerXmlImpl.java,v 1.2 2005/05/16 15:53:07 alexey Exp $
 */
package com.ness.fw.cache.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.exceptions.CacheExternalizationException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.externalization.DOMList;
import com.ness.fw.common.externalization.DOMRepository;
import com.ness.fw.common.externalization.ExternalizationException;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.util.TypeConverter;


/**
 * CacheConfig implementation, which maintains configuration in XML.
 */
public class CacheConfigManagerXmlImpl extends CacheConfigManager
{
	/**
	 * Creates a new instance.
	 */
	protected CacheConfigManagerXmlImpl()
	{
	}
	
	/**
	 * Initializes cache configuration.
	 */
	public void initialize(ArrayList confFilesRoots) throws CacheExternalizationException
	{
		initialized = false;
		
		//Get number of cache definitions
		int numberOfCacheDefinitions;
		try
		{
			numberOfCacheDefinitions = SystemResources.getInstance().getInteger(NUMBER_OF_CACHE_DEFINITIONS_KEY);
		}
		catch (ResourceException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".initialize failed to get system property [" + NUMBER_OF_CACHE_DEFINITIONS_KEY + "]");
			
			numberOfCacheDefinitions = 10;
		}
		
		//Get number of entities
		int numberOfEntities;
		try
		{
			numberOfEntities = SystemResources.getInstance().getInteger(NUMBER_OF_ENTIIES_KEY);
		}
		catch (ResourceException e2)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".initialize failed to get system property [" + NUMBER_OF_ENTIIES_KEY + "]");
			
			numberOfEntities = 10;
		}
		 
		if(cache2Def != null)
		{
			//Clear map
			cache2Def.clear();
		}
		else
		{
			//Create a new map
			cache2Def = new HashMap(numberOfCacheDefinitions + 1, 1);
		}

		if(entity2Config != null)
		{
			//Clear map
			entity2Config.clear();
		}
		else
		{
			//Create a new map
			entity2Config = new HashMap(numberOfEntities + 1, 1);
		}
		
		DOMRepository domRepos = new DOMRepository();
		
		try
		{
			domRepos.initialize(confFilesRoots);
		}
		catch (ExternalizationException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "unable to initialize cache configuration", e);
			throw new CacheExternalizationException("unable to initialize cache configuration", e);
		}
		
		//Build defName2Def map
		Map defName2Def = buildDefName2Def(numberOfCacheDefinitions, domRepos);
		
		//Build cache2Def map
		buildCache2Def(domRepos, defName2Def);
		
		//Build entity config map
		buildEntity2Config(domRepos);
		
		//Cache was initialized
		initialized = true;
	}

	/**
	 * Builds entity2config map.
	 * @param domRepos
	 * @return entity to config map.
	 */
	private void buildEntity2Config(DOMRepository domRepos)
	{
		//Read entities
		DOMList domList = domRepos.getDOMList(CacheExternalizationConsts.ENTITY_TAG_NAME);
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				//Get document
				Document doc = domList.getDocument(i);
				
				//Get root
				Element root = doc.getDocumentElement();
		
				//Get entity elements
				NodeList ls = root.getElementsByTagName(CacheExternalizationConsts.ENTITY_TAG_NAME);
		
				int count = ls.getLength();
				for(int j = 0; j < count; j++)
				{
					//Get entity element
					Element entityElem = (Element)ls.item(j);
					
					//Get entity name
					String entityName = entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_NAME);
					
					if(entity2Config.containsKey(entityName))
					{
						Logger.error(CacheManager.LOGGER_CONTEXT, "Unable to load entity configuration for entity [" + entityName + "]: that configuration already exists.");
					}
					else
					{
						Logger.info(CacheManager.LOGGER_CONTEXT, "loading configuration for entity " + "[" + entityName + "]");
						
						//Create an entity configuration
						CacheEntityConfig entityConfig = createCacheEntityConfig(entityElem);
			
						//Put entity config to result map
						entity2Config.put(entityConfig.getEntityName(), entityConfig);
						
						if(entityConfig.isDefault())
						{
							if(defaultEntityConfig != null)
							{
								Logger.error(CacheManager.LOGGER_CONTEXT, getClass().getName() + ".buildEntity2Config unable to set cache entity configuration [" + entityName + "] as default. A default cache entity configuration already exist: [" + defaultEntityConfig.getEntityName() + "]");
							}
							else
							{
								defaultEntityConfig = entityConfig;
								
								Logger.info(CacheManager.LOGGER_CONTEXT, "Set cache entity configuration [" + entityName + "] as default");
							}
						}
					}		
				}
			}
		}
	}

	/**
	 * Creates a CacheEntityConfig object from XML entity element.
	 * @param entityElem
	 * @return
	 */
	private CacheEntityConfig createCacheEntityConfig(Element entityElem)
	{
		//Create a new CacheEntityConfig object
		CacheEntityConfig cacheEntityConfig = new CacheEntityConfig();
		
		//Set entity name
		cacheEntityConfig.setEntityName(XMLUtil.getAttribute(entityElem, CacheExternalizationConsts.ENTITY_ATTR_NAME));
		
		//Set cache name
		cacheEntityConfig.setCacheName(XMLUtil.getAttribute(entityElem, CacheExternalizationConsts.ENTITY_ATTR_CACHE));
		
		//Set notification type
		cacheEntityConfig.setNotificationType(getNotificationTypeFromXml(entityElem));
		
		//Set isLoadOnStart flag
		cacheEntityConfig.setLoadOnStart(TypeConverter.convertToBoolean(entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_LOAD_ON_START)).booleanValue());
		
		//Set cache entity loader
		cacheEntityConfig.setLoader(getCacheEntityLoaderFromXml(entityElem));
		
		//Set cache size
		cacheEntityConfig.setSize(getCacheEntitySizeFromXml(entityElem));
		
		//Set cache algorithm
		cacheEntityConfig.setCacheAlgorithm(getCacheAlgorithmFromXml(entityElem));
		
		//Set comparator class name
		cacheEntityConfig.setItemsComparatorClassName(XMLUtil.getAttribute(entityElem, CacheExternalizationConsts.ENTITY_ATTR_COMPARATOR_CLASS));
		
		//Set compareOn flag
		cacheEntityConfig.setCompareOn(getCompareOnFromXml(entityElem));
		
		//Set active flag
		cacheEntityConfig.setActive(TypeConverter.convertToBoolean(entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_ACTIVE)).booleanValue());
		
		//Set default flag
		cacheEntityConfig.setDefault(TypeConverter.convertToBoolean(entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_IS_DEFAULT)).booleanValue());
		
		return cacheEntityConfig;
	}

	/**
	 * Extracts notification type from entity XML element.
	 * @param entityElem entity XML element.
	 * @return notification type.
	 */
	private int getNotificationTypeFromXml(Element entityElem)
	{
		int type = -1;
		
		//Get notification type from XML
		String xmlNotificationType = entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_NOTIFICATION);
		
		//Set notification type
		if(xmlNotificationType.equals(CacheExternalizationConsts.NOTIFICATION_TYPE_INVALIDATE))
		{
			type = CacheEntityConfig.INVALIDATE_NOTIFICATION_TYPE;
		}
		else if(xmlNotificationType.equals(CacheExternalizationConsts.NOTIFICATION_TYPE_FULL_DISTRIBUTION))
		{
			type = CacheEntityConfig.FULL_DISTRIBUTION_NOTIFICATION_TYPE;
		}
		else
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "notification type not provided, will set to default");
			
			//TODO set default
			type = CacheEntityConfig.INVALIDATE_NOTIFICATION_TYPE;
		}
		
		return type;
	}

	/**
	 * Extracts loader from entity XML element. 
	 * @param entityElem entity XML element.
	 * @return
	 */
	private CacheEntityLoader getCacheEntityLoaderFromXml(Element entityElem)
	{
		//Get loaders list
		NodeList ls = entityElem.getElementsByTagName(CacheExternalizationConsts.LOADER_TAG_NAME);

		CacheEntityLoader entityLoader = null;
		if(ls.getLength() == 1)
		{
			//Create loader
			entityLoader = createCacheEntityLoader((Element)ls.item(0));
		}
		
		return entityLoader;
	}

	/**
	 * Creates a cache entity loader from XML loader element.
	 * @param element
	 * @return
	 */
	private CacheEntityLoader createCacheEntityLoader(Element element)
	{
		//Create a new loader
		CacheEntityLoader cacheEntityLoader = new CacheEntityLoader();
		
		//Set loader class name
		cacheEntityLoader.setClassName(XMLUtil.getAttribute(element, CacheExternalizationConsts.LOADER_ATTR_CLASS));
		
		//Set needConnectionProvider flag
		cacheEntityLoader.setNeedConnectionProvider(TypeConverter.convertToBoolean(element.getAttribute(CacheExternalizationConsts.LOADER_ATTR_NEED_CP)).booleanValue());
		
		//Set connection manager name
		cacheEntityLoader.setConnectionManagerName(XMLUtil.getAttribute(element, CacheExternalizationConsts.LOADER_ATTR_CONN_MNGR_NAME));
		
		//Set findById method name
		cacheEntityLoader.setFindByIdMethodName(XMLUtil.getAttribute(element, CacheExternalizationConsts.LOADER_ATTR_FIND_BY_ID));
		
		//Set findAll method name
		cacheEntityLoader.setFindAllMethodName(XMLUtil.getAttribute(element, CacheExternalizationConsts.LOADER_ATTR_FIND_ALL));
		
		//Set findByCriteria method name
		cacheEntityLoader.setFindByCriteriaMethodName(XMLUtil.getAttribute(element, CacheExternalizationConsts.LOADER_ATTR_FIND_BY_CRITERIA));
		
		return cacheEntityLoader;
	}

	/**
	 * Extracts cache size from entity XML element.
	 * @param entityElem entity XML element.
	 * @return cache size.
	 */
	private int getCacheEntitySizeFromXml(Element entityElem)
	{
		int res;
		
		//Get size as string
		String xmlSize = entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_SIZE);
		
		//Try to parse
		try
		{
			res = Integer.parseInt(xmlSize);			
		}
		catch (NumberFormatException e)
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "invalid cache size. A zise will set to default", e);
			
			//TODO set default
			res = CacheEntityConfig.ENTITY_SIZE_UNLIMITED;
		}
		
		return res;
	}

	/**
	 * Extracts cache algorithm from entity XML element.
	 * @param entityElem entity XML element.
	 * @return cache algorithm.
	 */
	private int getCacheAlgorithmFromXml(Element entityElem)
	{
		int alg = -1;
		
		//Get cache algorithm as string
		String xmlAlg = entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_ALGORITHM);
		
		//Get algorithm constant
		if(xmlAlg.equals(CacheExternalizationConsts.CACHE_ALGORITHM_LRU))
		{
			alg = CacheEntityConfig.LRU_CACHE_ALGORITHM;	
		}
		else if(xmlAlg.equals(CacheExternalizationConsts.CACHE_ALGORITHM_FIFO))
		{
			alg = CacheEntityConfig.FIFO_CACHE_ALGORITHM;
		}
		else
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "unknown cache algorithm, will set to default");
			
			//     set default
			alg = CacheEntityConfig.LRU_CACHE_ALGORITHM;	
		}
		
		return alg;
	}

	/**
	 * Extracts compareOn flag from entity XML element.
	 * @param entityElem
	 * @return
	 */
	private int getCompareOnFromXml(Element entityElem)
	{
		int res = 0;
		
		//Get compareOn from XML
		String xmlCompareOn = entityElem.getAttribute(CacheExternalizationConsts.ENTITY_ATTR_COMPARE_ON);
		
		if(xmlCompareOn.equals(CacheExternalizationConsts.COMPARE_ON_GETALL))
		{
			res = CacheEntityConfig.COMPARE_ON_GETALL;
		}
		else if(xmlCompareOn.equals(CacheExternalizationConsts.COMPARE_ON_PUT))
		{
			res = CacheEntityConfig.COMPARE_ON_PUT;
		}
		
		return res;
	}

	/**
	 * Builds definition name to definition map.
	 * @param domRepos
	 * @return definition name to definition map.
	 */
	private Map buildDefName2Def(int numberOfCacheDefinitions, DOMRepository domRepos)
	{
		//Create a result map
		Map defName2Def = new HashMap(numberOfCacheDefinitions);
		
		//Read cache definitions
		DOMList domList = domRepos.getDOMList(CacheExternalizationConsts.CACHE_DEF_TAG_NAME);
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				//Get document
				Document doc = domList.getDocument(i);
				
				//Get root
				Element root = doc.getDocumentElement();
		
				//Get cache definition elements
				NodeList lsDef = root.getElementsByTagName(CacheExternalizationConsts.CACHE_DEF_TAG_NAME);
		
				int count = lsDef.getLength();
				for(int j = 0; j < count; j++)
				{
					//Get XML definition
					Element xmlDef = (Element)lsDef.item(j);
			
					//Create a new cache definition.
					CacheDefinition def = createCacheDefinition(xmlDef);
			
					String defName = def.getDefName();
					if(defName2Def.containsKey(defName))
					{
						Logger.error(CacheManager.LOGGER_CONTEXT, "unable to load cache definition [" + defName + "]. Definition with that name already exists.");
					}
					else
					{
						Logger.info(CacheManager.LOGGER_CONTEXT, "cache definition [" + defName + "] loaded.");
						
						//Put on result map
						defName2Def.put(defName, def);
					}
				}
			}
		}
		
		return defName2Def;
	}

	/**
	 * Builds cache to definition map.
	 * @param domRepos
	 * @param defName2Def definition name to definition map.
	 * @return cache to definition map.
	 */
	private void buildCache2Def(DOMRepository domRepos, Map defName2Def)
	{
		//Read caches
		DOMList domList = domRepos.getDOMList(CacheExternalizationConsts.CACHE_TAG_NAME);
		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				//Get document
				Document doc = domList.getDocument(i);
				
				//Get root
				Element root = doc.getDocumentElement();
		
				//Get cache elements
				NodeList ls = root.getElementsByTagName(CacheExternalizationConsts.CACHE_TAG_NAME);
		
				int count = ls.getLength();
				for(int j = 0; j < count; j++)
				{
					//Get cache element
					Element cacheElem = (Element)ls.item(j);
			
					//Get cache logic name
					String cacheName = cacheElem.getAttribute(CacheExternalizationConsts.CACHE_ATTR_NAME);
			
					//Get cache definition name
					String defName = cacheElem.getAttribute(CacheExternalizationConsts.CACHE_ATTR_DEF);
			
					//Get definition by name
					CacheDefinition def = (CacheDefinition)defName2Def.get(defName);
					
					if(def == null)
					{
						Logger.error(CacheManager.LOGGER_CONTEXT, "unable map cache [" + cacheName + "] to cache definition [" + defName + "]: cache definition not found.");
					}
					else if(cache2Def.containsKey(cacheName))
					{
						Logger.error(CacheManager.LOGGER_CONTEXT, "unable map cache [" + cacheName + "] to cache definition [" + defName + "]: cache already mapped to cache definition [" + ((CacheDefinition)cache2Def.get(cacheName)).getDefName() + "]");
					}
					else
					{
						Logger.info(CacheManager.LOGGER_CONTEXT, "cache [" + cacheName + "] mapped to cache definition [" + defName + "]");
						
						//Put def to cache2def
						cache2Def.put(cacheName, def);
						
						//Get createOnStart attribute
						String attrCreateOnStart = cacheElem.getAttribute(CacheExternalizationConsts.CACHE_ATTR_CREATE_ON_START);
						
						boolean isCreateOnStart = TypeConverter.convertToBoolean(attrCreateOnStart).booleanValue();
						
						if(isCreateOnStart || (def.isCentral() && def.isCentralNode()))
						{
							//Create createOnStart list if not created yet
							if(lsCreateOnStart == null)
							{
								lsCreateOnStart = new ArrayList();	 
							}
							
							//Add cache to createOnStart list
							lsCreateOnStart.add(cacheName);
						}
					}			
				}
			}
		}
	}
	
	/**
	 * Creates a cache definition.
	 * @param xmlDef
	 * @return
	 */
	private CacheDefinition createCacheDefinition(Element xmlDef)
	{
		//Creates a cache definition
		CacheDefinition cacheDefinition = new CacheDefinition();
		
		//Set cache definition name
		cacheDefinition.setDefName(XMLUtil.getAttribute(xmlDef, CacheExternalizationConsts.CACHE_DEF_ATTR_NAME));
		
		//Set cache type
		cacheDefinition.setCacheType(getCacheType(xmlDef));
		
		//Set distribution manager type
		cacheDefinition.setDistributionManagerType(getDistributionManagerFromXml(xmlDef));
		
		//Set cache impl class name
		cacheDefinition.setImplClassName(getImplClassName(xmlDef));
		
		//Set cache location
		cacheDefinition.setCacheLocation(getCacheLocation(xmlDef));
		
		//Set isCentralNode flag
		cacheDefinition.setCentralNode(TypeConverter.convertToBoolean(xmlDef.getAttribute(CacheExternalizationConsts.CACHE_DEF_ATTR_IS_CENTRAL_NODE)).booleanValue());
		
		//Set BPO server definition name
		cacheDefinition.setBpoServerDefinitionName(XMLUtil.getAttribute(xmlDef, CacheExternalizationConsts.CACHE_DEF_ATTR_BPO_SERVER_DEFINITION_NAME));
		
		return cacheDefinition;
	}

	/**
	 * Extracts cache type from cache definition XML element.
	 * @param xmlDef cache definition XML element.
	 * @return cache type.
	 */
	private int getCacheType(Element xmlDef)
	{
		int res = -1;
		
		//Get cache type from XML
		String xmlCacheType = xmlDef.getAttribute(CacheExternalizationConsts.CACHE_DEF_ATTR_TYPE);
		
		//Set cache type
		if(xmlCacheType.equals(CacheExternalizationConsts.CACHE_TYPE_SINGLE))
		{
			res = CacheDefinition.SINGLE_CACHE_TYPE;
		}
		else if(xmlCacheType.equals(CacheExternalizationConsts.CACHE_TYPE_DISTRIBUTED))
		{
			res = CacheDefinition.DISTRIBUTED_CACHE_TYPE;
		}
		else if(xmlCacheType.equals(CacheExternalizationConsts.CACHE_TYPE_CENTRAL))
		{
			res = CacheDefinition.CENTRAL_CACHE_TYPE;
		}
		else
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "unknown cache type. Cache type will set to default");
			
			//TODO Set default
			res = CacheDefinition.SINGLE_CACHE_TYPE;
		}
		
		return res;
	}

	/**
	 * Extracts distribution manager type from cache definition XML element.
	 * @param xmlDef cache definition XML element.
	 * @return distribution manager type.
	 */
	private int getDistributionManagerFromXml(Element xmlDef)
	{
		int type = -1;
		
		//Get type from XML
		String xmlMngrType = xmlDef.getAttribute(CacheExternalizationConsts.CACHE_DEF_ATTR_DISTRIB_MNGR_TYPE);
		
		//Set manager type
		if(xmlMngrType.equals(CacheExternalizationConsts.DISTRIBUTION_MNGR_JGROUPS))
		{
			type = CacheDefinition.DISTRIBUTION_MANAGER_JGROUPS; 
		}
		else
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "unknown distributed manager type. Type will set to default");
			 
			//TODO set default
			type = CacheDefinition.DISTRIBUTION_MANAGER_JGROUPS; 
		}
		
		return type;
	}

	/**
	 * Extracts a cache implementation class name from cache definition XML element.
	 * @param xmlDef cache definition XML element.
	 * @return a cache implementation class name.
	 */
	private String getImplClassName(Element xmlDef)
	{
		//Get impl class name from XML
		String className = xmlDef.getAttribute(CacheExternalizationConsts.CACHE_DEF_ATTR_IMPL);
		
		if(className.equals(""))
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "implementation class not defined. The cache will implement default implementation"); 
			
			//TODO set default
			className = "com.ness.fw.cache.implementation.FwCacheImpl";
		}
		
		return className;
	}

	/**
	 * Extracts a cache location from cache definition XML element.
	 * @param xmlDef cache definition XML element.
	 * @return a cache location.
	 */
	private int getCacheLocation(Element xmlDef)
	{
		int location = -1;
		
		//Get cache location from XML
		String xmlLocation = xmlDef.getAttribute(CacheExternalizationConsts.CACHE_DEF_ATTR_LOCATION);
		
		//Set cache location
		if(xmlLocation.equals(CacheExternalizationConsts.CACHE_LOCATION_LOCAL))
		{
			location = CacheDefinition.LOCAL_LOCATION;
		}
		else if(xmlLocation.equals(CacheExternalizationConsts.CACHE_LOCATION_REMOTE))
		{
			location = CacheDefinition.REMOTE_LOCATION;
		}
		else
		{
			Logger.error(CacheManager.LOGGER_CONTEXT, "unknown cache location. Location will set to default");
			
			//TODO set default
			location = CacheDefinition.LOCAL_LOCATION;
		}
		
		return location;
	}
}
