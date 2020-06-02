/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: TransitionList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Implelents list of transitions based on <code>ArrayList</code> implementation
 */
public class TransitionList
{
	private ArrayList list;

	public TransitionList()
	{
		list = new ArrayList();
	}

	public Transition getTransition(int index)
	{
		return (Transition) list.get(index);
	}

	public void addTransition(Transition transition)
	{
		list.add(transition);
	}

	public int getTransitionsCount()
	{
		return list.size();
	}
	
	protected boolean removeTransition (Transition transition)
	{
		return list.remove(transition);
	}

	public Transition getTransition(String eventName)
	{
		for(int index = 0 ; index < list.size() ; index++)
		{
			Transition transition = (Transition) list.get(index);
			if(transition != null && transition.getEventName().equals(eventName))
			{
				return transition;
			}
		}
		
		return null;
	}

	
}
