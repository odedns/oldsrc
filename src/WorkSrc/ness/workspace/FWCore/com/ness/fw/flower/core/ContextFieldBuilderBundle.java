/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextFieldBuilderBundle.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.lang.reflect.*;

/**
 * Contained in a {@link ClassContextFieldType} object, for a type definition.
 * The object should construct a new data with the class that declared in the type 
 * definition, each time that a value was set into the context and it's class is 
 * not equals to type in the type definition.  
 */
public class ContextFieldBuilderBundle
{
	/**
	 * The class of the data to build from. 
	 */
	private Class typeToBuildFrom;
	
	/**
	 * The {@link ContextFieldBuilder} instance.
	 */
	private ContextFieldBuilder builderInstance;
	
	/**
	 * The builder method instance.
	 */
	private Method builderMethod;

	/**
	 * create new ContextFieldBuilderBundle Object.
	 * @param builderInstance The {@link ContextFieldBuilder} instance.
	 * @param builderMethod The builder method instance.
	 * @param typeToBuildFrom The class of the data to build from. 
	 */
	public ContextFieldBuilderBundle(ContextFieldBuilder builderInstance, Method builderMethod, Class typeToBuildFrom)
	{
		this.builderInstance = builderInstance;
		this.builderMethod = builderMethod;
		this.typeToBuildFrom = typeToBuildFrom;
	}

	/**
	 * Returns the class of the data to build from. 
	 * @return Class
	 */
	public Class getTypeToBuildFrom()
	{
		return typeToBuildFrom;
	}

	/**
	 * Construct and returns the new data with the class of the type definition. 
 	 * definition using the data that was set into to the context
	 * @param messagesContainer The {@link MessagesContainer} to add the messages when an error occurred while constructing the data.
	 * @param localizable The {@link LocalizedResources} to uses its data when an error occurred while constructing the data.
	 * @param currentFieldData The data that the context contains for the field, before setting the new constructed data.
	 * @param newFieldData The new data to set into the context field (of type typeToBuildFrom).
	 * @return Object The new data constructed from the new value to the type of the context field. 
	 * @throws InvocationTargetException
	 * @throws IncompatibleFieldTypeException
	 * @throws FatalException
	 */
	public Object build(ContextFieldBuilderParams builderParams, Object currentFieldData, Object newFieldData) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		return builderMethod.invoke(builderInstance, new Object[]{builderParams, currentFieldData, newFieldData});
	}
	
}
