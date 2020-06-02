/*
 * Created on 15/03/2004
 * Author: yifat har-nof
 * @version $Id: AuthManager.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.auth;

import com.ness.fw.shared.common.SystemConstants;

/**
 * Authorization Manager Interface.
 * Contains the authorization data for the user.
 */
public interface AuthManager 
{
	/**
	 * Constant for NOT_INITIALIZED authorization level. 
	 * It means that the element authorization was not initialized yet.   
	 */
	public static final int AUTH_LEVEL_NOT_INITIALIZED = SystemConstants.AUTH_LEVEL_NOT_INITIALIZED;
	
	/**
	 * Constant for ALL authorization level. 
	 * It means that all the elements will be open / available. 
	 * The elemnts could be: flow / flow state / ui elements i.e. include jsp, 
	 * toolbar, text field, button etc.   
	 */
	public static final int AUTH_LEVEL_ALL = SystemConstants.AUTH_LEVEL_ALL;
	
	/**
	 * Constant for READONLY authorization level. 
	 * It means that all the UI elements will be disabled.
	 * The flow will be created but all the activities (operation, service method, activity) 
	 * with activity type ReadWrite will no be able to be executed.   
	 */
	public static final int AUTH_LEVEL_READONLY = SystemConstants.AUTH_LEVEL_READONLY;
	
	/**
	 * Constant for NONE authorization level. 
	 * It means that all the UI elements will be not visible.
	 * The flow could be created.
	 * Cannot move to state.
	 */
	public static final int AUTH_LEVEL_NONE = SystemConstants.AUTH_LEVEL_NONE;

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
	public int getAuthLevel (String authId);
	
	
	/**
	 * check for allowed values on a data field. 
	 * This method will retrieve the current userId and authentizcation type
	 * from the session context and query the authorization system.
	 * The authorization system will check if the data value passed is allowed 
	 * for the specific field identified by authId.
	 * This method will only be used by application programmers.
	 * @param authId the authorization id identifying the data field.
	 * @param val the data value to check.
	 * @return boolean true in case the data is allowed false otherwise.
	 */
	public boolean checkAllowedValues(String authId, Number val);

	/**
	 * returns the allowed values on a data field. 
	 * This method will retrieve the current userId and authentizcation type
	 * from the session context and query the authorization system.
	 * The authorization system will check if the data value passed is allowed 
	 * for the specific field identified by authId.
	 * This method will only be used by application programmers.
	 * @param authId the authorization id identifying the data field.
	 * @param val the data value to check.
	 * @return String allowed values
	 */
	public String getAllowedValues(String authId, Number val);


	
}
