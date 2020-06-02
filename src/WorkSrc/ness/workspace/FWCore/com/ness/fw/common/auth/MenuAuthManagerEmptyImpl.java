/*
 * Created on: 21/12/2004
 * Author: yifat har-nof
 * @version $Id: MenuAuthManagerEmptyImpl.java,v 1.1 2005/04/11 04:53:43 yifat Exp $
 */
package com.ness.fw.common.auth;

import com.ness.fw.common.resources.LocalizedResources;

/**
 * Empty Menu Authorization Manager implementation, without menu items loading. 
 */
public class MenuAuthManagerEmptyImpl extends MenuAuthManager
{
	/**
	 * Creates new MenuAuthManagerImpl object.
	 * @param userAuthData The basic authorization data of the current user.
	 * @param localizedResources
	 */
	public MenuAuthManagerEmptyImpl() throws AuthException
	{
		super(null, null);
	}
	
	/**
	 * Loads the menu tree for the current user.
	 * Should be implemented by sub classes. 
	 * @param localizedResources
	 * @return MenuItem The root menu item.
	 */
	protected void loadMenuTree(LocalizedResources localizedResources) throws AuthException
	{
	}	
}
