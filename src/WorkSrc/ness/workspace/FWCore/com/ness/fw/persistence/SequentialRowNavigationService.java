/*
 * Created on: 03/04/2005
 * Author: yifat har-nof
 * @version $Id: SequentialRowNavigationService.java,v 1.4 2005/04/05 09:57:05 yifat Exp $
 */
package com.ness.fw.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Sequential row navigation service on query results with open connection to the db.
 * Allow only forword navigation on the result set (only next).
 * 
 * The query will be executed on the constructor (on loadOnStart=true) or 
 * when begin method is executed.
 *   
 * In the end of the query processing you should execute "end" method to 
 * release the resources.
 */
public class SequentialRowNavigationService extends RowNavigationService
{

	private static final String LOGGER_CONTEXT =
		PersistenceConstants.LOGGER_CONTEXT + "SEQUENTIAL RNS";

	/**
	 * Creates new SequentialRowNavigationService
	 * @param sqlService The {@link SqlService} object to exceute
	 * @param connectionProvider The ConnectionProvider to use its connection.
	 * @param loadOnStart Indicates whether to load the query results.  
	 * @throws PersistenceException
	 */
	public SequentialRowNavigationService(
		SqlService sqlService,
		ConnectionProvider connectionProvider,
		boolean loadOnStart)
		throws PersistenceException
	{
		super(sqlService, connectionProvider, loadOnStart);
	}

	/**
	 * load the result set.
	 */
	protected void loadResultSet() throws PersistenceException
	{
		try
		{
			long startTime = System.currentTimeMillis();

			PreparedStatement pst =
				getSqlService().getPreparedStatement(getConnectionProvider());
			ResultSet rs = pst.executeQuery();

			setResultSet(rs);
			Logger.debug(
				LOGGER_CONTEXT,
				"load execution time ["
					+ (System.currentTimeMillis() - startTime)
					+ "]");
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
			Logger.error(LOGGER_CONTEXT, "load failed. " + sqle.getMessage());
			throw new PersistenceException(sqle);
		}
	}

}
