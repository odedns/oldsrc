/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: SubFlowData.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.util.*;

/**
 * Represents data collection of all data needed to create subflow.
 * Usually used by transitions and subflow states
 */
public class SubFlowData
{
	/**
	 * Name of flow to execute
	 */
	private String flowName;

	/**
	 * Input formatter
	 */
	private Formatter inFormatter;

	/**
	 * Output formatter
	 */
	private Formatter outFormatter;

	/**
	 * List of final state name, engaging of each will instruct flow to
	 * format its context to parent flow context
	 */
	private StringList goodFinalStates;

	/**
	 * Indicates whether to format parameters from the flow context to the parent 
	 * flow context, when the sub flow is being killed.
	 */
	private boolean formatContextOnInterrupt;

	public SubFlowData(String flowName, Formatter inFormatter, Formatter outFormatter, StringList goodFinalStates, boolean formatContextOnInterrupt)
	{
		this.flowName = flowName;
		this.inFormatter = inFormatter;
		this.outFormatter = outFormatter;
		this.goodFinalStates = goodFinalStates;
		this.formatContextOnInterrupt = formatContextOnInterrupt;
	}

	public boolean isFormatContextOnInterrupt()
	{
		return formatContextOnInterrupt;
	}

	public String getFlowName()
	{
		return flowName;
	}

	public Formatter getInFormatter()
	{
		return inFormatter;
	}

	public Formatter getOutFormatter()
	{
		return outFormatter;
	}

	public StringList getGoodFinalStates()
	{
		return goodFinalStates;
	}
}
