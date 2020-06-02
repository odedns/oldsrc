/*
 * Created on 13/07/2004
 * Created By yharnof
 * @version $Id: ContextMetaData.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Encapsulates the <code>Context</code> fields & services declarations.
 */
public class ContextMetaData
{
	
	/**
	 * Map of permitted fields for the context
	 */
	private HashMap fields;

	/**
	 * Services data - The data needed for service initializing.
	 */
	private ArrayList servicesData;
	
	/**
	 * Indicates if at least one of the context fields has a default value.
	 */
	private boolean defaultValuesPresents = false;

	/**
	 * Creates new ContextMetaData object. 
	 */
	public ContextMetaData()
	{
		fields = new HashMap();
		servicesData = new ArrayList();
	}

	public boolean isContainsField (String fieldName)
	{
		return fields.containsKey(fieldName);
	}

	/**
	 * Add a field to the context.
	 * @param field The {@link ContextField} to add to the context.
	 */
	public void addField(ContextField field) throws ContextException
	{
		String name = field.getName();
		if(isContainsField(name))
		{
			throw new ContextException("Unable to add the field [" + name + "] to the context.  A field with that name is already defined.");
		}
		
		fields.put(name, field);
		
		if(!defaultValuesPresents)
		{
			if (field.isDefaultValuesPresents())
			{
				defaultValuesPresents = true;
			}
		}
	}

	/**
	 * Add a service to the context.
	 *
	 * @param serviceName The name of the service to be used inside the {@link Context} to identify the service.
	 * @param serviceRefName The reference to the service declaration.
	 * @param parameterList The {@link ParameterList} to initialize the service.
	 */
	public void addServiceData(String serviceName, String serviceRefName, ParameterList parameterList)
	{
		addServiceData(new ServiceData(serviceName, serviceRefName, parameterList));
	}

	/**
	 * Add a service to the context.
	 *
	 * @param serviceName The name of the service to be used inside the {@link Context} to identify the service.
	 * @param serviceRefName The reference to the service declaration.
	 * @param parameterList The {@link ParameterList} to initialize the service.
	 */
	protected void addServiceData(ServiceData serviceData)
	{
		servicesData.add(serviceData);
	}
	
	/**
	 * Used by {@link Context} instance to check if a field with 
	 * the given name is permitted for the context.
	 *
	 * @param contextFieldName The name of field
	 * @return ContextField The field declaration. 
	 */
	protected ContextField getField(String fieldName)
	{
		return (ContextField) fields.get(fieldName);
	}

	/**
	 * Returns the service data by the service index.
	 * Used for initializing services by context instance while construction.
	 * @param index The index of the {@link ServiceData} in the list.
	 * @return ServiceData
	 */
	protected ServiceData getServiceData(int index)
	{
		return (ServiceData) servicesData.get(index);
	}

	/**
	 * Returns the number of services defined in the context. 
	 * @return int Services count.
	 */
	public int getServicesCount()
	{
		return servicesData.size();
	}

	/**
	 * Returns the number of fields defined in the context. 
	 * @return int fields count.
	 */
	public int getFieldsCount()
	{
		return fields.size();
	}

	/**
	 * Returns the context field names.
	 * @return Iterator
	 */
	protected Iterator getFieldNames()
	{
		return fields.keySet().iterator();
	}

	/**
	 * Returns the fields.
	 * @return HashMap
	 */
	protected HashMap getFields()
	{
		return fields;
	}

	/**
	 * Returns the services.
	 * @return ArrayList
	 */
	protected ArrayList getServices()
	{
		return servicesData;
	}

	/**
	 * Indicates if at least one of the context fields has a default value.
	 * @return boolean
	 */
	public boolean isDefaultValuesPresents()
	{
		return defaultValuesPresents;
	}


}
