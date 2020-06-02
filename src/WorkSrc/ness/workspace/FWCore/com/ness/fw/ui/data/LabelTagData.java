package com.ness.fw.ui.data;

import com.ness.fw.ui.UIConstants;

public class LabelTagData extends UITagData 
{
	public void setValue(String value)
	{
		setAttribute("value",value);
	}
	
	public String getValue()
	{
		return getStringAttribute("value");
	}
	
	public void setDefaultValue(String defaultValue)
	{
		setAttribute("defaultValue",defaultValue);
	}
	
	public String getDefaultValue()
	{
		return getStringAttribute("defaultValue");
	}	
	
	public void setClassName(String className)
	{
		setAttribute("className",className);
	}
	
	public String getClassName()
	{
		return getStringAttribute("className");
	}
	
	public void setDecorated(boolean decorated)
	{
		setBooleanAttribute("decorated",decorated);
	}
	
	public Boolean isDecorated()
	{
		return getBooleanAttribute("decorated");
	}
	
	public void setMask(String mask)
	{
		setAttribute("mask",mask);
	}
	
	public String getMask()
	{
		return getStringAttribute("mask");
	}
	
	public void setMethodName(String methodName)
	{
		setAttribute("methodName",methodName);
	}
	
	public String getMethodName()
	{
		return getStringAttribute("methodName");
	}
	

	public String getTagType() 
	{
		return UIConstants.TAG_TYPE_LABEL;
	}	
}
