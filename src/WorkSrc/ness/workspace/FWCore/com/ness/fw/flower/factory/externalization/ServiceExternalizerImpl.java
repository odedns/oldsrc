/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ServiceExternalizerImpl.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;

import java.util.*;

import org.w3c.dom.*;

public class ServiceExternalizerImpl extends ServiceExternalizer
{
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " SERVICE EXT.";

    private HashMap services;

	public ServiceExternalizerImpl(DOMRepository domRepository) throws ExternalizerInitializationException
	{
        services = new HashMap();

        DOMList domList = domRepository.getDOMList(ExternalizerConstants.SERVICE_TAG_NAME);

		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);
				processDOM(doc);
			}
		}
	}

	public Service createService(String serviceName) throws ExternalizationException
	{
		ServiceData serviceData = (ServiceData) services.get(serviceName);

		if (serviceData == null)
		{
			throw new ExternalizationException("No service is defined with name [" + serviceName + "]");
		}

		Service service;
		try
		{
			service = (Service) serviceData.clazz.newInstance();
		} catch (Throwable ex)
		{
			throw new ExternalizationException("Unable to create Service with name [" + serviceData.name + "]. See exception:", ex);
		}

		service.setName(serviceData.name);
		service.setValidator(serviceData.validator);
		service.setActivityType(serviceData.activityType);
		service.setServiceMethodDataList(serviceData.methodList);
		service.setDefualtMethodContextName(serviceData.defaultMethodContext);

		return service ;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	private void processDOM(Document doc)
	{
        Element rootElement = doc.getDocumentElement();
		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.SERVICE_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			try
			{
				readService(element);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Error reading service. Continue with other services.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void readService(Element element) throws ExternalizerInitializationException, ExternalizerNotInitializedException, ExternalizationException
	{
		String name = ExternalizerUtil.getName(element);
		if(services.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize service [" + name + "]. A service with that name is already defined.");
		}
		else
		{
			ServiceData serviceData = new ServiceData();
	
	        serviceData.name = name;
	        
			String className = ExternalizerUtil.getClassName(element);
	
			Logger.debug(LOGGER_CONTEXT, "Loading service with name: [" + serviceData.name +  "] and class name [" + className + "]");
	
			serviceData.defaultMethodContext = XMLUtil.getAttribute(element, ExternalizerConstants.SERVICE_ATTR_DEFAULT_METHOD_CONTEXT);
	
			// retrieve validator for all the service
			serviceData.validator = ExternalizerUtil.createValidator(element);
	
			// retrieve activity type for all the service
			serviceData.activityType = ExternalizerUtil.getActivityType(element);
	
			// read service methods data
			serviceData.methodList = new ServiceMethodDataList();
			NodeList methodNodes = XMLUtil.getElementsByTagName(element, ExternalizerConstants.SERVICE_METHOD_TAG_NAME);
			for (int i = 0; i < methodNodes.getLength(); i++)
			{
				Element methodElement = (Element) methodNodes.item(i);
				serviceData.methodList.addServiceMethodData(readServiceMethod(methodElement));
			}
	
			// load the service class
			serviceData.clazz = null;
			try
			{
				serviceData.clazz = createServiceClass(className);
			} catch (Throwable ex)
			{
				throw new ExternalizerInitializationException("Unable to load Service with name [" + serviceData.name + "]. See exception:", ex);
			}
	
			services.put(serviceData.name, serviceData);
		}
	}

	private ServiceMethodData readServiceMethod (Element element) throws ExternalizerNotInitializedException, ExternalizationException
	{
		String name = ExternalizerUtil.getName(element);
		String executionMethod = XMLUtil.getAttribute(element, ExternalizerConstants.SERVICE_METHOD_ATTR_EXECUTION_METHOD); 
		String contextName = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_CONTEXT);
		Validator validator = ExternalizerUtil.createValidator(element);
		int activityType = ExternalizerUtil.getActivityType(element);
		return new ServiceMethodData (name, executionMethod, contextName, validator, activityType);
	}

	private Class createServiceClass(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException
	{
		Logger.debug(LOGGER_CONTEXT, "Creating service from class [" + className + "]");
        return Class.forName(className);
	}

	private class ServiceData
	{
		String name;
		Class clazz;
		String defaultMethodContext;
		Validator validator;
		int activityType;
		ServiceMethodDataList methodList;
	}
}
