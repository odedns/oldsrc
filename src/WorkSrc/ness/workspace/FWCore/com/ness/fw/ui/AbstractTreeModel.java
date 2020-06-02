package com.ness.fw.ui;

import java.util.HashMap;
import java.util.StringTokenizer;

public abstract class AbstractTreeModel extends AbstractModel
{
	protected String id;
	protected AbstractTreeNode root;
	protected HashMap nodes = new HashMap();
	protected int idCounter = 0;
	
	/**
	 * constructor for TreeModel.
	 * @param root the root node of the tree model
	 */
	public AbstractTreeModel(AbstractTreeNode root)
	{
		root.setId(String.valueOf(idCounter++));
		setTreeNode(root);
	}
			
	/**
	 * adds node to root
	 * @param newNode the new node to add
	 * @return String the id of the new node
	 */
	public String add(AbstractTreeNode newNode)
	{
		return add(newNode,"");
	}
	
	/**
	 * adds node to the tree by its parent
	 * @param newNode the new node to add
	 * @param parent id of the new node.if empty String or null,new node is added to root
	 * @return the id of the new node
	 */
	public String add(AbstractTreeNode newNode,String parentNodeId)
	{
		if (parentNodeId == null || parentNodeId.equals("")) 
		{
			parentNodeId = root.getId();
		}
		AbstractTreeNode parentNode = (AbstractTreeNode)nodes.get(parentNodeId);
		if (parentNode == null) 
		{
			System.out.println("parent is null");	
		}
		return add(newNode,parentNode);
	}
		
	/**
	 * adds node to tree by its parent
	 * @param newNode the new node to add
	 * @param parentNode the parent of the new node 
	 * @return the id of the new node
	 */
	public String add(AbstractTreeNode newNode,AbstractTreeNode parentNode)
	{
		renumberNodes(newNode);
		newNode.setParent(parentNode);
		parentNode.addChild(newNode);
		nodes.put(newNode.getId(),newNode);
		return newNode.getId();
	} 
	
	protected abstract void renumberNodes(AbstractTreeNode newNode);
	
	/**
	 * adds node to tree by its location ib the tree
	 * @param newNode the new node to add
	 * @param path the location of the node in the tree
	 * @return the id of the new node
	 */
	public String addByPath(AbstractTreeNode newNode,String path)
	{
		AbstractTreeNode currentNode = root;
		StringTokenizer st = new StringTokenizer(path, "-");
		while (st.hasMoreTokens())
		{
			int nodeIndex = Integer.parseInt(st.nextToken());
			currentNode = currentNode.getChild(nodeIndex - 1);
			if (currentNode == null)
			{
				break;
			}
		}
		return add(newNode,currentNode.getId());
	}
		
	/**
	 * removes node and all its children from the tree by the node's id
	 * @param treeNodeId the id of the node to remove
	 */
	public void remove(String treeNodeId)
	{
		AbstractTreeNode treeNode = (AbstractTreeNode) nodes.get(treeNodeId);
		if (treeNode != null)
		{
			AbstractTreeNode parent = treeNode.getParent();
			if (parent != null)
			{
				parent.removeChild(treeNode);
				nodes.remove(treeNode.getId());
				removeAllChildren(treeNode);
			}
			else //root - do not remove 
			{
				return;
			}		
		}
	}
	
	/**
	 * Removes all the children of a node
	 * @param treeNode
	 */
	public void removeChildren(String treeNodeId)
	{
		AbstractTreeNode treeNode = (AbstractTreeNode)nodes.get(treeNodeId);
		if (treeNode != null)
		{
			removeAllChildren(treeNode);	
		}
	}
	
	/**
	 * Removes all the nodes of the tree,except of the root
	 */
	protected void removeAll()
	{
		removeAllChildren(root);
	}
	
	private void removeAllChildren(AbstractTreeNode treeNode)
	{
		for (int index = 0; index < treeNode.getChildrenNumber(); index++)
		{
			AbstractTreeNode son = treeNode.getChild(index);
			
			// baruch 06/12/04 should remove the child from the parent
			AbstractTreeNode parent = son.getParent();
			if (parent != null)
			{
				parent.removeChild(son);
				// removing elements in the loop,so we must decrease the index
				index--;
			}
			//end baruch 
			
			nodes.remove(son.getId());
			if (son.hasChildren())
			{
				removeAllChildren(son);
			}
		}		
	}
	
	/**
	 * removes node from the tree by its location in the tree
	 * @param path the location of the node to remove
	 */
	public void removeByPath(String path)
	{
		AbstractTreeNode currentNode = root;
		StringTokenizer st = new StringTokenizer(path, "-");
		while (st.hasMoreTokens())
		{
			int nodeIndex = Integer.parseInt(st.nextToken());
			currentNode = currentNode.getChild(nodeIndex - 1);
			if (currentNode == null)
			{
				return;
			}
		}
		remove(currentNode.getId());
	}
	
	/**
	 * returns a specific node from the tree
	 * @param nodeId the id of the node to return
	 * @return the node found
	 */
	public abstract AbstractTreeNode getTreeNode(String nodeId);	
	
	/**
	 * sets root for the tree
	 * @param root the new root of the tree
	 */
	public void setTreeNode(AbstractTreeNode root)
	{
		this.root = root;
		nodes.put(root.getId(), root);
	}
		
	/**
	 * returns the hashmap of nodes
	 * @return the hashmap of nodes
	 */
	public HashMap getNodes()
	{
		return nodes;
	}
		
	/**
	 * returns the root of the tree
	 * @return the root of the tree
	 */
	public abstract AbstractTreeNode getRoot();
	
	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}
}
