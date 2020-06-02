
package com.ness.fw.ui.data;

import com.ness.fw.ui.UIConstants;

public class TextFieldTagData extends TextTagData 
{
	public void setTextType(String textType)
	{
		setAttribute("textType",textType);
	}
	
	public String getTextType()
	{
		return getStringAttribute("textType");
	}
	
	public void setSize(int size)
	{
		setIntAttribute("size",size);
	}
	
	public Integer getSize()
	{
		return getIntegerAttribute("size");
	}
	
	public void setDateDialogParams(String dateDialogParams)
	{
		setAttribute("dateDialogParams",dateDialogParams);
	}
	
	public String getDateDialogParams()
	{
		return getStringAttribute("dateDialogParams");
	}
	
	public void setDateButtonClassName(String dateButtonClassName)
	{
		setAttribute("dateButtonClassName",dateButtonClassName);
	}
	
	public String getDateButtonClassName()
	{
		return getStringAttribute("dateButtonClassName");
	}
	
	public void setDateButtonTitle(String dateButtonTitle)
	{
		setAttribute("dateButtonTitle",dateButtonTitle);
	}
	
	public String getDateButtonTitle()
	{
		return getStringAttribute("dateButtonTitle");
	}	
	
	public void setDateButtonImage(String dateButtonImage)
	{
		setAttribute("dateButtonImage",dateButtonImage);
	}
	
	public String getDateButtonImage()
	{
		return getStringAttribute("dateButtonImage");
	}
	
	public void setDateDisabledButtonImage(String dateDisabledButtonImage)
	{
		setAttribute("dateDisabledButtonImage",dateDisabledButtonImage);
	}
	
	public String getDateDisabledButtonImage()
	{
		return getStringAttribute("dateDisabledButtonImage");
	}
	
	public void setDateButtonTooltip(String dateButtonTooltip)
	{
		setAttribute("dateButtonTooltip",dateButtonTooltip);
	}
	
	public String getDateButtonTooltip()
	{
		return getStringAttribute("dateButtonTooltip");
	}	
	
	public String getTagType() 
	{
		return UIConstants.TAG_TYPE_TEXT_FIELD;
	}			
}
