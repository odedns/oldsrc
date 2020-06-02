package com.ness.fw.ui.taglib;

import java.util.Stack;

import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.data.InputTagData;
import com.ness.fw.common.exceptions.UIException;

public abstract class AbstractInputTag extends AbstractModelTag
{	
	private final static String JS_FUNCTION_SET_COMPONENT_FOCUS = "setComponentFocus";
	private final static String HTML_SET_FIELD_FOCUS = "setFocus";
	private final static String HTML_FIELD_ERROR = "error";
	private final static String HTML_FIELD_MANDATORY = "mandatory";
	protected final static String CSS_INPUT_PREFIX = "input";
	protected final static String ID_SUFFIX = "Id";
	protected final static String COMPLEX_COMPONENT_SUFFIX = "Wrapper";
	
	protected void initAttributes()
	{
		if (tagData != null)
		{
			super.initAttributes();
			InputTagData inputTagData = (InputTagData)tagData;
			if (inputTagData.getDirection() != null)
			{
				setDirection(inputTagData.getDirection());
			}
			if (inputTagData.getInputType() != null)
			{
				setInputType(inputTagData.getInputType());
			}			
			if (inputTagData.isFocus() != null)
			{
				setFocus(inputTagData.isFocus().booleanValue());
			}
			if (inputTagData.isError() != null)
			{
				setError(inputTagData.isError().booleanValue());
			}
			if (inputTagData.getTooltip() != null)
			{
				setTooltip(inputTagData.getTooltip());
			}
			if (inputTagData.getWidth() != null)
			{
				setWidth(inputTagData.getWidth());
			}
			if (inputTagData.getHeight() != null)
			{
				setHeight(inputTagData.getHeight());
			}
			if (inputTagData.getStyle() != null)
			{
				setStyle(inputTagData.getStyle());
			}
			if (inputTagData.getOnclick() != null)
			{
				setOnClick(inputTagData.getOnclick());
			}
			if (inputTagData.getNormalClassName() != null)
			{
				setNormalClassName(inputTagData.getNormalClassName());
			}
			if (inputTagData.getErrorClassName() != null)
			{
				setErrorClassName(inputTagData.getErrorClassName());
			}
			if (inputTagData.getDisableClassName() != null)
			{
				setDisableClassName(inputTagData.getDisableClassName());
			}
			if (inputTagData.getMandatoryClassName() != null)
			{
				setMandatoryClassName(inputTagData.getMandatoryClassName());
			}
		}
	}
	
	/**
	 * the direction of the text in the component 
	 */	
	protected String direction;
		
	/**
	 * The inputType of the input tag,which may be on the following constants:<br>
	 * UIConstants.COMPONENT_NORMAL_INPUT_TYPE(default)<br>
	 * UIConstants.COMPONENT_MANDATORY_INPUT_TYPE
	 */
	protected String inputType = UIConstants.COMPONENT_NORMAL_INPUT_TYPE;
	
	/**
	 * If true - there is error in the component
	 */
	protected boolean isError = false;
	
	/**
	 * the tooltip of the input tag
	 */
	protected String tooltip;

	/**
	 * the width of the input tag
	 */
	protected String width;
	
	/**
	 * the height of the input tag
	 */
	protected String height;

	/**
	 * the css style of the input tag 
	 */
	protected String style;
	
	/**
	 * class name for normal state
	 */
	protected String normalClassName;

	/**
	 * class name for mandatory state
	 */
	protected String mandatoryClassName;

	/**
	 * class name for error state
	 */
	protected String errorClassName;

	/**
	 * class name for disabled state
	 */
	protected String disableClassName;

	/**
	 * onclick event script 
	 */
	protected String onClick = "";
	
	protected boolean focus = false;

	protected void init() throws UIException
	{
		initDirtyContainer();
		initModel();
		initCss();
	}
		
	/**
	 * Initializes css class names
	 */
	protected void initCss()
	{
		normalClassName = initUIProperty(normalClassName,getDefaultCssPrefix() + getUIProperty("ui.inputField.normal.suffix"));
		mandatoryClassName = initUIProperty(mandatoryClassName,getDefaultCssPrefix() + getUIProperty("ui.inputField.mandatory.suffix"));
		errorClassName = initUIProperty(errorClassName,getDefaultCssPrefix() + getUIProperty("ui.inputField.error.suffix"));
		disableClassName = initUIProperty(disableClassName,getDefaultCssPrefix() + getUIProperty("ui.inputField.disable.suffix"));
	}
	
	protected void validateAttributes() throws UIException
	{
		super.validateAttributes();
		if (!inputType.equals(UIConstants.COMPONENT_NORMAL_INPUT_TYPE) && !inputType.equals(UIConstants.COMPONENT_MANDATORY_INPUT_TYPE))
		{
			throw new UIException("the inputType attribute of tag with id " + id + " cannot be set to " + inputType);
		}	
	}
		
	/**
	 * Initializes the id of the dirty model that contains this component,if one
	 * exists.
	 */	
	protected void initDirtyContainer() throws UIException 
	{	
		Stack dirtyStack = (Stack)getRequestContextValue(REQUEST_ATTRIBUTE_DIRTY);
		if (dirtyStack != null)
		{
			dirtyModelId = (String)dirtyStack.peek();	
		}
		if (getRequestContextValue(REQUEST_ATTRIBUTE_DIRTABLE) != null)
		{
			dirtable = ((Boolean)getRequestContextValue(REQUEST_ATTRIBUTE_DIRTABLE)).booleanValue();
		}
	}
	
	protected String getComponentId()
	{
		return id + ID_SUFFIX;
	}
	
	/**
	 * returns the tag css prefix
	 * @return the tag css prefix 
	 */
	protected String getDefaultCssPrefix()
	{
		return CSS_INPUT_PREFIX;
	}	
	
	/**
	 * Renders the class attribute of the input depending on its state
	 */
	protected void renderClassByState()
	{
		if (isError)
		{
			addAttribute(CLASS,errorClassName);
		}
		else
		{
			if (isDisabled())
			{
				addAttribute(CLASS,disableClassName);
			}
			else if (inputType.equals(UIConstants.COMPONENT_MANDATORY_INPUT_TYPE))
			{
				addAttribute(CLASS,mandatoryClassName);
			}
			else
			{
				addAttribute(CLASS,normalClassName);
			}
		}
	}
	
	/**
	 * Renders the css style - including width and height
	 */
	protected void renderStyle(boolean renderHidden)
	{
		if (style == null)
		{
			style = getWidthAndHeightAsAtyle();	
		}
		else
		{
			style += getWidthAndHeightAsAtyle();
		}
		if (renderHidden && state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
		{
			style += DISPLAY_NONE + ";";
		}		
		if (!style.equals(""))
		{
			addAttribute(STYLE,style);
		}
	}
	
	protected String getWidthAndHeightAsAtyle()
	{
		String style = "";
		if (width != null)
		{
			style += WIDTH + ":" + width + ";";
		}
		if (height != null)
		{
			style += HEIGHT + ":" + height + ";";
		}
		return style; 
	}
	
	protected void renderInputTypeIndication()
	{
		if (isError())
		{
			append(BLANK + HTML_FIELD_ERROR);
		}	
		else if (inputType.equals(UIConstants.COMPONENT_MANDATORY_INPUT_TYPE))
		{
			append(BLANK + HTML_FIELD_MANDATORY);
		}
	}
	
	protected void renderEnableNotAuthorizedIndication()
	{
		append(ENABLE_NOT_AUTHORIZED);
	}
	
	protected void renderOnClick()
	{
		if (!onClick.equals(""))
		{
			addAttribute(ONCLICK,onClick);
		}
	}
	
	protected void renderFieldFocus()
	{
		if (focus)
		{
			addAttribute(HTML_SET_FIELD_FOCUS,String.valueOf(true));		
		}
	}
					
	protected void resetTagState()
	{
		direction = null;
		state = UIConstants.COMPONENT_ENABLED_STATE;
		inputType = UIConstants.COMPONENT_NORMAL_INPUT_TYPE;		
		isError = false;
		tooltip = null;
		width = null;
		height = null;
		style = null;
		normalClassName = null;
		mandatoryClassName = null;
		errorClassName = null;
		disableClassName = null;
		onClick = "";
		focus = false;
		dirtable = true;
		super.resetTagState();
	}

	/**
	 * Returns the normalClassName.
	 * @return String
	 */
	public String getNormalClassName()
	{
		return normalClassName;
	}

	/**
	 * Returns the disableClassName.
	 * @return String
	 */
	public String getDisableClassName()
	{
		return disableClassName;
	}

	/**
	 * Returns the errorClassName.
	 * @return String
	 */
	public String getErrorClassName()
	{
		return errorClassName;
	}

	/**
	 * Sets the normalClassName.
	 * @param normalClassName The normalClassName to set
	 */
	public void setNormalClassName(String defaultClassName)
	{
		this.normalClassName = defaultClassName;
	}

	/**
	 * Sets the disableClassName.
	 * @param disableClassName The disableClassName to set
	 */
	public void setDisableClassName(String disableClassName)
	{
		this.disableClassName = disableClassName;
	}

	/**
	 * Sets the errorClassName.
	 * @param errorClassName The errorClassName to set
	 */
	public void setErrorClassName(String errorClassName)
	{
		this.errorClassName = errorClassName;
	}

	/**
	 * Returns the direction.
	 * @return String
	 */
	public String getDirection()
	{
		return direction;
	}

	/**
	 * Sets the direction.
	 * @param direction The direction to set
	 */
	public void setDirection(String dir)
	{
		this.direction = dir;
	}

	/**
	 * Returns the madatoryClassName.
	 * @return String
	 */
	public String getMandatoryClassName()
	{
		return mandatoryClassName;
	}

	/**
	 * Sets the madatoryClassName.
	 * @param madatoryClassName The madatoryClassName to set
	 */
	public void setMandatoryClassName(String mandatoryClassName)
	{
		this.mandatoryClassName = mandatoryClassName;
	}

	/**
	 * @return
	 */
	public String getTooltip()
	{
		return tooltip;
	}

	/**
	 * @param string
	 */
	public void setTooltip(String string)
	{
		tooltip = string;
	}

	/**
	 * @return
	 */
	public String getOnClick()
	{
		return onClick;
	}

	/**
	 * @param string
	 */
	public void setOnClick(String onClick)
	{
		this.onClick = onClick;
	}

	/**
	 * @return
	 */
	public String getHeight()
	{
		return height;
	}

	/**
	 * @return
	 */
	public String getWidth()
	{
		return width;
	}

	/**
	 * @param string
	 */
	public void setHeight(String string)
	{
		height = string;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string)
	{
		width = string;
	}

	/**
	 * @return
	 */
	public String getStyle()
	{
		return style;
	}

	/**
	 * @param string
	 */
	public void setStyle(String string)
	{
		style = string;
	}

	/**
	 * @return
	 */
	public boolean isError()
	{
		return isError;
	}

	/**
	 * @param b
	 */
	public void setError(boolean b)
	{
		isError = b;
	}

	/**
	 * @param b
	 */
	public void setFocus(boolean b) {
		focus = b;
	}

	/**
	 * @return
	 */
	public String getInputType() 
	{
		return inputType;
	}

	/**
	 * @param string
	 */
	public void setInputType(String string) 
	{
		inputType = string;
	}

}
