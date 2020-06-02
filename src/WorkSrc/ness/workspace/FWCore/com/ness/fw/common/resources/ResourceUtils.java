package com.ness.fw.common.resources;

import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import com.ness.fw.common.SystemInitializationManager;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.flower.common.LanguagesManager;

/**
 * @author bhizkya
 *
 * This class offers convenience methods for handling Resources whice are in the 
 * ClassPath 
 * 
 */
public class ResourceUtils
{
	/** Finds a resource with a given name. This method throws IOException 
	 *	if no resource with this name is found. the resource is searched
	 *  from a system preifx (if supplied)
	 * @param resourceName
	 * @return URL. The URL representing the resource
	 * @throws IOException
	 */	
	public static URL getResource(String resourceName) throws IOException
	{
		return getResource(resourceName,true);
	}


	/** Finds a resource with a given name. This method throws IOException
	 *  if no resource with this name is found. the resource is searched
	 *  from a system preifx (if supplied) and if was asked to search from it,
	 *  otherwise the resource is searched relative to this class.
	 * @param resourceName
	 * @param usePrefix
	 * @return
	 * @throws IOException
	 */
	public static URL getResource(String resourceName, boolean usePrefix) throws IOException
	{
	   URL url = null;
	   if (usePrefix)
	   {
	      url = ResourceUtils.class.getResource("/"+ getResourcesPrefix() + resourceName);	   	
	   }
	   else
	   {
	      url = ResourceUtils.class.getResource("/"+ resourceName);
	   }

	   if (url == null)
	   {
		  throw new IOException("resourceName " + resourceName + " wasn't found or not have adequate privileges to the resource");
	   }
	   
	   return url;

	}

	/** Finds a resource with a given name. This method throws IOException 
	 *	if no resource with this name is found. the resource is searched
	 *  from a system preifx (if supplied)
	 * @param resourceName
	 * @return InputStream. The URL representing the resource
	 * @throws IOException
	 */	
	public static InputStream getResourceInputStream(String resourceName) throws IOException
	{
		return getResourceInputStream(resourceName, true);
	}

	/**
	 * Finds a resource with a given name. 
	 * This method returns null if no resource with this name is found.
	 * @param resourceName
	 * @return InputStream.
	 */
	public static InputStream getResourceInputStream(String resourceName, boolean usePrefix) throws IOException
	{
	    InputStream stream = null;
		if (usePrefix)
		{
			 stream = ResourceUtils.class.getResourceAsStream("/"+ getResourcesPrefix() + resourceName);
		}
		else
		{
			stream = ResourceUtils.class.getResourceAsStream("/"+ resourceName);
		}

	   if (stream == null)
	   {
	      throw new IOException("resourceName " + resourceName + " wasn't found");
	   }
	   
	   return stream;

	}

	/** Returns the pyshical file the represent the resourceName
	 * @param resourceName
	 * @return
	 * @throws ResourceException
	 */
	public static File getFileFromResource(String resourceName) throws ResourceException
	{
		File file = null;
		try
		{
			URL url = getResource(resourceName);
			file = new File(url.getFile().substring(1));	
		}
		catch (IOException ie)
		{
			throw new ResourceException(ie);
		}
		return file;		
	}
	

	/**
	 * Loads properties file
	 * @param name The properies file name
	 * @return Properties
	 * @throws ResourceException
	 */
	public static Properties load(String name) throws ResourceException
	{
		Properties properties = new Properties();

		//Load the properties file
		try
		{
			 properties.load(getResourceInputStream(name + ".properties"));
		}
		catch (IOException e)
		{
			throw new ResourceException("Properties file " + name + " wasn't found", e);
		}

		return properties;
	}


	public static ResourceBundle getBundle(String name)
	{
		return ResourceBundle.getBundle(getResourcesPrefix() + name);
	}

	/**
	 * @return String. The resources prefix
	 */
	public static String getResourcesPrefix()
	{
		String prefix = SystemInitializationManager.getInstance().getSystemResourcesPrefix();
		if (prefix == null)
		{
			return "";
		}
		else
		{
			return prefix;
		}
	}
	
	public static LocalizedResources getLocalizedResources(Locale userLocale)
	{
		// TODO ask baruch about moving the logic to the getLocalizableResource method
		Locale locale = LanguagesManager.getSupportedLocale(userLocale);
		return LocalizedResourcesManager.getInstance().getLocalizableResource(locale);
	}

	/**
	 * get the resource absolute path according to the resource relative path
	 * @param resourceRelativePath
	 * @param usePrefix
	 * @return String path
	 * @throws IOException
	 */
	public static String getResourceAbsolutePath(String resourceRelativePath, boolean usePrefix) throws IOException
	{
		//URL url = objectClass.getClassLoader().getResource(resourceRelativePath);
		URL url  = ResourceUtils.getResource(resourceRelativePath, usePrefix);
		
		String path = null;
		if (url != null)
		{
			path = url.getPath();
		}
		return path;			
	}

	/**
	 * get the resource absolute path according to the resource relative path
	 * @param resourceRelativePath
	 * @return String path
	 */
	public static String getResourceAbsolutePath(String resourceRelativePath) throws IOException
	{
		// By default initialization resources will be in different location
		// than the prefix, so locate them without it.
		return getResourceAbsolutePath(resourceRelativePath, false);
	}

}
