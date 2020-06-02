package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.TextModel;

public class VariableTag extends AbstractInputTag
{
	protected String value;
	protected String defaultValue = "";
	protected TextModel textModel;
	
	protected void initModel() throws UIException
	{
		if (value == null)
		{
			textModel = (TextModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);	
			if (textModel == null)
			{
				throw new UIException("TextModel is null in text tag with id " + id);
			}
			if (textModel.getData() == null)
			{
				value = defaultValue;			
			}
			else
			{
				value = FlowerUIUtil.getFormattedValueFromContext(getHttpRequest(),id);
			}
		}
	}

	protected void renderStartTag()
	{
		startTag(INPUT);
		addAttribute(TYPE,HIDDEN);
		addAttribute(ID,id);
		addAttribute(NAME,id);
		addAttribute(VALUE,value);
		endTag();
	}	

	protected void resetTagState()
	{
		defaultValue = "";
		value = null;
		super.resetTagState();
	}

	protected void renderEndTag()
	{
				
	}	
	
	/**
	 * Returns the value.
	 * @return String
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets the value.
	 * @param value The value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return
	 */
	public String getDefaultValue() 
	{
		return defaultValue;
	}

	/**
	 * @param string
	 */
	public void setDefaultValue(String string) 
	{
		defaultValue = string;
	}

	protected boolean isPreviewAllowed() throws UIException 
	{
		return true;
	}
}
