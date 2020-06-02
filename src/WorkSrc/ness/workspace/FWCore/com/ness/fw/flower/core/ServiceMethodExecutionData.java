/*
 * Created on 18/11/2004
 * Created By yharnof
 * @version $Id: ServiceMethodExecutionData.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Contains data for executing specific service method. 
 */
public class ServiceMethodExecutionData
{
	
	/**
	 * The name of service instance in the <code>Context</code>. 
	 */
	private String serviceName;
	 
	/**
	 * The method to execute in the service. 
	 */
	private String methodName;
	
	/**
	 * The {@link ComplexFormatter} performs the field’s 
	 * copy from the parent {@link Context} to the service method {@link Context}, 
	 * before processing the service. And performs the field’s copy from the service 
	 * method {@link Context} to the parent {@link Context}, after processing the service.
	 */
	private String complexFormatterName;
	
	/**
	 * The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the service method {@link Context}, before processing the service.
	 */
	private String inFormatterName;
	
	/**
	 * The {@link Formatter} performs the field’s copy from the service method {@link Context} 
	 * to the parent {@link Context}, after processing the service.
	 */
	private String outFormatterName;

	/**
	 * Creates new ServiceMethodExecutionData object.
	 * @param serviceName The name of service instance in the <code>Context</code>.
	 * @param methodName The method to execute in the service. 
	 */
	public ServiceMethodExecutionData(String serviceName, String methodName)
	{
		this.serviceName = serviceName;
		this.methodName = methodName;
	}

	/**
	 * Creates new ServiceMethodExecutionData object.
	 * @param serviceName The name of service instance in the <code>Context</code>.
	 * @param methodName The method to execute in the service. 
	 * @param inFormatterName The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the service method {@link Context}, before processing the service.
	 * @param outFormatterName The {@link Formatter} performs the field’s copy from the service method {@link Context} 
	 * to the parent {@link Context}, after processing the service.
	 */
	public ServiceMethodExecutionData(String serviceName, String methodName, String inFormatterName, String outFormatterName)
	{
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.inFormatterName = inFormatterName;
		this.outFormatterName = outFormatterName;
	}

	/**
	 * Creates new ServiceMethodExecutionData object.
	 * @param serviceName The name of service instance in the <code>Context</code>.
	 * @param methodName The method to execute in the service. 
	 * @param complexFormatterName The {@link ComplexFormatter} performs the field’s 
	 * copy from the parent {@link Context} to the service method {@link Context}, 
	 * before processing the service. And performs the field’s copy from the service 
	 * method {@link Context} to the parent {@link Context}, after processing the service.
	 */
	public ServiceMethodExecutionData(String serviceName, String methodName, String complexFormatterName)
	{
		this.serviceName = serviceName;
		this.methodName = methodName;
		this.complexFormatterName = complexFormatterName;
	}



	/**
	 * @return
	 */
	public String getComplexFormatterName()
	{
		return complexFormatterName;
	}

	/**
	 * @return
	 */
	public String getInFormatterName()
	{
		return inFormatterName;
	}

	/**
	 * @return
	 */
	public String getMethodName()
	{
		return methodName;
	}

	/**
	 * @return
	 */
	public String getOutFormatterName()
	{
		return outFormatterName;
	}

	/**
	 * @return
	 */
	public String getServiceName()
	{
		return serviceName;
	}

}
