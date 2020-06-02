/*
 * Author: yifat har-nof
 * @version $Id: Tomcat403.java,v 1.1 2005/02/21 15:07:12 baruch Exp $
 */
package com.ness.fw.persistence.connectionmanager;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.*;

import com.ness.fw.persistence.PersistenceConstants;
import com.ness.fw.util.SystemProperties;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * An implementation of the {@link ConnectionManager} interface that
 * manages connections for Tomcat 4.03.
 */
public class Tomcat403 extends AbstractConnectionManager
{
	/**
	 * Data Source lookup string
	 */
	private static final String DATA_SOURCE_LOOKUP = "java:comp/env";

	/** The data source name.
	 */
	private String dataSourceName;

	/** The database user name.
	 */
	private String dbUser;

	/** The database user password
	 */
	private String dbPassword;

	/** Create a new JDBCStandard.
	 * @param connectionManagerName The name of the connection manager.
	 * @param dbProperties A {@link SystemProperties} file that holds specific data source and database properties.
	 */
	public Tomcat403(String connectionManagerName, SystemProperties dbProperties) 
	{
		super (connectionManagerName, dbProperties);
		dataSourceName = dbProperties.getProperty(PersistenceConstants.CONNECTION_DATA_SOURCE);
		dbUser = dbProperties.getProperty(PersistenceConstants.CONNECTION_DB_USER);
		dbPassword = dbProperties.getProperty(PersistenceConstants.CONNECTION_DB_PASSWORD);
	}

	/** Close the specified database connection. It should be noted that the connection is
	 * not realy closed, it is returned to the Websphere database connections pool.
	 * @param connection The database connection to close.
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

	/** Get a new database connection. It should be noted that the connection is
	 * not realy a new one, it is retrieved from the Websphere database connections pool.
	 * @return Connection A new database connection or <I>null</I> if failed.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public Connection getConnection() throws PersistenceException
	{
		Connection con = null;
		try
		{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup(DATA_SOURCE_LOOKUP);
			DataSource ds = (DataSource) envCtx.lookup("jdbc/" + dataSourceName);
			if (dbUser == null || dbUser == "")
			{
				con = ds.getConnection();
			}
			else
			{
				con = ds.getConnection(dbUser, dbPassword);
			}
		}
		catch (Throwable t)
		{
			throw new PersistenceException(t);
		}
		return con;
	}

}