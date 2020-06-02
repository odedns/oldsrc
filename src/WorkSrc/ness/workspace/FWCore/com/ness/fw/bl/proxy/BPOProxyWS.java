/*
 * Created on: 21/07/2004
 * Author: yifat har-nof
 * @version $Id: BPOProxyWS.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;

import com.ness.fw.bl.BusinessProcessContainer;

/**
 *  A proxy for executing bpo commands using web services layer.  
 */
class BPOProxyWS
{
	/**
	 * Execute the bpo command using the {@link BPOFacadeWS} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 */
	protected static Object execute(String bpoCommand, BusinessProcessContainer bpc) throws BPOCommandNotFoundException, BPOCommandException
	{
		throw new BPOCommandException("The proxy was not implemented yet");
		 
//		try
//		{
//			// TODO get the BPOFacadeWS from the ...
//			return BPOFacadeWS.execute(bpoCommand, bpc);
//		}
//		catch (NotSerializableException e)
//		{
//			// TODO check the elements, which one is not serializable
//		}
	}

	/**
	 * Execute the bpo command using the {@link BPOFacadeWS} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 */
	protected static void execute(String bpoCommand, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut) throws BPOCommandNotFoundException, BPOCommandException
	{
		throw new BPOCommandException("The proxy was not implemented yet");
		 
//		try
//		{
//			// TODO get the BPOFacadeWS from the ...
//			BPOFacadeWS.execute(bpoCommand, bpcIn, bpcOut);
//		}
//		catch (NotSerializableException e)
//		{
//			// TODO check the elements, which one is not serializable
//		}
	}

}
