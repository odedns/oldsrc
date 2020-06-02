package com.ness.fw.ui;

import java.util.*;

import com.ness.fw.ui.events.CustomEvent;

public class TreeNode extends DefaultTreeNode
{
	private String nodeClassName;
	private String linkedNodeClassName;
	private HashMap extraData;
	protected int nodeOpenType = 0;
	private Object data;
	private String dataMaskKey;
	private String dataType;	
	private CustomEvent treeNodeClickEvent;
	
	/**
	 * Empty constructor for TreeNode
	 */
	public TreeNode()
	{
		this.id = DEFAULT_ROOT_ID;
	}
		
	/**
	 * Constructor for TreeNode.
	 * @param data the data of the node
	 */
	public TreeNode(Object data)
	{
		this.data = data;
	}

	/**
	 * Constructor for TreeNode.
	 * @param data the data of the node
	 * @param isOpen if true marks the root as open
	 */
	public TreeNode(Object data,boolean isOpen)
	{
		this.data = data;
		this.open = isOpen;
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#getChild(int)
	 */
	public AbstractTreeNode getChild(int index)
	{
		return (TreeNode)nodes.get(index);
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#getParent()
	 */
	public AbstractTreeNode getParent()
	{
		return (TreeNode) parent;
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeNode#setParent(AbstractTreeNode)
	 */
	public void setParent(AbstractTreeNode parent)
	{
		this.parent = (TreeNode) parent;
	}
			
	/**
	* Adds extra data to node.
	* @param key the extra data key
	* @param value the extra data value
	*/
	public void addExtraData(String key,Object value)
	{
		if (extraData == null) extraData = new HashMap();
		extraData.put(key,value);
	}
	
	/**
	 * Returns extra data of the node
	 * @param key the key of the extra data
	 * @return the extra data
	 */
	public Object getExtraData(String key)
	{
		if (extraData == null) extraData = new HashMap();
		return extraData.get(key);
	}
	
	/** 
	 * Returns an iterator of the extra data's keys
	 * @return Iterator object of the extra data's keys
	 */
	public Iterator getExtraDataKeysIterator()
	{
		if (extraData == null) extraData = new HashMap(); 
		return extraData.keySet().iterator(); 
	}
	
	/**
	 * Returns the css class name of the node
	 * @return the css class name of the node
	 */
	public String getNodeClassName()
	{
		return nodeClassName;
	}

	/**
	 * Sets the css class name of the node
	 * @param  the css class name of the node
	 */
	public void setNodeClassName(String nodeClassName)
	{
		this.nodeClassName = nodeClassName;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.CustomEvent} object which holds the information about 
	 * click event on this tree node.Relevant only if this node is linkable. 
	 * @return event the {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public CustomEvent getTreeNodeClickEvent() 
	{
		return treeNodeClickEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.CustomEvent} object which holds the information about 
	 * click event on this tree node.
	 * @param event the {@link com.ness.fw.ui.events.CustomEvent} object
	 */
	public void setTreeNodeClickEvent(CustomEvent event) 
	{
		treeNodeClickEvent = event;
	}

	/**
	 * Returns the css class name of the node which contain link
	 * @return the css class name of the node which contain link
	 */
	public String getLinkedNodeClassName() 
	{
		return linkedNodeClassName;
	}

	/**
	 * Sets the css class name of the node which contain link
	 * @param linkedNodeClassName the css class name of the node which contain link
	 */
	public void setLinkedNodeClassName(String linkedNodeClassName) 
	{
		this.linkedNodeClassName = linkedNodeClassName;
	}

	/**
	 * Returns the nodeOpenType property which determines<br>
	 * whether an event will be sent to the server when this node is<br>
	 * expanded.<br>
	 * The value may be one of 3 constants from {@link TreeModel}<br>
	 * TREE_OPEN_NODE_TYPE_DEFAULT<br>
	 * TREE_OPEN_NODE_TYPE_SUBMIT_FIRST_TIME<br>
	 * TREE_OPEN_NODE_TYPE_SUBMIT_ALWAYS<br>
	 * @return nodeOpenType property
	 */
	public int getNodeOpenType() 
	{
		return nodeOpenType;
	}

	/**
	 * Setter method for the nodeOpenType property which determines<br>
	 * whether an event will be sent to the server when this node is<br>
	 * expanded.<br>
	 * The value may be one of 3 constants from {@link TreeModel}<br>
	 * TREE_OPEN_NODE_TYPE_DEFAULT<br>
	 * TREE_OPEN_NODE_TYPE_SUBMIT_FIRST_TIME<br>
	 * TREE_OPEN_NODE_TYPE_SUBMIT_ALWAYS<br>
	 * @param nodeOpenType
	 */
	public void setNodeOpenType(int nodeOpenType) 
	{
		this.nodeOpenType = nodeOpenType;
	}

	/**
	 * Returns the data of the cell.The Object may of type String,Number,Date or Boolean.
	 * @return the data of the cell
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Returns the dataMaskKey of the node,which is a string representing a mask<br>
	 * in a properties file.This mask affect  the way that the data of this node will be formatted.
	 * @return the key of the data mask from a properties file.
	 */
	public String getDataMaskKey() {
		return dataMaskKey;
	}

	/**
	 * Returns the data type of the node ,which may be one of 4 constans of {@link TreeModel} <br>
	 * DATA_TYPE_STRING - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.String 
	 * DATA_TYPE_DATE - indicates that the object that was set by the method setData<br>
	 * is of type java.util.Date 
	 * DATA_TYPE_NUMBER - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Number 
	 * DATA_TYPE_BOOLEAN - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Boolean
	 * The dataType affects the way that the data of this node will be formatted. 
	 * @return the dataType of this node.
	 */
	public String getDataType() 
	{
		return dataType;
	}

	/**
	 * Sets the data of the node.The Object may of type String,Number,Date or Boolean.
	 * @param data the new data of the node
	 */
	public void setData(Object data) 
	{
		this.data = data;
	}

	/**
	 * Sets the dataMaskKey of the node,which is a string representing a mask<br>
	 * in a properties file.This mask affect  the way that the data of this node will be formatted.
	 * @param dataMaskKey the key of the data mask from a properties file.
	 */
	public void setDataMaskKey(String dataMaskKey) 
	{
		this.dataMaskKey = dataMaskKey;
	}

	/**
	 * Sets the data type of the node ,which may be one of 4 constans of {@link TreeModel} <br>
	 * DATA_TYPE_STRING - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.String 
	 * DATA_TYPE_DATE - indicates that the object that was set by the method setData<br>
	 * is of type java.util.Date 
	 * DATA_TYPE_NUMBER - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Number 
	 * DATA_TYPE_BOOLEAN - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Boolean
	 * The dataType affects the way that the data of this node will be formatted. 
	 * @param dataType the data type of this node
	 */
	public void setDataType(String dataType) 
	{
		this.dataType = dataType;
	}

}