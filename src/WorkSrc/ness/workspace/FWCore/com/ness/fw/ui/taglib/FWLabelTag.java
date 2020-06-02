package com.ness.fw.ui.taglib;

import java.lang.reflect.InvocationTargetException;

import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.data.LabelTagData;
import com.ness.fw.common.exceptions.UIException;

public class FWLabelTag extends AbstractLabelTag
{
	protected String mask;
	protected String methodName;
	
	protected void initAttributes()
	{
		super.initAttributes();
		if (tagData != null)
		{
			LabelTagData labelTagData = (LabelTagData)tagData;
			if (labelTagData != null)
			{
				if (labelTagData.getValue() != null)
				{
					setValue(labelTagData.getValue());
				}
				if (labelTagData.getDefaultValue() != null)
				{
					setDefaultValue(labelTagData.getDefaultValue());
				}
				if (labelTagData.getClassName() != null)
				{
					setClassName(labelTagData.getClassName());
				}
				if (labelTagData.isDecorated() != null)
				{
					setDecorated(labelTagData.isDecorated().booleanValue());
				}
				if (labelTagData.getMask() != null)
				{
					setMask(labelTagData.getMask());
				}
				if (labelTagData.getMethodName() != null)
				{
					setMethodName(labelTagData.getMethodName());
				}
			}
		}
	}
		
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.AbstractLabelTag#getValue()
	 */
	protected String getValue() throws UIException
	{
		if (mask != null && methodName != null)
		{
			throw new UIException("mask and methodName are not allowed together");
		}
		if (value == null)
		{
			//the methodName should be a name of a getter method in an object 
			//in the context
			if (methodName != null)
			{
				try 
				{
					Object obj = FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
					if (obj == null)
					{
						throw new UIException("object with id " + id + " in context is null and method " + methodName + " cannot be called");
					}
					Object methodResult = ((obj.getClass()).getMethod(methodName,new Class[0])).invoke(obj,new Object[0]); 
					if (methodResult == null)
					{
						return defaultValue;
					}
					else
					{
						return methodResult.toString();
					}
				} 
				catch (IllegalAccessException e) 
				{
					throw new UIException(e);
				} 
				catch (InvocationTargetException e) 
				{
					throw new UIException(e.getTargetException());
				} 
				catch (NoSuchMethodException e) 
				{
					throw new UIException(e);
				}
			}
			
			//if methodName is null,the value in the context should be
			//an xiType object
			else
			{				
				value = FlowerUIUtil.getFieldValueFromContext(getHttpRequest(),id,mask);
				return value != null ? value : defaultValue;	
			}
		}
		else
		{
			return value;		
		}
	}

	protected void resetTagState()
	{
		mask = null;
		methodName = null;		
		super.resetTagState();
	}

	/**
	 * @param string
	 */
	public void setMask(String string)
	{
		mask = string;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) 
	{
		methodName = string;
	}

}
