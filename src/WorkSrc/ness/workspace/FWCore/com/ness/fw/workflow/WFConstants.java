/*
 * Created on: 05/08/2004
 * Author Amit Mendelson
 * @version $Id: WFConstants.java,v 1.16 2005/05/05 14:16:58 amit Exp $
 */
package com.ness.fw.workflow;

/**
 * List of all constants used in the WorkFlow module, excluding the
 * Agent constants (which appear in another class, AgentConstants)
 * and LDAP constants.
 */
public class WFConstants
{

	/**
	 * TARGET_USER_ID
	 */
	public static final String TARGET_USER_ID = "targetUserId";

	/**
	 * DESCRIPTION_PAIRS_DELIMITER
	 * This delimiter will separate between pairs in the description.
	 */
	public static final String DESCRIPTION_PAIRS_DELIMITER = ";";
	/**
	 * DESCRIPTION_KEY_VALUE_DELIMITER
	 * This delimiter will separate between key and value in a single
	 * pair in the delimiter.
	 */
	public static final String DESCRIPTION_KEY_VALUE_DELIMITER = "=";

	//Constants used in the workflow.properties file

	/**
	 * SESSION_MODE
	 */
	public static final String SESSION_MODE = "WorkFlow.SessionMode";

	/**
	 * PROCESS_INSTANCES_THRESHOLD_KEY
	 * This constant represents the value, in the properties file,
	 * of the WFProcessInstance threshold (number of ProcessInstances to 
	 * retrieve in querying ProcessInstances).
	 */
	public static final String PROCESS_INSTANCES_THRESHOLD_KEY =
		"WorkFlow.ProcessInstancesThreshold";

	/**
	 * PROCESS_TEMPLATES_THRESHOLD_KEY
	 * This constant represents the value, in the properties file,
	 * of the ProcessTemplate names threshold (number of names of
	 * ProcessTemplates to retrieve in querying ProcessTemplate names).
	 */
	public static final String PROCESS_TEMPLATES_THRESHOLD_KEY =
		"WorkFlow.ProcessTemplatesThreshold";

	/**
	 * WORK_ITEMS_THRESHOLD_KEY
	 * This constant represents the value, in the properties file,
	 * of the WorkItems threshold (number of WorkItems to retrieve in
	 * querying WorkItems).
	 */
	public static final String WORK_ITEMS_THRESHOLD_KEY =
		"WorkFlow.WorkItemsThreshold";

	/**
	 * This mask is for calculating WFWorkItem expected ending time from the
	 * value of the description field.
	 */
	public static final String DATE_MASK = "WorkFlow.DateMask";

	/**
	 * Min Priority value are according to the priority range of
	 * IBM workflow.
	 */
	public static final String PROCESS_MIN_PRIORITY_VALUE =
		"WorkFlow.ProcessMinPriority";
	/**
	 * Max Priority value is according to the priority range of
	 * IBM workflow.
	 */
	public static final String PROCESS_MAX_PRIORITY_VALUE =
		"WorkFlow.ProcessMaxPriority";

	/**
	 * This constant represents the name of the key "ProcessInstancePriority".
	 */
	public static final String DESCRIPTION_PRIORITY_KEY =
		"WorkFlow.ProcessInstancePriorityKey";

	/**
	 * This constant represents the name of the key "WorkItemPriority".
	 */
	public static final String DESCRIPTION_WORK_ITEM_PRIORITY_KEY =
		"WorkFlow.WorkItemPriorityKey";

	/**
	 * Max Priority value is according to the priority range of
	 * IBM workflow.
	 */
	public static final String DEFAULT_WORK_ITEM_PRIORITY_VALUE =
		"WorkFlow.DefaultWorkItemPriority";

	/**
	 * This constant represents the name of the key "WorkItemExpectedEndTime".
	 */
	public static final String DESCRIPTION_EXPECTED_END_TIME_KEY =
		"WorkFlow.WorkItemExpectedEndTimeKey";

	/**
	 * This constant represents the name of the key "trackWorkItem".
	 * This parameter is used when routing a WorkItem.
	 */
	public static final String DESCRIPTION_TRACK_WORK_ITEM_KEY =
		"WorkFlow.TrackWorkItemKey";

	/**
	 * NUMBER_OF_TRIES
	 * A constant in the properties file - number of tries before
	 * stop calling a method.
	 */	
	public static final String NUMBER_OF_TRIES_KEY = 
		"WorkFlow.NumberOfTries";

	/**
	 * TIME_TO_WAIT
	 * A constant in the properties file - time to wait before another
	 * activation of a method (is used at "autoOpeningOfNextWorkItem").
	 */	
	public static final String TIME_TO_WAIT_KEY = "WorkFlow.TimeToWait";

	/**
	 * MANAGER_USER_ID
	 * A constant in the properties file - manager user Id.
	 */	
	public static final String MANAGER_USER_ID_KEY = "WorkFlow.ManagerUserId";

	/**
	 * MANAGER_PASSWORD_KEY
	 * A constant in the properties file - manager password.
	 */	
	public static final String MANAGER_PASSWORD_KEY = "WorkFlow.ManagerPassword";

	/**
	 * USERS_SET_INITIAL_SIZE_KEY
	 * A constant in the properties file - initial size for sets of users.
	 */	
	public static final String USERS_SET_INITIAL_SIZE_KEY = "WorkFlow.UsersSetInitialSize";

	/**
	 * UNDEFINED_POSTPONE_TIME_VALUE
	 * This constant is used in case no postpone time was defined, to prevent
	 * an exception in the method getNumberOfDaysForActivityCompletion().
	 */
	public static final String UNDEFINED_POSTPONE_TIME_VALUE = 
		"WorkFlow.UndefinedPostponeTime";

	/**
	 * Is used in retrieval of open processes for customer.
	 */
	public static final String STATE_ASC = "STATE ASC";

	/**
	 * Is used in retrieval of process template names
	 * (default sorting option, if no other sort option supported).
	 */
	public static final String NAME_ASC = "NAME ASC";

	/**
	 * This prefix is used in retrieval of open processes for
	 * customer, can be used also in another places in the document.
	 */
	public static final String FILTER_AND_INFIX = " AND ";

	/**
	 * FILTER_FOR_AUTO_OPEN_PREFIX
	 * This prefix is used in the method autoOpeningOfNextWorkItem.
	 */
	public static final String FILTER_FOR_AUTO_OPEN_PREFIX = 
		"STATE IN READY AND PROCESS_NAME LIKE '";

	/**
	 * FILTER_CHECK_OUT_STATE_PREFIX
	 * Is used in the method terminateProcess().
	 */
	public static final String FILTER_CHECK_OUT_STATE_PREFIX = 
		"AND STATE IN CHECKED_OUT";

	/**
	 * Is used in returnWorkItemToGeneralQueue().
	 */
	public static final String CONTAINER_ROLE = "_PROCESS_INFO.Role";
	/**
	 * Is used in returnWorkItemToGeneralQueue().
	 */
	public static final String CONTAINER_ORGANIZATION =
		"_PROCESS_INFO.Organization";

	/**
	 * Is used in getLastHandlerOfCustomer().
	 */
	public static final String ACTIVITY_ORGANIZATION = "_ACTIVITY_INFO.Organization";
	/**
	 * Is used in returnWorkItemToGeneralQueue().
	 */
	public static final String MEMBER_OF_ROLES =
		"_ACTIVITY_INFO.MembersOfRoles";

	/**
	 * Is used in returnWorkItemToGeneralQueue().
	 */
	public static final String PEOPLE = "_ACTIVITY_INFO.People";

	/**
	 * Is used in retrieval of tasks marked for show only.
	 */
	public static final String TRUE = "true";

	//Various constants
	/**
	 * SESSION_MODE_DEFAULT - Represents default session mode
	 * (for logging workflow).
	 */
	public static final String SESSION_MODE_DEFAULT = "Default";

	/**
	 * PRESENT_HERE - Represents "Present Here" session mode
	 * (for logging workflow).
	 */
	public static final String SESSION_MODE_PRESENT_HERE = "PresentHere";
	/**
	 * PRESENT - Represents "Present" session mode
	 * (for logging workflow).
	 */
	public static final String SESSION_MODE_PRESENT = "Present";

	/**
	 * PROCESS_CONTEXT
	 */
	public static final String PROCESS_CONTEXT = "processContext";

	/**
	 * PROCESS_INSTANCE_ID
	 */
	public static final String PROCESS_INSTANCE_ID = "processInstIdIN";

	/**
	 * Name of super-virtual user in the system. all the users will be
	 * authorized for this user.
	 */
	public static final String SUPER_VIRTUAL_USER_ID = "ALLPERSON";

	//Constants to be passed in the WorkFlowServiceParametersMap
	/**
	 * USER_ID
	 */
	public static final String USER_ID = "userIdIN";

	/**
	 * PASSWORD
	 */
	public static final String PASSWORD = "passwordIN";

	/**
	 * FILTER
	 */
	public static final String FILTER = "Filter";
	/**
	 * SORT_CRITERIA
	 */
	public static final String SORT_CRITERIA = "SortCriteria";

//	/**
//	 * WORKLIST_NAME
//	 */
//	public static final String WORKLIST_NAME = "WorkListName";

	/**
	 * ACTIVITY_ID
	 */
	public static final String ACTIVITY_ID = "ActivityID";
	/**
	 * WORKITEM_ID
	 */
	public static final String WORKITEM_ID = "workItemId";
	/**
	 * PROCESS_ID
	 */
	public static final String PROCESS_ID = "ProcessID";

	/**
	 * ID_OF_NEXT_WORK_ITEM
	 */
	public static final String ID_OF_NEXT_WORK_ITEM = "IDOfNextWorkItem";

	/**
	 * TASKS_OWNED_BY_THIS_USER
	 */
	public static final String TASKS_OWNED_BY_THIS_USER =
		"TasksOwnedByThisUser";

	/**
	 * LAST_HANDLER_OF_CUSTOMER_PARAMETER
	 * is used at the service getLastHandlerOfCustomer(),
	 * getCustomerOpenProcesses(), doesCustomerHaveOpenProcesses()
	 */
	public static final String LAST_HANDLER_OF_CUSTOMER_PARAMETER =
		"CustomerIDForLastHandler";

	/**
	 * LONG
	 */
	public static final String LONG = "LONG";
	/**
	 * DOUBLE
	 */
	public static final String DOUBLE = "DOUBLE";
	/**
	 * BINARY
	 */
	public static final String BINARY = "BINARY";
	/**
	 * STRING
	 */
	public static final String STRING = "STRING";
	/**
	 * BINARY_MAP
	 */
	public static final String BINARY_MAP = "BinaryMap";

	/**
	 * This parameter name represents the expected end time of
	 * the WFWorkItem.
	 */
	public static final String EXPECTED_END_TIME = "expectedEndTime";
	
	/**
	 * STATE_IN_READY_OR_RUNNING_PREFIX
	 * is used in internalGetCustomerOpenProcesses()
	 */
	public static final String STATE_IN_READY_OR_RUNNING_PREFIX = 
		"((STATE IN READY) OR (STATE IN RUNNING)) AND ";

	/**
	 * PROCESS_STATE_IN_READY_OR_RUNNING_PREFIX
	 * is used in routeWorkItemsOfThisCustomer()
	 */
	public static final String PROCESS_STATE_IN_READY_OR_RUNNING_PREFIX = 
		"((PROCESS_STATE IN READY) OR (PROCESS_STATE IN RUNNING)) AND ";
	
	// Next constants are used to find the last handler of a process.
	/**
	 * Filter on states finished/force_finished.
	 */
	public static final String FILTER_STATE_FOR_LAST_HANDLER =
		"((STATE IN FORCE_FINISHED) OR (STATE IN FINISHED))";
	/**
	 * 
	 */
	public static final String FILTER_PROCESS_NAME_PREFIX =
		" AND (PROCESS_NAME IN '";
	/**
	 * 
	 */
	public static final String SORT_LAST_MODIFICATION_TIME_DESC =
		"LAST_MODIFICATION_TIME DESC";
	/**
	 * 
	 */
	public static final String RIGHT_PHARENTESIS = "')";

	/*
	 * Next constants are used to create a WFProcessInstance from IBM's WFProcessInstance.
	 */
	/**
	 * Allows filtering according to process name.
	 */
	public static final String FILTER_PROCESS_NAME_IN = "PROCESS_NAME IN '";
	/**
	 * represent the ' that comes after a string in filter.
	 */
	public static final String GERESH = "'";
	/**
	 * sorting according to creation time, ascending.
	 */
	public static final String SORT_CREATION_TIME_ASC = "CREATION_TIME ASC";

	/**
	 * Is used in retrieveTasks filter
	 */
	public static final String OWNER_EQUALS_PREFIX = "OWNER = '";

	/**
	 * Is used at retrieveTasks()
	 */
	public static final String FILTER_CURRENT_USER_SUFFIX =
		" AND OWNER IN CURRENT_USER";
	
	/**
	 * Is used at retrieveTasks()
	 */
	public static final String FILTER_OWNER_IN_CURRENT_USER_SUFFIX =
		"OWNER IN CURRENT_USER";

	//WFWorkItem Constants

	/**
	 * WORKITEM_TYPE
	 */
	public static final int WORKITEM_TYPE = 1;
	/**
	 * NOTIFICATION_TYPE
	 */
	public static final int NOTIFICATION_TYPE = 2;
	/**
	 * ACTIVITY_INSTANCE_TYPE
	 */
	public static final int ACTIVITY_INSTANCE_TYPE = 3;

	/**
	 * WORKITEM_STATE_CHECKED_OUT
	 */
	public static final int WORKITEM_STATE_CHECKED_OUT = 11;
	/**
	 * WORKITEM_STATE_DELETED
	 */
	public static final int WORKITEM_STATE_DELETED = 12;
	/**
	 * WORKITEM_STATE_DISABLED
	 */
	public static final int WORKITEM_STATE_DISABLED = 13;
	/**
	 * WORKITEM_STATE_EXECUTED
	 */
	public static final int WORKITEM_STATE_EXECUTED = 14;
	/**
	 * WORKITEM_STATE_EXPIRED
	 */
	public static final int WORKITEM_STATE_EXPIRED = 15;
	/**
	 * WORKITEM_STATE_FINISHED
	 */
	public static final int WORKITEM_STATE_FINISHED = 16;
	/**
	 * WORKITEM_STATE_FORCE_FINISHED
	 */
	public static final int WORKITEM_STATE_FORCE_FINISHED = 17;
	/**
	 * WORKITEM_STATE_IN_ERROR
	 */
	public static final int WORKITEM_STATE_IN_ERROR = 18;
	/**
	 * WORKITEM_STATE_PLANNING
	 */
	public static final int WORKITEM_STATE_PLANNING = 19;
	/**
	 * WORKITEM_STATE_READY
	 */
	public static final int WORKITEM_STATE_READY = 20;
	/**
	 * WORKITEM_STATE_RUNNING
	 */
	public static final int WORKITEM_STATE_RUNNING = 21;
	/**
	 * WORKITEM_STATE_SUSPENDED
	 */
	public static final int WORKITEM_STATE_SUSPENDED = 22;
	/**
	 * WORKITEM_STATE_SUSPENDING
	 */
	public static final int WORKITEM_STATE_SUSPENDING = 23;
	/**
	 * WORKITEM_STATE_TERMINATED
	 */
	public static final int WORKITEM_STATE_TERMINATED = 24;
	/**
	 * WORKITEM_STATE_TERMINATING
	 */
	public static final int WORKITEM_STATE_TERMINATING = 25;
	/**
	 * WORKITEM_STATE_UNDEFINED
	 */
	public static final int WORKITEM_STATE_UNDEFINED = 26;

	//WFProcessInstance constants
	/**
	 * PROCESS_STATE_DELETED
	 */
	public static final int PROCESS_STATE_DELETED = 30;
	/**
	 * PROCESS_STATE_FINISHED
	 */
	public static final int PROCESS_STATE_FINISHED = 31;
	/**
	 * PROCESS_STATE_READY
	 */
	public static final int PROCESS_STATE_READY = 32;
	/**
	 * PROCESS_STATE_RUNNING
	 */
	public static final int PROCESS_STATE_RUNNING = 33;
	/**
	 * PROCESS_STATE_SUSPENDED
	 */
	public static final int PROCESS_STATE_SUSPENDED = 34;
	/**
	 * PROCESS_STATE_SUSPENDING
	 */
	public static final int PROCESS_STATE_SUSPENDING = 35;
	/**
	 * PROCESS_STATE_TERMINATED
	 */
	public static final int PROCESS_STATE_TERMINATED = 36;
	/**
	 * PROCESS_STATE_TERMINATING
	 */
	public static final int PROCESS_STATE_TERMINATING = 37;
	/**
	 * PROCESS_STATE_UNDEFINED
	 */
	public static final int PROCESS_STATE_UNDEFINED = 38;

	//WorkFlowServiceParameter constants

	/**
	 * CONTAINER_TYPE_GLOBAL_CONTAINER
	 */
	public static final int CONTAINER_TYPE_GLOBAL_CONTAINER = 10;
	/**
	 * CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER
	 */
	public static final int CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER = 20;
	/**
	 * CONTAINER_TYPE_ACTIVITY_BINARY_INPUT_CONTAINER
	 */
	public static final int CONTAINER_TYPE_ACTIVITY_BINARY_INPUT_CONTAINER = 21;
	/**
	 * CONTAINER_TYPE_ACTIVITY_OUTPUT_CONTAINER
	 */
	public static final int CONTAINER_TYPE_ACTIVITY_OUTPUT_CONTAINER = 30;
	/**
	 * CONTAINER_TYPE_ACTIVITY_BINARY_OUTPUT_CONTAINER
	 */
	public static final int CONTAINER_TYPE_ACTIVITY_BINARY_OUTPUT_CONTAINER =
		31;
	/**
	 * DATA_TYPE_PROCESS_CONTEXT
	 */
	public static final int DATA_TYPE_PROCESS_CONTEXT = 40;
	/**
	 * DATA_TYPE_PROCESS_INSTANCE_DESCRIPTION
	 */
	public static final int DATA_TYPE_PROCESS_INSTANCE_DESCRIPTION = 50;
	/**
	 * DATA_TYPE_WORKITEM_DESCRIPTION
	 */
	public static final int DATA_TYPE_WORKITEM_DESCRIPTION = 55;
	/**
	 * DATA_TYPE_PERSON_DESCRIPTION
	 */
	public static final int DATA_TYPE_PERSON_DESCRIPTION = 60;

	/**
	 * OBJECT_TYPE_STRING
	 */
	public static final int OBJECT_TYPE_STRING = 100;
	/**
	 * OBJECT_TYPE_LONG
	 */
	public static final int OBJECT_TYPE_LONG = 110;
	/**
	 * OBJECT_TYPE_DOUBLE
	 */
	public static final int OBJECT_TYPE_DOUBLE = 120;
	/**
	 * OBJECT_TYPE_BOOLEAN
	 */
	public static final int OBJECT_TYPE_BOOLEAN = 130;
	/**
	 * OBJECT_TYPE_WORKFLOW_SERVICE_PARAMETER_MAP
	 */
	public static final int OBJECT_TYPE_WORKFLOW_SERVICE_PARAMETER_MAP = 140;
	/**
	 * OBJECT_TYPE_CALENDAR
	 */
	public static int OBJECT_TYPE_CALENDAR = 150;
	/**
	 * OBJECT_TYPE_BINARY
	 */
	public static int OBJECT_TYPE_BINARY = 160;
	/**
	 * OBJECT_TYPE_ANOTHER_CLASS
	 */
	public static final int OBJECT_TYPE_ANOTHER_CLASS = 170;

	/**
	 * ARGUMENT_TYPE_INPUT
	 */
	public static final int ARGUMENT_TYPE_INPUT = 200;
	/**
	 * ARGUMENT_TYPE_OUTPUT
	 */
	public static final int ARGUMENT_TYPE_OUTPUT = 210;
	/**
	 * ARGUMENT_TYPE_INPUT_OUTPUT
	 */
	public static final int ARGUMENT_TYPE_INPUT_OUTPUT = 220;
	
	/**
	 * CUSTOMER_ID
	 */
	public static final String CUSTOMER_ID = "CustomerId";
	
	/**
	 * WORK_ITEMS_THRESHOLD
	 * Threshold for workItems in the parametersMap 
	 */
	public static final String WORK_ITEMS_THRESHOLD = "WorkItemsThreshold";

	/**
	 * PROCESS_INSTANCES_THRESHOLD
	 * Threshold for processInstances in the parametersMap 
	 */
	public static final String PROCESS_INSTANCES_THRESHOLD = "ProcessInstancesThreshold";

	/**
	 * PROCESS_TEMPLATES_THRESHOLD
	 * Threshold for processTemplates in the parametersMap 
	 */
	public static final String PROCESS_TEMPLATES_THRESHOLD = "ProcessTemplatesThreshold";

	/**
	 * STATE_ASC_AND_START_TIME_DESC
	 * Is used in the method getLastHandlerOfCustomer().
	 */
	public static final String STATE_ASC_AND_START_TIME_DESC = 
		"STATE ASC, START_TIME DESC";
	
	/**
	 * CREATION_TIME_ASC
	 * Is used in the method getLastHandlerOfCustomer().
	 */
	public static final String CREATION_TIME_ASC = "CREATION_TIME ASC";


	/**
	 * WORK_FLOW_DATE_MASK
	 * Is used in filtering at getLastHandlerOfCustomer()
	 */
	public static final String WORK_FLOW_DATE_MASK = "yyyy-MM-dd";
	
	/**
	 * MAX_LAST_MODIFICATION_TIME
	 * Is used in filtering at getLastHandlerOfCustomer()
	 */
	public static final String MAX_LAST_MODIFICATION_TIME = "MaxLastModificationTime";
	
	/**
	 * IN_CONTAINER_ELEMENT_NAMES
	 */	
	public static final String IN_CONTAINER_ELEMENT_NAMES = "inContainerElementNames";

	/**
	 * OUT_CONTAINER_ELEMENT_NAMES
	 */	
	public static final String OUT_CONTAINER_ELEMENT_NAMES = "outContainerElementNames";

	/**
	 * GLOBAL_CONTAINER_ELEMENT_NAMES
	 */	
	public static final String GLOBAL_CONTAINER_ELEMENT_NAMES = 
		"globalContainerElementNames";

	/**
	 * PROCESS_NAME_LIKE_PREFIX
	 */
	public static final String PROCESS_NAME_LIKE_PREFIX = "PROCESS_NAME LIKE '";
	
	/**
	 * LAST_MODIFICATION_TIME_INFIX
	 */
	public static final String LAST_MODIFICATION_TIME_INFIX = 
		" AND LAST_MODIFICATION_TIME >= ";
	
	/**
	 * STATE_OR_START_TIME_INFIX
	 */	
	public static final String STATE_OR_START_TIME_INFIX = 
		" AND (STATE IN (READY,RUNNING) OR START_TIME>= ";
	
//	/**
//	 * CUSTOMER_ID_IN_PROCESS_TEMPLATE_INFIX
//	 */	
//	public static final String CUSTOMER_ID_IN_PROCESS_TEMPLATE_INFIX =
//		"*:CustomerID LIKE '";
	
	/**
	 * LIKE_INFIX
	 */
	public static final String LIKE_INFIX =  " LIKE '";

	/**
	 * EQUALS_INFIX
	 */
	public static final String EQUALS_INFIX =  " = ";
	
	/**
	 * CUSTOMER_ID_PARAMETER
	 */	
	public static final String CUSTOMER_ID_PARAMETER = "CustomerIDParameter";
	
	/**
	 * PROCESS_NAME_LIKE_INFIX
	 */
	public static final String PROCESS_NAME_LIKE_INFIX = " AND PROCESS_NAME LIKE '";
	/**
	 * RETRIEVE_NOTIFICATIONS
	 */	
	public static final String RETRIEVE_NOTIFICATIONS = "retrieveNotifications";
	
	/**
	 * RETRIEVE_EXPIRATION_TIME
	 */
	public static final String RETRIEVE_EXPIRATION_TIME = "retrieveExpirationTime";
	
	/**
	 * PRIORITY
	 */
	public static final String PRIORITY = "Priority";
	
	/**
	 * NAME_IN_PREFIX
	 */
	public static final String NAME_IN_PREFIX = "NAME IN '";
	
	/**
	 * NAME_IS_NOT_NULL
	 * Is used at getProcessTemplateNames().
	 */
	public static final String NAME_IS_NOT_NULL = "NAME IS NOT NULL";
	
	/**
	 * IS_WORK_ITEM_OPEN_FOR_CUSTOMER
	 * Should be used for the indication about open WorkItems for customer.
	 */
	public static final String DOES_CUSTOMER_HAVE_OPEN_PROCESSES = 
		"doesCustomerHaveOpenProcesses";
	
	/**
	 * CONTAINER_FIELDS
	 * Fields to be written to the container when passing from one
	 * WorkItem to the next one.
	 */	
	public static final String CONTAINER_FIELDS = "containerFields";
	
	/**
	 * PROCESS_MODEL
	 * Is used to retrieve the WorkItem's processTemplateName.
	 */
	public static final String PROCESS_MODEL = "_PROCESS_MODEL";
	
	/**
	 * DEFAULT_TASKS_FILTER
	 * Is used when no filter is supplied.
	 */
	public static final String DEFAULT_TASKS_FILTER = "";
	
	/**
	 * NAME_LIKE
	 * Is used at getProcessInstanceByName().
	 */
	public static final String NAME_LIKE = "NAME LIKE '";

	/**
	 * NAME_EQUALS
	 * Is used at getProcessInstanceByName().
	 */
	public static final String NAME_EQUALS = "NAME = '";

	
	/**
	 * LOGON_TYPE_REGULAR
	 * Code for regular log-on.
	 */
	public static final int LOGON_TYPE_REGULAR = 0;
	
	/**
	 * LOGON_TYPE_SINGLE_SIGNON
	 * Code for single sign-on log-on.
	 */
	public static final int LOGON_TYPE_SINGLE_SIGNON = 1;
	
	/**
	 * CURRENT_USER
	 */
	public static final String CURRENT_USER = "Current user: ";
	
	/**
	 * USE_MANAGER
	 */
	public static final String USE_MANAGER = "useManager";
	
	/**
	 * OWNER_NOT_CURRENT_USER_SUFFIX
	 * Is used during routing of workItems of customer after checkout.
	 */
	public static final String OWNER_NOT_CURRENT_USER_SUFFIX =
		" AND OWNER <> CURRENT_USER";
}