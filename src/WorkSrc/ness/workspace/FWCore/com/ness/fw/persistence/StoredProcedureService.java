/*
 * Author: yifat har-nof
 * @version $Id: StoredProcedureService.java,v 1.5 2005/04/05 09:39:53 yifat Exp $
 */
package com.ness.fw.persistence;

import java.sql.*;
import java.util.*;

import com.ness.fw.persistence.connectionmanager.*;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

/**
 * Execute a Stored Procedure.
 * Support SP with multiple Result Sets & output parameters. 
 */
public class StoredProcedureService extends AbstractSqlService
{

	/** 
	 * The constant indicates that the parameter is input.
	 */
	private static final int INPUT = 1;

	/** 
	 * The constant indicates that the parameter is output.
	 */
	private static final int OUTPUT = 2;

	/** 
	 * The constant indicates that the parameter is input/output.
	 */
	private static final int INPUT_OUTPUT = 3;

	/** 
	 * Integer type for output parameter.
	 */
	public static final int INTEGER = Types.INTEGER;

	/** 
	 * Decimal type for output parameter.
	 */
	public static final int DECIMAL = Types.DECIMAL;

	/** 
	 * Long type for output parameter.
	 */
	public static final int LONG = Types.BIGINT;

	/** 
	 * Double type for output parameter.
	 */
	public static final int DOUBLE = Types.DOUBLE;

	/** 
	 * Float type for output parameter.
	 */
	public static final int FLOAT = Types.FLOAT;

	/** 
	 * Date type for output parameter.
	 */
	public static final int DATE = Types.DATE;

	/** 
	 * String type for output parameter.
	 */
	public static final int STRING = Types.VARCHAR;

	/** 
	 * TimeStamp type for output parameter.
	 */
	public static final int TIMESTAMP = Types.TIMESTAMP;

	/** 
	 * Time type for output parameter.
	 */
	public static final int TIME = Types.TIME;

	/** 
	 * The {@link CallableStatement} object to exceute
	 */
	private CallableStatement callableStatement = null;

	/** 
	 * The connection manager to use.
	 */
	private ConnectionManager connectionManager = null;

	/** 
	 * Create a new StoredProcedureService without parameters.
	 */
	public StoredProcedureService() throws PersistenceException
	{
		this(null, null);
	}

	/** Create a new StoredProcedureService.
	 * @param statementString The stored procedure call statement string to execute.
	 * @param connectionManagerName The name of connection manager to use.
	 */
	public StoredProcedureService(
		String statementString,
		String connectionManagerName)
		throws PersistenceException
	{
		super();

		setStatementString(statementString);

		connectionManager =
			ConnectionManagerFactory.getConnectionManager(
				connectionManagerName);

		setParameters(new ArrayList());
	}

	/** Create a new StoredProcedureService with default connection manager.
	 * @param statementString The stored procedure call statement string to execute.
	 */
	public StoredProcedureService(String statementString)
		throws PersistenceException
	{
		this(statementString, null);
	}

	/** set an input parameter to a specific value.
	 * @param index The parameter index to set.
	 * @param value The value of the parameter.
	 */
	public final void setInputParameter(int index, Object value)
	{
		setParameterInner(index, new ParamDetails(value, INPUT));
	}

	/** set an input/output parameter to a specific value.
	 * @param index The parameter index to set.
	 * @param value The value of the parameter.
	 */
	public final void setInputOutputParameter(int index, Object value)
	{
		setParameterInner(index, new ParamDetails(value, INPUT_OUTPUT));
	}

	/** set an output parameter to a specific sql type.
	 * @param index The parameter index to set.
	 * @param sqlType The type of the output parameter.
	 */
	public final void setOutputParameter(int index, int sqlType)
	{
		setParameterInner(index, new ParamDetails(sqlType, OUTPUT));
	}

	/** Add an input parameter to the parameters list to be used in the call statement.
	 * @param value The value of the parameter.
	 */
	public final void addInputParameter(Object value)
	{
		addParameterInner(new ParamDetails(value, INPUT));
	}

	/** Add an input/output parameter to the parameters list to be used in the call statement.
	 * @param value The value of the parameter.
	 */
	public final void addInputOutputParameter(Object value)
	{
		addParameterInner(new ParamDetails(value, INPUT_OUTPUT));
	}

	/**
	 * Add an output parameter to the parameters list to be used in the call statement.
	 * @param sqlType The type of the output parameter.
	 */
	public final void addOutputParameter(int sqlType)
	{
		addParameterInner(new ParamDetails(sqlType, OUTPUT));
	}

	/** 
	 * Get a specific parameter value. Only for Input or In/Out parameters.
	 * @param index The parameter index to get the value of.
	 * @return Object The value of the specified parameter.
	 */
	public final Object getParameter(int index)
	{
		return getParamDetails(index).getValue();
	}

	/** Append a list of INPUT parameters to the end of the current parameters list 
	 * @param parameters The input parameters list to add.
	 */
	public final void addParameters(List inputParameters)
	{
		// Set the parameters values into the statement & register the output parameters.
		for (int index = 0; index < inputParameters.size(); index++)
		{
			addInputParameter(inputParameters.get(index));
		}
	}

	/**
	 * Get a specific ParamDetails.
	 * @param index The parameter index to get the value of.
	 * @return ParamDetails
	 */
	private ParamDetails getParamDetails(int index)
	{
		return (ParamDetails) getParameterInner(index);
	}

	/**
	 * Sets the maximum rows in the result pages.
	 * @param maxRows Max rows.
	 */
	public final void setMaxRowsInResultPages(int maxRows)
	{
		setMaxRowsInStatement(maxRows);
	}

	/** 
	 * Execute the call statement to the stored procedure with the given parameters.
	 * @param connectionProvider The database connection to use.
	 * @return StoredProcedureResults The execution results.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public final StoredProcedureResults execute(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		int paramsCount = getParametersCount();
		int index;
		ParamDetails paramDetails;
		StoredProcedureResults results = null;
		ResultSet rs = null;
		boolean hasMoreResultSets;

		try
		{

			// create the Callable object with the call statement string.
			CallableStatement cStatement =
				connectionProvider.getConnection().prepareCall(
					getStatementString());

			// Set the parameters values into the statement & register the output parameters.
			setStatementParameters(cStatement, connectionProvider);

			// handle statement max rows			
			setMaxRowsInStatement(0, connectionProvider, cStatement);

			// handle query timeout
			setQueryTimeout(connectionProvider, cStatement);

			if (SystemResources.getInstance().isDebugMode())
			{
				buildSQL();
			}

			// execute the SP
			hasMoreResultSets = cStatement.execute();

			// create new results object
			results = new StoredProcedureResults();

			// convert the returning ResultSets into pages.
			while (hasMoreResultSets)
			{
				rs = cStatement.getResultSet();

				results.addPage(new Page(rs, getMaxRowsInStatement()));

				hasMoreResultSets = cStatement.getMoreResults();
			}

			// Collect the output values.
			for (index = 0; index < paramsCount; index++)
			{
				paramDetails = getParamDetails(index);
				if (paramDetails.isOutputParameter())
				{
					results.setOutputValue(
						index,
						cStatement.getObject(index + 1));
				}
			}

			cStatement.close();

			return results;

		}
		catch (SQLException sqle)
		{
			PersistenceException pe = new PersistenceException(sqle);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error while executing stored procedure: "
					+ getStatementString()
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
		ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		int paramsCount = getParametersCount();
		int index;
		ParamDetails paramDetails;
		int dbIndex;
		CallableStatement cStatement = (CallableStatement) pStatement;

		try
		{

			// Set the parameters values into the statement & register the output parameters.
			for (index = 0; index < paramsCount; index++)
			{
				paramDetails = getParamDetails(index);
				dbIndex = index + 1;

				// set the value of the parameter
				if (paramDetails.getParameterType() != OUTPUT)
				{
					DBUtil.setStatementParameter(
						cStatement,
						paramDetails.getValue(),
						dbIndex);
				}
				// register the output parameters with the sql type
				if (paramDetails.isOutputParameter())
				{
					cStatement.registerOutParameter(
						dbIndex,
						paramDetails.getSqlType());
				}
			}

		}
		catch (PersistenceException pe)
		{
			throw pe;
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/**
	 * Return a prepared {@link CallableStatement} based on the basic SQL statement
	 * with the parameters embedded. The method uses the same instance of 
	 * the PreparedStatement object, instead of creating new object every call..
	 * @param con The database connection to use.
	 * @param newParameters The list of parameters to use in the statement.
	 * @return PreparedStatement The {@link PreparedStatement} object with the parameters embedded.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected final PreparedStatement prepareStatement(
		ConnectionProvider connectionProvider,
		List newParameters)
		throws PersistenceException
	{
		try
		{

			clearParameters();
			addParameters(newParameters);

			if (callableStatement == null)
			{
				// create the Callable object with the call statement string.
				callableStatement =
					connectionProvider.getConnection().prepareCall(
						getStatementString());
			}

			setStatementParameters(callableStatement, connectionProvider);
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}

		return callableStatement;
	}

	/**
	 * Clears the preparedStatement object.
	 */
	protected final void clearPreparedStatement()
	{
		callableStatement = null;
	}

	/** Parse the value of a parameter to String including apostrophes in String objects.
	 * @param index The index of the parameter to parse.
	 * @return String The parsed parameter value as String.
	 */
	protected final String parseValue(int index)
	{
		return parseValue(getParameter(index));
	}

	/**
	 * This class contains details about a procedure parameter.
	 */
	class ParamDetails
	{

		/** The parameter value.
		 */
		private Object value = null;
		/** Indication whether the param is input, output or input/output.
		 */
		private int parameterType = -1;
		/** The parameter output value.
		 */
		private Object outputValue = null;

		private int sqlType = -1;

		/** Create a ParamDetails Object for input/input-output parameter.
		 * @param value The parameter value.
		 * @param parameterType indication whether the parameter is input, output or input/output
		 * parameter or only input
		 */
		ParamDetails(Object value, int parameterType)
		{
			this.value = value;
			this.parameterType = parameterType;
			if (parameterType == INPUT_OUTPUT)
			{
				sqlType = DBUtil.getObjectSqlType(value);
			}
		}

		/** Create a ParamDetails Object for output parameter.
		 * @param sqlType The SqlType of the output parameter.
		 * @param parameterType indication whether the parameter is input, output or input/output
		 */
		ParamDetails(int sqlType, int parameterType)
		{
			this.sqlType = sqlType;
			this.parameterType = parameterType;
		}

		/**
		 * Returns the Indication whether the parameter is output.
		 * @return boolean
		 */
		public boolean isOutputParameter()
		{
			return (parameterType != INPUT);
		}

		/**
		 * Returns the value of the parameter.
		 * @return Object
		 */
		public Object getValue()
		{
			return value;
		}

		/**
		 * Returns the output value of the parameter.
		 * @return Object
		 */
		public Object getOutputValue()
		{
			return outputValue;
		}

		/**
		 * Sets the output value of the parameter.
		 * @param outputValue The outputValue to set
		 */
		void setOutputValue(Object outputValue)
		{
			this.outputValue = outputValue;
		}

		/**
		 * Returns the outputParamSqlType.
		 * @return int
		 */
		public int getSqlType()
		{
			return sqlType;
		}

		/**
		 * Returns the parameterType.
		 * @return int
		 */
		public int getParameterType()
		{
			return parameterType;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			if (value == null)
				return NULL_VALUE;
			return value.toString();
		}

	}

}