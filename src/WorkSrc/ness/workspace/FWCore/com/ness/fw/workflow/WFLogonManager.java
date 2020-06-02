/*
 * Created on 18/04/2005
 *
 * Author Amit Mendelson
 * @version $Id: WFLogonManager.java,v 1.3 2005/05/05 16:28:25 amit Exp $
 */
package com.ness.fw.workflow;

import com.ibm.workflow.api.FmcException;
import com.ibm.workflow.api.ServicePackage.AbsenceIndicator;
import com.ibm.workflow.api.ServicePackage.SessionMode;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.pool.GenericPool;
import com.ness.fw.common.pool.PoolException;
import com.ness.fw.common.pool.PoolFactory;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.util.Message;

/**
 */
public class WFLogonManager
{

	private static String LOGGER_CONTEXT = "WFLogin";

	/**
	 * Generic pool for receiving the managerExecutionService.
	 */
	private static GenericPool managerPool = null;

	/**
	 * Assistance method to check if logged in and get quickly the manager's
	 * ExecutionService.
	 * The managerExecutionService should be taken from a pool.
	 * @return ExecutionService (only if it is not null)
	 * @throws WorkFlowException if the WFExecutionService is null or if the
	 * ExecutionService is null.
	 */
	protected static void initManagerExecutionServicePool()
		throws WorkFlowException
	{

		Logger.debug(LOGGER_CONTEXT, "started initManagerExecutionService");

		try
		{
			managerPool =
				PoolFactory.createGenericPool(new WFPoolableFactory());
		}
		catch (PoolException poex)
		{
			throw new WorkFlowException(poex);
		}
	}

	/**
	 * logon - this method is called when a user logs in to the
	 *  system, in order to create connection with IBM WorkFlow. 
	 * @param agent - the system's agent (already initialized with the 
	 * working method - local agent or remote agent).
	 * @param userID - ID of the user that will be logged on.
	 * @param password - password of this user.
	 * @throws WorkFlowException
	 * @throws AgentException
	 * @throws NullParametersException
	 */
	protected static WFExecutionService internalLogon(
		String userID,
		String password)
		throws
			FmcException,
			WorkFlowException,
			AgentException,
			NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started internalLogon");

		WFExecutionService executionServiceLocal = null;

		Agent agent = AgentFactory.getAgent();

		if (agent == null)
		{
			throw new AgentException(AgentConstants.AGENT_NOT_INITIALIZED);
		}

		if (userID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		if (password == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PASSWORD_IS_NULL,
				new Message(
					WFExceptionMessages.PASSWORD_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		/*
		 * Retrieve the session mode from the constants file.
		 * In case it is bad/null, ignore it, and use the default session mode.
		 */
		String sessionModeString =
			SystemResources.getInstance().getProperty(WFConstants.SESSION_MODE);

		/*
		 * Will contain the session mode: this parameter controls the number of
		 * sessions this user can do at once.
		 */
		SessionMode sessionMode = getSessionMode(sessionModeString);

		//set the local WFExecutionService
		executionServiceLocal = agent.getExecutionService(userID, password);
		if (executionServiceLocal == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.EXECUTION_SERVICE_NULL);
		}

		/*
		 * AbsenceIndicator - if it is RESET, the absence setting of the
		 * user is reset, he's back in work (there are another 
		 * AbsenceIndicator values, not relevant for this case).
		 */
		executionServiceLocal.getExecutionService().logon2(
			userID,
			password,
			sessionMode,
			AbsenceIndicator.RESET);

		Logger.debug(LOGGER_CONTEXT, "internalLogon, logon was performed");

		return executionServiceLocal;

	}

	/**
	 * Assistance method of logon(), is used to generate the SessionMode
	 * of workflow (required during logon()).
	 * 
	 * If the sessionMode is default - allow this user to perform
	 * parrallel log-ons in 2 different execution services.
	 * If the sessionMode is Present_Here, disallow this user to
	 * perform parrallel log-ons.
	 * The default option is (of course!) default.
	 * @param sessionModeString - Should contain the sessionMode.
	 * If it is null, take the default session mode.
	 * @return SessionMode - The workflow SessionMode.
	 */
	protected static SessionMode getSessionMode(String sessionModeString)
	{

		Logger.debug(LOGGER_CONTEXT, "started getSessionMode");

		// Will contain the return value.
		SessionMode sessionMode = null;

		if (sessionModeString == null)
		{
			sessionMode = SessionMode.DEFAULT;
		}
		else if (
			sessionModeString.equals(WFConstants.SESSION_MODE_PRESENT_HERE))
		{
			sessionMode = SessionMode.PRESENT_HERE;
		}
		else if (sessionModeString.equals(WFConstants.SESSION_MODE_PRESENT))
		{
			sessionMode = SessionMode.PRESENT;
		}
		else
			// Stable code - don't generate Exception if not forced to do so.
			{
			sessionMode = SessionMode.DEFAULT;
		}

		return sessionMode;

	}

	/**
	 * logon - this method is called when a user logs in to the system, 
	 * in order to create connection with IBM WorkFlow.
	 * @param Agent WF Agent.
	 * @param userCredentials Is required for single sign-on.
	 * @param userID - ID of the user that will be logged on.
	 * @throws WorkFlowException
	 * @throws AgentException
	 * @throws NullParametersException
	 */
	protected static WFExecutionService internalLogon(
		UserCredentials userCredentials,
		String userID)
		throws
			WorkFlowException,
			AgentException,
			NullParametersException,
			FmcException
	{

		Logger.debug(LOGGER_CONTEXT, "started internalLogon with UserCredentials");

		Agent agent = AgentFactory.getAgent();

		byte[] credentials = userID.getBytes();

		Logger.debug(LOGGER_CONTEXT, "internalLogon, retrieved credentials");

		WFExecutionService executionServiceLocal = null;

		if (agent == null)
		{
			throw new AgentException(AgentConstants.AGENT_NOT_INITIALIZED);
		}

		if (userID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(LOGGER_CONTEXT, 
			"internalLogon with credentials, userId: " + userID);

		if (userCredentials == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_CREDENTIALS_IS_NULL,
				new Message(
					WFExceptionMessages.USER_CREDENTIALS_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		/*
		 * Retrieve the session mode from the constants file.
		 * In case it is bad/null, ignore it, and use the default session mode.
		 */
		String sessionModeString =
			SystemResources.getInstance().getProperty(WFConstants.SESSION_MODE);

		/*
		 * Will contain the session mode: this parameter controls the number of
		 * sessions this user can do at once.
		 */
		SessionMode sessionMode = getSessionMode(sessionModeString);

		Logger.debug(LOGGER_CONTEXT, 
			"internalLogon with credentials, received sessionMode");

		//set the local WFExecutionService
		executionServiceLocal =
			agent.getExecutionService(userID, userCredentials);
		if (executionServiceLocal == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.EXECUTION_SERVICE_NULL);
		}

		Logger.debug(LOGGER_CONTEXT, 
			"internalLogon with credentials, took executionService");

		/*
		 * AbsenceIndicator - if it is RESET, the absence setting of the
		 * user is reset, he's back in work (there are another 
		 * AbsenceIndicator values, not relevant for this case).
		 */
		executionServiceLocal.getExecutionService().logon4(
			credentials,
			sessionMode,
			AbsenceIndicator.RESET,
			userID);
		Logger.debug(
			LOGGER_CONTEXT,
			"logon with UserCredentials (Single sign-on) performed");

		return executionServiceLocal;

	}

	/**
	 * Relogon.
	 * @param executionService
	 * @throws WorkFlowException
	 */
	protected static void relogon(WFExecutionService executionService)
		throws FmcException
	{

		Logger.debug(LOGGER_CONTEXT, "started relogon");

		String userId = executionService.getUserId();

		Logger.debug(LOGGER_CONTEXT, "started relogon");

		//retrieve the saved parameters
		int logonType = executionService.getLogonType();
		if (logonType == WFConstants.LOGON_TYPE_REGULAR)
		{
			String password = executionService.getPassword();

			Logger.debug(LOGGER_CONTEXT, "relogon, regular logon");

			executionService.getExecutionService().logon(userId, password);
		}
		else
		{
			//UserCredentials userCredentials =
			//	executionService.getUserCredentials();
			String sessionModeString =
				SystemResources.getInstance().getProperty(
					WFConstants.SESSION_MODE);

			Logger.debug(LOGGER_CONTEXT, "relogon, logon with UserCredentials");

			executionService.getExecutionService().logon4(
				userId.getBytes(),
				WFLogonManager.getSessionMode(sessionModeString),
				AbsenceIndicator.RESET,
				userId);
		}

	}

}
