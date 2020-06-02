package com.ness.fw.ui;

import java.util.*;

public abstract class AbstractTreeNode
{	
	/**
	 * the id of this node
	 */
	protected String id;
	
	/**
	 * list of this node's children
	 */
	protected ArrayList nodes = new ArrayList();
	
	/**
	 * the parent node of this node
	 */
	protected AbstractTreeNode parent;

	/**
	 * constant for default node name
	 */
	protected static String DEFAULT_ROOT_ID = "root";

	/**
	 * returns number of children of this node 
	 * @return number of children of this node
	 */
	public int getChildrenNumber()
	{
		return nodes.size();
	}	

	/**
	 * checks if this node has children
	 * @return true if this node has children
	 */
	public boolean hasChildren()
	{
		return (nodes.size() != 0);
	}
	
	/**
	 * returns the children of this node
	 * @return the children of this node
	 */
	public ArrayList getChildren()
	{
		return nodes;
	}

	/**
	 * returns the id of this node
	 * @return the id of the node
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Sets new id for this node
	 * @param id the new id of the node
	 */
	protected void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Adds child to this node
	 * @param son the child to add
	 */
	protected void addChild(AbstractTreeNode child)
	{
		nodes.add(child);
	}
	
	/**
	 * removes a child of this node
	 * @param son the child to remove from this node
	 */
	public void removeChild(AbstractTreeNode child)
	{
		nodes.remove(child);
	}
	
	/**
	 * returns a specific child of this node by its index
	 * @the child's index
	 * @return the node's child
	 */
	public abstract AbstractTreeNode getChild(int index);

	/**
	 * returns the parent node of this node
	 * @return the parent node of this node
	 */
	public abstract AbstractTreeNode getParent();

	/**
	 * sets this node's parent node
	 * @param parent the node's new parent node
	 */
	public abstract void setParent(AbstractTreeNode parent);

}
