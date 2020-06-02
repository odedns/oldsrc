/*
 * Created on 16/12/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.taglib.flower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.taglib.UITag;

import com.ness.fw.flower.common.MenuItem;
import com.ness.fw.flower.common.MenuItemList;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MenuTag extends UITag 
{
	private MenuItemList menuItemList;
	private int maxLevel = 0;
	
	private static final String JS_TREE = "new Tree";
	private static final String JS_MENU_ITEM = "new MenuItem";
	private static final String JS_ADD_NODE = "addNode";
	private static final String JS_ADD_NODE_BY_PARENT = "addNodeByParent";
	private static final String JS_MENU_EVENT = "menuEvent";
	private static final String JS_MENU_EVENT_NAME = "menuItemEvent";
	private static final String JS_MAIN_ITEM_OVER = "menuMainItemOver";
	private static final String JS_MAIN_ITEM_OUT = "menuMainItemOut";
	private static final String JS_EXIT_ITEM = "menuItemExitSystem";
	private static final String JS_EXIT_ITEM_OUT = "menuItemExitSystemOut";
	private static final String JS_EXIT_ITEM_OVER = "menuItemExitSystemOver";
	private static final String JS_MENU_FIRST_OPEN = "menuFirstOpen";
	private static final String JS_VARIABLE_MENU_TREE = "menuTree";
	
	private static final String JS_CONST_MENU_TREE_ROOT_NAME = "menuRoot";
	private static final String JS_CONST_MENU_ITEM_HEADER_NAME = "mh";
	
	private static final String HTML_CONST_MENU_TABLE_ID = "menuTable";
	private static final String HTML_CONST_MENU_SPACE_ID = "menuSpace";
	private static final String HTML_CONST_MENU_DIV_LEVEL_ID = "menuDivLevel";
	private static final String HTML_CONST_MENU_IFRAME_ID = "menuIframe";
	
	private String menuHeaderClassName = null;
	private String menuDivLevelClassName = null;
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
		initCss();
		menuItemList = FlowerUIUtil.getMenuItemList(getHttpRequest());
		if (menuItemList == null)
		{
			throw new UIException("menu item list in null");
		}
		
		//renders the script for building the menu tree
		renderMenuJScript();
		
		//renders the html for the menu table
		renderMenu();
		
		//renders the div elements that contains the levels of the menu,for each
		//level one hidden div element is rendered
		renderMenuLevels();
	}
	
	private void initCss()
	{
		menuHeaderClassName = getUIProperty("ui.menu.header") + getLocaleCssSuffix();
		menuDivLevelClassName = getUIProperty("ui.menu.div");
	}

	/**
	 * Renders the java script for building the menu tree.
	 */
	private void renderMenuJScript() throws UIException
	{
		startTag(SCRIPT,true);
		appendln();
		
		//Initialize the menus tree
		renderMenuTreeJScript();
		
		//Renders each one of the "headers" of the menu.
		//The javascript name of each menu item is 
		//JS_CONST_MENU_ITEM_HEADER_NAME + its index in the menuItemList.
		for (int index = 0;index < menuItemList.getCount();index++)
		{
			MenuItem menuItem = menuItemList.getMenuItem(index);
			if (menuItem != null)
			{
				maxLevel = Math.max(maxLevel,menuItem.countDescendants(MenuItem.MAX_RECURSION,false));	
				renderMenuHeaderJScript(menuItem,JS_CONST_MENU_ITEM_HEADER_NAME + (index + 1));
			}
		}
		endTag(SCRIPT);
		appendln();
	}
	
	/**
	 * Renders the java script for constructing an object of type Tree,which
	 * id used for holding the menus.
	 * The script that is rendered looks like this : 
	 * var menuTree = new Tree('menuRoot',new MenuItem('menuRoot',''));
	 */
	private void renderMenuTreeJScript()
	{
		addVariable(JS_VARIABLE_MENU_TREE,getFunctionCall(JS_TREE,getSingleQuot(JS_CONST_MENU_TREE_ROOT_NAME) + COMMA + getMenuItemJScript(JS_CONST_MENU_TREE_ROOT_NAME," "),false),false);
		appendln();
	}
	
	/**
	 * Renders the java script code for adding an "header" MenuItem object to the tree.
	 * This menu item will be a direct child of the root of the menu tree. 
	 * @param menuItem the (@link com.ness.fw.flower.common.MenuItem} object
	 * @param menuItemHeaderName the javascript unique name of the header menu item,used
	 * for identifying when added to the menu tree.
	 */
	private void renderMenuHeaderJScript(MenuItem menuItem,String menuItemHeaderName) throws UIException
	{		
		//Renders the java script code for adding an "header" MenuItem object to the tree.
		renderFunctionCall(JS_ADD_NODE,null + COMMA + getQuot(menuItemHeaderName) + COMMA + getMenuItemJScript(menuItemHeaderName," "),false,JS_VARIABLE_MENU_TREE);
		appendln(";");
		
		//Get the header menu item's children
		TreeMap menuItems = menuItem.getChildren();
		if (menuItems != null)
		{
			Iterator keys = menuItems.keySet().iterator();
			int childNumber = 1;
	
			//For each header,build the js tree from its children.
			while (keys.hasNext())
			{
				//Renders the javascript code which adds the "header" menu item's
				//children to the menu tree.
				renderMenuItemChildrenJScript((MenuItem)menuItems.get(keys.next()),menuItemHeaderName,childNumber++);
			}
		}
	}
	
	/**
	 * Renders the javascript code which adds a menu item and its children to the tree
	 * @param menuItem the (@link com.ness.fw.flower.common.MenuItem} object
	 * @param parentId the id in the tree of the parent of the menu item 
	 * @param childNumber the order of the menu item its parent children list,the
	 * id of this menu item in the tree will be its parent id + "-" + childNumber.
	 * The script that is rendered looks like this : 
	 * menuTree.addNodeByParent('mh1','mh1-1',new MenuItem('title','menuEvent("menuItemEvent,flowPath","flowState","flowName")'));
	 */
	private void renderMenuItemChildrenJScript(MenuItem menuItem,String parentId,int childNumber) throws UIException
	{
		//The new MenuItem's id : parentId + "-" + childNumber
		String newMenuItemId = parentId + "-" + childNumber;
		ArrayList params = new ArrayList();
		params.add(getQuot(parentId));
		params.add(getQuot(newMenuItemId));
		params.add(getMenuItemJScript(menuItem));
		renderFunctionCall(JS_ADD_NODE_BY_PARENT,params,false,JS_VARIABLE_MENU_TREE);
		appendln(";");
		
		//Render the new MenuItem's children
		TreeMap menuItems = menuItem.getChildren();
		if (menuItems != null)
		{
			Iterator keys = menuItems.keySet().iterator();
			int counter = 1;
			while (keys.hasNext())
			{
				renderMenuItemChildrenJScript((MenuItem)menuItems.get(keys.next()),newMenuItemId,counter++);
			}
		}
	}
	
	/**
	 * Returns the java script code for creating a new MenuItem object,which 
	 * contains the title of the MenuItem and the javascript function to call
	 * when the MenuItem is clicked.
	 * @param title the title of the MenuItem
	 * @param jsFunction the java script code to be executed when the MenuItem
	 * is clicked.
	 * @return java script code which creates new MenuItem.
	 * The script that is returned looks like this : 
	 * new MenuItem('title','jsFunction')
	 */
	private String getMenuItemJScript(String title,String jsFunction)
	{
		ArrayList params = new ArrayList();
		params.add(getQuot(title));
		params.add(getQuot(jsFunction));
		return getFunctionCall(JS_MENU_ITEM,params,false);
		//return getFunctionCall(JS_MENU_ITEM,title + COMMA + jsFunction,true);
	}
	
	/**
	 * Returns the java script code for creating a new MenuItem object,which 
	 * contains the title of the MenuItem and the javascript function to call
	 * when the MenuItem is clicked.
	 * @param menuItem an object of type (@link com.ness.fw.flower.common.MenuItem}
	 * @return java script code which creates new MenuItem.
	 * The script that is returned looks like this :
	 * new MenuItem('title','menuEvent("menuItemEvent,flowPath","flowState","flowName")')
	 */
	private String getMenuItemJScript(MenuItem menuItem) throws UIException
	{
		ArrayList params = new ArrayList();
		params.add(getQuot(menuItem.getText()));
		params.add(getQuot(getMenuItemJsFunctionCall(menuItem)));
		return getFunctionCall(JS_MENU_ITEM,params,false);
	}
	
	/**
	 * Returns the java script code for calling a function,when a MenuItem is clicked 
	 * @param menuItem an object of type (@link com.ness.fw.flower.common.MenuItem}
	 * @return java script function call.
	 * The script that is returned looks like this :
	 * menuEvent("menuItemEvent","flowPath","flowState","flowName")
	 */
	private String getMenuItemJsFunctionCall(MenuItem menuItem) throws UIException
	{			
		if (!menuItem.isLeaf())
		{
			return "";
		}
		String menuEventFunction = getFunctionCall(
									JS_MENU_EVENT,
									getSingleQuot(menuItem.getEventName()) + COMMA + 
									getSingleQuot(menuItem.getFlowPath()) + COMMA + 
									getSingleQuot(menuItem.getFlowState()) + COMMA +
									menuItem.isLeaf() + COMMA +
									getSingleQuot(menuItem.getOpenAs()) + COMMA + 
									getSingleQuot(menuItem.getWindowExtraParams() != null ? menuItem.getWindowExtraParams() : " "),false);
		if (menuItem.getShortCutKey() != null)
		{
			addShortCutKey(menuItem.getShortCutKey(),menuEventFunction);
		}
		return menuEventFunction;
	}

	/** 
	 * Renders the html code for the main table of the menu item's headers	
	 */
	private void renderMenu()
	{
		startTag(TABLE);
		addAttribute(ID,HTML_CONST_MENU_TABLE_ID);
		addAttribute(ONMOUSEOVER,CANCEL_BUBBLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(BORDER,"0");
		addAttribute(HEIGHT,"100%");
		endTag();
		startTag(ROW);
		addAttribute(STYLE,CURSOR_HAND);
		endTag();
		
		//Loop the header menu items
		for (int index = 0;index < menuItemList.getCount();index++)
		{
			MenuItem menuItem = menuItemList.getMenuItem(index);
			if (menuItem != null)
			{
				renderMenuHeader(menuItem,index + 1);
				//renderMenuHeaderSeperator();
			}
		}	
		
		//Render the "Exit" item
		renderExitItem();	
		
		endTag(ROW);
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(ID,HTML_CONST_MENU_SPACE_ID);
		endTag();
		endTag(CELL);
		endTag(ROW);
		endTag(TABLE);
	}

	/**
	 * Renders the html code for an header menu item,which is a cell in the
	 * main table of the menu.
	 * @param menuItem
	 * @param childNumber
	 */
	private void renderMenuHeader(MenuItem menuItem,int childNumber)
	{
		startTag(CELL);
		addAttribute(ID,JS_CONST_MENU_ITEM_HEADER_NAME + childNumber);
		addAttribute(ONMOUSEOVER,getFunctionCall(JS_MAIN_ITEM_OVER,"",false));
		addAttribute(ONMOUSEOUT,getFunctionCall(JS_MAIN_ITEM_OUT,"",false));
		addAttribute(ONCLICK,getFunctionCall(JS_MENU_FIRST_OPEN,"",false));
		addAttribute(CLASS,menuHeaderClassName);
		endTag();
		append(menuItem.getText());
		endTag(CELL);
	}
	
	/**
	 * Renders the html code for an header in the main table,which pressing 
	 * on it will perform an exit action.
	 */
	private void renderExitItem()
	{
		startTag(CELL);
		addAttribute(ONCLICK,getLogoutFunctionCall(SystemConstants.REQUEST_PARAM_NAME_LOGOUT,SystemConstants.SYSTEM_REQUEST_PARAM_VALUE));
		addAttribute(ONMOUSEOVER,getFunctionCall(JS_EXIT_ITEM_OVER,""));
		addAttribute(ONMOUSEOUT,getFunctionCall(JS_EXIT_ITEM_OUT,""));
		addAttribute(CLASS,menuHeaderClassName);
		endTag();
		append(getLocalizedText("system.exit"));
		endTag(CELL);		
	}
	
	/**
	 * Renders the html code for the div elements that contains the html code for
	 * each level.This code is built dynamically in the javascript.
	 */
	private void renderMenuLevels()
	{
		//System.out.println("maxLevel=" + maxLevel);
		for (int index = 1;index < 5;index++)
		{
			startTag(DIV);
			addAttribute(STYLE,getStyleAttribute(STYLE_DISPLAY,STYLE_NONE) + getStyleAttribute(STYLE_POSITION,STYLE_ABSOLUTE));
			addAttribute(ID,HTML_CONST_MENU_DIV_LEVEL_ID);
			addAttribute(CLASS,menuDivLevelClassName);
			addAttribute(ONMOUSEOVER,CANCEL_BUBBLE);
			endTag();
			endTagLn(DIV);
			startTag(IFRAME);
			addAttribute(STYLE,getStyleAttribute(STYLE_DISPLAY,STYLE_NONE) + getStyleAttribute(STYLE_POSITION,STYLE_ABSOLUTE));
			addAttribute(ID,HTML_CONST_MENU_IFRAME_ID);
			endTag();
			endTagLn(IFRAME);
		}
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException 
	{
	}

	/**
	 * @return
	 */
	public MenuItemList getMenuItemList() {
		return menuItemList;
	}

	/**
	 * @param list
	 */
	public void setMenuItemList(MenuItemList list) {
		menuItemList = list;
	}

}
