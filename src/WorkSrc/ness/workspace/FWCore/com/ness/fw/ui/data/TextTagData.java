package com.ness.fw.ui.data;

public abstract class TextTagData extends InputTagData 
{
	public void setDefaultValue(String defaultValue)
	{
		setAttribute("defaultValue",defaultValue);
	}
	
	public String getDefaultValue()
	{
		return getStringAttribute("defaultValue");
	}	
	
	public void setMask(String mask)
	{
		setAttribute("mask",mask);
	}

	public String getMask()
	{
		return getStringAttribute("mask");
	}
	
	public void setMaxLength(int maxLength)
	{
		setIntAttribute("maxLength",maxLength);
	}
	
	public Integer getMaxLength()
	{
		return getIntegerAttribute("maxLength");
	}
	
	public void setAutoSelect(boolean autoSelect)
	{
		setBooleanAttribute("autoSelect",autoSelect);
	}
	
	public Boolean isAutoSelect()
	{
		return getBooleanAttribute("autoSelect");
	}
	
	public void setOnchange(String onchange)
	{
		setAttribute("onchange",onchange);
	}
	
	public String getOnchange()
	{
		return getStringAttribute("onchange");
	}
	
	public void setOnkeypress(String keypress)
	{
		setAttribute("keypress",keypress);
	}
	
	public String getOnkeypress()
	{
		return getStringAttribute("keypress");
	}	
	
	public void setReadOnly(String readonly)
	{
		setAttribute("readonly",readonly);
	}
	
	public String getReadOnly()
	{
		return getStringAttribute("readonly");
	}
	
	public void setText(String text)
	{
		setAttribute("text",text);
	}
	
	public String getText()
	{
		return getStringAttribute("text");
	}
}
