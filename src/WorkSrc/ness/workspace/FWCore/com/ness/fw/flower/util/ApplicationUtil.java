/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: ApplicationUtil.java,v 1.3 2005/03/17 11:33:00 yifat Exp $
 */
package com.ness.fw.flower.util;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.servlet.*;
import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.shared.ui.AuthorizedEventData;
import com.ness.fw.ui.DirtyModel;
import com.ness.fw.ui.MessagesModel;
import com.ness.fw.util.*;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.resources.*;
import javax.servlet.http.*;

/**
 * A utility class used to mediating between the Dynamic global objects 
 * within the Context and the HttpServletRequest parameters.
 */
public class ApplicationUtil
{
	private static final char MESSAGE_PLACEHOLDER_SIGN	=   '$';

	/**
	 * Returns the flow state of the last sent event.
	 * @param request The current {@link HttpServletRequest} to use.
	 * @return String event name
	 */
	public static String getEventFlowState(HttpServletRequest request) throws FatalException
	{
		return getXIStringValueFromContext(request, Event.FLOW_STATE_FIELD_CMB);
	}

	/**
	 * Returns the flow id of the last sent event.
	 * @param request The current {@link HttpServletRequest} to use.
	 * @return String event name
	 */
	public static String getEventFlowID(HttpServletRequest request) throws FatalException
	{
		return getXIStringValueFromContext(request, Event.FLOW_ID_FIELD_CMB);
	}


	/**
	 * Returns the name of the last sent event.
	 * @param request The current {@link HttpServletRequest} to use.
	 * @return String event name
	 */
	public static String getLastEventName(HttpServletRequest request) throws FatalException
	{
		return getXIStringValueFromContext(request, Event.EVENT_NAME_FIELD_CMB);
	}


	private static String getXIStringValueFromContext(HttpServletRequest request, String fieldName) throws FatalException
	{
		String value = null;
		Context currentContext = FlowerHTMLUtil.getCurrentFlowCurrentStateContext(request);
		
		try
		{
			value = currentContext.getXIString(fieldName);
		}
		catch (ContextException e)
		{
			throw new FatalException ("Unable to get field with name [" + fieldName + "] from the context ", e);
		}
		
		return value;
	}

	public static LocalizedResources getLocalizedResources(Context context)
	{
		return context.getDynamicGlobals().getLocalizedResources();
	}

	/**
	 * Returns the authorization levels object from the given request.
	 * @param request The current {@link HttpServletRequest} to use.
	 * @return AuthLevelsManager
	 */
	public static AuthLevelsManager getAuthLevelsManager(HttpServletRequest request)
	{
		Context mainContext = FlowerHTMLUtil.getMainFlowCurrentStateContext(request);
		return getAuthLevelsManager(mainContext);
	}

	/**
	 * Returns the authorization levels object from the given Context.
	 * @param context The {@link Context} to get the global AuthLevelsManager.
	 * @return AuthLevelsManager
	 */
	public static AuthLevelsManager getAuthLevelsManager(Context context)
	{
		return context.getDynamicGlobals().getAuthLevelsManager();
	}

	/**
	 * Returns the global {@link UserAuthData} from the Context.
	 * @param context The {@link Context} to get the global UserAuthData.
	 * @return UserAuthData
	 */		
	public static UserAuthData getUserAuthData(Context context)
	{
		return context.getDynamicGlobals().getUserAuthData();
	}

	/**
	 * Returns the global {@link UserAuthData} from the given request.
	 * @param request The current {@link HttpServletRequest} to use.
	 * @return UserAuthData
	 */
	public static UserAuthData getUserAuthData(HttpServletRequest request)
	{
		Context mainContext = FlowerHTMLUtil.getMainFlowCurrentStateContext(request);
		return mainContext.getDynamicGlobals().getUserAuthData();
	}
	
	/**
	 * Merge the messages from the given MessagesContainer to the global MessagesContainer. 
	 * @param context The {@link Context} to get the global MessagesContainer.
	 * @param messagesContainer A MessagesContainer to merge.
	 */
	public static void mergeGlobalMessageContainer(Context context, MessagesContainer messagesContainer)
	{
		if(messagesContainer != null && messagesContainer.getMessagesCount() > 0)
		{
			context.getDynamicGlobals().getMessagesContainer().merge(messagesContainer);
		}
	}


	/**
	 * Check if the global {@link MessagesContainer} contains errors or warning messages.
	 * @param context The {@link Context} to get the global MessagesContainer.
	 * @return true when the container contains errors or warnings.
	 */		
	public static boolean containsGlobalErrorsOrWarnings(Context context)
	{
		return context.getDynamicGlobals().getMessagesContainer().containsErrorsOrWarnings();
	}


	/**
	 * Returns the global {@link MessagesContainer} from the Context.
	 * @param context The {@link Context} to get the global MessagesContainer.
	 * @return The global MessagesContainer.
	 */		
	public static MessagesContainer getGlobalMessageContainer(Context context)
	{
		return context.getDynamicGlobals().getMessagesContainer();
	}

	/**
	 * Returns the global {@link MessagesContainer} from the Context attached to the request.
	 * @param request The HttpServletRequest to get the Context.
	 * @return The global MessagesContainer.
	 */
	public static MessagesContainer getGlobalMessagesContainer(HttpServletRequest request)
	{
		return getGlobalMessageContainer(FlowerHTMLUtil.getMainFlowCurrentStateContext(request));
	}

	public static void addGlobalMessage(Context context, Message message)
	{
		MessagesContainer msgContainer = getGlobalMessageContainer(context);
		msgContainer.addMessage(message);
	}

	public static void addGlobalMessageMapping(Context context, String htmlFieldName, String businessFieldName)
	{
		MessagesContainer msgContainer = getGlobalMessageContainer(context);
		msgContainer.addMapping(htmlFieldName, businessFieldName);
	}

	public static MessagesContainer cleanGlobalMessages(Context context)
	{
		return context.getDynamicGlobals().removeMessagesContainer();
	}
	
	public static MessagesContainer cleanGlobalMessages(HttpServletRequest request)
	{
		return cleanGlobalMessages(FlowerHTMLUtil.getMainFlowCurrentStateContext(request)); 
	}

	public static SubflowNotFoundException findSubflowNotFoundException(HttpServletRequest request)
	{
		return (SubflowNotFoundException)findThrowable(SubflowNotFoundException.class.getName(),request);
	}

	public static AuthorizationException findAuthorizationException(HttpServletRequest request)
	{
		return (AuthorizationException)findThrowable(AuthorizationException.class.getName(),request);
	}

	private static Throwable findThrowable(String className,HttpServletRequest request)
	{
		ResultEvent resultEvent = FlowerHTMLUtil.getResultEvent(request);
		if(resultEvent != null)
		{
			for (int i = 0; i < resultEvent.getExceptionsCount(); i++)
			{
				if (resultEvent.getException(i).getClass().getName().equals(className))
				{
					return resultEvent.getException(i);
				}
			}
		}
		return null;
	}

	public static String fillWithContextParameters(String source, Context ctx)
	{
		char arr[] = source.toCharArray();
		boolean insidePlaceholders = false;
		int lastPlaceholderIndex = -1;
		StringBuffer resBuffer = new StringBuffer(arr.length);
		for (int i = 0; i < arr.length; i++)
		{
			char c = arr[i];

			if (c == MESSAGE_PLACEHOLDER_SIGN)
			{
				if (!insidePlaceholders)
				{
					resBuffer.append(arr, lastPlaceholderIndex + 1, i - lastPlaceholderIndex - 1);
					insidePlaceholders = true;
				}
				else
				{
					String placeholder = String.valueOf(arr, lastPlaceholderIndex + 1, i - lastPlaceholderIndex - 1);
					try{
						resBuffer.append(ctx.getFormattedFieldValue(placeholder));
					}catch (Throwable ex){}
					insidePlaceholders = false;
				}

				lastPlaceholderIndex = i;
			}
		}

		if (!insidePlaceholders)
		{
			resBuffer.append(arr, lastPlaceholderIndex + 1, arr.length - lastPlaceholderIndex - 1);
		}

		return resBuffer.toString();
	}

	/**
	 * Write the <code>Flow</code> in the <code>ResultEvent</code> 
	 * @param flow
	 * @param resultEvent
	 * @param clearAuthorizedEvents
	 */
	public static void writeFlowInResultEvent (Flow flow, ResultEvent resultEvent, boolean clearAuthorizedEvents)
	{
		if(clearAuthorizedEvents)
			flow.clearAuthorizedEvents();
		resultEvent.setFlow(flow);
	}

	/**
	 * Register the event to the flow to be able to send the event later.  
	 * @param flow The flow to register the event.
	 * @param eventName The event name to register.
	 * @param componentDescription The component that registers the event.
	 */
	public static void addAuthorizedEvent (Flow flow, String eventName, String componentDescription)
	{
		flow.addAuthorizedEvent(eventName, componentDescription);
	}

	/**
	 * 
	 * @param eventName The event name to register.
	 * @param targetType The target type of the event, according to the type 
	 * we determine to which flow to register the event. 
	 * @param request The http request.
	 * @param componentDescription The component that registers the event.
	 * @return AuthorizedEventData Contains information about the flow path and flow state to send with the event.
	 */
	public static AuthorizedEventData addAuthorizedEvent (String eventName, String targetType, HttpServletRequest request, String componentDescription)
	{
		Flow flow;
		
		if(targetType.equals(SystemConstants.EVENT_TARGET_TYPE_NEW_WINDOW))
		{
			flow = FlowerHTMLUtil.getMainFlow(request);
		}
		else 
		{
			flow = FlowerHTMLUtil.getCurrentFlow(request);
		}
		
		flow.addAuthorizedEvent(eventName, componentDescription);
		return new AuthorizedEventData(flow.getFlowPathString(), flow.getCurrentState().getName());
	}
	

	/**
	 * Returns a {@link Throwable}s list from the given request. 
	 * @param request
	 * @return List {@link Throwable} list.
	 */
	public static List getRequestThrowables(HttpServletRequest request)
	{
		List throwables = new ArrayList();
		ArrayList errorsList = (ArrayList)request.getAttribute(HTMLConstants.ERROR_LIST);
		ResultEvent resultEvent = (ResultEvent)request.getAttribute(HTMLConstants.RESULT_EVENT);

		// Getting the exceptions from the error list
		if (errorsList != null)
		{
			for (int i = 0; i < errorsList.size(); i++)
			{
				throwables.add(errorsList.get(i));
			}
		}

		// Getting the exceptions from the result event
		if (resultEvent != null)
		{
			for (int i = 0; i < resultEvent.getExceptionsCount(); i++)
			{
				throwables.add(resultEvent.getException(i));
			}
		}

		return throwables;		
	}

	/**
	 * Set dirty falg as clean. 
	 * @param context The <code>Context</code> to use.
	 * @throws ContextException
	 */
	public static void clearDirtyAreaFlag (Context context, String areaName) throws ContextException
	{
		((DirtyModel)context.getField(areaName)).setClean();
	}

	/**
	 * Creates new <code>DirtyModel</code> and set into the <code>Context</code>.
	 * @param context The <code>Context</code> to use.
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public static void createDirtyFlagModel (Context context) throws ContextException, FatalException, AuthorizationException
	{
		context.setField(SystemConstants.DIRTY_FLAG_FIELD_NAME, new DirtyModel());
	}

	/**
	 * Set dirty falg as clean. 
	 * @param context The <code>Context</code> to use.
	 * @throws ContextException
	 */
	public static void clearDirtyFlag (Context context) throws ContextException
	{
		ApplicationUtil.clearDirtyAreaFlag(context,SystemConstants.DIRTY_FLAG_FIELD_NAME);
	}

	/**
	 * Set dirty flag as dirty. 
	 * @param context The <code>Context</code> to use.
	 * @throws ContextException
	 */
	public static void setDirtyFlag (Context context) throws ContextException
	{
		setDirtyFlag(context,SystemConstants.DIRTY_FLAG_FIELD_NAME);
	}

	/**
	 * Set dirty flag of area as dirty. 
	 * @param context The <code>Context</code> to use.
	 * @throws ContextException
	 */
	public static void setDirtyFlag (Context context, String areaName) throws ContextException
	{
		((DirtyModel)context.getField(areaName)).setDirty();
	}

	/**
	 * Creates new <code>MessagesModel</code> and set into the <code>Context</code>.
	 * @param context The <code>Context</code> to use.
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public static void createMessagesModel (Context context) throws ContextException, FatalException, AuthorizationException
	{
		context.setField(SystemConstants.MESSAGES_FIELD_NAME, new MessagesModel());
	}
	
}
