/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: BooleanBuilder.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.shared.builders.flower;

import com.ness.fw.flower.core.*;
import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.*;

/**
 * A builder & printer for Boolean data types.
 */
public class BooleanBuilder implements ContextFieldBuilder, ContextFieldPrinter
{
	/**
	 * 
	 * @param messagesContainer
	 * @param localizable
	 * @param currentContextFieldData
	 * @param newFieldData
	 * @return Boolean The Boolean object built from the given String value.
	 * @throws ContextFieldConstructionException
	 */
	public Boolean build(ContextFieldBuilderParams builderParams, Boolean currentContextFieldData, String newFieldData) throws ContextFieldConstructionException
	{
		if (newFieldData == null || newFieldData.trim().length() == 0)
		{
			return null;
		}

		try
		{
			return com.ness.fw.util.BooleanFormatterUtil.build(newFieldData);
		}
		catch (Throwable ex)
		{
			Message message = new Message("GE0006", Message.SEVERITY_ERROR);
			throw new ContextFieldConstructionException("com.ness.fw.util.typeBuilders.NumberFormatterUtil thrown Exception", ex, message);
		}
	}

	public String print(LocalizedResources localizable, Boolean contextFieldData, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.BooleanFormatterUtil.print(localizable, contextFieldData);
	}


	public void initialize(ParameterList parameterList) throws Exception
	{
	}
}
