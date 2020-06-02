/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: OperationAction.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.logger.Logger;

/**
 * Implementation of <code>Action</code> that encapsulates <code>Operation</code> inside
 */
public class OperationAction extends Action
{
	public static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "OPERATION";

	/**
	 * The <code>Operation</code>
	 */
    private Operation operation;

	/**
	 * 
	 * @param operation The operation to execute from the action.
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @param parameterList The parameter list to pass the operation.
	 */
	public OperationAction(Operation operation, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList)
	{
		super(inFormatter, outFormatter, parameterList);

		this.operation = operation;
	}

	/**
	 * Used by <code>Action</code> while framework call to <code>execute</code> method
	 *
	 * @param parentContext in the case the same as <code>ctxToRunOn</code>
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @param parameterList The parameter list to pass the operation.
	 * @param authLevel The current authorization level.
	 * @throws ActionException
	 * @throws ValidationException
	 * @throws OperationAction
	 */
	protected final void performExecution(Context parentContext, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList, int authLevel) throws ActionException, ValidationException
	{
		try
		{
			Logger.debug(LOGGER_CONTEXT, "Execute operation [" + operation.getName() + "]");
			operation.execute(parentContext, inFormatter, outFormatter, parameterList, authLevel);
		}
		catch (ValidationException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw new ActionException("Unable to execute operation [" + operation.getName() + "].", ex);
		}
	}
		
}
