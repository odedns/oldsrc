/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: HTTPSessionBoundFlow.java,v 1.6 2005/05/04 12:15:03 yifat Exp $
 */
package com.ness.fw.flower.servlet;

import com.ness.fw.flower.common.MenuItemList;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.FlowElementsFactoryException;
import com.ness.fw.flower.util.*;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.*;

import javax.servlet.http.*;
import java.util.*;

public class HTTPSessionBoundFlow implements Flow, HttpSessionBindingListener
{
	/**
	 * The main flow to pass the events. 
	 */
	private Flow flow;

	/**
	 * Creates new HTTPSessionBoundFlow object.
	 * @param flow The main flow to pass the events.
	 */
	public HTTPSessionBoundFlow(Flow flow)
	{
		this.flow = flow;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Implementing HttpSessionBindingListener interface
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
	 */
	public void valueBound(HttpSessionBindingEvent event)
	{
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
	 */
	public void valueUnbound(HttpSessionBindingEvent event)
	{
		String loggerContextExt = "Unbound SessionId [" + event.getSession().getId() 
			+ "] Flow [" + getName() + "] Flow Path [" + getFlowPathString() + "] : ";

		Throwable error = null;

		try
		{
			// process cleanup event on the main flow before the kill.
			ResultEvent resultEvent = performCleanUp(loggerContextExt);
			if (resultEvent.getExceptionsCount() > 0)
			{
				error = resultEvent.getException(0); 
			}
		} 
		catch (Throwable ex)
		{
			error = ex;
		}
		
		if(error != null)
		{
			Logger.error(FLOWERConstants.LOGGER_CONTEXT, 
				loggerContextExt + " An error occured while session cleanup. ", 
				error);
		}

		try
		{
			// kill the main flow		
			flow.kill();
		} 
		catch (Throwable ex)
		{
			Logger.error(FLOWERConstants.LOGGER_CONTEXT, 
				loggerContextExt + " An error occured while killing main flow. ", 
				ex);
			error = ex;
		}
		
		if(error == null)
		{
			Logger.debug(FLOWERConstants.LOGGER_CONTEXT, loggerContextExt + " Session cleanup succeeded."); 
		}
		
	}
	
	/**
	 * process cleanup event on the main flow before the kill.
	 * @param loggerContextExt
	 * @return ResultEvent
	 * @throws Throwable
	 */
	private ResultEvent performCleanUp (String loggerContextExt) throws Throwable
	{
		// clear authorization levels data from the previous request
		getCurrentStateContext().getDynamicGlobals().getAuthLevelsManager().clear();

		//only single point of synchronization because - concurent threads
		//on same session may be only in case of existing flow. (multiple
		//frames or opening new explorer window by pressing Ctrl-N)
		//passing event to flow
		ResultEvent resultEvent = flow.processEventForState(new FlowCleanUpEvent());
		return resultEvent;				
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Implementing Flow interface
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	public ResultEvent initiate(Flow parentFlow, Context parentFlowContext, String flowPathString, SubFlowData subFlowData, DynamicGlobals dynamicGlobals, boolean interactiveMode)
	{
		return flow.initiate(parentFlow, parentFlowContext, flowPathString, subFlowData, dynamicGlobals, interactiveMode);
	}

	public String getFlowPathString()
	{
		return flow.getFlowPathString();
	}

	public FlowState getCurrentState()
	{
		return flow.getCurrentState();
	}

	public Context getCurrentStateContext()
	{
		return flow.getCurrentStateContext();
	}

	public void kill() throws FormatterException, ContextException, FatalException, AuthorizationException, FlowException
	{
		flow.kill();
	}

	public FlowDefinition getFlowDefinition()
	{
		return flow.getFlowDefinition();
	}

	/**
	 * Returns the page element (from the flow / current state), 
	 * contains the page & his authorizations.
	 * @return PageElementAuthLevel
	 */
	public PageElementAuthLevel getPageElement()
	{
		return flow.getPageElement();
	}
	
	/**
	 * Returns the flow page element, contains the page & his authorizations.
	 * @return PageElementAuthLevel
	 */
	public PageElementAuthLevel getFlowPageElement()
	{
		return flow.getFlowPageElement();
	}

	/**
	 * Returns the current state page element, contains the page & his authorizations.
	 * @return PageElementAuthLevel
	 */
	public PageElementAuthLevel getCurrentStatePageElement()
	{
		return flow.getCurrentStatePageElement();
	}

	public ResultEvent processEvent(Event event)
	{
		return flow.processEvent(event);
	}

	/**
	 * Used to process event when the state is determined finally
	 * @param event
	 */
	public ResultEvent processEventForState(Event event) throws GuardException,  ActivityException, ValidationException, ActionException, FlowElementsFactoryException, FlowException, ValidationProcessException, ResourceException, ContextException, FormatterException, FatalException, AuthorizationException
	{
		return flow.processEventForState(event);
	}

	public String getName()
	{
		return flow.getName();
	}

	public Flow getSubflowByName(String name)
	{
		return flow.getSubflowByName(name);
	}

	/**
	 * Return the IDs of the sub flows in the hierarchy of the current flow, 
	 * without independent sub flows that was opened under the current flow.  
	 * @return Iterator An iterator over the sub flows IDs.
	 */
	public Iterator getHierarchySubFlowIdsIterator()
	{
		return flow.getHierarchySubFlowIdsIterator();
	}

	/**
	 * Return the IDs of all the sub flows of the current flow.
	 * @return Iterator An iterator over the sub flows IDs.
	 */
	public Iterator getSubFlowIdsIterator()
	{
		return flow.getSubFlowIdsIterator();
	}

	public Flow getSubFlowById(String s)
	{
		return flow.getSubFlowById(s);
	}

	public boolean isIndependent()
	{
		return flow.isIndependent();
	}

	public boolean isFinished()
	{
		return flow.isFinished();
	}

	/**
	 * Returns the authorization level determined for the current state.
	 */
	public int getAuthLevel()
	{
		return flow.getAuthLevel();
	}

	/**
	 * Returns the authorization level determined for the flow.
	 */
	public int getFlowAuthLevel()
	{
		return flow.getFlowAuthLevel();
	}

	/**
	 * Returns a list of visible & authorized {@link FlowState}s. 
	 * @return FlowStatesList
	 * @throws ResourceException
	 */
	public FlowStatesList getVisibleFlowStatesList() throws ResourceException
	{
		return flow.getVisibleFlowStatesList();
	}
	
	/**
	 * clears the authorized events of the flow.
	 */
	public void clearAuthorizedEvents ()
	{
		flow.clearAuthorizedEvents();
	}

	/**
	 * Add an event to the authorizedEvents list.
	 * @param clickEventName The event name to add.
	 * @param componentDescription The component that registers the event.
	 */
	public void addAuthorizedEvent(String eventName, String componentDescription)
	{
		flow.addAuthorizedEvent(eventName, componentDescription);
	}
	
	/**
	 * remove the event from the authorizedEvents list.
	 * @param eventName The event name to add.
	 */
	public void removeAuthorizedEvent(String eventName)
	{
		flow.removeAuthorizedEvent(eventName);
	}
	
	/**
	 * Check if the event is authorized for the flow.
	 * @param clickEventName The event name to check.
	 * @return boolean True if the event is authorized.
	 */
	public boolean isEventAuthorized (String eventName)
	{
		return flow.isEventAuthorized (eventName);
	}
	
	/**
	 * Check if the current state is of type complex & contains subflow to run.
	 * @return boolean
	 */
	public boolean isCurrentStateOfTypeSubFlow ()
	{
		return flow.isCurrentStateOfTypeSubFlow();		
	}
	
	/**
	 * Check if the current state is of type complex & contains page to display.
	 * @return boolean
	 */
	public boolean isCurrentStateOfTypePage ()
	{
		return flow.isCurrentStateOfTypePage();		
	}

	/**
	 * Indicates whether the flow contains page
	 * @return boolean
	 */
	public boolean isContainsPage()
	{
		return flow.isContainsPage();
	}

	/**
	 * Indicates whether the current state is visible
	 * @return boolean
	 */
	public boolean isCurrentStateVisible()
	{
		return flow.isCurrentStateVisible();
	}

	/**
	 * Returns the menuItems list related to the current state,  
	 * from the flow hierarchy above and from all the subflows.
	 * @return MenuItemList
	 * @throws FlowerException
	 */
	public MenuItemList getMenuItemList () throws FlowerException
	{
		return flow.getMenuItemList();
	}

	/**
	 * Add the menu ids of the parent flow to the given list.
	 * add the menu ids of the current flow if argument "addCurrent" is set to true.
	 * @param menuList The menuList to add the menu items. 
	 * @param addCurrent Indicates whether to add the current flow menu ids or just 
	 * to call to the parent flow method.
	 */
	public void addParentMenuIds (MenuIdList menuList, boolean addCurrent)
	{
		flow.addParentMenuIds(menuList, addCurrent);
	}
	
	
	/**
	 * Add the menu ids of the sub flows to the given list.
	 * add the menu ids of the current flow if argument "addCurrent" is set to true.
	 * @param menuList The menuList to add the menu items 
	 * @param addCurrent Indicates whether to add the current flow menu ids or just 
	 * to call to the child sub flows method.
	 */
	public void addChildrenMenuIds (MenuIdList menuList, boolean addCurrent)
	{
		flow.addChildrenMenuIds(menuList, addCurrent);
	}

	/**
	 * Returns the first flow in the hierarchy.
	 * @return Flow
	 */
	public Flow getFirstFlow()
	{
		return flow.getFirstFlow();
	}

	/**
	 * Returns the menu flow from the current flow hierarchy.
	 * @param flowPath
	 * @return Flow the menu flow
	 * @throws FlowerException
	 */
	public Flow getMenuFlow (FlowPath flowPath)
	{
		return flow.getMenuFlow(flowPath);
	}

	/**
	 * Indicates whether the type is menu.
	 * @return boolean
	 */
	public boolean isMenuFlow ()
	{
		return flow.isMenuFlow();
	}

	/**
	 * Creates the ButtonExtraDataList with the buttons of the current state and the states above & under in the hierarchy.
	 * @param groupId
	 * @throws FlowException
	 */
	public ButtonExtraDataList getButtonExtraDataList (String groupId) throws FlowException
	{
		return flow.getButtonExtraDataList(groupId);
	}  

	public void addChildrenButtons (String groupId, ButtonExtraDataList buttonExtraDataList, boolean addCurrent)
	{
		flow.addChildrenButtons(groupId, buttonExtraDataList, addCurrent);
	}


}
