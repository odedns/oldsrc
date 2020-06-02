/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ActivityCompletionEvent.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * This event returned by <code>execute</code> method of {@link ActivityImpl} to navigate the rest of 
 * the flow after the execution of the activity process.
 */
public class ActivityCompletionEvent extends Event
{
	
	
	/**
	 * Create new <code>ActivityCompletionEvent</code> object.
	 * @param clickEventName The event name, which used to toggle suitable {@link Transition}.
	 */
	public ActivityCompletionEvent(String eventName)
	{
		super(eventName, null, null);
	}

	/**
	 * Returns the parameter data from the event parameters according to the given key.
	 * not implemented.
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
	 * Return {@link Enumeration} of all parameters names
	 * not implemented.
	 * 
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
