/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: OperationExternalizer.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.core.*;

public abstract class OperationExternalizer
{
	private static OperationExternalizer instance;

	public static void initialize(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		instance = new OperationExternalizerImpl(domRepository);
	}

	public static OperationExternalizer getInstance() throws ExternalizerNotInitializedException
	{
		if (instance == null)
		{
			throw new ExternalizerNotInitializedException("OperationExternalizer not initialized");
		}

		return instance;
	}

	public abstract Operation createOperation(String operationName) throws ExternalizationException;
}
