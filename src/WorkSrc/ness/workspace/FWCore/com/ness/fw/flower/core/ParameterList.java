/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ParameterList.java,v 1.2 2005/05/04 12:59:42 yifat Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Parameter list used to pass init parameters from XML to some Entities such as custom formatter and co...
 */
public class ParameterList
{
	/**
	 * List of parameters
	 */
	private ArrayList params;
	private HashMap paramsMap;

	/**
	 * Creates new ParameterList object.
	 */
	public ParameterList()
	{
		params = new ArrayList();
		paramsMap = new HashMap();
	}

	/**
	 * Returns the parameters according to the given index.
	 * @param index The parameter index.
	 * @return Parameter
	 */
	public Parameter getParameter(int index)
	{
		return (Parameter) params.get(index);
	}

	/**
	 * Returns the parameters according to the given name.
	 * @param name The parameter name.
	 * @return Parameter
	 */
	public Parameter getParameter(String name)
	{
		return (Parameter) paramsMap.get(name);
	}

	/**
	 * check if a parameter with the given name exists in the list.
	 * @param name
	 * @return boolean
	 */
	public boolean containsParameter(String name)
	{
		return paramsMap.get(name) != null;
	}

	/**
	 * Add a parameter to the end of the list.
	 * @param parameter The <code>Parameter</code> to add.
	 */
	public void addParameter(Parameter parameter)
	{
		params.add(parameter);
		paramsMap.put(parameter.getKey(), parameter);
	}

	/**
	 * Returns the parameters count.
	 * @return int count
	 */
	public int getParameterCount()
	{
		return params.size();
	}
}
