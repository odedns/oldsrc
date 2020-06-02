/*
 * Created on: 26/01/2005
 * Author: baruch hizkya
 * @version $Id
 */
package com.ness.fw.bl.proxy;

import com.ness.fw.flower.factory.*;
import com.ness.fw.flower.factory.externalization.ExternalizerConstants;
import com.ness.fw.flower.factory.externalization.ExternalizerUtil;
import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;
import org.w3c.dom.*;
import java.util.*;

/**
 * The ServerExternalizer is responsible for managing server definitions
 *
 */
public class ServerExternalizer
{
	private static final String LOGGER_CONTEXT = FlowElementsFactoryImpl.LOGGER_CONTEXT + " SERVERS EXT.";

	private static final String SERVER_TAG_NAME  = "serverDefinition";
	private static final String ATTR_URL 		 = "url";
	private static final String ATTR_TYPE 		 = "type";
	private static final String ATTR_JNDI_NAME	 = "jndiName";
	private static final String ATTR_DEFAULT 	 = "default";
	private static final String ATTR_REF_NAME 	 = "refName";
	
	/**
	 * server types
	 */
	private static final String EJB_TYPE 		 = "ejb";
	private static final String LOCAL_TYPE 		 = "local";
	private static final String WS_TYPE 		 = "ws";
	
	private static String defaultServerName;
	
	/**
	 * container for the servers definition
	 */
	private static HashMap servers = new HashMap();


	/**
	 * Retuns all the servers definitions
	 * @return Iterator
	 */
	public static Iterator getServers()
	{
		return servers.keySet().iterator();
	}

	/**
	 * Returns a ServerDefinition according to logic server name
	 * @param serverName
	 * @return ServerDefinition
	 */
	public static ServerDefinition getServer(String serverName)
	{
		return (ServerDefinition)servers.get(serverName);
	}
	
	/**
	 * Returns the default ServerDefinition
	 * @return ServerDefinition
	 */
	public static ServerDefinition getDefaultServerDefinition()
	{
		return (ServerDefinition)servers.get(defaultServerName);
	}

	/**
	 * Initialize the servers definitions from the xml files. 
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 * @param confFilesRoots A list of root path of the servers configuration xml files.
	 * @throws ServerExternalizerException
	 */
	public static void initialize (String confFilesRoot) throws ServerExternalizerException
	{ 
		
		//creating DOM repository
		DOMRepository domRepository = new DOMRepository();
		
		try
		{
			domRepository.initialize(confFilesRoot);
		} catch (ExternalizationException e)
		{
		//	throw new BPOCommandsExternalizationException("Unable to initialize servers DOM Repository", e);
		}

		initializeServers(domRepository);		
	}

	/**
	 * Clears all the servers in the pool and initialize the servers 
	 * from the given DOM repository. 
	 * @param domRepository The DOMRepository to load the servers from.
	 */
	private static void initializeServers (DOMRepository domRepository) throws ServerExternalizerException 
	{
		synchronized (servers)
		{
			Logger.debug(LOGGER_CONTEXT, "loading servers");
			
			servers.clear();
			defaultServerName = null;
			
			loadServers(domRepository);
			
			// init implType in BPOProxy from here because it is static
			// and we want to change impl without reloaf the server
			BPOProxy.setImplementationType(getDefaultServerDefinition().getImplementationType()); 
		}
	}

	/**
	 * Load the servers from the given DOM repository.
	 * @param domRepository
	 * @throws ServerExternalizerException
	 */
	private static void loadServers(DOMRepository domRepository) throws ServerExternalizerException
	{
		servers = new HashMap();

        // TODO: should be in externilzer constans ?????
        DOMList domList = domRepository.getDOMList(SERVER_TAG_NAME);

		if (domList != null)
		{
			for (int i = 0; i < domList.getDocumentCount(); i++)
			{
				Document doc = domList.getDocument(i);
				processDOM(doc);
			}
		}
	}

	/**
	 * Parsing all servers in the specific DOM
	 * @param doc
	 * @throws ServerExternalizerException
	 */
	private static void processDOM(Document doc) throws ServerExternalizerException
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, SERVER_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			try
			{
				processServer(element);
			} 
			catch (ServerExternalizerException ex)
			{
				Logger.error(LOGGER_CONTEXT, "Error initializing servers. See exception. Continue to initialize other servers");
				Logger.error(LOGGER_CONTEXT, ex);
				throw ex;
			}
		}
		
		// check if default server was declared
		if (defaultServerName == null)
		{
			throw new ServerExternalizerException("no default server was declared");
		}

	}

	/**
	 * Used to parse specific server document element
	 * @param element
	 * @throws ServerExternalizerException
	 */
	private static  void processServer(Element element) throws ServerExternalizerException
	{
        String serverName = ExternalizerUtil.getName(element);

		// same server's name are not allowed
		if(servers.containsKey(serverName))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize server [" + serverName + "]. A server with that name is already defined.");
		}
		else
		{	
			// find the default server definition
			boolean isDefault = false;
			String isDefaultStr = XMLUtil.getAttribute(element, ATTR_DEFAULT);
			if (isDefaultStr != null && isDefaultStr.equals(ExternalizerConstants.TRUE))
			{
				isDefault = true;
				if (defaultServerName != null)
				{
					throw new ServerExternalizerException("can't have more than one default server");
				}
				else
				{
					defaultServerName = serverName;
				}
			}

			// get all the server definition attributes
			String url = XMLUtil.getAttribute(element, ATTR_URL);

			String jndiName = XMLUtil.getAttribute(element, ATTR_JNDI_NAME);
			String typeStr = XMLUtil.getAttribute(element, ATTR_TYPE);
			int type = -1;
	
			if (typeStr.equals(LOCAL_TYPE))
			{
				type = SystemConstants.BPO_IMPLEMENTATION_TYPE_LOCAL;
			}
			else if (typeStr.equals(EJB_TYPE))
			{
				type = SystemConstants.BPO_IMPLEMENTATION_TYPE_EJB;			
			}
			else if (typeStr.equals(WS_TYPE))
			{
				type = SystemConstants.BPO_IMPLEMENTATION_TYPE_WS;
			}

			String refName = XMLUtil.getAttribute(element, ATTR_REF_NAME);

			ServerDefinition definition = new ServerDefinition(serverName, url,type,jndiName,isDefault, refName);
			servers.put(serverName, definition);
			Logger.debug(LOGGER_CONTEXT, "server" + serverName + " loaded");
		}
	}
}
