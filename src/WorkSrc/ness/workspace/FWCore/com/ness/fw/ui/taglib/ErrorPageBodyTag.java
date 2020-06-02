package com.ness.fw.ui.taglib;

import java.util.StringTokenizer;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;

public class ErrorPageBodyTag extends AbstractBodyTag
{
	private static final String JS_CLOSE_WINDOW_ONLOAD = "closeWindowOnLoad";
	private static final String JS_CONFIRM_MODAL = "confirmModal";
	
	private static final String ERROR_DIV_ID = "errorPageDiv";
	private static final String ERROR_AREA_START_SIGN = "<ERRORPAGESTART>";
	private static final String ERROR_AREA_END_SIGN = "<ERRORPAGEEND>";
	
	public ErrorPageBodyTag()
	{
		ignoreAuth = true;
		initAuth = false;	
	}
	
	protected void renderBodyAfterStart()
	{
		append(ERROR_AREA_START_SIGN);
	}
	
	protected void renderBodyBeforeEnd()
	{		
		append(ERROR_AREA_END_SIGN);
	}	
	
	/**
	 * @see com.ness.fw.ui.taglib.AbstractBodyTag#renderEvents()
	 */
	protected void addEvents() throws UIException
	{
		if (closeModalOnSubFlowNotfound())
		{
			onLoad = getFunctionCall(JS_CLOSE_WINDOW_ONLOAD,getLocalizedText("ui.body.illegalSubFlow"),true) + ";" + onLoad;
		}
		else if (closeModalOnAuthorizationViolation())
		{
			onLoad = getFunctionCall(JS_CLOSE_WINDOW_ONLOAD,getLocalizedText("ui.body.unAuthorizedReuqest"),true) + ";" + onLoad;
		}	
	}
	
	/**
	 * Renders the script tags which are needed for the error page. 
	 */
	protected void renderScripts()
	{
		renderErrorPageScripts();
	}

	protected void renderErrorPageScripts()
	{
		StringTokenizer st = new StringTokenizer(getUIProperty("ui.scripts.files.errorPage"),COMMA);
		String scriptsDir = getSystemProperty("ui.directory.scripts");
		while (st.hasMoreTokens())
		{
			startTag(SCRIPT);
			addAttribute(SRC,scriptsDir + System.getProperty("file.separator") + st.nextToken());
			endTag();
			endTag(SCRIPT);
			appendln();
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.AbstractBodyTag#renderCss()
	 */
	protected void renderCss() throws UIException 
	{
		StringTokenizer st = new StringTokenizer(getUIProperty("ui.css.files"),COMMA);
		String cssDir = getSystemProperty("ui.directory.css");
		while (st.hasMoreTokens())
		{
			startTag(CSS_LINK);
			addAttribute(HREF,cssDir + System.getProperty("file.separator") + st.nextToken());
			addAttribute("rel","stylesheet");
			addAttribute(TYPE,"text/css");
			endTag();
			appendln();	
		}		
	}	
	
	/**
	 * Indicates if SubFlowNotFoundException was thrown.
	 * If true a javascript for closing a popup or a dialog window is rendered.
	 * @return true if SubFlowNotFoundException was thrown
	 */
	protected boolean closeModalOnSubFlowNotfound()
	{
		return (!FlowerUIUtil.isSubFlowFound(getHttpRequest()));
	}
	
	/**
	 * Indicates if AuthorizationException was thrown.
	 * If true a javascript for closing a popup or a dialog window is rendered.
	 * @return true if AuthorizationException  was thrown
	 */
	protected boolean closeModalOnAuthorizationViolation()
	{
		return (!FlowerUIUtil.isRequestAuthorized(getHttpRequest()));
	}		
}
