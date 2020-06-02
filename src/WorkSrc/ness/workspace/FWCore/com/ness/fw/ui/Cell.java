package com.ness.fw.ui;

import com.ness.fw.ui.events.Event;

public class Cell implements Comparable
{
	private String id;
	private String tooltip = "";
	private String className = "";
	private String linkClassName = "";	
	private String cssStyle = "";
	private boolean linkable = false;
	
	private Object data;
	private String dataMaskKey;
	private String dataType;
	private int order = 0;
		
	private Event cellClickEvent; 
	
	/**
	 * Constructs Cell with empty data and no tooltip
	 * @param data
	 */
	public Cell()
	{
		this("","");
	}
	
	/**
	 * Constructor for Cell
	 * @param data the data of the cell
	 */
	public Cell(Object data)
	{
		this(data,"");	
	}

	/**
	 * Constructor for Cell
	 * @param data the data of the cell
	 * @param tooltip the tooltip of the cell
	 */
	public Cell(Object data,String tooltip)
	{
		this.data = data;
		this.tooltip = tooltip;
	}
	
	
	/**
	 * Constructor that uses a Cell object for contructing another Cell object
	 * @param cell the cell that the constructor is using
	 */
	public Cell(Cell cell)
	{
		setData(cell.data);
		setTooltip(cell.tooltip);
		setLinkable(cell.linkable);
		setClassName(cell.className);
		setCssStyle(cssStyle);
	}
	
	/**
	 * Sets the Cells's id,this value will be set by the {@link Row} object that<br>
	 * contains the cell.
	 * @param id the new Cell's id
	 */
	protected void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * Sets the Cells's id,this value will be set by the {@link Row} object that<br>
	 * contains the cell.
	 * @param id the new Cell's id
	 */
	protected void setId(int id)
	{
		this.id = String.valueOf(id);
	}
	
	
	/**
	 * Returns the cells's Id
	 * @return String the cells's Id
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Sets the data of the cell.The Object may of type String,Number,Date or Boolean.
	 * @param data the new data of the cell
	 */
	public void setData(Object data)
	{
		this.data = data;
	}
	
	/**
	 * Returns the data of the cell.The Object may of type String,Number,Date or Boolean.
	 * @return the data of the cell
	 */
	public Object getData()
	{
		return data;
	}
		
	/**
	 * Sets the tooltip for this cell.
	 * @param tooltip the tooltip for this cell.
	 */
	public void setTooltip(String tooltip)
	{
		this.tooltip = tooltip;
	}
	
	/**
	 * Returns the tooltip for this cell.
	 * @return the tooltip for this cell.
	 */
	public String getTooltip()
	{
		return tooltip;
	}
	
	/**
	 * Returns true if links are allowed in the cell
	 * @return true if links are allowed in the cell
	 */
	public boolean isLinkable()
	{
		return linkable;
	}
	
	/**
	 * Sets the linkable property,if true links are allowed in the cell.
	 * @param linkable
	 */
	public void setLinkable(boolean linkable)
	{
		this.linkable = linkable;
	}
	
	/**
	 * Returns the css class name for this cell.
	 * @return the css class name for this cell.
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
	 * Returns the css style string of this cell.
	 * @return the css style string of this cell.
	 */
	public String getCssStyle()
	{
		return cssStyle;
	}
	
	/**
	 * Sets the cssStyle.
	 * @param cssStyle The cssStyle to set
	 */
	public void setCssStyle(String cssStyle)
	{
		this.cssStyle = cssStyle;
	}
		
	/**
	 * Returns the {@link com.ness.fw.ui.events.CustomEvent} object which holds the information <br>about 
	 * click event on this cell.Relevant only if this cell is linkable. 
	 * @return event object
	 */
	public Event getCellClickEvent() 
	{
		return cellClickEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.CustomEvent} object which holds the information <br>about 
	 * click event on this cell.
	 * @param event the event object
	 */
	public void setCellClickEvent(Event customEvent) 
	{
		cellClickEvent = customEvent;
	}

	/**
	 * Returns the css class name for cell that contains link
	 * @return the css class name for cell that contains link
	 */
	public String getLinkClassName() 
	{
		return linkClassName;
	}

	/**
	 * Sets the css class name for cell that contains link
	 * @param string the css class name for cell that contains link
	 */
	public void setLinkClassName(String string) 
	{
		linkClassName = string;
	}

	/**
	 * Returns the data type of the cell ,which may be one of 4 constans of {@link AbstractTableModel} <br>
	 * DATA_TYPE_STRING - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.String 
	 * DATA_TYPE_DATE - indicates that the object that was set by the method setData<br>
	 * is of type java.util.Date 
	 * DATA_TYPE_NUMBER - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Number 
	 * DATA_TYPE_BOOLEAN - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Boolean
	 * The dataType affects the way that the data of this cell will be formatted. 
	 * @return the dataType of this Cell.
	 */
	public String getDataType() 
	{
		return dataType;
	}

	/**
	 * Sets the data type of the cell ,which may be one of 4 constans of {@link AbstractTableModel} <br>
	 * DATA_TYPE_STRING - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.String 
	 * DATA_TYPE_DATE - indicates that the object that was set by the method setData<br>
	 * is of type java.util.Date 
	 * DATA_TYPE_NUMBER - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Number 
	 * DATA_TYPE_BOOLEAN - indicates that the object that was set by the method setData<br>
	 * is of type java.lang.Boolean
	 * The dataType affects the way that the data of this cell will be formatted. 
	 * @param dataType the data type of this cell
	 */
	public void setDataType(String dataType) 
	{
		this.dataType = dataType;
	}
	
	/**
	 * Returns the dataMaskKey of the cell,which is a string representing a mask<br>
	 * in a properties file.This mask affect  the way that the data of this cell will be formatted.
	 * @return the key of the data mask from a properties file.
	 */
	public String getDataMaskKey() 
	{
		return dataMaskKey;
	}

	/**
	 * Sets the dataMaskKey of the cell,which is a string representing a mask<br>
	 * in a properties file.This mask affect  the way that the data of this cell will be formatted.
	 * @param dataMaskKey the key of the data mask from a properties file.
	 */
	public void setDataMaskKey(String dataMaskKey) 
	{
		this.dataMaskKey = dataMaskKey;
	}
	
	/**
	 * Sets the order of the cell in the {@link Row}.
	 * @param order the order of the cell in the row.
	 */
	protected void setOrder(int order)
	{
		this.order = order;
	}
	
	/**
	 * Returns the order of the cell in the {@link Row}.
	 * @param order
	 */
	public int getOrder()
	{
		return order;
	}

	public int compareTo(Object o) 
	{
		Cell cell = (Cell)o;
		if (order > cell.getOrder())
		{
			return 1;
		}
		else if (order < cell.getOrder())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}	

}
