/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowPath.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Used to parse and keep flow path in more comfortable way that plain string
 */
public abstract class FlowPath
{
	public static final String FLOW_ID_LEVEL_DELIMITER =   "/";
	
	public static final String MAIN_FLOW_ID =  "0";

	public static FlowPath newInstance ()
	{
		return new FlowPathImpl();
	}

	/**
	 * Used to parse flow ID string. Parsed data is keept in the instance of <code>FlowPath</code>
	 *
	 * @param flowIdString string to be parsed
	 * @throws MalformedFlowIdException thrown if the flow path string can not be parsed
	 */
	public abstract void parseFlowIdString(String flowIdString) throws MalformedFlowIdException;

	/**
	 * Used to retrieve next element of flow path. Each call to the method returns advance the internal pointer to next element.
	 *
	 * @return String representation of flow path element (flow id).  When last element is passed
	 * returns <code>null</code> (that indicated that appropriate flow instannce is reached)
	 */
	public abstract String getNextLevel();

	/**
	 * Moves internal pointer back to the begining
	 */
	public abstract void resetToUpperLevel();
}
