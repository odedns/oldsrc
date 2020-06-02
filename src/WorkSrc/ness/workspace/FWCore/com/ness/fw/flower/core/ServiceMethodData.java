/*
 * Created on 18/11/2004
 * Created By yharnof
 * @version $Id: ServiceMethodData.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * @author yharnof
 *
 * This class contains information about a method of service. 
 */
public class ServiceMethodData
{
	/**
	 * The name of service method as defined in XML.
	 */
	private String name;

	/**
	 * The name of the method to execute in the service class.
	 */
	private String executionMethod;

	/**
	 * The name for {@link Context} to be created for the <code>Service</code> method 
	 * while runtime.
	 */
	private String contextName;

	/**
	 * Validator (optionally) to validate Context data before the method execution.
	 */
	private Validator validator;

	/**
	 * The activity type performed in the <code>Service</code> method. 
	 * Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	private int activityType;

	/**
	 * Creates new ServiceMethodData object.
	 * @param name The name of service method as defined in XML
	 * @param executionMethod The name of the method to execute in the service class
	 * @param contextName The name for {@link Context} to be created for the <code>Service</code> method. 
	 * @param validator Validator (optionally) to validate Context data before the method execution.
	 * @param activityType The activity type performed in the <code>Service</code> method.
	 */
	public ServiceMethodData(String name, String executionMethod, String contextName, Validator validator, int activityType)
	{
		this.name = name;
		this.executionMethod = executionMethod;
		this.contextName = contextName;
		this.validator = validator; 
		this.activityType = activityType;
	}

	/**
	 * The activity type performed in the <code>Service</code> method. 
	 * @return int Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public int getActivityType()
	{
		return activityType;
	}

	/**
	 * Returns the name of service method as defined in XML.
	 * @return String name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returna The <code>Validator</code> (optionally) to validate Context data before the method execution.
	 * @return Validator
	 */
	public Validator getValidator()
	{
		return validator;
	}

	/**
	 * Returns the name of the method to execute in the service class.
	 * @return  String executionMethod
	 */
	public String getExecutionMethod()
	{
		return executionMethod;
	}

	/**
	 * Returns the name for {@link Context} to be created for the <code>Service</code> method.
	 * @return String contextName
	 */
	public String getContextName()
	{
		return contextName;
	}
}
