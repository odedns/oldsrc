/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: HTTPRequestEvent.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.flower.servlet;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.flower.core.Event;
import com.ness.fw.flower.core.FlowPath;
import com.ness.fw.flower.core.ParametersMissingException;

/**
 * Implementation of <code>Event</code>. Generated when HTTP request is recieved from client.
 */
public class HTTPRequestEvent extends Event
{
	
	public static final String LOGGER_CONTEXT = "HTTPRequestEvent";
	
	/**
	 * Original HTTP request
	 */
	HttpServletRequest request;

	public HTTPRequestEvent(HttpServletRequest request, String eventName, FlowPath flowPath, String flowState) throws ParametersMissingException
	{
		//caching request
        super(eventName,flowPath, flowState);

		this.request = request;
	}

	/**
	 * Used to get event parameter (passes call to http request)
	 */
	public String getParameter(String key)
	{
		return request.getParameter(key);
	}


	/**
	 * Used to get event parameter values (passes call to http request)
	 */
	public String[] getParameterValues(String key)
	{
		return request.getParameterValues(key);
	}

	/**
	 * Used to list event parameters. Passes call to http request
	 */
	public Enumeration getParameterNames()
	{
		return request.getParameterNames();
	}

	/**
	 * Indicates whether the event contains parameters. 
	 * @return boolean
	 */
	public boolean isContainsParameters()
	{
		return request.getParameterNames().hasMoreElements();
	}

}
