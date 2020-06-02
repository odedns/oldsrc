/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: SubFlowDataList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Implementation of list of sub flow data instances based on ArrayList
 */
public class SubFlowDataList
{
	private ArrayList list;

	public SubFlowDataList()
	{
		list = new ArrayList();
	}

	public SubFlowData getSubFlowData(int index)
	{
		return (SubFlowData) list.get(index);
	}

	public void addSubFlowData(SubFlowData subFlowData)
	{
		list.add(subFlowData);
	}

	public int getSubFlowDataCount()
	{
		return list.size();
	}
}
