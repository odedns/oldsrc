/*
 * Created on 08/07/2004
 * Created By yharnof
 * @version $Id: DynamicField.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Represent a definition of a specific field for adding dynamically into a context. 
 */
public class DynamicField
{

	/**
	 * read only security modifier. 
	 * Everyone in the context hierarchy can get the value of the field. 
	 * Only the owner of the context can change the value of the field. 
	 */
	public static final int CHILD_ACCESS_READ_ONLY = ContextField.CHILD_ACCESS_READ_ONLY;
	
	/**
	 * full access security modifier. 
	 * Everyone in the context hierarchy can get or change the value of the field. 
	 */
	public static final int CHILD_ACCESS_FULL = ContextField.CHILD_ACCESS_FULL;

	/**
	 * none access security modifier. 
	 * Only the owner of the context can get or change the value of the field. 
	 */
	public static final int CHILD_ACCESS_NONE = ContextField.CHILD_ACCESS_NONE;

	/**
	 * The field name in the context.
	 */
	private String fieldName;
	
	/**
	 * The field type. Could be one of the following: 
	 * type definition name, Structure name, class name.
	 */
	private String fieldType;
	
	/**
	 * The child access of the field. Could be one of the following: READ_ONLY, FULL, NONE
	 */
	private int childAccess = CHILD_ACCESS_READ_ONLY;
	
	/**
	 * The default value for the field.
	 */
	private String defaultValue;

	/**
	 * The caption of the field.
	 */
	private String caption;
	
	/**
	 * Creates new DynamicField object.
	 * Holds the data for adding a new field dynamically into the context.
	 * @param fieldName The field name in the context.
	 * @param fieldType The field type. Could be one of the following:
	 * type definition name, Structure name, class name. 
	 * @param childAccess The child access of the field. 
	 * Could be one of the following: READ_ONLY, FULL, NONE 
	 * @param defaultValue The default value for the field.
	 * @param caption The caption of the field.
	 */
	public DynamicField(String fieldName, String fieldType, int childAccess, String defaultValue, String caption)
	{
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.childAccess = childAccess;
		this.defaultValue = defaultValue; 
	}

	/**
	 * Creates new DynamicField object.
	 * gets the data for adding a new field dynamically to the context.
	 * @param fieldName The field name in the context.
	 * @param fieldType The field type. Could be one of the following:
	 * type definition name, Structure name, class name. 
	 * @param defaultValue The default value for the field.
	 * @param caption The caption of the field.
	 */
	public DynamicField(String fieldName, String fieldType, String defaultValue, String caption)
	{
		this(fieldName, fieldType, CHILD_ACCESS_READ_ONLY, defaultValue, caption);
	}

	/**
	 * Creates new DynamicField object.
	 * gets the data for adding a new field dynamically to the context.
	 * @param fieldName The field name in the context.
	 * @param fieldType The field type. Could be one of the following:
	 * type definition name, Structure name, class name. 
	 * @param defaultValue The default value for the field.
	 */
	public DynamicField(String fieldName, String fieldType, String defaultValue)
	{
		this(fieldName, fieldType, CHILD_ACCESS_READ_ONLY, defaultValue, null);
	}

	
	/**
	 * Creates new DynamicField object.
	 * gets the data for adding a new field dynamically to the context.
	 * @param fieldName The field name in the context.
	 * @param fieldType The field type. Could be one of the following:
	 * type definition name, Structure name, class name. 
	 * @param caption The caption of the field.
	 */
	public DynamicField(String fieldName, String fieldType)
	{
		this(fieldName, fieldType, CHILD_ACCESS_READ_ONLY, null, null);
	}	
	
	/**
	 * Returns the child access of the field. 
	 * @return int childAccess Could be one of the following: READ_ONLY, FULL, NONE
	 */
	public int getChildAccess()
	{
		return childAccess;
	}

	/**
	 * Returns the default value for the field.
	 * @return String defaultValue
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * Returns the field name in the context.
	 * @return String fieldName
	 */
	public String getFieldName()
	{
		return fieldName;
	}

	/**
	 * Returns the field type. Could be one of the following:
	 * type definition name, Structure name, class name. 
	 * @return String fieldType
	 */
	public String getFieldType()
	{
		return fieldType;
	}

	/**
	 * Sets the child access of the field. 
	 * @param int childAccess Could be one of the following: READ_ONLY, FULL, NONE
	 */
	public void setChildAccess(int childAccess)
	{
		this.childAccess = childAccess;
	}

	/**
	 * Sets the default value for the field.
	 * @param String defaultValue
	 */
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	/**
	 * Sets the field name in the context.
	 * @param String fieldName
	 */
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	/**
	 * Sets the field type. Could be one of the following:
	 * type definition name, Structure name, class name. 
	 * @param String fieldType
	 */
	public void setFieldType(String fieldType)
	{
		this.fieldType = fieldType;
	}

	/**
	 * Returns the caption of the field.
	 * @return String caption
	 */
	public String getCaption()
	{
		return caption;
	}

	/**
	 * Sets the caption of the field.
	 * @param caption
	 */
	public void setCaption(String caption)
	{
		this.caption = caption;
	}

}
