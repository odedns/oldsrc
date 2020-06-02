/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowElementsFactoryImpl.java,v 1.2 2005/05/08 12:11:21 yifat Exp $
 */
package com.ness.fw.flower.factory;

import java.util.ArrayList;

import com.ness.fw.flower.factory.externalization.*;
import com.ness.fw.flower.util.DynamicGlobals;
import com.ness.fw.flower.core.*;
import com.ness.fw.common.externalization.*;
import com.ness.fw.common.logger.*;

public class FlowElementsFactoryImpl extends FlowElementsFactory
{
	/**
	 * String used as context for all logger prints
	 */
	public final static String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "FACTORY ";

	/**
	 * Repository that contains all static <code>Context</code>'s
	 */
	private ContextRepository staticContextRepository;

	FlowElementsFactoryImpl()
	{
		super();
	}


	/**
	 * Should be called before first usage.
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 *
	 * @param confFilesRoot root location of configuration files
	 */
	public void initialize(ArrayList confFilesRoots) throws FlowElementsFactoryException
	{
		//creating DOM repository
		DOMRepository domRepository = new DOMRepository();
		try
		{
			domRepository.initialize(confFilesRoots);
		} catch (ExternalizationException e)
		{
			throw new FlowElementsFactoryException("Unable to initialize DOM Repository", e);
		}

		//initialize externalizers
		try
		{
			createExternalizers(domRepository);
		} catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to initialize externalaizer.", ex);
		}

		//create static context repository
		try
		{
			staticContextRepository = new ContextRepositoryImpl(createStaticContextList());
		} catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to initialize static contexts repository.", ex);
		}

		//prints static contexts tree to log
		Logger.debug(LOGGER_CONTEXT, staticContextRepository.toString());
	}



	/**
	 * Should be called before first usage.
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 *
	 * @param confFilesRoot root location of configuration files
	 */
	public void initialize(String confFilesRoot) throws FlowElementsFactoryException
	{
		ArrayList roots = new ArrayList();
		roots.add(confFilesRoot);
		initialize(roots);
	}

	/**
	 * Creates <code>Flow</code> instance by name
	 *
	 * @param flowName The name of the flow
	 * @return <code>Flow</code> instance
	 */
	public Flow createFlow(String flowName) throws FlowElementsFactoryException
	{
		try
		{
			return FlowExternalizer.getInstance().createFlow(flowName);
		} catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to Create flow with name [" + flowName + "]", ex);
		}
	}

	/**
	 * Creates <code>Context</code> instance by name
	 *
	 * @param contextName The name of the context
	 * @param parent The parent context.
	 * @return <code>Context</code> instance
	 */
	public Context createContext(String contextName, Context parent) throws FlowElementsFactoryException
	{
		try
		{
			return ContextExternalizer.getInstance().createContext(contextName, parent);
		} catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to create context with name [" + contextName + "]", ex);
		}
	}
	
	/**
	 * Creates <code>Context</code> instance by name
	 *
	 * @param contextName The name of the context
	 * @param parent The parent context.
	 * @param dynamicGlobals The DynamicGlobals 
	 * @return <code>Context</code> instance
	 */
	public Context createContext(String contextName, Context parent, DynamicGlobals dynamicGlobals) throws FlowElementsFactoryException
	{
		try
		{
			return ContextExternalizer.getInstance().createContext(contextName, parent, dynamicGlobals);
		} catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to create context with name [" + contextName + "]", ex);
		}
	}

	/**
	 * Used to retrieve static <code>Context</code> from repository of static <code>Context</code>'s
	 *
	 * @param contextName The name of the context
	 * @return <code>Context</code> instance
	 */
	public Context getStaticContext(String contextName) throws FlowElementsFactoryException
	{
		try
		{
			return staticContextRepository.getStaticContext(contextName);
		} catch (ContextRepositoryException ex)
		{
			throw new FlowElementsFactoryException("No static context defined with name [" + contextName + "]", ex);
		}
	}

	/**
	 * Used to retrieve first static <code>Context</code> from repository of static <code>Context</code>'s
	 *
	 * @return <code>Context</code> instance
	 */
	public Context getFirstStaticContext() throws FlowElementsFactoryException
	{
		try
		{
			return staticContextRepository.getFirstStaticContext();
		} catch (ContextRepositoryException ex)
		{
			throw new FlowElementsFactoryException("No static contexts was defined.", ex);
		}
	}

	/**
	 * Used to create new instance of <code>Service</code> by name as defined in XML
	 *
	 * @param serviceName The name of service as defined in XML
	 * @return <code>Service</code> instance
	 */
	public Service createServiceInstance(String serviceName) throws FlowElementsFactoryException
	{
		try
		{
			return ServiceExternalizer.getInstance().createService(serviceName);
		}
		catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to create service instance for name [" + serviceName + "].", ex );
		}		
	}
	
	/**
	 * Creates <code>Formatter</code> instance by name
	 *
	 * @param formatterName The name of the simple formatter
	 * @return <code>Formatter</code> instance
	 */
	public Formatter createFormatter(String formatterName) throws FlowElementsFactoryException
	{
		try
		{
			return FormatterExternalizer.getInstance().createFormatter(formatterName);
		} catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to create formatter with name [" + formatterName + "]", ex);
		}
	}

	/**
	 * Creates <code>ComplexFormatter</code> instance by name
	 *
	 * @param formatterName The name of the complex formatter
	 * @return <code>ComplexFormatter</code> instance
	 */
	public ComplexFormatter createComplexFormatter(String formatterName) throws FlowElementsFactoryException
	{
		try
		{
			return FormatterExternalizer.getInstance().createComplexFormatter(formatterName);
		} catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to create complex formatter with name [" + formatterName + "]", ex);
		}
	}

	/**
	 * Used to create new instance of <code>Operation</code> by name as defined in XML
	 *
	 * @param operationName The name of <code>Operation</code> as defined in XML
	 * @return <code>Operation</code> instance
	 */
	public Operation createOperation(String operationName) throws FlowElementsFactoryException
	{
		try
		{
			return OperationExternalizer.getInstance().createOperation(operationName);
		}
		catch (Throwable ex)
		{
			throw new FlowElementsFactoryException("Unable to create operation instance for name [" + operationName + "].", ex );
		}		
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Retrieves Repository of static contexts from Externalizer
	 */
	private ContextList createStaticContextList() throws FlowElementsFactoryException
	{
		try
		{
			return ContextExternalizer.getInstance().getStaticContextList();
		} catch (Throwable e)
		{
			throw new FlowElementsFactoryException("Unable to create Static context list.", e);
		}
	}

	/**
	 * Initializes externalizers one by one
	 *
	 * @param domRepository repository of DOM's
	 */
	private void createExternalizers(DOMRepository domRepository) throws ExternalizerInitializationException, ExternalizerNotInitializedException
	{
		TypeDefinitionExternalizer.initialize(domRepository);
		FormatterExternalizer.initialize(domRepository);
		ValidatorExternalizer.initialize(domRepository);
		OperationExternalizer.initialize(domRepository);
		ServiceExternalizer.initialize(domRepository);
		ContextExternalizer.initialize(domRepository);
		FlowExternalizer.initialize(domRepository);
	}
}
