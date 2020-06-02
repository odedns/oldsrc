package com.ness.fw.ui;

import java.util.*;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;

public class TreeTableModel extends AbstractTableModel
{	
	private static final String TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY = "open";
	private static final String TREE_TABLE_MODEL_CLOSED_ROWS_PROPERTY = "close";	
	
	private static final String TREE_TABLE_MODEL_OPEN_EVENT_TYPE = "ot";
		
	private TableTreeModel tableTreeModel;
	
	private boolean showRoot = true;
	
	/**
	 * empty constructor
	 */
	public TreeTableModel()
	{
		super();
		tableTreeModel = new TableTreeModel(new TableTreeNode());
	}
	
	/**
	 * Constructor for TreeTableModel.
	 * @param root the new {@link Row} root of the tree table model
	 */
	public TreeTableModel(Row root)
	{
		super();
		setRootRow(root);
		//tableTreeModel = new TableTreeModel(new TableTreeNode(root));	
	}
	
	/**
	 * handles TreeTableModel events 
	 */
	protected void handleEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		String eventType = getEventTypeProperty();
		if (eventType != null)
		{
			if (eventType.equals(TABLE_MODEL_LINK_EVENT_TYPE))
			{
				handleCellEvent(checkAuthorization);
				handleRowEvent(checkAuthorization);
			}
			else if (eventType.equals(TREE_TABLE_MODEL_OPEN_EVENT_TYPE))
			{
				handleRowEvent(checkAuthorization);
			}
			
			else if (eventType.equals(TABLE_MODEL_ROWS_EVENT_TYPE))
			{
				handleRowEvent(checkAuthorization);
			}	
			else if (eventType.equals(TABLE_MODEL_ROW_EVENT_TYPE))
			{
				handleRowEvent(checkAuthorization);
			}	
			else if (eventType.equals(TABLE_MODEL_MENU_EVENT_TYPE))
			{
				handleMenuEvent(checkAuthorization);
				handleRowEvent(checkAuthorization);
			}	
			handleOpenTreeEvent();		
		}		
	}
	
	/**
	 * handles open tree event
	 * @throws UIException
	 */
	protected void handleOpenTreeEvent() throws UIException
	{
		setOpenRows(getOpenedRowsIds());
	}
	
	/**
	 * Marks rows as open and adds the ids of the rows to the list of
	 * the open rows.
	 * @param openRows ArrayList with id's of open rows
	 */
	public void setOpenRows(ArrayList openRows)
	{
		if (openRows != null)
		{
			tableTreeModel.setOpenNodes(openRows);
			modelProperties.put(TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY,openRows);
		}
	}
	
	/**
	 * Adds row to the list of the open rows
	 * rowId the id of the row to add
	 */
	protected void addOpenRow(String rowId)
	{
		ArrayList openedRows = getOpenedRowsIds();
		if (openedRows == null)
		{
			openedRows = new ArrayList();
		}
		openedRows.add(rowId);
		modelProperties.put(TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY,openedRows);
	}
	
	/**
	 * Returns ArrayList of rows that are marked as open 
	 * @return list of open rows
	 */
	public ArrayList getOpenedRowsIds()
	{
		return getListProperty(TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY);
	}
	
	/**
	 * Sets new {@link Row} root to the tree table
	 * @param root the new row root of the tree table model
	 */
	public String setRootRow(Row root)
	{
		return setRootRow(root,false); 	
	}
	
	/**
	 * Sets new {@link Row} root to the tree table
	 * @param root the new row root of the tree table model
	 * @param isOpen if true marks the new roor as open
	 */	
	public String setRootRow(Row root,boolean isOpen)
	{
		if (tableTreeModel == null) 
		{
			tableTreeModel = new TableTreeModel(new TableTreeNode(root,isOpen));
			root.setId(tableTreeModel.getRoot().getId());
		}	
		else 
		{
			TableTreeNode rootNode = (TableTreeNode)tableTreeModel.getRoot();
			root.setId(rootNode.getId());
			rootNode.setRow(root);
			rootNode.setOpen(isOpen);
			if (isOpen)
			{
				addOpenRow(rootNode.getId());
			}
		}	
		return tableTreeModel.getRoot().getId();			
	}
	
	/**
	 * Creates new {@link Row} object, initialize it with empty {@link Cell} objects.<br>
	 * The number of cells created is the number of columns in the table.
	 * @return the new {@link Row} object
	 */
	public Row createRow()
	{	Row row = new Row(getColumnsCount());
		return row;
	}
		
	/**
	 * Adds {@link Row} under the root
	 * @param row the to add
	 */
	public String addRow(Row row)
	{
		return addRow(row,getRoot().getId());
	}
	
	/**
	 * adds {@link Row} to the model by the id of the row's parent in the tree
	 * @param row the row to add
	 * @param rowParentId the id of the parent row
	 * @return the id of the row that was added	
	 */
	public String addRow(Row row,String rowParentId)
	{
		return addRow(row,false,rowParentId);
	}	
	
	/**
	 * adds {@link Row} to the model by the id of the row's parent in the tree
	 * @param row the row to add
	 * @param isOpen if true - the row is open which means that its children will be rendered
	 * @param rowParentId the id of the parent row
	 * @return the id of the row that was added	
	 */
	public String addRow(Row row,boolean isOpen,String rowParentId)
	{
		return addRow(row,isOpen,"","","",rowParentId);	
	}

	/**
	 * Adds {@link Row} to the model by the id of the row's parent in the tree
	 * @param row the row to add
	 * @param isSelected if true - the row is marked as selected
	 * @param isOpen if true - the row is open which means that its children will be rendered
	 * @param rowParentId the id of the parent row
	 * @return the id of the row that was added	
	 */
	public String addRow(Row row,boolean isOpen,boolean isSelected,String rowParentId)
	{
		return addRow(row,isOpen,isSelected,"","","",rowParentId);	
	}
	
	/**
	 * adds {@link Row} to the model by the id of the row's parent in the tree
	 * @param row the row to add
	 * @param isOpen if true - the row is open which means that its children will be rendered
	 * @param closeImage name of an image for closed row - a row that has children which are not visible
	 * @param openImage name of an image for open row - a row that has children which are visible
	 * @param emptyImage name of an image for empty row -  a row that has no children
	 * @param rowParentId the id of the parent row
	 * @return the id of the row that was added	
	 */	
	public String addRow(Row row,boolean isOpen,String emptyImage, String openImage, String closeImage,String rowParentId)
	{
		return addRow(row,isOpen,false,emptyImage,openImage,closeImage,rowParentId);
	}
	
	/**
	 * adds {@link Row} to the model by the id of the row's parent in the tree
	 * @param row the row to add
	 * @param isOpen if true - the row is open which means that its children will be rendered
	 * @param isSelected if true - the row is marked as selected
	 * @param closeImage name of an image for closed row - a row that has children which are not visible
	 * @param openImage name of an image for open row - a row that has children which are visible
	 * @param emptyImage name of an image for empty row -  a row that has no children
	 * @param rowParentId the id of the parent row
	 * @return the id of the row that was added	
	 */
	public String addRow(Row row,boolean isOpen,boolean isSelected,String emptyImage, String openImage, String closeImage,String rowParentId)
	{
		row.setSelected(isSelected);
		String rowId;
		if (tableTreeModel == null)
		{
			if (!showRoot)
			{
				tableTreeModel =
					new TableTreeModel(new TableTreeNode(new Row(TableTreeNode.DEFAULT_ROOT_ID), true, emptyImage, openImage, closeImage));
				rowId = tableTreeModel.add(new TableTreeNode(row,isOpen,emptyImage,openImage, closeImage),TableTreeNode.DEFAULT_ROOT_ID);
			}
			else
			{
				tableTreeModel = new TableTreeModel(new TableTreeNode(row,isOpen,emptyImage, openImage, closeImage));
				rowId = tableTreeModel.getRoot().getId();
			}                    
		}
		else
		{
			TableTreeNode newNode = new TableTreeNode(row,isOpen,emptyImage,openImage, closeImage);
			rowId = tableTreeModel.add(newNode,rowParentId);
		}
		if (isOpen)
		{
			addOpenRow(rowId);
		}
		if (isSelected)
		{
			if (isMultiple())
			{
				addSelectedRow(row);
			}
			else
			{
				try
				{
					setSelectedRow(rowId);
				}
				catch (UIException ui)
				{
					ui.printStackTrace();
				}
			}
		}
		return rowId;
	}

	/**
	 * adds {@link Row} to the model by the location of the row's node in the tree
	 * @param row the row to add
	 * @return the id of the row that was added	
	 */
	public String addRowByPath(Row row)
	{
		return addRowByPath(row, "", true);
	}
	

	/**
	 * adds {@link Row} to the model by the location of the row's node in the tree
	 * @param row the row to add
	 * @param path path to add the row to
	 * @return the id of the row that was added	
	 */
	public String addRowByPath(Row row,String path)
	{
		return addRowByPath(row, path, true);
	}
	
	/**
	 * adds {@link Row} to the model by the location of the row's node in the tree
	 * @param row the row to add
	 * @param path the exact path to add the row to
	 * @param isOpen if true - the row is open which means that its children will be rendered
	 * @return the id of the row that was added	
	 */
	public String addRowByPath(Row row,String path,boolean isOpen)
	{
		return addRowByPath(row,path,isOpen,"","","");
	}
	
	/**
	 * adds {@link Row} to the model by the location of the row's node in the tree
	 * @param row the row to add
	 * @param path the exact path to add the row to
	 * @param isOpen if true - the row is open which means that its children will be visible
	 * @return the id of the row that was added	
	 */
	public String addRowByPath(Row row,String path,boolean isOpen,boolean isSelected)
	{
		return addRowByPath(row,path,isOpen,isSelected,"","","");		
	}
	
	/**
	 * adds {@link Row} to the model by the location of the row's node in the tree
	 * @param row the row to add
	 * @param path the exact path to add the row to
	 * @param isOpen if true - the row is open which means that its children will be visible
	 * @param closeImage name of an image for closed row - a row that has children which are not visible
	 * @param openImage name of an image for open row - a row that has children which are visible
	 * @param emptyImage name of an image for empty row -  a row that has no children
	 * @return the id of the row that was added	
	 */
	public String addRowByPath(Row row,String path, boolean isOpen,String emptyImage, String openImage, String closeImage)
	{
		return addRowByPath(row,path,isOpen,false,emptyImage,openImage,closeImage);
	}
	
	/**
	 * adds {@link Row} to the model by the location of the row's node in the tree
	 * @param row the row to add
	 * @param path the exact path to add the row to
	 * @param isOpen if true - the row is open which means that its children will be visible
	 * @param isSelected if true - the row is marked as selected
	 * @param closeImage name of an image for closed row - a row that has children which are not visible
	 * @param openImage name of an image for open row - a row that has children which are visible
	 * @param emptyImage name of an image for empty row -  a row that has no children
	 * @return the id of the row that was added	
	 */
	public String addRowByPath(Row row,String path, boolean isOpen,boolean isSelected,String emptyImage, String openImage, String closeImage)
	{
		row.setSelectable(isSelected);
		if (tableTreeModel == null)
		{
			if (!showRoot)
			{
				tableTreeModel =
					new TableTreeModel(new TableTreeNode(new Row(TableTreeNode.DEFAULT_ROOT_ID), true, emptyImage, openImage, closeImage));
				row.setId(String.valueOf(idCounter));
				idCounter++;
				tableTreeModel.add(new TableTreeNode(row, isOpen, emptyImage, openImage, closeImage), TableTreeNode.DEFAULT_ROOT_ID);
			}
			else
			{
				row.setId(tableTreeModel.getRoot().getId());
				tableTreeModel = new TableTreeModel(new TableTreeNode(row, isOpen, emptyImage, openImage, closeImage));
			}
		}
		else
		{
			row.setId(String.valueOf(idCounter));
			idCounter++;
			TableTreeNode newNode = new TableTreeNode(row, isOpen, emptyImage, openImage, closeImage);
			tableTreeModel.addByPath(newNode, path);
		}
		if (isOpen)
		{
			addOpenRow(row.getId());
		}
		if (isSelected)
		{
			addSelectedRow(row);
		}
		return row.getId();
	}
	
	/**
	 * Removes {@link Row} from the tree by the row's id
	 * @param rowId the id of the removed row
	 */	
	public void removeRow(String rowId) throws UIException
	{
		setUnselectedRow(rowId);
		tableTreeModel.remove(rowId);
	}
	
	public void removeChildren(String rowId)
	{
		tableTreeModel.removeChildren(rowId);
	}
	
	/**
	 * Removes all the rows of the model,except the root.
	 * @throws UIException
	 */
	public void removeAllRows() throws UIException
	{
		tableTreeModel.removeAll();
		removeProperty(TREE_TABLE_MODEL_CLOSED_ROWS_PROPERTY);
		removeProperty(TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY);
		removeProperty(TABLE_MODEL_SELECTED_ROWS_PROPERTY);
		removeProperty(TABLE_MODEL_SELECTED_ROW_PROPERTY);
		removeProperty(TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY);
		removeProperty(TABLE_MODEL_SELECTED_CELL_PROPERTY);
		removeProperty(TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY);
		removeProperty(TABLE_MODEL_SELECTED_MENU_ITEM_PROPERTY);
		idCounter = 0;	
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.AbstractTableModel#getAllRows()
	 */
	public ArrayList getAllRows()
	{
		return getAllRows(getRoot(),new ArrayList());
	}
	
	private ArrayList getAllRows(TableTreeNode node,ArrayList rows)
	{
		if (node != null)
		{
			Row row = node.getRow();
			rows.add(row);
			for (int index = 0;index < node.getChildrenNumber();index++)
			{
				getAllRows((TableTreeNode)node.getChild(index),rows);
			}
		}
		return rows;
	}
	
	/**
	 * Returns {@link Row} of the tree table model
	 * @param rowId the id of the row to get
	 * @return {@link Row} object 
	 * @throws UIException if the row does not exist
	 */
	public Row getRow(String rowId) throws UIException
	{
		TableTreeNode node = (TableTreeNode)tableTreeModel.getTreeNode(rowId);
		if (node == null)
		{
			throw new UIException("row " + rowId + " does not exist in the tree table model");
		}
		return node.getRow();
	}

	/**
	 * Returns the number of rows in this tree table model.<br>
	 * If showRoot=false,the root is not counted
	 * @return number of rows in this tree table model
	 */
	public int getRowsCount()
	{
		return showRoot ? tableTreeModel.getNodes().size() : tableTreeModel.getNodes().size() - 1;	
	}
	
	/**
	 * Returns parent of a row from the tree model
	 * @param rowId the id of the row
	 * @return {@link Row} object - the row's parent
	 */
	public Row getRowParent(String rowId)
	{
		return ((TableTreeNode) ((TableTreeNode) tableTreeModel.getTreeNode(rowId)).getParent()).getRow();
	}

	/**
	 * Returns list of children rows of a specific row of the tree model
	 * @param rowId the id of the row to get its child rows
	 * @return a list of the row's children rows
	 */
	public ArrayList getRowChildren(String rowId)
	{
		ArrayList rows = new ArrayList();
		TableTreeNode parent = (TableTreeNode) tableTreeModel.getTreeNode(rowId);
		for (int index = 0; index < parent.getChildrenNumber(); index++)
		{
			rows.add(((TableTreeNode) parent.getChild(index)).getRow());
		}
		return rows;
	}

	/**
	 * Marks rows in the tree as open
	 * @param openRowIds array of open rows ids
	 */
	protected void setOpenRows(String[] openRows)
	{
		tableTreeModel.setOpenNodes(openRows);
	}
	
	/**
	 * Marks rows of the tree as open
	 * @param openRows string which represents a list of row ids,seperated by "|"
	 */
	protected void setOpenRows(String openRows)
	{
		tableTreeModel.setOpenNodes(openRows);	
	}
	
	/**
	 * Marks nodes of the tree as open
	 * @param openRowsIds hashmap which represents a list of row ids,seperated by "|"
	 */
	protected void setOpenRows(HashMap openRowsIds)
	{
		tableTreeModel.setOpenNodes(openRowsIds);	
	}	

	/**
	 * Selects rows in the table 
	 * @param selectedRows hashmap of row ids to mark as selected
	 */
	protected void selectRows(HashMap selectedRows,boolean unSelectAll)
	{
		if (!isMultiple() && selectedRows.size() > 1)
		{
			return;
		}
		Iterator iter = tableTreeModel.getNodes().values().iterator();
		while (iter.hasNext())
		{
			TableTreeNode treeTableNode = (TableTreeNode) iter.next();
			if (selectedRows.get(treeTableNode.getRow().getId()) != null)
			{
				treeTableNode.getRow().setSelected(true);
			}
			else if (!isMultiple() || unSelectAll)
			{
				treeTableNode.getRow().setSelected(false);
			}
		}
	}
	
	/**
	 * Unselects rows in the table 
	 * @param unSelectedRows hashmap of row ids to mark as unselected
	 */
	protected void unSelectRows(HashMap unSelectedRows)
	{
		Iterator iter = tableTreeModel.getNodes().values().iterator();
		while (iter.hasNext())
		{
			TableTreeNode treeTableNode = (TableTreeNode) iter.next();
			if (unSelectedRows.get(treeTableNode.getRow().getId()) != null)
			{
				treeTableNode.getRow().setSelected(false);
			}
		}
	}
	
	/**
	 * Marks rows in the table as selected or unselected
	 * @param markedRows hashmap of row ids
	 */
	protected void markRows(HashMap markedRows)
	{
		Iterator iter = tableTreeModel.getNodes().values().iterator();
		while (iter.hasNext())
		{
			TableTreeNode treeTableNode = (TableTreeNode)iter.next();
			Row row = treeTableNode.getRow();
			row.setSelected(((Boolean)markedRows.get(row.getId())).booleanValue());
		}
	}		
	
	/**
	 * Removes all cells that belong to a specific column
	 * @param columnIndex the index of the column to remove all his cells
	 * @throws UIException if the column does not exist
	 */
	public void removeColumnCells(int columnIndex) throws UIException
	{
		Iterator nodes = tableTreeModel.getNodes().values().iterator();
		while (nodes.hasNext())
		{
			((TableTreeNode) nodes.next()).getRow().removeCell(columnIndex);
		}
	}

	/**
	 * Returns the root of the tree of this tree table model
	 * @return {@link TableTreeNode} object
	 */
	public TableTreeNode getRoot()
	{
		return (TableTreeNode)tableTreeModel.getRoot();
	}
	
	/**
	 * Returns a {@link TableTreeNode} object a specific node in the tree table
	 * @param nodeId the id of the node
	 * @return TableTreeNode the {@link TableTreeNode} object for the node or null<br>
	 * if the node does nor exist in the tree table
	 */
	public TableTreeNode getTableTreeNode(String nodeId)
	{
		return (TableTreeNode)tableTreeModel.getTreeNode(nodeId);
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
			tableTreeModel.setRootLevel(TableTreeModel.DEFAULT_TREE_LEVEL);
		}
		else 
		{
			tableTreeModel.setRootLevel(TableTreeModel.MULTIPLE_ROOT_TREE_LEVEL);
		}
	}
	
	/**
	 * Returns the level of the root in the tree table model,which is 0 if
	 * showRoot = true and 1 if showRoot = false.
	 * @return the level of the root in the tree
	 */
	public int getRootLevel()
	{
		return tableTreeModel.getRootLevel();
	}
	
	/**
	 * Checks if there are any rows in the model
	 * @return true if there are no rows in the model
	 */	
	public boolean isModelEmpty()
	{
		return (tableTreeModel.getNodes() == null || 
				tableTreeModel.getNodes().isEmpty() ||
				tableTreeModel.getNodes().size() == 1 && !showRoot); 	
	}
}
