package com.ness.fw.ui.events;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.shared.common.SystemConstants;

public class Event 
{
	/**
	 * This constant is related to level of authorization.<br>
	 * It indicates that this event may only cause to read actions<br>
	 * which means that a less strict level of authorization should be used.	 
	 */
	public final static int EVENT_TYPE_READONLY = SystemConstants.EVENT_TYPE_READONLY;

	/**
	 * This constant is related to level of authorization.<br>
	 * It indicates that this event may also cause to write actions<br>
	 * which means that a more strict level of authorization should be used.
	 */
	public final static int EVENT_TYPE_READWRITE = SystemConstants.EVENT_TYPE_READWRITE;

	/**
	 * This constant is used for the default authorization ype of the event
	 * which is EVENT_TYPE_READWRITE
	 */
	public final static int EVENT_TYPE_DEFAULT = EVENT_TYPE_READWRITE;
	
	/**
	 * This constant is used for the eventType property when sending an event to a normal window.
	 */
	public final static String EVENT_TARGET_TYPE_NORMAL = SystemConstants.EVENT_TARGET_TYPE_NORMAL;

	/**
	 * This constant is used for the eventType property when sending an event to a popup window.
	 */
	public final static String EVENT_TARGET_TYPE_POPUP = SystemConstants.EVENT_TARGET_TYPE_POPUP;

	/**
	 * This constant is used for the eventType property when sending an event to a modal window.
	 */
	public final static String EVENT_TARGET_TYPE_DIALOG = SystemConstants.EVENT_TARGET_TYPE_DIALOG;		

	/**
	 * This constant is used for the eventType property when sending an event and closing a modal window.
	 */
	public final static String EVENT_TARGET_TYPE_CLOSE_DIALOG = SystemConstants.EVENT_TARGET_TYPE_CLOSE_DIALOG;

	/**
	 * This constant is used for the eventType property when sending an event and closing a popup window.
	 */
	public final static String EVENT_TARGET_TYPE_CLOSE_POPUP = SystemConstants.EVENT_TARGET_TYPE_CLOSE_POPUP;

	/**
	 * This constant is used for the the default value of the eventType property,<br>
	 * when sending an event to a normal window.
	 */
	public final static String EVENT_TARGET_TYPE_DEFAULT = EVENT_TARGET_TYPE_NORMAL;
	
	public final static String WARNING_EVENT_SUFFIX = ".Accept";
	
	private final static String EVENT_TARGET_NAME_CLOSE_DIALOG = "closeDialog";
	private final static String EVENT_TARGET_NAME_CLOSE_POPUP = "closePopup";
	private final static String EVENT_TARGET_NAME_NORMAL = " ";
	
	protected int eventType = EVENT_TYPE_DEFAULT;	
	protected String eventTargetType = EVENT_TARGET_TYPE_DEFAULT;
	protected boolean checkDirty = false;
	protected boolean checkWarnings = false;
	protected boolean allowEventOnDirtyIds = true;
	protected String confirmMessageId;
	protected String windowExtraParams = " ";
	protected List dirtyModels; 
	
	/**
	 * 
	 */
	public Event()
	{
	}
	
	public Event(int eventType) 
	{
		this.eventType = eventType;
	}

	public Event(int eventType,String eventTargetType) 
	{
		this.eventType = eventType;
		this.eventTargetType = eventTargetType;
	}
	
	public Event(int eventType,String eventTargetType,String windowExtraParams) 
	{
		this.eventType = eventType;
		this.eventTargetType = eventTargetType;
		this.windowExtraParams = windowExtraParams;
	}	

	/**
	 * Returns the type of the event which may be one of the constants:
	 * EVENT_TYPE_READONLY or EVENT_TYPE_READWRITE.
	 * @return an integer representing the type of this event.Default value is EVENT_TYPE_READONLY.
	 */
	public int getEventType() 
	{
		return eventType;
	}

	/**
	 * Sets the type of the event which may be one of the constants:
	 * EVENT_TYPE_READONLY or EVENT_TYPE_READWRITE.
	 * @param eventType The new type of the event. 
	 */
	public void setEventType(int eventType) 
	{
		this.eventType = eventType;
	}

	/**
	 * Returns the type of the target of the event which may be one of the constants:
	 * EVENT_TARGET_TYPE_NORMAL - opens in the same window(default).
	 * EVENT_TARGET_TYPE_POPUP - opens in a popup window.
	 * EVENT_TARGET_TYPE_DIALOG - opens in a modal window.
	 * EVENT_TARGET_NEW_WINDOW - opens in new window(not popup but an actual window).
	 * EVENT_TARGET_TYPE_CLOSE_POPUP - returns from a popup window to the main window.
	 * EVENT_TARGET_TYPE_CLOSE_DIALOG - returns from a modal window to the main window.
	 * @return string representing the type of the target of the event.
	 */
	public String getEventTargetType() 
	{
		return eventTargetType;
	}

	/**
	 * Sets the target type of the event which may be one of the constants:
	 * EVENT_TARGET_TYPE_NORMAL - opens in the same window(default).
	 * EVENT_TARGET_TYPE_POPUP - opens in a popup window.
	 * EVENT_TARGET_TYPE_DIALOG - opens in a modal window.
	 * EVENT_TARGET_NEW_WINDOW - opens in new window(not popup but an actual window).
	 * EVENT_TARGET_TYPE_CLOSE_POPUP - returns from a popup window to the main window.
	 * EVENT_TARGET_TYPE_CLOSE_DIALOG - returns from a modal window to the main window.
	 * @param eventTargetType the new type of the target of the event.
	 */
	public void setEventTargetType(String eventTargetType) 
	{
		this.eventTargetType = eventTargetType;
	}
	
	public String getEventTargetName()
	{
		return getTargetNameByTargetType(eventTargetType);
	}
	
	public static String getTargetNameByTargetType(String targetType)
	{
		if (targetType.equals(EVENT_TARGET_TYPE_CLOSE_POPUP))
		{
			return EVENT_TARGET_NAME_CLOSE_POPUP;
		}
		else if (targetType.equals(EVENT_TARGET_TYPE_CLOSE_DIALOG))
		{
			return EVENT_TARGET_NAME_CLOSE_DIALOG;
		}
		else
		{
			return EVENT_TARGET_NAME_NORMAL;
		}		
	}

	/**
	 * Returns the checkDirty property.If true,there will be a check for dirty
	 * flag before sending an event to the server.
	 * @return checkDirty if true,there will be a check for dirty
	 * flag before sending an event to the server. 
	 */
	public boolean isCheckDirty() 
	{
		return checkDirty;
	}

	/**
	 * Sets the checkDirty property.If true,there will be a check for dirty
	 * flag before sending an event to the server.
	 * @param checkDirty if true,there will be a check for dirty
	 * flag before sending an event to the server. 
	 */
	public void setCheckDirty(boolean checkDirty) 
	{
		this.checkDirty = checkDirty;
	}

	/**
	 * Returns the checkWarnings property.If true,there will be a check for warnings
	 * before sending an event to the server.
	 * @return checkWarnings if true,there will be a check for warnings
	 * before sending an event to the server. 
	 */
	public boolean isCheckWarnings() 
	{
		return checkWarnings;
	}

	/**
	 * Sets the checkWarning property.If true,there will be a check for warnings
	 * flag before sending an event to the server.
	 * @param checkWarning if true,there will be a check for dirty
	 * flag before sending an event to the server. 
	 */
	public void setCheckWarnings(boolean checkWarning) 
	{
		this.checkWarnings = checkWarning;
	}

	
	/**
	 * Sets list of dirty models' ids.The event should check all the flags of those<br>
	 * models before sending the event to the server.
	 * @return list of dirty models' ids.
	 */
	public List getDirtyModels() 
	{
		return dirtyModels;
	}

	/**
	 * Returns list of dirty models' ids.The event should check all the flags of those<br>
	 * models before sending the event to the server.
	 * @param dirtyModels the list of dirty models' ids.
	 */
	public void setDirtyModels(List dirtyModels) 
	{
		this.dirtyModels = dirtyModels;
	}
	
	/**
	 * Adds dirty model's id to the list of dirty models' ids.
	 * @param dirtyModelId the id of the dirty model to add.
	 */
	public void addDirtyModelId(String dirtyModelId)
	{
		if (dirtyModels == null)
		{
			dirtyModels = new ArrayList();
		}
		dirtyModels.add(dirtyModelId);
	}
	
	/**
	 * Removes dirty model's id from the list of dirty models' ids.
	 * @param dirtyModelId the id of the dirty model to remove.
	 */
	public void removeDirtyModelId(String dirtyModelId)
	{
		if (dirtyModels != null)
		{
			dirtyModels.remove(dirtyModelId);
		}
	}
	
	/**
	 * @return
	 */
	public boolean isAllowEventOnDirtyIds() 
	{
		return allowEventOnDirtyIds;
	}

	/**
	 * @param b
	 */
	public void setAllowEventOnDirtyIds(boolean b) 
	{
		allowEventOnDirtyIds = b;
	}

	/**
	 * Returns the confirmMessageId of the confirmation message that will appeae<br>
	 * before sending the event.
	 * @return confirmMessageId
	 */
	public String getConfirmMessageId() 
	{
		return confirmMessageId;
	}

	/**
	 * Sets the confirmMessageId of the confirmation message that will appeae<br>
	 * before sending the event.
	 * @param confirmMessageId
	 */
	public void setConfirmMessageId(String confirmMessageId) 
	{
		this.confirmMessageId = confirmMessageId;
	}

	/**
	 * Returns the extra params used when the event causes the opening of a<br>
	 * new window.Relevant when eventTargetType is EVENT_TARGET_TYPE_DIALOG or EVENT_TARGET_TYPE_POPUP
	 * @return a string representing the parameters for opening a new window.
	 */
	public String getWindowExtraParams() 
	{
		return windowExtraParams;
	}

	/**
	 * Sets the extra params used when the event causes the opening of a<br>
	 * new window.Relevant when eventTargetType is EVENT_TARGET_TYPE_DIALOG or EVENT_TARGET_TYPE_POPUP
	 * @param windowExtraParams a string representing the parameters for opening a new window.
	 */
	public void setWindowExtraParams(String windowExtraParams) 
	{
		this.windowExtraParams = windowExtraParams;
	}

}
