/*
 * Created on: 07/12/2004
 *
 * Author Amit Mendelson
 * @version $Id: WFProcessInstanceList.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.util.Message;

/**
 * Wraps the ProcessInstance[] array returned in retrieveProcesses()
 * and openProcessesForCustomer().
 */
public class WFProcessInstanceList
{

	/**
	 * Holds all the retrieved ProcessInstances.
	 */
	private WFProcessInstance[] processInstances = null;

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
	public WFProcessInstanceList(
		WFProcessInstance[] processInstancesArray)
		throws WorkFlowLogicException, NullParametersException
	{

		if (processInstancesArray != null)
		{
			processInstances = 
				new WFProcessInstance[processInstancesArray.length];
			for (int i = 0; i < processInstancesArray.length; i++)
			{
				if (processInstancesArray[i] == null)
				{
					throw new WorkFlowLogicException(
						WFExceptionMessages.NULL_FIELD_IN_WORKITEMS_ARRAY + i,
						new Message(
							WFExceptionMessages.NULL_FIELD_IN_WORKITEMS_ARRAY + i,
							Message.SEVERITY_ERROR));
				}
				processInstances[i] = processInstancesArray[i];
			}
		}
	}

	/**
	 * Returns the WorkItems array (like the corresponding method in IBM's wf).
	 * @return WFWorkItem[] - the WorkItems array.
	 */
	public WFProcessInstance[] getProcessInstancesList()
	{
		return processInstances;
	}
}
