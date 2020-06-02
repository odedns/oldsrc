package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.data.TextFieldTagData;
import com.ness.fw.common.exceptions.UIException;

public class TextFieldTag extends AbstractTextTag
{
	protected static final String JS_SELECT_DATE = "openCalendarPopup";
	protected static final String JS_SELECT_MONTH_DATE = "openMonthCalendarPopup";
	protected static final String JS_FORMAT_DATE = "return formatDate";
	protected static final String JS_VALIDATE_DATE = "validateDate";
	protected static final String JS_VALIDATE_DATE_ONPASTE = "return validateDateOnPaste";			
	protected static final String JS_FORMAT_NUMERIC = "return formatNumeric";
	protected static final String JS_VALIDATE_NUMERIC = "validateNumeric";	
	protected static final String JS_VALIDATE_NUMERIC_ONPASTE = "return validateNumericOnPaste";
	protected static final String JS_VALIDATE_REG_EXP = "validateRegExp";
	protected final static String TEXT_DAYS_LABELS_ARRAY_SUFFIX = "Days";
	protected final static String TEXT_MONTHS_LABELS_ARRAY_SUFFIX = "Months";
	protected final static String TEXT_DEFAULT_DATE_DIALOG_PARAMS = "dialogWidth:300px;dialogHeight:250px;";
	
	private final static String PROPERTY_KEY_CALENDAR_BUTTON = "ui.textField.calendarButton";
	private final static String PROPERTY_KEY_CALENDAR_BUTTON_TITLE = "ui.textField.dateButton";
	private final static String PROPERTY_KEY_CALENDAR_BUTTON_TOOLTIP = "ui.textField.dateButton.tooltip";
	private final static String PROPERTY_KEY_CALENDAR_BUTTON_IMAGE = "ui.textField.dateImage";
	private final static String PROPERTY_KEY_CALENDAR_BUTTON_IMAGE_DISABLED = "ui.textField.dateImage.disabled";
	private final static String PROPERTY_KEY_VALIDATE_DATE_MESSAGE = "ui.textField.validate.dateMessage";
	private final static String PROPERTY_KEY_VALIDATE_REGEXP_MESSAGE = "ui.textField.validate.regExp";
	private final static String PROPERTY_KEY_VALIDATE_NUMERIC_MESSAGE = "ui.textField.validate.numericMessage";
	private final static String PROPERTY_KEY_CSS_PREFIX = "ui.textField.prefix";
	
	protected String textType = UIConstants.TEXT_FIELD_TYPE_DEFAULT;
	protected String dateDialogParams = TEXT_DEFAULT_DATE_DIALOG_PARAMS;
	protected String dateButtonClassName;
	protected String dateButtonTitle;
	protected String dateButtonImage;
	protected String dateDisabledButtonImage;
	protected String dateButtonTooltip;
	protected int size = 0;
	protected String regExpPattern;
	
	public TextFieldTag()
	{
		dirtable = true;
	}
	
	/**
	 * returns the tag css prefix
	 * @return the tag css prefix 
	 */
	protected String getDefaultCssPrefix()
	{		
		return getUIProperty(PROPERTY_KEY_CSS_PREFIX);
	}
	
	protected void initAttributes()
	{
		super.initAttributes();
		TextFieldTagData  textFieldTagData = (TextFieldTagData)tagData;	
		if (textFieldTagData != null)
		{
			if (textFieldTagData.getTextType() != null)
			{
				setTextType(textFieldTagData.getTextType());
			}
			if (textFieldTagData.getSize() != null)
			{
				setSize(textFieldTagData.getSize().intValue());
			}
			if (textFieldTagData.getDateDialogParams() != null)
			{
				setDateDialogParams(textFieldTagData.getDateDialogParams());
			}
			if (textFieldTagData.getDateButtonClassName() != null)
			{
				setDateButtonClassName(textFieldTagData.getDateButtonClassName());
			}
			if (textFieldTagData.getDateButtonTitle() != null)
			{
				setDateButtonTitle(textFieldTagData.getDateButtonTitle());
			}	
			if (textFieldTagData.getDateButtonImage() != null)
			{
				setDateButtonImage(textFieldTagData.getDateButtonImage());
			}
			if (textFieldTagData.getDateDisabledButtonImage() != null)
			{
				setDateDisabledButtonImage(textFieldTagData.getDateDisabledButtonImage());
			}
			if (textFieldTagData.getDateButtonTooltip() != null)
			{
				setDateButtonTooltip(textFieldTagData.getDateButtonTooltip());
			}													
		}
	}
	protected void initCss()
	{
		super.initCss();
		dateButtonClassName = initUIProperty(dateButtonClassName,PROPERTY_KEY_CALENDAR_BUTTON);
		dateButtonImage = initUIProperty(dateButtonImage,PROPERTY_KEY_CALENDAR_BUTTON_IMAGE);
		dateDisabledButtonImage = initUIProperty(dateDisabledButtonImage,PROPERTY_KEY_CALENDAR_BUTTON_IMAGE_DISABLED);
	}
	
	protected void renderStartTag() throws UIException
	{
		dateButtonTitle = dateButtonTitle == null ? PROPERTY_KEY_CALENDAR_BUTTON_TITLE : dateButtonTitle;
		dateButtonTooltip = dateButtonTooltip == null ? PROPERTY_KEY_CALENDAR_BUTTON_TOOLTIP : dateButtonTooltip;
		if (isDateType())
		{
			renderDateField();
		}
		else
		{
			renderTextField();
		}
		if (textModel != null)
		{
			textModel.setAuthLevel(getAuthLevel());
		}
	}	
	
	protected void renderDateField() throws UIException
	{
		startTag(TABLE);
		renderComponentWrapperStyle();
		addAttribute(ID,id + COMPLEX_COMPONENT_SUFFIX);
		addAttribute(CELLSPACING,"0");
		addAttribute(CELLPADDING,"0");
		if (!allowClientEnabling)
		{
			append(ENABLE_NOT_AUTHORIZED);
		}		
		endTag();
		startTag(ROW,true);
		startTag(CELL,true);
		renderTextField();
		endTag(CELL);
		startTag(CELL);
		addAttribute(WIDTH,"1");
		endTag();
		append(SPACE);
		endTag(CELL);
		startTag(CELL);
		addAttribute(VALIGN,TOP);
		endTag();
		renderDateFieldButton();
		endTag(CELL);
		endTag(ROW);
		endTag(TABLE);
	}
	
	protected void renderDateFieldButton()
	{		
		String jsDateFunction = textType.equals(UIConstants.TEXT_FIELD_TYPE_DATE) ? JS_SELECT_DATE : JS_SELECT_MONTH_DATE;
		if (!isDisabled() && dateButtonImage != null || isDisabled() && dateDisabledButtonImage != null)
		{
			startTag(IMAGE);
			if (!isDisabled())
			{
				addAttribute(SRC,getLocalizedImagesDir() + dateButtonImage);
				addAttribute(STYLE,CURSOR_HAND);
				addAttribute(ONCLICK,getFunctionCall(jsDateFunction,id + COMMA + THIS,false));	
			}
			else
			{
				addAttribute(SRC,getLocalizedImagesDir() + dateDisabledButtonImage);
				addAttribute(ONCLICK,getFunctionCall(jsDateFunction,id + COMMA + THIS,false));	
			}
			if (dateButtonTooltip != null)
			{
				addAttribute(ALT,getLocalizedText(dateButtonTooltip));
			}
		}
		else
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,getLocalizedText(dateButtonTitle));
			addAttribute(CLASS,dateButtonClassName);
			addAttribute(ONCLICK,getFunctionCall(jsDateFunction,id + COMMA + THIS,false));
			addAttribute(STYLE,CURSOR_HAND);
			if (dateButtonTooltip != null)
			{
				addAttribute(TITLE,dateButtonTooltip);
			}
		}	
		append(stateString);	
		endTag();
	}
	
	protected void renderTextField() throws UIException 
	{
		startTag(INPUT);
		if (textType.equals(UIConstants.TEXT_FIELD_TYPE_PASSWORD))
		{
			addAttribute(TYPE,HTML_INPUT_TYPE_PASSWORD);
		}
		else
		{
			addAttribute(TYPE,TEXT);
		}
		addAttribute(ID,id);
		addAttribute(NAME,id);
		renderStyle(!isDateType());
		addAttribute(VALUE,getValue());
		renderClassByState();
		if (maxLength != 0)
		{
			addAttribute(MAX_LENGTH,String.valueOf(maxLength));
		}
		if (size != 0)
		{
			addAttribute(SIZE,String.valueOf(size));
		}
		if (autoSelect)
		{
			addAttribute(ONFOCUS,SCRIPT_AUTO_SELECT);
		}
		if (onKeyPress != null)
		{
			addAttribute(ONPROPERTYCHANGE);
			append(QUOT);
			append(onKeyPress);
			append(JS_END_OF_LINE);
			append(getSetDirtyFunction());
			append(QUOT);
		}
		else
		{
			renderSetDirtyProperty();
		}
		renderFormat();
		renderValidate();
		if (readonly != null)
		{
			addAttribute(READONLY,readonly);
		}
		else 
		{
			append(stateString);
		}
		if (text != null)
		{
			append(" " + text);
		}
		if (tooltip != null)
		{
			addAttribute(TITLE,getLocalizedText(tooltip));
		}		
		if (!isDateType() && !allowClientEnabling)
		{
			append(ENABLE_NOT_AUTHORIZED);
		}	
		renderFieldFocus();	
		renderInputTypeIndication();
		endTag();		
	}

	private void renderValidate() throws UIException 
	{
		if (textModel.getRegExpPattern() != null)
		{
			regExpPattern = textModel.getRegExpPattern();
		}
		String onChangeStr = "";
		String onChangeFunction = null;
		ArrayList onChangeParams = new ArrayList();
		
		String onPasteStr = "";
		String onPasteFunction = null;
		ArrayList onPasteParams = new ArrayList();
		
		//date type
		if (textType.equals(UIConstants.TEXT_FIELD_TYPE_DATE) || textType.equals(UIConstants.TEXT_FIELD_TYPE_MONTH_DATE))
		{
			onChangeFunction = JS_VALIDATE_DATE;
			onChangeParams.add(THIS);
			onChangeParams.add(getSingleQuot(getLocalizedText(PROPERTY_KEY_VALIDATE_DATE_MESSAGE)));
			onChangeParams.add(String.valueOf(textType.equals(UIConstants.TEXT_FIELD_TYPE_MONTH_DATE)));
			onChangeParams.add(getSingleQuot(regExpPattern));
			onChangeParams.add(getSingleQuot(getLocalizedText(PROPERTY_KEY_VALIDATE_REGEXP_MESSAGE)));
			
			onPasteFunction = JS_VALIDATE_DATE_ONPASTE;
			onPasteParams.add(String.valueOf(textType.equals(UIConstants.TEXT_FIELD_TYPE_MONTH_DATE)));
		}
		//numeric type 
		else if (textType.equals(UIConstants.TEXT_FIELD_TYPE_NUMERIC) || textType.equals(UIConstants.TEXT_FIELD_TYPE_INTEGER))
		{
			onChangeFunction = JS_VALIDATE_NUMERIC;
			onChangeParams.add(THIS);
			onChangeParams.add(String.valueOf(textType.equals(UIConstants.TEXT_FIELD_TYPE_INTEGER)));
			onChangeParams.add(getSingleQuot(getLocalizedText(PROPERTY_KEY_VALIDATE_NUMERIC_MESSAGE)));
			onChangeParams.add(getSingleQuot(regExpPattern));
			onChangeParams.add(getSingleQuot(getLocalizedText(PROPERTY_KEY_VALIDATE_REGEXP_MESSAGE)));
			
			onPasteFunction = JS_VALIDATE_NUMERIC_ONPASTE;
			onPasteParams.add(String.valueOf(textType.equals(UIConstants.TEXT_FIELD_TYPE_INTEGER)));
		}
		//regular text field
		else if (regExpPattern != null)
		{
			onChangeFunction = JS_VALIDATE_REG_EXP;
			onChangeParams.add(getSingleQuot(regExpPattern));
			onChangeParams.add(getSingleQuot(getLocalizedText(PROPERTY_KEY_VALIDATE_REGEXP_MESSAGE)));		
		}
		
		if (onChangeFunction != null)
		{
			onChangeStr = getFunctionCall(onChangeFunction,onChangeParams,false);
		}
		
		if (onPasteFunction != null)
		{
			onPasteStr = getFunctionCall(onPasteFunction,onPasteParams,false);
		}		
		
		if (onChange != null)
		{
			onChange = onChangeStr + onChange;
		}
		else
		{
			onChange = onChangeStr;
		}
		
		if (onChange != null && !onChange.equals(""))
		{
			addAttribute(ONCHANGE,onChange);
		}		
		if (!onPasteStr.equals(""))
		{
			addAttribute(ONPASTE,onPasteStr);
		}
	}
	
	private void renderFormat()
	{
		String onKeypressStr = null;
		if (textType.equals(UIConstants.TEXT_FIELD_TYPE_DATE)|| textType.equals(UIConstants.TEXT_FIELD_TYPE_MONTH_DATE))
		{
			onKeypressStr = getFunctionCall(JS_FORMAT_DATE,THIS,false) + JS_END_OF_LINE;
		}
		else if (textType.equals(UIConstants.TEXT_FIELD_TYPE_NUMERIC) || textType.equals(UIConstants.TEXT_FIELD_TYPE_INTEGER))
		{
			onKeypressStr = getFunctionCall(JS_FORMAT_NUMERIC,THIS + COMMA + (textType.equals(UIConstants.TEXT_FIELD_TYPE_INTEGER)),false) + JS_END_OF_LINE;
		}	
		if (onKeypressStr != null)
		{
			addAttribute(ONKEYPRESS,onKeypressStr);	
		}
	}
	
	private boolean isDateType()
	{
		return textType.equals(UIConstants.TEXT_FIELD_TYPE_DATE) || textType.equals(UIConstants.TEXT_FIELD_TYPE_MONTH_DATE);
	}

	protected void resetTagState()
	{
		textType = UIConstants.TEXT_FIELD_TYPE_DEFAULT;
		dateDialogParams = TEXT_DEFAULT_DATE_DIALOG_PARAMS;
		dateButtonClassName = null;
		dateButtonTitle = null;
		dateButtonImage = null;
		dateDisabledButtonImage = null;
		dateButtonTooltip = null;
		size = 0;		
		regExpPattern = null;
		super.resetTagState();
	}
	
	protected void renderEndTag() throws UIException
	{
	}	
	

	/**
	 * Returns the type.
	 * @return String
	 */
	public String getTextType()
	{
		return textType;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setTextType(String textType)
	{
		this.textType = textType;
	}

	/**
	 * @return
	 */
	public String getDateDialogParams()
	{
		return dateDialogParams;
	}

	/**
	 * @param string
	 */
	public void setDateDialogParams(String string)
	{
		dateDialogParams = string;
	}

	/**
	 * @return
	 */
	public String getDateButtonTitle()
	{
		return dateButtonTitle;
	}

	/**
	 * @param string
	 */
	public void setDateButtonTitle(String string)
	{
		dateButtonTitle = string;
	}

	/**
	 * @return
	 */
	public String getDateButtonImage()
	{
		return dateButtonImage;
	}

	/**
	 * @param string
	 */
	public void setDateButtonImage(String string)
	{
		dateButtonImage = string;
	}

	/**
	 * @return
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * @param i
	 */
	public void setSize(int i)
	{
		size = i;
	}

	/**
	 * @return
	 */
	public String getDateButtonTooltip()
	{
		return dateButtonTooltip;
	}

	/**
	 * @param string
	 */
	public void setDateButtonTooltip(String string)
	{
		dateButtonTooltip = string;
	}

	/**
	 * @param string
	 */
	public void setDateDisabledButtonImage(String string) 
	{
		dateDisabledButtonImage = string;
	}
	/**
	 * @param string
	 */
	public void setDateButtonClassName(String string) 
	{
		dateButtonClassName = string;
	}

	/**
	 * @return
	 */
	public String getRegExpPattern() {
		return regExpPattern;
	}

	/**
	 * @param string
	 */
	public void setRegExpPattern(String string) {
		regExpPattern = string;
	}

}
