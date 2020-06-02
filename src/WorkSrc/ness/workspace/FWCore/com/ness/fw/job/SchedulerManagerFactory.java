/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.ResourceUtils;

/**
 * a factory for creating schedulres 
 */
public class SchedulerManagerFactory
{
	private static SchedulerManagerFactory schdulerInstance = null;

	private HashMap schdulerInstances;
	private HashMap classMapping;
	private String defaultScheduler;
	private final String MANAGERS_PROPERTY_FILE_NAME = "schedulersManagers";
	private final String DEFAULT_SCHEDULER_MANAGER = "defaultSchedulerManager";

	public static final String LOGGER_CONTEXT = "JOBS FACTORY";

	/**
	 * constructor
	 */
	private SchedulerManagerFactory()
	{
		init();
	}

	/**
	 * Get an instance of the SchedulerManagerFactory
	 * @return
	 */
	public static SchedulerManagerFactory getInstance()
	{
		if (schdulerInstance == null)
		{
			schdulerInstance = new SchedulerManagerFactory();
		}
		
		return schdulerInstance;
	}


	/**
	 * Initializing the factory. for each entry in the property file
	 * creates a scheduler impl.
	 *
	 */
	private synchronized void init()
	{
		//ResourceBundle bundle = ResourceBundle.getBundle(MANAGERS_PROPERTY_FILE_NAME);
		ResourceBundle bundle = ResourceUtils.getBundle(MANAGERS_PROPERTY_FILE_NAME);
		
		defaultScheduler = bundle.getString(DEFAULT_SCHEDULER_MANAGER);
		Enumeration enumeration = bundle.getKeys();
		
		if (classMapping == null)
		{
			classMapping = new HashMap();			
		}
		else
		{
			classMapping.clear();
			schdulerInstances.clear();		
		}


		//for each entry in the property file creates a scheduler impl.
		while (enumeration.hasMoreElements())
		{
			String key = (String)enumeration.nextElement();
			String value = bundle.getString(key);
			if (key.endsWith(".class"))
			{
				String schedulerName = key.substring(0, key.indexOf(".class"));
				classMapping.put(schedulerName, value);
			}
		}
	}

	/**
	 * 
	 * @return
	 * @throws JobException
	 */
	public SchedulerManager createSchdulerManager() throws JobException
	{
		return createSchdulerManager(null);
	}

	/**
	 * create a specific schedluer impl
	 * @param schdulerName
	 * @return
	 * @throws JobException
	 */
	public SchedulerManager createSchdulerManager(String schdulerName) throws JobException
	{
		// if no name was passed, use the default
		if (schdulerName == null)
		{
			schdulerName = defaultScheduler;
		}

		if (schdulerInstances == null)
		{
			schdulerInstances = new HashMap();
		}

		SchedulerManager scheduler = findInCache(schdulerName);
	
		// if the specific scheduler wasn't initialized alredy, create it
		if (scheduler == null)
		{
			String className = (String)classMapping.get(schdulerName);
	
			if (className == null)
			{
				Logger.error(LOGGER_CONTEXT,"scheduler name " + schdulerName + " not supported");
				throw new JobException("scheduler name " + schdulerName + " not supported");				
			}

			try
			{
				Class c = Class.forName(className);
				scheduler = (SchedulerManager)c.getConstructor(null).newInstance(null);
				schdulerInstances.put(schdulerName,scheduler);
			}
			catch(Throwable e)
			{
				throw new JobException("Error in initalizing managers", e);
			}
		}

		return scheduler;
	}
	
	/**
	 * reload the schedulrer managers
	 *
	 */
	public void reload()
	{
		init();
	}

	/**
	 * get a scheduler in the cache if was already loaded, otherwise return null
	 * @param schdulerName
	 * @return
	 */
	private SchedulerManager findInCache(String schdulerName)
	{
		return (SchedulerManager)schdulerInstances.get(schdulerName);
	}
}
