/*
 * Created on: 03/04/2005
 * Author: yifat har-nof
 * @version $Id: RandomRowNavigationService.java,v 1.4 2005/04/05 09:57:05 yifat Exp $
 */
package com.ness.fw.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Random row navigation service on query results with open connection to the db.
 * Provide random access to the query results rows.
 * 
 * The query will be executed on the constructor (on loadOnStart=true) or 
 * when begin method is executed.
 *   
 * In the end of the query processing you should execute "end" method to 
 * release the resources.
 */
public class RandomRowNavigationService extends RowNavigationService
{

	private static final String LOGGER_CONTEXT =
		PersistenceConstants.LOGGER_CONTEXT + "RANDOM RNS";

	/**
	 * Creates new RandomRowNavigationService
	 * @param sqlService The {@link SqlService} object to exceute
	 * @param connectionProvider The ConnectionProvider to use its connection.
	 * @param loadOnStart Indicates whether to load the query results.  
	 * @throws PersistenceException
	 */
	public RandomRowNavigationService(
		SqlService sqlService,
		ConnectionProvider connectionProvider,
		boolean loadOnStart)
		throws PersistenceException
	{
		super(sqlService, connectionProvider, loadOnStart);
	}

	/**
	 * load the result set.
	 */
	protected void loadResultSet() throws PersistenceException
	{
		try
		{
			long startTime = System.currentTimeMillis();

			ResultSet rs =
				getSqlService().getScrollableResultSet(getConnectionProvider());

			// move the cursor to the first row of the ResultSet
			rs.beforeFirst();

			setResultSet(rs);
			Logger.debug(
				LOGGER_CONTEXT,
				"load execution time ["
					+ (System.currentTimeMillis() - startTime)
					+ "]");
		}
		catch (SQLException sqle)
		{
			Logger.error(LOGGER_CONTEXT, "load failed. " + sqle.getMessage());
			throw new PersistenceException(sqle);
		}
	}

	/** 
	 * Set the cursor position before the first row.
	 * @throws PersistenceException
	 */
	public void beforeFirst() throws PersistenceException
	{
		try
		{
			getResultSet().beforeFirst();
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Set the cursor position after the last row.
	 * @throws PersistenceException
	 */
	public void afterLast() throws PersistenceException
	{
		try
		{
			getResultSet().afterLast();
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Set the cursor position to the first row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException
	 */
	public boolean first() throws PersistenceException
	{
		try
		{
			return getResultSet().first();
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Set the cursor position to the last row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException
	 */
	public boolean last() throws PersistenceException
	{
		try
		{
			return getResultSet().last();
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Set the cursor position to the previous row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException
	 */
	public boolean previous() throws PersistenceException
	{
		try
		{
			return getResultSet().previous();
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Moves the cursor a relative number of rows, either positive or negative.
	 * Attempting to move beyond the first/last row in the
	 * result set positions the cursor before/after the
	 * the first/last row. Calling <code>relative(0)</code> is valid, but does
	 * not change the cursor position.
	 * @param numOfRows number of rows
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException
	 */
	public boolean relative(int numOfRows) throws PersistenceException
	{
		try
		{
			return getResultSet().relative(numOfRows);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Set the cursor position to the given row number.
	 * @param rowNum row number
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException
	 */
	public boolean absolute(int rowNum) throws PersistenceException
	{
		try
		{
			return getResultSet().absolute(rowNum);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

}
