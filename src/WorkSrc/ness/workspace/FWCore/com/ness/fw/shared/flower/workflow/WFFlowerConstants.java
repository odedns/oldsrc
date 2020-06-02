/*
 * Created on: 05/08/2004
 * Author: Amit Mendelson
 * @version $Id: WFFlowerConstants.java,v 1.6 2005/05/02 07:37:23 amit Exp $
 */
package com.ness.fw.shared.flower.workflow;

/**
 * List of all constants used in the WorkFlow module, excluding the
 * Agent constants (which appear in another class, AgentConstants).
 */
public class WFFlowerConstants
{
	
	/**
	 * WF_MESSAGE_ID_PREFIX
	 */
	public static final String WF_MESSAGE_ID_PREFIX = "WF MessageID: ";

	/**
	 * logon input: USER_ID
	 */
	public static final String LOGON_USER_ID = "WFLogonInSTR.userId";

	/**
	 * logon input: PASSWORD
	 */
	public static final String LOGON_PASSWORD = "WFLogonInSTR.password";

	/**
	 * SingleSignOn logon input: USER_ID
	 */
	public static final String SINGLE_SIGNON_LOGON_USER_ID = "WFSingleSignOnLogonInSTR.userId";

	/**
	 * SingleSignOn logon input: USER_CREDENTIALS
	 */
	public static final String SINGLE_SIGNON_LOGON_USER_CREDENTIALS = 
		"WFSingleSignOnLogonInSTR.userCredentials";

	/**
	 * retrieve tasks input: ParameterMap
	 */
	public static final String RETRIEVE_TASKS_PARAMETER_MAP = "WFRetrieveTasksInSTR.retrieveTasksMap";		

	/**
	 * retrieve tasks input: predefined filter Id
	 */
	public static final String RETRIEVE_TASKS_PREDEFINED_FILTER_ID = "WFRetrieveTasksInSTR.predefinedFilterId";		

	/**
	 * retrieve tasks output: Map
	 */
	public static final String RETRIEVE_TASKS_LIST = "WFRetrieveTasksOutSTR.retrieveTasksList";		

	/**
	 * retrieve tasks Ids input: ParameterMap
	 */
	public static final String RETRIEVE_TASKS_IDS_PARAMETER_MAP = "WFRetrieveTasksIdsInSTR.retrieveTasksMap";		

	/**
	 * retrieve tasks Ids input: predefined filter Id
	 */
	public static final String RETRIEVE_TASKS_IDS_PREDEFINED_FILTER_ID = "WFRetrieveTasksIdsInSTR.predefinedFilterId";		

	/**
	 * retrieve tasks Ids input: number of rows in page.
	 */
	public static final String RETRIEVE_TASKS_IDS_ROWS_IN_PAGE = "WFRetrieveTasksIdsInSTR.numOfRowsInPage";

	/**
	 * retrieve tasks Ids output: Paging object
	 */
	public static final String RETRIEVE_TASKS_IDS_PAGING_OBJECT = "WFRetrieveTasksIdsOutSTR.retrievedTasksPagingObject";		

	/**
	 * getWorkItem input: workItemID
	 */
	public static final String GET_TASK_WORK_ITEM_ID = "WFGetWorkItemInSTR.workItemId";		

	/**
	 * getWorkItem input: parametersMap
	 */
	public static final String GET_TASK_PARAMETER_MAP = "WFGetWorkItemInSTR.getWorkItemParamsMap";		

	/**
	 * getWorkItem output: retrievedWorkItem
	 */
	public static final String GET_TASK_RETRIEVED_WORK_ITEM = "WFGetWorkItemOutSTR.retrievedWorkItem";		

	/**
	 * getWorkItems input: workItemIDs
	 */
	public static final String GET_TASKS_PAGING_OBJECT = "WFGetWorkItemsInSTR.workItemsPagingObject";		

	/**
	 * getWorkItems input: parametersMap
	 */
	public static final String GET_TASKS_PARAMETER_MAP = "WFGetWorkItemsInSTR.getWorkItemsParamsMap";		

	/**
	 * getWorkItems output: retrievedWorkItems
	 */
	public static final String GET_TASKS_RETRIEVED_WORK_ITEMS = "WFGetWorkItemsOutSTR.retrievedWorkItems";		

	/**
	 * checkOut input: workItemID
	 */
	public static final String CHECK_OUT_WORK_ITEM_ID = "WFCheckOutInSTR.workItemId";		

	/**
	 * checkOut input: paramsMap
	 */
	public static final String CHECK_OUT_PARAMS_MAP = "WFCheckOutInSTR.checkOutParamsMap";		

	/**
	 * checkOut output: checkOutData
	 */
	public static final String CHECK_OUT_DATA = "WFCheckOutOutSTR.checkOutData";		

	/**
	 * checkIn input: workItemID
	 */
	public static final String CHECK_IN_WORK_ITEM_ID = "WFCheckInInSTR.workItemId";		

	/**
	 * checkIn input: MAP
	 */
	public static final String CHECK_IN_MAP = "WFCheckInInSTR.checkInMap";		

	/**
	 * cancelCheckOut input: workItemID
	 */
	public static final String CANCEL_CHECK_OUT_WORK_ITEM_ID = "WFCancelCheckOutInSTR.workItemId";		

	/**
	 * postponeWorkItem input: workItemID
	 */
	public static final String POSTPONE_WORK_ITEM_ID = "WFPostponeWorkItemInSTR.workItemId";		

	/**
	 * postponeWorkItem input: date
	 */
	public static final String POSTPONE_WORK_ITEM_DATE = "WFPostponeWorkItemInSTR.expectedEndTime";		

	/**
	 * postponeWorkItem output: was workItem postponed
	 */
	public static final String POSTPONE_WORK_ITEM_OUTPUT = "WFPostponeWorkItemOutSTR.wasWorkItemPostponed";		


	/**
	 * setPriorityOfProcessInstance input: processInstanceIds
	 */
	public static final String SET_PRIORITY_PROCESS_INSTANCE_IDS = "WFSetPriorityOfProcessInstanceInSTR.processInstanceIds";		

	/**
	 * setPriorityOfWorkItem input: priority
	 */
	public static final String SET_PRIORITY_OF_WORK_ITEM_PRIORITY = "WFSetPriorityOfWorkItemInSTR.priority";		

	/**
	 * setPriorityOfWorkItem input: workItemID
	 */
	public static final String SET_PRIORITY_OF_WORK_ITEM_WORK_ITEM_IDS = "WFSetPriorityOfWorkItemInSTR.workItemIds";		

	/**
	 * setPriorityOfProcessInstance input: priority
	 */
	public static final String SET_PRIORITY_PRIORITY = "WFSetPriorityOfProcessInstanceInSTR.priority";		

	/**
	 * getProcessInstance input: processInstanceId
	 */
	public static final String GET_PROCESS_INSTANCE_PROCESS_ID = "WFGetProcessInstanceInSTR.processInstanceId";
	
	/**
	 * getProcessInstance input: parametersMap
	 */
	public static final String GET_PROCESS_INSTANCE_PARAMETERS_MAP = "WFGetProcessInstanceInSTR.processInstanceParametersMap";
	
	/**
	 * getProcessInstance output: retrieved process
	 */
	public static final String GET_PROCESS_INSTANCE_RETRIEVED_PROCESS = "WFGetProcessInstanceOutSTR.retrievedProcess";

	/**
	 * getProcessInstances input: processInstance paging service
	 */
	public static final String GET_PROCESS_INSTANCES_PAGING_SERVICE = "WFGetProcessInstancesInSTR.processInstancesPagingService";
	
	/**
	 * getProcessInstances input: parametersMap
	 */
	public static final String GET_PROCESS_INSTANCES_PARAMETERS_MAP = "WFGetProcessInstancesInSTR.processInstancesParametersMap";
	
	/**
	 * getProcessInstances output: retrieved process
	 */
	public static final String GET_PROCESS_INSTANCES_RETRIEVED_PROCESSES = "WFGetProcessInstancesOutSTR.retrievedProcesses";


	/**
	 * getProcessInstanceByName input: processInstanceId
	 */
	public static final String GET_PROCESS_INSTANCE_BY_NAME_PROCESS_NAME = "WFGetProcessInstanceByNameInSTR.processInstanceName";
	
	/**
	 * getProcessInstanceByName input: containerElementNames
	 */
	public static final String GET_PROCESS_INSTANCE_BY_NAME_PARAMETERS_MAP = "WFGetProcessInstanceByNameInSTR.processInstanceParametersMap";
	
	/**
	 * getProcessInstanceByName output: retrieved process
	 */
	public static final String GET_PROCESS_INSTANCE_BY_NAME_RETRIEVED_PROCESS = "WFGetProcessInstanceByNameOutSTR.retrievedProcess";

	
	/**
	 * returnWorkItemToGeneralQueue input: workItemId
	 */
	public static final String RETURN_WORK_ITEM_TO_QUEUE_WORK_ITEM_ID = "WFReturnWorkItemToGeneralQueueInSTR.workItemId";

	/**
	 * retrieveProcesses input: retrieve processes map
	 */
	public static final String RETRIEVE_PROCESSES_RETRIEVE_PROCESSES_MAP = "WFRetrieveProcessesInSTR.retrieveProcessesMap";

	/**
	 * retrieveProcesses output: retrieved processes
	 */
	public static final String RETRIEVE_PROCESSES_RETRIEVED_PROCESSES = 
		"WFRetrieveProcessesOutSTR.retrievedProcesses";

	/**
	 * retrieveProcessIds input: retrieve processes map
	 */
	public static final String RETRIEVE_PROCESS_IDS_RETRIEVE_PROCESSES_MAP = 
		"WFRetrieveProcessIdsInSTR.retrieveProcessIdsMap";

	/**
	 * retrieveProcessIds input: number of rows in page
	 */
	public static final String RETRIEVE_PROCESS_IDS_ROWS_IN_PAGE = 
		"WFRetrieveProcessIdsInSTR.numOfRowsInPage";

	/**
	 * retrieveProcessIds output: retrieved processes
	 */
	public static final String RETRIEVE_PROCESS_IDS_RETRIEVED_PAGING_OBJECT = 
		"WFRetrieveProcessIdsOutSTR.retrievedProcessesPagingObject";

	/**
	 * retrieveUsersForSelectSubstitute output: users array
	 */
	public static final String RETRIEVE_USERS_FOR_SELECT_SUBSTITUTE_USERS_ARRAY = 
		"WFRetrieveUsersForSelectSubstituteOutSTR.usersForSelectSubstituteArray";

	/**
	 * route workItem input: workItem Id
	 */
	public static final String ROUTE_WORK_ITEM_WORK_ITEM_ID = 
		"WFRouteWorkItemInSTR.workItemId";

	/**
	 * route workItem input: target user Id
	 */
	public static final String ROUTE_WORK_ITEM_TARGET_USER_ID = 
		"WFRouteWorkItemInSTR.targetUserId";

	/**
	 * route workItem input: track work item
	 */
	public static final String ROUTE_WORK_ITEM_TRACK_WORK_ITEM = "WFRouteWorkItemInSTR.trackWorkItem";

	/**
	 * retrieve users for routing output: usersForWorkItemRouting
	 */
	public static final String RETRIEVE_USERS_FOR_WORK_ITEM_ROUTING_USERS_SET = 
		"WFRetrieveUsersForWorkItemRoutingOutSTR.usersForWorkItemRouting";

	/**
	 * retrieve users for routing input: workItem Id
	 */
	public static final String RETRIEVE_USERS_FOR_WORK_ITEM_ROUTING_WORK_ITEM_ID = 
		"WFRetrieveUsersForWorkItemRoutingInSTR.workItemId";

	/**
	 * retrieve users for routing output: usersForWorkItemRouting
	 */
	public static final String RETRIEVE_USERS_DETAILS_FOR_WORK_ITEM_ROUTING_USERS_SET = 
		"WFRetrieveUsersDetailsForWorkItemRoutingOutSTR.usersForWorkItemRouting";

	/**
	 * retrieve users for routing input: workItem Id
	 */
	public static final String RETRIEVE_USERS_DETAILS_FOR_WORK_ITEM_ROUTING_WORK_ITEM_ID = 
		"WFRetrieveUsersDetailsForWorkItemRoutingInSTR.workItemId";

	/**
	 * set user substitute: user Id
	 */
	public static final String SET_USER_SUBSTITUTE_USER_ID = "WFSetUserSubstituteInSTR.userId";

	/**
	 * set user substitute: substitute Id
	 */
	public static final String SET_USER_SUBSTITUTE_SUBSTITUTE_ID = "WFSetUserSubstituteInSTR.substituteId";

	/**
	 * set user substitute: substitute Id
	 */
	public static final String SET_CURRENT_USER_SUBSTITUTE_SUBSTITUTE_ID = 
		"WFSetCurrentUserSubstituteInSTR.substituteId";

	/**
	 * get user substitute: user Id
	 */
	public static final String GET_USER_SUBSTITUTE_USER_ID = "WFGetUserSubstituteInSTR.userId";

	/**
	 * get user substitute: substitute Id
	 */
	public static final String GET_USER_SUBSTITUTE_SUBSTITUTE_ID = "WFGetUserSubstituteOutSTR.userSubstitute";

	/**
	 * get current user substitute: substitute Id
	 */
   public static final String GET_CURRENT_USER_SUBSTITUTE_SUBSTITUTE_ID = "WFGetCurrentUserSubstituteOutSTR.userSubstitute";

	/**
     * terminate process: process instance Ids
     */
   public static final String TERMINATE_PROCESS_PROCESS_IDS = "WFTerminateProcessInSTR.processInstanceIds";

   /**
    * Does process instance have checked out items: process instance Id
    */
   public static final String DOES_PROCESS_HAVE_CHECKED_OUT_ITEMS_PROCESS_ID = 
		"WFDoesProcessInstanceHaveCheckedOutItemsInSTR.processInstanceId";

  /**
   * Does process instance have checked out items: doesProcessInstHaveCheckedOutItems
   */
   public static final String DOES_PROCESS_HAVE_CHECKED_OUT_ITEMS_OUTPUT = 
		"WFDoesProcessInstanceHaveCheckedOutItemsOutSTR.doesProcessInstHaveCheckedOutItems";

   /**
	* Set user as absent: user Id
	*/
   public static final String SET_USER_AS_ABSENT_USER_ID = "WFSetUserAsAbsentInSTR.userId";

   /**
	* Is user absent: user Id
	*/
   public static final String IS_ABSENT_USER_ID = "WFIsAbsentInSTR.userId";

   /**
	* Is user absent: isAbsent
	*/
   public static final String IS_ABSENT_OUTPUT = "WFIsAbsentOutSTR.isAbsent";

   /**
	* CreateProcessInstance: createProcessInstance Map
	*/
   public static final String CREATE_PROCESS_INSTANCE_PROCESS_MAP = 
		"WFCreateAndStartProcessInstanceInSTR.createProcessMap";

	/**
	 * CreateProcessInstance: createProcessInstance processName
	 */
	public static final String CREATE_PROCESS_INSTANCE_PROCESS_NAME = 
		 "WFCreateAndStartProcessInstanceInSTR.processInstanceName";

	/**
	 * CreateProcessInstance: createProcessInstance processContext
	 */
	public static final String CREATE_PROCESS_INSTANCE_PROCESS_CONTEXT = 
		 "WFCreateAndStartProcessInstanceInSTR.processContext";	

	/**
	 * CreateProcessInstance: createProcessInstance processTemplateName
	 */
	public static final String CREATE_PROCESS_INSTANCE_PROCESS_TEMPLATE_NAME = 
		 "WFCreateAndStartProcessInstanceInSTR.processTemplateName";

	/**
	 * CreateProcessInstance: createProcessInstance processID
	 */
	public static final String CREATE_PROCESS_INSTANCE_PROCESS_ID = 
		 "WFCreateAndStartProcessInstanceOutSTR.processInstanceId";

		 
	/**
	 * RETRIEVE_FILTER_NAMES_OUTPUT
	 */	
	public static final String 	RETRIEVE_FILTER_NAMES_OUTPUT =
		"WFRetrieveFilterNamesOutSTR.retrievedFilterNames";

	/**
	 * GET_PROCESS_TEMPLATE_NAMES_PARAMS_MAP
	 */
	public static final String GET_PROCESS_TEMPLATE_NAMES_PARAMS_MAP =
		"WFGetProcessTemplateNamesInSTR.processTemplateNamesMap";

	/**
	 * GET_PROCESS_TEMPLATE_NAMES_TEMPLATE_NAMES
	 */
	public static final String GET_PROCESS_TEMPLATE_NAMES_TEMPLATE_NAMES =
		"WFGetProcessTemplateNamesOutSTR.processTemplateNamesArray";
	
	/**
	 * RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_ORGANIZATION
	 */
	public static final String RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_ORGANIZATION =
		"WFRetrieveUsersInOrganizationalTeamInSTR.organization";

	/**
	 * RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_ROLE
	 */
	public static final String RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_ROLE =
		"WFRetrieveUsersInOrganizationalTeamInSTR.role";

	/**
	 * RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_USERS
	 */
	public static final String RETRIEVE_USERS_IN_ORGANIZATIONAL_TEAM_USERS =
		"WFRetrieveUsersInOrganizationalTeamOutSTR.usersInOrganizationalTeam";

	/**
	 * RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_ORGANIZATION
	 */
	public static final String RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_ORGANIZATION =
		"WFRetrieveUsersDetailsInOrganizationalTeamInSTR.organization";

	/**
	 * RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_ROLE
	 */
	public static final String RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_ROLE =
		"WFRetrieveUsersDetailsInOrganizationalTeamInSTR.role";

	/**
	 * RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_USERS
	 */
	public static final String RETRIEVE_USERS_DETAILS_IN_ORGANIZATIONAL_TEAM_USERS =
		"WFRetrieveUsersDetailsInOrganizationalTeamOutSTR.usersInOrganizationalTeam";

	/**
	 * GET_CUSTOMER_OPEN_PROCESSES_PARAMETERS_MAP
	 */
	public static final String GET_CUSTOMER_OPEN_PROCESSES_PARAMETERS_MAP =
		"WFGetCustomerOpenProcessesInSTR.getCustomerOpenProcessesMap";

	/**
	 * GET_CUSTOMER_OPEN_PROCESSES_OUTPUT
	 */
	public static final String GET_CUSTOMER_OPEN_PROCESSES_OUTPUT =
		"WFGetCustomerOpenProcessesOutSTR.customerOpenProcesses";

	/**
	 * DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETERS_MAP
	 */
	public static final String DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETERS_MAP =
		"WFDoesCustomerHaveOpenProcessesInSTR.doesCustomerHaveOpenProcessesMap";

	/**
	 * DOES_CUSTOMER_HAVE_OPEN_PROCESSES_OUTPUT
	 */
	public static final String DOES_CUSTOMER_HAVE_OPEN_PROCESSES_OUTPUT =
		"WFDoesCustomerHaveOpenProcessesOutSTR.doesCustomerHaveOpenProcesses";

	/**
	 * LIST_OF_CUSTOMER_OPEN_PROCESSES_PARAMETERS_MAP
	 */
	public static final String LIST_OF_CUSTOMER_OPEN_PROCESSES_PARAMETERS_MAP =
		"WFGetListOfCustomerOpenProcessesInSTR.listOfCustomerOpenProcessesMap";

	/**
	 * LIST_OF_CUSTOMER_OPEN_PROCESSES_OUTPUT
	 */
	public static final String LIST_OF_CUSTOMER_OPEN_PROCESSES_OUTPUT =
		"WFGetListOfCustomerOpenProcessesOutSTR.listOfCustomerOpenProcesses";

	/**
	 * GET_CUSTOMER_OPEN_PROCESSES_IDS_PARAMETERS_MAP
	 */
	public static final String GET_CUSTOMER_OPEN_PROCESSES_IDS_PARAMETERS_MAP =
		"WFGetCustomerOpenProcessesIdsInSTR.getCustomerOpenProcessesIdsMap";

	/**
	 * GET_CUSTOMER_OPEN_PROCESSES_IDS_ROWS_IN_PAGE
	 */
	public static final String GET_CUSTOMER_OPEN_PROCESSES_IDS_ROWS_IN_PAGE =
		"WFGetCustomerOpenProcessesIdsInSTR.numOfRowsInPage";

	/**
	 * GET_CUSTOMER_OPEN_PROCESSES_IDS_OUTPUT
	 */
	public static final String GET_CUSTOMER_OPEN_PROCESSES_IDS_OUTPUT =
		"WFGetCustomerOpenProcessesIdsOutSTR.getCustomerOpenProcessesPagingObject";


	/**
	 * AUTO_OPENING_OF_NEXT_WORK_ITEM_PARAMS_MAP
	 */
	public static final String AUTO_OPENING_OF_NEXT_WORK_ITEM_PARAMS_MAP =
		"WFAutoOpeningOfNextWorkItemInSTR.AutoOpeningOfNextWorkItemMap";

	/**
	 * AUTO_OPENING_OF_NEXT_WORK_ITEM_WORK_ITEM_ID
	 */
	public static final String AUTO_OPENING_OF_NEXT_WORK_ITEM_WORK_ITEM_ID =
		"WFAutoOpeningOfNextWorkItemInSTR.workItemId";

	/**
	 * AUTO_OPENING_OF_NEXT_WORK_ITEM_OUTPUT
	 */
	public static final String AUTO_OPENING_OF_NEXT_WORK_ITEM_OUTPUT =
		"WFAutoOpeningOfNextWorkItemOutSTR.nextWorkItemId";

	/**
	 * ALERT_FOR_AUTO_OPENING_OF_NEXT_WORK_ITEM_PARAMS_MAP
	 */
	public static final String ALERT_FOR_AUTO_OPENING_OF_NEXT_WORK_ITEM_PARAMS_MAP =
		"WFAlertForAutoOpeningOfNextWorkItemInSTR.AlertForAutoOpeningOfNextWorkItemMap";

	/**
	 * ALRET_FOR_AUTO_OPENING_OF_NEXT_WORK_ITEM_WORK_ITEM_ID
	 */
	public static final String ALRET_FOR_AUTO_OPENING_OF_NEXT_WORK_ITEM_WORK_ITEM_ID =
		"WFAlertForAutoOpeningOfNextWorkItemInSTR.workItemId";

	/**
	 * PREPARE_LIST_OF_USERS_OUTPUT
	 */
	public static final String PREPARE_LIST_OF_USERS_OUTPUT =
		"WFPrepareListOfUsersOutSTR.listOfUsersForViewAnother";

	/**
	 * PREPARE_LIST_OF_USERS_WITH_DETAILS_OUTPUT
	 */
	public static final String PREPARE_LIST_OF_USERS_DETAILS_OUTPUT =
		"WFPrepareListOfUsersWithDetailsOutSTR.listOfUsersForViewAnother";


	/**
	 * FORCE_RESTART_PARAMS_MAP
	 */
	public static final String FORCE_RESTART_PARAMS_MAP =
		"WFForceRestartInSTR.forceRestartMap";

	/**
	 * FORCE_RESTART_WORK_ITEM_ID
	 */
	public static final String FORCE_RESTART_WORK_ITEM_ID =
		"WFForceRestartInSTR.workItemId";

	/**
	 * GET_LAST_PERFORMED_ACTIVITY_PROCESS_INSTANCE_NAME
	 */
	public static final String GET_LAST_PERFORMED_ACTIVITY_PROCESS_INSTANCE_NAME =
		"WFGetLastPerformedActivityInSTR.processInstanceName";

	/**
	 * GET_LAST_PERFORMED_ACTIVITY_OUTPUT
	 */	
	public static final String GET_LAST_PERFORMED_ACTIVITY_OUTPUT =
		"WFGetLastPerformedActivityOutSTR.lastPerformedActivity";

	/**
	 * RETRIEVE_PEOPLE_THIS_USER_IS_AUTHORIZED_FOR_USER_ID
	 */
	public static final String RETRIEVE_PEOPLE_THIS_USER_IS_AUTHORIZED_FOR_USER_ID =
		"WFRetrievePeopleThisUserIsAuthorizedForInSTR.userId";

	/**
	 * RETRIEVE_PEOPLE_THIS_USER_IS_AUTHORIZED_FOR_OUTPUT
	 */
	public static final String RETRIEVE_PEOPLE_THIS_USER_IS_AUTHORIZED_FOR_OUTPUT =
		"WFRetrievePeopleThisUserIsAuthorizedForOutSTR.peopleThisUserIsAuthorizedFor";

	/**
	 * GET_LAST_HANDLER_OF_CUSTOMER_PARAMS_MAP
	 */
	public static final String GET_LAST_HANDLER_OF_CUSTOMER_PARAMS_MAP =
		"WFGetLastHandlerOfCustomerInSTR.lastHandlerOfCustomerMap";

	/**
	 * GET_LAST_HANDLER_OF_CUSTOMER_ROLE_NAME
	 */
	public static final String GET_LAST_HANDLER_OF_CUSTOMER_ROLE_NAME =
		"WFGetLastHandlerOfCustomerInSTR.roleName";

	/**
	 * GET_LAST_HANDLER_OF_CUSTOMER_PROCESS_TEMPLATE_NAME
	 */
	public static final String GET_LAST_HANDLER_OF_CUSTOMER_PROCESS_TEMPLATE_NAME =
		"WFGetLastHandlerOfCustomerInSTR.processTemplateName";

	/**
	 * GET_LAST_HANDLER_OF_CUSTOMER_OUTPUT
	 */
	public static final String GET_LAST_HANDLER_OF_CUSTOMER_OUTPUT =
		"WFGetLastHandlerOfCustomerOutSTR.lastHandlerOfCustomer";

}