/*
 * Author: yifat har-nof
 * @version $Id: SqlService.java,v 1.5 2005/04/04 09:37:58 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.*;
import java.sql.*;
import java.util.ArrayList;

import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

/**
 * Helper class for constructing SQL statements.
 */
public class SqlService extends AbstractSqlService
{

	/** A constant that says that like statement will search a value 
	 * that contains the parameter value.
	 */
	public static final int LIKE_CONTAINS = 1;

	/** A constant that says that like statement will search a value 
	 * that starts with the parameter value
	 */
	public static final int LIKE_STARTS_WITH = 2;

	/** A constant that says that like statement will search a value 
	 * that ends with the parameter value
	 */
	public static final int LIKE_ENDS_WITH = 3;

	/** The {@link PreparedStatement} object to exceute
	 */
	private PreparedStatement preparedStatement = null;

	/** Create a new SqlService without parameters.
	 */
	protected SqlService() throws PersistenceException
	{
		this(null, null);
	}

	/** Create a new SqlService.
	 * @param statementString The base Sql statement to execute.
	 */
	public SqlService(String statementString) throws PersistenceException
	{
		this(statementString, null);
	}

	/** Create a new SqlService with a given list of parameters.
	  * @param statementString The base Sql statement to execute.
	  * @param parameters The parameters to use replacing the <B>"?"</B> placeholders.
	  */
	public SqlService(String statementString, List parameters)
		throws PersistenceException
	{
		setStatementString(statementString);
		if (parameters == null)
		{
			setParameters(new ArrayList(0));
		}
		else
		{
			setParameters(new ArrayList(parameters));
		}
	}

	/**
	 * Add a parameter to the parameters list to be used in the prepared statement.
	 * The parameter should be included in LIKE statement 
	 * and the search will be performed according to the given likeType .
	 * @param parameter The parameter value to add.
	 * @param likeType. can be LIKE_CONTAINS, LIKE_STARTS_WITH or LIKE_ENDS_WITH.
	 */
	public void addLikeParameter(
		Object parameter,
		int likeType,
		ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		String value = parameter.toString();

		String likeSign =
			connectionProvider.getConnectionProperty(
				PersistenceConstants.GENERAL_LIKE_SIGN);
		if (likeSign == null)
		{
			throw new PersistenceException(
				"Attribute "
					+ PersistenceConstants.GENERAL_LIKE_SIGN
					+ " was not found in the connection manager properties file.");
		}

		if (likeType == LIKE_CONTAINS)
			value = likeSign + value + likeSign;
		else if (likeType == LIKE_STARTS_WITH)
			value = value + likeSign;
		else if (likeType == LIKE_ENDS_WITH)
			value = likeSign + value;

		addParameterInner(value);
	}

	/** Add a parameter to the parameters list to be used in the prepared statement.
	 * @param parameter The parameter value to add.
	 */
	public void addParameter(Object parameter)
	{
		addParameterInner(parameter);
	}

	/** Append a list of parameters to the end of the current parameters list.
	 * @param parameters The parameters list to add.
	 */
	public void addParameters(List parameters)
	{
		if (parameters != null && parameters.size() > 0)
		{
			getParameters().addAll(parameters);
		}
	}

	/** Set a parameter to a specific value.
	 * @param index The parameter index to set.
	 * @param parameter The Object to set the parameter to.
	 */
	public void setParameter(int index, Object parameter)
	{
		setParameterInner(index, parameter);
	}

	/** Get a specific parameter value.
	 * @param index The parameter index to get the value of.
	 * @return Object The value of the specified parameter.
	 */
	public Object getParameter(int index)
	{
		return getParameterInner(index);
	}

	/** Parse the value of a parameter to String including apostrophes in String objects.
	 * @param index The index of the parameter to parse.
	 * @return String The parsed parameter value as String.
	 */
	protected String parseValue(int index)
	{
		Object obj = getParameterInner(index);
		return parseValue(obj);
	}

	/**
	 * Parse and return a scrollable statement based on the base SQL statment
	 * with the parameters embeded.
	 * @param connection The database connection to use.
	 * @param maxRows Maximum rows to include in the resultset.
	 * @return ResultSet The scrallable statement after execution.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected ResultSet getScrollableResultSet(
		ConnectionProvider connectionProvider,
		int maxRows)
		throws PersistenceException
	{
		try
		{
			boolean allowScrollable =
				TypesUtil.convertStringToBoolean(
					connectionProvider.getConnectionProperty(
						PersistenceConstants.GENERAL_ALLOW_SCROLLBALE_PS));
			ResultSet rs = null;

			if (allowScrollable)
			{
				PreparedStatement ps =
					connectionProvider.getConnection().prepareStatement(
						getStatementString(),
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				setStatementParameters(ps, connectionProvider, maxRows);
				rs = ps.executeQuery();
				if (SystemResources.getInstance().isDebugMode())
				{
					buildSQL();
				}
			}
			else
			{
				Statement s =
					connectionProvider.getConnection().createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				s.setMaxRows(maxRows);
				rs = s.executeQuery(buildSQL());
			}

			return rs;
		}
		catch (SQLException sqle)
		{
			PersistenceException pe = new PersistenceException(sqle);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error while executing statement: "
					+ getStatementString()
					+ "\n"
					+ pe);
			throw pe;
		}
	}

	/**
	 * Parse and return a scrollable statement based on the base SQL statment
	 * with the parameters embeded.
	 * @param connectionProvider The database connection to use.
	 * @return ResultSet The scrollable statement after execution.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected ResultSet getScrollableResultSet(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		return getScrollableResultSet(connectionProvider, 0);
	}

	/**
	 * Return a prepared statement based on the base SQL statment
	 * with the parameters embeded.
	 * @param connection The database connection to use.
	 * @param maxRows Maximum rows to include in the resultset.
	 * @return PreparedStatement The prepared statement.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected PreparedStatement getPreparedStatement(
		ConnectionProvider connectionProvider,
		int maxRows)
		throws PersistenceException
	{
		try
		{
			PreparedStatement pStatement =
				connectionProvider.getConnection().prepareStatement(
					getStatementString());
			setStatementParameters(pStatement, connectionProvider, maxRows);

			if (SystemResources.getInstance().isDebugMode())
			{
				buildSQL();
			}

			return pStatement;
		}
		catch (Throwable e)
		{
			PersistenceException pe = new PersistenceException(e);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error while preparing statement: "
					+ getStatementString()
					+ "\n"
					+ pe);
			throw pe;
		}

	}

	/**
	 * set the parameters in the given PreparedStatement.
	 * @param pStatement The prepared statement to set the parameters.
	 * @param connection The database connection to use.
	 * @param maxRows Maximum rows to include in the resultset.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private void setStatementParameters(
		PreparedStatement pStatement,
		ConnectionProvider connectionProvider,
		int maxRows)
		throws PersistenceException
	{
		try
		{
			DBUtil.setStatementParameters(pStatement, getParameters());

			// handle statement max rows			
			setMaxRowsInStatement(maxRows, connectionProvider, pStatement);

			// handle query timeout
			setQueryTimeout(connectionProvider, pStatement);

		}
		catch (Throwable e)
		{
			PersistenceException pe = new PersistenceException(e);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error while setting statement parameters for: "
					+ getStatementString()
					+ "\n"
					+ pe);
			throw pe;
		}
	}

	/** Return a prepared statement based on the base SQL statment
	 * with the parameters embeded.
	 * @param ConnectionProvider The database connection to use.
	 * @return PreparedStatement The prepared statement.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected PreparedStatement getPreparedStatement(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		return getPreparedStatement(connectionProvider, 0);
	}

	/**
	 * Return a prepared statement based on the basic SQL statement
	 * with the parameters embedded. The method uses the same instance of 
	 * the PreparedStatement object, instead of creating new object every call..
	 * @param ConnectionProvider The database connection to use.
	 * @param newParameters The list of parameters to use in the statement.
	 * @return PreparedStatement The {@link PreparedStatement} object with the parameters embedded.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected PreparedStatement prepareStatement(
		ConnectionProvider connectionProvider,
		List newParameters)
		throws PersistenceException
	{
		clearParameters();
		addParameters(newParameters);
		if (preparedStatement == null)
		{
			preparedStatement = getPreparedStatement(connectionProvider);
		}
		else
		{
			setStatementParameters(preparedStatement, connectionProvider, 0);
		}

		return preparedStatement;
	}

	/**
	 * Clears the preparedStatement object.
	 */
	protected void clearPreparedStatement()
	{
		preparedStatement = null;
	}

}