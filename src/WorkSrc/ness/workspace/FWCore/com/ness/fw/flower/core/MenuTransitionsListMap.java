/*
 * Created on: 20/01/2005
 * Author: yifat har-nof
 * @version $Id: MenuTransitionsListMap.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.HashMap;

import com.ness.fw.common.logger.Logger;

/**
 * 
 */
public class MenuTransitionsListMap extends TransitionsListMap
{

	public static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "MenuTransitionsListMap";

	/**
	 * The flow map contains a TransitionList for each menuIdFlowPath.
	 */
	private HashMap flowTransitionsMap;
	
	/**
	 * Creates new MenuTransitionsListMap object.
	 */
	public MenuTransitionsListMap()
	{
		super();
		flowTransitionsMap = new HashMap(5);
	}

	/**
	 * Add a transition to the event transition map and to the menuIdFlowPath transition map.
	 * @param transition
	 * @param menuIdFlowPath
	 */
	protected void addTransition(Transition transition, String menuIdFlowPath)
	{
		addTransition(transition);
		TransitionList transitionList = (TransitionList) flowTransitionsMap.get(menuIdFlowPath);
		if (transitionList == null)
		{
			transitionList = new TransitionList();
			flowTransitionsMap.put(menuIdFlowPath, transitionList);
			transitionList.addTransition(transition);
		}
		else if(transitionList.getTransition(transition.getEventName()) == null)
		{
			transitionList.addTransition(transition);
		}
	}

	/**
	 * remove all the transitions related to the given menuIdFlowPath 
	 * @param menuIdFlowPath
	 * @param eventHandlerFlow
	 */
	protected void removeMenuIdFlowPath (String menuIdFlowPath, Flow eventHandlerFlow)
	{
		TransitionList transitionList = (TransitionList) flowTransitionsMap.get(menuIdFlowPath);
		Logger.debug(LOGGER_CONTEXT, "remove MenuIdFlowPath [" + menuIdFlowPath + "] from flow [" + eventHandlerFlow.getName() + "]" + " flow path  "+  eventHandlerFlow.getFlowPathString());
		if (transitionList != null)
		{
			int count = transitionList.getTransitionsCount();
			for (int index = 0 ; index < count ; index++)
			{
				Transition transition = transitionList.getTransition(index);
				eventHandlerFlow.removeAuthorizedEvent(transition.getEventName());
				removeTransition(transition);
			}
		}
		flowTransitionsMap.remove(menuIdFlowPath);				
	}

	/**
	 * Returns the transitions created from the menuIdFlowPath.
	 * @param menuIdFlowPath
	 * @return TransitionList
	 */
	protected TransitionList getMenuIdFlowPathTransitions(String menuIdFlowPath)
	{
		return (TransitionList) flowTransitionsMap.get(menuIdFlowPath);
	}


}
