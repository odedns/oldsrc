package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.TextModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.data.TextTagData;
import com.ness.fw.ui.events.Event;

public abstract class AbstractTextTag extends AbstractInputTag
{
	protected TextModel textModel;
	protected String value;
	protected String defaultValue = "";
	protected String mask;
	protected int maxLength = 0;
	protected boolean autoSelect;
	protected String onChange;
	protected String onKeyPress;
	protected String readonly;
	protected String text;
	
	protected final static String SCRIPT_AUTO_SELECT = "this.createTextRange().select()"; 
	
	protected void initModel() throws UIException
	{
		textModel = (TextModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		
		if (textModel == null)
		{
			throw new UIException("no text model exists in context's field " + id);
		}
		
		//initialize the state of this text tag
		initState();
		
		if (isEventRendered(Event.EVENT_TYPE_READWRITE))
		{
			stateString = "";
		}
		else
		{
			stateString = DISABLED;
		}

		//initialize more properties for this tag
		if (textModel != null && textModel.getInputType() != null)
		{
			inputType = textModel.getInputType();
		}
		if (textModel != null && textModel.getMask() != null)
		{
			mask = textModel.getMask();
		}

		//search for errors
		if (!isErrorState())
		{
			if (textModel.getData() == null)
			{
				value = defaultValue;
			}
			else
			{
				value = FlowerUIUtil.getFormattedValueFromContext(getHttpRequest(),id,mask);
			}
		}
		
		value = getFormattedHtmlValue(value);
	}

	protected void setModelState()
	{
		if (textModel != null && textModel.getState() != null)
		{
			if (isStricterState(textModel.getState(),state))
			{
				state = textModel.getState();
			}
		}			
	}

	protected boolean isErrorState() throws UIException
	{
		if (state.equals(UIConstants.COMPONENT_DISABLED_STATE) || state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
		{
			return false;
		}
		else if (FlowerUIUtil.isSevereErrorField(getHttpRequest(),id))
		{
			isError = true;
			value = getHttpRequest().getParameter(id);
			if (value == null) value = "";			
			return true;
		}
		else if (FlowerUIUtil.isErrorField(getHttpRequest(),id))
		{
			isError = true;
			return false;
		}
		else
		{
			if (textModel != null)
			{
				isError = textModel.isError();
			}
			return false;
		}
	}
	
	protected void initAttributes()
	{
		if (tagData != null)
		{
			super.initAttributes();
			TextTagData textTagData = (TextTagData)tagData;
			if (textTagData.getDefaultValue() != null)
			{
				setDefaultValue(textTagData.getDefaultValue());
			}
			if (textTagData.getMask() != null)
			{
				setMask(textTagData.getMask());
			}
			if (textTagData.getMaxLength() != null)
			{
				setMaxLength(textTagData.getMaxLength().intValue());
			}
			if (textTagData.isAutoSelect() != null)
			{
				setAutoSelect(textTagData.isAutoSelect().booleanValue());
			}
			if (textTagData.getOnchange() != null)
			{
				setOnChange(textTagData.getOnchange());
			}
			if (textTagData.getOnkeypress() != null)
			{
				setOnKeyPress(textTagData.getOnkeypress());
			}
			if (textTagData.getReadOnly() != null)
			{
				setReadonly(textTagData.getReadOnly());
			}
			if (textTagData.getText() != null)
			{
				setText(textTagData.getText());
			}
		}
	}
	
	protected void resetTagState()
	{
		textModel = null;
		value = null;
		defaultValue = "";
		mask = null;
		maxLength = 0;
		autoSelect = false;
		onChange = null;
		onKeyPress = null;
		readonly = null;
		text = null;		
		super.resetTagState();
	}
	
	/**
	 * Returns the mask.
	 * @return String
	 */
	public String getMask()
	{
		return mask;
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
	 * Sets the mask.
	 * @param mask The mask to set
	 */
	public void setMask(String mask)
	{
		this.mask = mask;
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
	 * Returns the autoSelect.
	 * @return boolean
	 */
	public boolean isAutoSelect()
	{
		return autoSelect;
	}

	/**
	 * Sets the autoSelect.
	 * @param autoSelect The autoSelect to set
	 */
	public void setAutoSelect(boolean autoSelect)
	{
		this.autoSelect = autoSelect;
	}

	/**
	 * @param string
	 */
	public void setMaxLength(int maxLength)
	{
		this.maxLength = maxLength;
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

	/**
	 * @return
	 */
	public String getOnChange()
	{
		return onChange;
	}

	/**
	 * @param string
	 */
	public void setOnChange(String string)
	{
		onChange = string;
	}

	/**
	 * @return
	 */
	public String getReadonly()
	{
		return readonly;
	}

	/**
	 * @param string
	 */
	public void setReadonly(String string)
	{
		readonly = string;
	}

	/**
	 * @return
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param string
	 */
	public void setText(String string)
	{
		text = string;
	}

	/**
	 * @return
	 */
	public TextModel getTextModel()
	{
		return textModel;
	}

	/**
	 * @param model
	 */
	public void setTextModel(TextModel model)
	{
		textModel = model;
	}

	/**
	 * @return
	 */
	public String getOnKeyPress()
	{
		return onKeyPress;
	}

	/**
	 * @param string
	 */
	public void setOnKeyPress(String string)
	{
		onKeyPress = string;
	}	
}
