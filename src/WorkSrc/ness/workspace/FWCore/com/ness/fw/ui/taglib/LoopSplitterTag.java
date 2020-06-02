package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public class LoopSplitterTag extends UITag 
{
	private int columnNumber;
	private String startText;
	private String endText;
	private String splitText;
	private String seperatorText;
	
	protected final static String PAGE_CONTEXT_NAME_COLUMN_NUMBER = "columnNumber";
	protected final static String PAGE_CONTEXT_NAME_CURRENT_COLUMN = "currentColumn";
	protected final static String PAGE_CONTEXT_NAME_SPLIT_TEXT = "splitText";
	protected final static String PAGE_CONTEXT_NAME_SEPERATOR_TEXT = "seperatorText";
	
	protected void init() throws UIException
	{
		if (columnNumber <= 0)
		{
			throw new UIException("columnNumber in loopSplitter tag must be a positive number");
		}
		startTagReturnValue = EVAL_BODY_INCLUDE;
		pageContext.setAttribute(PAGE_CONTEXT_NAME_COLUMN_NUMBER,new Integer(columnNumber));
		pageContext.setAttribute(PAGE_CONTEXT_NAME_CURRENT_COLUMN,new Integer(0));
		if (splitText != null)
		{		
			pageContext.setAttribute(PAGE_CONTEXT_NAME_SPLIT_TEXT,splitText);
		}
		if (seperatorText != null)
		{
			pageContext.setAttribute(PAGE_CONTEXT_NAME_SEPERATOR_TEXT,seperatorText);
		}
		startText = initUIProperty(startText,"ui.loopSplitter.startText");
		splitText = initUIProperty(splitText,"ui.loopSplitter.splitText");
		endText = initUIProperty(endText,"ui.loopSplitter.endText");
	}

	protected void renderStartTag() throws UIException 
	{
		append(startText);
	}
	
	protected void resetTagState()
	{
		super.resetTagState();		
		columnNumber = 0;
		startText = null;
		endText = null;
		splitText = null;
		seperatorText = null;
		pageContext.removeAttribute(PAGE_CONTEXT_NAME_COLUMN_NUMBER);
		pageContext.removeAttribute(PAGE_CONTEXT_NAME_CURRENT_COLUMN);
		pageContext.removeAttribute(PAGE_CONTEXT_NAME_SEPERATOR_TEXT);
		pageContext.removeAttribute(PAGE_CONTEXT_NAME_SPLIT_TEXT);
	}	

	protected void renderEndTag() throws UIException 
	{
		appendToEnd();
		append(endText);
	}

	/**
	 * @param columnNumber
	 */
	public void setColumnNumber(int columnNumber) 
	{
		this.columnNumber = columnNumber;
	}

	/**
	 * @param string
	 */
	public void setEndText(String string) {
		endText = string;
	}

	/**
	 * @param string
	 */
	public void setStartText(String string) {
		startText = string;
	}

	/**
	 * @return
	 */
	public String getSeperatorText() {
		return seperatorText;
	}

	/**
	 * @return
	 */
	public String getSplitText() {
		return splitText;
	}

	/**
	 * @param string
	 */
	public void setSeperatorText(String string) {
		seperatorText = string;
	}

	/**
	 * @param string
	 */
	public void setSplitText(String string) {
		splitText = string;
	}

}
