/*
 * Created on: 17/06/2004
 * Author Amit Mendelson
 * @version $Id: WorkFlowServiceParameterMap.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.util.Message;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * A Map of WorkFlowServiceParameter objects.
 * This class is used for managing data structures of the workflow - 
 * pass data to the workflow or from it, replace the Containers of workflow, etc.
 */
public class WorkFlowServiceParameterMap implements Serializable
{
	/**
	 * The Map of WorkFlowServiceParameter objects.
	 * Implemmentation is with a HashMap.
	 */
	private HashMap parameterMap;

	/**
	 * Constructor.
	 * In construction, creates a new WorkFlowServiceParameterMap object.
	 */
	public WorkFlowServiceParameterMap()
	{
		parameterMap = new HashMap();
	}

	/**
	 * Is used in MQWorkFlowService method setContainerBufferFromMap
	 * (Which takes the map and saves its information in the Container).
	 * Can also be used by the application, if required.
	 * @return parameterMap - the Map.
	 */
	public Map getAllParameters()
	{
		return parameterMap;
	}
	/**
	 * Add a WorkFlowServiceParameter object to the parameter objects Map.
	 * @param parameter The parameter object.
	 * There is no meaning for addition of a null parameter - in this case the
	 * parameter won't be added.
	 */
	public void add(WorkFlowServiceParameter parameter)
	{
		if (parameter != null)
		{
			parameterMap.put(parameter.getName(), parameter);
		}
	}

	/**
	 * Create and add a new Input WorkFlowServiceParameter.
	 * @param name
	 * @param value - the object wrapped by the input parameter.
	 * @param objectType
	 * @throws NullParametersException
	 * The name is mandatory, it can't be null!
	 * The value can be null.
	 */
	public void addInputParameter(String name, Object value, int objectType)
		throws NullParametersException
	{
		if (name == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.NAME_PARAMETER_NULL,
				new Message(
					WFExceptionMessages.NAME_PARAMETER_NULL,
					Message.SEVERITY_ERROR));
		}
		add(
			new WorkFlowServiceParameter(
				name,
				WFConstants.CONTAINER_TYPE_ACTIVITY_INPUT_CONTAINER,
				objectType,
				WFConstants.ARGUMENT_TYPE_INPUT,
				value));
	}

	/**
	 * Create and add a new Output WorkFlowServiceParameter.
	 * @param name
	 * @param objectType
	 * The name is mandatory, it can't be null!
	 * @throws NullParametersException
	 */
	public void addOutputParameter(String name, int objectType)
		throws NullParametersException
	{
		if (name == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.NAME_PARAMETER_NULL,
				new Message(
					WFExceptionMessages.NAME_PARAMETER_NULL,
					Message.SEVERITY_ERROR));
		}
		add(
			new WorkFlowServiceParameter(
				name,
				WFConstants.CONTAINER_TYPE_ACTIVITY_OUTPUT_CONTAINER,
				WFConstants.ARGUMENT_TYPE_OUTPUT,
				objectType));
	}

	/**
	 * Returns WorkFlowServiceParameter object according to the given index.
	 * @param name The name of the parameter object to return.
	 * The name is mandatory, it can't be null!
	 * @return WorkFlowServiceParameter
	 */
	public WorkFlowServiceParameter get(String name)
		throws NullParametersException
	{
		if (name == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.NAME_PARAMETER_NULL,
				new Message(
					WFExceptionMessages.NAME_PARAMETER_NULL,
					Message.SEVERITY_ERROR));
		}
		return (WorkFlowServiceParameter) parameterMap.get(name);
	}

	/**
	 * This method allows the user to add another parametersMap contents to 
	 * this parametersMap. In case there are keys in the passed parametersMap 
	 * that are identical to existing keys in the current parametersMap,
	 * these keys' values will be overriden.
	 * @param map - the WorkFlowServiceParameterMap whose contents are added.
	 * @throws NullParametersException
	 */
	public void putAll(WorkFlowServiceParameterMap map)
		throws NullParametersException
	{

		if (map == null)
		{
			//No value will be added in this case.
			return;
		}
		this.parameterMap.putAll(map.getAllParameters());
	}

	/**
	 * returns the parameter objects count.
	 * @return int - size of the internal HashMap.
	 */
	public int size()
	{
		return parameterMap.size();
	}

	/**
	 * Remove a WorkFlowServiceParameter object from the parameter objects Map.
	 * @param key Name of the parameter.
	 * There is no meaning for removal of a null parameter - in this case the
	 * parameter won't be removed.
	 */
	public void remove(String key)
	{
		if (key != null)
		{
			parameterMap.remove(key);
		}
	}
}
