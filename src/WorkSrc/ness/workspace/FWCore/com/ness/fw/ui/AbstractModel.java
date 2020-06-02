package com.ness.fw.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.events.Event;

public abstract class AbstractModel
{
	public static final String MODEL_EVENT_DATA_CONSTANT = "";
	public static final String MODEL_PARAM_SEPERATOR = UIConstants.MODEL_PARAM_SEPERATOR;
	protected static final String MODEL_KEY_VALUE_SEPERATOR = "=";
	protected static final String MODEL_MULTI_VALUES_SEPERATOR = ",";
	protected static final String MODEL_MULTI_VALUES_START = "{";
	protected static final String MODEL_MULTI_VALUES_END = "}";
	protected static final String MODEL_EVENT_TYPE_PROPERTY = "eventType";
	
	protected HashMap modelProperties = new HashMap();
	
	protected int authLevel = FlowerUIUtil.AUTH_LEVEL_ALL;
	
	protected String state = MODEL_STATE_ENABLED;
	
	public final static String MODEL_STATE_ENABLED = UIConstants.COMPONENT_ENABLED_STATE;

	public final static String MODEL_STATE_DISABLED = UIConstants.COMPONENT_DISABLED_STATE;

	public final static String MODEL_STATE_HIDDEN = UIConstants.COMPONENT_HIDDEN_STATE;
	
	/**
	 * Sets property for this model
	 * @param name the key of the property(string)
	 * @param value the value of the property(object)
	 */
	protected void setProperty(String name,Object value)
	{
		modelProperties.put(name,value);
	}
	
	/**
	 * Removes property from this model
	 * @param name the key of the property(string)
	 */
	protected void removeProperty(String name)
	{ 
		modelProperties.remove(name);
	}
	
	/**
	 * Returns property of this model
	 * @param name the key of the property
	 * @return Object - property of this model
	 */
	protected Object getProperty(String name)
	{
		return modelProperties.get(name);
	}
	
	/**
	 * Returns string property of this model
	 * @param name the key of the property(string)
	 * @return the value of the property(string)
	 */
	protected String getStringProperty(String name)
	{
		return (String)modelProperties.get(name);
	}
	
	/**
	 * Returns ArrayList property of this model
	 * @param name the key of the property(string)
	 * @return the value of the property(ArrayList)
	 */
	protected ArrayList getListProperty(String name)
	{
		return (ArrayList)modelProperties.get(name);
	}	
	
	/**
	 * Returns the event type that was set for this model,the event type is <br>
	 * a string that represents an event that was last sent to this model.<br>
	 * If no event was sent to this model the methos returns null.
	 * @return the string representation of the type of event sent to this model.
	 */
	public String getEventTypeProperty()
	{
		return (String)modelProperties.get(MODEL_EVENT_TYPE_PROPERTY);
	}
	
	/**
	 * Removes the event type that was set for this model.
	 */
	protected void removeEventType()
	{
		modelProperties.remove(MODEL_EVENT_TYPE_PROPERTY);
	}
	
	protected void removeEventType(String eventTypeName)
	{
		String eventType = getEventTypeProperty();
		if (eventType != null && eventType.equals(eventTypeName))
		{
			removeEventType();
		}
	}
	
	/**
	 * Parses all the data set by the builder.
	 * @param eventDataContent string representing the data sent by the event
	 * @throws UIException if UI problem ocuurs in the parsing of the event data
	 * @throws AuthorizationException if the model is not authorized to parse this event's data
	 */
	public void parseEventData(String eventDataContent) throws UIException, AuthorizationException
	{
		parseEventData(eventDataContent,false);
	}
	
	/**
	 * Parses all the data set by the builder.
	 * @param eventDataContent string representing the data sent by the event
	 * @throws UIException if UI problem ocuurs in the parsing of the event data
	 * @throws AuthorizationException if the model is not authorized to parse this event's data
	 */
	public void parseEventData(String eventDataContent,boolean checkAuthorization) throws UIException, AuthorizationException
	{
		//change the checkAuthorization to (not checkAuthorization)		
		//checkAuthorization = !checkAuthorization;
		//checkAuthorization = true;
	
		//if the data is empty do nothing
		if (!eventDataContent.equals(""))
		{
			//if the authorization level is ALL or PREVIEW,parse the data.
			if (!checkAuthorization || authLevel != FlowerUIUtil.AUTH_LEVEL_NONE)
		//	if (authLevel != FlowerUIUtil.AUTH_LEVEL_NONE)
			{ 
				StringTokenizer params = new StringTokenizer(eventDataContent,MODEL_PARAM_SEPERATOR);		
				while (params.hasMoreTokens())
				{
					String param = params.nextToken();
					int keyValueSeparatorIndex = param.indexOf(MODEL_KEY_VALUE_SEPERATOR);
					if (keyValueSeparatorIndex == -1)
					{
						throw new UIException("the data " + eventDataContent + " from model " + this + " cannot be parsed ");
					}
					String key = param.substring(0,keyValueSeparatorIndex);
					String value = param.substring(keyValueSeparatorIndex + 1);
					setProperty(key,getParsedValue(value));
				}
				handleEvent(checkAuthorization);
			}
			else
			{
				handleUnauthorizedEvent();
			}
		}		
	}
	
	
	private Object getParsedValue(String value)
	{
		int first = value.indexOf(MODEL_MULTI_VALUES_START); 
		if (first != -1)
		{
			int last = value.lastIndexOf(MODEL_MULTI_VALUES_END);
			if (last != -1)
			{
				StringTokenizer values = new StringTokenizer(value.substring(first + MODEL_MULTI_VALUES_START.length(),last),MODEL_MULTI_VALUES_SEPERATOR);
				ArrayList valuesArr = new ArrayList();
				while (values.hasMoreTokens())
				{
					valuesArr.add(values.nextToken());
				}
				return valuesArr;
			}
			else
			{
				return null;
			}		
		}
		else
		{
			return value;
		}
	}
	
	/**
	 * Checks if an event is legal for this model.If it is legal the 
	 * information passed from the server is kept by the model,if not 
	 * it is removed.
	 * @param event the (@link com.ness.fw.events.Event) object
	 * @return true if event is legal
	 */
	protected boolean checkEventLegal(Event event,boolean checkAuthorization) throws UIException, AuthorizationException
	{
		if (!checkAuthorization || (event != null && (authLevel == FlowerUIUtil.AUTH_LEVEL_ALL || authLevel == FlowerUIUtil.AUTH_LEVEL_READONLY && event.getEventType() == Event.EVENT_TYPE_READONLY)))
	//	if ((event != null && (authLevel == FlowerUIUtil.AUTH_LEVEL_ALL || authLevel == FlowerUIUtil.AUTH_LEVEL_READONLY && event.getEventType() == Event.EVENT_TYPE_READONLY)))
		{
			return true;
		}
		else
		{
			handleUnauthorizedEvent();
			return false;
		}			
	}
	
	/**
	 * Checks if an event is legal,by a specific authorizationLevel and eventType. 
	 * @param authorizationLevel 
	 * @param eventType
	 * @return true if the event is legal.
	 * @throws UIException if UI problem ocuurs during the check of the event
	 * @throws AuthorizationException if the event is not legal
	 */
	protected boolean checkEventLegal(int authorizationLevel,int eventType) throws UIException, AuthorizationException
	{
		if (authorizationLevel == FlowerUIUtil.AUTH_LEVEL_ALL || authorizationLevel == FlowerUIUtil.AUTH_LEVEL_READONLY && eventType == Event.EVENT_TYPE_READONLY)
		{
			return true;
		}
		else
		{
			handleUnauthorizedEvent();
			return false;
		}
	}
	
	/**
	 * Handles an unauthorized request.The basic implementation of this method
	 * throws AuthorizationException.
	 * @throws AuthorizationException
	 */
	protected void handleUnauthorizedEvent() throws AuthorizationException
	{
		throw new AuthorizationException("unauthorized action from model " + getClass().getName());
	}
	
	/**
	 * Handles the data that was set by the builder,after the data<br>
	 * was parsed.Each model that inherits the AbstractModel should<br>
	 * implements this method.
	 * @throws UIException if UI problem occurs during the handling of the event
	 */
	protected abstract void handleEvent(boolean checkAuthorization) throws UIException,AuthorizationException;		

	/**
	 * Returns the current authorization level of the model
	 * @return the current authorization level of the model
	 */
	public int getAuthLevel() 
	{
		return authLevel;
	}

	/**
	 * Sets the current authorization level of the model
	 * @param authLevel the current authorization level of the model
	 */
	public void setAuthLevel(int authLevel) 
	{
		this.authLevel = authLevel;
	}
	
	/**
	 * Returns the state of this model which may one of the constants<br>
	 * MODEL_STATE_ENABLED - normal state.
	 * MODEL_STATE_DISABLED - disabled state,the tags that use this model will be disabled<br>
	 * and will not be able to send events whose eventType is READ_WRITE
	 * MODEL_STATE_HIDDEN - the tag that use this model will not be visible.
	 * @return the state of this model
	 */
	public String getState()
	{
		return state;
	}
	
	/**
	 * Sets the state of this model which may one of the constants<br>
	 * MODEL_STATE_ENABLED - normal state.
	 * MODEL_STATE_DISABLED - disabled state,the tags that use this model will be disabled<br>
	 * and will not be able to send events whose eventType is READ_WRITE
	 * MODEL_STATE_HIDDEN - the tag that use this model will not be visible.
	 * @param state the state of this model
	 */
	public void setState(String state)
	{
		this.state = state;
	}

}
