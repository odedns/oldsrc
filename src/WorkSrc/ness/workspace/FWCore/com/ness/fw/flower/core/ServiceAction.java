/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ServiceAction.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.logger.Logger;

/**
 * Implementation of <code>Action</code> that encapsulates <code>Service</code>
 */
public class ServiceAction extends Action
{
	public static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "SERVICE";
	
	/**
	 * The name of service as defined in context
	 */
	private String serviceName;

	/**
	 * The name of service method to execute
	 */
	private String methodName;

	/**
	 * Creates new ServiceAction object.
	 * @param serviceName The name of service as defined in context
	 * @param methodName The name of service method to execute
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 */
	public ServiceAction(String serviceName, String methodName, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList)
	{
		super(inFormatter, outFormatter, parameterList);

		this.methodName = methodName;
		this.serviceName = serviceName;
	}

	/**
	 * Used by <code>Action</code> while framework call to <code>execute</code> method
	 *
	 * @param parentContext in which the <code>Service</code> is defined
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @param parameterList The parameter list to pass the service method.
	 * @param authLevel The current authorization level.
	 */
	protected final void performExecution(Context parentContext, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList, int authLevel) throws ActionException, ValidationException
	{		
		try
		{
			Logger.debug(LOGGER_CONTEXT, "Execute method [" + methodName + "] in service [" + serviceName + "]");
			Service service = parentContext.getService(serviceName);
			service.executeService(methodName, parentContext, inFormatter, outFormatter, parameterList, authLevel);
        }
        catch (ValidationException ex)
        {
	        throw ex;
        }
		catch (Throwable ex)
        {
	        throw new ActionException("unable to execute service [" + serviceName + "] with method [" + methodName + "].", ex);
        }
		
	}

}
