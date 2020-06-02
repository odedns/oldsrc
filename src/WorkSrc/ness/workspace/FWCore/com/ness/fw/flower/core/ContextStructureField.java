/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ContextStructureField.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Represents a field data in the {@link ContextStructure} that encapsulates:
 * <ol>
 * <li>field name
 * <li>field type
 * <li>default value
 * </ol>
 */
public class ContextStructureField
{
	/**
	 * The separator between the structure name and the field name. 
	 */
	public static final String STRUCTURE_NAME_SEPARATOR = ".";

	/**
	 * The name of the field.
	 */
	private String name;

	/**
	 * The type of the field.
	 */
	private ContextFieldType type;

	/**
	 * The default value for the field, to set when the structure has been created.
	 */
	private String defaultValue;

	/**
	 * create new ContextStructureField object.
	 * @param name The name of the field.
	 * @param type The type of the field.
	 * @param defaultValue The default value for the field, to set when the structure has been created.
	 */
	public ContextStructureField(String name, ContextFieldType type, String defaultValue)
	{
		this.defaultValue = defaultValue;
		this.name = name;
		this.type = type;
	}

	/**
	 * Returns the default value for the field, to set when the structure has been created.
	 * @return String Default value.
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Returns the name of the field.
	 * @return String Name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the type of the field.
	 * @return ContextFieldType Field type
	 */
	public ContextFieldType getType()
	{
		return type;
	}
	
	/**
	 * Indicates whether the type is XI (external interface) type and contains model that 
	 * check the perfmissions to set the data into the field from the request parameters. 
	 * @return boolean
	 */
	public boolean isXIType()
	{
		return type.isXIType();
	}
}
