package com.ness.fw.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;

public class DefaultTreeModel extends AbstractTreeModel
{
	private static final String TREE_MODEL_OPENED_NODES_PROPERTY = "open";
	private static final String TREE_MODEL_CLOSED_NODES_PROPERTY = "close";	
	private static final String TREE_MODEL_SELECTED_NODES_PROPERTY = "selectedNodes";
	protected static final String TREE_MODEL_SELECTED_NODE_PROPERTY = "selectedNode";

	protected static final String TREE_MODEL_NODE_EVENT_TYPE = "node";
	protected static final String TREE_MODEL_NODES_EVENT_TYPE = "nodes";
	protected static final String TREE_MODEL_OPEN_EVENT_TYPE = "ot";

	public final static int DEFAULT_TREE_LEVEL = 0;
	public final static int MULTIPLE_ROOT_TREE_LEVEL = 1;
	protected int rootLevel = DEFAULT_TREE_LEVEL;
	
	/**
	 * constructor for DefaultTreeModel.
	 * @param root root of this tree model
	 */
	public DefaultTreeModel(DefaultTreeNode root)
	{
		super(root);
	}

	/**
	 * Marks nodes as open and adds the ids of the nodes to the list of
	 * the open nodes.
	 * @param openNodes ArrayList with id's of open nodes
	 */
	public void setOpenNodes(ArrayList openNodes)
	{
		HashMap openNodesMap = new HashMap();
		if (openNodes != null)
		{
			for (int index = 0; index < openNodes.size(); index++)
			{
				openNodesMap.put(openNodes.get(index),"");	
			}
			setOpenNodes(openNodesMap);
			modelProperties.put(TREE_MODEL_OPENED_NODES_PROPERTY,openNodes);
		}	
	}
	
	/**
	 * Marks node as open by its id and adds its id to the list of
	 * the open nodes.
	 * @param nodeId the id of the node to open
	 */
	public void setOpenNode(String nodeId)
	{
		setOpenNode(nodeId,false);
	}
	
	/**
	 * Marks node as open by its id and adds its id to the list of
	 * the open nodes.
	 * @param nodeId the id of the node to open
	 * @param openParentNodes indicates if parents of the node should be marked as open
	 */
	public void setOpenNode(String nodeId,boolean openParentNodes)
	{
		ArrayList openedNodes = getOpenNodesIds();
		if (openedNodes == null)
		{
			openedNodes = new ArrayList();
		}
		((DefaultTreeNode)getTreeNode(nodeId)).setOpen(true);
		openedNodes.add(nodeId);
		if (openParentNodes)
		{
			openedNodes.addAll(getParentNodeIds(nodeId));
		}
		modelProperties.put(TREE_MODEL_OPENED_NODES_PROPERTY,openedNodes);
	}
	
	
	private ArrayList getParentNodeIds(String nodeId)
	{
		ArrayList parentNodeIds = new ArrayList();
		DefaultTreeNode treeNode = (DefaultTreeNode)getTreeNode(nodeId);
		while (treeNode.getParent() != null)
		{
			treeNode = (DefaultTreeNode)treeNode.getParent();
			treeNode.setOpen(true);
			parentNodeIds.add(treeNode.getId());			
		}
		return parentNodeIds;
	}
	
	/**
	 * Adds node to the list of the open nodes
	 * @param nodeId the id of the node to add
	 */
	protected void addOpenNode(String nodeId)
	{
		ArrayList openedNodes = getOpenNodesIds();
		if (openedNodes == null)
		{
			openedNodes = new ArrayList();
		}
		openedNodes.add(nodeId);
		modelProperties.put(TREE_MODEL_OPENED_NODES_PROPERTY,openedNodes);
	}

	/**
	 * Marks node as selected and sets it as the selected node of the tree
	 * @param nodeId the node id
	 */
	public void setSelectedNode(String nodeId)
	{
		HashMap selectedNodesMap = new HashMap();
		if (nodeId != null)
		{
			selectedNodesMap.put(nodeId,"");
		}
		setSelectedNodes(selectedNodesMap);
		modelProperties.put(TREE_MODEL_SELECTED_NODE_PROPERTY,nodeId);
	}
	
	/**
	 * Assigning new ids to children of a node which was just added to tree,if this
	 * node has child.
	 * @param newNode The new node added to the tree
	 */
	protected void renumberNodes(AbstractTreeNode newNode)
	{
		DefaultTreeNode defaultTreeNode = (DefaultTreeNode)newNode;
		newNode.setId(String.valueOf(idCounter++));
		if (defaultTreeNode.isOpen())
		{
			addOpenNode(newNode.getId());
		}
		if (defaultTreeNode.isSelected())
		{
			setSelectedNode(newNode.getId());
		}
		for (int index = 0;index < newNode.getChildrenNumber();index++)
		{
			AbstractTreeNode child = newNode.getChild(index);
			child.setId(String.valueOf(idCounter++));
			renumberNodes(child);
		}	
	}	
		
	/**
	 * Returns list of open nodes ids
	 * @return list of open nodes ids or null if no open nodes exist.
	 */
	public ArrayList getOpenNodesIds()
	{
		return getListProperty(TREE_MODEL_OPENED_NODES_PROPERTY);
	}
	
	/**
	 * Returns the id of the selected {@link TreeNode}
	 * @return the id of the selected node or null if no node was selected
	 */	
	public String getSelectedNodeId()
	{
		return getStringProperty(TREE_MODEL_SELECTED_NODE_PROPERTY);
	}		
	
	/**
	 * Returns the selected {@link TreeNode} object
	 * @return the selected {@link TreeNode} or null if no node was selected
	 */	
	public TreeNode getSelectedNode()
	{
		String nodeId = getStringProperty(TREE_MODEL_SELECTED_NODE_PROPERTY); 
		return (nodeId != null ? (TreeNode)getTreeNode(nodeId) : null);
	}		
		
	/**
	 * Sets open state for the tree's nodes,node which does not appear in the
	 * open nodes array is closed.
	 * @param openNodes array with id's of open nodes
	 */
	protected void setOpenNodes(String[] openNodes)
	{
		HashMap openNodesMap = new HashMap();
		if (openNodes != null)
		{
			for (int index = 0; index < openNodes.length; index++)
			{
				openNodesMap.put(openNodes[index],"");	
			}
			setOpenNodes(openNodesMap);
		}			
	}
	
	/**
	 * Sets open state for the tree's nodes,node which does not appear in the
	 * open nodes hashmap is closed.
	 * @param openNodes hashmap with id's of open nodes
	 */
	protected void setOpenNodes(HashMap openNodes)
	{
		Iterator iter = nodes.keySet().iterator();
		while (iter.hasNext())
		{
			DefaultTreeNode treeNode = (DefaultTreeNode)nodes.get(iter.next());
			if (treeNode != null)
			{				
				treeNode.setOpen(openNodes.get(treeNode.getId()) != null);
			}
		}
	}

	/**
	 * Sets open state for the tree's nodes node which does not appear in the 
	 * open nodes string is closed
	 * @param openNodes a string which represents a list of nodes ids,seperated by "|"
	 */
	protected void setOpenNodes(String openNodes)
	{
		HashMap openNodesMap = new HashMap();
		StringTokenizer st = new StringTokenizer(openNodes,"|");
		while (st.hasMoreTokens()) {
			openNodesMap.put(st.nextToken(),"");			
		}
		setOpenNodes(openNodesMap);
	}
	
	/**
	 * Marks nodes as selected
	 * @param selectedNodesIds HashMap of the selected nodes id's
	 */
	protected void setSelectedNodes(HashMap selectedNodesIds)
	{
		Iterator iter = nodes.keySet().iterator();
		while (iter.hasNext())
		{
			DefaultTreeNode treeNode = (DefaultTreeNode)nodes.get(iter.next());
			if (treeNode != null)
			{
				treeNode.setSelected(selectedNodesIds.get(treeNode.getId()) != null);				
			}
		}
	}
	
	/**
	 * Marks one node as selected or not selected
	 * @param nodeId the node to mark
	 * @param isSelected if true mark not as selected,if false mark node as not selected
	 */
	protected void setSelectedNode(String nodeId,boolean isSelected)
	{
		setSelectedNode(nodeId,isSelected,true);
	}

	/**
	 * Marks one node as selected or not selected
	 * @param nodeId the node to mark
	 * @param isSelected if true mark not as selected,if false mark node as not selected
	 * @param isDeselectAll if true deselects all the other nodes, except the marked one
	 */
	protected void setSelectedNode(String nodeId,boolean isSelected,boolean isDeselectAll)
	{
		DefaultTreeNode treeNode = (DefaultTreeNode)nodes.get(nodeId);
		if (treeNode != null)
		{
			treeNode.setSelected(isSelected);
		}
		if (isSelected && isDeselectAll)
		{
			HashMap selectedNodesIds = new HashMap();
			selectedNodesIds.put(nodeId,"");
			setSelectedNodes(selectedNodesIds);		
		}
	}
	
	/**
	 * Removes all the nodes of the model,except the root.
	 * @throws UIException
	 */
	public void removeAllNodes() throws UIException
	{
		super.removeAll();	
		removeProperty(TREE_MODEL_OPENED_NODES_PROPERTY);
		removeProperty(TREE_MODEL_SELECTED_NODE_PROPERTY);
		removeProperty(TREE_MODEL_SELECTED_NODES_PROPERTY);
		removeProperty(TREE_MODEL_CLOSED_NODES_PROPERTY);
	}
	
		
	/**
	 * @see com.ness.fw.ui.AbstractTreeModel#getTreeNode(String)
	 */
	public AbstractTreeNode getTreeNode(String nodeId)
	{
		return (DefaultTreeNode)nodes.get(nodeId);
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeModel#getRoot()
	 */
	public AbstractTreeNode getRoot()
	{
		return (DefaultTreeNode)root;	
	}
	/**
	 * Returns the level of the root in the tree table model,which is 0 if
	 * showRoot = true and 1 if showRoot = false.
	 * @return the level of the root in the tree
	 */
	public int getRootLevel()
	{
		return rootLevel;
	}

	/**
	 * Sets the level of the root in the tree table model,which is 0 if
	 * showRoot = true and 1 if showRoot = false.
	 * @param rootLevel the level of the root or roots in the model
	 */
	public void setRootLevel(int rootLevel)
	{
		this.rootLevel = rootLevel;
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.AbstractModel#handleEvent()
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException 
	{
		
	}

}
