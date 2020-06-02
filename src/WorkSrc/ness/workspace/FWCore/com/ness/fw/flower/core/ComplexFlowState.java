/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ComplexFlowState.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * State type simple. The commonly used type of state that represents state of flow 
 * that correspond to page displayed by browser or activating an {@link ActivityImpl} 
 * (in non-interactive mode or when the state does not contain a declaration of a page).
 * The state can also run a new sub flow.
 * Has additional attribute - <code>SubFlowData</code>
 */
public class ComplexFlowState extends FlowStateImpl
{
	/**
	 * SubFlow information to create and executed the sub flow while move to the state.
	 */
	private SubFlowData subFlowData;

	/**
	 * The page name that is defined for the state.
	 */
	private String page;

	/**
	 * The <code>ActivityImpl</code> that is defined for the state.
	 */
	private Activity activity;
	
	/**
	 * A List of <code>ButtonSet</code> that is defined for the state.
	 */
	private ButtonSetList buttonSetList;
	
	/**
	 * Indicates if the state should wait for event or should choose a transition automatically.
	 */
	private boolean isWaitingForEvent;

	/**
	 * Indicates if the state should be visible in the tab system.
	 */
	private boolean visible;

	/**
	 * The constructor used while creating <code>FlowState</code>'s instance.  
	 * 
	 * @param name The state name.
	 * @param contextName The <code>Context</code> name.
	 * @param page The page name.
	 * @param inFormatter The input <code>Formatter</code>.
	 * @param outFormatter The output <code>Formatter</code>.
	 * @param validator The state <code>Validator</code>. 
	 * @param entryActions The entry <code>ActionList</code>.
	 * @param transitions The <code>TransitionsListMap</code>.
	 * @param exitActions The exit <code>ActionList</code>.
	 * @param reachableByFlowOnly Indicates if the state can be reached only by his holding <code>Flow</code>.
	 * @param initial Indicates if the state is the initial state for the <code>Flow</code>.
	 * @param <code>SubFlowData</code> Subflow information to create and executed the sub flow while move to the state.
	 * @param activity The <code>ActivityImpl</code>.
	 * @param ButtonSetList The <code>ButtonSet</code> List.
	 * @param transitionSupplierName The name of the <code>ExtendedTransitionSupplier</code>.
	 * @param isWaitingForEvent Indicates if the state is waiting for event or should choose a transition automatically.
	 * @param visible Indicates if the state should be visible in the tab system. 
 	 * @param authId The authorization id declared in the state. 
	 */
	public ComplexFlowState(String name, String contextName, String page, Formatter inFormatter, Formatter outFormatter, Validator validator, ActionList entryActions, TransitionsListMap transitions, ActionList exitActions, boolean reachableByFlowOnly, boolean initial, SubFlowData subFlowData, Activity activity, ButtonSetList buttonSetList, String transitionSupplierName, boolean isWaitingForEvent, boolean visible, String authId)
	{
		super(name, contextName, inFormatter, outFormatter, validator, entryActions, transitions, exitActions, transitionSupplierName, reachableByFlowOnly, initial, authId);

		this.page = page;
		this.subFlowData = subFlowData;
		this.activity = activity;
		this.buttonSetList = buttonSetList;
		this.isWaitingForEvent = isWaitingForEvent;
		this.visible = visible;
	}

	/**
	 * Returns the state type <code>STATE_TYPE_COMPLEX</code>.
	 * @return int type
	 */
	public int getType()
	{
		return STATE_TYPE_COMPLEX;
	}

	/**
	 * Returns the page name that is defined for the state.
	 * @return String 
	 */
	public String getPage()
	{
		return page;
	}

	/**
	 * Returns true when the state contains a page.
	 * @return boolean 
	 */
	public boolean isStateContainsPage()
	{
		return page != null;
	}

	/**
	 * Returns the SubFlow information to create and executed the sub flow while move to the state.
	 * @return <code>SubFlowData</code>
	 */
	public SubFlowData getSubFlowData()
	{
		return subFlowData;
	}

	/**
	 * Returns true when the state contains a sub flow.
	 * @return boolean 
	 */
	public boolean isStateContainsSubFlow()
	{
		return subFlowData != null;
	}

	
	/**
	 * Retuns the <code>ActivityImpl</code> that is defined for the state.
	 * @return <code>ActivityImpl</code>
	 */
	public Activity getActivity()
	{
		return activity;
	}

	/**
	 * Returns true when the state contains an activity.
	 * @return boolean 
	 */
	public boolean isStateContainsActivity()
	{
		return activity != null;
	}

	/**
	 * Retuns the <code>ButtonSet</code> with the ButtonSet that defined for the state.
	 * @return <code>ButtonSet</code>
	 */
	public ButtonSet getButtonSet(String group)
	{
		if(buttonSetList != null)
			return buttonSetList.getButtons(group);
		else
			return null;
	}

	/**
	 * Indicates if the state is waiting for event or should choose a transition automatically. 
	 * @return boolean  
	 */
	public boolean isStateWaitingForEvent()
	{
		return isWaitingForEvent || page != null;
	}

	/**
	 * Indicates if the state should be visible in the tab system. 
	 * @return boolean  
	 */
	public boolean isVisible()
	{
		return visible;
	}


}
