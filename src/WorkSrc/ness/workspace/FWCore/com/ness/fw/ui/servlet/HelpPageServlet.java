package com.ness.fw.ui.servlet;

import java.io.*;
import java.util.HashMap;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.flower.servlet.HTMLConstants;
import com.ness.fw.shared.servlet.HtmlServletUtil;
import com.ness.fw.ui.help.HelpConstants;
import com.ness.fw.ui.help.HelpModel;

/**
 * @version 	1.0
 * @author
 */
public class HelpPageServlet extends HttpServlet 
{
	private final static String PARAM_NAME_HELP_ID = "helpId";
	private final static String PARAM_NAME_HELP_EVENT = "helpEvent";
	private final static String PARAM_NAME_HELP_TEXT = "helpText";
	private final static String PARAM_NAME_HELP_CONTENT = "helpContent";
	private final static String PARAM_NAME_HELP_MODE = "helpMode";
	private final static String PARAM_NAME_IS_USER_ADMIN = "isUserAdmin";

	private final static String PARAM_NAME_VALUE_USER_ADMIN = "1";
	private final static String PARAM_VALUE_EDIT_MODE = "edit";
	private final static String PARAM_VALUE_VIEW_MODE = "view";
	
	private final static String PROPERTY_KEY_HELP_PAGE = "ui.help.jspPage";
	
	private final static String EVENT_SHOW_HELP_PAGE = "show";
	private final static String EVENT_SAVE_HELP_PAGE = "save";

	private static final String PARAM_JSP_PAGE = "page";
	private static final String PARAM_JSP_ROOT_PATH = "jspRootPath";
	
	private static String helpPage = null;
		
	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			processRequest(req,resp);
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
			processRequest(req,resp);
	}

	private void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			Logger.debug("HelpServlet start process request","sessionId " + request.getSession().getId());
			
			//Setting the character encoding
			HtmlServletUtil.setCharacterEncoding(request,response);
		
			//Building the help model 
			HelpModel helpModel = buildHelpModel(request);
			
			//Setting the  help model in the request
			request.setAttribute(HelpConstants.REQUEST_ATTRIBUTE_NAME_HELP_MODEL,helpModel);		
			
			//Show the servlet result
			HtmlServletUtil.servletResponse(request,response,getServeltResultParams());
			
			//getServletContext().getRequestDispatcher(getProperty(PROPERTY_KEY_HELP_PAGE)).include(request,response);	
			Logger.debug("HelpServlet end process request","sessionId " + request.getSession().getId());
		}
		catch (UIException ui)
		{
			throw new ServletException(ui);
		}
		catch (AuthorizationException ae)
		{
			throw new ServletException(ae);
		}
	}
	
	private HashMap getServeltResultParams()
	{
		HashMap params = new HashMap();
		params.put(PARAM_JSP_PAGE,getProperty(PROPERTY_KEY_HELP_PAGE));
		params.put(PARAM_JSP_ROOT_PATH,"");
		return params;
	}
	
	/**
	 * Building the HelpModel
	 * @param request
	 * @return
	 * @throws UIException
	 * @throws AuthorizationException
	 */
	private HelpModel buildHelpModel(HttpServletRequest request) throws UIException, AuthorizationException
	{
		//Constructing help model
		HelpModel helpModel = new HelpModel();
		
		//Setting the locale in the help model
		helpModel.setLocale(request.getLocale());
		
		//Setting the edit mode in the help model
		helpModel.setEditMode(isUserAdmin(request.getSession()));
		
		//Setting the title of the currently displayed help item
		helpModel.setDisplayedHelpItemTitle(request.getParameter(HelpConstants.REQUEST_PARAM_NAME_HELP_TITLE));
		
		//Setting the content of the currently displayed help item
		helpModel.setDisplayedHelpItemContent(request.getParameter(HelpConstants.REQUEST_PARAM_NAME_HELP_CONTENT));
		
		//Parsing the request data in the model
		helpModel.parseEventData(request.getParameter(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO));
				
		return helpModel;
	}
	
	private boolean isUserAdmin(HttpSession session)
	{
		Boolean helpAdmin = (Boolean)session.getAttribute(HTMLConstants.HELP_ADMIN); 
		return helpAdmin != null && helpAdmin.booleanValue();
	}
	
	private String getProperty(String key)
	{
		try 
		{
			return SystemResources.getInstance().getString(key);
		} 
		catch (ResourceException re) 
		{
			return key;
		}
	}
}
