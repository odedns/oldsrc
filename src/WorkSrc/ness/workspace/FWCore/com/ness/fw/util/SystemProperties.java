/*
 * Author: yifat har-nof
 * @version $Id: SystemProperties.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.resources.ResourceUtils;


/**
 * Manage a Properties file values.
 */
public class SystemProperties
{

	/**
	 * The Properties object.
	 */
	private Properties properties;

	/**
	 * The properties file name.
	 */
	private String fileName;

	/**
	 * 
	 * @param properties The Properties object.
	 * @param fileName The properties file name.
	 */
	public SystemProperties(Properties properties, String fileName)
	{
		this.properties = properties;
		this.fileName = fileName;
	}
	

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws PersistenceException
	 */
	public SystemProperties (String fileName) throws ResourceException
	{
		try
		{
			this.fileName = fileName;
			InputStream is = ResourceUtils.getResourceInputStream(fileName + ".properties");
			this.properties = new Properties();
			this.properties.load(is);
		}
		catch (IOException e)
		{
			throw new ResourceException("Properties file " + fileName + " wasn't found", e);
		}
	}



	/**
	 * Returns the property value of the given key.
	 * @param key The key to get the value of.
	 * @return String The value related to the key.
	 * @throws GeneralException
	 */
	public String getProperty(String key) 
	{
		return properties.getProperty(key);
	}

	/**
	* This method simplifies the getProperty method.
	**/
	public int getInt(String key)
	{
		return (Integer.valueOf(getProperty(key)).intValue());
	}

	/**
	 * This method simplifies the getProperty method.
	**/
	public double getDouble(String key)
	{
		return (Double.valueOf(getProperty(key)).doubleValue());
	}

	/**
	 * This method simplifies the getProperty method.
	**/
	public float getFloat(String key)
	{
		return (Float.valueOf(getProperty(key)).floatValue());
	}

	/**
	 * This method simplifies the getProperty method.
	**/
	public boolean getBoolean(String key) 
    {
		return (Boolean.valueOf(getProperty(key)).booleanValue());
	}

	/**
	 * This method tokenizes the given property value and returns it as an array of values.
	 * @param key
	 * @param delimiter
	 * @return String[]
	 * @throws GeneralException
	 */
	public String[] getArray(String key, String delimiter) 
	{

		if (getProperty(key) == null) {
			return (new String[] { key });
		}

		StringTokenizer tokenizer = new StringTokenizer(getProperty(key), delimiter);
		String[] retVal = new String[tokenizer.countTokens()];
		for (int iter = 0; iter < retVal.length; iter++)
			retVal[iter] = tokenizer.nextToken();
		return (retVal);
	}

	/**
	 * This method tokenizes the given property value and returns it as an array of int values.
	 * @param key
	 * @param delimiter
	 * @return int[]
	 * @throws GeneralException
	 */
	public int[] getIntArray(String key, String delimiter)
	{
		String value = getProperty(key);
		if (value == null)
			return (null);

		StringTokenizer tokenizer = new StringTokenizer(value, delimiter);
		int[] retVal = new int[tokenizer.countTokens()];
		for (int iter = 0; iter < retVal.length; iter++)
			retVal[iter] = Integer.parseInt(tokenizer.nextToken());
		return (retVal);
	}

	/**
	 * Returns the properties file name.
	 * @return String 
	 */
	public String getFileName()
	{
		return fileName;
	}

}
