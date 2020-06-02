package com.ness.fw.ui.taglib;

import com.ness.fw.ui.ButtonModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.common.exceptions.UIException;

public abstract class AbstractButtonTag extends AbstractModelTag
{
	protected ButtonModel buttonModel; 
	protected String name;
	protected String value = "";
	protected String enabledClassName;
	protected String disabledClassName;	
	protected String tooltip;
	protected String imageSrc;
	protected String disableImageSrc;
	protected String onClick = "";
	protected String width;
	protected String height;
	protected String style;
	protected String confirmSubmit;
	protected String shortCutKey;
			
	protected void renderStartTag() throws UIException
	{
		initCss();
		renderLink();
		if (buttonModel != null)
		{
			buttonModel.setAuthLevel(getAuthLevel());
			renderHiddenField();
		} 
	}
		
	protected void initCss()
	{
		enabledClassName = initUIProperty(enabledClassName,"ui.button.enabled");
		disabledClassName = initUIProperty(disabledClassName,"ui.button.disabled");
		if (disableImageSrc == null && imageSrc != null)
		{
			disableImageSrc = getImageSrcSuffix(imageSrc,CSS_USE_DISABLED);
			//disableImageSrc = imageSrc + CSS_SUFFIX_DISABLED;
		}
	}
	
	/**
	 * renders the css style - including width and height
	 */
	protected void renderStyle()
	{
		if (style == null)
		{
			style = getWidthAndHeightAsAtyle();	
		}
		else
		{
			if (!style.endsWith(";"))
			{
				style += ";";
			}
			style += getWidthAndHeightAsAtyle();
		}
		if (state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
		{
			style += DISPLAY_NONE;
		}

		if (!style.equals(""))
		{
			addAttribute(STYLE,style);
		}
	}
	
	private String getWidthAndHeightAsAtyle()
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
	
	protected String getClassByState()
	{
		return (stateString.trim().equals(UIConstants.COMPONENT_DISABLED_STATE) ? disabledClassName : enabledClassName);
	}
		
	protected abstract void renderLink() throws UIException;

	protected void resetTagState()
	{
		buttonModel = null; 
		name = null;
		value = "";
		enabledClassName = null;
		disabledClassName = null;	
		tooltip = null;
		imageSrc = null;
		onClick = "";
		width = null;
		height = null;
		style = null;
		state = UIConstants.COMPONENT_ENABLED_STATE;
		confirmSubmit = null;	
		shortCutKey = null;	
		super.resetTagState();
	}

	protected void renderEndTag()
	{
		
	}
				
	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getValue()
	{
		return value;
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
	public void setValue(String string)
	{
		value = string;
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
	public String getImageSrc()
	{
		return imageSrc;
	}

	/**
	 * @param string
	 */
	public void setImageSrc(String string)
	{
		imageSrc = string;
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
		if (onClick != null && !onClick.equals("") && onClick.charAt(onClick.length() - 1) != ';')
		{
			onClick += ";";
		}
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
	public String getStyle()
	{
		return style;
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
	public void setStyle(String string)
	{
		style = string;
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
	public String getDisabledClassName()
	{
		return disabledClassName;
	}

	/**
	 * @return
	 */
	public String getEnabledClassName()
	{
		return enabledClassName;
	}

	/**
	 * @param string
	 */
	public void setDisabledClassName(String string)
	{
		disabledClassName = string;
	}

	/**
	 * @param string
	 */
	public void setEnabledClassName(String string)
	{
		enabledClassName = string;
	}

	/**
	 * @return
	 */
	public String getConfirmSubmit()
	{
		return confirmSubmit;
	}

	/**
	 * @param string
	 */
	public void setConfirmSubmit(String string)
	{
		confirmSubmit = string;
	}

	/**
	 * @return
	 */
	public ButtonModel getButtonModel()
	{
		return buttonModel;
	}

	/**
	 * @param model
	 */
	public void setButtonModel(ButtonModel model)
	{
		buttonModel = model;
	}

	/**
	 * @param string
	 */
	public void setDisableImageSrc(String string) 
	{
		disableImageSrc = string;
	}

	/**
	 * @return
	 */
	public String getShortCutKey() 
	{
		return shortCutKey;
	}

	/**
	 * @param string
	 */
	public void setShortCutKey(String string) 
	{
		shortCutKey = string;
	}

}
