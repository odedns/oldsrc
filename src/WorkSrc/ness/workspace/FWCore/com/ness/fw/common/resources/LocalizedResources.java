package com.ness.fw.common.resources;

import java.util.*;
import com.ness.fw.common.exceptions.ResourceException;

/**
 * @author bhizkya
 * This class is is used as a wrapper to properties
 * It pemrits to reload a properties file.
 * 
 */

public class LocalizedResources
{

	private static final String  NOT_FOUND = "key not found: ";

	/**The managed properties file
	 */
	protected Properties properties;

	/**
	 * Creates a new LocalizedResources
	 */
	public LocalizedResources()
	{
		properties = new Properties();
	}

	/**
	 * Returns the string that is mapped with the given key
	 * @param key a key of the resource
	 * @return String
	 * @throws ResourceException - if key isn't found
	 */
	public String getString(String key) throws ResourceException
	{
		return getValue(key);
	}

	/**
	 * Method getChar.
	 * @param key a key of the resource
	 * @return char
	 * @throws ResourceException - if key isn't found
	 */
	public char getChar(String key) throws ResourceException
	{
		return getValue(key).charAt(0);
	}

	/**
	 * Returns the tokens that compose the string mapped with the given key 
	 * @param key a key of the resource
	 * @param delim
	 * @return ArrayList
	 * @throws ResourceException - if key isn't found
	 */
	public ArrayList getStringList(String key, String delim) throws ResourceException
	{
		ArrayList tokens = null;
		String value = getValue(key);
		StringTokenizer token = new StringTokenizer(value, delim);

		// if the value wasn't an empty string, create the array
		if (token.hasMoreTokens())
		{
			tokens = new ArrayList();
		}

		// Adding the tokens to the array
		while (token.hasMoreTokens())
		{
			tokens.add(token.nextToken());			
		}
		
		return tokens;
	}
	
	/**
	 * Searches for the property with the specified key in this property list. 
	 * @param key
	 * @return String
	 * @throws ResourceException - if key isn't found
	 */
	private String getValue(String key) throws ResourceException
	{
		String value = properties.getProperty(key);
		if (value == null)
		{
			value = key;
		}
		
		return value;
	}

	/**
	 * Returns the string that is mapped with the given key, without checking the results.
	 * @param key a key of the resource
	 * @return String
	 */
	public String getProperty(String key) 
	{
		return properties.getProperty(key);
	}


}
