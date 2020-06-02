/*
 * Created on: 15/10/2003
 * Author: shay rancus
 * @version $Id: IncludeTag.java,v 1.2 2005/02/23 15:50:46 shay Exp $
 */
package com.ness.fw.ui.taglib.flower;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.servlet.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.flower.util.PageElementAuthLevel;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.common.auth.ElementAuthLevel;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;

public class IncludeTag extends BodyTagSupport
{
	private String flowName;
	private boolean row;

	public String getFlowName()
	{
		return flowName;
	}

	public void setFlowName(String flowName)
	{
		this.flowName = flowName;
	}

	public boolean getRow()
	{
		return row;
	}

	public void setRow(boolean row)
	{
		this.row = row;
	}

	public int doStartTag() throws JspException
	{
		try
		{
			ResultEvent resultEvent = (ResultEvent) pageContext.getRequest().getAttribute(HTMLConstants.RESULT_EVENT);
			Flow flow = resultEvent.getFlow();

			Flow subFlow;
			if (flowName != null)
			{
				subFlow = flow.getSubflowByName(flowName);
				if (subFlow != null)
				{
					ApplicationUtil.writeFlowInResultEvent(subFlow, resultEvent, true);
					includeSubflow(subFlow);
				}
			}
			else
			{
				includeSubFlows(flow,resultEvent);
			}

			ApplicationUtil.writeFlowInResultEvent(flow, resultEvent, false);
		}
		catch (Throwable ex)
		{
			Throwable tmp = ex;
			while (ex instanceof ServletException)
			{
				ex = ((ServletException)ex).getRootCause();		
				if(ex != null)
				{
					  tmp = ex;
				}			
			}			
			ex = tmp;
			Logger.debug("IncludeTag", "the following stack trace is truncated");
			Logger.debug("IncludeTag", ex);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);		
			pw.flush();		
			Logger.debug("IncludeTag",sw.toString());
			throw new JspException(ex.getMessage(),ex);
		}

		return SKIP_BODY;
	}

	private void includeSubFlows(Flow flow,ResultEvent resultEvent) throws UIException, IOException, ServletException
	{
		JspWriter out = pageContext.getOut();
		Iterator subflowIdsIterator = flow.getHierarchySubFlowIdsIterator();
		Flow subFlow;
				
		renderStartIncludeTable(out);

		while (subflowIdsIterator.hasNext())
		{
			subFlow = flow.getSubFlowById((String)subflowIdsIterator.next());
			ApplicationUtil.writeFlowInResultEvent(subFlow, resultEvent, true);

			renderStartIncludeCell(out);

			if (subFlow.isContainsPage())
			{
				includeSubflow(subFlow);
			}
			else
			{
				includeSubFlows(subFlow,resultEvent);						
			}
			
			renderEndIncludeCell(out);
		}

		renderEndIncludeTable(out);		
	}

	private void printServletException(Throwable ex, int i)
	{
		Logger.debug("IncludeTag " + i, ex);

		if (ex instanceof ServletException)
		if (((ServletException)ex).getRootCause() != null)
		{
			printServletException(((ServletException)ex).getRootCause(), i + 1);
		}
	}

	private void includeSubflow(Flow subFlow) throws IOException, ServletException,UIException
	{
		PageElementAuthLevel pageElement = subFlow.getPageElement();
		int authLevel = FlowerUIUtil.getAuthLevelInformation((HttpServletRequest)pageContext.getRequest(), pageElement,"IncludeTag - flow " + subFlow.getName());
		if (authLevel != ElementAuthLevel.AUTH_LEVEL_NONE)
		{
			Logger.info("include tag","sessionId[" + ((HttpServletRequest)pageContext.getRequest()).getSession().getId() + "] include page=" + pageElement.getPage());
			pageContext.include(ControllerServlet.getJspRootPath() + pageElement.getPage());
		}
			
		FlowerUIUtil.removeAuthLevel(pageElement,(HttpServletRequest)pageContext.getRequest(),"IncludeTag - flow " + subFlow.getName());
	}
	
	private void renderStartIncludeTable(JspWriter out) throws IOException
	{
		if (row)
		{
			out.write("<table width=\"100%\" height=\"100%\" border=\"1\"><tr>");
		}
	}

	private void renderStartIncludeCell(JspWriter out) throws IOException
	{
		if (row)
		{
			out.write("<td>");
		}
		
	}
	
	private void renderEndIncludeCell(JspWriter out) throws IOException
	{
		if (row)
		{
			out.write("</td>");
		}
	}

	private void renderEndIncludeTable(JspWriter out) throws IOException
	{
		if (row)
		{
			out.write("</tr></table>");
		}
	}
		
	protected void resetTagState()
	{
		flowName = null;
		row = true;		
	}
	
	public void release()
	{
		super.release();
		resetTagState();	
	}
			
	public int doEndTag() throws JspTagException
	{
		return EVAL_PAGE;
	}
}
