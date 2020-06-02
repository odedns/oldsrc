/*
 * Author: yifat har-nof
 * @version $Id: AbstractSqlService.java,v 1.6 2005/04/19 14:47:47 yifat Exp $
 */
package com.ness.fw.persistence;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

/**
 * Provide basic implementation of a paging service.
 */
public abstract class AbstractSqlService implements Serializable
{
	/** 
	 * The default maximum number of rows to select in statement.
	 * it takes place only if nothing overrides it.
	 */
	public static final int MAX_ROWS_IN_STATEMENT = 10000;

	protected static final String NULL_VALUE = "null";
	protected static final String PARAMETER_PLACE_HOLDER = "?";

	/** 
	 * The original statement string to execute.
	 */
	private String statementString = null;

	/**  
	 * The parameters list to be used in the statement.
	 */
	private List parameters = null;

	/** 
	 * Query Timeout for this sql Service in seconds. default is do not use.
	 */
	private int queryTimeout = -1;

	/**
	 * The maximum rows allows to fetch in the statement.
	 */
	private int maxRowsInStatement = -1;

	/**
	 * Constructor for AbstractSqlService.
	 */
	public AbstractSqlService() throws PersistenceException
	{
		super();
	}

	/** 
	 * Set the Query timeout for this SQL Service.
	 * @param seconds The query timeout limit in seconds; zero means unlimited, -1 means do not use.
	 */
	public void setQueryTimeout(int seconds)
	{
		queryTimeout = seconds;
	}

	/** 
	 * Retruns the Query timeout for this SQL Service.
	 * @return The query timeout limit in seconds; zero means unlimited, -1 means do not use.
	 */
	public int getQueryTimeout()
	{
		return queryTimeout;
	}

	/**
	 * Returns the maximum rows allows to fetch in the statement.
	 * @return int
	 */
	protected int getMaxRowsInStatement()
	{
		return maxRowsInStatement;
	}

	/**
	 * Returns the statementString.
	 * @return String The base Sql statement to execute.
	 */
	public String getStatementString()
	{
		return statementString;
	}

	/**
	 * Sets the base Sql statement string.
	 * @param statementString The statementString to set
	 */
	public void setStatementString(String statementString)
	{
		this.statementString = statementString;
	}

	/** 
	 * Remove a specific parameter from the parameter list
	 * @param index The parameter index to remove.
	 */
	public void removeParameter(int index)
	{
		parameters.remove(index);
	}

	/** 
	 * Get the number of parameters in the parameters list.
	 * @return int The number of parameters in the parameters list.
	 */
	public int getParametersCount()
	{
		return parameters == null ? 0 : parameters.size();
	}

	/** 
	 * Get a list of all parameters in the parameters list.
	 * @return List The parameters list.
	 */
	public List getParameters()
	{
		if (parameters == null)
			parameters = new ArrayList();
		return parameters;
	}

	/** 
	 * Remove all of the parameters from the parameters list.
	 */
	public void clearParameters()
	{
		if (parameters != null)
			parameters.clear();
	}

	/** 
	 * Append a list of parameters to the end of the current parameters list.
	 * @param parameters The parameters list to add.
	 */
	public abstract void addParameters(List parameters);

	/** 
	 * Get a specific parameter value.
	 * @param index The parameter index to get the value of.
	 * @return Object The value of the specified parameter.
	 */
	public abstract Object getParameter(int index);

	/**
	 * Return a {@link PreparedStatement} based on the basic SQL statement
	 * with the parameters embedded. The method uses the same instance of 
	 * the PreparedStatement object, instead of creating new object every call..
	 * @param connectionProvider The database connection to use.
	 * @param newParameters The list of parameters to use in the statement.
	 * @return PreparedStatement The {@link PreparedStatement} object with the parameters embedded.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected abstract PreparedStatement prepareStatement(
		ConnectionProvider connectionProvider,
		List newParameters)
		throws PersistenceException;

	/**
	 * Clears the preparedStatement object.
	 */
	protected abstract void clearPreparedStatement();

	/** 
	 * Parse the value of a parameter to String including apostrophes in String objects.
	 * @param index The index of the parameter to parse.
	 * @return String The parsed parameter value as String.
	 */
	protected abstract String parseValue(int index);

	/**
	 * Get a specific parameter value.
	 * @param index The parameter index to get the value of.
	 * @return Object The value of the specified parameter.
	 */
	protected final Object getParameterInner(int index)
	
	{
		if (parameters != null)
		{
			return parameters.get(index);
		}
		return null;
	}

	/** 
	 * Add a parameter to the parameters list to be used in the prepared statement.
	 * @param parameter The parameter value to add.
	 */
	protected final void addParameterInner(Object parameter)
	{
		parameters.add(parameter);
	}

	/** 
	 * Set value to a specific parameter.
	 * @param index The parameter index to set.
	 * @param parameter The value of the parameter.
	 */
	protected final void setParameterInner(int index, Object parameter)
	{
		parameters.set(index, parameter);
	}

	/** 
	 * Set the list of parameters to the end of the current parameters list.
	 * @param parameters The parameters list to add.
	 */
	protected final void setParameters(List parameters)
	{
		this.parameters = parameters;
	}

	/** 
	 * Parse the value of an Object to String including apostrophes in String objects.
	 * @param obj The Object to parse.
	 * @return String The parsed parameter value as String.
	 */
	protected final String parseValue(Object obj)
	{
		if (obj == null)
		{
			return NULL_VALUE;
		}
		else if (obj instanceof String)
		{
			return DBUtil.formatString((String) obj);
		}
		else if (obj instanceof Boolean)
		{
			return DBUtil.formatBoolean((Boolean) obj);
		}
		else if (obj instanceof Timestamp)
		{
			return DBUtil.formatTimestamp((Timestamp) obj);
		}
		else if (obj instanceof Time)
		{
			return DBUtil.formatTime((Time) obj);
		}
		else if (obj instanceof java.util.Date)
		{
			return DBUtil.formatDate((java.util.Date) obj);
		}
		else
		{
			return obj.toString();
		}
	}

	/**
	 * Build an SQL statement with the parameters embeded instead of the ? marks.
	 * This is because certain sql drivers does not support the ? convention in some cases.
	 * @param onlyNullValues indication whether to handle only the parameters with null values or all the parameters.
	 * @return String The SQL statmenet with the parameters embeded.
	 */
	protected final String buildSQL()
	{
		StringBuffer sb = new StringBuffer(4096);
		int questionMark = 0;
		int lastPos = 0;
		for (int i = 0; questionMark != -1; i++)
		{
			questionMark =
				statementString.indexOf(PARAMETER_PLACE_HOLDER, lastPos);
			if (questionMark != -1)
			{
				sb.append(statementString.substring(lastPos, questionMark));
				sb.append(parseValue(i));
				lastPos = questionMark + 1;
			}
		}
		if (lastPos < statementString.length())
		{
			sb.append(
				statementString.substring(lastPos, statementString.length()));
		}

		if (SystemResources.getInstance().isDebugMode())
		{
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"Execute statement: " + sb.toString());
		}

		return sb.toString();
	}

	/**
	 * Print the SQL statement with the parameters embeded instead of the ? marks to stdout.
	 */
	public final void printSQL()
	{
		printSQL(buildSQL());
	}

	/** 
	 * Print the parsed SQL string to stdout.
	 * @param statement
	 */
	public final void printSQL(String statement)
	{
		if (SystemResources.getInstance().isDebugMode())
		{

			StringBuffer stringBuffer = new StringBuffer(1024); 
			stringBuffer.append("About to execute:\r\n");
			stringBuffer.append("--------------------------------------------------------------------\r\n");
			int pos = 0;
			while (pos < statement.length())
			{
				int posLen = pos + 68;
				if (posLen > statement.length())
				{
					posLen = statement.length();
				}
				String part = statement.substring(pos, posLen);
				stringBuffer.append(part);
				stringBuffer.append("\r\n");
				pos = posLen;
			}
			stringBuffer.append("--------------------------------------------------------------------\r\n");
				
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT, stringBuffer.toString());
		}
			
	}

	/**
	 * Sets the maximum rows in the statement.
	 * @param maxRows Max rows in the statement.
	 */
	protected final void setMaxRowsInStatement(int maxRows)
	{
		if (maxRows > 0)
		{
			maxRowsInStatement = maxRows;
		}
	}

	/**
	 * Sets the maximum rows in the statement according to the given maxRows & 
	 * the application maxRows.
	 * @param maxRows Max rows in the statement.
	 * @param connectionProvider
	 * @param statement
	 */
	protected final void setMaxRowsInStatement(
		int maxRows,
		ConnectionProvider connectionProvider,
		Statement statement)
		throws SQLException
	{
		setMaxRowsInStatement(maxRows);

		if (maxRowsInStatement <= 0)
		{
			// get connection manager default property
			String value =
				connectionProvider.getConnectionProperty(
					PersistenceConstants.GENERAL_MAX_ROWS_IN_STATEMENT);
			if (value != null)
				maxRowsInStatement = Integer.parseInt(value);
			else
				maxRowsInStatement = MAX_ROWS_IN_STATEMENT;
		}
		statement.setMaxRows(maxRowsInStatement);
	}

	/**
	 * Sets the query timeout for this sql Service in seconds. 
	 * If the current queryTimeout is not set, the application system property will be taken.
	 * @param connectionProvider
	 * @param statement
	 * @throws SQLException
	 */
	protected final void setQueryTimeout(
		ConnectionProvider connectionProvider,
		Statement statement)
		throws SQLException
	{
		if (queryTimeout < 0)
		{
			// get connection manager default property
			String value =
				connectionProvider.getConnectionProperty(
					PersistenceConstants.GENERAL_QUERY_TIMEOUT);
			if (value != null)
				queryTimeout = Integer.parseInt(value);
		}

		if (queryTimeout >= 0)
		{
			statement.setQueryTimeout(queryTimeout);
		}
	}
}
