/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FinalFlowState.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;


/**
 * Special kind of <code>FlowState</code>. Engaging this state by flow indicates to parent flow
 * that the current flow is finished its run.
 * <p>
 * The name of <code>FinalFlowState</code> used by parent flow as event name. For
 * example: if the name of <code>FinalFlowState</code> is "Ok" that parent flow at its current state must have
 * transition with event name "Ok"
 * </p>
 */
public class FinalFlowState extends FlowStateImpl
{
	
	/**
	 * The constructor used while creating <code>FlowState</code>'s instance.  
	 * All other attributes that usually defined for states of other types are not relevant here.  
	 * 
	 * @param name The state name.
 	 * @param authId The authorization id declared in the state. 
	 */
	public FinalFlowState(String name, String authId)
	{
		super(name, null, null, null, null, null, null, null, null, false, false, authId);
	}

	/**
	 * Returns the state type <code>STATE_TYPE_FINAL</code>.
	 * @return int type
	 */
	public int getType()
	{
		return STATE_TYPE_FINAL;
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
