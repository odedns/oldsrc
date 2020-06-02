/*
 * Created on: 20/05/2004
 * Author Amit Mendelson
 * @version $Id: WorkFlowService.java,v 1.8 2005/05/02 07:40:45 amit Exp $
 */
package com.ness.fw.workflow;

import java.util.Set;
import java.util.Date;

import com.ness.fw.util.IDPagingService;

/**
 * This class defines the interface for WorkFlowService methods.
 * Only methods that are defined here should be accessible from outside the
 * workflow module.
 */
public interface WorkFlowService
{

	/**
	 * logon - this method is called when a user logs in to the system, 
	 * in order to create connection with IBM WorkFlow. 
	 * @param userCredentials Is required for single sign-on.
	 * @param userID - ID of the user that will be logged on.
	 * @throws WorkFlowException
	 * @throws AgentException
	 * @throws NullParametersException
	 */
	public void logon(UserCredentials userCredentials, String userID)
		throws WorkFlowException, AgentException, NullParametersException;

	/**
	 * logon - this method is called when a user logs in to the
	 *  system, in order to create connection with IBM WorkFlow. 
	 * @param userID - ID of the user that will be logged on.
	 * @param password - password of this user.
	 * @throws WorkFlowException
	 * @throws AgentException
	 * @throws NullParametersException
	 */
	public void logon(String userID, String password)
		throws WorkFlowException, AgentException, NullParametersException;

	/**logoff - allows the application to finish the specified user
	 *  session with an MQ Workflow execution server.
	 * @throws WorkFlowException
	 */
	public void logoff() throws WorkFlowException;

	/**
	 * destroy - will be called on the system shutdown.
	 * will destroy the created Agent?
	 *
	 */
	public void exit() throws WorkFlowException;

	/**
	 * Retrieve all tasks (WorkItems and ActivityInstanceNotifications) that
	 * fulfill a given filter.
	 * @param parametersMap - will include:
	 *  TASKS_OWNED_BY_THIS_USER - show only personal tasks or also tasks of other users,
	 * etc.
	 * @param filterId Id of the WorkList to be retrieved for a predefined WorkList.
	 * @return WFWorkItem[] Array of all the retrieved WorkItems.
	 * @throws WorkFlowException wraps FmcException that might be thrown
	 * inside.
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFWorkItemList retrieveTasks(WorkFlowServiceParameterMap parametersMap,
		String filterId)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * Allows retrieval of single WFWorkItem from the Workflow.
	 * @param workItemID
	 * @param paramsMap WorkFlowServiceParameterMap. 
	 * @return WFWorkItem - the retrieved WFWorkItem according to the
	 * supplied workItemID.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFWorkItem getWorkItem(String workItemID, WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * Retrieve all task Ids (WorkItems and ActivityInstanceNotifications) that
	 * fulfill a given filter.
	 * @param parametersMap contains required parameters for retrieval.
	 * @param filterId Id of the WorkList to be retrieved for a predefined WorkList.
	 * @param numOfRowsInPage required for the created IDPagingService.
	 * @return IDPagingService PagingService that contains all the retrieved WorkItem Ids. 
	 * (In order to access the workItems, have to call getWorkItems() ).
	 * @throws WorkFlowException wraps FmcException that might be thrown
	 * inside.
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public IDPagingService retrieveTasksIds(WorkFlowServiceParameterMap parametersMap,
		String filterId, int numOfRowsInPage)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * Allows retrieval of multiple WFWorkItems from the Workflow.
	 * @param pagingService Ids of the requested WFWorkItems.
	 * @param paramsMap WorkFlowServiceParameterMap. 
	 * @return WFWorkItemList - the retrieved WFWorkItems according to the
	 * supplied workItemIds.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFWorkItemList getWorkItems(IDPagingService pagingService, WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;


	/**
	 * Checking out the selected activity.
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
	 * @param workItemID ID of the workItem to be checked out.
	 * @param paramsMap Wraps the names of container elements to be returned.
	 * @return WorkFlowServiceParameterMap - Wraps the container of the 
	 * checked out activity (can be used for check-in).
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WorkFlowServiceParameterMap checkOut(String workItemID, 
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException;

	/**
	 * This method allows checking-in of a WorkItem from the application level.
	 * @param workItemID - ID of the WorkItem to be checked-in.
	 * @param parametersMap - WorkFlowServiceParameterMap that wraps the information
	 * to be passed to the next WorkItem (OutContainer of the checked-in WorkItem).
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public void checkIn(
		String workItemID,
		WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

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
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * This method allows the user to postpone a WFWorkItem completion date
	 * to a specified date in the future.
	 * @param workItemID ID of the WFWorkItem to postpone.
	 * @param expectedEndTime expectedEndTime (Date).
	 * @return true if postpone succeeded, false otherwise.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public boolean postponeWorkItem(
		String workItemID, Date expectedEndTime)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * This method is used to set the priority in description field of ProcessInstance.
	 * @param processInstanceIds Array of all the processInstance Ids.
	 * @param newPriority the new requested priority.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public void setPriorityOfProcessInstance(
		String[] ProcessInstanceIds,
		int newPriority)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * This method is used to set the priority in description field of WorkItem.
	 * @param workItemIds Array of all the workItem Ids
	 * @param newPriority New priority to set
	 * @throws WorkFlowException if the new priority is illegal
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 * @see com.ness.fw.workflow.WorkFlowService#setPriorityOfWorkItem(String, int)
	 */
	public void setPriorityOfWorkItem(
		String[] workItemIds, int newPriority)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * Allows retrieval of single WFProcessInstance from the Workflow.
	 * @param processInstanceID
	 * @param paramsMap
	 * @return WFProcessInstance - the retrieved WFProcessInstance according 
	 * to the supplied processInstanceID.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstance getProcessInstance(String processInstanceID, 
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

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
	public WFProcessInstance getProcessInstanceByName(String processInstanceName,
		WorkFlowServiceParameterMap paramsMap) throws WorkFlowException,
		NullParametersException, WorkFlowLogicException;

	/**
	 * This method returns a WorkItem to the virtual user to whom
	 * it belonged before the current user (or should belong).
	 * 
	 * 0. get the WorkItem.
	 * 1. get the WorkItem input container.
	 * 2. get the role and organization from the input container.
	 * 3. attach them - the virtual user's name is the concatenation of them.
	 * 4. transfer the WorkItem to this user.
	 * 
	 * Note: the virtual user must be predefined in advance according to this
	 * convention(concatenation of role and organization).
	 * 
	 * @param workItemID - ID of the WorkItem to be transferred.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	public void returnWorkItemToGeneralQueue(String workItemID)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException;

	/** Retrieve all Ids of ProcessInstances that fulfill a given filter.
	 * @param parametersMap - holds processID and showOnlyActivitiesForShow(boolean)
	 * @param numOfRowsInPage
	 * @return IDPagingService containes all ids of the ProcessInstances that fulfill the filter.
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 * @see com.ness.fw.workflow.WorkFlowService#retrieveProcesses
	 */
	public IDPagingService retrieveProcessIds(WorkFlowServiceParameterMap parametersMap,
		int numOfRowsInPage)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * Allows retrieval of multiple WFProcessInstances from the Workflow.
	 * @param pagingService - contains IDs of the WFProcessInstances to be retrieved.
	 * @param paramsMap
	 * @return WFProcessInstanceList - the retrieved WFProcessInstances according 
	 * to the supplied processInstanceIds.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstanceList getProcessInstances(IDPagingService pagingService,
		WorkFlowServiceParameterMap paramsMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * Retrieve all ProcessInstances that fulfill a given filter.
	 * @param parametersMap - holds processID and showOnlyActivitiesForShow(boolean)
	 * @return WFProcessInstanceList Holds an array of all retrieved ProcessInstances.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstanceList retrieveProcesses(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

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
	 */
	public String[] retrieveUsersForSelectSubstitute()
		throws WorkFlowException, NullParametersException;

	/**
	 * Routing of the WorkItem.
	 * @param workItemID
	 * @param targetUserID
	 * @param trackWorkItem
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public void routeWorkItem(String workItemID, String targetUserID, boolean trackWorkItem)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException;

	/**
	 * Retrieval of users for the routing (does not route the task - 
	 * just indicates who is elligible to take care of it).
	 * @param workItemID
	 * @return Set of User IDs.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public Set retrieveUsersForWorkItemRouting(String workItemID)
		throws WorkFlowException, NullParametersException;

	/**
	 * This service is like retrieveUsersForWorkItemRouting, except that
	 * it returns a set of objects containing the user attributes.
	 * @param workItemID
	 * @return Set of users with their details.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */	
	public Set retrieveUsersDetailsForWorkItemRouting(String workItemID)
		throws WorkFlowException, NullParametersException;


	/**
	 * This method sets a substitute for the logged-on user.
	 * @see com.ness.fw.workflow.WorkFlowService#substitute(String)
	 * @param userID - ID of the person to set substitute for.
	 * @param substitute - ID of the substitute fot the person.
	 * @throws WorkFlowException - In case the substitute is an illegal user.
	 * @throws NullParametersException
	 */
	public void setUserSubstitute(String userID, String substitute)
		throws WorkFlowException, NullParametersException;

	/**
	 * This method sets a substitute for the logged-on user.
	 * @see com.ness.fw.workflow.WorkFlowService#substitute(String)
	 * @param substitute - ID of the substitute fot the person.
	 * @throws WorkFlowException - In case the substitute is an illegal user.
	 * @throws NullParametersException
	 */
	public void setCurrentUserSubstitute(String substitute)
		throws WorkFlowException, NullParametersException;

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
		throws WorkFlowException, NullParametersException;

	/**
	 * Returns the current substitute of the current user.
	 * If this user has no substitute, null value is returned.
	 * Anyone who uses this method should check its return value for null value.
	 * @return String - name of the substitute of this person (can be null).
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public String getCurrentUserSubstitute()
		throws WorkFlowException, NullParametersException;

	/**
	 * Terminates the processes identified by processIds.
	 * Handles all relevant things.
	 * In case of failure throws WorkFlowException.
	 * @param processInstanceIds - Ids of the process instances to be terminated.
	 * @throws WorkFlowException wraps FmcException that might be thrown
	 * inside.
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException
	 */
	public void terminateProcess(String[] processInstanceIds)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException;

	/**
	 * Assistance method of terminateProcess(). Can also be called independently.
	 * @param processInstanceID
	 * @return boolean true if there are any checked-out WorkItems, false otherwise.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	public boolean doesProcessInstanceHaveItemsCheckedOutByOtherUsers(
		String processInstanceID)
		throws NullParametersException, WorkFlowException;

	/**
	 * Sets the given user as absent.
	 * Note: In order for this to work, (when a user logs on again, 
	 * mark him as not absent), the checkbox:
	 * "Automatically reset absent indicator when person starts
	 * working again" should be marked in the person properties
	 * (Workflow buildTime).
	 * @param userID
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	public void setUserAsAbsent(String userID)
		throws WorkFlowException, WorkFlowLogicException;

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
		throws WorkFlowException, NullParametersException;

	/**
	 * Return all pre-defined filters in the system.
	 * @returnWFWorkList list of filters
	 * @throws WorkFlowException
	 */
	public WFWorkList[] retrieveFilterNames() throws WorkFlowException,
		NullParametersException;

	/**
	 * Retrieve names of existing ProcessTemplates in the system.
	 * @param parametersMap - WorkFlowServiceParameterMap, that will contain
	 * the appropriate query parameters (filter, sortCriteria, threshold).
	 * @return String[]
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public String[] getProcessTemplateNames(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException;

	/**
	 * Returns set of User IDs of all users in the given organizational
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
		throws WorkFlowException, NullParametersException;

	/**
	 * Returns set of UserDetails objects of all users in the given organizational
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
		throws WorkFlowException, NullParametersException;


	/**
	 * Retrieves all open processes for this customer (the customer is set in
	 * the description field).
	 * @param WorkFlowServiceParameterMap
	 * @return WFProcessInstanceList Holds all the ProcessInstances of this customer.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 * @throws WorkFlowLogicException
	 */
	public WFProcessInstanceList getCustomerOpenProcesses(
		WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException, WorkFlowLogicException;

	/**
	 * Retrieves the customerID from the ParametersMap
	 * Will perform query on processes, pass on them and return true
	 * if any process found with this customerID.
	 * @param parametersMap - WorkFlowServiceParameterMap
	 * @return true if there are any open processes for this customer.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */
	public boolean doesCustomerHaveOpenProcesses(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException;

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
	public String[] getListOfCustomerOpenProcesses(
		WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowException, NullParametersException;

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
	public IDPagingService getCustomerOpenProcessesIds(WorkFlowServiceParameterMap parametersMap,
		int numOfRowsInPage) throws WorkFlowException, NullParametersException;

	/**
	 * This method allows the user to check-in a checked-out item, and immediately
	 * catch the next WorkItem.
	 * @return String Id of the next workItem.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	public String autoOpeningOfNextWorkItem(String workItemID, 
		WorkFlowServiceParameterMap containerAsMap) 
		throws NullParametersException, WorkFlowException, WorkFlowLogicException;

	/**
	 * Alerts the user if he can open the next WorkItem.
	 * In this case, the WorkFlowServiceParameterMap will contain the WorkItemID
	 * required for check out.
	 * Otherwise, nothing is returned.
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	public void alertForAutoOpeningOfNextWorkItem(
		String workItemID, WorkFlowServiceParameterMap containerAsMap) 
		throws NullParametersException, WorkFlowException, WorkFlowLogicException;

	/**
	 * This service is like prepareListOfUsers, except that
	 * it returns a set of objects containing the user attributes.
	 * @param workItemID
	 * @return Set of users with their details.
	 * @throws WorkFlowException
	 * @throws NullParametersException
	 */	
	public Set prepareListOfUsersWithDetails()
		throws WorkFlowException, NullParametersException;

	/**
	 * This method is used to prepare list of users required for 
	 * "choosing working queue of another user".
	 * @return list of users for "choosing working queue of another user".
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 */
	public Set prepareListOfUsers()
		throws NullParametersException, WorkFlowException;

	/**
	 * This method is used to allow restart of a WorkItem.
	 * @param workItemID
	 * @param parametersMap
	 * @throws NullParametersException
	 * @throws WorkFlowException
	 * @throws WorkFlowLogicException
	 */
	public void forceRestart(String workItemID, WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException, WorkFlowException, WorkFlowLogicException;


	/**
	 * For a given WFProcessInstance, find the last activity performed in it.
	 * This method will return value other than null, only if the
	 * WFProcessInstance is in state FINISHED or FORCE_FINISHED.
	 * @param processInstanceName - name of the WFProcessInstance
	 * @return String - ID of the last performed activity.
	 * @throws WorkFlowException
	 */
	public String getLastPerformedActivity(String processInstanceName)
		throws WorkFlowException, NullParametersException;

	/**
	 * Return All the users - real and virtual - for which this user is authorized.
	 * @param userID ID of the requested user.
	 * @return String[] Array of all the users for whom the selected user
	 * is authorized.
	 * @throws WorkFlowException if a FmcException is thrown inside.
	 * @throws NullParametersException
	 */
	public String[] retrievePeopleThisUserIsAuthorizedFor(String userID)
		throws WorkFlowException, NullParametersException;

	/**
	 * Creation of a new ProcessInstance
	 * @param processTemplateName
	 * @param processName Name/ID of the new ProcessInstance to be created.
	 * @param processContext
	 * @param parametersMap Will contain the relevant applicative parameters.
	 * @return String ID of the new ProcessInstance
	 * @throws WorkFlowLogicException
	 * @throws WorkFlowException
	 */
	public String createAndStartProcessInstance(String processTemplateName, 
		String processName, String processContext, 
		WorkFlowServiceParameterMap parametersMap) 
		throws WorkFlowLogicException, WorkFlowException;

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
		String roleName, String processTemplateName)
		throws WorkFlowException, WorkFlowLogicException, NullParametersException;
		
}
