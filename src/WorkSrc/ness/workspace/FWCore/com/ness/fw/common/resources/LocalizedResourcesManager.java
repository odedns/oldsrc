package com.ness.fw.common.resources;

import java.io.*;
import java.util.*;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.common.LanguagesManager;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author bhizkya
 * This class offers convenience methods to decode properties entries with
 * localization support
 * It pemrits to reload a properties file.
 * 
 * Code Example:
 * <CODE>
 *      manager = new LocalizedResourcesManager(); <br>
 *		manager.init("configuration"); <br>
 *		resource = manager.getLocalizableResource(new Locale("en","US")); <br>
 *		resource.getString("f1_message1")
 * </CODE>
 */

public class LocalizedResourcesManager
{

	/** contains the last modification time of the resource files
	 */
	private HashMap times; 
	
	/**contains all the localizable resources in the system 
	 */
	private HashMap resources;
	
	/** The properties whice represent the config properties file
	 */
	private Properties configFile;
	
	/** The config file name
	 */
	private String configFileName = null;

	/** A constant that holds the type of properties file
	 */
	private static final String PROPERTY = "properties";

	/** A constant that holds the name of the file list entry
	 */
	private static final String FILES = "fileList";

	/** A constant that holds the seperator between values in the properties files
	 */
	private static final String DELIMITER = "|";

	/**
	 * Creates a new LocalizedResourcesManager
	 */

	/**
	 * A reference to the LocalizedResourcesManager instance
	 */
	private static LocalizedResourcesManager localized  = null;

	private LocalizedResourcesManager()
	{
		resources = new HashMap();	
		times = new HashMap();	
	}
	
	public static LocalizedResourcesManager getInstance()
	{
		if (localized == null)
		{
			localized = new LocalizedResourcesManager();
		}
		
		return localized;
	}

	/** Initializing the manager by loading all the properties file with
	 *  all permotaions of files & locales
	 * @param configFileName
	 * @throws ResourceException
	 */
	public void init(String configFileName) throws ResourceException
	{
		this.configFileName = configFileName;
		// Loading the config properties file
		configFile = ResourceUtils.load(configFileName);
		// Saving the last modification time of the config file
		times.put(configFileName, new Long((ResourceUtils.getFileFromResource(configFileName + "." + PROPERTY)).lastModified()));

		// Getting all the files to load and the available locales
		ArrayList files = getFiles();
		ArrayList locales = getLocales();
					
		// Loading all the files with the locale
		for (int i=0; i<files.size(); i++)
		{
		
			String fileName = (String)files.get(i);

			for (int j=0; j<locales.size(); j++)
			{
				Locale locale = getLocale((String)locales.get(j));
				LocalizedResources localizable = null;
				
				// Create a locale entry, if necessary
				if (resources.containsKey(locale))
				{
					localizable = (LocalizedResources)resources.get(locale);
				}
				else
				{
					localizable = new LocalizedResources();					
					resources.put(locale,localizable);					
				}
							
				// Loading a specific file with it's locale
				String name = null;
				try
				{				
					name = fileName + "_" + (String)locales.get(j) + "." + PROPERTY;
					InputStream stream = ResourceUtils.getResourceInputStream(name);
					localizable.properties.load(stream);

					// Save the lastmodified of the file for reload mecanishem
					File file = ResourceUtils.getFileFromResource(name);
					times.put(name,new Long(file.lastModified()));

					Logger.info("Resources", "resource file " + name + " was loaded succsesfully");
				}
				catch (IOException ie)
				{	
					// throw an exception if file not found
					Logger.fatal("Resources",ie.getMessage());
					throw new ResourceException("resource file " + name + " wasn't found", ie);
				}		
			}
		}
		
	}

	/**
	 * Returns the localizable resource according to the default
	 * locale in the system
	 * @return
	 */
	public LocalizedResources getLocalizableResource()
	{
		return (LocalizedResources)resources.get(LanguagesManager.getDefaultLanguageSet().getLocale());
	}


	/** Returns the localizable resource
	 * @param locale the desireable locale
	 * @return
	 */
	public LocalizedResources getLocalizableResource(Locale locale)
	{
		return (LocalizedResources)resources.get(locale);
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
			configFile.clear();
			configFile = ResourceUtils.load(configFileName);
			reloadFiles(true);
		}
		// reload the files that has changed
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
		ArrayList locales =  getLocales();
		
		for (int i=0; i<files.size(); i++)
		{
			String shortName = (String)files.get(i);
			for (int j=0; j<locales.size(); j++)
			{
				String locale = (String)locales.get(j);
				String fileName = shortName + "_" + locale + "." + PROPERTY;
				reloadFile(fileName, locale, reloadAlways);
			}
		}
	}

	/** Reload a specific file
	 * @param fileName the file to be reload
	 * @param locale the locale of the file
	 * @param alwaysReload if true - reload all the files ignore the time modification
	 * if false - reload only files with changes
	 * @throws ResourceException
	 */
	private void reloadFile(String fileName, String locale, boolean alwaysReload) throws ResourceException
	{
		Long lastModified = new Long((ResourceUtils.getFileFromResource(fileName)).lastModified());
		// if there was a change in the file or sholud reload it anyway
		if (!lastModified.equals(times.get(fileName)) || alwaysReload)
		{
			System.out.println("Reloading " + fileName);
			LocalizedResources localizable = (LocalizedResources)resources.get(getLocale(locale));
	//		localizable.properties.clear();										
			try
			{
				InputStream stream = ResourceUtils.getResourceInputStream(fileName);
				localizable.properties.load(stream);
			}
			catch (IOException ioe)
			{
				// throw an exception if file not found
				Logger.fatal("Resources",ioe.getMessage());
				throw new ResourceException("resource file " + fileName + " wasn't found", ioe);
			}
		}
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

	/** Returs the desireable locale to be loaded
	 * @return
	 */
	private ArrayList getLocales()
	{
		ArrayList locales = new ArrayList();
			
		// get the locales according to the languageSets that declaread for the system
		Iterator iterator  = LanguagesManager.getSupportedLocales();		
		while (iterator.hasNext())
		{
			locales.add(iterator.next());			
		}
		
		return locales;
	}

	/** Returns a Locale according to string that represent the locale
	 * @param locale
	 * @return
	 */
	private Locale getLocale(String locale)
	{
		String language = locale;
		String country  = "";
		int index = locale.indexOf("_");

		if (index != -1)
		{
			language = locale.substring(0,index).toLowerCase();
			country =  locale.substring(index+1).toUpperCase();
		}
		
		return new Locale(language,country);
		
	}
}
