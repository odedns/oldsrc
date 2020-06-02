/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Transition.java,v 1.2 2005/03/29 14:57:08 yifat Exp $
 */
package com.ness.fw.flower.core;

/**
 * Provides abstraction for transition between states in the flow.
 *
 * <br>
 * Consists of two major attributes:
 *
 * <ul>
 * <li>event name - name of event that toggles the transition
 * <li>guard condition - boolean expression that guards the transition
 * </ul>
 *
 * <br>
 * Both these attributes define when the transition can be toggled
 */
public class Transition
{
	/**
	 * Name of event that toggles the transition
	 */
	private String eventName;

	/**
	 * boolean expression that guards the transition
	 */
	private Guard guard;

	/**
	 * Name of context that is defined for the transition
	 */
	private String contextName;

	/**
	 * Formatter that is defined as input formatter for the transition.
	 */
    private Formatter inFormatter;

	/**
	 * Formatter that is defined as output formatter for the transition.
	 */
	private Formatter outFormatter;

	/**
	 * Validator that is defined for the transition
	 */
	private Validator validator;

	/**
	 * State to which the flow should move after toggling the transition
	 */
    private String targetState;
	private String alternativeTargetState;


	/**
	 * list of actions to be executed while toggling the transition
	 */
	private ActionList actions;

	/**
	 * list of actions to be executed when a ValidationException thrown from the transition.
	 */
	private ActionList validationExceptionActions;

	/**
	 * list of actions to be executed when the transition is choosen, after the Validator execution.
	 * The actions will be performed even if the validator is thrown ValidationException.
	 */
	private ActionList validationActions;

	/**
	 * indicates is the transition defined as internal (only self transition can be derfined as internal. when transition
	 * is internal no exit or entry actions will be performed)
	 */
	private boolean internal;

	/**
	 * indicates is the transition will be used as default transition for the state.
	 */
	private boolean defaultTransition;

	private boolean traverse;

	private SubFlowDataList subFlowDataList;

	private boolean handleValidationError;

	public Transition(String eventName, Guard guard, String contextName, Formatter inFormatter, Formatter outFormatter, Validator validator, String targetState, String alternativeTargetState, boolean handleValidationError, ActionList actions, ActionList validationExceptionActions, ActionList validationActions, boolean internal, SubFlowDataList subFlowDataList, boolean defaultTransition, boolean traverse)
	{
		this.actions = actions;
		this.validationExceptionActions = validationExceptionActions;
		this.validationActions = validationActions;
		this.contextName = contextName;
		this.eventName = eventName;
		this.guard = guard;
		this.inFormatter = inFormatter;
		this.outFormatter = outFormatter;
		this.targetState = targetState;
		this.validator = validator;
		this.internal = internal;
		this.subFlowDataList = subFlowDataList;
		this.defaultTransition = defaultTransition;
		this.traverse = traverse;
		this.alternativeTargetState = alternativeTargetState;
		this.handleValidationError = handleValidationError;
	}

	public Transition(String eventName, Guard guard, String targetState, boolean handleValidationError, boolean internal, SubFlowDataList subFlowDataList, boolean defaultTransition, boolean traverse)
	{
		this.eventName = eventName;
		this.guard = guard;
		this.targetState = targetState;
		this.internal = internal;
		this.subFlowDataList = subFlowDataList;
		this.defaultTransition = defaultTransition;
		this.traverse = traverse;
		this.handleValidationError = handleValidationError;
	}
	
	public ActionList getActions()
	{
		return actions;
	}

	public String getContextName()
	{
		return contextName;
	}

	public String getEventName()
	{
		return eventName;
	}

	public Guard getGuard()
	{
		return guard;
	}

	public Formatter getInFormatter()
	{
		return inFormatter;
	}

	public Formatter getOutFormatter()
	{
		return outFormatter;
	}

	public String getTargetState()
	{
		return targetState;
	}

	public Validator getValidator()
	{
		return validator;
	}

	public boolean isInternal()
	{
		return internal;
	}

	public boolean isDefaultTransition()
	{
		return defaultTransition;
	}

	public SubFlowDataList getSubFlowDataList()
	{
		return subFlowDataList;
	}

	public boolean isTraverse()
	{
		return traverse;
	}

	public String getAlternativeTargetState()
	{
		return alternativeTargetState;
	}

	public boolean isHandleValidationError()
	{
		return handleValidationError;
	}

	/**
	 * Return list of actions to be executed when a ValidationException thrown from the transition.
	 * @return ActionList
	 */
	public ActionList getValidationExceptionActions()
	{
		return validationExceptionActions;
	}

	/**
	 * Return list of actions to be executed when the transition is choosen, after the Validator execution.
	 * The actions will be performed even if the validator is thrown ValidationException.
	 * @return ActionList
	 */
	public ActionList getValidationActions()
	{
		return validationActions;
	}

	public String toString()
	{
		return "Transition Event [" + eventName + "] guard [" + (guard == null ? "null" : guard.toString()) + "] contextName [" + contextName + "] inFormatter [" + (inFormatter == null ? "null" : inFormatter.getName()) + "] outFormatter [" + (outFormatter == null ? "null" : outFormatter.getName()) + "] validator [" + (validator == null ? "null" : validator.getName()) + "] targetState [" + targetState + "] alternativeTargetState [" + alternativeTargetState + "] handleValidationError [" + handleValidationError + "] internal [" + internal + "] defaultTransition [" + defaultTransition + "] traverse [" + traverse + "]";
	}

}
