package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public class DynamicFieldLabelTag extends AbstractLabelTag 
{
	protected void initCss()
	{
		className = initUIProperty(className,"ui.caption.className");
	}
		
	protected String getValue() throws UIException
	{
		String label = (String)pageContext.getAttribute(DynamicFieldIterationTag.DYNAMIC_FIELD_LIST_TAG_CURRENT_LABEL);
		if (label != null)
		{
			return (!label.equals("") ? label : SPACE);
		}
		else
		{
			return "";				
		}
	}
}
