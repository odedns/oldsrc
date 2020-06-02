/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: NumberBuilder.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.shared.builders.flower;

import com.ness.fw.flower.core.*;
import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.*;

/**
 * A builder & printer for numeric data types.
 */
public class NumberBuilder implements ContextFieldBuilder, ContextFieldPrinter
{
	public static final String LOCALIZABLE_DEFAULT_OUT_MASK_KEY             =   "defaultOutMaskLocalizableKey";

	private String defaultOutMaskKey = null;

	public void initialize(ParameterList parameterList) throws Exception
	{
		defaultOutMaskKey = (String)parameterList.getParameter(LOCALIZABLE_DEFAULT_OUT_MASK_KEY).getValue();
	}

	public Integer buildInteger(ContextFieldBuilderParams builderParams, Integer currentContextFieldData, String newFieldData) throws ContextFieldConstructionException, UIException
	{
		if (newFieldData == null || newFieldData.trim().length() == 0)
		{
			return null;
		}

		try{
			return com.ness.fw.util.NumberFormatterUtil.buildInt(newFieldData);
		}catch (Throwable ex)
		{
			Message message = new Message("GE0006", Message.SEVERITY_ERROR);
			throw new ContextFieldConstructionException("com.ness.fw.util.typeBuilders.NumberFormatterUtil thrown Exceptin", ex, message);
		}
	}

	public String printInteger(LocalizedResources localizable, Integer contextFieldData, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.NumberFormatterUtil.printInt(localizable, contextFieldData, maskKey == null ? defaultOutMaskKey : maskKey);
	}

	public Long buildLong(ContextFieldBuilderParams builderParams, Long currentContextFieldData, String newFieldData) throws ContextFieldConstructionException
	{
		if (newFieldData == null || newFieldData.trim().length() == 0)
		{
			return null;
		}

		try{
			return com.ness.fw.util.NumberFormatterUtil.buildLong(newFieldData);
		}catch (Throwable ex)
		{
			Message message = new Message("GE0006", Message.SEVERITY_ERROR);
			throw new ContextFieldConstructionException("com.ness.fw.util.typeBuilders.NumberFormatterUtil thrown Exception", ex, message);
		}
	}

	public String printLong(LocalizedResources localizable, Long contextFieldData, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.NumberFormatterUtil.printLong(localizable, contextFieldData, maskKey == null ? defaultOutMaskKey : maskKey);
	}

	public Double buildDouble(ContextFieldBuilderParams builderParams, Double currentContextFieldData, String newFieldData) throws ContextFieldConstructionException
	{
		if (newFieldData == null || newFieldData.trim().length() == 0)
		{
			return null;
		}

		try{
			return com.ness.fw.util.NumberFormatterUtil.buildDouble(newFieldData);
		}catch (Throwable ex)
		{
			Message message = new Message("GE0006", Message.SEVERITY_ERROR);
			throw new ContextFieldConstructionException("com.ness.fw.util.typeBuilders.NumberFormatterUtil thrown Exception", ex, message);
		}
	}


	public String printDouble(LocalizedResources localizable, Double contextFieldData, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.NumberFormatterUtil.printDouble(localizable, contextFieldData, maskKey == null ? defaultOutMaskKey : maskKey);
	}


}
