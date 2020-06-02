/*
 * Created on: 26/08/2004
 * Author: yifat har-nof
 * @version $Id: EventParametersHandler.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.Enumeration;

import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.util.*;
import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.AuthorizationException;

/**
 * 
 */
public class EventParametersHandler
{
	public static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "EVENT HANDLER";
	
	/**
	 * Used by framework to transfer all parameters from the http request to specifyed context
	 *
	 * @param ctx the context to transfer parameters to.
	 * @throws ContextException thrown if parameters transfer is failed (for example one of
	 * fields from event is not allowed for the specifyed context)
	 */
	protected static void fillParametersToContext(Context ctx, Event event) throws ContextException, ValidationException, FatalException, AuthorizationException
	{
		boolean validationError = false;
		Enumeration enum = event.getParameterNames();

		Logger.debug(LOGGER_CONTEXT, ctx.toString());

		while (enum.hasMoreElements())
		{
			String key = (String) enum.nextElement();
			
			String values[] = event.getParameterValues(key);
			if(setFieldToContext(ctx, key, values))
			{
				validationError = true;
			}
		}

		if (validationError)
		{
			throw new ValidationException();
		}
	}

	/**
	 * set the field data into the context.
	 * @param key
	 * @param values
	 */
	private static boolean setFieldToContext(Context ctx, String key, String[] values) throws ContextException, ValidationException, FatalException, AuthorizationException
	{
		boolean validationError = false;
		
		try
		{
			if (values.length == 1)
			{
				ctx.setField(key, values[0], false);
			}
			else
			{
				ctx.setField(key, values, false);
			}
		}
		catch (FieldNotFoundException ex)
		{
			Logger.error(LOGGER_CONTEXT, "Unable to fill parameter [" + key + "] into context. Redundant parameter from HTTP request.");
		}
		catch (FieldAccessViolationException ex)
		{
			Logger.error(LOGGER_CONTEXT, "Unable to fill parameter [" + key + "] into context. Context access violation.");
			throw ex;
		}
		catch (ContextFieldConstructionException ex)
		{
			Logger.debug(LOGGER_CONTEXT, "Unable to fill parameter [" + key + "] into context." , ex);
			MessagesContainer globalMessageContainer = ApplicationUtil.getGlobalMessageContainer(ctx);
			if (ex.getMessagesContainer() != null)
			{
				MessagesContainer messagesContainer = ex.getMessagesContainer();
				for (int i = 0; i < messagesContainer.getMessagesCount(); i++)
				{
					Message message = messagesContainer.getMessage(i);
					message.addFieldName(key);
					
					//  get the caption from the field definition
					ContextField contextField = ctx.getFieldDefinition(key);
					message.addLocalizableParameter(contextField.getCaption());
				}

				globalMessageContainer.merge(messagesContainer);
			}
			else
			{
				Message message = new Message("GE0006", ex, Message.SEVERITY_ERROR);
				message.addFieldName(key);
				//  get the caption from the field definition
				ContextField contextField = ctx.getFieldDefinition(key);
				message.addLocalizableParameter(contextField.getCaption());

				ApplicationUtil.getGlobalMessageContainer(ctx).addMessage(message);
			}

			validationError = true;
		}
		catch (ContextException ex)
		{
			Logger.debug(LOGGER_CONTEXT, "Exception occured while filling parameters from event into transition context.", ex);

			Message message = new Message("GE0006", ex, Message.SEVERITY_ERROR);
			message.addFieldName(key);
			message.addLocalizableParameter(key);
			ApplicationUtil.getGlobalMessageContainer(ctx).addMessage(message);

			validationError = true;
		}
		return validationError;
	}

}
