/*
 * Created on: 20/10/2004
 * Author: yifat har-nof
 */
package com.ness.fw.common.auth;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.common.MenuItem;
import com.ness.fw.shared.common.SystemConstants;


/**
 * Contains the access to the menu tree with the entries allowed to the current user. 
 */
public abstract class MenuAuthManager 
{
	/**
	 * Constant for NOT_INITIALIZED authorization level. 
	 * It means that the element authorization was not initialized yet.   
	 */
	public static final int AUTH_LEVEL_NOT_INITIALIZED = SystemConstants.AUTH_LEVEL_NOT_INITIALIZED;
	
	/**
	 * Constant for ALL authorization level. 
	 * It means that all the elements will be open / available. 
	 * The elemnts could be: flow / flow state / ui elements i.e. include jsp, 
	 * toolbar, text field, button etc.   
	 */
	public static final int AUTH_LEVEL_ALL = SystemConstants.AUTH_LEVEL_ALL;
	
	/**
	 * Constant for READONLY authorization level. 
	 * It means that all the UI elements will be disabled.
	 * The flow will be created but all the activities (operation, service method, activity) 
	 * with activity type ReadWrite will no be able to be executed.   
	 */
	public static final int AUTH_LEVEL_READONLY = SystemConstants.AUTH_LEVEL_READONLY;
	
	/**
	 * Constant for NONE authorization level. 
	 * It means that all the UI elements will be not visible.
	 * The flow could be created.
	 * Cannot move to state.
	 */
	public static final int AUTH_LEVEL_NONE = SystemConstants.AUTH_LEVEL_NONE;

	/**
	 * The basic authorization data of the current user.
	 */
	private UserAuthData userAuthData;
	
	/**
	 * A Map with the MenuItem's from every menuId.
	 */
	private Map menuIdMap;

	/**
	 * Creates new MenuAuthManagerImpl object.
	 * @param userAuthData The basic authorization data of the current user.
	 */
	public MenuAuthManager(UserAuthData userAuthData, LocalizedResources localizedResources) throws AuthException
	{
		super();
		this.userAuthData = userAuthData;
		menuIdMap = new HashMap(5); 
		loadMenuTree(localizedResources);
	}

	/**
	 * Loads the menu tree for the current user.
	 * Should be implemented by sub classes. 
	 * @param localizedResources
	 * @return MenuItem The root menu item.
	 */
	protected abstract void loadMenuTree(LocalizedResources localizedResources) throws AuthException;

	protected void addMenuId (String menuItemID, MenuItem menuItem)
	{
		menuIdMap.put(menuItemID, menuItem);
	}

	/**
	 * Returns The Map with the MenuItem's from every menuId.
	 * @return Map
	 */
	protected Map getMenuIdMap()
	{
		return menuIdMap;
	}

	/**
	 * Returns the basic authorization data of the current user.
	 * @return UserAuthData
	 */
	protected UserAuthData getUserAuthData()
	{
		return userAuthData;
	}

	/**
	 * Returns The <code>MenuItem</code> object related to the given id.
	 * @param menuItemID The id of the menu item to return 
	 */
	public MenuItem getMenuItem (String menuItemID)
	{
		return (MenuItem)menuIdMap.get(menuItemID);
	}
	
	/**
	 * Returns the allowed menu ids.
	 * @return Iterator
	 */
	public Iterator getMenuIds ()
	{	
		return menuIdMap.keySet().iterator();
	}

}

