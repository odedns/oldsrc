/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: ButtonData.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.shared.common.SystemConstants;

/**
 * The class contains all data needed to create a button. 
 * Usually contained in {@link ButtonSet}.
 */
public class ButtonData
{
	
	/**
	 * Constant for <code>Button</code> in the ToolBar.
	 */
	public final static int TYPE_BUTTON = 1;

	/**
	 * Constant for <code>Space</code> in the ToolBar.
	 */
	public final static int TYPE_SPACER = 2;
	
	/**
	 * Constant for the defualt type in the ToolBar, which is button.
	 */
	public final static int TYPE_DEFAULT = TYPE_BUTTON;
	
	/**
	 * Constant for readWrite activity / event type. 
	 * It means that the activities that will be performed in the element will 
	 * performs updates to the DB and should not be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int EVENT_TYPE_READWRITE = SystemConstants.EVENT_TYPE_READWRITE;
	
	/**
	 * Constant for readOnly activity / event type. 
	 * It means that the activities that will be performed in the element will not 
	 * perform updates to the DB and could be performed when the authorization level 
	 * is Read Only (Preview).
	 */
	public final static int EVENT_TYPE_READONLY = SystemConstants.EVENT_TYPE_READONLY;
	
	/**
	 * The default event type.
	 */
	public final static int EVENT_TYPE_DEFAULT = EVENT_TYPE_READWRITE;

	/**
	 * This constant is used for the eventType property when sending an event to a normal window.
	 */
	public final static String OPEN_WINDOW_TYPE_NORMAL = SystemConstants.EVENT_TARGET_TYPE_NORMAL;
	
	/**
	 * This constant is used for the eventType property when sending an event to a modal window.
	 */
	public final static String OPEN_WINDOW_TYPE_DIALOG = SystemConstants.EVENT_TARGET_TYPE_DIALOG;
	
	/**
	 * This constant is used for the eventType property when sending an event to a popup window.
	 */
	public final static String OPEN_WINDOW_TYPE_POPUP = SystemConstants.EVENT_TARGET_TYPE_POPUP;
	
	/**
	 * This constant is used for the eventType property when sending an event and closing a modal window.
	 */
	public final static String OPEN_WINDOW_TYPE_CLOSE_DIALOG = SystemConstants.EVENT_TARGET_TYPE_CLOSE_DIALOG;

	/**
	 * This constant is used for the eventType property when sending an event and closing a popup window.
	 */
	public final static String OPEN_WINDOW_TYPE_CLOSE_POPUP = SystemConstants.EVENT_TARGET_TYPE_CLOSE_POPUP;

	/**
	 * The default open window type.
	 */
	public final static String OPEN_WINDOW_TYPE_DEFAULT = OPEN_WINDOW_TYPE_NORMAL;

	
	/**
	 * The name of the button. This name represent the translation key for the button text.
	 */
	private String name;

	/**
	 * The event name constructed from the button.
	 */
	private String eventName;
	
	/**
	 * The group id of the button.
	 */
	private String buttonGroup;

	/**
	 * The type of the button. 
	 * could be one of the following: <code>TYPE_BUTTON</code>, <code>TYPE_SPACER</code>.
	 * The default value is <code>TYPE_BUTTON</code>.
	 */
	private int type;

	/**
	 * The event type of the button. 
	 * could be one of the following: <code>EVENT_TYPE_READWRITE</code>, <code>EVENT_TYPE_READONLY</code>.
	 * The default value is <code>EVENT_TYPE_READONLY</code>.
	 */
	private int eventType;

	/**
	 * The target type of window opening from the button. 
	 */
	private String openWindowType;

	/**
	 * The extra parameters to open the dialog / popup window. (hight, width etc.)
	 */
	private String windowExtraParams;
	
	/**
	 * The confirm message id to display to the user before sending the event.
	 */
	private String confirmMessageId;
	
	/**
	 * The authorization id to determined the authorization level of the button. 
	 */
	private String authId;

	/**
	 * The name of the buttonSet that contains the button.
	 */
	private String buttonSetName;

	/**
	 * Indicates whether to check dirty flag in the client, before the event sent to the server.
	 */
	private boolean checkDirtyFlag;

	/**
	 * Indicates whether to check warnings flag in the client, before the event sent to the server.
	 */
	private boolean checkWarnings;

	/**
	 * The ids of the DirtyModels to check in the button before sending the event.
	 */
	private List checkDirtyModelIds;
	
	/**
	 * Determine whether to allow sending event to the server when exist dirty model 
	 * on one of the dirty ids.
	 */
	private boolean allowEventOnDirtyIds;

	/**
	 * The name of the StateModel that contains the state of the button.
	 */
	private String stateModelId;

	/**
	 * The short cut key of the button that should send the event related to button when it pressed. 
	 */
	private String shortCutKey;

	/**
	 * The constructor used while creating {@link ButtonSet} instance.
	 * @param name The name of the button. This name represent the translation key for the button text.
	 * @param clickEventName The event name constructed from the button.
	 * @param buttonGroup The group id of the button.
	 * @param type The type of the button. 
	 * could be one of the following: <code>TYPE_BUTTON</code>, <code>TYPE_SPACER</code>.
	 * @param eventType The event type of the button. 
	 * could be one of the following: <code>EVENT_TYPE_READWRITE</code>, <code>EVENT_TYPE_READONLY</code>.
	 * @param openWindowType The target type of window opening from the button. 
	 * could be one of the following: <code>EVENT_TYPE_READWRITE</code>, <code>EVENT_TYPE_READONLY</code>.
	 * @param windowExtraParams The extra parameters to open the dialog / popup window. (hight, width etc.)
	 * @param checkDirtyFlag Indicates whether to check dirty flag in the client, before the event sent to the server.
	 * @param checkWarnings Indicates whether to check warnings flag in the client, before the event sent to the server.
	 * @param checkDirtyModelIds The ids of the DirtyModels to check in the button before sending the event.
	 * @param allowEventOnDirtyIds Determine whether to allow sending event to the 
	 * server when exist dirty model on one of the dirty ids.
	 * @param authId The authorization id to determined the authorization level of the button.
	 * @param buttonSetName The name of the buttonSet that contains the button.
	 * @param shortCutKey The short cut key of the button that should send the event related to button when it pressed.
	 */
	public ButtonData(String name, String eventName, String buttonGroup, int type, int eventType, String openWindowType, String windowExtraParams, boolean checkDirtyFlag, boolean checkWarnings, List checkDirtyModelIds, boolean allowEventOnDirtyIds, String confirmMessageId, String authId, String buttonSetName, String stateModelId, String shortCutKey)
	{
		this.name = name;
		this.eventName = eventName;
		this.buttonGroup = buttonGroup;
		this.type = type;
		this.eventType = eventType;
		this.openWindowType = openWindowType;
		this.windowExtraParams = windowExtraParams;
		this.confirmMessageId = confirmMessageId;
		this.authId = authId;
		this.buttonSetName = buttonSetName;
		this.checkDirtyFlag = checkDirtyFlag;
		this.checkWarnings = checkWarnings;
		this.allowEventOnDirtyIds = allowEventOnDirtyIds;
		this.stateModelId = stateModelId;
		this.shortCutKey = shortCutKey;
		if(checkDirtyModelIds != null)
			this.checkDirtyModelIds = checkDirtyModelIds;
		else
			this.checkDirtyModelIds = new ArrayList(0);
	}
		

	/**
	 * Returns the name of the button.
	 * @return String name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the group id of the button.
	 * @return String group id.
	 */
	public String getButtonGroup()
	{
		return buttonGroup;
	}
	
	/**
	 * Returns the event name constructed from the button.
	 * @return String event name
	 */
	public String getEventName()
	{
		return eventName;
	}
	
	/**
	 * The event type of the button. 
	 * could be one of the following: <code>EVENT_TYPE_READWRITE</code>, <code>EVENT_TYPE_READONLY</code>.
	 * @return int eventType
	 */
	public int getEventType()
	{
		return eventType;
	}

	/**
	 * The target type of window opening from the button. 
	 * could be one of the following: <code>EVENT_TYPE_READWRITE</code>, <code>EVENT_TYPE_READONLY</code>.
	 * @return int openWindowType
	 */
	public String getOpenWindowType()
	{
		return openWindowType;
	}

	/**
	 * Returns the extra parameters to open the dialog / popup window. (hight, width etc.)
	 * @return String windowExtraParams
	 */
	public String getWindowExtraParams()
	{
		return windowExtraParams;
	}

	/**
	 * Returns the type of the button. 
	 * could be one of the following: <code>TYPE_BUTTON</code>, <code>TYPE_SPACER</code>.
	 * @return int type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * returns authorization id declared in the state.
	 * @return String authId 
	 */
	public String getAuthId() 
	{
		return authId;
	}

	/**
	 * Returns the name of the buttonSet that contains the button.
	 * @return String buttonSetName 
	 */
	public String getButtonSetName()
	{
		return buttonSetName;
	}

	/**
	 * Indicates whether to check dirty flag in the client, 
	 * before the event sent to the server.
	 * @return boolean
	 */
	public boolean isCheckDirtyFlag()
	{
		return checkDirtyFlag;
	}

	/**
	 * Returns the ids of the DirtyModels to check in the button before sending 
	 * the event.
	 * @return List
	 */
	public List getCheckDirtyModelIds()
	{
		return checkDirtyModelIds;
	}

	/**
	 * Indicates whether to check warnings flag in the client, 
	 * before the event sent to the server.
	 * @return boolean
	 */
	public boolean isCheckWarnings()
	{
		return checkWarnings;
	}

	/**
	 * Indicates whether to allow sending event to the server when exist dirty model 
	 * on one of the dirty ids.
	 * @return boolean
	 */
	public boolean isAllowEventOnDirtyIds()
	{
		return allowEventOnDirtyIds;
	}

	/**
	 * Returns the confirm message id to display to the user before sending the event.
	 * @return String message id
	 */
	public String getConfirmMessageId()
	{
		return confirmMessageId;
	}

	/**
	 * Returns the short cut key of the button that should send the event 
	 * related to button when it pressed.
	 * @return String
	 */
	public String getShortCutKey()
	{
		return shortCutKey;
	}

	/**
	 * Returns the name of the StateModel that contains the state of the button.
	 * @return String stateModelId 
	 */
	public String getStateModelId()
	{
		return stateModelId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Button name [" + name 
			+ "] group [" + buttonGroup
			+ "] type [" + buttonGroup 
		    + "] eventName [" + eventName 
			+ "] eventType [" + eventType 
			+ "] stateModelId [" + stateModelId 
			+ "] confirmMessageId [" + confirmMessageId 
			+ "] allowEventOnDirtyIds [" + allowEventOnDirtyIds 
			+ "] checkDirtyModelIds [" + checkDirtyModelIds 
			+ "] openWindowType ["	+ openWindowType + "]";
	}

}
