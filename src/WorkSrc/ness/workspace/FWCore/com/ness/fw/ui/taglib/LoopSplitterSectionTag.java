package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public class LoopSplitterSectionTag extends UITag 
{
	private String splitText;
	private String seperatorText;
	protected void init() throws UIException
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;		
	}
	
	protected void renderStartTag() throws UIException 
	{
		Integer currentColumn = (Integer)pageContext.getAttribute(LoopSplitterTag.PAGE_CONTEXT_NAME_CURRENT_COLUMN);
		Integer columnNumber = (Integer)pageContext.getAttribute(LoopSplitterTag.PAGE_CONTEXT_NAME_COLUMN_NUMBER);
		splitText = (String)pageContext.getAttribute(LoopSplitterTag.PAGE_CONTEXT_NAME_SPLIT_TEXT);
		seperatorText = (String)pageContext.getAttribute(LoopSplitterTag.PAGE_CONTEXT_NAME_SEPERATOR_TEXT);
		
		if (currentColumn == null)
		{
			throw new UIException("parent tag loopSplitter does not exist");
		}
		if (columnNumber == null)
		{
			throw new UIException("parent tag loopSplitter does not exist");
		}
		if(currentColumn.intValue() >= columnNumber.intValue())
		{
			if (splitText == null)
			{
				endTag(ROW);
				appendln();
				startTag(ROW,true);
			}
			else
			{
				append(splitText);
			}
			pageContext.setAttribute(LoopSplitterTag.PAGE_CONTEXT_NAME_CURRENT_COLUMN,new Integer(0));
		}
		else if (currentColumn.intValue() != 0)
		{
			if (seperatorText != null)
			{
				append(seperatorText);
			}
		}
	}

	protected void resetTagState()
	{
		splitText = null;
		seperatorText = null;
		super.resetTagState();
	}

	protected void renderEndTag() throws UIException 
	{
		Integer currentColumn = (Integer)pageContext.getAttribute(LoopSplitterTag.PAGE_CONTEXT_NAME_CURRENT_COLUMN);
		pageContext.setAttribute(LoopSplitterTag.PAGE_CONTEXT_NAME_CURRENT_COLUMN,new Integer(currentColumn.intValue() + 1));
	}
	
	/**
	 * @param string
	 */
	public void setSplitText(String string) 
	{
		splitText = string;
	}

}
