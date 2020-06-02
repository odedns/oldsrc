/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowState.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.List;

/**
 * Represents single {@link Flow} state. Contains data collected from configuration source (XML).
 */
public interface FlowState
{
	/**
	 * State type simple. The commonly used type of state that represents state of flow 
	 * that correspond to page displayed by browser or activating an {@link ActivityImpl} 
	 * (in non-interactive mode or when the state does not contain a declaration of a page).
	 * The state can also run a new sub flow.
	 */
	public static final int STATE_TYPE_COMPLEX = 1;

	/**
	 * State type final. Engaging this state indicates to parent flow that the current flow is finished.
	 */
	public static final int STATE_TYPE_FINAL = 2;

	/**
	 * State type simple - State that does not wait for any event. 
	 * (used for fine transition tuning).
	 */
	public static final int STATE_TYPE_SIMPLE = 3;

	/**
	 * Returns state name that is defined for the state.
	 * @return name
	 */
	public String getName();

	/**
	 * Returns the state type. Can be one of the following: {@link STATE_TYPE_SIMPLE}, 
	 * {@link STATE_TYPE_SIMPLE}, {@link STATE_TYPE_SUB_FLOW}, {@link STATE_TYPE_FINAL}.
	 * @return int type
	 */
	public int getType();

	/**
	 * Indicates if the state is the initial state for the flow.
	 */
	public boolean isInitialState();

	/**
	 * Returns the context name defined for the state.
	 * @return String Context name.
	 */
	public String getContextName();

	/**
	 * Returns the input formatter that is defined for the state.
	 * @return Formatter
	 */
	public Formatter getInFormatter();

	/**
	 * Returns the output formatter that is defined for the state.
	 * @return Formatter
	 */
	public Formatter getOutFormatter();

	/**
	 * Returns the validator that is defined for the state.
	 * @return Validator
	 */
	public Validator getValidator();

	/**
	 * Returns the entry actions list that is defined for the state.
	 * @return ActionList
	 */
	public ActionList getEntryActions();

	/**
	 * Returns the exit actions list that is defined for the state.
	 * @return ActionList
	 */
	public ActionList getExitActions();

	/**
	 * Returns the transitions map that is defined for the state.
	 * @return TransitionsListMap
	 */
	public TransitionsListMap getTransitions();

	/**
	 * Returns the default transition that is defined for the state.
	 * @return Transition
	 */
	public Transition getDefaultTransition();

	/**
	 * Returns true when the state contains a page.
	 * @return boolean 
	 */
	public boolean isStateContainsPage();

	/**
	 * Returns true when the state contains a sub flow.
	 * @return boolean 
	 */
	public boolean isStateContainsSubFlow();

	/**
	 * Returns true when the state contains an activity.
	 * @return boolean 
	 */
	public boolean isStateContainsActivity();

	/**
	 * Indicates if the state can be reached only by his holding {@link Flow}.
	 * @return boolean  
	 */
	public boolean isReachableByFlowOnly();
	
	/**
	 * returns authorization id declared in the state.
	 * @return String authId 
	 */
	public String getAuthId();

	/**
	 * Returns the menu id's associated with the state.
	 * @return List
	 */
	public List getMenuIds();

	/**
	 * Returns the page name that is defined for the state.
	 * @return String 
	 */
	public String getPage();

	/**
	 * Indicates if the state is waithing for event or should choose a transition automatically. 
	 * @return boolean  
	 */
	public boolean isStateWaitingForEvent();

	/**
	 * Indicates if the state should be visible in the tab system. 
	 * @return boolean  
	 */
	public boolean isVisible();
	
}
