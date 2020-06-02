/*
 * Author: yifat har-nof
 * @version $Id: Numerator.java,v 1.5 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import java.sql.*;

import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Allows handling application numerators.
 */
public class Numerator
{

	/**
	 * The numerator user id for the transaction.
	 */
	public static final String NUMERATOR_USER = "numerator";

	/**
	 * The default step size for new numerators.
	 */
	public static final int NUMERATOR_DEFAULT_STEP = 1;

	/**
	 * Returns the next value of the given numerator.
	 * @param numeratorName The name of the numerator.
	 * @param connectionManagerName The name of the connection manager.
	 * @return NumeratorDetails Contains the next value & step
	 * @throws PersistenceException
	 */
	protected static NumeratorDetails getNumeratorNextDetails(
		String numeratorName,
		String connectionManagerName)
		throws PersistenceException
	{
		NumeratorDetails numeratorDetails = null;
		// numeratorName = numeratorName.trim().toUpperCase();
		Transaction transaction = null;
		try
		{
			transaction =
				new Transaction(
					UserAuthDataFactory.getUserAuthData(NUMERATOR_USER),
					connectionManagerName);
			transaction.begin();

			numeratorDetails = getNextDetails(numeratorName, transaction);

			if (numeratorDetails != null)
			{
				updateNumeratorLastValue(
					numeratorName,
					transaction,
					numeratorDetails.getNextId());
			}
			else
			{ // new numerator
				numeratorDetails = new NumeratorDetails(numeratorName, 1, 1);

				createNumerator(numeratorName, transaction, 1, 1);
			}

			transaction.commit();
			transaction = null;
		}
		catch (SQLException sqle)
		{
			PersistenceException pe = new PersistenceException(sqle);
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Get next value for numerator: "
					+ numeratorName
					+ " failed. "
					+ pe.getMessage());
			throw pe;
		}
		finally
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
		}

		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Get next value for numerator: "
				+ numeratorName
				+ " next value is "
				+ numeratorDetails.getNextId()
				+ " step is: "
				+ numeratorDetails.getStep());

		return numeratorDetails;
	}

	/**
	 * Creates the numerator, with the initial value & step size.
	 * @param numeratorName The name of the numerator.
	 * @param connectionManagerName The name of the connection manager.
	 * @param initialValue The initial value to start the numerator
	 * @param step The step size
	 * @throws PersistenceException
	 */
	public static void createNumerator(
		String numeratorName,
		String connectionManagerName,
		long initialValue,
		int step)
		throws PersistenceException
	{
		Transaction transaction = null;
		try
		{
			transaction =
				new Transaction(
					UserAuthDataFactory.getUserAuthData(NUMERATOR_USER),
					connectionManagerName);
			transaction.begin();

			createNumerator(numeratorName, transaction, initialValue, step);

			transaction.commit();
			transaction = null;
		}
		catch (SQLException sqle)
		{
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error creating numerator: "
					+ numeratorName
					+ ". "
					+ sqle.getMessage());
			throw new PersistenceException(sqle);
		}
		finally
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
		}
	}

	/**
	 * Creates the numerator, with the initial value & step size.
	 * @param numeratorName The name of the numerator.
	 * @param transaction The transaction object to use.
	 * @param initialValue The initial value to start the numerator
	 * @param step The step size
	 * @throws PersistenceException
	 * @throws SQLException
	 */
	private static void createNumerator(
		String numeratorName,
		Transaction transaction,
		long initialValue,
		int step)
		throws PersistenceException, SQLException
	{
		PreparedStatement pStatement = null;
		try
		{
			NumeratorDetails numeratorDetails =
				getNextDetails(numeratorName, transaction);

			if (numeratorDetails != null)
			{
				throw new PersistenceException(
					"Numerator: " + numeratorName + " already exist.");
			}

			// insert new numerator row
			String statementString =
				transaction.getConnectionProperty(
					PersistenceConstants.NUMERATOR_INSERT_DETAILS);
			pStatement =
				transaction.getConnection().prepareStatement(statementString);
			pStatement.setString(1, numeratorName);
			pStatement.setLong(2, initialValue);
			pStatement.setInt(3, step);

			int numOfRows = pStatement.executeUpdate();
			if (numOfRows != 1)
			{
				throw new PersistenceException(
					"Error creating numerator: " + numeratorName);
			}

			pStatement.close();
			pStatement = null;
		}
		catch (SQLException sqle)
		{
			Logger.error(
				PersistenceConstants.LOGGER_CONTEXT,
				"Error creating numerator: "
					+ numeratorName
					+ ". "
					+ sqle.getMessage());
			throw new PersistenceException(sqle);
		}
		finally
		{
			if (pStatement != null)
			{
				pStatement.close();
			}
		}

		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"creating numerator: "
				+ numeratorName
				+ " last value is "
				+ initialValue
				+ " step is: "
				+ step);
	}

	/**
	 * Update the numerator last value.
	 * @param numeratorName The name of the numerator.
	 * @param transaction The transaction object to use.
	 * @param lastValue The last value
	 * @throws PersistenceException
	 * @throws SQLException
	 */
	private static void updateNumeratorLastValue(
		String numeratorName,
		Transaction transaction,
		long lastValue)
		throws PersistenceException, SQLException
	{
		PreparedStatement pStatement = null;
		try
		{
			// update the numerator row
			String statementString =
				transaction.getConnectionProperty(
					PersistenceConstants.NUMERATOR_UPDATE_DETAILS);
			pStatement =
				transaction.getConnection().prepareStatement(statementString);
			pStatement.setLong(1, lastValue);
			pStatement.setString(2, numeratorName);

			int numOfRows = pStatement.executeUpdate();

			if (numOfRows != 1)
			{
				throw new PersistenceException(
					"Error while last value of numerator: " + numeratorName);
			}
		}
		finally
		{
			if (pStatement != null)
			{
				pStatement.close();
			}
		}
	}

	/**
	 * Returns the details of the numerator.
	 * @param numeratorName The name of the numerator.
	 * @param transaction The transaction object to use.
	 * @return NumeratorDetails Contains the next value & step
	 * @throws PersistenceException
	 * @throws SQLException
	 */
	private static NumeratorDetails getNextDetails(
		String numeratorName,
		Transaction transaction)
		throws PersistenceException, SQLException
	{
		NumeratorDetails numeratorDetails = null;
		long nextId = 0;
		int step = 0;

		PreparedStatement pStatement = null;
		ResultSet resultSet = null;

		try
		{
			String statementString =
				transaction.getConnectionProperty(
					PersistenceConstants.NUMERATOR_GET_DETAILS);
			pStatement =
				transaction.getConnection().prepareStatement(statementString);
			pStatement.setString(1, numeratorName);
			resultSet = pStatement.executeQuery();

			if (resultSet.next())
			{
				nextId =
					resultSet.getLong(
						PersistenceConstants.NUMERATOR_FIELD_LAST_VALUE);
				step =
					resultSet.getInt(PersistenceConstants.NUMERATOR_FIELD_STEP);

				// add the step size to the last value of the numertor
				nextId += step;

				numeratorDetails =
					new NumeratorDetails(numeratorName, nextId, step);
			}
		}
		finally
		{
			if (pStatement != null)
			{
				if (resultSet != null)
				{
					resultSet.close();
				}
				pStatement.close();
			}
		}
		return numeratorDetails;
	}

	/**
	 * Returns the next value of the numerator as a long.
	 * @param numeratorName The name of the numerator.
	 * @return long The next value
	 * @throws PersistenceException
	 */
	public static long getNextLongValue(String numeratorName)
		throws PersistenceException
	{
		return getNumeratorNextDetails(numeratorName, null).getNextId();
	}

	/**
	 * Returns the next value of the numerator as a long.
	 * @param numeratorName The name of the numerator
	 * @param connectionManagerName The name of the connection manager.
	 * @return long The next value
	 * @throws PersistenceException
	 */
	public static long getNextLongValue(
		String numeratorName,
		String connectionManagerName)
		throws PersistenceException
	{
		return getNumeratorNextDetails(numeratorName, connectionManagerName)
			.getNextId();
	}

	/**
	 * Returns the next value of the numerator as an int.
	 * @param numeratorName The name of the numerator
	 * @return int The next value
	 * @throws PersistenceException
	 */
	public static int getNextValue(String numeratorName)
		throws PersistenceException
	{
		long value = getNumeratorNextDetails(numeratorName, null).getNextId();

		checkIntegerMaxValue(numeratorName, value);

		return (int) value;
	}

	/**
	 * Returns the next value of the numerator as an int.
	 * @param numeratorName The name of the numerator
	 * @param connectionManagerName The name of the connection manager.
	 * @return int The next value
	 * @throws PersistenceException
	 */
	public static int getNextValue(
		String numeratorName,
		String connectionManagerName)
		throws PersistenceException
	{
		long value =
			getNumeratorNextDetails(numeratorName, connectionManagerName)
				.getNextId();

		checkIntegerMaxValue(numeratorName, value);

		return (int) value;
	}

	/**
	 * Check if the value excedded the integer max value.
	 * @param numeratorName The name of the numerator
	 * @param value The long value to check
	 * @throws PersistenceException Thrown when the value excedded the integer max value. 
	 */
	protected static void checkIntegerMaxValue(
		String numeratorName,
		long value)
		throws PersistenceException
	{
		if (value > Integer.MAX_VALUE)
		{
			throw new PersistenceException(
				"numerator "
					+ numeratorName
					+ " value excedded max value (integer value).");
		}
	}

}
