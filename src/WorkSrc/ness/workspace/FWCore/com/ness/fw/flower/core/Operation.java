/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Operation.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.flower.factory.FlowElementsFactory;
import com.ness.fw.flower.factory.FlowElementsFactoryException;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.flower.util.AuthLevelsManager;
import com.ness.fw.shared.common.SystemConstants;

/**
 * Provides abstraction for operation. Operation is a piece of java code that can be defined in XML and executed as <code>Action</code>
 * Operation is stateless and never initialized.
 */
public abstract class Operation
{
	
	/**
	 * Constant for readWrite activity type. 
	 * It means the activities that will be performed in the current operation will 
	 * performs updates to the DB and should not be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int ACTIVITY_TYPE_READWRITE = SystemConstants.EVENT_TYPE_READWRITE;
	
	/**
	 * Constant for readOnly activity type. 
	 * It means the activities that will be performed in the current operation will not 
	 * perform updates to the DB and could be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int ACTIVITY_TYPE_READONLY = SystemConstants.EVENT_TYPE_READONLY;

	/**
	 * The name for {@link Context} to be created for the <code>Operation</code> 
	 * while runtime.
	 */
	private String contextName;
	
	/**
	 * The name of the operation as defined in XML
	 */
	private String name;

	/**
	 * The {@link Validator} (optional) to validate <code>Context</code> data before execution
	 */
	private Validator validator;
	
	/**
	 * The activity type performed in the <code>Operation</code>. 
	 * Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	private int activityType;

	/**
	 * Used by framework while execution <code>Operation</code>
	 * @param parentContext context to run on
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the {@link Action}'s {@link Context}, before processing the action.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the {@link Action}'s {@link Context} 
	 * to the parent {@link Context}, after processing the action.
	 * @param parameterList The parameter list to pass the operation.
	 * @param authLevel The current authorization level.
	 * @throws ValidationProcessException
	 * @throws FlowAuthorizationException
	 * @throws OperationExecutionException
	 * @throws ValidationException
	 * @throws AuthorizationException
	 * @throws FatalException
	 */
	protected final void execute(Context parentContext, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList, int authLevel) throws ValidationProcessException, FlowAuthorizationException, OperationExecutionException, ValidationException, AuthorizationException, FatalException
	{
		//if no context is specified the context of will be used
		Context localContext = parentContext;
		
		try
		{
			String localContextName = getContextName();
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
			
			executeOperationInternal(localContext, parameterList, authLevel);
			
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
			throw new OperationExecutionException("Unable to execute operation [" + getName() + "].", ex);
		}
	}
	
	/**
	 * Used by framework while execution <code>Operation</code>
	 *
	 * @param ctx context to run on
	 * @param parameterList The parameter list to pass the operation.
	 * @param authLevel The current authorization level.
	 */
	private void executeOperationInternal(Context ctx, ParameterList parameterList, int authLevel) throws ValidationProcessException, FlowAuthorizationException, OperationExecutionException, ValidationException, AuthorizationException, FatalException
	{
		// check authorization level
		if(activityType == ACTIVITY_TYPE_READWRITE && authLevel != AuthLevelsManager.AUTH_LEVEL_ALL)
		{
			throw new FlowAuthorizationException ("The operation " + getName() + " is not authorized to perform the action.");
		}
		
		//performs validation only if Validator is defined
        if (validator != null)
        {
	        validator.validate(ctx);
        }

		//calling to user overriden method
		execute(ctx, parameterList);
	}

	/**
	 * Should be overriden by Operation creator. Method that performs the actual work.
	 *
	 * @param ctx <code>Context</code> to run on
	 * @param parameterList The parameter list to pass the operation.
	 * @throws OperationExecutionException
	 * @throws ValidationException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	protected abstract void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, ValidationException, FatalException, AuthorizationException;

	/**
	 * Returns the name of the operation as defined in XML
	 * @return String name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the <code>Validator</code> (optional) to validate <code>Context</code> data before execution
	 * @return Validator
	 */
	public Validator getValidator()
	{
		return validator;
	}

	/**
	 * Sets the name of the operation as defined in XML
	 * @param String name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Sets the <code>Validator</code> (optional) to validate <code>Context</code> data before execution
	 * @param validator Validator
	 */
	public void setValidator(Validator validator)
	{
		this.validator = validator;
	}
	
	/**
	 * Returns the activity type performed in the <code>Operation</code>
	 * @return int Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public int getActivityType()
	{
		return activityType;
	}
	
	/**
	 * Sets the activity type performed in the <code>Operation</code>.
	 * @param activityType Could be one of the following: <code>ACTIVITY_TYPE_READWRITE</code>, <code>ACTIVITY_TYPE_READWRITE</code>
	 */
	public void setActivityType(int activityType)
	{
		this.activityType = activityType;
	}

	/**
	 * Returns the name for {@link Context} to be created for the <code>Operation</code>. 
	 * @return String contextName
	 */
	public String getContextName()
	{
		return contextName;
	}

	/**
	 * Sets the name for {@link Context} to be created for the <code>Operation</code>. 
	 * @param String contextName
	 */
	public void setContextName(String contextName)
	{
		this.contextName = contextName;
	}

	/**
	 * Execute the operation. 
	 * Format the data in & out of the context according to the given formatters.
	 * @param parentContext The parent <code>Context</code> to use in the <code>Operation</code>.
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the operation {@link Context}, before processing the operation.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the operation {@link Context} 
	 * to the parent {@link Context}, after processing the operation.
	 * @throws ValidationException
	 * @throws OperationExecutionException
	 */
	private void executeOperation(Context parentContext, Formatter inFormatter, Formatter outFormatter, ParameterList parameterList) throws ValidationException, OperationExecutionException
	{
		try
		{
			int authLevel = ApplicationUtil.getAuthLevelsManager(parentContext).getAuthLevel(getClass().getName()).getAuthLevel();
			execute(parentContext, inFormatter, outFormatter, parameterList, authLevel);
		}
		catch (ValidationException e)
		{
			throw e;
		}
		catch (Throwable e)
		{
			throw new OperationExecutionException("Unable to execute operation [" + getName() + "].", e);
		}	
	}

	/**
	 * Execute the operation. 
	 * @param parentContext The parent <code>Context</code> to use in the <code>Operation</code>.
	 * @throws ValidationException
	 * @throws OperationExecutionException
	 */
	public final void executeOperation(Context parentContext) throws ValidationException, OperationExecutionException
	{
		executeOperation(parentContext, (Formatter)null, (Formatter)null, null); 
	}

	/**
	 * Execute the operation. 
	 * Format the data in & out of the context according to the given complexFormatter.
	 * @param parentContext The parent <code>Context</code> to use in the <code>Operation</code>.
	 * @param complexFormatterName The {@link ComplexFormatter} performs the field’s copy 
	 * from the parent {@link Context} to the operation {@link Context}, before 
	 * processing the operation. And performs the field’s copy from the operation {@link Context} 
	 * to the parent {@link Context}, after processing the operation.
	 * @throws ValidationException
	 * @throws OperationExecutionException
	 * @throws FlowElementsFactoryException
	 */
	public final void executeOperation(Context parentContext, String complexFormatterName) throws ValidationException, OperationExecutionException, FlowElementsFactoryException
	{
		ComplexFormatter formatter = FlowElementsFactory.getInstance().createComplexFormatter(complexFormatterName);
		executeOperation(parentContext, formatter.getInFormatter(), formatter.getOutFormatter(), null); 
	}

	/**
	 * Execute the operation. 
	 * Format the data in & out of the context according to the given formatters.
	 * @param parentContext The parent <code>Context</code> to use in the <code>Operation</code>.
	 * @param inFormatter The {@link Formatter} performs the field’s copy from the parent {@link Context} 
	 * to the operation {@link Context}, before processing the operation.
	 * @param outFormatter The {@link Formatter} performs the field’s copy from the operation {@link Context} 
	 * to the parent {@link Context}, after processing the operation.
	 * @throws ValidationException
	 * @throws OperationExecutionException
	 * @throws FlowElementsFactoryException
	 */
	public final void executeOperation(Context parentContext, String inFormatterName, String outFormatterName) throws ValidationException, OperationExecutionException, FlowElementsFactoryException
	{
		Formatter inFormatter = null;
		if(inFormatterName != null)
			inFormatter = FlowElementsFactory.getInstance().createFormatter(inFormatterName);
		
		Formatter outFormatter = null;
		if(outFormatterName != null)
			outFormatter = FlowElementsFactory.getInstance().createFormatter(outFormatterName);
			
		executeOperation(parentContext, inFormatter, outFormatter, null); 
	}

}
