/*
 * Author: yifat har-nof
 * @version $Id: RandomPagingService.java,v 1.2 2005/04/04 09:35:01 yifat Exp $
 */
package com.ness.fw.persistence;

import java.sql.*;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Provide random access to pages of rows within a {@link ResultSet}.
 * In any page request, perform all the sql statement and return the requested page.
 */
public class RandomPagingService extends BasicPagingService
{

	/** The initial SqlService to use.
	 */
	private SqlService sqlService;

	/** The number of rows in the Result Set.
	 */
	private int rowCount = -1;

	/** The number of pages in the Result Set.
	 */
	private int pageCount = -1;

	/** The current Page number.
	 */
	private int currentPage = -1;

	/** Create a new PagingService
	 * @param sqlService The SqlService to use.
	 * @param rowsInPage The number of rows in each page.
	 * @param connectionManagerName The name of connection manager to use.
	 */
	public RandomPagingService(SqlService sqlService, int rowsInPage)
	{
		super(rowsInPage);
		this.sqlService = sqlService;
	}

	/** Get the number of rows in the resultset.
	 * @return int The number of rows in the resultset.
	 * @throws PersistenceException Any PersistenceException that may ocurr.
	 */
	public int getRowCount(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		if (rowCount == -1)
		{
			try
			{
				ResultSet rs =
					sqlService.getScrollableResultSet(connectionProvider);
				rs.last();
				rowCount = rs.getRow();
				rs.close();
			}
			catch (SQLException sqle)
			{
				throw new PersistenceException(sqle);
			}
		}
		return rowCount;
	}

	/** Get the number of pages in the resultset.
	 * @return int The number of pages in the resultset.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public int getPageCount(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		if (pageCount == -1)
		{
			getRowCount(connectionProvider);
			pageCount =
				rowCount / rowsInPage + (rowCount % rowsInPage > 0 ? 1 : 0);
		}
		return pageCount;
	}

	/** Get a Specific page from the resultset.
	 * @param pageNumber The requested Page number.
	 * @return Page The requested page from the resultset.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page getPage(int pageNumber, ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"RandomPagingService, Request page index: " + pageNumber);
		long startTime = System.currentTimeMillis();

		getPageCount(connectionProvider);
		if (pageNumber >= pageCount || pageNumber < 0)
		{
			throw new PersistenceException(
				"Requested page number ("
					+ pageNumber
					+ ") is not valid. (page count = "
					+ pageCount
					+ ")");
		}

		Page page = null;
		try
		{
			ResultSet rs =
				sqlService.getScrollableResultSet(connectionProvider, 0);
			// move the cursor to the first row of the page
			if (pageNumber == 0)
			{
				rs.beforeFirst();
			}
			else
			{
				rs.absolute(pageNumber * rowsInPage);
			}
			page = new Page(rs, rowsInPage);
			rs.close();
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"query execution time ["
					+ (System.currentTimeMillis() - startTime)
					+ "]");
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"Requested page contains " + page.getRowCount() + " rows");
		}
		catch (SQLException sqle)
		{
			PersistenceException pe = new PersistenceException(sqle);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Get page: " + pageNumber + " failed. " + pe.getMessage());
			throw pe;
		}
		hasMorePages = page.hasMorePages();
		currentPage = pageNumber;
		return page;
	}

	/** Get the current Page from the resultset
	 * @return Page The current Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page getPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		return getPage(currentPage, connectionProvider);
	}

	/** Set the cursor position before the first page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void beforeFirst()
	{
		currentPage = -1;
	}

	/** Set the cursor position after the last page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void afterLast(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		getPageCount(connectionProvider);
		currentPage = pageCount;
	}

	/** Set the cursor position to the first page.
	 * @return boolean <B>True</B> - a valid page found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean first(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		getPageCount(connectionProvider);
		currentPage = 0;
		return pageCount > 0;
	}

	/** Set the cursor position to the last page.
	 * @return boolean <B>True</B> - a valid page found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean last(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		getPageCount(connectionProvider);
		currentPage = pageCount - 1;
		return pageCount > 0;
	}

	/** Set the cursor position to the next page.
	 * @return boolean <B>True</B> - a valid page found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean next(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		getPageCount(connectionProvider);
		currentPage++;
		return currentPage >= 0 && currentPage < pageCount && pageCount > 0;
	}

	/** Set the cursor position to the previous page.
	 * @return boolean <B>True</B> - a valid page found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean previous(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		getPageCount(connectionProvider);
		currentPage--;
		return currentPage >= 0 && currentPage < pageCount && pageCount > 0;
	}

	/** Set the cursor position to the page number specified.
	 * @param pageNumber The requested page number.
	 * @return boolean <B>True</B> - a valid page found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean absolute(
		int pageNumber,
		ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		getPageCount(connectionProvider);
		currentPage = pageNumber;
		return currentPage >= 0 && currentPage < pageCount && pageCount > 0;
	}

	/** Set the cursor position specified number of
	 * pages from the current cursor position.
	 * @param numberOfPages The number of pages from the current page to set the
	 * cursor position to.
	 * @return boolean <B>True</B> - a valid page found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean relative(
		int numberOfPages,
		ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		getPageCount(connectionProvider);
		currentPage += numberOfPages;
		return currentPage >= 0 && currentPage < pageCount && pageCount > 0;
	}

	/** Get the first page of rows.
	 * @return Page The first page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page firstPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		first(connectionProvider);
		return getPage(connectionProvider);
	}

	/** Get the last page of rows.
	 * @return Page The last page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page lastPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		last(connectionProvider);
		return getPage(connectionProvider);
	}

	/** Get the next page of rows.
	 * @return Page The next page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page nextPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		next(connectionProvider);
		return getPage(connectionProvider);
	}

	/** Get the previous page of rows.
	 * @return Page The previous page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Page previousPage(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		previous(connectionProvider);
		return getPage(connectionProvider);
	}

	/** Is the specified operation valid based on the last operation
	 * results.
	 * @param operation The requested operation. Can be one of the following: GET_NEXT, GET_PREVIOUS, GET_FIRST, GET_LAST.
	 * @return boolean Whether the operation is valid or not.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean isOperationAllowed(int operation)
		throws PersistenceException
	{
		if (pageCount == -1)
		{
			throw new PersistenceException("Page count was not loaded. please call to getPageCount method.");
		}

		if (operation == GET_NEXT)
		{
			if (currentPage < pageCount - 1 && pageCount > 0)
			{
				return true;
			}
		}
		else if (operation == GET_PREVIOUS)
		{
			if (currentPage > 0 && pageCount > 0)
			{
				return true;
			}
		}
		else if (operation == GET_FIRST)
		{
			if (pageCount > 0)
			{
				return true;
			}
		}
		else if (operation == GET_LAST)
		{
			if (pageCount > 0)
			{
				return true;
			}
		}
		return false;
	}

}