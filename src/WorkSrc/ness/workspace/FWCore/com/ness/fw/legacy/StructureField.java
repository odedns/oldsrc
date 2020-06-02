/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: StructureField.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

import com.ness.fw.util.*;

/**
 * Contains the definition of a {@link Structure} field. 
*/
public class StructureField extends Field
{
	/**
	 * The data type of all the values. 
	 */
	private static final Integer VALUE_TYPE = new Integer(TypeConverter.TYPE_STRING);
	
	/*
	 * Buffer field attributes  
	 */
	/**
	 * The start position of the field value in the record data.
	 */
	private int startPosition;
	
	/**
	 * The length of the field value in the record data.
	 */
	private int length;

	/**
	 * Creates new StructureField object.
	 * @param attributeId The attribute id in the class.
	 * @param attributeType The attribute data type in the class.
	 * @param setter The name of the setter method in the value object, to set the field's data.
	 * @param startPosition The start position of the field value in the record data.
	 * @param length The length of the field value in the record data.
	 * @param fromMask The name of the setter method in the value object, to set the field's data.
	 */
	public StructureField(String attributeId, Integer attributeType, String setter, int startPosition, int length, String fromMask)
	{
		super(attributeId, attributeType, setter, fromMask);
		this.startPosition = startPosition;
		this.length = length;
	}
	/**
	 * Returns the length of the field value in the record data.
	 * @return int
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * Returns the start position of the field value in the record data.
	 * @return int
	 */
	public int getStartPosition()
	{
		return startPosition;
	}

	/**
	 * Returns the data type of the field's value.
	 * @return Integer
	 */
	public Integer getValueType()
	{
		return VALUE_TYPE;
	}


}
