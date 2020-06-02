/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ServiceExternalizer.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.core.*;

public abstract class ServiceExternalizer
{
	private static ServiceExternalizer instance;

	public static void initialize(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		instance = new ServiceExternalizerImpl(domRepository);
	}

	public static ServiceExternalizer getInstance() throws ExternalizerNotInitializedException
	{
		if (instance == null)
		{
			throw new ExternalizerNotInitializedException("ServiceExternalizer not initialized");
		}

		return instance;
	}

	public abstract Service createService(String serviceName) throws ExternalizationException;
}
