/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidatorExternalizer.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.common.externalization.*;
import com.ness.fw.flower.core.*;

public abstract class ValidatorExternalizer
{
	private static ValidatorExternalizer instance;

	public static void initialize(DOMRepository domRepository) throws ExternalizerInitializationException
	{
		instance = new ValidatorExternalizerImpl(domRepository);
	}

	public static ValidatorExternalizer getInstance() throws ExternalizerNotInitializedException
	{
		if (instance == null)
		{
			throw new ExternalizerNotInitializedException("ValidatorExternalizer not initialized");
		}

		return instance;
	}

	public abstract Validator createValidator(String validatorName) throws ExternalizationException;
}
