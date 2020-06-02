package com.ness.fw.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.ui.events.Event;

public abstract class AbstractTableModel extends AbstractModel
{
	protected static final String TABLE_MODEL_SELECTED_ROWS_PROPERTY = "rows";
	protected static final String TABLE_MODEL_UNSELECTED_ROWS_PROPERTY = "uRows";	
	protected static final String TABLE_MODEL_SELECTED_ROW_PROPERTY = "row";
	protected static final String TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY = "menuRow";
	protected static final String TABLE_MODEL_SELECTED_MENU_ITEM_PROPERTY = "menuIndex";
	protected static final String TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY = "linkRow";
	protected static final String TABLE_MODEL_SELECTED_CELL_PROPERTY = "cell";
	protected static final String TABLE_MODEL_MENU_ACTION_PROPERTY = "menuAction";
	protected static final String TABLE_MODEL_COLUMN_ORDER_PROPERTY = "colOrder";
	
	protected static final String TABLE_MODEL_ROWS_EVENT_TYPE = "multipleRow";
	protected static final String TABLE_MODEL_ROW_EVENT_TYPE = "singleRow";
	protected static final String TABLE_MODEL_LINK_EVENT_TYPE = "link";
	protected static final String TABLE_MODEL_MENU_EVENT_TYPE = "menu";
	protected static final String TABLE_MODEL_COLUMN_ORDER_EVENT_TYPE = "order";
	
	/**
	 * Constant used for the selectionType attribute.When the selectionType is set<br>
	 * to this value no selection of rows in the table is allowed. 
	 */
	public static final int SELECTION_NONE = UIConstants.TABLE_SELECTION_NONE;
	
	/**
	 * Constant used for the selectionType attribute.When the selectionType is set<br>
	 * to this value only selection of one row in the table is allowed. 
	 */
	public static final int SELECTION_SINGLE = UIConstants.TABLE_SELECTION_SINGLE;

	/**
	 * Constant used for attribute.When the selectionType is set<br>
	 * to this value selection of few rows in the table is allowed. 
	 */
	public static final int SELECTION_MULTIPLE = UIConstants.TABLE_SELECTION_MULTIPLE;

	/**
	 * Constant used for the selectionType attribute.When the selectionType is set<br>
	 * to this value selection of all the rows in the table in the same time is allowed. 
	 */
	public static final int SELECTION_ALL = UIConstants.TABLE_SELECTION_ALL;

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

	private String header;
	private String summary;
	private boolean allowMenus = false;
	private int selectionType;
	private ArrayList columns;
	private ArrayList columnsDisplayOrder;
	protected Menu defaultMenu = null;
	protected int idCounter = 0;
	protected HashMap columnNames;
	private ArrayList tableLinks;

	
	/**
	 * The {@link com.ness.fw.ui.events.Event} object which holds the default information about 
	 * double click event on a cell in the model 
	 */
	protected Event rowDefaultClickEvent;


	/**
	 * The {@link com.ness.fw.ui.events.Event} object which holds the default information about 
	 * click event on a cell in the model. 
	 */
	protected Event rowDefaultDblClickEvent;

	/**
	 * The {@link com.ness.fw.ui.events.Event} object which holds 
	 * the default information about click event on a cell in the model. 
	 */	
	protected Event cellDefaultClickEvent;
	
	/**
	 * empty constructor for TableModel.
	 */
	public AbstractTableModel()
	{
		columns = new ArrayList();
		columnsDisplayOrder = new ArrayList();
		columnNames = new HashMap();
		tableLinks = new ArrayList();	
		rowDefaultClickEvent = new Event();
		rowDefaultClickEvent.setEventType(Event.EVENT_TYPE_READONLY);
		rowDefaultDblClickEvent = new Event();
		rowDefaultDblClickEvent.setEventType(Event.EVENT_TYPE_READONLY);
	}
	
	public AbstractTableModel(AbstractTableModel model)
	{
		columns = new ArrayList();
		columnNames = new HashMap();
		setHeader(model.getHeader());
		setSummary(model.getSummary());
		setAllowMenus(model.isAllowMenus());
		setSelectionType(model.getSelectionType());
		if (model.getDefaultMenu() != null)
			setDefaultMenu(new Menu(model.getDefaultMenu()));
		for (int index = 0; index < model.getColumnsCount(); index++)
		{
			addColumn(new Column((Column) model.columns.get(index)));
		}
	}
		
	/**
	 * Marks rows as selected and adds the ids of the rows to the list of
	 * selected rows in multiple selection mode.
	 * @param selectedRows ArrayList of selected rows ids.
	 * @throws UIException if the selectionType of the model is not multiple.
	 */
	public void setSelectedRows(ArrayList selectedRows) throws UIException
	{
		if (!isMultiple())
		{
			throw new UIException("using this method is not allowed because the type of selection in the model is not multiple");
		}
		else if (selectedRows != null)
		{
			if (selectedRows.size() == 0)
			{
				unSelectAll();
			}
			else
			{
				selectRows(selectedRows,true);
			}	
			modelProperties.put(TABLE_MODEL_SELECTED_ROWS_PROPERTY,selectedRows);
		}
	}
		
	/**
	 * Marks row as selected in multiple rows model and adds the id of the row to the list of 
	 * selected rows in multiple rows model.
	 * @param rowId the id of the row
	 * @throws UIException if row does not exist
	 */	
	public void addSelectedRow(String rowId) throws UIException
	{
		if (isMultiple())
		{
			ArrayList selectedRows = getSelectedRowsIds();
			if (selectedRows == null)
			{
				selectedRows = new ArrayList();
			}
			selectedRows.add(rowId);
			getRow(rowId).setSelected(true);
			modelProperties.put(TABLE_MODEL_SELECTED_ROWS_PROPERTY,selectedRows);
		}
	}
	
	
	/**
	 * Marks Row as unselected
	 * @param rowId the row id
	 * @throws UIException if row does not exist
	 */
	public void setUnselectedRow(String rowId) throws UIException
	{
		getRow(rowId).setSelected(false);
		if (isMultiple())
		{
			ArrayList selectedRows = getSelectedRowsIds();
			if (selectedRows != null)
			{
				selectedRows.remove(rowId);
				setProperty(TABLE_MODEL_SELECTED_ROWS_PROPERTY,selectedRows);									
			}
		}
		else
		{
			String selectedRowId = getStringProperty(TABLE_MODEL_SELECTED_ROW_PROPERTY);
			if (selectedRowId != null && rowId.equals(selectedRowId))
			{
				removeProperty(TABLE_MODEL_SELECTED_ROW_PROPERTY);
			}
		}
	}
	
	/**
	 * Adds the id of the row to the list of selected rows in multiple rows model.
	 * @param row the row 
	 */
	protected void addSelectedRow(Row row)
	{
		ArrayList selectedRows = getListProperty(TABLE_MODEL_SELECTED_ROWS_PROPERTY);
		if (selectedRows == null)
		{
			selectedRows = new ArrayList();
		}
		selectedRows.add(row.getId());
		modelProperties.put(TABLE_MODEL_SELECTED_ROWS_PROPERTY,selectedRows);
	}
	
	/**
	 * Marks row as selected in single rows model and sets it as the
	 * selected Row of the model 
	 * @param rowId the selected rowId
	 * @throws UIException if row does not exist or if the selectionType of the model<br>
	 * is not single.
	 */
	public void setSelectedRow(String rowId) throws UIException
	{
		if (isMultiple())
		{
			throw new UIException("using this method is not allowed because the type of selection in the model is not single");
		}
		else
		{
			HashMap selectedRows = new HashMap();
			if (rowId != null)
			{
				selectedRows.put(rowId,"");
				selectRows(selectedRows,true);
				modelProperties.put(TABLE_MODEL_SELECTED_ROW_PROPERTY,rowId);
			}
		}
	}
	
	/**
	 * Marks all rows as unselected and removes them from the selected rows list
	 * @throws UIException
	 */
	public void unSelectAll() throws UIException
	{
		HashMap selectedRows = new HashMap();
		selectRows(selectedRows,true);
		if (isMultiple())
		{
			modelProperties.remove(TABLE_MODEL_SELECTED_ROWS_PROPERTY);	
		}
		else
		{
			modelProperties.remove(TABLE_MODEL_SELECTED_ROW_PROPERTY);
		}
	}
	
	/**
	 * Sets the row id of the Row that was selected from a Menu
	 * @param rowId the id of the row
	 */
	protected void setSelectedMenuRow(String rowId)
	{
		modelProperties.put(TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY,rowId);
	}
	
	/**
	 * Sets the row id of the Row that was selected from a link in a Cell
	 * @param rowId the id of the Row
	 */	
	protected void setSelectedLinkRow(String rowId)
	{
		modelProperties.put(TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY,rowId);
	}	
	
	/**
	 * handles row event
	 * @throws UIException
	 */
	protected void handleRowEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		if (isMultiple())
		{			
			setSelectedRows(getSelectedRowsIds());
		}
		else
		{
			if (getSelectedRowId() == null || checkEventLegal(getRowClickEvent(getSelectedRowId()),checkAuthorization))
			{
				setSelectedRow(getSelectedRowId());			
			}
			else
			{
				removeProperty(TABLE_MODEL_SELECTED_ROW_PROPERTY);
				removeEventType(TABLE_MODEL_ROW_EVENT_TYPE);
			}
		}
	}
	
	/**
	 * handles cell event
	 * @throws UIException
	 */
	protected void handleCellEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		String cellId = (String)getProperty(TABLE_MODEL_SELECTED_CELL_PROPERTY);
		String rowId = (String)getProperty(TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY);
		if (cellId != null && rowId != null && !checkEventLegal(getCellClickEvent(rowId,cellId),checkAuthorization))
		{
			removeProperty(TABLE_MODEL_SELECTED_CELL_PROPERTY);
			removeProperty(TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY);
			removeEventType(TABLE_MODEL_LINK_EVENT_TYPE);
		}	
	}
	
	/**
	 * handles menu event
	 * @throws UIException
	 */
	protected void handleMenuEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		String rowId = (String)getProperty(TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY);
		String menuItemIndex = (String)getProperty(TABLE_MODEL_SELECTED_MENU_ITEM_PROPERTY);
		if (rowId != null && menuItemIndex != null)
		{
			Event rowEvent = getRowClickEvent(rowId);
			CustomEvent menuItemEvent = getMenuItemClickEvent(rowId,menuItemIndex);
			if (!checkEventLegal(rowEvent,checkAuthorization) || !checkEventLegal(menuItemEvent,checkAuthorization))
			{
				removeProperty(TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY);
				removeEventType(TABLE_MODEL_MENU_EVENT_TYPE);
			}
		}		
	}
	
	/**
	 * Returns an ArrayList of the selected rows ids in a multiple rows model
	 * @return ArrayList of the selected rows ids or null if no rows were selected
	 * @throws UIException if the type of the selection is not multiple
	 */	
	public ArrayList getSelectedRowsIds() throws UIException
	{
		if (!isMultiple())
		{
			throw new UIException("using this method is not allowed because the type of selection in the model is not multiple ");
		}		
		return getListProperty(TABLE_MODEL_SELECTED_ROWS_PROPERTY);
	}
	
	/**
	 * Returns an ArrayList of selected {@link Row} objects in a multiple rows model
	 * @return ArrayList of {@link Row} objects or null if no rows were selected
	 * @throws UIException if a row is not found in the model or if the type of the<br>
	 * selection is not multiple
	 */
	public ArrayList getSelectedRows() throws UIException
	{
		if (!isMultiple())
		{
			throw new UIException("using this method is not allowed because the type of selection in the model is not multiple ");
		}
		else
		{
			ArrayList rowIds = getListProperty(TABLE_MODEL_SELECTED_ROWS_PROPERTY);
			if (rowIds == null)
			{
				return null;
			}
			ArrayList rows = new ArrayList();
			for (int index = 0;index < rowIds.size();index++)
			{
				rows.add(getRow((String)rowIds.get(index)));
			}
			return rows;
		}
	}


	/**
	 * Returns the id of the selected {@link Row} in a single row model
	 * @return the id of the Row or null if no row was selected or if the type of the<br>
	 * selection is not single
	 */
	public String getSelectedRowId() throws UIException
	{
		if (isMultiple())
		{
			throw new UIException("using this method is not allowed because the type of selection in the model is not single");
		}
		return getStringProperty(TABLE_MODEL_SELECTED_ROW_PROPERTY);
	}
	
	/**
	 * Returns the selected {@link Row} object in a single row model
	 * @return select {@link Row} object or null if no row was selected
	 * @throws if the {@link Row} is not found in the model or if the type of the<br>
	 * selection is not single
	 */	
	public Row getSelectedRow() throws UIException
	{
		if (isMultiple())
		{
			throw new UIException("using this method is not allowed because the type of selection in the model is not single");
		}
		String rowId = getStringProperty(TABLE_MODEL_SELECTED_ROW_PROPERTY);
		return (rowId != null ? getRow(rowId) : null);
	}

	/**
	 * Returns the id of the row that was selected from a Menu
	 * @return the id of the row or null if no row was selected from a menu
	 */
	public String getSelectedMenuRowId()
	{
		return getStringProperty(TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY);
	}
	
	/**
	 * Returns the {@link Row} object that was selected from a {@link Menu}
	 * @return the {@link Row} object whose menu was selected or null if no row<br>
	 * was selected from a menu
	 * @throws if the {@link Row} is not found in the model
	 */
	public Row getSelectedMenuRow() throws UIException
	{
		String rowId = getStringProperty(TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY);
		return (rowId != null ? getRow(rowId) : null);
	}	
	
	/**
	 * Returns the selected id of the Row whose Cell was selected
	 * @return the id of the Row or null if no cell selected
	 */
	public String getSelectedLinkRowId()
	{
		return getStringProperty(TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY);
	}

	/**
	 * Returns the {@link Row} object whose Cell was selected
	 * @return {@link Row} object which contains the {@link Cell} that was selected<br>
	 * or null if no cell was selected
	 * @throws UIException if the {@link Row} is not found in the model
	 */
	public Row getSelectedLinkRow() throws UIException
	{
		String rowId = getStringProperty(TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY); 
		return (rowId != null ? getRow(rowId) : null);
	}

	/**
	 * Returns the id of the {@link Column} whose {@link Cell} was selected
	 * @return the id of the column or null if no cell was selected
	 */
	public String getSelectedColumnId()
	{
		return getStringProperty(TABLE_MODEL_SELECTED_CELL_PROPERTY);
	}

	/**
	 * Returns the {@link Column} object whose {@link Cell} was selected
	 * @return the id of the column or null if no cell was selected
	 * @throws UIException if the {@link Column} is not found<br>
	 */
	public Column getSelectedColumn() throws UIException
	{
		String columnId = getStringProperty(TABLE_MODEL_SELECTED_CELL_PROPERTY); 
		return (columnId != null ? getColumnById(columnId) : null);
	}
	
	/**
	 * Returns the name of the {@link Column} whose cell was selected
	 * @return the name of the column
	 * @throws UIException if the {@link Column} whose cell was selected<br>
	 * does not have a name or is not found
	 */
	public String getSelectedColumnName() throws UIException
	{
		return getColumnNameByColumnId(getSelectedColumnId());
	}
			
	/**
	 * Adds {@link Column} to columns list.If the column's name attribute<br>
	 * id not null,the column's name is registered in the model so the<br>
	 * model is able to return {@link Column} by its name. 
	 * @param Column the {@link Column} to add
	 */
	public void addColumn(Column column)
	{
		column.setId(String.valueOf(columns.size()));
		columnsDisplayOrder.add(String.valueOf(idCounter++));
		columns.add(column);
		if (column.getName() != null)
		{
			columnNames.put(column.getName(),column.getId());
		}
	}
	
	/**
	 * Sets the array for the columns display order.
	 * @param columnsDisplayOrder
	 */
	protected void setColumnsDisplayOrder(ArrayList columnsDisplayOrder)
	{
		this.columnsDisplayOrder = columnsDisplayOrder;
	}
	
	/**
	 * Adds column to the columns display list
	 * @param columnId
	 */
	protected void addColumnToColumnsDisplayOrder(String columnId)
	{
		columnsDisplayOrder.add(columnId);
	}
	
	/**
	 * Returns the array for the columns display order. 
	 * @return
	 */
	public ArrayList getColumnsDisplayOrder()
	{
		return columnsDisplayOrder;
	}
	
	/**
	 * Sets column's order by the column's index(order of adding to the model)
	 * @param columnIndex the index of the {@link Column} in the model
	 * @param order the rendering order of the column
	 * @throws UIException if the column does not exist in the model
	 */
	public void setColumnOrder(int columnIndex,int order) throws UIException
	{
		if (columnIndex < 0 || columnIndex > columnsDisplayOrder.size() - 1)
		{
			throw new UIException("The index of the column " + columnIndex + " is not legal");
		}
				
		if (order < 0 || order > columnsDisplayOrder.size() - 1)
		{
			throw new UIException("The order set for the column must be a number between 0 and the total number of displayed columns");
		}
		columnsDisplayOrder.remove(String.valueOf(columnIndex));
		columnsDisplayOrder.add(order,String.valueOf(columnIndex));
	}
	
	/**
	 * Sets column's order by the column's name
	 * @param columnName the name of the {@link Column}
	 * @param order the rendering order of the column
	 * @throws UIException if the column does not exist in the model
	 */
	public void setColumnOrder(String columnName,int order) throws UIException
	{
		setColumnOrder(Integer.parseInt(getColumn(columnName).getId()),order);
	}	
	
	/**
	 * Indicates if column is displayed
	 * @return
	 */
	public boolean isColumnDisplayable(String columnId)
	{
		return columnsDisplayOrder.contains(columnId);
	}
	
	/**
	 * Sets the name of a column in the model,by its index.<br>
	 * If the column does not exist a {@link com.ness.fw.common.exceptions.UIException} is thrown
	 * @param columnId
	 * @param columnName
	 * @throws UIException if the column doed not exist
	 */
	public void setColumnName(int columnIndex,String columnName) throws UIException
	{
		Column column = getColumn(columnIndex);
		column.setName(columnName);
		columnNames.put(columnName,column.getId());
	}
	
	/**
	 * Removes {@link Column} from the columns list by its index.
	 * @param index the index of the Column to remove
	 * @throws UIException if column does not exist in the model
	 */
	public void removeColumn(int index) throws UIException
	{
		if (index > columns.size() - 1 || index < 0)
		{
			throw new UIException("column does not exist - index " + index + " is bigger than the columns list size " + columns.size());
		}
		columns.remove(index);
		removeColumnCells(index);
		adjustColumnIds();
	}
	
	/**
	 * Removes {@link Column} from the columns list by its name.
	 * @param columnName the columnName of the Column to remove
	 * @throws UIException if column does not exist in the model
	 */
	public void removeColumn(String columnName) throws UIException
	{
		String columnIndex = (String)columnNames.get(columnName); 
		if (columnIndex == null)
		{
			throw new UIException("column with the name " + columnName + " does not exit");
		}
		else
		{
			removeColumn(Integer.parseInt(columnIndex));
		}
	}
	
	protected void adjustColumnIds()
	{
		for (int index = 0;index < columns.size();index++)
		{
			Column column = (Column)columns.get(index);
			column.setId(index);
			if (column.getName() != null)
			{
				columnNames.put(column.getName(),column.getId());
			}			
		}
	}
	
	/**
	 * Removes all cells of a column.
	 * @param index the index of the column.
	 * @throws UIException if column does not exist
	 */
	protected abstract void removeColumnCells(int index) throws UIException;
	
	/**
	 * Returns {@link Column} from the columns list by its index.
	 * @param index the order of the column in the list
	 * @throws UIException if the column does not exist in the model
	 */
	public Column getColumn(int index) throws UIException
	{
		if (index > columns.size() - 1 || index < 0)
		{
			throw new UIException("column does not exist:" + index + " " + columns.size());
		}
		return (Column)columns.get(index);
	}
	
	/**
	 * Returns {@link Column} from the columns list by its name.
	 * @param columnName the name of the column
	 * @return the {@link Column} object found
	 * @throws UIException if the column does not exist in the model
	 */
	public Column getColumn(String columnName) throws UIException
	{
		return getColumn(Integer.parseInt(getColumnIdByColumnName(columnName)));
	}
	
	/**
	 * Returns {@link Column} by the column id
	 * @param columnId
	 * @return
	 * @throws UIException
	 */
	protected Column getColumnById(String columnId) throws UIException
	{
		return getColumn(Integer.parseInt(columnId));
	}
		
	/**
	 * Returns the id of a column by its name,if the column's name was set<br>
	 * before it was added to the model.
	 * @param columnName
	 * @return
	 * @throws UIException if the column does not exist.<br>
	 */	
	protected String getColumnIdByColumnName(String columnName) throws UIException
	{
		if (columnNames.get(columnName) != null)
		{
			return ((String)columnNames.get(columnName));
		}
		else
		{
			throw new UIException("column with the name " + columnName + " does not exit in the model");
		}		
	}
	
	/**
	 * Returns the name of a {@link Column} by its id,if the {@link Column} exist<br>
	 * in the model.
	 * @param columnId the id of the model
	 * @return the name of the {@link Column}
	 * @throws UIException if the column does not exist in the model
	 */
	protected String getColumnNameByColumnId(String columnId) throws UIException
	{
		Column column = getColumnById(columnId);
		if (column.getName() != null)
		{
			return column.getName();
		}
		else
		{
			throw new UIException("column with index " + column.getId() + "does not have a name");
		}
	}
	
	/**
	 * Returns the number of columns in the table
	 * @return number of columns in the table
	 */
	public int getColumnsCount()
	{
		return columns.size();
	}
	
	/**
	 * Returns the list of columns in the table model
	 * @return ArrayList of the columns in the model
	 */
	protected ArrayList getColumns()
	{
		return columns;
	}
	
	/**
	 * Indicates if at least one Column of the table contains footer
	 * @return true if at least one footer exist
	 */
	public boolean isFootersExit() throws UIException
	{
		for (int index = 0; index < columns.size(); index++)
		{
			if (getColumn(index).getFooter() != null && !getColumn(index).getFooter().equals(""))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Indicates if model is empty of rows.
	 * @return true if model does not contain any rows.
	 */
	public abstract boolean isModelEmpty();
	
	/**
	 * Indicates if multiple rows are allowed to be selected in the model
	 * @return true if multiple rows are allowed to be selected in the model
	 */
	public boolean isMultiple()
	{
		return selectionType > SELECTION_SINGLE; 
	}
	
	/**
	 * Indicates if any type of selection of rows - single or multiple
	 * is allowed in this model 
	 * @return true if selection of rows is allowed in this model
	 */
	public boolean isSelectionAllowed()
	{
		return selectionType != SELECTION_NONE;
	}
	

	protected void selectRows(ArrayList selectedRows) throws UIException
	{
		selectRows(selectedRows,true);
	}
	
	/**
	 * selects rows in the table
	 * @param selectedRowsIds list of row ids to mark as selected
	 */
	protected void selectRows(ArrayList selectedRows,boolean unSelectAll) throws UIException
	{	
		if (selectedRows != null && selectedRows.size() != 0)
		{
			HashMap selectedRowsMap = new HashMap();
			for (int index = 0;index < selectedRows.size();index++)
			{
				selectedRowsMap.put(selectedRows.get(index),"");
			}
			selectRows(selectedRowsMap,unSelectAll);
		}
	}
	
	/**
	 * unselects rows in the table
	 * @param unSelectedRowsIds list of row ids to mark as selected
	 */
	protected void unSelectRows(ArrayList unSelectedRows) throws UIException	
	{
		if (unSelectedRows != null)
		{
			HashMap unSelectedRowsMap = new HashMap();
			for (int index = 0;index < unSelectedRows.size();index++)
			{
				unSelectedRowsMap.put(unSelectedRows.get(index),"");
			}
			unSelectRows(unSelectedRowsMap);
		}		
	}
	
	protected void markRows(ArrayList selectedRows,ArrayList unSelectedRows) throws UIException
	{
		HashMap markedRowsMap = new HashMap();
		if (selectedRows != null)
		{
			for (int index = 0;index < selectedRows.size();index++)
			{
				markedRowsMap.put(selectedRows.get(index),new Boolean(true));
			}
		}
		if (unSelectedRows != null)
		{
			for (int index = 0;index < unSelectedRows.size();index++)
			{
				markedRowsMap.put(unSelectedRows.get(index),new Boolean(false));
			}
		}
		markRows(markedRowsMap);	
	}
	
	/**
	 * selects one row of the model,by the row's id
	 * @param rowId the row to select
	 */
	protected void selectRow(String rowId) throws UIException
	{
		HashMap selectedRows = new HashMap();
		selectedRows.put(rowId,"");
		selectRows(selectedRows,true);
	}	
	
	protected Event getCellClickEvent(String rowId,String cellId) throws UIException
	{
		Row row = getRow(rowId);
		int cellIndex = Integer.parseInt(cellId);
		Cell cell = row.getCell(cellIndex);
		Event cellEvent = cell.getCellClickEvent() != null ? cell.getCellClickEvent() : getColumn(cellIndex).getCellClickEvent();
		if (cellEvent == null)
		{
			cellEvent = getCellDefaultClickEvent();
		}
		return cellEvent;
	}

	protected Event getRowClickEvent(String rowId) throws UIException
	{
		Row row = getRow(rowId);
		return (row.getRowClickEvent() != null ? row.getRowClickEvent() : getRowDefaultClickEvent());
	}

	protected Event getRowDblClickEvent(String rowId) throws UIException
	{
		Row row = getRow(rowId);
		return (row.getRowDblClickEvent() != null ? row.getRowDblClickEvent() : getRowDefaultDblClickEvent());
	}
	
	protected CustomEvent getMenuItemClickEvent(String rowId,String menuItemIndex) throws UIException
	{
		Menu menu = getRow(rowId).getMenu();
		if (menu == null)
		{
			menu = defaultMenu;
		}
		return (menu.getMenuItem((Integer.parseInt(menuItemIndex))).getMenuItemClickEvent());
	}
	
	protected abstract void selectRows(HashMap selectedRows,boolean unSelectAll) throws UIException;	
	protected abstract void unSelectRows(HashMap unSelectedRows) throws UIException;
	protected abstract void markRows(HashMap markedRows) throws UIException;
			
	/**
	 * Returns {@link Row} from the model the by row's id
	 * @param rowId the id of the row
	 * @return the {@link Row} found
	 * @throws UIException if {@link Row} does not exist
	 */
	public abstract Row getRow(String rowId) throws UIException;
	
	/**
	 * Returns the list of the model's rows
	 * @return
	 */
	public abstract ArrayList getAllRows();
		
	/**
	 * Returns {@link Cell} object by the {@link Row} that contains it,<br>
	 * and by the name of the column that contains it.
	 * @param row {@link Row} that contains the cell 
	 * @param columnName the name of the column that contains the cell
	 * @return {@link Cell} object
	 * @throws UIException if the columnName does not exist in this model<br>
	 * or the row does not exist in this model. 
	 */
	public Cell getCell(Row row,String columnName) throws UIException
	{
		String columnIndex = (String)columnNames.get(columnName);
		if (columnIndex == null)
		{
			throw new UIException("column with the name " + columnName + " does not exit in the model");
		}
		return row.getCell(Integer.parseInt(columnIndex));
	}
		
	/**
	 * Returns {@link Cell} object by the {@link Row} that contains it,<br>
	 * and by the name of the column that contains it.
	 * @param rowId the id of the row that contains the cell 
	 * @param columnName the name of the column that contains the cell
	 * @return {@link Cell} object
	 * @throws UIException if the columnName does not exist in this model<br>
	 * or the row does not exist in this model. 
	 */
	public Cell getCell(String rowId,String columnName) throws UIException
	{
		String columnIndex = (String)columnNames.get(columnName);
		if (columnIndex == null)
		{
			throw new UIException("column with the name " + columnName + " does not exit in the model");
		}
		return getRow(rowId).getCell(Integer.parseInt(columnIndex));
	}
	
	/**
	 * Returns {@link Cell} object by the {@link Row} that contains it,<br>
	 * and by the index of the column that contains it.
	 * @param rowId the id of the row that contains the cell 
	 * @param columnIndex the index of the column that contains the cell
	 * @return {@link Cell} object
	 * @throws UIException if the columnIndex does not exist in this model<br>
	 * or the row does not exist in this model. 
	 */
	public Cell getCell(String rowId,int columnIndex) throws UIException
	{
		return getRow(rowId).getCell(columnIndex);
	}
	
	/**
	 * Retrieves the formatted value of data of a {@link Cell} object by the <br>
	 * cell's {@link Row} id and the cell's {@link Column} name.This value<br>
	 * depends on the dataType of the {@link Cell}, if the cell's data type was not<br>
	 * set the data type of the cell's (@link Column} is used.<br>
	 * It also depends on the maskKey of the {@link Cell}.if the cell's mask key<br>
	 * was not set the mask key of the cell's (@link Column} is used.
	 * @param rowId the id of the row that contains the cell 
	 * @param columnName the name of the column that contains the cell
	 * @param localizable 
	 * @return the formatted value of the cell's data.
	 * @throws UIException
	 */
	public String getCellFormattedData(String rowId,String columnName,LocalizedResources localizable) throws UIException
	{
		return getCellFormattedData(getCell(rowId,columnName),localizable);
	}


	/**
	 * Retrieves the formatted value of data of a {@link Cell} object by the <br>
	 * cell's {@link Row} id and the cell's {@link Column} index.This value<br>
	 * depends on the dataType of the {@link Cell}, if the cell's data type was not<br>
	 * set the data type of the cell's (@link Column} is used.<br>
	 * It also depends on the maskKey of the {@link Cell}.if the cell's mask key<br>
	 * was not set the mask key of the cell's (@link Column} is used.
	 * @param rowId the id of the row that contains the cell 
	 * @param columnIndex the index of the column that contains the cell
	 * @param localizable 
	 * @return the formatted value of the cell's data.
	 * @throws UIException
	 */
	public String getCellFormattedData(String rowId,int columnIndex,LocalizedResources localizable) throws UIException
	{
		return getCellFormattedData(getCell(rowId,columnIndex),localizable);
	}

	/**
	 * Retrieves the formatted value of data of a {@link Cell} object.This value<br>
	 * depends on the dataType of the {@link Cell}, if the cell's data type was not<br>
	 * set the data type of the cell's (@link Column} is used.<br>
	 * It also depends on the maskKey of the {@link Cell}.if the cell's mask key<br>
	 * was not set the mask key of the cell's (@link Column} is used.
	 * @param cell the {@link Cell} object {@link Cell},
	 * @param localizable 
	 * @return the formatted value of the cell's data.
	 * @throws UIException
	 */
	public String getCellFormattedData(Cell cell,LocalizedResources localizable) throws UIException
	{
		Column column = getColumn(Integer.parseInt(cell.getId()));
		String dataType = cell.getDataType() == null ? column.getDataType() : cell.getDataType();
		String dataMaskKey = cell.getDataMaskKey() == null ? column.getDataMaskKey() : cell.getDataMaskKey();
		return FlowerUIUtil.getFormattedValue(localizable,cell.getData(),dataType,dataMaskKey);
	}
	
	/**
	 * Retrieves the data type of (@link Cell} object in the model.<br>
	 * If the data type of the cell was not set,the data type of the cell's<br>
	 * (@link Column} is returned.
	 * @param cell the (@link Cell} object
	 * @return the correct data type of this cell.
	 * @throws UIException
	 */
	protected String getCellDataType(Cell cell) throws UIException
	{
		Column column = getColumnById(cell.getId());
		return cell.getDataType() == null ? column.getDataType() : cell.getDataType();
	}
	
	/**
	 * Returns the number of rows in this model
	 * @return number of rows in this model
	 */
	public abstract int getRowsCount();
	
	/**
	 * Adds a link to the model.
	 * @param customEvent the (@link CustomEvent} object
	 * @param caption the caption used when redering the link
	 */
	public void addTableLink(CustomEvent customEvent,String caption)
	{
		addTableLink(customEvent,caption,null);
	}
	
	/**
	 * Adds a link to the model.
	 * @param customEvent the (@link CustomEvent} object
	 * @param caption the caption used when redering the link
	 * @param cssClassName the name of the class used when redering the link
	 */
	public void addTableLink(CustomEvent customEvent,String caption,String cssClassName)
	{
		tableLinks.add(new TableLink(customEvent,caption,cssClassName));
	}
	
	/**
	 * Removes a link from the model 
	 * @param index the index of the link in the list
	 * @throws UIException if the link does not exist
	 */
	public void removeTableLink(int index) throws UIException
	{
		tableLinks.remove(getTableLink(index));
	}
	
	/**
	 * Removes all the links from the model
	 */
	public void removeAllTableLinks()
	{
		tableLinks.clear();
	}
	
	/**
	 * Returns the (@link CustomEvent} object of a link in the table
	 * @param index the index of the link in the table links list
	 * @return the (@link CustomEvent} object of the link
	 * @throws UIException if the index of the link is not found in the list
	 */
	public CustomEvent getTableLinkCustomEvent(int index) throws UIException
	{
		return (getTableLink(index).customEvent);
	}
	
	/**
	 * Returns the caption of a link in the table
	 * @param index the index of the link in the table links list
	 * @return the (@link CustomEvent} object of the link
	 * @throws UIException if the index of the link is not found in the list
	 */
	public String getTableLinkCaption(int index) throws UIException
	{
		return (getTableLink(index).caption);
	}
	
	/**
	 * Returns the caption of a link in the table
	 * @param index the index of the link in the table links list
	 * @return the (@link CustomEvent} object of the link
	 * @throws UIException if the index of the link is not found in the list
	 */
	public String getTableLinkCssClassName(int index) throws UIException
	{
		return (getTableLink(index).cssClassName);
	}
	
	private TableLink getTableLink(int index) throws UIException
	{
		TableLink te = (TableLink)tableLinks.get(index);
		if (te == null)
		{
			throw new UIException("table link in index " + index  + " do not exist");
		}
		return te;
		
	}
	
	/**
	 * Returns the number of links in this table model
	 * @return the number of links in this table model
	 */
	public int getTableLinksCount()
	{
		return tableLinks.size();
	}
	
	/**
	 * Returns the selectionType in this model which may be one of the following constants
	 * SELECTION_NONE - selection is not allowed
	 * SELECTION_SINGLE - only selection of one Row is allowed
	 * SELECTION_MULTIPLE - selection of multiple rows is allowed
	 * SELECTION_ALL - selection of multiple rows at the same time is allowed
	 * @return the selectionType in this model
	 */
	public int getSelectionType()
	{
		return selectionType;
	}

	/**
	 * Sets the selectionType of this model which may be one of the following constants
	 * SELECTION_NONE - selection is not allowed
	 * SELECTION_SINGLE - only selection of one Row is allowed
	 * SELECTION_MULTIPLE - selection of multiple rows is allowed
	 * SELECTION_ALL - selection of multiple rows at the same time is allowed
	 * @param selectionType the selectionType to set
	 */
	public void setSelectionType(int selectionType)
	{
		this.selectionType = selectionType;
	}
	
	/**
	 * Returns the footer(summary line) of this model
	 * @return footer of this model
	 */
	public String getSummary()
	{
		return summary;
	}
	
	/**
	 * returns the header of the table
	 * @return header of this table
	 */
	public String getHeader()
	{
		return header;
	}
	
	/**
	 * Sets the footer(summary line) of this model
	 * @param summary the summary to set
	 */
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	
	/**
	 * Sets the header of the table.
	 * @param header the header to set
	 */
	public void setHeader(String header)
	{
		this.header = header;
	}
	
	/**
	 * Returns the allowMenus property,which indicates if menus are allowed for
	 * this model.
	 * @return true if menus are allowed in this model
	 */
	public boolean isAllowMenus()
	{
		return allowMenus;
	}
	
	/**
	 * Sets the allowMenus property,if it is set to true menus are allowed in this model.
	 * Default value is false.
	 * @param allowMenus if true menus are allowed in this model
	 */
	public void setAllowMenus(boolean menuAllowed)
	{
		this.allowMenus = menuAllowed;
	}
	
	/**
	 * Returns the default Menu for the rows of this model.If no menu is set for 
	 * a row in the model,the default menu is set as the row's menu
	 * @return Menu the default menu for this model 
	 */
	public Menu getDefaultMenu()
	{
		return defaultMenu;
	}
	
	/**
	 * Sets the default Menu for the rows of this model
	 * @param defaultMenu the default Menu to set
	 */
	public void setDefaultMenu(Menu defaultMenu)
	{
		this.defaultMenu = defaultMenu;
	}
	
	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the default information about 
	 * click event on a row in the model. 
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getRowDefaultClickEvent() 
	{
		return rowDefaultClickEvent;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the default information about 
	 * double click event on a row in the model. 
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getRowDefaultDblClickEvent() 
	{
		return rowDefaultDblClickEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the default information about 
	 * double click event on a cell in the model 
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setRowDefaultClickEvent(Event event) 
	{
		rowDefaultClickEvent = event;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the default information about 
	 * click event on a cell in the model. 
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setRowDefaultDblClickEvent(Event event) 
	{
		rowDefaultDblClickEvent = event;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds 
	 * the default information about click event on a cell in the model. 
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getCellDefaultClickEvent() 
	{
		return cellDefaultClickEvent;
	}

	/**
	 * Sets the {@link com.ness.fw.ui.events.Event} object which holds the 
	 * default information about click event on a cell in the model.Relevant only if the cell
	 * is linkable and if no click event was set to the cell itself. 
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setCellDefaultClickEvent(Event event) 
	{
		cellDefaultClickEvent = event;
	}
	
	private class TableLink
	{
		CustomEvent customEvent;
		String caption;
		String cssClassName;
		TableLink(CustomEvent customEvent,String caption,String cssClassName)
		{
			this.customEvent = customEvent;
			this.caption = caption;
			this.cssClassName = cssClassName;	
		}
	}
}
