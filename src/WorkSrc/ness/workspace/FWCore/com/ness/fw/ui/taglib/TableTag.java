package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import com.ness.fw.common.exceptions.UIException;

public class TableTag extends AbstractTableTag
{
	protected boolean showPaging = false;
	protected int pagingControlType = 0;	
	protected boolean pagingControlCentered = false;
	
	protected String row1ClassName;
	protected String row2ClassName;
	protected String row1Style;
	protected String row2Style;
	
	protected String advancedSortLabelClassName;
	protected String advancedSortOrderClassName;
	protected String advancedSortMarkerClassName;
	
	protected String pagingBackground;
	protected String pagingTableClassName;
	protected String pagingButtonClassName;
	protected String pagingComboClassName;
	protected String pagingComboLabelClassName;
	protected String pagingLinkClassName;
	protected String pagingArrowsClassName;
	protected String pagingComboLabel;
	protected String pagingCurrentPageClassName;
	
	protected boolean allowAdvancedSort = true;
	
	protected static final String JS_PAGE_EVENT_FUNCTION = "page";
	protected static final String JS_SORT_EVENT = "sortEvent";
	protected static final String JS_OPEN_SORT_POPUP = "openSortPopup";
	protected static final String JS_OPEN_COLUMN_ORDER_POPUP = "openColumnOrderPopup";
	protected static final String JS_NEW_TABLE_COLUMN = "new TableColumn";
	protected static final String JS_NEW_TABLE_COLUMN_ORDER = "new TableColumnOrder";
	protected static final String JS_COLUMN_ARRAY_SUFFIX = "Columns";
	protected static final String JS_COLUMN_ORDER_ARRAY_SUFFIX = "ColumnsOrder";
	
	protected static final int PAGING_NEXT = 0;
	protected static final int PAGING_PREV = 1;
	protected static final int PAGING_TO = 2;
	protected static final int PAGING_FIRST = 3;
	protected static final int PAGING_LAST = 4;
	
	protected final static String FW_PAGING_EVENT = "paging";
	protected final static String FW_PAGING_NEXT_EVENT = "next";
	protected final static String FW_PAGING_PREV_EVENT = "prev";
	protected final static String FW_PAGING_LAST_EVENT = "last";
	protected final static String FW_PAGING_FIRST_EVENT = "first";
	protected final static String FW_PAGING_SPECIFIC_PAGE_EVENT = "specificPage";
	protected final static String FW_SORT_EVENT = "sort";
	protected final static String FW_COLUMN_ORDER_EVENT = "order";
		
	protected static final String HTML_TABLE_ATTRIBUTE_ROW_ID = "rid";
	
	private int totalPages = 1;
	private int startPagingRow = 0;
	private int endPagingRow = 0;
	private int startDisplayPagesFrom = 1;
	private int sortableColumnsNumber = 0;
	private int sortedColumnsNumber = 0;
		
	/*set model*/
	public void setModel(TableModel model)
	{
		this.model = model;
	}
	
	protected void initModel() throws UIException
	{
		if (model == null)
		{
			model = (TableModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);	
		}
		if (model == null)
		{
			throw new UIException("no table model exists in context's field " + id);
		}
		
		//initialize the state of this table tag
		initState();
		
		//initialize tag properties
		initTagProperties();		
	}
	
	protected void renderStartTag() throws UIException
	{
		initCss();
		initRowsLimit();
		renderSortColumnsArray();
		renderWrapperTable();
		renderHiddenField();	
		model.setAuthLevel(getAuthLevel());	
	}
	
	protected void resetTagState()
	{
		showPaging = false;
		pagingControlType = 0;	
		pagingControlCentered = false;
		row1ClassName = null;
		row2ClassName = null;
		row1Style = null;
		row2Style = null;
		pagingButtonClassName = null;
		pagingComboClassName = null;
		pagingLinkClassName = null;
		advancedSortLabelClassName = null;
		advancedSortOrderClassName = null;
		allowAdvancedSort = true;
		totalPages = 1;
		startPagingRow = 0;
		endPagingRow = 0;
		startDisplayPagesFrom = 1;
		sortableColumnsNumber = 0;
		sortedColumnsNumber = 0;		
		super.resetTagState();
	}
	
	protected void renderEndTag() 
	{
		
	}	
	
	/**
	 * Initializes style sheet properties
	 */
	protected void initCss()
	{
		super.initCss();
		row1ClassName = initUIProperty(row1ClassName,"ui.table.row1");
		row2ClassName = initUIProperty(row2ClassName,"ui.table.row2");

		pagingButtonClassName = initUIProperty(pagingButtonClassName,"ui.table.paging.button");
		pagingComboClassName = initUIProperty(pagingComboClassName,"ui.table.paging.combo");
		pagingComboLabel = getLocalizedText("ui.table.paging.comboLabel");
		pagingComboLabelClassName = initUIProperty(pagingComboLabelClassName,"ui.table.paging.combo.label");
		pagingLinkClassName = initUIProperty(pagingLinkClassName,"ui.table.paging.link");
		pagingArrowsClassName = initUIProperty(pagingArrowsClassName,"ui.table.paging.pagingArrows");
		pagingCurrentPageClassName = initUIProperty(pagingCurrentPageClassName,"ui.table.paging.currentPage");
		pagingTableClassName = initUIProperty(pagingTableClassName,"ui.table.paging.table");
		pagingBackground = initUIProperty(pagingBackground,"ui.table.paging.background"); 
		
		advancedSortLabelClassName = initUIProperty(advancedSortLabelClassName,"ui.table.advancedSortLabel");
		advancedSortOrderClassName = initUIProperty(advancedSortOrderClassName,"ui.table.advancedSortMarker");
		advancedSortMarkerClassName = initUIProperty(advancedSortMarkerClassName,"ui.table.advancedSortMarker");
	}
	
	/**
	 * Initializing paging parameters
	 * @throws UIException
	 */
	protected void initRowsLimit() throws UIException
	{
		if (!model.isModelEmpty())
		{
			TableModel tableModel = (TableModel)model;
			if (tableModel.getPagingType().equals(UIConstants.TABLE_PAGING_TYPE_AUTO))
			{
				int pageNumber = tableModel.getSelectedPage();
				int rowsInPage = tableModel.getRowsInPage();
				if (rowsInPage == 0)
				{
					rowsInPage = tableModel.getRowsCount();
				}
				startPagingRow = (pageNumber - 1) * rowsInPage;
				endPagingRow = (startPagingRow + rowsInPage);
				if (endPagingRow > tableModel.getRowsCount())
				{
					endPagingRow = tableModel.getRowsCount();
				}
				totalPages = tableModel.getRowsCount() / rowsInPage;
				if (tableModel.getRowsCount() % rowsInPage > 0) 
				{
					totalPages++;
				}
				if (tableModel.getSelectedPage() > totalPages)
				{
					throw new UIException("selected page " + tableModel.getSelectedPage() + " is bigger than total pages " + tableModel.getTotalPages());	
				}
			}
			else
			{
				endPagingRow = tableModel.getRowsCount();
				totalPages = tableModel.getTotalPages(); 	
			}		
		}
	}
	
	/**
	 * Renders 2 javascript arrays:<br>
	 * One array holds the names and the ids<br>
	 * of the columns of this model,in order to use them for<br>
	 * sorting the model.<br>
	 * The second array holds the order of the columns in the model. 
	 */
	protected void renderSortColumnsArray() throws UIException
	{
		TableModel tableModel = (TableModel)model;
		StringBuffer columnsArray = new StringBuffer(200);
		StringBuffer columnsOrderArray = new StringBuffer(200);
		ArrayList columnsDisplayOrder = model.getColumnsDisplayOrder();
		for (int index = 0;index < columnsDisplayOrder.size();index++)
		{
			int currentColumnIndex = Integer.parseInt((String)columnsDisplayOrder.get(index));
			Column column = model.getColumn(currentColumnIndex);			
			String columnHeader = getFormattedJSValue(column.getHeader());
			int columnSortOrder = tableModel.getColumnSortOrder(column.getId());
			int columnSortDir = TableModel.TABLE_COLUMN_SORT_NONE;
			
			if (columnSortOrder != -1)
			{
				columnSortDir = tableModel.getColumnSortDirection(column.getId());
				sortedColumnsNumber++;
			}
			
			//if column is sortable and displayable ,add a TableColumn object to the javascript Columns array
			if (column.isSortable() && column.isDisplayable())
			{
				sortableColumnsNumber++;
				if (columnsArray.length() != 0)
				{
					columnsArray.append(COMMA);
				}
				//add to columnsArray StringBuffer
				columnsArray.append(getFunctionCall(
									JS_NEW_TABLE_COLUMN,
									getQuot(column.getId()) + COMMA + 
									getQuot(columnHeader) + COMMA +
									getQuot(String.valueOf(columnSortOrder)) + COMMA +
									getQuot(String.valueOf(columnSortDir)),
									false
									));
			}
			
			//add a TableColumnORder object to the javascript ColumnsOrder array
			if (columnsOrderArray.length() != 0)
			{
				columnsOrderArray.append(COMMA);
			}
			
			//add to columnsOrderArray StringBuffer
			columnsOrderArray.append(getFunctionCall(
								JS_NEW_TABLE_COLUMN_ORDER,
								getQuot(column.getId()) + COMMA + 
								getQuot(columnHeader) + COMMA +
								String.valueOf(column.getOrder()) + COMMA +
								String.valueOf(column.isDisplayable()) + COMMA +
								String.valueOf(column.isRemovable()),
								false
								));
		}	
		if (sortableColumnsNumber > 1 && allowAdvancedSort)
		{
			appendln();
			startTag(SCRIPT,true);
			addVariable(id + JS_COLUMN_ARRAY_SUFFIX,columnsArray.toString(),true);	
			endTag(SCRIPT);
			appendln();
		}
		
		if (allowColumnOrder)
		{
			startTag(SCRIPT,true);
			addVariable(id + JS_COLUMN_ORDER_ARRAY_SUFFIX,columnsOrderArray.toString(),true);
			endTag(SCRIPT);
			appendln();
		}
	}

	protected boolean isAdvancedSortRendered()
	{
		return allowAdvancedSort && model.getRowsCount() > 0 && sortableColumnsNumber > 1;
	}
	
	protected void renderRows() throws UIException
	{	
		TableModel tableModel = (TableModel) model;
		for (int rowIndex = startPagingRow; rowIndex < endPagingRow; rowIndex++)
		{
			Row row = tableModel.getRow(rowIndex);
			renderRow(row,rowIndex);
		}
	}
	
	protected void renderRow(Row row,int rowIndex) throws UIException
	{
		if (row.isSelected() && !row.isSelectable())
		{
			throw new UIException("row " + row.getId() + " in model " + id + " is not selectable");
		}
		String rowClassName = getRowClassName(row,rowIndex);
		String rowStyle = getRowStyle(row,rowIndex);	
		boolean isRowSelected = !model.isMultiple() && row.isSelected();
		startTag(ROW);
		addAttribute(ID,PREFIX_ROW_ID + id + "-" + row.getId());
		addAttribute(HTML_TABLE_ATTRIBUTE_ROW_ID,row.getId());
		if (isRowSelected)
		{
			renderStyles(cssPre + rowClassName + "Tr" + BLANK + selectedRowClassName, selectedRowStyle);
		}
		else
		{
			renderStyles(rowClassName + "Tr",rowStyle);
		}
		
		//render single selection
		renderSingleSelection(row,rowIndex == 0,rowClassName,rowStyle);
		
		//render extra data
		renderRowExtraData(row);
		endTag();
		appendln();
		
		//render type selection
		renderMultipleSelection(row,rowClassName);
		
		//render menu
		renderMenu(row,rowIndex == 0);
		
		//render cells
		ArrayList columnsDisplayOrder = model.getColumnsDisplayOrder();
		for (int cellIndex = 0;cellIndex < columnsDisplayOrder.size(); cellIndex++)
		{
			int currentCellIndex = Integer.parseInt((String)columnsDisplayOrder.get(cellIndex));
			Column column = model.getColumn(currentCellIndex);
			if (column.isDisplayable())
			{
				renderCell(row,currentCellIndex,rowClassName,column.getId().equals(lastColumnRendered),isRowSelected);
			}
		}
		for (int cellIndex = row.getCellsCount(); cellIndex < model.getColumnsCount(); cellIndex++)
		{
			renderEmptyCell();
		}
		endTag(ROW);
		appendln();
	}
	
	/**
	 * Renders cell(td) in a row(tr)
	 * @param row
	 * @param cellIndex
	 * @param className
	 * @param isLastCell
	 * @param isRowSelected
	 * @throws UIException
	 */
	protected void renderCell(Row row,int cellIndex,String className,boolean isLastCell,boolean isCellRowSelected) throws UIException
	{
		renderCellStart(row,cellIndex,className,isLastCell,isCellRowSelected);
		renderCellEnd(row,cellIndex,isCellRowSelected,true);
	}	
	
	/**
	 * returns row's css class name
	 * @param row the row to get its class name
	 * @param rowIndex the index of the row in the model
	 * @return the row's css class name
	 */	
	protected String getRowClassName(Row row,int rowIndex)
	{
		if (rowIndex % 2 == 0)
		{
			if (!row.getCssClassName1().equals(""))
			{
				return cssPre + row.getCssClassName1();
			}
			else
			{
				return cssPre + row1ClassName;
			}
		}
		else
		{
			if (!row.getCssClassName2().equals(""))
			{
				return cssPre + row.getCssClassName2();
			}
			else
			{
				return cssPre + row2ClassName;
			}
		}
	}
	
	/**
	 * returns row's css style
	 * @param row the row to get its css style
	 * @param rowIndex the index of the row in the model
	 * @return the row's css style
	 */	
	protected String getRowStyle(Row row,int rowIndex)
	{
		if (rowIndex % 2 == 0)
		{
			if (!row.getCssStyle1().equals(""))
			{
				return row.getCssStyle1();
			}
			else
			{
				return row1Style;
			}
		}
		else
		{
			if (!row.getCssStyle2().equals(""))
			{
				return row.getCssStyle2();
			}
			else
			{
				return row2Style;
			}
		}
	}	
	
	/**
	 * renders one column header
	 */
	protected void renderColumn(Column column,int columnIndex) throws UIException
	{
		TableModel tableModel = (TableModel) model;
		boolean isSortAuthorized = isEventRendered(getColumnEvent(tableModel,column));
		int columnSortIndex = tableModel.getColumnSortOrder(column.getId());
		startTag(CELL);
		if (!column.getId().equals(lastColumnRendered))
		{
			renderCellWidth(columnIndex);
		}
		renderStyles(getColumnHeaderClassName(column),getColumnHeaderStyle(column));
		renderTooltip(column.getTooltip());
		if (isSortAuthorized && model.getRowsCount() > 0 && sortableColumnsNumber > 0)
		{
			renderSort(column,columnSortIndex);
		}
		endTag();
		if (!column.isBreakable()) startTag(NOBR,true);
		append(column.getHeader());
		if (isSortAuthorized && model.getRowsCount() > 0 && sortableColumnsNumber > 0 && column.getId() != null); 
		{
			if (columnSortIndex != -1)
			{
				renderSortSign(tableModel.getColumnSortDirection(column.getId()) == Column.SORT_DESC ? WEBDINGS_SYMBOL_ARROW_DOWN : WEBDINGS_SYMBOL_ARROW_UP,columnSortIndex);	
			}		
		}
		if (!column.isBreakable()) endTag(NOBR);
		endTag(CELL);
		appendln();
	}
	
	protected Event getColumnEvent(TableModel tableModel,Column column)
	{
		return (column.getColumnClickEvent() == null ? tableModel.getColumnDefaultClickEvent() : column.getColumnClickEvent());
	}
	
	/**
	 * Renders sort
	 * @param column
	 * @param columnSortIndex
	 */
	protected void renderSort(Column column,int columnSortIndex) throws UIException
	{
		TableModel tableModel = (TableModel) model;
		Event columnEvent = getColumnEvent(tableModel,column);
		if (column.isSortable() && column.getSortType() != Column.SORT_NONE)
		{
			int nextDirection = Column.SORT_DESC;
			if (!isDirectionAllowed(nextDirection,column.getSortType()))
			{
				nextDirection = Column.SORT_ASC;
			}
			if (columnSortIndex != -1)
			{
				nextDirection = tableModel.getColumnSortDirection(column.getId()) == Column.SORT_ASC ? Column.SORT_DESC : Column.SORT_ASC;
			}
			if (isDirectionAllowed(nextDirection,column.getSortType()))
			{
				addAttribute(ONCLICK);
				append(QUOT);
				renderJsCallSortEvent(column.getId(),String.valueOf(nextDirection),columnEvent);
				append(QUOT);
				addAttribute(STYLE,CURSOR_HAND);
			}
		}
	}
	
	private void renderSortSign(String sortSign,int columnSortIndex)
	{
		if (columnSortIndex != -1)
		{
			append(SPACE + SPACE + SPACE);
			startTag(FONT);
			addAttribute(STYLE,CURSOR_HAND);
			addAttribute(CLASS,advancedSortMarkerClassName);
			addAttribute(FACE,"Webdings");
			endTag();
			append(sortSign);
			endTag(FONT);
			if (sortedColumnsNumber > 1)
			{
				startTag(SPAN);
				addAttribute(CLASS,advancedSortOrderClassName);
				endTag();
				append("(" +(columnSortIndex + 1)+ ")");
				endTag(SPAN);
			}
		}
	}
	
	protected void renderAdvancedSort() throws UIException
	{
		startTag(CELL);
		addAttribute(CLASS,tableLinkClassName);
		addAttribute(NOWRAP,NOWRAP);
		endTag();
		startTag(SPAN);
		addAttribute(STYLE,CURSOR_HAND);
		addAttribute(ONCLICK);
		append(QUOT);
		renderAdvancedSortEvent();
		append(QUOT);
		endTag();
		append(getLocalizedText("ui.table.sort"));
		endTag(SPAN);
		endTag(CELL);
	}
	
	protected void renderAdvancedSortEvent() throws UIException
	{
		renderFunctionCall(JS_OPEN_SORT_POPUP,getSingleQuot(id) + COMMA + THIS,false);
	}
	

	protected void renderColumnOrder() throws UIException
	{
		startTag(CELL);
		addAttribute(CLASS,tableLinkClassName);
		addAttribute(NOWRAP,NOWRAP);
		endTag();
		startTag(SPAN);
		addAttribute(STYLE,CURSOR_HAND);
		addAttribute(ONCLICK);
		append(QUOT);
		renderColumnOrderEvent();
		append(QUOT);
		endTag();
		append(getLocalizedText("ui.table.columnOrder"));
		endTag(SPAN);
		endTag(CELL);
	}	
	
	protected void renderColumnOrderEvent() throws UIException
	{
		renderFunctionCall(JS_OPEN_COLUMN_ORDER_POPUP,getSingleQuot(id) + COMMA + THIS,false);
	}
	
	/*render end*/
	protected void renderAdditionalFeatures() throws UIException 
	{
		TableModel tableModel = (TableModel) model;
		//paging	
		if (showPaging || totalPages > 1 || tableModel.getPagingType().equals(UIConstants.TABLE_PAGING_TYPE_NORMAL) && pagingControlType > 0)
		{
			renderPaging(isEventRendered(tableModel.getPagingDefaultClickEvent()));
		}	
	}
	
	/***************render paging******************/
	private void renderPaging(boolean isAuthorized) throws UIException
	{
		boolean isAutoPaging = isAutoPaging();
		TableModel tableModel = (TableModel) model;
		append("<!--paging row!-->");
		appendln();
		startTag(ROW,true);
		startTag(CELL);
		if (pagingControlCentered)
		{
			addAttribute(ALIGN,CENTER);
		}
		addAttribute(CLASS,pagingBackground);
		endTag();
		startTag(TABLE);
		addAttribute(CLASS,pagingTableClassName);
		endTag();
		startTag(ROW,true);
		
		if (isFirstPagingButtonRendered())
		{
			startTag(CELL);
			addAttribute(CLASS,pagingArrowsClassName);
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_FIRST,1,1);
			append(QUOT);			
			endTag();
			append(getFirstPageSign()
			
			
			);
			endTag(CELL);
			startTag(CELL,true);
			startTag(SPAN);
			if (!isAuthorized || isFirstPagingButtonDisabled(tableModel.getSelectedPage(),isAutoPaging))
			{
				addAttribute(DISABLED,DISABLED);
			}
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_FIRST,1,1);
			append(QUOT);
			addAttribute(CLASS,pagingButtonClassName);
			endTag();
			append(getLocalizedText("ui.table.pagingButton.first"));
			endTag(SPAN);
			endTag(CELL);
			startTag(CELL,true);
			endTag(CELL);
		}
		
		if (isPrevPagingButtonRendered())
		{
			startTag(CELL);
			addAttribute(CLASS,pagingArrowsClassName);
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_PREV,tableModel.getSelectedPage() - 1 == 0 ? 1 : tableModel.getSelectedPage() - 1,tableModel.getStartPagesRange());
			append(QUOT);			
			endTag();
			append(getPrevPageSign());
			endTag(CELL);			
			startTag(CELL,true);
			startTag(SPAN);
			if (!isAuthorized || isPrevPagingButtonDisabled(tableModel.getSelectedPage(),isAutoPaging))
			{
				addAttribute(DISABLED,DISABLED);
			}
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_PREV,tableModel.getSelectedPage() - 1 == 0 ? 1 : tableModel.getSelectedPage() - 1,tableModel.getStartPagesRange());
			append(QUOT);
			addAttribute(CLASS,pagingButtonClassName);
			endTag();
			append(getLocalizedText("ui.table.pagingButton.prev"));
			endTag(SPAN);
			endTag(CELL);
		}
		
		if (isPagesLinksRendered())
		{
			startTag(CELL,true);
			startTag(TABLE);
			addAttribute(CELLPADDING,"0");
			addAttribute(CELLSPACING,"0");
			addAttribute(HEIGHT,"100%");
			endTag();
			startTag(ROW,true);
			renderPagesLinks(tableModel.getSelectedPage(),isAuthorized);
			endTag(ROW);
			endTag(TABLE);
			endTag(CELL);
		}
		
		if (isPagesComboRendered())
		{
			startTag(CELL);
			addAttribute(CLASS,pagingComboLabelClassName);
			endTag();
			append(pagingComboLabel);
			append(SPACE);
			endTag(CELL);
			startTag(CELL,true);
			startTag(SELECT);
			addAttribute(CLASS,pagingComboClassName);
			addAttribute(ID,"pageTo" + id);
			addAttribute(ONCHANGE);
			append(QUOT);
			renderJsCallPageEvent(PAGING_TO,0,tableModel.getStartPagesRange());
			append(QUOT);
			if (!isAuthorized)
			{
				addAttribute(DISABLED,DISABLED);
			}
			endTag();
			for (int index = 1; index <= totalPages; index++)
			{
				startTag(OPTION);
				addAttribute(VALUE, String.valueOf(index));
				if (tableModel.getSelectedPage() == index)
				{
					append(SELECTED);
				}
				endTag();
				append(String.valueOf(index));
				endTag(OPTION);
				appendln();
			}
			endTag(SELECT);
			endTag(CELL);
		}
		
		if (isNextPagingButtonRendered())
		{
			startTag(CELL,true);
			startTag(SPAN);
			if (!isAuthorized || isNextPagingButtonDisabled(tableModel.getSelectedPage(),isAutoPaging))
			{
				addAttribute(DISABLED,DISABLED);
			}
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_NEXT,tableModel.getSelectedPage() + 1 > totalPages ? totalPages : tableModel.getSelectedPage() + 1,tableModel.getStartPagesRange());
			append(QUOT);
			addAttribute(CLASS,pagingButtonClassName);
			endTag();
			append(getLocalizedText("ui.table.pagingButton.next"));
			endTag(SPAN);
			endTag(CELL);
			startTag(CELL);
			addAttribute(CLASS,pagingArrowsClassName);
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_NEXT,tableModel.getSelectedPage() + 1 > totalPages ? totalPages : tableModel.getSelectedPage() + 1,tableModel.getStartPagesRange());
			append(QUOT);			
			endTag();
			append(getNextPageSign());
			endTag(CELL);
		}
		
		if (isLastPagingButtonRendered())
		{
			startTag(CELL,true);
			startTag(SPAN);
			if (!isAuthorized || isLastPagingButtonDisabled(tableModel.getSelectedPage(),isAutoPaging))
			{
				addAttribute(DISABLED,DISABLED);
			}			
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_LAST,totalPages,totalPages - tableModel.getMaxPagesToDisplay() > 1 ? totalPages - tableModel.getMaxPagesToDisplay() : 1);
			append(QUOT);
			addAttribute(CLASS,pagingButtonClassName);
			endTag();
			append(getLocalizedText("ui.table.pagingButton.last"));
			endTag(SPAN);
			endTag(CELL);
			startTag(CELL);
			addAttribute(CLASS,pagingArrowsClassName);
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallPageEvent(PAGING_LAST,totalPages,totalPages - tableModel.getMaxPagesToDisplay() > 1 ? totalPages - tableModel.getMaxPagesToDisplay() : 1);
			append(QUOT);			
			endTag();
			append(getLastPageSign());
			endTag(CELL);			
			startTag(CELL,true);
			endTag(CELL);			
		}
		
		endTag(ROW);
		endTag(TABLE);
		endTag(CELL);
		endTag(ROW);
		append("<!--end paging row!-->");
	}
		
	private void renderPagesLinks(int selectedPage,boolean isAuthorized) throws UIException
	{
		TableModel tableModel = (TableModel)model;
		int startPagesRange = tableModel.getStartPagesRange();
		int endPagesRange = startPagesRange + tableModel.getMaxPagesToDisplay() - 1;
		if  (endPagesRange > totalPages)
		{
			endPagesRange = totalPages;
		}
		
		if (tableModel.getMaxPagesToDisplay() > 3)
		{
			if (selectedPage > endPagesRange)
			{
				endPagesRange = selectedPage;
				startPagesRange = endPagesRange - tableModel.getMaxPagesToDisplay() + 1;
			}
			else if (selectedPage < startPagesRange)
			{
				startPagesRange = selectedPage;
				endPagesRange = startPagesRange + tableModel.getMaxPagesToDisplay() - 1;
			}
			startTag(CELL,true);
			renderEndPageRange(selectedPage,startPagesRange,endPagesRange);
			endTag(CELL);
		}
		else
		{
			startPagesRange = 1;
			endPagesRange = totalPages;		
		}

		for (int index = endPagesRange; index >= startPagesRange; index--)
		{
			startTag(CELL);
			//current page
			if (index == selectedPage)
			{
				addAttribute(CLASS,pagingCurrentPageClassName);
			}
			//other page
			else
			{
				addAttribute(CLASS,pagingLinkClassName);
			}
			endTag();
			
			//current page
			if (index == selectedPage)
			{				
				append(String.valueOf(index));
			}
			//other page
			else
			{
				startTag(LINK);
				if (isAuthorized)
				{
					addAttribute(HREF);
					append(QUOT);
					append(JS_FUNCTION_CALL);
					renderJsCallPageEvent(PAGING_TO,index,tableModel.getStartPagesRange());
					append(QUOT);
				}
				endTag();
				append(String.valueOf(index));
				endTag(LINK);
			}
			endTag(CELL);
		}	
		
		if (tableModel.getMaxPagesToDisplay() > 3)
		{
			startTag(CELL,true);
			renderStartPageRange(selectedPage,startPagesRange,endPagesRange);	
			endTag(CELL);
		}
	}
	
	private void renderStartPageRange(int selectedPage,int startPagesRange,int endPagesRange) throws UIException
	{
		if (startPagesRange != 1)
		{
			int nextPage = selectedPage;
			if (selectedPage == endPagesRange)
			{
				nextPage--;
			}
			if (selectedPage == startPagesRange)
			{
				nextPage--;
			}			
			startTag(LINK);
			addAttribute(HREF);
			append(QUOT);
			append(JS_FUNCTION_CALL);
			renderJsCallPageEvent(PAGING_TO,nextPage,startPagesRange - 1);
			append(QUOT);
			addAttribute(CLASS,pagingLinkClassName);
			endTag();
			append("...");
			endTag(LINK);			
		}
	}

	private void renderEndPageRange(int selectedPage,int startPagesRange,int endPagesRange) throws UIException
	{
		if (endPagesRange != totalPages)
		{
			int nextPage = selectedPage;
			if (selectedPage == endPagesRange)
			{
				nextPage++;
			}
			if (selectedPage == startPagesRange)
			{
				nextPage++;
			}
			startTag(LINK);
			addAttribute(HREF);
			append(QUOT);
			append(JS_FUNCTION_CALL);
			renderJsCallPageEvent(PAGING_TO,nextPage,startPagesRange + 1);
			append(QUOT);
			addAttribute(CLASS,pagingLinkClassName);
			endTag();
			append("...");
			endTag(LINK);						
		}
	}
	
	protected boolean isAutoPaging()
	{
		return ((TableModel)model).getPagingType().equals(UIConstants.TABLE_PAGING_TYPE_AUTO);
	}
	
	private boolean isFirstPagingButtonRendered()
	{
		return (pagingControlType & UIConstants.PAGING_FIRSTLAST) > 0;
	}

	private boolean isFirstPagingButtonDisabled(int currentPage,boolean isAutoPaging)
	{
		if (isAutoPaging)
		{
			return (currentPage == 1);
		}
		else
		{
			return !((TableModel)model).isPagingFirstAllowed();
		}
	}
	
	private boolean isLastPagingButtonRendered()
	{
		return (pagingControlType & UIConstants.PAGING_FIRSTLAST) > 0;
	}
	
	private boolean isLastPagingButtonDisabled(int currentPage,boolean isAutoPaging)
	{
		if (isAutoPaging)
		{
			return (currentPage == totalPages);
		}
		else
		{
			return !((TableModel)model).isPagingLastAllowed();
		}
	}

	private boolean isNextPagingButtonRendered()
	{
		return (pagingControlType & UIConstants.PAGING_PREVNEXT) > 0;
	}
	
	private boolean isNextPagingButtonDisabled(int currentPage,boolean isAutoPaging)
	{
		if (isAutoPaging)
		{
			return currentPage >= totalPages;
		}
		else
		{
			return !((TableModel)model).isPagingNextAllowed();
		}
	}
	
	private boolean isPrevPagingButtonRendered()
	{
		return (pagingControlType & UIConstants.PAGING_PREVNEXT) > 0;
	}
	
	private boolean isPrevPagingButtonDisabled(int currentPage,boolean isAutoPaging)
	{
		if (isAutoPaging)
		{
			return currentPage == 1;
		}
		else
		{
			return !((TableModel)model).isPagingPrevAllowed();
		}
	}
	
	private boolean isPagesLinksRendered()
	{
		return (pagingControlType & UIConstants.PAGING_SPECIFIC_LINK) > 0 && totalPages > 1;
	}
	
	private boolean isPagesComboRendered()
	{
		return (pagingControlType & UIConstants.PAGING_SPECIFIC_COMBO) > 0 && totalPages > 1;
	}
		
	private boolean isDirectionAllowed(int direction, int columnSortType)
	{
		if (direction == Column.SORT_ASC)
		{
			return (columnSortType == Column.SORT_NORMAL || columnSortType == Column.SORT_ASC);
		}
		else if (direction == Column.SORT_DESC)
		{
			return (columnSortType == Column.SORT_NORMAL || columnSortType == Column.SORT_DESC);
		}
		return false;
	}
	
	protected void renderJsCallPageEvent(int pagingType,int pageNumber,int startPageRange) throws UIException
	{
		renderFunctionCall(JS_PAGE_EVENT_FUNCTION, pageNumber + COMMA + id + COMMA + ((TableModel)model).getPagingDefaultClickEvent().isCheckDirty());
	}
	protected void renderJsCallSortEvent(String columnId, String direction,Event columnEvent) throws UIException
	{
		renderFunctionCall(JS_SORT_EVENT, id + COMMA + columnId + COMMA + direction + COMMA + columnEvent.isCheckDirty() + COMMA + columnEvent.isCheckWarnings());
	}
	
	/***************setters and getters****************/
	/**
	 * Returns the pagingControlType.
	 * @return int
	 */
	public int getPagingControlType()
	{
		return pagingControlType;
	}
	/**
	 * Sets the pagingControlType.
	 * @param pagingControlType The pagingControlType to set
	 */
	public void setPagingControlType(int pagingType)
	{
		this.pagingControlType = pagingType;
	}
	/**
	 * Returns the row1ClassName.
	 * @return String
	 */
	public String getRow1ClassName()
	{
		return row1ClassName;
	}
	/**
	 * Returns the row1Style.
	 * @return String
	 */
	public String getRow1Style()
	{
		return row1Style;
	}
	/**
	 * Returns the row2ClassName.
	 * @return String
	 */
	public String getRow2ClassName()
	{
		return row2ClassName;
	}
	/**
	 * Returns the row2Style.
	 * @return String
	 */
	public String getRow2Style()
	{
		return row2Style;
	}
	/**
	 * Sets the row1ClassName.
	 * @param row1ClassName The row1ClassName to set
	 */
	public void setRow1ClassName(String row1ClassName)
	{
		this.row1ClassName = row1ClassName;
	}
	/**
	 * Sets the row1Style.
	 * @param row1Style The row1Style to set
	 */
	public void setRow1Style(String row1Style)
	{
		this.row1Style = row1Style;
	}
	/**
	 * Sets the row2ClassName.
	 * @param row2ClassName The row2ClassName to set
	 */
	public void setRow2ClassName(String row2ClassName)
	{
		this.row2ClassName = row2ClassName;
	}
	/**
	 * Sets the row2Style.
	 * @param row2Style The row2Style to set
	 */
	public void setRow2Style(String row2Style)
	{
		this.row2Style = row2Style;
	}
	/**
	 * @return
	 */
	public boolean isShowPaging()
	{
		return showPaging;
	}

	/**
	 * @param b
	 */
	public void setShowPaging(boolean b)
	{
		showPaging = b;
	}


	/**
	 * @return
	 */
	public String getPagingButtonClassName()
	{
		return pagingButtonClassName;
	}

	/**
	 * @return
	 */
	public String getPagingComboClassName()
	{
		return pagingComboClassName;
	}

	/**
	 * @return
	 */
	public String getPagingLinkClassName()
	{
		return pagingLinkClassName;
	}

	/**
	 * @param string
	 */
	public void setPagingButtonClassName(String string)
	{
		pagingButtonClassName = string;
	}

	/**
	 * @param string
	 */
	public void setPagingComboClassName(String string)
	{
		pagingComboClassName = string;
	}

	/**
	 * @param string
	 */
	public void setPagingLinkClassName(String string)
	{
		pagingLinkClassName = string;
	}

	/**
	 * @param i
	 */
	public void setTotalPages(int i)
	{
		totalPages = i;
	}

	/**
	 * @return
	 */
	public boolean isPagingControlCentered() {
		return pagingControlCentered;
	}

	/**
	 * @param b
	 */
	public void setPagingControlCentered(boolean b) {
		pagingControlCentered = b;
	}

	/**
	 * @param string
	 */
	public void setAdvancedSortLabelClassName(String string) {
		advancedSortLabelClassName = string;
	}

	/**
	 * @param string
	 */
	public void setAdvancedSortOrderClassName(String string) {
		advancedSortOrderClassName = string;
	}

	/**
	 * @param allowColumnOrder
	 */
	public void setAllowAdvancedSort(boolean allowAdvancedSort) 
	{
		this.allowAdvancedSort = allowAdvancedSort;
	}


}
