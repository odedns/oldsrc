/*
 * Created on: 12/04/2005
 * Author:  user name
 * @version $Id: HtmlServletUtil.java,v 1.1 2005/04/13 08:34:54 shay Exp $
 */
package com.ness.fw.shared.servlet;


import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ness.fw.flower.common.LanguagesManager;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HtmlServletUtil 
{
	private static final String RESPONSE_CONTEXT_TYPE = "text/html; charset=";
	public static final String PARAM_JSP_PAGE = "page";
	public static final String PARAM_JSP_ROOT_PATH = "jspRootPath";
	public static final int SESSION_STATUS_NEW = 1;
	public static final int SESSION_STATUS_EXIST = 2;
	public static final int SESSION_STATUS_INVALIDATE = 3;

	/**
	 * Sets the character enconding of the response of the servlet
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void setCharacterEncoding(HttpServletRequest request,HttpServletResponse response)
		throws IOException 
	{
		Locale locale = request.getLocale();
		// get the encoding from the Language set of this locale
		String encoding = LanguagesManager.getLanguageSet(locale).getEncoding();

		if (encoding != null)
		{
			response.setContentType(RESPONSE_CONTEXT_TYPE + encoding);
			request.setCharacterEncoding(encoding);
		}		
	}

	/**
	 * Handles the response of the servlet
	 * @param request
	 * @param response
	 * @param extraParams parameters used in order to send the servlets's response
	 * @throws IOException
	 * @throws ServletException
	 */	
	public static void servletResponse(HttpServletRequest request, HttpServletResponse response, HashMap extraParams) throws IOException, ServletException 
	{
		String page = (String)extraParams.get(PARAM_JSP_PAGE);
		if (page == null)
		{
			throw new ServletException("Unable to switch to page null");
		}
		String jspRootPath = (String)extraParams.get(PARAM_JSP_ROOT_PATH);
		request.getRequestDispatcher(jspRootPath + page).include(request, response);
	}

	/**
	 * Returns the status of the session which may be one of the constants:<br>
	 * SESSION_STATUS_NEW indicates that the session was created in the first time for the user<br>
	 * which usually means that the user entered the system for the first time.
	 * SESSION_STATUS_EXIST indicates that this session already exists <br>
	 * which is a normal status. 
	 * SESSION_STATUS_INVALIDATE indicates that this session was created for<br>
	 * the user,but for some reason was invalidated,usuaslly in means session timeout.
	 * @param request 
	 * @return the session's current status
	 */	
	public static int getSessionStatus(HttpServletRequest request,String requestIdentifierParamName) 
	{
		HttpSession session = request.getSession(false);
		//Session does not exist
		if (session == null)
		{
			//Search for the request indentifier
			//If the request indentifier does not exist,the session is new
			//(first enterance to the system)
			if (request.getParameter(requestIdentifierParamName) == null)
			{ 
				return SESSION_STATUS_NEW;
			}
			//If the request indentifier exists,the session was created for
			//this user,but was invalidated
			else
			{
				return SESSION_STATUS_INVALIDATE;
			}
		}		
		//Session exists
		else
		{
			return SESSION_STATUS_EXIST;
		}
	}	
}
