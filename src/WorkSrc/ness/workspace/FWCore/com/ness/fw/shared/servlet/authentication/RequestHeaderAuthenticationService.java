/*
 * Created on: 13/04/2005
 * Author:  user name
 * @version $Id: RequestHeaderAuthenticationService.java,v 1.7 2005/05/08 13:36:33 yifat Exp $
 */
package com.ness.fw.shared.servlet.authentication;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.auth.UserAuthDataFactory;

/**
 * @author srancus
 *
 *	RequestHeaderAuthenticationService retrieves the {@link com.ness.fw.common.auth.UserAuthData} object<br>
 *  by using parameters from the request's header. 
 */
public class RequestHeaderAuthenticationService implements AuthenticationService 
{
	protected String userId;
	protected String authorizationUserId;
	protected String userType;
	protected String authenticationType;
	
	private final static String PROPERTY_KEY_REQUEST_PARAMETR_USER_ID = "userId";
	private final static String PROPERTY_KEY_REQUEST_PARAMETR_AUTH_USER_ID = "authUserId";
	private final static String PROPERTY_KEY_REQUEST_PARAMETR_USER_TYPE = "userType";
	private final static String PROPERTY_KEY_REQUEST_PARAMETR_AUTH_TYPE = "authType";

	/**
	 * Constructs new RequestHeaderAuthenticationService
	 */
	protected RequestHeaderAuthenticationService() throws AuthenticationException
	{
	}
	
	/**
	 * Initializes the names of the parameters in the request that will be used<br>
	 * for authentication.
	 * @param requestParams HashMap that contains then names of the request'<br>
	 * parameters.The method will retrieve the parameters which are needed for<br>
	 * authentication.
	 * @throws AuthenticationException if one of the names of the parameters of<br>
	 * the request is null.
	 */
	public void initParameterNames(HashMap requestParams) throws AuthenticationException
	{
		userId = getRequestParamName(requestParams,PROPERTY_KEY_REQUEST_PARAMETR_USER_ID);
		authorizationUserId = getRequestParamName(requestParams,PROPERTY_KEY_REQUEST_PARAMETR_AUTH_USER_ID);
		userType = getRequestParamName(requestParams,PROPERTY_KEY_REQUEST_PARAMETR_USER_TYPE);
		authenticationType = getRequestParamName(requestParams,PROPERTY_KEY_REQUEST_PARAMETR_AUTH_TYPE);
	}
	
	/**
	 * Returns name of the request's parameter from the HashMap that was passed<br>
	 * in the initParameterNames method
	 * @param requestParams the HashMap of request parameters names
	 * @param paramName the name of the parameter in the HashMap
	 * @return The name of the parameter in the request,this name will be used<br>
	 * when retrieving the value from the request.
	 * @throws AuthenticationException if one of the names of the parameters of<br>
	 * the request is null.
	 */
	private String getRequestParamName(HashMap requestParams,String paramName) throws AuthenticationException
	{
		String requestParam = (String)requestParams.get(paramName);
		if (requestParam == null)
		{
			throw new AuthenticationException("requestParam with name=" + paramName +  " is undefined");
		}
		return requestParam;
	}
	
	/**
	 * Returns the {@link com.ness.fw.common.auth.UserAuthData} object for a specific<br>
	 * request.<br>
	 * The  {@link com.ness.fw.common.auth.UserAuthData} object is retrieved<Br>
	 * by passing 4 parameters from the request's header to the<br>
	 * {@link com.ness.fw.common.auth.UserAuthDataFactory} : <br>
	 * userId,authorizationUserId,userType,authenticationType
	 * @param request The {@link javax.servlet.http.HttpServletRequest} object<br>
	 * @return {@link com.ness.fw.common.auth.UserAuthData} object
	 * @throws AuthenticationException if one of the parameters is not found in the request's header.
	 */
	public UserAuthData getUserAuthData(HttpServletRequest request) throws AuthenticationException 
	{
		//Get the request parameters values
		String userIdParamValue =  getAuthenticationParameter(request,userId);
		String authorizationUserParamValue = getAuthenticationParameter(request,authorizationUserId);
		String userTypeParamValue = getAuthenticationParameter(request,userType);
		String authenticationTypeParamValue = getAuthenticationParameter(request,authenticationType);

		//If userId parameter value is null throw an AuthenticationException
		if (userIdParamValue == null)
		{
			throw new AuthenticationException("parameter userId is missing in the request's header");
		}	
		//If userId parameter value is null throw an AuthenticationException	
		if (authorizationUserParamValue == null)
		{
			throw new AuthenticationException("parameter authorizationUserId is missing in the request's header");
		}
		//If userTypeParamValue parameter value is null throw an AuthenticationException
		if (userTypeParamValue == null)
		{
			throw new AuthenticationException("parameter userTypeParam is missing in the request's header");
		}
		//If authenticationTypeParamValue parameter value is null throw an AuthenticationException
		if (authenticationTypeParamValue == null)
		{
			throw new AuthenticationException("parameter authenticationType is missing in the request's header");
		}
		
		return UserAuthDataFactory.getUserAuthData
			(userIdParamValue,
			authorizationUserParamValue,
			Integer.parseInt(userTypeParamValue),
			Integer.parseInt(authenticationTypeParamValue),
			request.getSession(false).getId());
	}
	
	/**
	 * Returns the authentication parameter value by its name in the request's header<br>
	 * @param request The {@link javax.servlet.http.HttpServletRequest} object which is passed on each http request.
	 * @param paramName The name of the parameters in the request's header
	 * @return the value of the request's header
	 */
	protected String getAuthenticationParameter(HttpServletRequest request,String paramName)
	{
		return request.getHeader(paramName);
	}
}
