/*
 * Author: yifat har-nof
 * @version $Id: AbstractConnectionManager.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.persistence.connectionmanager;

import java.sql.*;

import com.ness.fw.persistence.SqlPropertiesFactory;
import com.ness.fw.util.SystemProperties;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Generic ConnectionManager Object class.
 * A ConnectionManager implementation must inherit this class.
 * A basic implementation of the {@link ConnectionManager} interface .
 */
public abstract class AbstractConnectionManager implements ConnectionManager
{

	/** The properties file name that holds specific data source and database properties.
	 */
	private SystemProperties dbProperties;

	/** The name of the connection manager.
	 */
	private String connectionManagerName;

	/** Create a new ConnectionManager.
	 * @param connectionManagerName The name of the connection manager.
	 * @param dbProperties A {@link SystemProperties} file that holds specific data source and database properties.
	 */
	public AbstractConnectionManager(String connectionManagerName, SystemProperties dbProperties) 
	{
		this.dbProperties = dbProperties;
		this.connectionManagerName = connectionManagerName;
	}

	/** Close the specified database connection. It should be noted that the connection is
	 * not realy closed, it is returned to the Websphere database connections pool.
	 * @param connection The database connection to close.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public abstract void closeConnection(Connection connection) throws PersistenceException;

	/** Get a new database connection. It should be noted that the connection is
	 * not realy a new one, it is retrieved from the Websphere database connections pool.
	 * @return Connection A new database connection or <I>null</I> if failed.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public abstract Connection getConnection() throws PersistenceException;

	/** Get the name of the current connection manager.
	 * @return String The name of the current connection manager.
	 */
	public String getName()
	{
		return connectionManagerName;
	}
	
	/**
	 * returns the db property value related to the given key.
	 * @param key key name
	 * @return String The property value 
	 */
	public final String getProperty(String key) 
	{
		return dbProperties.getProperty(key);
	}

	/**
	 * returns the db property value related to the given key.
	 * @param key key name
	 * @return int The property value 
	 */
	public final int getIntProperty(String key)
	{
		return dbProperties.getInt(key);
	}


	/**
	 * returns the db properties file related to the current connection manager.
	 * @return SystemProperties The properties file
	 */
	public SystemProperties getProperties() 
	{
		return dbProperties;
	}

	/**
	 * returns the <code>SqlPropertiesFactory</code> related to the current connection manager.
	 * @return SqlPropertiesFactory The SqlPropertiesFactory object that supply sql props files according to the connection manager.
	 */
	public SqlPropertiesFactory getSqlPropertiesFactory() 
	{
		//TODO get the factory according to connection manager name
		return SqlPropertiesFactory.getInstance();
	}

}