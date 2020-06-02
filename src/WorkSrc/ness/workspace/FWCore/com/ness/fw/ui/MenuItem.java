package com.ness.fw.ui;

import com.ness.fw.ui.events.CustomEvent;

public class MenuItem
{
	private String caption;
	private String cssClassName;
	private String cssStyle = "";
	private CustomEvent menuItemClickEvent;
	
	/**
	 * empty constructor for MenuItem
	 */
	public MenuItem()
	{
		this(" ");
	}
	
	/**
	 * Constructor for MenuItem.
	 * @param caption The title of the link
	 */
	public MenuItem(String caption)
	{
		this.caption = caption;
	}
	
	public MenuItem(String caption,CustomEvent event)
	{
		this.caption = caption;
		this.menuItemClickEvent = event;
	}
		
	/**
	 * Constructor for MenuItem.
	 * @param menuItem the menuItem object
	 */
	public MenuItem(MenuItem menuItem)
	{
		setCaption(menuItem.caption);
		setCssClassName(menuItem.cssClassName);
		setCssStyle(menuItem.cssStyle);
	}
	
	/**
	 * Returns the label of this menu item.
	 * @return the label of this menu item.
	 */
	public String getCaption()
	{
		return caption;
	}
	
	/**
	 * Sets the caption.
	 * @param caption The caption to set
	 */
	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	/**
	 * Returns the css class name of this menu item.
	 * @return the css class name of this menu item.
	 */
	public String getCssClassName()
	{
		return cssClassName;
	}
	/**
	 * Returns the css style string of this menu item.
	 * @return the css style string of this menu item.
	 */
	public String getCssStyle()
	{
		return cssStyle;
	}
	/**
	 * Sets the cssClassName.
	 * @param cssClassName The cssClassName to set
	 */
	public void setCssClassName(String cssClassName)
	{
		this.cssClassName = cssClassName;
	}
	/**
	 * Sets the cssStyle.
	 * @param cssStyle The cssStyle to set
	 */
	public void setCssStyle(String cssStyle)
	{
		this.cssStyle = cssStyle;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.CustomEvent} object which holds the <br>information about 
	 * click event on this menuItem. 
	 * @return {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public CustomEvent getMenuItemClickEvent() 
	{
		return menuItemClickEvent;
	}

	/**
	 * Sets the {@link com.ness.fw.ui.events.CustomEvent} object which holds the <br>information about 
	 * click event on this menuItem.
	 * @param event the {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public void setMenuItemClickEvent(CustomEvent event) 
	{
		menuItemClickEvent = event;
	}

}
