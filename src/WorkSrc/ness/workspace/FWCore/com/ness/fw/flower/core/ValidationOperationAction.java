/*
 * Created on: 29/03/2005
 * Author: yifat har-nof
 * @version $Id: ValidationOperationAction.java,v 1.1 2005/03/29 17:44:41 yifat Exp $
 */
package com.ness.fw.flower.core;

/**
 * 
 */
public class ValidationOperationAction extends OperationAction
{

	/**
	 * Indicates wheater to stop the action list execution if the action 
	 * throw ValidationException.
	 * Used for the validation actions on the transition.  
	 */	
	private Boolean stopValidationOnError;

	/**
	 * @param operation
	 * @param inFormatter
	 * @param outFormatter
	 * @param parameterList
	 * @param stopValidationOnError
	 */
	public ValidationOperationAction(
		Operation operation,
		Formatter inFormatter,
		Formatter outFormatter,
		ParameterList parameterList,
		Boolean stopValidationOnError)
	{
		super(
			operation, inFormatter, outFormatter, parameterList);

		this.stopValidationOnError = stopValidationOnError;
	}
	
	/**
	 * Indicates wheater to stop the action list execution if the action 
	 * throw ValidationException.
	 * Used for the validation actions on the transition.
	 * @return boolean
	 */
	public boolean isStopValidationOnError()
	{
		return stopValidationOnError != null && stopValidationOnError.booleanValue();
	}
	
}
