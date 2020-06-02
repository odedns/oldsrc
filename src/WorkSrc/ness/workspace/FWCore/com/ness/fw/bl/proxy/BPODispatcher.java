/*
 * Created on: 21/07/2004
 * Author: yifat har-nof
 * @version $Id: BPODispatcher.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.externalization.DOMList;
import com.ness.fw.common.externalization.DOMRepository;
import com.ness.fw.common.externalization.ExternalizationException;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.logger.Logger;

/**
 * The dispatcher is responsible for managing & executing the bpo commands.  
 */
public class BPODispatcher
{
	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = "BPO DISPATCHER";
	
	/**
	 * The commands map
	 */
	private static Map commands = new HashMap();
	
	/**
	 * Initialize the command definitions from the xml files. 
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 * @param confFilesRoot The path of the business process configuration xml files.
	 * @throws BPOCommandsExternalizationException
	 */
	public static void initialize (String confFilesRoot) throws BPOCommandsExternalizationException
	{ 
		
		//creating DOM repository
		DOMRepository domRepository = new DOMRepository();
		
		try
		{
			domRepository.initialize(confFilesRoot);
		} catch (ExternalizationException e)
		{
			throw new BPOCommandsExternalizationException("Unable to initialize BPO Commands DOM Repository", e);
		}

		initializeCommands(domRepository);		
	}

	/**
	 * Initialize the command definitions from the xml files. 
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 * @param confFilesRoots A list of root path of the business process configuration xml files.
	 * @throws BPOCommandsExternalizationException
	 */
	public static void initialize (ArrayList confFilesRoots) throws BPOCommandsExternalizationException
	{ 
		
		//creating DOM repository
		DOMRepository domRepository = new DOMRepository();
		
		try
		{
			domRepository.initialize(confFilesRoots);
		} catch (ExternalizationException e)
		{
			throw new BPOCommandsExternalizationException("Unable to initialize BPO Commands DOM Repository", e);
		}

		initializeCommands(domRepository);		
	}

	/**
	 * Clears all the commands in the pool and initialize the commands 
	 * from the given DOM repository. 
	 * @param domRepository The DOMRepository to load the commands from.
	 * @throws BPOCommandsExternalizationException
	 */
	private static void initializeCommands (DOMRepository domRepository) throws BPOCommandsExternalizationException
	{
		synchronized (commands)
		{
			Logger.debug(LOGGER_CONTEXT, "loading bpo commands");
			
			commands.clear();
			
			loadCommands(domRepository);
		}
	}
	
	/**
	 * Load the commands from the given DOM repository. 
	 * @param domRepository
	 * @throws BPOCommandsExternalizationException
	 */
	private static void loadCommands (DOMRepository domRepository) throws BPOCommandsExternalizationException
	{
		synchronized (commands)
		{
			//read commands
			DOMList domList = domRepository.getDOMList(ExternalizerConstants.COMMAND_DEFINITION_TAG_NAME);
			if (domList != null)
			{
				for (int i = 0; i < domList.getDocumentCount(); i++)
				{
					Document doc = domList.getDocument(i);
	
					processCommandsDOM(doc);
				}
			}
		}
	}

	/**
	 * Parsing all commands in the specific DOM
	 * @param doc
	 */
	private static void processCommandsDOM (Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.COMMAND_DEFINITION_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element commandElement = (Element) nodes.item(i);

			try
			{
				readCommand(commandElement);
			} catch (Throwable ex)
			{
				Logger.error(LOGGER_CONTEXT, "Unable to initialize bpo command. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	/**
	 * Used to parse specific command document element
	 * @param element
	 */
	private static void readCommand(Element element)
	{
		String name = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_NAME);
		if(commands.containsKey(name))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize bpo command [" + name + "]. A bpo command with that name is already defined.");
		}
		else
		{
			Logger.debug(LOGGER_CONTEXT, "loading bpo command [" + name + "]");
			String className = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_CLASS_NAME);
			String methodName = XMLUtil.getAttribute(element, ExternalizerConstants.ATTR_METHOD_NAME);
			commands.put(name, new CommandDefinition(name, className, methodName));
		}
	}

	/**
	 * Returns the CommandDefinition according to the given command name.
	 * @param bpoCommandName The name of the command to return.
	 * @return CommandDefinition
	 * @throws BPOCommandNotFoundException
	 */
	private static CommandDefinition getCommand (String bpoCommandName) throws BPOCommandNotFoundException
	{
		CommandDefinition command = null;
		synchronized (commands)
		{
			command = (CommandDefinition) commands.get(bpoCommandName);
		}	
		
		if(command == null)
		{
			throw new BPOCommandNotFoundException("Unable to execute bpo command [" + bpoCommandName + "]. The command was not been defined.");
		}
		
		return command;
	}

	/**
	 * Check if the bpo method is static
	 * @param method The @link Method} to check 
	 * @param command The {@link CommandDefinition)
	 * @throws BPOCommandException
	 */
	private static void checkStaticMethod(Method method, CommandDefinition command) throws BPOCommandException
	{
		if (!Modifier.isStatic(method.getModifiers()))
		{
			throw new BPOCommandException("Unable to execute bpo command [" + command.getName() + "]. Method " + command.getMethodName() + " in class " + command.getClassName() + " must be static");
		}
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
		Class[] argumentTypes = new Class[]{bpc.getClass()};  
		Object[] methodArguments = new Object[]{bpc};

		return executeMethod(bpoCommandName, argumentTypes, methodArguments);
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
		Class[] argumentTypes = new Class[]{bpcIn.getClass(), bpcOut.getClass()};  
		Object[] methodArguments = new Object[]{bpcIn, bpcOut};

		executeMethod(bpoCommandName, argumentTypes, methodArguments);
	}

	private static Object executeMethod (String bpoCommandName, Class[] argumentTypes, Object[] methodArguments) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		// retrieving method
		Object results = null;
		CommandDefinition command = getCommand(bpoCommandName);
		
		Logger.debug(LOGGER_CONTEXT, "Execute bpo command [" + bpoCommandName + "]");
		
		Method method;
		try
		{
			method = Class.forName(command.getClassName()).getMethod(command.getMethodName(), argumentTypes);

			// check that the method must be static			
			checkStaticMethod(method, command);
		} 
		catch (BPOCommandException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw new BPOCommandException("Unable to execute bpo command [" + bpoCommandName + "]. Invalid method name [" + command.getMethodName() + "] in class " + command.getClassName(), ex);
		}

		// executing the method
		try
		{
			results = method.invoke(null, methodArguments);
		}
		catch (InvocationTargetException ex)
		{
			if (ex.getTargetException() instanceof BusinessLogicException)
			{
				Logger.error(LOGGER_CONTEXT, ex.getTargetException());
				throw (BusinessLogicException)ex.getTargetException();
			}
			throw new BPOCommandException("Unable to execute bpo command [" + bpoCommandName + "]. Execution method name [" + command.getMethodName() + "] in class " + command.getClassName(), ex.getTargetException());
		} 
		catch (Throwable ex)
		{
			Logger.error(LOGGER_CONTEXT, ex);
			throw new BPOCommandException("Unable to execute bpo command [" + bpoCommandName + "]. Execution method name [" + command.getMethodName() + "] in class " + command.getClassName(), ex);
		}
		
		return results;
	}

}
