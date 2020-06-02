/*
 * Author: yifat har-nof
 * @version $Id: ConnectionManagerFactory.java,v 1.1 2005/02/21 15:07:12 baruch Exp $
 */
package com.ness.fw.persistence.connectionmanager;

import java.util.*;
import java.io.IOException;
import java.io.InputStream;

import com.ness.fw.persistence.PersistenceConstants;
import com.ness.fw.util.SystemProperties;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.ResourceUtils;

/**
 * A factory for connection managers that implements the {@link ConnectionManager} Interface.
 * An application should use this factory to get a connection manager for a specific database.
 * The connection managers MUST be defined in the <I>connectionmanagers.properties</I> file.
 * Each connection manager is represented as a name which is passed to the factory, and a class name that
 * contains the package name as well.
 */
public class ConnectionManagerFactory
{
	private static final String PROPERTIES_FILE_NAME = "connectionmanagers";
	private static final String PROPERTIES_NAME_PREFIX = "propertiesNamePrefix";
	private static final String DEFAULT_CONNECTION_MANAGER = "defaultconnectionmanager";
	private static final String KEY_CLASS = ".class";
	private static final String KEY_DB_PROPERTIES = ".dbprops";
	private static final String PROPERTIES_FILE_EXTENSION = ".properties";

	private static ConnectionManagerFactory factory;

	static {
		factory = new ConnectionManagerFactory();
	}

	private Map classMapping;
	private Map propertiesMapping;
	private String defaultConnectionManager;
	private String propertiesNamePrefix;

	/**
	 * create new ConnectionManagerFactory object. 
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private ConnectionManagerFactory()
	{
		init();
	}

	/**
	 * @return
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private static ConnectionManagerFactory getInstance() throws PersistenceException
	{
		return factory;
	}

	/**
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private synchronized void init()
	{
		// load connection managers declarations

//		ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
		ResourceBundle bundle = ResourceUtils.getBundle(PROPERTIES_FILE_NAME);
		
		propertiesNamePrefix = bundle.getString(PROPERTIES_NAME_PREFIX);
		defaultConnectionManager = bundle.getString(DEFAULT_CONNECTION_MANAGER);

		classMapping = new HashMap();
		propertiesMapping = new HashMap();
		Enumeration enum = bundle.getKeys();
		while (enum.hasMoreElements())
		{
			String key = (String) enum.nextElement();
			String value = bundle.getString(key);

			if (key.endsWith(KEY_CLASS))
			{
				// get connection manager name
				String managerName = key.substring(0, key.indexOf(KEY_CLASS));
				classMapping.put(managerName, value);

				// check if declared specific properties file name for the current connection manager
				String dbpropsName = null;
				try
				{
					dbpropsName = bundle.getString(managerName + KEY_DB_PROPERTIES);
				}
				catch (MissingResourceException e)
				{
					dbpropsName =
						propertiesNamePrefix + managerName + PROPERTIES_FILE_EXTENSION;
				}

				Properties properties = new Properties();
				try
				{

//					InputStream is =
//						getClass().getResourceAsStream("/" + dbpropsName);

					InputStream is = ResourceUtils.getResourceInputStream(dbpropsName);

					properties.load(is);
					SystemProperties sysProps = new SystemProperties(properties, managerName); 
					propertiesMapping.put(managerName, sysProps);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					//					throw new PersistenceException("Properties file " + dbpropsName + " wasn't found", e);
				}
			}
		}
	}

	/**
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void reload() throws PersistenceException
	{
		init();
	}

	public ConnectionManager createConnectionManager(String name) throws PersistenceException
	{
		if (name == null)
			name = defaultConnectionManager;

		String className = (String) classMapping.get(name);
		if (className == null)
		{
			throw new PersistenceException(
				"Connection manager name  " + name + " wasn't found");
		}
		SystemProperties dbProperties = (SystemProperties) propertiesMapping.get(name);
		Logger.debug(PersistenceConstants.LOGGER_CONTEXT, "create " + name + " connection mangaer, dbProperties=" + dbProperties);

		try
		{
			Class c = Class.forName(className);
			Class argTypes[] =
				new Class[] { name.getClass(), dbProperties.getClass()};
			Object[] args = new Object[] { name, dbProperties };
			return (ConnectionManager) c.getConstructor(argTypes).newInstance(
				args);
		}
		catch (Throwable e)
		{
			PersistenceException pe = new PersistenceException(e);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error while creating connection manager: "
					+ name
					+ pe);
			throw pe;
		}
	}

	/** Get a new instance of a specific {@link ConnectionManager}.
	 * @param name The name of a connection manager. this name is the key to the class name in the <I>connectionmanagers.properties</I> file.
	 * @return ConnectionManager A new instance of the <I>ConnectionManager</I> class as specified in the <I>connectionmanagers.properties</I> file.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static ConnectionManager getConnectionManager(String name) throws PersistenceException
	{
		Logger.debug(PersistenceConstants.LOGGER_CONTEXT, "get " + name + " connection mangaer");
		
		return getInstance().createConnectionManager(name);
	}

	/**  Get a new instance of the default {@link ConnectionManager}.
	 * @return ConnectionManager A new instance of the ConnectionManager.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static ConnectionManager getConnectionManager() throws PersistenceException
	{
		return getConnectionManager(null);
	}

}