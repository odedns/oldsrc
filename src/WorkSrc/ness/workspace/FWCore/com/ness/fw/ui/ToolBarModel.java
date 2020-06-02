package com.ness.fw.ui;

import java.util.HashMap;
import java.util.Iterator;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.events.Event;
import com.ness.fw.shared.ui.FlowerUIUtil;

public class ToolBarModel extends AbstractModel
{
	/**
	 * Constant used for the state When this value is used,a button click is enabled<br>
	 * and its css class is set by this state. 
	 */
	public final static String BUTTON_STATE_ENABLED = UIConstants.COMPONENT_ENABLED_STATE;

	/**
	 * Constant used for the state When this value is used,a button click is disabled<br>
	 * and its css class is set by this state. 
	 */
	public final static String BUTTON_STATE_DISABLED = UIConstants.COMPONENT_DISABLED_STATE;

	/**
	 * Constant used for the state When this value is used,the button<br>
	 * is not rendered by the tag. 
	 */
	public final static String BUTTON_STATE_HIDDEN = UIConstants.COMPONENT_HIDDEN_STATE;
		
	private static final String TOOLBAR_MODEL_CLICK_EVENT_TYPE = "tbClick";	
	private static final String TOOLBAR_MODEL_CLICKED_BUTTON_PROPERTY = "tbName";
	private static final String TOOLBAR_MODEL_CLICKED_BUTTON_SET_PROPERTY = "tbsName";
	
	private HashMap toolBarButtons;
	
	/**
	 * constructor form ToolBarModel
	 */
	public ToolBarModel()
	{
		toolBarButtons = new HashMap();
	}
	
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.AbstractModel#handleEvent()
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException
	{
		String eventType = getEventTypeProperty();
		if (eventType.equals(TOOLBAR_MODEL_CLICK_EVENT_TYPE))
		{
			//checkEventLegal(new Event(),checkAuthorization);
			String buttonName = (String)getProperty(TOOLBAR_MODEL_CLICKED_BUTTON_PROPERTY);
			String buttonSet = (String)getProperty(TOOLBAR_MODEL_CLICKED_BUTTON_SET_PROPERTY);
			ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
			if (button == null)
			{
				handleUnauthorizedEvent();
			}
			else
			{
				checkEventLegal(button.authLevel,button.eventType);
			}
		}
	}
	
	/**
	 * Sets the state of button by the button name.The state may be one of the 
	 * following: enabled,disabled or hidden.
	 * @param buttonName the name of the button
	 * @param buttonState the state of the button
	 */
	public void setButtonState(String buttonName,String buttonSet,String buttonState)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null)
		{
			toolBarButtons.put(getButtonKey(buttonName,buttonSet),new ToolBarButton(buttonState,null,null,null));
		}
		else
		{
			button.state = buttonState;
		}
	}
	
	/**
	 * sets the class name for "enabled" state
	 * @param buttonName the name of the button
	 * @param className the name of the css class for enabled state
	 */
	public void setButtonEnabledClass(String buttonName,String buttonSet,String className)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null)
		{
			toolBarButtons.put(getButtonKey(buttonName,buttonSet),new ToolBarButton(null,null,className,null));
		}
		else
		{
			button.enabledClassName = className;
		}		
	}

	/**
	 * sets the class name for "disabled" state
	 * @param buttonName the name of the button
	 * @param className the name of the css class for disabled state
	 */
	public void setButtonDisabledClass(String buttonName,String buttonSet,String className)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null)
		{
			toolBarButtons.put(getButtonKey(buttonName,buttonSet),new ToolBarButton(null,className,null,null));
		}
		else
		{
			button.disabledClassName = className;
		}				
	}
	
	/**
	 * Sets script for click event in the client.
	 * @param buttonName the name of the button
	 * @param onClickScript the script for the click event
	 */
	public void setButtonOnClickScript(String buttonName,String buttonSet,String onClickScript)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null)
		{
			toolBarButtons.put(getButtonKey(buttonName,buttonSet),new ToolBarButton(null,null,null,onClickScript));
		}
		else
		{
			button.onClickScript = onClickScript;
		}
	}
	
	
	/**
	 * Returns state of a button by the button's name. 
	 * @param buttonName the name of the button
	 * @return the state of the button.If no state was set,returns "enabled" which
	 * is the default state. 
	 */
	public String getButtonState(String buttonName,String buttonSet)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null)
		{
			return BUTTON_STATE_ENABLED; 	
		}
		else
		{
			if (button.state == null)
			{
				return BUTTON_STATE_ENABLED;
			}
			else
			{
				return button.state;
			}
		}
	}
	
	/**
	 * Returns the class of the button by its state.
	 * @param buttonName the name of the button
	 * @return class name for enabled or disabled state.
	 */
	public String getButtonClassName(String buttonName,String buttonSet)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null || button.state == null)
		{
			return null;
		}
		else
		{
			if (button.state.equals(BUTTON_STATE_ENABLED))
			{
				return button.enabledClassName;
			}
			else if (button.state.equals(BUTTON_STATE_DISABLED))
			{
				return button.disabledClassName;
			}
			else
			{
				return button.enabledClassName;
			}
		}
	}
	
	/**
	 * Returns the script for click event of a button in the model
	 * @param buttonName the name of the button
	 * @return the script for click event of a button in the model or
	 * null if the button does not have such script.
	 */
	public String getButtonOnClickScript(String buttonName,String buttonSet)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null)
		{
			return null;
		}
		else
		{
			return button.onClickScript;		
		}
	}
	
	/**
	 * Sets authorization level and event type for button in the model.
	 * @param buttonName the name of the button in the model
	 * @param authLevel the authorization level which may be - 
	 * FlowerUIUtil.AUTH_LEVEL_ALL,FlowerUIUtil.AUTH_LEVEL_READONLY,FlowerUIUtil.AUTH_LEVEL_NONE
	 * @param eventType the type of the event which may be
	 * Event.EVENT_TYPE_READONLY
	 * Event.EVENT_TYPE_READWRITE
	 */
	public void setButtonAuthLevel(String buttonName,String buttonSet,int authLevel,int eventType)
	{
		ToolBarButton button = (ToolBarButton)toolBarButtons.get(getButtonKey(buttonName,buttonSet));
		if (button == null)
		{
			button = new ToolBarButton();
			button.authLevel = authLevel;
			button.eventType = eventType;
			toolBarButtons.put(getButtonKey(buttonName,buttonSet),button);
		}
		else
		{
			button.authLevel = authLevel;
			button.eventType = eventType;
		}	
	}

	/**
	 * Reset the authorization level of all the buttons in the model. 
	 * This method is useful when 2 toolbars are used with the
	 * same model,and both of them contains one or more common button name(=event),
	 * but different authorization level for each button.To prevent a conflict
	 * this method should be called before each rendering based on this model.
	 */
	public void clearAuth()
	{
		Iterator iter = toolBarButtons.keySet().iterator();
		while (iter.hasNext())
		{
			ToolBarButton button = (ToolBarButton)toolBarButtons.get(iter.next());
			button.clearAuth();
		}
	}
	
	/**
	 * Sets all the states of the buttons of a button set in the model <br>
	 * to BUTTON_STATE_ENABLED
	 */
	public void resetButtonsState(String buttonSet)
	{
		Iterator iter = toolBarButtons.keySet().iterator();
		while (iter.hasNext())
		{
			String buttonKey = (String)iter.next();
			if (buttonSet.equals(getButtonSetKey(buttonKey)))
			{
				ToolBarButton button = (ToolBarButton)toolBarButtons.get(buttonKey);
				button.state = BUTTON_STATE_ENABLED;
			}
		}			
	}
	
	private String getButtonKey(String buttonName,String buttonSet)
	{
		return buttonName + MODEL_PARAM_SEPERATOR + buttonSet;
	}
	
	private String getButtonSetKey(String buttonKey)
	{
		int index = buttonKey.indexOf(MODEL_PARAM_SEPERATOR);
		if (index == -1)
		{
			return "";
		}
		else
		{
			return buttonKey.substring(index + 1);
		}
	}
	
	
	
	class ToolBarButton
	{
		private String state;
		private String disabledClassName;	
		private String enabledClassName;	
		private int authLevel = FlowerUIUtil.AUTH_LEVEL_ALL;
		private int eventType = Event.EVENT_TYPE_DEFAULT;
		private String onClickScript;
		
		ToolBarButton()
		{
		}
		
		ToolBarButton(String state,String disabledClassName,String enabledClassName,String onClickScript)
		{
			this.state = state;
			this.disabledClassName = disabledClassName;
			this.enabledClassName = enabledClassName;
			this.onClickScript = onClickScript;
		}
		
		private void clearAuth()
		{
			authLevel = FlowerUIUtil.AUTH_LEVEL_NONE;
			eventType = Event.EVENT_TYPE_DEFAULT;
		}
	}
}
