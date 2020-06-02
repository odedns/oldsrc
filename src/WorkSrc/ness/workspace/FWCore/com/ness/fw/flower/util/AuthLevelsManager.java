/*
 * Created on 15/03/2004
 * Author: yifat har-nof
 * @version $Id: AuthLevelsManager.java,v 1.2 2005/04/11 05:05:31 yifat Exp $
 */
package com.ness.fw.flower.util;

import java.util.*;

import com.ness.fw.common.auth.AuthException;
import com.ness.fw.common.auth.AuthManager;
import com.ness.fw.common.auth.AuthManagerFactory;
import com.ness.fw.common.auth.ElementAuthLevel;
import com.ness.fw.common.auth.MenuAuthManager;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.externalization.ExternalizationException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.flower.common.MenuItem;
import com.ness.fw.flower.core.Flow;
import com.ness.fw.flower.core.Transition;
import com.ness.fw.flower.core.TransitionList;
import com.ness.fw.flower.core.TransitionsListMap;
import com.ness.fw.flower.factory.MenuTransitionFactory;
import com.ness.fw.flower.factory.externalization.ExternalizerNotInitializedException;
import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.util.tree.Node;

/**
 * Holds the data of the authorization levels of the user.
 * <br>
 * The object contains the auth managers with the allowed flows, components 
 * & menu trees.
 * <br>
 * Provide services to determine the authorization level of the components. 
 */
public class AuthLevelsManager
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
	 * The logger context.
	 */
	private static final String LOGGER_CONTEXT = "AUTHORIZATION";

	/**
	 * A stack with the authorization levels of the current request.
	 */
	private Stack stack;
		
	/**
	 * Authorization manager, Contains the authorization data for the user.
	 */
	private AuthManager authManager;
	
	/**
	 * MenuAuthManager, Contains the access to the menu tree with the entries allowed to the current user.
	 */
	private MenuAuthManager menuAuthManager;

	/**
	 * The transitions of the menu items
	 */	
	private TransitionsListMap menuTransitionsListMap;
	
	/**
	 * Creates new AuthLevelsManager object.
	 * @param userAuthData Contains the basic authorization data of the current user.
	 */
	protected AuthLevelsManager(UserAuthData userAuthData, LocalizedResources localizedResources) throws AuthException
	{
		stack = new Stack();
		if(!userAuthData.isEmptyUser())
		{
			authManager = AuthManagerFactory.getAuthManager(userAuthData);
			menuAuthManager = AuthManagerFactory.getMenuAuthManager(userAuthData, localizedResources);
		}
		else
		{
			authManager = AuthManagerFactory.getPublicAuthManager();
			menuAuthManager = AuthManagerFactory.getEmptyMenuAuthManager();
		}
		
		try
		{
			createMenuTransitions();
		}
		catch (ExternalizerNotInitializedException e)
		{
			throw new AuthException("Error constructing menu transitions", e);
		}
		catch (ExternalizationException e)
		{
			throw new AuthException("Error constructing menu transitions", e);
		}
	}

	/**
	 * Returns the auth level, according to the last auth id in the stack.
	 * @param sourceName The name of the source that should get the auth level.
	 * @return ElementAuthLevel The authorization level information of the element.
	 * @throws ResourceException
	 */
	public ElementAuthLevel getAuthLevel(String sourceName)
		throws ResourceException
	{
		return getAuthLevel(null, sourceName);
	}

	/**
	 * Returns the auth level, according to the last auth id in the stack and 
	 * the auth level determined in the authorization management system to 
	 * the given authId .
	 * @param authId (optional) The authorization id of the component to get the 
	 * level from the authorization management system
	 * @param sourceName The source name of the element.
	 * @return ElementAuthLevel The authorization level information of the element.
	 * @throws ResourceException
	 */
	public ElementAuthLevel getAuthLevel(String authId, String sourceName)
		throws ResourceException
	{
		boolean insertLevelToStack = false;
		
		int authLevel = determineElementAuthLevel(authId);
		
		// check if the auth level of the element is more severe then 
		// the last auth level of the stack.
		if (authLevel > getLastAuthLevel())
		{
			stack.push(new Integer(authLevel));
			insertLevelToStack = true;
			Logger.debug(LOGGER_CONTEXT, "Push element [" + sourceName + "] auth level [" + authLevel + "]");
		}

		return new ElementAuthLevel(authLevel, insertLevelToStack);
	}
	
	/**
	 * Returns the auth level of a specific page.
	 * @param pageElement The page element of the flow
	 * @param sourceName The source name of the element.
	 * @return int auth level
	 * @throws ResourceException
	 */
	public int getPageAuthLevel(PageElementAuthLevel pageElement, String sourceName)
			throws ResourceException
	{
		// get last level from stack
		int authLevel = getLastAuthLevel();
		
		if (pageElement.getAuthLevel() > authLevel)
		{
			pageElement.setAuthLevelInsertedToStack(true);
			authLevel = pageElement.getAuthLevel();
			stack.push(new Integer(authLevel));
			Logger.debug(LOGGER_CONTEXT, "Push element [" + sourceName + "] page [" + pageElement.getPage() + "] auth level [" + authLevel + "]");
		}

		return authLevel;
	}
	
	/**
	 * Determines the auth level of the first flow in the request.
	 * @param flow The first flow.
	 * @return ElementAuthLevel The authorization level of the first flow.
	 * @throws ResourceException
	 */
	public ElementAuthLevel setFirstFlowAuthLevel(Flow flow)
		throws ResourceException
	{
		ElementAuthLevel flowAuthLevel =
			new ElementAuthLevel(flow.getAuthLevel(), true);
		stack.push(new Integer(flowAuthLevel.getAuthLevel()));
		Logger.debug(LOGGER_CONTEXT, "setFirstFlowAuthLevel: Push element [flow - " + flow.getName() + "] auth level [" + flowAuthLevel.getAuthLevel() + "]");
		return flowAuthLevel;
	}

	/**
	 * Determines the auth level of a specific flow / state.
	 * @param authId The auth id of the flow/state/
	 * @param parentAuthLevel The parent auth level: 
	 * 	for state is the auth level of the flow. 
	 *  for flow it is the auth level of the parent flow.
	 * @param dynamicGlobals
	 * @return
	 * @throws ResourceException
	 */
	public int determineFlowAuthLevel(
		String authId,
		int parentAuthLevel,
		DynamicGlobals dynamicGlobals)
		throws ResourceException
	{
		int authLevel;
		if (parentAuthLevel > AUTH_LEVEL_NOT_INITIALIZED)
		{
			// copy parent flow auth level to the current flow
			authLevel = parentAuthLevel;
		}
		else
		{
			authLevel = AUTH_LEVEL_ALL;
			String firstAuthLevel =
				SystemResources.getInstance().getString(SystemConstants.DEFAUTL_FIRST_FLOW_AUTH_LEVEL);
			if(firstAuthLevel != null)
			{
				authLevel = Integer.parseInt(firstAuthLevel);
			}
		}

		if (authId != null)
		{
			int currAuthLevel = authManager.getAuthLevel(authId);
			
			// check if the auth level of the element is more severe then 
			// the last auth level of the stack.
			if (currAuthLevel > authLevel)
			{
				authLevel = currAuthLevel;
			}
		}
		return authLevel;
	}

	/**
	 * Determines the auth level according to the last auth id in the stack and 
	 * the auth level determined in the authorization management system to 
	 * the given authId .
	 * @param authId (optional) The authorization id of the component to get the 
	 * level from the authorization management system.
	 * @return int
	 */
	public int determineElementAuthLevel(String authId)
	{
		int authLevel = getLastAuthLevel();

		if (authLevel < AUTH_LEVEL_NONE)
		{
			// get auth level from authorization manager according to authId
			if (authId != null)
			{
				int currAuthLevel = authManager.getAuthLevel(authId);

				// check if the auth level of the element is more severe then 
				// the last auth level of the stack.
				if (currAuthLevel > authLevel)
				{
					authLevel = currAuthLevel;
				}
			}
		}
		
		return authLevel;
	}

	/**
	 * Removes the last auth level from the stack, only if the element auth level 
	 * was inserted before to the stack.
	 * @param elementAuthLevel Holds the authorization level of the element.
	 * @param sourceName The source name of the element.
	 */
	public void removeAuthLevel(ElementAuthLevel elementAuthLevel,String sourceName)
	{
		if (elementAuthLevel.isAuthLevelInsertedToStack())
		{
			Logger.debug(LOGGER_CONTEXT, "Pop element [" + sourceName + "] auth level [" + elementAuthLevel.getAuthLevel() + "]");
			stack.pop();
		}
	}

	/**
	 * Returns last auth id in the stack. if the stack is empty, returns all.
	 * @return int Auth level.
	 */
	private int getLastAuthLevel ()
	{
		int authLevel;
		if (!stack.isEmpty())
		{
			// get last level from stack
			authLevel = ((Number) stack.peek()).intValue();
		}
		else
		{
			authLevel = AUTH_LEVEL_ALL;
		}
		return authLevel;
	}
	
	/**
	 * Clears the auth levels in the stack.
	 */
	public void clear ()
	{
		stack.clear();
	}
	
	/**
	 * Returns the MenuAuthManager, Contains the access to the menu tree with 
	 * the entries allowed to the current user.
	 * @return MenuAuthManager
	 */
	public MenuAuthManager getMenuAuthManager()
	{
		return menuAuthManager;
	}
	
	private void createMenuTransitions () throws ExternalizerNotInitializedException, ExternalizationException
	{
		menuTransitionsListMap = new TransitionsListMap();
		
		Iterator iterator = menuAuthManager.getMenuIds();
		while (iterator.hasNext())
		{
			String menuId = (String)iterator.next();
			MenuItem rootMenuItem = menuAuthManager.getMenuItem(menuId);
			
			ArrayList leaves = rootMenuItem.findLeaves(Node.MAX_RECURSION, false);
			for(int leafIndex = 0 ; leafIndex < leaves.size() ; leafIndex++)
			{
				MenuItem currentMenuItem = (MenuItem)leaves.get(leafIndex);
				Transition transition = MenuTransitionFactory.createMenuTransition(currentMenuItem);
				menuTransitionsListMap.addTransition(transition);
			}			
		}
	}

	/**
	 * Returns the Transition of a specific menu item according to the event name.
	 * @return Transition
	 */
	public Transition getMenuTransition(String eventName)
	{
		Transition transition = null;
		TransitionList transitionList = menuTransitionsListMap.getTransitionList(eventName);
		if(transitionList != null && transitionList.getTransitionsCount() > 0)
		{
			transition = transitionList.getTransition(0);
		}
		return transition;
	}

}
