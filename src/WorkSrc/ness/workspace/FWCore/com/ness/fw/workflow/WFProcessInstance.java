/*
 * Created on: 07/06/2004
 * Author Amit Mendelson
 * @version $Id: WFProcessInstance.java,v 1.3 2005/03/21 12:33:41 amit Exp $
 */
package com.ness.fw.workflow;

import java.util.Date;
import com.ness.fw.common.resources.SystemResources;

import com.ness.fw.util.Message;

/**
 * This class is used instead of the workflow WFProcessInstance class.
 * The infrastructure class MQWorkFlowService is responsible for replacing
 * the workflow class with this class.
 */
public class WFProcessInstance
{

	//internal data structures	

	/**
	 * List of WorkItems that belong to this WFProcessInstance.
	 */
	private WFWorkItem[] activitiesArray;

	/**
	 * Contains all the applicative/WF information relevant to this 
	 * WFProcessInstance.
	 * Note: the parametersMap contains also the processContext.
	 * The ProcessContext is not decomposed to its parts, this is done
	 * by the application program. The relevant parameter is called
	 * "ProcessContext". Extact name of the parameter can be found 
	 * at WFConstants.PROCESS_CONTEXT
	 */
	private WorkFlowServiceParameterMap parametersMap = null;

	/**
	 *  Name of the process instance.
	 */
	private String processName = null;

	/**
	 *  Name of the process template.
	 */
	private String processTemplateName = null;

	/**
	 *  Name of the parent process instance.
	 */
	private String parentName = null;

	/**
	 * Name of the ancestor process - helps in determining if the process 
	 * is a parent process.
	 */
	private String topLevelName = null;

	/**
	 * ID of the process instance.
	 */
	private String processID = null;

	/**
	 * State of the process instance, e.g. "Ready", "Running", "Finished".
	 */
	private int processState = -1;

	/**
	 * Start time of the process instance.
	 */
	private Date processStartTime = null;

	/**
	 * End time of the process instance.
	 */
	private Date processEndTime = null;

	/**
	 * Category - as in the workflow.
	 */
	private String category = null;

	/**
	 * Last handler of the process instance. If the instance is new, it's last
	 * handler can be null.
	 */
	private String lastHandler = null;

	/**
	 * starter of the process instance (not the person who created it!).
	 */
	private String starter = null;

	/**
	 * Priority of the WFProcessInstance.
	 */
	private int priority = -1;

	/**
	 * Get the WFProcessInstance priority.
	 * @return priority
	 */
	public int getPriority()
	{
		return priority;
	}

	/**
	 * Set priority of the WFProcessInstance
	 * @param priority - as retrieved from the workflow.
	 * @throws WorkFlowLogicException if the WFProcessInstance parameter is null
	 */
	public void setPriority(int priority) throws WorkFlowLogicException
	{

		/*
		 * Retrieve the min/max process priority values from the workflow.properties
		 * file.
		 */
		int minPriorityValue =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.PROCESS_MIN_PRIORITY_VALUE));

		int maxPriorityValue =
			Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.PROCESS_MAX_PRIORITY_VALUE));

		/*
		 * The value -1 is used to represent missing priority value 
		 * in the workflow.
		 * In this case, the application should replace this value
		 * with a legal value.
		 */
		if (((priority < minPriorityValue) && (priority != -1))
			|| (priority > maxPriorityValue))
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.INVALID_PRIORITY_VALUE,
				new Message(
					WFExceptionMessages.INVALID_PRIORITY_VALUE,
					Message.SEVERITY_ERROR));
		}
		this.priority = priority;
	}
	
	/**
	 * Get the WFProcessInstance ID.
	 * @return processID.
	 */
	public String getProcessID()
	{
		return processID;
	}

	/**
	 * Set processID
	 * @param processID - as retrieved from the workflow.
	 * @throws NullParametersException if the processID parameter is null
	 * Note: this method is protected as it is used by MQWorkFlowService.
	 */
	protected void setProcessID(String processID)
		throws NullParametersException
	{
		if (processID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_INSTANCE_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.processID = processID;
	}

	/**
	 * Returns the processName.
	 * @return processName.
	 */
	public String getProcessName()
	{
		return processName;
	}

	/**
	 * Sets the process name.
	 * @param processName the process' name in workflow.
	 * @throws NullParametersException if the passed parameter is null.
	 */
	protected void setProcessName(String processName)
		throws NullParametersException
	{
		if (processName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		this.processName = processName;
	}

	/**
	 * Returns the processTemplateName.
	 * @return processTemplateName.
	 */
	public String getProcessTemplateName()
	{
		return processTemplateName;
	}

	/**
	 * Sets the process template name.
	 * @param processTemplateName the process' template name in workflow.
	 * @throws NullParametersException if the passed parameter is null.
	 */
	protected void setProcessTemplateName(String processTemplateName)
		throws NullParametersException
	{
		if (processTemplateName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_TEMPLATE_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_TEMPLATE_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		this.processTemplateName = processTemplateName;
	}

	/**
	 * gets the parent WFProcessInstance name.
	 * @return parentName name of the parent WFProcessInstance.
	 */
	public String getParentName()
	{
		return parentName;
	}

	/**
	 * sets the parent name.
	 * @param parentName - name of the parent WFProcessInstance (can be null).
	 */
	protected void setParentName(String parentName)
	{

		this.parentName = parentName;
	}

	/**
	 * Gets the name of the WFProcessInstance ancestor.
	 * @return top level name - name of the ancestor of this WFProcessInstance.
	 */
	public String getProcessTopLevelName()
	{
		return topLevelName;
	}

	/**
	 * Sets the process' top level name.
	 * @param topLevelName - name of the WFProcessInstance ancestor.
	 * @throws NullParametersException if the passed parameter is null.
	 */
	public void setProcessTopLevelName(String topLevelName)
		throws NullParametersException
	{

		if (topLevelName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_TOP_LEVEL_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_TOP_LEVEL_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.topLevelName = topLevelName;

	}

	/**
	 * returns true if the WFProcessInstance is a parent WFProcessInstance 
	 * (has no parents). 
	 * @return true if the WFProcessInstance has no parent ProcessInstances, 
	 * false otherwise.
	 */
	public boolean isParentProcess()
	{
		if (processName.equalsIgnoreCase(topLevelName))
		{
			return true;
		}
		return false;
	}
	/**
	 * returns the process state.
	 * @return processState - state of the WFProcessInstance
	 */
	public int getProcessState()
	{
		return processState;
	}

	/**
	 * sets the WFProcessInstance state.
	 * @param processState - state of the WFProcessInstance
	 * @throws NullParametersException if the passed parameter is null.
	 */
	protected void setProcessState(int processState)
		throws NullParametersException
	{
		this.processState = processState;
	}

	/**
	 * returns the date the process has started.
	 * @return processStartTime
	 */
	public Date getProcessStartTime()
	{
		return processStartTime;
	}

	/**
	 * Sets the process' start time.
	 * The process' start time can be null, if the WFProcessInstance has not
	 * started yet.
	 * @param processStartTime time the process has started.
	 */
	protected void setProcessStartTime(Date processStartTime)
	{

		this.processStartTime = processStartTime;
	}

	/**
	 * Returns the WFProcessInstance end time.
	 * @return processEndTime.
	 */
	public Date getProcessEndTime()
	{
		return processEndTime;
	}

	/**
	 * Set the WFProcessInstance end time.
	 * @param endTime (might be null).
	 */
	public void setProcessEndTime(Date processEndTime)
	{

		this.processEndTime = processEndTime;
	}

	/**
	 * returns the activities array of the WFProcessInstance.
	 * @return WFWorkItem[] the activities array - WorkItems that
	 * belong to this WFProcessInstance.
	 */
	public WFWorkItem[] getActivitiesArray()
	{
		return activitiesArray;
	}

	/**
	 * Returns the last handler of the WFProcessInstance.
	 * @return the last handler of the WFProcessInstance.
	 */
	public String getLastHandler()
	{
		return lastHandler;
	}

	/**
	 * Note - the handler is found in the MQWorkFlowService and passed "as is"
	 * to this class.
	 * If the process is new, it can be without any last handler.
	 * @param lastHandler
	 */
	protected void setLastHandler(String lastHandler)
	{
		this.lastHandler = lastHandler;
	}

	/**
	 * Returns the WFProcessInstance category.
	 * @return category - the category.
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * sets the process' category.
	 * @param category - WFProcessInstance category.
	 * @throws NullParametersException if the passed parameter is null.
	 */
	protected void setCategory(String category) throws NullParametersException
	{
		if (category == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_CATEGORY_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_CATEGORY_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.category = category;
	}

	/**
	 * returns the process' starter.
	 * @return starter - name of the WFProcessInstance starter.
	 */
	public String getStarter()
	{
		return starter;
	}

	/**
	 * Sets the process' starter.
	 * The process' starter can be null, if the WFProcessInstance has not
	 * started yet.
	 * @param starter name of the WFProcessInstance starter
	 */
	protected void setStarter(String starter)
	{

		this.starter = starter;
	}

	/**
	 * Return the WFProcessInstance WorkFlowServiceParameterMap.
	 * @return parametersMap the WFProcessInstance WorkFlowServiceParameterMap.
	 */
	public WorkFlowServiceParameterMap getParametersMap()
	{
		return parametersMap;
	}

	/**
	 * Return the ProcessContext of this WFProcessInstance.
	 * The ProcessContext is returned as one String, not decomposed
	 * to parts. If the application programmer is interested in such
	 * decomposition, it should be done at the application level.
	 * @return String the ProcessContext of this ProcessInstace.
	 */
	public String getProcessContext() throws NullParametersException
	{
		WorkFlowServiceParameter parameter =
			parametersMap.get(WFConstants.PROCESS_CONTEXT);
		if (parameter == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PROCESS_CONTEXT_IS_NULL,
				new Message(
					WFExceptionMessages.PROCESS_CONTEXT_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		return parameter.getStringValue();
	}

	/**
	 * Set the WFProcessInstance WorkFlowServiceParameterMap.
	 * @param parametersMap WorkFlowServiceParameterMap
	 * @throws NullParametersException if the passed parameter is null.
	 */
	public void setParametersMap(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		if (parametersMap == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}
		this.parametersMap = parametersMap;
	}

/*
	/**
	 * This method is used to add a map to this WFProcessInstance map.
	 * In case there are already fields with the same key, they will
	 * be overriden.
	 * @param parametersMap
	 * @throws NullParametersException if thrown from the method putAll()
	 * of WorkFlowServiceParameterMap.
	 *//*
	protected void addToParametersMap(WorkFlowServiceParameterMap parametersMap)
		throws NullParametersException
	{

		//no action needed if the new map is null
		if (parametersMap == null)
		{
			return;
		}

		this.parametersMap.putAll(parametersMap);
	}
	*/

	/**
	 * Set the activities Array of the WFProcessInstance.
	 * @param activitiesArray the Activities that belong to this WFProcessInstance
	 * @throws NullParametersException if the passed parameter is null.
	 */
	protected void setActivitiesArray(WFWorkItem[] activitiesArray)
		throws NullParametersException
	{

		if (activitiesArray == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.PASSED_WORKITEMS_ARRAY_IS_NULL,
				new Message(
					WFExceptionMessages.PASSED_WORKITEMS_ARRAY_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.activitiesArray = new WFWorkItem[activitiesArray.length];
		for (int i = 0; i < activitiesArray.length; i++)
		{
			this.activitiesArray[i] = activitiesArray[i];
		}

	}
}
