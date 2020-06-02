/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Parameter.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Element of parameter list used to pass init parameters from XML to some Entities such as custom formatter and co...
 */
public class Parameter
{
	/**
	 * Parameter key
	 */
	private String key;

	/**
	 * Parameter value
	 */
	private Object value;

	public Parameter(String key, Object value)
	{
		this.key = key;
		this.value = value;
	}

	public String getKey()
	{
		return key;
	}

	public Object getValue()
	{
		return value;
	}

    public String toString()
    {
	    return "Parameter key [" + key + "] value [" + value + "]";
    }
}
