/*
 * Created on 09/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.common.SystemConstants;

public class DirtyModel extends AbstractModel 
{
	public static final String DIRTY_MODEL_ID = SystemConstants.DIRTY_FLAG_FIELD_NAME;
	public static final String DIRTY_MODEL_ISDIRTY_PROPERTY = "isDirty";	
	private static final String DIRTY_MODEL_DIRTY = "0";
	private static final String DIRTY_MODEL_CLEAN = "1";

	private boolean isDirty = false;
	private String messageId;
	
	/**
	 * Handles events which affect the dirty model
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException 
	{
		if (getProperty(DIRTY_MODEL_ISDIRTY_PROPERTY) != null && getProperty(DIRTY_MODEL_ISDIRTY_PROPERTY).equals(DIRTY_MODEL_DIRTY))
		{
			setDirty();
		}
		else
		{
			setClean();
		}
	}

	/**
	 * Sets true value to the dirty flag
	 */
	public void setDirty()
	{
		isDirty = true;
	}
	
	/**
	 * Returns the dirty indication
	 * @return
	 */
	public boolean isDirty()
	{
		return isDirty;
	}
	
	/**
	 * Sets false value to the dirty flag
	 */
	public void setClean()
	{
		isDirty = false;
	}	
	
	/**
	 * Returns the string constant of the dirty flag
	 * @return string string constant of the dirty flag
	 */
	public String getDirtyIndication()
	{
		return isDirty ? DIRTY_MODEL_DIRTY : DIRTY_MODEL_CLEAN;
	}
	/**
	 * @return
	 */
	public String getMessageId() 
	{
		return messageId;
	}

	/**
	 * @param string
	 */
	public void setMessageId(String string) 
	{
		messageId = string;
	}

}
