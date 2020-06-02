/*
 * Author: yifat har-nof
 * @version $Id: Batch.java,v 1.2 2005/04/04 09:41:17 yifat Exp $
 */
package com.ness.fw.persistence;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import com.ness.fw.util.StringFormatterUtil;
import com.ness.fw.util.TypesUtil;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * This class provides an execution of a set of sql statements in batch.
 * This class support execution of multiple statement strings and for each support 
 * execution with several lists of parameters.
 */
public class Batch
{

	// ----- private constants -----

	private static final int INITIAL_STATEMENTS_NUMBER_OF_RECORDS = 5;

	private static final int INITIAL_IDENTITY_KEYS_AMOUNT = 5;

	private static final int INITIAL_STATEMENTS_AMOUNT = 5;

	private static final int INITIAL_STATEMENT_EXECUTIONS_AMOUNT = 10;

	/** A constant used to set the command type to execute with default command type.
	 */
	private static final int TYPE_USING_DEFAULT = 0;

	// ----- public constants -----

	/** A constant used to set the command type to execute with {@link Statement} object.
	 */
	public static final int TYPE_USING_STATEMENT = 1;

	/** A constant used to set the command type to execute with {@link PreparedStatement} object.
	 */
	public static final int TYPE_USING_PREPARED_STATEMENT = 2;

	/** A constant used to set the command type to Stored Procedure call, 
	 *  with {@link CallableStatement} object
	 */
	public static final int TYPE_STORED_PROCEDURE_CALL = 3;

	/** A constant used to set the command type to get the last value assigned 
	 *  in identity column.
	 */
	private static final int TYPE_GET_LAST_IDENTITY_KEY = 4;

	/** A constant indicates that the command was processed successfully 
	 *  but that the number of rows affected is unknown.
	 */
	public static final int COMMAND_SUCCESS_ROWS_AFFECTED_UNKONWN = -2;

	/** A constant indicates that the command failed to execute successfully. 
	 */
	public static final int COMMAND_FAILED = -3;

	/** A list of StatementDetails objects that contains the statement string.
	 */
	private ArrayList statements = null;

	/** A list of StatementExecutionDetails objects to execute in the order of the insertion to the list.
	 */
	private ArrayList executionStatements = null;

	/** The commands default type. The default value is TYPE_USING_STATEMENT.
	 */
	private int defaultCommandType = TYPE_USING_STATEMENT;

	/** A map contains an IdentityKey/Value of the identity columns that was assigned 
	 *  in this batch.
	 */
	private HashMap identityKeys = null;

	/**
	 * create a new Batch.
	 */
	public Batch() throws PersistenceException
	{
		super();
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"create new Batch Object");
	}

	/**
	 * Returns the defaultCommandType.
	 * @return int
	 */
	public int getDefaultCommandType()
	{
		return defaultCommandType;
	}

	/**
	 * Returns the number of execution statements.
	 * @return int
	 */
	public int getNumOfExecutionStatements()
	{
		if (executionStatements != null)
			return executionStatements.size();
		return 0;
	}

	/**
	 * Sets the defaultCommandType.
	 * @param defaultCommandType The defaultCommandType to set
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void setDefaultCommandType(int defaultCommandType)
		throws PersistenceException
	{
		if (defaultCommandType != TYPE_USING_STATEMENT
			&& defaultCommandType != TYPE_USING_PREPARED_STATEMENT
			&& defaultCommandType != TYPE_STORED_PROCEDURE_CALL)
		{
			throw new PersistenceException("The default command type is not correct!");
		}
		this.defaultCommandType = defaultCommandType;
	}

	/**
	 * Execute the statements with the given {@link Connection} object.
	 * Can’t execute SP with output parameters or ResultSet.
	 * Can’t execute select statements.
	 * @param connection The Connection object to use.
	 * @return int[] The number of records returned from the executions.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected synchronized BatchResults execute(ConnectionProvider connectionProvider)
		throws PersistenceException
	{
		int[] numOfRecords;
		int resultsCount = 0;
		AbstractSqlService sqlService = null;
		SqlService preparedService = new SqlService();
		StoredProcedureService storedProcedureService =
			new StoredProcedureService();
		ArrayList statementsNumOfRecords =
			new ArrayList(INITIAL_STATEMENTS_NUMBER_OF_RECORDS);
		StatementExecutionDetails currentExecutionDetails;
		StatementDetails currentStatementDetails;
		int currentCommandType = -1;
		int prevCommandType = -1;
		int currentStatementIdx = -1;
		int prevStatementIdx = -1;
		int size = 0;
		Statement statement = null;

		if (executionStatements != null)
			size = executionStatements.size();

		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Execute batch, " + size + " execution statements");
		try
		{

			// loop on the execution objects
			for (int index = 0; index < size; index++)
			{
				currentExecutionDetails =
					(StatementExecutionDetails) executionStatements.get(index);
				currentStatementIdx =
					currentExecutionDetails.getStatementStringIdx();
				currentStatementDetails =
					(StatementDetails) statements.get(currentStatementIdx);
				currentCommandType = currentExecutionDetails.getCommandType();

				// Execute the batch before handling the current statement execution
				//------------------------------------------------------------------
				if (statement != null
					&& ((prevCommandType != -1
						&& currentCommandType != prevCommandType)
						|| (currentCommandType != TYPE_USING_STATEMENT
							&& prevStatementIdx != currentStatementIdx)))
				{
					// not the first iteration & 
					// (the statement type has changed or 
					// (statement type != STATEMENT & index has changed))

					// Execute the batch statements and save the number of records 
					// returned from each statement execution.
					numOfRecords = statement.executeBatch();
					resultsCount =
						setExecutionNumOfRecords(
							numOfRecords,
							statementsNumOfRecords,
							resultsCount);

					// clear the statement object after the batch execution, 
					// for creating new one according to the current statement type.					
					statement = null;

					// clear the PreparedStatement/CallableStatement of the previous statement
					sqlService.clearPreparedStatement();
				}

				if (currentCommandType == TYPE_STORED_PROCEDURE_CALL)
					sqlService = storedProcedureService;
				else
					sqlService = preparedService;

				// Set the statement string & parameters in the Statement object 
				// and add it to the batch statements pool.
				statement =
					performCommand(
						statement,
						currentStatementDetails,
						currentExecutionDetails,
						sqlService,
						connectionProvider);

				// save information for the next iteration
				prevCommandType = currentCommandType;
				prevStatementIdx = currentStatementIdx;

			} // end main loop

			if (statement != null)
			{
				// Execute the batch statements and save the number of records 
				// returned from each statement execution.
				numOfRecords = statement.executeBatch();
				resultsCount =
					setExecutionNumOfRecords(
						numOfRecords,
						statementsNumOfRecords,
						resultsCount);

				// clear the statement object after the batch execution, 
				// for creating new one according to the current statement type.					
				statement = null;

				// clear the PreparedStatement/CallableStatement of the previous statement
				sqlService.clearPreparedStatement();
			}

			// clear the statements objects after handling
			clearStatements();

		}
		catch (BatchUpdateException bue)
		{
			PersistenceException pe = new PersistenceException(bue);
			String updateCount =
				convertUpdateCountToString(bue.getUpdateCounts());
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Batch execution failed, update count="
					+ updateCount
					+ pe.getMessage());
			throw pe;

		}
		catch (SQLException sqle)
		{
			PersistenceException pe = new PersistenceException(sqle);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Batch execution failed. " + pe.getMessage());
			throw pe;
		}

		// get the array of number of records for each statement execution.
		numOfRecords = getNumOfRecords(statementsNumOfRecords, resultsCount);
		String updateCount = convertUpdateCountToString(numOfRecords);
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Finish batch execution , update count=" + updateCount);

		return new BatchResults(numOfRecords, identityKeys);
	}

	/**
	 * Convert the array of update count to string.
	 * @param int[] updateCount of each executed statement
	 * @return String 
	 */
	private String convertUpdateCountToString(int[] updateCount)
	{
		String str = "";
		int len = updateCount.length;
		for (int i = 0; i < len; i++)
		{
			str += updateCount[i] + ", ";
		}
		return str;
	}

	/**
	 * Set the statement string & parameters in the {@link Statement}/{@link PreparedStatement} 
	 * object and add it to the batch statements pool.
	 * @param statement The statement object to set.
	 * @param batchStatement The {@link BatchStatement} object contains the data about 
	 * the statement string to execute.
	 * @param batchStatementExecution The {@link BatchStatementExecution} object contains the data about 
	 * the execution (parameters etc.).
	 * @param sqlService The {@link SqlService} used to format the statment string.
	 * @param connection The Connection object to use.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @return statement The statement object to execute.
	 */
	private Statement performCommand(
		Statement statement,
		StatementDetails statementDetails,
		StatementExecutionDetails statementExecutionDetails,
		AbstractSqlService sqlService,
		ConnectionProvider connectionProvider)
		throws PersistenceException
	{

		int commandType = statementExecutionDetails.getCommandType();

		try
		{

			if (commandType == TYPE_USING_STATEMENT)
			{
				// --- Simple update statement to execute with Statement object. ---

				if (statement == null)
				{
					statement =
						connectionProvider.getConnection().createStatement();
				}

				// get the StatementString object according to the StatementStringIdx of the object.
				// set the StatementString & the parameters in the SqlService
				sqlService.setStatementString(
					setIdentityKeysInStatement(
						statementDetails.getStatementString()));
				sqlService.clearParameters();
				sqlService.addParameters(
					statementExecutionDetails.getStatementParameters());

				// add the statement to the batch with the parameters inside
				statement.addBatch(sqlService.buildSQL());

			}
			else if (
				commandType == TYPE_USING_PREPARED_STATEMENT
					|| commandType == TYPE_STORED_PROCEDURE_CALL)
			{
				// --- Simple update statement to execute with PreparedStatement object. ---
				// --- or Call to stored procedure	 ---

				// set the statement string in the sqlservice after replacing the last key 
				// values in the basic statement
				sqlService.setStatementString(
					setIdentityKeysInStatement(
						statementDetails.getStatementString()));

				// get prepared/callable statement with the parameters embedded.
				// If the PreparedStatement/CallableStatement object exists in the sqlService, 
				// return the same instance with the parameters embedded.
				statement =
					sqlService.prepareStatement(
						connectionProvider,
						statementExecutionDetails.getStatementParameters());
				// add the statement to the batch
				 ((PreparedStatement) statement).addBatch();

			}
			else if (commandType == TYPE_GET_LAST_IDENTITY_KEY)
			{
				// --- Get identity last value allocation ---

				setLastIdentityKey(
					statementDetails.getStatementString(),
					connectionProvider,
					sqlService);

				// Clear the statement because we don't want executeBatch to be performed.
				statement = null;
				sqlService.clearPreparedStatement();
			}

		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}

		return statement;
	}

	/**
	 * Set the value of the last identity column assignment
	 * @param key The key of the identity column.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private void setLastIdentityKey(
		String key,
		ConnectionProvider connectionProvider,
		AbstractSqlService sqlService)
		throws SQLException, PersistenceException
	{

		//set the StatementString object according to the StatementStringIdx of the object.
		String statementString =
			connectionProvider.getConnectionProperty(
				PersistenceConstants.IDENTITY_KEY_GET_LAST_VALUE);
		sqlService.setStatementString(statementString);

		PreparedStatement pStatement =
			sqlService.prepareStatement(connectionProvider, null);

		Integer newValue = null;
		boolean hasResultSet = pStatement.execute();
		if (hasResultSet)
		{
			ResultSet rs = pStatement.getResultSet();
			if (rs.next())
			{
				newValue = TypesUtil.convertNumberToInteger(rs.getObject(1));
			}
		}

		if (newValue == null)
		{
			throw new PersistenceException(
				"Value not found for identity key " + key);
		}

		if (identityKeys == null)
		{
			identityKeys = new HashMap(INITIAL_IDENTITY_KEYS_AMOUNT);
		}

		ArrayList keyValues = (ArrayList) identityKeys.get(key);
		if (keyValues == null)
		{
			keyValues = new ArrayList(1);
		}
		keyValues.add(newValue);
		identityKeys.put(key, keyValues);
	}

	/**
	 * Returns the statement with the identity keys replaced with the allocated value.
	 * @param basicStatement The statement contains the identity keys to replace with the value.
	 * @return String The statement with the identity key values.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private String setIdentityKeysInStatement(String basicStatement)
		throws PersistenceException
	{
		String returnedStatement = new String(basicStatement);
		String key;
		BigDecimal value;
		ArrayList keyValues;
		int posEnd;
		int posStart = returnedStatement.indexOf("<#");

		while (posStart > -1)
		{
			posEnd = returnedStatement.indexOf("/>");
			key = returnedStatement.substring(posStart, posEnd + 2);

			keyValues = (ArrayList) getIdentityKeyValues(key);
			value = (BigDecimal) keyValues.get(keyValues.size() - 1);
			returnedStatement =
				StringFormatterUtil.replace(
					returnedStatement,
					key,
					value.toString());

			posStart = returnedStatement.indexOf("<#");
		}

		return returnedStatement;
	}

	/**
	 * Return the value of an identity key, if it was assigned in this batch.
	 */
	private ArrayList getIdentityKeyValues(String key)
	{
		return (ArrayList) identityKeys.get(key);
	}

	/**
	 * The method clears the statements String & parameters in the current object.
	 */
	private void clearStatements()
	{
		if (statements != null)
			statements.clear();
		if (executionStatements != null)
			executionStatements.clear();
	}

	/**
	 * save the arrays of the number of records returned from each statement execution.
	 * @param statementNumOfRecords The array of number of records from the last execution.
	 * @param statementsNumOfRecords A List with arrays of number of records from the previous executions.
	 * @param resultsCount The results count of the previous executions.
	 * @return int The results count contains the count of the last execution.
	 */
	private int setExecutionNumOfRecords(
		int[] statementNumOfRecords,
		ArrayList statementsNumOfRecords,
		int resultsCount)
		throws PersistenceException
	{
		for (int i = 0; i < statementNumOfRecords.length; i++)
		{
			if (statementNumOfRecords[i] == COMMAND_FAILED)
			{
				throw new PersistenceException("An error occurred while executing the statement");
			}
		}
		statementsNumOfRecords.add(statementNumOfRecords);
		resultsCount += statementNumOfRecords.length;
		return resultsCount;
	}

	/**
	 * This method returns the array of number of records for each statement execution.
	 * @param statementsNumOfRecords A List with arrays of number of records from all the executions.
	 * @param resultsCount The results count contains the count of all the number 
	 * of records from all the executions.
	 * @return int[] Array of number of records for each statement execution.
	 */
	private int[] getNumOfRecords(
		ArrayList statementsNumOfRecords,
		int resultsCount)
	{
		// join the arrays of numOfRecords 
		int[] numOfRecords = new int[resultsCount];
		Iterator iter = statementsNumOfRecords.iterator();
		int[] temp;
		int index = 0;
		while (iter.hasNext())
		{
			temp = (int[]) iter.next();
			System.arraycopy(temp, 0, numOfRecords, index, temp.length);
			index += temp.length;
		}
		return numOfRecords;
	}

	/**
	 * Add a new statement string to the current object.
	 * @param statementString The statement string to add. 
	 * @param withoutParameters If true, call to  addStatementExecution without parameters automatically.
	 * @return int The index of the statement string to link to the parameters lists.
	 */
	public int addStatementString(
		String statementString,
		boolean withoutParameters)
	{
		return addStatementString(statementString, null, withoutParameters);
	}

	/**
	 * Add a new statement string to the current object.
	 * @param statementString The statement string to add. 
	 * @param identityKey The key name of the identity column allocated in this INSERT statement. 
	 * should be in the following structure: <#identityKeyName/>.
	 * The statements that should contain the value of the identity key, should include this name 
	 * (with the same structure) in the update/call statement and we replace it with the real value 
	 * of the identity column assigned before (should be in same batch before the update statements 
	 * of the childrens).
	 * @param withoutParameters If true, call to  addStatementExecution without parameters automatically.
	 * @return int The index of the statement string to link to the parameters lists.
	 */
	public int addStatementString(
		String statementString,
		String identityKey,
		boolean withoutParameters)
	{
		if (statements == null)
		{
			statements = new ArrayList(INITIAL_STATEMENTS_AMOUNT);
		}
		statements.add(new StatementDetails(statementString, identityKey));

		int keyIdx = statements.size() - 1;
		if (withoutParameters)
		{
			addStatementExecution(keyIdx, null);
		}

		return keyIdx;
	}

	/**
	 * Add a new statement string to the current object.
	 * @param statementString The statement string to add. 
	 * @param identityKey The key name of the identity column allocated in this INSERT statement. 
	 * should be in the following structure: <#identityKeyName/>.
	 * The statements that should contain the value of the identity key, should include this name 
	 * (with the same structure) in the update/call statement and we replace it with the real value 
	 * of the identity column assigned before (should be in same batch before the update statements 
	 * of the childrens).
	 * @return int The index of the statement string to link to the parameters lists.
	 */
	public int addStatementString(String statementString, String identityKey)
	{
		return addStatementString(statementString, identityKey, false);
	}

	/**
	 * Add a new statement string to the current object.
	 * @param statementString The statement string to add. 
	 * The statement that can contain identity key name in the following structure: <#identityKeyName/>.
	 * @return int The index of the statement string to link to the parameters lists.
	 */
	public int addStatementString(String statementString)
	{
		return addStatementString(statementString, null);
	}

	/**
	 * Add a list of parameters to execute with the statement string according to 
	 * the given index, with a given command type.
	 * @param statementStringIdx The index of the sql statement string.
	 * @param statementParameters A list of parameters to link to the statement string index.
	 * @param commandType The command type. Can be one of the following: 
	 * TYPE_USING_STATEMENT, TYPE_USING_PREPARED_STATEMENT,
	 * TYPE_STORED_PROCEDURE_CALL, TYPE_GET_LAST_IDENTITY_KEY
	 */
	public void addStatementExecution(
		int statementStringIdx,
		List statementParameters,
		int commandType)
	{
		if (executionStatements == null)
		{
			executionStatements =
				new ArrayList(INITIAL_STATEMENT_EXECUTIONS_AMOUNT);
		}

		StatementDetails statementDetails =
			(StatementDetails) statements.get(statementStringIdx);
		executionStatements.add(
			new StatementExecutionDetails(
				statementStringIdx,
				statementParameters,
				commandType));

		// if we should save the value of the identity key of the current insert statement, 
		// we should add a statement of type TYPE_GET_LAST_IDENTITY_KEY.
		if (statementDetails.shouldGetIdentityKeyValue())
		{
			int identityIdx =
				addStatementString(statementDetails.getIdentityKey());
			executionStatements.add(
				new StatementExecutionDetails(
					identityIdx,
					null,
					TYPE_GET_LAST_IDENTITY_KEY));
		}

	}

	/**
	 * Add a list of parameters to execute with the statement string according to 
	 * the given index, with the default command type.
	 * @param statementStringIdx The index of the sql statement string.
	 * @param statementParameters A list of parameters to link to the statement string index.
	 */
	public void addStatementExecution(
		int statementStringIdx,
		List statementParameters)
	{
		addStatementExecution(
			statementStringIdx,
			statementParameters,
			TYPE_USING_DEFAULT);
	}

	/**
	 * Contains details about a statement.
	 */
	private class StatementDetails
	{
		/** The statement string to execute.
		 */
		private String statementString = null;
		/** The key name of the identity column allocated in this statement.
		 */
		private String identityKey = null;

		/**
		 * Constructor for BatchStatement.
		 * @param statementString The statement string to execute.
		 * @param commandType The command type.
		 * @param identityKey The key name of the identity column allocated in this statement
		 */
		public StatementDetails(String statementString, String identityKey)
		{
			this.statementString = statementString;
			if (identityKey != null && (identityKey.trim()).length() > 0)
			{
				this.identityKey = identityKey;
			}
		}

		/**
		 * Returns the statement string to execute..
		 * @return String Statement string.
		 */
		public String getStatementString()
		{
			return statementString;
		}

		/**
		 * Returns the key name of the identity column allocated in this statement.
		 * @return String
		 */
		public String getIdentityKey()
		{
			return identityKey;
		}

		/**
		 * Returns true when should get the value of the identity key after the insertion.
		 * @return boolean
		 */
		public boolean shouldGetIdentityKeyValue()
		{
			return identityKey != null;
		}

	}

	/**
	 * Contains details about a statement execution.
	 */
	private class StatementExecutionDetails
	{

		/** The index of the Sql Statement String.
		 */
		private int statementStringIdx;
		/** The parameters list.
		 */
		private List statementParameters;
		/** The command type. Can be one of the following: 
		 *  TYPE_USING_STATEMENT, TYPE_USING_PREPARED_STATEMENT,
		 *  TYPE_STORED_PROCEDURE_CALL, TYPE_GET_LAST_IDENTITY_KEY
		 */
		private int commandType;

		/**
		 * create new BatchStatement object.
		 * @param statementStringIdx The index of the Sql Statement String.
		 * @param statementParameters The parameters list.
		 * @param commandType The command type. Can be one of the following: 
		 * TYPE_USING_STATEMENT, TYPE_USING_PREPARED_STATEMENT,
		 * TYPE_STORED_PROCEDURE_CALL, TYPE_GET_LAST_IDENTITY_KEY
		 */
		private StatementExecutionDetails(
			int statementStringIdx,
			List statementParameters,
			int commandType)
		{
			this.commandType = commandType;
			this.statementStringIdx = statementStringIdx;

			if (statementParameters == null)
			{
				this.statementParameters = new ArrayList(0);
			}
			else
			{
				this.statementParameters = new ArrayList(statementParameters);
			}
		}

		/**
		 * This method returns the parameters list.
		 * @return List The parameters list.
		 */
		public List getStatementParameters()
		{
			return statementParameters;
		}

		/**
		 * This method returns the index of the Sql Statement String.
		 * @return int The index of the Sql Statement String.
		 */
		public int getStatementStringIdx()
		{
			return statementStringIdx;
		}

		/**
		 * Returns the command type. Can be one of the following: 
		 * TYPE_USING_STATEMENT, TYPE_USING_PREPARED_STATEMENT,
		 * TYPE_STORED_PROCEDURE_CALL, TYPE_GET_LAST_IDENTITY_KEY
		 * @return int
		 */
		public int getCommandType()
		{
			if (commandType == TYPE_USING_DEFAULT)
				return defaultCommandType;
			return commandType;
		}

	}

}