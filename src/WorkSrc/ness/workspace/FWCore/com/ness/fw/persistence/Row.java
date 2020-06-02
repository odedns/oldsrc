/*
 * Author: yifat har-nof
 * @version $Id: Row.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

/**
 * Represents a Cached row from a {@link Page}
 */
public class Row extends java.util.ArrayList
{

	/** Create a new Row with no initial columns capacity.
	 */
	public Row()
	{
		super();
	}

	/** Create a new Row with specified initial Capacity of columns.
	 * @param initialCapacity The initial columns capacity of the created Row.
	 */
	public Row(int initialCapacity)
	{
		super(initialCapacity);
	}

	/** Return the String representation of the Row.
	 * @return A String containing the values of all of the columns in the Row
	 * Separated with a <I><B>" | "</B></I> character sequence.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer(1024);
		sb.append("|");
		for (int i = 0; i < size(); i++)
		{
			if (get(i) != null)
			{
				sb.append(" " + get(i).toString() + " |");
			}
		}
		return sb.toString();
	}

}
