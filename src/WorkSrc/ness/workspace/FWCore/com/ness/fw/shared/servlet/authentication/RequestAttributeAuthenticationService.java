/*
 * Created on: 14/04/2005
 * Author:  user name
 * @version $Id: RequestAttributeAuthenticationService.java,v 1.6 2005/05/08 13:36:33 yifat Exp $
 */
package com.ness.fw.shared.servlet.authentication;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.auth.UserAuthDataFactory;

/**
 * @author srancus
 *
 *	RequestAttributeAuthenticationService retrieves the {@link com.ness.fw.common.auth.UserAuthData} object<br>
 *  by using request's attributes. 
 */
public class RequestAttributeAuthenticationService
	extends RequestHeaderAuthenticationService {

	/**
	 * Constructs new RequestAttributeAuthenticationService
	 */
	protected RequestAttributeAuthenticationService() throws AuthenticationException 
	{
		super();
	}
	
	/**
	 * Returns the {@link com.ness.fw.common.auth.UserAuthData} object for a specific<br>
	 * request.<br>
	 * The  {@link com.ness.fw.common.auth.UserAuthData} object is retrieved<Br>
	 * by passing 4 parameters from the request's attributes to the<br>
	 * {@link com.ness.fw.common.auth.UserAuthDataFactory} : <br>
	 * userId,authorizationUserId,userType,authenticationType
	 * @param request The {@link javax.servlet.http.HttpServletRequest} object<br>
	 * @return
	 * @throws AuthenticationException if the userId parameter is not found in the request's attributes.
	 */	
	public UserAuthData getUserAuthData(HttpServletRequest request) throws AuthenticationException 
	{
		//Get the request parameters values
		String userIdParamValue =  getAuthenticationParameter(request,userId);
		String authorizationUserParamValue = getAuthenticationParameter(request,authorizationUserId);
		String userTypeParamValue = getAuthenticationParameter(request,userType);
		String authenticationTypeParamValue = getAuthenticationParameter(request,authenticationType);

		//If userId parameter value is null throw exception
		if (userIdParamValue == null)
		{
			throw new AuthenticationException("parameter " + userId + " is missing in the request's attributes");
		}		
		//If authorizationUserParamValue is null use the userId parameter value
		if (authorizationUserParamValue == null)
		{
			authorizationUserParamValue = userIdParamValue;
		}
		//If userTypeParamValue is null use default value of UserAuthData
		if (userTypeParamValue == null)
		{
			userTypeParamValue = String.valueOf(UserAuthData.USER_TYPE_AGENT);
		}
		//If authenticationTypeParamValue is null use default value of UserAuthData
		if (authenticationTypeParamValue == null)
		{
			authenticationTypeParamValue = String.valueOf(UserAuthData.AUTHENTICATION_TYPE_LOGIN);
		}
		
		return UserAuthDataFactory.getUserAuthData(
			userIdParamValue,
			authorizationUserParamValue,
			Integer.parseInt(userTypeParamValue),
			Integer.parseInt(authenticationTypeParamValue), 
			request.getSession(false).getId());
	}	
	
	/**
	 * Returns the authentication parameter value by its name in the request's attributes<br>
	 * @param request The {@link javax.servlet.http.HttpServletRequest} object which is passed on each http request.
	 * @param paramName The name of the parameters in the request's attributes
	 * @return the value of the request's attribute
	 */
	protected String getAuthenticationParameter(HttpServletRequest request,String paramName)
	{
		return (String)request.getAttribute(paramName);
	}
}
