/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: TransitionsListMap.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Used to map lists of transitions to event name
 */
public class TransitionsListMap
{
	/**
	 * The event map contains a TransitionList for each event name.
	 */
	private HashMap eventTransitionsMap;
	
	/**
	 * The default transition
	 */
	private Transition defaultTransition;

	public TransitionsListMap()
	{
		eventTransitionsMap = new HashMap(5);
	}

	public TransitionList getTransitionList(String eventName)
	{
		return (TransitionList) eventTransitionsMap.get(eventName);
	}

	public void addTransition(Transition transition)
	{
		if (transition.isDefaultTransition())
		{
			defaultTransition = transition;
		}
		else
		{
			TransitionList list = (TransitionList) eventTransitionsMap.get(transition.getEventName());
			if (list == null)
			{
				list = new TransitionList();
				eventTransitionsMap.put(transition.getEventName(), list);
			}

			list.addTransition(transition);
		}
	}

	public Transition getDefaultTransition()
	{
		return defaultTransition;
	}
	
	protected void removeTransition (Transition transition)
	{
		eventTransitionsMap.remove(transition.getEventName());
	}
}
