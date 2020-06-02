/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowStatesList.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Implementation of list of flows based on ArrayList
 */
public class FlowStatesList
{
	private ArrayList list;

	public FlowStatesList()
	{
		list = new ArrayList();
	}

	public FlowState getFlowState(int index)
	{
		return (FlowState) list.get(index);
	}

	public void addFlowState(FlowState flowState)
	{
		list.add(flowState);
	}

	public int getFlowStatesCount()
	{
		return list.size();
	}
	
}
