/*
 * Author: yifat har-nof
 * @version $Id: Query.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import java.sql.*;

import com.ness.fw.persistence.exceptions.LockException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Execute an {@link SqlService} and return the results in a {@link Page}
 */
public class Query
{

	/** Execute the query with the default limit of rows returned.
	 * @param sqlService The SqlService to use.
	 * @param connectionProvider The ConnectionProvider to use its connection.
	 * @return Page The query results as a {@link Page} object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static Page execute(
		SqlService sqlService,
		ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		return execute(sqlService, connectionProvider, 0);
	}

	/** Execute the query and limit the number of rows returned.
	 * @param sqlService The SqlService to use.
	 * @param connectionProvider The ConnectionProvider to use its connection.
	 * @param maxRowsInPage The maximum number of rows to return in the page.
	 * @return Page The query results as a {@link Page} object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static Page execute(
		SqlService sqlService,
		ConnectionProvider connectionProvider,
		int maxRowsInPage)
		throws LockException, PersistenceException
	{
		Page page = null;
		try
		{
			long startTime = System.currentTimeMillis();
			int maxRows = maxRowsInPage;
			if (maxRowsInPage > 0)
			{ // add 1 row to know if exist more pages.
				maxRows = maxRowsInPage + 1;
			}

			PreparedStatement pst =
				sqlService.getPreparedStatement(connectionProvider, maxRows);
			ResultSet rs = pst.executeQuery();

			if (maxRowsInPage > 0)
				maxRows = maxRowsInPage;
			else
				maxRows = sqlService.getMaxRowsInStatement();

			page = new Page(rs, maxRows);
			rs.close();
			pst.close();

			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"query execution time ["
					+ (System.currentTimeMillis() - startTime)
					+ "]");
		}
		catch (SQLException sqle)
		{
			if (sqle.getErrorCode()
				== connectionProvider.getIntConnectionProperty(
					PersistenceConstants.SQLCODE_LOCKED_RECORD))
			{
				throw new LockException(
					"The record is locked by another user",
					sqle);
			}

			PersistenceException pe = new PersistenceException(sqle);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error while executing statement: "
					+ sqlService.getStatementString()
					+ pe);
			throw pe;
		}
		return page;
	}

}