/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ControllerServlet.java,v 1.17 2005/05/08 13:46:08 yifat Exp $
 */
package com.ness.fw.flower.servlet;

import com.ness.fw.bl.proxy.BPOInitializationManager;
import com.ness.fw.common.SystemInitializationManager;
import com.ness.fw.common.auth.*;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.lock.MultipleReadersSingleWriterLock;
import com.ness.fw.common.lock.SynchronizationLockException;
import com.ness.fw.common.logger.*;
import com.ness.fw.common.resources.*;
import com.ness.fw.flower.util.*;
import com.ness.fw.flower.common.LanguagesManager;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.shared.servlet.HtmlServletUtil;
import com.ness.fw.shared.servlet.authentication.AuthenticationException;
import com.ness.fw.shared.servlet.authentication.AuthenticationManager;

import javax.servlet.http.*;
import javax.servlet.*;

import java.io.*;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The single point which deeling with all HTTP requests
 * This class assumes that it is not interrupted.
 */
public class ControllerServlet extends HttpServlet
{
	private final static String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "SERVLET";
	private static final String RESPONSE_CONTEXT_TYPE = "text/html; charset=";


	//configuration parameters
	private static final String CP_APP_JSP_ROOT_PATH = "applicationJspRootPath";
	private static final String CP_SYS_JSP_ROOT_PATH = "systemJspRootPath";
	private static final String CP_ERROR_PAGE = "errorPage";
	private static final String CP_TIMEOUT_PAGE = "timeoutPage";
	private static final String CP_LOGOUT_PAGE = "logoutPage"; 
	private static final String CP_RELOAD_PAGE = "reloadPage";
	private static final String CP_INITIAL_FLOW_NAME = "initialFlowName";
	private static final String CP_APP_INITIALIZATION_FLOW_NAME = "applicationInitializationFlowName";

	private static final String VALUE = "value";	
	private static final String COUNTRY_LOCALE = "defaultCountryLocale";
	private static final String LANGUAGE_LOCALE = "defaultLanguageLocale";

	private static final String APP_INIT_SESSION_ID ="AppInitSessionId";

	// servlet init parameters
	private static final String IP_SERVLET_CONFIG = "servletConfigurationLocation";
	private static final String IP_CONFIG_LOCATION = "systemConfigurationLocation";
	
	private static final String REQUEST_ATTR_USERID = "userid";
	private static final String LOCK_OBJECT_PROPERTIES_KEY = "ControllerServletLock"; 

	/**
	 * The page to be displayed when error occures
	 */
	private static String errorPage = null;

	/**
	 * The page to be displayed when xml reload requested.
	 */
	private static String reloadPage = null;

	/**
	 * The page to be displayed when session expires.
	 */
	private static String timeoutPage = null;

	/**
	 * The page to be displayed when the user logs out of the system.
	 */
	private static String logoutPage = null;

	/**
	 * The root path of all application jsp files
	 */
	private static String appJspRootPath = null;

	/**
	 * The root path of all system jsp files
	 */
	private static String sysJspRootPath = null;

	/**
	 * The name of flow to be created and executed when new client connects
	 */
	private String initialFlowName = null;

	/**
	 * The name of flow to be operatated and executed when the server is started
	 */
	private String appInitializationFlowName = null;

	/**
	 * The locale of flow to be operatated and executed when the server is started
	 */
	private String appInitializationCountryLocale = null;
	private String appInitializationLanguageLocale = null;

	/**
	 * The name of the system configuration location
	 */
	private String configurationLocation = null;

	/**
	 * The lock object that manage the concurrency synchronization.
	 */
	private static MultipleReadersSingleWriterLock globalLockObject = 
		new MultipleReadersSingleWriterLock();

	/**
	 * initialize the servlet.
	 */
	public void init() throws ServletException
	{
		try
		{
			initializeConfiguration();
		}
		catch (SynchronizationLockException e)
		{
			Logger.fatal(LOGGER_CONTEXT, "Controller Servlet service method failed");
			Logger.fatal(LOGGER_CONTEXT, e);
			throw new ServletException("Controller Servlet service method failed", e);
		}
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// check if reader or writer request
		try
		{
			if (request.getParameter(HTMLConstants.RELOAD) != null) // writer
			{
				initializeConfiguration();
				
				// reload the servers, if any
				try
				{
					BPOInitializationManager.reload(SystemInitializationManager.getInstance().getServersConfigFile());
				}
				catch (Throwable e)
				{
					throw new ServletException("problem in reloading", e);
				}
				goToPage(reloadPage, sysJspRootPath, request, response);
				return;
			}
			else // reader
			{
				processUserRequest(request, response);
			}
		}
		catch (SynchronizationLockException e)
		{
			Logger.fatal(LOGGER_CONTEXT, "Controller Servlet initialization/request failed");
			Logger.fatal(LOGGER_CONTEXT, e);
			throw new ServletException("Controller Servlet initialization/request failed", e);
		}

	}

	/**
	 * Initialize the system configuration. (Writer)
	 * @throws ServletException
	 */
	private void initializeConfiguration() throws ServletException, SynchronizationLockException
	{

		/*
		 * Check if the writer could start.
		 * If yes, lock the object and prevent other readers to start until he is finished.
		 * Otherwise, wait for the readers to finish.  
		 */
		globalLockObject.getWriteLock();

		/*
		 * Start initialization
		 */

		// tracking load time
		long loadStart = System.currentTimeMillis();

		//read configuration xml file
		try
		{			
			// initializing init parameters
			readInitializeParameters();

			// init system
			configurationLocation = getInitParameter(IP_CONFIG_LOCATION);
			SystemInitializationManager initializer = SystemInitializationManager.getInstance();
			initializer.initializeConfiguration(configurationLocation);
	
			// initializin app flow
			runApplicationInitializationFlow();
		}
		catch (Throwable ex)
		{
			Logger.fatal(LOGGER_CONTEXT, "Controller Servlet initialization failed");
			Logger.fatal(LOGGER_CONTEXT, ex);
			throw new ServletException("Controller Servlet initialization failed", ex);
		}
		finally
		{
			//Release the writer locking. other readers could start. 
			globalLockObject.releaseWriteLock();
		}

		try
		{
			// reload the lock parameters from the system resources. 
			globalLockObject.reloadLockParameters(LOCK_OBJECT_PROPERTIES_KEY);
		}
		catch (ResourceException e)
		{
			Logger.fatal(LOGGER_CONTEXT, "Controller Servlet reloadLockParameters failed");
			Logger.fatal(LOGGER_CONTEXT, e);
			throw new ServletException("Controller Servlet reloadLockParameters failed", e);
		}
	

		Logger.debug(LOGGER_CONTEXT, "Total Controller Servlet initialization time [" + (System.currentTimeMillis() - loadStart) + "]ms");
	}

	/**
	 * handle the user request.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processUserRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException, SynchronizationLockException
	{

		/*
		 * Check if the reader could start (there is no writer at work).
		 */
		globalLockObject.getReadLock();
		HttpSession session = null;
		ResultEvent resultEvent = null;
		Flow flow = null;
		DynamicGlobals dynamicGlobals = null;
		Object sessionLockObject = null;

		long startTime = System.currentTimeMillis();		 	
		HtmlServletUtil.setCharacterEncoding(request, response);
		try
		{
			//int sessionStatus = servletService.getSessionStatus(request, Event.EVENT_NAME_FIELD_CMB); 
			int sessionStatus = HtmlServletUtil.getSessionStatus(request, Event.EVENT_NAME_FIELD_CMB);
					
			//Session time out
			if (sessionStatus == HtmlServletUtil.SESSION_STATUS_INVALIDATE)
			{
				goToPage(timeoutPage,sysJspRootPath,request,response);
				return;
			}

			// get the session
			session = request.getSession();
			
			// print request parameters - only on debug mode
			printRequestParameters(request, session.getId());

			//logout request
			if (request.getParameter(HTMLConstants.LOGOUT) != null)
			{
				session.invalidate();				
				goToPage(logoutPage,sysJspRootPath,request,response);
				return;
			}
			
			//logout and login request(on error)
			else if (request.getParameter(HTMLConstants.LOGIN_ONERROR) != null)
			{
				session.invalidate();
				response.sendRedirect("");
				return;
			}
			
			if (sessionStatus == HtmlServletUtil.SESSION_STATUS_EXIST)
			{
				//get the main flow from session
				flow = (Flow) session.getAttribute(HTMLConstants.FLOW_PROCESSOR);
				sessionLockObject = session.getAttribute(HTMLConstants.LOCK_OBJECT);
			}
			//new user
			else
			{
				// set new lock object in the session
				sessionLockObject = new Object();
				session.setAttribute(HTMLConstants.LOCK_OBJECT, sessionLockObject);
			}

			// get the DynamicGlobals of the session.
			dynamicGlobals = getDynamicGloabls(flow, session, request);
			
			Logger.debug(LOGGER_CONTEXT, "Process user request for sessionId: " + session.getId() + ", userId: " + dynamicGlobals.getUserAuthData().getUserID());
			
			// get the levels manager of the session.
			AuthLevelsManager authLevelsManager = dynamicGlobals.getAuthLevelsManager();

			/*
			 * Process the event starting in the main flow
			 */

			// lock the session lock object
			synchronized (sessionLockObject)
			{

				if (sessionStatus == HtmlServletUtil.SESSION_STATUS_NEW)
				{
					//creating new flow according to initialFlowName parameter
					flow = FlowElementsFactory.getInstance().createFlow(initialFlowName);

					//wrap flow with HTTPSessionBoundFlow
					flow = new HTTPSessionBoundFlow(flow);

					//saving the flow instance to session
					session.setAttribute(HTMLConstants.FLOW_PROCESSOR, flow);

					//starting flow
					resultEvent = flow.initiate(null, null, FlowPath.MAIN_FLOW_ID, null, dynamicGlobals, true);
				}
				else
				{
					//parsing HTTP request into Event
					HTTPRequestEvent event = HTTPRequestEventFactory.createHttpRequestEventInstance(request);

					// clear authorization levels data from the previous request
					authLevelsManager.clear();

					//only single point of synchronization because - concurent threads
					//on same session may be only in case of existing flow. (multiple
					//frames or opening new explorer window by pressing Ctrl-N)
					//passing event to flow
					resultEvent = flow.processEvent(event);
				}
			}

			// save the result event instance in the request
			request.setAttribute(HTMLConstants.RESULT_EVENT, resultEvent);

			// save the mainFlow instance in the result event
			resultEvent.setMainFlow(flow);

			if (resultEvent.getExceptionsCount() > 0)
			{
				//if exceptions was thrown - display the error page
				goToPage(errorPage, sysJspRootPath, request, response);
			}
			else
			{
				//if no flow climes its independency - main flow will be used instead
				if (resultEvent.getFlow() == null)
				{
					ApplicationUtil.writeFlowInResultEvent(flow, resultEvent, true);
				}

				// init authLevelsManager with the auth level of the last independent flow
				authLevelsManager.setFirstFlowAuthLevel(resultEvent.getFlow());

				// set helpAdmin & authAdmin in the session
				session.setAttribute(HTMLConstants.HELP_ADMIN, dynamicGlobals.getHelpAdmin());
				session.setAttribute(HTMLConstants.AUTH_ADMIN, dynamicGlobals.getAuthAdmin());
				Logger.debug(LOGGER_CONTEXT, "set session paramteres: helpAdmin =" + dynamicGlobals.getHelpAdmin() + ", authAdmin =" + dynamicGlobals.getAuthAdmin());

				// retrieve the page to display
				PageElementAuthLevel pageElement = resultEvent.getFlow().getPageElement();

				// display the app page
				goToPage(pageElement.getPage(), appJspRootPath, request, response);
			}
		}
		catch (Throwable ex)
		{
			Logger.fatal(LOGGER_CONTEXT, ex);
			ArrayList errors = new ArrayList();
			errors.add(ex);
			request.setAttribute(HTMLConstants.ERROR_LIST, errors);

			if (resultEvent == null)
			{
				resultEvent = new ResultEvent();
				// save the result event instance in the request
				request.setAttribute(HTMLConstants.RESULT_EVENT, resultEvent);
			}

			if (resultEvent.getFlow() == null && flow != null)
			{
				ApplicationUtil.writeFlowInResultEvent(flow, resultEvent, true);
				// save the mainFlow instance in the result event
				resultEvent.setMainFlow(flow);
			}

			// display the error page
			goToPage(errorPage, sysJspRootPath, request, response);
			return;
		}
		finally
		{
			// Release the reader locking.  
			globalLockObject.releaseReadLock();
		}

		Logger.info(LOGGER_CONTEXT, "Total SessionId [" + session.getId() + "] request time [" + (System.currentTimeMillis() - startTime) + "] ms. for user:" + dynamicGlobals.getUserAuthData().getUserID());

	}

	/**
	 * Returns the {@link DynamicGlobals} related to the session.
	 * @param flow
	 * @param session
	 * @return DynamicGlobals
	 */
	private DynamicGlobals getDynamicGloabls(Flow flow, HttpSession session, HttpServletRequest request) throws AuthException, AuthenticationException
	{
		DynamicGlobals dynamicGlobals;

		if (flow == null) //new user
		{
			// get user locale according to the request. By definition
			// if the client has no locale, the default locale of
			// the server will be return
			Locale locale = request.getLocale();
			locale = LanguagesManager.getSupportedLocale(locale);

			UserAuthData userAuthData = AuthenticationManager.getUserAuthData(request);

			//create DynamicGlobals object.
			dynamicGlobals = new DynamicGlobals(session.getId(), locale, userAuthData);
		}
		else
		{
			dynamicGlobals = flow.getCurrentStateContext().getDynamicGlobals();
		}
		return dynamicGlobals;
	}

	/**
	 * Returns the jsp root path.
	 * @return String
	 */
	public static String getJspRootPath()
	{
		return appJspRootPath;
	}

	/**
	 * Dispath to the given page.
	 * @param page
	 * @param jspRootPath
	 * @param request
	 * @param response
	 */
	private void goToPage(String page, String jspRootPath, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		HttpSession session = request.getSession(false);
		Logger.info(LOGGER_CONTEXT, "SessionId [" + (session != null ? session.getId() : "null") + "] go to page [" + page + "]");
		HashMap extraParams = new HashMap();
		extraParams.put(HtmlServletUtil.PARAM_JSP_PAGE,page);
		extraParams.put(HtmlServletUtil.PARAM_JSP_ROOT_PATH,jspRootPath);
		HtmlServletUtil.servletResponse(request,response,extraParams);
	}

	/**
	 * Initializing init parameters from the servlet configuration file 
	 * @throws XMLUtilException
	 */
	private void readInitializeParameters() throws XMLUtilException, IOException
	{
		//String path = FlowerHTMLUtil.getResourceAbsolutePath(this.getClass(), getInitParameter(IP_SERVLET_CONFIG));
		String path = ResourceUtils.getResourceAbsolutePath(getInitParameter(IP_SERVLET_CONFIG));
	
		Document doc = XMLUtil.readXML(path, false);

		//retrieve root element
		Element documentElement = doc.getDocumentElement();

		//retrieve error page
		errorPage = ((Element) XMLUtil.getElementsByTagName(documentElement, CP_ERROR_PAGE).item(0)).getAttribute(VALUE);

		//retrieve reload page
		reloadPage = ((Element) XMLUtil.getElementsByTagName(documentElement, CP_RELOAD_PAGE).item(0)).getAttribute(VALUE);

		//retrieve timeout page
		timeoutPage = ((Element) XMLUtil.getElementsByTagName(documentElement, CP_TIMEOUT_PAGE).item(0)).getAttribute(VALUE);
		
		//retrieve logout page
		logoutPage = ((Element) XMLUtil.getElementsByTagName(documentElement, CP_LOGOUT_PAGE).item(0)).getAttribute(VALUE);
		
		//retrieve application jsp root path
		appJspRootPath = getRootPath(CP_APP_JSP_ROOT_PATH, documentElement);

		//retrieve system jsp root path
		sysJspRootPath = getRootPath(CP_SYS_JSP_ROOT_PATH, documentElement);

		//retrieve app initialization flow name
		NodeList appInitializationList = XMLUtil.getElementsByTagName(documentElement, CP_APP_INITIALIZATION_FLOW_NAME);

		//retrieve app initialization locale
		if (appInitializationList.getLength() > 0)
		{
			Element appInitElement = (Element)appInitializationList.item(0);
			appInitializationFlowName = appInitElement.getAttribute(VALUE);
			appInitializationCountryLocale = appInitElement.getAttribute(COUNTRY_LOCALE);
			appInitializationLanguageLocale = appInitElement.getAttribute(LANGUAGE_LOCALE);
		}

		//retrieve initial flow name
		initialFlowName = ((Element) XMLUtil.getElementsByTagName(documentElement, CP_INITIAL_FLOW_NAME).item(0)).getAttribute(VALUE);
				
	}

	private String getRootPath(String basicRootPath, Element documentElement)
	{
		String rootPath = ((Element) XMLUtil.getElementsByTagName(documentElement, basicRootPath).item(0)).getAttribute(VALUE);
		rootPath = rootPath.startsWith("/") ? rootPath : "/" + rootPath;
		rootPath = rootPath.endsWith("/") ? rootPath : rootPath + "/";
		return rootPath;
	}

	/**
	 * Print to log the request parameters.
	 * @param request
	 * @throws ResourceException
	 */
	private void printRequestParameters(HttpServletRequest request, String sessionId) throws ResourceException
	{
		//print only if in debug mode 
		if (SystemResources.getInstance().isDebugMode())
		{
			//print debug
			StringBuffer sb = new StringBuffer(2048);
			sb.append("SessionId [");
			sb.append(sessionId);
			sb.append("] Parameters arrived with request: ");
			for (Enumeration enum = request.getParameterNames(); enum.hasMoreElements();)
			{
				String name = (String) enum.nextElement();
				String valueArr[] = request.getParameterValues(name);

				sb.append("[[" + name + "] -> [");
				if (valueArr.length == 1)
				{
					sb.append(valueArr[0]);
				}
				else
				{
					for (int i = 0; i < valueArr.length && i < 10; i++)
					{
						sb.append("[" + valueArr[i] + "], ");

					}
				}

				sb.append("]] ");
			}
			Logger.info(LOGGER_CONTEXT, sb.toString());
		}
	}
		
	private void runApplicationInitializationFlow() throws FlowElementsFactoryException, AuthException
	{
		Flow flow = null;

		// if app flow was supllied, run it
		if (appInitializationFlowName != null)
		{
			//creating new flow according to initialFlowName parameter
			flow = FlowElementsFactory.getInstance().createFlow(appInitializationFlowName);

			//create DynamicGlobals object.
			Locale locale = new Locale(appInitializationLanguageLocale,appInitializationCountryLocale);
			UserAuthData userAuthData = UserAuthDataFactory.getEmptyUserAuthData();
			DynamicGlobals dynamicGlobals = new DynamicGlobals(APP_INIT_SESSION_ID, locale, userAuthData);

			//starting flow
			flow.initiate(null, null, FlowPath.MAIN_FLOW_ID, null, dynamicGlobals, true);			
		}
	}
}
