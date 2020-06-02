/*
 * Created on: 13/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyService.java,v 1.2 2005/03/22 13:30:41 yifat Exp $
 */
package com.ness.fw.legacy;

import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.ConnectionProvider;
import com.ness.fw.persistence.ConnectionSequence;
import com.ness.fw.persistence.Transaction;
import com.ness.fw.persistence.TransactionFactory;

/**
 * Responsible for executing the legacy programs. 
 */
public class LegacyService
{

	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = "LEGACY SERVICE";
	
	/**
	 * Executes the legacy command of type Stored procedure, using a new connection to the DB.
	 * @param bpc The container that contains the arguments for the SP call.
	 * @return LegacyObjectGraph The legacy call results.
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	public static LegacyObjectGraph executeLegacyCommand(LegacyBPC bpc) throws BusinessLogicException, LegacyCommandException
	{
		return executeLegacyCommand(bpc, null);
	}

	/**
	 * Executes the legacy command of type Stored procedure.
	 * @param bpc The container that contains the arguments for the SP call.
	 * @param connectionProvider A {@link ConnectionProvider} to use its connection calling to the SP.
	 * @return LegacyObjectGraph The legacy call results.
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	public static LegacyObjectGraph executeLegacyCommand(LegacyBPC bpc, ConnectionProvider connectionProvider) throws BusinessLogicException, LegacyCommandException
	{
		LegacyCommand command;
		command = LegacyExternalizer.getCommand(bpc.getLegacyCommandName());
			
		if(command.getCommandType() == LegacyConstants.COMMAND_TYPE_SP)
		{
			return executeSPLegacyCommand(command, bpc, connectionProvider);
		}
		else
		{
			throw new LegacyCommandException ("Unable to execute legacy command [" + bpc.getLegacyCommandName() + "]. The command type [" + command.getCommandType() + "] is not supported.");
		}
		
	}

	/**
	 * Executes the legacy command of type Stored procedure.
	 * @param bpc The container that contains the arguments for the SP call.
	 * @param connectionProvider A {@link ConnectionProvider} to use its connection calling to the SP.
	 * @return LegacyObjectGraph The legacy call results.
	 * @throws LegacyCommandNotFoundException
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	private static LegacyObjectGraph executeSPLegacyCommand(LegacyCommand command, LegacyBPC bpc, ConnectionProvider connectionProvider) throws LegacyCommandException, BusinessLogicException
	{
		LegacyObjectGraph legacyObjectGraph = null;

		Logger.debug(LOGGER_CONTEXT, "Execute SP legacy command [" + bpc.getLegacyCommandName() + "]");

		if(connectionProvider != null)
		{
			legacyObjectGraph = command.execute(bpc, connectionProvider);
		}
		else
		{
			if(command.getActivityType() == LegacyConstants.ACTIVITY_TYPE_READ_WRITE)
			{
				legacyObjectGraph = executeReadWriteSP(((SPLegacyCommand)command), bpc);
			}
			else
			{
				legacyObjectGraph = executeReadOnlySP(((SPLegacyCommand)command), bpc);
			}
		}

		return legacyObjectGraph;
	}

	/**
	 * Executes the sp legacy command, using new read only connection (ConnectionSequence).
	 * @param command The command to execute
	 * @param bpc The container that contains the arguments for the SP call.
	 * @return LegacyObjectGraph The legacy call results.
	 * @throws LegacyCommandException
	 */
	private static LegacyObjectGraph executeReadOnlySP (SPLegacyCommand command, LegacyBPC bpc) throws LegacyCommandException, BusinessLogicException
	{
		LegacyObjectGraph legacyObjectGraph = null;
		ConnectionSequence connectionSequence = null;
		try
		{
			connectionSequence = ConnectionSequence.beginSequence(command.getConnectionManager(), bpc.getUserAuthData());
			connectionSequence.begin();
			
			legacyObjectGraph = command.execute(bpc.getInputContainer(), connectionSequence);
		}
		catch (LegacyCommandException le)
		{
			throw le;
		}
		catch (BusinessLogicException le)
		{
			throw le;
		}
		catch (Throwable e)
		{
			throw new LegacyCommandException ("Legacy command [" + command.getCommandName() + "] execution failed", e);
		}
		finally
		{
			if(connectionSequence != null)
			{
				try
				{
					connectionSequence.end();
				}
				catch (PersistenceException e1)
				{
					throw new LegacyCommandException ("Legacy command [" + command.getCommandName() + "] execution failed", e1);
				}
			}
		}		
		return legacyObjectGraph;
		
	}

	/**
	 * Executes the sp legacy command, using new read write connection (Transaction).
	 * @param command The command to execute
	 * @param bpc The container that contains the arguments for the SP call.
	 * @return LegacyObjectGraph The legacy call results.
	 * @throws BusinessLogicException
	 * @throws LegacyCommandException
	 */
	private static LegacyObjectGraph executeReadWriteSP (SPLegacyCommand command, LegacyBPC bpc) throws BusinessLogicException, LegacyCommandException
	{
		LegacyObjectGraph legacyObjectGraph = null;
		Transaction transaction = null;
		try
		{
			
			transaction = TransactionFactory.createTransaction(bpc.getUserAuthData(), command.getConnectionManager());
			transaction.begin();

			legacyObjectGraph = command.execute(bpc.getInputContainer(), transaction);
			
			transaction.commit();
			transaction = null;
		}
		catch (LegacyCommandException le)
		{
			throw le;
		}
		catch (BusinessLogicException le)
		{
			throw le;
		}
		catch (Throwable e)
		{
			throw new LegacyCommandException ("Legacy command [" + command.getCommandName() + "] execution failed", e);
		}
		finally
		{
			if(transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException e1)
				{
					throw new LegacyCommandException ("Legacy command [" + command.getCommandName() + "] execution failed", e1);
				}
			}
		}		
		return legacyObjectGraph;
	}

}
