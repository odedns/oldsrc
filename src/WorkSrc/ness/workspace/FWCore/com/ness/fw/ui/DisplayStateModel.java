package com.ness.fw.ui;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;


public class DisplayStateModel extends AbstractModel 
{
	/**
	 * Constant used for enabled state
	 */
	public final static String DISPLAY_STATE_ENABLED = UIConstants.COMPONENT_ENABLED_STATE;

	/**
	 * Constant used for disabled state
	 */
	public final static String DISPLAY_STATE_DISABLED = UIConstants.COMPONENT_DISABLED_STATE;

	/**
	 * Constant used for hidden state
	 */
	public final static String DISPLAY_STATE_HIDDEN = UIConstants.COMPONENT_HIDDEN_STATE;
	
	private String state = UIConstants.COMPONENT_ENABLED_STATE; 
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.AbstractModel#handleEvent()
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException 
	{
	}

	/**
	 * @return
	 */
	public String getState() 
	{
		return state;
	}

	/**
	 * @param string
	 */
	public void setState(String string) 
	{
		state = string;
	}

}
