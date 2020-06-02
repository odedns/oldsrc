package com.ness.fw.ui.taglib;

import com.ness.fw.shared.ui.AuthorizedEventData;
import com.ness.fw.ui.*;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.ui.events.Event;
import com.ness.fw.common.exceptions.UIException;
import java.util.*;

public abstract class AbstractTableTag extends AbstractModelTag
{
	protected AbstractTableModel model;

	protected String mainTableClassName; 
	protected String tableClassName;
	protected String headerTableClassName;
	protected String bodyTableClassName;
	protected String footerTableClassName;

	protected String mainTableHeaderClassName;
	protected String mainTableFooterClassName;
	
	protected String rowClassNameHover;
	protected String selectedRowClassName;
	protected String selectedRowStyle;
	protected String rowStyleHover;
	protected String rowStyle;
	protected String columnClassName;
	protected String columnLinkClassName;
	protected String columnClassNameHover;
	protected String columnStyle;
	protected String columnStyleHover;
	protected String columnFooterClassName;
	protected String columnFooterStyle;
	protected String columnHeaderClassName;
	protected String columnHeaderStyle;
	protected String cellClassName = "";
	protected String cellLinkClassName;
	protected String cellLinkSelectedClassName;
	protected String cellClassNameHover;
	protected String cellStyle;
	protected String cellStyleHover;
	protected String menuClassName;
	protected String menuStyle;
	protected String menuItemClassName;
	protected String menuItemStyle;
	protected String headerClassName;
	protected String headerStyle;
	protected String summaryClassName;
	protected String summaryStyle;
	protected String menuImage;
	protected String menuCell;
	protected String tableLinkClassName;
	private String checkBoxClassName;

	protected String columnsWidth[];
	protected boolean submitOnRowSelection = false;
	protected boolean submitOnDblRowSelection = false;
	protected String onDblClick;
	protected boolean allowColumnOrder = false;

	protected boolean isMenuRendered = false;
	protected boolean isRowSelected = false;
	protected boolean isFooterRendered = false;
	protected String lastColumnRendered;
	protected int numberOfRenderedColumns;
	
	/*constants for js functions*/
	protected static String JS_OPEN_MENU = "createMenu";
	protected static String JS_CHECK_ALL_ROWS = "rowSelectAllEvent";
	protected static String JS_CHECK_ROW = "checkRow";
	protected static String JS_ROW_HOVER = "rowHover";
	protected static String JS_ROW_EVENT = "rowEvent";
	protected static String JS_ROW_SELECTED_EVENT = "rowSelectedEvent";
	protected static String JS_ROW_SELECTED_SUBMIT_EVENT = "rowSelectedSubmitEvent";
	
	protected static String JS_ROW_MULTIPLE_SELECTED_EVENT = "rowMultipleSelectedEvent";
	protected static String JS_ROW_SELECTED_STYLE = "rowSelectedStyle";
	protected static String JS_ROW_SELECTED_SCRIPT = "rowSelected";
	protected static String JS_SELECT_ROW = "selectRow";
	protected static String JS_CELL_EVENT = "cellEvent";
	protected static String JS_RESIZE = "resizeModel";

	protected final static String FW_ROW_EVENT = "selection";
	protected final static String FW_ROW_DBL_EVENT = "dblSelection";
	protected final static String FW_LINK_EVENT = "link";

	protected final static String CHECKBOX_ID = "Checkbox";

	protected final static String HTML_TABLE_ATTRIBUTE_COMPONENT_NAME = "tableComponent";
	protected final static String HTML_TABLE_ATTRIBUTE_SELECT_ALL_CHECKBOX = "SelectAll";
	protected final static String HTML_ROW_ATTRIBUTE_EXTRA_DATA = "ed";
	protected final static String PREFIX_ROW_ID = "row";
	protected final static String PREFIX_MODEL_ID = "tableModel";
	protected final static String PREFIX_MENU_ID = "Menu";
	
	protected final static String CSS_SELECTED_LINKED_CELL_SUFFIX = "Selected";
	protected final static String CSS_LAST_CELL_SUFFIX = "Last";
	
	protected final static String REMARK_COMPONENT = "table component";
	protected final static String REMARK_COMPONENT_END = "end table component";
	protected final static String REMARK_TITLE_TABLE = "title table";
	protected final static String REMARK_TITLE_TABLE_END = "end title table";
	protected final static String REMARK_MAIN_TABLE = "main table";
	protected final static String REMARK_MAIN_TABLE_END = "end main table";
	protected final static String REMARK_SUMMARY_TABLE = "summary table";
	protected final static String REMARK_SUMMARY_TABLE_END = "end summary table";
	protected final static String REMARK_MAIN_TABLE_MAIN_DIV = "main div of main table";
	protected final static String REMARK_MAIN_TABLE_MAIN_DIV_END = "end main div of main table";
	protected final static String REMARK_MAIN_TABLE_HEADER_DIV = "header div of main table";
	protected final static String REMARK_MAIN_TABLE_HEADER_DIV_END = "end header div of main table";
	protected final static String REMARK_MAIN_TABLE_BODY_DIV = "body div of main table";
	protected final static String REMARK_MAIN_TABLE_BODY_DIV_END = "end body div of main table";
	protected final static String REMARK_MAIN_TABLE_FOOTER_DIV = "footer div of main table";
	protected final static String REMARK_MAIN_TABLE_FOOTER_DIV_END = "end footer div of main table";
	
	
	public void setModel(AbstractTableModel model)
	{
		this.model = model;
	}

	/**
	 * Initializes css class names
	 */
	protected void initCss()
	{
		selectedRowClassName = initUIProperty(selectedRowClassName,"ui.table.row.selected");
		columnClassName = initUIProperty(columnClassName,"ui.table.column");
		columnFooterClassName = initUIProperty(columnFooterClassName,"ui.table.column.footer");
		columnHeaderClassName = initUIProperty(columnHeaderClassName,"ui.table.column.header");
		cellLinkClassName = initUIProperty(cellLinkClassName,"ui.table.cell.linked");
		cellLinkSelectedClassName = initUIProperty(cellLinkSelectedClassName,"ui.table.cell.linked.selected");
		menuClassName = initUIProperty(menuClassName,"ui.table.menu");
		menuItemClassName = initUIProperty(menuItemClassName,"ui.table.menu.item");
		headerClassName = initUIProperty(headerClassName,"ui.table.title");
		summaryClassName = initUIProperty(summaryClassName,"ui.table.summary");
		headerTableClassName = initUIProperty(headerTableClassName,"ui.table.header");
		footerTableClassName = initUIProperty(footerTableClassName,"ui.table.footer");
		bodyTableClassName = initUIProperty(bodyTableClassName,"ui.table.body");
		mainTableHeaderClassName = initUIProperty(mainTableHeaderClassName,"ui.table.headerTable");
		mainTableFooterClassName = initUIProperty(mainTableFooterClassName,"ui.table.footerTable");
		tableClassName = initUIProperty(tableClassName,"ui.table.innerTable");
		mainTableClassName = initUIProperty(mainTableClassName,"ui.table.mainTable");	
		tableLinkClassName = initUIProperty(tableLinkClassName,"ui.table.link");
		checkBoxClassName = initUIProperty(checkBoxClassName,"ui.table.row.checkAllRows");
		menuImage = initUIProperty(menuImage,"ui.table.menu.image");
		menuCell = initUIProperty(menuCell,"ui.table.menu.cell");
		
		//numberOfRenderedColumns = model.getColumnsCount();
		
		//Change the classes that take the locale's direction into consideration
		checkBoxClassName += getLocaleCssSuffix();
		menuCell += getLocaleCssSuffix();
	}

	/**
	 * 
	 */
	protected void setModelState()
	{
		if (model.getState() != null)
		{
			if (isStricterState(model.getState(),state))
			{
				state = model.getState();
			}			
		}			
	}
	
	/**
	 * 
	 * @throws UIException
	 */
	protected void initTagProperties() throws UIException
	{
		ArrayList columnsDisplayOrder = model.getColumnsDisplayOrder();		
		for (int index = 0;index < columnsDisplayOrder.size();index++)
		{
			int currentColumnIndex = Integer.parseInt((String)columnsDisplayOrder.get(index));
			Column column = model.getColumn(currentColumnIndex);			
			if (!column.getFooter().equals(""))
			{
				isFooterRendered = true;
			}
			if (column.isDisplayable())
			{
				lastColumnRendered = column.getId();
				numberOfRenderedColumns++;
			}
		}
	}
	
	
	/**
	 * Renders the wrapper table of the whole component
	 * @throws UIException
	 */
	protected void renderWrapperTable() throws UIException
	{
		boolean isAdvancedSortRendered = isAdvancedSortRendered();
		
		//addRemark(REMARK_COMPONENT);
		startTag(TABLE);
		addAttribute(ID,id);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(HEIGHT,"100%");
		renderComponentWrapperStyle();
		endTag();
		appendln();
		
		//title row
		if (model.getHeader() != null || model.getTableLinksCount() > 0 || isAdvancedSortRendered || allowColumnOrder)
		{
			startTag(ROW,true);
			startTag(CELL,true);
			renderTitleTable();
			endTag(CELL);
			endTag(ROW);
			appendln();
		}
						
		//main row
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(HEIGHT,"100%");
		endTag();
		renderMainTable();
		endTag(CELL);
		endTag(ROW);
		appendln();
		
		//summary row
		startTag(ROW,true);
		startTag(CELL,true);
		renderSummaryTable();
		endTag(CELL);
		endTag(ROW);
		appendln();
		
		//additional features row
		renderAdditionalFeatures();
		appendln();
		endTag(TABLE);
		appendln();
		//addRemark(REMARK_COMPONENT_END);
	}
	
	/**
	 * Renders the title of the whole component, which is a table that contains the<br>
	 * header attribute of the model and additional features like table's links,<br>
	 * link to advanced sort and link to columns ordering.
	 */
	private void renderTitleTable() throws UIException
	{
		//addRemark(REMARK_TITLE_TABLE);
		startTag(TABLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		endTag();
		startTag(ROW,true);
		appendln();
		
		//renders table's title
		renderHeaderCell();
		appendln();

		//renders title's links - sort,column ordering and table's links
		renderLinks();		
		
		endTag(ROW);
		appendln();
		endTag(TABLE);
		appendln();		
		//addRemark(REMARK_TITLE_TABLE_END);
	}
	
	
	/**
	 * Renders the links in the row of the table's title
	 */
	private void renderLinks() throws UIException
	{
		boolean isTableLinksRendered = model.getTableLinksCount() > 0;
		boolean isAdvancedSortRendered = isAdvancedSortRendered();
		boolean isColumnOrderRendered = isColumnOrderRendered();   
		if (isTableLinksRendered || isAdvancedSortRendered || isColumnOrderRendered)
		{
			startTag(CELL,true);
			appendln();
			startTag(TABLE);
			addAttribute(CELLPADDING,"0");
			addAttribute(CELLSPACING,"0");
			endTag();
			appendln();
			startTag(ROW,true);
			appendln();
			
			//render table's links
			if (isTableLinksRendered)
			{
				renderTableLinks();
				appendln();
			}

			//render advanced sort cell	
			if (isAdvancedSortRendered)
			{
				renderAdvancedSort();
				appendln();
			}

			//render column order 
			if (isColumnOrderRendered)
			{
				renderColumnOrder();
				appendln();
			}
			
			endTag(ROW);
			appendln();
			endTag(TABLE);
			appendln();
			endTag(CELL);
			appendln();
		}
	}
	
	/**
	 * Renders title - the model's header
	 */
	private void renderHeaderCell()
	{
		startTag(CELL);
		addAttribute(WIDTH,"100%");
		renderStyles(cssPre + headerClassName, headerStyle);
		endTag();
		append(model.getHeader() == null ? SPACE : model.getHeader());
		endTag(CELL);		
	}
	
	protected void renderTableLinks() throws UIException
	{
		for (int index = 0;index < model.getTableLinksCount();index++)
		{
			CustomEvent customEvent = new CustomEvent(model.getTableLinkCustomEvent(index));
			customEvent.setEventName(id + UIConstants.MODEL_PARAM_SEPERATOR + customEvent.getEventName());
			String caption = model.getTableLinkCaption(index);
			String cssClassName = model.getTableLinkCssClassName(index);
			if (cssClassName == null)
			{
				cssClassName = tableLinkClassName;
			}
			startTag(CELL);
			addAttribute(CLASS,cssClassName);
			addAttribute(NOWRAP,NOWRAP);
			endTag();
			startTag(SPAN);
			addAttribute(STYLE,CURSOR_HAND);
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallCustomEvent(customEvent);
			append(QUOT);
			endTag();
			append(getLocalizedText(caption));
			endTag(SPAN);			
			endTag(CELL);
		}
	}

	/**
	 * Renders advanced sort
	 */
	protected void renderAdvancedSort() throws UIException
	{
	}
	
	/**
	 * Renders column order
	 */
	protected void renderColumnOrder() throws UIException
	{	
	}
	
	/**
	 * Renders the summary of the whole component, which is a table that contains the<br>
	 * summary attribute of the model.
	 */
	private void renderSummaryTable() throws UIException
	{
		if (model.getSummary() != null)
		{
			//addRemark(REMARK_SUMMARY_TABLE);
			startTag(TABLE);
			addAttribute(CELLPADDING,"0");
			addAttribute(CELLSPACING,"0");
			addAttribute(WIDTH,"100%");
			endTag();		
			appendln();	
			startTag(ROW,true);
			appendln();
			startTag(CELL);
			renderStyles(cssPre + summaryClassName, summaryStyle);
			endTag();
			append(model.getSummary());
			endTag(CELL);
			appendln();
			endTag(ROW);
			appendln();
			endTag(TABLE);
			appendln();
			//addRemark(REMARK_SUMMARY_TABLE_END);
		}		
	}
	
	/**
	 * Render the main table of the component, which contains 3 parts:<br>
	 * The columns' headers<br>
	 * The rows of the table<br>
	 * The columns' footers
	 */
	protected void renderMainTable() throws UIException
	{
		//addRemark(REMARK_MAIN_TABLE);
		startTag(TABLE);
		addAttribute(CLASS, mainTableClassName);
		addAttribute(CELLPADDING, "0");
		addAttribute(CELLSPACING, "0");
		addAttribute(WIDTH,"100%");
		endTag();
		appendln();
		startTag(ROW,true);
		appendln();
		startTag(CELL);
		addAttribute(HEIGHT,"100%");
		endTag();
		renderMainTableMainDiv();				
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();		
		endTag(TABLE);
		appendln();
		//addRemark(REMARK_MAIN_TABLE_END);
	}
	
	/**
	 * Renders the main table's main div which contains 3 divs<br>
	 * <br>header
	 * <br>body(rows of the table)
	 * <br>footer
	 * @throws UIException
	 */
	protected void renderMainTableMainDiv() throws UIException
	{
		//addRemark(REMARK_MAIN_TABLE_MAIN_DIV);
		startTag(DIV);
		addAttribute(CLASS,tableClassName);
		addAttribute(ID,HTML_TABLE_ATTRIBUTE_COMPONENT_NAME);
		addAttribute("model",id);
		endTag();
		appendln();
		renderMainTableHeaderDiv();
		renderMainTableBodyDiv();
		renderMainTableFooterDiv();
		endTag(DIV);
		//addRemark(REMARK_MAIN_TABLE_MAIN_DIV_END);
		appendln();		
	}

	/**
	 * Renders the header div of the main table
	 * @throws UIException
	 */
	protected void renderMainTableHeaderDiv() throws UIException
	{
		//addRemark(REMARK_MAIN_TABLE_HEADER_DIV);
		startTag(DIV);
		addAttribute(CLASS, headerTableClassName);
		endTag();
		appendln();
		startTag(TABLE);
		addAttribute(CLASS,mainTableHeaderClassName);
		addAttribute(CELLSPACING, "0");
		addAttribute(CELLPADDING, "0");
		addAttribute(WIDTH, "100%");
		addAttribute(BORDER,"0");
		endTag();
		appendln();
		startTag(ROW, true);
		appendln();
		if (!model.isModelEmpty())
		{
			renderCheckAllCheckBox(true, true);
			renderFloatingMenuEmptyCell(true, true);
		}
		ArrayList columnsDisplayOrder = model.getColumnsDisplayOrder();
		for (int index = 0; index < columnsDisplayOrder.size(); index++)
		{
			int currentColumnIndex = Integer.parseInt((String)columnsDisplayOrder.get(index));
			Column column = model.getColumn(currentColumnIndex);			
			if (column.isDisplayable())
			{
				renderColumn(column,index);
			}
		}
		startTag(CELL);
		addAttribute(CLASS,columnHeaderClassName + CSS_LAST_CELL_SUFFIX);
		endTag();
		append(SPACE);
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();
		endTag(TABLE);
		appendln();
		endTag(DIV);
		appendln();		
		//addRemark(REMARK_MAIN_TABLE_HEADER_DIV_END);
	}
	
	/**
	 * Renders the body div of the main table
	 * @throws UIException
	 */
	protected void renderMainTableBodyDiv() throws UIException
	{
		//addRemark(REMARK_MAIN_TABLE_BODY_DIV);
		startTag(DIV);
		addAttribute(CLASS, bodyTableClassName);
		addAttribute(ID,id + "BodyTable");
		endTag();
		appendln();
		startTag(TABLE);
		addAttribute(CELLSPACING, "0");
		addAttribute(CELLPADDING, "0");
		addAttribute(WIDTH, "100%");
		addAttribute(BORDER,"0");
		endTag();
		appendln();
		if (model.isModelEmpty() || numberOfRenderedColumns == 0)
		{
			startTag(ROW,true);
			for (int index = 0;index < (numberOfRenderedColumns > 0 ? numberOfRenderedColumns : model.getColumnsCount());index++)
			{
				startTag(CELL);
				renderCellWidth(index);
				endTag();
				endTag(CELL);
			}
			endTag(ROW);
		}
		else
		{
			renderRows();
		}
		endTag(TABLE);
		appendln();
		endTag(DIV);
		appendln();	
		//addRemark(REMARK_MAIN_TABLE_BODY_DIV_END);		
	}
	
	/**
	 * Renders the footer div of the main table
	 * @throws UIException
	 */
	protected void renderMainTableFooterDiv() throws UIException
	{
		//addRemark(REMARK_MAIN_TABLE_FOOTER_DIV);
		startTag(DIV);
		addAttribute(CLASS, footerTableClassName);
		if (!isFooterRendered)
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}
		endTag();
		appendln();
		startTag(TABLE);
		addAttribute(CLASS,mainTableFooterClassName);
		addAttribute(CELLSPACING, "0");
		addAttribute(CELLPADDING, "0");
		addAttribute(WIDTH, "100%");
		addAttribute(BORDER,"0");
		endTag();
		appendln();
		startTag(ROW,true);
		appendln();
		if (!model.isModelEmpty())
		{
			renderCheckAllCheckBox(false, isFooterRendered);
			renderFloatingMenuEmptyCell(false, isFooterRendered);
		}
		for (int index = 0; index < model.getColumnsCount(); index++)
		{
			renderColumnFooter((Column) model.getColumn(index), isFooterRendered);
		}

		//last empty cell
		startTag(CELL);
		addAttribute(CLASS,columnHeaderClassName + CSS_LAST_CELL_SUFFIX);
		endTag();
		if (isFooterRendered)
		{
			append(SPACE);
		}
		endTag(CELL);
		appendln();
		endTag(ROW);
		appendln();
		endTag(TABLE);
		appendln();
		endTag(DIV);
		appendln();		
		//addRemark(REMARK_MAIN_TABLE_FOOTER_DIV_END);
	}
		
	/**
	 *	Renders "check all" checkbox 
	 */
	protected void renderCheckAllCheckBox(boolean isHeader, boolean isFooterRendered) throws UIException
	{
		if (numberOfRenderedColumns > 0 && model.getSelectionType() >= UIConstants.TABLE_SELECTION_MULTIPLE)
		{
			startTag(CELL);
			addAttribute(WIDTH,"13");
			addAttribute(CLASS,checkBoxClassName);
			endTag();
			if (!isHeader)
			{
				if (isFooterRendered)
				{
					append(SPACE);
				}
			}
			else
			{
				if (model.getSelectionType() == UIConstants.TABLE_SELECTION_ALL)
				{
					startTag(INPUT);
					addAttribute(TYPE, CHECKBOX);
					addAttribute(ID, HTML_TABLE_ATTRIBUTE_SELECT_ALL_CHECKBOX + id);
					addAttribute(STYLE,"width:13;height:13");
					addAttribute(ONCLICK);
					append(QUOT);
					renderJsCallCheckAllRows();
					append(QUOT);
					if (model.getSelectedRowsIds() != null && model.getRowsCount() == model.getSelectedRowsIds().size())
					{
						append(CHECKED);
					}
					renderSetDirtyProperty();
					endTag();
				}
				else if (model.getSelectionType() == UIConstants.TABLE_SELECTION_MULTIPLE)
				{
					append(SPACE);
				}
			}
			endTag(CELL);
		}
	}

	/**
	 * renders empty cell if floating menu allowed
	 */
	protected void renderFloatingMenuEmptyCell(boolean isHeader, boolean isFooterRendered)
	{
		if (numberOfRenderedColumns > 0 && model.isAllowMenus())
		{
			startTag(CELL);
			addAttribute(CLASS,menuCell);
			addAttribute(WIDTH,"1");
			endTag();
			if (isHeader || isFooterRendered)
			{
				startTag(IMAGE);
				addAttribute(SRC,getLocalizedImagesDir() + menuImage);
				endTag();
			}
			endTag(CELL);
			appendln();
		}
	}

	protected void renderEmptyRow()
	{
		startTag(ROW, true);
		for (int index = 0; index < model.getColumnsCount(); index++)
		{
			startTag(CELL,true);
			endTag(CELL);
		}
		endTag(ROW);
	}

	/**
	 * Renders column's footer 
	 * @param column
	 */
	protected void renderColumnFooter(Column column, boolean isFooterRendered)
	{
		if (column.getFooter() != null)
		{
			startTag(CELL);
			renderStyles(getColumnFooterClassName(column),getColumnFooterStyle(column));
			endTag();
			if (isFooterRendered)
			{
				if (column.getFooter().equals(""))
				{
					append(SPACE);
				}
				else
				{
					if (!column.isBreakable()) startTag(NOBR,true);
					append(column.getFooter());
					if (!column.isBreakable())endTag(NOBR);
				}
			}
			endTag(CELL);
		}
	}

	/**
	 * Renders the first part of the cell
	 * @param row
	 * @param cellIndex
	 * @param className
	 * @param isLastCell
	 * @param isRowSelected
	 * @throws UIException
	 */
	protected String renderCellStart(Row row,int cellIndex,String rowClassName,boolean isLastCell,boolean isCellRowSelected) throws UIException
	{
		Cell cell = row.getCell(cellIndex);
		String className = getCellClassName(cell,cellIndex,rowClassName);	
		//If the cell is the last one in the row,add a special constant 
		//suffix to its class.
		if (isLastCell)
		{
			className += CSS_LAST_CELL_SUFFIX;
		}
		//If the cell is not the last one,add a special locale's direction
		//suffix to its class 
		else
		{
			className += getLocaleCssSuffix();
		}
		if (isCellRowSelected)
		{
			className += BLANK + selectedRowClassName;
		}
		startTag(CELL);
		addAttribute(ID,String.valueOf(cellIndex));
		addAttribute(CLASS,className);
		renderTooltip(cell.getTooltip());
		if (!isLastCell)
		{
			renderCellWidth(cellIndex);
		}
		endTag();
		return className;		
	}
	
	/**
	 * Renders the last part of the cell
	 * @param row
	 * @param cellIndex
	 * @throws UIException
	 */
	protected void renderCellEnd(Row row,int cellIndex,boolean isCellRowSelected,boolean isStartNobrRendered) throws UIException
	{
		Column column = model.getColumn(cellIndex);		
		Cell cell = row.getCell(cellIndex);
		Event cellEvent = getCellClickEvent(cell,cellIndex);
		String formattedData = model.getCellFormattedData(cell,getLocalizable());
		renderCellData(row,column,cell,cellEvent,formattedData,isCellRowSelected);
		endTag(CELL);
		appendln();
	}	

	protected void renderCellData(Row row,Column column,Cell cell,Event cellEvent,String formattedData,boolean isCellRowSelected) throws UIException
	{
		//render data with link
		if (row.isLinkable() 
			&& (cell.isLinkable() || column.isLinkable())
			&& (isEventRendered(cellEvent))
			&& !formattedData.equals(""))
		{
			startTag(LINK);
			addAttribute(CLASS,isCellRowSelected ? cellLinkClassName + BLANK + cellLinkSelectedClassName : cellLinkClassName);
			addAttribute(ONCLICK);
			append(QUOT);
			append(CANCEL_BUBBLE + JS_END_OF_LINE);
			renderJsCallCellEvent(row,cell,cellEvent);
			append(QUOT);
			addAttribute(HREF,"#");
			endTag();
			if (!column.isBreakable()) startTag(NOBR,true);
			append(formattedData);
			if (!column.isBreakable()) endTag(NOBR);
			endTag(LINK);
		}
		
		//render data without link
		else
		{
			if (!column.isBreakable()) startTag(NOBR,true);
			append(!formattedData.equals("") ? formattedData : SPACE);
			if (!column.isBreakable()) endTag(NOBR);
		}
	}

	protected boolean isAdvancedSortRendered()
	{
		return false;
	}
	
	protected boolean isColumnOrderRendered()
	{
		return allowColumnOrder;
	}	

	protected abstract void renderRows() throws UIException;
	protected abstract void renderColumn(Column column,int columnIndex) throws UIException;
	protected abstract void renderAdditionalFeatures() throws UIException;

	protected void renderStyles(String className, String style)
	{
		if (className != null && !className.equals("") && !className.equals("null"))
		{
			addAttribute(CLASS, className);
		}
		if (style != null && !style.equals("") && !style.equals("null"))
		{
			addAttribute(STYLE, style);
		}
	}

	protected void renderTooltip(String tooltip)
	{
		if (tooltip != null && !tooltip.equals(""))
		{
			addAttribute(TITLE, tooltip);
		}
	}

	/**
	 * renders html of menu and its "open" image
	 * @param row the row than contains the menu
	 */
	protected void renderMenu(Row row, boolean isRenderMenu) throws UIException
	{
		Menu menu = null;
		if (numberOfRenderedColumns > 0 && model.isAllowMenus())
		{
			menu = (row.getMenu() != null ? row.getMenu() : model.getDefaultMenu());
			if (menu != null)
			{
				String menuStr = getMenuStr(menu,row.getId());
				//renders "open" image
				startTag(CELL);
				addAttribute(WIDTH,"10");
				addAttribute(CLASS,menuCell);
				endTag();
				if (menuImage != null)
				{
					startTag(IMAGE);
					addAttribute(SRC,getLocalizedImagesDir() + menuImage);
				}
				else
				{
					startTag(SPAN);
				}
				addAttribute(STYLE,CURSOR_HAND);
				addAttribute(ONCLICK);
				append(QUOT);
				append(CANCEL_BUBBLE);
				append(";");
				if (!menuStr.equals(""))
				{
					renderJsCallOpenMenu(row.getId(),menuStr,getMenuClassName(menu));
				}
				append(QUOT);
				endTag();
				if (menuImage == null)
				{
					append("+");
					endTag(SPAN);
				}
				endTag(CELL);
				appendln();
			}
			else
			{
				startTag(CELL);
				addAttribute(WIDTH, "10");
				endTag();
				append(SPACE);
				endTag(CELL);
				appendln();
			}
		}
	}

	/**
	 * returns a string representation of menu items of a menu in the following way:
	 * caption1-className1-event1|caption2-className2-event2
	 * @param menu menu of a row
	 * @return a string that contains caption,class name anf event of each menu item of a menu
	 */
	protected String getMenuStr(Menu menu,String rowId) throws UIException
	{
		StringBuffer sb = new StringBuffer(50);
		for (int index = 0; index < menu.getMenuItemsCounts(); index++)
		{
			String menuItemStr = getMenuItemStr(menu.getMenuItem(index),index,rowId);
			if (!menuItemStr.equals(""))
			{
				sb.append(menuItemStr + SEPERATOR);
			}		
		}
		//remove last SEPERATOR
		if (!sb.toString().equals(""))
		{
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * returns a string representation of a menu item in the following way:
	 * caption-className-event
	 * @param menuItem menu item of a menu
	 * @return a string that contains the caption,class name and event of a menu item
	 */
	protected String getMenuItemStr(MenuItem menuItem,int menuItemIndex,String rowId) throws UIException
	{
		CustomEvent event = menuItem.getMenuItemClickEvent();
		checkDirtyFlag(event);
		if (event == null)
		{
			throw new UIException("event of menu item number " + menuItemIndex + " in row id " + rowId + " is null in tag table id " + id);
		}
		else if (event.getEventName() == null)
		{
			throw new UIException("eventName of custom event of menu item number " + menuItemIndex + " in row id " + rowId + " is null in tag table id " + id);
		}
		else if (!isEventRendered(event))
		{ 
			return "";
		}	
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(event.getEventName(),event.getEventTargetType(),true,event.isCheckWarnings());
		String menuItemStr = menuItem.getCaption() + "-" + getMenuItemClassName(menuItem);
		menuItemStr += "-" + event.getEventName();
		menuItemStr += "-" + event.getEventTargetType();
		menuItemStr += "-" + menuItemIndex;
		menuItemStr += "-" + getEventDirtyFlagsAsString(event,false,"-");
		menuItemStr += "-" + authorizedEventData.getFlowPath();
		menuItemStr += "-" + authorizedEventData.getFlowStateName();
		menuItemStr += "-" + event.getWindowExtraParams();
		return menuItemStr;
	}

	protected void renderSingleSelection(Row row, boolean isFirstRow, String rowClassName, String rowStyle) throws UIException
	{
		if (model.isSelectionAllowed() && !model.isMultiple() && row.isSelectable())
		{
			renderSelectionScript(row, rowClassName, rowStyle);
			if (model.getSelectedRowId() != null && model.getSelectedRowId().equals(row.getId()))
			{
				isRowSelected = true;
				appendToEnd();
				startTag(SCRIPT,true);
				renderFunctionCall
				(
					JS_SELECT_ROW,
					id	+ COMMA + 
					PREFIX_ROW_ID + id + "-" + row.getId() + COMMA + 
					rowClassName + COMMA + 
					rowStyle
				);
				endTag(SCRIPT);
				appendToStart();
			}
		}
	}

	protected void renderMultipleSelection(Row row,String className)
	{
		if (numberOfRenderedColumns > 0 && model.isMultiple())
		{
			if (row.isSelectable())
			{
				startTag(CELL);
				addAttribute(WIDTH,"13");
				addAttribute(CLASS,checkBoxClassName);
				endTag();
				startTag(INPUT);
				addAttribute(ID,id + CHECKBOX_ID);
				addAttribute(VALUE,row.getId());
				addAttribute(TYPE,CHECKBOX);
				addAttribute(STYLE,"width:13;height:13");
				renderMultipleSelectionEvent(row);
				if (row.isSelected())
				{
					addAttribute(CHECKED);
				}
				renderSetDirtyProperty();
				endTag();
				endTag(CELL);
			}
			else
			{			
				startTag(CELL);
				addAttribute(WIDTH,"1");
				addAttribute(CLASS,checkBoxClassName);
				endTag();
				append(SPACE);
				endTag(CELL);
			}
			appendln();
		}
	}
	
	protected void renderMultipleSelectionEvent(Row row)
	{
		addAttribute(ONCLICK);
		append(QUOT);
		renderFunctionCall(JS_CHECK_ROW,id);
		append(";");
		renderFunctionCall
		(
			JS_ROW_MULTIPLE_SELECTED_EVENT,
			getSingleQuot(id) + COMMA + 
			getSingleQuot(row.getId()) + COMMA + 
			"this.checked",
			false
		);
		append(QUOT);		
	}

	protected void renderCellWidth(int cellIndex) throws UIException
	{
		if (!model.getColumn(cellIndex).getWidth().equals("")) 
		{
			addAttribute(WIDTH,model.getColumn(cellIndex).getWidth());
		}	
		else if(columnsWidth != null && columnsWidth[cellIndex] != null)
		{
			addAttribute(WIDTH,columnsWidth[cellIndex]);
		}
	}
	
	protected void resetTagState()
	{
		model = null;
		mainTableClassName = null; 
		tableClassName = null;
		headerTableClassName = null;
		bodyTableClassName = null;
		footerTableClassName = null;
		rowClassNameHover = null;
		selectedRowClassName = null;
		selectedRowStyle = null;
		rowStyleHover = null;
		rowStyle = null;
		columnClassName = null;
		columnLinkClassName = null;
		columnClassNameHover = null;
		columnStyle = null;
		columnStyleHover = null;
		columnFooterClassName = null;
		columnFooterStyle = null;
		columnHeaderClassName = null;
		columnHeaderStyle = null;
		cellClassName = "";
		cellLinkClassName = null;
		cellClassNameHover = null;
		cellStyle = null;
		cellStyleHover = null;
		menuClassName = null;
		menuStyle = null;
		menuItemClassName = null;
		menuItemStyle = null;
		headerClassName = null;
		headerStyle = null;
		summaryClassName = null;
		summaryStyle = null;
		menuImage = null;
		columnsWidth = null;
		submitOnRowSelection = false;
		submitOnDblRowSelection = false;
		onDblClick = null;
		isMenuRendered = false;
		isRowSelected = false;
		isFooterRendered = false;
		dirtable = false;
		lastColumnRendered = null;
		numberOfRenderedColumns = 0;
		super.resetTagState();
	}
		
	/**
	 * Returns the appropiate event for click on a cell.
	 * @param cell the cell.
	 * @param cellIndex the index of the cell in the row.
	 * @return Event object which contains the information about the event of
	 * the cell,including if events on this cell are authorizrd. 
	 * @throws UIException
	 */
	protected Event getCellClickEvent(Cell cell,int cellIndex) throws UIException
	{
		Event cellEvent = cell.getCellClickEvent() != null ? cell.getCellClickEvent() : model.getColumn(cellIndex).getCellClickEvent();
		if (cellEvent == null)
		{
			cellEvent = model.getCellDefaultClickEvent();
		}
		return cellEvent;
	}
	
	/**
	 * Returns the appropiate event for click on a row.This event is relevant only if
	 * the submitOnRowSelection attrobute is set to true.
	 * @param row the row.
	 * @return Event object which contains the information about the event of
	 * the row,including if events on this row are authorizrd. 
	 * @throws UIException
	 */
	protected Event getRowClickEvent(Row row)
	{
		return (row.getRowClickEvent() != null ? row.getRowClickEvent() : model.getRowDefaultClickEvent());
	}
	
	/**
	 * Returns the appropiate event for double click on a row.This event is relevant only if
	 * the submitOnDblRowSelection attrobute is set to true.
	 * @param row the row.
	 * @return Event object which contains the information about the event of
	 * the row,including if events on this row are authorizrd. 
	 * @throws UIException
	 */
	protected Event getRowDblClickEvent(Row row)
	{
		return (row.getRowDblClickEvent() != null ? row.getRowDblClickEvent() : model.getRowDefaultDblClickEvent());
	}

	/**
	 * Returns the cell's css class name.If 
	 * @param cell the cell to get its class name
	 * @param cellIndex the index of the cell in the row
	 * @return the cell's css class name
	 */
	protected String getCellClassName(Cell cell,int cellIndex,String rowClassName) throws UIException
	{
		Column column = model.getColumn(cellIndex);
		if (!cell.getClassName().equals(""))
		{
			return cell.getClassName();
		}
		else if (column.isOverrideCellCss())
		{
			return getColumnClassName(column);
		}
		else
		{
			return rowClassName;
		}
	}

	protected String getLinkedCellClassName(Cell cell, int cellIndex) throws UIException
	{
		Column column = model.getColumn(cellIndex);
		String className = !cell.getLinkClassName().equals("") ? cell.getLinkClassName() : cellLinkClassName;
		if (column.isOverrideCellCss() && className.equals(""))
		{
			className = getColumnLinkClassName(column);
		}
		return className;
	}

	protected String getCellStyle(Cell cell, int cellIndex) throws UIException
	{
		String style = !cell.getCssStyle().equals("") ? cell.getCssStyle() : cellStyle;
		if (model.getColumn(cellIndex).isOverrideCellCss() && (style == null || style.equals("")))
		{
			return getColumnStyle(model.getColumn(cellIndex));
		}
		else
		{
			return style;
		}
	}


	/**
	 * Returns the class name of an header in the main table.
	 * This class takes into consideration the locale's direction.
	 * @param column
	 * @return
	 */
	protected String getColumnHeaderClassName(Column column)
	{
		if (!column.getCssHeaderClassName().equals(""))
		{
			return cssPre + column.getCssHeaderClassName() + getLocaleCssSuffix();
		}
		else
		{
			return cssPre + columnHeaderClassName + getLocaleCssSuffix();
		}
	}
	
	/**
	 * Returns the class name of an footer in the main table.
	 * This class takes into consideration the locale's direction.
	 * @param column
	 * @return
	 */
	protected String getColumnFooterClassName(Column column)
	{
		if (!column.getCssFooterClassName().equals(""))
		{
			return cssPre + column.getCssFooterClassName()+ getLocaleCssSuffix();
		}
		else
		{
			return cssPre + columnFooterClassName + getLocaleCssSuffix();
		}
	}	

	protected String getColumnHeaderStyle(Column column)
	{
		if (!column.getCssHeaderStyle().equals(""))
		{
			return cssPre + column.getCssHeaderStyle();
		}
		else
		{
			return cssPre + columnHeaderStyle;
		}
	}
	
	protected String getColumnFooterStyle(Column column)
	{
		if (!column.getCssFooterStyle().equals(""))
		{
			return cssPre + column.getCssFooterStyle();
		}
		else
		{
			return cssPre + columnFooterStyle;
		}
	}	

	protected String getColumnClassName(Column column)
	{
		if (!column.getClassName().equals(""))
		{
			return cssPre + column.getClassName();
		}
		else
		{
			return cssPre + columnClassName;
		}
	}

	protected String getColumnLinkClassName(Column column)
	{
		if (!column.getLinkClassName().equals(""))
		{
			return cssPre + column.getLinkClassName();
		}
		else
		{
			return cssPre + columnLinkClassName;
		}
	}

	protected String getColumnStyle(Column column)
	{
		if (!column.getCssStyle().equals(""))
		{
			return column.getCssStyle();
		}
		else
		{
			return columnStyle;
		}
	}

	/**
	 * returns menu's css class name
	 * @param menu menu of a row
	 * @return menu's class name
	 */
	protected String getMenuClassName(Menu menu)
	{
		if (!menu.getCssClassName().equals(""))
		{
			return menu.getCssClassName();
		}
		else
		{
			return menuClassName;
		}
	}

	/**
	 * returns menu's css style
	 * @param menu menu of a row
	 * @return menu's css style
	 */
	protected String getMenuStyle(Menu menu)
	{
		if (!menu.getCssStyle().equals(""))
		{
			return menu.getCssStyle();
		}
		else
		{
			return menuStyle;
		}
	}

	/**
	 * returns menu item's css class name
	 * @param menuItem menu item of a menu
	 * @return menu item's class name
	 */
	protected String getMenuItemClassName(MenuItem menuItem)
	{
		return menuItem.getCssClassName() == null ? menuItemClassName : menuItem.getCssClassName();
	}

	/**
	 * returns menu item's css style
	 * @param menuItem menu item of a menu
	 * @return menu item's css style
	 */
	protected String getMenuItemStyle(MenuItem menuItem)
	{
		if (!menuItem.getCssStyle().equals(""))
		{
			return menuItem.getCssStyle();
		}
		else
		{
			return menuItemStyle;
		}
	}

	protected void renderSelectionScript(Row row, String className, String style) throws UIException
	{
		Event rowEvent = getRowClickEvent(row);
		Event dblRowEvent = getRowDblClickEvent(row);
		
		if (!submitOnDblRowSelection)
		{
			addAttribute(ONCLICK);
			append(QUOT);
			if (!submitOnRowSelection)
			{
				renderJsCallRowSelectionStyle(className, style);
				append(";");
				renderJsCallRowSelectionEvent(row);
			}
			else if (isEventRendered(rowEvent))
			{
				checkDirtyFlag(rowEvent);
				renderJsCallRowSelectionSubmitEvent(row,rowEvent);
			}
			append(QUOT);
		}
						
		if (onDblClick != null || submitOnDblRowSelection)
		{	
			addAttribute(ONDBLCLICK);
			append(QUOT);
			if (onDblClick != null)
			{
				append(onDblClick);
			}
			if (!submitOnDblRowSelection)
			{
				renderJsCallRowSelectionStyle(className, style);
				append(";");
			}
			else if (submitOnDblRowSelection && isEventRendered(dblRowEvent))
			{
				checkDirtyFlag(dblRowEvent);
				renderJsCallRowSelectionSubmitEvent(row,dblRowEvent);
			}
			append(QUOT);	
			addAttribute(ONCLICK);
			append(QUOT);
			renderJsCallRowSelectionStyle(className, style);
			append(";");
			renderJsCallRowSelectionEvent(row);				
			append(QUOT);
		}
	}

	protected void renderRowExtraData(Row row)
	{
		StringBuffer extraData = new StringBuffer(50);
		HashMap map = row.getExtraData();
		if (map != null && !map.isEmpty())
		{
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext())
			{
				String key = (String)iter.next();
				if (row.getExtraData(key) != null)
				{
					String value = getFormattedHtmlValue(row.getExtraData(key).toString());
					extraData.append(key + "-" + value + SEPERATOR);
				}
			}
			if (extraData.toString().length() > 1)
			{
				extraData.deleteCharAt(extraData.length() - 1);
			}
		}
		addAttribute(HTML_ROW_ATTRIBUTE_EXTRA_DATA, extraData.toString());
	}

	/*js functions calls*/
	protected void renderJsCallCellEvent(Row row, Cell cell,Event event) throws UIException
	{
		renderFunctionCall(JS_CELL_EVENT, id + COMMA + row.getId() + COMMA + cell.getId() + COMMA + event.isCheckDirty() + COMMA + event.isCheckWarnings());
	}

	protected void renderJsCallRowSelectionScript(Row row)
	{
		renderFunctionCall(JS_ROW_SELECTED_SCRIPT, row.isUpdateable() + COMMA + row.isDeletable());
	}
	
	protected void renderJsCallRowSelectionSubmitEvent(Row row,Event rowEvent) throws UIException
	{
		renderFunctionCall(JS_ROW_SELECTED_SUBMIT_EVENT, id + COMMA + row.getId() + COMMA + rowEvent.getEventTargetName() + COMMA +  rowEvent.isCheckDirty() + COMMA + dirtable + COMMA + rowEvent.isCheckWarnings());
	}
	
	protected void renderJsCallRowSelectionEvent(Row row)
	{
		renderFunctionCall
		(
			JS_ROW_SELECTED_EVENT,
			id + COMMA + 
			row.getId() + COMMA + 
			dirtable 
		);		
	}
	
	protected void renderJsCallRowSelectionStyle(String className, String style)
	{
		renderFunctionCall
		(
			JS_ROW_SELECTED_STYLE,
			getSingleQuot(id) + COMMA + 
			THIS + COMMA +
			getSingleQuot(selectedRowClassName) + COMMA + 
			getSingleQuot(className) + COMMA + 
			getSingleQuot(selectedRowStyle) + COMMA +
			getSingleQuot(style) + COMMA + 
			dirtable,
			false
		);
	}

	protected void renderJsCallOpenMenu(String rowId, String menuStr, String menuClassName)
	{
		renderFunctionCall(
			JS_OPEN_MENU,
			id + COMMA + rowId + COMMA + menuStr + COMMA + menuClassName + COMMA + false);
	}

	protected void renderJsCallCheckAllRows()
	{
		renderFunctionCall(JS_CHECK_ALL_ROWS, "'" + id + "',this.checked", false);
	}

	/*getters anf setters*/
	/**
	 * Returns the cellClassName.
	 * @return String
	 */
	public String getCellClassName()
	{
		return cellClassName;
	}
	/**
	 * Returns the cellClassNameHover.
	 * @return String
	 */
	public String getCellClassNameHover()
	{
		return cellClassNameHover;
	}
	/**
	 * Returns the cellStyle.
	 * @return String
	 */
	public String getCellStyle()
	{
		return cellStyle;
	}
	/**
	 * Returns the cellStyleHover.
	 * @return String
	 */
	public String getCellStyleHover()
	{
		return cellStyleHover;
	}
	/**
	 * Returns the columnClassName.
	 * @return String
	 */
	public String getColumnClassName()
	{
		return columnClassName;
	}
	/**
	 * Returns the columnClassNameHover.
	 * @return String
	 */
	public String getColumnClassNameHover()
	{
		return columnClassNameHover;
	}
	/**
	 * Returns the columnStyle.
	 * @return String
	 */
	public String getColumnStyle()
	{
		return columnStyle;
	}
	/**
	 * Returns the columnStyleHover.
	 * @return String
	 */
	public String getColumnStyleHover()
	{
		return columnStyleHover;
	}

	/**
	 * Returns the menuClassName.
	 * @return String
	 */
	public String getMenuClassName()
	{
		return menuClassName;
	}
	/**
	 * Returns the menuItemClassName.
	 * @return String
	 */
	public String getMenuItemClassName()
	{
		return menuItemClassName;
	}
	/**
	 * Returns the menuItemStyle.
	 * @return String
	 */
	public String getMenuItemStyle()
	{
		return menuItemStyle;
	}
	/**
	 * Returns the menuStyle.
	 * @return String
	 */
	public String getMenuStyle()
	{
		return menuStyle;
	}
	/**
	 * Returns the model.
	 * @return AbstractTableModel
	 */
	public AbstractTableModel getModel()
	{
		return model;
	}
	/**
	 * Returns the rowClassNameHover.
	 * @return String
	 */
	public String getRowClassNameHover()
	{
		return rowClassNameHover;
	}
	/**
	 * Returns the selectedRowClassName.
	 * @return String
	 */
	public String getSelectedRowClassName()
	{
		return selectedRowClassName;
	}
	/**
	 * Returns the rowStyle.
	 * @return String
	 */
	public String getRowStyle()
	{
		return rowStyle;
	}
	/**
	 * Returns the rowStyleHover.
	 * @return String
	 */
	public String getRowStyleHover()
	{
		return rowStyleHover;
	}
	/**
	 * Returns the selectedRowStyle.
	 * @return String
	 */
	public String getSelectedRowStyle()
	{
		return selectedRowStyle;
	}
	/**
	 * Returns the tableClassName.
	 * @return String
	 */
	public String getTableClassName()
	{
		return tableClassName;
	}

	/**
	 * Sets the cellClassName.
	 * @param cellClassName The cellClassName to set
	 */
	public void setCellClassName(String cellClassName)
	{
		this.cellClassName = cellClassName;
	}
	/**
	 * Sets the cellClassNameHover.
	 * @param cellClassNameHover The cellClassNameHover to set
	 */
	public void setCellClassNameHover(String cellClassNameHover)
	{
		this.cellClassNameHover = cellClassNameHover;
	}
	/**
	 * Sets the cellStyle.
	 * @param cellStyle The cellStyle to set
	 */
	public void setCellStyle(String cellStyle)
	{
		this.cellStyle = cellStyle;
	}
	/**
	 * Sets the cellStyleHover.
	 * @param cellStyleHover The cellStyleHover to set
	 */
	public void setCellStyleHover(String cellStyleHover)
	{
		this.cellStyleHover = cellStyleHover;
	}
	/**
	 * Sets the columnClassName.
	 * @param columnClassName The columnClassName to set
	 */
	public void setColumnClassName(String columnClassName)
	{
		this.columnClassName = columnClassName;
	}
	/**
	 * Sets the columnClassNameHover.
	 * @param columnClassNameHover The columnClassNameHover to set
	 */
	public void setColumnClassNameHover(String columnClassNameHover)
	{
		this.columnClassNameHover = columnClassNameHover;
	}
	/**
	 * Sets the columnStyle.
	 * @param columnStyle The columnStyle to set
	 */
	public void setColumnStyle(String columnStyle)
	{
		this.columnStyle = columnStyle;
	}
	/**
	 * Sets the columnStyleHover.
	 * @param columnStyleHover The columnStyleHover to set
	 */
	public void setColumnStyleHover(String columnStyleHover)
	{
		this.columnStyleHover = columnStyleHover;
	}

	/**
	 * Sets the menuClassName.
	 * @param menuClassName The menuClassName to set
	 */
	public void setMenuClassName(String menuClassName)
	{
		this.menuClassName = menuClassName;
	}
	/**
	 * Sets the menuItemClassName.
	 * @param menuItemClassName The menuItemClassName to set
	 */
	public void setMenuItemClassName(String menuItemClassName)
	{
		this.menuItemClassName = menuItemClassName;
	}
	/**
	 * Sets the menuItemStyle.
	 * @param menuItemStyle The menuItemStyle to set
	 */
	public void setMenuItemStyle(String menuItemStyle)
	{
		this.menuItemStyle = menuItemStyle;
	}
	/**
	 * Sets the menuStyle.
	 * @param menuStyle The menuStyle to set
	 */
	public void setMenuStyle(String menuStyle)
	{
		this.menuStyle = menuStyle;
	}

	/**
	 * Sets the rowClassNameHover.
	 * @param rowClassNameHover The rowClassNameHover to set
	 */
	public void setRowClassNameHover(String rowClassNameHover)
	{
		this.rowClassNameHover = rowClassNameHover;
	}
	/**
	 * Sets the selectedRowClassName.
	 * @param selectedRowClassName The selectedRowClassName to set
	 */
	public void setSelectedRowClassName(String rowClassNameSelected)
	{
		this.selectedRowClassName = rowClassNameSelected;
	}
	/**
	 * Sets the rowStyle.
	 * @param rowStyle The rowStyle to set
	 */
	public void setRowStyle(String rowStyle)
	{
		this.rowStyle = rowStyle;
	}
	/**
	 * Sets the rowStyleHover.
	 * @param rowStyleHover The rowStyleHover to set
	 */
	public void setRowStyleHover(String rowStyleHover)
	{
		this.rowStyleHover = rowStyleHover;
	}
	/**
	 * Sets the selectedRowStyle.
	 * @param selectedRowStyle The selectedRowStyle to set
	 */
	public void setSelectedRowStyle(String rowStyleSelected)
	{
		this.selectedRowStyle = rowStyleSelected;
	}
	/**
	 * Sets the tableClassName.
	 * @param tableClassName The tableClassName to set
	 */
	public void setTableClassName(String tableClassName)
	{
		this.tableClassName = tableClassName;
	}

	/**
	 * Returns the submitOnRowSelection.
	 * @return boolean
	 */
	public boolean isSubmitOnRowSelection()
	{
		return submitOnRowSelection;
	}
	/**
	 * Sets the submitOnRowSelection.
	 * @param submitOnRowSelection The submitOnRowSelection to set
	 */
	public void setSubmitOnRowSelection(boolean submitOnRowSelection)
	{
		this.submitOnRowSelection = submitOnRowSelection;
	}
	/**
	 * Returns the summaryClassName.
	 * @return String
	 */
	public String getSummaryClassName()
	{
		return summaryClassName;
	}
	/**
	 * Returns the summaryStyle.
	 * @return String
	 */
	public String getSummaryStyle()
	{
		return summaryStyle;
	}
	/**
	 * Returns the headerClassName.
	 * @return String
	 */
	public String getHeaderClassName()
	{
		return headerClassName;
	}
	/**
	 * Returns the headerStyle.
	 * @return String
	 */
	public String getHeaderStyle()
	{
		return headerStyle;
	}
	/**
	 * Sets the summaryClassName.
	 * @param summaryClassName The summaryClassName to set
	 */
	public void setSummaryClassName(String footerClassName)
	{
		this.summaryClassName = footerClassName;
	}
	/**
	 * Sets the summaryStyle.
	 * @param summaryStyle The summaryStyle to set
	 */
	public void setSummaryStyle(String footerStyle)
	{
		this.summaryStyle = footerStyle;
	}
	/**
	 * Sets the headerClassName.
	 * @param headerClassName The headerClassName to set
	 */
	public void setHeaderClassName(String headerClassName)
	{
		this.headerClassName = headerClassName;
	}
	/**
	 * Sets the headerStyle.
	 * @param headerStyle The headerStyle to set
	 */
	public void setHeaderStyle(String headerStyle)
	{
		this.headerStyle = headerStyle;
	}
	/**
	 * Returns the columnFooterClassName.
	 * @return String
	 */
	public String getColumnFooterClassName()
	{
		return columnFooterClassName;
	}
	/**
	 * Returns the columnFooterStyle.
	 * @return String
	 */
	public String getColumnFooterStyle()
	{
		return columnFooterStyle;
	}
	/**
	 * Sets the columnFooterClassName.
	 * @param columnFooterClassName The columnFooterClassName to set
	 */
	public void setColumnFooterClassName(String columnFooterClassName)
	{
		this.columnFooterClassName = columnFooterClassName;
	}
	/**
	 * Sets the columnFooterStyle.
	 * @param columnFooterStyle The columnFooterStyle to set
	 */
	public void setColumnFooterStyle(String columnFooterStyle)
	{
		this.columnFooterStyle = columnFooterStyle;
	}

	/**
	 * @return
	 */
	public String getColumnHeaderClassName()
	{
		return columnHeaderClassName;
	}

	/**
	 * @return
	 */
	public String getColumnHeaderStyle()
	{
		return columnHeaderStyle;
	}

	/**
	 * @param string
	 */
	public void setColumnHeaderClassName(String string)
	{
		columnHeaderClassName = string;
	}

	/**
	 * @param string
	 */
	public void setColumnHeaderStyle(String string)
	{
		columnHeaderStyle = string;
	}

	/**
	 * @return
	 */
	public String getMenuImage()
	{
		return menuImage;
	}

	/**
	 * @param string
	 */
	public void setMenuImage(String string)
	{
		menuImage = string;
	}

	/**
	 * @param strings
	 */
	public void setColumnsWidth(String[] strings)
	{
		columnsWidth = strings;
	}

	/**
	 * @return
	 */
	public String getOnDblClick() {
		return onDblClick;
	}

	/**
	 * @return
	 */
	public boolean isSubmitOnDblRowSelection() {
		return submitOnDblRowSelection;
	}

	/**
	 * @param string
	 */
	public void setOnDblClick(String string) {
		onDblClick = string;
	}

	/**
	 * @param b
	 */
	public void setSubmitOnDblRowSelection(boolean b) {
		submitOnDblRowSelection = b;
	}

	/**
	 * @return
	 */
	public String getCellLinkedClassName() 
	{
		return cellLinkClassName;
	}

	/**
	 * @param string
	 */
	public void setCellLinkedClassName(String string) 
	{
		cellLinkClassName = string;
	}

	/**
	 * @param allowColumnOrder
	 */
	public void setAllowColumnOrder(boolean allowColumnOrder) 
	{
		this.allowColumnOrder = allowColumnOrder;
	}
}
