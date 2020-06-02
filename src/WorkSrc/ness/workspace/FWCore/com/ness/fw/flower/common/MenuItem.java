/*
 * Created on: 15/12/04
 * Author: yifat har-nof
 * @version $$Id: MenuItem.java,v 1.2 2005/02/21 16:45:58 shay Exp $$
 */
package com.ness.fw.flower.common;

import java.util.Iterator;

import com.ness.fw.ui.events.MenuEvent;
import com.ness.fw.util.tree.KeyGenerator;
import com.ness.fw.util.tree.Node;

/**
 * Represents a menu item in the menu tree.
 * <b>
 * The value of the description will be sent as an event name to the server when 
 * menu button or the short cut key is pressed.
 */
public class MenuItem extends Node
{
	private static final String EVENT_PREFIX = "Evt-"; 
	
	/**
	 * Constant used to indicate that flow opened using the menu item should be shown in the same window
	 */
	public static final String OPEN_AS_DIALOG = MenuEvent.EVENT_TARGET_TYPE_DIALOG;

	/**
	 * Constant used to indicate that flow opened using the menu item should be shown in the same window
	 */
	public static final String OPEN_AS_NORMAL = MenuEvent.EVENT_TARGET_TYPE_NORMAL;

	/**
	 * Constant used to indicate that flow opened using the menu item should be shown in popup window
	 */
	public static final String OPEN_AS_POPUP = MenuEvent.EVENT_TARGET_TYPE_POPUP;

	/**
	 * Constant used to indicate that flow opened using the menu item should be shown in the separate window
	 */
	public static final String OPEN_AS_NEW_WINDOW = MenuEvent.EVENT_TARGET_TYPE_NEW_WINDOW;

	/**
	 * The text of the menu item.
	 */
	private String text;

	/**
	 * The name of the flow to be executed when menu button or the short cut 
	 * key is pressed.
	 */
	private String flowName;

	/**
	 * Indicates how the flow should be opened (in the same window, new window, popup, dialog). See constants.
	 */
	private String openAs;
	
	/**
	 * The short cut key of the menu item that should send the event related to menu item when it pressed. 
	 */
	private String shortCutKey;

	/**
	 * The window extra params for opening a popup window or a modal dialog when the menu item is pressed.
	 */
	private String windowExtraParams;

	/**
	 * The input formatter to perform when the subflow starts.
	 */
	private String inFormatterName;

	/**
	 * The output formatter to perform when the subflow finished.
	 */
	private String outFormatterName;

	/**
	 * The event to send.
	 */
	private String eventName;

	/**
	 * The flow path to send the event.
	 */
	private String flowPath;
	
	/**
	 * The flow state name to send the event.
	 */
	private String flowState;

	/**
	 * Creates new MenuItem Object.
	 * @param key The key for this node.
	 * @param description The description of the node. will be sent as event name to the 
	 * server when menu button or the short cut key is pressed.
	 * @param text The text of the menu item. 
	 */
	public MenuItem(Integer key, String description, String text)
	{
		super(key, description);
		this.text = text;
		setDefaultEventName();
	}

	/**
	 * Creates new MenuItem Object.
	 * @param keyGen The KeyGenerator that will be used to generate the key of the menu item.
	 * @param description The description of the node. will be sent as event name to the 
	 * server when menu button or the short cut key is pressed.
	 * @param text The text of the menu item. 
	 */
	public MenuItem(KeyGenerator keyGenerator, String description, String text)
	{
		super(keyGenerator, description);
		this.text = text;
		setDefaultEventName();
	}

	/**
	 * Creates new MenuItem Object.
	 * @param key The key for this node.
	 * @param description The description of the node. will be sent as event name to the 
	 * server when menu button or the short cut key is pressed.
	 * @param text The text of the menu item. 
	 * @param flowName The name of the flow to be executed when menu button or the short cut 
	 * key is pressed. 
	 * @param openAs Indicates how the flow should be opened (in the same window, new window, popup, dialog). See constants.
	 * @param shortCutKey The short cut key of the menu item that should send the event related to menu item when it pressed.
	 * @param windowExtraParams The window extra param for opening a popup window or a modal dialog when the menu item is pressed.
	 */
	public MenuItem(Integer key, String description, String text, String flowName, String openAs, String shortCutKey, String windowExtraParams)
	{
		super(key, description);
		
		this.text = text;
		this.flowName = flowName;
		this.openAs = openAs;
		this.shortCutKey = shortCutKey;
		this.windowExtraParams = windowExtraParams;
		setDefaultEventName();
	}

	/**
	 * Creates new MenuItem Object.
	 * @param keyGen The KeyGenerator that will be used to generate the key of the menu item.
	 * @param description The description of the node. will be sent as event name to the 
	 * server when menu button or the short cut key is pressed.
	 * @param text The text of the menu item. 
	 * @param flowName The name of the flow to be executed when menu button or the short cut 
	 * key is pressed. 
	 * @param openAs Indicates how the flow should be opened (in the same window, new window, popup, dialog). See constants.
	 * @param shortCutKey The short cut key of the menu item that should send the event related to menu item when it pressed.
	 * @param windowExtraParams The window extra param for opening a popup window or a modal dialog when the menu item is pressed. 
	 */
	public MenuItem(KeyGenerator keyGenerator, String description, String text, String flowName, String openAs, String shortCutKey, String windowExtraParams)
	{
		super(keyGenerator, description);
		
		this.text = text;
		this.flowName = flowName;
		this.openAs = openAs;
		this.shortCutKey = shortCutKey;
		this.windowExtraParams = windowExtraParams;
		setDefaultEventName();
	}

	/**
	 * Creates new MenuItem Object.
	 * @param keyGen The KeyGenerator that will be used to generate the key of the menu item.
	 * @param description The description of the node. will be sent as event name to the 
	 * server when menu button or the short cut key is pressed.
	 * @param text The text of the menu item. 
	 * @param flowName The name of the flow to be executed when menu button or the short cut 
	 * key is pressed. 
	 * @param openAs Indicates how the flow should be opened (in the same window, new window, popup, dialog). See constants.
	 * @param shortCutKey The short cut key of the menu item that should send the event related to menu item when it pressed.
	 * @param windowExtraParams The window extra param for opening a popup window or a modal dialog when the menu item is pressed.
	 * @param inFormatterName The input formatter to perform when the subflow starts.
	 * @param outFormatterName The output formatter to perform when the subflow finished.
	 */
	public MenuItem(KeyGenerator keyGenerator, String description, String text, String flowName, String openAs, String shortCutKey, String windowExtraParams, String inFormatterName, String outFormatterName)
	{
		super(keyGenerator, description);
		
		this.text = text;
		this.flowName = flowName;
		this.openAs = openAs;
		this.shortCutKey = shortCutKey;
		this.windowExtraParams = windowExtraParams;
		this.inFormatterName = inFormatterName;
		this.outFormatterName = outFormatterName;
		setDefaultEventName();
	}
	
	/**
	 * Creates new MenuItem Object.
	 * @param key The key for this node.
	 * @param description The description of the node. will be sent as event name to the 
	 * server when menu button or the short cut key is pressed.
	 * @param text The text of the menu item. 
	 * @param flowName The name of the flow to be executed when menu button or the short cut 
	 * key is pressed. 
	 * @param openAs Indicates how the flow should be opened (in the same window, new window, popup, dialog). See constants.
	 * @param shortCutKey The short cut key of the menu item that should send the event related to menu item when it pressed.
	 * @param windowExtraParams The window extra param for opening a popup window or a modal dialog when the menu item is pressed.
	 * @param inFormatterName The input formatter to perform when the subflow starts.
	 * @param outFormatterName The output formatter to perform when the subflow finished.
	 */
	public MenuItem(Integer key, String description, String text, String flowName, String openAs, String shortCutKey, String windowExtraParams, String inFormatterName, String outFormatterName)
	{
		super(key, description);
		
		this.text = text;
		this.flowName = flowName;
		this.openAs = openAs;
		this.shortCutKey = shortCutKey;
		this.windowExtraParams = windowExtraParams;
		this.inFormatterName = inFormatterName;
		this.outFormatterName = outFormatterName;
		setDefaultEventName();
	}

	private void setDefaultEventName ()
	{
		eventName = getDescription();
	}

	/**
	 * Returns the event name to send to the server when 
	 * menu button or the short cut key is pressed.
	 * @return String
	 */
	public String getEventName()
	{
		return eventName;
	}

	/**
	 * Returns the text of the menu item.
	 * @return String
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Returns the indication how the flow should be opened (in the same window, new window, popup, dialog). See constants.
	 * @return String
	 */
	public String getOpenAs()
	{
		return openAs;
	}

	/**
	 * Returns the short cut key of the menu item that should send the event related to menu item when it pressed.
	 * @return String
	 */
	public String getShortCutKey()
	{
		return shortCutKey;
	}

	/**
	 * Returns the name of the flow to be executed when menu button or the short cut 
	 * key is pressed.
	 * @return String
	 */
	public String getFlowName()
	{
		return flowName;
	}

	/**
	 * Returns the input formatter to perform when the subflow starts.
	 * @return String
	 */
	public String getInFormatterName()
	{
		return inFormatterName;
	}

	/**
	 * Returns the output formatter to perform when the subflow finished.
	 * @return String
	 */
	public String getOutFormatterName()
	{
		return outFormatterName;
	}
	
	/**
	 * Returns the flow path to send the event.
	 * @return String
	 */
	public String getFlowPath()
	{
		return flowPath;
	}

	/**
	 * Returns the flow state name to send the event.
	 * @return String 
	 */
	public String getFlowState()
	{
		return flowState;
	}

	/**
	 * Returns the window extra param for opening a popup window or a modal dialog.
	 * @return String
	 */
	public String getWindowExtraParams() 
	{
		return windowExtraParams;
	}



	/**
	 * Sets the flow path, flow state name & event name in each menu item in the hierarchy.
	 * @param normalFlowPath
	 * @param normalFlowState
	 * @param newWindowFlowPath
	 * @param newWindowFlowState
	 * @param eventNamePrefix
	 */
	public void setEventData(String normalFlowPath, String normalFlowState, String newWindowFlowPath, String newWindowFlowState, String eventNamePrefix)
	{
		if(isLeaf())
		{
			if(isSendEventToMenuFlow())
			{
				flowPath = normalFlowPath;
				flowState = normalFlowState;
			}
			else
			{
				flowPath = newWindowFlowPath;
				flowState = newWindowFlowState;
			}
//			eventName = eventNamePrefix + EVENT_PREFIX + getDescription();
		}
		
		if(hasChildren())
		{
			Iterator iter = getChildren().values().iterator();
			while(iter.hasNext())
			{
				MenuItem menuItem = (MenuItem)iter.next();
				menuItem.setEventData(normalFlowPath, normalFlowState, newWindowFlowPath, newWindowFlowState, eventNamePrefix);
			}
		}
	}

	public boolean isSendEventToMenuFlow ()
	{
		return openAs.equals(MenuItem.OPEN_AS_NORMAL);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("MenuItem: \n");
		sb.append(" key=");
		sb.append(getKey());
		sb.append(" eventName=");
		sb.append(getEventName());
		sb.append(" text=");
		sb.append(text);
		sb.append(" flowName=");
		sb.append(flowName);
		sb.append(" openAs=");
		sb.append(openAs);
		sb.append(" shortCutKey=");
		sb.append(shortCutKey);
		sb.append(" windowExtraParams=");
		sb.append(windowExtraParams);
		sb.append("\n inFormatterName=");
		sb.append(inFormatterName);
		sb.append(" outFormatterName=");
		sb.append(outFormatterName);
		sb.append(" flowPath=");
		sb.append(flowPath);
		sb.append(" flowState=");
		sb.append(flowState);
		sb.append("\n");
//		sb.append(super.toString());

		if(hasChildren())
		{
			Iterator iter = getChildren().values().iterator();
			while(iter.hasNext())
			{
				MenuItem menuItem = (MenuItem)iter.next();
				sb.append(" Child ");
				sb.append(menuItem.toString());
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
