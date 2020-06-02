/*
 * Created on: 24/01/2005
 * @author: baruch hizkya
 * @version $Id: SystemInitializationManager.java,v 1.6 2005/04/20 11:32:28 baruch Exp $
 */

package com.ness.fw.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.*;

import com.ness.fw.bl.proxy.BPODispatcher;
import com.ness.fw.bl.proxy.ServerExternalizer;
import com.ness.fw.common.externalization.ExternalizationException;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.ResourceUtils;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.flower.common.LanguageException;
import com.ness.fw.flower.common.LanguagesManager;
import com.ness.fw.flower.common.SystemUtil;
import com.ness.fw.flower.core.FLOWERConstants;
import com.ness.fw.flower.factory.ExtendedTransitionSupplierFactory;
import com.ness.fw.flower.factory.FlowElementsFactory;
import com.ness.fw.legacy.LegacyExternalizer;
import com.ness.fw.shared.servlet.authentication.AuthenticationManager;
import com.ness.fw.workflow.AgentFactory;
import com.ness.fw.workflow.WorkFlowServiceFactory;

/**
 * @author bhizkya
 *
 * A Class that responsiple to inizialize the system.
 */
public class SystemInitializationManager
{

	private final static String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "SERVLET";

	//configuration parameters
	private static final String CP_FLOWER_ROOT_PATH = "flowerConfigRootPath";
	private static final String CP_FLOWER_ROOT_PATH_ENTRY = "rootPath";
	private static final String CP_FLOWER_MENU_PATH = "flowerMenuPath";
	private static final String CP_BUSINESS_PROCESS_PATH = "businessProcessConfigPath";
	private static final String CP_LEGACY_PATH = "legacyConfigPath";
	private static final String CP_LANGUAGES = "languageConfigFile";
	private static final String CP_SERVER_CONFIG_FILE = "serversConfigFile";
	private static final String CP_WORK_FLOW_AGENT_CONFIG = "WorkFlowAgentConfig";
	private static final String CP_PREFIX_RESOURCES_LOCATION = "systemResourcesPrefix";
	private static final String CP_WORK_FLOW_SERVICE = "workFlowService";
	private static final String CP_AUTHENTICATION = "userAuthentication";
	private static final String CP_AUTHENTICATION_REQUEST_PARAM = "requestParam";

	private static final String TRUE = "true";
	private static final String LOAD = "load";
	private static final String VALUE = "value";

	private static final String AGENT_NAME = "agentName";
	private static final String AGENT_TYPE = "agentType";
	
	private static final String AUTHENTICATION_IMPL = "authenticationImpl";
	private static final String AUTHENTICATION_NAME = "name";
	private static final String AUTHENTICATION_REQUEST_PARAM_NAME = "requestParamName";
	
	// servlet init parameters
	private static final String IP_LOGGER_CONFIG = "loggerConfigurationLocation";
	private static final String IP_LOCALIZABLE_CONFIG = "localizableConfigurationFiles";
	private static final String IP_SYSTEM_CONFIG = "systemConfigurationFiles";


	/**
	 * The roots path of all flower configuration xml files. 
	 */
	private static ArrayList flowerRootsPath = null;

	/**
	 * The roots path of all bpo configuration xml files. 
	 */
	private static ArrayList bpoRootsPath = null;

	/**
	 * The roots path of all legacy configuration xml files. 
	 */
	private static ArrayList legacyRootsPath = null;

	/**
	 * The roots path of the flower menu configuration xml file. 
	 */
	private static ArrayList flowerMenuRootsPath = null;

	/**
	 * The location of the logger configuration file.
	 */
	private String loggerConfigurationLocation = null;

	/**
	 * The location of the system configuration file.
	 */
	private String systemConfigurationFiles = null;

	/**
	 * The location of the localizable configuration file.
	 */
	private String localizableConfigurationFiles = null;

	/**
	 * The location of the languages configuration file.
	 */
	private String languageConfigFile = null;	

	/**
	 * The system resources prefix.
	 */
	private String systemResourcesPrefix = null;	

	/**
	 * Indicate if to init wf service.
	 */
	private boolean initWFService = false;	

	/**
	 * Indicate if to init authentication manager.
	 */
	private boolean initAuthenticationManager = false;	


	/**
	 * The class implementing AuthentincationService
	 */
	private String authenticationImpl = null;
	
	/**
	 * The map holding the name of request's parameters for authentication
	 */
	private HashMap authenticationRequestParams = null;


	/**
	 * The name of the servers location file that should be reloaded when
	 * reload operation had done.
	 */
	private String serversConfigFile = null;

	/**
	 * The name of the workflow agent type
	 */
	private String worflowAgentType = null;

	/**
	 * The name of the workflow agent name
	 */
	private String worflowAgentName = null;

	/**
	 * This variable holding the instance class
	 */
	private static SystemInitializationManager initializer = null;

	/**
	 * indicate if the system was initialized
	 */
	private boolean isInitialized = false;

	private SystemInitializationManager()
	{
	}

	/**
	 * Gets a SystemInitializationManager instance
	 * @return
	 */
	public static SystemInitializationManager getInstance()
	{
		 if (initializer == null)
		 {
		 	initializer = new SystemInitializationManager();
		 }
		 
		 return initializer;
	}


	/**
	 * Initialize the system configuration. (Writer)
	 * @throws ServletException
	 */
	public void initializeConfiguration(String configurationLocation) throws SystemInitializationException
	{
		/*
		 * Start initialization
		 */

		// tracking load time
		long loadStart = System.currentTimeMillis();

		//read configuration xml file
		try
		{

			// initializing init parameters
			if (configurationLocation != null)
			{
				readInitializeParameters(configurationLocation);
			}

			//initializing logger
			String path = ResourceUtils.getResourceAbsolutePath(loggerConfigurationLocation);
			Logger.reset(path);

			// init system resources
			SystemResources.getInstance().init(systemConfigurationFiles);

			
			// initializing flower definitions
			if (flowerRootsPath != null && !flowerRootsPath.isEmpty())
			{
				FlowElementsFactory.getInstance().initialize(flowerRootsPath);
			}

			// initializing transition supplier definitions 
			if (flowerMenuRootsPath != null && !flowerMenuRootsPath.isEmpty())
			{
				ExtendedTransitionSupplierFactory.initialize(flowerMenuRootsPath);
			}

			// initializing bussiness process definitions
			if (bpoRootsPath != null && !bpoRootsPath.isEmpty())
			{
				BPODispatcher.initialize(bpoRootsPath);
			}

			// initializing legacy definitions
			if (legacyRootsPath != null && !legacyRootsPath.isEmpty())
			{
				LegacyExternalizer.initialize(legacyRootsPath);
			}

			// init languages
			try
			{
				if (languageConfigFile != null)
				{
					LanguagesManager.initizlize(ResourceUtils.getResourceAbsolutePath(languageConfigFile));
				}
			}
			catch (LanguageException e)
			{
				Logger.warning(LOGGER_CONTEXT, "Problem with langagues set.", e); 
			}

			// initializing system configuration and static globals
			if (localizableConfigurationFiles != null)
			{
				SystemUtil.initSystemConfiguration(localizableConfigurationFiles);
			}
			
			// initializing all the server
			if (serversConfigFile != null)
			{
				ServerExternalizer.initialize(serversConfigFile);
			}

			// initializing work flow agent factory
			if(worflowAgentType != null)
			{
				AgentFactory.initialize(worflowAgentType, worflowAgentName);						
			}

			// initializing workflow service
			if (initWFService)
			{
				WorkFlowServiceFactory.initialize();
			}

			//initializing authentication service
			if (initAuthenticationManager)
			{
				AuthenticationManager.initialize(authenticationImpl,authenticationRequestParams);
			}

			isInitialized = true;

		}
		
		catch (Throwable ex)
		{
			Logger.fatal(LOGGER_CONTEXT, "system initialization failed");
			Logger.fatal(LOGGER_CONTEXT, ex);
			throw new SystemInitializationException("system initialization failed", ex);
		}

		Logger.debug(LOGGER_CONTEXT, "Total system initialization time [" + (System.currentTimeMillis() - loadStart) + "]ms");
	}


	/**
	 * Initializing init parameters from the servlet configuration file 
	 * @throws XMLUtilException
	 */
	private void readInitializeParameters(String configurationLocation) throws XMLUtilException, IOException, ExternalizationException
	{
		String path = ResourceUtils.getResourceAbsolutePath(configurationLocation);
	
		Document doc = XMLUtil.readXML(path, false);

		//retrieve root element
		Element documentElement = doc.getDocumentElement();

		// retrieve logger path 		
		NodeList loggerConfigurationList = XMLUtil.getElementsByTagName(documentElement,IP_LOGGER_CONFIG);
		if (loggerConfigurationList.getLength() > 0)
		{
			Element loggerConfigurationElement = (Element)loggerConfigurationList.item(0);
			loggerConfigurationLocation = loggerConfigurationElement.getAttribute(VALUE);
		}
		else
		{
			throw new ExternalizationException(IP_LOGGER_CONFIG + " must be supplied");
		}
		
		//retrieve localizable configuration file
		NodeList localizableConfigurationList = XMLUtil.getElementsByTagName(documentElement, IP_LOCALIZABLE_CONFIG);
		if (localizableConfigurationList.getLength() > 0)
		{
			Element localizableConfigurationElement = (Element)localizableConfigurationList.item(0);
			localizableConfigurationFiles = localizableConfigurationElement.getAttribute(VALUE);
		}

		// retrieve system resources path 
		NodeList systemConfigurationList = XMLUtil.getElementsByTagName(documentElement, IP_SYSTEM_CONFIG);
		if (systemConfigurationList.getLength() > 0)
		{
			Element systemConfigurationElement = (Element)systemConfigurationList.item(0);
			systemConfigurationFiles = systemConfigurationElement.getAttribute(VALUE);
		}
		
		//retrieve roots path of all flower configuration xml files. 
		flowerRootsPath = getConfigRootsPath(documentElement, CP_FLOWER_ROOT_PATH);

		//retrieve root path of all flower menus configuration xml files. 
		flowerMenuRootsPath = getConfigRootsPath(documentElement,CP_FLOWER_MENU_PATH);
		
		//retrieve root path of all business process configuration xml files. 
		bpoRootsPath = getConfigRootsPath(documentElement, CP_BUSINESS_PROCESS_PATH);

		legacyRootsPath = getConfigRootsPath(documentElement, CP_LEGACY_PATH);
								
		NodeList languageConfigList = XMLUtil.getElementsByTagName(documentElement, CP_LANGUAGES);
		if (languageConfigList.getLength() > 0)
		{
			Element languageConfigElement = (Element)languageConfigList.item(0);
			languageConfigFile = languageConfigElement.getAttribute(VALUE);
		}
							
		// retrieve the name of the servers configuration file.
		NodeList serversConfigList = XMLUtil.getElementsByTagName(documentElement, CP_SERVER_CONFIG_FILE);
		if (serversConfigList.getLength() > 0)
		{
			Element serversConfigElement = (Element)serversConfigList.item(0);
			serversConfigFile = serversConfigElement.getAttribute(VALUE);							
		}
		
		// retrieve workflow agent configuration
		NodeList workFlowAgentList = XMLUtil.getElementsByTagName(documentElement, CP_WORK_FLOW_AGENT_CONFIG);
		if (workFlowAgentList.getLength() > 0)
		{
			Element workFlowAgentElement = (Element)workFlowAgentList.item(0);
			worflowAgentType = workFlowAgentElement.getAttribute(AGENT_TYPE);							
			worflowAgentName = workFlowAgentElement.getAttribute(AGENT_NAME);	
		}
		
		// retrieve system resources prefix
		NodeList systemResourcesPrefixList = XMLUtil.getElementsByTagName(documentElement, CP_PREFIX_RESOURCES_LOCATION);
		if (systemResourcesPrefixList.getLength() > 0)
		{
			Element systemResourcesPrefixElement = (Element)systemResourcesPrefixList.item(0);
			systemResourcesPrefix = systemResourcesPrefixElement.getAttribute(VALUE);							

			if (systemResourcesPrefix == null || systemResourcesPrefix.equals(""))
			{
				systemResourcesPrefix = "";
			}
			else
			{
				systemResourcesPrefix += "/";
			}
		}
		else
		{
			systemResourcesPrefix = "";
		}
		
		// retrieve workflow service
		NodeList wfServiceList = XMLUtil.getElementsByTagName(documentElement, CP_WORK_FLOW_SERVICE);
		if (wfServiceList.getLength() > 0)
		{
			Element wfElement = (Element)wfServiceList.item(0);
			String initWFServiceStr = wfElement.getAttribute(LOAD);	
			if (initWFServiceStr != null && initWFServiceStr.equals(TRUE))
			{
				initWFService = true;
			}					
		}
		
		//Retrieve authentication parameters
		NodeList authenticationList = XMLUtil.getElementsByTagName(documentElement, CP_AUTHENTICATION);
		if (authenticationList.getLength() > 0)
		{
			initAuthenticationManager = true;
			
			//Get the implementing AuthenticationService class
			Element authenticationElement = (Element)authenticationList.item(0);
			authenticationImpl = authenticationElement.getAttribute(AUTHENTICATION_IMPL);
			
			//Get the request's parameters names
			NodeList requestParams =  XMLUtil.getElementsByTagName(authenticationElement,CP_AUTHENTICATION_REQUEST_PARAM);
			if (requestParams.getLength() > 0)
			{
				authenticationRequestParams = new HashMap();
				for (int index = 0;index < requestParams.getLength();index++)
				{
					Element requestParam = (Element)requestParams.item(index);
					authenticationRequestParams.put(requestParam.getAttribute(AUTHENTICATION_NAME),requestParam.getAttribute(AUTHENTICATION_REQUEST_PARAM_NAME));
				}
			}
		}
	}

	/**
	 * Returns all the paths in a element 
	 * @param documentElement
	 * @param entryName
	 * @return ArrayList
	 */
	protected ArrayList getConfigRootsPath(Element documentElement, String entryName)
	{
		ArrayList roots = null;
		NodeList rootNodesList = XMLUtil.getElementsByTagName(documentElement, entryName);
		if(rootNodesList.getLength() > 0)
		{
			roots = processPaths((Element) rootNodesList.item(0));
		}
		else
		{
			roots = new ArrayList(0);
		}

		return roots;
	}

	/**
	 * Returns all the paths with reload=true
	 * @param documentElement
	 * @return
	 */
	private ArrayList processPaths(Element documentElement)
	{
		ArrayList roots = new ArrayList();
		NodeList rootNodesList = XMLUtil.getElementsByTagName(documentElement, CP_FLOWER_ROOT_PATH_ENTRY);

		for (int i = 0; i < rootNodesList.getLength(); i++)
		{
			Element rootElement = (Element) rootNodesList.item(i);
			// getting param attributes
			String value = XMLUtil.getAttribute(rootElement, VALUE);

			String loadStr = XMLUtil.getAttribute(rootElement, LOAD);
			if (loadStr != null && loadStr.equals(TRUE))
			{
				roots.add(value);
			}
		}

		return roots;
	}

	/**
	 * @return The server configuration file
	 */
	public String getServersConfigFile()
	{
		return serversConfigFile;
	}

	/**
	 * @param The server configuration file
	 */
	public void setServersConfigFile(String serversConfigFile)
	{
		this.serversConfigFile = serversConfigFile;
	}

	/**
	 * @return boolean. If the system was initialized
	 */
	public boolean isInitialized()
	{
		return isInitialized;
	}

//	/**
//	 * @return String. The workflow agent name.
//	 */
//	public String getAgentName()
//	{
//		return worflowAgentName;
//	}
//
//	/**
//	 * @return String. The workflow agent type.
//	 */
//	public String getAgentType()
//	{
//		return worflowAgentType;
//	}
//
//	/**
//	 * @param agentName The workflow agent name.
//	 */
//	public void setAgentName(String agentName)
//	{
//		this.worflowAgentName = agentName;
//	}
//
//	/**
//	 * @param agentType The workflow agent type.
//	 */
//	public void setAgentType(String agentType)
//	{
//		this.worflowAgentType = agentType;
//	}

	/**
	 * @return String. The system reosurces prefix
	 */
	public String getSystemResourcesPrefix()
	{
		return systemResourcesPrefix;
	}
}
