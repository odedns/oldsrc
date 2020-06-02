/*
 * Created on 08/07/2004
 * Created By yharnof
 * @version $Id: DynamicStructure.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.ArrayList;
import java.util.List;


/**
 * Represent a definition of specific structure, for adding dynamically into a context.
 * The structure definition is made in the current object and do not depend on 
 * a structure definition in an XML file.
 */
public class DynamicStructure
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
	 * The structure name.
	 */
	private String structureName;
	
	/**
	 * The name of the field in the context.
	 */
	private String contextFieldName;
	
	/**
	 * The child access of the field. 
	 * Could be one of the following: READ_ONLY, FULL, NONE 
	 */
	private int childAccess = CHILD_ACCESS_READ_ONLY;
	
	/**
	 * The structure fields list. 
	 */
	private List fields;

	/**
	 * Creates new DynamicStructure object.
	 * Holds the data for adding a new field of type structure dynamically into 
	 * the context.
	 * @param structureName The structure name.
	 * @param contextFieldName The name of the field in the context.
	 * @param childAccess The child access of the field. 
	 * Could be one of the following: READ_ONLY, FULL, NONE 
	 */
	public DynamicStructure(String structureName, String contextFieldName, int childAccess)
	{
		fields = new ArrayList(1);
		this.structureName = structureName;
		this.contextFieldName = contextFieldName;
		this.childAccess = childAccess;
	}

	/**
	 * Returns the child access of the field. 
	 * @return int Could be one of the following: READ_ONLY, FULL, NONE
	 */
	public int getChildAccess()
	{
		return childAccess;
	}

	/**
	 * Returns the name of the field in the context.
	 * @return String
	 */
	public String getContextFieldName()
	{
		return contextFieldName;
	}

	/**
	 * Returns the structure name.
	 * @return structureName
	 */
	public String getStructureName()
	{
		return structureName;
	}

	/**
	 * Stes the child access of the field. 
	 * @param childAccess int Could be one of the following: READ_ONLY, FULL, NONE
	 */
	public void setChildAccess(int childAccess)
	{
		this.childAccess = childAccess;
	}

	/**
	 * Sets the name of the field in the context.
	 * @param contextFieldName String
	 */
	public void setContextFieldName(String contextFieldName)
	{
		this.contextFieldName = contextFieldName;
	}

	/**
	 * Sets the structure name.
	 * @param structureName String
	 */
	public void setStructureName(String structureName)
	{
		this.structureName = structureName;
	}

	/**
	 * Add a specific field into the structure.
	 * @param fieldName The field name.
	 * @param fieldType The field type. Could be one of the following: 
	 * type definition name, Structure name, class name.
	 * @param defaultValue The default value for the field.
	 * @param caption  The caption of the field.
	 */
	public void addField (String fieldName, String fieldType, String defaultValue, String caption)
	{
		fields.add(new DynamicField(fieldName, fieldType, defaultValue, caption));
	}

	/**
	 * Add a specific field into the structure.
	 * @param fieldName The field name.
	 * @param fieldType The field type. Could be one of the following: 
	 * type definition name, Structure name, class name.
	 * @param defaultValue The default value for the field.
	 */
	public void addField (String fieldName, String fieldType, String defaultValue)
	{
		fields.add(new DynamicField(fieldName, fieldType, defaultValue, null));
	}

	/**
	 * Add a specific field into the structure.
	 * @param fieldName The field name.
	 * @param fieldType The field type. Could be one of the following: 
	 * type definition name, Structure name, class name.
	 */
	public void addField (String fieldName, String fieldType)
	{
		fields.add(new DynamicField(fieldName, fieldType));
	}
	
	/**
	 * Returns the declaration of a field from teh structure definition.
	 * @param index The field index
	 * @return DynamicField The field definition 
	 */
	public DynamicField getField (int index)
	{
		return (DynamicField) fields.get(index);	
	}
	
	/**
	 * Returns the structure fields count.
	 * @return int count
	 */
	public int getFieldsCount()
	{
		return fields.size();
	}
	
}
