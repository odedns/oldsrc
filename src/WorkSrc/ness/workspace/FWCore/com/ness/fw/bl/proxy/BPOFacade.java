/*
 * Created on: 21/07/2004
 * Author: yifat har-nof
 * @version $Id: BPOFacade.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.common.exceptions.BusinessLogicException;

/**
 *  The super class for the BPO facade objects.  
 */
abstract class BPOFacade
{

	/**
	 * Execute the bpo command using the {@link BPODispatcher}. 
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	protected static Object execute(String bpoCommand, BusinessProcessContainer bpc) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		// direct method call to the dispatcher
		return BPODispatcher.execute(bpoCommand, bpc);
	}

	/**
	 * Execute the bpo command using the {@link BPODispatcher}. 
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	protected static void execute(String bpoCommand, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		// direct method call to the dispatcher
		BPODispatcher.execute(bpoCommand, bpcIn, bpcOut);
	}

}
