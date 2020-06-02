/*
 * Author: yifat har-nof
 * @version $Id: SequentialPagingService.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.*;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Provide consecutive Pages for the implamentation of sequential paging.
 * In any page request, perform the sql statement starting with records from 
 * the last page retrieval, according to the requested direction, and return the 
 * requested page.
 */
public class SequentialPagingService extends AbstractSequentialPagingService
{

	/** Direction to get the next page will be backward.
	 */
	public static final int BACKWARD = 0;

	/** Direction to get the next page will be forward.
	 */
	public static final int FORWARD = 1;

	/** The last page request type (first, last, next, previous).
	 */
	protected int lastOperation = GET_FIRST;

	/** The Key/Value pairs of keys and their first/last values.
	 */
	protected Map[] keys;

	/** Create a new PagingService.
	 * @param sqlService The SqlService to use.
	 * @param rowsInPage The number of rows in each page.
	 * @param connectionManagerName The name of connection manager to use.
	 */
	public SequentialPagingService(SqlService sqlService, int rowsInPage)
	{
		super(sqlService, rowsInPage);
		keys = new Map[] { new HashMap(), new HashMap()};
	}

	/** Add a key to be used in the paging mechanism with specified order.
	 * these keys will be used in the SQL WHERE and ORDER BY clauses.
	 * @param columnName The Column name to be added as a key.
	 * @param order The Column order. can be ASCEND or DESCEND.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void addOrderedPagingKey(String columnName, boolean order)
		throws PersistenceException
	{
		if (keyNames.contains(columnName))
		{
			throw new PersistenceException(
				"Duplicate paging key: " + columnName);
		}
		keyNames.add(columnName);
		keyOrder.add(new Boolean(order));
		keys[BACKWARD].put(columnName, null);
		keys[FORWARD].put(columnName, null);
	}

	/** Set a key value to be used in the paging mechanism with specified order.
	 * @param columnName The Column name to set it's value.
	 * @param direction The Direction to use. can be FORWARD or BACKWARD.
	 * @param value The value to  be assigned to the specified key.
	 */
	public void setPagingKeyValue(
		String columnName,
		int direction,
		Object value)
	{
		keys[direction].put(columnName, value);
	}

	/** Get a key value that is used in the paging mechanism with specified order.
	 * @param columnName The Column name to get it's value.
	 * @param direction The Direction to get. can be FORWARD or BACKWARD.
	 * @return Object The specified key value.
	 */
	public Object getPagingKeyValue(String columnName, int direction)
	{
		return keys[direction].get(columnName);
	}

	/** Parse the SQL statement and replaces the <paging:where/> and the <paging:sort/> tags, to be
	 * the actual keys to use in the SQL query. These tags must be present in the SQL statement.
	 * If there are other conditions in the SQL WHERE clause, then the <paging:where/> tag must appear
	 * between the WHERE clause and these conditions. The <paging:sort/> tag must appear immediately after the
	 * ORDER BY clause and no other columns may be added to the ORDER BY clause.
	 * Example 1: SELECT * FROM MYFILE WHERE <paging:where/> NAME LIKE '%jon%' ORDER BY <paging:sort/>
	 * Example 2: SELECT * FROM MYFILE <paging:where/> ORDER BY <paging:sort/>
	 * these keys will be used in the SQL WHERE and ORDER BY clauses.
	 * @param direction The diretion in which to get the page. Possible values are FORWARD and BACKWARD.
	 * @return String The parsed Sql statement.
	 */
	private String parseStatementKeys(int direction)
		throws PersistenceException
	{
		StringBuffer sb = new StringBuffer(4096);
		// get the position of the tags in the statement
		int pagingWhereTagPos = statement.indexOf(PAGING_WHERE_TAG);
		int pagingSortTagPos =
			statement.indexOf(
				PAGING_SORT_TAG,
				pagingWhereTagPos + PAGING_WHERE_TAG.length());

		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"SequentialPagingService, About to parse statement: " + statement);

		if (pagingWhereTagPos == -1 || pagingSortTagPos == -1)
			throw new PersistenceException(
				"paging tags does not exists in the sql statement. ("
					+ PAGING_WHERE_TAG
					+ " / "
					+ PAGING_SORT_TAG
					+ ")");

		// append the WHERE part to the statement with the keys in the requested direction.
		boolean whereExists = statement.toUpperCase().indexOf(" WHERE ") > -1;
		if (isFirstPage)
		{
			// don't need to add keys to the statement
			sb.append(statement.substring(0, pagingWhereTagPos) + " ");
			sb.append(
				statement.substring(
					pagingWhereTagPos + PAGING_WHERE_TAG.length(),
					pagingSortTagPos));
		}
		else
		{
			sb.append(
				statement.substring(0, pagingWhereTagPos)
					+ ((whereExists) ? " (" : " WHERE ("));
			int count = 0;
			String previous = null;
			for (count = 0; count < keyNames.size(); count++)
			{
				String current = (String) keyNames.get(count);
				boolean ascend = ((Boolean) keyOrder.get(count)).booleanValue();
				if (count > 0)
				{
					sb.append(" or " + previous + "=? and (");
				}
				sb.append(
					current
						+ ((direction == FORWARD)
							? ((ascend) ? ">?" : "<?")
							: ((ascend) ? "<?" : ">?")));
				previous = current;
			}
			while (count-- > 1)
			{
				sb.append(")");
			}
			sb.append(
				((whereExists) ? ") and " : ") ")
					+ statement.substring(
						pagingWhereTagPos + PAGING_WHERE_TAG.length(),
						pagingSortTagPos));
		}
		// append the ORDER BY part to the statement according to the keys in the requested order and the direction.
		for (int count = 0; count < keyNames.size(); count++)
		{
			if (count > 0)
			{
				sb.append(",");
			}
			boolean ascend = ((Boolean) keyOrder.get(count)).booleanValue();
			sb.append(
				" "
					+ keyNames.get(count)
					+ " "
					+ ((direction == BACKWARD)
						? ((ascend) ? "desc" : "asc")
						: ((ascend) ? "asc" : "desc")));
		}
		sb.append(
			" "
				+ statement.substring(
					pagingSortTagPos + PAGING_SORT_TAG.length()));
		return sb.toString();
	}

	/** Get the first page of rows.
	 * @return Page The first page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page firstPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		isFirstPage = true;
		lastOperation = GET_FIRST;
		return getPage(FORWARD, connectionProvider);
	}

	/** Get the last page of rows.
	 * @return Page The last page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page lastPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		isFirstPage = true;
		lastOperation = GET_LAST;
		return getPage(BACKWARD, connectionProvider);
	}

	/** Get the next page of rows.
	 * @return Page The next page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page nextPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		lastOperation = GET_NEXT;
		return getPage(FORWARD, connectionProvider);
	}

	/** Get the previous page of rows.
	 * @return Page The previous page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page previousPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		lastOperation = GET_PREVIOUS;
		return getPage(BACKWARD, connectionProvider);
	}

	/** Get a page of rows.
	 * @param direction The direction in which to get the page. Possible values are FORWARD and BACKWARD.
	 * @return Page The page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private Page getPage(int direction, ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"SequentialPagingService, Request page with direction: "
				+ direction);

		SqlService sqlService =
			new SqlService(parseStatementKeys(direction), null);
		if (isFirstPage)
		{
			isFirstPage = false;
		}
		else
		{
			// add the key values to the statement
			for (int i = 0; i < keyNames.size(); i++)
			{
				sqlService.addParameter(keys[direction].get(keyNames.get(i)));
				if (i < keyNames.size() - 1)
				{
					sqlService.addParameter(
						keys[direction].get(keyNames.get(i)));
				}
			}
		}
		// add the param values of the basic statement
		sqlService.addParameters(sqlServiceParams);

		// execute the statement with/without keys & build a Page with rows 
		// from the first row of the ResultSet to number of rows in page (if retrieved).
		Page page = Query.execute(sqlService, connectionProvider, rowsInPage);

		if (page.getRowCount() > 0)
		{
			if (direction == BACKWARD)
			{
				page.reverseRowsOrder();
			}
			// keep the keys of the first row of the last requested page
			page.first();
			for (int i = 0; i < keyNames.size(); i++)
			{
				String keyName = getPageColumnName((String) keyNames.get(i));
				keys[BACKWARD].put(keyNames.get(i), page.getObject(keyName));
			}
			// keep the keys of the last row of the last requested page
			page.last();
			for (int i = 0; i < keyNames.size(); i++)
			{
				String keyName = getPageColumnName((String) keyNames.get(i));
				keys[FORWARD].put(keyNames.get(i), page.getObject(keyName));
			}
		}
		page.beforeFirst();
		hasMorePages = page.hasMorePages();
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Requested page contains " + page.getRowCount() + " rows");
		return page;
	}

	private String getPageColumnName(String keyName)
	{
		int dotPos = keyName.indexOf(".");
		if (dotPos >= 0)
		{
			return keyName.substring(dotPos + 1);
		}
		return keyName;
	}

	/** Is the specified operation valid based on the last operation
	 * results.
	 * @param operation The requested operation. 
	 * Can be one of the following: GET_NEXT, GET_PREVIOUS, GET_FIRST, GET_LAST.
	 * @return boolean Whether the operation is valid or not.
	 */
	public boolean isOperationAllowed(int operation)
	{
		if (operation == GET_NEXT)
		{
			if ((lastOperation == GET_FIRST || lastOperation == GET_NEXT)
				&& hasMorePages
				|| lastOperation == GET_PREVIOUS)
			{
				return true;
			}
		}
		else if (operation == GET_PREVIOUS)
		{
			if ((lastOperation == GET_LAST || lastOperation == GET_PREVIOUS)
				&& hasMorePages
				|| lastOperation == GET_NEXT)
			{
				return true;
			}
		}
		else if (operation == GET_FIRST)
		{
			return true;
		}
		else if (operation == GET_LAST)
		{
			return true;
		}
		return false;
	}

}