/*
 * Author: yifat har-nof
 * @version $Id: SqlPropertiesFactory.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.ness.fw.util.SystemProperties;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.resources.ResourceUtils;

/**
 * A factory of properties files for sql statements.
 */
public class SqlPropertiesFactory
{
	private static SqlPropertiesFactory factory;

	static {
		factory = new SqlPropertiesFactory();
	}

	/**
	 * mapping between properties file name and his {@link SystemProperties} loaded. 
	 */
	private Map propertiesMapping;

	/**
	 * 
	 */
	public SqlPropertiesFactory()
	{
		propertiesMapping = new HashMap(2);
	}

	/**
	 * @return
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static SqlPropertiesFactory getInstance()
	{
		return factory;
	}

	public SystemProperties getProperties(String fileName)
		throws PersistenceException
	{
		SystemProperties sysProperties =
			(SystemProperties) propertiesMapping.get(fileName);
		if (sysProperties == null)
		{
			sysProperties = loadPropertiesFile(fileName);
		}

		return sysProperties;
	}

	/**
	 * returns the property value related to the given key & file.
	 * @param fileName The name of the properties file.
	 * @param key The key name
	 * @return String The property value 
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public String getProperty(String fileName, String key)
		throws PersistenceException
	{
		String value = null;
		SystemProperties sysProperties = getProperties(fileName);
		if (sysProperties != null)
		{
			value = sysProperties.getProperty(key);
		}
		return value;
	}

	private SystemProperties loadPropertiesFile(String fileName)
		throws PersistenceException
	{
		SystemProperties sysProperties = null;
		try
		{

			//InputStream is = getClass().getResourceAsStream("/" + fileName + ".properties");
			// For now, sql properies are within the DAO's package, so
			// no system resource prefix should be used.
			InputStream is =
				ResourceUtils.getResourceInputStream(
					fileName + ".properties",
					false);

			Properties properties = new Properties();
			properties.load(is);
			sysProperties = new SystemProperties(properties, fileName);
			propertiesMapping.put(fileName, sysProperties);
		}
		catch (IOException e)
		{
			throw new PersistenceException(
				"Properties file " + fileName + " wasn't found",
				e);
		}
		return sysProperties;
	}

}
