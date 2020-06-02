package com.ness.fw.ui;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.common.XIData;
import com.ness.fw.ui.events.Event;

public abstract class TextModel extends AbstractModel implements XIData
{
	/**
	 * Constant used for the state attribute,which affects the css class name<br> 
	 * of the component.This state is the default state of a component.
	 */	
	public final static String TEXT_ENABLED_STATE = UIConstants.COMPONENT_ENABLED_STATE;

	/**
	 * Constant used for the state attribute,which affects the css class name<br> 
	 * of the component.This state indicates that a field is disabled.
	 */	
	public final static String TEXT_DISABLED_STATE = UIConstants.COMPONENT_DISABLED_STATE;

	/**
	 * Constant used for the state attribute.This state indicates that a text component is invisible.
	 */	
	public final static String TEXT_HIDDEN_STATE = UIConstants.COMPONENT_HIDDEN_STATE;

	/**
	 * Constant used for the inputType attribute,which affects the css class name<br> 
	 * of the component.This inputType indicates that a field is normal.
	 */	
	public final static String TEXT_NORMAL_INPUT_TYPE = UIConstants.COMPONENT_NORMAL_INPUT_TYPE;

	/**
	 * Constant used for the inputType attribute,which affects the css class name<br> 
	 * of the component.This inputType indicates that a value in a text component is mandatory.
	 */	
	public final static String TEXT_MANDATORY_INPUT_TYPE = UIConstants.COMPONENT_MANDATORY_INPUT_TYPE;
	
	protected String inputType;
	protected boolean error;
	protected Object data;
	protected String mask;
	protected String regExpPattern;
	
	/**
	 * handles TextModel events
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException
	{
		checkAutorization(checkAuthorization);
	}
	
	public void checkAutorization(boolean checkAuthorization) throws UIException, AuthorizationException
	{
		checkEventLegal(new Event(Event.EVENT_TYPE_READWRITE),checkAuthorization);
	}
	
	/**
	 * Returns the inputType of this TextModel
	 * @return
	 */
	public String getInputType()
	{
		return inputType;
	}
	
	/**
	 * Sets the inputType of this TextModel
	 * @param inputType
	 */	
	public void setInputType(String inputType)
	{
		this.inputType = inputType;
	}
	
	/**
	 * Returns true if the TextModel is marked as error
	 * @return true if the TextModel is marked as error
	 */
	public boolean isError()
	{
		return error;
	}

	/**
	 * Sets the error state of the TextModel,if true the TextModel is marked as error
	 * @param the error state of the TextModel
	 */
	public void setError(boolean error)
	{
		this.error = error;
	}
	
	public void setData(Object data)
	{
		this.data = data;
	}

	public Object getData() 
	{
		return data;
	}

	/**
	 * @return
	 */
	public String getMask() 
	{
		return mask;
	}

	/**
	 * @param string
	 */
	public void setMask(String mask) 
	{
		this.mask = mask;
	}

	/**
	 * @return
	 */
	public String getRegExpPattern() 
	{
		return regExpPattern;
	}

	/**
	 * @param regExpPattern
	 */
	public void setRegExpPattern(String regExpPattern) 
	{
		this.regExpPattern = regExpPattern;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return getClass().getName() + " Data=" + data;
	}
	
	public boolean equals(Object obj)
	{
		if (data == null)
		{
			return false;
		}
		else
		{
			return data.equals(obj);
		}
	}
}
