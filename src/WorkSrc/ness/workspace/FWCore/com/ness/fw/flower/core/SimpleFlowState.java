/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: SimpleFlowState.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * The background state type - {@link FlowState} that does not wait for any event 
 * (used for fine transition tuning).
 */
public class SimpleFlowState extends FlowStateImpl
{
	
	/**
	 * The constructor used while creating {@link FlowState}'s instance.
	 * 
 	 * @param name The state name.
	 * @param contextName The {@link Context} name.
	 * @param inFormatter The input {@link Formatter}.
	 * @param outFormatter The output {@link Formatter}.
	 * @param validator The state {@link Validator}. 
	 * @param entryActions The entry {@link ActionList}.
	 * @param transitions The {@link TransitionsListMap}.
	 * @param exitActions The exit {@link ActionList}.
	 * @param initial Indicates if the state is the initial state for the {@link Flow}.
	 * @param authId The authorization id to determined the authorization level of the state. 
	 */
	public SimpleFlowState(String name, String contextName, Formatter inFormatter, Formatter outFormatter, Validator validator, ActionList entryActions, TransitionsListMap transitions, ActionList exitActions, boolean initial, String authId)
	{
		super(name, contextName, inFormatter, outFormatter, validator, entryActions, transitions, exitActions, null, false, initial, authId);
	}

	/**
	 * Returns the state type {@link STATE_TYPE_SIMPLE}.
	 * @return int type
	 */
	public int getType()
	{
		return STATE_TYPE_SIMPLE;
	}

	/**
	 * Returns the page name that is defined for the state.
	 * @return String 
	 */
	public String getPage()
	{
		return null;
	}
	
	/**
	 * Returns true when the state contains a page.
	 * @return boolean 
	 */
	public boolean isStateContainsPage()
	{
		return false;
	}
	
	/**
	 * Returns true when the state contains a sub flow.
	 * @return boolean 
	 */
	public boolean isStateContainsSubFlow()
	{
		return false;
	}
	
	/**
	 * Returns true when the state contains an activity.
	 * @return boolean 
	 */
	public boolean isStateContainsActivity()
	{
		return false;
	}

	/**
	 * Indicates if the state is waiting for event or should choose a transition automatically. 
	 * @return boolean  
	 */
	public boolean isStateWaitingForEvent()
	{
		return false;
	}

	/**
	 * Indicates if the state should be visible in the tab system. 
	 * @return boolean  
	 */
	public boolean isVisible()
	{
		return false;
	}
	
}
