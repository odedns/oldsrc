package com.ness.fw.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.events.Event;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Row
{
	private String id;
	private ArrayList cells;
	private Menu menu = null;
	private boolean selected = false;
	private boolean selectable = true;
	private boolean deletable = true;
	private boolean updateable = true;
	private boolean linkable = true;
	private HashMap extraData;
	private String cssClassName1 = "";
	private String cssStyle1 = "";
	private String cssClassName2 = "";
	private String cssStyle2 = "";
	private int idCounter = 0;

	private Event rowClickEvent;
	private Event rowDblClickEvent;
	
	/**
	 * Constructs Row with no cells.
	 */
	public Row()
	{
		cells = new ArrayList();
	}
	
	/**
	 * Constructs Row with cells.
	 * @param cellsNumber number of {@link Cell} to construct ib the Row.
	 */
	protected Row(int cellsNumber)
	{
		this();
		for (int index = 0;index < cellsNumber;index++)
		{
			addCell(new Cell());
		}
	}
	
	protected Row(String id)
	{
		this();
		this.id = id;
	}
	
	/**
	 * Constructor for Row from a Row object.
	 * @param row the row object
	 */
	public Row(Row row)
	{
		cells = new ArrayList();
		setSelected(row.selected);
		setSelectable(row.selectable);
		setDeletable(row.deletable);
		setUpdateable(row.updateable);
		setLinkable(row.linkable);
		setCssClassName1(row.cssClassName1);
		setCssStyle1(row.cssStyle1);
		setCssClassName2(row.cssClassName2);
		setCssStyle2(row.cssStyle2);
		if (row.getMenu() != null)
			setMenu(new Menu(row.getMenu()));
		for (int index = 0; index < row.getCellsCount(); index++)
		{
			addCell(new Cell((Cell) row.cells.get(index)));
		}
	}
	
	/**
	 * Returns the id of the row
	 * @return the id of the row
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Sets the id of the row,this value will be set by the model that holds<br>
	 * the row when the row is added to the model.
	 * @param id the id of the row
	 */
	protected void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * Adds {@link Cell} to cells list
	 * @param cell The {@link Cell} to add
	 */
	public void addCell(Cell cell)
	{
		cell.setId(String.valueOf(idCounter++));
		cell.setOrder(cells.size());
		cells.add(cell);
	}
	
	/**
	 * Removes {@link Cell} from the cells list by its index
	 * @param index The index of the cell to remove
	 * @throws UIException if cell does not exist
	 */
	protected void removeCell(int index) throws UIException
	{
		if (index > cells.size() - 1 || index < 0)
		{
			throw new UIException("cell does not exist index " + index + " total cells:" + cells.size());
		}
		cells.remove(index);
	}
		
	/**
	 * Returns {@link Cell} from cells list
	 * @param index the order of the cell in the list
	 * @return the cell in the requeted index
	 * @throws UIException if cell does not exist
	 */
	public Cell getCell(int index) throws UIException
	{
		if (index > cells.size() - 1)
		{
			throw new UIException("cell does not exist index " + index + " total cells:" + cells.size());
		}
		return (Cell) cells.get(index);
	}
	
	/**
	 * Returns the number of cells in the row
	 * @return the number of cells in the row
	 */
	public int getCellsCount()
	{
		return cells.size();
	}
	
	/**
	 * Returns the list of cells of this row
	 * @return an ArrayList of the row's cells
	 */
	public ArrayList getCells()
	{
		return cells;
	}
	
	protected void adjustCellIds() throws UIException 
	{
		for (int index = 0;index < cells.size();index++)
		{
			getCell(index).setId(index);
		}
	}
	
	/**
	 * sets the {@link Menu} of the row
	 * @param menu the menu of the row
	 */
	public void setMenu(Menu menu)
	{
		this.menu = menu;
	}
	
	/**
	 * Returns the {@link Menu} of the row
	 * @return the menu of the row
	 */
	public Menu getMenu()
	{
		return menu;
	}
	
	/**
	 * Returns true if the row is marked as selected
	 * @return true if the row is currently selected
	 */
	public boolean isSelected()
	{
		return selected;
	}
	
	/**
	 * Sets the row as selected or unselected
	 * @param selected if true - the row is marked as selected
	 */
	protected void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	/**
	 * Returns the extra data HashMap of the row 
	 * @return HashMap of extra data for this row
	 */
	public HashMap getExtraData()
	{
		return extraData;
	}
	
	/**
	 * Returns one extra data object from the HashMap.
	 * @param ket the key of the value to return
	 * @return Object,the value found
	 */
	public Object getExtraData(String key)
	{
		if (extraData == null)
		{
			return null;
		}
		return extraData.get(key);
	}

	/**
	 * Returns one extra data string from the HashMap.
	 * @param ket the key of the value to return
	 * @return String,the value found
	 */
	public String getStringExtraData(String key)
	{
		if (extraData == null)
		{
			return null;
		}
		Object value = extraData.get(key); 
		return value != null ? value.toString() : null;
	}
	
	/**
	 * Sets the extra data HashMap.
	 * @param extraData The new HashMap
	 */
	public void setExtraData(HashMap extraData)
	{
		this.extraData = extraData;
	}
	
	/**
	 * Adds one object value to the extra data map.The method sets the value as<br>
	 * the string representation of the object using the toString() method.
	 * @param value the object value to set
	 * @param key the key to insert the value into
	 */
	public void addExtraData(String key, Object value)
	{
		if (extraData == null) extraData = new HashMap();
		extraData.put(key,value);
	}
	
	/**
	 * Returns the linkable property,this property affect the cells of the row.
	 * @return true if links are allowed in the cells of this row
	 */
	public boolean isLinkable()
	{
		return linkable;
	}
	
	/**
	 * Sets the linkable property,if true the cells of this row can contain links.
	 * @param linkable if true links are allowed in this row
	 */
	public void setLinkable(boolean linkable)
	{
		this.linkable = linkable;
	}
	
	/**
	 * Returns the deletable property
	 * @return true if the row can be deleted
	 */
	public boolean isDeletable()
	{
		return deletable;
	}
	
	/**
	 * Returns the selectable propery
	 * @return true if this row can be marked as selected inside a model. 
	 */
	public boolean isSelectable()
	{
		return selectable;
	}
	
	/**
	 * Sets the deletable property.The model that holds the row is not<br>
	 * responsible for preventing deletion of the row in case this property is set to false,<br>
	 * but the user of the model should do it.
	 * @param deletable if true ,the row can be deleted.
	 */
	public void setDeletable(boolean deletable)
	{
		this.deletable = deletable;
	}
	
	/**
	 * Sets the selectable property.
	 * @param selectable if true this row can be marked as selected inside a model. 
	 */
	public void setSelectable(boolean selectable)
	{
		this.selectable = selectable;
	}
	
	/**
	 * Returns the updateable property
	 * @return true if the row can be updated
	 */
	public boolean isUpdateable()
	{
		return updateable;
	}

	/**
	 * Sets the updateable property.The model that holds the row is not<br>
	 * responsible for preventing update of the row in case this property is set to false,<br>
	 * but the user of the model should do it.
	 * @param deletable if true ,the row can be updated.
	 */
	public void setUpdateable(boolean updateable)
	{
		this.updateable = updateable;
	}
	
	/**
	 * Returns the css class name for an uneven row in the model.
	 * @return the css class name for an uneven row in the model.
	 */
	public String getCssClassName1()
	{
		return cssClassName1;
	}

	/**
	 * Returns the css class name for an even row in the model.
	 * @return the css class name for an even row in the model.
	 */
	public String getCssClassName2()
	{
		return cssClassName2;
	}
	
	/**
	 * Returns the css style string for an uneven row in the model.
	 * @return the css style string for an uneven row in the model.
	 */
	public String getCssStyle1()
	{
		return cssStyle1;
	}

	/**
	 * Returns the css style string for an even row in the model.
	 * @return the css style string for an even row in the model.
	 */
	public String getCssStyle2()
	{
		return cssStyle2;
	}
	
	/**
	 * Sets the css class name for an uneven row in the model.
	 * @param cssClassName1 the css style string for an uneven row in the model.
	 */
	public void setCssClassName1(String cssClassName1)
	{
		this.cssClassName1 = cssClassName1;
	}

	/**
	 * Sets the css class name for an even row in the model.
	 * @param cssClassName2 the css style string for an even row in the model.
	 */
	public void setCssClassName2(String cssClassName2)
	{
		this.cssClassName2 = cssClassName2;
	}
	
	/**
	 * Sets the css style string for an uneven row in the model.
	 * @param cssStyle1 the css style string for an uneven row in the model.
	 */
	public void setCssStyle1(String cssStyle1)
	{
		this.cssStyle1 = cssStyle1;
	}
	
	/**
	 * Sets the css style string for an even row in the model.
	 * @param cssStyle2 the css style string for an even row in the model.
	 */
	public void setCssStyle2(String cssStyle2)
	{
		this.cssStyle2 = cssStyle2;
	}
	
	/**
	 * Compares Row object and another object
	 * @param obj the object to compare the row to
	 * return true if the row and the object have the same id
	 */
	public boolean equals(Object obj)
	{
		if (obj instanceof Row)
		{
			return ((Row) obj).getId().equals(id);
		}
		return false;
	}
	
	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * click event on this row. 
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getRowClickEvent() 
	{
		return rowClickEvent;
	}

	/**
	 * Returns the {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * double click event on this row. 
	 * @return {@link com.ness.fw.ui.events.Event} object
	 */
	public Event getRowDblClickEvent() 
	{
		return rowDblClickEvent;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * click event on this row.
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setRowClickEvent(Event event) 
	{
		rowClickEvent = event;
	}

	/**
	 * Sets {@link com.ness.fw.ui.events.Event} object which holds the <br>information about 
	 * double click event on this menuItem.
	 * @param event the {@link com.ness.fw.ui.events.Event} object
	 */
	public void setRowDblClickEvent(Event event) 
	{
		rowDblClickEvent = event;
	}

}
