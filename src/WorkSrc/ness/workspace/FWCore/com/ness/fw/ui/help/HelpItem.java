/*
 * Created on: 08/03/2005
 * Author:  user name
 * @version $Id: HelpItem.java,v 1.1 2005/03/16 18:21:13 shay Exp $
 */
package com.ness.fw.ui.help;

import java.util.ArrayList;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelpItem extends HelpTreeNode
{
	private ArrayList linkedItems;
	
	protected HelpItem(Object key,String description)
	{
		super(key,description);
		type = HelpConstants.HELP_ITEM_TYPE_FILE;
	}
	
	protected void addLinkedItem(String linkedItemId)
	{
		if (linkedItems == null)
		{
			linkedItems = new ArrayList();
		}
		linkedItems.add(linkedItemId);
	}
	
	protected ArrayList getLinkedItems()
	{
		return linkedItems;
	}
	
	protected void setLinkedItems(ArrayList linkedItems)
	{
		this.linkedItems = linkedItems;
	}
	
	protected int getLinkedItemsNumber()
	{
		return linkedItems != null ? linkedItems.size() : 0;
	}
	
	protected String getLinkedItemd(int index)
	{
		return (String)linkedItems.get(index);
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof HelpItem)
		{
			return ((HelpItem)obj).getId().equals(getKey());
		}
		return false;
	}
}
