/*
 * Created on 21/03/2004
 * Author: yifat har-nof
 * @version $Id: StaticGlobals.java,v 1.1 2005/02/21 15:07:20 baruch Exp $
 */
package com.ness.fw.flower.util;

import java.util.*;
import com.ness.fw.common.resources.*;

/**
 * Contains static global objects to service the application.
 * The global objects can be retrieved according to their given name.
 */
public class StaticGlobals
{
	
	/**
	 * The instance of the object.
	 */
	private static StaticGlobals instance;
	
	/**
	 * The mapping between the name of the global object and the reference to them.
	 */
	private Map applicationObjectsMapping;

	/**
	 * creates new StaticGlobals object.
	 */
	private StaticGlobals()
	{
		applicationObjectsMapping = new HashMap(2);
	}

	/**
	 * Returns the StaticGlobals.
	 * @return StaticGlobals
	 */	
	public static StaticGlobals getInstance()
	{
		if(instance == null)
		{
			instance = new StaticGlobals();
		}
		return instance;
	}

	private void putObject (String name, Object obj)
	{
		synchronized (applicationObjectsMapping)
		{
			applicationObjectsMapping.put(name, obj);
		}
	}
	
	public LocalizedResourcesManager getLocalizableResourceManager ()
	{
		return (LocalizedResourcesManager)applicationObjectsMapping.get("LocalizableResourceManager"); 
	}

	public void setLocalizableResourceManager (LocalizedResourcesManager localizableResourceManager)
	{
		putObject("LocalizableResourceManager", localizableResourceManager); 
	}
}
