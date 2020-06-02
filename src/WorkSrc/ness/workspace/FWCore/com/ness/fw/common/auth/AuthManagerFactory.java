/*
 * Created on 19/05/2004
 * Author: yifat har-nof
 * @version $Id: AuthManagerFactory.java,v 1.2 2005/04/11 04:53:44 yifat Exp $
 */
package com.ness.fw.common.auth;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.shared.common.SystemConstants;


/**
 * Provide authorization managers.
 */
public class AuthManagerFactory
{

	public final static String LOGGER_CONTEXT = "AUTH MANAGER FACTORY";
	
	public final static int TYPE_CUSTOM = 1;
	public final static int TYPE_PUBLIC = 2;

	/**
	 * Return AuthManager of the class written in the properties file definition.
	 * @param userAuthData The basic authorization data of the user.
	 * @return AuthManager
	 * @throws AuthException
	 */
	public static AuthManager getAuthManager (UserAuthData userAuthData) throws AuthException
	{
		try
		{

			String className = SystemResources.getInstance().getProperty(
				SystemConstants.SYS_PROPS_KEY_AUTH_MANAGER_IMPL);
			Logger.debug(LOGGER_CONTEXT, "Initializing AuthManager with [" + className + "] implementation for user [" + userAuthData.getUserID() + "]");

			Constructor constructor =
				Class.forName(className).getConstructor(new Class[] {userAuthData.getClass()});
			
			return (AuthManager) constructor.newInstance(new Object[]{userAuthData}); 
		}
		catch (Throwable e)
		{
			throw new AuthException("error while AuthManager instanciation", e);
		}
	}

	/**
	 * Return MenuAuthManager of the class written in the properties file definition.
	 * @param userAuthData The basic authorization data of the user.
	 * @param localizedResources The specific locale resource manager.
	 * @return MenuAuthManager
	 * @throws AuthException
	 */
	public static MenuAuthManager getMenuAuthManager (UserAuthData userAuthData, LocalizedResources localizedResources) throws AuthException
	{
		try
		{
			String className = SystemResources.getInstance().getProperty(
				SystemConstants.SYS_PROPS_KEY_MENU_AUTH_MANAGER_IMPL);
			Logger.debug(LOGGER_CONTEXT, "Initializing MenuAuthManager with [" + className + "] implementation for user [" + userAuthData.getUserID() + "]");
			Constructor constructor =
				Class.forName(className).getConstructor(new Class[] {userAuthData.getClass(), localizedResources.getClass()});
			
			return (MenuAuthManager) constructor.newInstance(new Object[]{userAuthData, localizedResources}); 
		}
		catch(InvocationTargetException e)
		{
			if(e.getTargetException() instanceof AuthException)
			{
				throw ((AuthException)e.getTargetException());
			}
			throw new AuthException ("error while MenuAuthManager instanciation", e.getTargetException());
		}
		catch (Throwable e)
		{
			throw new AuthException("error while MenuAuthManager instanciation", e);
		}
	}

	/**
	 * Returns an implementation of AuthManager with all public authorization level. 
	 * @return AuthManager
	 * @throws AuthException
	 */
	public static AuthManager getPublicAuthManager () throws AuthException
	{
		return new AuthManagerPublicImpl(); 
	}

	/**
	 * Returns an implementation of MenuAuthManager without menu items loading. 
	 * @return MenuAuthManager
	 * @throws AuthException
	 */
	public static MenuAuthManager getEmptyMenuAuthManager () throws AuthException
	{
		return new MenuAuthManagerEmptyImpl();
	}

}
