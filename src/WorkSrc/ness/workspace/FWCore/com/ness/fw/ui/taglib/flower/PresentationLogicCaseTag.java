/*
 * Created on 09/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.taglib.flower;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;

public class PresentationLogicCaseTag extends BodyTagSupport 
{
	private String result;
	private String page;
	public int doStartTag() throws JspException
	{
		try 
		{		
			if (!(getParent() instanceof PresentationLogicTag))
			{
				throw new UIException("case of presentation logic must be inside presentationLogic tag");
			}
			if (result.equals(((PresentationLogicTag)getParent()).getLogicResult()))
			{
				Logger.info("presentation logic case tag","sessionId[" + ((HttpServletRequest)pageContext.getRequest()).getSession().getId() + "] include page=" + page);
				pageContext.include(page);
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
		return SKIP_BODY;
	}
	
	protected void resetTagState()
	{
		result = null;
		page = null;		
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
	public void setPage(String string) 
	{
		page = string;
	}

	/**
	 * @param string
	 */
	public void setResult(String string) 
	{
		result = string;
	}

}
