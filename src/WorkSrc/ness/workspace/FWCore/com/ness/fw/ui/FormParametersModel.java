package com.ness.fw.ui;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.events.CustomEvent;

public class FormParametersModel extends AbstractModel 
{
	/**
	 * Constant used for the state attribute.When this value is used,the opening of the form parameters is enabled
	 */
	public final static String FORM_PARAMETERS_STATE_ENABLED = UIConstants.FORM_PARAMETERS_STATE_ENABLED;

	/**
	 * Constant used for the state attribute.When this value is used,the opening of the form parameters is disabled
	 */
	public final static String FORM_PARAMETERS_STATE_DISABLED = UIConstants.FORM_PARAMETERS_STATE_DISABLED;

	private final static String FORM_PARAMETERS_MODEL_CLICK_EVENT_TYPE = "fmClick";

	/**
	 * {@link com.ness.fw.ui.events.CustomEvent} object which holds the default <br>information about 
	 * click event on a button of a form parameters.
	 */
	protected CustomEvent buttonClickEvent;
	
	/**
	 * Constructs empty FormParametersModel
	 */
	public FormParametersModel()
	{
		buttonClickEvent = new CustomEvent();
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.AbstractModel#handleEvent()
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException 
	{
		String eventType = getEventTypeProperty();
		if (eventType != null && eventType.equals(FORM_PARAMETERS_MODEL_CLICK_EVENT_TYPE))
		{
			checkEventLegal(buttonClickEvent,checkAuthorization);
		}
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.CustomEvent} object which holds the default information about<br>
	 * click event on a button of a form parameters. 
	 * @return {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public CustomEvent getButtonClickEvent() 
	{
		return buttonClickEvent;
	}

	/**
	 * Sets the {@link com.ness.fw.ui.events.CustomEvent} object which holds the default information about<br> 
	 * click event on a button of a form parameters. 
	 * @param the {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public void setButtonClickEvent(CustomEvent event) 
	{
		buttonClickEvent = event;
	}

}
