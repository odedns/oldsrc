/*
 * Created on: 25/6/2003
 * Author: yifat har-nof
 * @version $Id: Action.java,v 1.2 2005/03/29 17:44:41 yifat Exp $
 */
package com.ness.fw.flower.core;

/**
 * The abstract {@link Action} class provides abstraction for all action type entities (eg. Operation, Service ...)
 * of the flow.
 *
 * In addition the class provides uniform implementation to process Action's Contexts and to
 * run Action's Validators
 *
 * Only one method - performExecution should be overriden by action implementator to
 * create new kind of Action
 */
public abstract class Action
{
	
	/**
	 * The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 */
	private Formatter inFormatter;

	/**
	 * The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 */
	private Formatter outFormatter;

	/**
	 * The activity type performed in the <code>Operation</code>. 
	 * Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	private int activityType;

	/**
	 * The parameter list to pass the operation / service method.
	 */
	private ParameterList parameterList;

	/**
	 * The constructor used while creating {@link Action}'s instance.
	 */
	public Action(Formatter inFormatter, Formatter outFormatter, ParameterList parameterList)
	{
		this.inFormatter = inFormatter;
		this.outFormatter = outFormatter;
		this.parameterList = parameterList;
	}

	/**
	 * The final method to be called by framework only to execute the {@link Action}.
	 *
	 * @param parentContext The {@link Context} of parent entity (eg. {@link Context} of state or flow...)
	 * @param authLevel The current authorization level.
	 * @throws ActionException thrown by {@link Action} implementation.
	 * @throws ValidationException thrown when {@link Action}'s {@link Validator} was not passed. Can
	 * also be thrown by {@link Action}'s implementation.
	 */
	public final void execute(Context parentContext, int authLevel) throws ActionException, ValidationException
	{
		try{
			
			//performing execution - executing overriden abstract method
			performExecution(parentContext, inFormatter, outFormatter, parameterList, authLevel);
		}
		catch (ValidationException ex)
		{
			//pass the exception to the Flow - to indicate missing or malformed fields.
			throw ex;
		}
		catch (Throwable ex)
		{
            throw new ActionException("Unable to execute action.", ex);
		}
	}

	/**
	 * The abstract method to be implemented by {@link Action}'s implementators.
	 *
 	 * @param definitionContext The context the {@link Action} is defined in (for example service is usually
	 * defined at any {@link Context})
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @param parameterList The parameter list to pass the operation / service method.
	 * @param authLevel The current authorization level.
	 * @throws ActionException Should be thrown by {@link Action} implementator when any exception is occured
	 * while {@link Action} run
	 * @throws ValidationException Should be thrown by {@link Action}'s implementator to indicate that some
	 * fields are missing or malformed.
	 */
	protected abstract void performExecution(Context definitionContext, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList, int authLevel) throws ActionException, ValidationException;
	
	/**
	 * Returns the {@link Formatter} that performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @return {@link Formatter}
	 */
	public Formatter getInFormatter()
	{
		return inFormatter;
	}

	/**
	 * Returns the {@link Formatter} that performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @return {@link Formatter}
	 */
	public Formatter getOutFormatter()
	{
		return outFormatter;
	}
	
	/**
	 * Returns the activity type performed in the <code>Action</code>
	 * @return int Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public int getActivityType()
	{
		return activityType;
	}

	/**
	 * Returns the parameter list to pass the operation / service method.
	 * @return ParameterList
	 */
	public ParameterList getParameterList()
	{
		return parameterList;
	}

}