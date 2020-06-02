package com.ness.fw.ui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.UIConstants;

public class IfTag extends BodyTagSupport 
{
	private String fieldName;
	private String fieldType = UIConstants.FIELD_TYPE_CONTEXT;
	private String result;
	private String mask;
	private String methodName;
	private boolean equal = true;
	private boolean compareToNull = false;
	
	public int doStartTag() throws JspException
	{
		try 
		{
			if (compareToNull && result != null)
			{
				throw new UIException("result is not allowed to be set when compareToNull is true");
			}
			if (mask != null && methodName != null)
			{
				throw new UIException("mask is not allowed to be set when methodName is set");
			}			
			return getTagEvaluation(getComparableValue());
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
	}
	
	private Object getComparableValue() throws UIException
	{
		Object value = null;
		if (fieldType.equals(UIConstants.FIELD_TYPE_CONTEXT))
		{
			value = getComparableValueFromContext();
		}
		else
		{
			value = (String)pageContext.getAttribute(fieldName);	
		}
		return value;		
	}
	
	private Object getComparableValueFromContext() throws UIException
	{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		Object value = null;
		if (methodName != null)
		{
			value = FlowerUIUtil.getMethodValueFromContext(request,fieldName,methodName);
			if (value == null && !compareToNull)
			{
				throw new UIException("the method " + methodName + " of object in context field " + fieldName + " returns null");
			}
		}
		else
		{

			// the result will be a formatted, if mask was supplied. if not
			// it will be the field value (even if xiType)
			value = FlowerUIUtil.getFieldValueFromContext(request,fieldName,mask);

			//if compareToNull=true get the unformatted value
			if (compareToNull)
			{
				// value = FlowerUIUtil.getObjectFromContext((HttpServletRequest)pageContext.getRequest(),fieldName);
			}
			
			//if compareToNull=true  and mask was set get the formatted value,if mask
			//was not set get the unformatted value
			else
			{

		// start baruch 
		 
				if (value == null)
				{
					value = "";
				}


//				if (mask != null)
//				{
//					value = FlowerUIUtil.getFormattedValueFromContext((HttpServletRequest)pageContext.getRequest(),fieldName,mask);
//				}
//				else
//				{
//					value = FlowerUIUtil.getObjectFromContext((HttpServletRequest)pageContext.getRequest(),fieldName);
//				}


		// end baruch

			}			
		}
		return value;
	}
	
	private int getTagEvaluation(Object compareResultTo) throws UIException
	{
		if (compareToNull)
		{
			if (equal && compareResultTo == null || !equal && compareResultTo != null)
			{
				return EVAL_BODY_INCLUDE;
			}
		}
		else
		{
			if (result == null)
			{
				throw new UIException("result must be set in IfTag when compareToNull=false");
			}
			else
			{
				if (compareResultTo == null)
				{
					throw new UIException(fieldName + " is null in the context and cannot be compared to " + result);
				}
				if (equal && result.equals(compareResultTo.toString()) || !equal && !result.equals(compareResultTo.toString()))
				{
					return EVAL_BODY_INCLUDE;
				}				
			}
		}
		return SKIP_BODY;		
	}
	
	protected void resetTagState()
	{
		fieldName = null;
		fieldType = UIConstants.FIELD_TYPE_CONTEXT;
		result = null;
		equal = true;		
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
	
	/**
	 * @param string
	 */
	public void setResult(String string) 
	{
		result = string;
	}
	
	/**
	 * @param b
	 */
	public void setEqual(boolean b) 
	{
		equal = b;
	}
	
	

	/**
	 * @param string
	 */
	public void setFieldName(String fieldName) 
	{
		this.fieldName = fieldName;
	}

	/**
	 * @param string
	 */
	public void setFieldType(String fieldType) 
	{
		this.fieldType = fieldType;
	}

	/**
	 * @param string
	 */
	public void setMask(String string) 
	{
		mask = string;
	}

	/**
	 * @param b
	 */
	public void setCompareToNull(boolean b) 
	{
		compareToNull = b;
	}

	/**
	 * @return
	 */
	public String getMethodName() 
	{
		return methodName;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) 
	{
		methodName = string;
	}

}
