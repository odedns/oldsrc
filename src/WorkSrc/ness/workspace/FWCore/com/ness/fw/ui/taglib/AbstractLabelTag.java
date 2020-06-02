package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public abstract class AbstractLabelTag extends UITag
{
	protected String value;
	protected String defaultValue = "";
	protected String className;
	protected boolean decorated = true;
	protected abstract String getValue() throws UIException;
	
	protected void init() throws UIException
	{
		initState();
		initCss();
	}
	
	protected void initCss()
	{
		className = initUIProperty(className,"ui.label.className");
	}
		
	protected void renderStartTag() throws UIException
	{
		renderLabel();
	}
	
	protected void renderLabel() throws UIException
	{
		if (decorated)
		{
			startTag(SPAN);
			renderSetDirtyProperty();
			renderComponentWrapperStyle();
			addAttribute(CLASS,className);
			endTag();
		}
		append(getValue());
		if (decorated)
		{
			endTag(SPAN);
		}
	}
	
	protected void resetTagState()
	{
		value = null;
		id = null;
		defaultValue = "";
		className = null;
		decorated = true;
		super.resetTagState();		
	}
	
	protected void renderEndTag() throws UIException
	{	
		
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

	public void setValue(String value)
	{
		this.value = value;
	}
	/**
	 * @return
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * @param string
	 */
	public void setClassName(String string)
	{
		className = string;
	}

	/**
	 * @return
	 */
	public boolean isDecorated()
	{
		return decorated;
	}

	/**
	 * @param b
	 */
	public void setDecorated(boolean b)
	{
		decorated = b;
	}

}
