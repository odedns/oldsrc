/*
 * Created on: 28/06/2004
 * Author Amit Mendelson
 * @version $Id: AgentFactory.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.workflow;

/**
 * This class is used for creation/retrieval of instances of the Agent required
 * for logging to the WorkFlow.
 */
public class AgentFactory
{

	/**
	 * An instance of the Agent.
	 */
	private static Agent instance;
	
	/**
	 * private constructor. 
	 */
	private AgentFactory()
	{
		
	}

	/**
	 * Initializes the AgentFactory.
	 * In case the instance is null, create a new instance.
	 *
	 */
	public static final void initialize(String agentType, String agentName)
		throws AgentException
	{
		if (instance == null)
		{
			instance = new MQWorkFlowAgent(agentType, agentName);
		}
	}
	
	/**
	 * Returns an instance of the AgentFactory.
	 * @return Agent the instance held by this factory.
	 * @throws AgentException
	 */
	public static final Agent getAgent() throws AgentException
	{
		if(instance==null)
		{
			throw new AgentException(AgentConstants.AGENT_NOT_INITIALIZED);
		}
		return instance;
	}

}