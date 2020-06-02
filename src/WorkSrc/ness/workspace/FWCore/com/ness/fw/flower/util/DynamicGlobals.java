/*
 * Created on 21/03/2004
 * Author: yifat har-nof
 * @version $Id: DynamicGlobals.java,v 1.4 2005/04/11 05:05:31 yifat Exp $
 */
package com.ness.fw.flower.util;

import java.util.*;

import com.ness.fw.common.auth.AuthException;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.resources.*;
import com.ness.fw.util.MessagesContainer;

/**
 * Contains dynamic global objects to service the session.
 * The global objects can be retrieved according to their given name.
 */
public class DynamicGlobals
{
	/**
	 * Constant for the global paramater that holds the {@link LocalizedResources}. 
	 * The object contains the localized resources  according to the user's locale.
	 */
	private static final String GB_LOCALIZED_RESOURCES = "LocalizedResources";

	/**
	 * Constant for the global paramater that holds the {@link MessagesContainer}. 
	 * The object holds the messages from the last user request.
	 */
	private static final String GB_MESSAGES_CONTAINER = "MessagesContainer";

	/**
	 * Constant for the global paramater that holds the {@link AuthLevelsManager}.
	 * The object contains the auth managers with the allowed flows, components & menu trees,
	 * and provide services to determine the authorization level of the components. 
	 */
	private static final String GB_AUTH_LEVELS_MANAGER = "AuthLevelsManager";

	/**
	 * Constant for the global paramater that holds the {@link UserAuthData}. 
	 * The object contains the basic authorization data of the current user.
	 */
	private static final String GB_USER_AUTH_DATA = "UserAuthData";

	/**
	 * The session id.
	 */
	private String sessionId;

	/**
	 * The user's Locale.
	 */
	private Locale locale;

	/**
	 * The mapping between the name of the global object and the reference to them.
	 */
	private Map objectsMapping;
	
	/**
	 * Indicates whether the current user is help admin. 
	 */
	private Boolean helpAdmin;
	
	/**
	 * Indicates whether the current user is authorization admin. 
	 */
	private Boolean authAdmin;

	/**
	 * creates new DynamicGlobals object.
	 */
	public DynamicGlobals(String sessionId, Locale locale, UserAuthData userAuthData) throws AuthException
	{
		this.sessionId = sessionId;
		this.locale = locale;
		objectsMapping = new HashMap(5);
		init(userAuthData);
	}

	/**
	 * creates new DynamicGlobals object.
	 */
	public DynamicGlobals(String sessionId, Locale locale) throws AuthException
	{
		this.sessionId = sessionId;
		this.locale = locale;
		objectsMapping = new HashMap(5);
		init(null);
	}


	/**
	 * Init the object with the basic global objects.
	 * @param systemConfigurationFile
	 */
	private void init(UserAuthData userAuthData) throws AuthException
	{
		// init LocalizedResources
		LocalizedResourcesManager localizableManager =
					LocalizedResourcesManager.getInstance();
		LocalizedResources localizedResources = localizableManager.getLocalizableResource(locale);
		setLocalizedResources(localizedResources);
		
		// set the basic authorization data of the current user.
		setUserAuthData(userAuthData);

		// init AuthLevelsManager
		setAuthLevelsManager(new AuthLevelsManager(userAuthData, localizedResources));
	}

	/**
	 * Add / Change a global Object.
	 * @param objectName The object name.
	 * @param obj The object.
	 */
	public void setObject(String objectName, Object globalObject)
	{
		synchronized (objectsMapping)
		{
			objectsMapping.put(objectName, globalObject);
		}
	}

	/**
	 * Returns a global Object according to the object name. 
	 * @param objectName The object name to return. 
	 * @return Object A global object.
	 */
	public Object getObject(String objectName)
	{
		return objectsMapping.get(objectName);
	}

	/**
	 * Returns the LocalizedResources object contains the localized resources 
	 * according to the user's locale.
	 * @return LocalizedResources
	 */
	public LocalizedResources getLocalizedResources()
	{
		return (LocalizedResources) getObject(GB_LOCALIZED_RESOURCES);
	}

	/**
	 * Sets the {@link LocalizedResources} object contains the localized resources 
	 * according to the user's locale.
	 * @param localizedResources
	 */
	public void setLocalizedResources(LocalizedResources localizedResources)
	{
		setObject(GB_LOCALIZED_RESOURCES, localizedResources);
	}

	/**
	 * Returns the UserAuthData object contains the basic authorization data 
	 * of the current user.
	 * @return UserAuthData
	 */
	public UserAuthData getUserAuthData()
	{
		return (UserAuthData) getObject(GB_USER_AUTH_DATA);
	}

	/**
	 * Sets the {@link Configuration} object that contains the basic authorization data 
	 * of the current user.
	 * @param userAuthData
	 */
	private void setUserAuthData(UserAuthData userAuthData)
	{
		setObject(GB_USER_AUTH_DATA, userAuthData);
	}

	/**
	 * Returns the global <code>MessagesContainer</code> that holds 
	 * the messages from the last user request.
	 * @return MessagesContainer
	 */
	public MessagesContainer getMessagesContainer()
	{
		MessagesContainer msgContainer = (MessagesContainer) getObject(GB_MESSAGES_CONTAINER);
		if (msgContainer == null)
		{
			msgContainer = new MessagesContainer();
			setObject(GB_MESSAGES_CONTAINER, msgContainer);
		}
		return msgContainer;
	}

	/**
	 * Clear the MessagesContainer and returns the previous MessagesContainer object.  
	 * @return MessagesContainer The previous container. 
	 */
	public MessagesContainer removeMessagesContainer()
	{
		MessagesContainer msgContainer = (MessagesContainer) getObject(GB_MESSAGES_CONTAINER);
		if (msgContainer != null)
		{
			setObject(GB_MESSAGES_CONTAINER, null);
		}
		else
		{
			msgContainer = new MessagesContainer();
		}
		return msgContainer;
	}

	/**
	 * Sets the {@link AuthLevelsManager} object.
	 * The object contains the auth managers with the allowed flows, components & menu trees,
	 * and provide services to determine the authorization level of the components.
	 * @param AuthLevelsManager
	 */
	private void setAuthLevelsManager(AuthLevelsManager authLevelsManager)
	{
		setObject(GB_AUTH_LEVELS_MANAGER, authLevelsManager);
	}

	/**
	 * Returns the <code>AuthLevelsManager</code>.
	 * The object contains the auth managers with the allowed flows, components & menu trees,
	 * and provide services to determine the authorization level of the components.
	 * @return AuthLevelsManager
	 */
	public AuthLevelsManager getAuthLevelsManager()
	{
		return (AuthLevelsManager) getObject(GB_AUTH_LEVELS_MANAGER);
	}

	/**
	 * Sets the user's {@link Locale}.
	 * @return Locale
	 */
	public Locale getLocale()
	{
		return locale;
	}

	/**
	 * Returns the user's {@link Locale}.
	 * @param locale
	 */
	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	/**
	 * Returns the session id
	 * @return String sessionId 
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * Indicates whether the current user is authorization admin.
	 * @return boolean
	 */
	public boolean isAuthAdmin()
	{
		return authAdmin != null && authAdmin.booleanValue();
	}

	/**
	 * Indicates whether the current user is help admin. 
	 * @return boolean
	 */
	public boolean isHelpAdmin()
	{
		return helpAdmin != null && helpAdmin.booleanValue();
	}

	/**
	 * Indicates whether the current user is authorization admin.
	 * @return Boolean
	 */
	public Boolean getAuthAdmin()
	{
		return authAdmin;
	}


	/**
	 * Indicates whether the current user is help admin. 
	 * @return Boolean
	 */
	public Boolean getHelpAdmin()
	{
		return helpAdmin;
	}

	/**
	 * Set indication whether the current user is authorization admin.
	 * @param authAdmin boolean
	 */
	public void setAuthAdmin(boolean authAdmin)
	{
		this.authAdmin = new Boolean(authAdmin);
	}

	/**
	 * Set indication whether the current user is help admin. 
	 * @param helpAdmin boolean
	 */
	public void setHelpAdmin(boolean helpAdmin)
	{
		this.helpAdmin = new Boolean(helpAdmin);
	}

}
