/*
 * Created on: 16/01/2005
 * Author:  Alexey Levin
 * @version $Id: CacheEntityLoader.java,v 1.2 2005/05/03 13:47:06 alexey Exp $
 */
package com.ness.fw.cache.config;

import java.lang.reflect.Method;

import com.ness.fw.cache.CacheManager;
import com.ness.fw.cache.exceptions.CacheException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.ConnectionProvider;

/**
 * Cache entity loader interface provides access to cache entity data finders. 
 */
public class CacheEntityLoader
{
	/**
	 * Loader class name.
	 */
	private String className;
	
	/**
	 * Loader class.
	 */
	private Class loaderClass;
	
	/**
	 * Loader instance.
	 */
	private Object loaderInstance;
	
	/**
	 * Finder method that fetches all the elements.
	 */
	private Method findAllMethod;
	
	/**
	 * Finder method that fetches element by ID.
	 */
	private Method findByIdMethod;
	
	/**
	 * Finder method that fetches elements by criteria.
	 */
	private Method findByCriteriaMethod;
	
	/**
	 * Tells whether loader methods need connection provider.
	 */
	private boolean needConnectionProvider;
	
	/**
	 * Connection manager name.
	 */
	private String connectionManagerName;
	
	/**
	 * findById method name.
	 */
	private String findByIdMethodName;
	
	/**
	 * findAll method name.
	 */
	private String findAllMethodName;
	
	/**
	 * findByCriteria method name.
	 */
	private String findByCriteriaMethodName;
	
	/**
	 * Returns findAll method.
	 * @return findAll method.
	 */
	public Method getFindAllMethod() throws CacheException
	{
		if(findAllMethod == null)
		{
			if(!findAllMethodName.equals(""))
			{
				Class loader = getLoaderClass();
			
				try
				{
					//Find method
					if(needConnectionProvider)
					{
						findAllMethod = loader.getMethod(findAllMethodName, new Class[] {ConnectionProvider.class}); 
					}
					else
					{
						findAllMethod = loader.getMethod(findAllMethodName, null); 
					}
				}
				catch (NoSuchMethodException e)
				{
					Logger.error(CacheManager.LOGGER_CONTEXT, "failed to get [" + findAllMethodName + "] method from loader [" + className + "]", e);
					
					throw new CacheException("failed to get [" + findAllMethodName + "] method from loader [" + className + "]", e);
				}
			}
			else
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, "findAll method name is empty in loader [" + className + "]");
					
				throw new CacheException("findAll method name is empty in loader [" + className + "]");
			}
		}
		
		return findAllMethod;
	}
	
	/**
	 * Returns findById method.
	 * @return findById method.
	 */
	public Method getFindByIdMethod(Class idClass) throws CacheException
	{
		if(findByIdMethod == null)
		{
			if(!findByIdMethodName.equals(""))
			{
				Class loader = getLoaderClass();
			
				try
				{
					//Find method
					if(needConnectionProvider)
					{
						findByIdMethod = loader.getMethod(findByIdMethodName, new Class[] {idClass, ConnectionProvider.class});  
					}
					else
					{
						findByIdMethod = loader.getMethod(findByIdMethodName, new Class[] {idClass});  
					}
				}
				catch (NoSuchMethodException e)
				{
					Logger.error(CacheManager.LOGGER_CONTEXT, "failed to get [" + findByIdMethodName + "] method from loader [" + className + "]", e);
					
					throw new CacheException("failed to get [" + findByIdMethodName + "] method from loader [" + className + "]", e);
				}
			}
			else
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, "findById method name is empty in loader [" + className + "]");
					
				throw new CacheException("findById method name is empty in loader [" + className + "]");
			}
		}
		
		return findByIdMethod;
	}
	
	/**
	 * Returns findByCriteria method.
	 * @return findByCriteria method.
	 */
	public Method getFindByCriteriaMethod() throws CacheException
	{
		if(findByCriteriaMethod == null)
		{
			if(!findByCriteriaMethodName.equals(""))
			{
				Class loader = getLoaderClass();
			
				try
				{
					//Find method
					if(needConnectionProvider)
					{
						findByCriteriaMethod = loader.getMethod(findByCriteriaMethodName, new Class[] {ConnectionProvider.class});  
					}
					else
					{
						findByCriteriaMethod = loader.getMethod(findByCriteriaMethodName, new Class[] {});  
					}
				}
				catch (NoSuchMethodException e)
				{
					Logger.error(CacheManager.LOGGER_CONTEXT, "failed to get [" + findByCriteriaMethodName + "] method from loader [" + className + "]", e);
					
					throw new CacheException("failed to get [" + findByCriteriaMethodName + "] method from loader [" + className + "]", e);
				}
			}
			else
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, "findByCriteria method name is empty in loader [" + className + "]");
					
				throw new CacheException("findByCriteria method name is empty in loader [" + className + "]");
			}
		}
		
		return findByCriteriaMethod;
	}
	
	/**
	 * Tells whether loader methods need connection provider.
	 * @return true if loader methods need connection provider, false otherwise.
	 */
	public boolean isNeedConnectionProvider()
	{
		return needConnectionProvider;
	}
	
	/**
	 * Returns connection manager name.
	 * @return connection manager name.
	 */
	public String getConnectionManagerName()
	{
		return connectionManagerName;
	}
	
	/**
	 * Returns loader class.
	 * @return loader class.
	 */
	public Class getLoaderClass() throws CacheException
	{
		if(loaderClass == null)
		{
			try
			{
				loaderClass = Class.forName(className);
			}
			catch (ClassNotFoundException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, "loader class [" + className + "] not found", e);
				throw new CacheException("loader class [" + className + "] not found", e);
			}
		}
		
		return loaderClass;
	}
	
	/**
	 * Returns loader instance.
	 * @return loader instance.
	 */
	public Object getLoaderInstance() throws CacheException
	{
		if(loaderInstance == null)
		{
			try
			{
				loaderInstance = getLoaderClass().newInstance();
			}
			catch (InstantiationException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, "failed to create loader instance [" + className + "]", e);
				throw new CacheException("failed to create loader instance [" + className + "]");
			}
			catch (IllegalAccessException e)
			{
				Logger.error(CacheManager.LOGGER_CONTEXT, "failed to create loader instance [" + className + "]", e);
				throw new CacheException("failed to create loader instance [" + className + "]");
			}
		}
		
		return loaderInstance;
	}
	/**
	 * @param string
	 */
	public void setClassName(String string)
	{
		className = string;
	}

	/**
	 * @param string
	 */
	public void setConnectionManagerName(String string)
	{
		connectionManagerName = string;
	}

	/**
	 * @param string
	 */
	public void setFindAllMethodName(String string)
	{
		findAllMethodName = string;
	}

	/**
	 * @param string
	 */
	public void setFindByCriteriaMethodName(String string)
	{
		findByCriteriaMethodName = string;
	}

	/**
	 * @param string
	 */
	public void setFindByIdMethodName(String string)
	{
		findByIdMethodName = string;
	}

	/**
	 * @param b
	 */
	public void setNeedConnectionProvider(boolean b)
	{
		needConnectionProvider = b;
	}

}
