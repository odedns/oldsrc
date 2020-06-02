/*
 * Author: yifat har-nof
 * @version $Id: PageManager.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.Row;

/**
 * Encapsulates a page of query results.
 */
public class PageManager implements Serializable, Cloneable
{
	/** A constant used to add a sorting column with ascending order.
	 */
	public static final int ASCEND = Page.ASCEND;

	/** A constant used to add a sorting column with descending order.
	 */
	public static final int DESCEND = Page.DESCEND;

	/**
	 * The page with the query results to encapsulate.
	 */
	private Page page;

	/**
	 * Creates new PageManager object.
	 * @param page The page with the query results to encapsulate.
	 */
	public PageManager(Page page)
	{
		this.page = page;
	}

	/** Get column name by index.
	 * @param index The column index to use.
	 * @return The column name.
	 */
	public String getColumnName(int index)
	{
		return page.getColumnName(index);
	}

	/** Get the column data type by index
	 * @param index The index to use.
	 * @return int The column data type.
	 */
	public int getColumnType(int index)
	{
		return page.getColumnType(index);
	}

	/** Get the number of columns in the page.
	 * @return int The number of columns in the page.
	 */
	public int getColumnCount()
	{
		return page.getColumnCount();
	}

	/** Get the number of rows in the page.
	 * @return int The number of rows in the page.
	 */
	public int getRowCount()
	{
		return page.getRowCount();
	}

	/** Get a list of {@link Row}s contained by the page.
	 * @return List The rows.
	 */
	public List getRows()
	{
		return page.getRows();
	}

	/** Get the current cursor position.
	 * @return int The current cursor position.
	 */
	public int getCursorPosition()
	{
		return page.getCursorPosition();
	}

	/** Reverse the order of the rows within the page.
	 */
	public void reverseRowsOrder()
	{
		page.reverseRowsOrder();
	}

	/** Are there any more pages after this one.
	 * @return boolean Whether there are more pages after this one.
	 */
	public boolean hasMorePages()
	{
		return page.hasMorePages();
	}

	/** Get a {@link Row} by row number.
	 * @param rowNumber The row number to use.
	 * @return Row The Row.
	 */
	public Row getRow(int rowNumber)
	{
		return page.getRow(rowNumber);
	}

	/** Get the current {@link Row}.
	 * @return Row The current {@link Row}.
	 */
	public Row getRow()
	{
		return page.getRow();
	}

	/** Get the column data as an Object by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Object An Object containing the column data.
	 */
	public Object getObject(int index)
	{
		return page.getObject(index);
	}

	/** Get the column data as Object by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Object An Object containing the column data.
	 */
	public Object getObject(String name)
	{
		return page.getObject(name);
	}

	/** Get the column data as int by index from the current {@link Row}.
	 * @param index The column index.
	 * @return int An int containing the column data.
	 */
	public int getInt(int index)
	{
		return page.getInt(index);
	}

	/** Get the column data as int by name from the current {@link Row}.
	 * @param name The column name.
	 * @return int An int containing the column data.
	 */
	public int getInt(String name)
	{
		return page.getInt(name);
	}

	/** Get the column data as Integer by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Integer An Integer containing the column data.
	 */
	public Integer getInteger(int index) 
	{
		return page.getInteger(index);
	}

	/** Get the column data as Integer by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Integer An Integer containing the column data.
	 */
	public Integer getInteger(String name) 
	{
		return page.getInteger(name);
	}


	/** Get the column data as double by index from the current {@link Row}.
	 * @param index The column index.
	 * @return double A double containing the column data.
	 */
	public double getDouble(int index)
	{
		return page.getDouble(index);
	}

	/** Get the column data as double by name from the current {@link Row}.
	 * @param name The column name.
	 * @return double A double containing the columns data.
	 */
	public double getDouble(String name)
	{
		return page.getDouble(name);
	}

	/** Get the column data as double by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Double A double containing the column data.
	 */
	public Double getDoubleObject(int index)
	{
		return page.getDoubleObject(index);
	}

	/** Get the column data as double by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Double A double containing the columns data.
	 */
	public Double getDoubleObject(String name)
	{
		return page.getDoubleObject(name);
	}

	/** Get the column data as float by index from the current {@link Row}.
	 * @param index The column index.
	 * @return float A float containing the column data.
	 */
	public float getFloat(int index)
	{
		return page.getFloat(index);
	}

	/** Get the column data as float by name from the current {@link Row}.
	 * @param name The column name.
	 * @return double A float containing the columns data.
	 */
	public float getFloat(String name)
	{
		return page.getFloat(name);
	}

	/** Get the column data as Float by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Float A float containing the column data.
	 */
	public Float getFloatObject(int index)
	{
		return page.getFloatObject(index);
	}

	/** Get the column data as Float by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Float A float containing the columns data.
	 */
	public Float getFloatObject(String name)
	{
		return page.getFloatObject(name);
	}

	/** Get the column data as long by index from the current {@link Row}.
	 * @param index The column index.
	 * @return long A long containing the column data.
	 */
	public long getLong(int index)
	{
		return page.getLong(index);
	}

	/** Get the column data as long by name from the current {@link Row}.
	 * @param name The column name.
	 * @return long A long containing the columns data.
	 */
	public long getLong(String name)
	{
		return page.getLong(name);
	}

	/** Get the column data as Long by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Long A long containing the column data.
	 */
	public Long getLongObject(int index)
	{
		return page.getLongObject(index);
	}

	/** Get the column data as Long by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Long A long containing the column data.
	 */
	public Long getLongObject(String name)
	{
		return page.getLongObject(name);
	}


	/** Get the column data as String by index from the current {@link Row}.
	 * @param index The column index.
	 * @return String A String containing the column data.
	 */
	public String getString(int index)
	{
		return page.getString(index);
	}

	/** Get the column data as String by name from the current {@link Row}.
	 * @param name The column name.
	 * @return String A String containing the column data.
	 */
	public String getString(String name)
	{
		return page.getString(name);
	}

	/** Get the column data as Date by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Date A Date containing the column data.
	 */
	public java.util.Date getDate(int index)
	{
		return page.getDate(index);
	}

	/** Get the column data as Date by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Date A Date containing the column data.
	 */
	public java.util.Date getDate(String name)
	{
		return page.getDate(name);
	}

	/** Get the column data as TimestampTextModel by index from the current {@link Row}.
	 * @param index The column index.
	 * @return TimestampTextModel A TimestampTextModel containing the column data.
	 */
	public Timestamp getTimeStamp(int index)
	{
		return page.getTimeStamp(index);
	}

	/** Get the column data as TimestampTextModel by name from the current {@link Row}.
	 * @param name The column name.
	 * @return TimestampTextModel A TimestampTextModel containing the column data.
	 */
	public Timestamp getTimeStamp(String name)
	{
		return page.getTimeStamp(name);
	}

	/** Get the column data as Time by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Time A Time containing the column data.
	 */
	public Time getTime(int index)
	{
		return page.getTime(index);
	}

	/** Get the column data as Time by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Time A Time containing the column data.
	 */
	public Time getTime(String name)
	{
		return page.getTime(name);
	}

	/** Get the column data as BigDecimal by index from the current {@link Row}.
	 * @param index The column index.
	 * @return BigDecimal A BigDecimal containing the column data.
	 */
	public java.math.BigDecimal getBigDecimal(int index)
	{
		return page.getBigDecimal(index);
	}

	/** Get the column data as BigDecimal by name from the current {@link Row}.
	 * @param name The column name.
	 * @return BigDecimal A BigDecimal containing the column data.
	 */
	public java.math.BigDecimal getBigDecimal(String name)
	{
		return page.getBigDecimal(name);
	}

	/** Get the column data as Number by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Number A Number containing the column data.
	 */
	public Number getNumber(int index)
	{
		return page.getNumber(index);
	}

	/** Get the column data as Number by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Number A Number containing the column data.
	 */
	public Number getNumber(String name)
	{
		return page.getNumber(name);
	}

	/** Set the cursor position before the first row.
	 */
	public void beforeFirst()
	{
		page.beforeFirst();
	}

	/** Set the cursor position after the last row.
	 */
	public void afterLast()
	{
		page.afterLast();
	}

	/** Set the cursor position to the first row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean first()
	{
		return page.first();
	}

	/** Set the cursor position to the last row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean last()
	{
		return page.last();
	}

	/** Set the cursor position to the next row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean next()
	{
		return page.next();
	}

	/** Set the cursor position to the previous row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean previous()
	{
		return page.previous();
	}

	/** Set the cursor position to the row number specified.
	 * @param rowNumber The absolute row number to set the cursor to.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean absolute(int rowNumber)
	{
		return page.absolute(rowNumber);
	}

	/** Set the cursor position specified number of
	 * rows from the current cursor position.
	 * @param numberOfRows The number of rows from the current row to set the cursor to.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean relative(int numberOfRows)
	{
		return page.relative(numberOfRows);
	}

	/** Add a sorting column to sort the page with.
	 * @param index The column number to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void addSortColumn(int index, int order)
	{
		page.addSortColumn(index, order);
	}

	/** Add a sorting column to sort the page with.
	 * @param name The column nname to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void addSortColumn(String name, int order)
	{
		page.addSortColumn(name, order);
	}

	/** Clear the list of sort columns.
	 * This method should be called to allow for a new sort columns
	 * combination.
	 */
	public void clearSortColumns()
	{
		page.clearSortColumns();
	}

	/** Sort the page by a specified column in a specified order.
	 * @param index The column number to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void sortByColumn(int index, int order)
	{
		page.sortByColumn(index, order);
	}

	/** Sort the page by a specified column in a specified order.
	 * @param name The column nname to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void sortByColumn(String name, int order)
	{
		page.sortByColumn(name, order);
	}

	/** Sort the page according to the sort columns list.
	 * this function should be used as following:<br><br>
	 *      <CODE>page.addSortColumn(1, ASCEND);<br>
	 *      page.addSortColumn(2, DESCEND);<br>
	 *      page.sort();<br>
	 *      page.clearSortColumns();<br></CODE>
	 */
	public void sort()
	{
		page.sort();
	}

	/** Get an array of the column numbers that the page is sorted by.
	 * @return int[] An array of the column numbers that the page is sorted by.
	 * If the page is not sorted an empty array is returned.
	 */
	public int[] getSortColumns()
	{
		return page.getSortColumns();
	}

	/** Get the sort order of a specified sort column.
	 * @param index The sort column index.
	 * @return int The sort order of the specified column. Could be ASCEND or DESCEND.
	 */
	public int getSortColumnOrder(int index)
	{
		return page.getSortColumnOrder(index);
	}

	/** Get the contents of the page as a String.
	 * @return String The contents of the page.
	 */
	public String toString()
	{
		return page.toString();
	}

	/** Get the contents of the page as a ready to print HTML table.
	 * @return String The contents of the page as a ready to print HTML table.
	 */
	public String toHTML()
	{
		return page.toHTML();
	}


}
