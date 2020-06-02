/*
 * Created on 19/04/2004
 */
package com.ness.fw.flower.common;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.resources.LocalizedResourcesManager;

/**
 * @author yharnof
 *
 *	Encapsulate flower system methods.
 */
public class SystemUtil
{

	/**
	 * Init the System configuration and static globals.
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 * @param localizableConfigurationFiles
	 * @param systemConfigurationFiles
	 * @throws ResourceException
	 */
	public static void initSystemConfiguration (String localizableConfigurationFiles) throws ResourceException
	{
		// init StaticGlobals object
		//StaticGlobals staticGlobals = StaticGlobals.getInstance();

		//init LocalizedResourcesManager
		LocalizedResourcesManager resourceManager = LocalizedResourcesManager.getInstance();
		resourceManager.init(localizableConfigurationFiles);
		
		//staticGlobals.setLocalizableResourceManager(resourceManager);				
	}

}
