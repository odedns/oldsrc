/*
 * Created on 15/03/2004
 * Author: yifat har-nof
 * @version $Id: AuthManagerPublicImpl.java,v 1.1 2005/04/11 04:53:43 yifat Exp $
 */
package com.ness.fw.common.auth;


/**
 * Authorization Manager implementation with all public authorization level.
 */
public class AuthManagerPublicImpl implements AuthManager
{
	/**
	 * 
	 */
	public AuthManagerPublicImpl()
	{
		super();
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
