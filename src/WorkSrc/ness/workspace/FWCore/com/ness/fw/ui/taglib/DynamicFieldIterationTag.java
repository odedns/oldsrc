package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.DynamicFieldList;

public class DynamicFieldIterationTag extends IterationUITag 
{
	private DynamicFieldList dynamicFieldList;

	protected final static String DYNAMIC_FIELD_LIST_TAG_CURRENT_LABEL = "currentCaption";
	protected final static String DYNAMIC_FIELD_LIST_TAG_CURRENT_ID = "currentId";		
	protected final static String DYNAMIC_FIELD_LIST_TAG_CURRENT_TAG_DATA = "currentTagData";

	private int limit = 0;
	private int current = 0;
	
	public DynamicFieldIterationTag()
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
	}
	
	protected void init() throws UIException
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		if (dynamicFieldList == null)
		{
			dynamicFieldList = (DynamicFieldList)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
			if (dynamicFieldList == null)
			{
				throw new UIException("no dynmaic fields list exists in context's field " + id);
			}		
			limit = dynamicFieldList.getFieldCount();	
		}
	}
	
	protected void renderStartTag() throws UIException 
	{
		if (limit > 0)
		{
			renderIterationBody();
		}
		else
		{
			startTagReturnValue = SKIP_BODY;
		}
	}

	private void renderIterationBody() throws UIException
	{
		String currentLabel = dynamicFieldList.getFieldLabel(current);
		if (currentLabel != null)
		{
			pageContext.setAttribute(DYNAMIC_FIELD_LIST_TAG_CURRENT_LABEL,dynamicFieldList.getFieldLabel(current));
		}
		else
		{
			pageContext.removeAttribute(DYNAMIC_FIELD_LIST_TAG_CURRENT_LABEL);
		}
		pageContext.setAttribute(DYNAMIC_FIELD_LIST_TAG_CURRENT_ID,dynamicFieldList.getFieldId(current));
		pageContext.setAttribute(DYNAMIC_FIELD_LIST_TAG_CURRENT_TAG_DATA,dynamicFieldList.getFieldTagData(current));
	}

	protected void renderBeforeEndTag() throws UIException
	{
		current++;		
		if (current == limit)
		{
			afterBodyReturnValue = SKIP_BODY;
		}
		else
		{
			renderIterationBody();
		}
	}
	
	protected void resetTagState()
	{
		dynamicFieldList = null;
		limit = 0;
		current = 0;
		super.resetTagState();
	}
	
	protected void renderEndTag() throws UIException 
	{
	}
}
