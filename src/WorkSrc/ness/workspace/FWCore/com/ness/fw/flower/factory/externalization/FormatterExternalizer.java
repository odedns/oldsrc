/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FormatterExternalizer.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.core.*;

public abstract class FormatterExternalizer
{

	private static FormatterExternalizer instance;

	public static void initialize(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		instance = new FormatterExternalizerImpl(domRepository);
	}

	public static FormatterExternalizer getInstance() throws ExternalizerNotInitializedException
	{
		if (instance == null)
		{
			throw new ExternalizerNotInitializedException("FormatterExternalizer not initialized");
		}

		return instance;
	}

	public abstract Formatter createFormatter(String formatterName) throws ExternalizationException;
	public abstract ComplexFormatter createComplexFormatter(String formatterName) throws ExternalizationException;
}
