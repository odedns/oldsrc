/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextFieldBuilder.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Provides abstraction for the builders of context fields. 
 */
public interface ContextFieldBuilder
{
	
	/**
	 * Used by framework while initializing builders. 
	 * 
	 * @param parameterList A list of parameters for the builder initialization.
	 * @throws Exception Any Exception that may occur while initializing.
	 */
	public void initialize(ParameterList parameterList) throws Exception;
}
