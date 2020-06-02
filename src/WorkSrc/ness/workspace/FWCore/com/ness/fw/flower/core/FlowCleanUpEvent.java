/*
 * Created on: 12/04/2005
 * Author: yifat har-nof
 * @version $Id: FlowCleanUpEvent.java,v 1.1 2005/04/12 13:08:10 yifat Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Used to send cleanup event to the main flow when session is been invalidated. 
 */
public class FlowCleanUpEvent extends Event
{

	/**
	 * Create new <code>FlowCleanUpEvent</code> object.
	 */
	public FlowCleanUpEvent()
	{
		super(FLOWERConstants.CLEANUP_EVENT, null, null);
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
