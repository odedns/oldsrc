/*
 * Created on 15/03/2004
 * Author: yifat har-nof
 * @version $Id: AuthManagerImpl.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.auth;


/**
 * Authorization Manager implementation.
 */
public class AuthManagerImpl implements AuthManager
{
	private UserAuthData userAuthData;

	/**
	 * 
	 */
	public AuthManagerImpl(UserAuthData userAuthData)
	{
		super();
		this.userAuthData = userAuthData;
	}

	/**
	 * get the authorization level according to a given authId.
	 * This method will retrieve the current userId and authentizcation type
	 * from the session context and query the authorization system.
	 * The authorization system will return the result according to 
	 * the above statuses.
	 * @param authId the id of the authorization component, flow, operation,
	 * button etc.
	 * @return int the authorization status.
	 */
	public int getAuthLevel (String authId)
	{
		if(authId != null)
		{
			if(authId.equals("ro"))
				return AUTH_LEVEL_READONLY;
			if(authId.equals("none"))
				return AUTH_LEVEL_NONE;
		}
		return AUTH_LEVEL_ALL;
	}
	
	
	/* (non-Javadoc)
	 * @see com.ness.fw.common.auth.AuthManager#checkAllowedValues(java.lang.String, java.lang.Number)
	 */
	public boolean checkAllowedValues(String authId, Number val)
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.common.auth.AuthManager#getAllowedValues(java.lang.String, java.lang.Number)
	 */
	public String getAllowedValues(String authId, Number val)
	{
		return null;
	}

}
