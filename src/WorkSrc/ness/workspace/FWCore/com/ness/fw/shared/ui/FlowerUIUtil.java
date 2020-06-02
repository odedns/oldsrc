package com.ness.fw.shared.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.ness.fw.flower.common.MenuItemList;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Event;
import com.ness.fw.flower.core.Flow;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.FlowStatesList;
import com.ness.fw.flower.core.FlowerException;
import com.ness.fw.flower.core.ResultEvent;
import com.ness.fw.flower.servlet.FlowerHTMLUtil;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.flower.util.AuthLevelsManager;
import com.ness.fw.flower.util.PageElementAuthLevel;

import com.ness.fw.ui.UIConstants;

import com.ness.fw.util.*;
import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;

import com.ness.fw.common.resources.*;
import com.ness.fw.common.auth.AuthManager;
import com.ness.fw.common.auth.ElementAuthLevel;
import com.ness.fw.common.exceptions.*;

public class FlowerUIUtil
{
	protected final static String UI_CONFIGURATION_FILE_NAME_KEY  = "ui.fw.config";
	protected final static String APP_UI_CONFIGURATION_FILE_NAME_KEY  = "ui.app.config";
	
	private static final char MESSAGE_PLACEHOLDER_SIGN	=   '$';
	
	public static final int AUTH_LEVEL_ALL = AuthManager.AUTH_LEVEL_ALL;
	public static final int AUTH_LEVEL_READONLY = AuthManager.AUTH_LEVEL_READONLY;
	public static final int AUTH_LEVEL_NONE = AuthManager.AUTH_LEVEL_NONE;

	public static final String REQUEST_PARAM_FLOW_ID_FIELD_CMB = Event.FLOW_ID_FIELD_CMB;
	public static final String REQUEST_PARAM_FLOW_STATE_FIELD_CMB = Event.FLOW_STATE_FIELD_CMB;
	public static final String REQUEST_PARAM_EVENT_NAME_FIELD_CMB = Event.EVENT_NAME_FIELD_CMB;
	public static final String REQUEST_PARAM_EXTRA_PARAMS_FIELD_CMB = Event.EVENT_EXTRA_PARAMS_FIELD_CMB;
	public static final String REQUEST_PARAM_CHECK_WARNINGS_FIELD_CMB = "checkWarnings";
	public static final String REQUEST_PARAM_IS_POPUP_FIELD_CMB = "isPopupWindow";
	
	private static final String USER_NOT_FOUND_MESSAGE = "N/A";
	
	public static Context getCurrentFlowCurrentStateContext(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getCurrentFlowCurrentStateContext(request);
	}
	
	/**
	 * Returns the current flow's path.
	 * @param request the HttpServletRequest object
	 * @return current flow's path
	 */
	public static String getCurrentFlowPath(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getCurrentFlowPath(request);
	}
	
	/**
	 * Returns the current flow's state.
	 * @param request the HttpServletRequest object
	 * @return current flow's state
	 */
	public static String getCurrentFlowState(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getCurrentFlowStateName(request);
	}
	
	/**
	 * Returns the main flow's path.
	 * @param request the HttpServletRequest object
	 * @return main flow's path
	 */
	public static String getMainFlowPath(HttpServletRequest request)
	{
		return " ";
	}

	/**
	 * Returns the main flow's state.
	 * @param request the HttpServletRequest object
	 * @return main flow's state
	 */
	public static String getMainFlowState(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getMainFlowStateName(request);
	}

	/**
	 * Returns the menu flow's path.
	 * @param request the HttpServletRequest object
	 * @return menu flow's path
	 */
	public static String getMenuFlowPath(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getMenuFlowPath(request);
	}

	/**
	 * Returns the menu flow's state.
	 * @param request the HttpServletRequest object
	 * @return menu flow's state
	 */
	public static String getMenuFlowState(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getMenuFlowStateName(request);
	}

		
	/**
	 * Cleans all messages and returns the {@link com.ness.fw.util.MessagesContainer} object.
	 * @param request the HttpServletRequest object
	 * @return a {@link com.ness.fw.util.MessagesContainer} object that contains all
	 * the relevant messages for a single request.
	 */
	public static MessagesContainer cleanMessageContainer(HttpServletRequest request)
	{
		return ApplicationUtil.cleanGlobalMessages(request);
	}
	
	/**
	 * Returns the {@link com.ness.fw.util.MessagesContainer} object.
	 * @param request the HttpServletRequest object
	 * @return a {@link com.ness.fw.util.MessagesContainer} object that contains all
	 * the relevant messages for a single request.
	 */
	public static MessagesContainer getMessageContainer(HttpServletRequest request)
	{
		return ApplicationUtil.getGlobalMessagesContainer(request);
	}	

	public static String getCurrentUserId(HttpServletRequest request)
	{
		return ApplicationUtil.getUserAuthData(request).getUserID();
	}	

	public static String getErrorPageCurrentUserId(HttpServletRequest request)
	{
		if (FlowerHTMLUtil.getMainFlow(request) != null)
		{
			return getCurrentUserId(request);
		}
		else
		{
			return USER_NOT_FOUND_MESSAGE;
		}
	}
	
	/**
	 * Indicates if there is an error in the value of an input field,by 
	 * itterating the {@link com.ness.fw.util.MessagesContainer} object and
	 * searching for a message that is connected to this field.
	 * @param request the HttpServletRequest object
	 * @param fieldId the id of the field in the context.
	 * @return true if a message connected to the field was found
	 * in the {@link com.ness.fw.util.MessagesContainer} object.
	 */
	public static boolean isErrorField(HttpServletRequest request,String fieldId)
	{
		try 
		{
			MessagesContainer messagesContainer = ApplicationUtil.getGlobalMessagesContainer(request);
			Message message = messagesContainer.getMessageByFieldName(fieldId);
			return (message != null && message.getSeverity() != Message.SEVERITY_INFO);
		}
		catch (Throwable t)
		{
			return false;
		}
	}
	
	/**
	 * Indicates if there is a severe error in the value of an input field,by 
	 * itterating the {@link com.ness.fw.util.MessagesContainer} object and
	 * searching for a message of type error that is connected to this field.
	 * Only a message of type "error" is considered as a severe error.
	 * @param request the HttpServletRequest object
	 * @param fieldId the id of the field in the context.
	 * @return true if an error message connected to the field was found
	 * in the {@link com.ness.fw.util.MessagesContainer} object.
	 */
	public static boolean isSevereErrorField(HttpServletRequest request,String fieldId)
	{
		try 
		{
			MessagesContainer messagesContainer = ApplicationUtil.getGlobalMessagesContainer(request);
			Message message = messagesContainer.getMessageByFieldName(fieldId);
			return (message != null && message.getSeverity() == Message.SEVERITY_ERROR);		
		}
		catch (Throwable t)
		{
			return false;
		}
	}
	
	/**
	 * Indicates if a {@link com.ness.fw.flower.core.SubFlowNotFoundException was thrown} in
	 * order to handle it in the client.
	 * @param request request the HttpServletRequest object
	 * @return true if a {@link com.ness.fw.flower.core.SubFlowNotFoundException was thrown}
	 */
	public static boolean isSubFlowFound(HttpServletRequest request)
	{
		try 
		{
			return ApplicationUtil.findSubflowNotFoundException(request) == null;
		}
		catch (Throwable t)
		{
			return true;
		}
	}
	
	/**
	 * Indicates if a {@link com.ness.fw.common.exceptions.AuthorizationException was thrown} in
	 * order to handle it in the client.
	 * @param request request the HttpServletRequest object
	 * @return true if a {@link com.ness.fw.common.exceptions.AuthorizationException was thrown}
	 */
	public static boolean isRequestAuthorized(HttpServletRequest request)
	{
		return ApplicationUtil.findAuthorizationException(request) == null;
	}

	/**
	 * Returns the (@licom.ness.fw.flower.core.ResultEvent
	 * @param request
	 * @return
	 */
	public static ResultEvent getResultEvent(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getResultEvent(request);
	}
	
	public static boolean isValidationException(HttpServletRequest request)
	{
		return getResultEvent(request).isValidationExceptionThrown();
	}
	
	public static FlowStatesList getFlowStateList()
	{
		FlowStatesList list = new FlowStatesList();
		return list;	
	}
	
	public static Flow getCurrentFlow(HttpServletRequest request)
	{
		return FlowerHTMLUtil.getCurrentFlow(request);
	}
		
	public static Object getObjectFromContext(HttpServletRequest request,String id) throws UIException
	{	 
		try
		{
			return FlowerHTMLUtil.getCurrentFlowCurrentStateContext(request).getField(id);
		}
		catch (ContextException ce)
		{
			throw new UIException(ce);
		}
	}
	
	public static Object getObjectFromFlowContext(Flow flow,String id) throws UIException
	{
		try 
		{
			return flow.getCurrentStateContext().getField(id);
		} 
		catch (ContextException ce) 
		{	
			throw new UIException(ce);
		}
	}
	
	public static String getValueFromContext(HttpServletRequest request,String id) throws UIException
	{
		try
		{
			return FlowerHTMLUtil.getCurrentFlowCurrentStateContext(request).getString(id);
		}
		catch (ContextException ce)
		{
			throw new UIException(ce);
		}
	}
	
	public static String getFormattedValueFromContext(HttpServletRequest request,String id) throws UIException
	{
		try
		{
			return FlowerHTMLUtil.getCurrentFlowCurrentStateContext(request).getFormattedFieldValue(id);
		}
		catch (ContextException ce)
		{
			throw new UIException(ce);
		}
	}

	public static String getFormattedValueFromContext(HttpServletRequest request,String contextFieldName,String mask) throws UIException
	{
		try
		{
			if (mask == null)
			{
				return getFormattedValueFromContext(request,contextFieldName);
			}
			else
			{
				return FlowerHTMLUtil.getCurrentFlowCurrentStateContext(request).getFormattedFieldValue(contextFieldName,mask);
			}
		}
		catch (ContextException ce)
		{
			throw new UIException(ce);
		}
	}
	
	// start baruch 30/11/04
	/**
	 * Get the field value. if it's xiType get the real valur of the model
	 * @param request
	 * @param fieldName
	 * @param mask
	 * @return
	 * @throws UIException
	 */
	public static String getFieldValueFromContext(HttpServletRequest request,String fieldName,String mask) throws UIException
	{
		try
		{
			return FlowerHTMLUtil.getCurrentFlowCurrentStateContext(request).getFieldValue(fieldName,mask);
		}
		catch (ContextException e)
		{
			throw new UIException(e);
		}		
	}
	// end baruch 30/11/04
		 

	// start baruch 16/12/04
	/**
	 * Get the field value. if it's xiType get the real valur of the model
	 * @param request
	 * @param fieldName
	 * @param mask
	 * @return
	 * @throws UIException
	 */
	public static String getFieldValueFromMainContext(HttpServletRequest request,String fieldName) throws UIException
	{
		try
		{
			return FlowerHTMLUtil.getMainFlowCurrentStateContext(request).getFieldValue(fieldName,null);
		}
		catch (ContextException e)
		{
			throw new UIException(e);
		}		
	}
	// end baruch 16/12/04


	 
	public static String getMethodValueFromContext(HttpServletRequest request,String contextFieldName,String methodName) throws UIException
	{
		Object contextObject = FlowerUIUtil.getObjectFromContext(request,contextFieldName);
		if (contextObject == null)
		{
			throw new UIException("cannot use method " + methodName + " on null in field " + contextFieldName);	 
		}
		try 
		{
			Object value = ((contextObject.getClass()).getMethod(methodName,new Class[0])).invoke(contextObject,new Object[0]);
			if (value != null)
			{
				return value.toString();
			}
			else
			{
				return null;
			}
		} 
		catch (IllegalAccessException e) 
		{
			throw new UIException(e);
		} 
		catch (NoSuchMethodException e) 
		{
			throw new UIException(e);
		} 
		catch (InvocationTargetException e) 
		{
			throw new UIException(e.getTargetException());
		}
	}
	
	public static LocalizedResources getLocalizable(HttpServletRequest request) throws UIException
	{
		try
		{
			return ApplicationUtil.getLocalizedResources(FlowerHTMLUtil.getMainFlowCurrentStateContext(request));
		}
		catch (Throwable t)
		{
			throw new UIException(t);
		}
	}
	
	public static String getAPPUIProperty(String uiKey)
	{
			return SystemResources.getInstance().getProperty(uiKey);
	}

	public static String getSYSUIProperty(String uiKey)
	{
		try 
		{
			return SystemResources.getInstance().getString(uiKey);
		} 
		catch (ResourceException re) 
		{
			return uiKey;
		}
	}
	
	public static String getSystemProperty(String key)
	{
		String systemProperty = SystemResources.getInstance().getProperty(key);
		if (systemProperty == null)
		{
			return key;
		}
		else
		{	
			return systemProperty;
		}
	}
	
	public static String getLocalizedText(HttpServletRequest request,String key)
	{
		try
		{
			String localizedText = getLocalizedResources(request).getString(key);
			if (localizedText == null)
			{
				return key;
			}
			else
			{
				return localizedText;
			}
		}
		catch (ResourceException re)
		{
			//throw new UIException(re.getMessage());
			return key;
		}
	}
	
	public static LocalizedResources getLocalizedResources(HttpServletRequest request)
	{
		return ApplicationUtil.getLocalizedResources(FlowerHTMLUtil.getMainFlowCurrentStateContext(request));
	}
	
	/**
	 * Returns string reperesentation of numbers,dates and boolean values. 
	 * @param localizable 
	 * @param sourceValue
	 * @param dataType
	 * @param maskKey
	 * @return
	 * @throws ResourceException
	 * @throws UIException
	 */
	public static String getFormattedValue(LocalizedResources localizable,Object sourceValue,String dataType,String maskKey) throws UIException
	{	
		if (sourceValue == null)
		{
			return "";
		}
		// TODO add types time and timestamp
		try
		{
			if (dataType.equals(UIConstants.DATA_TYPE_NUMBER))
			{
				return NumberFormatterUtil.printNumber(localizable,(Number)sourceValue,maskKey);
			}
			if (dataType.equals(UIConstants.DATA_TYPE_DATE))
			{
				return DateFormatterUtil.printDate(localizable,(Date)sourceValue,maskKey);
			}
			if (dataType.equals(UIConstants.DATA_TYPE_BOOLEAN))
			{
				return BooleanFormatterUtil.print(localizable,(Boolean)sourceValue);
			}
			else if (dataType.equals(UIConstants.DATA_TYPE_STRING))
			{
				return sourceValue.toString();
			}
			else
			{
				throw new UIException("dataType " + dataType + " is not valid for object " + sourceValue);
			}
		}
		catch (ResourceException re)
		{
			throw new UIException(re);		
		}
		catch (ClassCastException cce)
		{
			throw new UIException("data type " + dataType + " is not compatible with " + sourceValue.getClass().getName() + " in cell with data " + sourceValue);
		}
	}	
	
	public static ElementAuthLevel getAuthLevelInformation(String authId,HttpServletRequest request,String sourceName) throws UIException
	{
		AuthLevelsManager authLevelManager = ApplicationUtil.getAuthLevelsManager(request);
		try 
		{
			return authLevelManager.getAuthLevel(authId,sourceName);
		}
		catch (ResourceException re)
		{
			throw new UIException(re);
		}
	}
	
	public static int getAuthLevelInformation(HttpServletRequest request,PageElementAuthLevel pageElement,String sourceName) throws UIException
	{
		AuthLevelsManager authLevelsManager = ApplicationUtil.getAuthLevelsManager(request);
		try 
		{
			return authLevelsManager.getPageAuthLevel(pageElement,sourceName);
		} 
		catch (ResourceException re) 
		{	
			throw new UIException(re);
		}
	}
	
	public static boolean isInsertLevelToStack(ElementAuthLevel elementAuthLevel)
	{
		return elementAuthLevel.isAuthLevelInsertedToStack();
	}
	
	public static int getAuthLevel(ElementAuthLevel elementAuthLevel)
	{ 
		return elementAuthLevel != null ? elementAuthLevel.getAuthLevel() : AUTH_LEVEL_ALL;
	}	
	
	public static void removeAuthLevel(ElementAuthLevel elementAuthLevel,HttpServletRequest request,String sourceName)
	{
		AuthLevelsManager authLevelManager = ApplicationUtil.getAuthLevelsManager(request);
		authLevelManager.removeAuthLevel(elementAuthLevel,sourceName);
	}
	
	public static int getStateAuthLevel(String authId,HttpServletRequest request)
	{
		AuthLevelsManager authLevelManager = ApplicationUtil.getAuthLevelsManager(request);
		return authLevelManager.determineElementAuthLevel(authId);
	}
	
	
	/**
	 * Adds authorized event and returns FWParam object which contains 
	 * inforamtion about the flow path and flow state.
	 * @param eventName the name of the event to add.
	 * @param eventTargetType the target type of the event.
	 * @param request
	 * @return
	 */
	public static AuthorizedEventData addAuthorizedEvent(String eventName,String eventTargetType,HttpServletRequest request,String componentDesc)
	{
		return ApplicationUtil.addAuthorizedEvent(eventName,eventTargetType,request,componentDesc);
	}
	
	/**
	 * Adds authorized event to the the list of authorized events 
	 * by the event name and the flow.
	 * @param clickEventName
	 * @param flow
	 */
	public static void addAuthorizedEvent(String eventName,Flow flow,String componentDesc)
	{
		ApplicationUtil.addAuthorizedEvent(flow,eventName,componentDesc);
	}
	
	public static boolean checkWarnings(HttpServletRequest request) throws UIException
	{
		MessagesContainer messagesContainer = getMessageContainer(request);
		return (messagesContainer.containsWarnings() 
				&& !messagesContainer.containsErrors()
				&& getEventCheckWarningsIndication(request).equals("true"));
	}
	
	public static boolean isPopupWindow(HttpServletRequest request) throws UIException
	{
		String isPopupWindow = getEventIsPopupWindow(request);
		return isPopupWindow != null && isPopupWindow.equals("1");
	}
	
	/**
	 * Indicates if error messages or warning messages are found in the message container
	 * @param request
	 * @return
	 * @throws UIException
	 */
	public static boolean errorWarningMessagesFound(HttpServletRequest request) throws UIException
	{
		MessagesContainer messagesContainer = getMessageContainer(request);
		return messagesContainer.containsWarnings() || messagesContainer.containsErrors();
	}
	public static String getLastEventName(HttpServletRequest request) throws UIException
	{		
		return request.getParameter(REQUEST_PARAM_EVENT_NAME_FIELD_CMB);
	}
	
	public static String getLastFlowState(HttpServletRequest request) throws UIException
	{
		return request.getParameter(REQUEST_PARAM_FLOW_STATE_FIELD_CMB);		
	}
	
	public static String getLastFlowPath(HttpServletRequest request) throws UIException
	{
		return request.getParameter(REQUEST_PARAM_FLOW_ID_FIELD_CMB);
	}
	
	public static String getEventCheckWarningsIndication(HttpServletRequest request)
	{
		return request.getParameter(REQUEST_PARAM_CHECK_WARNINGS_FIELD_CMB);
	}
	
	public static String getEventIsPopupWindow(HttpServletRequest request)
	{
		return request.getParameter(REQUEST_PARAM_IS_POPUP_FIELD_CMB);
	}	
	
	public static String replaceDynamicParameters(String source,Context ctx)
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
					try
					{
						resBuffer.append(ctx.getFormattedFieldValue(placeholder));
					}
					catch (Throwable ex)
					{
					
					}
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
	
	public static MenuItemList getMenuItemList(HttpServletRequest request) throws UIException
	{
		Flow currentFlow = getCurrentFlow(request);
		try 
		{
			return currentFlow.getMenuItemList();
		} 
		catch (FlowerException fe) 
		{
			throw new UIException(fe);	
		}
	}
}
