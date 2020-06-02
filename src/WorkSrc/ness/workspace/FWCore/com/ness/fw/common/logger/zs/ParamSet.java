/*
 * Created on: 15/12/2003
 * Author: baruch hizkya
 * @version $Id: ParamSet.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.logger.zs;

/**
 * Represent a set of parameters.
 * The super class fo rthe ParameSet implementations.
 */
public abstract class ParamSet
{
	/**
	 * Constant for parameter - class name
	 */
    public static final int PARAM_CLASS_NAME = 0;

	/**
	 * Constant for number of parameters
	 */
	public static final int PARAMS_COUNT = 1;

	/**
	 * Factory method
	 *
	 * @return new <code>ParamSetImpl</code> instance
	 */
	public static ParamSet createInstance()
	{
		return new ParamSetImpl();
	}


	/**
	 * Adding parameter to the list.
	 *
	 * @param param
	 */
	public abstract void addParam(int param);

	/**
	 * retrieve parameter at the specifyed index
	 *
	 * @param index
	 * @return parameter
	 */
	public abstract int getParam(int index);

	/**
	 * getter for counter of parameters. Used for iteration.
	 *
	 * @return parameters counter
	 */
	public abstract int getParamsCount();
}
