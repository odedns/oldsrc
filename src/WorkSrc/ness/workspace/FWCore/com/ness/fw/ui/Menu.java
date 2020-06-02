package com.ness.fw.ui;

import java.util.ArrayList;
public class Menu
{
	private ArrayList menuItems;
	private String cssClassName = "";
	private String cssStyle = "";
	
	/**
	 * Empty constructor for Menu.
	 */
	public Menu()
	{
		menuItems = new ArrayList();
	}
	
	public Menu(Menu menu)
	{
		menuItems = new ArrayList();
		if (menu != null)
		{
			setCssClassName(menu.cssClassName);
			setCssStyle(menu.cssStyle);
			for (int index = 0; index < menu.getMenuItemsCounts(); index++)
			{
				addMenuItem(new MenuItem(menu.getMenuItem(index)));
			}
		}
	}
	
	/**
	 * Adds (@link MenuItem) to menu items list
	 * @param menuItem the menu item to add 
	 */
	public void addMenuItem(MenuItem menuItem)
	{
		menuItems.add(menuItem);
	}
	
	/**
	 * Returns (@link MenuItem) from menu items
	 * @param index the order of the menu ite, in the list 
	 */
	public MenuItem getMenuItem(int index)
	{
		return (MenuItem) menuItems.get(index);
	}
	
	/**
	 * Returns the number of menu items in the menu
	 */
	public int getMenuItemsCounts()
	{
		return menuItems.size();
	}
	
	/**
	 * Returns the css class name of this menu.
	 * @return the css class name of this menu.
	 */
	public String getCssClassName()
	{
		return cssClassName;
	}
	/**
	 * Returns the css style of this menu.
	 * @return the css style of this menu
	 */
	public String getCssStyle()
	{
		return cssStyle;
	}
	
	/**
	 * Sets the css class name of this menu.
	 * @param cssClassName the css class name of this menu.
	 */
	public void setCssClassName(String cssClassName)
	{
		this.cssClassName = cssClassName;
	}
	
	/**
	 * Sets the css style of this menu.
	 * @param cssStyle the css style of this menu
	 */
	public void setCssStyle(String cssStyle)
	{
		this.cssStyle = cssStyle;
	}
}
