/*
 * Author: yifat har-nof
 * @version $Id: ConnectionManager.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.persistence.connectionmanager;

import java.sql.*;

import com.ness.fw.persistence.SqlPropertiesFactory;
import com.ness.fw.util.SystemProperties;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * A generic database connection manager.
 * This interface must be implemented by a connection manager, if
 * it should be produced by the {@link ConnectionManagerFactory} class.
 */
public interface ConnectionManager 
{
	
    /** Get a connection from the connection manager.
     * The connection can be either new or pooled connection, depending
     * on the implementing class.
     * @return Connection A new or pooled connection.
     * @throws PersistenceException Any PersistenceException that may occur.
     */
    public Connection getConnection() throws PersistenceException;

    /** Close a connection.
     * A connection can be closed or returned, depending
     * on the implementing class.
     * @param connection The connection that should be closed or returned to the pool.
     * @throws PersistenceException Any PersistenceException that may occur.
     */
    public void closeConnection(Connection connection) throws PersistenceException;

	/** Get the name of the current connection manager.
	 * @return String The name of the current connection manager.
	 */
	public String getName();
	
	/**
	 * returns the db property value related to the given key.
	 * @param key key name
	 * @return String The property value 
	 */
	public String getProperty(String key);

	/**
	 * returns the db property value related to the given key.
	 * @param key key name
	 * @return int The property value 
	 */
	public int getIntProperty(String key);
	
	/**
	 * returns the db properties file related to the current connection manager.
	 * @return SystemProperties The properties file
	 */
	public SystemProperties getProperties();

	/**
	 * returns the <code>SqlPropertiesFactory</code> related to the current connection manager.
	 * @return SqlPropertiesFactory The SqlPropertiesFactory object that supply sql props files according to the connection manager.
	 */
	public SqlPropertiesFactory getSqlPropertiesFactory();


}