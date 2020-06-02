/*
 * Author: yifat har-nof
 * @version $Id: Page.java,v 1.2 2005/04/04 09:34:32 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.*;
import java.io.Serializable;
import java.sql.*;

import com.ness.fw.common.exceptions.PersistenceException;

/**
 ** Represents a Cached page from a {@link ResultSet}.
 */
public class Page implements Serializable, Cloneable
{

	/** A constant used to add a sorting column with ascending order.
	 */
	public static final int ASCEND = 1;

	/** A constant used to add a sorting column with descending order.
	 */
	public static final int DESCEND = -1;

	/** A List of the column numbers to sort the page with.
	 */
	private List sortColumns = null;

	/** A List of the column sort orders (ascending/descending) to sort the page with.
	 */
	private List sortOrder = null;

	/** A list of the Rows in the Page.
	 */
	protected List rows;

	/** A list of the original Column names from the result set.
	 */
	private String colNames[];

	/** A list of the column types from the result set.
	 */
	private int colTypes[];

	/** A map of the Column names from the result set converted to upper case for searching.
	 */
	private Map colNamesUpper;

	/** The last Column index that was added to the page.
	 */
	private int lastColumn = 0;

	/** The maximum number of rows in the page.
	 */
	private int maxRowsInPage = 0;

	/** The current cursor position
	 */
	private int cursorPosition = -1;

	/** Are there more pages after this one
	 */
	private boolean hasMorePages = false;

	/** Create a new Page with specified number of columns.
	 * @param columnsNumber The number of columns in the created Page.
	 * @param rowsNumber The maximum number of rows in the page.
	 */
	public Page(int columnsNumber, int maxRowsInPage)
	{
		this.maxRowsInPage = maxRowsInPage;
		colNames = new String[columnsNumber];
		colTypes = new int[columnsNumber];
		colNamesUpper = new HashMap(columnsNumber);
		rows = new ArrayList(maxRowsInPage);
	}

	/** Create a new Page from specified ResultSet.
	 * @param rs The ResultSet to use.
	 * @param rowsNumber The maximum number of rows in the page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page(ResultSet rs, int maxRowsInPage) throws PersistenceException
	{
		try
		{
			this.maxRowsInPage = maxRowsInPage;
			setColumns(rs.getMetaData());
			setRows(rs);
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/** Create a new empty Page based on another page.
	 * @param page The Page to base upon.
	 */
	public Page(Page page)
	{
		this.sortColumns = page.sortColumns;
		this.sortOrder = page.sortOrder;
		this.colNames = page.colNames;
		this.colTypes = page.colTypes;
		this.colNamesUpper = page.colNamesUpper;
		this.lastColumn = page.lastColumn;
		this.maxRowsInPage = page.maxRowsInPage;
		rows = new ArrayList(maxRowsInPage);
	}

	/** Set columns infornation from a given {@link ResultSetMetaData}.
	 * @param rsMetaData The ResultSetMetaData to use.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void setColumns(ResultSetMetaData rsMetaData)
		throws PersistenceException
	{
		try
		{
			int colCount = rsMetaData.getColumnCount();
			colNames = new String[colCount];
			colTypes = new int[colCount];
			colNamesUpper = new HashMap(colCount);
			lastColumn = 0;
			for (int i = 1; i <= colCount; i++)
			{
				addColumn(
					rsMetaData.getColumnName(i),
					rsMetaData.getColumnType(i));
			}
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/** Add a Column to the Page.
	 * @param colName The column name.
	 * @param colType The column type.
	 */
	public void addColumn(String colName, int colType)
	{
		colNames[lastColumn] = colName;
		colTypes[lastColumn] = colType;
		colNamesUpper.put(colName.toUpperCase(), new Integer(lastColumn));
		if (rows != null && rows.size() > 0)
		{
			for (int i = 0; i < rows.size(); i++)
			{
				((Row) rows.get(i)).add(null);
			}
		}
		lastColumn++;
	}

	/** Add a Row to the Page.
	 * @param row The {@link Row} to be added to the page.
	 * @return int The number of rows in the page.
	 */
	public int addRow(Row row)
	{
		rows.add(row);
		cursorPosition = rows.size() - 1;
		return rows.size();
	}

	/** Add an Empty Row (Contains null column values) to the Page.
	 * @return int The number of rows in the page.
	 */
	public int addEmptyRow()
	{
		Row row = new Row(colNames.length);
		for (int i = 1; i <= colNames.length; i++)
		{
			row.add(null);
		}
		rows.add(row);
		cursorPosition = rows.size() - 1;
		return rows.size();
	}

	/** Set column data by index into the current {@link Row}.
	 * @param index The column index.
	 * @param obj An Object containing the columns data.
	 */
	public void setColumnValue(int index, Object obj)
	{
		((Row) rows.get(cursorPosition)).set(index - 1, obj);
	}

	/** Set column data by name into the current {@link Row}.
	 * @param name The column name.
	 * @param obj An Object containing the columns data.
	 */
	public void setColumnValue(String name, Object obj)
	{
		((Row) rows.get(cursorPosition)).set(
			((Integer) colNamesUpper.get(name.toUpperCase())).intValue(),
			obj);
	}

	/** Get column name by index.
	 * @param index The column index to use.
	 * @return The column name.
	 */
	public String getColumnName(int index)
	{
		return colNames[index - 1];
	}

	/** Get the column data type by index
	 * @param index The index to use.
	 * @return int The column data type.
	 */
	public int getColumnType(int index)
	{
		return colTypes[index - 1];
	}

	/** Get the number of columns in the page.
	 * @return int The number of columns in the page.
	 */
	public int getColumnCount()
	{
		return colNames.length;
	}

	/** Get the number of rows in the page.
	 * @return int The number of rows in the page.
	 */
	public int getRowCount()
	{
		return rows.size();
	}

	/** Get a list of {@link Row}s contained by the page.
	 * @return List The rows.
	 */
	public List getRows()
	{
		return new ArrayList(rows);
	}

	/** Get the current cursor position.
	 * @return int The current cursor position.
	 */
	public int getCursorPosition()
	{
		return cursorPosition;
	}

	/** Set the rows in the page.
	 * @param rows A list of rows to set in the page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void setRows(List rows) throws PersistenceException
	{
		this.rows = rows;
	}
	/** Set the rows in the page from a given {@link ResultSet}.
	 * @param rs The ResultSet to use.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private void setRows(ResultSet rs) throws PersistenceException
	{
		try
		{
			rows = new ArrayList(maxRowsInPage);
			int currentRow = 0;
			while (currentRow < maxRowsInPage && rs.next())
			{
				Row row = new Row(colNames.length);
				for (int i = 1; i <= colNames.length; i++)
				{
					if (colTypes[i - 1] == java.sql.Types.CLOB)
					{
						row.add(rs.getString(i));
					}
					else
					{
						row.add(rs.getObject(i));
					}
				}
				rows.add(row);
				currentRow++;
			}
			hasMorePages = rs.next();
			cursorPosition = -1;
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/** Reverse the order of the rows within the page.
	 */
	public void reverseRowsOrder()
	{
		Collections.reverse(rows);
	}

	/** Are there any more pages after this one.
	 * @return boolean Whether there are more pages after this one.
	 */
	public boolean hasMorePages()
	{
		return hasMorePages;
	}

	/** Get a {@link Row} by row number.
	 * @param rowNumber The row number to use.
	 * @return Row The Row.
	 */
	public Row getRow(int rowNumber)
	{
		return (Row) rows.get(rowNumber);
	}

	/** Get the current {@link Row}.
	 * @return Row The current {@link Row}.
	 */
	public Row getRow()
	{
		return (Row) rows.get(cursorPosition);
	}

	/** Get the column data as an Object by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Object An Object containing the column data.
	 */
	public Object getObject(int index)
	{
		return ((Row) rows.get(cursorPosition)).get(index - 1);
	}

	/** Get the column data as Object by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Object An Object containing the column data.
	 */
	public Object getObject(String name)
	{
		return ((Row) rows.get(cursorPosition)).get(
			((Integer) colNamesUpper.get(name.toUpperCase())).intValue());
	}

	/** Get the column data as int by index from the current {@link Row}.
	 * @param index The column index.
	 * @return int An int containing the column data.
	 */
	public int getInt(int index)
	{
		Number i = (Number) getObject(index);
		return (i == null) ? 0 : i.intValue();
	}

	/** Get the column data as int by name from the current {@link Row}.
	 * @param name The column name.
	 * @return int An int containing the column data.
	 */
	public int getInt(String name)
	{
		Number i = (Number) getObject(name);
		return (i == null) ? 0 : i.intValue();
	}

	/** Get the column data as Integer by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Integer An Integer containing the column data.
	 */
	public Integer getInteger(int index)
	{
		return (Integer) getObject(index);
	}

	/** Get the column data as Integer by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Integer An Integer containing the column data.
	 */
	public Integer getInteger(String name)
	{
		return (Integer) getObject(name);
	}

	/** Get the column data as double by index from the current {@link Row}.
	 * @param index The column index.
	 * @return double A double containing the column data.
	 */
	public double getDouble(int index)
	{
		Number d = (Number) getObject(index);
		return (d == null) ? 0 : d.doubleValue();
	}

	/** Get the column data as double by name from the current {@link Row}.
	 * @param name The column name.
	 * @return double A double containing the columns data.
	 */
	public double getDouble(String name)
	{
		Number d = (Number) getObject(name);
		return (d == null) ? 0 : d.doubleValue();
	}

	/** Get the column data as double by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Double A double containing the column data.
	 */
	public Double getDoubleObject(int index)
	{
		return (Double) getObject(index);
	}

	/** Get the column data as double by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Double A double containing the columns data.
	 */
	public Double getDoubleObject(String name)
	{
		return (Double) getObject(name);
	}

	/** Get the column data as long by index from the current {@link Row}.
	 * @param index The column index.
	 * @return long A long containing the column data.
	 */
	public long getLong(int index)
	{
		Number f = (Number) getObject(index);
		return (f == null) ? 0 : f.longValue();
	}

	/** Get the column data as long by name from the current {@link Row}.
	 * @param name The column name.
	 * @return long A long containing the columns data.
	 */
	public long getLong(String name)
	{
		Number f = (Number) getObject(name);
		return (f == null) ? 0 : f.longValue();
	}

	/** Get the column data as Long by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Long A long containing the column data.
	 */
	public Long getLongObject(int index)
	{
		return (Long) getObject(index);
	}

	/** Get the column data as Long by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Long A long containing the columns data.
	 */
	public Long getLongObject(String name)
	{
		return (Long) getObject(name);
	}

	/** Get the column data as float by index from the current {@link Row}.
	 * @param index The column index.
	 * @return float A float containing the column data.
	 */
	public float getFloat(int index)
	{
		Number f = (Number) getObject(index);
		return (f == null) ? 0 : f.floatValue();
	}

	/** Get the column data as float by name from the current {@link Row}.
	 * @param name The column name.
	 * @return float A float containing the columns data.
	 */
	public float getFloat(String name)
	{
		Number f = (Number) getObject(name);
		return (f == null) ? 0 : f.floatValue();
	}

	/** Get the column data as Float by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Float A float containing the column data.
	 */
	public Float getFloatObject(int index)
	{
		return (Float) getObject(index);
	}

	/** Get the column data as Float by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Float A float containing the columns data.
	 */
	public Float getFloatObject(String name)
	{
		return (Float) getObject(name);
	}

	/** Get the column data as String by index from the current {@link Row}.
	 * @param index The column index.
	 * @return String A String containing the column data.
	 */
	public String getString(int index)
	{
		return (String) getObject(index);
	}

	/** Get the column data as String by name from the current {@link Row}.
	 * @param name The column name.
	 * @return String A String containing the column data.
	 */
	public String getString(String name)
	{
		return (String) getObject(name);
	}

	/** Get the column data as java.util.Date by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Date A Date containing the column data.
	 */
	public java.util.Date getDate(int index)
	{
		return (java.util.Date) getObject(index);
	}

	/** Get the column data as java.util.Date by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Date A Date containing the column data.
	 */
	public java.util.Date getDate(String name)
	{
		return (java.util.Date) getObject(name);
	}

	/** Get the column data as java.sql.Timestamp by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Timestamp A Timestamp containing the column data.
	 */
	public Timestamp getTimeStamp(int index)
	{
		return (Timestamp) getObject(index);
	}

	/** Get the column data as java.sql.Timestamp by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Timestamp A Timestamp containing the column data.
	 */
	public Timestamp getTimeStamp(String name)
	{
		return (Timestamp) getObject(name);
	}

	/** Get the column data as java.sql.Time by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Time A Time containing the column data.
	 */
	public Time getTime(int index)
	{
		return (Time) getObject(index);
	}

	/** Get the column data as java.sql.Time by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Time A Time containing the column data.
	 */
	public Time getTime(String name)
	{
		return (Time) getObject(name);
	}

	/** Get the column data as java.math.BigDecimal by index from the current {@link Row}.
	 * @param index The column index.
	 * @return BigDecimal A BigDecimal containing the column data.
	 */
	public java.math.BigDecimal getBigDecimal(int index)
	{
		return (java.math.BigDecimal) getObject(index);
	}

	/** Get the column data as java.math.BigDecimal by name from the current {@link Row}.
	 * @param name The column name.
	 * @return BigDecimal A BigDecimal containing the column data.
	 */
	public java.math.BigDecimal getBigDecimal(String name)
	{
		return (java.math.BigDecimal) getObject(name);
	}

	/** Get the column data as Number by index from the current {@link Row}.
	 * @param index The column index.
	 * @return Number A Number containing the column data.
	 */
	public Number getNumber(int index)
	{
		return (Number) getObject(index);
	}

	/** Get the column data as Number by name from the current {@link Row}.
	 * @param name The column name.
	 * @return Number A Number containing the column data.
	 */
	public Number getNumber(String name)
	{
		return (Number) getObject(name);
	}

	/** Set the cursor position before the first row.
	 */
	public void beforeFirst()
	{
		cursorPosition = -1;
	}

	/** Set the cursor position after the last row.
	 */
	public void afterLast()
	{
		cursorPosition = rows.size();
	}

	/** Set the cursor position to the first row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean first()
	{
		cursorPosition = 0;
		return rows.size() > 0;
	}

	/** Set the cursor position to the last row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean last()
	{
		cursorPosition = rows.size() - 1;
		return rows.size() > 0;
	}

	/** Set the cursor position to the next row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean next()
	{
		cursorPosition++;
		return cursorPosition >= 0
			&& cursorPosition < rows.size()
			&& rows.size() > 0;
	}

	/** Set the cursor position to the previous row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean previous()
	{
		cursorPosition--;
		return cursorPosition >= 0
			&& cursorPosition < rows.size()
			&& rows.size() > 0;
	}

	/** Set the cursor position to the row number specified.
	 * @param rowNumber The absolute row number to set the cursor to.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean absolute(int rowNumber)
	{
		cursorPosition = rowNumber;
		return cursorPosition >= 0
			&& cursorPosition < rows.size()
			&& rows.size() > 0;
	}

	/** Set the cursor position specified number of
	 * rows from the current cursor position.
	 * @param numberOfRows The number of rows from the current row to set the cursor to.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 */
	public boolean relative(int numberOfRows)
	{
		cursorPosition += numberOfRows;
		return cursorPosition >= 0
			&& cursorPosition < rows.size()
			&& rows.size() > 0;
	}

	/** Add a sorting column to sort the page with.
	 * @param index The column number to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void addSortColumn(int index, int order)
	{
		if (sortColumns == null)
		{
			sortColumns = new ArrayList();
			sortOrder = new ArrayList();
		}
		sortColumns.add(new Integer(index - 1));
		sortOrder.add(new Integer(order));
	}

	/** Add a sorting column to sort the page with.
	 * @param name The column nname to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void addSortColumn(String name, int order)
	{
		addSortColumn(
			((Integer) colNamesUpper.get(name.toUpperCase())).intValue() + 1,
			order);
	}

	/** Clear the list of sort columns.
	 * This method should be called to allow for a new sort columns
	 * combination.
	 */
	public void clearSortColumns()
	{
		sortColumns = null;
		sortOrder = null;
	}

	/** Sort the page by a specified column in a specified order.
	 * @param index The column number to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void sortByColumn(int index, int order)
	{
		clearSortColumns();
		addSortColumn(index, order);
		sort();
	}

	/** Sort the page by a specified column in a specified order.
	 * @param name The column nname to add as a sorting key.
	 * @param order The order in which the column will be sorted.
	 * Should be ASCEND or DESCEND.
	 */
	public void sortByColumn(String name, int order)
	{
		clearSortColumns();
		addSortColumn(name, order);
		sort();
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
		Comparator rowsComparator = new Comparator()
		{
			public int compare(Object obj1, Object obj2)
			{
				int equality = 0;
				for (int i = 0; i < sortColumns.size() && equality == 0; i++)
				{
					int order = ((Integer) (sortOrder.get(i))).intValue();
					int colNum = ((Integer) (sortColumns.get(i))).intValue();
					Object var1 = ((ArrayList) (obj1)).get(colNum);
					Object var2 = ((ArrayList) (obj2)).get(colNum);
					if (var1 == null)
					{
						if (var2 == null)
						{
							equality = 0;
						}
						else
						{
							equality = -1;
						}
					}
					else if (var2 == null)
					{
						equality = 1;
					}
					else
					{
						equality = ((Comparable) var1).compareTo(var2);
					}
					equality *= order;
				}
				return equality;
			}
		};
		Collections.sort(rows, rowsComparator);
	}

	/** Get an array of the column numbers that the page is sorted by.
	 * @return int[] An array of the column numbers that the page is sorted by.
	 * If the page is not sorted an empty array is returned.
	 */
	public int[] getSortColumns()
	{
		int cols[];
		if (sortColumns != null)
		{
			cols = new int[sortColumns.size()];
			for (int i = 0; i < sortColumns.size(); i++)
			{
				cols[i] = ((Integer) sortColumns.get(i)).intValue();
			}
		}
		else
		{
			cols = new int[] {
			};
		}
		return cols;
	}

	/** Get the sort order of a specified sort column.
	 * @param index The sort column index.
	 * @return int The sort order of the specified column. Could be ASCEND or DESCEND.
	 */
	public int getSortColumnOrder(int index)
	{
		return ((Integer) sortOrder.get(index)).intValue();
	}

	/** Get the contents of the page as a ready to print HTML table.
	 * @return String The contents of the page as a ready to print HTML table.
	 */
	public String toHTML()
	{
		StringBuffer sb = new StringBuffer(4096);
		sb.append("<table border>\r\n");
		sb.append("<tr>\r\n");
		for (int i = 0; i < colNames.length; i++)
		{
			sb.append("<td><b>" + colNames[i] + "</b></td>\r\n");
		}
		sb.append("</tr>\r\n");
		for (int i = 0; i < rows.size(); i++)
		{
			sb.append("<tr>\r\n");
			for (int j = 0; j < colNames.length; j++)
			{
				sb.append(
					"<td>" + ((Row) rows.get(i)).get(j) + "&nbsp;</td>\r\n");
			}
			sb.append("</tr>\r\n");
		}
		return sb.toString();
	}

	/** Get the contents of the page as a String.
	 * @return String The contents of the page.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer(4096);
		sb.append(
			"\r\n---------------------------------------------------\r\n");
		sb.append(
			" Start printing page, contains " + rows.size() + " rows \r\n");

		sb.append(" ROWNUM ");
		for (int i = 0; i < colNames.length; i++)
		{
			sb.append(" " + colNames[i] + "    ");
		}
		sb.append("\r\n");
		for (int i = 0; i < rows.size(); i++)
		{
			sb.append("  " + (i + 1) + "  ");
			for (int j = 0; j < colNames.length; j++)
			{
				if (((Row) rows.get(i)).get(j) != null)
					sb.append(
						" "
							+ (((Row) rows.get(i)).get(j)).toString().trim()
							+ "      ");
				else
					sb.append(" null      ");
			}
			sb.append("\r\n");
		}

		sb.append(" End printing page \r\n");
		sb.append("---------------------------------------------------\r\n");
		return sb.toString();
	}

}