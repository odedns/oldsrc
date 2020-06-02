/*
 * Created on: 13/04/2005
 * Author:  user name
 * @version $Id: AuthenticationService.java,v 1.2 2005/04/18 16:01:46 shay Exp $
 */
package com.ness.fw.shared.servlet.authentication;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.common.auth.UserAuthData;

/**
 * @author srancus
 *
 *	The interface AuthenticationService provides a method for retrieving<br>
 *  the user data, based on a http request.
 *	Classes that implements this interface should retrieve the<br>
 *	{@link com.ness.fw.common.auth.UserAuthData} object using the<br>
 *	{@link javax.servlet.http.HttpServletRequest} object which is passed on each http request.
 */
public interface AuthenticationService 
{
	/**
	 * Initializes the names of the parameters in the request that will be used<br>
	 * for authentication.
	 * @param requestParams HashMap that contains then names of the request'<br>
	 * parameters.The method will retrieve the parameters which are needed for<br>
	 * authentication.
	 */	
	public void initParameterNames(HashMap requestParams) throws AuthenticationException;
	
	/**
	 * Returns the {@link com.ness.fw.common.auth.UserAuthData} object for a specific<br>
	 * request.<br>
	 * The classes implementing this method should retrieve the  {@link com.ness.fw.common.auth.UserAuthData} object<Br>
	 * by passing parameters from the request to the
	 * {@link com.ness.fw.common.auth.UserAuthDataFactory}
	 * @param request The {@link javax.servlet.http.HttpServletRequest} object which<br>
	 * @return
	 * @throws AuthenticationException
	 */
	public UserAuthData getUserAuthData(HttpServletRequest request) throws AuthenticationException;
}
