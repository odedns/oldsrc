/*
 * Created on: 06/06/2004
 * Author Amit Mendelson
 * @version $Id: Agent.java,v 1.2 2005/03/17 07:20:57 amit Exp $
 */
package com.ness.fw.workflow;

/**
 * This class represents an abstraction of Agent required for the connection
 * between our system and the WorkFlow Service.
 */
public abstract class Agent
{

	/**
	 *  Type of the Agent - Either local agent or remote (JNDI) agent.
	 */
	private String agentType;

	/**
	 *  Name of the Agent.
	 */
	private String agentName;

	/**
	 * Constructor with 2 parameters.
	 * @param AgentType - should use constant string "LOC" or "JNDI".
	 * @param name - the agent name.
	 * @param context - String array passed to the Agent's context. 
	 * (currently not used here, maybe in the future?)
	 * @throws AgentException
	 */
	protected Agent(String agentType, String agentName) throws AgentException
	{

		if (agentType == null)
		{
			throw new AgentException(AgentConstants.AGENT_TYPE_IS_NULL);
		}
		else if (agentName == null)
		{
			throw new AgentException(AgentConstants.AGENT_NAME_IS_NULL);
		}
		this.agentType = agentType;
		this.agentName = agentName;
	}

	/**
	 * returns a new instance of WFExecutionService.
	 * @param userName Name of the logging user.
	 * @param password
	 * @return WFExecutionService - an instance of WFExecutionService
	 *  required for the user's login to the WorkFlow. 
	 * @throws AgentException if an exception is thrown (like FmcException).
	 */
	public abstract WFExecutionService getExecutionService(String userName,
		String password)
		throws AgentException;

	/**
	 * returns a new instance of WFExecutionService.
	 * @param userName Name of the logging user.
	 * @param userCredentials
	 * @return WFExecutionService - an instance of WFExecutionService
	 *  required for the user's login to the WorkFlow. 
	 * @throws AgentException if an exception is thrown (like FmcException).
	 */
	public abstract WFExecutionService getExecutionService(String userName,
		UserCredentials userCredentials) throws AgentException;

	/**
	 * This method implementation should call the method setLocator
	 * of the appropriate Agent.
	 * setLocator sets the locator policy for this agent (either local
	 * or remote).
	 * @param agentType - either local agent or remote agent.
	 * @throws AgentException if an exception is thrown (like FmcException).
	 */
	public abstract void setLocator(String agentType) throws AgentException;

}
