/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: Field.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

/**
 * The super class of the Field classes in the Legacy layer.
 */
public abstract class Field
{
	/*
	 * Class field attributes  
	 */
	 
	/**
	 * The attribute id in the class.
	 */
	private String attributeId;
	
	/**
	 * The attribute data type in the class.
	 */
	private Integer attributeType;
	
	/**
	 * The mask to convert the value from. 
	 */
	private String fromMask;
	
	/**
	 * The name of the setter method in the value object, to set the field's data.  
	 */
	private String setter;

	/**
	 * Creates new Field object.
	 * @param attributeId The attribute id in the class.
	 * @param attributeType The attribute data type in the class.
	 * @param setter The name of the setter method in the value object, to set the field's data.
	 * @param fromMask The mask to convert the value from.
	 */
	public Field(String attributeId, Integer attributeType, String setter, String fromMask)
	{
		this.attributeId = attributeId;
		this.attributeType = attributeType;
		this.setter = setter;
		this.fromMask = fromMask;
	}

	/**
	 * Returns the attribute id in the class.
	 * @return String 
	 */
	public String getAttributeId()
	{
		return attributeId;
	}

	/**
	 * Returns the attribute data type in the class.
	 * @return Integer 
	 */
	public Integer getAttributeType()
	{
		return attributeType;
	}

	/**
	 * Returns the name of the setter method in the value object, to set the field's data.
	 * @return String
	 */
	public String getSetter()
	{
		return setter;
	}

	/**
	 * Returns the mask to convert the value from. 
	 * @return String
	 */
	public String getFromMask()
	{
		return fromMask;
	}
	
	/**
	 * Returns the data type of the field's value.
	 * @return Integer
	 */
	public abstract Integer getValueType();
	
	

}
