/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Activity.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.util.AuthLevelsManager;
import com.ness.fw.shared.common.SystemConstants;

/**
 * ActivityImpl is alternative way to execute state of page kind. 
 * Usually attached to state of type page.
 *
 * To implement activity new class extending {@link ActivityImpl} should be created. 
 * The {@link execute} method of activitiy is called by framework when appropriate 
 * state of flow is reached and the flow is running in non-interactive mode or 
 * if there is no page defined in the state. 
 * {@link ActivityCompletionEvent} returned by {@link ActivityImpl} will navigate the rest of the flow.
 */
public abstract class Activity
{
	/**
	 * Constant for readWrite activity type. 
	 * It means the activities that will be performed in the current activity will 
	 * performs updates to the DB and should not be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int ACTIVITY_TYPE_READWRITE = SystemConstants.EVENT_TYPE_READWRITE;
	
	/**
	 * Constant for readOnly activity type. 
	 * It means the activities that will be performed in the current activity will not 
	 * perform updates to the DB and could be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int ACTIVITY_TYPE_READONLY = SystemConstants.EVENT_TYPE_READONLY;
	
	/**
	 * The {@link Validator} that defined for the activity.
	 */
	private Validator validator;
	
	/**
	 * The name of the activiy.
	 */
	private String name;

	/**
	 * The activity type performed in the <code>ActivityImpl</code>. 
	 * Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	private int activityType;

	/**
	 * Called by framework when state is reached. Performs validation and call to user 
	 * overriden {@link execute} method.
	 *
	 * @param ctx Context to run on.
	 * @param authLevel The current authorization level.
	 * @return {@link ActivityCompletionEvent} that will navigate the rest of the flow. 
	 * (The event created by user overriden {@link execute} method).
	 * @throws ActivityException thrown when activity fails to execute.
	 */
	public final ActivityCompletionEvent executeActivity(Context ctx, int authLevel) throws ActivityException, FlowAuthorizationException
	{
		try
		{
			// check authorization level
			if(activityType == ACTIVITY_TYPE_READWRITE && authLevel != AuthLevelsManager.AUTH_LEVEL_ALL)
			{
				throw new FlowAuthorizationException ("The activity " + getName() + " is not authorized to perform the action.");
			}
			
			//performing validation
			if (validator != null)
			{
				validator.validate(ctx);
			}

			//actual execute
			return execute(ctx);
		} catch (FlowerException ex)
		{
			throw new ActivityException("Validation exception is thrown", ex);
		}
	}

	/**
	 * Returns the name of the activiy.
	 * @return String name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the activiy.
	 * @param String The name to set. 
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	
	/**
	 * Sets the {@link Validator} that defined for the activity.
	 * @param {@link Validator}
	 */
	public void setValidator(Validator validator)
	{
		this.validator = validator;
	}

	/**
	 * Returns the activity type performed in the <code>ActivityImpl</code>
	 * @return int Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public int getActivityType()
	{
		return activityType;
	}

	/**
	 * Sets the activity type performed in the <code>ActivityImpl</code>.
	 * @param activityType Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public void setActivityType(int activityType)
	{
		this.activityType = activityType;
	}


	/**
	 * Method that must be override by activity implementation to perform the activity process.
	 *
	 * @param ctx {@link Context} to run on.
	 * @return {@link ActivityCompletionEvent} that will navigate the rest of the flow
	 * @throws ActivityException thrown when fails to excecute activity.
	 * @throws ValidationException thrown when validation problem occures.
	 */
	public abstract ActivityCompletionEvent execute(Context ctx) throws ActivityException, ValidationException;

	/**
	 * Must be override by activity implementation. If no initialization is needed shouls have empty implementation.
	 *
	 * @param parameterList List of parameters accumulated from configuration (XML file)
	 * @throws ActivityException thrown if activity fails to be initialized
	 */
	public abstract void initialize(ParameterList parameterList) throws ActivityException;

}
