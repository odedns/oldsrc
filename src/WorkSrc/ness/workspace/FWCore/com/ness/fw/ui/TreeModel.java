package com.ness.fw.ui;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.events.Event;

public class TreeModel extends DefaultTreeModel 
{
	/**
	 * Used in {@link com.ness.fw.ui.TreeNode} for the nodeOpenType<br>
	 * attribute.When nodeOpenType is set to this value,the expanding of<br>
	 * a node in the tree will not send an event to the server.<br>
	 */
	public final static int TREE_OPEN_NODE_TYPE_DEFAULT = UIConstants.TREE_OPEN_NODE_TYPE_DEFAULT;

	/**
	 * Used in {@link com.ness.fw.ui.TreeNode} for the nodeOpenType<br>
	 * attribute.When nodeOpenType is set to this value,the expanding of<br>
	 * a node in the tree will send an event to the server as long as the<br>
	 * the node does not have child nodes under it.When the node's childs<br>
	 * node are fetched in the server,the expanding of the node will not<br>
	 * send an event to the server.
	 */
	public final static int TREE_OPEN_NODE_TYPE_SUBMIT_FIRST_TIME = UIConstants.TREE_OPEN_NODE_TYPE_SUBMIT_FIRST_TIME;

	/**
	 * Used in {@link com.ness.fw.ui.TreeNode} for the nodeOpenType<br>
	 * attribute.When nodeOpenType is set to this value,the expanding of<br> 
	 * a node in the tree will always send an event to the server.
	 */
	public final static int TREE_OPEN_NODE_TYPE_SUBMIT_ALWAYS = UIConstants.TREE_OPEN_NODE_TYPE_SUBMIT_ALWAYS;
	
	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.lang.String
	 */
	public final static String DATA_TYPE_STRING = UIConstants.DATA_TYPE_STRING;
	
	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.lang.Number
	 */
	public final static String DATA_TYPE_NUMBER = UIConstants.DATA_TYPE_NUMBER;
	
	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.util.Date
	 */	
	public final static String DATA_TYPE_DATE = UIConstants.DATA_TYPE_DATE;
	
	/**
	 * Used in {@link com.ness.fw.ui.AbstractTableModel} ,in {@link com.ness.fw.ui.TreeModel},<br>
	 * in {@link com.ness.fw.ui.Cell} and in {@link com.ness.fw.ui.TreeNode} for the <br>
	 * setDataType method.When this value is used the data which is set by the setData method<br>
	 * must be of type java.lang.Boolean
	 */	
	public final static String DATA_TYPE_BOOLEAN = UIConstants.DATA_TYPE_BOOLEAN;


	private static final String TREE_MODEL_OPEN_NODE_PROPERTY = "exNode";
			
	private static final String TREE_MODEL_OPEN_NODE_EVENT_TYPE = "expand";
			
	private Event treeNodeDefaultClickEvent;
	private Event treeNodeDefaultExpandEvent;
	
	private boolean showRoot = true;
	
	private String dataMaskKey;
	private String dataType = DATA_TYPE_STRING;	
	
	/**
	 *  empty constructor for TreeModel. 
	 */
	public TreeModel()
	{
		super(new TreeNode());
		treeNodeDefaultClickEvent = new Event();
		treeNodeDefaultClickEvent.setEventType(Event.EVENT_TYPE_READONLY);
		treeNodeDefaultExpandEvent = new Event();
		treeNodeDefaultExpandEvent.setEventType(Event.EVENT_TYPE_READONLY);
	}
		
	/**
	 * constructor for TreeModel.
	 * @param root the root of the tree model
	 */
	public TreeModel(TreeNode root)
	{
		super(root);		
		treeNodeDefaultClickEvent = new Event();
		treeNodeDefaultClickEvent.setEventType(Event.EVENT_TYPE_READONLY);
		treeNodeDefaultExpandEvent = new Event();
		treeNodeDefaultExpandEvent.setEventType(Event.EVENT_TYPE_READONLY);		
		if (root.isOpen())
		{
			addOpenNode(root.getId());
		}
		if (root.isSelected())
		{
			setSelectedNode(root.getId());
		}		
	}	
		
	/**
	 * Handles tree model events
	 */
	protected void handleEvent(boolean checkAuthorization) throws UIException, AuthorizationException
	{
		String eventType = getEventTypeProperty();
		if (eventType.equals(TREE_MODEL_OPEN_NODE_EVENT_TYPE))
		{
			handleOpenNodeEvent();
		}
		handleNodeEvent(checkAuthorization);
		handleOpenTreeEvent();
	}
	
	protected void handleOpenNodeEvent()
	{
	}
	
	protected void handleOpenTreeEvent()
	{
		setOpenNodes(getOpenNodesIds());
	}
	
	protected void handleNodeEvent(boolean checkAuthorization) throws UIException, AuthorizationException
	{
		if (getSelectedNodeId() == null || checkEventLegal(getNodeClickEvent(getSelectedNodeId()),checkAuthorization))
		{
			setSelectedNode(getSelectedNodeId());
		}
		else
		{
			removeProperty(TREE_MODEL_SELECTED_NODE_PROPERTY);
			removeEventType(TREE_MODEL_NODE_EVENT_TYPE);
		}	
	}
		
	/**
	 * @see com.ness.fw.ui.AbstractTreeModel#getRoot()
	 */
	public AbstractTreeNode getRoot()
	{
		return (TreeNode)root;
	}
	
	/**
	 * @see com.ness.fw.ui.AbstractTreeModel#getTreeNode(String)
	 */
	public AbstractTreeNode getTreeNode(String nodeId)
	{	
		return (TreeNode)nodes.get(nodeId);
	}	
	
	/**
	 * Retrieves the formatted data of a (@link TreeNode}.This value depends<br>
	 * on the dataType of the (@link TreeNode}.If the node's dataType was not set<b>
	 * the dataType of the TreeModel is used.It also depends on the dataMaskKey<br>
	 * of the (@link TreeNode}.If the node's dataMaskKey is not set,the dataMaskKey<br>
	 * of the TreeModel is used.
	 * @param treeNode the (@link TreeNode} to get its formatted data.
	 * @param localizable {@link com.ness.fw.common.resources.LocalizedResources} object used for formatting strings
	 * @return the formatted value of (@link TreeNode}
	 * @throws UIException 
	 */
	public String getNodeFormattedData(TreeNode treeNode,LocalizedResources localizable) throws UIException
	{
		String type = treeNode.getDataType() == null ? dataType : treeNode.getDataType();
		String maskKey = treeNode.getDataMaskKey() == null ? dataMaskKey : treeNode.getDataMaskKey();
		return FlowerUIUtil.getFormattedValue(localizable,treeNode.getData(),type,maskKey);		
	}
	

	/**
	 * Retrieves the formatted data of a (@link TreeNode} by the id of the node.This value depends<br>
	 * on the dataType of the (@link TreeNode}.If the node's dataType was not set<b>
	 * the dataType of the TreeModel is used.It also depends on the dataMaskKey<br>
	 * of the (@link TreeNode}.If the node's dataMaskKey is not set,the dataMaskKey<br>
	 * of the TreeModel is used.
	 * @param nodeId the id of the (@link TreeNode} to get its formatted data.
	 * @param localizable {@link com.ness.fw.common.resources.LocalizedResources} object used for formatting strings
	 * @return the formatted value of (@link TreeNode}
	 * @throws UIException if the (@link TreeNode} does not exist in this model
	 */
	public String getNodeFormattedData(String nodeId,LocalizedResources localizable) throws UIException
	{
		TreeNode treeNode = (TreeNode)nodes.get(nodeId);
		if (treeNode == null)
		{
			throw new UIException("node with id " + nodeId + " does not exist");
		}
		return getNodeFormattedData(treeNode,localizable);
	}
		
	/**
	 * adds extra data to specific node in the table tree
	 * @param nodeId the id of the node to add the extra data to
	 * @param key the key of the extra data
	 * @param value the value of the extra data
	 */
	public void addNodeExtraData(String nodeId,String key,Object value)
	{
		TreeNode treeNode = (TreeNode)nodes.get(nodeId);
		if (treeNode != null) 
		{
			treeNode.addExtraData(key,value);
		}
	}	
		
	/**
	 * Returns extra data of a specific node
	 * @param nodeId the id of the node to get the extra data from
	 * @param key the key of the extra data
	 * @return the extra data of the node
	 */
	public Object getNodeExtraData(String nodeId,String key)
	{
		TreeNode treeNode = (TreeNode)nodes.get(nodeId);
		if (treeNode != null) 
		{
			return treeNode.getExtraData(key);
		}
		else
		{
			return null;
		}
	}	
	
	/**
	 * Returns the last node of the tree that was opened,relevant 
	 * only when a click on a node of the tree,is sending an event to the
	 * server. 
	 * @return the id of the node in the tree that was last opened,or 
	 * null if no node was opened.
	 */
	public String getLastExpandedNode()
	{
		return (String)getProperty(TREE_MODEL_OPEN_NODE_PROPERTY);
	}
	
	protected Event getNodeClickEvent(String treeNodeId)
	{
		TreeNode treeNode = (TreeNode)getTreeNode(treeNodeId);
		return (treeNode.getTreeNodeClickEvent() != null ? treeNode.getTreeNodeClickEvent() : getTreeNodeDefaultClickEvent());
	}
	
	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * click event on a tree node.Relevant only the tree node is linkable and
	 * if an event was not set to the node. 
	 * @return event the {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getTreeNodeDefaultClickEvent() 
	{
		return treeNodeDefaultClickEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * click event on a tree node in the tree model.
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setTreeNodeDefaultClickEvent(Event event) 
	{
		treeNodeDefaultClickEvent = event;
	}

	/**
	 * Returns the showRoot property.
	 * @return true if the root of this model is shown.
	 */
	public boolean isShowRoot()
	{
		return showRoot;
	}

	/**
	 * Sets the showRoot property.
	 * @param showRoot the showRoot to set - if true the tree has one root,if<br>
	 * false tree's root is never shown,and in fact the tree has multiple roots
	 */
	public void setShowRoot(boolean showRoot)
	{
		this.showRoot = showRoot;
		if (showRoot)
		{
			setRootLevel(DEFAULT_TREE_LEVEL);
		}
		else 
		{
			setRootLevel(MULTIPLE_ROOT_TREE_LEVEL);
		}
	}

	/**
	 * Returns the default dataMaskKey of all nodes in the tree<br>
	 * which is a string representing a mask in a properties file.<br>
	 * This default value can be overriden by each {@link TreeNode}
	 * This mask affects the way that the data of this node will be formatted.
	 * @return the key of the data mask from a properties file.
	 */
	public String getDataMaskKey() 
	{
		return dataMaskKey;
	}
	
	/**
	 * Returns the default data type of all nodes in the tree<br> 
	 * which may be one of 4 constans of TreeModel <br>
	 * DATA_TYPE_STRING - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.String 
	 * DATA_TYPE_DATE - indicates that the object that was set by the method setData<br>
	 * is of type java.util.Date 
	 * DATA_TYPE_NUMBER - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Number 
	 * DATA_TYPE_BOOLEAN - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Boolean
	 * This default value can be overriden by each {@link TreeNode}
	 * The dataType affects the way that the data of this node will be formatted. 
	 * @return the default data type of all nodes in the tree.
	 */
	public String getDataType() 
	{
		return dataType;
	}
	
	/**
	 * Sets the default dataMaskKey of all nodes in the tree<br>
	 * which is a string representing a mask in a properties file.<br>
	 * This default value can be overriden by each {@link TreeNode}
	 * This mask affects the way that the data of this node will be formatted.
	 * @param dataMaskKey the key of the data mask from a properties file.
	 */
	public void setDataMaskKey(String dataMaskKey) 
	{
		this.dataMaskKey = dataMaskKey;
	}
	
	/**
	 * Returns the default data type of all nodes in the tree<br> 
	 * which may be one of 4 constans of TreeModel <br>
	 * DATA_TYPE_STRING - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.String 
	 * DATA_TYPE_DATE - indicates that the object that was set by the method setData<br>
	 * is of type java.util.Date 
	 * DATA_TYPE_NUMBER - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Number 
	 * DATA_TYPE_BOOLEAN - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Boolean
	 * This default value can be overriden by each {@link TreeNode}
	 * The dataType affects the way that the data of this node will be formatted. 
	 * @param dataType the default data type of all nodes in the tree.
	 */
	public void setDataType(String dataType) 
	{
		this.dataType = dataType;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * expand event on a tree node. 
	 * @return event the {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getTreeNodeDefaultExpandEvent() 
	{
		return treeNodeDefaultExpandEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * expand event on a tree node in the tree model.
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setTreeNodeDefaultExpandEvent(Event event) 
	{
		treeNodeDefaultExpandEvent = event;
	}

}
