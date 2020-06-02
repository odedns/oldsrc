package com.ness.fw.ui;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.events.CustomEvent;

public class ButtonModel extends AbstractModel
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
	
	private static final String BUTTON_MODEL_CLICK_EVENT_TYPE = "bClick";
	
	protected String value;
	protected boolean translateValue = true;
	
	/**
	 * {@link com.ness.fw.ui.events.CustomEvent} object which holds the default <br>information about 
	 * click event on a button.
	 */
	protected CustomEvent buttonClickEvent;
	
	/**
	 * Handles button events
	 */
	protected void handleEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		String eventType = getEventTypeProperty();
		if (eventType != null && eventType.equals(BUTTON_MODEL_CLICK_EVENT_TYPE))
		{
			checkEventLegal(buttonClickEvent,checkAuthorization);
		}
	}
	
	/**
	 * Returns the {@link com.ness.fw.ui.events.CustomEvent} object which holds the default information about 
	 * click event on a button. 
	 * @return {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public CustomEvent getButtonClickEvent() 
	{
		return buttonClickEvent;
	}

	/**
	 * Sets the {@link com.ness.fw.ui.events.CustomEvent} object which holds the default information about 
	 * click event on a button. 
	 * @param event the {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public void setButtonClickEvent(CustomEvent event) 
	{
		buttonClickEvent = event;
	}

	/**
	 * Returns the caption of the button
	 * @return string representing the caption of the button
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the caption of the button
	 * @param value string representing the caption of the button
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public boolean isTranslateValue() 
	{
		return translateValue;
	}

	/**
	 * @param b
	 */
	public void setTranslateValue(boolean translateValue) 
	{
		this.translateValue = translateValue;
	}

}
