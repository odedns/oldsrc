/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextPeer.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * The common part of all {@link Context} instances of the same type. 
 * Keeps all contexts definitions such as list of permitted fields, services data etc...
 */
public class ContextPeer 
{

	/**
	 * The name of the context
	 */
	private String contextName;

	/**
	 * The name of the parent context, should be from static type.
	 */
	private String parentContextName;

	/**
	 * Type of context (static or dynamic)
	 */
	private int contextType;

	/**
	 * Indicates if adding new fields that was not been declared in 
	 * the context declaration on demand is permitted.
	 */
	private boolean addOnDemand;

	/**
	 * Holds the <code>Context</code> fields & services declarations.
	 */
	private ContextMetaData metaData;

	/**
	 * Creates new ContextPeer object.
	 * @param contextName The name of the context.
	 * @param parentContextName The name of the parent context, should be from static type.
	 * @param contextType The type of the context (static or dynamic).
	 * @param addOnDemand Indicates if adding new fields that was not 
	 * been declared in the context declaration on demand is permitted.
	 */
	public ContextPeer(String contextName, String parentContextName, int contextType, boolean addOnDemand)
	{
		this.contextName = contextName;
		this.parentContextName = parentContextName;
		this.contextType = contextType;
		this.addOnDemand = addOnDemand;
		metaData = new ContextMetaData();

	}

	/**
	 * Returns the name of the context
	 * @return String Context name.
	 */
	public String getContextName()
	{
		return contextName;
	}

	/**
	 * Returns the name of the parent context, should be from static type.
	 * @return String Parent context name.
	 */
	public String getParentContextName()
	{
		return parentContextName;
	}

	/**
	 * Returns the type of the context (static or dynamic).
	 * @return int Context type.
	 */
	public int getContextType()
	{
		return contextType;
	}

	/**
	 * Indicates if adding new fields that was not been declared in 
	 * the context declaration on demand is permitted.
	 * @return boolean
	 */
	public boolean isAddOnDemand()
	{
		return addOnDemand;
	}

	/**
	 * Indicates if at least one of the context fields has a default value.
	 * @return boolean
	 */
	public boolean isDefaultValuesPresents()
	{
		return metaData.isDefaultValuesPresents();
	}

	/**
	 * Returns the number of services defined in the context. 
	 * @return int Services count.
	 */
	public int getServicesCount()
	{
		return metaData.getServicesCount();
	}
	
	/**
	 * Returns the service data by the service index.
	 * Used for initializing services by context instance while construction.
	 * @param index The index of the {@link ServiceData} in the list.
	 * @return ServiceData
	 */
	protected ServiceData getServiceData(int index)
	{
		return metaData.getServiceData(index);
	}	

	/**
	 * Used by {@link Context} instance to check if a field with 
	 * the given name is permitted for the context and returns the field declaration.
	 * 
	 * If the field does not exist in the current meta data, search the field in the 
	 * extended meta data. 
	 * 
	 * @param contextFieldName The name of field
	 * @param extendedMetaData The extended <code>ContextMetaData</code> to get the field from.
	 * @return ContextField The field declaration. 
	 */
	protected ContextField getField(String fieldName, ContextMetaData extendedMetaData)
	{
		ContextField field = metaData.getField(fieldName);
		if(field == null && extendedMetaData != null)
		{
			field = extendedMetaData.getField(fieldName);
		}
		return field;
	}

	/**
	 * Used by {@link Context} instance to check if a field with 
	 * the given name is permitted for the context and returns the field declaration.
	 * 
	 * @param contextFieldName The name of field
	 * @return ContextField The field declaration. 
	 */
	protected ContextField getField(String fieldName)
	{
		return metaData.getField(fieldName);
	}

	/**
	 * Returns the context field names.
	 * @return Iterator
	 */
	protected Iterator getFieldNames()
	{
		return metaData.getFieldNames();
	}

	/**
	 * Add a field to the context.
	 * @param field The {@link ContextField} to add to the context.
	 */
	private boolean isContainsField (String fieldName, ContextMetaData dynamicMetaData)
	{
		boolean contains = false;
		
		if(metaData.isContainsField(fieldName))
			contains = true;
		else if(dynamicMetaData != null && dynamicMetaData.isContainsField(fieldName))
			contains = true;
			
		return contains;
	}

	/**
	 * Returns meta data object that holds the <code>Context</code> fields & services declarations.
	 * @return ContextMetaData
	 */
	public ContextMetaData getMetaData()
	{
		return metaData;
	}

	/**
	 * Add a field to the context.
	 * @param field The {@link ContextField} to add to the context.
	 */
	public void addContextField(ContextField field, ContextMetaData dynamicMetaData) throws ContextException
	{
		if (isContainsField(field.getName(), dynamicMetaData))
		{
			throw new ContextException("Unable to add the field [" + field.getName() + "] to the context [" + contextName + "].  A field with that name is already defined.");
		}
		else
		{
			if(dynamicMetaData == null)
			{
				metaData.addField(field);
			}
			else
			{
				dynamicMetaData.addField(field);
			}
		}
	}
	
	/**
	 * copy the fields & services from the given metaData to the current metaData / dynamic metaData if exists.
	 * @param metaDataToCopy The metadata to copy the definitions.
	 * @param dynamicMetaData The Dynamic context metaData to add the definitions, if exists.
	 */
	public void copyMetaDataDefinitions (ContextMetaData metaDataToCopy, ContextMetaData dynamicMetaData) throws ContextException
	{
		// copy fields
		Iterator copyFieldsIterator = metaDataToCopy.getFields().keySet().iterator();
		while (copyFieldsIterator.hasNext())
		{
			String name = (String)copyFieldsIterator.next(); 
			addContextField((ContextField)metaDataToCopy.getField(name), dynamicMetaData); 
		}

		// copy services
		int servicesCount = metaDataToCopy.getServicesCount();
		if (servicesCount > 0)
		{
			ContextMetaData destinationMetaData = metaData;
			if(dynamicMetaData != null)
			{
				destinationMetaData = dynamicMetaData;
			}

			for (int i = 0 ; i < servicesCount ; i++)
			{
				destinationMetaData.addServiceData(metaDataToCopy.getServiceData(i));
			}
		}
	}
}
