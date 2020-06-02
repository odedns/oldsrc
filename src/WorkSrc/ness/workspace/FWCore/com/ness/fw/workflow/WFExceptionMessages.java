/*
 * Created on: 19/09/2004
 *
 * Author Amit Mendelson
 * @version $Id: WFExceptionMessages.java,v 1.8 2005/05/04 13:53:43 amit Exp $
 */
package com.ness.fw.workflow;

/**
 * This class defines all the exception messages in WorkFlow package.
 */
public class WFExceptionMessages
{

	//Errors
	/**WF0002
	 * NOT_LOGGED_IN_ERROR
	 * "Either not logged in or already logged off";
	 * This error occurs when the user is not logged in, and tries to
	 * use the WFExecutionService.
	 */
	public static final String NOT_LOGGED_IN_ERROR = "WF0002";

	/**WF0003
	 * PARAMETER_NAME_IS_NULL
	 * "Parameter name is null";
	 * This error occurs during creation of MQ WorkFlow WFWorkItem
	 * from the infrastructure's WFWorkItem.
	 */
	public static final String PARAMETER_NAME_IS_NULL = "WF0003";

	/**WF0004
	 * PARAMETERS_MAP_NULL
	 * "WorkFlowServiceParametersMap is null";
	 * This error occurs when the WorkFlowServiceParametersMap is null.
	 */
	public static final String PARAMETERS_MAP_NULL = "WF0004";
	/**WF0005
	 * DESCRIPTION_FIELD_NULL
	 * "Description field is null";
	 * This error occurs when the Description field is null.
	 */
	public static final String DESCRIPTION_FIELD_NULL = "WF0005";

	/**WF0006
	 * USER_ID_IS_NULL
	 * "User ID is null";
	 * This error occurs if the User ID is null.
	 */
	public static final String USER_ID_IS_NULL = "WF0006";

	/**WF0007
	 * PASSWORD_IS_NULL
	 * This error occurs if the password is null.
	 * "Password is null";
	 */
	public static final String PASSWORD_IS_NULL = "WF0007";

	/**WF0008
	 * TASKS_OWNED_BY_THIS_USER_PARAMETER_IS_NULL
	 * "TasksOwnedByThisUser parameter is null";
	 */
	public static final String TASKS_OWNED_BY_THIS_USER_PARAMETER_IS_NULL =
		"WF0008";
	/**WF0009
	 * SORT_CRITERIA_PARAMETER_IS_NULL_IN_MAP
	 * "SortCriteria parameter is null in the WorkFlowServiceParametersMap";
	 * The map contains a key named SortCriteria, but it's value is null.
	 */
	public static final String SORT_CRITERIA_PARAMETER_IS_NULL_IN_MAP =
		"WF0009";

	/**WF0010
	 * FILTER_PARAMETER_IS_NULL_IN_MAP
	 * "Filter parameter is null in the WorkFlowServiceParametersMap";
	 * The map contains a key named Filter, but it's value is null.
	 */
	public static final String FILTER_PARAMETER_IS_NULL_IN_MAP = "WF0010";
	/**WF0011
	 * THRESHOLD_PARAMETER_IS_NULL_IN_MAP
	 * "Threshold parameter is null in the WorkFlowServiceParametersMap";
	 * The map contains a key named Threshold, but it's value is null.
	 */
	public static final String THRESHOLD_PARAMETER_IS_NULL_IN_MAP = "WF0011";

	/**WF0012
	 * WORK_LIST_NAME_PARAMETER_IS_NULL_IN_MAP
	 * "WorkListName parameter is null in the WorkFlowServiceParametersMap";
	 * The map contains a key named WorkListName, but it's value is null.
	 */
	public static final String WORK_LIST_NAME_PARAMETER_IS_NULL_IN_MAP =
		"WF0012";

	/**WF0013
	 * WORK_ITEM_ID_PARAMETER_IS_NULL_IN_MAP
	 * "WorkItemID parameter is null in the WorkFlowServiceParametersMap";
	 */
	public static final String WORK_ITEM_ID_PARAMETER_IS_NULL_IN_MAP = "WF0013";

	/**WF0014
	 * SORT_CRITERIA_PARAMETER_VALUE_IS_NULL
	 * "SortCriteria parameter value is null";
	 * This error occurs when the sortCriteria parameter has no value.
	 */
	public static final String SORT_CRITERIA_PARAMETER_VALUE_IS_NULL = "WF0014";

	/**WF0015
	 * FILTER_PARAMETER_VALUE_IS_NULL
	 * "Filter parameter value is null";
	 * This error occurs when the filter parameter has no value.
	 */
	public static final String FILTER_PARAMETER_VALUE_IS_NULL = "WF0015";

	/**WF0016
	 * THRESHOLD_PARAMETER_VALUE_IS_NULL
	 * "Threshold parameter value is null";
	 * This error occurs when the threshold parameter has no value.
	 */
	public static final String THRESHOLD_PARAMETER_VALUE_IS_NULL = "WF0016";

	/**WF0017
	 * INVALID_PRIORITY_VALUE
	 * "Priority value is invalid";
	 * This error occurs if the priority (of a WFWorkItem or 
	 * a WFProcessInstance) is invalid.
	 */
	public static final String INVALID_PRIORITY_VALUE = "WF0017";

	/**WF0018
	 * CANNOT_RETURN_WORK_ITEM_AS_IT_IS_NOT_OWNED_BY_YOU
	 * "Cannot return WorkItem to the general queue, as the WorkItem is not owned by you";
	 */
	public static final String CANNOT_RETURN_WORK_ITEM_AS_IT_IS_NOT_OWNED_BY_YOU =
		"WF0018";

	/**WF0019
	 * NO_PRIORITY_SET_IN_DESCRIPTION
	 * "No priority was set in description field";
	 * This error occurs if the ProcessInstance description field
	 * lacks the priority.
	 */
	public static final String NO_PRIORITY_SET_IN_DESCRIPTION = "WF0019";

	/**WF0020
	 * ILLEGAL_PROCESS_INSTANCE_STATE
	 * "Illegal ProcessInstance state";
	 * This error occurs if the ProcessInstance state is not one of the
	 * legal states.
	 */
	public static final String ILLEGAL_PROCESS_INSTANCE_STATE = "WF0020";

	/**WF0021
	 * ILLEGAL_WORK_ITEM_STATE
	 * "Illegal WorkItem state";
	 * This error occurs if the WFWorkItem state is not one of the legal states.
	 */
	public static final String ILLEGAL_WORK_ITEM_STATE = "WF0021";

	/**
	 * This error occurs during creation of MQ WorkFlow WFWorkItem
	 * from the infrastructure's WFWorkItem.
	 */
	public static final String CONVERSION_TO_BINARY_FAILED =
		"Conversion to ByteArray has failed";

	/**WF0022
	 * WORK_ITEM_IDS_ARE_NULL
	 */
	public static final String WORK_ITEM_IDS_ARE_NULL = "WF0022";

	/**WF0023
	 * CANNOT_TRANSFER_CHECKED_OUT_ITEM
	 * Cannot transfer a checked-out item
	 * This error occurs in returnWorkItemToGeneralQueue, if
	 * the item to be transfered is checked out.
	 */
	public static final String CANNOT_TRANSFER_CHECKED_OUT_ITEM = "WF0023";

	/**WF0024
	 * ITEM_NOT_OWNED_BY_USER
	 * "The item is not owned by this user";
	 * This error occurs in checkOut/cancelCheckOut, if the WFWorkItem
	 * is not owned by this user.
	 */
	public static final String ITEM_NOT_OWNED_BY_USER = "WF0024";

	/**WF0025
	 * ITEM_ALREADY_CHECKED_OUT_BY_ANOTHER_USER
	 * "Cannot checkout the item as it is already checked out by another user";
	 * This error occurs in checkOut.
	 */
	public static final String ITEM_ALREADY_CHECKED_OUT_BY_ANOTHER_USER =
		"WF0025";

	/**WF0026
	 * WORK_ITEM_STATE_DOES_NOT_ALLOW_CANCEL_CHECK_OUT
	 * "WorkItem state does not allow cancel check out";
	 * WotkItem's state doesn't allow check out.
	 */
	public static final String WORK_ITEM_STATE_DOES_NOT_ALLOW_CANCEL_CHECK_OUT =
		"WF0026";

	/**WF0027
	 * WORK_ITEM_ALREADY_CHECKED_OUT_BY_THIS_USER
	 * "The WorkItem is already checked out by this user";
	 * The WorkItem is already checked out by this user.
	 */
	public static final String WORK_ITEM_ALREADY_CHECKED_OUT_BY_THIS_USER =
		"WF0027";

	/**WF0028
	 * ITEM_STATE_DOES_NOT_ALLOW_CHECKING_OUT
	 * "Item's state doesn't allow checking out";
	 * The WorkItem can't be checked out since it's state doesn't allow it.
	 */
	public static final String ITEM_STATE_DOES_NOT_ALLOW_CHECKING_OUT =
		"WF0028";

	/**WF0029
	 * WORK_ITEM_STATE_DOES_NOT_ALLOW_CHECKING_IN
	 * "WorkItem state doesn't allow checking in"
	 * The WorkItem can't be checked in since it's state doesn't allow it.
	 */
	public static final String WORK_ITEM_STATE_DOES_NOT_ALLOW_CHECKING_IN =
		"WF0029";

	/**WF0030
	 * SUBSTITUTE_IS_NULL
	 * "Substitute is null";
	 * This error occurs if the substitute of a user is null.
	 */
	public static final String SUBSTITUTE_IS_NULL = "WF0030";
	/**WF0031
	 * ITEM_IS_NOT_A_NOTIFICATION
	 * "Selected Item is not a notification";
	 * This error occurs during building of a notification WFWorkItem,
	 * if the passed MQ WorkFlow activity is not a notification.
	 */
	public static final String ITEM_IS_NOT_A_NOTIFICATION = "WF0031";

	/**WF0032
	 * EXPECTED_END_TIME_NULL
	 * "Expected end Time is null";
	 * This error occurs if the expected end time of a WFWorkItem (taken
	 * from the WFWorkItem description) is null.
	 */
	public static final String EXPECTED_END_TIME_NULL = "WF0032";

	/**WF0033
	 * ITEM_IS_NULL
	 * "Item is null";
	 * This error occurs if the Item is null.
	 */
	public static final String ITEM_IS_NULL = "WF0033";

	/**WF0034
	 * WORK_ITEM_IS_NULL
	 * "WorkItem is null";
	 * This error occurs if the WorkItem is null.
	 */
	public static final String WORK_ITEM_IS_NULL = "WF0034";

	/**WF0035
	 * WORK_LIST_IS_NULL
	 * "WorkList is null";
	 * This error occurs if the WorkList is null.
	 */
	public static final String WORK_LIST_IS_NULL = "WF0035";

	/**WF0036
	 * WORK_LIST_NAME_IS_NULL
	 * "WorkList name is null";
	 * This error occurs if the WorkList name is null.
	 */
	public static final String WORK_LIST_NAME_IS_NULL = "WF0036";

	/**WF0037
	 * WORK_ITEM_ID_IS_NULL
	 * "WorkItem ID is null";
	 * This error occurs if the WorkItem ID is null.
	 */
	public static final String WORK_ITEM_ID_IS_NULL = "WF0037";

	/**WF0038
	 * FILTER_IS_NULL
	 * "Filter is null";
	 * This error occurs if the Filter is null.
	 */
	public static final String FILTER_IS_NULL = "WF0038";

	/**WF0039
	 * PROCESS_INSTANCE_ID_IS_NULL
	 * "ProcessInstance ID is null";
	 * This error occurs if the Process ID is null.
	 */
	public static final String PROCESS_INSTANCE_ID_IS_NULL = "WF0039";

	/**WF0040
	 * PROCESS_TEMPLATE_IS_NULL
	 * "ProcessTemplate is null";
	 * This error occurs if the ProcessTemplate is null.
	 */
	public static final String PROCESS_TEMPLATE_IS_NULL = "WF0040";

	/**WF0041
	 * PROCESS_TEMPLATE_ID_IS_NULL
	 * "ProcessTemplate ID is null";
	 * This error occurs if the ProcessTemplate ID is null.
	 */
	public static final String PROCESS_TEMPLATE_ID_IS_NULL = "WF0041";

	/**WF0042
	 * RETRIEVED_WORKLIST_NULL
	 * "Retrieved WorkList is null";
	 * This error occurs if the retrieved WFWorkItemList is null.
	 */
	public static final String RETRIEVED_WORKLIST_NULL = "WF0042";

	/**WF0043
	 * PROCESS_INSTANCE_IDS_ARE_NULL
	 */
	public static final String PROCESS_INSTANCE_IDS_ARE_NULL = "WF0043";

	// Constants of WFWorkItem class

	/**WF0044
	 * WORK_ITEM_OWNER_IS_NULL
	 * "WorkItem parameter owner is null";
	 */
	public static final String WORK_ITEM_OWNER_IS_NULL = "WF0044";

	/*	
	/**WF0045
	 * WORK_ITEM_LAST_MODIFICATION_TIME_IS_NULL
	 * "WorkItem parameter lastModificationTime is null";
	 */
	//	public static final String WORK_ITEM_LAST_MODIFICATION_TIME_IS_NULL = "WF0045";

	/**WF0046
	 * WORK_ITEM_RECEIVED_TIME_IS_NULL
	 * "WorkItem parameter receivedTime is null";
	 */
	public static final String WORK_ITEM_RECEIVED_TIME_IS_NULL = "WF0046";

	/**WF0047
	 * WORK_ITEM_CREATION_TIME_IS_NULL
	 * "WorkItem parameter creationTime is null";
	 */
	public static final String WORK_ITEM_CREATION_TIME_IS_NULL = "WF0047";

	/**WF0048
	 * WORK_ITEM_ACTIVITY_NAME_IS_NULL
	 * "WorkItem parameter activityName is null";
	 */
	public static final String WORK_ITEM_ACTIVITY_NAME_IS_NULL = "WF0048";

	/**WF0049
	 * AT_LEAST_ONE_ITEM_IS_NULL
	 * "At least one of the retrieved items is null";
	 * This error occurs during retrieval of WorkItems (retrieveTasks).
	 */
	public static final String AT_LEAST_ONE_ITEM_IS_NULL = "WF0049";

	//various errors

	/*
	/**WF0050
	 * PROCESS_INSTANCES_ARRAY_IS_NULL
	 * "Process instances array is null";
	 * This error occurs if the retrieval of ProcessInstances from
	 * the MQ WorkFlow yields null results.
	 */
	//	public static final String PROCESS_INSTANCES_ARRAY_IS_NULL = "WF0050";

	/*
	/**	WF0051
	 * SESSION_MODE_IS_NULL
	 * "SessionMode is null";
	 * This error occurs if retrieval of SessionMode from the 
	 * workflow.properties file gave no results.
	 */
	//   public static final String SESSION_MODE_IS_NULL = "WF0051";

	/**WF0052
	 * PROCESS_CANNOT_BE_TERMINATED
	 * "The ProcessInstance cannot be terminated, as its state doesn't allow it";
	 * This error occurs in terminateProcess(), if the process state
	 * is undefined.
	 */
	public static final String PROCESS_CANNOT_BE_TERMINATED = "WF0052";

	/*
	/**WF0053
	 * DESCRIPTION_FIELD_INVALID
	 * "Description field is invalid";
	 * This error occurs in the method getPriority().
	 */
	//	public static final String DESCRIPTION_FIELD_INVALID = "WF0053";

	/**WF0054
	 * CANNOT_POSTPONE_WORK_ITEM_DATE_THAT_ALEADY_PASSED
	 * "Cannot postpone WorkItem to date that has already passed";
	 * This error occurs in the method postponeWorkItem()
	 */
	public static final String CANNOT_POSTPONE_WORK_ITEM_DATE_THAT_ALEADY_PASSED =
		"WF0054";

	/**WF0055
	 * AT_LEAST_ONE_PARAMETER_NULL
	 * "At least one parameter is null";
	 * This error occurs if at least one parameter is null
	 * (at the class WFWorkItemList).
	 */
	public static final String AT_LEAST_ONE_PARAMETER_NULL = "WF0055";

	//WFProcessInstance errors

	/**WF0056
	 * PROCESS_NAME_IS_NULL
	 * "ProcessInstance name is null";
	 * This error is thrown in the method setProcessName() of WFProcessInstance,
	 * in case the passed parameter is null, and in getLastPerformedActivity() of
	 * MQWorkFlowService.
	 */
	public static final String PROCESS_NAME_IS_NULL = "WF0056";

	/**WF0057
	 * PROCESS_TOP_LEVEL_NAME_IS_NULL
	 * "ProcessInstance topLevelName parameter is null";
	 * This error is thrown in the method setProcessTopLevelName() 
	 * of WFProcessInstance, in case the passed parameter is null.
	 */
	public static final String PROCESS_TOP_LEVEL_NAME_IS_NULL = "WF0057";

	/**WF0058
	 * PROCESS_STATE_IS_NULL
	 * "Process state is null";
	 * This error is thrown in the method setProcessState() 
	 * of WFProcessInstance, in case the passed parameter is null.
	 */
	public static final String PROCESS_STATE_IS_NULL = "WF0058";

	/*
	/**WF0059
	 * PROCESS_START_TIME_IS_NULL
	 * "ProcessInstance StartTime parameter is null";
	 * This error is thrown in the method setProcessStartTime() 
	 * of WFProcessInstance, in case the passed parameter is null.
	 */
	//	public static final String PROCESS_START_TIME_IS_NULL = "WF0059";

	/**WF0060
	 * PROCESS_CATEGORY_IS_NULL
	 * "ProcessInstance Category parameter is null";
	 * This error is thrown in the method setCategory() 
	 * of WFProcessInstance, in case the passed parameter is null.
	 */
	public static final String PROCESS_CATEGORY_IS_NULL = "WF0060";

	/**WF0061
	 * PROCESS_CONTEXT_IS_NULL
	 * "ProcessInstance ProcessContext parameter is null";
	 * This error is thrown in the method getProcessContext() 
	 * of WFProcessInstance, in case the passed parameter is null.
	 */
	public static final String PROCESS_CONTEXT_IS_NULL = "WF0061";

	/*
	/**WF0062
	 * PROCESS_STARTER_IS_NULL
	 * "ProcessInstance Starter parameter is null";
	 * This error is thrown in the method setStarter() 
	 * of WFProcessInstance, in case the passed parameter is null.
	 */
	//	public static final String PROCESS_STARTER_IS_NULL = "WF0062";

	/**WF0063
	 * WORK_ITEM_PROCESS_CATEGORY_IS_NULL
	 * "WorkItem parameter processCategory is null";
	 */
	public static final String WORK_ITEM_PROCESS_CATEGORY_IS_NULL = "WF0063";

	/**WF0064
	 * WORK_ITEM_PROCESS_NAME_IS_NULL
	 * "WorkItem parameter processName is null";
	 */
	public static final String WORK_ITEM_PROCESS_NAME_IS_NULL = "WF0064";

	/**WF0065
	 * WORK_ITEM_TYPE_INVALID
	 * "WorkItem's type is invalid";
	 */
	public static final String WORK_ITEM_TYPE_INVALID = "WF0065";

	/**WF0066
	 * WORK_ITEM_MISSING_PARAMETERS_MAP
	 * "Missing parameterMap";
	 */
	public static final String WORK_ITEM_MISSING_PARAMETERS_MAP = "WF0066";

	/**WF0067
	 * WORK_ITEM_END_TIME_NOT_SET
	 * "WorkItem end time is not set";
	 */
	public static final String WORK_ITEM_END_TIME_NOT_SET = "WF0067";

	//WorkFlowServiceParameter errors

	/**WF0068
	 * VALUE_NOT_SET
	 * "No value was set for this parameter";
	 * This error occurs if mo value was set for the parameter.
	 */
	public static final String VALUE_NOT_SET = "WF0068";

	/**WF0069
	 * NAME_PARAMETER_NULL
	 * "WorkFlowServiceParameter name is null";
	 * This error occurs if the parameter's name is null
	 */
	public static final String NAME_PARAMETER_NULL = "WF0069";

	//WFWorkItemList errors	

	/**WF0070
	 * NULL_FIELD_IN_WORKITEMS_ARRAY
	 * "The WorkItems array item is null at ";
	 * This error occurs during building of WFWorkItemList.
	 */
	public static final String NULL_FIELD_IN_WORKITEMS_ARRAY = "WF0070";

	//various errors

	/**WF0071
	 * PASSED_WORKITEMS_ARRAY_IS_NULL
	 * "Passed WorkItems array is null";
	 * This error is thrown in the method setActivitiesArray() of 
	 * WFProcessInstance, in case the passed array is null.
	 */
	public static final String PASSED_WORKITEMS_ARRAY_IS_NULL = "WF0071";

	/**WF0072
	 * WORK_ITEM_IS_NOT_A_TEAM_TASK
	 * "Cannot return the WorkItem to the general queue as it was not a team task";
	 * This error is thrown in the method returnWorkItemToGeneralQueue()
	 * of MQWorkFlowService
	 */
	public static final String WORK_ITEM_IS_NOT_A_TEAM_TASK = "WF0072";

	/**WF0073
	 * USER_ID_PARAMETER_IS_NULL_IN_MAP
	 * "userID parameter is null in the WorkFlowServiceParametersMap"
	 * 
	 */
	public static final String USER_ID_PARAMETER_IS_NULL_IN_MAP = "WF0073";

	/**WF0074
	 * TASKS_OWNED_BY_THIS_USER_PARAMETER_IS_NULL_IN_MAP
	 * "TasksOwnedByThisUser parameter is null in the WorkFlowServiceParametersMap"
	 */
	public static final String TASKS_OWNED_BY_THIS_USER_PARAMETER_IS_NULL_IN_MAP =
		"WF0074";

	/**WF0075
	 * DESCRIPTION_KEY_VALUE_DELIMITER_IS_NULL
	 * "Delimiter between key and value in the description is null";
	 */
	public static final String DESCRIPTION_KEY_VALUE_DELIMITER_IS_NULL =
		"WF0075";

	/**
	 * WF0076
	 * CUSTOMER_ID_IS_NULL
	 * "Customer ID is null"
	 * is used at doesCustomerHaveOpenProcesses().
	 */
	public static final String CUSTOMER_ID_IS_NULL = "WF0076";

	/**
	 * WF0077
	 * CUSTOMER_OPEN_PROCESSES_PARAMETER_IS_NULL_IN_MAP
	 * is used at doesCustomerHaveOpenProcesses().
	 */
	public static final String CUSTOMER_OPEN_PROCESSES_PARAMETER_IS_NULL_IN_MAP =
		"WF0077";

	/**
	 * WF0078
	 * THE_PROCESS_IS_NOT_LINEAR
	 * is used at autoOpeningOfNextWorkItem().
	 */
	public static final String THE_PROCESS_IS_NOT_LINEAR =
		"WF0078";

	/**
	 * WF0079
	 * CAN_OPEN_THE_NEXT_WORK_ITEM
	 * is used at alertForAutoOpeningOfNextWorkItem().
	 */
	public static final String CAN_OPEN_THE_NEXT_WORK_ITEM =
		"WF0079";

	/**WF0080
	 * PROCESS_TEMPLATE_NAME_IS_NULL
	 */
	public static final String PROCESS_TEMPLATE_NAME_IS_NULL = "WF0080";

	/**WF0081
	 * PROCESS_INSTANCE_CANNOT_BE_TERMINATED
	 * "The ProcessInstance cannot be terminated since it has checked-out WorkItems"
	 */
	public static final String PROCESS_INSTANCE_CANNOT_BE_TERMINATED = "WF0081";

	/**WF0082
	 * NO_PROCESS_TEMPLATE_FOUND_WITH_THE_GIVEN_NAME
	 * Is used during createAndStartProcessInstance().
	 */
	public static final String NO_PROCESS_TEMPLATE_FOUND_WITH_THE_GIVEN_NAME = "WF0082";

	/**WF0083
	 * MORE_THAN_ONE_PROCESS_TEMPLATE_FOUND_WITH_THE_GIVEN_NAME
	 * Is used during createAndStartProcessInstance().
	 */
	public static final String MORE_THAN_ONE_PROCESS_TEMPLATE_FOUND_WITH_THE_GIVEN_NAME = 
		"WF0083";
		
	/**
	 * WF0084
	 * NO_PROCESS_INSTANCES_RETRIEVED_FOR_CUSTOMER
	 * Is used during getLastHandlerOfCustomer().
	 */
	public static final String NO_PROCESS_INSTANCES_RETRIEVED_FOR_CUSTOMER =
		"WF0084";

	/**
	 * LAST_MODIFICATION_TIME_PARAMETER_IS_NULL_IN_MAP
	 * Is used during getLastHandlerOfCustomer().
	 */
	public static final String LAST_MODIFICATION_TIME_PARAMETER_IS_NULL_IN_MAP =
		"WF0085";

	/**
	 * LAST_MODIFICATION_TIME_PARAMETER_IS_NULL
	 * Is used during getLastHandlerOfCustomer().
	 */
	public static final String LAST_MODIFICATION_TIME_PARAMETER_VALUE_IS_NULL =
		"WF0086";
	
	/**
	 * CONTAINER_ELEMENT_NAMES_PARAMETER_IS_NULL
	 */
	public static final String CONTAINER_ELEMENT_NAMES_PARAMETER_IS_NULL = 
		"WF0087";
	
	/**
	 * WORK_ITEM_STATE_DOES_NOT_ALLOW_ROUTING
	 */
	public static final String WORK_ITEM_STATE_DOES_NOT_ALLOW_ROUTING =
		"WF0088";

	/**
	 * USER_IS_NOT_AUTHORIZED_FOR_THIS_OPERATION
	 */
	public static final String USER_IS_NOT_AUTHORIZED_FOR_THIS_OPERATION =
		"WF0089";

	/**
	 * WF0090
	 * USER_ALREADY_HAVE_THIS_WORK_ITEM
	 * is used at WFWorkItem.setProcessID().
	 */
	public static final String USER_ALREADY_HAVE_THIS_WORK_ITEM =
		"WF0090";
		
	/**
	 * PREDEFINED_FILTER_ID_IS_NULL
	 */
	public static final String PREDEFINED_FILTER_ID_IS_NULL =
		"WF0091";

	/**
	 * WF0092
	 * LAST_HANDLER_OF_CUSTOMER_PARAMETER_IS_NULL_IN_MAP
	 * is used at doesCustomerHaveOpenProcesses().
	 */
	public static final String LAST_HANDLER_OF_CUSTOMER_PARAMETER_IS_NULL_IN_MAP =
		"WF0092";

	/**
	 * WF0093
	 * DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETER_IS_NULL_IN_MAP
	 * is used at doesCustomerHaveOpenProcesses().
	 */
	public static final String DOES_CUSTOMER_HAVE_OPEN_PROCESSES_PARAMETER_IS_NULL_IN_MAP =
		"WF0093";

	/**
	 * WF0094
	 * CONTAINER_ELEMENT_NAME_NOT_FOUND
	 * is used at fillMapFromContainerElements().
	 */
	public static final String CONTAINER_ELEMENT_NAME_NOT_FOUND =
		"WF0094";

	/**
	 * WF0095
	 * WORK_ITEM_PROCESS_ID_IS_NULL
	 * is used at WFWorkItem.setProcessID().
	 */
	public static final String WORK_ITEM_PROCESS_ID_IS_NULL =
		"WF0095";

	/**
	 * WF0096
	 * MISSING_GLOBAL_CONTAINER_ELEMENT
	 */
	public static final String MISSING_GLOBAL_CONTAINER_ELEMENT =
		"WF0096";

	/**
	 * WF0097
	 * GLOBAL_CONTAINER_ELEMENT_NAME_IS_NULL_IN_THE_MAP
	 */
	public static final String GLOBAL_CONTAINER_ELEMENT_NAME_IS_NULL_IN_THE_MAP =
		"WF0097";

	/**
	 * WF0098
	 * CONTAINER_FIELDS_PARAMETER_IS_NULL
	 */
	public static final String CONTAINER_FIELDS_PARAMETER_IS_NULL =
		"WF0098";

	/**
	 * WF0099
	 * USER_CREDENTIALS_IS_NULL
	 */
	public static final String USER_CREDENTIALS_IS_NULL =
		"WF0099";

	/**
	 * WF0100
	 * ORGANIZATION_DOES_NOT_EXIST
	 */
	public static final String ORGANIZATION_DOES_NOT_EXIST =
		"WF0100";

	/**
	 * WF0101
	 * MANAGER_USER_ID_IS_NULL
	 */
	public static final String MANAGER_USER_ID_IS_NULL =
		"WF0101";

	/**
	 * WF0102
	 * MANAGER_PASSWORD_IS_NULL
	 */
	public static final String MANAGER_PASSWORD_IS_NULL =
		"WF0102";

	/**
	 * WF0103
	 * GENERAL_QUEUE_UNDEFINED
	 */
	public static final String GENERAL_QUEUE_UNDEFINED =
		"WF0103";

	/**
	 * WF0104
	 * PAGING_SERVICE_IS_NULL
	 */
	public static final String PAGING_SERVICE_IS_NULL =
		"WF0104";

	/**
	 * WF0105
	 * PAGING_SERVICE_EXCEPTION
	 */
	public static final String PAGING_SERVICE_EXCEPTION =
		"WF0105";

	/**
	 * WF0106
	 * USER_UNKNOWN
	 */
	public static final String USER_UNKNOWN =
		"WF0106";

	/*
	 * Un-numbered exceptions.
	 * Those exceptions are not numbered as they indicate serious exceptions,
	 * not meant for the user but to the programmer.
	 * For example, Exceptions that involve workflow's containers - data
	 * structures that should not be visible to the end-user.
	 */
	 /**
	  * CONTAINER_MEMBER_NOT_FOUND
	  * Is thrown when attempting to access a field that doesn't exist
	  * in the container.
	  */
	public static final String CONTAINER_MEMBER_NOT_FOUND =
		"Container member not found: ";
	/** 
	 * This error occurs when the WFExecutionService is null.
	 */
	public static final String EXECUTION_SERVICE_NULL =
		"The ExecutionService is null";

	/**
	 * Is used if the WFWorkItem's or WFProcessInstance's state is null.
	 */
	public static final String EXECUTION_STATE_IS_NULL =
		"ExecutionState is null";

	/**
	 * Is used if the Container is null.
	 */
	public static final String CONTAINER_IS_NULL = "Container is null";

	/**
	 * This error occurs if the ProcessInstance is null.
	 */
	public static final String PROCESS_INSTANCE_IS_NULL =
		"ProcessInstance is null";

	/**
	 * This error occurs when creating WFWorkItem/WFProcessInstance 
	 * WorkFlowServiceParameterMap from its containers, if the container
	 * contains complex container elements.
	 */
	public static final String COMPLEX_CONTAINER_ELEMENTS_NOT_SUPPORTED =
		"Complex ContainerElements are not supported in this version";

	/**
	 * This error occurs in returnWorkItemToGeneralQueue, in case the
	 * role is missing in the input container.
	 */
	public static final String ROLE_CONTAINER_PARAMETER_IS_NULL =
		"Role parameter in the container is null";
	/**
	 * This error occurs in returnWorkItemToGeneralQueue, in case the
	 * organization is missing in the input container.
	 */
	public static final String ORGANIZATION_CONTAINER_PARAMETER_IS_NULL =
		"Organization parameter in the container is null";
	/**
	 * This error occurs when the requested parameter exists in the map,
	 * but its value is null.
	 */
	public static final String PARAMETER_IS_NULL_IN_MAP =
		" parameter is null in the WorkFlowServiceParametersMap";

	/*
	 * General messages in the WorkFlowFlowerService for the container
	 */
	 /**
	  * AGENT_PROBLEM
	  */
	 public static final String AGENT_PROBLEM = "Agent problem";
	 
	/**
	 * CONTEXT_PROBLEM
	 */
	 public static final String CONTEXT_PROBLEM = "Context problem";
	 
	/**
	 * WORKFLOW_PROBLEM
	 */
	 public static final String WORKFLOW_PROBLEM = "WorkFlow problem";
	 
	/**
	 * LDAP_INITIALIZATION_PROBLEM
	 */
	 public static final String LDAP_INITIALIZATION_PROBLEM = 
		"LDAP initialization problem";

}
