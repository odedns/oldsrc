/*
 * Created on: 21/12/2004
 * Author: yifat har-nof
 * @version $Id: MenuAuthManagerImpl.java,v 1.3 2005/04/11 04:53:43 yifat Exp $
 */
package com.ness.fw.common.auth;

import java.util.Iterator;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.common.MenuItem;
import com.ness.fw.flower.factory.ExtendedTransitionSupplierFactory;
import com.ness.fw.flower.factory.MenuGroupImpl;
import com.ness.fw.util.tree.KeyGenerator;

/**
 * Menu Authorization Manager implementation, 
 * reads the menu items from th emenu xml files.
 */
public class MenuAuthManagerImpl extends MenuAuthManager
{
	private static final String FLOW_1 = "MenuItemFlowNormal";
	private static final String FLOW_2 = "MenuItemFlowNewWindow";
	private static final String FLOW_3 = "MenuItemFlowDialog";
	private static final String FLOW_4 = "MenuItemFlowPopup";
	
	private static KeyGenerator keyGenerator = new KeyGenerator();

	/**
	 * Creates new MenuAuthManagerImpl object.
	 * @param userAuthData The basic authorization data of the current user.
	 * @param localizedResources
	 */
	public MenuAuthManagerImpl(UserAuthData userAuthData, LocalizedResources localizedResources) throws AuthException
	{
		super(userAuthData, localizedResources);
	}
	
	/**
	 * Loads the menu tree for the current user.
	 * Should be implemented by sub classes. 
	 * @param localizedResources
	 * @return MenuItem The root menu item.
	 */
	protected void loadMenuTree(LocalizedResources localizedResources) throws AuthException
	{
		try
		{
			KeyGenerator keyGenerator = new KeyGenerator();
			
			ExtendedTransitionSupplierFactory factory = ExtendedTransitionSupplierFactory.getInstance();
			Iterator iterator = factory.getSupplierGroups();
			while (iterator.hasNext())
			{
				String groupId = (String) iterator.next();
				MenuGroupImpl menuGroup  = (MenuGroupImpl)factory.getTransitionSupplier(groupId);
				String groupText = localizedResources.getString(groupId);
				MenuItem groupMenuItem = new MenuItem (keyGenerator, groupId, groupText);
				addMenuId(groupId, groupMenuItem);
				MenuItem subGroupMenuItem = new MenuItem (keyGenerator, groupId, groupText);
				groupMenuItem.addChild(subGroupMenuItem);
				for(int i = 0 ; i < menuGroup.getMenusCount() ; i++)
				{
					MenuItem menuItem = menuGroup.getMenuItem(i);
					
					String menuText = localizedResources.getString(menuItem.getText());
					subGroupMenuItem.addChild(new MenuItem (keyGenerator, menuItem.getDescription(), menuText, menuItem.getFlowName(), menuItem.getOpenAs(), menuItem.getShortCutKey(), menuItem.getWindowExtraParams()));
				}
			}
		}
		catch (ResourceException e)
		{
			throw new AuthException ("Unable to create authorized menu tree", e);
		}
		
	}	
}
