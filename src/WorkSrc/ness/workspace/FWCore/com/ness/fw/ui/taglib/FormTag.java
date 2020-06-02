package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public class FormTag extends UITag
{
	protected static final String FORM_METHOD_POST = "post";
	protected static final String FORM_METHOD_GET = "get";
	
	protected String name;
	protected String action;
	protected String target = "";
	protected String method = FORM_METHOD_POST;
	
	protected void init()
	{
		startTagReturnValue = EVAL_BODY_INCLUDE;
		startTagNotAuthorizedReturnValue = EVAL_BODY_INCLUDE;
	}
	protected void renderStartTag() throws UIException
	{
		renderForm();
	}

	protected void resetTagState()
	{
		name = null;
		action = null;
		target = "";
		method = FORM_METHOD_POST;		
		super.resetTagState();
	}

	protected void renderEndTag() throws UIException
	{
		appendToEnd();
		endTag(FORM);		
	}
	
	protected void renderForm()  throws UIException
	{
		startTag(FORM);	
		endTag();
		renderHiddenFields();
	}
	
	protected void renderHiddenFields()  throws UIException
	{
		
	}
		
	/**
	 * @return
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * @return
	 */
	public String getMethod()
	{
		return method;
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
	public String getTarget()
	{
		return target;
	}

	/**
	 * @param string
	 */
	public void setAction(String string)
	{
		action = string;
	}

	/**
	 * @param string
	 */
	public void setMethod(String string)
	{
		method = string;
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
	public void setTarget(String string)
	{
		target = string;
	}

}
