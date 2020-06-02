/*
 * Created on: 15/12/2003
 * Author: baruch hizkya
 * @version $Id: ParamSetImpl.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.logger.zs;

import java.util.*;

/**
 * The implementation of the ParamSet.
 * Represent a set of parameters.
 */
public class ParamSetImpl extends ParamSet
{
	/**
	 * list for parameters
	 */
	private ArrayList params;

	/**
	 * Default constructor. Initializes the parameters list
	 */
	public ParamSetImpl()
	{
		params = new ArrayList();
	}

	/**
	 * Adding parameter to the list.
	 *
	 * @param param
	 */
	public void addParam(int param)
	{
		//run over existing parameters and looking for new parameter existance
		for (int i = 0; i < params.size(); i++)
		{
			Integer p = (Integer) params.get(i);
			if (p.intValue() == param)
			{
				//parameter already exist in the list
				return;
			}
		}

		//adding parameter
		params.add(new Integer(param));
	}

	/**
	 * retrieve parameter at the specifyed index
	 *
	 * @param index
	 * @return parameter
	 */
	public int getParam(int index)
	{
		return ((Integer)params.get(index)).intValue();
	}

	/**
	 * getter for counter of parameters. Used for iteration.
	 *
	 * @return parameters counter
	 */
	public int getParamsCount()
	{
		return params.size();
	}


}
