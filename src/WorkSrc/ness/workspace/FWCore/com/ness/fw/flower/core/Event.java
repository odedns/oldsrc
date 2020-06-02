/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: Event.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * The common ancestor for all events in the framework.
 * <p>
 * Each event contains map collection of data. (In case of <code>HTTPRequestEventImpl</code> -
 * parameters from HTTP request)
 * </p>
 */
public abstract class Event
{
	public static final String EVENT_DATA_STRUCTURE_FIELD    =   "eventData";


	public static final String FLOW_ID_FIELD              =   "flowId";
	public static final String FLOW_STATE_FIELD           =   "flowState";
	public static final String EVENT_NAME_FIELD           =   "eventName";
	public static final String EVENT_EXTRA_PARAMS_FIELD   =   "eventExtraParams";


	public static final String FLOW_ID_FIELD_CMB              =   EVENT_DATA_STRUCTURE_FIELD + "." + FLOW_ID_FIELD;
	public static final String FLOW_STATE_FIELD_CMB           =   EVENT_DATA_STRUCTURE_FIELD + "." + FLOW_STATE_FIELD;
	public static final String EVENT_NAME_FIELD_CMB           =   EVENT_DATA_STRUCTURE_FIELD + "." + EVENT_NAME_FIELD;
	public static final String EVENT_EXTRA_PARAMS_FIELD_CMB   =   EVENT_DATA_STRUCTURE_FIELD + "." + EVENT_EXTRA_PARAMS_FIELD;


	/**
	 * The event name, which used to toggle suitable <code>Transition</code>.
	 */
	private String eventName;

	/**
	 * The path of <code>Flow</code> to which is target for <code>Event</code>.
	 */
	private FlowPath flowPath;

	/**
	 * The state of <code>Flow</code> at which the <code>Event</code> supposed to arrive.
	 */
	private String flowState;

	/**
	 * Create new <code>Event</code> object.
	 * @param eventName The event name, which used to toggle suitable <code>Transition</code>.
	 * @param flowPath The path of <code>Flow</code> to which is target for <code>Event</code>.
	 * @param flowState The state of <code>Flow</code> at which the <code>Event</code> supposed to arrive.
	 */
	public Event(String eventName, FlowPath flowPath, String flowState)
	{
		this.eventName = eventName;
		this.flowPath = flowPath;
		this.flowState = flowState;
	}

	/**
	 * Retuns the event name, which used to toggle suitable <code>Transition</code>.
	 * @return name
	 */
	public String getEventName()
	{
		return eventName;
	}

	/**
	 * Returns the path of <code>Flow</code> to which is target for <code>Event</code>.
	 * @return <code>FlowPath</code>
	 */
	public FlowPath getFlowPath()
	{
		return flowPath;
	}

	/**
	 * Returns the state of <code>Flow</code> at which the <code>Event</code> supposed to arrive.
	 * @return flowState
	 */
	public String getFlowState()
	{
		return flowState;
	}

	/**
	 * Returns the parameter data from the event parameters according to the given key.
	 *
	 * @param key The key of the parameter.
	 * @return String The parameter data.
	 */
	public abstract String getParameter(String key);

	/**
	 * Used to get event parameter values (passes call to http request)
	 */
	public abstract String[] getParameterValues(String key);

	/**
	 * Return <code>Enumeration</code> of all parameters names
	 * @return Enumeration
	 */
	public abstract Enumeration getParameterNames();

	/**
	 * Indicates whether the event contains parameters. 
	 * @return boolean
	 */
	public abstract boolean isContainsParameters();
	

}
