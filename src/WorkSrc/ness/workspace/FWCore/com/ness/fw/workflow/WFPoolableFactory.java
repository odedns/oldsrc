package com.ness.fw.workflow;

import com.ibm.workflow.api.FmcException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.pool.BasePoolableObjectFactory;
import com.ness.fw.common.pool.PoolException;
import com.ness.fw.common.resources.SystemResources;
public class WFPoolableFactory extends BasePoolableObjectFactory
{

	/**
	 * Logger context for writing to the Log.
	 */
	private static final String LOGGER_CONTEXT = "WFPoolableFactory";

	/**
	 * Creates an instance that can be returned by the pool.
	 * @return an instance that can be returned by the pool.
	 */
	public Object makeObject() throws PoolException
	{

		// TODO : use the constants from the property file
		SystemResources systemResources = SystemResources.getInstance();
		String userId =
			systemResources.getProperty(WFConstants.MANAGER_USER_ID_KEY);
		String password =
			systemResources.getProperty(WFConstants.MANAGER_PASSWORD_KEY);
		try
		{
			// TODO check session mode check if should logon with credentials
			return WFLogonManager.internalLogon(userId, password);
		}

		catch (GeneralException ge)
		{
			throw new PoolException("Error while logon", ge);
		}

		catch (FmcException e)
		{
			throw new PoolException("FMC Error:", e);
		}
	}

	/**
	 * Destroys an instance no longer needed by the pool.
	 * @param obj the instance to be destroyed
	 */
	public void destroyObject(Object obj) throws PoolException
	{
		Logger.debug(LOGGER_CONTEXT, "started destroyObject");
		WFExecutionService executionService = (WFExecutionService) obj;

		try
		{
			if (executionService.getExecutionService().isLoggedOn())
			{
				executionService.getExecutionService().logoff();
			}
		}
		catch (FmcException e)
		{
			throw new PoolException("FMC Error:", e);
		}

	}

	/**
	 * Ensures that the instance is safe to be returned by the pool.
	 * Returns <tt>false</tt> if this object should be destroyed.
	 * @param obj the instance to be validated
	 * @return <tt>false</tt> if this <i>obj</i> is not valid and should
	 *         be dropped from the pool, <tt>true</tt> otherwise.
	 */
	public boolean validateObject(Object obj)
	{
		WFExecutionService executionService = (WFExecutionService) obj;
		try
		{
			if (!executionService.getExecutionService().isLoggedOn())
			{
				WFLogonManager.relogon(executionService);
			}
		}

		catch (FmcException e)
		{
			return false;
		}

		return true;
	}

}
