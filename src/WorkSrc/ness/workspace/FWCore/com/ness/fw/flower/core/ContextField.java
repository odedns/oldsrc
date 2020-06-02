/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextField.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Represents a field data in the context that encapsulates:
 * <ol>
 * <li>field name
 * <li>field type
 * <li>default value
 * <li>security modifier
 * </ol>
 */
public class ContextField
{
	/**
	 * none access security modifier. 
	 * Only the owner of the context can get or change the value of the field. 
	 */
	public static final int CHILD_ACCESS_NONE = 0;
	
	/**
	 * read only security modifier. 
	 * Everyone in the context hierarchy can get the value of the field. 
	 * Only the owner of the context can change the value of the field. 
	 */
	public static final int CHILD_ACCESS_READ_ONLY = 1;
	
	/**
	 * full access security modifier. 
	 * Everyone in the context hierarchy can get or change the value of the field. 
	 */
	public static final int CHILD_ACCESS_FULL = 2;

	/**
	 * The name of the field.
	 */
    private String name;

	/**
	 * The security modifier of the field. 
	 * Can be one of the following: <code>CHILD_ACCESS_READ_ONLY</code>, <code>CHILD_ACCESS_FULL</code>
	 */
	private int access;

	/**
	 * The type of the field.
	 */
	private ContextFieldType type;

	/**
	 * The default value of the field, to set when the {@link Context} has been created.
	 */
	private String defaultValue;

	/**
	 * The caption of the field.
	 */
	private String caption;

	/**
	 * create new ContextField object.
	 * @param name The name of the field.
	 * @param type The type of the field.
	 * @param access The security modifier of the field. Can be one of the following: <code>CHILD_ACCESS_READ_ONLY</code>, <code>CHILD_ACCESS_FULL</code>
	 * @param defaultValue The default value of the field, to set when the {@link Context} has been created.
	 * @param caption The caption of the field.
	 */
	public ContextField(String name, ContextFieldType type, int access, String defaultValue, String caption)
	{
		this.access = access;
		this.defaultValue = defaultValue;
		this.name = name;
		this.type = type;
		this.caption = caption;
	}

	/**
	 * Returns the name of the field.
	 * @return String Name.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the security modifier of the field. Can be one of the following: <code>CHILD_ACCESS_READ_ONLY</code>,<code>CHILD_ACCESS_FULL</code> 
	 * @return int
	 */
	public int getAccess()
	{
		return access;
	}

	/**
	 * Returns the type of the field.
	 * @return ContextFieldType
	 */
	public ContextFieldType getType()
	{
		return type;
	}

	/**
	 * Indicates whether the field is of XI (external interface) type and contains model that 
	 * check the perfmissions to set the data into the field from the request parameters. 
	 * @return boolean
	 */
	public boolean isXIType() 
	{
		return type.isXIType();
	}
	
	/**
	 * The default value of the field, to set when the {@link Context} 
	 * has been created.
	 * @return String Default value.
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Returns the caption of the field. 
	 * If the caption was not supplied, returns the name of the field.
	 * @return String Field's caption
	 */
	public String getCaption()
	{
		if(caption != null)
			return caption;
		else
			return name;
	}
	
	/**
	 * Indicates if the fields contains default value.
	 * @return boolean
	 */
	public boolean isDefaultValuesPresents ()
	{
		boolean isDefaultPresent = false;
		if (defaultValue != null)
		{
			isDefaultPresent = true;
		}
		else
		{
			if (type.getType() == ContextFieldType.STRUCTURE_TYPE)
			{
				if (((StructureContextFieldType)type).isDefaultValuesPresents())
				{
					isDefaultPresent = true;
				}
			}
		}
		
		return isDefaultPresent;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "name=" + name + " XIType=" + type.isXIType() + " class=" + type.getClass() + " defaultValue=" + defaultValue + " childAccess=" + access;  
	}


}
