/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Service.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.flower.factory.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.flower.util.AuthLevelsManager;
import com.ness.fw.shared.common.SystemConstants;

import java.lang.reflect.*;

/**
 * Provides abstraction for service.
 * Service is an piece of code that is statefull and initiallised by <code>Context</code>. If defined at static context - initialized once per
 * application. If defined at dynamic context initialixed every time context is initialised.
 */
public abstract class Service
{
	
	/**
	 * Constant for readWrite activity type. 
	 * It means the activities that will be performed in the current service will 
	 * performs updates to the DB and should not be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int ACTIVITY_TYPE_READWRITE = SystemConstants.EVENT_TYPE_READWRITE;
	
	/**
	 * Constant for readOnly activity type. 
	 * It means the activities that will be performed in the current service will not 
	 * perform updates to the DB and could be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int ACTIVITY_TYPE_READONLY = SystemConstants.EVENT_TYPE_READONLY;
	
	/**
	 * Name of service as defined in XML
	 */
	private String name;

	/**
	 * The name for {@link Context} to be created for the <code>Service</code> method 
	 * while runtime, if a specific context for the service method was not declared.
	 */
	private String defualtMethodContextName;

	/**
	 * Validator (optionally) to validate Context data before execution
	 */
	private Validator validator;

	/**
	 * The activity type performed in the <code>Service</code>. 
	 * Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	private int activityType;
	
	/**
	 * A List of the {@link ServiceMethodData} used to execute the methods in the service. 
	 */
	private ServiceMethodDataList serviceMethodDataList;  

	/**
	 * Called by framework while initializing
	 * @param parameters list of parameters as defined in XML
	 */
	public abstract void initialize(ParameterList parameters) throws ServiceException;

	/**
	 * used for cleanup while destroy context
	 */
	public abstract void destroy();

	/**
	 * Used by <code>Action</code> while framework call to <code>execute</code> method
	 *
	 * @param methodName The method to execute in the service. 
	 * @param parentContext in which the <code>Service</code> is defined
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @param parameterList The parameter list to pass the service method.
	 * @param authLevel The current authorization level.
	 */
	protected final void executeService(String methodName, Context parentContext, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList, int authLevel) throws ServiceException, ValidationException
	{
		//if no context is specified the context of will be used
		Context localContext = parentContext;
		
		try
		{
			String localContextName = getContextName(methodName);
			
			//if context id specified it will be created and chained
			if (localContextName != null)
			{
				//create context
				localContext = FlowElementsFactory.getInstance().createContext(localContextName, parentContext);

				//formatting (copy fields from parent context to the context of Action)
				if (inFormatter != null)
				{
					inFormatter.format(parentContext, localContext);
				}
			}
	        
			executeServiceInternal(localContext, methodName, parameterList, authLevel);
	        
			//if local context is not the same sa a context of parent formatting back
			if (outFormatter != null && localContextName != null)
			{
				//copy fields from the Action's context to context of parent
				outFormatter.format(localContext, parentContext);
			}

			//destroy context
			if (localContext != parentContext)
			{
				localContext.destroy();
			}
	        
		}
		catch (ValidationException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw new ServiceException("unable to execute service [" + getName() + "] with method [" + methodName + "].", ex);
		}
	}

	/**
	 * Called by <code>ServiceAction</code> or by custom code while executing service.
	 *
	 * @param ctx context to run on
	 * @param methodName name of method to be executed
	 * @param authLevel The current authorization level.
	 */
	private void executeServiceInternal(Context ctx, String methodName, ParameterList parameterList, int authLevel) throws ServiceException, ValidationException, FlowElementsFactoryException, ContextException, FormatterException, ValidationProcessException, FlowAuthorizationException
	{
		ServiceMethodData methodData = getServiceMethodData(methodName);
		if(methodData == null)
		{
			throw new ServiceException("Unable to execute service. Invalid method name [" + methodName + "].");		
		}

		// check authorization level
		if(authLevel != AuthLevelsManager.AUTH_LEVEL_ALL)
		{
			// check all service auth level
			if(activityType == ACTIVITY_TYPE_READWRITE)
			{
				throw new FlowAuthorizationException ("The service [" + getName() + "] is not authorized to perform the action.");
			}
			
			// check service method auth level
			if(methodData.getActivityType() == ACTIVITY_TYPE_READWRITE)
			{
				throw new FlowAuthorizationException ("The method [" + methodData.getName() + "] in service [" + getName() + "] is not authorized to perform the action.");
			}
		}
		
		// retrieving method
		Method method;
		try
		{
			Class[] methodArgumentsTypes = new Class[]{Context.class, ParameterList.class};
			method = this.getClass().getMethod(methodData.getExecutionMethod(), methodArgumentsTypes);
		} catch (Throwable ex)
		{
			throw new ServiceException("Unable to execute service. Invalid method name [" + methodName + "].", ex);
		}


		// validating the context data with the general validator for the service methods.
		if (validator != null)
		{
            validator.validate(ctx);
		}

		// validating the context data with the specific validator for the method.
		if (methodData.getValidator() != null)
		{
			methodData.getValidator().validate(ctx);
		}


		// executing the method
		try
		{
			Object[] methodArguments = new Object[]{ctx, parameterList};

			method.invoke(this, methodArguments);
		}
		catch (InvocationTargetException ex)
		{
			Throwable th = ex.getTargetException();
			if (th instanceof ValidationException)
			{
				throw (ValidationException)th;
			}
			else
			{
				throw new ServiceException("Unable to execute service. Execution method name [" + methodName + "].", ex.getTargetException());
			}
		} 
		catch (Throwable ex)
		{
			throw new ServiceException("Unable to execute service. Execution method name [" + methodName + "].", ex);
		}
	}

	/**
	 * Returns the name of service as defined in XML
	 * @return String name
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * Sets the name of service as defined in XML
	 * @param String name
	 */
	public final void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Returns the {@link Validator} (optionally) to validate Context data before execution.
	 * @return Validator
	 */
	public final Validator getValidator()
	{
		return validator;
	}

	/**
	 * Sets  the {@link Validator} (optionally) to validate Context data before execution. 
	 * @param validator 
	 */
	public final void setValidator(Validator validator)
	{
		this.validator = validator;
	}
	
	/**
	 * Returns the activity type performed in the <code>Service</code>
	 * @return int Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public final int getActivityType()
	{
		return activityType;
	}

	/**
	 * Sets the activity type performed in the <code>Service</code>.
	 * @param activityType Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public final void setActivityType(int activityType)
	{
		this.activityType = activityType;
	}
	
	/**
	 * Sets the List of the {@link ServiceMethodData} used to execute the 
	 * methods in the service.
	 * @param list ServiceMethodDataList
	 */
	public final void setServiceMethodDataList(ServiceMethodDataList list)
	{
		serviceMethodDataList = list;
	}

	/**
	 * Returns the List of the {@link ServiceMethodData} used to execute the 
	 * methods in the service.
	 * @return ServiceMethodDataList
	 */
	private ServiceMethodDataList getServiceMethodDataList()
	{
		return serviceMethodDataList;
	}

	/**
	 * Returns the data about the service method.
	 * @return ServiceMethodData
	 */
	private ServiceMethodData getServiceMethodData(String methodName) throws ServiceException
	{
		return serviceMethodDataList.getServiceMethodData(methodName);
	}

	/**
	 * Returns the name for {@link Context} to be created for the <code>Service</code> method 
	 * while runtime, if a specific context for the service method was not declared.
	 * @return String default context name
	 */
	public String getDefualtMethodContextName()
	{
		return defualtMethodContextName;
	}

	/**
	 * Sets the name for {@link Context} to be created for the <code>Service</code> method 
	 * while runtime, if a specific context for the service method was not declared.
	 * @param String default context name
	 */
	public void setDefualtMethodContextName(String defualtMethodContextName)
	{
		this.defualtMethodContextName = defualtMethodContextName;
	}
	
	/**
	 * Returns the name for {@link Context} to be created for the {@link Action} while runtime.
	 * @param methodName The method to execute
	 * @return String Context name
	 */
	private String getContextName(String methodName) throws ServiceException
	{
		String contextName;
		ServiceMethodData methodData = getServiceMethodData(methodName);
		if(methodData.getContextName() == null)
		{
			contextName = getDefualtMethodContextName();
		}
		else
		{
			contextName = methodData.getContextName();
		}
		return contextName;
	}

	/**
	 * Execute a service method of the current service instance. 
	 * Format the data in & out of the context according to the given formatters.
	 * @param parentContext in which the <code>Service</code> is defined
	 * @param methodName The method to execute in the service. 
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the service method {@link Context}, before processing the service.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the service method {@link Context} 
	 * to the parent {@link Context}, after processing the service.
	 * @throws ServiceException
	 * @throws ValidationException
	 * @throws FlowElementsFactoryException
	 * @throws ResourceException
	 */
	private void executeMethod(Context parentContext, String methodName, Formatter inFormatter, Formatter outFormatter) throws ServiceException, ValidationException, FlowElementsFactoryException, ResourceException
	{
		int authLevel = ApplicationUtil.getAuthLevelsManager(parentContext).getAuthLevel(getClass().getName()).getAuthLevel();
		executeService(methodName, parentContext, inFormatter, outFormatter, null, authLevel);	
	}

//	/**
//	 * Execute a service method of the current service instance. 
//	 * @param parentContext in which the <code>Service</code> is defined
//	 * @param methodName The method to execute in the service. 
//	 * @throws ServiceException
//	 * @throws ValidationException
//	 * @throws FlowElementsFactoryException
//	 * @throws ResourceException
//	 */
//	public final void executeMethod(Context parentContext, String methodName) throws ServiceException, ValidationException, FlowElementsFactoryException, ResourceException
//	{
//		executeMethod(parentContext, methodName, (Formatter)null, (Formatter)null); 
//	}
//
//	/**
//	 * Execute a service method of the current service instance. 
//	 * Format the data in & out of the context according to the given complex foramtter.
//	 * @param parentContext in which the <code>Service</code> is defined
//	 * @param methodName The method to execute in the service. 
//	 * @param complexFormatterName The {@link ComplexFormatter} performs the field’s 
//	 * copy from the parent {@link Context} to the service method {@link Context}, 
//	 * before processing the service. And performs the field’s copy from the service 
//	 * method {@link Context} to the parent {@link Context}, after processing the service.
//	 * @throws ServiceException
//	 * @throws ValidationException
//	 * @throws FlowElementsFactoryException
//	 * @throws ResourceException
//	 */
//	public final void executeMethod(Context parentContext, String methodName, String complexFormatterName) throws ServiceException, ValidationException, FlowElementsFactoryException, ResourceException
//	{
//		ComplexFormatter formatter = FlowElementsFactory.getInstance().createComplexFormatter(complexFormatterName);
//		executeMethod(parentContext, methodName, formatter.getInFormatter(), formatter.getOutFormatter()); 
//	}
//
//	/**
//	 * Execute a service method of the current service instance. 
//	 * Format the data in & out of the context according to the given formatters.
//	 * @param parentContext in which the <code>Service</code> is defined
//	 * @param methodName The method to execute in the service. 
//	 * @param inFormatterName The {@link Formatter} performs the field’s copy from the parent {@link Context} 
//	 * to the service method {@link Context}, before processing the service.
//	 * @param outFormatterName The {@link Formatter} performs the field’s copy from the service method {@link Context} 
//	 * to the parent {@link Context}, after processing the service.
//	 * @throws ServiceException
//	 * @throws ValidationException
//	 * @throws FlowElementsFactoryException
//	 * @throws ResourceException
//	 */
//	public final void executeMethod(Context parentContext, String methodName, String inFormatterName, String outFormatterName) throws ServiceException, ValidationException, FlowElementsFactoryException, ResourceException
//	{
//		Formatter inFormatter = null;
//		if(inFormatterName != null)
//			inFormatter = FlowElementsFactory.getInstance().createFormatter(inFormatterName);
//		
//		Formatter outFormatter = null;
//		if(outFormatterName != null)
//			outFormatter = FlowElementsFactory.getInstance().createFormatter(outFormatterName);
//			
//		executeMethod(parentContext, methodName, inFormatter, outFormatter); 
//	}

	/**
	 * Execute a service method of the current service instance. 
	 * @param parentContext in which the <code>Service</code> is defined
	 * @param methodName The method to execute in the service. 
	 * @throws ServiceException
	 * @throws ValidationException
	 * @throws FlowElementsFactoryException
	 * @throws ResourceException
	 */
	protected final void executeMethod(Context parentContext, ServiceMethodExecutionData exceutionData) throws ServiceException, ValidationException, FlowElementsFactoryException, ResourceException
	{
		if(exceutionData.getComplexFormatterName() != null)
		{
			ComplexFormatter formatter = FlowElementsFactory.getInstance().createComplexFormatter(exceutionData.getComplexFormatterName());
			executeMethod(parentContext, exceutionData.getMethodName(), formatter.getInFormatter(), formatter.getOutFormatter()); 
		}
		else if (exceutionData.getInFormatterName() != null || exceutionData.getOutFormatterName() != null)
		{
			Formatter inFormatter = null;
			if(exceutionData.getInFormatterName() != null)
				inFormatter = FlowElementsFactory.getInstance().createFormatter(exceutionData.getInFormatterName());
		
			Formatter outFormatter = null;
			if(exceutionData.getOutFormatterName() != null)
				outFormatter = FlowElementsFactory.getInstance().createFormatter(exceutionData.getOutFormatterName());
			
			executeMethod(parentContext, exceutionData.getMethodName(), inFormatter, outFormatter); 
		}
		else
		{
			executeMethod(parentContext, exceutionData.getMethodName(), (Formatter)null, (Formatter)null); 
		}
	}


}
