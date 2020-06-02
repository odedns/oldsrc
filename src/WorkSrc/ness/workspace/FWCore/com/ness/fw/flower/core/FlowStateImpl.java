/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowStateImpl.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.List;

import com.ness.fw.util.StringFormatterUtil;

/**
 * Implements flow state.
 */
public abstract class FlowStateImpl implements FlowState
{
	/**
	 * The state name that is defined for the state.
	 */
	private String name;
	
	/**
	 * The {@link Context} name defined for the state.
	 */
	private String contextName;
	
	/**
	 * The input {@link Formatter} that is defined for the state.
	 */
	private Formatter inFormatter;
	
	/**
	 * The output {@link Formatter} that is defined for the state.
	 */
	private Formatter outFormatter;
	
	/**
	 * The {@link Validator} that is defined for the state.
	 */
	private Validator validator;
	
	/**
	 * The entry {@link ActionList} that is defined for the state.
	 */
	private ActionList entryActions;

	/**
	 * The exit {@link ActionList} that is defined for the state.
	 */
	private ActionList exitActions;

	/**
	 * The {@link TransitionsListMap} that is defined for the state.
	 */
	private TransitionsListMap transitions;
		
	/**
	 * Indicates if the state can be reached only by his holding {@link Flow}.
	 */
	private boolean reachableByFlowOnly;
	
	/**
	 * Indicates if the state is the initial state for the {@link Flow}.
	 */
	private boolean initial;

	/**
	 * The menu id's associated with the state.
	 */
	private List menuIds;

	/**
	 * The authorization id to determined the authorization level of the state. 
	 */
	private String authId;

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
	 * @param menuIds  The menu id's associated with the state.
	 * @param reachableByFlowOnly Indicates if the state can be reached only by his holding {@link Flow}.
	 * @param initial Indicates if the state is the initial state for the {@link Flow}.
	 * @param authId The authorization id declared in the state. 
	 */
	public FlowStateImpl(String name, String contextName, Formatter inFormatter, Formatter outFormatter, Validator validator, ActionList entryActions, TransitionsListMap transitions, ActionList exitActions, String menuIds, boolean reachableByFlowOnly, boolean initial, String authId)
	{
		this.contextName = contextName;
		this.entryActions = entryActions;
		this.exitActions = exitActions;
		this.inFormatter = inFormatter;
		this.name = name;
		this.outFormatter = outFormatter;
		this.transitions = transitions;
		this.validator = validator;
		this.reachableByFlowOnly = reachableByFlowOnly;
		this.initial = initial;
		this.authId = authId;
		this.menuIds = StringFormatterUtil.convertStringToList(menuIds, ',');
	}

	/**
	 * Returns state name that is defined for the state.
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the state type. Can be one of the following: {@link STATE_TYPE_SIMPLE}, 
	 * {@link STATE_TYPE_SIMPLE}, {@link STATE_TYPE_SUB_FLOW}, {@link STATE_TYPE_FINAL}.
	 * @return int type
	 */
	public abstract int getType();

	/**
	 * Indicates if the state is the initial state for the flow.
	 */
	public boolean isInitialState()
	{
		return initial;
	}

	/**
	 * Returns the {@link Context} name defined for the state.
	 * @return String {@link Context} name.
	 */
	public String getContextName()
	{
		return contextName;
	}

	/**
	 * Returns the input formatter that is defined for the state.
	 * @return Formatter
	 */
	public Formatter getInFormatter()
	{
		return inFormatter;
	}

	/**
	 * Returns the output formatter that is defined for the state.
	 * @return Formatter
	 */
	public Formatter getOutFormatter()
	{
		return outFormatter;
	}

	/**
	 * Returns the {@link Validator} that is defined for the state.
	 * @return {@link Validator}
	 */
	public Validator getValidator()
	{
		return validator;
	}

	/**
	 * Returns the entry actions list that is defined for the state.
	 * @return {@link ActionList}
	 */
	public ActionList getEntryActions()
	{
		return entryActions;
	}

	/**
	 * Returns the exit actions list that is defined for the state.
	 * {@link ActionList}
	 */
	public ActionList getExitActions()
	{
		return exitActions;
	}

	/**
	 * Returns the transitions map that is defined for the state.
	 * @return {@link TransitionsListMap}
	 */
	public TransitionsListMap getTransitions()
	{
		return transitions;
	}

	/**
	 * Returns the default transition that is defined for the state.
	 * @return {@link Transition}
	 */
	public Transition getDefaultTransition()
	{
		return transitions.getDefaultTransition();
	}

	/**
	 * Indicates if the state can be reached only by his holding {@link Flow}.
	 * @return boolean  
	 */
	public boolean isReachableByFlowOnly()
	{
		return reachableByFlowOnly;
	}

	/**
	 * returns authorization id declared in the state.
	 * @return String authId 
	 */
	public String getAuthId() 
	{
		return authId;
	}
	
	/**
	 * Returns the menu id's associated with the state.
	 * @return List
	 */
	public List getMenuIds()
	{
		return menuIds;
	}

	/**
	 * Sets the transitions map that is defined for the state.
	 * @param map TransitionsListMap
	 */
	public void setTransitions(TransitionsListMap map)
	{
		transitions = map;
	}

	/**
	 * Return a string that represents the current object.  
	 * @return String 
	 */
	public String toString()
	{
		return "State name [" + name + "]";
	}

}
