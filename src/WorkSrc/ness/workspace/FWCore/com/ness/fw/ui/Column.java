package com.ness.fw.ui;

import com.ness.fw.ui.events.Event;

public class Column implements Comparable
{
	/**
	 * Constant used for the sortType attribute for not allowing sorting
	 */
	public static final int SORT_NONE = UIConstants.COLUMN_SORT_NONE;

	/**
	 * Constant used for the sortType attribute for allowing only ascending sorting
	 */
	public static final int SORT_ASC = UIConstants.COLUMN_SORT_ASC;
	
	/**
	 * Constant used for the sortType attribute for allowing only descending sorting
	 */
	public static final int SORT_DESC = UIConstants.COLUMN_SORT_DESC;

	/**
	 * Constant used for the sortType attribute for allowing normal sorting
	 */
	public static final int SORT_NORMAL = UIConstants.COLUMN_SORT_NORMAL;
	
	private String id;
	private String name;
	private String header = "";
	private String footer = "";
	private String tooltip = "";
	private String width = "";
	private boolean sortable = false;
	private boolean filterable = false;
	private boolean linkable = false;
	private boolean overrideCellCss = false;
	private String className = "";
	private String linkClassName = "";
	private String cssStyle = "";
	private String cssHeaderClassName = "";
	private String cssHeaderStyle = "";	
	private String cssFooterClassName = "";
	private String cssFooterStyle = "";
	private int sortType = SORT_NORMAL;
	private boolean breakable = false;
	private String dataType = TableModel.DATA_TYPE_STRING;
	private String dataMaskKey;
	private boolean displayable = true;
	private boolean removable = true;
	private int order = 0;
	
	private Event columnClickEvent;
	private Event cellClickEvent;
	
	/**
	 * Constructs a Column in model
	 * @param header the header of the column
	 * @param sortable if true the column can be sorted
	 */
	public Column(String header,boolean sortable)
	{
		this.header = header;
		this.sortable = sortable;		
	}
	
	/**
	 * Constructs a Column in model
	 * @param name the name of the column
	 * @param header the header of the column
	 * @param sortable if true the column can be sorted
	 */
	public Column(String name,String header,boolean sortable)
	{
		this.name = name;
		this.header = header;
		this.sortable = sortable;
	}
	
	public Column(Column column)
	{
		setHeader(column.getHeader());
		setFooter(column.getFooter());
		setTooltip(column.getTooltip());
		setWidth(column.getWidth());
		setSortable(column.isSortable());
		setLinkable(column.isLinkable());
		setClassName(column.getClassName());
		setCssStyle(column.getCssStyle());
		setCssFooterClassName(column.getCssFooterClassName());
		setCssFooterStyle(column.getCssFooterStyle());
		setSortType(column.getSortType());
	}
	
	/**
	 * Returns the id of this column.
	 * @return the id of this column.
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Sets the id of this column,this value will be set by the model<br>
	 * that containd the column
	 * @param the new id of the column.
	 */
	protected void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the id of this column,this value will be set by the model<br>
	 * that containd the column
	 * @param the new id of the column.
	 */
	protected void setId(int id)
	{
		this.id = String.valueOf(id);
	}	
	
	/**
	 * Returns the tooltip of this column.
	 * @return the tooltip of this column.
	 */
	public String getTooltip()
	{
		return tooltip;
	}
	
	/**
	 * Sets the tooltip of this column
	 * @param the tooltip of this column
	 */
	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}
	
	/**
	 * Returns true if this column's cells may be sorted
	 * @return true if this column's cells may be sorted
	 */
	public boolean isSortable()
	{
		return sortable;
	}
	
	/**
	 * Sets the sortable property,if true this column's cells may be sorted
	 * @param sortable
	 */
	public void setSortable(boolean sortable)
	{
		this.sortable = sortable;
	}
		
	/**
	 * Returns the width.
	 * @return width of the column
	 */
	public String getWidth()
	{
		return width;
	}
	
	/**
	 * Sets the width.
	 * @param width The width to set
	 */
	public void setWidth(String width)
	{
		this.width = width;
	}
	/**
	 * Returns the footer of this column.
	 * @return the footer of this column.
	 */
	public String getFooter()
	{
		return footer;
	}
	/**
	 * Returns the header of this column.
	 * @return the header of this column.
	 */
	public String getHeader()
	{
		return header;
	}
	
	/**
	 * Returns the sortType.
	 * @return int
	 */
	public int getSortType()
	{
		return sortType;
	}
	
	/**
	 * Sets the footer of this column.
	 * @param footer the footer of this column.
	 */
	public void setFooter(String footer)
	{
		this.footer = footer;
	}
	/**
	 * Sets the header.
	 * @param header The header to set
	 */
	public void setHeader(String header)
	{
		this.header = header;
	}
	/**
	 * Sets the sortType.
	 * @param sortType The sortType to set
	 */
	public void setSortType(int sortType)
	{
		this.sortType = sortType;
	}

	/**
	 * Returns the css class name of this column.This class name will affect all<br>
	 * the cells of this column, if overrideCellsCss=true
	 * @return the css class name of this column
	 */
	public String getClassName()
	{
		return className;
	}
	
	/**
	 * Sets the className.
	 * @param className The className to set
	 */
	public void setClassName(String cssClassName)
	{
		this.className = cssClassName;
	}

	/**
	 * Returns the css style string of this column.This style will affect all<br>
	 * the cells of this column, if overrideCellsCss=true
	 * @return the css style string of this column
	 */
	public String getCssStyle()
	{
		return cssStyle;
	}
	
	/**
	 * Sets the css style string of this column.This style will affect all<br>
	 * the cells of this column, if overrideCellsCss=true
	 * @param cssStyle the css style string of this column
	 */
	public void setCssStyle(String cssStyle)
	{
		this.cssStyle = cssStyle;
	}
	
	/**
	 * Returns the css class name of the footer of this column.
	 * @return css class name of the footer of this column
	 */
	public String getCssFooterClassName()
	{
		return cssFooterClassName;
	}
	/**
	 * Returns css style string of the footer of this column
	 * @return css style string of the footer of this column
	 */
	public String getCssFooterStyle()
	{
		return cssFooterStyle;
	}
	/**
	 * Sets the cssFooterClassName.
	 * @param cssFooterClassName The cssFooterClassName to set
	 */
	public void setCssFooterClassName(String cssFooterClassName)
	{
		this.cssFooterClassName = cssFooterClassName;
	}
	/**
	 * Sets the cssFooterStyle.
	 * @param cssFooterStyle The cssFooterStyle to set
	 */
	public void setCssFooterStyle(String cssFooterStyle)
	{
		this.cssFooterStyle = cssFooterStyle;
	}
	/**
	 * Returns the linkable.
	 * @return boolean
	 */
	public boolean isLinkable()
	{
		return linkable;
	}
	/**
	 * Sets the linkable.
	 * @param linkable The linkable to set
	 */
	public void setLinkable(boolean linkable)
	{
		this.linkable = linkable;
	}

	/**
	 * @return
	 */
	public boolean isOverrideCellCss()
	{
		return overrideCellCss;
	}

	/**
	 * @param b
	 */
	public void setOverrideCellCss(boolean b)
	{
		overrideCellCss = b;
	}

	/**
	 * Returns css class name of the header of this column
	 * @return css style string of the footer of this column
	 */
	public String getCssHeaderClassName()
	{
		return cssHeaderClassName;
	}

	/**
	 * Returns css style string of the header of this column
	 * @return css style string of the header of this column
	 */
	public String getCssHeaderStyle()
	{
		return cssHeaderStyle;
	}

	/**
	 * @param string
	 */
	public void setCssHeaderClassName(String string)
	{
		cssHeaderClassName = string;
	}

	/**
	 * @param string
	 */
	public void setCssHeaderStyle(String string)
	{
		cssHeaderStyle = string;
	}

	/**
	 * @return
	 */
	public boolean isBreakable()
	{
		return breakable;
	}

	/**
	 * @param b
	 */
	public void setBreakable(boolean b)
	{
		breakable = b;
	}

	/**
	 * @return
	 */
	public String getLinkClassName() {
		return linkClassName;
	}

	/**
	 * @param string
	 */
	public void setLinkClassName(String string) {
		linkClassName = string;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the<br> information about 
	 * click event on all the cells that belong to this column.
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getCellClickEvent() 
	{
		return cellClickEvent;
	}

	/**
	 * Sets event object which holds the information about 
	 * click event on all the cells that belong to this column.Relevant
	 * only when the column is linkable and only if a cell does not
	 * have its own event object.
	 * @param event the event object
	 */
	public void setCellClickEvent(Event event) 
	{
		cellClickEvent = event;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * sort event in this column.
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getColumnClickEvent() 
	{
		return columnClickEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the <br> information about sort event 
	 * in this column.Relevant only if the column is sortable.
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public void setColumnClickEvent(Event event) 
	{
		columnClickEvent = event;
	}
	
	/**
	 * Returns the name of this column,used to identify the column in order<br>
	 * to get a column from a model.
	 * @return the name of this column.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Sets the name of this column,used to identify the column in order<br>
	 * to identify this column inside a model.This value will be set by the<br> 
	 * model that contains the column.
	 * @param name the name of the column
	 */
	protected void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getDataType() 
	{
		return dataType;
	}

	/**
	 * @param string
	 */
	public void setDataType(String string) 
	{
		dataType = string;
	}

	/**
	 * @return
	 */
	public String getDataMaskKey() 
	{
		return dataMaskKey;
	}

	/**
	 * @param string
	 */
	public void setDataMaskKey(String string) 
	{
		dataMaskKey = string;
	}
	
	/**
	 * Sets the displayable attribute which determines if this column should be rendered.
	 * @param displayable if true this column will be rendered
	 */
	public void setDisplayable(boolean displayable)
	{
		this.displayable = displayable;
	}
	
	/**
	 * Returns the displayable attribute which determines if this column should be rendered.
	 * @return the displayable attribute
	 */
	public boolean isDisplayable()
	{
		return displayable;
	}
	
	/**
	 * Sets the removable attribute which determines if this column may be removed from <br>
	 * the columns  list of its table. 
	 * @param removable if true this column may be removed from the columns display list of its table. 
	 */
	public void setRemovable(boolean removable)
	{
		this.removable = removable;
	}
	
	/**
	 * Returns the removable attribute which determines if this column may be removed from <br>
	 * the columns list of its table. 
	 * @return
	 */
	public boolean isRemovable()
	{
		return removable;
	}
	
	/**
	 * Sets the order of the column in the columns list of its table.
	 * @param order the order of the column in the columns list of its table.
	 */
	protected void setOrder(int order)
	{
		this.order = order;
	}
	
	/**
	 * Returns the order of the column in the columns list of its table.
	 * @param order
	 */
	public int getOrder()
	{
		return order;
	}

	public int compareTo(Object o) 
	{
		Column column = (Column)o;
		if (order > column.getOrder())
		{
			return 1;
		}
		else if (order < column.getOrder())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
