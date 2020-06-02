/*
 * Created on: 20/05/2004
 * Author Amit Mendelson
 * @version $Id: WorkFlowFlowerService.java,v 1.9 2005/05/04 13:54:13 amit Exp $
 */
package com.ness.fw.shared.flower.workflow;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.util.IDPagingService;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.core.Service;
import com.ness.fw.flower.core.ServiceException;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.flower.util.ApplicationUtil;

import com.ness.fw.util.MessagesContainer;
import com.ness.fw.workflow.*;

import java.util.Set;
import java.util.Date;

/**
 * This class supports the methods to connect from Flower service to
 * the WorkFlow module.
 */
public class WorkFlowFlowerService extends Service
{
	
	/**
	 * Logger context for writing to the Log.
	 */
	private static final String LOGGER_CONTEXT = "Workflow Service";

	/**
	 * wfService - Local instance of WorkFlowService. 
	 */
	private WorkFlowService wfService;

	/* (non-Javadoc)
	 * @see com.ness.fw.flower.core.Service#initialize(
	 * com.ness.fw.flower.core.ParameterList)
	 */
	public void initialize(ParameterList parameters) throws ServiceException
	{

		try
		{
			wfService = WorkFlowServiceFactory.getInstance().getWorkFlowService();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.flower.core.Service#destroy()
	 */
	public void destroy()
	{

	}

	/**
	 * singleSignOnLogon - this method should be called each time a user logs in to the
	 *  system, in order to receive an WFExecutionService object. 
	 * @param context. should include the user & password parameters.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void singleSignOnLogon(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String userID = context.getString(WFFlowerConstants.SINGLE_SIGNON_LOGON_USER_ID);
			UserCredentials userCredentials = 
				(UserCredentials)context.getField(WFFlowerConstants.SINGLE_SIGNON_LOGON_USER_CREDENTIALS);
			wfService.logon(userCredentials, userID);

		}
		catch (AgentException aex)
		{
			throw new ServiceException(WFExceptionMessages.AGENT_PROBLEM, aex);

		}
		catch (ContextException cex)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, cex);

		}
		catch (NullParametersException npex)
		{
			handleMessagesContainer(npex.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException wfx)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, wfx);

		}

	}

	/**
	 * logon - this method should be called each time a user logs in to the
	 *  system, in order to receive an WFExecutionService object. 
	 * @param context. should include the user & password parameters.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void logon(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String userID = context.getString(WFFlowerConstants.LOGON_USER_ID);
			String password = context.getString(WFFlowerConstants.LOGON_PASSWORD);
			wfService.logon(userID, password);

		}
		catch (AgentException aex)
		{
			throw new ServiceException(WFExceptionMessages.AGENT_PROBLEM, aex);

		}
		catch (ContextException cex)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, cex);

		}
		catch (NullParametersException npex)
		{
			handleMessagesContainer(npex.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException wfx)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, wfx);

		}

	}
	
	/**logoff - allows the application to finish the specified user
	 *  session with an MQ Workflow execution service.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 */
	public void logoff(Context context, ParameterList parameterList)
		throws ServiceException
	{
		try
		{
			wfService.logoff();
		}
		catch (WorkFlowException wfx)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, wfx);
		}
	}

	/**
	 * exit - will be called on the system shutdown.
	 * will destroy the created Agent?
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 */
	public void exit(Context context, ParameterList parameterList)
		throws ServiceException
	{

		try
		{
			wfService.exit();
		}
		catch (WorkFlowException wfx)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, wfx);
		}
	}

	/**
	 * Retrieve all tasks (WorkItems and ActivityInstanceNotifications) that
	 * fulfill a given filter.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveTasks(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{
			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.RETRIEVE_TASKS_PARAMETER_MAP);
			String predefinedFilterID = 
				context.getString(WFFlowerConstants.RETRIEVE_TASKS_PREDEFINED_FILTER_ID);
			WFWorkItemList list = wfService.retrieveTasks(parametersMap, predefinedFilterID);
			context.setField(WFFlowerConstants.RETRIEVE_TASKS_LIST, list);

		}

		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();

		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}


	}

	/**
	 * Retrieve all task Ids (WorkItems and ActivityInstanceNotifications) that
	 * fulfill a given filter.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveTasksIds(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{
			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.RETRIEVE_TASKS_IDS_PARAMETER_MAP);
			String predefinedFilterID = 
				context.getString(WFFlowerConstants.RETRIEVE_TASKS_IDS_PREDEFINED_FILTER_ID);
			Integer numOfRowsInPageAsInteger = 
				context.getInteger(WFFlowerConstants.RETRIEVE_TASKS_IDS_ROWS_IN_PAGE);
			int numOfRowsInPage = numOfRowsInPageAsInteger.intValue();
			IDPagingService taskIds = 
				wfService.retrieveTasksIds(parametersMap, predefinedFilterID, numOfRowsInPage);
			context.setField(WFFlowerConstants.RETRIEVE_TASKS_IDS_PAGING_OBJECT, taskIds);

		}

		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();

		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Allows retrieval of single WFWorkItem from the Workflow.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getWorkItem(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.GET_TASK_WORK_ITEM_ID);
			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.GET_TASK_PARAMETER_MAP);

			WFWorkItem item = wfService.getWorkItem(workItemID, parametersMap);
			context.setField(WFFlowerConstants.GET_TASK_RETRIEVED_WORK_ITEM, item);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException cex)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, cex);

		}

		catch (WorkFlowLogicException wflex)
		{
			handleMessagesContainer(wflex.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException wfx)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, wfx);

		}

	}

	/**
	 * Allows retrieval of multiple WFWorkItems from the Workflow.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getWorkItems(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			IDPagingService workItemIDs =
				(IDPagingService)context.getField(WFFlowerConstants.GET_TASKS_PAGING_OBJECT);
			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.GET_TASKS_PARAMETER_MAP);

			WFWorkItemList items = wfService.getWorkItems(workItemIDs, parametersMap);
			context.setField(WFFlowerConstants.GET_TASKS_RETRIEVED_WORK_ITEMS, items);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException cex)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, cex);

		}

		catch (WorkFlowLogicException wflex)
		{
			handleMessagesContainer(wflex.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException wfx)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, wfx);

		}

	}

	/**
	 * checking out the selected activity.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void checkOut(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.CHECK_OUT_WORK_ITEM_ID);

			WorkFlowServiceParameterMap paramsMap =
				(WorkFlowServiceParameterMap)context.getField(WFFlowerConstants.CHECK_OUT_PARAMS_MAP);

			WorkFlowServiceParameterMap checkOutResult =
				wfService.checkOut(workItemID, paramsMap);

			context.setField(WFFlowerConstants.CHECK_OUT_DATA, checkOutResult);


		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);

		}

	}

	/**
	 * This method allows checking-in of a WorkItem from the application level.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void checkIn(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.CHECK_IN_WORK_ITEM_ID);
			WorkFlowServiceParameterMap parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.CHECK_IN_MAP);
			wfService.checkIn(workItemID, parametersMap);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();

		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}


	}

	/**
	 * Cancels check out of the selected WFWorkItem.
	 * This method does not return the item to the previous owner
	 * (before the checking-out).
	 * @param context.
	 * @param parametersList
	 * @return true if check-out was cancelled, false otherwise.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void cancelCheckOut(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.CANCEL_CHECK_OUT_WORK_ITEM_ID);
			wfService.cancelCheckout(workItemID);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}


	}

	/**
	 * This method allows the user to postpone a WFWorkItem completion date
	 * to a specified date in the future.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void postponeWorkItem(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.POSTPONE_WORK_ITEM_ID);
			Date expectedEndTime =
				(Date) context.getField(WFFlowerConstants.POSTPONE_WORK_ITEM_DATE);
			Boolean wasItemPostponed = 
				Boolean.valueOf(wfService.postponeWorkItem(workItemID, expectedEndTime));

			context.setField(WFFlowerConstants.POSTPONE_WORK_ITEM_OUTPUT, wasItemPostponed);
			
		}

		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();

		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method is used to set the priority of the selected ProcessInstance.
	 * @param context.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void setPriorityOfProcessInstance(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String[] processInstanceIds =
				(String[])context.getField(WFFlowerConstants.SET_PRIORITY_PROCESS_INSTANCE_IDS);
			Integer newPriorityAsInteger = 
				context.getInteger(WFFlowerConstants.SET_PRIORITY_PRIORITY);
			int newPriority = newPriorityAsInteger.intValue();
			wfService.setPriorityOfProcessInstance(
				processInstanceIds,
				newPriority);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();

		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method is used to set the priority of the selected ProcessInstance.
	 * @param context.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void setPriorityOfWorkItem(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String[] workItemIds =
				(String[])context.getField(WFFlowerConstants.SET_PRIORITY_OF_WORK_ITEM_WORK_ITEM_IDS);

			Integer newPriorityAsInteger = 
				context.getInteger(WFFlowerConstants.SET_PRIORITY_OF_WORK_ITEM_PRIORITY);
			int newPriority = newPriorityAsInteger.intValue();
			wfService.setPriorityOfWorkItem(workItemIds,	newPriority);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();

		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}


	/**
	 * Allows retrieval of single WFProcessInstance from the Workflow.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getProcessInstance(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String processInstanceID =
				context.getString(WFFlowerConstants.GET_PROCESS_INSTANCE_PROCESS_ID);

			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.GET_PROCESS_INSTANCE_PARAMETERS_MAP);

			WFProcessInstance processInstance =
				wfService.getProcessInstance(processInstanceID, parametersMap);

			context.setField(WFFlowerConstants.GET_PROCESS_INSTANCE_RETRIEVED_PROCESS, processInstance);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);

		}
	}

	/**
	 * Allows retrieval of multiple WFProcessInstances from the Workflow.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getProcessInstances(Context context,ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			IDPagingService processInstancesPagingService =
				(IDPagingService)context.getField(WFFlowerConstants.GET_PROCESS_INSTANCES_PAGING_SERVICE);

			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.GET_PROCESS_INSTANCES_PARAMETERS_MAP);

			WFProcessInstanceList processInstances =
				wfService.getProcessInstances(processInstancesPagingService, parametersMap);

			context.setField(WFFlowerConstants.GET_PROCESS_INSTANCES_RETRIEVED_PROCESSES, processInstances);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);

		}
	}

	/**
	 * Allows retrieval of single WFProcessInstance from the Workflow
	 * by the processInstance name.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getProcessInstanceByName(
		Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String processInstanceName =
				context.getString(WFFlowerConstants.GET_PROCESS_INSTANCE_BY_NAME_PROCESS_NAME);

			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.GET_PROCESS_INSTANCE_BY_NAME_PARAMETERS_MAP);

			WFProcessInstance processInstance =
				wfService.getProcessInstanceByName(processInstanceName, parametersMap);

			context.setField(WFFlowerConstants.GET_PROCESS_INSTANCE_BY_NAME_RETRIEVED_PROCESS, processInstance);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);

		}
	}

	/**
	 * This method returns a WorkItem to the virtual user to whom
	 * it belonged before the current user (or should belong).
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void returnWorkItemToGeneralQueue(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.RETURN_WORK_ITEM_TO_QUEUE_WORK_ITEM_ID);
			wfService.returnWorkItemToGeneralQueue(workItemID);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieve all ProcessInstances that fulfill a given filter.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveProcesses(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.RETRIEVE_PROCESSES_RETRIEVE_PROCESSES_MAP);

			WFProcessInstanceList instances =
				wfService.retrieveProcesses(parametersMap);

			context.setField(WFFlowerConstants.RETRIEVE_PROCESSES_RETRIEVED_PROCESSES, instances);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}


	/**
	 * Retrieve all Ids of ProcessInstances that fulfill a given filter.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveProcessIds(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.RETRIEVE_PROCESS_IDS_RETRIEVE_PROCESSES_MAP);
			Integer numOfRowsInPageAsInteger = 
				context.getInteger(WFFlowerConstants.RETRIEVE_PROCESS_IDS_ROWS_IN_PAGE);

			int numOfRowsInPage = numOfRowsInPageAsInteger.intValue();

			IDPagingService pagingService =
				wfService.retrieveProcessIds(parametersMap, numOfRowsInPage);

			context.setField(WFFlowerConstants.RETRIEVE_PROCESS_IDS_RETRIEVED_PAGING_OBJECT, pagingService);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method is used when looking for an appropriate
	 * substitute for this user. The substitute must be a real user.
	 * A super-virtual user is defined in advance in the workflow process.
	 * This user has no authorizations for others, and all real users should
	 * be authorized for him, so calling the method "personsAuthorizedForMe" 
	 * should return all real users in the system.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException.
	 * @throws ValidationException
	 */
	public void retrieveUsersForSelectSubstitute(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String[] users = wfService.retrieveUsersForSelectSubstitute();

			context.setField(WFFlowerConstants.RETRIEVE_USERS_FOR_SELECT_SUBSTITUTE_USERS_ARRAY, users);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);

		}

	}

	/**
	 * Routing of the WFWorkItem.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void routeWorkItem(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.ROUTE_WORK_ITEM_WORK_ITEM_ID);
			String targetUserID =
				context.getString(WFFlowerConstants.ROUTE_WORK_ITEM_TARGET_USER_ID);
			Boolean trackWorkItem =
				context.getBoolean(WFFlowerConstants.ROUTE_WORK_ITEM_TRACK_WORK_ITEM);
			wfService.routeWorkItem(workItemID, targetUserID, trackWorkItem.booleanValue());
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieval of users for the routing (does not route the task - 
	 * just indicates who is elligible to take care of it).
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveUsersDetailsForWorkItemRouting(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.RETRIEVE_USERS_DETAILS_FOR_WORK_ITEM_ROUTING_WORK_ITEM_ID);

			Set usersSet =
				wfService.retrieveUsersDetailsForWorkItemRouting(workItemID);

			context.setField(WFFlowerConstants.RETRIEVE_USERS_DETAILS_FOR_WORK_ITEM_ROUTING_USERS_SET, usersSet);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieval of users for the routing (does not route the task - 
	 * just indicates who is elligible to take care of it).
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveUsersForWorkItemRouting(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.RETRIEVE_USERS_FOR_WORK_ITEM_ROUTING_WORK_ITEM_ID);

			Set usersSet =
				wfService.retrieveUsersForWorkItemRouting(workItemID);

			context.setField(WFFlowerConstants.RETRIEVE_USERS_FOR_WORK_ITEM_ROUTING_USERS_SET, usersSet);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method sets a substitute for the logged-on user.
	 * @see com.ness.fw.workflow.WorkFlowService#substitute(String)
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void setUserSubstitute(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String userID = context.getString(WFFlowerConstants.SET_USER_SUBSTITUTE_USER_ID);

			String substitute =
				context.getString(WFFlowerConstants.SET_USER_SUBSTITUTE_SUBSTITUTE_ID);
			wfService.setUserSubstitute(userID, substitute);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}
	}

	/**
	 * This method sets a substitute for the logged-on user.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void setCurrentUserSubstitute(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String substitute =
				context.getString(WFFlowerConstants.SET_CURRENT_USER_SUBSTITUTE_SUBSTITUTE_ID);
			wfService.setCurrentUserSubstitute(substitute);
		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Returns the current substitute of the selected user.
	 * If this user has no substitute, null value is returned.
	 * Anyone who uses this method should check its return value for null value.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getUserSubstitute(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String userID = context.getString(WFFlowerConstants.GET_USER_SUBSTITUTE_USER_ID);

			String substitute = wfService.getUserSubstitute(userID);

			context.setField(WFFlowerConstants.GET_USER_SUBSTITUTE_SUBSTITUTE_ID, substitute);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Returns the current substitute of the current user.
	 * If this user has no substitute, null value is returned.
	 * Anyone who uses this method should check its return value for null value.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getCurrentUserSubstitute(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String substitute = wfService.getCurrentUserSubstitute();

			context.setField(WFFlowerConstants.GET_CURRENT_USER_SUBSTITUTE_SUBSTITUTE_ID, substitute);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Terminates the process identified by processID.
	 * Handles all relevant things.
	 * In case of failure throws WorkFlowException.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void terminateProcess(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String[] processInstanceIds =
				(String[])context.getField(WFFlowerConstants.TERMINATE_PROCESS_PROCESS_IDS);
			wfService.terminateProcess(processInstanceIds);

		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}
	}

	/**
	 * Assistance method of terminateProcess(). Can also be called independently.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void doesProcessInstanceHaveCheckedOutItems(Context context, 
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String processInstanceID =
				context.getString(WFFlowerConstants.DOES_PROCESS_HAVE_CHECKED_OUT_ITEMS_PROCESS_ID);
			boolean result = 
				wfService.doesProcessInstanceHaveItemsCheckedOutByOtherUsers(processInstanceID);
			context.setField(WFFlowerConstants.DOES_PROCESS_HAVE_CHECKED_OUT_ITEMS_OUTPUT,
				new Boolean(result));

		}

		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Sets the given user as absent.
	 * Note: In order for this to work, (when a user logs on again, 
	 * mark him as not absent), the checkbox:
	 * "Automatically reset absent indicator when person starts
	 * working again" should be marked in the person properties
	 * (Workflow buildTime).
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void setUserAsAbsent(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String userID = context.getString(WFFlowerConstants.SET_USER_AS_ABSENT_USER_ID);
			wfService.setUserAsAbsent(userID);

		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Returns true if the user is absent.
	 * Returns false either if the user is not absent or if the user
	 * does not exist in WorkFlow (in the method isAbsent(). and this
	 * method also returns false if the userID is null).
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void isAbsent(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String userID = context.getString(WFFlowerConstants.IS_ABSENT_USER_ID);

			Boolean isAbsent = new Boolean(wfService.isAbsent(userID));

			context.setField(WFFlowerConstants.IS_ABSENT_OUTPUT, isAbsent);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Return all pre-defined filters in the system.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 */
	public void retrieveFilterNames(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WFWorkList[] filterNames = wfService.retrieveFilterNames();

			context.setField(WFFlowerConstants.RETRIEVE_FILTER_NAMES_OUTPUT, filterNames);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieve names of existing ProcessTemplates in the system.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getProcessTemplateNames(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap;
			parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
				WFFlowerConstants.GET_PROCESS_TEMPLATE_NAMES_PARAMS_MAP);

			String[] processTemplateNames =
				wfService.getProcessTemplateNames(parametersMap);

			context.setField(WFFlowerConstants.GET_PROCESS_TEMPLATE_NAMES_TEMPLATE_NAMES,
				processTemplateNames);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}

		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Returns set of User details objects of all users in the given organizational
	 * team, that can perform this role/
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveUsersDetailsInOrganizationalTeam(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String organization =
				context.getString(WFFlowerConstants.RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_ORGANIZATION);
			String role = 
				context.getString(WFFlowerConstants.RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_ROLE);

			Set usersInOrganizationalTeam =
				wfService.retrieveUsersDetailsInOrganizationalTeam(organization, role);

			context.setField(
				WFFlowerConstants.RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_USERS,
				usersInOrganizationalTeam);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Returns array of User IDs of all users in the given organizational
	 * team, that can perform this role/
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrieveUsersInOrganizationalTeam(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String organization =
				context.getString(WFFlowerConstants.RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_ORGANIZATION);
			String role = context.getString(WFFlowerConstants.RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_ROLE);

			Set usersInOrganizationalTeam =
				wfService.retrieveUsersInOrganizationalTeam(organization, role);

			context.setField(
				WFFlowerConstants.RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_USERS,
				usersInOrganizationalTeam);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieves all open processes for this customer (the customer is set in
	 * the description field).
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getCustomerOpenProcesses(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap;
			parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.GET_CUSTOMER_OPEN_PROCESSES_PARAMETERS_MAP);

			WFProcessInstanceList customerOpenProcesses =
				wfService.getCustomerOpenProcesses(parametersMap);

			context.setField(WFFlowerConstants.GET_CUSTOMER_OPEN_PROCESSES_OUTPUT, 
				customerOpenProcesses);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieves the customerID from the ParametersMap
	 * Will perform query on processes, pass on them and return true
	 * if any process found with this customerID.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void doesCustomerHaveOpenProcesses(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap;
			parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETERS_MAP);

			Boolean doesCustomerHaveOpenProcesses =
				new Boolean(
					wfService.doesCustomerHaveOpenProcesses(parametersMap));

			context.setField(
				WFFlowerConstants.DOES_CUSTOMER_HAVE_OPEN_PROCESSES_OUTPUT,
				doesCustomerHaveOpenProcesses);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieves the customerID from the ParametersMap
	 * Will perform query on processes, pass on them and return the list
	 * of open processes found with this customerID.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getListOfCustomerOpenProcesses(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.LIST_OF_CUSTOMER_OPEN_PROCESSES_PARAMETERS_MAP);

			String[] listOfCustomerOpenProcesses =
				wfService.getListOfCustomerOpenProcesses(parametersMap);

			context.setField(
				WFFlowerConstants.LIST_OF_CUSTOMER_OPEN_PROCESSES_OUTPUT,
				listOfCustomerOpenProcesses);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Returns a IDPagingService object with the relevant process Ids for
	 * the given customerId.
	 * @param context.
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getCustomerOpenProcessesIds(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.GET_CUSTOMER_OPEN_PROCESSES_IDS_PARAMETERS_MAP);
			Integer numOfRowsInPageAsInteger =
				context.getInteger(WFFlowerConstants.GET_CUSTOMER_OPEN_PROCESSES_IDS_ROWS_IN_PAGE);
			int numOfRowsInPage = numOfRowsInPageAsInteger.intValue();

			IDPagingService customerOpenProcessesIds =
				wfService.getCustomerOpenProcessesIds(parametersMap, numOfRowsInPage);

			context.setField(
				WFFlowerConstants.GET_CUSTOMER_OPEN_PROCESSES_IDS_OUTPUT,
				customerOpenProcessesIds);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method allows the user to check-in a checked-out item, and 
	 * immediately catch the next WorkItem.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void autoOpeningOfNextWorkItem(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.AUTO_OPENING_OF_NEXT_WORK_ITEM_WORK_ITEM_ID);

			WorkFlowServiceParameterMap parametersMap;
			parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.AUTO_OPENING_OF_NEXT_WORK_ITEM_PARAMS_MAP);

			String nextWorkItemId =
				wfService.autoOpeningOfNextWorkItem(workItemID, parametersMap);

			context.setField(WFFlowerConstants.AUTO_OPENING_OF_NEXT_WORK_ITEM_OUTPUT, 
				nextWorkItemId);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method allows the user to check-in a checked-out item, and 
	 * immediately catch the next WorkItem.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void alertForAutoOpeningOfNextWorkItem(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(
				WFFlowerConstants.ALRET_FOR_AUTO_OPENING_OF_NEXT_WORK_ITEM_WORK_ITEM_ID);

			WorkFlowServiceParameterMap parametersMap;
			parametersMap =
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.ALERT_FOR_AUTO_OPENING_OF_NEXT_WORK_ITEM_PARAMS_MAP);

			wfService.alertForAutoOpeningOfNextWorkItem(workItemID, parametersMap);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method is used to prepare list of users required for 
	 * "choosing working queue of another user", with returning full name
	 * details for the users.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void prepareListOfUsersWithDetails(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			Set listOfUsers = wfService.prepareListOfUsersWithDetails();

			context.setField(WFFlowerConstants.PREPARE_LIST_OF_USERS_DETAILS_OUTPUT, listOfUsers);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method is used to prepare list of users required for 
	 * "choosing working queue of another user".
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void prepareListOfUsers(
		Context context,
		ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			Set listOfUsers = wfService.prepareListOfUsers();

			context.setField(WFFlowerConstants.PREPARE_LIST_OF_USERS_OUTPUT, listOfUsers);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * This method is used to allow restart of a WorkItem.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void forceRestart(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String workItemID =
				context.getString(WFFlowerConstants.FORCE_RESTART_WORK_ITEM_ID);

			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.FORCE_RESTART_PARAMS_MAP);

			wfService.forceRestart(workItemID, parametersMap);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * For a given WFProcessInstance, find the last activity performed in it.
	 * This method will return value other than null, only if the
	 * WFProcessInstance is in state FINISHED or FORCE_FINISHED.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getLastPerformedActivity(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String processInstanceName =
				context.getString(WFFlowerConstants.GET_LAST_PERFORMED_ACTIVITY_PROCESS_INSTANCE_NAME);

			String lastPerformedActivity = 
				wfService.getLastPerformedActivity(processInstanceName);

			context.setField(WFFlowerConstants.GET_LAST_PERFORMED_ACTIVITY_OUTPUT, 
				lastPerformedActivity);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}
	}

	/**
	 * Return All the users - real and virtual - for which this user is authorized.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void retrievePeopleThisUserIsAuthorizedFor(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String userID =
				context.getString(
				WFFlowerConstants.RETRIEVE_PEOPLE_THIS_USER_IS_AUTHORIZED_FOR_USER_ID);

			String[] peopleThisUserIsAuthorizedFor = 
				wfService.retrievePeopleThisUserIsAuthorizedFor(userID);

			context.setField(
				WFFlowerConstants.RETRIEVE_PEOPLE_THIS_USER_IS_AUTHORIZED_FOR_OUTPUT, 
				peopleThisUserIsAuthorizedFor);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}
		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Create and start a new ProcessInstance.
	 * @param context
	 * @param parametersList
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void createAndStartProcessInstance(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			String processTemplateName =
				context.getString(WFFlowerConstants.CREATE_PROCESS_INSTANCE_PROCESS_TEMPLATE_NAME);

			String processInstanceName =
				context.getString(WFFlowerConstants.CREATE_PROCESS_INSTANCE_PROCESS_NAME);

			String processContext =
				context.getString(WFFlowerConstants.CREATE_PROCESS_INSTANCE_PROCESS_CONTEXT);

			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.CREATE_PROCESS_INSTANCE_PROCESS_MAP);

			String processInstanceID = wfService.createAndStartProcessInstance(processTemplateName, 
				processInstanceName, processContext, parametersMap);

			context.setField(
				WFFlowerConstants.CREATE_PROCESS_INSTANCE_PROCESS_ID, processInstanceID);


		}

		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}

		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Retrieve the last handler in a given customer.
	 * First, find the last ProcessInstance of this customer.
	 * Second, find the last performed WorkItem in this ProcessInstance.
	 * Return the owner of this WorkItem.
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	public void getLastHandlerOfCustomer(Context context, ParameterList parameterList)
		throws ServiceException, ValidationException
	{

		try
		{

			WorkFlowServiceParameterMap parametersMap = 
				(WorkFlowServiceParameterMap) context.getField(
					WFFlowerConstants.GET_LAST_HANDLER_OF_CUSTOMER_PARAMS_MAP);
			String roleName = 
				context.getString(WFFlowerConstants.GET_LAST_HANDLER_OF_CUSTOMER_ROLE_NAME);
			
			String processTemplateName =
				context.getString(WFFlowerConstants.GET_LAST_HANDLER_OF_CUSTOMER_PROCESS_TEMPLATE_NAME);

			WFLastHandlerOfCustomer lastHandlerOfCustomer = 
				wfService.getLastHandlerOfCustomer(parametersMap, roleName, 
				processTemplateName);

			context.setField(WFFlowerConstants.GET_LAST_HANDLER_OF_CUSTOMER_OUTPUT, 
				lastHandlerOfCustomer);

		}
		catch (AuthorizationException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (FatalException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);

		}
		catch (ContextException e)
		{
			throw new ServiceException(WFExceptionMessages.CONTEXT_PROBLEM, e);
		}

		catch (WorkFlowLogicException e)
		{
			handleMessagesContainer(e.getMessagesContainer(), context);
			throw new ValidationException();
		}
		catch (WorkFlowException e)
		{
			throw new ServiceException(WFExceptionMessages.WORKFLOW_PROBLEM, e);
		}

	}

	/**
	 * Assistance method
	 * Extract the joint code for handling MessagesContainer errors.
	 * @param container
	 * @param context
	 */
	private void handleMessagesContainer(MessagesContainer container, Context context)
	{

		if(container.containsErrors())
		{ 
			Logger.debug(LOGGER_CONTEXT, 
				WFFlowerConstants.WF_MESSAGE_ID_PREFIX + 
				container.getMessage(0).getMessageId());
		}
		ApplicationUtil.mergeGlobalMessageContainer(
			context, container);
		
	}
}
