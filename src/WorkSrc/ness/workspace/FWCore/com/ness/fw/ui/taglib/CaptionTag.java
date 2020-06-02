package com.ness.fw.ui.taglib;

import com.ness.fw.ui.UIConstants;
import com.ness.fw.common.exceptions.UIException;

public class CaptionTag extends AbstractLabelTag
{	
	protected String name;
	protected String suffix = "";
	protected String fieldType = UIConstants.CAPTION_FIELD_TYPE_PROPERTIES;
	
	protected void initCss()
	{
		className = initUIProperty(className,"ui.caption.className");
	}
		
	protected String getValue() throws UIException
	{
		if (value == null)
		{
			if (fieldType.equals(UIConstants.CAPTION_FIELD_TYPE_PROPERTIES))
			{	
				value = getLocalizedText(name);
			}
			else
			{
				value = (String)pageContext.getAttribute(name);
				if (pageContext.getAttribute(name) == null)
				{
					throw new UIException("attribute " + name + " is null in page scope");
				}
				else
				{
					if (value.equals(""))
					{
						return SPACE;
					}
					else
					{
						value = getLocalizedText((String)pageContext.getAttribute(name));
					}
				}
			}
		}
		return value + suffix;
	}	
	
	/**
	 * @param string
	 */
	public void setName(String string) 
	{
		name = string;
	}

	/**
	 * @param string
	 */
	public void setSuffix(String string) 
	{
		suffix = string;
	}

	/**
	 * @param string
	 */
	public void setFieldType(String fieldType) 
	{
		this.fieldType = fieldType;
	}

}
