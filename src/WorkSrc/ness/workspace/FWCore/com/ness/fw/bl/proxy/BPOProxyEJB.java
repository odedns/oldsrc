/*
 * Created on: 21/07/2004
 * Author: yifat har-nof
 * @version $Id: BPOProxyEJB.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.*;
import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.bl.proxy.ejb.BPOEJBFacade;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.shared.common.SystemUtil;

/**
 *  A proxy for executing bpo commands using EJB layer.  
 */
class BPOProxyEJB
{

	// TODO: should manage many EJB's ????

	private static BPOEJBFacade facade;

	/**
	 * Execute the bpo command using the {@link BPOFacadeEJB} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	protected static Object execute(String bpoCommand, BusinessProcessContainer bpc) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{		
		return execute(bpoCommand, bpc,ServerExternalizer.getDefaultServerDefinition());
	}

	/**
	 * Execute the bpo command using the {@link BPOFacadeEJB} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	protected static void execute(String bpoCommand, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		execute(bpoCommand, bpcIn, bpcOut, ServerExternalizer.getDefaultServerDefinition());
	}
	
	/**
	 * Execute the bpo command using the {@link BPOFacadeEJB} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @param serverDefinition The server definition whice the proxy will work.
	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	protected static Object execute(String bpoCommand, BusinessProcessContainer bpc, ServerDefinition serverDefinition) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{		
		EJBContainer container = null;
		try
		{
			// get the BPOFacadeEJB from the ejb container 			
			facade = getEJB(serverDefinition);
			container = facade.execute(bpoCommand, bpc);
			SystemUtil.copyContainerData(bpc,container.getBpc());
		}


		catch (NamingException e)
		{
			throw new BPOCommandException("ejb->",e);
		}
		
		// This exception contain a nested exception, whice can be 
		// a NotSerializableException. this excpetion confirm
		// the fields that are not serializable
		catch (RemoteException e)
		{
			throw new BPOCommandException("ejb->",e);
		} 
		catch (CreateException e)
		{
			throw new BPOCommandException("ejb->",e);
		}
	
		return container.getResult();

	}

	/**
	 * Execute the bpo command using the {@link BPOFacadeEJB} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @param serverDefinition The server definition whice the proxy will work.
	 * 	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	protected static void execute(String bpoCommand, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut, ServerDefinition serverDefinition) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		EJBContainer container = null;

		try
		{
			// get the BPOFacadeEJB from the ejb container 
			facade = getEJB(serverDefinition);
			container = facade.execute(bpoCommand, bpcIn, bpcOut);
			SystemUtil.copyContainerData(bpcOut,container.getBpc());
		}

		catch (NamingException e)
		{
			throw new BPOCommandException("ejb->",e);
		}
		
		// This exception contain a nested exception, whice can be 
		// a NotSerializableException. this excpetion confirm
		// the fields that are not serializable
		catch (RemoteException e)
		{
			throw new BPOCommandException("ejb->",e);
		} 
		catch (CreateException e)
		{
			throw new BPOCommandException("ejb->",e);
		}

	}


	protected static BPOEJBFacade getEJB() throws RemoteException, NamingException, CreateException
	{
		EJBLocator locator = EJBLocatorFactory.findEJB();
		facade = (BPOEJBFacade)locator.getEJB();
		
		return facade;		
	}
	
	protected static BPOEJBFacade getEJB(ServerDefinition serverDefinition) throws RemoteException, NamingException, CreateException
	{
		EJBLocator locator = EJBLocatorFactory.findEJB();
		facade = (BPOEJBFacade)locator.getEJB(serverDefinition);
		
		return facade;		
	}
}
