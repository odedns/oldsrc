/*
 * Created on 12/07/2004
 * Author: yifat har-nof
 * @version $Id: FlowerBusinessLogicUtil.java,v 1.3 2005/03/22 13:48:13 yifat Exp $
 */
package com.ness.fw.shared.flower.bl;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.bl.DataViewContainer;
import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.ValidationException;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.util.MessagesContainer;

/**
 * Utility Class for handling the relations between the Flower layer objects 
 * and the Business Logic layer objects. 
 */
public class FlowerBusinessLogicUtil
{
	/**
	 * Returns the value of user auth data field from the given {@link Context}
	 * The user id is taken from the context.
	 * @param context The Context the get the user auth data field.
	 * @return UserAuthData Contains basic authorization data of the current user.
	 */
	public static UserAuthData getUserAuthData (Context context)
	{
		return ApplicationUtil.getUserAuthData(context);
	}
	
	/**
	 * Sets the user auth data from the context into the <code>BusinessProcessContainer</code>.
	 * @param context The <code>Context</code> to get the user auth data field.
	 * @param bpc The <code>BusinessProcessContainer</code> to set the user auth data.
	 */
	public static void setBPOContainerUserData (Context context, BusinessProcessContainer bpc) 
	{
		bpc.setUserAuthData(getUserAuthData(context));
	}
	
	/**
	 * Sets the user auth data from the context into the <code>DataViewContainer</code>.
	 * @param context The <code>Context</code> to get the user auth data field.
	 * @param dvc The <code>DataViewContainer</code> to set the user auth data.
	 * @throws ContextException
	 */
	public static void setDVOContainerUserData (Context context, DataViewContainer dvc) throws ContextException
	{
		dvc.setUserAuthData(getUserAuthData(context));
	}
	
	/**
	 * Returns the current user id from the given {@link Context}
	 * @param context The Context the get the user auth data field.
	 * @return String current user id
	 * @throws ContextException
	 */
	public static String getUserId (Context context) throws ContextException
	{
		UserAuthData userAuthData = getUserAuthData(context);
		if(userAuthData != null)
		{ 
			return userAuthData.getUserID();
		}
		return null;
	}

	
	/**
	 * Execute the bpo command (set the UserAuthData in the bpc before the execution). 
	 * merge the messages to the global messages container, 
	 * and throw ValidationException if the container containes errors or warnings. 
	 * @param context The current Context, to get the UserAuthData.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @return Object The return value of the business process method.
	 * @throws ValidationException Thrown when the messages container contains errors or warnings.
	 * @throws ContextException
	 * @throws FatalException
	 */
	public static final Object executeBPOCommand (Context context, String bpoCommandName, BusinessProcessContainer bpc) throws ValidationException, FatalException
	{
		Object returnValue = null;		
		try
		{	
			setBPOContainerUserData(context, bpc);
			returnValue = BPOProxy.execute(bpoCommandName, bpc);
			handleMessages(context, bpc.getMessagesContainer());
		}
		catch (BusinessLogicException e)
		{
			handleMessages(context, e.getMessagesContainer());
		} 
		catch (BPOCommandNotFoundException e)
		{
			throw new FatalException ("error executing bpo " + bpoCommandName, e);
		} 
		catch (BPOCommandException e)
		{
			throw new FatalException ("error executing bpo " + bpoCommandName, e);
		}
		return returnValue;
	}

	/**
	 * Merge the messages to the global messages container and throw Validation Exception 
	 * if the container containes errors or warnings. 
	 * @param context The {@link Context} to get the global MessagesContainer.
	 * @param messagesContainer A MessagesContainer to merge.
	 * @throws ValidationException
	 */
	public static final void handleMessages (Context context, MessagesContainer messagesContainer) throws ValidationException
	{
		ApplicationUtil.mergeGlobalMessageContainer(context, messagesContainer);
			
		// if there were errors, stop the process
		if (ApplicationUtil.containsGlobalErrorsOrWarnings(context))
		{
			throw new ValidationException();
		}
	}

}
