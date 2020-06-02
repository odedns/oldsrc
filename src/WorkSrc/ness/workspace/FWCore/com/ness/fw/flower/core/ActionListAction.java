/*
 * Created on: 25/6/2003
 * Author: yifat har-nof
 * @version $Id: ActionListAction.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;


/**
 * The {@link ActionListAction} used to abstract list of {@link Action}s. 
 * The {@link ActionListAction} can be used as any action of any other type. 
 * While executed - executes all its contained {@link Action}s one by one.
 */
public class ActionListAction extends Action
{
	/**
	 * The list of contained {@link Action}s.
	 */
	private ActionList actionsList;

	/**
	 * The constructor used while creating {@link Action}'s instance.
	 *
	 * @param actionsList The list of contained {@link Action}s.
	 */
	public ActionListAction(ActionList actionsList)
	{
		super(null, null, null);

		this.actionsList = actionsList;
	}

	/**
	 * Executes all the contained {@link Action}s one by one.
  	 * @param definitionContext The context the {@link Action} is defined in (for example service is usually
	 * defined at any {@link Context})
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @param parameterList The parameter list to pass the operation.
	 * @param authLevel The current authorization level.
	 * @throws ActionException Should be thrown by {@link Action} implementator when any exception is occured
	 * while {@link Action} run
	 * @throws ValidationException Should be thrown by {@link Action}'s implementator to indicate that some
	 * fields are missing or malformed.
	 */
	protected final void performExecution(Context parentContext, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList, int authLevel) throws ActionException, ValidationException
	{
		//run over the list of actions and execute each of them
		for (int i = 0; i < actionsList.getActionsCount(); i++)
		{
			actionsList.getAction(i).execute(parentContext, authLevel);
		}
	}
}
