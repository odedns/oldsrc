/*
 * Created on: 06/06/2004
 * Author Amit Mendelson
 * @version $Id: MQWorkFlowAgent.java,v 1.2 2005/03/17 07:20:57 amit Exp $
 */
package com.ness.fw.workflow;

import java.beans.PropertyVetoException;

/**
 * This class represents an implementation of Agent for the MQWorkFlow
 * product. It is used for login to the MQWorkFlow.
 */
public class MQWorkFlowAgent extends Agent
{

	/**
	 * An instance of the WorkFlow Agent.
	 */
	private com.ibm.workflow.api.Agent mqwfAgent;

	/**
	 * Constructor.
	 * @param agentType - should use constant string "LOC" or "JNDI".
	 * @param agentName - the agent name.
	 * @throws AgentException
	 */
	protected MQWorkFlowAgent(String agentType, String agentName)
		throws AgentException
	{

		//Checking validity of parameters is done in the super constructor
		super(agentType, agentName);

		try
		{

			mqwfAgent = new com.ibm.workflow.api.Agent();

			String[] context = new String[2];
			context[0] = agentType;
			context[1] = agentName;

			mqwfAgent.setContext(context, null);
			setLocator(agentType);

			/*
			 * The setName() might throw a PropertyVetoException.
			 */
			mqwfAgent.setName(agentName);

		}
		catch (PropertyVetoException pvex)
		{
			throw new AgentException(pvex);
		}
	}

	/**
	 * This method implementation should call the method setLocator
	 * of the appropriate Agent.
	 * setLocator sets the locator policy for this agent (either local
	 * or remote). 
	 * @throws AgentException if a PropertyVetoException is thrown from
	 * the call to setLocator of the agent.
	 */
	public void setLocator(String agentType) throws AgentException
	{
		try
		{
			if (agentType == null)
			{
				throw new AgentException(AgentConstants.AGENT_TYPE_IS_NULL);
			}
			if (agentType.equalsIgnoreCase(AgentConstants.LOCAL_AGENT))
			{
				mqwfAgent.setLocator(com.ibm.workflow.api.Agent.LOC_LOCATOR);
			}
			else if (agentType.equalsIgnoreCase(AgentConstants.JNDI_AGENT))
			{
				mqwfAgent.setLocator(com.ibm.workflow.api.Agent.JNDI_LOCATOR);
			}
			else
			{
				throw new AgentException(AgentConstants.AGENT_TYPE_IS_INVALID);
			}

		}
		catch (PropertyVetoException pvex)
		{
			throw new AgentException(pvex);
		}
	}

	/**
	 * returns a new instance of WFExecutionService.
	 * @param userId
	 * @param password
	 *	@return WFExecutionService - an instance of WFExecutionService
	 *  required for the user's login to the WorkFlow. 
	 * @throws AgentException if an exception is thrown (like FmcException).
	 */
	public WFExecutionService getExecutionService(String userId,
		String password) throws AgentException
	{

		try
		{
			//In case the instance is null - the agent was not initialized.
			ExecutionServiceImpl executionService =
				new ExecutionServiceImpl();

			com.ibm.workflow.api.ExecutionService service =
				mqwfAgent.locate("", "");

			executionService.setExecutionService(service, userId, password, this);
			return executionService;
		}
		catch (WorkFlowException npex)
		{
			throw new AgentException(npex);
		}
		catch (com.ibm.workflow.api.FmcException fmcex)
		{
			throw new AgentException(fmcex);
		}
	}

	/**
	 * returns a new instance of WFExecutionService.
	 * @param userId
	 * @param userCredentials
	 *	@return WFExecutionService - an instance of WFExecutionService
	 *  required for the user's login to the WorkFlow. 
	 * @throws AgentException if an exception is thrown (like FmcException).
	 */
	public WFExecutionService getExecutionService(String userId,
		UserCredentials userCredentials) throws AgentException
	{

		try
		{
			//In case the instance is null - the agent was not initialized.
			ExecutionServiceImpl executionService =
				new ExecutionServiceImpl();

			com.ibm.workflow.api.ExecutionService service =
				mqwfAgent.locate("", "");

			executionService.setExecutionService(service, userId, userCredentials, this);
			return executionService;
		}
		catch (WorkFlowException npex)
		{
			throw new AgentException(npex);
		}
		catch (com.ibm.workflow.api.FmcException fmcex)
		{
			throw new AgentException(fmcex);
		}
	}

}
