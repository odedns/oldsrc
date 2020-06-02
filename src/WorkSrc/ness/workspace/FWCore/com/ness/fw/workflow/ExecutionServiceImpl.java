/*
 * Created on: 08/08/2004
 * Author Amit Mendelson
 * @version $Id: ExecutionServiceImpl.java,v 1.3 2005/04/21 09:15:19 amit Exp $
 */
package com.ness.fw.workflow;

import com.ibm.workflow.api.ExecutionService;

/**
 * This class wraps MQ Workflow ExecutionService.
 */
public class ExecutionServiceImpl extends WFExecutionService
{

	/**
	 * Private instance of MQ WF ExecutionService.
	 */
	private ExecutionService executionService;
	
	/**
	 * userId, is used for relogin in case of time-out.
	 */
	private String userId;
	
	/**
	 * password, is used for relogin in case of time-out.
	 */
	private String password;
	
	/**
	 * agent, is used for relogin in case of time-out.
	 */
	private Agent agent;

	/**
	 * userCredentials
	 */
	private UserCredentials userCredentials;

	/**
	 * logonType
	 */
	private int logonType = -1;

	/**
	 * Returns the IBM workflow instance.
	 * Is meant for usage only in the MQWorkFlowService class and tests.
	 * Should NOT be used in external modules!
	 * @return MQ WF WFExecutionService
	 */
	public ExecutionService getExecutionService()
	{
		return executionService;
	}

	/**
	 * Sets the WFExecutionService from an ExecutionService.
	 * @param service
	 * @param userId
	 * @param password
	 * @param agent
	 * @throws WorkFlowException
	 */
	public void setExecutionService(ExecutionService service,
		String userId, String password, Agent agent)
		throws WorkFlowException
	{

		if (service == null)
		{
			throw new WorkFlowException(WFExceptionMessages.EXECUTION_SERVICE_NULL);
		}
		this.executionService = service;
		this.userId = userId.toUpperCase();
		this.password = password;
		this.agent = agent;
		this.logonType = WFConstants.LOGON_TYPE_REGULAR;
	}

	/**
	 * Sets the WFExecutionService from an ExecutionService.
	 * @param service
	 * @param userId
	 * @param userCredentials
	 * @throws WorkFlowException
	 */
	public void setExecutionService(ExecutionService service,
		String userId, UserCredentials userCredentials, Agent agent)
		throws WorkFlowException
	{

		if (service == null)
		{
			throw new WorkFlowException(WFExceptionMessages.EXECUTION_SERVICE_NULL);
		}
		this.executionService = service;
		this.userId = userId.toUpperCase();
		this.userCredentials = userCredentials;
		this.agent = agent;
		this.logonType = WFConstants.LOGON_TYPE_SINGLE_SIGNON;
	}
	
	/**
	 * @return userId
	 */
	public String getUserId()
	{
		return userId;
	}
	
	/**
	 * @return password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return agent
	 */
	public Agent getAgent() {

		return agent;
	}

	/**
	 * @return userCredentials
	 */
	public UserCredentials getUserCredentials() {

		return userCredentials;
	}

	/**
	 * @return logonType
	 */
	public int getLogonType()
	{
		return logonType;
	}
	
}
