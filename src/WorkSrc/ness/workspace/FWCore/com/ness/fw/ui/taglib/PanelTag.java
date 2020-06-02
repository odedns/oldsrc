package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.UIConstants;

public class PanelTag extends UITag 
{
	private final static int PANEL_SCROLL_NONE = UIConstants.PANEL_SCROLL_NONE;
	private final static int PANEL_SCROLL_X_AUTO = UIConstants.PANEL_SCROLL_X_AUTO;
	private final static int PANEL_SCROLL_Y_AUTO = UIConstants.PANEL_SCROLL_Y_AUTO;
	private final static int PANEL_SCROLL_AUTO = UIConstants.PANEL_SCROLL_AUTO;
	
	private static final String FRAME_DEFAULT_STYLE = "1px solid";

	private String title;
	private int scrollType = PANEL_SCROLL_NONE;
	private boolean frame = true;
	private String height;
	private String width;
	private String titleClassName;
	private String frameClassName;
		
	protected void init() throws UIException
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		initState();
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
		startTag(TABLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		renderComponentWrapperStyle();
		addAttribute(HEIGHT,height == null ? "100%" : height);
		addAttribute(WIDTH,width == null ? "100%" : width);
		endTag();
		appendln();
		renderPanelTitle();
		renderPanelBody();
	}

	private void renderPanelTitle()
	{
		if (title != null)
		{
			startTag(ROW,true);
			startTag(CELL);
			if (titleClassName != null)
			{
				addAttribute(CLASS,titleClassName);
			}
			endTag();
			append(title);
			endTag(CELL);
			endTag(ROW);
			appendln();
		}
	}
	
	private void renderPanelBody()
	{
		startTag(ROW,true);
		startTag(CELL,true);
		appendln();
		startTag(DIV);
		if (frame && frameClassName != null)
		{
			addAttribute(CLASS,frameClassName);
		}
		renderPanelStyle();
		endTag();
		appendln();
	}

	private void renderPanelStyle()
	{
		StringBuffer style = new StringBuffer(50);
		if (height != null)
		{
			style.append(getStyleAttribute(HEIGHT,height)); 
		}
		style.append(getStyleAttribute(WIDTH,width == null ? "100%" : width)); 
		if (frame && frameClassName == null)
		{
			style.append(getStyleAttribute(BORDER,FRAME_DEFAULT_STYLE)); 
		}
		if ((scrollType == PANEL_SCROLL_X_AUTO))
		{
			style.append(getStyleAttribute("overflow-x","auto"));
		}
		else if ((scrollType == PANEL_SCROLL_Y_AUTO))
		{
			style.append(getStyleAttribute("overflow-y","auto"));
		}	
		else if ((scrollType == PANEL_SCROLL_AUTO))
		{
			style.append(getStyleAttribute("overflow","auto"));
		}	
		if (!style.toString().equals(""))
		{
			addAttribute(STYLE,style.toString());
		}
	}
	
	protected void resetTagState()
	{
		title = null;
		scrollType = PANEL_SCROLL_NONE;
		frame = true;
		height = null;
		width = null;
		titleClassName = null;
		frameClassName = null;
		super.resetTagState();
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException 
	{
		appendToEnd();
		endTag(DIV);
		appendln();
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();
		endTag(TABLE);
	}

	/**
	 * @param b
	 */
	public void setFrame(boolean b) 
	{
		frame = b;
	}

	/**
	 * @param string
	 */
	public void setFrameClassName(String string) 
	{
		frameClassName = string;
	}

	/**
	 * @param string
	 */
	public void setHeight(String string) 
	{
		height = string;
	}

	/**
	 * @param i
	 */
	public void setScrollType(int i) 
	{
		scrollType = i;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) 
	{
		title = string;
	}

	/**
	 * @param string
	 */
	public void setTitleClassName(String string) 
	{
		titleClassName = string;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) 
	{
		width = string;
	}
}
