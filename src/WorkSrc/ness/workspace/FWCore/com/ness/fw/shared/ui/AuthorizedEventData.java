/*
 * Created on 01/06/2004
 * Author: yifat har-nof
 * @version $Id: AuthorizedEventData.java,v 1.2 2005/02/23 15:30:54 yifat Exp $
 */
package com.ness.fw.shared.ui;

/**
 * Contains information about the flow path and flow state to send with the event.
 */
public class AuthorizedEventData
{

	/**
	 * The flow path to the send with the event.
	 */
	private String flowPath;
	
	/**
	 * The flow state name the send with the event.
	 */
	private String flowStateName;
	
	/**
	 * Creates new AuthorizedEventData object.
	 * @param flowPath The flow path to the send with the event.
	 * @param flowStateName The flow state name the send with the event.
	 */
	public AuthorizedEventData(String flowPath, String flowStateName)
	{
		this.flowPath = flowPath;
		this.flowStateName = flowStateName;
	}

	/**
	 * Returns the flow path to the send with the event.
	 * @return String
	 */
	public String getFlowPath()
	{
		return flowPath;
	}

	/**
	 * Returns the flow state name the send with the event.
	 * @return String
	 */
	public String getFlowStateName()
	{
		return flowStateName;
	}

}
