/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextFieldType.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Common ancestor for classes that represent type of context field
 */
public abstract class ContextFieldType
{
	/**
	 * Constant for Class type (field defined using class or type definition).
	 */
	public static final int CLASS_TYPE = 1;

	/**
	 * Constant for Structure type. (Field defined using structure definition).
	 */
	public static final int STRUCTURE_TYPE = 2;

	/**
	 * The type of the field. 
	 * Can be one of the following: {@link CLASS_TYPE}, {@link STRUCTURE_TYPE} 
	 */
	private int type;

	/**
	 * Indicates whether the type is XI (external interface) type and contains model 
	 * that check the perfmissions to set the data into the field from the 
	 * request parameters.
	 */
	private boolean xIType;

	/**
	 * The name of the type definition.
	 */
	private String typeName;

	/**
	 * The name of the basicXItype definition - this will use when we want 
	 * type and it's basic type should be other type
	 * For example: myBigDecimal with it's attributes, it's basic type is bigDEcimal
	 */
	private String basicXITypeName;


	/**
	 * create new ContextFieldType Object.
	 * @param type The type of the field.
	 * Can be one of the following: {@link CLASS_TYPE}, {@link STRUCTURE_TYPE}.
	 * @param typeName The name of the type definition.
	 * @param xIType Indicates whether the type is XI (external interface) type and contains model that 
	 * check the perfmissions to set the data into the field from the request parameters.
	 */
	public ContextFieldType(int type, String typeName, boolean xIType, String basicXITypeName)
	{
		this.type = type;
		this.typeName = typeName;
		this.xIType = xIType;
		this.basicXITypeName = basicXITypeName;
	}

	/**
	 * Returns the type of the field. 
	 * Can be one of the following: {@link CLASS_TYPE}, {@link STRUCTURE_TYPE} 
	 * @return int type
	 */
	public int getType()
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
		return xIType;
	}

	/**
	 * Returns the name of the type definition.
	 * @return String
	 */
	public String getTypeName()
	{
		return typeName;
	}

	/**
	 * Returns the name of the basicXItype definition
	 * @return String
	 */
	public String getBasicXITypeName()
	{
		return basicXITypeName;
	}

}