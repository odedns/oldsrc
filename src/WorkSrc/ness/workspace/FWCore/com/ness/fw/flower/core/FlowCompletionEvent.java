/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowCompletionEvent.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Used to inform parent flow that subflow is finished it's run
 */
public class FlowCompletionEvent extends Event
{
	/**
	 * Create new <code>FlowCompletionEvent</code> object.
	 * @param eventName The event name, which used to toggle suitable <code>Transition</code>.
	 */
	public FlowCompletionEvent(String eventName)
	{
		super(eventName, null, null);
	}

	/**
	 * Returns the parameter data from the event parameters according to the given key.
	 *
	 * @param key The key of the parameter.
	 * @return String The parameter data.
	 */
	public String getParameter(String key)
	{
		return null;
	}

	/**
	 * Used to get event parameter values (passes call to http request)
	 */
	public String[] getParameterValues(String key)
	{
		return null;
	}

	/**
	 * Return <code>Enumeration</code> of all parameters names
	 * @return Enumeration
	 */
	public Enumeration getParameterNames()
	{
		return null;
	}

	/**
	 * Indicates whether the event contains parameters. 
	 * @return boolean
	 */
	public boolean isContainsParameters()
	{
		return false;
	}
	
}
