package com.ness.fw.ui.taglib.flower;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.shared.ui.FlowerUIUtil;

public class ContextGetterTag extends BodyTagSupport 
{
	protected String contextFieldName;
	protected String pageScopeFieldName;
	protected String methodName;
	protected String mask;
	
	public int doStartTag() throws JspException
	{
		try 
		{
			if (mask != null && methodName != null)
			{
				throw new UIException("mask is not allowed to be set when methodName is set");
			}
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			Object contextObject = FlowerUIUtil.getObjectFromContext(request,contextFieldName);
			if (contextObject != null)
			{			
				//the methodName should be a name of a getter method in an object 
				//in the context
				if (methodName != null)
				{
					Object value = ((contextObject.getClass()).getMethod(methodName,new Class[0])).invoke(contextObject,new Object[0]);
					if (value == null)
					{
						throw new UIException("the method " + methodName + " of object " + contextObject.getClass().getName() + " returns null");
					}
					pageContext.setAttribute(pageScopeFieldName,value);
				}
				//if methodName is null and mask wa set ,the value in the context should not be
				//a data access object,but a more primitive type:String,Double,Integer.
				else if (mask != null)
				{					
					pageContext.setAttribute(pageScopeFieldName,FlowerUIUtil.getFormattedValueFromContext((HttpServletRequest)pageContext.getRequest(),contextFieldName,mask));			
				}
				//Put an object from the context in the page scope
				else
				{
					pageContext.setAttribute(pageScopeFieldName,contextObject);
				}
			}
			else
			{
				throw new UIException("cannot set null value to page scope,context field name is: " + contextFieldName);
			}
		}
		catch (UIException ui)
		{
			ui.setUITagName(getClass().getName());
			Logger.debug(getClass().getName(),ui);
			throw new JspException(ui.getMessage(),ui);
		}
		catch (Throwable e)
		{
			Logger.debug(getClass().getName(),e);
			throw new JspException(e);
		}		
		return SKIP_BODY;
	}
	
	protected void resetTagState()
	{
		contextFieldName = null;
		pageScopeFieldName = null;
		methodName = null;
		mask = null;		
	}
	
	public void release()
	{
		super.release();
		resetTagState();	
	}
		
	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;
	}
	
	/**
	 * @return
	 */
	public String getPageScopeFieldName() {
		return pageScopeFieldName;
	}

	/**
	 * @return
	 */
	public String getContextFieldName() {
		return contextFieldName;
	}

	/**
	 * @param string
	 */
	public void setPageScopeFieldName(String string) {
		pageScopeFieldName = string;
	}

	/**
	 * @param string
	 */
	public void setContextFieldName(String string) {
		contextFieldName = string;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) {
		methodName = string;
	}

	/**
	 * @param string
	 */
	public void setMask(String string) {
		mask = string;
	}

}
