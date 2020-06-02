/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowerHTMLUtil.java,v 1.2 2005/03/27 16:19:46 baruch Exp $
 */
package com.ness.fw.flower.servlet;

import com.ness.fw.flower.core.*;
import javax.servlet.http.*;

public class FlowerHTMLUtil
{
	public static Flow getCurrentFlow(HttpServletRequest request)
	{
		return ((ResultEvent) request.getAttribute(HTMLConstants.RESULT_EVENT)).getFlow();
	}

	public static Context getCurrentFlowCurrentStateContext(HttpServletRequest request)
	{
		return getCurrentFlow(request).getCurrentStateContext();
	}

	public static Context getMainFlowCurrentStateContext(HttpServletRequest request)
	{
		Flow mainFlow = getMainFlow(request); 	
		return mainFlow.getCurrentStateContext();
	}

	public static Flow getMainFlow(HttpServletRequest request)
	{
		return ((ResultEvent) request.getAttribute(HTMLConstants.RESULT_EVENT)).getMainFlow();
	}

	public static String getCurrentFlowPath(HttpServletRequest request)
	{
		try{
			return getCurrentFlow(request).getFlowPathString();
		}catch (Throwable ex)
		{
			return "dummy_value";
		}
	}

	public static String getCurrentFlowStateName(HttpServletRequest request)
	{
		try{
			return getCurrentFlow(request).getCurrentState().getName();
		}catch (Throwable ex)
		{
			return "dummy_value";
		}
	}

	public static ResultEvent getResultEvent(HttpServletRequest request)
	{
		return (ResultEvent) request.getAttribute(HTMLConstants.RESULT_EVENT);
	}

	public static String getMainFlowStateName(HttpServletRequest request)
	{
		try{
			return getMainFlow(request).getCurrentState().getName();
		}catch (Throwable ex)
		{
			return "dummy_value";
		}
	}

	public static String getMenuFlowStateName(HttpServletRequest request)
	{
		try{
//			Flow currentFlow = getCurrentFlow(request);
//			return ((ResultEvent) request.getAttribute(HTMLConstants.RESULT_EVENT)).getMainFlow().getSubFlowById(currentFlow.getFlowPathString().substring(0, currentFlow.getFlowPathString().indexOf(FlowPath.FLOW_ID_LEVEL_DELIMITER))).getCurrentState().getName();

			return getMenuFlow(request).getCurrentState().getName();
		}catch (Throwable ex)
		{
			return "dummy_value";
		}
	}

	public static String getMenuFlowPath(HttpServletRequest request)
	{
		try{
			Flow menuFlow = getMenuFlow(request);

			String flowPathString = menuFlow.getFlowPathString();//((ResultEvent) request.getAttribute(HTMLConstants.RESULT_EVENT)).getMainFlow().getSubFlowById(currentFlow.getFlowPathString().substring(0, currentFlow.getFlowPathString().indexOf(FlowPath.FLOW_ID_LEVEL_DELIMITER))).getFlowPathString();
			//Logger.debug("FlowerHTMLUtil", "Asked for Menu flow Path [" + flowPathString + "]");
			return flowPathString;
		}catch (Throwable ex)
		{
			return "dummy_value";
		}
	}

	public static Flow getMenuFlow(HttpServletRequest request)
	{
		ResultEvent resultEvent = ((ResultEvent) request.getAttribute(HTMLConstants.RESULT_EVENT));
		Flow currentFlow = getCurrentFlow(request);

		Flow menuFlow;
		String currentFlowPathString = currentFlow.getFlowPathString();
		if (currentFlowPathString.length() > 0)
		{
			int firstFlowPathDelimiterIndex = currentFlowPathString.indexOf(FlowPath.FLOW_ID_LEVEL_DELIMITER, 0);
			int secondFlowPathDelimiterIndex = -1;
			if(firstFlowPathDelimiterIndex != -1)
			{
				secondFlowPathDelimiterIndex = currentFlowPathString.indexOf(FlowPath.FLOW_ID_LEVEL_DELIMITER, firstFlowPathDelimiterIndex + 1);
			}
			String menuFlowId = currentFlowPathString.substring(firstFlowPathDelimiterIndex+1, secondFlowPathDelimiterIndex == -1 ? currentFlowPathString.length() : secondFlowPathDelimiterIndex);

			menuFlow = resultEvent.getMainFlow().getSubFlowById(menuFlowId);
		}
		else
		{
			menuFlow = resultEvent.getMainFlow();
		}
		return menuFlow;
	}
}
