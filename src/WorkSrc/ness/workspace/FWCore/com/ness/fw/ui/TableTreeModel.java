package com.ness.fw.ui;

public class TableTreeModel extends DefaultTreeModel
{
	/**
	 * Constructor for TableTreeModel.
	 * @param root
	 */
	public TableTreeModel(TableTreeNode root)
	{
		super(root);
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeModel#getRoot()
	 */
	public AbstractTreeNode getRoot()
	{
		return (TableTreeNode)root;
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeModel#getTreeNode(String)
	 */
	public AbstractTreeNode getTreeNode(String nodeId)
	{
		return (TableTreeNode)nodes.get(nodeId);
	}
		
	/**
	 * Assigning new ids to children of a row which was just added to tree,if this
	 * row has child.
	 * @param newNode The new node added to the tree
	 */
	protected void renumberNodes(AbstractTreeNode newNode)
	{
		TableTreeNode parent = (TableTreeNode)newNode;
		parent.setId(String.valueOf(idCounter++));
		parent.getRow().setId(parent.getId());		
		for (int index = 0;index < newNode.getChildrenNumber();index++) {
			TableTreeNode child = (TableTreeNode)newNode.getChild(index);
			child.setId(String.valueOf(idCounter++));
			child.getRow().setId(child.getId());			
			renumberNodes(child);		
		}	
	}	

}
