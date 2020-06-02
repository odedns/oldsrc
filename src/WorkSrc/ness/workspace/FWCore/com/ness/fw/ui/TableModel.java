package com.ness.fw.ui;

import java.util.*;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.events.Event;


public class TableModel extends AbstractTableModel
{
	private static final String TABLE_MODEL_SORT_DIR_PROPERTY = "sortDir";
	private static final String TABLE_MODEL_SORT_COLUMN_PROPERTY = "sortCol";
	private static final String TABLE_MODEL_PAGE_START_RANGE_PROPERTY = "range";
	private static final String TABLE_MODEL_PAGE_NUMBER_PROPERTY = "page";
	
	private static final String TABLE_MODEL_SORT_EVENT_TYPE = "sort";
	private static final String TABLE_MODEL_PAGING_EVENT_TYPE = "paging";
	
	private ArrayList rows;
	
	private int totalPages = 1;
	private int maxPagesToDisplay = 0;
	private int rowsInPage = 0;
	private boolean pagingNextAllowed = true;
	private boolean pagingPrevAllowed = true;
	private boolean pagingFirstAllowed = true;
	private boolean pagingLastAllowed = true;
	private String pagingType = PAGING_TYPE_NORMAL;
	private String sortType = SORT_TYPE_AUTO;
	
	protected Event columnDefaultClickEvent;
	protected Event pagingDefaultClickEvent;	
	
	/**
	 * Constant for paging which is managed outside the model.
	 */
	public static final String PAGING_TYPE_NORMAL = UIConstants.TABLE_PAGING_TYPE_NORMAL;
	
	/**
	 * Constant for automatic paging managed by the model.
	 */
	public static final String PAGING_TYPE_AUTO = UIConstants.TABLE_PAGING_TYPE_AUTO;

	/**
	 * Constant for sort which is managed outside the model.
	 */
	public static final String SORT_TYPE_NORMAL = UIConstants.TABLE_SORT_TYPE_NORMAL;
	
	/**
	 * Constant for automatic sort managed by the model.
	 */
	public static final String SORT_TYPE_AUTO = UIConstants.TABLE_SORT_TYPE_AUTO;
	
	/**
	 * Constant used for column that is not sorted
	 */
	public static final int TABLE_COLUMN_SORT_NONE = UIConstants.TABLE_COLUMN_SORT_NONE;

	/**
	 * Constant used for column that is sorted in ascending direction.
	 */
	public static final int TABLE_COLUMN_SORT_ASC = UIConstants.TABLE_COLUMN_SORT_ASC;
	
	/**
	 * Constant used for column that is sorted in descending direction.
	 */
	public static final int TABLE_COLUMN_SORT_DESC = UIConstants.TABLE_COLUMN_SORT_DESC;
	
	/**
	 * Constructor for TableModel.
	 */
	public TableModel()
	{
		super();
		rows = new ArrayList();
		columnDefaultClickEvent = new Event();
		columnDefaultClickEvent.setEventType(Event.EVENT_TYPE_READONLY);
		pagingDefaultClickEvent = new Event();		
		pagingDefaultClickEvent.setEventType(Event.EVENT_TYPE_READONLY);
	}
	
	public TableModel(TableModel tableModel)
	{
		super(tableModel);
		rows = new ArrayList();
		setTotalPages(tableModel.getTotalPages());
		for (int index = 0; index < tableModel.getRowsCount(); index++)
		{
			addRow(new Row((Row) tableModel.rows.get(index)));
		}
	}
	
	/**
	 * handles TableModel events
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
			else if (eventType.equals(TABLE_MODEL_MENU_EVENT_TYPE))
			{
				handleMenuEvent(checkAuthorization);
				handleRowEvent(checkAuthorization);
			}
			else if (eventType.equals(TABLE_MODEL_SORT_EVENT_TYPE))
			{
				if (sortType.equals(SORT_TYPE_AUTO))
				{
					handleSortEvent(checkAuthorization);
				}
			}
			else if (eventType.equals(TABLE_MODEL_PAGING_EVENT_TYPE))
			{
				handlePagingEvent(checkAuthorization);
			}
			else if (eventType.equals(TABLE_MODEL_ROWS_EVENT_TYPE))
			{
				handleRowEvent(checkAuthorization);
			}			
			else if (eventType.equals(TABLE_MODEL_ROW_EVENT_TYPE))
			{				
				handleRowEvent(checkAuthorization);
			}
			else if (eventType.equals(TABLE_MODEL_COLUMN_ORDER_EVENT_TYPE))
			{
				handleColumnOrderEvent(checkAuthorization);
			}
		}
	}	
	
	protected void handleColumnOrderEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		handleRowEvent(checkAuthorization);
		ArrayList columnIds = getListProperty(TABLE_MODEL_COLUMN_ORDER_PROPERTY);
		boolean newSort = false;
		if (columnIds != null)
		{
			//setting the new list of displayed columns
			setColumnsDisplayOrder(columnIds);
			
			//change the list of ordered columns
			ArrayList sortedColumns = getSortedColumns();
			ArrayList removedSortedColumns = new ArrayList();
			if (sortedColumns != null)
			{		
				for (int index = 0;index < sortedColumns.size();index++)
				{
					String sortedColumn = (String)sortedColumns.get(index);
					if (!isColumnDisplayable(sortedColumn))
					{
						removedSortedColumns.add(sortedColumn);
						newSort = true;
					}
				}
			}
			//sort again if nessecery
			if (newSort)
			{
				removeSortedColumns(removedSortedColumns);
				sort();
			}
			
			//complete the list of ordered columns
			for (int index = 0;index < getColumns().size();index++)
			{
				Column column = getColumn(index);
				column.setDisplayable((isColumnDisplayable(column.getId())));
				if (!column.isDisplayable())
				{
					addColumnToColumnsDisplayOrder(column.getId());
				}
			}
		}
	}
	
	/**
	 * Handles sort event
	 * @throws UIException
	 */
	protected void handleSortEvent(boolean checkAuthorization) throws AuthorizationException, UIException
	{
		handleRowEvent(checkAuthorization);
		if (getSortedColumns() != null)
		{
			sort();
		}
	}
	
	/**
	 * Handles paging event
	 * @throws UIException
	 */
	protected void handlePagingEvent(boolean checkAuthorization) throws UIException, AuthorizationException
	{
		if (checkEventLegal(getPagingDefaultClickEvent(),checkAuthorization))
		{
			if (getStartPagesRange() == 0)
			{
				setStartPagesRange(1);
			}		
		}
		else
		{
			removeProperty(TABLE_MODEL_PAGE_START_RANGE_PROPERTY);
			removeProperty(TABLE_MODEL_PAGE_NUMBER_PROPERTY);
			removeEventType(TABLE_MODEL_PAGING_EVENT_TYPE);
		}
	}
			
	/**
	 * Sets number of current page
	 * @param pageNumber number of current page
	 */
	public void setSelectedPage(int pageNumber)
	{
		modelProperties.put(TABLE_MODEL_PAGE_NUMBER_PROPERTY,String.valueOf(pageNumber));
	}
	
	/**
	 * Sets the first page to display in the pages display bar.The total number of
	 * pages to display is set by the setMaxPagesToDisplay method.
	 * @param range the number of the first page to display.
	 */
	public void setStartPagesRange(int range)
	{
		modelProperties.put(TABLE_MODEL_PAGE_START_RANGE_PROPERTY,String.valueOf(range));
	}
	
	/**
	 * Returns ArrayList of sort directions of sorted columns.Each entry<br> 
	 * in the list cotain the following constants
	 * TABLE_COLUMN_SORT_ASC - the direction of the sorted column is ascending
	 * TABLE_COLUMN_SORT_DESC - the direction of the sorted column is descending
	 * @return list of sort directions of sorted columns
	 */	
	public ArrayList getSortedColumnsDirections()
	{
		return getListProperty(TABLE_MODEL_SORT_DIR_PROPERTY);
	}

	/**
	 * Sets list of sort directions of sorted columns.Each entry<br>
	 * in the list cotain the following constants
	 * TABLE_COLUMN_SORT_ASC - the direction of the sorted column is ascending
	 * TABLE_COLUMN_SORT_DESC - the direction of the sorted column is descending
	 * @param sortedColumnsDirections list of the directions of the sorted columns
	 */
	public void setSortedColumnsDirections(ArrayList sortedColumnsDirections)
	{
		setProperty(TABLE_MODEL_SORT_DIR_PROPERTY,sortedColumnsDirections);
	}

	/**
	 * Returns an {@link java.util.ArrayList} of ids of the sorted columns
	 * @return list of ids of the sorted columns(strings)
	 */
	public ArrayList getSortedColumns()
	{
		return getListProperty(TABLE_MODEL_SORT_COLUMN_PROPERTY);
	}
	
	/**
	 * Returns a list of the sorted columns names.The list relies on <br>
	 * the list of sorted columns ids.<br>
	 * All the columns in the list must have names ,if not a UIException is thrown
	 * @return  a list of the sorted columns names(string)
	 * @throws UIException if at least one of the columns in the sorted columns list<br>
	 * does not have a name.
	 */
	public ArrayList getSortedColumnsNames() throws UIException
	{
		ArrayList sortedColumnIds = getListProperty(TABLE_MODEL_SORT_COLUMN_PROPERTY);
		ArrayList sortedColumnNames = new ArrayList();
		if (sortedColumnIds != null)
		{
			for (int index = 0;index < sortedColumnIds.size();index++)
			{
				sortedColumnNames.add(getColumnNameByColumnId((String)sortedColumnIds.get(index)));
			}
		}
		return sortedColumnNames;
	}

	/**
	 * Sets the list of ids of the sorted columns.
	 * @param sortedColumnIds ArrayList of ids of sorted columns(string)
	 */
	public void setSortedColumns(ArrayList sortedColumnIds)
	{
		setProperty(TABLE_MODEL_SORT_COLUMN_PROPERTY,sortedColumnIds);
	}

	/**
	 * Sets the list of ids of the sorted columns by list of columns names.<br>
	 * If one of the names of the columns is not found in the model,a UIException is thrown
	 * @param sortedColumnsNames ArrayList of columns names(Strings)
	 * @throws UIException if a column whose name was in the sortedColumnsNames<br>
	 * does not exist in the model.<br>
	 */
	public void setSortedColumnByNames(ArrayList sortedColumnsNames) throws UIException
	{
		ArrayList sortedColumnIds = new ArrayList();
		for (int index = 0;index < sortedColumnsNames.size();index++)
		{
			sortedColumnIds.add(getColumnIdByColumnName((String)sortedColumnsNames.get(index)));
		}
		setSortedColumns(sortedColumnIds);
	}

	/**
	 * Returns the order of a column in the sort by its id 
	 * @param columnId the id of the column
	 * @return the order of the column in the sort or -1 if column is not in the sort.
	 */
	public int getColumnSortOrder(String columnId)
	{
		ArrayList sortedColumns = getSortedColumns();
		if (sortedColumns == null)
		{
			return -1;
		}
		return sortedColumns.indexOf(columnId);
	}
	
	/**
	 * Returns the order of a column in the sort by its name 
	 * @param columnName the name of the column
	 * @return the order of the column in the sort or -1 if column is not in the sort.
	 * @throws UIException if the column does not exist in the model.<br>
	 */
	public int getColumnNameSortOrder(String columnName) throws UIException
	{
		return (getColumnSortOrder(getColumnIdByColumnName(columnName)));
	}
	
	/**
	 * Returns the sort direction of a column in the sort or -1 by its id
	 * @param columnId the id of the column
	 * @return the sort direction of the column which may be one the following<br>
	 * from {@link UIConstants}<br>
	 * TABLE_COLUMN_SORT_ASC<br>
	 * TABLE_COLUMN_SORT_DESC<br>
	 * TABLE_COLUMN_SORT_NONE if the column is not in the list of sorted columns
	 */
	public int getColumnSortDirection(String columnId)
	{
		ArrayList sortedColumnsDir = getSortedColumnsDirections();
		ArrayList sortedColumns = getSortedColumns();
		if (sortedColumns != null && sortedColumnsDir != null)
		{
			int index = sortedColumns.indexOf(columnId);
			if (index == -1)
			{
				return TABLE_COLUMN_SORT_NONE;
			}
			else
			{
				return Integer.parseInt((String)sortedColumnsDir.get(index));
			}
		}
		return TABLE_COLUMN_SORT_NONE;
	}

	/**
	 * Returns the sort direction of a column in the sort or -1 by its name
	 * @param columnName the name of the column
	 * @return the sort direction of the column which may be one the following<br>
	 * from {@link UIConstants}<br>
	 * TABLE_COLUMN_SORT_ASC<br>
	 * TABLE_COLUMN_SORT_DESC<br>
	 * or -1 if the column is not in the list of sorted columns
	 */
	public int getColumnNameSortDirection(String columnName) throws UIException
	{
		return getColumnSortDirection(getColumnIdByColumnName(columnName));	
	}
	
	/**
	 * Removes column from the sorted columns list if the column exists in this list.
	 * @param columnId the id of the column to remove from the sorted columns list.
	 */
	protected void removeSortedColumn(String columnId)
	{
		ArrayList sortedColumnIds = getSortedColumns();
		if (sortedColumnIds != null)
		{
			int index = sortedColumnIds.indexOf(columnId);
			if (index != -1)
			{
				sortedColumnIds.remove(columnId);
				ArrayList sortedColumnDirections = getSortedColumnsDirections();
				sortedColumnDirections.remove(index);
			}
		}
	}
	
	/**
	 * Removes few columns from the sorted columns list
	 * @param columnIds
	 */
	protected void removeSortedColumns(ArrayList columnIds)
	{
		ArrayList sortedColumnIds = getSortedColumns();
		if (sortedColumnIds != null)
		{
			sortedColumnIds.removeAll(columnIds);
		}
	}
		
	/**
	 * Sets order for all the cells of a column in the model
	 * @param columnIndex the index of the column.All this column's cells will have the same order as the column. 
	 * @param order the order to set
	 */
	private void setColumnCellsOrder(int columnIndex,int order) throws UIException
	{
		for (int index = 0;index < rows.size();index++)
		{
			getRow(index).getCell(columnIndex).setOrder(order);
		}
	}
					
	/**
	 * Returns number of current page according to the last page number 
	 * and the paging type
	 * @return number of current page
	 */
	public int getSelectedPage()
	{
		String pageNumber = getStringProperty(TABLE_MODEL_PAGE_NUMBER_PROPERTY);
		if (pageNumber != null)
		{
			return Integer.parseInt((pageNumber));	
		}
		else 
		{
			return 1;
		}	
	}
	
	/**
	 * Returns the number of the first page to display in the pages display bar.
	 * @return The number of the first page displayed,or 1 if no value was set.
	 */
	public int getStartPagesRange()
	{
		String range = (String)modelProperties.get(TABLE_MODEL_PAGE_START_RANGE_PROPERTY);
		if (range != null)
		{
			return Integer.parseInt(range);
		}
		else
		{
			return 1;
		}
	}
	
	/**
	 * Sorts the rows of the model by the value that was set to the cells.
	 * @throws UIException
	 */
	public void sort() throws UIException
	{		
		if (getSortedColumns() != null)
		{
			SortModel sortModel = new SortModel();
			Collections.sort(rows,sortModel);
			if (sortModel.getSortException() != null)
			{
				throw sortModel.getSortException();
			}
		}
	}	
	
	/**
	 * Sorts the columns of the model by the order attribute of each {@link Column} object.
	 * @throws UIException
	 */
	protected void sortColumns() throws UIException
	{
		SortColumn sortColumn = new SortColumn();
		
		//set each cell's order number according to the cell's column
		for (int rowIndex = 0;rowIndex < getRowsCount();rowIndex++)
		{
			Row row = getRow(rowIndex);
			for (int cellIndex = 0;cellIndex < row.getCellsCount();cellIndex++)
			{
				Cell cell = row.getCell(cellIndex);
				Column column = getColumn(cellIndex);
				cell.setOrder(column.getOrder());
			}
			//sort the row accoring to the order attribute of each cell
			sortRow(row,sortColumn);
		}
		
		//sort columns 
		Collections.sort(getColumns(),sortColumn);
		
		//adjust ids of columns and cells
		adjustColumnIds();
		adjustCellIds();
	}
	
	protected void adjustColumnIds()
	{
		ArrayList sortedColumns = getSortedColumns();
		for (int index = 0;index < getColumns().size();index++)
		{
			Column column = (Column)getColumns().get(index);
			String oldId =  column.getId();
			
			//set column's new id
			column.setId(index);
			
			//fix column's id in the sorted columns list
			if (column.isDisplayable() && sortedColumns != null)
			{
				int sortedColumnsIndex = sortedColumns.indexOf(oldId);
				if (sortedColumnsIndex != -1)
				{
					sortedColumns.set(sortedColumnsIndex,column.getId());
				}
			}

			if (column.getName() != null)
			{
				columnNames.put(column.getName(),column.getId());
			}			
		}
	}	
	
	protected void adjustCellIds() throws UIException 
	{
		for (int index = 0;index < rows.size();index++)
		{
			getRow(index).adjustCellIds();
		}		
	}
	
	/**
	 * Sorts the cells of the {@link Row} by the order attribute of each {@link Cell} object.
	 * @throws UIException
	 */
	private void sortRow(Row row,SortColumn sortColumn) throws UIException
	{
		Collections.sort(row.getCells(),sortColumn);		
	}
	
	
	/**
	 * Marks row as selected in single rows model and sets it as the
	 * selected row of the tree,by the index of the row in the model. 
	 * @param rowIndex the index of the row in the model
	 * @throws UIException if row does not exist
	 */
	public void setSelectedRow(int rowIndex) throws UIException
	{
		Row row = getRow(rowIndex);
		setSelectedRow(row.getId()); 
	}
	
	/**
	 * Creates new {@link Row} object, initialize it with empty {@link Cell} objects.<br>
	 * The number of cells created is the number of columns in the table.
	 * @return the new {@link Row} object
	 */
	public Row createRow()
	{	
		Row row = new Row(getColumnsCount());
		addRow(row);
		return row;
	}
		
	/**
	 * Adds {@link Row} to rows list 
	 * @param row the {@link Row} to add
	 */
	public void addRow(Row row)
	{
		addRow(row,false);
	}

	/**
	 * Adds {@link Row} to rows list 
	 * @param row the {@link Row} to add
	 * @param isSelected if true the row is marked as selected
	 */
	public void addRow(Row row,boolean isSelected)
	{
		row.setId(String.valueOf(idCounter));
		idCounter++;
		row.setSelected(isSelected);
		if (row.isSelected())
		{
			if (isMultiple())
			{
				addSelectedRow(row);
			}
			else
			{	
				for (int index = 0; index < rows.size(); index++)
				{
					((Row)rows.get(index)).setSelected(false);
				}
				setProperty(TABLE_MODEL_SELECTED_ROW_PROPERTY,row.getId());	
			}
		}
		rows.add(row);
	}
		
	/**
	 * Returns row from rows list by row's index
	 * @param index the order of the row in the list
	 * @return the row found
	 * @throws UIException if index bigger than number of rows in the model 
	 */
	public Row getRow(int index) throws UIException
	{
		if (index > rows.size() - 1)
		{
			throw new UIException("row index " + index + " is bigger than the total number of rows " + rows.size());
		}
		return (Row)rows.get(index);
	}
	
	/**
	 * Returns {@link Row} from rows list by row's id
	 * @param id the id of the row
	 * @return the row found
	 * @throws UIException if {@link Row} does not exist
	 */
	public Row getRow(String rowId) throws UIException
	{
		int index = rows.indexOf(new Row(rowId));
		if (index != -1)
		{
			return (Row)rows.get(index);
		}
		else
		{
			throw new UIException("row does not exist");
		}
	}
			
	/**
	 * Returns {@link Cell} object by the index of the {@link Row} that contains it,<br>
	 * and by the name of the column that contains it.
	 * @param rowIndex the index of the row that contains the cell 
	 * @param columnName the name of the column that contains the cell
	 * @return {@link Cell} object
	 * @throws UIException if the columnName does not exist in this model<br>
	 * or the row does not exist in this model. 
	 */
	public Cell getCell(int rowIndex,String columnName) throws UIException
	{
		String columnIndex = (String)columnNames.get(columnName);
		if (columnIndex == null)
		{
			throw new UIException("column with the name " + columnName + " does not exit in the model");
		}
		return getRow(rowIndex).getCell(Integer.parseInt(columnIndex));
	}
	
	/**
	 * Returns {@link Cell} object by the index of the {@link Row} that contains it,<br>
	 * and by the index of the column that contains it.
	 * @param rowIndex the index of the row that contains the cell 
	 * @param columnIndex the index of the column that contains the cell
	 * @return {@link Cell} object
	 * @throws UIException if the columnIndex does not exist in this model<br>
	 * or the row does not exist in this model. 
	 */
	public Cell getCell(int rowIndex,int columnIndex) throws UIException
	{
		return getRow(rowIndex).getCell(columnIndex);
	}	
	
	/**
	 * Returns the number of rows in the table
	 * @return number of rows in the rable
	 */
	public int getRowsCount()
	{
		return rows.size();
	}
	
	/**
	 * Removes {@link Row} by row index
	 * @param index the index of the row to delete
	 * @throws UIException if index bigger than number of rows in the model
	 */
	public void removeRow(int index) throws UIException
	{
		if (index > rows.size() - 1)
		{
			throw new UIException("row index out of bound row number:" + index + " number of rows: " + rows.size());
		}
		setUnselectedRow(((Row)rows.get(index)).getId());
		rows.remove(index);
	}
	
	/**
	 * Removes {@link Row} by rowId
	 * @param rowId the id of the row to delete
	 * @throws UIException if row is not found
	 */
	public void removeRow(String rowId) throws UIException
	{
		setUnselectedRow(rowId);
		rows.remove(new Row(rowId));
	}
	
	/**
	 * Removes all rows from the table models
	 */
	public void removeAllRows()
	{
		rows.clear();
		setSelectedPage(1);
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
		return rows;
	}
	
	/**
	 * Selects rows in the table
	 * @param selectedRows hashmap of row ids to mark as selected
	 */
	protected void selectRows(HashMap selectedRows,boolean unSelectAll) throws UIException
	{
		if (!isMultiple() && selectedRows.size() > 1)
		{
			return;
		}
		for (int index = 0; index < rows.size(); index++)
		{
			if (selectedRows.get(getRow(index).getId()) != null)
			{
				getRow(index).setSelected(true);
			}
			else if (!isMultiple() || unSelectAll)
			{
				getRow(index).setSelected(false);
			}
		}
	}
	
	/**
	 * unselects rows in the table
	 * @param unSelectedRows hashmap of row ids to mark as unselected
	 */
	protected void unSelectRows(HashMap unSelectedRows) throws UIException
	{
		for (int index = 0; index < rows.size(); index++)
		{
			if (unSelectedRows.get(getRow(index).getId()) != null)
			{
				getRow(index).setSelected(false);
			}
		}
	}	
	
	/**
	 * Marks rows in the table as selected or unselected
	 * @param unSelectedRows hashmap of row ids to mark as unselected
	 */	
	protected void markRows(HashMap markedRows) throws UIException
	{
		for (int index = 0; index < rows.size(); index++)
		{
			Row row = getRow(index);
			row.setSelected(((Boolean)markedRows.get(row.getId())).booleanValue());
		}	
	}
				
	/**
	 * Removes all cells that belong to a specific column
	 * @param columnIndex the index of the column to remove all his cells
	 * @throws UIException if cell is not found in a row
	 */
	public void removeColumnCells(int columnIndex) throws UIException
	{
		for (int index = 0; index < rows.size(); index++)
		{
			Row row = getRow(index);
			if (columnIndex > row.getCellsCount())
			{
				throw new UIException("cell out of bound:" + columnIndex + " total number of cells is" + row.getCellsCount());
			}
			((Row) rows.get(index)).removeCell(columnIndex);
		}
	}
		
	protected Event getColumnEvent(String columnId) throws UIException
	{
		Column column = getColumn(Integer.parseInt(columnId));
		return (column.getColumnClickEvent() == null ? getColumnDefaultClickEvent() : column.getColumnClickEvent());
	}
		
	/**
	 * Returns the total pages in this model.If pagingType=PAGING_TYPE_AUTO
	 * this value depends on the value of rowInPage.
	 * @return the total pages in this model.
	 */
	public int getTotalPages()
	{
		return totalPages;
	}
	
	/**
	 * Sets the totalPages.Relevant when pagingType is set to PAGING_TYPE_NORMAL
	 * In PAGING_TYPE_AUTO the total number of pages is calculated by the model.
	 * @param totalPages The totalPages to set
	 */
	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
	}
	
	/**
	 * Checks if there are any rows in the model
	 * @return true if there are no rows in the model
	 */
	public boolean isModelEmpty()
	{
		return (rows == null || rows.size() == 0);
	}
	
	/**
	 * Returns the maximum number of rows to display in each page.Relevant when
	 * paging type is PAGING_TYPE_AUTO.
	 * @return maximum number of rows to display in each page.
	 */
	public int getRowsInPage()
	{
		return rowsInPage;
	}

	/**
	 * Sets the maximum number of rows to display in each page.
	 * @param rowInPage
	 */
	public void setRowsInPage(int rowInPage)
	{
		this.rowsInPage = rowInPage;
	}

	/**
	 * Returns the paging type of this model which may be PAGING_TYPE_NORMAL(default)<br>
	 * or PAGING_TYPE_AUTO.
	 * @return the paging type of this model
	 */
	public String getPagingType()
	{
		return pagingType;
	}

	/**
	 * Sets the paging type of this table model.The pagingType may be set to 
	 * one of 2 values : 
	 * PAGING_TYPE_NORMAL(default),when the paging is managed
	 * outside the model<br>
	 * PAGING_TYPE_AUTO when the paging is managed by the model.
	 * @param pagingType The pagingType of this model.
	 */
	public void setPagingType(String pagingType)
	{
		this.pagingType = pagingType;
	}

	/**
	 * Returns the sort type of this model which may be SORT_TYPE_NORMAL(default)<br>
	 * or SORT_TYPE_AUTO.
	 * @return the sort type of this model
	 */
	public String getSortType() 
	{
		return sortType;
	}

	/**
	 * Sets the sort type of this table model.The sortType may be set to 
	 * one of 2 values : 
	 * SORT_TYPE_NORMAL(default),when the sort is managed
	 * outside the model<br>
	 * SORT_TYPE_AUTO when the sort is managed by the model.
	 * @param sortType The sortType of this model.
	 */
	public void setSortType(String sortType) 
	{
		this.sortType = sortType;
	}


	/**
	 * Returns true if paging to last page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to last page is allowed.
	 */
	public boolean isPagingLastAllowed()
	{
		return pagingLastAllowed;
	}

	/**
	 * Returns true if paging to next page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to next page is allowed.
	 */
	public boolean isPagingNextAllowed()
	{
		return pagingNextAllowed;
	}

	/**
	 * Returns true if paging to previous page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to previous page is allowed.
	 */
	public boolean isPagingPrevAllowed()
	{
		return pagingPrevAllowed;
	}
	
	/**
	 * Returns true if paging to first page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to first page is allowed.
	 */
	public boolean isPagingFirstAllowed()
	{
		return pagingFirstAllowed;
	}
	
	/**
	 * Returns an {@link java.util.ArrayList} of rows in this model
	 * @return list of the rows in this model
	 */
	public ArrayList getRows()
	{
		return rows;
	}

	/** 
	 * Sets the pagingLastAllowed property.
	 * If true paging to last page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to last page is allowed.
	 */
	public void setPagingLastAllowed(boolean pagingLastAllowed)
	{
		this.pagingLastAllowed = pagingLastAllowed;
	}

	/** 
	 * Sets the pagingNextAllowed property.
	 * If true paging to next page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to next page is allowed.
	 */
	public void setPagingNextAllowed(boolean pagingNextAllowed)
	{
		this.pagingNextAllowed = pagingNextAllowed;
	}

	/**
	 * Sets the pagingPrevAllowed property.
	 * If true paging to previous page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to previous page is allowed.
	 */
	public void setPagingPrevAllowed(boolean pagingPrevAllowed)
	{
		this.pagingPrevAllowed = pagingPrevAllowed;
	}

	/**
	 * Sets the pagingFirstAllowed property.
	 * If true paging to first page is allowed in the current state of the model.
	 * Used only when the paging is managed outside the model(paginType=PAGING_TYPE_NORMAL)
	 * @return true if paging to first page is allowed.
	 */
	public void setPagingFirstAllowed(boolean pagingFirstAllowed)
	{
		this.pagingFirstAllowed = pagingFirstAllowed;
	}

	/**
	 * Sets new list of rows for this model
	 * @param list ArrayList of {@link Row} objects
	 */
	public void setRows(ArrayList list)
	{
		rows = list;
	}

	/**
	 * Returns the maximum number of pages to display in the pages display bar.
	 * @return the maximum number of pages to display in the pages display bar.
	 */
	public int getMaxPagesToDisplay()
	{
		return maxPagesToDisplay;
	}

	/**
	 * Sets the maximum number of pages to display in the pages display bar.
	 * @param maxPagesToDisplay the maximum number of pages to display in the pages display bar. 
	 */
	public void setMaxPagesToDisplay(int maxPagesToDisplay)
	{
		this.maxPagesToDisplay = maxPagesToDisplay;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which 
	 * holds the default information about sort event in this column.
	 * @return event the {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getColumnDefaultClickEvent() 
	{
		return columnDefaultClickEvent;
	}
	
	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object 
	 * which holds the  information about paging event in this column.
	 * @return event {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getPagingDefaultClickEvent() 
	{
		return pagingDefaultClickEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the default information about sort event 
	 * in this column.Relevant only if the column is sortable and if
	 * no event object was set to the column object.
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public void setColumnDefaultClickEvent(Event event) 
	{
		columnDefaultClickEvent = event;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the  information about page event 
	 * in this model.
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public void setPagingDefaultClickEvent(Event event) 
	{
		pagingDefaultClickEvent = event;
	}
	
	class SortColumn implements Comparator
	{
		public int compare(Object obj1, Object obj2) 
		{
			return ((Comparable)obj1).compareTo((Comparable)obj2);
		}	
	}
	
	class SortModel implements Comparator
	{
		private UIException uiException;
		public int compare(Object obj1, Object obj2)
		{
			ArrayList sortColumns = getSortedColumns();
			ArrayList sortOrder = getSortedColumnsDirections();
			int equality = 0;
			for (int i = 0; i < sortColumns.size() && equality == 0; i++) 
			{
				int order = Integer.parseInt((String)(sortOrder.get(i)));
				int colNum = Integer.parseInt((String)(sortColumns.get(i)));
				try
				{
					Cell c1 = ((Row) (obj1)).getCell(colNum);
					Cell c2 = ((Row) (obj2)).getCell(colNum);
					equality = getEquality(c1,c2) * order;
				}
				catch (UIException ui)
				{
					uiException = ui;
				}
			}
			return equality;
		}
		
		private int getEquality(Cell cell1,Cell cell2) throws UIException
		{
			String dataType1 = getCellDataType(cell1);
			String dataType2 = getCellDataType(cell2);
			if (!dataType1.equals(dataType2))
			{
				throw new UIException("data types of the same column do not match: " + dataType1 + " and " + dataType2 + " in column " + getColumnById(cell1.getId()).getId());
			}
			Object data1 = cell1.getData();
			Object data2 = cell2.getData();
			if (data1 == null)
			{
				return (data2 == null) ? 0 : 1;
			}
			else if (data2 == null)
			{
				return -1;
			}
			
			if (dataType1.equals(DATA_TYPE_BOOLEAN))
			{
				return data1.toString().compareTo(data2.toString());
			}
			else
			{
				try 
				{
					return ((Comparable)data1).compareTo(data2);
				}
				catch (ClassCastException cce)
				{
					throw new UIException(data1.getClass().getName() + " is not Comparable");
				}
			}
		}
		
		private UIException getSortException()
		{
			return uiException;
		}
	}
}
