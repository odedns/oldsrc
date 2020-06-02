/*
 * Created on: 13/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyBPO.java,v 1.2 2005/03/22 13:23:10 yifat Exp $
 */
package com.ness.fw.legacy;

import com.ness.fw.bl.BusinessProcess;
import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.common.exceptions.BusinessLogicException;

/**
 * The legacy business processes that execute the legacy calls. 
 */
public class LegacyBPO extends BusinessProcess
{

	/**
	 * Executes the legacy command, using a new connection to the DB.
	 * @param bpc The container that contains the arguments for the SP call.
	 * @return LegacyObjectGraph The legacy call results.
	 * @throws BusinessLogicException
	 * @throws BPOCommandException
	 */
	public static LegacyObjectGraph executeLegacyCommand(LegacyBPC bpc) throws BusinessLogicException, BPOCommandException
	{
		try
		{
			return LegacyService.executeLegacyCommand(bpc);
		}
		catch (LegacyCommandException e)
		{
			throw new BPOCommandException("error executing legacy command", e);
		}
	}

}
