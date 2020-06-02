/*
 * Created on 20/07/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.data;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class InputTagData extends UITagData 
{
	private final static String ATTRIBUTE_DIRECTION = "direction";
	private final static String ATTRIBUTE_INPUP_TYPE = "inputType";
	private final static String ATTRIBUTE_FOCUS = "focus";
	private final static String ATTRIBUTE_ERROR = "error";
	private final static String ATTRIBUTE_TOOLTIP = "tooltip"; 
	private final static String ATTRIBUTE_WIDTH = "width";
	private final static String ATTRIBUTE_HEIGHT = "height";
	private final static String ATTRIBUTE_STYLE = "style";
	private final static String ATTRIBUTE_ONCLICK = "onclick"; 
	private final static String ATTRIBUTE_NORMAL_CLASS_NAME = "normalClassName";
	private final static String ATTRIBUTE_MANDATORY_CLASS_NAME = "mandatoryClassName";
	private final static String ATTRIBUTE_ERROR_CLASS_NAME = "errorClassName";
	private final static String ATTRIBUTE_DISABLED_CLASS_NAME = "disableClassName";
	
	public void setDirection(String direction)
	{
		setAttribute(ATTRIBUTE_DIRECTION,direction);
	}
	
	public String getDirection()
	{
		return getStringAttribute(ATTRIBUTE_DIRECTION);
	}
		
	public void setInputType(String inputType)
	{
		setAttribute(ATTRIBUTE_INPUP_TYPE,inputType);
	}
	
	public String getInputType()
	{
		return getStringAttribute(ATTRIBUTE_INPUP_TYPE);
	}
	
	public void setFocus(boolean focus)
	{
		setBooleanAttribute(ATTRIBUTE_FOCUS,focus);
	}
	
	public Boolean isFocus()
	{
		return getBooleanAttribute(ATTRIBUTE_FOCUS);
	}
	
	public void setError(boolean error)
	{
		setBooleanAttribute(ATTRIBUTE_ERROR,error);
	}
	
	public Boolean isError()
	{
		return getBooleanAttribute(ATTRIBUTE_ERROR);
	}	
	
	public void setTooltip(String tooltip)
	{
		setAttribute(ATTRIBUTE_TOOLTIP,tooltip);
	}
	
	public String getTooltip()
	{
		return getStringAttribute(ATTRIBUTE_TOOLTIP);
	}
	
	public void setWidth(String width)
	{
		setAttribute(ATTRIBUTE_WIDTH,width);
	}
	
	public String getWidth()
	{
		return getStringAttribute(ATTRIBUTE_WIDTH);
	}	
	
	public void setHeight(String height)
	{
		setAttribute(ATTRIBUTE_HEIGHT,height);
	}
	
	public String getHeight()
	{
		return getStringAttribute(ATTRIBUTE_HEIGHT);
	}	

	public void setStyle(String style)
	{
		setAttribute(ATTRIBUTE_STYLE,style);
	}
	
	public String getStyle()
	{
		return getStringAttribute(ATTRIBUTE_STYLE);
	}
	
	public void setOnclick(String onclick)
	{
		setAttribute(ATTRIBUTE_ONCLICK,onclick);
	}
	
	public String getOnclick()
	{
		return getStringAttribute(ATTRIBUTE_ONCLICK);
	}
	
	public void setNormalClassName(String normalClassName)
	{
		setAttribute(ATTRIBUTE_NORMAL_CLASS_NAME,normalClassName);
	}
	
	public String getNormalClassName()
	{
		return getStringAttribute(ATTRIBUTE_NORMAL_CLASS_NAME);
	}
	
	public void setMandatoryClassName(String mandatoryClassName)
	{
		setAttribute(ATTRIBUTE_MANDATORY_CLASS_NAME,mandatoryClassName);
	}
	
	public String getMandatoryClassName()
	{
		return getStringAttribute(ATTRIBUTE_MANDATORY_CLASS_NAME);
	}
	
	public void setErrorClassName(String errorClassName)
	{
		setAttribute(ATTRIBUTE_ERROR_CLASS_NAME,errorClassName);
	}
	
	public String getErrorClassName()
	{
		return getStringAttribute(ATTRIBUTE_ERROR_CLASS_NAME);
	}
	
	public void setDisableClassName(String disableClassName)
	{
		setAttribute(ATTRIBUTE_DISABLED_CLASS_NAME,disableClassName);
	}
	
	public String getDisableClassName()
	{
		return getStringAttribute(ATTRIBUTE_DISABLED_CLASS_NAME);
	}			
}
