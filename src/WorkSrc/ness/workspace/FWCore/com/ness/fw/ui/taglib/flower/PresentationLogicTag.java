/*
 * Created on 09/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.taglib.flower;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.shared.ui.FlowerUIUtil;

public class PresentationLogicTag extends BodyTagSupport 
{
	private String logicResult = "";
	private String fieldName;
	private String mask;	
	private String methodName;
	private String className;
	
	public int doStartTag() throws JspException
	{
		try 
		{
			if (fieldName != null)
			{
				checkField();
			}
			else if (methodName == null || className == null)
			{
				throw new UIException("if fieldName is not set,both methodName and className must be set in tag presentationLogic");
			}
			else
			{
				checkObject();
			}
		}
		catch (UIException ui)
		{
			ui.setUITagName(getClass().getName());
			Logger.debug(getClass().getName(),ui);
			throw new JspException(ui.getMessage(),ui);
		}
		catch (Throwable t)
		{
			Logger.debug(getClass().getName(),t);
			throw new JspException(t);
		}		
		return EVAL_BODY_INCLUDE;
	}
	
	private void checkField() throws UIException
	{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		// 25/11/04 barch. should check the mask. if no mask supllied should get
		// the field without formatted 

		//	logicResult = FlowerUIUtil.getFormattedValueFromContext(request,fieldName,mask);

		// the result will be a formatted, if mask was supplied. if not
		// it will be the field value (even if xiType)
		logicResult = FlowerUIUtil.getFieldValueFromContext(request,fieldName,mask);
		if (logicResult == null)
		{
			logicResult = "";
		}
		
		// end baruch
	}
	
	private void checkObject() throws UIException
	{
		try 
		{
			HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
			Class[] parameterTypes = {Class.forName("com.ness.fw.flower.core.Context")};
			Object[] methodInput = {FlowerUIUtil.getCurrentFlowCurrentStateContext(request)};
			Method method = Class.forName(className).getMethod(methodName,parameterTypes);
			
			if (!Modifier.isStatic(method.getModifiers()))
			{
				throw new UIException("method " + methodName + " in class " + className + " must be static");
			}
			logicResult = method.invoke(null,methodInput).toString();
		}
		catch (IllegalAccessException e) 
		{
			throw new UIException(e);
		} 
		catch (NoSuchMethodException e) 
		{
			throw new UIException("method " + methodName + " does not exist in class " + className,e);
		} 
		catch (ClassNotFoundException e) 
		{
			throw new UIException("class " + className + " does not exist " + e);
		}	
		catch (InvocationTargetException e) 
		{
			throw new UIException(e.getTargetException());
		}		
	}
	
	protected void resetTagState()
	{
		logicResult = "";
		fieldName = null;
		mask = null;	
		methodName = null;
		className = null;		
	}
	
	public void release()
	{
		super.release();
		resetTagState();	
	}
		
	public int doEndTag()
	{
		return EVAL_PAGE;
	}	
	
	protected String getLogicResult()
	{
		return logicResult;
	}
	/**
	 * @param string
	 */
	public void setFieldName(String string) 
	{
		fieldName = string;
	}

	/**
	 * @param string
	 */
	public void setMask(String string) 
	{
		mask = string;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) 
	{
		methodName = string;
	}

	/**
	 * @param string
	 */
	public void setClassName(String string) 
	{
		className = string;
	}

}
