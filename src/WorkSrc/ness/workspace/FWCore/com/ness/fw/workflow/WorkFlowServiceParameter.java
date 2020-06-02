/*
 * Created on: 17/06/2004
 * Author Amit Mendelson
 * @version $Id: WorkFlowServiceParameter.java,v 1.3 2005/03/27 08:33:08 amit Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.common.logger.Logger;
import com.ness.fw.util.Message;
import java.io.Serializable;

/**
 * This class is used to pass data from/to the WorkFlow services.
 */
public class WorkFlowServiceParameter implements Serializable
{
	
	public static final String LOGGER_CONTEXT = "WorkFlowServiceParameter";
	
	/**
	 * argumentType - either input, output, input/output.
	 */
	private int argumentType;

	/**
	 * Name of the parameter.
	 */
	private String name;

	/**
	 * The parameter's value (can be String, Long, Double, etc.).
	 */
	private Object value;

	/**
	 * The corresponding workflow's containerType - can be input container,
	 * output container, global container or process context.
	 */
	private int containerType;

	/**
	 * ObjectType - this variable helps to quickly determine the held object type
	 * (type of the object kept in the variable "value").
	 * The supported objectTypes are - String, long, double, boolean, 
	 * parameterMap, calendar, binary
	 */
	private int objectType;

	/**
	 * Constructor without a value for the Object.
	 * In this case, the object is set to null (can be set later to the
	 * requested value).
	 * @param name
	 * @param containerType
	 * @param objectType
	 * @param argumentType
	 * @throws NullParametersException
	 */
	public WorkFlowServiceParameter(
		String name,
		int containerType,
		int objectType,
		int argumentType)
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
		this.name = name;
		this.containerType = containerType;
		this.objectType = objectType;
		this.argumentType = argumentType;
		this.value = null; //default parameter
	}
	
	/**
	 * Assistance method for printing the class representation
	 * as a String.
	 * @return String
	 */
	public String toString()
	{
		return "["+this.name+"]:["+this.value+"]";
	}

	/**
	 * Container with the Object value.
	 * @param name
	 * @param containerType
	 * @param objectType
	 * @param argumentType
	 * @param value
	* @throws NullParametersException
	*/
	public WorkFlowServiceParameter(
		String name,
		int containerType,
		int objectType,
		int argumentType,
		Object value)
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
		this.name = name;
		this.containerType = containerType;
		this.objectType = objectType;
		this.argumentType = argumentType;
		this.value = value;
	}

	/**
	 * Returns the containerType.
	 * @return containerType
	 */
	public int getContainerType()
	{
		return containerType;
	}

	/**
	 * Returns the argumentType.
	 * @return argumentType
	 */
	public int getArgumentType()
	{
		return argumentType;
	}

	/**
	 * Returns the WorkFlowServiceParameter name.
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the object held by this WorkFlowServiceParameter.
	 * @return value
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * returns value as String.
	 * @return String - value.toString()
	 */
	public String getStringValue()
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"called method getStringValue of WorkFlowServiceParameter");

		if (value == null)
		{
			return null;
		}
		return value.toString();
	}

	/**
	 * returns value as Boolean.
	 * @return Boolean - represents the value as boolean.
	 * @throws NullParametersException
	 */
	public Boolean getBooleanValue() throws NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"called method getBooleanValue of WorkFlowServiceParameter");

		if (value == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.VALUE_NOT_SET,
				new Message(
					WFExceptionMessages.VALUE_NOT_SET,
					Message.SEVERITY_ERROR));
		}
		return (Boolean) value;
	}

	/**
	 * returns value as int.
	 * Note: this conforms with the parameters of "setLong()" of 
	 * ReadWriteContainer.
	 * This method might throw NumberFormatException if the value can't
	 * be parsed as a numeric value.
	 * Note: Can't do simple casting from Object to int.
	 * @return int - the value as int.
	 * @throws NullParametersException
	 */
	public int getLongValue() throws NullParametersException
	{
		Logger.debug(
			LOGGER_CONTEXT,
			"called method getLongValue of WorkFlowServiceParameter");

		if (value == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.VALUE_NOT_SET,
				new Message(
					WFExceptionMessages.VALUE_NOT_SET,
					Message.SEVERITY_ERROR));
		}
		Integer integer = Integer.valueOf(value.toString());
		return integer.intValue();
	}

	/**
	 * returns value as double.
	
	 * This method might throw NumberFormatException if the value can't
	 * be parsed as a numeric value.
	 * Note: Can't do simple casting from Object to double.
	 * @return double - the value as double.
	 * @throws NullParametersException
	 */
	public double getDoubleValue() throws NullParametersException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"called method getDoubleValue of WorkFlowServiceParameter");

		if (value == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.VALUE_NOT_SET,
				new Message(
					WFExceptionMessages.VALUE_NOT_SET,
					Message.SEVERITY_ERROR));
		}
		Double d = Double.valueOf(value.toString());
		return d.doubleValue();
	}

	/**
	 * This method returns the objectType.
	 * @return objectType.
	 */
	public int getObjectType()
	{
		return objectType;
	}

	/**
	 * sets the objectType.
	 * @param objectType - the new Type.
	 */
	public void setObjectType(int objectType)
	{
		this.objectType = objectType;
	}

	/**
	 * sets containerType to type.
	 * @param containerType
	 */
	public void setContainerType(int containerType)
	{
		this.containerType = containerType;
	}

	/**
	 * sets the parameter's name.
	 * @param name
	 * @throws NullParametersException
	 */
	public void setName(String name) throws NullParametersException
	{
		if (name == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.NAME_PARAMETER_NULL,
				new Message(
					WFExceptionMessages.NAME_PARAMETER_NULL,
					Message.SEVERITY_ERROR));
		}
		this.name = name;
	}

	/**
	 * Sets this WorkFlowServiceParameter object' value.
	 * There is no test for null value - the parameter value can be null
	 * (but not its name or type).
	 * @param value - the new value.
	 */
	public void setValue(Object value)
	{

		this.value = value;
	}

}
