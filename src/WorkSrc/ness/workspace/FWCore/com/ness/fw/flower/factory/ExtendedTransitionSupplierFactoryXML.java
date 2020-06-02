/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExtendedTransitionSupplierFactoryXML.java,v 1.2 2005/02/21 16:45:58 shay Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.flower.common.MenuItem;
import com.ness.fw.flower.core.*;
import com.ness.fw.util.tree.KeyGenerator;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;

import java.util.*;

import org.w3c.dom.*;

public class ExtendedTransitionSupplierFactoryXML extends ExtendedTransitionSupplierFactory
{
	public static final String MENU_GROUP_NODE                      =   "menuGroup";
	public static final String MENU_ITEM_NODE                       =   "menuItem";

	public static final String ATTR_NAME                            =   "name";

	public static final String ATTR_ID                              =   "id";
	public static final String ATTR_SHORT_DESC                      =   "shortDesc";
	public static final String ATTR_SHORT_CUT_KEY                   =   "shortCutKey";
	public static final String ATTR_WINDOW_EXTRA_PARAMS             =   "windowExtraParams";
	public static final String ATTR_FLOW_NAME                       =   "flowName";
	public static final String ATTR_IN_FORMATTER_NAME               =   "inFormatterName";
	public static final String ATTR_OUT_FORMATTER_NAME              =   "outFormatterName";
	public static final String ATTR_OPENAS                          =   "openAs";

	public static final String OPENAS_NORMAL						=   "normal";
	public static final String OPENAS_NEW_WINDOW                    =   "newWindow";
	public static final String OPENAS_POPUP_WINDOW                  =   "popup";
	public static final String OPENAS_DIALOG_WINDOW                 =   "dialog";

	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " MENU_EXT";


	HashMap menuGroups = new HashMap();
	MenuGroupImpl globalMenuGroup = new MenuGroupImpl("");


	public ExtendedTransitionSupplierFactoryXML(ArrayList rootsPath) throws ExtendedTransitionSupplierFactoryException
	{
		
		//creating DOM repository
		DOMRepository domRepository = new DOMRepository();
		
		try
		{
			domRepository.initialize(rootsPath);
			init(domRepository);
		}
		catch (ExternalizationException e)
		{
			throw new ExtendedTransitionSupplierFactoryException("error in dom repository", e);
		} 
		catch (XMLUtilException e)
		{
			throw new ExtendedTransitionSupplierFactoryException("error in dom repository", e);
		} 
	}

	public ExtendedTransitionSupplier getTransitionSupplier(String supplierName)
	{
		return (ExtendedTransitionSupplier) menuGroups.get(supplierName);
	}

	public ExtendedTransitionSupplier getGlobalTransitionSupplier()
	{
		return globalMenuGroup;
	}

	/**
	 * Returns the ids of all the supplier groups.
	 * @return Iterator
	 */
	public Iterator getSupplierGroups ()
	{
		return menuGroups.keySet().iterator();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////

	private void init(DOMRepository domRepository) throws XMLUtilException, ExtendedTransitionSupplierFactoryException 
	{
		Logger.debug("ExtendedTransitionSupplierFactoryXML", "Start initialization");
		synchronized (menuGroups)
		{
			Logger.debug(LOGGER_CONTEXT, "loading groups");			
			menuGroups.clear();			
			loadGroups(domRepository);
		}
	}

	private void loadGroups(DOMRepository domRepository) throws XMLUtilException, ExtendedTransitionSupplierFactoryException
	{
		synchronized (menuGroups)
		{
			//read groups
			DOMList domList = domRepository.getDOMList(MENU_GROUP_NODE);
			if (domList != null)
			{
				for (int i = 0; i < domList.getDocumentCount(); i++)
				{
					Document doc = domList.getDocument(i);
	
					processGroupsDOM(doc);
				}
			}
		}
	}

	private void processGroupsDOM (Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, MENU_GROUP_NODE);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element menuGroupElement = (Element) nodes.item(i);

			try
			{
				processMenuGroup(menuGroupElement);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize group Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	private void processMenuGroup(Element menuGroupElement) throws ExtendedTransitionSupplierFactoryException
	{
		String groupName = XMLUtil.getAttribute(menuGroupElement, ATTR_NAME);

		if (menuGroups.containsKey(groupName))
		{
			throw new ExtendedTransitionSupplierFactoryException("Unable to initialize group [" + groupName + "]. A group name with that name is already defined.");
		}
		Logger.debug("ExtendedTransitionSupplierFactoryXML", "Loading menu group [" + groupName + "]");
	
		MenuGroupImpl menuGroup = new MenuGroupImpl(groupName);

		KeyGenerator keyGenerator = new KeyGenerator();

		NodeList menuItemsNodeList = XMLUtil.getElementsByTagName(menuGroupElement, MENU_ITEM_NODE);
		for (int i = 0; i < menuItemsNodeList.getLength(); i++)
		{
			Element menuItemElement = (Element) menuItemsNodeList.item(i);

			String openAsStr = XMLUtil.getAttribute(menuItemElement, ATTR_OPENAS);
			String openAs;
			if (openAsStr.equals(OPENAS_NORMAL))
			{
				openAs = MenuItem.OPEN_AS_NORMAL;
			}
			else if (openAsStr.equals(OPENAS_NEW_WINDOW))
			{
				openAs = MenuItem.OPEN_AS_NEW_WINDOW;
			}
			else if (openAsStr.equals(OPENAS_POPUP_WINDOW))
			{
				openAs = MenuItem.OPEN_AS_POPUP;
			}
			else if (openAsStr.equals(OPENAS_DIALOG_WINDOW))
			{
				openAs = MenuItem.OPEN_AS_DIALOG;
			}
			else
			{
				throw new ExtendedTransitionSupplierFactoryException("Illegal open as mode [" + openAsStr + "]");
			}


			String shortDesc;
			String shortCutKey;
			String windowExtraParams;
			
			shortDesc = XMLUtil.getAttribute(menuItemElement, ATTR_SHORT_DESC);
			shortCutKey = XMLUtil.getAttribute(menuItemElement, ATTR_SHORT_CUT_KEY);
			windowExtraParams = XMLUtil.getAttribute(menuItemElement, ATTR_WINDOW_EXTRA_PARAMS);
			MenuItem menuItem = new MenuItem(keyGenerator, 
			        XMLUtil.getAttribute(menuItemElement, ATTR_ID),
					shortDesc,
			        XMLUtil.getAttribute(menuItemElement, ATTR_FLOW_NAME),
					openAs,
					shortCutKey,
					windowExtraParams,
			        XMLUtil.getAttribute(menuItemElement, ATTR_IN_FORMATTER_NAME),
			        XMLUtil.getAttribute(menuItemElement, ATTR_OUT_FORMATTER_NAME)
			);

			try
			{
				menuGroup.addMenuItem(menuItem);
				globalMenuGroup.addMenuItem(menuItem);
			} 
			catch (Throwable ex)
			{
				throw new ExtendedTransitionSupplierFactoryException("Unable to create menu item", ex);
			}
		}
		
		menuGroups.put(menuGroup.getName(), menuGroup);
	}
}
