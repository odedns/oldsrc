/*
 * Created on: 07/06/2004
 * Author Amit Mendelson
 * @version $Id: WFWorkItemList.java,v 1.2 2005/03/09 10:49:52 amit Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.util.Message;

/**
 * This class is used to wrap all the retrieved WorkItems, and allow two ways
 * of accessing them - either serially or by their IDs.
 */
public class WFWorkItemList
{

	/**
	 * Holds all the retrieved WorkItems.
	 */
	private WFWorkItem[] workItems = null;

	/**
	 * Constructor.
	 * This constructor receives the results of queryWorkItems() in MQWF,
	 * and builds the infrastructure WFWorkItemList.
	 * @param workItemsArray
	 * @param filter - the filter used to query the WorkItems.
	 * @param name - name of the WFWorkItemList
	 * @param threshold - maximum number of WorkItems in the WFWorkItemList
	 * @throws WorkFlowLogicException
	 * @throws NullParametersException if any supplied parameter is null.
	 */
	public WFWorkItemList(
		WFWorkItem[] workItemsArray)
		throws WorkFlowLogicException, NullParametersException
	{

		if (workItemsArray == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.AT_LEAST_ONE_PARAMETER_NULL,
				new Message(
					WFExceptionMessages.AT_LEAST_ONE_PARAMETER_NULL,
					Message.SEVERITY_ERROR));
		}
		workItems = new WFWorkItem[workItemsArray.length];
		for (int i = 0; i < workItemsArray.length; i++)
		{
			if (workItemsArray[i] == null)
			{

				throw new WorkFlowLogicException(
					WFExceptionMessages.NULL_FIELD_IN_WORKITEMS_ARRAY + i,
					new Message(
						WFExceptionMessages.NULL_FIELD_IN_WORKITEMS_ARRAY + i,
						Message.SEVERITY_ERROR));
			}
			workItems[i] = workItemsArray[i];
		}
	}

	/**
	 * Returns the WorkItems array (like the corresponding method in IBM's wf).
	 * @return WFWorkItem[] - the WorkItems array.
	 */
	public WFWorkItem[] getWorkItems()
	{
		return workItems;
	}

}
