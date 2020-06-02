/*
 * Created on: 21/07/2004
 * Author: yifat har-nof
 * @version $Id: CommandDefinition.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl.proxy;

/**
 * Holds the definition of a business process command.
 */
class CommandDefinition
{
	/**
	 * The name of the command.
	 */
	private String name;
	
	/**
	 * The class name that implement the command. 
	 */
	private String className;
	
	/**
	 * The method in class to execute.
	 */
	private String methodName;

	/**
	 * 
	 * @param name The name of the command.
	 * @param className The class name that implement the command.
	 * @param methodName The method in class to execute.
	 */
	 protected CommandDefinition(String name, String className, String methodName)
	{
		this.name = name;
		this.className = className;
		this.methodName = methodName;
	}
	
	/** Returns the class name that implement the command.
	 * @return String class name
	 */
	protected String getClassName()
	{
		return className;
	}

	/**
	 * Returns the method in class to execute.
	 * @return String method name
	 */
	protected String getMethodName()
	{
		return methodName;
	}

	/**
	 * Returns the name of the command.
	 * @return String command name
	 */
	protected String getName()
	{
		return name;
	}

}
