/*
 * Author: yifat har-nof
 * @version $Id: CumulativePagingService.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.*;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Provide consecutive Pages for the implementation of both sequential and random paging.
 * The paging can be random between the first and the last pages fetched until now, however the
 * paging continues after the last page to get more pages. this increases the range for random page
 * access.
 */
public class CumulativePagingService extends AbstractSequentialPagingService
{

	/** The Key/Value pairs of keys starting each page.
	 */
	private ArrayList keys;

	/** The last Key/Value pairs of the last fetched page for getting the next page.
	 */
	private Map nextKeys;

	/** The last page request type (first, last, next, previous).
	 */
	private int currentPage = -1;

	/** Create a new PagingService.
	 * @param sqlService The SqlService to use.
	 * @param rowsInPage The number of rows in each page.
	 * @param connectionManagerName The name of connection manager to use.
	 */
	public CumulativePagingService(SqlService sqlService, int rowsInPage)
	{
		super(sqlService, rowsInPage);
		keys = new ArrayList();
		nextKeys = new HashMap();
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
	}

	/** Set a key value to be used in the paging mechanism for next page retrieval.
	 * @param columnName The Column name to set it's value.
	 * @param value The value to  be assigned to the specified key.
	 */
	public void setPagingKeyValue(String columnName, Object value)
	{
		nextKeys.put(columnName, value);
	}

	/** Get a key value that is used in the paging mechanism for next page retrieval.
	 * @param columnName The Column name to get it's value.
	 * @return Object The specified key value.
	 */
	public Object getPagingKeyValue(String columnName)
	{
		return nextKeys.get(columnName);
	}

	/** Parse the SQL statement and replaces the <paging:where/> and the <paging:sort/> tags, to be
	 * the actual keys to use in the SQL query. These tags must be present in the SQL statement.
	 * If there are other conditions in the SQL WHERE clause  then the <paging:where/> tag must appear
	 * between the WHERE clause and these conditions. The <paging:sort/> tag must appear immediately after the
	 * ORDER BY clause and no other columns may be added to the ORDER BY clause.
	 * Example 1: SELECT * FROM MYFILE WHERE <paging:where/> AND NAME LIKE '%jon%' ORDER BY <paging:sort/>
	 * Example 2: SELECT * FROM MYFILE <paging:where/> ORDER BY <paging:sort/>
	 * these keys will be used in the SQL WHERE and ORDER BY clauses.
	 * @param pageExist Indication whether the page already fetched.
	 * @return The parsed SQL statement.
	 */
	private String parseStatementKeys(boolean pageExist)
	{
		StringBuffer sb = new StringBuffer(4096);
		// get the position of the tags in the statement
		int pagingWhereTagPos = statement.indexOf(PAGING_WHERE_TAG);
		int pagingSortTagPos =
			statement.indexOf(
				PAGING_SORT_TAG,
				pagingWhereTagPos + PAGING_WHERE_TAG.length());
		// append the WHERE part to the statement with the keys.
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
				if (pageExist && count == keyNames.size() - 1)
				{
					sb.append(current + ((ascend) ? ">=?" : "<=?"));
				}
				else
				{
					sb.append(current + ((ascend) ? ">?" : "<?"));
				}
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
		// append the ORDER BY part to the statement according to the keys in the requested order.
		for (int count = 0; count < keyNames.size(); count++)
		{
			if (count > 0)
			{
				sb.append(",");
			}
			boolean ascend = ((Boolean) keyOrder.get(count)).booleanValue();
			sb.append(
				" " + keyNames.get(count) + ((ascend) ? " asc" : " desc"));
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
		return getPage(0, connectionProvider);
	}

	/** Get the last requested page of rows.
	 * @return Page The last page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page lastPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		return getPage(keys.size() - 1, connectionProvider);
	}

	/** Get the next page of rows.
	 * @return Page The next page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page nextPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		return getPage(currentPage + 1, connectionProvider);
	}

	/** Get the previous page of rows.
	 * @return Page The previous page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page previousPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		return getPage(currentPage - 1, connectionProvider);
	}

	/** Get a page of rows.
	 * @param index The index of the page.
	 * @return Page The page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page getPage(int index, ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"CumulativePagingService, Request page index: " + index);

		if (index < 0 || index > keys.size() || (index > 0 && !hasMorePages))
			throw new PersistenceException(
				"Requested page number (" + index + ") is not valid.");

		boolean pageExist = (index < keys.size());
		SqlService sqlService =
			new SqlService(parseStatementKeys(pageExist), null);
		if (isFirstPage)
		{
			// For the first page we select the whole statement, 
			// without adding keys to the statement
			isFirstPage = false;
		}
		else
		{
			// should add the keys to the statement
			if (index >= keys.size())
			{
				// The page wasn't fetched before
				// set the starting parameters from the last fetched page
				for (int i = 0; i < keyNames.size(); i++)
				{
					sqlService.addParameter(nextKeys.get(keyNames.get(i)));
					if (i < keyNames.size() - 1)
					{
						sqlService.addParameter(nextKeys.get(keyNames.get(i)));
					}
				}
			}
			else
			{
				// The page was fetched before
				// set the starting parameters from the first row of this page
				for (int i = 0; i < keyNames.size(); i++)
				{
					sqlService.addParameter(
						((Map) keys.get(index)).get(keyNames.get(i)));
					if (i < keyNames.size() - 1)
					{
						sqlService.addParameter(
							((Map) keys.get(index)).get(keyNames.get(i)));
					}
				}
			}
		}

		sqlService.addParameters(sqlServiceParams);

		// execute the statement with/without keys & build a Page with rows 
		// from the first row of the ResultSet to number of rows in page (if retrieved).
		Page page = Query.execute(sqlService, connectionProvider, rowsInPage);
		boolean newIndex = false;
		if (page.getRowCount() > 0)
		{
			if (index >= keys.size())
			{
				newIndex = true;
				keys.add(new HashMap());
			}
			page.first();
			for (int i = 0; i < keyNames.size(); i++)
			{
				((Map) keys.get(index)).put(
					keyNames.get(i),
					page.getObject((String) keyNames.get(i)));
			}

			if (newIndex)
			{
				page.last();
				for (int i = 0; i < keyNames.size(); i++)
				{
					nextKeys.put(
						keyNames.get(i),
						page.getObject((String) keyNames.get(i)));
				}
			}
		}
		page.beforeFirst();
		hasMorePages = page.hasMorePages();
		currentPage = index;
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Requested page contains " + page.getRowCount() + " rows");
		return page;
	}

	/** Get the last page request type (first, last, next, previous).
	 * @return int The last page request type (first, last, next, previous).
	 */
	public int getCurrentPage()
	{
		return currentPage;
	}

	/** Get the number of pages in the service until now.
	 * @return int The number of pages in the service until now.
	 */
	public int getPageCount()
	{
		return keys.size();
	}

	/** Is the specified operation valid based on the last operation
	 * results.
	 * @param operation The requested operation. Can be one of the following: GET_NEXT, GET_PREVIOUS, GET_FIRST, GET_LAST.
	 * @return boolean Whether the operation is valid or not.
	 */
	public boolean isOperationAllowed(int operation)
	{
		if (operation == GET_NEXT)
		{
			return hasMorePages;
		}
		else if (operation == GET_PREVIOUS)
		{
			return currentPage > 0;
		}
		else if (operation == GET_FIRST)
		{
			return true;
		}
		else if (operation == GET_LAST)
		{
			return keys.size() > 0;
		}
		return false;
	}

}