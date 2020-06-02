/*
 * Author: yifat har-nof
 * @version $Id: Transaction.java,v 1.5 2005/04/07 06:17:39 yifat Exp $
 */
package com.ness.fw.persistence;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.exceptions.DuplicateKeyException;
import com.ness.fw.persistence.exceptions.MissingSavePointException;
import com.ness.fw.util.StringFormatterUtil;

/**
 *  Transaction manager.
 *  This class encapsulates and tracks transactions.
 */
public class Transaction extends ConnectionProvider
{

	/** The current Transaction object allows auto commit operations.
	 */
	private boolean allowAutoCommit = false;

	/** Used to get the last identity key that allocated in 
	 *  the current connection. 
	 */
	private PreparedStatement lastIdentityKeyPS = null;

	/** A list of save points declared in the current transaction. 
	 */
	private ArrayList savePoints = null;

	/**
	 * Indicates whether the Transaction already used. 
	 */
	private boolean used = false;

	/** A list of <code>StateManaged</code> objects that saved in the current transaction. 
	 */
	private StateManagedRegistry stateManagedList = null;

	/** A list of <code>LockingData</code> objects that saved in the current transaction. 
	 */
	private LockingDataList lockingDataList = null;

	/** 
	 * Create a new Transaction object based on specific connection manager.
	 * @param userAuthData Contains the basic authorization data of the current user.
	 * @param connectionManagerName The name of connection manager to use.
	 * @param allowAutoCommit allow auto commit operations.
	 */
	protected Transaction(
		UserAuthData userAuthData,
		String connectionManagerName,
		boolean allowAutoCommit)
		throws PersistenceException
	{
		super(connectionManagerName, userAuthData);

		this.allowAutoCommit = allowAutoCommit;
		stateManagedList = new StateManagedRegistry();
		lockingDataList = new LockingDataList();
	}

	/**
	 * Create a new Transaction object based on a specific connection manager.
	 * Do not allow auto commit operations.
	 * @param userAuthData Contains the basic authorization data of the current user.
	 * @param connectionManagerName The name of the connection manager.
	 */
	protected Transaction(
		UserAuthData userAuthData,
		String connectionManagerName)
		throws PersistenceException
	{
		this(userAuthData, connectionManagerName, false);
	}

	/**
	 * Create a new Transaction object with the default connection manager.
	 * Do not allow auto commit operations.
	 * @param userAuthData Contains the basic authorization data of the current user.
	 */
	protected Transaction(UserAuthData userAuthData)
		throws PersistenceException
	{
		this(userAuthData, null);
	}

	/** 
	 * Open a database connection and begin a database transaction.
	 * @throws PersistenceException Failure in the process of begining the transaction.
	 */
	public void begin() throws PersistenceException
	{
		if (used)
		{
			throw new PersistenceException("The Transaction was already been used. Please create another one!!");
		}

		try
		{
			used = true;
			connection = connectionManager.getConnection();
			connection.setAutoCommit(false);
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"Begin Transaction for userId [" + getUserIdInternal() + "]");
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/** 
	 * Commit the current transaction.
	 * This method also releases the resources involved,
	 * which are the {@link ConnectionManager} and the {@link Connection}.
	 * @throws PersistenceException Failure in the process of commiting the transaction.
	 * The transaction is rolled back and the resources are released.
	 */
	public void commit() throws PersistenceException
	{
		try
		{
			if (lastIdentityKeyPS != null)
			{
				lastIdentityKeyPS.close();
				lastIdentityKeyPS = null;
			}

			if (connection != null && !connection.getAutoCommit())
			{
				connection.commit();
				connection.setAutoCommit(true);
				connectionManager.closeConnection(connection);
				connection = null;

				// set the all savepoints as inactive
				setSavePointsInactivity(null);

				// clear the state of all the managed objects that was 
				// saved in the current transaction.
				// refresh the lock id of the lockables		
				clearRegisteredObjects();
			}
		}
		catch (SQLException sqle)
		{
			rollback();
			throw new PersistenceException(sqle);
		}
		catch (FatalException e)
		{
			rollback();
			throw new PersistenceException(e);
		}
		finally
		{
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"End Transaction for userId [" + getUserIdInternal() + "]");
		}
	}

	/** 
	 * Rollback the database transaction.
	 * This method also releases the resources involved,
	 * which are the {@link ConnectionManager} and the {@link Connection}.
	 */
	public void rollback() throws PersistenceException
	{
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Rollback performed, userId [" + getUserIdInternal() + "]");
		try
		{
			if (lastIdentityKeyPS != null)
			{
				lastIdentityKeyPS.close();
				lastIdentityKeyPS = null;
			}

			if (connection != null && !connection.getAutoCommit())
			{
				connection.rollback();
				connectionManager.closeConnection(connection);
				connection = null;

				// set the all savepoints as inactive
				setSavePointsInactivity(null);

				// remove all the managed objects that was saved in 
				// the current transaction.
				removeRegisteredObjects();
			}
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/**
	 * If no connection is present, create non-transactional connection (auto commit).
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private void startOperation() throws PersistenceException
	{
		try
		{
			// if no connection is present, it means that we do a non-transactional operation.
			if (connection == null)
			{

				if (allowAutoCommit)
				{
					//  no begin() method was invoked so we must get a connection for this operation.
					connection = connectionManager.getConnection();
					// we allow non-transactional operation.
					connection.setAutoCommit(true);
				}
				else
				{
					throw new PersistenceException("The Transaction was not started. Please call to begin method!!");
				}
			}
		}
		catch (SQLException sqle)
		{
			rollbackOnError();
			throw new PersistenceException(sqle);
		}
	}

	/**
	 * if this was a non-transactional operation, we must free the connection.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private void finishOperation() throws PersistenceException
	{
		try
		{
			// if this was a non-transactional operation, we must free the connection.
			if (connection.getAutoCommit())
			{
				connectionManager.closeConnection(connection);
				connection = null;
			}
		}
		catch (SQLException sqle)
		{
			rollbackOnError();
			throw new PersistenceException(sqle);
		}
	}

	/** 
	 * Execute an SQL statement within the boundaries of a previously
	 * started transaction. If no transaction was started before issuing this method, the
	 * statement is committed automatically.
	 * @param sqlService An SqlService object containing the SQL statement to be executed.
	 * @return The number of rows affected by the SQL statement.
	 * @throws PersistenceException Failure in executing the SQL statement. The transaction
	 * is rolled back and the resources are released.
	 */
	public int execute(SqlService sqlService) throws PersistenceException
	{
		try
		{
			long startTime = System.currentTimeMillis();

			// if no connection is present, it means that we do a non-transactional operation.
			startOperation();

			PreparedStatement pst = sqlService.getPreparedStatement(this);
			int numOfRecords = pst.executeUpdate();
			pst.close();

			// if this was a non-transactional operation, we must free the connection.
			finishOperation();
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"Transaction - SqlService execution time ["
					+ (System.currentTimeMillis() - startTime)
					+ "]");

			return numOfRecords;

		}
		catch (PersistenceException ge)
		{
			rollbackOnError();
			throw ge;
		}
		catch (SQLException sqle)
		{
			rollbackOnError();

			if (sqle.getErrorCode()
				== getIntConnectionProperty(
					PersistenceConstants.SQLCODE_DUPLICATE_KEY))
			{
				throw new DuplicateKeyException(
					"Duplicate key while executing the update statement",
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
	}

	/**
	 * Execute a set of SQL statements within the boundaries of a previously
	 * started transaction. If no transaction was started before issuing this method, the
	 * statements are committed automatically.
	 * @param batch A {@link Batch} object containing the SQL statements to be executed.
	 * @return int[] The Number Of Records returned from the execution.
	 * @throws PersistenceException Failure in executing the SQL statement. The transaction
	 * is rolled back and the resources are released.
	 */

	public BatchResults execute(Batch batch) throws PersistenceException
	{
		BatchResults results = null;

		try
		{

			long startTime = System.currentTimeMillis();

			// if no connection is present, it means that we do a non-transactional operation.
			startOperation();

			results = batch.execute(this);

			// if this was a non-transactional operation, we must free the connection.
			finishOperation();
			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"Transaction - Batch execution time ["
					+ (System.currentTimeMillis() - startTime)
					+ "]");

			return results;

		}
		catch (PersistenceException pe)
		{
			rollbackOnError();
			throw pe;
		}
	}

	/**
	 * Execute the call statement to the stored procedure within the boundaries of a previously
	 * started transaction. If no transaction was started before issuing this method, the
	 * statements are committed automatically.
	 * @param storedProcedureService A {@link StoredProcedureService} object to execute.
	 * @return {@linkStoredProcedureResults} The results of the stored procedure execution.
	 * @throws PersistenceException Failure in executing the SQL statement. The transaction
	 * is rolled back and the resources are released.
	 */
	public StoredProcedureResults execute(StoredProcedureService storedProcedureService)
		throws PersistenceException
	{

		try
		{
			long startTime = System.currentTimeMillis();

			// if no connection is present, it means that we do a non-transactional operation.
			startOperation();

			StoredProcedureResults results =
				storedProcedureService.execute(this);

			// if this was a non-transactional operation, we must free the connection.
			finishOperation();

			Logger.debug(
				PersistenceConstants.LOGGER_CONTEXT,
				"Transaction - StoredProcedure execution time ["
					+ (System.currentTimeMillis() - startTime)
					+ "]");
			return results;

		}
		catch (PersistenceException pe)
		{
			rollbackOnError();
			throw pe;
		}
	}

	/**
	 * Sets the allowAutoCommit.
	 * @param allowAutoCommit The allowAutoCommit to set
	 */
	public void setAllowAutoCommit(boolean allowAutoCommit)
	{
		this.allowAutoCommit = allowAutoCommit;
	}

	/**
	 * Returns true when the current object allows auto commit operations.
	 * @return boolean True if the current object allows auto commit operations.
	 */
	public boolean isAllowAutoCommit()
	{
		return allowAutoCommit;
	}

	/**
	 * Returns the last identity column key that allocated in the current transaction.
	 * The Transaction should be started.
	 * @return BigDecimal The value of the last identity column key.
	 * @throws PersistenceException Failure in executing the SQL statement. The transaction
	 * is rolled back and the resources are released.
	 */
	public int getLastIdentityKey() throws PersistenceException
	{
		int lastIdentityKey = -1;

		if (connection == null)
		{
			throw new PersistenceException("The Transaction was not started. Please call to begin method!!");
		}

		try
		{
			if (lastIdentityKeyPS == null)
			{
				lastIdentityKeyPS =
					connection.prepareStatement(
						getConnectionProperty(
							PersistenceConstants.IDENTITY_KEY_GET_LAST_VALUE));
			}
			ResultSet rs = lastIdentityKeyPS.executeQuery();
			rs.next();
			BigDecimal value =  rs.getBigDecimal(1);
			if(value == null)
			{
				throw new PersistenceException("No insert statement with identity key allocation was executed within the transaction.");
			}
			lastIdentityKey = value.intValue();
			rs.close();
		}
		catch (SQLException sqle)
		{
			rollbackOnError();
			throw new PersistenceException(sqle);
		}
		return lastIdentityKey;
	}

	/**
	 * Creates a unique savepoint with the given name in the current transaction.
	 * @param name The name of the savepoint to set.
	 * @return SavePoint The {@link SavePoint} object
	 * @throws PersistenceException Failure in executing the SQL statement. The transaction
	 * is rolled back and the resources are released.
	 */
	public SavePoint setSavepoint(String name) throws PersistenceException
	{

		SavePoint savePoint = null;
		if (connection == null)
		{
			throw new PersistenceException("The Transaction was not started. Please call to begin method!!");
		}
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"set save point ["
				+ name
				+ "] for userId ["
				+ getUserIdInternal()
				+ "]");

		try
		{
			String statementString =
				getConnectionProperty(PersistenceConstants.SAVEPOINT_SET);
			statementString =
				StringFormatterUtil.replace(statementString, "?", name);
			SqlService sqlService = new SqlService(statementString);
			PreparedStatement pStatement =
				sqlService.getPreparedStatement(this);
			pStatement.execute();

			savePoint = new SavePoint(name);

			if (savePoints == null)
			{
				savePoints = new ArrayList(1);
			}
			savePoints.add(savePoint);

			return savePoint;

		}
		catch (SQLException sqle)
		{
			rollbackOnError();
			throw new PersistenceException(sqle);
		}
	}

	/**
	 * Undoes all changes made after the given savepoint was set.
	 * @param  SavePoint The {@link SavePoint} object to rollback
	 * @throws PersistenceException Failure in executing the SQL statement. The transaction
	 * is rolled back and the resources are released.
	 */
	public void rollback(SavePoint savePoint) throws PersistenceException
	{
		if (connection == null)
		{
			throw new PersistenceException("The Transaction was not started. Please call to begin method!!");
		}

		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Rollback to save point ["
				+ savePoint.getName()
				+ "] for userId ["
				+ getUserIdInternal()
				+ "]");

		if (!savePoint.isActive())
		{
			throw new PersistenceException(
				"The SavePoint "
					+ savePoint.getName()
					+ " is no longer active");
		}

		try
		{

			String statementString =
				getConnectionProperty(PersistenceConstants.SAVEPOINT_ROLLBACK);
			statementString =
				StringFormatterUtil.replace(
					statementString,
					"?",
					savePoint.getName());
			SqlService sqlService = new SqlService(statementString);
			PreparedStatement pStatement =
				sqlService.getPreparedStatement(this);
			pStatement.execute();

			// set the savepoints that was created after the current savePoint
			// as inactive
			setSavePointsInactivity(savePoint);

			// remove all the managed objects that was saved in 
			// the current savePoint.
			removeRegisteredObjects(savePoint);

		}
		catch (SQLException sqle)
		{
			rollbackOnError();

			if (sqle.getErrorCode()
				== getIntConnectionProperty(
					PersistenceConstants.SQLCODE_MISSING_SAVEPOINT))
			{
				throw new MissingSavePointException(
					"Unable to rollback to save point ["
						+ savePoint.getName()
						+ "], The save point was not found.",
					sqle);
			}

			throw new PersistenceException(sqle);
		}
	}

	/**
	 * set the savepoints that was created after the current savePoint as inactive.
	 * @param fromSavePoint The {@link SavePoint} to begin from
	 */
	private void setSavePointsInactivity(SavePoint fromSavePoint)
	{
		if (savePoints == null)
			return;

		SavePoint currentSavePoint;
		int index = 0;
		if (fromSavePoint != null)
		{
			index = savePoints.indexOf(fromSavePoint);
		}
		for (; index < savePoints.size(); index++)
		{
			currentSavePoint = (SavePoint) savePoints.get(index);
			currentSavePoint.setInactive();
		}
	}

	/**
	 * Removes the given Savepoint object from the current transaction. 
	 * @param  SavePoint The {@link SavePoint} object to release.
	 * @throws PersistenceException Failure in executing the SQL statement. The transaction
	 * is rolled back and the resources are released.
	 */
	public void releaseSavepoint(SavePoint savePoint)
		throws PersistenceException
	{

		if (connection == null)
		{
			throw new PersistenceException("The Transaction was not started. Please call to begin method!!");
		}

		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"release save point ["
				+ savePoint.getName()
				+ "] for userId ["
				+ getUserIdInternal()
				+ "]");
		if (!savePoint.isActive())
		{
			throw new PersistenceException(
				"The SavePoint "
					+ savePoint.getName()
					+ " is no longer active");
		}

		try
		{
			String statementString =
				getConnectionProperty(PersistenceConstants.SAVEPOINT_RELEASE);
			statementString =
				StringFormatterUtil.replace(
					statementString,
					"?",
					savePoint.getName());
			SqlService sqlService = new SqlService(statementString);
			PreparedStatement pStatement =
				sqlService.getPreparedStatement(this);
			pStatement.execute();

			// set the savepoint as inactive
			savePoint.setInactive();

			// move the StateManaged objects to the main list. 
			stateManagedList.addAll(savePoint.getStateManagedList());
			savePoint.removeManagedObjects();

			lockingDataList.addAll(savePoint.getLockingDataList());
			savePoint.removeLockingDataObjects();

		}
		catch (SQLException sqle)
		{
			rollbackOnError();

			if (sqle.getErrorCode()
				== getIntConnectionProperty(
					PersistenceConstants.SQLCODE_MISSING_SAVEPOINT))
			{
				throw new MissingSavePointException(
					"Unable to release save point ["
						+ savePoint.getName()
						+ "], The SavePoint was not found.",
					sqle);
			}

			throw new PersistenceException(sqle);
		}
	}

	/**
	 * Returns the current save point.
	 * @return SavePoint
	 */
	private SavePoint getCurrentSavePoint()
	{
		SavePoint currentSavePoint = null;
		if (savePoints != null)
		{
			currentSavePoint =
				(SavePoint) savePoints.get(savePoints.size() - 1);
		}
		return currentSavePoint;
	}

	/**
	 * Add a <code>StateManaged</code> to the end of the list.
	 * @param stateManaged
	 */
	public void registerStateManaged(StateManaged stateManaged)
	{
		SavePoint currentSavePoint = getCurrentSavePoint();
		if (currentSavePoint == null)
		{
			stateManagedList.add(stateManaged);
		}
		else
		{
			currentSavePoint.addStateManaged(stateManaged);
		}
	}

	/**
	 * Add a <code>StateManagedList</code> to the end of the list.
	 * @param stateManagedList
	 */
	public void registerStateManagedList(StateManagedList newList)
	{
		SavePoint currentSavePoint = getCurrentSavePoint();
		if (currentSavePoint == null)
		{
			stateManagedList.add(newList);
		}
		else
		{
			currentSavePoint.addStateManagedList(newList);
		}
	}

	/**
	 * Add a <code>Lockable</code> object to the list.
	 * @param lockable The Lockable object.
	 * @param  newLockId The new lockId to update in the lockable
	 */
	public void registerLockable(Lockable lockable, int newLockId)
	{
		SavePoint currentSavePoint = getCurrentSavePoint();
		if (currentSavePoint == null)
		{
			lockingDataList.add(new LockingData(lockable, newLockId));
		}
		else
		{
			currentSavePoint.addLockingData(
				new LockingData(lockable, newLockId));
		}
	}

	/**
	 * Clears the state of the <code>StateManaged</code> objects that 
	 * was saved in the current transaction.
	 * 
	 * Refresh the Lockables lockId.
	 */
	private void clearRegisteredObjects() throws FatalException
	{
		// clear stateManaged main list
		stateManagedList.clearObjectsState();
		stateManagedList.removeAll();

		// clear lockable main list
		lockingDataList.refreshObjectsLockId();
		lockingDataList.removeAll();

		// clear save points list
		if (savePoints != null)
		{
			SavePoint currentSavePoint;
			for (int index = 0; index < savePoints.size(); index++)
			{
				currentSavePoint = (SavePoint) savePoints.get(index);
				currentSavePoint.clearManagedObjectsState();
				currentSavePoint.removeManagedObjects();

				currentSavePoint.refreshObjectsLockId();
				currentSavePoint.removeLockingDataObjects();
			}
		}
	}

	/**
	 * remove all the <code>StateManaged</code> objects And 
	 * the <code>LockingData</code> objects from the given <code>SavePoint</code>
	 * and from the SavePoints that was opened after the given SavePoint.
	 * @param savePoint
	 */
	private void removeRegisteredObjects(SavePoint fromSavePoint)
	{
		int index = 0;

		if (fromSavePoint != null)
		{
			fromSavePoint.removeManagedObjects();
			fromSavePoint.removeLockingDataObjects();
			index = savePoints.indexOf(fromSavePoint) + 1;
		}

		if (savePoints != null)
		{
			// clear save points that was opened after the given SavePoint.
			SavePoint currentSavePoint;
			for (; index < savePoints.size(); index++)
			{
				currentSavePoint = (SavePoint) savePoints.get(index);
				currentSavePoint.removeManagedObjects();
				currentSavePoint.removeLockingDataObjects();
			}
		}
	}

	/**
	 * remove all the <code>StateManaged</code> objects from the list.
	 */
	private void removeRegisteredObjects()
	{
		// remove the objects from the main list.
		stateManagedList.removeAll();

		// remove the objects from the main list.
		lockingDataList.removeAll();

		// remove the objects from the save points.
		removeRegisteredObjects(null);

	}

	/**
	 * Indicates whether the transaction containes save points.
	 * @return
	 */
	private boolean containsSavePoints()
	{
		return savePoints != null && savePoints.size() > 0;
	}

	/**
	 * perform rollback when the connection is valid and not in auto commit mode.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private void rollbackOnError() throws PersistenceException
	{
		if (!containsSavePoints())
		{
			try
			{
				if (connection != null && !connection.getAutoCommit())
				{
					rollback();
				}
			}
			catch (SQLException sqle2)
			{
				throw new PersistenceException(sqle2);
			}
		}
	}

	/** 
	 * Release all the resources if they have not been previously released.
	 */
	public void finalize()
	{
		try
		{
			super.finalize();
			lastIdentityKeyPS = null;
			savePoints = null;
			stateManagedList = null;
			lockingDataList = null;
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

}