/*
 * Created on: 08/03/2005
 * Author:  user name
 * @version $Id: HelpDirectory.java,v 1.2 2005/04/04 15:52:50 shay Exp $
 */
package com.ness.fw.ui.help;

import java.util.ArrayList;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelpDirectory extends HelpTreeNode 
{
	private ArrayList helpItems;
	
	/**
	 * Creates new HelpDirectory object
	 * @param keyGen The KeyGenerator that will be used to generate the key of the menu item.
	 * @param description The description of the node. will be sent as event name to the 
	 */
	public HelpDirectory(Object key, String description)
	{
		super(key,description);
		type = HelpConstants.HELP_ITEM_TYPE_DIRECTORY;
	}
	
	public int getChildNumber()
	{
		return getChildren().size();
	}	
}
