/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ResultEvent.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

import com.ness.fw.common.logger.Logger;

/**
 * Returned by <code>Flow</code> when finish processing <code>Event</code>
 */
public class ResultEvent
{
	
	/**
	 * logger context
	 */
	public static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "RESULT EVENT";
	
	/**
	 * Exceptions that was catched while <code>Event</code> processing
	 */
	private ArrayList exceptions;

	/**
	 * Flow that's page should be shown at topmost level. If stay null - page of main flow will be used
	 */
	private Flow flow, mainFlow;

	private boolean validationExceptionThrown;

	public ResultEvent()
	{
		validationExceptionThrown = false;
	}

	public boolean isValidationExceptionThrown()
	{
		return validationExceptionThrown;
	}

	public void setValidationExceptionThrown()
	{
		this.validationExceptionThrown = true;
	}

	public Flow getFlow()
	{
		return flow;
	}

	public void addException (Throwable ex)
	{
		if (exceptions == null)
		{
			exceptions = new ArrayList();
		}

		exceptions.add(ex);
	}

	public Throwable getException(int index)
	{
		return (Throwable) exceptions.get(index);
	}

	public int getExceptionsCount()
	{
		return exceptions == null ? 0 : exceptions.size();
	}

	/**
	 * Indicates if <code>ResultEvent</code> contains any Exceptions or Missing Fields
	 */
	public boolean hasErrors()
	{
		return exceptions != null || validationExceptionThrown ;
	}

	public void setFlow(Flow flow)
	{
		this.flow = flow;
		Logger.debug(LOGGER_CONTEXT, "write flow [" + flow.getFlowDefinition().getName() + "] flow path [" + flow.getFlowPathString() + "]");
	}

//	public void addMenuGroup(MenuGroup menuGroup)
//	{
//		if (menuGroup != null)
//		{
//			menuGroupList.addMenuGroup(menuGroup);
//		}
//	}
//
//	public MenuGroupList getMenuGroupList()
//	{
//		return menuGroupList;
//	}

//	public void addFlowWithMenus(Flow flow)
//	{
//		flowsWithMenus.add(flow);
//	}
//
//	public ArrayList getFlowsWithMenusList()
//	{
//		return flowsWithMenus;
//	}

	public Flow getMainFlow()
	{
		return mainFlow;
	}

	public void setMainFlow(Flow mainFlow)
	{
		this.mainFlow = mainFlow;
	}
}
