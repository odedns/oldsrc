/*
 * Created on: 19/04/2005
 * Author:  user name
 * @version $Id: FileAuthenticationService.java,v 1.2 2005/05/08 13:36:33 yifat Exp $
 */
package com.ness.fw.shared.servlet.authentication;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.auth.UserAuthDataFactory;

/**
 * @author srancus
 *
 *	FileAuthenticationService retrieves the {@link com.ness.fw.common.auth.UserAuthData} object<br>
 *  by using definitions from an xml file
 */
public class FileAuthenticationService implements AuthenticationService 
{
	
	/**
	 * Constructs new FileAuthenticationService
	 */
	protected FileAuthenticationService() throws AuthenticationException 
	{
		
	}

	/**
	 * This has an empty implementation because it does not use parameters<br>
	 * from the request.
	 */
	public void initParameterNames(HashMap requestParams) throws AuthenticationException 
	{
	}

	/**
	 * Returns the {@link com.ness.fw.common.auth.UserAuthData} object for a specific<br>
	 * request.<br>
	 * The  {@link com.ness.fw.common.auth.UserAuthData} object is retrieved<Br>
	 * by calling to <br>
	 * {@link com.ness.fw.common.auth.UserAuthDataFactory.getUserAuthData} <br>
	 * without parameters.
	 * @param request The {@link javax.servlet.http.HttpServletRequest} object<br>
	 * @return {@link com.ness.fw.common.auth.UserAuthData} object
	 * @throws AuthenticationException.
	 */
	public UserAuthData getUserAuthData(HttpServletRequest request) throws AuthenticationException 
	{
		return UserAuthDataFactory.getUserAuthDataFromXML(request.getSession(false).getId());
	}
}
