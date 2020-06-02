package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.data.TextAreaTagData;

public class TextAreaTag extends AbstractTextTag
{
	protected final static String JS_EXPAND_TEXT_AREA = "expandTextArea";
	
	protected String rows;
	protected String cols;
	protected boolean expandable = false;
	protected String expanderTitle = "";
	protected String expanderButtonTooltip;	
	protected String expanderButtonClassName;
	protected String expanderDialogParams = " ";
	
	public TextAreaTag()
	{
		dirtable = true;
	}

	/**
	 * returns the tag css prefix
	 * @return the tag css prefix 
	 */
	protected String getDefaultCssPrefix()
	{
		return getUIProperty("ui.textArea.prefix");
	}
	
	protected void initAttributes()
	{
		if (tagData != null)
		{ 
			TextAreaTagData  textAreaTagData = (TextAreaTagData)tagData;
			if (textAreaTagData.getRows() != null)
			{
				setRows(textAreaTagData.getRows());
			}
			if (textAreaTagData.getCols() != null)
			{
				setCols(textAreaTagData.getCols());
			}
			if (textAreaTagData.isExpandaple() != null)
			{
				setExpandable(textAreaTagData.isExpandaple().booleanValue());
			}
			if (textAreaTagData.getExpanderTitle() != null)
			{
				setExpanderTitle(textAreaTagData.getExpanderTitle());
			}
			if (textAreaTagData.getExpanderDialogParams() != null)
			{
				setExpanderDialogParams(textAreaTagData.getExpanderDialogParams());
			}
			super.initAttributes();
		}
	}
		
	protected void initCss()
	{
		super.initCss();
		expanderButtonClassName = initUIProperty(expanderButtonClassName,"ui.textArea.expandButton");
	}
		
	protected void renderStartTag() throws UIException
	{
		initCss();
		expanderButtonTooltip = expanderButtonTooltip == null ?"ui.textarea.expanderButton.tooltip" : expanderButtonTooltip;
		if (expandable)
		{
			startTag(TABLE);
			addAttribute(CELLSPACING,"0");
			addAttribute(CELLPADDING,"0");
			addAttribute(ID,id + COMPLEX_COMPONENT_SUFFIX);
			renderComponentWrapperStyle();
			endTag();
			startTag(ROW,true);
			startTag(CELL,true);
		}
		if (!isDisabled())
		{
			renderTextArea();
		}
		else
		{
			renderDisabledTextArea();
		}
		if (expandable)
		{
			//Used for adding suffixes to the class of the expander button
			int expanderButtonClassNameSuffixes = CSS_USE_DIRECTION;
			if (isDisabled())
			{
				expanderButtonClassNameSuffixes += CSS_USE_DISABLED;
			}
			
			endTag(CELL);
			renderEmptyCell();
			startTag(CELL);
			addAttribute(VALIGN,TOP);
			endTag();
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(CLASS,expanderButtonClassName + getCssClassSuffix(expanderButtonClassNameSuffixes));
			addAttribute(VALUE,getLocalizedText("ui.textarea.expanderButton"));
			addAttribute(TITLE,getLocalizedText(expanderButtonTooltip));
			addAttribute(
				ONCLICK,
				getFunctionCall
					(JS_EXPAND_TEXT_AREA,id + COMMA + 															
						getSingleQuot(getLocalizedText(expanderTitle)) + COMMA + 
						getSingleQuot(expanderDialogParams) + COMMA + 
						getSingleQuot(getLocalizedText("ui.textarea.expander.approveButton")) + COMMA +
						getSingleQuot(getLocalizedText("ui.textarea.expander.cancelButton"))
						,false
					)
			);
			if (isDisabled())
			{
				append(DISABLED);
			}
			endTag();
			endTag(CELL);
			endTag(ROW);
			endTag(TABLE);
		}
		if (textModel != null)
		{
			textModel.setAuthLevel(getAuthLevel());
		}		
	}	

	protected void renderDisabledTextArea()
	{
		startTag(DIV);
		addAttribute(STYLE,getWidthAndHeightAsAtyle() + "overflow:auto;");
		renderClassByState();
		endTag();
		startTag(PRE);
		addAttribute(STYLE,getStyleAttribute("font-family","arial") + getStyleAttribute("font-weight","normal"));
		endTag();
		append(getValue());
		endTag(PRE);
		endTag(DIV);
	}
	protected void renderTextArea()
	{
		startTag(TEXTAREA);
		addAttribute(ID,id);
		addAttribute(NAME,id);
		if (maxLength != 0)
		{
			addAttribute(MAX_LENGTH,String.valueOf(maxLength));
		}
		renderStyle(!expandable);
		if (cols != null)
		{
			addAttribute(TA_COLS,cols);
		}
		if (rows != null)
		{
			addAttribute(TA_ROWS,rows);
		}
		renderClassByState();
		if (onChange != null)
		{
			addAttribute(ONCHANGE,onChange);
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
		
		if (autoSelect)
		{
			addAttribute(ONFOCUS,SCRIPT_AUTO_SELECT);
		}
		if (readonly != null)
		{
			addAttribute(READONLY,readonly);
		}
		else if (isDisabled())
		{
			if (readonly != null)
			{
				addAttribute(READONLY,readonly);
			}
			else
			{
				append(DISABLED);
			}
		}
		if (text != null)
		{
			append(BLANK + text);
		}
		if (tooltip != null)
		{
			addAttribute(TITLE,getLocalizedText(tooltip));
		}		
		renderFieldFocus();
		renderInputTypeIndication();
		endTag();	
		append(getValue());
		endTag(TEXTAREA);		
	}
	
	protected void resetTagState()
	{
		rows = null;
		cols = null;
		expandable = false;
		expanderTitle = "";	
		expanderDialogParams = " ";
		dirtable = true;			
		super.resetTagState();		
	}
	
	protected void renderEndTag()
	{
				
	}	
		
	/**
	 * Returns the cols.
	 * @return String
	 */
	public String getCols()
	{
		return cols;
	}

	/**
	 * Returns the rows.
	 * @return String
	 */
	public String getRows()
	{
		return rows;
	}

	/**
	 * Sets the cols.
	 * @param cols The cols to set
	 */
	public void setCols(String cols)
	{
		this.cols = cols;
	}

	/**
	 * Sets the rows.
	 * @param rows The rows to set
	 */
	public void setRows(String rows)
	{
		this.rows = rows;
	}

	/**
	 * Returns the expandable.
	 * @return boolean
	 */
	public boolean isExpandable()
	{
		return expandable;
	}

	/**
	 * Sets the expandable.
	 * @param expandable The expandable to set
	 */
	public void setExpandable(boolean expandable)
	{
		this.expandable = expandable;
	}

	/**
	 * @return
	 */
	public String getExpanderTitle()
	{
		return expanderTitle;
	}

	/**
	 * @param string
	 */
	public void setExpanderTitle(String string)
	{
		expanderTitle = string;
	}

	/**
	 * @return
	 */
	public String getExpanderDialogParams()
	{
		return expanderDialogParams;
	}

	/**
	 * @param string
	 */
	public void setExpanderDialogParams(String string)
	{
		expanderDialogParams = string;
	}	
	/**
	 * @return
	 */
	public String getExpanderButtonTooltip() {
		return expanderButtonTooltip;
	}

	/**
	 * @param string
	 */
	public void setExpanderButtonTooltip(String string) {
		expanderButtonTooltip = string;
	}

	/**
	 * @return
	 */
	public String getExpanderButtonClassName() {
		return expanderButtonClassName;
	}

	/**
	 * @param string
	 */
	public void setExpanderButtonClassName(String string) {
		expanderButtonClassName = string;
	}

}
