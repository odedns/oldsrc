/*
 * Created on: 21/07/2004
 * Author: yifat har-nof
 * @version $Id: BPOProxy.java,v 1.2 2005/02/23 07:01:59 yifat Exp $
 */
package com.ness.fw.bl.proxy;

import java.util.Iterator;
import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.bl.proxy.ejb.BPOEJBFacade;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.shared.common.SystemConstants;

/**
 * A proxy for the business process objects.
 * This proxy used for calling the business process methods from the operational layer.  
 */
public class BPOProxy
{
	/**
	 * The logger context.
	 */
	private static final String LOGGER_CONTEXT = "BPO PROXY";

	/**
	 * The BPO implementation type. could be one of the following: Local, ejb, ws
	 */
	private static int implementationType = ServerExternalizer.getDefaultServerDefinition().getImplementationType();
	
	/**
	 * Static block for loading the BPO implementation type defined for the system. 
	 */
	static
	{
		implementationType = ServerExternalizer.getDefaultServerDefinition().getImplementationType();
		Logger.debug(LOGGER_CONTEXT, SystemConstants.SYS_PROPS_KEY_BPO_IMPLEMENTATION_TYPE + " [" + implementationType + "]");
	} 

	/**
	 * Execute the business process method according to the definition of the 
	 * command in the xml file. 
	 * The business process method should be define as following:
	 * 		1) The method should be define as public static method.
	 * 		2) The argumnets should contain a single argument that inherit from BusinessProcessContainer.
	 * 		3) The return value could be void or a Serializable Object 
	 * Example 1: public static Integer saveCustomer (CustomerBPC bpc)
	 * Example 2: public static void saveCustomer (CustomerBPC bpc)
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public static Object execute(String bpoCommandName, BusinessProcessContainer bpc) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		String serverDefinition = ServerExternalizer.getDefaultServerDefinition().getServerName();		
		return execute(bpoCommandName,bpc,serverDefinition);
	}

	/**
	 * Execute the business process method according to the definition of the 
	 * command in the xml file. 
	 * The business process method should be define as following:
	 * 		1) The method should be define as public static method.
	 * 		2) The argumnets should contain 2 arguments that inherit from 
	 *         BusinessProcessContainer, one for input data and one for output data.
	 * 		3) The return value should be void 
	 * Example 1: public static void saveCustomer (CustomerInBPC bpcIn, CustomerOutBPC bpcOut)
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public static void execute(String bpoCommandName, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		String serverDefinition = ServerExternalizer.getDefaultServerDefinition().getServerName();		
		execute(bpoCommandName, bpcIn, bpcOut, serverDefinition);
	}
	
	/**
	 * Execute the business process method according to the definition of the 
	 * command in the xml file. 
	 * The business process method should be define as following:
	 * 		1) The method should be define as public static method.
	 * 		2) The argumnets should contain a single argument that inherit from BusinessProcessContainer.
	 * 		3) The return value could be void or a Serializable Object 
	 * Example 1: public static Integer saveCustomer (CustomerBPC bpc)
	 * Example 2: public static void saveCustomer (CustomerBPC bpc)
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @param serverDefinitionName The server definition name whice the proxy will work.
	 * 	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public static Object execute(String bpoCommandName, BusinessProcessContainer bpc, String serverDefinitionName) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		Object results = null;
		
		ServerDefinition serverDefinition = ServerExternalizer.getServer(serverDefinitionName);
		// if server not found, use the default one
		if (serverDefinition == null)
		{
			serverDefinition = ServerExternalizer.getDefaultServerDefinition();
		}

		// override the default system implementation type
		int implementationType = serverDefinition.getImplementationType();
		
		if(implementationType == SystemConstants.BPO_IMPLEMENTATION_TYPE_LOCAL)
		{
			// direct method call to the local facade
			results = BPOFacadeLocal.execute(bpoCommandName, bpc);
		}
		else if(implementationType == SystemConstants.BPO_IMPLEMENTATION_TYPE_EJB)
		{
			// call to the proxy to get the ejb facade
			results = BPOProxyEJB.execute(bpoCommandName, bpc, serverDefinition);
		}
		else if(implementationType == SystemConstants.BPO_IMPLEMENTATION_TYPE_WS)
		{
			// call to the proxy to get the ws facade
			results = BPOProxyWS.execute(bpoCommandName, bpc);
		}
		
		return results;
	}

	/**
	 * Execute the business process method according to the definition of the 
	 * command in the xml file. 
	 * The business process method should be define as following:
	 * 		1) The method should be define as public static method.
	 * 		2) The argumnets should contain 2 arguments that inherit from 
	 *         BusinessProcessContainer, one for input data and one for output data.
	 * 		3) The return value should be void 
	 * Example 1: public static void saveCustomer (CustomerInBPC bpcIn, CustomerOutBPC bpcOut)
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @param serverDefinitionName The server definition name whice the proxy will work.
	 * 	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public static void execute(String bpoCommandName, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut, String serverDefinitionName) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{

		ServerDefinition serverDefinition = ServerExternalizer.getServer(serverDefinitionName);
		// if server not found, use the default one
		if (serverDefinition == null)
		{
			serverDefinition = ServerExternalizer.getDefaultServerDefinition();
		}

		// override the default system implementation type
		int implementationType = serverDefinition.getImplementationType();

		if(implementationType == SystemConstants.BPO_IMPLEMENTATION_TYPE_LOCAL)
		{
			// direct method call to the local facade
			BPOFacadeLocal.execute(bpoCommandName, bpcIn, bpcOut);
		}
		else if(implementationType == SystemConstants.BPO_IMPLEMENTATION_TYPE_EJB)
		{
			// call to the proxy to get the ejb facade
			BPOProxyEJB.execute(bpoCommandName, bpcIn, bpcOut, serverDefinition);
		}
		else if(implementationType == SystemConstants.BPO_IMPLEMENTATION_TYPE_WS)
		{
			// call to the proxy to get the ws facade
			BPOProxyWS.execute(bpoCommandName, bpcIn, bpcOut);
		}
	}
	
	protected static void reloadServers(String serverConfigurationLocation) throws Throwable
	{
		// for local implementation the servlet will reload it by calling
		// init configuration that call SystemInitializer.init 
		
		if(implementationType == SystemConstants.BPO_IMPLEMENTATION_TYPE_EJB)
		{
			// reload bpo (the others will be reloaded by the servlet)
			ServerExternalizer.initialize(serverConfigurationLocation);
			Iterator iterator = ServerExternalizer.getServers();
			while (iterator.hasNext())
			{
				String serverName = (String)iterator.next();
				ServerDefinition serverDefinition =  ServerExternalizer.getServer(serverName);				
				int type = serverDefinition.getImplementationType();
				if (type == SystemConstants.BPO_IMPLEMENTATION_TYPE_EJB)
				{
					BPOEJBFacade facade = BPOProxyEJB.getEJB(ServerExternalizer.getServer(serverName));
					facade.reloadSystemConfig();
				}
				
				else if (type == SystemConstants.BPO_IMPLEMENTATION_TYPE_WS)
				{
					// TODO: need 2 implement
				}
			}
		}		
	}


	protected static void setImplementationType(int implType)
	{
		implementationType = implType;
	}

}
