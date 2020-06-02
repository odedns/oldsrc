/*
 * Created on: 13/04/2005
 * Author:  user name
 * @version $Id: AuthenticationManager.java,v 1.4 2005/04/19 05:59:54 shay Exp $
 */
package com.ness.fw.shared.servlet.authentication;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.common.auth.UserAuthData;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AuthenticationManager 
{
	private static AuthenticationService authService;

	/**
	 * Initialized the AuthenticationService by creating a new instance of an<br>
	 * implementing class
	 * @param authServiceImpl the name of the class which implements {@link AuthenticationService}
	 * @param requestParams Map which contains the names od the request's parameters<br>
	 * which are used for authentication
	 * @throws AuthenticationException if the name of the implementing<br>
	 * class(authServiceImpl) is not legal.
	 */
	public static void initialize(String authServiceImpl,HashMap requestParams) throws AuthenticationException
	{
		try
		{
			if (authServiceImpl == null)
			{
				throw new AuthenticationException("authenticationImpl attribute is undefined");
			}
			authService = (AuthenticationService)(Class.forName(authServiceImpl).newInstance());
			authService.initParameterNames(requestParams);
		}
		catch (ClassNotFoundException ce)
		{
			throw new AuthenticationException("problem in loading authentication service class " + authServiceImpl,ce);
		}
		catch (InstantiationException ie)
		{
			throw new AuthenticationException("problem in loading authentication service class " + authServiceImpl,ie);
		}
		catch (IllegalAccessException ie)
		{
			throw new AuthenticationException("problem in loading authentication service class " + authServiceImpl,ie);
		}		
	}
	
	/**
	 * Returns the {@link com.ness.fw.common.auth.UserAuthData} object for a specific<br>
	 * request.<br>
	 * The {@link com.ness.fw.common.auth.UserAuthData} object is retrieved by calling<br>
	 * the getUserAuthData method of the right implementation of the<br>
	 * {@link AuthService} interface.   
	 * @param request The {@link javax.servlet.http.HttpServletRequest} object which<br>
	 * was passed in the request.
	 * @return {@link com.ness.fw.common.auth.UserAuthData} object
	 * @throws AuthenticationException if the authService was not already initialized
	 */
	public static UserAuthData getUserAuthData(HttpServletRequest request) throws AuthenticationException 
	{
		if (authService == null)
		{
			throw new AuthenticationException("AuthenticationService was not initialized");
		}			
		return authService.getUserAuthData(request);
	}
}
