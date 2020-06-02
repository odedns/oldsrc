/*
 * Created on: 03/06/2004
 * Author Amit Mendelson
 * @version $Id: WFExecutionService.java,v 1.2 2005/03/17 07:20:57 amit Exp $
 */
package com.ness.fw.workflow;

import com.ibm.workflow.api.ExecutionService;

/**
 * This class represents an abstraction of the infrastructure WFExecutionService.
 */
public abstract class WFExecutionService
{

	/**
	 * Returns the IBM workflow instance.
	 * Is meant for usage only in the MQWorkFlowService class and tests.
	 * Should NOT be used in external modules!
	 * @return
	 */
	public abstract ExecutionService getExecutionService();

	/**
	 * Sets the WFExecutionService from an ExecutionService.
	 * @param service The duplicated service
	 * @param userId
	 * @param password Password of the user.
	 * @param agent Agent Instance.
	 * @throws WorkFlowException
	 */
	public abstract void setExecutionService(ExecutionService service,
		String userId, String password, Agent agent)
		throws WorkFlowException;

	/**
	 * Sets the WFExecutionService from an ExecutionService.
	 * @param service The duplicated service
	 * @param userId
	 * @param UserCredentials.
	 * @param agent Agent Instance.
	 * @throws WorkFlowException
	 */
	public abstract void setExecutionService(ExecutionService service,
		String userId, UserCredentials credentials, Agent agent)
		throws WorkFlowException;
	
	/**
	 * @return userId
	 */	
	public abstract String getUserId();
	
	/**
	 * @return password
	 */
	public abstract String getPassword();
	
	/**
	 * @return Agent
	 */
	public abstract Agent getAgent();
	
	/**
	 * @return UserCredentials
	 */
	public abstract UserCredentials getUserCredentials();
	
	/**
	 * @return logonType (currently either regular or single sign-on).
	 */
	public abstract int getLogonType();
}
