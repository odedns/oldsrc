/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FlowExternalizer.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.core.*;

/**
 * Factory used to parse XML and create <code>Flow</code> instances
 */
public abstract class FlowExternalizer
{
	/**
	 * Instance of Externalizer
	 */
	private static FlowExternalizer instance;

	/**
	 * Should be called before first usage
	 *
	 */
	public static void initialize(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		instance = new FlowExternalizerImpl(domRepository);
	}

	/**
	 * Used to retrieve instance of externalizer
	 *
	 */
	public static FlowExternalizer getInstance() throws ExternalizerNotInitializedException
	{
		if (instance == null)
		{
			throw new ExternalizerNotInitializedException("FlowExternalizer is not initialized");
		}

		return instance;
	}

	/**
	 * Used to create instance of <code>Flow</code>
	 *
	 * @param flowName name of flow to create
	 */
	public abstract Flow createFlow(String flowName) throws ExternalizationException;
 }
