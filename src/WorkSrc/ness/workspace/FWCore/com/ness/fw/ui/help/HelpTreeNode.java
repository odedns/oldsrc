/*
 * Created on: 10/03/2005
 * Author:  user name
 * @version $Id: HelpTreeNode.java,v 1.1 2005/03/16 18:21:14 shay Exp $
 */
package com.ness.fw.ui.help;

import java.util.Iterator;

import com.ness.fw.util.tree.Node;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelpTreeNode extends Node 
{
	protected int type;
	
	/**
	 * @param keyGen
	 * @param description
	 */
	public HelpTreeNode(Object key,String description) 
	{
		super(key,description);
	}
	
	public int getType()
	{
		return type;
	}
	
	public String getId()
	{
		return getKey().toString();
	}
	
	public Iterator getChildDirectories()
	{
		setOrderType(SORT_ASCENDING);
		return getChildren() != null ? sortChildren(SORT_BY_ORDER).iterator() : null;	
	}		
}
