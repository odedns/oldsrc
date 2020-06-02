/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: HTMLConstants.java,v 1.3 2005/04/12 12:46:54 shay Exp $
 */
package com.ness.fw.flower.servlet;

import com.ness.fw.shared.common.SystemConstants;


/**
 * Usefull constants for http request parsing and JSP tags building
 */
public class HTMLConstants
{
	/**
	 * used for logout request parsing
	 */
	public static final String LOGOUT = SystemConstants.REQUEST_PARAM_NAME_LOGOUT;
	public static final String LOGIN_ONERROR = SystemConstants.REQUEST_PARAM_NAME_LOGIN_ONERROR;
	public static final String RELOAD = SystemConstants.REQUEST_PARAM_NAME_RELOAD;

	public static final String RELOAD_BPO = "586_reload_bpo";
	public static final String RELOAD_SYSTEM_PROPERTIES = "586_reload_systemProperties";
	public static final String RELOAD_LOCALIZED_PROPERTIES = "586_reload_localizedProperties";
	public static final String RELOAD_LOGGER = "586_reload_logger";
	public static final String RELOAD_FLOWER = "586_reload_flower";

	/**
	 * key for error list in request
	 */
	public static final String ERROR_LIST = "errorList";

	/**
	 * key for main flow processor in the session
	 */
	public static final String FLOW_PROCESSOR = "flowProcessor";

	/**
	 * key for main flow processor in the session
	 */
	public static final String LOCK_OBJECT = "lockObject";

	/**
	 * key for result event in request
	 */
	public static final String RESULT_EVENT = "resultEvent";

	/**
	 * key for help admin indication in the session
	 */
	public static final String HELP_ADMIN = "helpAdmin";

	/**
	 * key for auth admin indication in the session
	 */
	public static final String AUTH_ADMIN = "authAdmin";

		
}
