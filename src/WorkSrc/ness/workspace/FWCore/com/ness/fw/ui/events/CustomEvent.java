package com.ness.fw.ui.events;

public class CustomEvent extends Event 
{
	protected String eventName;
	
	public CustomEvent()
	{
		super();
	}
	
	public CustomEvent(String eventName)
	{
		this.eventName = eventName;
	}
	
	public CustomEvent(int eventType)
	{
		super(eventType);
	}
	
	public CustomEvent(int eventType,String eventName)
	{
		super(eventType);
		this.eventName = eventName;
	}

	public CustomEvent(int eventType,String eventTargetType,String eventName)
	{
		super(eventType,eventTargetType);
		this.eventName = eventName;
	}

	public CustomEvent(int eventType,String eventTargetType,String eventName,String windowExtraParams)
	{
		super(eventType,eventTargetType,windowExtraParams);
		this.eventName = eventName;
	}

		
	public CustomEvent(CustomEvent customEvent)
	{
		this(customEvent.getEventType(),customEvent.getEventTargetType(),customEvent.getEventName(),customEvent.getWindowExtraParams());
	}
	
	/**
	 * Returns the name of the event to pass to the server when this
	 * events is rendered.
	 * @return Name of event.
	 */
	public String getEventName()
	{
		return eventName;
	}

	/**
	 * Sets clickEventName for this event.
	 * @param clickEventName rhe new clickEventName.
	 */
	public void setEventName(String eventName) 
	{
		this.eventName = eventName;
	}

}
