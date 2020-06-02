/*
 * Created on: 20/12/2004
 * Author: yifat har-nof
 * @version $Id: UserAuthData.java,v 1.3 2005/05/08 13:45:27 yifat Exp $
 */
package com.ness.fw.common.auth;

import java.io.Serializable;

/**
 * Contains the basic authorization data of the current user.
 */
public class UserAuthData implements Serializable, Cloneable
{
	/**
	 *  Indicates whether the user is an empty one.
	 */
	protected final static String EMPTY_USER_ID = "NONE";

	/**
	 * Constant for the NONE authentication type.
	 */
	public static final int AUTHENTICATION_TYPE_NONE = 0;

	/**
	 * Constant for the LOGIN authentication type.
	 */
	public static final int AUTHENTICATION_TYPE_LOGIN = 1;

	/**
	 * Constant for the AGENT user type.
	 */
	public static final int USER_TYPE_AGENT = 1;

	/**
	 * Constant for the EMPLOYEE user type.
	 */
	public static final int USER_TYPE_EMPLOYEE = 2;

	/**
	 * Constant for the CUSTOMER user type.
	 */
	public static final int USER_TYPE_CUSTOMER = 3;

	/**
	 * Constant for the None user type.
	 */
	public static final int USER_TYPE_NONE = 0;
	
	/**
	 * The user id of the current user (from login).
	 */
	private String userID;
	
	/**
	 * The authorization user id of the current user. determines the authorizations of the user.
	 */
	private String authorizationUserID;
	
	/**
	 * The user type which the current user loged on with. 
	 * could be: customer, company employee, agent.  
	 */
	private int userType;
	
	/**
	 * The authentication type. could be: login, card etc. 
	 */
	private int authenticationType;

	/**
	 * The session id of the user.
	 */
	private String sessionID;

	/**
	 * Creates new UserAuthData object.
	 * @param userID The user id of the current user (from login).
	 * @param authorizationUserID The authorization user id of the current user. 
	 * @param userType The user type which the current user loged on with. 
	 * could be: customer, company employee, agent.
	 * @param authenticationType The authentication type. could be: login, card etc. 
	 */
	protected UserAuthData(String userID, String authorizationUserID, int userType, int authenticationType, String sessionID)
	{
		this.userID = userID;
		this.authorizationUserID = authorizationUserID;
		this.userType = userType;
		this.authenticationType = authenticationType;	
		this.sessionID = sessionID;	
	}

	/**
	 * Returns the authentication type. could be: login, card etc. 
	 * @return int
	 */
	public int getAuthenticationType()
	{
		return authenticationType;
	}

	/**
	 * Returns the authorization user id of the current user. determines the authorizations of the user.
	 * @return String
	 */
	public String getAuthorizationUserID()
	{
		return authorizationUserID;
	}

	/**
	 * Returns the user id of the current user (from login).
	 * @return String
	 */
	public String getUserID()
	{
		return userID;
	}

	/**
	 * Returns the user type which the current user loged on with. 
	 * could be: customer, company employee, agent.
	 * @return int
	 */
	public int getUserType()
	{
		return userType;
	}

	/**
	 * Indicates whether the user is an empty one.
	 * @return boolean
	 */
	public boolean isEmptyUser()
	{
		return userID.equals(EMPTY_USER_ID);
	}

	/**
	 * Returns the session id of the user.
	 * @return String
	 */
	public String getSessionID()
	{
		return sessionID;
	}

}
