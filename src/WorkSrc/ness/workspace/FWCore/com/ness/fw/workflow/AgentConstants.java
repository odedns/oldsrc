/*
 * Created on: 08/08/2004
 * Author Amit Mendelson
 * @version $Id: AgentConstants.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.workflow;

/**
 * This class holds all the constants and error messages for the Agent classes.
 * 
 * All the exceptions in this class are considered serious (the workflow can't
 * function), therefore there are no user messages, but programmer errors.
 */
public class AgentConstants
{

	/**
	 * AGENT_NAME_KEY
	 * This constant represents the value, in the properties file,
	 * of the default Agent name (currently not used).
	 */
	public static final String AGENT_NAME_KEY = "WorkFlow.FMCAGENT";

	/**
	 * LOCAL_AGENT_KEY
	 * This constant represents the value, in the properties file,
	 * of the local agent type (currently not used).
	 */
	public static final String LOCAL_AGENT_KEY = "WorkFlow.LOC";

	/**
	 * JNDI_AGENT_KEY
	 * This constant represents the value, in the properties file,
	 * of the JNDI agent type (currently not used).
	 */
	public static final String JNDI_AGENT_KEY = "WorkFlow.JNDI";

	/**
	 * WORKFLOW_AGENT_NAME
	 */
	public static final String WORKFLOW_AGENT_NAME = "WorkFlowAgentName";

	/**
	 * WORKFLOW_CONNECTION_TYPE
	 */
	public static final String WORKFLOW_CONNECTION_TYPE =
		"WorkFlowAgentConnectionType";

	/**
	 * AGENT
	 */
	public static final String AGENT = "agent"; //agentIN

	/**
	 * LOCAL_AGENT
	 */
	public static final String LOCAL_AGENT = "LOC";
	/**
	 * JNDI_AGENT
	 */
	public static final String JNDI_AGENT = "JNDI";

	/**
	 * AGENT_TYPE_IS_NULL
	 */
	public static final String AGENT_TYPE_IS_NULL = "agentType is null";
	/**
	 * AGENT_NAME_IS_NULL
	 */
	public static final String AGENT_NAME_IS_NULL = "agentName is null";

	/**
	 * AGENT_TYPE_IS_INVALID
	 */
	public static final String AGENT_TYPE_IS_INVALID = "agentType is invalid";
	/**
	 * AGENT_NAME_IS_INVALID
	 */
	public static final String AGENT_NAME_IS_INVALID = "agentName is invalid";

	/**
	 * AGENT_NOT_INITIALIZED
	 * This error occurs when the Agent is not initialized.
	 */
	public static final String AGENT_NOT_INITIALIZED = "Agent not initialized";

	/**
	 * WORKFLOW_AGENT_NAME
	 * Name of the Agent parameter in the context for the method
	 * load() of WorkFlowFlowerAgentService.
	 */
	public static final String WORK_FLOW_AGENT = "WorkFlowAgent";
	
	/**
	 * CONTEXT_IS_NULL
	 */
	public static final String CONTEXT_IS_NULL = "Context is null";

	/**
	 * PARAMETER_LIST_IS_NULL
	 */
	public static final String PARAMETER_LIST_IS_NULL = "ParameterList is null";

	/**
	 * MISSING_AGENT_CONNECTION_TYPE
	 */
	public static final String MISSING_AGENT_CONNECTION_TYPE = 
		"Missing WorkFlowAgentConnectionType parameter";
	
	/**
	 * MISSING_AGENT_NAME
	 */	
	public static final String MISSING_AGENT_NAME = "Missing WorkFlowAgentName parameter";
	
	/**
	 * AGENT_CONNECTION_TYPE_IS_NULL
	 */
	public static final String AGENT_CONNECTION_TYPE_IS_NULL = 
		"WorkFlowAgentConnectionType parameter is null";
	
	/**
	 * WF_AGENT_NAME_IS_NULL
	 */
	public static final String WF_AGENT_NAME_IS_NULL = 
		"WorkFlowAgentName parameter is null";

}
