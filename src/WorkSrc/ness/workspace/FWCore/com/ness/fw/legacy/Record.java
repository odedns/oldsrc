/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: Record.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

import java.util.List;

/**
 * Contains the record definition of a ResultSet.
 * According to the definition the framework analyzes the {@link Page}
 * built according to the ResultSet and creates the LegacyObjectGraph.
 */
public class Record
{
	/**
	 * The record id
	 */
	private String id;
	
	/**
	 * The class name to instansiate for each record of that ResultSet.
	 */
	private String className;
	
	/**
	 * The index of the unique identifier field in the list.
	 */
	private int uniqueFieldIndex;

	/**
	 * The {@link RecordField} definitions inside the record.
	 * @associates <{com.ness.fw.legacy.RecordField}>
	 * @clientCardinality 1
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private List fields = null;

	/**
	 * Creates new Record object
	 * @param id The record id
	 * @param className The class name to instansiate for each record of that ResultSet.
	 * @param uniqueFieldIndex The index of the unique identifier field in the list.
	 * @param fields The {@link RecordField} definitions inside the record.
	 */
	public Record(String id, String className, int uniqueFieldIndex, List fields)
	{
		this.id = id;
		this.className = className;
		this.uniqueFieldIndex = uniqueFieldIndex;
		this.fields = fields;
	}

	/**
	 * Returns the record id.
	 * @return String
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Returns the class name to instansiate for each record of that ResultSet.
	 * @return String
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Returns the index of the unique identifier field in the list.
	 * @return int
	 */
	public int getUniqueIdFieldIndex()
	{
		return uniqueFieldIndex;
	}

	/**
	 * Returns a specific field definition according to the index.  
	 * @param index The field index in the list
	 * @return RecordField
	 */
	public RecordField getField(int index)
	{
		if(index >= 0 && index < fields.size())
			return (RecordField)fields.get(index);
		return null;
	}

	/**
	 * Returns the index of the field in the list according to the fieldAttributeId.
	 * @param fieldAttributeId
	 * @return int
	 */
	public int getFieldIndex(String fieldAttributeId)
	{
		int fieldIndex = -1;
		for (int i = 0 ; i < fields.size() ; i++)
		{
			RecordField field = (RecordField)fields.get(i);
			if(field.getAttributeId().equals(fieldAttributeId))
			{
				fieldIndex = i;
				break;
			}
		}
		return fieldIndex;
	}

	/**
	 * Returns the count of the fields inside the record.
	 * @return int
	 */
	public int getFieldsCount()
	{
		return fields == null ? 0 : fields.size();
	}

}
