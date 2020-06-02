/*
 * Created on: 06/06/2004
 * Author Amit Mendelson
 * @version $Id: WorkFlowServiceFactory.java,v 1.7 2005/05/04 13:53:43 amit Exp $
 */
package com.ness.fw.workflow;

/**
 * This class is used to return the appropriate implementation of WorkFlowService.
 */
public class WorkFlowServiceFactory
{
	
	/**
	 * An instance of WorkflowServiceFactory.
	 */
	private static WorkFlowServiceFactory workFlowFactory;

	/**
	 * private constructor.
	 */
	private WorkFlowServiceFactory()
	{
	}

	/**
	 * Initialize the WorkFlowServiceFactory.
	 */
	public static void initialize() throws WorkFlowException
	{
		if (workFlowFactory == null)
		{
			workFlowFactory = new WorkFlowServiceFactory();
		}
		MQWorkFlowService.initialize();
	}

	/**
	 * Returns an instance of the WorkFlowServiceFactory.
	 * @return WorkFlowServiceFactory
	 */
	public static WorkFlowServiceFactory getInstance()
	{
		return workFlowFactory;
	}

	/**
	 * Returns an instance of WorkFlowService.
	 * @return WorkFlowService an instance of WorkFlowService implementation.
	 */
	public WorkFlowService getWorkFlowService() throws WorkFlowException
	{
		return new MQWorkFlowService();
	}

}