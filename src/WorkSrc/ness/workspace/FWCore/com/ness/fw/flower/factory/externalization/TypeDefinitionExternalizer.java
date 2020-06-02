/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: TypeDefinitionExternalizer.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.core.*;

public abstract class TypeDefinitionExternalizer
{
	private static TypeDefinitionExternalizer instance;

	public static void initialize(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		instance = new TypeDefinitionExternalizerImpl(domRepository);
	}

	public static TypeDefinitionExternalizer getInstance() throws ExternalizerNotInitializedException
	{
		if (instance == null)
		{
			throw new ExternalizerNotInitializedException("TypeDefinitionExternalizer not initialized");
		}

		return instance;
	}

	public abstract Class getTypeClass(String typeName) throws ExternalizationException;
	public abstract ClassContextFieldType getType(String typeName) throws ExternalizationException;
}
