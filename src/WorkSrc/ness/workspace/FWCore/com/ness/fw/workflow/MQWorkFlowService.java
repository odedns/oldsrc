/*
* Created on: 20/05/2004
* Author Amit Mendelson
* @version $Id: MQWorkFlowService.java,v 1.72 2005/05/09 10:45:31 amit Exp $
*/
package com.ness.fw.workflow;

import com.ibm.workflow.api.FmcException;
import com.ibm.workflow.api.ReadOnlyContainer;
import com.ibm.workflow.api.ReadWriteContainer;
import com.ibm.workflow.api.ExecutionService;
import com.ibm.workflow.api.ProcessInstance;
import com.ibm.workflow.api.Item;
import com.ibm.workflow.api.Person;
import com.ibm.workflow.api.WorkItem;
import com.ibm.workflow.api.WorkList;
import com.ibm.workflow.api.ActivityInstanceNotification;
import com.ibm.workflow.api.ContainerElement;
import com.ibm.workflow.api.Container;
import com.ibm.workflow.api.ProcessTemplate;
import com.ibm.workflow.api.ItemPackage.ItemType;

import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.pool.GenericPool;
import com.ness.fw.common.pool.PoolException;
import com.ness.fw.common.pool.PoolFactory;
import com.ness.fw.util.IDPagingService;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.util.DateFormatterUtil;
import com.ness.fw.util.Message;
import com.ness.fw.util.StringFormatterUtil;
import com.ness.fw.util.LdapException;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Date;
import java.util.Collection;

/**
* This class is the major class of package com.ness.fw.workflow.
* It performs all operations involved with accessing to IBM MQ WorkFlow,
* retrieval of information about processes and tasks, managing tasks, etc.
* This class holds an instance of MQ WorkFlow WFExecutionService, to keep 
* connection with the MQWF. It does not hold any other variables, and does
* not keep in it information about items retrieved/operations done.
* If this is required, it is recommended for the application level to manage
* the received information, and perform new calls to MQWorkFlowService,
* to keep the information updated.
*/
public class MQWorkFlowService implements WorkFlowService
{

	/**
	 * Generic pool for receiving the managerExecutionService.
	 */
	private static GenericPool managerPool = null;

	/**
	 * Logger context for writing to the Log.
	 */
	private static final String LOGGER_CONTEXT = "MQWorkflow Service";

	/**
	 * This variable holds the WFExecutionService used by this service to 
	 * contact WF. It is set during logon().
	 */
	private WFExecutionService executionService = null;

	/**
	 * Local instance of WFLdapManager.
	 */
	private WFLdapManager ldapManager = null;

	/**
	 * protected constructor.
	 * Also initializes WFLdapManager.
	 */
	protected MQWorkFlowService() throws WorkFlowException
	{
		try
		{
			ldapManager = new WFLdapManager();
		}
		catch (LdapException e)
		{
			throw new WorkFlowException(
				WFExceptionMessages.LDAP_INITIALIZATION_PROBLEM,
				e);
		}
	}

	/**
	 * Initialize the WorkFlowServiceFactory.
	 */
	protected static void initialize() throws WorkFlowException
	{
		initManagerExecutionServicePool();
	}

	/**
	 * Assistance method to check if logged in and get quickly the ExecutionService.
	 * @return ExecutionService (only if it is not null)
	 * @throws WorkFlowException if the WFExecutionService is null or if the
	 * ExecutionService is null.
	 */
	private ExecutionService getExecutionService() throws WorkFlowException
	{

		if (executionService == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.NOT_LOGGED_IN_ERROR);
		}

		ExecutionService wfExecutionService =
			executionService.getExecutionService();
		if (wfExecutionService == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.EXECUTION_SERVICE_NULL);
		}
		return wfExecutionService;
	}

	/**
	 * Assistance method to check if logged in and get quickly the manager's
	 * ExecutionService.
	 * The managerExecutionService should be taken from a pool.
	 * @return WFExecutionService (only if it is not null)
	 * @throws WorkFlowException if the WFExecutionService is null or if the
	 * ExecutionService is null.
	 */
	protected static WFExecutionService getManagerExecutionService()
		throws WorkFlowException
	{
		try
		{
			WFExecutionService wfExecutionService =
				(WFExecutionService) managerPool.borrowObject();
			if (wfExecutionService == null)
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_SERVICE_NULL);
			}
			return wfExecutionService;

		}
		catch (PoolException poex)
		{
			throw new WorkFlowException(poex);
		}
	}

	/**
	 * Return the loaded managerExecutionService to the pool.
	 * @param managerExecutionService
	 * @throws WorkFlowException
	 */
	protected void putManagerExecutionService(WFExecutionService managerExecutionService)
		throws WorkFlowException
	{
		Logger.debug(LOGGER_CONTEXT, "started putManagerExecutionService");
		try
		{
			managerPool.returnObject(managerExecutionService);
		}
		catch (PoolException poex)
		{
			throw new WorkFlowException(poex);
		}
	}

	/**
	 * Will be used on system shutdown, to clear the login information
	 * for the manager user.
	 * @throws WorkFlowException
	 */
	public static void clear() throws WorkFlowException
	{

		Logger.debug(LOGGER_CONTEXT, "started clearManagerExecutionService");

		try
		{
			managerPool.close();
			Logger.debug(LOGGER_CONTEXT, "ended clearManagerExecutionService");

		}
		catch (PoolException e)
		{
			throw new WorkFlowException(e);
		}

	}

	/**
	 * Assistance method
	 * Default usage of handleFmcException (not using manager, allowing relogon)
	 * @param fex
	 * @throws WorkFlowLogicException
	 * @throws WorkFlowException
	 */
	private void handleFmcException(FmcException fex)
		throws WorkFlowLogicException, WorkFlowException
	{
		handleFmcException(fex, false, true);
	}

	/**
	 * Assistance method
	 * Usage of handleFmcException when using manager, allowing relogon
	 * @param fex
	 * @param useManager
	 * @throws WorkFlowLogicException
	 * @throws WorkFlowException
	 */
	private void handleFmcException(FmcException fex, boolean useManager)
		throws WorkFlowLogicException, WorkFlowException
	{
		handleFmcException(fex, useManager, true);
	}

	/**
	 * Assistance method that centralizes the exception handling
	 * in the MQWorkFlowService.
	 * In case the thrown exception is a time-out one, this method performs
	 * re-login.
	 * @param fex The thrown FmcException.
	 * @return boolean: If true, recall the service
	 * @throws WorkFlowLogicException In case the problem is a logic exception.
	 * @throws WorkFlowException In case the problem is a failure of MQ WorkFlow.
	 */
	private void handleFmcException(
		FmcException fex,
		boolean useManager,
		boolean allowRelogon)
		throws WorkFlowLogicException, WorkFlowException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"Handling FmcException, error code: " + fex.rc);

		/*
		 * The managerExecutionService is validated before using it.
		 * In case there is a time-out error with it, it is a serious
		 * error, and relogon should not be performed.
		 */
		if (useManager)
		{
			throw new WorkFlowException(
				"error handling manager execution service",
				fex);
		}

		//perform relogin.
		if (allowRelogon && fex.rc == FmcException.FMC_ERROR_NOT_LOGGED_ON)
		{

			Logger.debug(
				LOGGER_CONTEXT,
				"Handling FmcException, not logged on error");
			try
			{
				WFLogonManager.relogon(executionService);
				//avoid recursive logon
			}
			catch (FmcException fmex)
			{
				throw new WorkFlowException(fmex);
			}
		}

		else if (fex.rc == FmcException.FMC_ERROR_NOT_AUTHORIZED)
		{

			Logger.debug(
				LOGGER_CONTEXT,
				"Handling FmcException, not authorized error");
			String currentUser = executionService.getUserId();
			Logger.debug(
				LOGGER_CONTEXT,
				"Handling FmcException, not authorized user: " + currentUser);

			throw new WorkFlowLogicException(
				WFExceptionMessages.USER_IS_NOT_AUTHORIZED_FOR_THIS_OPERATION,
			//+ currentUser,
			new Message(
				WFExceptionMessages.USER_IS_NOT_AUTHORIZED_FOR_THIS_OPERATION,
			//+ currentUser,
			Message.SEVERITY_ERROR));
		}
		else if (fex.rc == FmcException.FMC_ERROR_USERID_UNKNOWN)
		{

			Logger.debug(
				LOGGER_CONTEXT,
				"Handling FmcException, unknown user error");
			throw new WorkFlowLogicException(
				WFExceptionMessages.USER_UNKNOWN,
				new Message(
					WFExceptionMessages.USER_UNKNOWN,
					Message.SEVERITY_ERROR));
		}
		else
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"Error could not be handled by Handling FmcException");
			throw new WorkFlowException(fex);
		}
	}

	/**
	 * logon - this method is called when a user logs in to the system, 
	 * in order to create connection with IBM WorkFlow. (This format is the
	 * single sign-on format, in order to prevent multiple log-ons).
	 * @param userCredentials Is required for single sign-on.
	 * @param userID - ID of the user that will be logged on.
	 * @throws WorkFlowException
	 * @throws AgentException
	 * @throws NullParametersException
	 */
	public void logon(UserCredentials userCredentials, String userID)
		throws WorkFlowException, AgentException, NullParametersException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"logon with userCredentials (single sign-on) started");
		Logger.debug(LOGGER_CONTEXT, "logon, received userId: " + userID);

		try
		{
			executionService =
				WFLogonManager.internalLogon(userCredentials, userID);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}
	}

	/**
	 * logon - this method is called when a user logs in to the system, 
	 * in order to create connection with IBM WorkFlow. 
	 * @param userID - ID of the user that will be logged on.
	 * @param password - password of this user.
	 * @throws WorkFlowException
	 * @throws AgentException
	 * @throws NullParametersException
	 */
	public void logon(String userID, String password)
		throws WorkFlowException, AgentException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "logon started");
		Logger.debug(LOGGER_CONTEXT, "logon, received userId: " + userID);

		try
		{
			executionService = WFLogonManager.internalLogon(userID, password);
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}
		Logger.debug(LOGGER_CONTEXT, "logon performed");

	}

	/**
	 * logoff
	 * in successful logoff, the WFExecutionService object should be
	 * nullified, so no other access to the old WFExecutionService
	 * will be possible.
	 * @throws WorkFlowException in case a FmcException was thrown.
	 */
	public void logoff() throws WorkFlowException
	{

		Logger.debug(LOGGER_CONTEXT, "logoff started");

		try
		{
			if (executionService != null)
			{
				if (executionService.getExecutionService().isLoggedOn())
				{
					executionService.getExecutionService().logoff();
				}

			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex, false, false);
		}

		executionService = null;

		/*
		 * No operation required in case the executionService is null:
		 * already logged off.
		 */
		Logger.debug(LOGGER_CONTEXT, "logoff completed");

	}

	/**
	 * This method is called to handle all operations required when exiting (on
	 * system shutdown).
	 * Currently just logs off. Another operations may be added in the future,
	 * on requirement.
	 * E.G. destroy the Agent - cannot be done in the MQWorkFlowService level.
	 * @throws WorkFlowException
	 */
	public void exit() throws WorkFlowException
	{
		Logger.debug(LOGGER_CONTEXT, "exit started");
		logoff();
		Logger.debug(LOGGER_CONTEXT, "WorkFlow exit performed");

	}

	/**
	 * This method should access WorkFlow ProcessInstance's description field, as
	 * the MQ workFlowService does not hold the WorkItems in it.
	 * The priority should be kept in a fixed place of the description
	 * field, the description should be separated by tokens like:
	 * priority=8;name=hello;...
	 * @param processInstanceID
	 * @param newPriority priority to set
	 * @throws WorkFlowException if the new priority is illegal
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @see com.ness.fw.workflow.WorkFlowService#setPriorityOfProcessInstance(String, int)
	 */
	public void setPriorityOfProcessInstance(
		String[] processInstanceIds,
		int newPriority)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "setPriorityOfProcessInstance started");
		Logger.debug(
			LOGGER_CONTEXT,
			"new priority passed for the processes: " + newPriority);

		if ((processInstanceIds == null) || (processInstanceIds.length == 0))
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_INSTANCE_IDS_ARE_NULL,
				new Message(
					WFExceptionMessages.PROCESS_INSTANCE_IDS_ARE_NULL,
					Message.SEVERITY_ERROR));
		}
		/*
		 * Retrieve the min/max process priority values from the 
		 * workflow.properties file.
		 */
		SystemResources systemResources = SystemResources.getInstance();
		int minPriorityValue =
			Integer.parseInt(
				systemResources.getProperty(
					WFConstants.PROCESS_MIN_PRIORITY_VALUE));

		int maxPriorityValue =
			Integer.parseInt(
				systemResources.getProperty(
					WFConstants.PROCESS_MAX_PRIORITY_VALUE));

		/*
		 * Have to throw exception, otherwise a FmcException will be thrown
		 * by the workflow.
		 */
		if ((newPriority < minPriorityValue)
			|| (newPriority > maxPriorityValue))
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.INVALID_PRIORITY_VALUE,
				new Message(
					WFExceptionMessages.INVALID_PRIORITY_VALUE,
					Message.SEVERITY_ERROR));
		}

		String priorityKey =
			systemResources.getProperty(WFConstants.DESCRIPTION_PRIORITY_KEY);

		for (int i = 0; i < processInstanceIds.length; i++)
		{
			setPriorityOfProcessInstance(
				processInstanceIds[i],
				newPriority,
				priorityKey);
		}
	}

	/**
	 * This method should access WorkFlow ProcessInstance's description field, as
	 * the MQ workFlowService does not hold the WorkItems in it.
	 * The priority should be kept in a fixed place of the description
	 * field, the description should be separated by tokens like:
	 * priority=8;name=hello;...
	 * @param processInstanceID
	 * @param newPriority Priority to set
	 * @param priorityKey The priority key for setting in the description.
	 * @throws WorkFlowException if the new priority is illegal
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @see com.ness.fw.workflow.WorkFlowService#setPriorityOfProcessInstance(String, int)
	 */
	private void setPriorityOfProcessInstance(
		String processInstanceID,
		int newPriority,
		String priorityKey)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"called setPriorityOfProcessInstance, ProcessInstance Id is: "
				+ processInstanceID);

		if (processInstanceID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		try
		{

			ProcessInstance instance =
				getExecutionService().persistentProcessInstance(
					processInstanceID);
			/*
			 * According to Leonid, 12.4.05. the returned processInstance
			 * cannot be null if it has a legal processInstance Id.
			 */

			if (!instance.isComplete())
			{
				instance.refresh();
			}
			String description = instance.description();

			description =
				setElementInDescriptionString(
					description,
					priorityKey,
					String.valueOf(newPriority));
			Logger.debug(
				LOGGER_CONTEXT,
				"updated description of ProcessInstance is: " + description);

			instance.setDescription(description);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//reachable only in case of time-out
			setPriorityOfProcessInstance(
				processInstanceID,
				newPriority,
				priorityKey);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"priority "
				+ newPriority
				+ "was set for processInstance "
				+ processInstanceID);

	}

	/**
	 * This method should access WorkFlow WorkItem's description field, as
	 * the MQ workFlowService does not hold the WorkItems in it.
	 * The priority should be kept in a fixed place of the description
	 * field, the description should be separated by tokens like:
	 * priority=8;name=hello;...
	 * @param workItemIds
	 * @param newPriority priority to set
	 * @throws WorkFlowException if the new priority is illegal
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @see com.ness.fw.workflow.WorkFlowService#setPriorityOfWorkItem(String, int)
	 */
	public void setPriorityOfWorkItem(String[] workItemIds, int newPriority)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "setPriorityOfWorkItem started");
		Logger.debug(
			LOGGER_CONTEXT,
			"new priority passed for the workItems: " + newPriority);

		if (workItemIds == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_IDS_ARE_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_IDS_ARE_NULL,
					Message.SEVERITY_ERROR));
		}
		/*
		 * Retrieve the min/max process priority values from the 
		 * workflow.properties file.
		 */
		SystemResources systemResources = SystemResources.getInstance();
		int minPriorityValue =
			Integer.parseInt(
				systemResources.getProperty(
					WFConstants.PROCESS_MIN_PRIORITY_VALUE));

		int maxPriorityValue =
			Integer.parseInt(
				systemResources.getProperty(
					WFConstants.PROCESS_MAX_PRIORITY_VALUE));

		/*
		 * Have to throw exception, otherwise a FmcException will be thrown
		 * by the workflow.
		 */
		if ((newPriority < minPriorityValue)
			|| (newPriority > maxPriorityValue))
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.INVALID_PRIORITY_VALUE,
				new Message(
					WFExceptionMessages.INVALID_PRIORITY_VALUE,
					Message.SEVERITY_ERROR));
		}

		String priorityKey =
			systemResources.getProperty(
				WFConstants.DESCRIPTION_WORK_ITEM_PRIORITY_KEY);

		for (int i = 0; i < workItemIds.length; i++)
		{
			setPriorityOfWorkItem(workItemIds[i], newPriority, priorityKey);
		}
	}

	/**
	 * This method should access WorkFlow WorkItem's description field, as
	 * the MQ workFlowService does not hold the WorkItems in it.
	 * The priority should be kept in a fixed place of the description
	 * field, the description should be separated by tokens like:
	 * priority=8;name=hello;...
	 * @param workItemID
	 * @param newPriority priority to set
	 * @param priorityKey The priority key for setting in the description.
	 * @throws WorkFlowException if the new priority is illegal
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @see com.ness.fw.workflow.WorkFlowService#setPriorityOfWorkItem(String, int)
	 */
	private void setPriorityOfWorkItem(
		String workItemID,
		int newPriority,
		String priorityKey)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"called setPriorityOfWorkItem, WorkItem Id is: " + workItemID);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		WFExecutionService managerExecutionService =
			getManagerExecutionService();
		WorkItem workItem = null;
		String owner = null;

		try
		{

			workItem =
				managerExecutionService
					.getExecutionService()
					.persistentWorkItem(
					workItemID);
			workItem.refresh();
			Logger.debug(
				LOGGER_CONTEXT,
				"setPriorityOfWorkItem, used manager to retrieve the workItem");
		}
		catch (FmcException fex)
		{
			handleFmcException(fex, true);
			putManagerExecutionService(managerExecutionService);
			managerExecutionService = null;
			//will be reachable only in case of time-out
			setPriorityOfWorkItem(workItemID, newPriority, priorityKey);
		}
		finally
		{
			if (managerExecutionService != null)
			{
				putManagerExecutionService(managerExecutionService);
				managerExecutionService = null;
			}
		}

		try
		{

			String description = workItem.description();
			Logger.debug(
				LOGGER_CONTEXT,
				"description of WorkItem before update is: " + description);

			description =
				setElementInDescriptionString(
					description,
					priorityKey,
					String.valueOf(newPriority));

			Logger.debug(
				LOGGER_CONTEXT,
				"updated description of WorkItem is: " + description);
			owner = workItem.owner();
			updateDescription(workItem, workItemID, owner, description);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//reachable only in case of time-out
			setPriorityOfWorkItem(workItemID, newPriority, priorityKey);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"priority " + newPriority + "was set for workItem " + workItemID);

	}

	/**
	 * This method is used to retrieve the Item's priority from the 
	 * Item's description field.
	 * @see getPriority(ProcessInstance instance)
	 * @param item the selected Item.
	 * @return int - the Item's priority.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private int getPriority(Item item)
		throws WorkFlowException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "started method getPriority");

		if (item == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.ITEM_IS_NULL,
				new Message(
					WFExceptionMessages.ITEM_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		String priorityKey =
			SystemResources.getInstance().getProperty(
				WFConstants.DESCRIPTION_WORK_ITEM_PRIORITY_KEY);

		String priorityStr = null;
		/*
		 * extract the priority of the WorkItem from the
		 * description. Checking that the description is not null is
		 * done in the method getElementFromDescription().
		 */
		try
		{

			priorityStr =
				this.getElementFromDescription(item.description(), priorityKey);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getPriority(item);
		}

		/*
		 * In case there is no priority parameter in the description,
		 * set the priority to default value from the properties file - 
		 * Indicates to the application that the priority is not set, 
		 * and should be set in the application level.
		 */
		if (priorityStr == null)
		{
			priorityStr =
				SystemResources.getInstance().getProperty(
					WFConstants.DEFAULT_WORK_ITEM_PRIORITY_VALUE);
		}
		/*
		 * In case the token is not a number, a NumberFormatException
		 * will be thrown.
		 */
		return Integer.parseInt(priorityStr);

	}

	/**
	 * This method is used to retrieve the ProcessInstance's priority from 
	 * the Description field. If the description is null, return -1 (the
	 * application should set the priority to useful value).
	 * @param instanceDescription Description of the processInstance.
	 * @return int - the WFProcessInstance's priority.
	 * If no information can be extracted about the priority, return -1.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private int getPriorityOfProcessInstance(String instanceDescription)
		throws WorkFlowLogicException
	{

		String priorityKey =
			SystemResources.getInstance().getProperty(
				WFConstants.DESCRIPTION_PRIORITY_KEY);

		/*
		 * extract the priority of the WFProcessInstance from the
		 * description. Checking that the description is not null is
		 * done in the method getElementFromDescription().
		 */
		String priorityStr =
			this.getElementFromDescription(instanceDescription, priorityKey);

		/*
		 * In case there is no priority parameter in the description,
		 * set the priority to -1 - Indicates to the application that
		 * the priority is not set, and should be set in the
		 * application level.
		 */
		if (priorityStr == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.NO_PRIORITY_SET_IN_DESCRIPTION,
				new Message(
					WFExceptionMessages.NO_PRIORITY_SET_IN_DESCRIPTION,
					Message.SEVERITY_ERROR));
		}
		/*
		 * In case the token is not a number, a NumberFormatException
		 * will be thrown.
		 */
		return Integer.parseInt(priorityStr);
	}

	/**
	 * Terminates the ProcessInstance identified by processID.
	 * Handles all relevant things.
	 * In case the ProcessInstance has checked-out WorkItems, it cannot
	 * be terminated, and a WorkFlowLogicException will be thrown.
	 * In case of failure throws WorkFlowException.
	 * Note: if the ProcessInstance's state is READY, a WorkFlowException is thrown
	 * (wraps FmcException generated since this state doesn't allow terminating
	 * of the ProcessInstance).
	 * @param processInstanceIds Ids of the ProcessInstances to be terminated.
	 * @throws WorkFlowException wraps FmcException that might be thrown
	 * inside.
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	public void terminateProcess(String[] processInstanceIds)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "terminateProcess started");

		if (!((processInstanceIds == null)
			|| (processInstanceIds.length == 0)))
		{
			for (int i = 0; i < processInstanceIds.length; i++)
			{
				terminateProcess(processInstanceIds[i]);
				Logger.debug(
					LOGGER_CONTEXT,
					"process " + processInstanceIds[i] + " was terminated");
			}
		}

	}

	/**
	 * Terminates the ProcessInstance identified by processID.
	 * Handles all relevant things.
	 * In case the ProcessInstance has checked-out WorkItems, it cannot
	 * be terminated, and a WorkFlowLogicException will be thrown.
	 * In case of failure throws WorkFlowException.
	 * Note: if the ProcessInstance's state is READY, a WorkFlowException is thrown
	 * (wraps FmcException generated since this state doesn't allow terminating
	 * of the ProcessInstance).
	 * @param processInstanceID ID of the ProcessInstance to be terminated.
	 * @throws WorkFlowException wraps FmcException that might be thrown
	 * inside.
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private void terminateProcess(String processInstanceID)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		// This method checks also if the processInstanceID is not null.
		if (
			doesProcessInstanceHaveItemsCheckedOutByOtherUsers(processInstanceID))
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.PROCESS_INSTANCE_CANNOT_BE_TERMINATED,
				new Message(
					WFExceptionMessages.PROCESS_INSTANCE_CANNOT_BE_TERMINATED,
					Message.SEVERITY_WARNING));
		}

		int executionStateValue = -1;
		ProcessInstance processInstance = null;

		try
		{

			processInstance =
				getExecutionService().persistentProcessInstance(
					processInstanceID);

			/*
			 * According to Leonid, 12/4/05, processInstance cannot be
			 * empty when an Id was supplied.
			 */
			if (!processInstance.isComplete())
			{
				processInstance.refresh();
			}

			com
				.ibm
				.workflow
				.api
				.ProcessInstancePackage
				.ExecutionState executionState =
				processInstance.state();

			/*
			 * If the Process' State is Terminated - nothing to do.
			 * If the state is deleted - the process was already terminated before 
			 * it was deleted, again nothing to do.
			 * Note: currently, if the state is "terminating", the program will 
			 * still try to terminate it. is this redundant?
			 */
			executionStateValue = executionState.value();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//reachable only in case of time-out
			terminateProcess(processInstanceID);
		}
		if ((executionStateValue
			== com
				.ibm
				.workflow
				.api
				.ProcessInstancePackage
				.ExecutionState
				._TERMINATED)
			|| (executionStateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._DELETED)
			|| (executionStateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._FINISHED))
		{
			//do nothing - there is no process to be terminated.
			//Note: this is done since there are also states that do not allow termination:
			//READY, UNDEFINED.
		}
		/*
		 * The ProcessInstance can be terminated only if it's state is one of
		 * Running, Suspending or Suspended.
		 */
		else if (
			(executionStateValue
				!= com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._RUNNING)
				&& (executionStateValue
					!= com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._SUSPENDING)
				&& (executionStateValue
					!= com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._SUSPENDED))
		{
			Message message =
				new Message(
					WFExceptionMessages.PROCESS_CANNOT_BE_TERMINATED,
					Message.SEVERITY_WARNING);
			message.addParameter(processInstanceID);
			throw new WorkFlowLogicException(
				WFExceptionMessages.PROCESS_CANNOT_BE_TERMINATED,
				message);
		}

		else
		{
			try
			{
				// Terminate the ProcessInstance.
				processInstance.terminate();
				processInstance.delete();
			}
			catch (FmcException fex)
			{
				handleFmcException(fex);
				//reachable only in case of time-out
				terminateProcess(processInstanceID);
			}
		}

	}

	/**
	 * Assistance method of terminateProcess(). Can also be accessed directly.
	 * @param processInstanceID
	 * @return boolean true if there are any checked-out WorkItems, false otherwise.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	public boolean doesProcessInstanceHaveItemsCheckedOutByOtherUsers(String processInstanceID)
		throws NullParametersException, WorkFlowException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"doesProcessInstanceHaveItemsCheckedOutByOtherUsers started");
		Logger.debug(
			LOGGER_CONTEXT,
			"checked processInstance Id: " + processInstanceID);

		if (processInstanceID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		ExecutionService wfService = getExecutionService();

		try
		{

			ProcessInstance processInstance =
				wfService.persistentProcessInstance(processInstanceID);

			/*
			 * According to Leonid, 12/4/05, processInstance cannot be
			 * empty when an Id was supplied.
			 */
			if (!processInstance.isComplete())
			{
				processInstance.refresh();
			}

			/*
			 * In case the ProcessInstance has checked-out WorkItems, it cannot be
			 * terminated. Therefore, a test is performed to see if there are
			 * any checked-out WorkItems in the ProcessInstance.
			 */
			StringBuffer tempFilter = new StringBuffer(128);

			tempFilter.append(WFConstants.FILTER_PROCESS_NAME_IN);
			tempFilter.append(processInstance.name());
			tempFilter.append(WFConstants.GERESH);
			tempFilter.append(" ");
			tempFilter.append(WFConstants.FILTER_CHECK_OUT_STATE_PREFIX);

			//Take the number of WorkItems to be retrieved from the properties
			Integer threshold =
				new Integer(
					SystemResources.getInstance().getProperty(
						WFConstants.WORK_ITEMS_THRESHOLD_KEY));

			Logger.debug(
				LOGGER_CONTEXT,
				"doesProcessInstanceHaveItemsCheckedOutByOtherUsers, generated filter is: "
					+ tempFilter.toString()
					+ ", Id of process is: "
					+ processInstanceID);

			WorkItem[] checkedOutWorkItems =
				wfService.queryWorkItems(
					tempFilter.toString(),
					WFConstants.NAME_ASC,
					threshold);

			if ((checkedOutWorkItems == null)
				|| (checkedOutWorkItems.length == 0))
			{
				return false;
			}

			if (checkedOutWorkItems.length == 1)
			{
				/*
				 * If only this user checks out a WorkItem, return false (there
				 * are no other users who check out WorkItems).
				 */
				if ((checkedOutWorkItems[0].owner())
					.equals(executionService.getUserId()))
				{
					return false;
				}
			}
			return true;
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return doesProcessInstanceHaveItemsCheckedOutByOtherUsers(processInstanceID);
		}
	}

	/**
	 * Sets the given user as absent.
	 * Note: In order for this to work, (when a user logs on again, 
	 * mark him as not absent), the checkbox:
	 * "Automatically reset absent indicator when person starts
	 * working again" should be marked in the person properties
	 * (Workflow buildTime).
	 * @param userID - ID of the absent user.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	public void setUserAsAbsent(String userID)
		throws WorkFlowException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"setUserAsAbsent started, received userID: " + userID);

		if (userID == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		try
		{

			/*
			 * The second parameter of setPersonAbsent2 (true), means
			 * that the user should be set as absent.
			 */
			getExecutionService().setPersonAbsent2(userID, true);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}
		Logger.debug(LOGGER_CONTEXT, "setUserAsAbsent completed");

	}

	/**
	 * Returns true if the user is absent.
	 * Returns false either if the user is not absent or if the user
	 * does not exist in WorkFlow (in the method isAbsent(). and this
	 * method also returns false if the userID is null).
	 * @param userID - ID of the user.
	 * @throws WorkFlowException if FmcException is thrown.
	 * @throws NullParametersException
	 */
	public boolean isAbsent(String userID)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"isAbsent started, received userID: " + userID);

		if (userID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		try
		{

			Person person = getExecutionService().persistentPerson(userID);

			/*Note: the returned person won't be null! if there is no such 
			 * person, the method will return false - so that method can't 
			 * be used as a proof that such person exists at all.
			 */
			/*
			 * According to Leonid, 12/4/05, processInstance cannot be
			 * empty when an Id was supplied.
			 */
			if (!person.isComplete())
			{
				person.refresh();
			}
			return person.isAbsent();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return isAbsent(userID);
		}
	}

	/**
	 * This method sets a substitute for the given user.
	 * @see com.ness.fw.workflow.WorkFlowService#substitute(String)
	 * @param userID - ID of the person to set substitute for.
	 * @param substitute - ID of the substitute for the person.
	 * @throws WorkFlowException - In case the substitute is an illegal user.
	 * @throws NullParametersException
	 */
	public void setUserSubstitute(String userID, String substitute)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"setUserSubstitute started, received userID: "
				+ userID
				+ ", received substitute: "
				+ substitute);

		if (userID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		if (substitute == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.SUBSTITUTE_IS_NULL,
				new Message(
					WFExceptionMessages.SUBSTITUTE_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		try
		{

			Person person = getExecutionService().persistentPerson(userID);

			/*
			 * According to Leonid, 12/4/05, the received person cannot be null.
			 */
			if (!person.isComplete())
			{
				person.refresh();
			}
			person.setSubstitute(substitute);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}
	}

	/**
	 * This method sets a substitute for the logged-on user.
	 * @see com.ness.fw.workflow.WorkFlowService#substitute(String)
	 * @param substitute - ID of the substitute for the person.
	 * @throws WorkFlowException - In case the substitute is an illegal user.
	 * @throws NullParametersException
	 */
	public void setCurrentUserSubstitute(String substitute)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"setCurrentUserSubstitute started, received substitute: "
				+ substitute);
		setUserSubstitute(executionService.getUserId(), substitute);
		Logger.debug(LOGGER_CONTEXT, "setCurrentUserSubstitute completed");
	}

	/**
	 * Returns the current substitute of the selected user.
	 * If this user has no substitute, null value is returned.
	 * Anyone who uses this method should check its return value for null value.
	 * @param userID - ID of the user.
	 * @return String - name of the substitute of this person (can be null).
	 * If the userID is null, null will be returned.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public String getUserSubstitute(String userID)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"getUserSubstitute started, received userID: " + userID);

		if (userID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		try
		{

			/*
			 * Note: A persistent person will be returned even if there is
			 *  no such person in the system (in this case, its fields will
			 * be null!)
			 */
			Person person = getExecutionService().persistentPerson(userID);

			/*
			 * According to Leonid, 12/4/05, the received person cannot be null.
			 */
			if (!person.isComplete())
			{
				person.refresh();
			}

			//might be null
			return person.substitute();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getUserSubstitute(userID);
		}
	}

	/**
	 * Returns the current substitute of the current user.
	 * If this user has no substitute, null value is returned.
	 * Anyone who uses this method should check its return value for null value.
	 * @return String - name of the substitute of this person (can be null).
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public String getCurrentUserSubstitute()
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "getCurrentUserSubstitute started");
		return getUserSubstitute(executionService.getUserId());
	}

	/**
	 * Retrieves the customerID from the ParametersMap parameter 
	 * DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETER.
	 * Will perform query on processes, pass on them and return true
	 * if any process found with this customerID.
	 * @param parametersMap - WorkFlowServiceParameterMap
	 * @return true if there are any open processes for this customer.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public boolean doesCustomerHaveOpenProcesses(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "doesCustomerHaveOpenProcesses started");

		ProcessInstance[] instances =
			internalGetCustomerOpenProcesses(parametersMap);

		if ((instances == null) || (instances.length == 0))
		{
			return false;
		}
		return true;
	}

	/**
	 * Retrieves the customerID from the ParametersMap parameter 
	 * DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETER.
	 * Will perform query on processes, pass on them and return the
	 * list of processes found with this customerID.
	 * @param parametersMap - WorkFlowServiceParameterMap
	 * @return String[] all the process Ids for processes open for
	 * this customer.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public String[] getListOfCustomerOpenProcesses(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "getListOfCustomerOpenProcesses started");

		ProcessInstance[] instances =
			internalGetCustomerOpenProcesses(parametersMap);

		if (instances == null)
		{
			return null;
		}

		String[] returnedList = new String[instances.length];

		for (int i = 0; i < instances.length; i++)
		{
			try
			{

				returnedList[i] = instances[i].persistentOid();
			}
			catch (FmcException fex)
			{
				handleFmcException(fex);
				//will be reachable only in case of time-out
				return getListOfCustomerOpenProcesses(parametersMap);

			}

		}
		return returnedList;

	}

	/**
	 * Retrieves the customerID from the ParametersMap parameter 
	 * DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETER.
	 * Will perform query on processes, pass on them and return the
	 * IDPagingService containing a list of process Ids found with this customerID.
	 * @param parametersMap - WorkFlowServiceParameterMap
	 * @param numOfRowsInPage Required for generation of the IDPagingService
	 * @returnIDPagingService Contains all the process Ids for processes open for
	 * this customer.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public IDPagingService getCustomerOpenProcessesIds(
		WorkFlowServiceParameterMap parametersMap,
		int numOfRowsInPage)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "getCustomerOpenProcessIds started");

		IDPagingService pagingService = null;
		ArrayList processes = new ArrayList();

		ProcessInstance[] instances =
			internalGetCustomerOpenProcesses(parametersMap);

		if (instances != null)
		{
			for (int i = 0; i < instances.length; i++)
			{
				try
				{
					processes.add(i, instances[i].persistentOid());
				}
				catch (FmcException fex)
				{
					handleFmcException(fex);
					//will be reachable only in case of time-out
					return getCustomerOpenProcessesIds(
						parametersMap,
						numOfRowsInPage);

				}
			}
		}

		pagingService = new IDPagingService(processes, numOfRowsInPage);

		return pagingService;

	}

	/**
	 * Retrieves all open processes for this customer (the customer is set in
	 * the description field).
	 * @param WorkFlowServiceParameterMap parametersMap
	 * @return WFProcessInstance[] - all the ProcessInstances of this customer.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstanceList getCustomerOpenProcesses(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "getCustomerOpenProcesses started");

		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"getCustomerOpenProcesses, received parametersMap: "
				+ parametersMap.getAllParameters());
		Integer workItemsThreshold = getWorkItemsThreshold(parametersMap);

		//Names of parameters to be retrieved from the container
		String[] inContainerElementNames =
			getInContainerElementNames(parametersMap);
		String[] outContainerElementNames =
			getOutContainerElementNames(parametersMap);
		String[] globalContainerElementNames =
			getGlobalContainerElementNames(parametersMap);

		//default option
		boolean retrieveNotifications = getRetrieveNotifications(parametersMap);
		boolean getExpirationTime = getExpirationTime(parametersMap);
		boolean useManager = getUseManager(parametersMap);

		ProcessInstance[] instances =
			internalGetCustomerOpenProcesses(parametersMap);
		int size = instances.length;
		Logger.debug(
			LOGGER_CONTEXT,
			"getCustomerOpenProcesses, number of retrieved processes: " + size);

		WFProcessInstance[] resultInstances = new WFProcessInstance[size];

		// Build the result WFProcessInstance[] array.
		for (int i = 0; i < size; i++)
		{
			resultInstances[i] =
				createWFProcessInstance(
					instances[i],
					workItemsThreshold,
					WFConstants.SORT_LAST_MODIFICATION_TIME_DESC,
					inContainerElementNames,
					outContainerElementNames,
					globalContainerElementNames,
					getExecutionService(),
					retrieveNotifications,
					getExpirationTime,
					useManager);
		}
		return new WFProcessInstanceList(resultInstances);
	}

	/**
	 * Assistance method of getCustomerOpenProcesses() and 
	 * doesCustomerHaveOpenProcesses().
	 * This method performs query on ProcessInstances, whose description
	 * field contains the appropriate customerID.
	 * @param customerID - Id of the customer to search.
	 * @param customerIDParameter - Name of the field to take from the Global container.
	 * @param sortCriteria - sort criteria (optional)
	 * @param thresholdStr - how many ProcessInstances to retrieve (optional)
	 * @return WFProcessInstance[] - all the ProcessInstances of this customer.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private ProcessInstance[] internalGetCustomerOpenProcesses(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started internalGetCustomerOpenProcesses");

		Integer customerID = null;
		String customerIDParameter = null;
		String sortCriteria = null;

		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"internalGetCustomerOpenProcesses, received parametersMap: "
				+ parametersMap.getAllParameters());

		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.LAST_HANDLER_OF_CUSTOMER_PARAMETER);
		if (parameter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages
					.DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETER_IS_NULL_IN_MAP,
				new Message(
					WFExceptionMessages
						.DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETER_IS_NULL_IN_MAP,
					Message.SEVERITY_ERROR));
		}
		else
		{
			customerID = Integer.valueOf(parameter.getStringValue());
		}

		customerIDParameter = getCustomerIdParameter(parametersMap);

		sortCriteria = getSortCriteria(parametersMap);

		Integer threshold = getProcessInstancesThreshold(parametersMap);

		Logger.debug(
			LOGGER_CONTEXT,
			"internalGetCustomerOpenProcesses, customerID: " + customerID);

		/*
		 * Have to generate a temporary filter as here the filter is required
		 * for ProcessInstances, and in routeWorkItemsOfThisCustomer, the filter
		 * is required for WorkItems.
		 */
		String tempFilter =
			generateCustomerFilter(customerID, customerIDParameter);

		Logger.debug(
			LOGGER_CONTEXT,
			"internalGetCustomerOpenProcesses, tempFilter: " + tempFilter);

		String finalFilter =
			WFConstants.STATE_IN_READY_OR_RUNNING_PREFIX + tempFilter;

		Logger.debug(
			LOGGER_CONTEXT,
			"internalGetCustomerOpenProcesses, finalFilter: " + finalFilter);

		try
		{

			return getExecutionService().queryProcessInstances(
				finalFilter,
				sortCriteria,
				threshold);
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return internalGetCustomerOpenProcesses(parametersMap);
		}
	}

	/**
	 * Assistance method used to generate filter on the customerID.
	 * Is called from the methods internalGetCustomerOpenProcesses()
	 * and routeWorkItemsOfThisCustomer().
	 * @param customerID
	 * @param customerIDParameter Name of the parameter to be retrieved
	 * @return String the appropriate filter.
	 * @throws NullParametersException
	 */
	private String generateCustomerFilter(
		Integer customerID,
		String customerIDParameter)
		throws NullParametersException
	{
		if (customerID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.CUSTOMER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.CUSTOMER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"generateCustomerFilter, received customerId: " + customerID);

		StringBuffer customerFilter = new StringBuffer(256);

		customerFilter.append("*:");
		customerFilter.append(customerIDParameter);
		customerFilter.append(WFConstants.EQUALS_INFIX);
		customerFilter.append(customerID);
		Logger.debug(
			LOGGER_CONTEXT,
			"filter generated for query on customer: "
				+ customerFilter.toString());

		return customerFilter.toString();

	}

	/**
	 * Assistance method, that returns true if the state indicates that
	 * the WorkItem has not ended yet (and can be postponed), false
	 * otherwise.
	 * @param state - The checked ExecutionState.
	 * @return boolean - true if the state allows postpone, false otherwise.
	 * @throws WorkFlowException
	 */
	private boolean stateAllowsPostpone(int state) throws WorkFlowException
	{

		if ((state
			== com.ibm.workflow.api.ItemPackage.ExecutionState._CHECKED_OUT)
			|| (state == com.ibm.workflow.api.ItemPackage.ExecutionState._RUNNING)
			|| (state == com.ibm.workflow.api.ItemPackage.ExecutionState._READY)
			|| (state
				== com.ibm.workflow.api.ItemPackage.ExecutionState._PLANNING)
			|| (state
				== com.ibm.workflow.api.ItemPackage.ExecutionState._SUSPENDED)
			|| (state
				== com.ibm.workflow.api.ItemPackage.ExecutionState._SUSPENDING))
		{
			return true;
		}
		/*
		 * Any other state means that the WorkItem has already completed 
		 * processing or is in a bad state (like IN_ERROR / UNDEFINED).
		 */
		return false;
	}
	/**
	 * This method allows the user to postpone a WFWorkItem completion date
	 * to a specified date in the future.
	 * @param workItemID - ID of the WFWorkItem to be postponed.
	 * @param expectedEndTime expectedEndTime (Date).
	 * @return true if postpone succeeded, false otherwise.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @see com.ness.fw.workflow.WorkFlowService#postponeWorkItem(String, 
	 * 	WorkFlowParameterMap)
	 */
	public boolean postponeWorkItem(String workItemID, Date expectedEndTime)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"postponeWorkItem started, received workItemId: "
				+ workItemID
				+ ", received postponeTime: "
				+ expectedEndTime);

		boolean result = true;

		//check the parameters
		if ((workItemID == null) || (workItemID.equals("")))
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		/*
		 * Checking of the received Date is done in inner method
		 * updateItemExpectedEndTime
		 */
		WFExecutionService managerExecutionService =
			getManagerExecutionService();
		WorkItem workItem = null;
		String owner = null;

		try
		{

			workItem =
				managerExecutionService
					.getExecutionService()
					.persistentWorkItem(
					workItemID);
			workItem.refresh();
			Logger.debug(
				LOGGER_CONTEXT,
				"postponeWorkItem, used manager to retrieve the workItem");
		}
		catch (FmcException fex)
		{
			handleFmcException(fex, true);
			putManagerExecutionService(managerExecutionService);
			managerExecutionService = null;
			//will be reachable only in case of time-out
			return postponeWorkItem(workItemID, expectedEndTime);
		}
		finally
		{
			if (managerExecutionService != null)
			{
				putManagerExecutionService(managerExecutionService);
				managerExecutionService = null;
			}
		}

		/*
		 * According to Leonid, 12/4/05, the WorkItem can't be null.
		 */
		//		 Check the WorkItem state.
		try
		{
			com.ibm.workflow.api.ItemPackage.ExecutionState state =
				workItem.state();
			if (!stateAllowsPostpone(state.value()))
			{
				// Can't postpone the WFWorkItem as its state doesn't allow it.
				result = false;
				Logger.debug(
					LOGGER_CONTEXT,
					"postponeWorkItem, state of the WorkItem does not allow postpone");

			}
			/*
			 * Check if the WI is under your control.
			 * Otherwise change the result to false.
			 */
			owner = workItem.owner();

			Logger.debug(
				LOGGER_CONTEXT,
				"postponeWorkItem, workItem owner: "
					+ workItem.owner()
					+ ", result: "
					+ result);

			// Perform postpone, by updating the description.
			if (result == true)
			{

				String updatedDescription =
					updateItemExpectedEndTime(
						expectedEndTime,
						workItem.description());
				Logger.debug(
					LOGGER_CONTEXT,
					"postponeWorkItem, updatedDescription: "
						+ updatedDescription);

				updateDescription(
					workItem,
					workItemID,
					owner,
					updatedDescription);

				Logger.debug(
					LOGGER_CONTEXT,
					"postponeWorkItem, updated the WorkItem description");

			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return postponeWorkItem(workItemID, expectedEndTime);
		}

		return result;
	}

	/**
	 * Assistance method.
	 * Is used since the description can be updated only by the WorkItem owner
	 * (not by the manager user).
	 * @param workItemID
	 * @param owner owner of the WorkItem
	 * @param description new description to be updated.
	 * @throws WorkFlowException
	 */
	private void updateDescription(
		WorkItem managerWorkItem,
		String workItemId,
		String owner,
		String description)
		throws WorkFlowException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started updateDescription, workItemID: " + workItemId);
		String currentUserId = null;

		try
		{

			currentUserId = getExecutionService().userID();
			Logger.debug(
				LOGGER_CONTEXT,
				"updateDescription, currentUserId: " + currentUserId);
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			updateDescription(managerWorkItem, workItemId, owner, description);
		}

		updateDescriptionWithoutReturningToOwner(
			managerWorkItem,
			workItemId,
			owner,
			currentUserId,
			description);

		//If(not owner) return workItem
		if (!currentUserId.equals(owner))
		{
			transferWorkItem(managerWorkItem, owner);
			Logger.debug(
				LOGGER_CONTEXT,
				"updateDescription, workItem was returned to original user ");

		}

	}

	/**
	 * Assistance method.
	 * Is used since the description can be updated only by the WorkItem owner
	 * (not by the manager user).
	 * @param managerWorkItem
	 * @param workItemId
	 * @param owner owner of the WorkItem
	 * @param currentUserId
	 * @param description new description to be updated.
	 * @throws WorkFlowException
	 */
	private void updateDescriptionWithoutReturningToOwner(
		WorkItem managerWorkItem,
		String workItemId,
		String owner,
		String currentUserId,
		String description)
		throws WorkFlowException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started updateDescriptionWithoutReturningToOwner, workItemId: "
				+ workItemId);
		ExecutionService wfService = getExecutionService();
		WorkItem workItemForDescription = null;

		Logger.debug(
			LOGGER_CONTEXT,
			"updateDescriptionWithoutReturningToOwner, workItemOwner: "
				+ owner);
		try
		{

			if (!currentUserId.equals(owner))
			{
				transferWorkItem(managerWorkItem, currentUserId);
				Logger.debug(
					LOGGER_CONTEXT,
					"updateDescriptionWithoutReturningToOwner, workItem was transferred to current user ");
			}

			workItemForDescription = wfService.persistentWorkItem(workItemId);
			Logger.debug(
				LOGGER_CONTEXT,
				"updateDescriptionWithoutReturningToOwner, received workItemForDescription");
			workItemForDescription.refresh();

			//Update the description of the workItem.
			workItemForDescription.setDescription(description);
			Logger.debug(
				LOGGER_CONTEXT,
				"updateDescriptionWithoutReturningToOwner, set description successfully");

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			updateDescriptionWithoutReturningToOwner(
				managerWorkItem,
				workItemId,
				owner,
				currentUserId,
				description);
		}

	}

	/**
	 * Creation of a new ProcessInstance
	 * @param processTemplateName name of the ProcessTemplate from which
	 * the ProcessInsance should be created.
	 * @param processName Name of the new ProcessInstance to be created.
	 * @param processContext A String defined in the API like the description,
	 * but cannot be updated during the ProcessInstance live time.
	 * @param parametersMap Will contain the relevant applicative parameters.
	 * @return String processID ID of the new ProcessInstance.
	 * @throws WorkFlowLogicException
	 * @throws WorkFlowException
	 */
	public String createAndStartProcessInstance(
		String processTemplateName,
		String processName,
		String processContext,
		WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowLogicException, WorkFlowException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"createAndStartProcessInstance started, received processTemplateName: "
				+ processTemplateName
				+ ", received processName: "
				+ processName
				+ ", received processContext: "
				+ processContext
				+ ", received parametersMap: "
				+ parametersMap);

		if (processTemplateName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_TEMPLATE_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_TEMPLATE_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		if (processName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		if (processContext == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_CONTEXT_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_CONTEXT_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		String filter = WFConstants.NAME_IN_PREFIX + processTemplateName + "'";

		try
		{

			ProcessTemplate[] templates =
				getExecutionService().queryProcessTemplates(
					filter,
					WFConstants.NAME_ASC,
					new Integer(
						SystemResources.getInstance().getProperty(
							WFConstants.PROCESS_TEMPLATES_THRESHOLD_KEY)));

			if ((templates == null) || (templates.length == 0))
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages
						.NO_PROCESS_TEMPLATE_FOUND_WITH_THE_GIVEN_NAME,
					new Message(
						WFExceptionMessages
							.NO_PROCESS_TEMPLATE_FOUND_WITH_THE_GIVEN_NAME,
						Message.SEVERITY_ERROR));
			}

			/*
			 * According to talk with Leonid, 17.11.04, no chance that the templates array length
			 * will be greater than one, so if there is a template, it is unique.
			 */
			// Get the container required for the method.
			ReadWriteContainer container = templates[0].initialInContainer();

			if (parametersMap != null)
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"createAndStartProcessInstance, received parametersMap: "
						+ parametersMap.getAllParameters());

				//Set the container buffer from the given parametersMap.
				WorkFlowServiceParameter containerFieldsParameter =
					parametersMap.get(WFConstants.CONTAINER_FIELDS);

				if (containerFieldsParameter != null)
				{
					WorkFlowServiceParameterMap fieldsMap =
						(WorkFlowServiceParameterMap) containerFieldsParameter
							.getValue();

					setContainerBufferFromMap(fieldsMap, container);
				}

			}

			ProcessInstance instance =
				templates[0].createAndStartInstance3(
					processName,
					"",
					"",
					container,
					true,
					processContext);
			return instance.persistentOid();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return createAndStartProcessInstance(
				processTemplateName,
				processName,
				processContext,
				parametersMap);
		}
	}

	/** Retrieve all ProcessInstances that fulfill a given filter.
	 * @param parametersMap - holds processID and showOnlyActivitiesForShow(boolean)
	 * @return WFProcessInstanceList Holds an array of all retrieved ProcessInstances.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 * @see com.ness.fw.workflow.WorkFlowService#retrieveProcesses
	 */
	public WFProcessInstanceList retrieveProcesses(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "RetrieveProcesses started");

		//checking that the parameters are legal.
		//checking if the parametersMap is null is performed inside.
		checkFilterParameter(parametersMap);

		Logger.debug(
			LOGGER_CONTEXT,
			"RetrieveProcesses, received parametersMap: "
				+ parametersMap.getAllParameters());

		String filter = parametersMap.get(WFConstants.FILTER).getStringValue();
		String sortCriteria = getSortCriteria(parametersMap);
		Integer processesThreshold =
			getProcessInstancesThreshold(parametersMap);
		Integer workItemsThreshold = getWorkItemsThreshold(parametersMap);

		/*
		 * This check is NOT covered in checkWorkListParameters!
		 * "Missing parameter in the ParametersMap"
		 */
		if (filter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.FILTER_PARAMETER_VALUE_IS_NULL,
				new Message(
					WFExceptionMessages.FILTER_PARAMETER_VALUE_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		//Names of parameters to be retrieved from the container
		String[] inContainerElementNames =
			getInContainerElementNames(parametersMap);
		String[] outContainerElementNames =
			getOutContainerElementNames(parametersMap);
		String[] globalContainerElementNames =
			getGlobalContainerElementNames(parametersMap);

		ExecutionService wfService = getExecutionService();
		ProcessInstance[] processInstances = null;

		//		//TODO: added temporarily to resolve problem with SyncLDAP2WF
		//		StringBuffer processedFilter = null;
		//		if(!filter.equals(""))
		//		{
		//			processedFilter = filter + " AND " + 
		//                 "PROCESS_TEMPLATE_NAME NOT LIKE ('SyncLDAP2WF')"
		//		}

		try
		{
			processInstances =
				wfService.queryProcessInstances(
					filter,
					sortCriteria,
					processesThreshold);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrieveProcesses(parametersMap);
		}

		//default option
		boolean retrieveNotifications = getRetrieveNotifications(parametersMap);
		boolean getExpirationTime = getExpirationTime(parametersMap);
		boolean useManager = getUseManager(parametersMap);

		WFProcessInstance[] instances = null;

		/*
		 * The processInstances array can be null, for example, if no
		 * ProcessInstance fits the filter. In this case, this method will
		 * return null.
		 */
		if (processInstances != null)
		{

			// This variable will contain the result array.
			instances = new WFProcessInstance[processInstances.length];

			// Initialization of the ProcessInstances array.
			for (int i = 0; i < processInstances.length; i++)
			{
				instances[i] =
					createWFProcessInstance(
						processInstances[i],
						workItemsThreshold,
						WFConstants.SORT_CREATION_TIME_ASC,
						inContainerElementNames,
						outContainerElementNames,
						globalContainerElementNames,
						wfService,
						retrieveNotifications,
						getExpirationTime,
						useManager);
			}

		}

		return new WFProcessInstanceList(instances);
	}

	/** Retrieve all Ids of ProcessInstances that fulfill a given filter.
	 * @param parametersMap - holds processID and showOnlyActivitiesForShow(boolean)
	 * @param numOfRowsInPage
	 * @return IDPagingService Contains all ids of the ProcessInstances that 
	 * fulfill the filter.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 * @see com.ness.fw.workflow.WorkFlowService#retrieveProcesses
	 */
	public IDPagingService retrieveProcessIds(
		WorkFlowServiceParameterMap parametersMap,
		int numOfRowsInPage)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "RetrieveProcessIds started");

		//checking that the parameters are legal.
		//checking if the parametersMap is null is performed inside.
		checkFilterParameter(parametersMap);

		Logger.debug(
			LOGGER_CONTEXT,
			"RetrieveProcessIds, received parametersMap: "
				+ parametersMap.getAllParameters());

		String filter = parametersMap.get(WFConstants.FILTER).getStringValue();
		String sortCriteria = getSortCriteria(parametersMap);
		Integer processesThreshold =
			getProcessInstancesThreshold(parametersMap);

		/*
		 * This check is NOT covered in checkWorkListParameters!
		 * "Missing parameter in the ParametersMap"
		 */
		if (filter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.FILTER_PARAMETER_VALUE_IS_NULL,
				new Message(
					WFExceptionMessages.FILTER_PARAMETER_VALUE_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		ExecutionService wfService = getExecutionService();
		ProcessInstance[] processInstances = null;
		ArrayList result = new ArrayList();

		//		//TODO: added temporarily to resolve problem with SyncLDAP2WF
		//		StringBuffer processedFilter = null;
		//		if(!filter.equals(""))
		//		{
		//			processedFilter = filter + " AND " + 
		//                 "PROCESS_TEMPLATE_NAME NOT LIKE ('SyncLDAP2WF')"
		//		}

		try
		{
			processInstances =
				wfService.queryProcessInstances(
					filter,
					sortCriteria,
					processesThreshold);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrieveProcessIds(parametersMap, numOfRowsInPage);
		}

		/*
		 * The processInstances array can be null, for example, if no
		 * ProcessInstance fits the filter. In this case, this method will
		 * return null.
		 */
		if (processInstances != null)
		{

			// Initialization of the ProcessInstances array.
			for (int i = 0; i < processInstances.length; i++)
			{
				try
				{
					result.add(i, processInstances[i].persistentOid());
				}
				catch (FmcException fex)
				{
					handleFmcException(fex);
					//will be reachable only in case of time-out
					return retrieveProcessIds(parametersMap, numOfRowsInPage);
				}
			}
		}

		return new IDPagingService(result, numOfRowsInPage);
	}

	/**
	 * Assistance method.
	 * @param parametersMap
	 * @return useManager should manager authorization be used with the ExecutionService
	 * @throws NullParametersException
	 */
	private boolean getUseManager(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{
		WorkFlowServiceParameter useManagerParameter =
			parametersMap.get(WFConstants.USE_MANAGER);

		//default option
		boolean useManager = false;
		if (useManagerParameter != null)
		{
			Boolean useManagerObject = useManagerParameter.getBooleanValue();
			if (useManagerObject != null)
			{
				useManager = useManagerObject.booleanValue();
			}
		}
		return useManager;
	}

	/**
	 * Assistance method.
	 * @param parametersMap
	 * @return expirationTime for a WorkItem
	 * @throws NullParametersException
	 */
	private boolean getExpirationTime(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{
		WorkFlowServiceParameter getExpirationTimeParameter =
			parametersMap.get(WFConstants.RETRIEVE_EXPIRATION_TIME);

		//default option
		boolean getExpirationTime = false;
		if (getExpirationTimeParameter != null)
		{
			Boolean getExpirationTimeObject =
				getExpirationTimeParameter.getBooleanValue();
			if (getExpirationTimeObject != null)
			{
				getExpirationTime = getExpirationTimeObject.booleanValue();
			}
		}
		return getExpirationTime;
	}

	/**
	 * Assistance method.
	 * @param parametersMap
	 * @return retrieveNotifications should ActivityInstanceNotifications be retrieved ?
	 * @throws NullParametersException
	 */
	private boolean getRetrieveNotifications(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{
		WorkFlowServiceParameter retrieveNotificationsParameter =
			parametersMap.get(WFConstants.RETRIEVE_NOTIFICATIONS);
		//default option
		boolean retrieveNotifications = false;
		if (retrieveNotificationsParameter != null)
		{
			Boolean retrieveNotificationsObject =
				retrieveNotificationsParameter.getBooleanValue();
			if (retrieveNotificationsObject != null)
			{
				retrieveNotifications =
					retrieveNotificationsObject.booleanValue();
			}
		}

		return retrieveNotifications;
	}

	/**
	 * Assistance method.
	 * @param parametersMap
	 * @return String[] list of parameters to be retrieved from the input container.
	 * @throws NullParametersException
	 */
	private String[] getInContainerElementNames(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		//Names of parameters to be retrieved from the container
		String[] inContainerElementNames = null;

		WorkFlowServiceParameter inContainerElementsParameter =
			parametersMap.get(WFConstants.IN_CONTAINER_ELEMENT_NAMES);
		if (inContainerElementsParameter == null)
		{
			inContainerElementNames = null;

		}
		else
		{

			/* Note that the containerElementNames can itself be null. In this case,
			 * no parameter will be retrieved from the container.
			 */
			inContainerElementNames =
				(String[]) inContainerElementsParameter.getValue();
		}

		return inContainerElementNames;

	}

	/**
	 * Assistance method.
	 * @param parametersMap
	 * @return String[] list of parameters to be retrieved from the output container.
	 * @throws NullParametersException
	 */
	private String[] getOutContainerElementNames(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		//Names of parameters to be retrieved from the container
		String[] outContainerElementNames = null;

		WorkFlowServiceParameter outContainerElementsParameter =
			parametersMap.get(WFConstants.OUT_CONTAINER_ELEMENT_NAMES);
		if (outContainerElementsParameter == null)
		{
			outContainerElementNames = null;

		}
		else
		{

			/* Note that the containerElementNames can itself be null. In this case,
			 * no parameter will be retrieved from the container.
			 */
			outContainerElementNames =
				(String[]) outContainerElementsParameter.getValue();
		}

		return outContainerElementNames;

	}

	/**
	 * Assistance method.
	 * @param parametersMap
	 * @return String[] list of parameters to be retrieved from the global container.
	 * @throws NullParametersException
	 */
	private String[] getGlobalContainerElementNames(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		//Names of parameters to be retrieved from the container
		String[] globalContainerElementNames = null;

		WorkFlowServiceParameter globalContainerElementsParameter =
			parametersMap.get(WFConstants.GLOBAL_CONTAINER_ELEMENT_NAMES);
		if (globalContainerElementsParameter == null)
		{
			globalContainerElementNames = null;

		}
		else
		{

			/* Note that the containerElementNames can itself be null. In this case,
			 * no parameter will be retrieved from the container.
			 */
			globalContainerElementNames =
				(String[]) globalContainerElementsParameter.getValue();
		}

		return globalContainerElementNames;

	}

	/**
	 * Assistance method.
	 * @param parametersMap
	 * @return sortCriteria
	 * @throws NullParametersException
	 */
	private String getSortCriteria(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{
		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.SORT_CRITERIA);

		if (parameter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.SORT_CRITERIA_PARAMETER_IS_NULL_IN_MAP,
				new Message(
					WFExceptionMessages.SORT_CRITERIA_PARAMETER_IS_NULL_IN_MAP,
					Message.SEVERITY_ERROR));
		}

		String sortCriteria = parameter.getStringValue();
		if (sortCriteria == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.SORT_CRITERIA_PARAMETER_VALUE_IS_NULL,
				new Message(
					WFExceptionMessages.SORT_CRITERIA_PARAMETER_VALUE_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		return sortCriteria;
	}

	/**
	 * Assistance method (Used in services performing query on WorkItems).
	 * @param parametersMap
	 * @return threshold
	 * @throws NullParametersException
	 */
	private Integer getWorkItemsThreshold(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started getWorkItemsThreshold");

		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.WORK_ITEMS_THRESHOLD);
		String thresholdValue = null;

		if (parameter != null)
		{
			thresholdValue = parameter.getStringValue();
		}

		if (thresholdValue == null)
		{

			thresholdValue =
				SystemResources.getInstance().getProperty(
					WFConstants.WORK_ITEMS_THRESHOLD_KEY);
			Logger.debug(
				LOGGER_CONTEXT,
				"getWorkItemsThreshold, took threshold from properties");

		}
		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkItemsThreshold, returned threshold: " + thresholdValue);

		return Integer.valueOf(thresholdValue);
	}

	/**
	 * Assistance method (Used in services performing query on ProcessInstances).
	 * @param parametersMap
	 * @return threshold
	 * @throws NullParametersException
	 */
	private Integer getProcessInstancesThreshold(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{
		Logger.debug(LOGGER_CONTEXT, "started getProcessInstancesThreshold");

		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.PROCESS_INSTANCES_THRESHOLD);
		String thresholdValue = null;

		if (parameter != null)
		{
			thresholdValue = parameter.getStringValue();
		}

		if (thresholdValue == null)
		{

			thresholdValue =
				SystemResources.getInstance().getProperty(
					WFConstants.PROCESS_INSTANCES_THRESHOLD_KEY);
			Logger.debug(
				LOGGER_CONTEXT,
				"getProcessInstancesThreshold, took threshold from properties");
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstancesThreshold, returned threshold: "
				+ thresholdValue);

		return Integer.valueOf(thresholdValue);
	}

	/**
	 * Assistance method (to be used when the customerId is mandatory).
	 * @param parametersMap
	 * @return String customerIdParameter
	 * @throws NullParametersException
	 */
	private String getCustomerIdParameter(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{
		String result = null;

		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.CUSTOMER_ID_PARAMETER);
		if (parameter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages
					.GLOBAL_CONTAINER_ELEMENT_NAME_IS_NULL_IN_THE_MAP,
				new Message(
					WFExceptionMessages
						.GLOBAL_CONTAINER_ELEMENT_NAME_IS_NULL_IN_THE_MAP,
					Message.SEVERITY_ERROR));
		}
		else
		{
			result = parameter.getStringValue();
		}

		if (result == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.MISSING_GLOBAL_CONTAINER_ELEMENT,
				new Message(
					WFExceptionMessages.MISSING_GLOBAL_CONTAINER_ELEMENT,
					Message.SEVERITY_ERROR));
		}

		return result;
	}

	/**
	 * Assistance method, for getting WorkList in case a predefinedFilterId
	 * was supplied in method retrieveTasks.
	 * @param parametersMap
	 * @param useManager
	 * @param wfService The used ExecutionService
	 * @param predefinedFilterId
	 * @return
	 * @throws FmcException
	 * @throws WorkFlowException
	 */
	private WorkList getWorkList(
		WorkFlowServiceParameterMap parametersMap,
		ExecutionService wfService,
		String predefinedFilterId)
		throws FmcException, WorkFlowException
	{

		//Default option. Only if retrieved predefined filter, it won't be null.
		WorkList workList = null;

		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkList, generating persistent WorkList");
		// useManager was selected, therefore use manager

		workList = wfService.persistentWorkList(predefinedFilterId);

		/*
		 * According to Leonid, 12/4/05, the workList won't be null.
		 */
		if (workList.isEmpty())
		{
			workList.refresh();
		}

		//Returned value can be null.
		return workList;

	}

	/**
	 * Assistance method of retrieveTasks().
	 * If can't retrieve the predefinedFilter, generate an empty filter.
	 * @param workList
	 * @return predefinedFilter the predefined filter or "".
	 * @throws WorkFlowException
	 */
	private String retrievePredefinedFilter(WorkList workList) throws FmcException
	{
		String predefinedFilter = null;

		if (workList != null)
		{

			predefinedFilter = workList.filter();
			Logger.debug(
				LOGGER_CONTEXT,
				"retrievePredefinedFilter, predefined filter: "
					+ predefinedFilter);

		}
		// Predefined filter is mandatory!
		if (predefinedFilter == null)
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"retrievePredefinedFilter, predefined filter was null");

			//Required as the predefinedFilter is used in a StringBuffer
			//to create the actual filter.
			predefinedFilter = "";

		}

		return predefinedFilter;

	}

	/**
	 * Retrieve all tasks (WorkItems and optionally ActivityInstanceNotifications) 
	 * that fulfill a given filter.
	 * @param parametersMap - will include:
	 *  TASKS_OWNED_BY_THIS_USER - show only personal tasks or also tasks 
	 * of other users, etc.
	 * @param filterId Id of the WorkList to be retrieved for a predefined WorkList.
	 * @return WFWorkItem[] Array of all the retrieved WorkItems.
	 * @throws WorkFlowException wraps FmcException that might be thrown
	 * inside.
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFWorkItemList retrieveTasks(
		WorkFlowServiceParameterMap parametersMap,
		String predefinedFilterId)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "RetrieveTasks started");

		//Checking validity of the parameters
		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"RetrieveTasks, received parametersMap: "
				+ parametersMap.getAllParameters());
		Logger.debug(
			LOGGER_CONTEXT,
			"RetrieveTasks, received predefinedFilterId: "
				+ predefinedFilterId);

		ExecutionService wfService = null;

		/*
		 * This parameter will contain the applicative field
		 * "Retrieve only tasks owned by this user".
		 */
		Boolean personalTasksOnly =
			getPersonalTasksOnlyParameter(parametersMap);

		/*
		 * This map will be passed to retrieveTasksForUser(), maybe changed 
		 * according to the value of TASKS_OWNED_BY_THIS_USER.
		 */
		WorkFlowServiceParameter filterParameter =
			parametersMap.get(WFConstants.FILTER);
		//The filter can be null.
		String filter = null;
		//Default option. Only if retrieved predefined filter, it won't be null.
		WorkList workList = null;

		//default option
		boolean useManager = getUseManager(parametersMap);

		String predefinedFilter = null;

		WFWorkItemList list = null;

		//Names of parameters to be retrieved from the container
		String[] inContainerElementNames =
			getInContainerElementNames(parametersMap);
		String[] outContainerElementNames =
			getOutContainerElementNames(parametersMap);
		String[] globalContainerElementNames =
			getGlobalContainerElementNames(parametersMap);

		Logger.debug(
			LOGGER_CONTEXT,
			"RetrieveTasks, set all container arrays from parametersMap");

		//default option
		boolean retrieveNotifications = getRetrieveNotifications(parametersMap);

		// Should the expiration time be retrieved?
		boolean getExpirationTime = getExpirationTime(parametersMap);

		WFWorkItem[] workItems = null;

		WFExecutionService managerExecutionService = null;

		/*
		 * This Try - finally block is used to ensure in case of usage in
		 * ManagerExecutionService, after usage it will be returned to the pool.
		 */

		try
		{
			if (useManager)
			{
				managerExecutionService = getManagerExecutionService();
				wfService = managerExecutionService.getExecutionService();
				Logger.debug(
					LOGGER_CONTEXT,
					"RetrieveTasks, got managerExecutionService from the pool");

			}
			else
			{
				wfService = getExecutionService();
			}

			if (predefinedFilterId != null)
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"RetrieveTasks, getting WorkList, using manager: "
						+ useManager);
				workList =
					getWorkList(parametersMap, wfService, predefinedFilterId);

			}
			/*
			 * Here we are outside the loop for workList generation, so the workList
			 * can be null.
			 */
			Logger.debug(
				LOGGER_CONTEXT,
				"RetrieveTasks, before checking the predefined filter");
			predefinedFilter = retrievePredefinedFilter(workList);

			if (filterParameter == null)
			{
				if (workList != null)
				{
					//Use filter of the WorkList

					workItems =
						queryWorkList(
							workList,
							inContainerElementNames,
							outContainerElementNames,
							globalContainerElementNames,
							wfService,
							retrieveNotifications,
							getExpirationTime);

				}
				else //WorkList is null, use default filter
					{
					Logger.debug(
						LOGGER_CONTEXT,
						"RetrieveTasks, using default filter: "
							+ WFConstants.DEFAULT_TASKS_FILTER);

					workItems =
						retrieveTasksForUser(
							inContainerElementNames,
							outContainerElementNames,
							globalContainerElementNames,
							WFConstants.DEFAULT_TASKS_FILTER,
							getSortCriteria(parametersMap),
							getWorkItemsThreshold(parametersMap),
							wfService,
							retrieveNotifications,
							getExpirationTime);

				}

			}
			else
			{
				/*
				 * This variable will contain the manipulated filter.
				 * In case the passed filter (or the relevant filterParameter)
				 * is null, simply ignore it and use the predefined filter, 
				 * and maybe tasks that conform with TASKS_OWNED_BY_THIS_USER).
				 */
				filter =
					handleWorkItemsFilter(
						predefinedFilter,
						filterParameter,
						personalTasksOnly);
				Logger.debug(
					LOGGER_CONTEXT,
					"RetrieveTasks, filter: " + filter);

				workItems =
					retrieveTasksForUser(
						inContainerElementNames,
						outContainerElementNames,
						globalContainerElementNames,
						filter,
						getSortCriteria(parametersMap),
						getWorkItemsThreshold(parametersMap),
						wfService,
						retrieveNotifications,
						getExpirationTime);

			}

			/*
			 * Showing only tasks whose owner is the current user.
			 * If no special filter is shown, the user will be able to see tasks
			 * of all persons he is allowed for.
			 */

			for (int i = 0; i < workItems.length; i++)
			{
				if (workItems[i] == null)
				{
					throw new WorkFlowLogicException(
						WFExceptionMessages.AT_LEAST_ONE_ITEM_IS_NULL,
						new Message(
							WFExceptionMessages.AT_LEAST_ONE_ITEM_IS_NULL,
							Message.SEVERITY_ERROR));
				}
			}

			list = new WFWorkItemList(workItems);
			Logger.debug(
				LOGGER_CONTEXT,
				"retrieveTasks, created new WFWorkItemList");

			if (list == null)
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.RETRIEVED_WORKLIST_NULL,
					new Message(
						WFExceptionMessages.RETRIEVED_WORKLIST_NULL,
						Message.SEVERITY_ERROR));
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex, useManager);
			//Reachable only in case of time-out
			retrieveTasks(parametersMap, predefinedFilterId);
		}

		finally
		{
			if (managerExecutionService != null)
			{
				putManagerExecutionService(managerExecutionService);
				managerExecutionService = null;
				wfService = null;
			}
		}

		return list;

	}

	/**
	 * Query on a predefined workList
	 * @param workList
	 * @param inContainerElementNames
	 * @param outContainerElementNames
	 * @param globalContainerElementNames
	 * @param wfService
	 * @param getExpirationTime
	 * @return WFWorkItem[] the retrieved WorkItems.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private WFWorkItem[] queryWorkList(
		WorkList workList,
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		ExecutionService wfService,
		boolean retrieveNotifications,
		boolean getExpirationTime)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "started queryWorkList");

		if (retrieveNotifications)
		{
			return queryWorkListWithNotifications(
				workList,
				inContainerElementNames,
				outContainerElementNames,
				globalContainerElementNames,
				wfService,
				getExpirationTime);
		}
		else
		{
			return queryWorkListWithoutNotifications(
				workList,
				inContainerElementNames,
				outContainerElementNames,
				globalContainerElementNames,
				wfService,
				getExpirationTime);

		}
	}

	/**
	 * Assistance method - perform query without notifications.
	 * @param workList
	 * @param containerElementNames
	 * @param wfService
	 * @param getExpirationTime
	 * @return WFWorkItem[] the retrieved WorkItems.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private WFWorkItem[] queryWorkListWithoutNotifications(
		WorkList workList,
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		ExecutionService wfService,
		boolean getExpirationTime)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started queryWorkListWithoutNotifications");

		WFWorkItem[] workItems = null;
		WorkItem[] itemsArray = null;

		itemsArray = workList.queryWorkItems();

		/*
		 * This program returns one array, its first part is the WorkItems 
		 * and the second part is the ActivityInstanceNotifications.
		 */
		int itemsLength = itemsArray.length;
		workItems = new WFWorkItem[itemsLength];

		for (int index = 0; index < itemsLength; index++)
		{
			workItems[index] =
				createWFWorkItem(
					inContainerElementNames,
					outContainerElementNames,
					globalContainerElementNames,
					itemsArray[index],
					wfService,
					getExpirationTime);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"just before return from queryWorkListWithoutNotifications");
		return workItems;

	}

	/**
	 * Assistance method - perform query with notifications.
	 * @param workList
	 * @param inContainerElementNames
	 * @param outContainerElementNames
	 * @param globalContainerElementNames
	 * @param wfService
	 * @param getExpirationTime
	 * @return WFWorkItem[] the retrieved WorkItems.
	 * @throws FmcException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @throws WorkFlowException
	 */
	private WFWorkItem[] queryWorkListWithNotifications(
		WorkList workList,
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		ExecutionService wfService,
		boolean getExpirationTime)
		throws
			FmcException,
			NullParametersException,
			WorkFlowLogicException,
			WorkFlowException
	{

		Logger.debug(LOGGER_CONTEXT, "started queryWorkListWithNotifications");
		WFWorkItem[] workItems = null;
		ActivityInstanceNotification[] notificationsArray = null;
		WorkItem[] itemsArray = null;

		/*
		 * This program returns one array, its first part is the WorkItems 
		 * and the second part is the ActivityInstanceNotifications.
		 */
		itemsArray = workList.queryWorkItems();
		notificationsArray = workList.queryActivityInstanceNotifications();

		int itemsLength = itemsArray.length + notificationsArray.length;
		workItems = new WFWorkItem[itemsLength];

		for (int index = 0; index < itemsArray.length; index++)
		{
			workItems[index] =
				createWFWorkItem(
					inContainerElementNames,
					outContainerElementNames,
					globalContainerElementNames,
					itemsArray[index],
					wfService,
					getExpirationTime);
		}

		/*
		 * Notifications do not have Input/Output containers, therefore
		 * there is no need to pass them the containerElementNames to be
		 * retrieved.
		 */

		for (int j = 0; j < notificationsArray.length; j++)
		{
			workItems[itemsArray.length + j] =
				createWFWorkItem(
					notificationsArray[j],
					wfService,
					getExpirationTime);
		}

		return workItems;

	}

	/**
	 * Assist method of retrieveTasks(), is used to get the value of the
	 * parameter "TASKS_OWNED_BY_THIS_USER" (retrieve only tasks for this user).
	 * @param parametersMap
	 * @return Boolean - TRUE if should retrieve only personal tasks, FALSE
	 * if should retrieve also tasks of users this user is authorized for.
	 * @throws NullParametersException
	 */
	private Boolean getPersonalTasksOnlyParameter(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		/*
		 * This parameter will contain the applicative field
		 * "Show only tasks owned by this user".
		 */
		Boolean personalTasksOnly = Boolean.FALSE;
		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.TASKS_OWNED_BY_THIS_USER);
		if (parameter == null)
		{
			return Boolean.FALSE;
		}
		else
		{
			if (parameter.getValue() == null)
			{
				return Boolean.FALSE;
			}
			else
			{
				personalTasksOnly = parameter.getBooleanValue();
			}
		}
		return personalTasksOnly;
	}

	/**
	 * Assistance method of retrieveTasks.
	 * Generate the manipulated filter, based on value retrieved from the 
	 * WorkFlowService parameter. 
	 * In case the passed parameter is null, simply ignore it and use the fixed 
	 * filter (only tasks for show, and maybe tasks that conform with 
	 * TASKS_OWNED_BY_THIS_USER).
	 * @param parameter - WorkFlowServiceParameter that is expected to 
	 * contain the filter.
	 * @param personalTasksOnly - boolean, is used to decide if to retrieve 
	 * only tasks of this user.
	 * @return String - the updated filter.
	 */
	private String handleWorkItemsFilter(
		String predefinedFilter,
		WorkFlowServiceParameter parameter,
		Boolean personalTasksOnly)
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started handleWorkItemsFilter, predefinedFilter is: "
				+ predefinedFilter);

		/*
		 * Update the filter to see only tasks for presentation
		 * (tasks whose presentation flag is set to true in the description).
		 */
		StringBuffer tempFilter = new StringBuffer(256);

		tempFilter.append(predefinedFilter);
		if (parameter != null)
		{
			String value = parameter.getStringValue();
			if (value != null)
			{
				if (!(tempFilter.toString().equals("")))
				{
					tempFilter.append(WFConstants.FILTER_AND_INFIX);
				}
				tempFilter.append(parameter.getStringValue());

			}
		}

		/*
		 * In case personalTasksOnly is TRUE - retrieve only tasks whose owner 
		 * is the current user (do the appropriate filtering).
		 * Otherwise, no need for special filtering.
		 */
		if (personalTasksOnly == Boolean.TRUE)
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"generating filter, personal tasks only is True");
			if (!(tempFilter.toString().equals("")))
			{
				tempFilter.append(WFConstants.FILTER_AND_INFIX);
			}

			/*
			 * Since using manager, have to specify the currentUser (do not use
			 * CURRENT_USER as it might be the manager).
			 */
			String userId = executionService.getUserId();
			tempFilter.append(WFConstants.OWNER_EQUALS_PREFIX);
			tempFilter.append(userId);
			tempFilter.append("'");
		}
		return tempFilter.toString();
	}

	/**
	 * Assistance method to simplify the code of retrieveTasks.
	 * Returns nothing if execution is OK, if it is not OK (any parameter is null),
	 *  throws NullParametersException.
	 * @param parametersMap - contains the parameters to be checked.
	 * @throws NullParametersException
	 */
	private void checkFilterParameter(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.FILTER);

		if (parameter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.FILTER_PARAMETER_IS_NULL_IN_MAP,
				new Message(
					WFExceptionMessages.FILTER_PARAMETER_IS_NULL_IN_MAP,
					Message.SEVERITY_ERROR));
		}
		else
		{
			if (parameter.getValue() == null)
			{
				throw new NullParametersException(
					WFExceptionMessages.FILTER_IS_NULL,
					new Message(
						WFExceptionMessages.FILTER_IS_NULL,
						Message.SEVERITY_ERROR));
			}
		}

	}

	/**
	 * This method is called by retrieveTasks, and does the actual work.
	 * @param inContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param outContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param globalContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param filter.
	 * @param sortCriteria
	 * @param threshold
	 * @param wfService
	 * @param retrieveNotifications
	 * @param getExpirationTime
	 * @return WFWorkItem[] - array of the retrieved workitems.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @throws FmcException
	 */
	private WFWorkItem[] retrieveTasksForUser(
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		String filter,
		String sortCriteria,
		Integer threshold,
		ExecutionService wfService,
		boolean retrieveNotifications,
		boolean getExpirationTime)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "started retrieveTasksForUser");

		{
			if (retrieveNotifications)
			{
				return retrieveTasksWithNotifications(
					inContainerElementNames,
					outContainerElementNames,
					globalContainerElementNames,
					filter,
					sortCriteria,
					threshold,
					wfService,
					getExpirationTime);

			}
			else
			{
				return retrieveTasksWithoutNotifications(
					inContainerElementNames,
					outContainerElementNames,
					globalContainerElementNames,
					filter,
					sortCriteria,
					threshold,
					wfService,
					getExpirationTime);

			}
		}
	}

	/**
	 * Assistance method - perform query without notifications.
	 * @param workList
	 * @param inContainerElementNames
	 * @param outContainerElementNames
	 * @param globalContainerElementNames
	 * @param wfService
	 * @param getExpirationTime Should expirationTime be retrieved
	 * @param useManager
	 * @return WFWorkItem[] the retrieved WorkItems.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private WFWorkItem[] retrieveTasksWithoutNotifications(
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		String filter,
		String sortCriteria,
		Integer threshold,
		ExecutionService wfService,
		boolean getExpirationTime)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started retrieveTasksWithoutNotifications");

		/* Create two arrays, one for WorkItems and another 
		 * for ActivityInstanceNotifications.
		 * This seperation simplifies the code (no need for speical check
		 * of Activity kind, as creation of each kind is different).
		 */
		WorkItem[] itemsArray = null;
		WFWorkItem[] workItems = null;

		itemsArray = wfService.queryWorkItems(filter, sortCriteria, threshold);

		int itemsLength = itemsArray.length;

		/*
		 * This program returns one array, its first part is the WorkItems 
		 * and the second part is the ActivityInstanceNotifications.
		 */
		workItems = new WFWorkItem[itemsLength];

		for (int index = 0; index < itemsLength; index++)
		{
			workItems[index] =
				createWFWorkItem(
					inContainerElementNames,
					outContainerElementNames,
					globalContainerElementNames,
					itemsArray[index],
					wfService,
					getExpirationTime);
		}

		return workItems;

	}

	/**
	 * Assistance method - perform query with notifications.
	 * @param workList
	 * @param inContainerElementNames
	 * @param outContainerElementNames
	 * @param globalContainerElementNames
	 * @param wfService
	 * @param getExpirationTime
	 * @param useManager
	 * @return WFWorkItem[] the retrieved WorkItems.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private WFWorkItem[] retrieveTasksWithNotifications(
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		String filter,
		String sortCriteria,
		Integer threshold,
		ExecutionService wfService,
		boolean getExpirationTime)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "started retrieveTasksWithNotifications");

		/* Create two arrays, one for WorkItems and another 
		 * for ActivityInstanceNotifications.
		 * This seperation simplifies the code (no need for speical check
		 * of Activity kind, as creation of each kind is different).
		 */
		WorkItem[] itemsArray = null;
		ActivityInstanceNotification[] notificationsArray = null;
		WFWorkItem[] workItems = null;

		itemsArray = wfService.queryWorkItems(filter, sortCriteria, threshold);
		notificationsArray =
			wfService.queryActivityInstanceNotifications(
				filter,
				sortCriteria,
				threshold);

		/*
		 * This program returns one array, its first part is the WorkItems 
		 * and the second part is the ActivityInstanceNotifications.
		 */

		int itemsLength = itemsArray.length + notificationsArray.length;
		workItems = new WFWorkItem[itemsLength];

		/*
		 * This program returns one array, its first part is the WorkItems 
		 * and the second part is the ActivityInstanceNotifications.
		 */
		for (int index = 0; index < itemsArray.length; index++)
		{
			workItems[index] =
				createWFWorkItem(
					inContainerElementNames,
					outContainerElementNames,
					globalContainerElementNames,
					itemsArray[index],
					wfService,
					getExpirationTime);
		}

		/*
		 * Notifications do not have Input/Output containers, therefore
		 * there is no need to pass them the containerElementNames to be
		 * retrieved.
		 */

		for (int j = 0; j < notificationsArray.length; j++)
		{
			workItems[itemsArray.length + j] =
				createWFWorkItem(
					notificationsArray[j],
					wfService,
					getExpirationTime);
		}

		return workItems;

	}

	/**
	 * This method is used when looking for an appropriate
	 * substitute for this user. The substitute must be a real user.
	 * A super-virtual user is defined in advance in the workflow process.
	 * This user has no authorizations for others, and all real users should
	 * be authorized for him, so calling the method "personsAuthorizedForMe" 
	 * should return all real users in the system.
	 * @return String[] - Array of all the user IDs of the real users in
	 * the system.
	 * @throws WorkFlowException if a FmcException is thrown inside.
	 * @throws NullParametersException
	 * @see com.ness.fw.workflow.WorkFlowService#retrieveUsersForSelectSubstitute(String)
	 */
	public String[] retrieveUsersForSelectSubstitute()
		throws WorkFlowException, NullParametersException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForSelectSubstitute started");

		try
		{

			// Access the super-virtual user ID
			Person superVirtualUser =
				getExecutionService().persistentPerson(
					WFConstants.SUPER_VIRTUAL_USER_ID);
			/*
			 * Access list for the super-virtual user might change
			 * due to addition/deletion of users.
			 */
			superVirtualUser.refresh();

			return superVirtualUser.personsAuthorizedForMe();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrieveUsersForSelectSubstitute();
		}
	}

	/**
	 * Return All the users - real and virtual - for which this user is authorized.
	 * @param userID ID of the requested user.
	 * @return String[] Array of all the users for whom the selected user
	 * is authorized.
	 * @throws WorkFlowException if a FmcException is thrown inside.
	 * @throws NullParametersException
	 */
	public String[] retrievePeopleThisUserIsAuthorizedFor(String userID)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"retrievePeopleThisUserIsAuthorizedFor, received userID: "
				+ userID);

		if (userID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		try
		{

			Person user = getExecutionService().persistentPerson(userID);
			user.refresh();

			return user.personsAuthorizedFor();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrievePeopleThisUserIsAuthorizedFor(userID);
		}
	}

	/**
	 * This service is like retrieveUsersForWorkItemRouting, except that
	 * it returns a set of objects containing the user attributes.
	 * @param workItemID
	 * @return Set of users with their details.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public Set retrieveUsersDetailsForWorkItemRouting(String workItemID)
		throws WorkFlowException, NullParametersException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersDetailsForWorkItemRouting, received workItemID: "
				+ workItemID);
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.USERS_SET_INITIAL_SIZE_KEY));
		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersDetailsForWorkItemRouting, initial set size: "
				+ userSetsInitialSize);

		Set set = retrieveUsersForWorkItemRouting(workItemID);
		Set resultSet = new HashSet(userSetsInitialSize);
		UserLdapDetails userDetails = null;

		if (set != null)
		{
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				try
				{

					userDetails =
						ldapManager.getUserDetails(it.next().toString());
				}
				catch (LdapException lex)
				{
					throw new WorkFlowException(lex);
				}
				if (userDetails != null)
				{
					resultSet.add(userDetails);
				}
			}
			Logger.debug(
				LOGGER_CONTEXT,
				"finished retrieveUsersDetailsForWorkItemRouting");

			return resultSet;
		}
		else
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"finished retrieveUsersDetailsForWorkItemRouting, returning null");
			return null;
		}

	}

	/**
	 * Retrieval of users for the routing (does not route the task - 
	 * just indicates who is elligible to take care of it).
	 * @param workItemID - ID of the WorkItem to be routed.
	 * @return Set - Set of all appropriate users.
	 * @throws WorkFlowException - Wraps NamingException
	 * @throws NullParametersException
	 */
	public Set retrieveUsersForWorkItemRouting(String workItemID)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, received workItemID: "
				+ workItemID);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		// Create the persistent WorkItem.
		ExecutionService wfService = getExecutionService();
		ReadOnlyContainer container = null;

		Set managersSet = null;
		Set authorizedSet = null;
		//TODO: Changed in attempt to resolve sorting problems
		Set resultSet = null;
		//		SortedSet resultSet = null;
		Set membersOfRole = null;
		Set managedSet = null;

		WorkItem persistentWorkItem = null;

		WFExecutionService managerExecutionService =
			getManagerExecutionService();

		try
		{

			persistentWorkItem =
				managerExecutionService
					.getExecutionService()
					.persistentWorkItem(
					workItemID);
			//Have to refresh the WorkItem for checking its ownership.
			persistentWorkItem.refresh();
			Logger.debug(
				LOGGER_CONTEXT,
				"retrieveUsersForWorkItemRouting, workItem retrieved with ManagerExecutionService");

		}
		catch (FmcException fex)
		{
			handleFmcException(fex, true);
			putManagerExecutionService(managerExecutionService);
			managerExecutionService = null;
			//will be reachable only in case of time-out
			return retrieveUsersForWorkItemRouting(workItemID);
		}

		finally
		{
			if (managerExecutionService != null)
			{
				putManagerExecutionService(managerExecutionService);
				managerExecutionService = null;
			}
		}
		try
		{

			// Get the list of roles.
			container = persistentWorkItem.inContainer();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrieveUsersForWorkItemRouting(workItemID);
		}

		String workItemRole = null;

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, trying to access containerField: "
				+ WFConstants.MEMBER_OF_ROLES);

		try
		{

			ContainerElement memberOfRoles =
				container.getElement(WFConstants.MEMBER_OF_ROLES);
			workItemRole = memberOfRoles.getString();

		}
		// In case the role is null, don't use it.
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				workItemRole = null;
			}
			else
			{
				throw new WorkFlowException(fex);
			}
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, workItemRole: " + workItemRole);

		//  Search for objects with these matching attributes

		/*
		 * The minimum load factor of the set is defined in the properties file.
		 * If any of the sets is expected to contain more than the defined limit
		 * users, it might be good to set another initial load factor
		 * for the HashSets.
		 */
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.USERS_SET_INITIAL_SIZE_KEY));

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, initial set size: "
				+ userSetsInitialSize);

		managersSet = new HashSet(userSetsInitialSize);
		authorizedSet = new HashSet(userSetsInitialSize);
		//TODO: changed in order to allow sorted set
		resultSet = new HashSet(userSetsInitialSize);
		//		resultSet = new TreeSet();
		membersOfRole = new HashSet(userSetsInitialSize);

		//In case the person is a manager, all people managed by him.
		managedSet = new HashSet(userSetsInitialSize);

		if (workItemRole != null)
		{
			try
			{
				membersOfRole = ldapManager.membersOfRole(workItemRole);
			}
			catch (LdapException fex)
			{
				throw new WorkFlowException(fex);
			}
			if (membersOfRole != null)
			{
				resultSet.addAll(membersOfRole);
			}
			Logger.debug(
				LOGGER_CONTEXT,
				"retrieveUsersForWorkItemRouting, set of people with this role: "
					+ resultSet);
		}

		/*
		 * Third phase:
		 * Add all users who are authorized for those virtual users.
		 */

		authorizedSet = generateSetOfAuthorizedUsers(resultSet.iterator());
		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, set of users authorized for the virtual users: "
				+ authorizedSet);

		//Find all virtual users for the current person.
		// and add their managers.
		String userId = executionService.getUserId().toUpperCase();
		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, userId: " + userId);
		Person currentPerson = null;

		try
		{

			//No need to refresh since the person was just retrieved
			currentPerson = wfService.persistentPerson(userId);
			Logger.debug(
				LOGGER_CONTEXT,
				"retrieveUsersForWorkItemRouting, currentPerson: "
					+ currentPerson);

			/*
			 * Take care of the case the current person is manager.
			 * In this case, the workItem role is null.
			 */
			if (currentPerson.isManager())
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"retrieveUsersForWorkItemRouting, currentPerson is manager");

				try
				{
					managedSet = handleManagerAccessList(userId, currentPerson);
				}
				catch (LdapException fex)
				{
					throw new WorkFlowException(fex);
				}

			}

			Logger.debug(
				LOGGER_CONTEXT,
				"retrieveUsersForWorkItemRouting, managedSet: " + managedSet);

			Logger.debug(
				LOGGER_CONTEXT,
				"retrieveUsersForWorkItemRouting, workItemRole: "
					+ workItemRole);
			if (workItemRole == null)
			{

				Person tempPerson = null;
				int personsAuthorizedLength;

				String[] personsAuthorizedFor =
					currentPerson.personsAuthorizedFor();
				if (personsAuthorizedFor != null)
				{
					personsAuthorizedLength = personsAuthorizedFor.length;

					Logger.debug(
						LOGGER_CONTEXT,
						"retrieveUsersForWorkItemRouting, number of personsAuthorizedFor:"
							+ personsAuthorizedLength);

					for (int i = 0; i < personsAuthorizedLength; i++)
					{
						Logger.debug(
							LOGGER_CONTEXT,
							"retrieveUsersForWorkItemRouting, personsAuthorizedFor["
								+ i
								+ "]: "
								+ personsAuthorizedFor[i]);
						/*
						 * The super virtual user is not a real user and should be elimintaed
						 * from the list.
						 * Aldo eliminate the default manager (not a real user too).
						 */
						if (!(personsAuthorizedFor[i]
							.equals(WFConstants.SUPER_VIRTUAL_USER_ID))
							&& (!(personsAuthorizedFor[i]
								.equals(WFLdapConstants.DEFAULT_MANAGER))))
						{
							tempPerson =
								wfService.persistentPerson(
									personsAuthorizedFor[i]);

							Logger.debug(
								LOGGER_CONTEXT,
								"retrieveUsersForWorkItemRouting, tempPerson: "
									+ tempPerson);
							String tempPersonManager = tempPerson.manager();

							if (!(tempPersonManager
								.equals(WFLdapConstants.DEFAULT_MANAGER)))
							{
								managersSet.add(tempPersonManager);

							}

						}
						else
						{

							Logger.debug(
								LOGGER_CONTEXT,
								"retrieveUsersForWorkItemRouting, eliminated super virtual user and defaultManager from list");

						}

					}
				}

				Logger.debug(
					LOGGER_CONTEXT,
					"retrieveUsersForWorkItemRouting, completed block when role is null");
			}

			/*
			 * Fourth phase: Add coordinator of this role.
			 * Note that the Coordinator of role is not mandatory and there can
			 * be a role without a coordinator for it.
			 * Therefore, if LdapException occurs, it is caught.
			 */
			if (workItemRole != null)
			{
				String coordinatorOfRole = null;
				try
				{
					coordinatorOfRole =
						ldapManager.getCoordinatorOfRole(workItemRole);
				}
				catch (LdapException lex)
				{
					Logger.debug(
						LOGGER_CONTEXT,
						"retrieveUsersForWorkItemRouting, LdapException was thrown "
							+ "while retrieving coordinator of role.");

				}
				Logger.debug(
					LOGGER_CONTEXT,
					"retrieveUsersForWorkItemRouting, coordinator of this role: "
						+ coordinatorOfRole);
				if (coordinatorOfRole != null)
				{
					resultSet.add(coordinatorOfRole);
				}

			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrieveUsersForWorkItemRouting(workItemID);
		}

		/*
		 * Fifth phase:
		 * Merge all sets
		 */
		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, managersSet: " + managersSet);

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, managedSet: " + managedSet);

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, authorizedSet: " + authorizedSet);

		if (managersSet != null)
		{
			resultSet.addAll(managersSet);
		}

		if (managedSet != null)
		{
			resultSet.addAll(managedSet);
		}

		if (authorizedSet != null)
		{
			resultSet.addAll(authorizedSet);
		}

		/*
		 * Sixth phase:
		 * Remove the router from the list
		 *
		 * In case the user is manager, he should be in the final list,
		 * so he'll be able to route for himself workItems not belonging
		 * to him.
		 */
		try
		{
			String owner = persistentWorkItem.owner();

			if ((currentPerson.isManager()) && (!(userId.equals(owner))))
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"retrieveUsersForWorkItemRouting, user is not owner");
				Logger.debug(
					LOGGER_CONTEXT,
					"retrieveUsersForWorkItemRouting, owner: " + owner);
				Logger.debug(
					LOGGER_CONTEXT,
					"retrieveUsersForWorkItemRouting,userId: " + userId);

				resultSet.add(userId);
			}
			else
			{
				resultSet.remove(executionService.getUserId());
			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrieveUsersForWorkItemRouting(workItemID);
		}

		// Eliminate the super-virtual user, if exists in the list.
		if (resultSet
			.contains(WFConstants.SUPER_VIRTUAL_USER_ID.toUpperCase()))
		{
			resultSet.remove(WFConstants.SUPER_VIRTUAL_USER_ID.toUpperCase());
		}
		// eliminate the default manager (not a real user) from the list.
		if (resultSet.contains(WFLdapConstants.DEFAULT_MANAGER.toUpperCase()))
		{
			resultSet.remove(WFLdapConstants.DEFAULT_MANAGER.toUpperCase());
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersForWorkItemRouting, final set returned: "
				+ resultSet);

		return resultSet;

	}

	/**
	 * Retrieve the virtual user name for the specified WorkItem.
	 * In case the WorkItem has a value in its 'people' field in its container,
	 * the WorkItem was not delivered for a virtual user. Null value will be
	 * returned in this case.
	 * @param item WorkItem
	 * @return String Name of the responsible virtual user, or null
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private String getVirtualUserName(WorkItem item)
		throws WorkFlowException, WorkFlowLogicException
	{

		ReadOnlyContainer inContainer = null;

		try
		{

			inContainer = item.inContainer();
		}

		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getVirtualUserName(item);
		}

		if (inContainer == null)
		{
			throw new WorkFlowException(WFExceptionMessages.CONTAINER_IS_NULL);
		}

		//retrieve the fields

		/*
		 * Have to check if the task is a team one.
		 * This field means which people are permitted to perform
		 * the WorkItem.
		 * The try-catch is to avoid the case the ContainerElement is not set.
		 */
		try
		{

			if (inContainer.getString(WFConstants.PEOPLE) != null)
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"getVirtualUserName, value of people field in the container: "
						+ inContainer.getString(WFConstants.PEOPLE));

				return null;
			}
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ WFConstants.PEOPLE);
			}
			else if (fex.rc != FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				throw new WorkFlowException(fex);
			}
		}

		/*
		 * Role and Organization are used to generate the name of
		 * the virtual user.
		 */
		String role = null;
		String organization = null;
		try
		{
			role = inContainer.getString(WFConstants.MEMBER_OF_ROLES);
			Logger.debug(
				LOGGER_CONTEXT,
				"getVirtualUserName, value of role field in the container: "
					+ role);
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ WFConstants.MEMBER_OF_ROLES);
			}
			else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				throw new WorkFlowException(
					WFExceptionMessages.ROLE_CONTAINER_PARAMETER_IS_NULL);
			}
			else
			{
				throw new WorkFlowException(fex);
			}
		}
		try
		{
			organization =
				inContainer.getString(WFConstants.ACTIVITY_ORGANIZATION);
			Logger.debug(
				LOGGER_CONTEXT,
				"getVirtualUserName, value of organization field in the container: "
					+ organization);
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ WFConstants.ACTIVITY_ORGANIZATION);
			}
			else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				throw new WorkFlowException(
					WFExceptionMessages
						.ORGANIZATION_CONTAINER_PARAMETER_IS_NULL);
			}
			else
			{
				throw new WorkFlowException(fex);
			}
		}

		/*
		 * Check the fields.
		 * Both the role field and the organization field must exist
		 * in order for the virtual user to exist.
		 */
		if (role == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.ROLE_CONTAINER_PARAMETER_IS_NULL);
		}
		if (organization == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.ORGANIZATION_CONTAINER_PARAMETER_IS_NULL);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"getVirtualUserName, returned virtual userName: "
				+ role
				+ organization);

		try
		{

			return getVirtualUserId(role, organization);

		}
		catch (LdapException lex)
		{
			throw new WorkFlowException(lex);
		}
	}

	/**
	 * Assistance method used during generation of people list for routing.
	 * @param iterator
	 * @return set - set of authorized users for routing.
	 * @throws WorkFlowException
	 */
	private Set generateSetOfAuthorizedUsers(Iterator iterator)
		throws WorkFlowException
	{

		Person person = null;
		String virtualUser = null;

		/*
		 * The minimum load factor of the set is defined in the properties file.
		 * If any of the sets is expected to contain more than the defined limit
		 * users, it might be good to set another initial load factor
		 * for the HashSets.
		 */
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.USERS_SET_INITIAL_SIZE_KEY));

		Set authorizedSet = new HashSet(userSetsInitialSize);

		try
		{

			while (iterator.hasNext())
			{
				virtualUser = iterator.next().toString().toUpperCase();
				Logger.debug(
					LOGGER_CONTEXT,
					"generateSetOfAuthorizedUsers, virtual userName: "
						+ virtualUser);

				person = getExecutionService().persistentPerson(virtualUser);
				person.refresh();

				String[] personsAuthorized = person.personsAuthorizedForMe();
				for (int k = 0; k < personsAuthorized.length; k++)
				{
					authorizedSet.add(personsAuthorized[k]);
				}

			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return generateSetOfAuthorizedUsers(iterator);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"generateSetOfAuthorizedUsers, set of users authorized for the virtual user: "
				+ authorizedSet);

		return authorizedSet;

	}

	/**
	 * This method returns true if the user's roles include the role 
	 * given as parameter.
	 * @param persistentUser
	 * @param workItemRole
	 * @return true if the user can perform this role, false otherwise.
	 * @throws WorkFlowException
	 */
	private boolean userCanPerformRole(
		Person persistentUser,
		String workItemRole)
		throws WorkFlowException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started userCanPerformRole ,given role: " + workItemRole);

		boolean result = false;
		try
		{

			/*
			 * Will contain the names of roles this user can perform.
			 */
			String[] userRoles = persistentUser.namesOfRoles();

			for (int i = 0; i < userRoles.length; i++)
			{
				if (userRoles[i].equals(workItemRole))
				{
					result = true;
				}
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return userCanPerformRole(persistentUser, workItemRole);
		}

		return result;

	}

	/**
	 * Returns array of User IDs of all users in the given organizational
	 * team, that can perform this role.
	 * @param organizationalTeam
	 * @param role
	 * @return Set Contains user IDs.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public Set retrieveUsersDetailsInOrganizationalTeam(
		String organizationalTeam,
		String role)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersDetailsInOrganizationalTeam started, received organizationalTeam: "
				+ organizationalTeam
				+ ", received role: "
				+ role);

		/*
		 * The minimum load factor of the set is defined in the properties file.
		 * If any of the sets is expected to contain more than the defined limit
		 * users, it might be good to set another initial load factor
		 * for the HashSets.
		 */
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.USERS_SET_INITIAL_SIZE_KEY));

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersDetailsInOrganizationalTeam, users set initial size: "
				+ userSetsInitialSize);

		Set set = retrieveUsersInOrganizationalTeam(organizationalTeam, role);
		Set resultSet = new HashSet(userSetsInitialSize);
		UserLdapDetails userDetails = null;

		if (set != null)
		{
			Iterator it = set.iterator();

			while (it.hasNext())
			{
				try
				{

					userDetails =
						ldapManager.getUserDetails(it.next().toString());
				}
				catch (LdapException lex)
				{
					throw new WorkFlowException(lex);
				}
				if (userDetails != null)
				{
					resultSet.add(userDetails);
				}
			}
			Logger.debug(
				LOGGER_CONTEXT,
				"finished retrieveUsersDetailsInOrganizationalTeam");
			return resultSet;
		}
		else
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"finished retrieveUsersDetailsInOrganizationalTeam, returning null");
			return null;
		}

	}

	/**
	 * Returns array of User IDs of all users in the given organizational
	 * team, that can perform this role.
	 * @param organizationalTeam
	 * @param role
	 * @return Set Contains user IDs.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public Set retrieveUsersInOrganizationalTeam(
		String organizationalTeam,
		String role)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"retrieveUsersInOrganizationalTeam started, received organizationalTeam: "
				+ organizationalTeam
				+ ", received role: "
				+ role);

		try
		{

			return ldapManager.retrieveUsersInOrganizationalTeam(
				organizationalTeam,
				role);
		}
		catch (LdapException lex)
		{
			throw new WorkFlowException(lex);
		}
	}

	/**
	 * Retrieve all tasks (WorkItems and optionally ActivityInstanceNotifications) 
	 * that fulfill a given filter.
	 * @param parametersMap - will include:
	 *  TASKS_OWNED_BY_THIS_USER - show only personal tasks or also tasks 
	 * of other users, etc.
	 * @param filterId Id of the WorkList to be retrieved for a predefined WorkList.
	 * @param numOfRowsInPage
	 * @return IDPagingService Contains an ArrayList of all the retrieved WorkItem Ids.
	 * @throws WorkFlowException wraps FmcException that might be thrown
	 * inside.
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public IDPagingService retrieveTasksIds(
		WorkFlowServiceParameterMap parametersMap,
		String predefinedFilterId,
		int numOfRowsInPage)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "RetrieveTasksIds started");

		//Checking validity of the parameters
		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"RetrieveTasksIds, received parametersMap: "
				+ parametersMap.getAllParameters());
		Logger.debug(
			LOGGER_CONTEXT,
			"RetrieveTasksIds, received predefinedFilterId: "
				+ predefinedFilterId);

		/*
		 * This parameter will contain the applicative field
		 * "Retrieve only tasks owned by this user".
		 */
		Boolean personalTasksOnly =
			getPersonalTasksOnlyParameter(parametersMap);

		/*
		 * This map will be passed to retrieveTasksForUser(), maybe changed 
		 * according to the value of TASKS_OWNED_BY_THIS_USER.
		 */
		WorkFlowServiceParameter filterParameter =
			parametersMap.get(WFConstants.FILTER);
		//The filter can be null.
		String filter = null;
		//Default option. Only if retrieved predefined filter, it won't be null.
		WorkList workList = null;

		boolean retrieveNotifications = getRetrieveNotifications(parametersMap);

		ArrayList workItemIds = null;

		//default option
		boolean useManager = getUseManager(parametersMap);

		String predefinedFilter = null;
		/*
		 * Note: the managerExecutionService is required for the WorkList generation.
		 * Therefore, if using it, it must be defined outside the if, but when accessed,
		 * won't be null.
		 */
		WFExecutionService managerExecutionService = null;

		ExecutionService wfService = getExecutionService();

		try
		{

			if (useManager)
			{
				managerExecutionService = getManagerExecutionService();
				wfService = managerExecutionService.getExecutionService();
			}
			else
			{
				wfService = getExecutionService();
			}

			if (predefinedFilterId != null)
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"RetrieveTasksIds, getting workList with predefinedFilterId");

				workList =
					getWorkList(parametersMap, wfService, predefinedFilterId);

			}
			/*
			 * Here we are outside the loop for workList generation, so the workList
			 * can be null.
			 */
			Logger.debug(
				LOGGER_CONTEXT,
				"RetrieveTasksIds, before checking the predefined filter");
			predefinedFilter = retrievePredefinedFilter(workList);

			if (filterParameter == null)
			{
				if (workList != null)
				{
					//Use the filter of the WorkList

					Logger.debug(
						LOGGER_CONTEXT,
						"RetrieveTasksIds, filter is null and workList is not null");

					workItemIds =
						queryWorkListForPaging(
							workList,
							wfService,
							retrieveNotifications);

				}
				else
				{
					Logger.debug(
						LOGGER_CONTEXT,
						"RetrieveTasksIds, using default filter: "
							+ WFConstants.DEFAULT_TASKS_FILTER);

					workItemIds =
						queryWorkItemIdsWithDynamicParameters(
							WFConstants.DEFAULT_TASKS_FILTER,
							parametersMap,
							wfService,
							retrieveNotifications);

				}

			}
			else
			{
				/*
				 * This variable will contain the manipulated filter.
				 * In case the passed filter (or the relevant filterParameter)
				 * is null, simply ignore it and use the predefined filter, 
				 * and maybe tasks that conform with TASKS_OWNED_BY_THIS_USER).
				 */
				filter =
					handleWorkItemsFilter(
						predefinedFilter,
						filterParameter,
						personalTasksOnly);
				Logger.debug(
					LOGGER_CONTEXT,
					"RetrieveTaskIds, filter: " + filter);
				workItemIds =
					queryWorkItemIdsWithDynamicParameters(
						filter,
						parametersMap,
						wfService,
						retrieveNotifications);
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex, useManager);
			//Will be reachable only in case of time-out
			retrieveTasksIds(
				parametersMap,
				predefinedFilterId,
				numOfRowsInPage);
		}
		finally
		{
			if (managerExecutionService != null)
			{
				putManagerExecutionService(managerExecutionService);
				managerExecutionService = null;
				wfService = null;
			}
		}

		//Creating the pagingService object
		IDPagingService pagingService =
			new IDPagingService(workItemIds, numOfRowsInPage);

		return pagingService;

	}

	/**
	 * Query on a predefined workList (paging version).
	 * @param workList
	 * @param wfService
	 * @return ArrayList Contains the retrieved WorkItem ids.
	 * @param FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private ArrayList queryWorkListForPaging(
		WorkList workList,
		ExecutionService wfService,
		boolean retrieveNotifications)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "started queryWorkListForPaging");

		if (retrieveNotifications)
		{
			return queryWorkListForPagingWithNotifications(
				workList,
				wfService);
		}
		else
		{
			return queryWorkListForPagingWithoutNotifications(
				workList,
				wfService);

		}
	}

	/**
	 * Assistance method - perform query without notifications.
	 * @param workList
	 * @param containerElementNames
	 * @param wfService
	 * @param getExpirationTime
	 * @return ArrayList Contains the retrieved WorkItem ids.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private ArrayList queryWorkListForPagingWithoutNotifications(
		WorkList workList,
		ExecutionService wfService)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started queryWorkListForPagingWithoutNotifications");

		ArrayList workItemIds = null;
		WorkItem[] itemsArray = null;

		itemsArray = workList.queryWorkItems();

		int itemsLength = itemsArray.length;
		workItemIds = new ArrayList();

		for (int index = 0; index < itemsLength; index++)
		{
			workItemIds.add(index, itemsArray[index].persistentOid());
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"just before return from queryWorkListForPagingWithoutNotifications");
		return workItemIds;

	}

	/**
	 * Assistance method - perform query with notifications.
	 * @param workList
	 * @param wfService
	 * @return ArrayList Contains the retrieved WorkItem ids.
	 * @throws FmcException 
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private ArrayList queryWorkListForPagingWithNotifications(
		WorkList workList,
		ExecutionService wfService)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started queryWorkListForPagingWithNotifications");
		ArrayList workItemIds = null;
		ActivityInstanceNotification[] notificationsArray = null;
		WorkItem[] itemsArray = null;

		itemsArray = workList.queryWorkItems();

		/*
		 * This program returns one array, its first part is the WorkItems 
		 * and the second part is the ActivityInstanceNotifications.
		 */
		notificationsArray = workList.queryActivityInstanceNotifications();

		workItemIds = new ArrayList();

		for (int index = 0; index < itemsArray.length; index++)
		{
			workItemIds.add(index, itemsArray[index].persistentOid());

		}

		for (int j = 0; j < notificationsArray.length; j++)
		{
			workItemIds.add(
				itemsArray.length + j,
				notificationsArray[j].persistentOid());
		}

		return workItemIds;

	}

	/**
	 * Assistance method for the case the query should include dynamic filtering
	 * passed by the application.
	 * @param filter
	 * @param parametersMap
	 * @param wfService
	 * @param retrieveNotifications true if notifications should be retrieved too.
	 * @return ArrayList Contains the WorkItem ids
	 * @throws FmcException
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private ArrayList queryWorkItemIdsWithDynamicParameters(
		String filter,
		WorkFlowServiceParameterMap parametersMap,
		ExecutionService wfService,
		boolean retrieveNotifications)
		throws
			FmcException,
			NullParametersException,
			WorkFlowException,
			WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started queryWorkItemIdsWithDynamicParameters");

		//At this point, the parameters were already checked!
		String sortCriteria =
			parametersMap.get(WFConstants.SORT_CRITERIA).getStringValue();
		Integer threshold = getWorkItemsThreshold(parametersMap);

		if (sortCriteria == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.SORT_CRITERIA_PARAMETER_IS_NULL_IN_MAP,
				new Message(
					WFExceptionMessages.SORT_CRITERIA_PARAMETER_IS_NULL_IN_MAP,
					Message.SEVERITY_ERROR));
		}

		if (retrieveNotifications)
		{
			return retrieveTaskIdsWithNotifications(
				filter,
				sortCriteria,
				threshold,
				wfService);

		}
		else
		{
			return retrieveTaskIdsWithoutNotifications(
				filter,
				sortCriteria,
				threshold,
				wfService);

		}
	}

	/**
	 * Assistance method - perform query with notifications.
	 * @param workList
	 * @param wfService
	 * @return ArrayList the retrieved WorkItem ids.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private ArrayList retrieveTaskIdsWithNotifications(
		String filter,
		String sortCriteria,
		Integer threshold,
		ExecutionService wfService)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started retrieveTaskIdsWithNotifications");

		/* Create two arrays, one for WorkItems and another 
		 * for ActivityInstanceNotifications.
		 * This seperation simplifies the code (no need for speical check
		 * of Activity kind, as creation of each kind is different).
		 */
		WorkItem[] itemsArray = null;
		ActivityInstanceNotification[] notificationsArray = null;
		ArrayList result = null;

		itemsArray = wfService.queryWorkItems(filter, sortCriteria, threshold);
		notificationsArray =
			wfService.queryActivityInstanceNotifications(
				filter,
				sortCriteria,
				threshold);

		/*
		 * This program returns one ArrayList, its first part is the WorkItem ids 
		 * and the second part is the ActivityInstanceNotification ids.
		 */

		result = new ArrayList();

		for (int index = 0; index < itemsArray.length; index++)
		{
			result.add(index, itemsArray[index].persistentOid());
		}

		for (int j = 0; j < notificationsArray.length; j++)
		{
			result.add(
				itemsArray.length + j,
				notificationsArray[j].persistentOid());
		}

		return result;

	}

	/**
	 * Assistance method - perform query without notifications.
	 * @param workList
	 * @param wfService
	 * @return ArrayList Contains the retrieved WorkItem ids.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private ArrayList retrieveTaskIdsWithoutNotifications(
		String filter,
		String sortCriteria,
		Integer threshold,
		ExecutionService wfService)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started retrieveTaskIdsWithoutNotifications");

		WorkItem[] itemsArray = null;
		ArrayList result = null;

		itemsArray = wfService.queryWorkItems(filter, sortCriteria, threshold);

		int itemsLength = itemsArray.length;

		/*
		 * This program returns one array, its first part is the WorkItem ids 
		 * and the second part is the ActivityInstanceNotification ids.
		 */
		result = new ArrayList();

		for (int index = 0; index < itemsLength; index++)
		{
			result.add(index, itemsArray[index].persistentOid());
		}

		return result;

	}

	/**
	 * Allows retrieval of multiple WFWorkItem from the Workflow.
	 * @param IDPagingService Contains the requested WorkItems IDs.
	 * @param paramsMap. 
	 * @return WFWorkItemList - the retrieved WFWorkItems according to the
	 * supplied workItemIds.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFWorkItemList getWorkItems(
		IDPagingService pagingService,
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkItems started, received paramsMap: " + paramsMap);
		if (pagingService == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PAGING_SERVICE_IS_NULL,
				new Message(
					WFExceptionMessages.PAGING_SERVICE_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		ArrayList workItemIds = null;

		try
		{
			workItemIds = pagingService.getSubId();
		}
		catch (GeneralException e)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.PAGING_SERVICE_EXCEPTION,
				new Message(
					WFExceptionMessages.PAGING_SERVICE_EXCEPTION,
					Message.SEVERITY_ERROR));
		}
		int size = workItemIds.size();
		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkItems, size of WorkItemIds arrayList: " + size);

		WFWorkItem[] items = new WFWorkItem[size];

		for (int i = 0; i < size; i++)
		{
			items[i] = getWorkItem((String) (workItemIds.get(i)), paramsMap);
		}

		if (items == null)
		{

			throw new WorkFlowLogicException(
				WFExceptionMessages.PASSED_WORKITEMS_ARRAY_IS_NULL,
				new Message(
					WFExceptionMessages.PASSED_WORKITEMS_ARRAY_IS_NULL,
					Message.SEVERITY_ERROR));

		}

		WFWorkItemList list = new WFWorkItemList(items);

		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkItems, created new WFWorkItemList");

		if (list == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.RETRIEVED_WORKLIST_NULL,
				new Message(
					WFExceptionMessages.RETRIEVED_WORKLIST_NULL,
					Message.SEVERITY_ERROR));
		}

		return list;

	}

	/**
	 * Allows retrieval of single WFWorkItem from the Workflow.
	 * @param workItemID - ID of the requested WorkItem.
	 * @param paramsMap contains relevant parameters. 
	 * @return WFWorkItem - the retrieved WFWorkItem according to the
	 * supplied workItemID.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFWorkItem getWorkItem(
		String workItemID,
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkItem started, received workItemID: " + workItemID);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		if (paramsMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkItem started, received parametersMap contents: "
				+ paramsMap.getAllParameters());

		//Names of parameters to be retrieved from the container
		String[] inContainerElementNames =
			getInContainerElementNames(paramsMap);

		String[] outContainerElementNames =
			getOutContainerElementNames(paramsMap);
		String[] globalContainerElementNames =
			getGlobalContainerElementNames(paramsMap);

		boolean getExpirationTime = getExpirationTime(paramsMap);
		boolean useManager = getUseManager(paramsMap);

		ExecutionService wfService = getExecutionService();
		WFWorkItem item = null;
		WorkItem workItem = null;

		WFExecutionService managerExecutionService = null;

		try
		{

			if (useManager)
			{
				try
				{
					Logger.debug(LOGGER_CONTEXT, "getWorkItem, using manager");

					managerExecutionService = getManagerExecutionService();

					workItem =
						managerExecutionService
							.getExecutionService()
							.persistentWorkItem(
							workItemID);
					workItem.refresh();
				}
				catch (FmcException fex)
				{
					handleFmcException(fex, true);
					putManagerExecutionService(managerExecutionService);
					managerExecutionService = null;
					//will be reachable only in case of time-out
					return getWorkItem(workItemID, paramsMap);
				}

			}
			else
			{
				try
				{

					Logger.debug(
						LOGGER_CONTEXT,
						"getWorkItem, not using manager");

					workItem = wfService.persistentWorkItem(workItemID);
					workItem.refresh();

				}
				catch (FmcException fex)
				{
					handleFmcException(fex);
					//will be reachable only in case of time-out
					return getWorkItem(workItemID, paramsMap);
				}
			}

			try
			{
				item =
					createWFWorkItem(
						inContainerElementNames,
						outContainerElementNames,
						globalContainerElementNames,
						workItem,
						wfService,
						getExpirationTime);
			}

			catch (FmcException fex)
			{
				if (useManager)
				{
					handleFmcException(fex, true);
					putManagerExecutionService(managerExecutionService);
					managerExecutionService = null;
					//will be reachable only in case of time-out
					return getWorkItem(workItemID, paramsMap);
				}
				else
				{
					handleFmcException(fex);
					//will be reachable only in case of time-out
					return getWorkItem(workItemID, paramsMap);
				}
			}
		}

		finally
		{
			if (managerExecutionService != null)
			{
				putManagerExecutionService(managerExecutionService);
				managerExecutionService = null;
			}
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"getWorkItem, item was created successfully");
		return item;
	}

	/**
	 * Retrieve the processInstance by its name.
	 * This service is used when working with UPES (in this case, the
	 * processInstanceId is not available in advance and need to be calculated).
	 * @param processInstanceName
	 * @param paramsMap
	 * @return WFProcessInstance the generated WFProcessInstance.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstance getProcessInstanceByName(
		String processInstanceName,
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstanceByName started, received processInstanceName: "
				+ processInstanceName
				+ ", received parametersMap: "
				+ paramsMap);

		if (processInstanceName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		//		String filter = WFConstants.NAME_LIKE + processInstanceName + "'";
		String filter = WFConstants.NAME_EQUALS + processInstanceName + "'";

		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstanceByName, filter: " + filter);

		String sortCriteria = WFConstants.NAME_ASC;
		Integer threshold = getProcessInstancesThreshold(paramsMap);

		String processInstanceId = null;

		try
		{

			ProcessInstance[] instances =
				getExecutionService().queryProcessInstances(
					filter,
					sortCriteria,
					threshold);
			Logger.debug(
				LOGGER_CONTEXT,
				"getProcessInstanceByName, performed queryProcessInstances, instances: "
					+ instances);

			if ((instances == null) || (instances.length == 0))
			{
				return null;
			}

			//Since there is only one instance with that name, it is in the
			//first returned place in the array.
			processInstanceId = instances[0].persistentOid();

		}
		catch (FmcException fex)
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"getProcessInstanceByName, fmcException was thrown, rc: "
					+ fex.rc);

			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getProcessInstanceByName(processInstanceName, paramsMap);
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstanceByName, processInstanceId: "
				+ processInstanceId);

		return getProcessInstance(processInstanceId, paramsMap);

	}

	/**
	 * Allows retrieval of multiple WFProcessInstance from the Workflow.
	 * @param pagingService - Contains IDs of the WFProcessInstances to be retrieved.
	 * @param paramsMap
	 * @return WFProcessInstanceList - the retrieved WFProcessInstances according 
	 * to the supplied processInstanceIds.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstanceList getProcessInstances(
		IDPagingService pagingService,
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{
		Logger.debug(LOGGER_CONTEXT, "getProcessInstances started");

		if (pagingService == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PAGING_SERVICE_IS_NULL,
				new Message(
					WFExceptionMessages.PAGING_SERVICE_IS_NULL,
					Message.SEVERITY_ERROR));

		}
		ArrayList processInstanceIds = null;
		try
		{
			processInstanceIds = pagingService.getSubId();
		}
		catch (GeneralException e)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.PAGING_SERVICE_EXCEPTION,
				new Message(
					WFExceptionMessages.PAGING_SERVICE_EXCEPTION,
					Message.SEVERITY_ERROR));
		}

		int size = processInstanceIds.size();
		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstances, size of processInstanceIds: " + size);

		WFProcessInstance[] instances = new WFProcessInstance[size];

		for (int i = 0; i < size; i++)
		{
			instances[i] =
				getProcessInstance(
					(String) processInstanceIds.get(i),
					paramsMap);
		}

		if (instances == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.PAGING_SERVICE_IS_NULL,
				new Message(
					WFExceptionMessages.PAGING_SERVICE_IS_NULL,
					Message.SEVERITY_ERROR));

		}

		WFProcessInstanceList list = new WFProcessInstanceList(instances);
		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstances, created new WFProcessInstanceList");

		if (list == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.RETRIEVED_WORKLIST_NULL,
				new Message(
					WFExceptionMessages.RETRIEVED_WORKLIST_NULL,
					Message.SEVERITY_ERROR));
		}

		return list;
	}

	/**
	 * Allows retrieval of single WFProcessInstance from the Workflow.
	 * @param processInstanceID - ID of the WFProcessInstance to be retrieved.
	 * @param paramsMap
	 * @return WFProcessInstance - the retrieved WFProcessInstance according 
	 * to the supplied processInstanceID.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstance getProcessInstance(
		String processInstanceID,
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstance started, received processInstanceId: "
				+ processInstanceID
				+ ", received parametersMap: "
				+ paramsMap);

		if (processInstanceID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		if (paramsMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstance, received parametersMap: "
				+ paramsMap.getAllParameters());

		//Names of parameters to be retrieved from the container
		String[] inContainerElementNames =
			getInContainerElementNames(paramsMap);
		String[] outContainerElementNames =
			getOutContainerElementNames(paramsMap);
		String[] globalContainerElementNames =
			getGlobalContainerElementNames(paramsMap);

		Integer threshold = getWorkItemsThreshold(paramsMap);

		ExecutionService wfService = getExecutionService();

		//default option
		boolean retrieveNotifications = getRetrieveNotifications(paramsMap);
		boolean getExpirationTime = getExpirationTime(paramsMap);
		boolean useManager = getUseManager(paramsMap);

		ProcessInstance processInstance = null;

		try
		{

			processInstance =
				wfService.persistentProcessInstance(processInstanceID);
			if (!processInstance.isComplete())
			{
				processInstance.refresh();
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getProcessInstance(processInstanceID, paramsMap);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessInstance, finished handling parameters");

		//The default value is not to retrieve any notifications.
		return createWFProcessInstance(
			processInstance,
			threshold,
			WFConstants.SORT_CREATION_TIME_ASC,
			inContainerElementNames,
			outContainerElementNames,
			globalContainerElementNames,
			wfService,
			retrieveNotifications,
			getExpirationTime,
			useManager);

	}

	/**
	 * This method returns a WorkItem to the virtual user to whom
	 * it belonged before the current user (or should belong).
	 * 
	 * 0. get the WorkItem.
	 * 1. get the WorkItem input container.
	 * 2. get the role and organization from the input container.
	 * 3. ensure that the WorkItem is not checked out.
	 * 4. attach the role and organization - the virtual user's name is 
	 * 	  the concatenation of them.
	 * 5. transfer the WorkItem to the virtual user.
	 * 
	 * Note: the virtual user must be predefined in advance according to this
	 * convention(concatenation of role and organization).
	 * 
	 * The WorkItem should not be checked out. If it is checked out, a
	 * WorkFlowLogicException will be thrown.
	 * 
	 * As the WorkItem is moved to the general queue, which the user is 
	 * allowed to see, the WorkItem will still be visible in the user's 
	 * working queue. 
	 * If the user does not want to see such WorkItems anymore, he should 
	 * choose to see only personal tasks.
	 * 
	 * @param workItemID - ID of the WorkItem to be transferred.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 * @see com.ness.fw.workflow.WorkFlowService#returnWorkItemToGeneralQueue(
	 * String)
	 */
	public void returnWorkItemToGeneralQueue(String workItemID)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"returnWorkItemToGeneralQueue started, received workItemID: "
				+ workItemID);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		WorkItem item = null;
		String itemOwner = null;

		try
		{

			//retrieve the WorkItem.
			item = getExecutionService().persistentWorkItem(workItemID);
			if (!item.isComplete())
			{
				item.refresh();
			}

			/*
			* Ensure that the WorkItem is owned by the current user 
			* (to prevent case that the user tries to transfer a WorkItem 
			* he isn't permitted to transfer).
			*/
			itemOwner = item.owner();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}
		if (!(itemOwner.equals(executionService.getUserId())))
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages
					.CANNOT_RETURN_WORK_ITEM_AS_IT_IS_NOT_OWNED_BY_YOU,
				new Message(
					WFExceptionMessages
						.CANNOT_RETURN_WORK_ITEM_AS_IT_IS_NOT_OWNED_BY_YOU,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"returnWorkItemToGeneralQueue, item owner: " + itemOwner);

		//retrieve the container
		String role = null;
		String organization = null;
		ReadOnlyContainer inContainer = null;

		try
		{

			inContainer = item.inContainer();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}

		if (inContainer == null)
		{
			throw new WorkFlowException(WFExceptionMessages.CONTAINER_IS_NULL);
		}

		//retrieve the fields

		/*
		 * Have to check if the task is a team one.
		 * This field means which people are permitted to perform
		 * the WorkItem.
		 */

		try
		{
			if (inContainer.getString(WFConstants.PEOPLE) != null)
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.WORK_ITEM_IS_NOT_A_TEAM_TASK,
					new Message(
						WFExceptionMessages.WORK_ITEM_IS_NOT_A_TEAM_TASK,
						Message.SEVERITY_ERROR));
			}

		}
		catch (FmcException fex)
		{

			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ WFConstants.PEOPLE);
			}
			else if (fex.rc != FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				throw new WorkFlowException(fex);
			}
			//else not needed - regard this case like "people==null".
		}
		/*
		 * Role and Organization are used to generate the name of
		 * the virtual user.
		 */

		/*
		 * In case the role or organization are not set, replace
		 * the FmcException with the relevant WorkFlowException.
		 */
		try
		{
			role = inContainer.getString(WFConstants.MEMBER_OF_ROLES);

		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ WFConstants.MEMBER_OF_ROLES);
			}
			else if (fex.rc != FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				throw new WorkFlowException(fex);
			}
			else
			{
				throw new WorkFlowException(
					WFExceptionMessages.ROLE_CONTAINER_PARAMETER_IS_NULL);
			}
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"returnWorkItemToGeneralQueue, role in the container: " + role);

		try
		{
			organization =
				inContainer.getString(WFConstants.ACTIVITY_ORGANIZATION);

		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ WFConstants.ACTIVITY_ORGANIZATION);
			}
			else if (fex.rc != FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				throw new WorkFlowException(fex);
			}
			else
			{
				throw new WorkFlowException(
					WFExceptionMessages
						.ORGANIZATION_CONTAINER_PARAMETER_IS_NULL);
			}
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"returnWorkItemToGeneralQueue, organization in the container: "
				+ organization);

		//check the fields.
		if (role == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.ROLE_CONTAINER_PARAMETER_IS_NULL);
		}
		if (organization == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.ORGANIZATION_CONTAINER_PARAMETER_IS_NULL);
		}

		//ensure that the WorkItem is not checked out.
		try
		{
			com.ibm.workflow.api.ItemPackage.ExecutionState state =
				item.state();
			if (state == null)
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}
			if (state.value()
				== com.ibm.workflow.api.ItemPackage.ExecutionState._CHECKED_OUT)
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.CANNOT_TRANSFER_CHECKED_OUT_ITEM,
					new Message(
						WFExceptionMessages.CANNOT_TRANSFER_CHECKED_OUT_ITEM,
						Message.SEVERITY_ERROR));
			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}

		String generalQueue = null;

		//transfer to virtual user
		try
		{
			generalQueue = getVirtualUserId(role, organization);
		}
		catch (LdapException lex)
		{
			throw new WorkFlowException(lex);
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"returnWorkItemToGeneralQueue, name of virtual user: "
				+ generalQueue);

		//in case the virtual user Id is null, the general queue is undefined.
		if (generalQueue == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.GENERAL_QUEUE_UNDEFINED,
				new Message(
					WFExceptionMessages.GENERAL_QUEUE_UNDEFINED,
					Message.SEVERITY_ERROR));
		}

		try
		{
			item.transfer(generalQueue.toUpperCase());
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
		}
		Logger.debug(LOGGER_CONTEXT, "returnWorkItemToGeneralQueue completed");

	}

	/** 
	 * Routing of the WorkItem.
	 * Note: this method is for the routing itself.
	 * It does not handle the look-up for candidates to the routing process.
	 * @param workItemID ID of the WorkItem to be routed.
	 * @param targetUserID ID of the target user that will receive the WorkItem.
	 * @param trackWorkItem If set to true, the router name will be set in the 
	 * description.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public void routeWorkItem(
		String workItemID,
		String targetUserID,
		boolean trackWorkItem)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"routeWorkItem started, workItemID: "
				+ workItemID
				+ "targetUserID: "
				+ targetUserID
				+ "trackWorkItem: "
				+ trackWorkItem);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		if (targetUserID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"routeWorkItem, UserID: " + executionService.getUserId());

		WFExecutionService managerExecutionService = null;
		WorkItem workItemForRouting = null;

		//Changed 5.4.05 to resolve authorization problem
		//when transferring the workItem.
		managerExecutionService = getManagerExecutionService();
		try
		{

			workItemForRouting =
				managerExecutionService
					.getExecutionService()
					.persistentWorkItem(
					workItemID);
		}

		catch (FmcException fex)
		{
			handleFmcException(fex, true);
			putManagerExecutionService(managerExecutionService);
			managerExecutionService = null;
			//will be reachable only in case of time-out
			routeWorkItem(workItemID, targetUserID, trackWorkItem);
		}
		finally
		{
			if (managerExecutionService != null)
			{
				putManagerExecutionService(managerExecutionService);
				managerExecutionService = null;
			}
		}

		try
		{
			//Avoid routing in case the owner already have the workItem.
			if (targetUserID.equals(workItemForRouting.owner()))
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.USER_ALREADY_HAVE_THIS_WORK_ITEM,
					new Message(
						WFExceptionMessages.USER_ALREADY_HAVE_THIS_WORK_ITEM,
						Message.SEVERITY_ERROR));

			}

			//Check if the item's state allows transfer!
			String processInstanceId =
				workItemForRouting.persistentOidOfProcessInstance();
			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItem, processInstanceId: " + processInstanceId);

			if (!doesActivityStateAllowTransfer(processInstanceId,
				workItemForRouting))
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.WORK_ITEM_STATE_DOES_NOT_ALLOW_ROUTING,
					new Message(
						WFExceptionMessages
							.WORK_ITEM_STATE_DOES_NOT_ALLOW_ROUTING,
						Message.SEVERITY_ERROR));
			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex, true);
			//reachable only in case of time-out
			routeWorkItem(workItemID, targetUserID, trackWorkItem);
		}
		String currentUserId = null;

		try
		{
			currentUserId = getExecutionService().userID();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//reachable only in case of time-out
			routeWorkItem(workItemID, targetUserID, trackWorkItem);

		}

		if (trackWorkItem)
		{

			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItem, entered trackWorkItem section");
			String workItemOwner = null;
			String trackWorkItemKey =
				SystemResources.getInstance().getProperty(
					WFConstants.DESCRIPTION_TRACK_WORK_ITEM_KEY);
			String updatedDescription = null;

			try
			{

				/*
				 * Check WorkItem ownership is done on WorkItemForRouting,
				 * as the checking might not be allowed for workItemForDescription.
				 */
				workItemOwner = workItemForRouting.owner();
				Logger.debug(
					LOGGER_CONTEXT,
					"routeWorkItem, workItemOwner: " + workItemOwner);

				Logger.debug(
					LOGGER_CONTEXT,
					"routeWorkItem, currentUserId: " + currentUserId);

				updatedDescription =
					setElementInDescriptionString(
						workItemForRouting.description(),
						trackWorkItemKey,
						currentUserId);

			}
			catch (FmcException fex)
			{
				handleFmcException(fex);
				//reachable only in case of time-out
				routeWorkItem(workItemID, targetUserID, trackWorkItem);
			}

			/*
			 * If I'm not the owner, have to transfer the WorkItem to me in
			 * order to allow me to set the description.
			 */
			updateDescriptionWithoutReturningToOwner(
				workItemForRouting,
				workItemID,
				workItemOwner,
				currentUserId,
				updatedDescription);

		}

		/*
		 * In case I already transferred the workItem during setting of trackWorkItem,
		 * and the targetUserId is me (the currentUser),
		 * should not attempt to transfer the workItem from me to me.
		 */
		if ((!(currentUserId.equals(targetUserID))) || !trackWorkItem)
		{
			transferWorkItem(workItemForRouting, targetUserID);

		}
		Logger.debug(LOGGER_CONTEXT, "routeWorkItem completed");

	}

	/**
	 * Assistance method of routeWorkItem.
	 * Will be called after possible user's response to message
	 * if to do something with checked-out WorkItem.
	 * @param item the item to be transferred.
	 * @param targetUserID the target userID.
	 * @throws WorkFlowException
	 */
	private void transferWorkItem(Item item, String targetUserID)
		throws WorkFlowException
	{
		Logger.debug(LOGGER_CONTEXT, "started transferWorkItem");

		try
		{
			item.transfer(targetUserID);
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//reachable only in case of time-out
			transferWorkItem(item, targetUserID);
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"item was transferred to user: " + targetUserID);

	}

	/**
	 * In case the Activity state is one of - 
	 * Ready, InError, Executed, Suspending, Suspended, or Terminated,
	 * And the relevant Process state is one of -
	 * Running, Suspending, Suspended, or Terminating,
	 * than the Activity can be transferred.
	 * Note: As workItems in our project are implemented only by a program,
	 * workItems in Running state can't be transferred!
	 * @param processID - ID of the Process
	 * @param item - the transferred item
	 * @return boolean - true if the Activity's state allows transfer,
	 * 	false otherwise
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private boolean doesActivityStateAllowTransfer(
		String processID,
		WorkItem item)
		throws WorkFlowException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"doesActivityStateAllowTransfer started, processId: "
				+ processID
				+ ", workItem is: "
				+ item);

		if (processID == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		if (item == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.WORK_ITEM_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		int itemState = -1;
		int processState = -1;

		try
		{

			com.ibm.workflow.api.ItemPackage.ExecutionState state =
				item.state();
			if (state == null)
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}
			itemState = state.value();

			com.ibm.workflow.api.ProcessInstancePackage.ExecutionState pState =
				item.processInstanceState();
			if (pState == null)
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}
			processState = pState.value();

		}
		catch (FmcException fex)
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"FmcException thrown in doesActivityStateAllowTransfer");
			handleFmcException(fex, true);
			//will be reachable only in case of time-out
			return doesActivityStateAllowTransfer(processID, item);
		}
		if ((itemState
			== com.ibm.workflow.api.ItemPackage.ExecutionState._READY)
			|| (itemState
				== com.ibm.workflow.api.ItemPackage.ExecutionState._IN_ERROR)
			|| (itemState
				== com.ibm.workflow.api.ItemPackage.ExecutionState._EXECUTED)
			|| (itemState
				== com.ibm.workflow.api.ItemPackage.ExecutionState._SUSPENDING)
			|| (itemState
				== com.ibm.workflow.api.ItemPackage.ExecutionState._SUSPENDED))
		{
			if ((processState
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._RUNNING)
				|| (processState
					== com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._SUSPENDING)
				|| (processState
					== com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._SUSPENDED)
				|| (processState
					== com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._TERMINATING))
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"doesActivityStateAllowTransfer, return true");

				return true;
			}
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"doesActivityStateAllowTransfer, return false");
		return false;

	}

	/**
	 * Receive a container and fill the hash map with its elements 
	 * @param containerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param container the received container.
	 * @param containerType - Either input/output/global container
	 * @return the filled WorkFlowServiceParameterMap.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameterMap fillMap(
		String[] containerElementNames,
		Container container,
		int containerType)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{
		/*
		 * Don't throw Exception here. not all calling methods
		 * require that the container/map will not be null.
		 */
		Logger.debug(LOGGER_CONTEXT, "started method fillMap");

		if (container == null)
		{
			Logger.debug(LOGGER_CONTEXT, "fillMap, received container is null");

			return null;
		}
		if (containerType
			== WFConstants.CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER)
		{
			return fillMapFromInputContainer(container, containerElementNames);
		}
		else if (
			containerType
				== WFConstants.CONTAINER_TYPE_ACTIVITY_OUTPUT_CONTAINER)
		{
			return fillMapFromOutputContainer(container, containerElementNames);
		}
		else if (containerType == WFConstants.CONTAINER_TYPE_GLOBAL_CONTAINER)
		{
			return fillMapFromGlobalContainer(container, containerElementNames);
		}
		return null;
	}

	/**
	 * Create Map from input container
	 * @param container inputContainer
	 * @param containerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @return WorkFlowServiceParameterMap the created ParameterMap
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameterMap fillMapFromInputContainer(
		Container container,
		String[] containerElementNames)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started fillMapFromInputContainer");

		WorkFlowServiceParameterMap parametersMap =
			new WorkFlowServiceParameterMap();

		parametersMap =
			fillMapFromContainerElements(
				containerElementNames,
				container,
				WFConstants.CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER,
				WFConstants.ARGUMENT_TYPE_INPUT);
		Logger.debug(
			LOGGER_CONTEXT,
			"fillMapFromInputContainer, generated parametersMap values: "
				+ parametersMap.getAllParameters());

		return parametersMap;
	}

	/**
	 * Assistance method used to generate a WorkFlowServiceParameterMap
	 * from the given container and its elements.
	 * @param containerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param container
	 * @param containerType - type of the container (ReadOnly, ReadWrite, Global)
	 * @param argumentType - type of the argument (input, output, input-output)
	 * @return WorkFlowServiceParameterMap
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameterMap fillMapFromContainerElements(
		String[] containerElementNames,
		Container container,
		int containerType,
		int argumentType)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		WorkFlowServiceParameterMap parametersMap =
			new WorkFlowServiceParameterMap();

		int count;
		if (containerElementNames == null)
		{
			count = 0;
		}
		else
		{
			count = containerElementNames.length;
		}

		try
		{

			for (int index = 0; index < count; index++)
			{

				/*
				 * Next line throws WorkFlowException in case there is no such 
				 * element in the container.
				 */
				ContainerElement element = null;
				try
				{
					element =
						container.getElement(containerElementNames[index]);

				}
				catch (FmcException fex)
				{
					if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
					{
						continue;
						//throw new WorkFlowLogicException(
						//	WFExceptionMessages.CONTAINER_ELEMENT_NAME_NOT_FOUND,
						//	new Message(
						//		WFExceptionMessages.CONTAINER_ELEMENT_NAME_NOT_FOUND,
						//		Message.SEVERITY_ERROR));
					}
					else
					{
						throw new WorkFlowException(fex);
					}
				}
				/* handling complex elements is not supported, according to
				 * talk with Leonid, 27.7.04.
				 */
				if (element.isStruct())
				{
					throw new WorkFlowException(
						WFExceptionMessages
							.COMPLEX_CONTAINER_ELEMENTS_NOT_SUPPORTED);
				}
				else if (element.type().equals(WFConstants.BINARY))
				{
					/*
					 * In case the container has binary elements, they represent
					 * a WorkFlowServiceParameterMap - so put its contents in the
					 * working WorkFlowServiceParameterMap. (In case 
					 * setMapFromContainerBuffer() returns null, nothing is put
					 * into the WorkFlowServiceParametersMap).
					 */
					//					parametersMap.putAll(setMapFromContainerBuffer(container));
					//Handle the binary field (not covered in the container element names).
					parametersMap.putAll(setMapFromContainerBuffer(container));

				}
				else
				{
					parametersMap.add(
						handleContainerElement(
							element,
							containerElementNames[index],
							containerType,
							argumentType));
				}
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return fillMapFromContainerElements(
				containerElementNames,
				container,
				containerType,
				argumentType);
		}

		return parametersMap;

	}

	/**
	 * This method generates the appropriate WorkFlowServiceParameter,
	 * according to the ContainerElement type.
	 * In case the ContainerElement value is not set, the value of the
	 * WorkFlowServiceParameter is set to NULL.
	 * @param ContainerElement the element to be extracted
	 * @param elementName name of the extracted element.
	 * @param containerType - InputContainer/OutputContainer/GlobalContainer
	 * @param argumentType - input/output/input-output
	 * @return WorkFlowServiceParameter
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	private WorkFlowServiceParameter handleContainerElement(
		ContainerElement element,
		String elementName,
		int containerType,
		int argumentType)
		throws WorkFlowException, NullParametersException
	{

		WorkFlowServiceParameter parameter = null;

		try
		{

			if (element.isEmpty())
			{
				parameter =
					new WorkFlowServiceParameter(
						element.fullName(),
						containerType,
						WFConstants.OBJECT_TYPE_STRING,
						argumentType,
						null);
			}
			else
			{ //Start of big else

				String elemntType = element.type();

				/*
				 * The element is not complex (can't be struct as this 
				 * possibility was eliminated in the calling method).
				 */
				if (elemntType.equals(WFConstants.STRING))
				{

					parameter =
						createStringParameter(
							element,
							elementName,
							containerType,
							argumentType);

				}
				else if (elemntType.equals(WFConstants.LONG))
				{

					parameter =
						createLongParameter(
							element,
							elementName,
							containerType,
							argumentType);

				}
				else if (elemntType.equals(WFConstants.DOUBLE))
				{

					parameter =
						createDoubleParameter(
							element,
							elementName,
							containerType,
							argumentType);

				}
				else if (elemntType.equals(WFConstants.BINARY))
				{

					/*
					 * Do nothing here - the binary element of the container
					 * is decomposed in another method, setMapFromContainerBuffer()
					 */
				}
			} //End of Big Else

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return handleContainerElement(
				element,
				elementName,
				containerType,
				argumentType);
		}
		return parameter;

	}

	/**
	 * Assistance meyhod of handleContainerElement().
	 * This method creates a WorkFlowServiceParameter that represents 
	 * a String parameter.
	 * @param element - the handled element.
	 * @param elementName name of the extracted element.
	 * @param containerType
	 * @param argumentType
	 * @return WorkFlowServiceParameter - the generated parameter.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameter createStringParameter(
		ContainerElement element,
		String elementName,
		int containerType,
		int argumentType)
		throws FmcException, WorkFlowException, NullParametersException
	{

		String parameterValue = null;
		try
		{
			parameterValue = element.getString();
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ elementName);
			}
			else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				parameterValue = null;
			}
			else
			{
				throw new WorkFlowException(fex);
			}
		}
		return new WorkFlowServiceParameter(
			element.fullName(),
			containerType,
			WFConstants.OBJECT_TYPE_STRING,
			argumentType,
			parameterValue);

	}

	/**
	 * Assist meyhod of handleContainerElement().
	 * This method creates a WorkFlowServiceParameter that represents 
	 * a Long parameter.
	 * @param element
	 * @param elementName name of the extracted element.
	 * @param containerType
	 * @param argumentType
	 * @return WorkFlowServiceParameter - the generated parameter.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameter createLongParameter(
		ContainerElement element,
		String elementName,
		int containerType,
		int argumentType)
		throws FmcException, WorkFlowException, NullParametersException
	{

		String parameterValue = null;

		try
		{
			parameterValue = Integer.toString(element.getLong());
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ elementName);
			}
			else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				parameterValue = null;
			}
			else
			{
				throw new WorkFlowException(fex);
			}
		}
		return new WorkFlowServiceParameter(
			element.fullName(),
			containerType,
			WFConstants.OBJECT_TYPE_LONG,
			argumentType,
			parameterValue);
	}

	/**
	 * Assist meyhod of handleContainerElement().
	 * This method creates a WorkFlowServiceParameter that represents 
	 * a Double parameter.
	 * @param element
	 * @param elementName name of the extracted element.
	 * @param containerType
	 * @param argumentType
	 * @return WorkFlowServiceParameter - the generated parameter.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameter createDoubleParameter(
		ContainerElement element,
		String elementName,
		int containerType,
		int argumentType)
		throws FmcException, WorkFlowException, NullParametersException
	{

		String parameterValue = null;

		try
		{
			parameterValue = Double.toString(element.getDouble());
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ elementName);
			}
			else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				parameterValue = null;
			}
			else
			{
				throw new WorkFlowException(fex);
			}
		}
		return new WorkFlowServiceParameter(
			element.fullName(),
			containerType,
			WFConstants.OBJECT_TYPE_DOUBLE,
			argumentType,
			parameterValue);
	}

	/**
	 * Creates map from output container.
	 * @param container Output container
	 * @param containerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @return WorkFlowServiceParameterMap the created WorkFlowServiceParameterMap
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameterMap fillMapFromOutputContainer(
		Container container,
		String[] containerElementNames)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started fillMapFromOutputContainer");

		WorkFlowServiceParameterMap parametersMap =
			new WorkFlowServiceParameterMap();

		parametersMap =
			fillMapFromContainerElements(
				containerElementNames,
				container,
				WFConstants.CONTAINER_TYPE_ACTIVITY_OUTPUT_CONTAINER,
				WFConstants.ARGUMENT_TYPE_INPUT_OUTPUT);
		Logger.debug(
			LOGGER_CONTEXT,
			"fillMapFromOutputContainer, generated parametersMap values: "
				+ parametersMap.getAllParameters());

		return parametersMap;
	}

	/**
	 * Creates map from global container.
	 * @param container the global container.
	 * @param containerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @return WorkFlowServiceParameterMap the created 
	 * WorkFlowServiceParameterMap
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameterMap fillMapFromGlobalContainer(
		Container container,
		String[] containerElementNames)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started fillMapFromGlobalContainer");

		WorkFlowServiceParameterMap parametersMap =
			new WorkFlowServiceParameterMap();

		parametersMap =
			fillMapFromContainerElements(
				containerElementNames,
				container,
				WFConstants.CONTAINER_TYPE_GLOBAL_CONTAINER,
				WFConstants.ARGUMENT_TYPE_INPUT);
		Logger.debug(
			LOGGER_CONTEXT,
			"fillMapFromGlobalContainerElements: "
				+ parametersMap.getAllParameters());

		return parametersMap;
	}

	/**
	 * This method is used when creating the WFProcessInstance.
	 * Extract the processContext from the Workflow, and keep its contents
	 * in a new WorkFlowServiceParameterMap
	 * @param ProcessInstance
	 * @return WorkFlowServiceParameter
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameter getProcessContext(ProcessInstance instance)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		if (instance == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.PROCESS_INSTANCE_IS_NULL);
		}
		String processContext = null;

		try
		{

			processContext = instance.processContext();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getProcessContext(instance);
		}
		WorkFlowServiceParameter parameter =
			new WorkFlowServiceParameter(
				WFConstants.PROCESS_CONTEXT,
				WFConstants.DATA_TYPE_PROCESS_CONTEXT,
				WFConstants.OBJECT_TYPE_STRING,
				WFConstants.ARGUMENT_TYPE_INPUT_OUTPUT,
				processContext);

		return parameter;

	}

	/**
	 * For a given WFProcessInstance, find the last activity performed in it.
	 * This method will return value other than null, only if the
	 * WFProcessInstance is in state FINISHED or FORCE_FINISHED.
	 * @param processInstanceName - name of the WFProcessInstance
	 * @return String - ID of the last performed activity.
	 * @throws WorkFlowException
	 */
	public String getLastPerformedActivity(String processInstanceName)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started getLastPerformedActivity, received processInstanceName: "
				+ processInstanceName);

		if (processInstanceName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		StringBuffer filter = new StringBuffer(128);
		filter.append(WFConstants.FILTER_STATE_FOR_LAST_HANDLER);
		filter.append(WFConstants.FILTER_PROCESS_NAME_PREFIX);
		filter.append(processInstanceName);
		filter.append(WFConstants.RIGHT_PHARENTESIS);

		try
		{

			/*
			 * We need just the first WFWorkItem in the sorted array, 
			 * therefore the threshold was set to 1.
			 */
			WorkItem[] workItems =
				getExecutionService().queryWorkItems(
					filter.toString(),
					WFConstants.SORT_LAST_MODIFICATION_TIME_DESC,
					Integer.valueOf("1"));

			/*
			 * The process has no WorkItems - No information about the 
			 * lastHandler can be found.
			 */
			if ((workItems == null) || (workItems.length == 0))
			{
				return null;
			}
			return workItems[0].persistentOid();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getLastPerformedActivity(processInstanceName);
		}
	}

	/**
	 * checking out the selected activity.
	 * The algorithm:
	 * 1. if the item's holder is not the current user:
	 *    a. if the activity is already checked out, not by one of your
	 *       sub-workers, throw exception, as you can't check out this item.
	 *    b. if the activity is checked out by one of your sub-workers,
	 *       cancel the check out, transfer the activity to you and check out.
	 *    c. if the activity is not checked out, transfer the activity to you
	 *       and check out.
	 * 2. if the activity is hold by you and already checked out, 
	 * 		throw exception.
	 * 3. if the activity is hold by you and not checked out, perform check out.
	 * @param WorkItemID the WFWorkItem to be checked out.
	 * @param paramsMap Wraps the containerElementNames to be retrieved.
	 * @return WorkFlowServiceParameterMap - Wraps the container of the 
	 * checked out activity (can be used for check-in).
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WorkFlowServiceParameterMap checkOut(
		String workItemID,
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"CheckOut started, received workItemId: " + workItemID);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		//Default value. If customerId is not supported,
		// no attempt to route tasks of this customer.
		String customerIDParameter = null;

		if (paramsMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"CheckOut, received parametersMap values: "
				+ paramsMap.getAllParameters());

		//Names of parameters to be retrieved from the container
		String[] inContainerElementNames =
			getInContainerElementNames(paramsMap);

		WorkFlowServiceParameter parameter =
			paramsMap.get(WFConstants.CUSTOMER_ID_PARAMETER);
		if (parameter != null)
		{
			customerIDParameter = parameter.getStringValue();
		}

		/*
		 *      //Commented as the customerId is now optional.
				if (customerIDParameter == null)
				{
					throw new NullParametersException(
						WFExceptionMessages.MISSING_GLOBAL_CONTAINER_ELEMENT,
						new Message(
							WFExceptionMessages.MISSING_GLOBAL_CONTAINER_ELEMENT,
							Message.SEVERITY_ERROR));
				}
		*/
		return returnCheckOutParameterMap(
			inContainerElementNames,
			checkOutLogic(workItemID, customerIDParameter));

	}

	/**
	 * This method is required to extract the common code of checkOut() and
	 * checkOutAlreadyCheckedOutItem().
	 * @param inContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param container - ReadOnlyContainer returned by checkOut().
	 * @return WorkFlowServiceParameterMap
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameterMap returnCheckOutParameterMap(
		String[] inContainerElementNames,
		ReadOnlyContainer container)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{
		WorkFlowServiceParameterMap parametersMap =
			this.fillMap(
				inContainerElementNames,
				container,
				WFConstants.CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER);

		Logger.debug(
			LOGGER_CONTEXT,
			"returnCheckOutParameterMap, received parametersMap: "
				+ parametersMap.getAllParameters());

		return parametersMap;
	}

	/**
	 * checking out the selected activity - performs the hidden logic
	 * around actual checking-out.
	 * The algorithm:
	 * 1. if the item's holder is not the current user:
	 *    a. if the activity is already checked out, not by one of your
	 *       sub-workers, throw exception, as you can't check out this item.
	 *    b. if the activity is checked out by one of your sub-workers,
	 *       cancel the check out, transfer the activity to you and check out.
	 *    c. if the activity is not checked out, transfer the activity to you
	 *       and check out.
	 * 2. if the activity is hold by you and already checked out, throw exception.
	 * 3. if the activity is hold by you and not checked out, perform check out.
	 * @param WorkItemID the WFWorkItem to be checked out.
	 * @param customerIDParameter Is used for retrieval of the customerID from the
	 * process Template.
	 * @return ReadOnlyContainer container of the checked out activity
	 * (can be used for check-in)
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private ReadOnlyContainer checkOutLogic(
		String workItemID,
		String customerIDParameter)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started checkOutLogic, received workItemId: "
				+ workItemID
				+ ", received customerIdParameter: "
				+ customerIDParameter);

		ExecutionService wfService = getExecutionService();
		ReadOnlyContainer container = null;
		WorkItem item = null;
		try
		{

			item = wfService.persistentWorkItem(workItemID);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return checkOutLogic(workItemID, customerIDParameter);
		}

		String currentUserID = executionService.getUserId();

		if (currentUserID == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.USER_ID_IS_NULL,
				new Message(
					WFExceptionMessages.USER_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"checkOutLogic, currentUserId is: " + currentUserID);

		String itemOwner = null;
		try
		{

			itemOwner = item.owner();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return checkOutLogic(workItemID, customerIDParameter);
		}
		//		if (!item.isComplete())
		if (itemOwner.equals(""))
		{

			try
			{

				Logger.debug(LOGGER_CONTEXT, "ItemOwner was empty string");

				item.refresh();
				itemOwner = item.owner();
				Logger.debug(
					LOGGER_CONTEXT,
					"ItemOwner after refresh: " + itemOwner);

			}
			catch (FmcException fex)
			{
				handleFmcException(fex);
				//will be reachable only in case of time-out
				return checkOutLogic(workItemID, customerIDParameter);
			}

		}
		Logger.debug(
			LOGGER_CONTEXT,
			"checkOutLogic, itemOwner is: " + itemOwner);

		if ((!(itemOwner.equals(currentUserID))) && (!(itemOwner.equals(""))))
		{
			/*
			 * This call also includes an assertion about the WorkItem's
			 * state - to avoid redundant work.
			 */
			Logger.debug(
				LOGGER_CONTEXT,
				"calling handleItemNotOwnedByThisUser, itemOwner is: "
					+ itemOwner);
			// This method also transfers the WorkItem to the current User.
			handleItemNotOwnedByThisUser(item, currentUserID, wfService);
		}

		// From here - the Item is owned by this user. 
		com.ibm.workflow.api.ItemPackage.ExecutionState itemState = null;
		try
		{
			itemState = item.state();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return checkOutLogic(workItemID, customerIDParameter);
		}
		if (itemState == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.EXECUTION_STATE_IS_NULL);
		}
		int itemStateValue = itemState.value();

		if (itemStateValue
			== com.ibm.workflow.api.ItemPackage.ExecutionState._CHECKED_OUT)
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"checkOutLogic, item already checked by this user");

			//Eliminated exception as requested by the application team.
			//No multiple check-out is performed here!
			try
			{
				container = item.inContainer();
			}
			catch (FmcException fex)
			{
				handleFmcException(fex);
				//will be reachable only in case of time-out
				return checkOutLogic(workItemID, customerIDParameter);
			}

		}
		else
		{
			/*
			 * No point in handling a WorkItem that can't be checked out.
			 * If the WorkItem's state is not READY/RUNNING/CHECKED_OUT,
			 * throw an exception.
			 */
			assertItemStateAllowsCheckingOut(item, itemStateValue, wfService);

			Logger.debug(
				LOGGER_CONTEXT,
				"checkOutLogic, immediately before calling item.checkOut()");
			try
			{
				container = item.checkOut();
				Logger.debug(
					LOGGER_CONTEXT,
					"checkOutLogic, immediately after calling item.checkOut()");

			}
			catch (FmcException fex)
			{
				handleFmcException(fex);
				//will be reachable only in case of time-out
				return checkOutLogic(workItemID, customerIDParameter);
			}
		}

		/*Locate all other WIs for the customer, held by a virtual user, 
		 * that the user can handle, and transfer them to him.
		 */
		if (customerIDParameter != null)
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"checkOutLogic, customerIDParameter is not null, and is: "
					+ customerIDParameter);
			routeWorkItemsOfThisCustomer(
				item,
				currentUserID,
				customerIDParameter);

		}

		return container;

	}

	/**
	 * Assistance method of checkout().
	 * This method was written to fulfill requirement for transferring all
	 * WorkItems for the same customer to the person which checked out the WorkItem.
	 * @param item The checked-out WorkItem.
	 * @param targetUserID Name of the user who checked out the WorkItem.
	 * @param customerIDParameter Is used for retrieval of the customerID from the
	 * process Template.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private void routeWorkItemsOfThisCustomer(
		WorkItem item,
		String targetUserID,
		String customerIDParameter)
		throws WorkFlowException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"routeWorkItemsOfThisCustomer started, targetUserId: "
				+ targetUserID
				+ ", customerIdParameter: "
				+ customerIDParameter);

		// Retrieve the customerID from the WorkItem's description.
		ProcessInstance instance = null;
		ReadOnlyContainer container = null;

		try
		{
			instance = item.processInstance();
			container = instance.globalContainer();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			routeWorkItemsOfThisCustomer(
				item,
				targetUserID,
				customerIDParameter);

		}
		if (container == null)
		{
			throw new WorkFlowException(WFExceptionMessages.CONTAINER_IS_NULL);
		}

		Integer customerID = null;

		try
		{
			customerID = (new Integer(container.getLong(customerIDParameter)));
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(
					WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
						+ customerIDParameter);
			}
			else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
			{
				//As the value was not set, can't route WorkItems.
				Logger.debug(
					LOGGER_CONTEXT,
					"routeWorkItemsOfThisCustomer, customerID is not set in the container");

				customerID = null;
			}
			else
			{
				throw new WorkFlowException(fex);
			}
		}
		if (customerID == null)
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItemsOfThisCustomer, skipping method as customerID is null");
		}
		else //a valid customerID was supplied - use it.
			{

			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItemsOfThisCustomer, customerID is:  " + customerID);

			// Generate the required filter for getting only WorkItems of this customer.
			String tempFilter =
				this.generateCustomerFilter(customerID, customerIDParameter);

			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItemsOfThisCustomer, tempFilter: " + tempFilter);

			String filter =
				WFConstants.PROCESS_STATE_IN_READY_OR_RUNNING_PREFIX
					+ tempFilter
					+ WFConstants.OWNER_NOT_CURRENT_USER_SUFFIX;

			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItemsOfThisCustomer, finalFilter: " + filter);
			String sortCriteria = WFConstants.STATE_ASC;
			Integer threshold =
				new Integer(
					SystemResources.getInstance().getProperty(
						WFConstants.WORK_ITEMS_THRESHOLD_KEY));
			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItemsOfThisCustomer, threshold taken from properties is:  "
					+ threshold);

			//Only WorkItems for this customer were retrieved.
			WorkItem[] itemsArray = null;

			try
			{

				itemsArray =
					getExecutionService().queryWorkItems(
						filter,
						sortCriteria,
						threshold);
			}
			catch (FmcException fex)
			{
				handleFmcException(fex);
				//will be reachable only in case of time-out
				routeWorkItemsOfThisCustomer(
					item,
					targetUserID,
					customerIDParameter);

			}

			/*
			 * How to eliminate from the itemsArray the just-routed WorkItem?
			 * Answer: the routed WorkItem state is "checked-out", therefore can't route it again.
			 */

			//2. Assert that only virtual tasks are handled.
			Logger.debug(
				LOGGER_CONTEXT,
				"routeWorkItemsOfThisCustomer, before loop on WorkItems");

			for (int i = 0; i < itemsArray.length; i++)
			{

				//Check if the item's state allows transfer.
				/*
				 * The state checking was changed: In case the activity state
				 * does not allow transfer, simply skip this activity.
				 */
				try
				{

					if (doesActivityStateAllowTransfer(itemsArray[i]
						.persistentOidOfProcessInstance(),
						itemsArray[i]))
					{
						Logger.debug(
							LOGGER_CONTEXT,
							"routeWorkItemsOfThisCustomer, before transfering item "
								+ i);

						try
						{
							//route this WorkItem to the user.
							itemsArray[i].transfer(targetUserID);
							Logger.debug(
								LOGGER_CONTEXT,
								"routeWorkItemsOfThisCustomer, item was transferred to the user");

						}
						catch (FmcException fex)
						{
							/*
							 * Errors relavant to the routing are caught:
							 * Not authorized exception and "owner already assigned"
							 * (the owner already has the workItem).
							 */
							//						if ((fex.rc != FmcException.FMC_ERROR_NOT_AUTHORIZED)
							//							&& (fex.rc!= FmcException.FMC_ERROR_OWNER_ALREADY_ASSIGNED))
							if (fex.rc
								!= FmcException.FMC_ERROR_NOT_AUTHORIZED)
							{
								throw fex;
							}
							/*
							 * If we reached here, the thrown exception was a 
							 * "not authorized" exception. In this case, ignore the
							 * current workItem and continue the loop.
							 */
							Logger.debug(
								LOGGER_CONTEXT,
								"routeWorkItemsOfThisCustomer, caught not authorized exception");

						}
					}

				}
				catch (FmcException fex)
				{
					handleFmcException(fex);
					//will be reachable only in case of time-out
					routeWorkItemsOfThisCustomer(
						item,
						targetUserID,
						customerIDParameter);

				}

				//else, skip this WorkItem. It should not be re-routed as no
				//permissions were given (or its state does not allow routing).

			}
		}

	}

	/**
	 * Assistance method to ensure the WorkItem's state allows checking out.
	 * @param item the WorkItem to be checked out.
	 * @param itemStateValue Value of the Item's ExecutionState.
	 * @param wfService The local instance of ExecutionService
	 * @throws WorkFlowLogicException
	 * @throws WorkFlowException
	 */
	private void assertItemStateAllowsCheckingOut(
		WorkItem item,
		int itemStateValue,
		ExecutionService wfService)
		throws WorkFlowLogicException, WorkFlowException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"assertItemStateAllowsCheckingOut started");

		int processState = -1;
		try
		{

			ProcessInstance processInstance =
				wfService.persistentProcessInstance(
					item.persistentOidOfProcessInstance());
			if (!processInstance.isComplete())
			{
				processInstance.refresh();
			}
			com.ibm.workflow.api.ProcessInstancePackage.ExecutionState pState =
				processInstance.state();

			if ((pState == null))
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}

			processState = pState.value();
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			assertItemStateAllowsCheckingOut(item, itemStateValue, wfService);
		}
		if ((itemStateValue
			!= com.ibm.workflow.api.ItemPackage.ExecutionState._READY)
			|| (processState
				!= com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._RUNNING))
		{
			// Can't check out the item as its state doesn't allow it.
			throw new WorkFlowLogicException(
				WFExceptionMessages.ITEM_STATE_DOES_NOT_ALLOW_CHECKING_OUT,
				new Message(
					WFExceptionMessages.ITEM_STATE_DOES_NOT_ALLOW_CHECKING_OUT,
					Message.SEVERITY_ERROR));

		}

	}

	/**
	 * In case the item is not held by the current user:
	 * 1. check state of the item. if it is checked out, and you're
	 * not the manager of the checking out person, throw exception.
	 * 2. if it is checked out by one of your managed workers,
	 * cancel check out and confiscate the work item.
	 * 3. otherwise, transfer the work item to me (no checking is
	 * performed about the role of the user).
	 * @param item The item to be checked out.
	 * @param currentHolder - current person who holds this WorkItem
	 * @param wfService Instance of the local ExecutionService
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private void handleItemNotOwnedByThisUser(
		WorkItem item,
		String currentUserID,
		ExecutionService wfService)
		throws WorkFlowException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"handleItemNotOwnedByThisUser started, currentUserId: "
				+ currentUserID
				+ ", workItem: "
				+ item);
		int itemStateValue = -1;

		try
		{

			/*
			 * In case the WorkItem is already checked out by another user,
			 * can't confiscate and check out (throw an Exception).
			 */
			com.ibm.workflow.api.ItemPackage.ExecutionState itemState =
				item.state();
			if (itemState == null)
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}
			itemStateValue = itemState.value();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			handleItemNotOwnedByThisUser(item, currentUserID, wfService);
		}

		if (itemStateValue
			== com.ibm.workflow.api.ItemPackage.ExecutionState._CHECKED_OUT)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.ITEM_ALREADY_CHECKED_OUT_BY_ANOTHER_USER,
				new Message(
					WFExceptionMessages
						.ITEM_ALREADY_CHECKED_OUT_BY_ANOTHER_USER,
					Message.SEVERITY_ERROR));
		}

		/*
		 * No point in handling a WorkItem that can't be checked out.
		 * If the WorkItem's state is not READY/RUNNING/CHECKED_OUT,
		 * throw an exception.
		 */
		Logger.debug(
			LOGGER_CONTEXT,
			"handleItemNotOwnedByThisUser, calling assertItemStateAllowsCheckingOut");

		assertItemStateAllowsCheckingOut(item, itemStateValue, wfService);

		/*
		 * The item is not checked out - can confiscate it.
		 * Note: if the workItemID does not represent a legal
		 * ID of existing workItem, than a FmcError 
		 * FMC00122E Object is empty
		 * will be thrown.
		 */
		transferWorkItem(item, currentUserID);

	}

	/**
	 * This method is called during checkOut(), in order to handle 
	 * the case where the selected element is already checked out
	 * by another user.
	 * If the another user is one of your workers, cancel its checking out
	 * and confiscate the WorkItem.
	 * This method will be called only if the WorkItem is checked out,
	 * but not by the current user.
	 * @param item The item to be checked out.
	 * @param currentHolder - current person who holds this WorkItem
	 * @param currentUserID - Id of the current user in the system.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private void handleAlreadyCheckedOutItem(
		WorkItem item,
		Person currentHolder,
		String currentUserID)
		throws WorkFlowException, WorkFlowLogicException
	{

		try
		{
			/*
			 * This can occur, for example, if the user held the WorkItem, 
			 * but it was caught by its manager - so the user tries now
			 * to check out a WorkItem that no longer belongs to him.
			 * In this case, a message is sent to the user. He can later return
			 * to this point, using the method checkOutAlreadyCheckedOutItem().
			 */
			if (currentUserID != currentHolder.manager())
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages
						.ITEM_ALREADY_CHECKED_OUT_BY_ANOTHER_USER,
					new Message(
						WFExceptionMessages
							.ITEM_ALREADY_CHECKED_OUT_BY_ANOTHER_USER,
						Message.SEVERITY_WARNING));
			}
			else
			{
				//Confiscate the item - the current user is the team leader
				item.cancelCheckOut();
				//Move the item to me.
				item.transfer(currentUserID);
			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			handleAlreadyCheckedOutItem(item, currentHolder, currentUserID);
		}
	}

	/**
	 * check in.
	 * @param workItem The WorkItem to check in.
	 * @param container - optional container if the user wants to pass
	 * additional information to the next activity.
	 * @param wfService
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private void checkIn(
		WorkItem workItem,
		WorkFlowServiceParameterMap paramsMap,
		ExecutionService wfService)
		throws WorkFlowException, WorkFlowLogicException
	{

		try
		{

			ProcessInstance instance = workItem.processInstance();

			if (instance == null)
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.PROCESS_INSTANCE_IS_NULL,
					new Message(
						WFExceptionMessages.PROCESS_INSTANCE_IS_NULL,
						Message.SEVERITY_ERROR));
			}
			com
				.ibm
				.workflow
				.api
				.ProcessInstancePackage
				.ExecutionState processState =
				instance.state();

			com.ibm.workflow.api.ItemPackage.ExecutionState itemState =
				workItem.state();
			if ((processState == null) || (itemState == null))
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}
			int processStateValue = processState.value();
			int itemStateValue = itemState.value();

			/*
			 * Can check in the WFWorkItem only if the item's state is checked out 
			 * and the process' state is running. otherwise - return false.
			 */
			if ((itemStateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._CHECKED_OUT)
				&& (processStateValue
					== com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._RUNNING))
			{

				//Actual update of the container.
				ReadWriteContainer container =
					updateContainerForCheckIn(
						workItem,
						instance,
						paramsMap,
						wfService);
				/*
				 * The "0" here is from the signature - checkIn(readwritecontainer, int).
				 * This int is a return code that can be used during exit 
				 * conditions from a task.
				 */
				workItem.checkIn(container, 0); //readwriteContainer, int
				return;
			}
			throw new WorkFlowLogicException(
				WFExceptionMessages.WORK_ITEM_STATE_DOES_NOT_ALLOW_CHECKING_IN,
				new Message(
					WFExceptionMessages
						.WORK_ITEM_STATE_DOES_NOT_ALLOW_CHECKING_IN,
					Message.SEVERITY_ERROR));

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			checkIn(workItem, paramsMap, wfService);
		}
	}

	/**
	 * This method allows checking-in of a WorkItem from the application level.
	 * @param workItemID - ID of the WorkItem to be checked-in.
	 * @param containerAsMap - WorkFlowServiceParameterMap that wraps the 
	 * information to be passed to the next WorkItem (OutContainer of the 
	 * checked-in WorkItem).
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public void checkIn(
		String workItemID,
		WorkFlowServiceParameterMap containerAsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"CheckIn started, received WorkItemId: " + workItemID);
		Logger.debug(
			LOGGER_CONTEXT,
			"CheckIn, received parametersMap: " + containerAsMap);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		/*
		 * Note: there is no requirement that the ParametersMap will not be null
		 * (it is an optional parameter).
		 */
		if (containerAsMap == null)
		{
			containerAsMap = new WorkFlowServiceParameterMap();
			Logger.debug(
				LOGGER_CONTEXT,
				"CheckIn, generated new parametersMap");

		}
		Logger.debug(
			LOGGER_CONTEXT,
			"CheckIn, received parametersMap contents: "
				+ containerAsMap.getAllParameters());
		ExecutionService wfService = getExecutionService();
		WorkItem workItem = null;

		try
		{

			workItem = wfService.persistentWorkItem(workItemID);

			if (!workItem.isComplete())
			{
				workItem.refresh();
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			checkIn(workItemID, containerAsMap);
		}
		checkIn(workItem, containerAsMap, wfService);
		Logger.debug(LOGGER_CONTEXT, "CheckIn completed");

	}

	/**
	 * Assistance method of checkIn.
	 * Takes care of the required updates of the container, and transfers the
	 * priority to the next WorkItem.
	 * @param workItem The Checked-in WorkItem
	 * @param instance The relevant ProcessInstance
	 * @param paramsMap Holds information to be passed to the container
	 * @param wfService
	 * @return ReadWriteContainer the updated container.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private ReadWriteContainer updateContainerForCheckIn(
		WorkItem workItem,
		ProcessInstance instance,
		WorkFlowServiceParameterMap paramsMap,
		ExecutionService wfService)
		throws WorkFlowException, WorkFlowLogicException
	{
		String priorityKey =
			SystemResources.getInstance().getProperty(
				WFConstants.DESCRIPTION_WORK_ITEM_PRIORITY_KEY);
		try
		{

			/*
			 * extract the priority of the WorkItem from the
			 * description. Checking that the description is not null is
			 * done in the method getElementFromDescription().
			 */
			String priorityStr =
				getElementFromDescription(workItem.description(), priorityKey);
			Logger.debug(
				LOGGER_CONTEXT,
				"updateContainerForCheckIn, priority: " + priorityStr);

			if (priorityStr == null)
			{
				//Take priority of processInstance
				priorityKey =
					SystemResources.getInstance().getProperty(
						WFConstants.DESCRIPTION_PRIORITY_KEY);
				if (instance == null)
				{
					throw new WorkFlowException(
						WFExceptionMessages.PROCESS_INSTANCE_IS_NULL);
				}
				priorityStr =
					getElementFromDescription(
						instance.description(),
						priorityKey);
				if (priorityStr == null)
				{
					throw new WorkFlowLogicException(
						WFExceptionMessages.INVALID_PRIORITY_VALUE,
						new Message(
							WFExceptionMessages.INVALID_PRIORITY_VALUE,
							Message.SEVERITY_ERROR));

				}
			}
			ReadWriteContainer container = workItem.outContainer();

			//Set the container buffer from the given parametersMap.
			WorkFlowServiceParameter containerFieldsParameter =
				paramsMap.get(WFConstants.CONTAINER_FIELDS);

			if (containerFieldsParameter != null)
			{
				WorkFlowServiceParameterMap fieldsMap =
					(WorkFlowServiceParameterMap) containerFieldsParameter
						.getValue();

				setContainerBufferFromMap(fieldsMap, container);
			}
			try
			{
				container.setLong(
					WFConstants.PRIORITY,
					Integer.parseInt(priorityStr));

			}
			catch (FmcException fex)
			{
				if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
				{
					throw new WorkFlowException(
						WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
							+ WFConstants.PRIORITY);
				}
				else
				{
					handleFmcException(fex);
					//will be reachable only in case of time-out
					return updateContainerForCheckIn(
						workItem,
						instance,
						paramsMap,
						wfService);
				}
			}

			//Return the updated container.
			return container;

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return updateContainerForCheckIn(
				workItem,
				instance,
				paramsMap,
				wfService);
		}
	}

	/**
	 * Alerts the user if he can open the next WorkItem.
	 * In this case, the WorkFlowServiceParameterMap will contain the WorkItemID
	 * required for check out.
	 * Otherwise, nothing is returned.
	 * @param workItemID The current WorkItem
	 * @param WorkFlowServiceParameterMap
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	public void alertForAutoOpeningOfNextWorkItem(
		String workItemID,
		WorkFlowServiceParameterMap containerAsMap)
		throws NullParametersException, WorkFlowException, WorkFlowLogicException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"alertForAutoOpeningOfNextWorkItem started, received workItemId: "
				+ workItemID);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		if (containerAsMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.CONTAINER_IS_NULL,
				new Message(
					WFExceptionMessages.CONTAINER_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"alertForAutoOpeningOfNextWorkItem, received parametersMap contents: "
				+ containerAsMap.getAllParameters());

		checkIn(workItemID, containerAsMap);

		Integer threshold = getWorkItemsThreshold(containerAsMap);

		try
		{

			/*
			 * According to Leonid, 17/4/05, no need for refreshing here.
			 */
			WorkItem item =
				getExecutionService().persistentWorkItem(workItemID);

			String filter =
				WFConstants.FILTER_FOR_AUTO_OPEN_PREFIX
					+ item.processInstanceName()
					+ "'";

			WorkItem nextItem = locateNextWorkItem(threshold, filter);

			if (nextItem == null)
			{
				nextItem = retrieveNextWorkItemLoop(threshold, filter);

				/*
				 * Either the last WorkItem in the process, or the next WorkItem
				 * should not arrive the current user, or just a delay. Handling
				 * the case of delay is done at retrieveNextWorkItem().
				 */
			}
			if (nextItem != null)
			{
				checkOwnerAndUpdateParametersMap(containerAsMap, nextItem);
				/*
				 * Either the last WorkItem in the process, or the next 
				 * WorkItem should not arrive the current user.
				 * It is not a simple delay, as waiting was already performed above. 
				 */
			}
			return;

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			alertForAutoOpeningOfNextWorkItem(workItemID, containerAsMap);
		}
	}

	/**
	 * Assistance method of alertForAutoOpeningOfNextWorkItem().
	 * The method updates the supplied WorkFlowServiceParameterMap
	 * with the ID of the next WorkItem, and alerts the user (only in
	 * case he can open the next WorkItem).
	 * @param containerAsMap Will contain the ID of the next WorkItem (if applicable).
	 * @param nextItem next WorkItem
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private void checkOwnerAndUpdateParametersMap(
		WorkFlowServiceParameterMap containerAsMap,
		WorkItem nextItem)
		throws
			FmcException,
			WorkFlowException,
			NullParametersException,
			WorkFlowLogicException
	{
		String owner = nextItem.owner();
		if (owner.equals(executionService.getUserId()))
		{

			//keep the ID of the next WorkItem in the parameters map.
			//So the user can access the next WorkItem ID.
			containerAsMap.addInputParameter(
				WFConstants.ID_OF_NEXT_WORK_ITEM,
				nextItem.persistentOid(),
				WFConstants.OBJECT_TYPE_STRING);

			//Message to this user - He should approve catching of the 
			//next WorkItem.
			throw new WorkFlowLogicException(
				WFExceptionMessages.CAN_OPEN_THE_NEXT_WORK_ITEM,
				new Message(
					WFExceptionMessages.CAN_OPEN_THE_NEXT_WORK_ITEM,
					Message.SEVERITY_WARNING));
		}
	}

	/**
	 * Assistance method of autoOpeningOfNextWorkItem().
	 * Is used to retrieve the next WorkItem. Since there might be
	 * some delay between checking in the previous WorkItem and the
	 * arrival of the next one, this method allows the user to try again
	 * to get the next WorkItem.
	 * @param workItemID
	 * @param containerAsMap
	 * @return WorkItem
	 */
	private WorkItem retrieveNextWorkItemLoop(Integer threshold, String filter)
		throws WorkFlowException, WorkFlowLogicException, FmcException
	{
		WorkItem nextWorkItem = null;
		SystemResources systemResources = SystemResources.getInstance();

		int timeToWait =
			Integer.parseInt(
				systemResources.getProperty(WFConstants.TIME_TO_WAIT_KEY));

		int numberOfTries =
			Integer.parseInt(
				systemResources.getProperty(WFConstants.NUMBER_OF_TRIES_KEY));

		Logger.debug(
			LOGGER_CONTEXT,
			"autoOpeningOfNextWorkItem, in the method retrieveNextWorkItemLoop,  "
				+ "timeToWait is: "
				+ timeToWait
				+ " ,numberOfTries is: "
				+ numberOfTries);

		for (int i = 0; i < numberOfTries; i++)
		{
			try
			{
				Thread.sleep(timeToWait);
				nextWorkItem = locateNextWorkItem(threshold, filter);
				if (nextWorkItem != null)
				{
					Logger.debug(
						LOGGER_CONTEXT,
						"autoOpeningOfNextWorkItem, in the method retrieveNextWorkItemLoop,  "
							+ "returned the nextWorkItem");

					return nextWorkItem;
				}
				Logger.debug(
					LOGGER_CONTEXT,
					"autoOpeningOfNextWorkItem, in the method retrieveNextWorkItemLoop,  "
						+ "thread slept "
						+ timeToWait);

			}
			catch (InterruptedException iex)
			{

				Logger.debug(
					LOGGER_CONTEXT,
					"autoOpeningOfNextWorkItem, in the method retrieveNextWorkItemLoop,  "
						+ "InterruptedException has occured");

				Logger.debug(
					LOGGER_CONTEXT,
					"autoOpeningOfNextWorkItem, in the method retrieveNextWorkItemLoop,  "
						+ "nextWorkItem is: "
						+ nextWorkItem);

			}

		}
		Logger.debug(
			LOGGER_CONTEXT,
			"autoOpeningOfNextWorkItem, in the method retrieveNextWorkItemLoop,  "
				+ "returned null");

		return null;
	}

	/**
	 * Assistance method for the AutoOpeningOfNextWorkItem() methods.
	 * 
	 * @param threshold How many WorkItems to retrieve?
	 * @param filter Defines the filtering on the WorkItems.
	 * @return WorkItem the next WorkItem (can be null).
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private WorkItem locateNextWorkItem(Integer threshold, String filter)
		throws FmcException, WorkFlowException, WorkFlowLogicException
	{
		WorkItem[] items =
			getExecutionService().queryWorkItems(
				filter,
				WFConstants.STATE_ASC,
				threshold);

		if ((items == null) || (items.length == 0))
		{
			/*
			 * Either the last WorkItem in the process, or the next WorkItem
			 * should not arrive the current user.
			 */
			return null;
		}
		else if (items.length > 1)
		{

			/*
			 * There are at least two WorkItems after this one - the
			 * Process is not linear.
			 */
			throw new WorkFlowLogicException(
				WFExceptionMessages.THE_PROCESS_IS_NOT_LINEAR,
				new Message(
					WFExceptionMessages.THE_PROCESS_IS_NOT_LINEAR,
					Message.SEVERITY_ERROR));
		}
		else
		{

			return items[0];
		}
	}

	/**
	 * Returns The WorkFlowServiceParameterMap returned from checkOut of the WorkItem.
	 * @param workItemID - ID of the current WorkItem to checkIn.
	 * @param containerAsMap - WorkFlowServiceParameterMap
	 * @return parametersMap returned from checking out the new WorkItem.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	public String autoOpeningOfNextWorkItem(
		String workItemID,
		WorkFlowServiceParameterMap containerAsMap)
		throws NullParametersException, WorkFlowException, WorkFlowLogicException
	{

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		if (containerAsMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.CONTAINER_IS_NULL,
				new Message(
					WFExceptionMessages.CONTAINER_IS_NULL,
					Message.SEVERITY_ERROR));

		}

		Logger.debug(
			LOGGER_CONTEXT,
			"autoOpeningOfNextWorkItem, received parametersMap values: "
				+ containerAsMap.getAllParameters());

		checkIn(workItemID, containerAsMap);

		Logger.debug(
			LOGGER_CONTEXT,
			"autoOpeningOfNextWorkItem, performed checkIn");

		Integer threshold = getWorkItemsThreshold(containerAsMap);

		try
		{

			/*
			 * According to Leonid, 17/4/2005, no need to refresh here.
			 */
			WorkItem item =
				getExecutionService().persistentWorkItem(workItemID);

			String filter =
				WFConstants.FILTER_FOR_AUTO_OPEN_PREFIX
					+ item.processInstanceName()
					+ "'";

			Logger.debug(
				LOGGER_CONTEXT,
				"autoOpeningOfNextWorkItem, generated filter: " + filter);

			WorkItem nextItem = locateNextWorkItem(threshold, filter);

			/*
			 * There might be a delay in arrival of the next WorkItem.
			 * Therefore, the retrieveNextWorkItemLoop() is performed.
			 */

			if (nextItem == null)
			{
				nextItem = retrieveNextWorkItemLoop(threshold, filter);
				Logger.debug(
					LOGGER_CONTEXT,
					"autoOpeningOfNextWorkItem, got the nextItem from the method retrieveNextWorkItemLoop");

			}

			if (nextItem != null)
			{
				String owner = nextItem.owner();
				Logger.debug(
					LOGGER_CONTEXT,
					"autoOpeningOfNextWorkItem, owner of the nextItem is: "
						+ owner);

				if (owner.equals(executionService.getUserId()))
				{
					checkOut(nextItem.persistentOid(), containerAsMap);
					Logger.debug(
						LOGGER_CONTEXT,
						"autoOpeningOfNextWorkItem, performed checking-out");

				}
				return nextItem.persistentOid();
			}

			/*
			 * Either the last WorkItem in the process, or the next WorkItem
			 * should not arrive the current user, or a time-out has occured.
			 */
			return null;

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return autoOpeningOfNextWorkItem(workItemID, containerAsMap);
		}
	}

	/**
	 * Force restart of the WorkItem.
	 * Passes to the WorkItem information required for restarting.
	 * One of the required parameters is the name of the applicative state.
	 * @param workItemID ID of the WorkItem to be restarted
	 * @param parametersMap contains data to be passed to the restarted WorkItem.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	public void forceRestart(
		String workItemID,
		WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException, WorkFlowException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"forceRestart started, received workItemId: "
				+ workItemID
				+ ", received parametersMap: "
				+ parametersMap.getAllParameters());

		//check the parameters
		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		try
		{

			/*
			 * Checking of the received Calendar is done in inner method
			 * updateItemExpectedEndTime
			 */
			WorkItem workItem =
				getExecutionService().persistentWorkItem(workItemID);

			if (!workItem.isComplete())
			{
				workItem.refresh();
			}

			//TODO: Add verification that the WorkItem state allows forceRestart!

			if (parametersMap != null)
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"forceRestart, received parametersMap: "
						+ parametersMap.getAllParameters());

				//Changed after talk with Leonid, 27.3.2005
				ReadWriteContainer container =
					workItem.inContainer().asReadWriteContainer();
				//This method assumes only parameters for the container are passed
				//in the parametersMap.
				setContainerBufferFromMap(parametersMap, container);
				Logger.debug(
					LOGGER_CONTEXT,
					"forceRestart, activating WorkFlow with writing to the container");
				workItem.forceRestart2(container);

			}
			else
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"forceRestart, activating WorkFlow without writing to the container");

				/*
				 * Uses the default - take information from inContainer
				 * (Without the changed performed by the user). 
				 */
				workItem.forceRestart();
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			forceRestart(workItemID, parametersMap);
		}
	}

	//	/**
	//	 * Assistance method to ensure the WorkItem's state allows forceRestart.
	//	 * @param item the WorkItem to be checked out.
	//	 * @param wfService The local instance of ExecutionService
	//	 * @throws WorkFlowLogicException
	//	 * @throws WorkFlowException
	//	 */
	//	private void assertItemStateAllowsForceRestart(
	//		WorkItem item,
	//		ExecutionService wfService)
	//		throws WorkFlowLogicException, WorkFlowException
	//	{
	//
	//		int itemStateValue = -1;
	//		int processState = -1;
	//		try
	//		{
	//
	//			com.ibm.workflow.api.ItemPackage.ExecutionState iState =
	//				item.state();
	//			itemStateValue = iState.value();
	//
	//			ProcessInstance processInstance =
	//				wfService.persistentProcessInstance(
	//					item.persistentOidOfProcessInstance());
	//			if (!processInstance.isComplete())
	//			{
	//				processInstance.refresh();
	//			}
	//			com.ibm.workflow.api.ProcessInstancePackage.ExecutionState pState =
	//				processInstance.state();
	//
	//			if ((pState == null))
	//			{
	//				throw new WorkFlowException(
	//					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
	//			}
	//
	//			processState = pState.value();
	//		}
	//		catch (FmcException fex)
	//		{
	//			handleFmcException(fex);
	//			//will be reachable only in case of time-out
	//			assertItemStateAllowsForceRestart(item, wfService);
	//		}
	//		if((processState!=))
	//		if ((itemStateValue
	//			!= com.ibm.workflow.api.ItemPackage.ExecutionState._READY)
	//			|| (processState
	//				!= com
	//					.ibm
	//					.workflow
	//					.api
	//					.ProcessInstancePackage
	//					.ExecutionState
	//					._RUNNING))
	//		{
	//			// Can't check out the item as its state doesn't allow it.
	//			//TODO: replace the exception
	//			throw new WorkFlowLogicException(
	//				WFExceptionMessages.ITEM_STATE_DOES_NOT_ALLOW_CHECKING_OUT,
	//				new Message(
	//					WFExceptionMessages.ITEM_STATE_DOES_NOT_ALLOW_CHECKING_OUT,
	//					Message.SEVERITY_ERROR));
	//
	//		}
	//
	//	}

	/**
	 * Cancels check out of the selected WFWorkItem.
	 * This method does not return the item to the previous owner
	 * (before the checking-out).
	 * @param workItemID - ID of the checked-out WFWorkItem.
	 * @return true if check-out was cancelled, false otherwise.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public void cancelCheckout(String workItemID)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started cancelCheckOut, received workItemId: " + workItemID);

		if (workItemID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		ExecutionService wfService = getExecutionService();
		try
		{

			WorkItem item = wfService.persistentWorkItem(workItemID);
			/*
			 * The WorkItem can't be null.
			 */
			if (!item.isComplete())
			{
				item.refresh();
			}

			String owner = item.owner();

			Logger.debug(
				LOGGER_CONTEXT,
				"cancelCheckOut, current owner: " + owner);

			Person currentHolder = wfService.persistentPerson(owner);
			if (!currentHolder.isComplete())
			{
				currentHolder.refresh();
			}
			String currentUserID = executionService.getUserId();

			Logger.debug(
				LOGGER_CONTEXT,
				"cancelCheckOut, current userId: " + currentUserID);

			/*
			 * Only the item's owner or its manager are permitted to
			 * cancel checking out of the item.
			 */
			if (!(owner.equals(currentUserID)))
			{
				if ((currentUserID != currentHolder.manager()))
				{
					throw new WorkFlowLogicException(
						WFExceptionMessages.ITEM_NOT_OWNED_BY_USER,
						new Message(
							WFExceptionMessages.ITEM_NOT_OWNED_BY_USER,
							Message.SEVERITY_ERROR));
				}

				/*
				 * If the user is the holder's manager, there is no problem, 
				 * he can cancel check-out of the workitem.
				 */
			}

			/*
			 * According to leonid, 17/4/2005, no need to refresh here.
			 */
			com
				.ibm
				.workflow
				.api
				.ProcessInstancePackage
				.ExecutionState processState =
				wfService
					.persistentProcessInstance(
						item.persistentOidOfProcessInstance())
					.state();
			if (processState == null)
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}
			com.ibm.workflow.api.ItemPackage.ExecutionState itemState =
				item.state();

			if (itemState == null)
			{
				throw new WorkFlowException(
					WFExceptionMessages.EXECUTION_STATE_IS_NULL);
			}
			int processStateValue = processState.value();
			int itemStateValue = itemState.value();

			/*
			 * Can cancel check out the WorkItem only if the item's state is 
			 * checked out and the process' state is running. 
			 * otherwise - return false (did not perform cancel check out).
			 */
			if ((itemStateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._CHECKED_OUT)
				&& (processStateValue
					== com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._RUNNING))
			{
				item.cancelCheckOut();
			}
			else
			{

				/*
				 * The WorkItem is not in check-out state or the ProcessInstance 
				 * state is not running - can't cancel checking out.
				 */
				throw new WorkFlowLogicException(
					WFExceptionMessages
						.WORK_ITEM_STATE_DOES_NOT_ALLOW_CANCEL_CHECK_OUT,
					new Message(
						WFExceptionMessages
							.WORK_ITEM_STATE_DOES_NOT_ALLOW_CANCEL_CHECK_OUT,
						Message.SEVERITY_ERROR));
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			cancelCheckout(workItemID);
		}
	}

	/**
	 * This service is like prepareListOfUsers, except that
	 * it returns a set of objects containing the user attributes.
	 * @param workItemID
	 * @return Set of users with their details.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public Set prepareListOfUsersWithDetails()
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started prepareListOfUsersWithDetails");

		Set set = prepareListOfUsers();

		/*
		 * The minimum load factor of the set is defined in the properties file.
		 * If any of the sets is expected to contain more than the defined limit
		 * users, it might be good to set another initial load factor
		 * for the HashSets.
		 */
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.USERS_SET_INITIAL_SIZE_KEY));

		Logger.debug(
			LOGGER_CONTEXT,
			"prepareListOfUsersWithDetails, users set initial size: "
				+ userSetsInitialSize);

		Set resultSet = new HashSet(userSetsInitialSize);
		UserLdapDetails userDetails = null;

		if (set != null)
		{
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				try
				{

					userDetails =
						ldapManager.getUserDetails(it.next().toString());
				}
				catch (LdapException lex)
				{
					throw new WorkFlowException(lex);
				}
				if (userDetails != null)
				{
					resultSet.add(userDetails);
				}
			}
			Logger.debug(
				LOGGER_CONTEXT,
				"finished prepareListOfUsersWithDetails");
			return resultSet;
		}
		else
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"finished prepareListOfUsersWithDetails, returning null");
			return null;
		}

	}

	/**
	 * This method is used to prepare list of users required for 
	 * "choosing working queue of another user".
	 * @return list of users for "choosing working queue of another user".
	 * The return type is a Set, in order to prevent multiple entries easily.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	public Set prepareListOfUsers()
		throws NullParametersException, WorkFlowException
	{

		Logger.debug(LOGGER_CONTEXT, "started prepareListOfUsers");

		ExecutionService wfService = getExecutionService();
		String userID = executionService.getUserId();

		Logger.debug(
			LOGGER_CONTEXT,
			"prepareListOfUsers, current userId: " + userID);

		/*
		 * The minimum load factor of the set is defined in the properties file.
		 * If any of the sets is expected to contain more than the defined limit
		 * users, it might be good to set another initial load factor
		 * for the HashSets.
		 */
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.USERS_SET_INITIAL_SIZE_KEY));

		Logger.debug(
			LOGGER_CONTEXT,
			"prepareListOfUsers, users set initial size: "
				+ userSetsInitialSize);

		HashSet result = new HashSet(userSetsInitialSize);

		try
		{

			Person person = wfService.persistentPerson(userID);

			if (!person.isComplete())
			{
				person.refresh();
			}
			/*
			 * If the person is a manager, retrieve list of users in each 
			 * organization managed by him.
			 */
			if (person.isManager())
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"prepareListOfUsers, person is manager");

				/*
				 * Don't use operator add on sets - to avoid generation of a
				 * set member which is a set itself.
				 */
				Set managedSet = handleManagerAccessList(userID, person);
				if (managedSet != null)
				{
					result.addAll(managedSet);
				}
				Logger.debug(
					LOGGER_CONTEXT,
					"prepareListOfUsers, result: " + result);

			}
			else
			{

				//The user is not a manager.
				Logger.debug(
					LOGGER_CONTEXT,
					"prepareListOfUsers, person is not manager");

				/*
				 * Add all non-virtual users this user is authorized for.
				 * Note: Currently, regular users are not authorized for another
				 * users, therefore the generated list is expected to be blank.
				 * This might be changed in stage B of the project
				 * (According to Uri's mail, 28/2/2005).
				 */
				String[] personsAuthorizedFor = person.personsAuthorizedFor();
				for (int k = 0; k < personsAuthorizedFor.length; k++)
				{
					result.add(personsAuthorizedFor[k]);
				}
				Logger.debug(
					LOGGER_CONTEXT,
					"prepareListOfUsers, result: " + result);

			}

			/*
			 *Next if was added to avoid addition of all people
			 *authorized for the super-virtual user.  
			 */
			//			if (result.contains(WFConstants.SUPER_VIRTUAL_USER_ID))
			//			{
			//				result.remove(WFConstants.SUPER_VIRTUAL_USER_ID);
			//			}
			if (result
				.contains(WFConstants.SUPER_VIRTUAL_USER_ID.toUpperCase()))
			{
				result.remove(WFConstants.SUPER_VIRTUAL_USER_ID.toUpperCase());
			}
			/*
			 * eliminate the default manager (not a real user) from the list.
			 */
			if (result.contains(WFLdapConstants.DEFAULT_MANAGER.toUpperCase()))
			{
				result.remove(WFLdapConstants.DEFAULT_MANAGER.toUpperCase());
			}

			//Add the user himself
			result.add(userID);
			Logger.debug(
				LOGGER_CONTEXT,
				"prepareListOfUsers, final list returned: " + result);
			return result;
		}

		catch (LdapException nex)
		{
			throw new WorkFlowException(nex);
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return prepareListOfUsers();
		}

	}

	/**
	 * Assistance method of prepareListOfUsers().
	 * This method generated the list of users for a manager in the system
	 * (the users in each organization managed by him).
	 * @param userID
	 * @param person
	 * @return Set set of users the manager can access.
	 * @throws LdapException
	 * @throws FmcException
	 */
	private Set handleManagerAccessList(String userID, Person person)
		throws LdapException, FmcException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"handleManagerAccessList, userId: " + userID);

		/*
		 * The minimum load factor of the set is defined in the properties file.
		 * If any of the sets is expected to contain more than the defined limit
		 * users, it might be good to set another initial load factor
		 * for the HashSets.
		 */
		int userSetsInitialSize =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.USERS_SET_INITIAL_SIZE_KEY));

		Logger.debug(
			LOGGER_CONTEXT,
			"handleManagerAccessList, users set initial size: "
				+ userSetsInitialSize);

		HashSet result = new HashSet(userSetsInitialSize);

		Set accessListOfUser = ldapManager.accessListOfUser(userID);

		if (accessListOfUser != null)
		{
			result.addAll(accessListOfUser);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"handleManagerAccessList, accessList of the user: " + result);

		/*
		 * Next if was added to avoid addition of all people
		 * authorized for the super-virtual user.  
		 */
		if (result.contains(WFConstants.SUPER_VIRTUAL_USER_ID))
		{
			result.remove(WFConstants.SUPER_VIRTUAL_USER_ID);
			Logger.debug(
				LOGGER_CONTEXT,
				"handleManagerAccessList, removed super virtual user");

		}

		//No need to take care of the default manager as no one is authorized for him.
		String[] organizations = person.namesOfManagedOrganizations();

		Set usersInSubOrganizationCurrentUser;
		Set usersInSubOrganizationCurrentOrganization;
		Iterator iterator = null;
		String currOrganization = null;
		String currOrganizationManager = null;
		for (int i = 0; i < organizations.length; i++)
		{
			//add sub-organizations of the managed organizations
			Set subOrganizations =
				ldapManager.getSubOrganizationalTeams(organizations[i]);
			Logger.debug(
				LOGGER_CONTEXT,
				"handleManagerAccessList, subOrganizations of organization["
					+ i
					+ "]: "
					+ subOrganizations);

			/*
			 * For each sub-organization, add users in it.
			 * Add also all the managers of the sub-organizations (specific
			 * treatment since a bug that caused no addittion of the managers).
			 */
			iterator = subOrganizations.iterator();
			while (iterator.hasNext())
			{
				currOrganization = iterator.next().toString();
				Logger.debug(
					LOGGER_CONTEXT,
					"handleManagerAccessList, currOrganization: "
						+ currOrganization);

				currOrganizationManager =
					ldapManager.getOrganizationManager(currOrganization);
				if ((currOrganizationManager != null)
					&& (!(currOrganizationManager
						.equals(WFLdapConstants.DEFAULT_MANAGER))))
				{
					result.add(currOrganizationManager);
				}

				usersInSubOrganizationCurrentUser =
					ldapManager.usersInSubOrganizationForViewAnother(
						currOrganization);
				if (usersInSubOrganizationCurrentUser != null)
				{
					Logger.debug(
						LOGGER_CONTEXT,
						"handleManagerAccessList, users in subOrganization for organization "
							+ currOrganization
							+ ": "
							+ usersInSubOrganizationCurrentUser);

					result.addAll(usersInSubOrganizationCurrentUser);
				}
			}

			usersInSubOrganizationCurrentOrganization =
				ldapManager.usersInSubOrganizationForViewAnother(
					organizations[i]);
			if (usersInSubOrganizationCurrentOrganization != null)
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"handleManagerAccessList, users in subOrganization for organization "
						+ organizations[i]
						+ ": "
						+ usersInSubOrganizationCurrentOrganization);

				result.addAll(usersInSubOrganizationCurrentOrganization);
			}
		}
		/*
		 * Add users authorized for the virtual users.
		 * Pass on the received set, for each virtual user add users
		 * authorized for him.
		 */
		Iterator usersIterator = result.iterator();
		HashSet tempResult = new HashSet(userSetsInitialSize);
		String checkedUser;
		Set tempSet;
		Set usersAuthorizedForThisUser;

		while (usersIterator.hasNext())
		{
			//Pass only on the virtual/approving users!!!
			checkedUser = usersIterator.next().toString();

			tempSet =
				ldapManager.searchUsersWithGivenAttribute(WFLdapConstants.OU);
			if ((tempSet != null) && (tempSet.contains(checkedUser)))
			{
				usersAuthorizedForThisUser =
					ldapManager.usersAuthorizedForThisUser(checkedUser);
				if (usersAuthorizedForThisUser != null)
				{

					Logger.debug(
						LOGGER_CONTEXT,
						"handleManagerAccessList, users authorized for checked user "
							+ checkedUser
							+ ": "
							+ usersAuthorizedForThisUser);

					tempResult.addAll(usersAuthorizedForThisUser);
				}
			}
		}
		if (tempResult != null)
		{
			result.addAll(tempResult);
		}

		return result;
	}

	/**
	 * Assist method to create a WFProcessInstance.
	 * @param ProcessInstance The MQ WorkFlow WFProcessInstance
	 * @param threshold
	 * @param inContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param outContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param globalContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param wfService
	 * @param retrieveNotifications
	 * @param getExpirationTime
	 * @param useManager
	 * @return WFProcessInstance The infrastructure WFProcessInstance
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private WFProcessInstance createWFProcessInstance(
		ProcessInstance instance,
		Integer threshold,
		String sortCriteria,
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		ExecutionService wfService,
		boolean retrieveNotifications,
		boolean getExpirationTime,
		boolean useManager)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(LOGGER_CONTEXT, "started createWFProcessInstance");

		if (instance == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.PROCESS_INSTANCE_IS_NULL);
		}

		/*
		 * This filter is used to retrieve all WorkItems connected to this
		 * WFProcessInstance.
		 */
		StringBuffer tempFilter = new StringBuffer(128);
		tempFilter.append(WFConstants.FILTER_PROCESS_NAME_IN);
		WFProcessInstance newInstance = null;
		String description = null;
		WFWorkItem[] items = null;

		try
		{

			tempFilter.append(instance.name());
			tempFilter.append(WFConstants.GERESH);

			if (useManager)
			{
				WFExecutionService managerExecutionService =
					getManagerExecutionService();
				try
				{
					items =
						retrieveTasksForUser(
							inContainerElementNames,
							outContainerElementNames,
							globalContainerElementNames,
							tempFilter.toString(),
							sortCriteria,
							threshold,
							managerExecutionService.getExecutionService(),
							retrieveNotifications,
							getExpirationTime);
				}
				catch (FmcException fex)
				{
					handleFmcException(fex);
					putManagerExecutionService(managerExecutionService);
					managerExecutionService = null;
					return createWFProcessInstance(
						instance,
						threshold,
						sortCriteria,
						inContainerElementNames,
						outContainerElementNames,
						globalContainerElementNames,
						wfService,
						retrieveNotifications,
						getExpirationTime,
						useManager);

				}
				finally
				{
					putManagerExecutionService(managerExecutionService);
					managerExecutionService = null;
				}

			}
			else
			{
				items =
					retrieveTasksForUser(
						inContainerElementNames,
						outContainerElementNames,
						globalContainerElementNames,
						tempFilter.toString(),
						sortCriteria,
						threshold,
						wfService,
						retrieveNotifications,
						getExpirationTime);
			}

			newInstance = new WFProcessInstance();

			newInstance.setActivitiesArray(items);

			newInstance.setCategory(instance.category());
			newInstance.setLastHandler(
				getLastHandlerOfProcess(instance.name()));

			Logger.debug(
				LOGGER_CONTEXT,
				"called getLastHandlerOfProcess, last Handler: "
					+ getLastHandlerOfProcess(instance.name()));

			// Taking care of containers.
			WorkFlowServiceParameterMap map =
				fillMap(
					outContainerElementNames,
					instance.outContainer(),
					WFConstants.CONTAINER_TYPE_ACTIVITY_OUTPUT_CONTAINER);

			if (map == null)
			{
				throw new NullParametersException(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					new Message(
						WFExceptionMessages.PARAMETERS_MAP_NULL,
						Message.SEVERITY_ERROR));
			}

			Logger.debug(
				LOGGER_CONTEXT,
				"createWFProcessInstance, generated map: "
					+ map.getAllParameters());

			//putAll() checks the added map for null value.
			//If the value is null, nothing is added.
			map.putAll(
				fillMap(
					inContainerElementNames,
					instance.inContainer(),
					WFConstants.CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER));

			Logger.debug(
				LOGGER_CONTEXT,
				"createWFProcessInstance, generated map after adding input container values: "
					+ map.getAllParameters());

			/*
			 * The processInstance does not require to have a global container.
			 * In case it does not have a global container, it will simply
			 * not be added.
			 */
			try
			{

				/*
				 * In case the instance status is READY or FINISHED, it
				 * has no global container - don't fill it.
				 */
				com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState state =
					instance.state();
				if (state == null)
				{
					throw new WorkFlowException(
						WFExceptionMessages.EXECUTION_STATE_IS_NULL);
				}
				int stateValue = state.value();
				if ((stateValue
					!= com
						.ibm
						.workflow
						.api
						.ProcessInstancePackage
						.ExecutionState
						._READY)
					&& (stateValue
						!= com
							.ibm
							.workflow
							.api
							.ProcessInstancePackage
							.ExecutionState
							._FINISHED))
				{
					ReadOnlyContainer globalContainer =
						instance.globalContainer();
					map.putAll(
						(fillMap(globalContainerElementNames,
						globalContainer,
						WFConstants.CONTAINER_TYPE_GLOBAL_CONTAINER)));
				}

			}
			catch (FmcException fex)
			{

				if (fex.rc != FmcException.FMC_ERROR_NO_GLOBAL_CONTAINER)
				{
					throw new WorkFlowException(fex);
				}
			}

			// Adding contents of the description
			description = instance.description();
			Logger.debug(
				LOGGER_CONTEXT,
				"createWFProcessInstance, description: " + description);
			map.putAll(fillMapFromDescription(description));
			// Adding the ProcessContext details.
			map.add(getProcessContext(instance));
			Logger.debug(
				LOGGER_CONTEXT,
				"createWFProcessInstance, final map: "
					+ map.getAllParameters());

			newInstance.setParametersMap(map);

			newInstance.setProcessID(instance.persistentOid());
			newInstance.setParentName(instance.parentName());

			Calendar endTime = instance.endTime();
			if (endTime != null)
			{
				newInstance.setProcessEndTime(endTime.getTime());

			}

			Calendar startTime = instance.startTime();
			if (startTime != null)
			{
				newInstance.setProcessStartTime(startTime.getTime());

			}
			newInstance.setProcessName(instance.name());
			Logger.debug(
				LOGGER_CONTEXT,
				"createWFProcessInstance, generated instance name: "
					+ instance.name());

			newInstance.setProcessTemplateName(instance.processTemplateName());
			newInstance.setProcessState(getProcessState(instance.state()));

			Logger.debug(
				LOGGER_CONTEXT,
				"createWFProcessInstance, processState: "
					+ newInstance.getProcessState());

			newInstance.setProcessTopLevelName(instance.topLevelName());
			newInstance.setStarter(instance.starter());
			Logger.debug(
				LOGGER_CONTEXT,
				"createWFProcessInstance, instance starter: "
					+ instance.starter());
		}
		catch (FmcException fex)
		{
			if (fex.rc == FmcException.FMC_ERROR_NOT_AUTHORIZED)
			{

				String currentUser = executionService.getUserId();

				throw new WorkFlowLogicException(
					WFExceptionMessages
						.USER_IS_NOT_AUTHORIZED_FOR_THIS_OPERATION
						+ currentUser,
					new Message(
						WFExceptionMessages
							.USER_IS_NOT_AUTHORIZED_FOR_THIS_OPERATION
							+ currentUser,
						Message.SEVERITY_ERROR));
			}

			else
			{
				throw new WorkFlowException(fex);
			}
		}

		newInstance.setPriority(getPriorityOfProcessInstance(description));

		return newInstance;

	}

	/**
	 * Assistance method to get the process' state and replace the ExecutionState of
	 * IBM workflow.
	 * @param state - The MQWorkFlow ExecutionState of the process
	 * @return String representing the process' state.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	private int getProcessState(
		com.ibm.workflow.api.ProcessInstancePackage.ExecutionState state)
		throws NullParametersException, WorkFlowException
	{

		Logger.debug(LOGGER_CONTEXT, "started getProcessState");

		int processState = -1;
		if (state == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.EXECUTION_STATE_IS_NULL);
		}
		int stateValue = state.value();

		Logger.debug(
			LOGGER_CONTEXT,
			"state of the WorkFlow ProcessInstance is: " + stateValue);

		if (stateValue
			== com
				.ibm
				.workflow
				.api
				.ProcessInstancePackage
				.ExecutionState
				._RUNNING)
		{
			processState = WFConstants.PROCESS_STATE_RUNNING;
		}
		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._TERMINATED)
		{
			processState = WFConstants.PROCESS_STATE_TERMINATED;
		}

		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._DELETED)
		{
			processState = WFConstants.PROCESS_STATE_DELETED;
		}
		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._FINISHED)
		{
			processState = WFConstants.PROCESS_STATE_FINISHED;
		}

		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._READY)
		{
			processState = WFConstants.PROCESS_STATE_READY;
		}

		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._SUSPENDED)
		{
			processState = WFConstants.PROCESS_STATE_SUSPENDED;
		}
		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._SUSPENDING)
		{
			processState = WFConstants.PROCESS_STATE_SUSPENDING;
		}

		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._TERMINATING)
		{
			processState = WFConstants.PROCESS_STATE_TERMINATING;
		}
		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ProcessInstancePackage
					.ExecutionState
					._UNDEFINED)
		{
			processState = WFConstants.PROCESS_STATE_UNDEFINED;
		}
		else
		{
			throw new WorkFlowException(
				WFExceptionMessages.ILLEGAL_PROCESS_INSTANCE_STATE);
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"state of the ProcessInstance is: " + processState);

		return processState;
	}

	/**
	 * Assistance method - get last handler
	 * Note: applicable only for processes that already finished!
	 * @param processInstanceName name of the WFProcessInstance.
	 * @return String last handler of process. it can be null if the WFProcessInstance
	 * did not finish yet.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private String getLastHandlerOfProcess(String processInstanceName)
		throws WorkFlowException, NullParametersException
	{

		if (processInstanceName == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.PROCESS_INSTANCE_IS_NULL);
		}

		StringBuffer tempFilter = new StringBuffer(128);
		tempFilter.append(WFConstants.FILTER_STATE_FOR_LAST_HANDLER);
		tempFilter.append(WFConstants.FILTER_PROCESS_NAME_PREFIX);
		tempFilter.append(processInstanceName);
		tempFilter.append(WFConstants.RIGHT_PHARENTESIS);

		Logger.debug(
			LOGGER_CONTEXT,
			"getLastHandlerOfProcess, filter for lastHandler: "
				+ tempFilter.toString());

		try
		{

			/*
			 * We need just the first WorkItem in the sorted array, 
			 * therefore the threshold was set to 1.
			 */
			WorkItem[] workItems =
				getExecutionService().queryWorkItems(
					tempFilter.toString(),
					WFConstants.SORT_LAST_MODIFICATION_TIME_DESC,
					Integer.valueOf("1"));
			/*
				* The process has no WorkItems - No information about the 
				* lastHandler can be found.
				*/
			Logger.debug(
				LOGGER_CONTEXT,
				"getLastHandlerOfProcess, performed queryWorkItems");

			if ((workItems == null) || (workItems.length == 0))
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"getLastHandlerOfProcess, no workItems found");

				return null;
			}
			/*
			 * We need just the first WFWorkItem in the sorted array.
			 */
			return workItems[0].owner();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getLastHandlerOfProcess(processInstanceName);
		}
	}

	/**
	 * Retrieve the last handler of a given customer, for a specific roleName
	 * and a specific ProcessTemplate.
	 * First, find the last ProcessInstance of this customer.
	 * Second, find the last performed WorkItem in this ProcessInstance.
	 * Return the owner of this WorkItem.
	 * @param parametersMap
	 * @param roleName optional (can be null).
	 * @param processTemplateName optional (can be null).
	 * @return WFLastHandlerOfCustomer wraps last handler and organization.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	public WFLastHandlerOfCustomer getLastHandlerOfCustomer(
		WorkFlowServiceParameterMap parametersMap,
		String roleName,
		String processTemplateName)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"getLastHandlerOfCustomer started, received roleName: "
				+ roleName
				+ ", received processTemplateName: "
				+ processTemplateName
				+ "received parametersMap: "
				+ parametersMap);

		String customerID = null;
		String customerIDParameter = null;
		String dateStr = null;

		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		Logger.debug(
			LOGGER_CONTEXT,
			"getLastHandlerOfCustomer, received parametersMap values: "
				+ parametersMap.getAllParameters());

		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.LAST_HANDLER_OF_CUSTOMER_PARAMETER);
		if (parameter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages
					.LAST_HANDLER_OF_CUSTOMER_PARAMETER_IS_NULL_IN_MAP,
				new Message(
					WFExceptionMessages
						.LAST_HANDLER_OF_CUSTOMER_PARAMETER_IS_NULL_IN_MAP,
					Message.SEVERITY_ERROR));
		}
		else
		{
			customerID = parameter.getStringValue();
		}
		//Checking customerID is done at the method generateCustomerFilter().

		customerIDParameter = getCustomerIdParameter(parametersMap);

		Integer threshold = getWorkItemsThreshold(parametersMap);

		parameter = parametersMap.get(WFConstants.MAX_LAST_MODIFICATION_TIME);
		if (parameter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages
					.LAST_MODIFICATION_TIME_PARAMETER_IS_NULL_IN_MAP,
				new Message(
					WFExceptionMessages
						.LAST_MODIFICATION_TIME_PARAMETER_IS_NULL_IN_MAP,
					Message.SEVERITY_ERROR));
		}
		else
		{
			dateStr = parameter.getStringValue();
		}

		if (dateStr == null)
		{
			throw new NullParametersException(
				WFExceptionMessages
					.LAST_MODIFICATION_TIME_PARAMETER_VALUE_IS_NULL,
				new Message(
					WFExceptionMessages
						.LAST_MODIFICATION_TIME_PARAMETER_VALUE_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		ExecutionService wfService = getExecutionService();

		StringBuffer tempFilter = new StringBuffer(256);
		//tempFilter.append(WFConstants.CUSTOMER_ID_IN_PROCESS_TEMPLATE_INFIX);
		tempFilter.append("*:");
		tempFilter.append(customerIDParameter);
		//TODO: commented since the customerId is now an Integer, not String
		//		tempFilter.append(WFConstants.LIKE_INFIX);
		//		tempFilter.append(customerID);
		//		tempFilter.append("'");
		//New code to resolve customerId bug.
		tempFilter.append("=");
		tempFilter.append(customerID);
		//
		tempFilter.append(WFConstants.LAST_MODIFICATION_TIME_INFIX);
		tempFilter.append("'");
		tempFilter.append(dateStr);
		tempFilter.append("'");

		if (processTemplateName != null)
		{
			//Next added to take care of ProcessName
			tempFilter.append(WFConstants.PROCESS_NAME_LIKE_INFIX);
			tempFilter.append(processTemplateName);
			tempFilter.append("*'");
		}

		WFLastHandlerOfCustomer lastHandler = null;
		WorkItem[] items = null;

		try
		{

			items =
				wfService.queryWorkItems(
					tempFilter.toString(),
					WFConstants.CREATION_TIME_ASC,
					threshold);

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getLastHandlerOfCustomer(
				parametersMap,
				roleName,
				processTemplateName);
		}

		/*
		 * The process has no WorkItems - No information about the 
		 * lastHandler can be found.
		 */
		if ((items == null) || (items.length == 0))
		{
			return null;
		}
		/*
		 * We need just the first WorkItem in the sorted array.
		 */
		String role = null;
		String organization = null;
		try
		{

			if (roleName == null)
			{
				role = items[0].owner();
				ReadOnlyContainer inContainer = items[0].inContainer();
				try
				{
					organization =
						inContainer.getString(
							WFConstants.ACTIVITY_ORGANIZATION);
				}
				catch (FmcException fex)
				{
					throw new WorkFlowException(
						WFExceptionMessages
							.ORGANIZATION_CONTAINER_PARAMETER_IS_NULL);
				}
				lastHandler = new WFLastHandlerOfCustomer(role, organization);

				//Role was found
			}
			else
			{
				lastHandler =
					findOwnerOfFirstWorkItemWithAppropriateRole(
						items,
						roleName);
			}

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getLastHandlerOfCustomer(
				parametersMap,
				roleName,
				processTemplateName);
		}
		return lastHandler;

	}

	/**
	 * Assistance method of getLastHandlerOfCustomer().
	 * @param items
	 * @param roleName
	 * @return String owner of the workItem.
	 * @throws WorkFlowException
	 */
	private WFLastHandlerOfCustomer findOwnerOfFirstWorkItemWithAppropriateRole(
		WorkItem[] items,
		String roleName)
		throws WorkFlowException
	{

		boolean itemFound = false;
		String itemRole = null;
		String organization = null;
		int length = items.length;
		WFLastHandlerOfCustomer lastHandler = null;

		try
		{

			for (int i = 0; i < length && !itemFound; i++)
			{
				ReadOnlyContainer inContainer = items[i].inContainer();

				try
				{
					/*
					 * According to checking with Leonid and Tammy, the role
					 * will not be null.
					 */
					itemRole =
						inContainer.getString(WFConstants.MEMBER_OF_ROLES);

				}
				catch (FmcException fex)
				{
					if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
					{
						throw new WorkFlowException(
							WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
								+ WFConstants.MEMBER_OF_ROLES);
					}
					else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
					{
						throw new WorkFlowException(
							WFExceptionMessages
								.ROLE_CONTAINER_PARAMETER_IS_NULL);
					}
					else
					{
						throw new WorkFlowException(fex);
					}
				}

				try
				{
					organization =
						inContainer.getString(
							WFConstants.ACTIVITY_ORGANIZATION);
				}
				catch (FmcException fex)
				{
					if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
					{
						throw new WorkFlowException(
							WFExceptionMessages.CONTAINER_MEMBER_NOT_FOUND
								+ WFConstants.ACTIVITY_ORGANIZATION);
					}
					else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
					{
						throw new WorkFlowException(
							WFExceptionMessages
								.ORGANIZATION_CONTAINER_PARAMETER_IS_NULL);
					}
					else
					{
						throw new WorkFlowException(fex);
					}
				}

				Logger.debug(
					LOGGER_CONTEXT,
					"organization field retrieved from container: "
						+ organization);

				if (itemRole.equals(roleName))
				{
					lastHandler =
						new WFLastHandlerOfCustomer(
							items[i].owner(),
							organization);
				}
			}
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return findOwnerOfFirstWorkItemWithAppropriateRole(items, roleName);
		}

		return lastHandler;

	}

	/**
	 * When retrieving WorkItems from WorkFlow, this method is used during the
	 * replacement process of MQWF WorkItem with the structural WFWorkItem.
	 * @param inContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param outContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param globalContainerElementNames Names of the ContainerElements required
	 * by the application level. 
	 * @param workItem - The MQ WorkFlow WorkItem.
	 * @param wfService
	 * @param getExpirationTime should expirationTime be retrieved?
	 * @return WFWorkItem the created WFWorkItem.
	 * @throws FmcException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private WFWorkItem createWFWorkItem(
		String[] inContainerElementNames,
		String[] outContainerElementNames,
		String[] globalContainerElementNames,
		WorkItem workItem,
		ExecutionService wfService,
		boolean getExpirationTime)
		throws
			FmcException,
			WorkFlowException,
			WorkFlowLogicException,
			NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started createWFWorkItem from WorkItem");

		if (workItem == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		WFWorkItem wfWorkItem = new WFWorkItem();

		wfWorkItem.setType(WFConstants.WORKITEM_TYPE);

		wfWorkItem.setActivityName(workItem.name());
		Logger.debug(
			LOGGER_CONTEXT,
			"createWFWorkItem, workItem name: " + workItem.name());

		ReadOnlyContainer inContainer = workItem.inContainer();

		if (inContainer != null)
		{
			wfWorkItem.setProcessTemplateName(
				inContainer.getString(WFConstants.PROCESS_MODEL));
			Logger.debug(
				LOGGER_CONTEXT,
				"createWFWorkItem, ProcessTemplateName: "
					+ inContainer.getString(WFConstants.PROCESS_MODEL));

		}

		/*
		 * Getting the Containers Information.
		 * WFWorkItem has no global container.
		 */
		WorkFlowServiceParameterMap map = new WorkFlowServiceParameterMap();

		/*
		 * In case there are no elements to be retrieved from the container,
		 * don't access the containers.
		 */
		if (inContainerElementNames != null)
		{
			map =
				fillMap(
					inContainerElementNames,
					inContainer,
					WFConstants.CONTAINER_TYPE_ACTIVITY_OUTPUT_CONTAINER);
		}

		if (outContainerElementNames != null)
		{

			map.putAll(
				fillMap(
					outContainerElementNames,
					workItem.outContainer(),
					WFConstants.CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER));
		}

		String processInstanceId = workItem.persistentOidOfProcessInstance();
		Logger.debug(
			LOGGER_CONTEXT,
			"createWFWorkItem, processInstanceId: " + processInstanceId);

		ProcessInstance pInstance =
			wfService.persistentProcessInstance(processInstanceId);
		if (!pInstance.isComplete())
		{
			pInstance.refresh();
		}

		//Take the global container from the ProcessInstance.
		if (globalContainerElementNames != null)
		{
			map.putAll(
				fillMap(
					globalContainerElementNames,
					pInstance.globalContainer(),
					WFConstants.CONTAINER_TYPE_GLOBAL_CONTAINER));
		}

		Calendar processStartTime = pInstance.startTime();
		if (processStartTime != null)
		{
			wfWorkItem.setProcessStartTime(processStartTime.getTime());

		}

		// Adding the contents of the description field.
		String description = workItem.description();
		Logger.debug(
			LOGGER_CONTEXT,
			"createWFWorkItem, description: " + description);

		map.putAll(fillMapFromDescription(description));
		wfWorkItem.setParametersMap(map);

		wfWorkItem.setID(workItem.persistentOid());
		Logger.debug(
			LOGGER_CONTEXT,
			"createWFWorkItem, workItem id: " + workItem.persistentOid());

		wfWorkItem.setCategory(workItem.category());
		String ownerId = workItem.owner();
		wfWorkItem.setOwner(ownerId);

		Person itemOwner = wfService.persistentPerson(ownerId);
		if (!itemOwner.isComplete())
		{
			itemOwner.refresh();
		}
		String ownerName = itemOwner.firstName() + " " + itemOwner.lastName();
		wfWorkItem.setOwnerName(ownerName);
		Logger.debug(
			LOGGER_CONTEXT,
			"createWFWorkItem, workItem ownerName: " + ownerName);

		Calendar creationTime = workItem.creationTime();
		if (creationTime != null)
		{
			wfWorkItem.setCreationTime(creationTime.getTime());
		}
		wfWorkItem.setState(getState(workItem.state()));

		Calendar startTime = workItem.startTime();
		if (startTime != null)
		{
			wfWorkItem.setStartTime(startTime.getTime());
		}

		if (getExpirationTime)
		{
			Calendar expirationTime = workItem.expirationTime();
			if (expirationTime != null)
			{
				wfWorkItem.setEndTime(expirationTime.getTime());
			}
		}
		else
		{
			/*
			 * In case the workItem has already finished, the end time field should
			 * already be set!
			 */
			Calendar endTime = workItem.endTime();
			if (endTime != null)
			{
				wfWorkItem.setEndTime(endTime.getTime());
			}
		}

		//Parse the postpone time as Date.
		Date expectedEndTime = retrieveItemExpectedEndTime(description);
		wfWorkItem.setPostponeTime(expectedEndTime);
		wfWorkItem.setImplementation(workItem.implementation());

		Calendar lastModificationTime = workItem.lastModificationTime();
		if (lastModificationTime != null)
		{
			wfWorkItem.setLastModificationTime(lastModificationTime.getTime());
		}
		Calendar receivedTime = workItem.receivedTime();
		if (receivedTime != null)
		{
			wfWorkItem.setReceivedTime(receivedTime.getTime());
		}
		wfWorkItem.setProcessName(workItem.processInstanceName());
		wfWorkItem.setProcessID(processInstanceId);

		wfWorkItem.setPriority(getPriority(workItem));

		return wfWorkItem;

	}

	/**
	 * retrieveItemEndTime - assist method to retrieve the end time of a
	 * WFWorkItem from its description. This is the time the WFWorkItem 
	 * should end, if not finished yet.
	 * @param description
	 * @return Date the expected end time.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	private Date retrieveItemExpectedEndTime(String description)
		throws WorkFlowException, WorkFlowLogicException
	{

		/*
		 * If the description is null, no information about the expected
		 * end time can be retrieved. Checking for null value is done at
		 * getElementFromDescription().
		 */
		SystemResources systemResources = SystemResources.getInstance();

		String endTimeKey =
			systemResources.getProperty(
				WFConstants.DESCRIPTION_EXPECTED_END_TIME_KEY);

		String endTimeStr = getElementFromDescription(description, endTimeKey);

		Date endTime = null;
		/*
		 * if endTimeStr== null, the description exists, but there is no
		 * expected end time field in it. in this case, the returned result
		 * will be null. Otherwise, the result will be set in the if().
		 */

		if (endTimeStr != null)
		{

			String dateMask =
				systemResources.getProperty(WFConstants.DATE_MASK);
			try
			{
				endTime = DateFormatterUtil.parse(endTimeStr, dateMask);
			}
			catch (ParseException pex)
			{
				throw new WorkFlowException(pex);
			}

		}

		return endTime;

	}

	/**
	 * updateItemEndTime - assist method to update the expected end time of a
	 * WFWorkItem in its description. This is the time the WorkItem should end,
	 * if not finished yet.
	 * @param expectedEndTime - Passed as Date.
	 * @param description - is used to embed the new end time in the description.
	 * @return String the updated String to be put in the WFWorkItem's description.
	 * @throws WorkFlowException
	 */
	private String updateItemExpectedEndTime(
		Date expectedEndTime,
		String description)
		throws WorkFlowException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"updateItemExpectedEndTime, expectedEndTime: "
				+ expectedEndTime
				+ ", description: "
				+ description);

		/*
		 * If there is no expected end time, return the description passed
		 * with no changes.
		 */
		if (expectedEndTime == null)
		{
			return description;
		}
		/*
		 * Checking if the new time is later than current time (there is no 
		 * checking if it is later than the previous expected end date).
		 * Exception can be caused due to a user error (attempting to set the
		 * end time to time that had already passed).
		 */

		if (expectedEndTime.before(Calendar.getInstance().getTime()))
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages
					.CANNOT_POSTPONE_WORK_ITEM_DATE_THAT_ALEADY_PASSED,
				new Message(
					WFExceptionMessages
						.CANNOT_POSTPONE_WORK_ITEM_DATE_THAT_ALEADY_PASSED,
					Message.SEVERITY_ERROR));
		}

		// Have to format the date according to the predefined mask.
		SystemResources systemResources = SystemResources.getInstance();
		String dateMask = systemResources.getProperty(WFConstants.DATE_MASK);
		Logger.debug(
			LOGGER_CONTEXT,
			"updateItemExpectedEndTime, dateMask: " + dateMask);
		String endTimeStr = DateFormatterUtil.format(expectedEndTime, dateMask);
		if (endTimeStr == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.EXPECTED_END_TIME_NULL);
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"updateItemExpectedEndTime, endTimeStr: " + endTimeStr);

		String expectedTimeKey =
			systemResources.getProperty(
				WFConstants.DESCRIPTION_EXPECTED_END_TIME_KEY);

		return setElementInDescriptionString(
			description,
			expectedTimeKey,
			String.valueOf(endTimeStr));

	}

	/**
	 * When retrieving WorkItems from WorkFlow, this method is used during the
	 * replacement process of MQWF Notification with the structural WFWorkItem.
	 * @param notification - MQ WorkFlow ActivityInstanceNotification.
	 * @param wfService
	 * @param getExpirationTime should expirationTime be retrieved?
	 * @return WFWorkItem the created Notification item.
	 * @throws WorkFlowException either if a FmcException was thrown, or if
	 * 			the received ActivityInstance is not a notification.
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private WFWorkItem createWFWorkItem(
		ActivityInstanceNotification notification,
		ExecutionService wfService,
		boolean getExpirationTime)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started createWFWorkItem from ActivityInstanceNotification");

		if (notification == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		WFWorkItem workItem = new WFWorkItem();

		/*
		 * set the WFWorkItem type. In case the WFWorkItem is a notification,
		 * then it has no input/output containers.
		 */
		try
		{

			String description = notification.description();

			if ((notification.kind()
				== ItemType.FIRST_ACTIVITY_INSTANCE_NOTIFICATION)
				|| (notification.kind()
					== ItemType.SECOND_ACTIVITY_INSTANCE_NOTIFICATION))
			{

				/*
				 * Note: currently there is no separation between
				 * first notification and second notification.
				 */
				workItem.setType(WFConstants.NOTIFICATION_TYPE);

				//Notification has no Input/Output containers!
				// Adding the contents of the description field.
				workItem.setParametersMap(fillMapFromDescription(description));

			}
			else
			{ //Not a notification - unlikely to occur.
				throw new WorkFlowLogicException(
					WFExceptionMessages.ITEM_IS_NOT_A_NOTIFICATION,
					new Message(
						WFExceptionMessages.ITEM_IS_NOT_A_NOTIFICATION,
						Message.SEVERITY_ERROR));
			}

			workItem.setID(notification.persistentOid());
			workItem.setActivityName(notification.name());
			workItem.setCategory(notification.category());
			workItem.setOwner(notification.owner());
			Calendar creationTime = notification.creationTime();
			if (creationTime != null)
			{
				workItem.setCreationTime(creationTime.getTime());
			}

			String processInstanceId =
				notification.persistentOidOfProcessInstance();
			ProcessInstance pInstance =
				wfService.persistentProcessInstance(processInstanceId);
			if (!pInstance.isComplete())
			{
				pInstance.refresh();
			}
			Calendar processStartTime = pInstance.startTime();
			if (processStartTime != null)
			{
				workItem.setProcessStartTime(processStartTime.getTime());

			}

			workItem.setProcessName(notification.processInstanceName());
			workItem.setProcessID(processInstanceId);
			workItem.setProcessTemplateName(pInstance.processTemplateName());

			int priority = getPriority(notification);
			workItem.setPriority(priority);

			workItem.setState(getState(notification.state()));
			Calendar startTime = notification.startTime();
			if (startTime != null)
			{
				workItem.setStartTime(startTime.getTime());
			}

			//Parse the end time as Calendar.
			Date endTime =
				retrieveItemExpectedEndTime(notification.description());
			workItem.setPostponeTime(endTime);

			Calendar lastModificationTime = notification.lastModificationTime();
			if (lastModificationTime != null)
			{
				workItem.setLastModificationTime(
					lastModificationTime.getTime());
			}

			if (getExpirationTime)
			{
				Calendar expirationTime = notification.expirationTime();
				if (expirationTime != null)
				{
					workItem.setEndTime(expirationTime.getTime());
				}

			}
			else
			{
				/*
				 * In case the notification has already finished, the end time 
				 * field should already be set!
				 */
				Calendar actualEndTime = notification.endTime();
				if (actualEndTime != null)
				{
					workItem.setEndTime(actualEndTime.getTime());
				}
			}

			return workItem;
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return createWFWorkItem(notification, wfService, getExpirationTime);
		}
	}

	/**
	 * Is used to return the ExecutionState state (replace the WI state of
	 * MQWorkFlow with the infrastructure appropriate WI state).
	 * @param state - The MQ WorkFlow ExecutionState.
	 * @return int - The WFWorkItem state
	 * @throws WorkFlowException
	 */
	private int getState(com.ibm.workflow.api.ItemPackage.ExecutionState state)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started method getState");

		int returnedState = -1;

		if (state == null)
		{
			throw new WorkFlowException(
				WFExceptionMessages.EXECUTION_STATE_IS_NULL);
		}
		int stateValue = state.value();
		Logger.debug(LOGGER_CONTEXT, "getState, value: " + stateValue);

		if (stateValue
			== com.ibm.workflow.api.ItemPackage.ExecutionState._CHECKED_OUT)
		{
			returnedState = WFConstants.WORKITEM_STATE_CHECKED_OUT;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._RUNNING)
		{
			returnedState = WFConstants.WORKITEM_STATE_RUNNING;
		}

		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._TERMINATED)
		{
			returnedState = WFConstants.WORKITEM_STATE_TERMINATED;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._DELETED)
		{
			returnedState = WFConstants.WORKITEM_STATE_DELETED;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._DISABLED)
		{
			returnedState = WFConstants.WORKITEM_STATE_DISABLED;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._EXECUTED)
		{
			returnedState = WFConstants.WORKITEM_STATE_EXECUTED;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._EXPIRED)
		{
			returnedState = WFConstants.WORKITEM_STATE_EXPIRED;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._FINISHED)
		{
			returnedState = WFConstants.WORKITEM_STATE_FINISHED;
		}
		else if (
			stateValue
				== com
					.ibm
					.workflow
					.api
					.ItemPackage
					.ExecutionState
					._FORCE_FINISHED)
		{
			returnedState = WFConstants.WORKITEM_STATE_FORCE_FINISHED;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._IN_ERROR)
		{
			returnedState = WFConstants.WORKITEM_STATE_IN_ERROR;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._PLANNING)
		{
			returnedState = WFConstants.WORKITEM_STATE_PLANNING;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._READY)
		{
			returnedState = WFConstants.WORKITEM_STATE_READY;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._SUSPENDED)
		{
			returnedState = WFConstants.WORKITEM_STATE_SUSPENDED;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._SUSPENDING)
		{
			returnedState = WFConstants.WORKITEM_STATE_SUSPENDING;
		}

		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._TERMINATING)
		{
			returnedState = WFConstants.WORKITEM_STATE_TERMINATING;
		}
		else if (
			stateValue
				== com.ibm.workflow.api.ItemPackage.ExecutionState._UNDEFINED)
		{
			returnedState = WFConstants.WORKITEM_STATE_UNDEFINED;
		}
		/*
		 * If reached here, and the returnedState is still -1,
		 * the state is not known!
		 */
		if (returnedState == -1)
		{
			throw new WorkFlowException(
				WFExceptionMessages.ILLEGAL_WORK_ITEM_STATE);
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"state of the WorkItem is: " + returnedState);

		return returnedState;
	}

	/**
	 * Return all pre-defined filters in the system.
	 * @return WFWorkList[] list of filters (the list can be null
	 * if no pre-defined filters exist).
	 * @throws WorkFlowException
	 * @see com.ness.fw.workflow.WorkFlowService#retrieveFilterNames()
	 */
	public WFWorkList[] retrieveFilterNames()
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started retrieveFilterNames");

		try
		{
			//List of filters in the WorkFlow.
			WorkList[] workListsArray = getExecutionService().queryWorkLists();

			if (workListsArray == null)
			{
				return null;
			}

			WFWorkList[] wfWorkListsArray =
				new WFWorkList[workListsArray.length];

			for (int i = 0; i < wfWorkListsArray.length; i++)
			{
				wfWorkListsArray[i] = createWFWorkList(workListsArray[i]);
			}
			return wfWorkListsArray;

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return retrieveFilterNames();
		}
	}

	/**
	 * Creating WFWorkList.
	 * @param list WorkFlow WorkList
	 * @return WFWorkList Wraps only list name and ID.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private WFWorkList createWFWorkList(WorkList list)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(LOGGER_CONTEXT, "started createWFWorkList");

		if (list == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_LIST_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_LIST_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		try
		{

			return new WFWorkList(list.name(), list.persistentOid());

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return createWFWorkList(list);
		}
	}

	/**
	 * Assistance method - create a container from WFSParametersMap.
	 * This allows saving of the applicative data from WFSParametersMap
	 * into the container.
	 * @param parametersMap - WorkFlowServiceParameterMap
	 * @param container The container to be updated
	 * @return ReadWriteContainer - The updated container.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	private ReadWriteContainer setContainerBufferFromMap(
		WorkFlowServiceParameterMap parametersMap,
		ReadWriteContainer container)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		if (container == null)
		{
			throw new WorkFlowException(WFExceptionMessages.CONTAINER_IS_NULL);
		}
		else if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		ByteArrayOutputStream baStream = new ByteArrayOutputStream();
		ObjectOutputStream stream = null;
		try
		{

			stream = new ObjectOutputStream(baStream);
		}
		catch (IOException iex)
		{
			throw new WorkFlowException(iex);
		}
		/*
		 * Write the values from the containerFields map to
		 * the container. The operation assumes that the parameters
		 * exist in the container, otherwise they are stored in
		 * the BinaryMap field of it.
		 */
		Map map = parametersMap.getAllParameters();
		Logger.debug(
			LOGGER_CONTEXT,
			"setContainerBufferFromMap, parametersMap: " + map);

		Collection collection = map.values();
		Iterator iterator = collection.iterator();
		//Attempt to pass the value to the exact field in the container.

		while (iterator.hasNext())
		{
			WorkFlowServiceParameter nextParameter =
				(WorkFlowServiceParameter) iterator.next();

			try
			{
				handleParameterThatExistsInContainer(
					nextParameter,
					container,
					nextParameter.getName());
				Logger.debug(
					LOGGER_CONTEXT,
					"setContainerBufferFromMap, wrote parameter: "
						+ nextParameter);

				//The parameter name was not predefined - insert it to binary.
			}
			catch (FmcException fex)
			{
				Logger.debug(
					LOGGER_CONTEXT,
					"setContainerBufferFromMap, exception occured: "
						+ fex.rc
						+ ", writing parameter to stream");

				try
				{
					stream.writeObject(nextParameter);
				}
				catch (IOException iex)
				{
					throw new WorkFlowException(iex);
				}
			}

		}

		//Take care of the binary field.
		//This field will hold all values not found in the container.
		//Writing to the binaryMap is optional.
		byte[] binaryMap = baStream.toByteArray();
		try
		{
			if (binaryMap != null)
			{
				container.setBuffer(WFConstants.BINARY_MAP, binaryMap);
				Logger.debug(
					LOGGER_CONTEXT,
					"setContainerBufferFromMap, wrote to binaryMap: " + " ");

			}

		}
		catch (FmcException fex)
		{
			if (fex.rc != FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				throw new WorkFlowException(fex);
			}
		}
		/*
		 * The container should have an element named "BinaryMap", 
		 * otherwise an error will occur.
		 */
		return container;

	}

	/**
	 * Assistance method of setContainerBufferFromMap().
	 * This method sets the given parameter in the container.
	 * @param nextParameter - the parameter to set.
	 * @param container - The container to update.
	 * @param parameterName - name of the parameter in the container.
	 * @throws FmcException
	 * @throws NullParametersException
	 */
	private void handleParameterThatExistsInContainer(
		WorkFlowServiceParameter nextParameter,
		ReadWriteContainer container,
		String parameterName)
		throws FmcException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started method handleParameterThatExistsInContainer");

		//depends on the object type: String, Double, Long, Other
		if (nextParameter.getObjectType() == WFConstants.OBJECT_TYPE_STRING)
		{
			//put the object as String 
			Logger.debug(
				LOGGER_CONTEXT,
				"setting parameter "
					+ parameterName
					+ " to context as a string"
					+ ", parameterValue: "
					+ nextParameter.getValue());

			container.setString(parameterName, nextParameter.getStringValue());
			Logger.debug(
				LOGGER_CONTEXT,
				"performed setString() for parameter: " + parameterName);

		}
		else if (nextParameter.getObjectType() == WFConstants.OBJECT_TYPE_LONG)
		{
			//put the object as Long
			Logger.debug(
				LOGGER_CONTEXT,
				"setting parameter "
					+ parameterName
					+ " to context as a long"
					+ ", parameterValue: "
					+ nextParameter.getValue());

			container.setLong(parameterName, nextParameter.getLongValue());
			Logger.debug(
				LOGGER_CONTEXT,
				"performed setLong() for parameter: " + parameterName);

		}
		else if (
			nextParameter.getObjectType() == WFConstants.OBJECT_TYPE_DOUBLE)
		{
			//put the object as Double
			Logger.debug(
				LOGGER_CONTEXT,
				"setting parameter "
					+ parameterName
					+ " to context as a double"
					+ ", parameterValue: "
					+ nextParameter.getValue());

			container.setDouble(parameterName, nextParameter.getDoubleValue());
			Logger.debug(
				LOGGER_CONTEXT,
				"performed setDouble() for parameter: " + parameterName);

		}
		else
		{
			//The object will be put as String
			Logger.debug(
				LOGGER_CONTEXT,
				"Object type not found, setting parameter "
					+ parameterName
					+ " to context as a string"
					+ ", parameterValue: "
					+ nextParameter.getValue());

			container.setString(parameterName, nextParameter.getStringValue());
		}

	}

	/**
	 * Assistance method - create a WorkFlowServiceParameterMap from a container.
	 * This allows saving of the applicative data from the container into the
	 * WorkFlowServiceParameterMap, and pass applicative data between
	 * WorkItems (keeping it in the buffer of the outContainer of the first
	 * WI, and taking it from the buffer of the inContainer of the second WI).
	 * The method handles ONLY elements in the container buffer. another 
	 * elements are handled in the method "handleContainerElement".
	 * The method will be called each time a container is required (E.g.
	 * retrieveTasks).
	 * @param container - Container (can be input, output or global one).
	 * @return WorkFlowServiceParameterMap
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	private WorkFlowServiceParameterMap setMapFromContainerBuffer(Container container)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started setMapFromContainerBuffer (reading values from the container to a new parametersMap)");

		if (container == null)
		{
			throw new WorkFlowException(WFExceptionMessages.CONTAINER_IS_NULL);
		}
		WorkFlowServiceParameterMap parametersMap =
			new WorkFlowServiceParameterMap();

		byte[] binaryMap = null;

		try
		{

			binaryMap = container.getBuffer(WFConstants.BINARY_MAP);

		}
		catch (FmcException fex)
		{

			/*
			 * if the exception is FMC00113E Container member not set - it is OK,
			 * (it's not mandatory to supply applicative parameters each time).
			 * If the exception is another one - it's a real error, like if 
			 * there is no such container element at all. In this case, the 
			 * exception is wrapped and thrown again.
			 */
			if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_FOUND)
			{
				return null;
			}
			else if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
				if (fex.rc == FmcException.FMC_ERROR_MEMBER_NOT_SET)
				{
					return null;
				}
				else
				{
					throw new WorkFlowException(fex);
				}
		}

		ByteArrayInputStream baStream = new ByteArrayInputStream(binaryMap);

		ObjectInputStream stream = null;

		try
		{

			stream = new ObjectInputStream(baStream);
		}
		catch (IOException iex)
		{
			throw new WorkFlowException(iex);
		}

		/*
		 * The stream is expected to contain ONLY objects from type 
		 * WorkFlowServiceParameter. If it is not the case, 
		 * ClassCastException will be thrown.
		 */
		WorkFlowServiceParameter serviceParameter = null;

		try
		{
			serviceParameter = (WorkFlowServiceParameter) stream.readObject();

		}

		catch (IOException iex)
		{
			throw new WorkFlowException(iex);
		}
		catch (ClassNotFoundException cnfex)
		{
			throw new WorkFlowException(cnfex);
		}
		parametersMap.add(serviceParameter);

		return parametersMap;

	}

	/**
	 * Retrieve names of existing ProcessTemplates in the system.
	 * @param parametersMap - WorkFlowServiceParameterMap, that will contain
	 * the appropriate query parameters (filter, sortCriteria, threshold).
	 * @return String[] - names of existing ProcessTemplates in the system.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public String[] getProcessTemplateNames(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started getProcessTemplateNames, parametersMap: " + parametersMap);

		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"getProcessTemplateNames, received parametersMap values: "
				+ parametersMap.getAllParameters());

		//Default values for the filter and sortCriteria.
		String filter = WFConstants.NAME_IS_NOT_NULL;
		String sortCriteria = WFConstants.NAME_ASC;

		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.FILTER);

		if (parameter != null)
		{
			if (parameter.getStringValue() != null)
			{
				filter = parameter.getStringValue();
			}
		}

		parameter = parametersMap.get(WFConstants.SORT_CRITERIA);

		if (parameter != null)
		{
			if (parameter.getStringValue() != null)
			{
				sortCriteria = parameter.getStringValue();
			}
		}

		parameter = parametersMap.get(WFConstants.PROCESS_TEMPLATES_THRESHOLD);
		String thresholdAsString = null;
		Integer threshold = null;
		if (parameter != null)
		{
			thresholdAsString = parameter.getStringValue();
		}

		if (thresholdAsString == null)
		{
			thresholdAsString =
				SystemResources.getInstance().getProperty(
					WFConstants.PROCESS_TEMPLATES_THRESHOLD_KEY);

		}

		threshold = Integer.valueOf(thresholdAsString);

		try
		{

			ProcessTemplate templates[] =
				getExecutionService().queryProcessTemplates(
					filter,
					sortCriteria,
					threshold);

			String[] templateNames = new String[templates.length];

			for (int i = 0; i < templates.length; i++)
			{
				templateNames[i] = templates[i].name();
			}

			return templateNames;
		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getProcessTemplateNames(parametersMap);
		}
	}

	/**
	 * This method returns the role of the first activity in the template
	 * identified by the given parameter.
	 * @param processTemplateID - ID of the given ProcessTemplate.
	 * @return String the role of the first activity in the ProcessTemplate
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public String getRoleOfFirstActivityInProcessTemplate(String processTemplateID)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started getRoleOfFirstActivityInProcessTemplate, received processTemplateId: "
				+ processTemplateID);

		if (processTemplateID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_TEMPLATE_ID_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_TEMPLATE_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		try
		{

			ProcessTemplate template =
				getExecutionService().persistentProcessTemplate(
					processTemplateID);
			if (template == null)
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.PROCESS_TEMPLATE_IS_NULL,
					new Message(
						WFExceptionMessages.PROCESS_TEMPLATE_IS_NULL,
						Message.SEVERITY_ERROR));
			}

			return template.roleName();

		}
		catch (FmcException fex)
		{
			handleFmcException(fex);
			//will be reachable only in case of time-out
			return getRoleOfFirstActivityInProcessTemplate(processTemplateID);
		}

	}

	/**
	 * This method is used to retrieve the specified element from
	 * the description.
	 * The parameters are checked in the calling method.
	 * @param description - the description.
	 * @param elementName - name of the element to get
	 * @return String - the element retrieved from the description 
	 * (can be null).
	 * @throws WorkFlowLogicException
	 */
	private String getElementFromDescription(
		String description,
		String elementName)
		throws WorkFlowLogicException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"started getElementFromDescription - Accessing description, description is: "
				+ description
				+ ", retrieved element name is: "
				+ elementName);

		if (description == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.DESCRIPTION_FIELD_NULL,
				new Message(
					WFExceptionMessages.DESCRIPTION_FIELD_NULL,
					Message.SEVERITY_ERROR));
		}

		Map map =
			StringFormatterUtil.convertStringToMap(
				description,
				WFConstants.DESCRIPTION_KEY_VALUE_DELIMITER,
				WFConstants.DESCRIPTION_PAIRS_DELIMITER);
		if (map == null)
		{
			return null;
		}
		return (String) map.get(elementName);
	}

	/**
	 * This method is used to set the specified element's value at
	 * the description.
	 * The parameters are checked in the calling method.
	 * @param description - description field (can come from ProcessInstance,
	 * WorkItem, Person or another applicable classes).
	 * @param elementName - name the element to set (it's key).
	 * @param elementValue - The value to set for this element in the
	 * description.
	 * @return String - the element retrieved from the description (can be null).
	 */
	private String setElementInDescriptionString(
		String description,
		String elementName,
		String elementValue)
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started setElementInDescriptionString, description: "
				+ description
				+ ", elementName: "
				+ elementName
				+ ", elementValue: "
				+ elementValue);
		/*
		 * If the element is invalid, don't change the passed
		 * description (even if it was null before).
		 */
		if ((elementName == null) || (elementValue == null))
		{
			return description;
		}
		/*
		 * In case the description is null, perform lazy initialization - 
		 * return a new description composed just of the new element.
		 */
		if (description == null)
		{
			return elementName
				+ WFConstants.DESCRIPTION_KEY_VALUE_DELIMITER
				+ elementValue;
		}
		/*
		 * Generate a StringBuffer to take care of addition to the
		 * Description.
		 */
		StringBuffer buffer = new StringBuffer(128);
		buffer.append(description);

		/*
		 * This index represents the key part of the key-value pair.
		 * E.g. "Key=value;Key1=value1", if the elementName is "Key1",
		 * the index will be the index of the substring "Key1=".
		 */
		int indexOfString =
			description.indexOf(
				elementName + WFConstants.DESCRIPTION_KEY_VALUE_DELIMITER);
		/*
		 * If the added element is missing, just append it
		 * to the Description String. Don't forget to precede it
		 * with the DESCRIPTION_PAIRS_DELIMITER.
		 */
		if (indexOfString == -1)
		{
			buffer.append(WFConstants.DESCRIPTION_PAIRS_DELIMITER);
			buffer.append(elementName);
			buffer.append(WFConstants.DESCRIPTION_KEY_VALUE_DELIMITER);
			buffer.append(elementValue);
		}
		else
		{

			/*
			 * Calculate the exact part to replace (previous value of the
			 * element).
			 */
			int sizeToPass =
				elementName.length()
					+ WFConstants.DESCRIPTION_KEY_VALUE_DELIMITER.length();
			/*
			 * The part to be replaced is between thr current 
			 * DESCRIPTION_KEY_VALUE_DELIMITER and DESCRIPTION_PAIRS_DELIMITER.
			 */
			int indexOfNextDelimiter =
				description.indexOf(
					WFConstants.DESCRIPTION_PAIRS_DELIMITER,
					indexOfString + sizeToPass);
			/*
			 * There are two cases to be considered:
			 * 1. The replaced string is last in the description
			 * (no pairs delimiter comes after it).
			 * 2. It isn't the last part of the description
			 */
			if (indexOfNextDelimiter == -1)
			{
				//should ensure the structure key-value
				buffer.replace(
					indexOfString + sizeToPass,
					description.length(),
					elementValue);
			}
			else
			{
				buffer.replace(
					indexOfString + sizeToPass,
					indexOfNextDelimiter,
					elementValue);
			}
		}

		return buffer.toString();

	}

	/**
	 * Create Map from input container
	 * @param container inputContainer
	 * @return WorkFlowServiceParameterMap the created ParameterMap
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	private WorkFlowServiceParameterMap fillMapFromDescription(String description)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException
	{

		if (description == null)
		{
			return null;
		}

		// Break the String into the key-value tokens.
		StringTokenizer tokenizer =
			new StringTokenizer(
				description,
				WFConstants.DESCRIPTION_PAIRS_DELIMITER);

		int count = tokenizer.countTokens();
		WorkFlowServiceParameterMap parametersMap =
			new WorkFlowServiceParameterMap();

		String keyValue = null;
		StringTokenizer tokenizer2 = null;
		/*
		 * For each key-value token, break it into the key and
		 * the value, and put both of them in the HashMap.
		 */
		WorkFlowServiceParameter parameter = null;
		for (int i = 0; i < count; i++)
		{
			keyValue = tokenizer.nextToken().trim();
			tokenizer2 =
				new StringTokenizer(
					keyValue,
					WFConstants.DESCRIPTION_KEY_VALUE_DELIMITER);
			if (tokenizer2 == null)
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.DESCRIPTION_KEY_VALUE_DELIMITER_IS_NULL,
					new Message(
						WFExceptionMessages
							.DESCRIPTION_KEY_VALUE_DELIMITER_IS_NULL,
						Message.SEVERITY_ERROR));
			}
			/*
			 * All the parameters in the description are assumed to
			 * be of type string.
			 */
			parameter =
				new WorkFlowServiceParameter(
					tokenizer2.nextToken().trim(),
					WFConstants.DATA_TYPE_PROCESS_INSTANCE_DESCRIPTION,
					WFConstants.OBJECT_TYPE_STRING,
					WFConstants.ARGUMENT_TYPE_INPUT_OUTPUT,
					tokenizer2.nextToken().trim());
			parametersMap.add(parameter);
		}
		Logger.debug(
			LOGGER_CONTEXT,
			"fillMapFromDescription, generated parametersMap values: "
				+ parametersMap.getAllParameters());

		return parametersMap;
	}

	/**
	 * 
	 * @param role
	 * @param organization
	 * @return
	 * @throws LdapException
	 */
	private String getVirtualUserId(String role, String organization)
		throws LdapException
	{
		//return ldapManager.findVirtualUserId(role, organization);	
		return ldapManager.createVirtualUserName(role, organization);
	}

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

			managerPool.setTestOnBorrow(true);
		}
		catch (PoolException poex)
		{
			throw new WorkFlowException(poex);
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable
	{
		super.finalize();
		if (executionService != null)
		{
			logoff();
		}

	}

}