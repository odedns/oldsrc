/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ContextStructureDefinition.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * encapsulates all definition data of {link ContextStructure}.
 */
public class ContextStructureDefinition
{
	/**
	 * Map of the fields that the structure contains.
	 */
	private HashMap fields;

	/**
	 * The name of the structure.
	 */
	private String structureName;

	/**
	 * Indicates if at least one of fields has default value.
	 */
	private boolean defaultValuesPresents;

	/**
	 * create new ContextStructureDefinition object.
	 * @param structureName The name of the structure.
	 */
	public ContextStructureDefinition(String structureName)
	{
		this.structureName = structureName;
		defaultValuesPresents = false;
		fields = new HashMap();
	}

	/**
	 * add a field to the structure. 
	 * @param ContextStructureField
	 */
	public void addStructureField(ContextStructureField field)
	{
		fields.put(field.getName(), field);
		if (field.getDefaultValue() != null)
		{
			defaultValuesPresents = true;
		}
	}

	/**
	 * Returns the field according the field name.
	 * @param contextFieldName The field name.
	 * @return ContextStructureField
	 */
	public ContextStructureField getField(String fieldName)
	{
		return (ContextStructureField) fields.get(fieldName);
	}

	/**
	 * Check if the field is already exists in the structure
	 * @param fieldName The field name to check.
	 * @return boolean
	 */
	public boolean isContainsField (String fieldName)
	{
		return fields.containsKey(fieldName);
	}

	/**
	 * Returns an iterator on the field’s names. 
	 * @return Iterator
	 */
	public Iterator getFieldNames()
	{
		return fields.keySet().iterator();
	}

	/**
	 * Indicates if at least one of fields has default value.
	 * @return boolean 
	 */
	public boolean isDefaultValuesPresents()
	{
		return defaultValuesPresents;
	}

	/**
	 * Returns the name of the structure.
	 * @return String name.
	 */
	public String getStructureName()
	{
		return structureName;
	}
}
