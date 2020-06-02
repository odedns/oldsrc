/*
 * Created on 12/05/2004
 * Author: yifat har-nof
 * @version $Id: ConnectionProvider.java,v 1.4 2005/04/04 09:41:17 yifat Exp $
 */
package com.ness.fw.persistence;

import java.sql.Connection;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.connectionmanager.ConnectionManager;
import com.ness.fw.persistence.connectionmanager.ConnectionManagerFactory;

/**
 * The super class for the classes that provides a connection.
 */
public abstract class ConnectionProvider
{

	/** 
	 * A constant indicates a virtual user for batch.
	 */
	public static final String BATCH_USER = "batchUser";

	/** 
	 * The current ConnectionManager.
	 */
	protected ConnectionManager connectionManager = null;

	/** 
	 * The current database connection.
	 */
	protected Connection connection = null;

	/**
	 * The SqlPropertiesFactory object that supply sql properties files 
	 * according to the connection manager.
	 */
	protected SqlPropertiesFactory sqlPropertiesFactory;

	/**
	 * Contains the basic authorization data of the current user.
	 */
	protected UserAuthData userAuthData;

	/** 
	 * Create a new ConnectionProvider object based on specific connection manager.
	 * @param connectionManagerName The name of connection manager to use.
	 */
	public ConnectionProvider(
		String connectionManagerName,
		UserAuthData userAuthData)
		throws PersistenceException
	{
		connectionManager =
			ConnectionManagerFactory.getConnectionManager(
				connectionManagerName);

		this.userAuthData = userAuthData;

		sqlPropertiesFactory = connectionManager.getSqlPropertiesFactory();
	}

	/** 
	 * Get the connection so that other services can use it.
	 * @return Connection The connection.
	 */
	protected final Connection getConnection() throws PersistenceException
	{
		if (connection == null)
			throw new PersistenceException("The Connection was not started. Please call to begin method!!");
		return connection;
	}

	/** 
	 * Release all the resources if they have not been previously released.
	 */
	public void finalize()
	{
		try
		{
			if (connection != null && connectionManager != null)
			{
				connectionManager.closeConnection(connection);
			}
			connection = null;
			connectionManager = null;
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Return current {@link ConnectionManager} name.
	 * @return String current {@link ConnectionManager} name.
	 */
	public final String getConnectionManagerName()
	{
		return connectionManager.getName();
	}

	/**
	 * Return current {@link ConnectionManager}.
	 * @return ConnectionManager
	 */
	protected final ConnectionManager getConnectionManager()
	{
		return connectionManager;
	}

	/**
	 * Returns the property value related to the given key & file.
	 * @param fileName The name of the properties file.
	 * @param key The key name
	 * @return String The property value 
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public final String getProperty(String fileName, String key)
		throws PersistenceException
	{
		return sqlPropertiesFactory.getProperty(fileName, key);
	}

	/**
	 * Returns the property value related to the given key from the 
	 * connection manager properties file.
	 * @param key The key name
	 * @return String The property value 
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected final String getConnectionProperty(String key)
	{
		return connectionManager.getProperty(key);
	}

	/**
	 * Returns the property value related to the given key from the 
	 * connection manager properties file.
	 * @param key The key name
	 * @return int The property value 
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected final int getIntConnectionProperty(String key)
	{
		return connectionManager.getIntProperty(key);
	}

	/**
	 * Returns the current user id.
	 * @return String The current user id. 
	 * @throws PersistenceException thrown when the UserAuthData is null.
	 */
	public String getUserId() throws PersistenceException
	{
		if (userAuthData != null)
			return userAuthData.getUserID();
		else
			throw new PersistenceException("UserAuthData is null in the connection provider");
	}

	/**
	 * Returns the current user id.
	 * @return String The current user id. 
	 */
	protected String getUserIdInternal()
	{
		if (userAuthData != null)
			return userAuthData.getUserID();
		else
			return null;
	}

	/**
	 * Returns the {@link UserAuthData}  object contains the basic authorization 
	 * data of the current user.
	 * @return UserAuthData
	 */
	public UserAuthData getUserAuthData()
	{
		return userAuthData;
	}

	/**
	 * Sets the {@link UserAuthData} object contains the basic authorization 
	 * data of the current user.
	 * @param userAuthData
	 */
	public void setUserAuthData(UserAuthData userAuthData)
	{
		this.userAuthData = userAuthData;
	}

}
