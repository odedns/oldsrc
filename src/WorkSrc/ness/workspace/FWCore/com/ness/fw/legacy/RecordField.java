/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: RecordField.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

/**
 * Contains the definition of a {@link Record} field. 
 */
public class RecordField extends Field
{

	/*
	 * General attributes  
	 */
	/**
	 * Indicates whether the field is the unique identifier of the record.
	 */
	private boolean uniqueId;

	/*
	 * ResultSet field attributes  
	 */
	
	/**
	 * The id of the column in the ResultSet.
	 */
	private String columnId;
	
	/**
	 * The data type of the field's value.
	 */
	private Integer columnType;

	/**
	 * Creates new RecordField object.
	 * @param attributeId The attribute id in the class.
	 * @param attributeType The attribute data type in the class.
	 * @param setter The name of the setter method in the value object, to set the field's data.
	 * @param uniqueId Indicates whether the field is the unique identifier of the record. 
	 * @param columnId The id of the column in the ResultSet.
	 * @param columnType The data type of the field's value.
	 * @param fromMask The name of the setter method in the value object, to set the field's data.
	 */
	public RecordField(String attributeId, Integer attributeType, String setter, boolean uniqueId, String columnId, Integer columnType, String fromMask)
	{
		super(attributeId, attributeType, setter, fromMask);
		this.columnId = columnId;
		this.columnType = columnType;
		this.uniqueId = uniqueId;
	}
		
	/**
	 * Returns the id of the column in the ResultSet.
	 * @return String
	 */
	public String getColumnId()
	{
		return columnId;
	}

	/**
	 * Indicates whether the field is the unique identifier of the record. 
	 * @return boolean
	 */
	public boolean isUniqueId()
	{
		return uniqueId;
	}
	
	/**
	 * Returns the data type of the field's value.
	 * @return Integer
	 */
	public Integer getValueType()
	{
		return columnType;
	}
	

}
