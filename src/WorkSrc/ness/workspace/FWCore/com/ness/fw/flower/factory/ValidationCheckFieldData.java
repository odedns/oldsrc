/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationCheckFieldData.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

/**
 * com.ness.fw.flower.factory
 * ${CLASS_NAME}
 */
public class ValidationCheckFieldData
{
	private String fieldName;
	private String caption;
	private boolean setFieldErrorState;

	public ValidationCheckFieldData(String fieldName, String caption, boolean setFieldErrorState)
	{
		this.fieldName = fieldName;
		this.caption = caption;
		this.setFieldErrorState = setFieldErrorState;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public String getCaption()
	{
		return caption == null ? fieldName : caption;
	}

	public boolean isSetErrorStateToField()
	{
		return setFieldErrorState;
	}
}
