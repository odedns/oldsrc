/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowPathImpl.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Implementation of flow path
 */
public class FlowPathImpl extends FlowPath
{
	/**
	 * List of flow path elements
	 */
	ArrayList flowIdList;

	/**
	 * Current element index (pointer)
	 */
	int index = 0;

	FlowPathImpl(){}

	/**
	 * Used to parse flow ID string. Parsed data is keept in the instance of <code>FlowPath</code>
	 * @param flowIdString string to be parsed
	 */
	public void parseFlowIdString(String flowIdString) throws MalformedFlowIdException
	{
		flowIdList = new ArrayList(5);
		flowIdString = flowIdString.trim();

		for (StringTokenizer stringTokenizer = new StringTokenizer(flowIdString, FLOW_ID_LEVEL_DELIMITER); stringTokenizer.hasMoreTokens();)
		{
			flowIdList.add(stringTokenizer.nextToken());
		}
	}

	/**
	 * Used to retrieve next element of flow path. Each call to the method returns advance the internal pointer to next element.
	 *
	 * @return String representation of flow path element (flow id).  When last element is passed
	 * returns <code>null</code> (that indicated that appropriate flow instannce is reached)
	 */
	public String getNextLevel()
	{
		if (index < flowIdList.size())
		{
			return (String) flowIdList.get(index++);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Moves internal pointer back to the begining
	 */
	public void resetToUpperLevel()
	{
		index = 0;
	}
}
