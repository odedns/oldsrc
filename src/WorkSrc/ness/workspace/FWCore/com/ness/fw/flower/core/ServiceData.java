/*
 * Created on 13/07/2004
 * Created By yharnof
 * @version $Id: ServiceData.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Used for encapsulating all data that needed for service instance
 * creation and initializing.
 */
public class ServiceData
{
	/**
	 * The service name.
	 */
	private String serviceName;
		
	/**
	 * The reference to the service declaration. 
	 */
	private String serviceRefName;
		
	/**
	 * The parameters list to initialize the service.
	 */
	private ParameterList parameterList;


	/**
	 * Creates new ServiceData object.
	 * @param serviceName The service name.
	 * @param serviceRefName The reference to the service declaration. 
	 * @param parameterList The parameters list to initialize the service.
	 */
	public ServiceData(String serviceName, String serviceRefName, ParameterList parameterList)
	{
		this.serviceName = serviceName;
		this.serviceRefName = serviceRefName;
		this.parameterList = parameterList;
	}

	/**
	 * Returns the parameters list to initialize the service.
	 * @return ParameterList
	 */
	public ParameterList getParameterList()
	{
		return parameterList;
	}

	/**
	 * Returns The service name.
	 * @return String
	 */
	public String getServiceName()
	{
		return serviceName;
	}

	/**
	 * Returns the reference to the service declaration. 
	 * @return String
	 */
	public String getServiceRefName()
	{
		return serviceRefName;
	}


}
