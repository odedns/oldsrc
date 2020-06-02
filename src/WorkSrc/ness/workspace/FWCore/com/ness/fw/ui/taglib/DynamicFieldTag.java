package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.data.TagData;

public class DynamicFieldTag extends UITag 
{
	private String tagType;
	private TagData tagData;
	
	protected void init() throws UIException
	{
		id = (String)pageContext.getAttribute(DynamicFieldIterationTag.DYNAMIC_FIELD_LIST_TAG_CURRENT_ID);
		tagData = (TagData)pageContext.getAttribute(DynamicFieldIterationTag.DYNAMIC_FIELD_LIST_TAG_CURRENT_TAG_DATA);
	}
	
	protected void renderStartTag() throws UIException 
	{
		renderTag(UITagFactory.createUITag(id,tagData));	
	}

	protected void renderEndTag() throws UIException 
	{
		
	}

}
