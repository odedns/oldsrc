/*
 * Author: yifat har-nof
 * @version $Id: ConnectionSequence.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Allow execution of a sequence of SQL Operations in the same connection.
 */
public class ConnectionSequence extends ConnectionProvider
{

	/** Create a new ConnectionSequence object based on a database properties file.
	 * @param connectionManagerName The name of connection manager to use.
	 * @param userAuthData contains the basic authorization data of the current user.
	 * @throws PersistenceException Failure in the process of begining the transaction.
	 */
	public ConnectionSequence(
		String connectionManagerName,
		UserAuthData userAuthData)
		throws PersistenceException
	{
		super(connectionManagerName, userAuthData);
	}

	/** Create a new ConnectionSequence object based on a database properties file.
	 * @param connectionManagerName The name of connection manager to use.
	 * @throws PersistenceException Failure in the process of begining the transaction.
	 */
	public ConnectionSequence(String connectionManagerName)
		throws PersistenceException
	{
		super(connectionManagerName, null);
	}

	/** 
	 * Create a new ConnectionSequence object with the default connection manager.
	 * @param userAuthData contains the basic authorization data of the current user.
	 * @throws PersistenceException Failure in the process of begining the transaction.
	 */
	public ConnectionSequence(UserAuthData userAuthData)
		throws PersistenceException
	{
		super(null, userAuthData);
	}

	/** 
	 * Create a new ConnectionSequence object with the default connection manager.
	 * @throws PersistenceException Failure in the process of begining the transaction.
	 */
	public ConnectionSequence() throws PersistenceException
	{
		super(null, null);
	}

	/**
	 * Creates new <code>ConnectionSequence</code> based on a database properties file 
	 * and call to begin method.
	 * @param connectionManagerName The name of connection manager to use.
	 * @param userAuthData contains the basic authorization data of the current user.
	 * @return ConnectionSequence
	 * @throws PersistenceException
	 */
	public static ConnectionSequence beginSequence(
		String connectionManagerName,
		UserAuthData userAuthData)
		throws PersistenceException
	{
		ConnectionSequence seq =
			new ConnectionSequence(connectionManagerName, userAuthData);
		seq.begin();
		return seq;
	}

	/**
	 * Creates new <code>ConnectionSequence</code> with the default connection manager 
	 * and call to begin method.
	 * @param userAuthData contains the basic authorization data of the current user.
	 * @return ConnectionSequence
	 * @throws PersistenceException
	 */
	public static ConnectionSequence beginSequence(UserAuthData userAuthData)
		throws PersistenceException
	{
		return beginSequence(null, userAuthData);
	}

	/**
	 * Creates new <code>ConnectionSequence</code> based on a database properties file 
	 * and call to begin method.
	 * @param connectionManagerName The name of connection manager to use.
	 * @return ConnectionSequence
	 * @throws PersistenceException
	 */
	public static ConnectionSequence beginSequence(String connectionManagerName)
		throws PersistenceException
	{
		return beginSequence(connectionManagerName, null);
	}

	/**
	 * Creates new <code>ConnectionSequence</code> with the default connection manager 
	 * and call to begin method.
	 * @return ConnectionSequence
	 * @throws PersistenceException
	 */
	public static ConnectionSequence beginSequence()
		throws PersistenceException
	{
		return beginSequence(null, null);
	}

	/** Open a database connection and begin a sequence of SQL operations.
	 * @throws PersistenceException Failure in the process of begining the transaction.
	 */
	public void begin() throws PersistenceException
	{
		if (connection != null)
		{
			throw new PersistenceException("Connection already began");
		}
		connection = connectionManager.getConnection();
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"Begin connection sequence");
	}

	/** End the sequence of SQL operations execution.
	 * This method releases the resources involved,
	 * which are the {@link ConnectionManager} and the {@link Connection}.
	 * @throws PersistenceException Failure in the process of commiting the transaction.
	 * The transaction is rolled back and the resources are released.
	 */
	public void end() throws PersistenceException
	{
		Logger.debug(
			PersistenceConstants.LOGGER_CONTEXT,
			"End connection sequence");
		if (connection != null)
		{
			connectionManager.closeConnection(connection);
			connection = null;
		}
	}

}