/*
 * Author: yifat har-nof
 * @version $Id: JDBCStandard.java,v 1.1 2005/02/21 15:07:12 baruch Exp $
 */
package com.ness.fw.persistence.connectionmanager;

import java.sql.*;

import com.ness.fw.persistence.PersistenceConstants;
import com.ness.fw.util.SystemProperties;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * An implementation of the {@link ConnectionManager} interface that
 * manages connections for a standard JDBC driver.
 */
public class JDBCStandard extends AbstractConnectionManager
{

	/** The class name of the JDBC driver to use.
	 */
	private String driver;

	/** The connection string that contains connection and driver specific
	 * attributes, such as translation options etc.
	 */
	private String connectionString;

	/** The database user name.
	 */
	private String dbUser;

	/** The database user password.
	 */
	private String dbPassword;

	/** Create a new JDBCStandard.
	 * @param connectionManagerName The name of the connection manager.
	 * @param dbProperties A {@link SystemProperties} file that holds specific data source and database properties.
	 */
	public JDBCStandard(String connectionManagerName, SystemProperties dbProperties)
	{
		super (connectionManagerName, dbProperties);
		driver = dbProperties.getProperty(PersistenceConstants.CONNECTION_DRIVER);
		connectionString = dbProperties.getProperty(PersistenceConstants.CONNECTION_STRING);
		dbUser = dbProperties.getProperty(PersistenceConstants.CONNECTION_DB_USER);
		dbPassword = dbProperties.getProperty(PersistenceConstants.CONNECTION_DB_PASSWORD);
	}

	/** Close the specified database connection.
	 * @param connection the database connection to close.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void closeConnection(Connection connection) throws PersistenceException
	{
		try
		{
			if (connection != null && !connection.isClosed())
				connection.close();
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/** Get a new database connection.
	 * @return Connection A new database connection or <I>null</I> if failed.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Connection getConnection() throws PersistenceException
	{
		Connection con = null;
		try
		{
			Class.forName(driver).newInstance();
			if (dbUser == null || dbUser == "")
			{
				con = DriverManager.getConnection(connectionString);
			}
			else
			{
				con =
					DriverManager.getConnection(
						connectionString,
						dbUser,
						dbPassword);
			}
		}
		catch (Throwable t)
		{
			throw new PersistenceException(t);
		}
		return con;
	}
	
}