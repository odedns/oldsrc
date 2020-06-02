/*
 * Created on 21/12/2004
 *
 * Author: Amit Mendelson
 * @version $Id: WFDBUtil.java,v 1.5 2005/04/27 11:34:25 amit Exp $
 */
package com.ness.fw.workflow.mdb;

import java.util.ArrayList;

import com.ness.fw.persistence.ConnectionSequence;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.SqlService;
import com.ness.fw.persistence.Transaction;
import com.ness.fw.persistence.TransactionFactory;
import com.ness.fw.persistence.Query;

import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

/**
 * @author Amit Mendelson
 *
 * This class handles access to DB during the MDB work.
 */
public class WFDBUtil
{

	public static final String LOGGER_CONTEXT = "WF MDB";

	/**
	 * Retrieve the correlIds from the DB, for the processes that should be released.
	 * @param processIds Ids of the process instances to be released.
	 * @return String[] The correlIds for the passed processIds.
	 * @throws UpesException
	 */
	public static ArrayList getCorrelIds(String[] processNames)
		throws UpesException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"WFDBUtil, started getCorrelIds, processNames: " + processNames);

		if ((processNames == null) || (processNames.length == 0))
		{
			throw new UpesException(UPESConstants.PROCESS_NAMES_EMPTY);
		}

		String selectStatement =
			SystemResources.getInstance().getProperty(
				UPESConstants.SELECT_FROM_DB);
		Logger.debug(
			LOGGER_CONTEXT,
			"getCorrelIds, selectStatement: " + selectStatement);
		if (selectStatement == null)
		{
			throw new UpesException(
				UPESConstants.PROBLEM_WITH_SYSTEM_RESOURCES);
		}
		int indexOfProcessIds =
			selectStatement.indexOf(UPESConstants.PROCESS_NAMES_ARRAY);
		Logger.debug(
			LOGGER_CONTEXT,
			"getCorrelIds, indexOfProcessIds in selectStatement: "
				+ indexOfProcessIds);
		StringBuffer sb = new StringBuffer(1024);
		//Adding the processId to the processIds array.
		for (int i = 0; i < processNames.length; i++)
		{
			sb.append("'");
			sb.append(processNames[i]);
			sb.append("'");
			sb.append(",");
			Logger.debug(
				LOGGER_CONTEXT,
				"getCorrelIds, current process: " + processNames[i]);
		}

		/*
		 * As a comma was added after every processId, the
		 * following if() ensures that the SQL string won't
		 * contain a redundant comma at the end.
		 */
		if (sb.lastIndexOf(",") == sb.length() - 1)
		{
			sb.deleteCharAt(sb.length() - 1);
		}
		StringBuffer sb2 = new StringBuffer(selectStatement);

		int holderLength = UPESConstants.PROCESS_NAMES_ARRAY.length();
		sb2.replace(
			indexOfProcessIds,
			indexOfProcessIds + holderLength,
			sb.toString());
		Page page = null;
		try
		{

			SqlService sqs = new SqlService(sb2.toString());

			Logger.debug(
				LOGGER_CONTEXT,
				"getCorrelIds, created SqlService " + sqs);
			ConnectionSequence connectionSequence =
				ConnectionSequence.beginSequence();
			Logger.debug(
				LOGGER_CONTEXT,
				"getCorrelIds, started connectionSequence");
			page = Query.execute(sqs, connectionSequence);

		}
		catch (PersistenceException pex)
		{
			throw new UpesException(pex);
		}
		Logger.debug(LOGGER_CONTEXT, "getCorrelIds, executed the query");
		ArrayList correlIdList = new ArrayList(page.getRowCount());
		Logger.debug(
			LOGGER_CONTEXT,
			"getCorrelIds, generated the correlIdsList");
		while (page.next())
		{
			correlIdList.add(page.getString(UPESConstants.CORREL_ID));
		}
		Logger.debug(LOGGER_CONTEXT, "getCorrelIds completed");

		return correlIdList;

	}

	/**
	 * Add a processName to the waiting list in the DB.
	 * @param processName name of the new waiting process.
	 * @param correlId correlId received from the message sent by WorkFlow,
	 * Is used to refer the waiting process.
	 * @throws UpesException
	 */
	public static void writeProcess(String processName, String correlId)
		throws UpesException
	{

		Logger.debug(LOGGER_CONTEXT, "WFDBUtil, started writeProcess");
		Logger.debug(
			LOGGER_CONTEXT,
			"WriteProcess, processName: "
				+ processName
				+ ", correlId: "
				+ correlId);

		Transaction transaction = null;

		if (processName == null)
		{
			throw new UpesException(UPESConstants.PROCESS_NAME_IS_NULL);
		}

		if (correlId == null)
		{
			throw new UpesException(UPESConstants.CORREL_ID_IS_NULL);
		}
		String insertStatement =
			SystemResources.getInstance().getProperty(
				UPESConstants.INSERT_TO_DB);
		Logger.debug(
			LOGGER_CONTEXT,
			"WriteProcess, insertStatement: " + insertStatement);
		if (insertStatement == null)
		{
			throw new UpesException(
				UPESConstants.PROBLEM_WITH_SYSTEM_RESOURCES);
		}
		try
		{

			SqlService sqs = new SqlService(insertStatement);
			Logger.debug(
				LOGGER_CONTEXT,
				"WriteProcess, generated new SqlService");

			sqs.addParameter(processName);
			sqs.addParameter(correlId);

			transaction =
				TransactionFactory.createTransaction(
					UserAuthDataFactory.getUserAuthData(
						SystemResources.getInstance().getProperty(
							UPESConstants.UPES_WF_APP_USER_ID)));
			Logger.debug(
				LOGGER_CONTEXT,
				"WriteProcess, generated new transaction");

			transaction.begin();
			Logger.debug(LOGGER_CONTEXT, "WriteProcess, transaction begun");

			transaction.execute(sqs);
			Logger.debug(LOGGER_CONTEXT, "WriteProcess, transaction executed");

			transaction.commit();
			Logger.debug(LOGGER_CONTEXT, "WriteProcess, transaction committed");

			transaction = null;

		}
		catch (PersistenceException pex)
		{
			throw new UpesException(pex);
		}
		finally
		{
			Logger.debug(LOGGER_CONTEXT, "WriteProcess, finally activated");
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
					Logger.debug(
						LOGGER_CONTEXT,
						"WriteProcess, rollBack performed");
				}
				catch (PersistenceException e)
				{
					throw new UpesException(e);
				}
			}
		}
	}

	/**
	 * Remove a correlId from the waiting list in DB.
	 * @param correlId Id of the removed correlId.
	 * @throws UpesException
	 */
	public static void removeCorrelIdFromDB(String correlId)
		throws UpesException
	{

		Logger.debug(
			LOGGER_CONTEXT,
			"started removeCorrelIdFromDB, correlId: " + correlId);
		Transaction transaction = null;

		if (correlId == null)
		{
			throw new UpesException(UPESConstants.CORREL_ID_IS_NULL);
		}
		String deleteStatement =
			SystemResources.getInstance().getProperty(
				UPESConstants.DELETE_FROM_DB);
		Logger.debug(
			LOGGER_CONTEXT,
			"removeCorrelIdFromDB, deleteStatement: " + deleteStatement);
		if (deleteStatement == null)
		{
			throw new UpesException(
				UPESConstants.PROBLEM_WITH_SYSTEM_RESOURCES);
		}

		try
		{

			SqlService sqs = new SqlService(deleteStatement);
			Logger.debug(
				LOGGER_CONTEXT,
				"removeCorrelIdFromDB, generated new SqlService");
			sqs.addParameter(correlId);

			transaction =
				TransactionFactory.createTransaction(
					UserAuthDataFactory.getUserAuthData(
						SystemResources.getInstance().getProperty(
							UPESConstants.UPES_WF_APP_USER_ID)));
			Logger.debug(
				LOGGER_CONTEXT,
				"removeCorrelIdFromDB, generated new transaction");

			transaction.begin();
			Logger.debug(
				LOGGER_CONTEXT,
				"removeCorrelIdFromDB, transaction begun");

			transaction.execute(sqs);
			Logger.debug(
				LOGGER_CONTEXT,
				"removeCorrelIdFromDB, transaction executed");

			transaction.commit();
			Logger.debug(
				LOGGER_CONTEXT,
				"removeCorrelIdFromDB, transaction committed");

			transaction = null;

		}
		catch (PersistenceException pex)
		{
			throw new UpesException(pex);
		}
		finally
		{
			Logger.debug(
				LOGGER_CONTEXT,
				"removeCorrelIdFromDB, finally activated");
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
					Logger.debug(
						LOGGER_CONTEXT,
						"removeCorrelIdFromDB, rollBack performed");
				}
				catch (PersistenceException e)
				{
					throw new UpesException(e);
				}
			}
		}
	}

}
