package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public abstract class AbstractBodyTag extends UITag
{
	private static final String JS_CHECK_BODY_KEY_DOWN = "checkBodyKeyDown";
		
	protected String onLoad = "";
	protected String onResize = "";
	protected String onClick = "";
	protected String onKeyPress = "";
	protected String onMouseOver = "";
	protected String onMouseLeave = "";
	protected String className;
	protected String name;
	protected String title = "temp";
	protected boolean allowBack = false;
	protected String eventName;
	
	protected void init() throws UIException
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		startTagNotAuthorizedReturnValue = EVAL_BODY_INCLUDE;
		initCss();
	}

	protected void initCss() throws UIException
	{
		className = initUIProperty(className,"ui.body.className");
	}
	
	protected void renderStartTag() throws UIException
	{
		renderScripts();
		renderCss();
		startTag(BODY);
		addAttribute(DIR_BODY,getLocaleDirection());
		if (className != null)
		{
			addAttribute(CLASS,className);
		}		
		if (!allowBack)
		{
			onLoad += "history.forward();";
		}
		addEvents();
		if (!onLoad.equals("")) 
		{
			addAttribute(ONLOAD,onLoad);
		}
					
		if (onResize != null && !onResize.equals("")) 
		{
			addAttribute(ONRESIZE,onResize);
		}
			
		if (onClick != null && !onClick.equals("")) 
		{
			addAttribute(ONCLICK,onClick);
		}
	
		if (onKeyPress != null && !onKeyPress.equals("")) 
		{
			addAttribute(ONKEYUP,onKeyPress);
		}
		addAttribute(ONKEYDOWN,getFunctionCall(JS_CHECK_BODY_KEY_DOWN,"",false));
		addAttribute(ONMOUSEOVER,onMouseOver);
		addAttribute(ONMOUSELEAVE,onMouseLeave);
		addAttribute(ONCONTEXTMENU,JS_RETURN_FALSE);
		endTag();
		appendln();		
		renderBodyAfterStart();
	}

	protected abstract void renderScripts() throws UIException;
	protected abstract void renderCss() throws UIException;
	protected abstract void addEvents() throws UIException; 
			
	protected void renderBodyAfterStart() throws UIException
	{		
	}
	
	protected void renderBodyBeforeEnd() throws UIException
	{		
	}

	protected void renderEndTag() throws UIException
	{
		appendToEnd();
		renderBodyBeforeEnd();
		endTag(BODY);		
	}

	protected void resetTagState()
	{
		onLoad = "";
		onResize = "";
		onClick = "";
		onKeyPress = "";
		className = null;
		name = null;
		allowBack = false;
		super.resetTagState();
	}
	
	/**
	 * Returns the className.
	 * @return String
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Returns the onLoad.
	 * @return String
	 */
	public String getOnLoad()
	{
		return onLoad;
	}

	/**
	 * Sets the className.
	 * @param className The className to set
	 */
	public void setClassName(String className)
	{
		this.className = className;
	}

	/**
	 * Sets the onLoad.
	 * @param onLoad The onLoad to set
	 */
	public void setOnLoad(String onLoad)
	{
		this.onLoad = onLoad;
	}

	/**
	 * Returns the onResize.
	 * @return String
	 */
	public String getOnResize()
	{
		return onResize;
	}

	/**
	 * Sets the onResize.
	 * @param onResize The onResize to set
	 */
	public void setOnResize(String onResize)
	{
		this.onResize = onResize;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String name)
	{
		this.name = name;
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
	public void setOnClick(String string)
	{
		onClick = string;
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

	/**
	 * @return
	 */
	public boolean isAllowBack()
	{
		return allowBack;
	}

	/**
	 * @param b
	 */
	public void setAllowBack(boolean b)
	{
		allowBack = b;
	}
	/**
	 * @return
	 */
	public String getEventName() {
		return eventName;
	}

	/**
	 * @param string
	 */
	public void setEventName(String string) {
		eventName = string;
	}

}
