/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Formatter.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;

/**
 * Abstract class that provides abstraction for all formatters in the system.
 * Usually formatters used to copy patametrers between contexts.
 */
public abstract class Formatter
{
	/**
	 * Name of formatter as defined in XML
	 */
	private String name;	

	/**
	 * Used to perform formatting
	 *
	 * @param from context to copy parameters from
	 * @param to context to copy parameters to
	 * @throws ContextException thrown if one of parameters does not allowed for context
	 */
	public abstract void format(Context from, Context to) throws ContextException, FormatterException, FatalException, AuthorizationException;

	/**
	 * Used by framework to pass init parameters to formatter. (Parameters defined in XML).
	 * Should be defined by custom formatter creators.
	 *
	 * In case of symple formatter throws an exception
	 *
	 * @param parametersList list of parameters
	 */
	public abstract void initialize(ParameterList parametersList) throws FormatterException;

	/**
	 * Set the name of the formatter.
	 * @param name The name of the formatter
	 */	
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return String The name of the formatter.
	 */
	public String getName()
	{
		return name;
	}
}
