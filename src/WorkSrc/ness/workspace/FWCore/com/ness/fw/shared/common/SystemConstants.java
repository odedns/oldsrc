/*
 * Author: yifat har-nof, Shay Rancus
 * @version $Id: SystemConstants.java,v 1.2 2005/04/12 12:46:38 shay Exp $
 */
package com.ness.fw.shared.common;

/**
 * Holds the system constants
 */
public class SystemConstants 
{
	
	// -------------------- //
	// Authorization Levels //
	// -------------------- //
	
	/**
	 * Constant for NOT_INITIALIZED authorization level. 
	 * It means that the element authorization was not initialized yet.   
	 */
	public static final int AUTH_LEVEL_NOT_INITIALIZED = 0;

	/**
	 * Constant for ALL authorization level. 
	 * It means that all the elements will be open / available. 
	 * The elemnts could be: flow / flow state / ui elements i.e. include jsp, 
	 * toolbar, text field, button etc.   
	 */
	public static final int AUTH_LEVEL_ALL = 1;
	
	/**
	 * Constant for READONLY authorization level. 
	 * It means that all the UI elements will be disabled.
	 * The flow will be created but all the activities (operation, service method, activity) 
	 * with activity type ReadWrite will no be able to be executed.   
	 */
	public static final int AUTH_LEVEL_READONLY = 2;

	/**
	 * Constant for NONE authorization level. 
	 * It means that all the UI elements will be not visible.
	 * The flow could be created.
	 * Cannot move to state.
	 */
	public static final int AUTH_LEVEL_NONE = 3;

	/**
	 * The constant for the default value for authorization level for the first flow,
	 * if a specific authorization id was not specified.
	 */
	public static final String DEFAUTL_FIRST_FLOW_AUTH_LEVEL = "defautFirstFlowAuthorizationLevel";


	/**
	 * Constant for LOCAL BPO implementation type.
	 * It means that the access to the business process methods will be directly 
	 * without proxies.
	 */
	public final static int BPO_IMPLEMENTATION_TYPE_LOCAL = 1;

	/**
	 * Constant for WS BPO implementation type.
	 * It means that the access to the business process methods will be using 
	 * web services proxy.
	 */
	public final static int BPO_IMPLEMENTATION_TYPE_WS = 2;

	/**
	 * Constant for EJB BPO implementation type.
	 * It means that the access to the business process methods will be using 
	 * ejb proxy.
	 */
	public final static int BPO_IMPLEMENTATION_TYPE_EJB = 3;

	// ----------- //
	// Event types //
	// ----------- //

	/**
	 * Constant for readOnly activity / event type. 
	 * It means that the activities that will be performed in the element will not 
	 * perform updates to the DB and could be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int EVENT_TYPE_READONLY = 1;
	
	/**
	 * Constant for readWrite activity / event type. 
	 * It means that the activities that will be performed in the element will 
	 * performs updates to the DB and should not be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int EVENT_TYPE_READWRITE = 2;
		
	
	// -------------------- //
	//  Context field names //
	// -------------------- //
	
	/**
	 * Constant for the name of the dirty flag model in the context.
	 */
	public final static String DIRTY_FLAG_FIELD_NAME = "dirtyFlagModel";
	
	/**
	 * Constant for the name of the messages model in the context.
	 */
	public final static String MESSAGES_FIELD_NAME = "messagesModel";

	/**
	 * Constant for the name of the user auth data field in the context.
	 */
	public final static String USER_AUTH_DATA_FIELD_NAME = "userAuthData";



	// ---------------------- //
	// System properties keys //
	// ---------------------- //

	/**
	 * Constant for the logger level mode indication key in the sys. configuraion properties file.
	 */
	public final static String SYS_PROPS_KEY_LOGGER_LEVEL_MODE = "systemLoggerLevel";

	/**
	 * Constant for the BPO implementation type key in the sys. configuraion properties file.
	 */
	public final static String SYS_PROPS_KEY_BPO_IMPLEMENTATION_TYPE = "businessProcessProxyImplType";
	
	/**
	 * Constant for the AuthManager Implementation key in the sys. configuraion properties file.
	 */
	public final static String SYS_PROPS_KEY_AUTH_MANAGER_IMPL = "AuthManagerImplementation";

	/**
	 * Constant for the MenuAuthManager Implementation key in the sys. configuraion properties file.
	 */
	public final static String SYS_PROPS_KEY_MENU_AUTH_MANAGER_IMPL = "MenuAuthManagerImplementation";
	
		
	// ----------------- //
	// Event target type //
	// ----------------- //
	
	/**
	 * This constant is used for the eventType property when sending an event to a normal window.
	 */
	public final static String EVENT_TARGET_TYPE_NORMAL = "normal";
	
	/**
	 * This constant is used for the eventType property when sending an event to a popup window.
	 */
	public final static String EVENT_TARGET_TYPE_POPUP = "popup";
	
	/**
	 * This constant is used for the eventType property when sending an event to a modal window.
	 */
	public final static String EVENT_TARGET_TYPE_DIALOG = "dialog";
	
	/**
	 * This constant is used for the eventType property when sending an event and closing a modal window.
	 */
	public final static String EVENT_TARGET_TYPE_CLOSE_DIALOG = "closeDialog";

	/**
	 * This constant is used for the eventType property when sending an event and closing a popup window.
	 */
	public final static String EVENT_TARGET_TYPE_CLOSE_POPUP = "closePopup";
	
	/**
	 * This constant is used for the eventType property when sending an event to open a new window.
	 */
	public final static String EVENT_TARGET_TYPE_NEW_WINDOW = "newWindow";

	// ----------------- //
	// Html names		 //
	// ----------------- //
	public final static String FORM_ELEMENT_PREFIX = "@";
	
	private final static String SYSTEM_REQUEST_PARAM_NAME_PREFIX = "systemParam";	
	public final static String SYSTEM_REQUEST_PARAM_VALUE = "true";
	public final static String REQUEST_PARAM_NAME_LOGOUT = SYSTEM_REQUEST_PARAM_NAME_PREFIX + "Logout";
	public final static String REQUEST_PARAM_NAME_RELOAD = SYSTEM_REQUEST_PARAM_NAME_PREFIX + "Reload";
	public final static String REQUEST_PARAM_NAME_LOGIN_ONERROR = SYSTEM_REQUEST_PARAM_NAME_PREFIX + "LoginOnError";

}
