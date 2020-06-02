/*
 * Created on 18/11/2004
 * Created By yharnof
 * @version $Id: ServiceMethodDataList.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Implementation of list of service method data instances.
 */
public class ServiceMethodDataList
{
	private Map methodMapping;

	public ServiceMethodDataList()
	{
		methodMapping = new HashMap();
	}

	public ServiceMethodData getServiceMethodData(String methodName) throws ServiceException
	{
		Object data = methodMapping.get(methodName);
		if(data == null)
			throw new ServiceException("Unable to execute service. Execution method [" + methodName + "] was not found.");
			
		return (ServiceMethodData) data;
	}

	public void addServiceMethodData(ServiceMethodData serviceMethodData)
	{
		methodMapping.put(serviceMethodData.getName(), serviceMethodData);
	}

	public int getServiceMethodDatasCount()
	{
		return methodMapping.size();
	}
}
