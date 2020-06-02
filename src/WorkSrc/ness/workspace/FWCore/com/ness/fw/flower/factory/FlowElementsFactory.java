/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowElementsFactory.java,v 1.2 2005/05/08 12:11:21 yifat Exp $
 */
package com.ness.fw.flower.factory;

import java.util.ArrayList;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.DynamicGlobals;

/**
 * Factory for <code>Context</code>'s, <code>Service</code>'s and <code>Flow</code>'s
 */
public abstract class FlowElementsFactory
{
	/**
	 * The single factory instance
	 */
	private static FlowElementsFactory instance;

	static
	{
		instance = new FlowElementsFactoryImpl();
	}

	public static FlowElementsFactory getInstance()
	{
		return instance;
	}

	/**
	 * Should be called before first usage.
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 *
	 * @param confFilesRoot root location of configuration files
	 */
	public abstract void initialize(ArrayList confFilesRoots) throws FlowElementsFactoryException;

	/**
	 * Should be called before first usage.
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 *
	 * @param confFilesRoot root location of configuration files
	 */
	public abstract void initialize(String confFilesRoot) throws FlowElementsFactoryException;



	/**
	 * Creates <code>Flow</code> instance by name
	 *
	 * @param flowName name of flow
	 * @return <code>Flow</code> instance
	 */
	public abstract Flow createFlow(String flowName) throws FlowElementsFactoryException;

	/**
	 * Creates <code>Context</code> instance by name
	 *
	 * @param contextName name of context
	 * @param parent The parent context.
	 * @return <code>Context</code> instance
	 */
	public abstract Context createContext(String contextName, Context parent) throws FlowElementsFactoryException;

	/**
	 * Creates <code>Context</code> instance by name
	 *
	 * @param contextName The name of the context
	 * @param parent The parent context.
	 * @param dynamicGlobals The DynamicGlobals 
	 * @return <code>Context</code> instance
	 */
	public abstract Context createContext(String contextName, Context parent, DynamicGlobals dynamicGlobals) throws FlowElementsFactoryException;

	/**
	 * Used to retrieve static <code>Context</code> from repository of static <code>Context</code>'s
	 *
	 * @param contextName name of context
	 * @return <code>Context</code> instance
	 */
	public abstract Context getStaticContext(String contextName) throws FlowElementsFactoryException;

	/**
	 * Used to retrieve first static <code>Context</code> from repository of static <code>Context</code>'s
	 *
	 * @return <code>Context</code> instance
	 */
	public abstract Context getFirstStaticContext() throws FlowElementsFactoryException;

	/**
	 * Used to create new instance of service by name as defined in XML
	 *
	 * @param serviceName name of service as defined in XML
	 * @return <code>Service</code> instance
	 */
	public abstract Service createServiceInstance(String serviceName) throws FlowElementsFactoryException;
	
	/**
	 * Creates <code>Formatter</code> instance by name
	 *
	 * @param formatterName name of formatter
	 * @return <code>Formatter</code> instance
	 */
	public abstract Formatter createFormatter(String formatterName) throws FlowElementsFactoryException;
	
	/**
	 * Creates <code>ComplexFormatter</code> instance by name
	 *
	 * @param formatterName name of formatter
	 * @return <code>ComplexFormatter</code> instance
	 */
	public abstract ComplexFormatter createComplexFormatter(String formatterName) throws FlowElementsFactoryException;
	
	/**
	 * Used to create new instance of <code>Operation</code> by name as defined in XML
	 *
	 * @param operationName The name of <code>Operation</code> as defined in XML
	 * @return <code>Operation</code> instance
	 */
	public abstract Operation createOperation(String operationName) throws FlowElementsFactoryException;
	
}
