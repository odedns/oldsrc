package com.ness.fw.common.resources;

import java.io.*;
import java.util.*;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.shared.common.SystemConstants;

/**
 * @author bhizkya
 * This class offers convenience methods to decode properties entries with types
 * like boolean, float, deouble, etc.
 * It pemrits to reload a properties file.
 * 
 * Code Example:
 * <CODE>
 *      configurator = new Configuration(); <br>
 *		configurator.init("configuration"); <br>
 *		configurator.getString("refreshRate")
 * </CODE>
 */
public class SystemResources
{

	/** contains the last modification time of the resource files
	 */
	private HashMap times; 
		
	/** The properties whice represent the config properties file
	 */
	private Properties configFile;

	/** The config file name
	 */
	private String configFileName = null;

	/** Indication for checking multiple entries
	 */
	private boolean checkMultipleEntries = true;

	/** Indication for checking multiple entries
	 */
	private boolean errorOnMultipleEntries = false;

	/** A constant that holds the type of properties file
	 */
	private static final String PROPERTY = "properties";

	/** A constant that holds the name of the file list entry
	 */
	private static final String FILES = "fileList";

	/** A constant that holds the seperator between values in the properties files
	 */
	private static final String DELIMITER = "|";

	/**The managed properties file
	 */
	protected Properties properties;

	private static SystemResources systemProperties = null;

	/**
	 * Creates a new Configuration
	 */
	private SystemResources()
	{
		properties = new Properties();
		times = new HashMap();
	}

	public static SystemResources getInstance()
	{
		if (systemProperties == null)
		{
			systemProperties = new SystemResources();
		}
		
		return systemProperties;
	}



	/** Initializing the manager by loading all the properties files
	 * @param configFileName
	 * @throws ResourceException
	 */
	public void init(String configFileName) throws ResourceException
	{
		this.configFileName = configFileName;
		// Loading the config properties file
		this.configFile = ResourceUtils.load(configFileName);
		// Saving the last modification time of the config file
		times.put(configFileName, new Long((ResourceUtils.getFileFromResource(configFileName + "." + PROPERTY)).lastModified()));
		ArrayList files = getFiles();
		InputStream stream;
					
		// Loading all the files with the locale
		String fileName = null;
		for (int i=0; i<files.size(); i++)
		{		
			String shortFileName = (String)files.get(i);
			fileName = shortFileName + "." + PROPERTY;							
			try
			{				
				stream = ResourceUtils.getResourceInputStream(fileName);				
				loadProperty(stream,checkMultipleEntries);
	
				// Save the lastmodified of the file for reload mecanishem
				File file = ResourceUtils.getFileFromResource(fileName);
				times.put(fileName,new Long(file.lastModified()));
				Logger.info("Resources", "resource file " + fileName + " was loaded succsesfully");

			}
			catch (IOException ie)
			{
				// throw an exception if file not found
				Logger.fatal("Resources",ie.getMessage());
				throw new ResourceException("resource file " + fileName + " wasn't found", ie);
			}
										
		}		
	}

	/**
	 * Remove entries from the general properties
	 * @param stream
	 * @throws IOException
	 * @throws ResourceException
	 */
	private void clearProperty(InputStream stream) throws IOException, ResourceException 
	{
		stream.mark(1000000);
		Properties tmpProps = new Properties();
		tmpProps.load(stream);
		// Looping over the keys, to find multiple entries
		Enumeration keys = tmpProps.keys();
		while (keys.hasMoreElements())
		{
			String key = (String)keys.nextElement();
			properties.remove(key);
		}
		stream.reset();
	}

	/**
	 * Load a property file, check for multiple entries
	 * @param stream
	 * @param checkMultipleEntries
	 * @throws IOException
	 * @throws ResourceException
	 */
	private void loadProperty(InputStream stream,boolean checkMultipleEntries) throws IOException, ResourceException 
	{
		if (checkMultipleEntries)
		{
			stream.mark(1000000);
			Properties tmpProps = new Properties();
			tmpProps.load(stream);
			// Looping over the keys, to find multiple entries
			Enumeration keys = tmpProps.keys();
			while (keys.hasMoreElements())
			{
				String key = (String)keys.nextElement();
				if (properties.containsKey(key))
				{
					if (errorOnMultipleEntries)
					{
						throw new ResourceException("key " +  key + " already exist");
					}
					else
					{
						Logger.warning("Resorces","key " +  key + " already exist");
					}
					
				}
			}
			stream.reset();
		}
		properties.load(stream);		
	}

	/** Reloads the propery file according to changes in the file:
	 * if the config file has changed all the files will be reloaded, otherwise reload
	 * only the files that changed
	 * @throws ResourceException
	 */
	public void reload() throws ResourceException
	{	

		// if config file has changed load all the resources from scratch
		Long configLastModified = new Long((ResourceUtils.getFileFromResource(configFileName + "." + PROPERTY)).lastModified());
		if (!configLastModified.equals((Long)times.get(configFileName)))
		{
			Logger.debug("Resorces","Reloading all files");
			configFile.clear();
			configFile = ResourceUtils.load(configFileName);
			reloadFiles(true);
		}
		// Looping through the files and reload the file that has changed
		else
		{
			reloadFiles(false);
		}
	}

	/** Reload all the files accoriding to the fileList in the config file
	 * @param reloadAlways if true - reload all the files ignore the time modification
	 * if false - reload only files with changes
	 * @throws ResourceException
	 */
	private void reloadFiles(boolean reloadAlways) throws ResourceException
	{
		ArrayList files = getFiles();
		
		for (int i=0; i<files.size(); i++)
		{
			String fileName = (String)files.get(i) +  "." + PROPERTY;
			reloadFile(fileName, reloadAlways);
		}
	}

	/** Reload a specific file
	 * @param fileName the file to be reload
	 * @param locale the locale of the file
	 * @param alwaysReload if true - reload all the files ignore the time modification
	 * if false - reload only files with changes
	 * @throws ResourceException
	 */
	private void reloadFile(String fileName, boolean alwaysReload) throws ResourceException
	{
		Long lastModified = new Long((ResourceUtils.getFileFromResource(fileName)).lastModified());
		if (!lastModified.equals(times.get(fileName)) || alwaysReload)
		{
			Logger.debug("Resorces","Reloading" +  fileName);
			try
			{
				InputStream stream = ResourceUtils.getResourceInputStream(fileName);
				
				// load the property
				loadProperty(stream,checkMultipleEntries);
			}
			catch (IOException ioe)
			{
				// throw an exception if file not found
				Logger.fatal("Resources",ioe.getMessage());
				throw new ResourceException("resource file " + fileName + " wasn't found", ioe);
			}
		}
	}


	/**
	 * Returns the boolean that is mapped with the given key
	 * @return boolean
	 * @throws ResourceException - if key isn't found
	 */
	public boolean getBoolean(String key) throws ResourceException
	{
		return new Boolean(getValue(key)).booleanValue();
	}

	/**
	 * Returns the int that is mapped with the given key
	 * @param key a key of the resource
	 * @return int
	 * @throws ResourceException - if key isn't found
	 */
	public int getInteger(String key) throws ResourceException
	{
		return Integer.parseInt(getValue(key));
	}

	/**
	 * Returns the float that is mapped with the given key 
	 * @param key a key of the resource
	 * @return float
	 * @throws ResourceException - if key isn't found
	 */
	public float getFloat(String key) throws ResourceException
	{
		return Float.parseFloat(getValue(key));
	}

	/**
	 * Returns the long that is mapped with the given key 
	 * @param key a key of the resource
	 * @return long
	 * @throws ResourceException - if key isn't found
	 */
	public long getLong(String key) throws ResourceException
	{
		return Long.parseLong(getValue(key));
	}

	/**
	 * Returns the double that is mapped with the given key 
	 * @param key a key of the resource
	 * @return double
	 * @throws ResourceException - if key isn't found
	 */
	public double getDouble(String key) throws ResourceException
	{
		return Double.parseDouble(getValue(key));
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
			throw new ResourceException("Key " + key + " wasn't found --> NullPointerException will be generated");
		}
		
		return value;
	}

	/**
	 * Returns the string that is mapped with the given key, without checking the results.
	 * @param key a key of the resource
	 * @return String
	 * @throws ResourceException - if key isn't found
	 */
	public String getProperty(String key) 
	{
		return properties.getProperty(key);
	}

	/**
	 * Indicates whether the system is in debug mode. 
	 * @return boolean
	 * @throws ResourceException
	 */
	public boolean isDebugMode() 
	{
		String tmpLevel = getProperty(SystemConstants.SYS_PROPS_KEY_LOGGER_LEVEL_MODE);
		if (tmpLevel == null)
		{
			return false;
		}
		
		int level = Integer.parseInt(tmpLevel);
		if ((level & Logger.LEVEL_DEBUG) == 0)
		{
			return false;
		}
		return true;

	}
	
	public int getSystemLoggerLevel() throws ResourceException
	{
		return getInteger(SystemConstants.SYS_PROPS_KEY_LOGGER_LEVEL_MODE);
	}


	/** Returns the files to be loaded 
	 * @return
	 */
	private ArrayList getFiles()
	{
		ArrayList files = new ArrayList();
		// Getting the files
		StringTokenizer filesList = new StringTokenizer((String) configFile.getProperty(FILES), DELIMITER);	

		while (filesList.hasMoreTokens())
		{
			files.add(filesList.nextToken());			
		}
		
		return files;

	}

	public boolean isErrorOnMultipleEntries() 
	{
		return errorOnMultipleEntries;
	}

	public void setErrorOnMultipleEntries(boolean errorOnMultipleEntries) 
	{
		this.errorOnMultipleEntries = errorOnMultipleEntries;
	}

	public boolean isCheckMultipleEntries()
	{
		return checkMultipleEntries;
	}

	public void setCheckMultipleEntries(boolean checkMultipleEntries) 
	{
		this.checkMultipleEntries = checkMultipleEntries;
	}

}
