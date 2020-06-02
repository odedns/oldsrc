/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: DateBuilder.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.shared.builders.flower;

import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.*;
import com.ness.fw.flower.core.*;

import java.util.*;

/**
 * A builder & printer for Date data types.
 */
public class DateBuilder implements ContextFieldBuilder, ContextFieldPrinter
{
	public static final String LOCALIZABLE_INPUT_MASK_PREFFIX_KEY           =   "localizableInputMasksPreffix";
	public static final String LOCALIZABLE_DEFAULT_OUT_MASK_KEY             =   "defaultOutMaskLocalizableKey";

	private String localizableInputMasksPreffix = null;
	private String defaultOutputMaskKey = null;

	public Date buildFromString(ContextFieldBuilderParams builderParams, Date currentFieldData, String newFieldData) throws ContextFieldConstructionException
	{
		if (newFieldData == null || newFieldData.trim().length() == 0)
		{
			return null;
		}

		try{
			return com.ness.fw.util.DateFormatterUtil.build(builderParams.getLocalizable(), newFieldData, localizableInputMasksPreffix);
		}catch (Exception ex)
		{
			Message message = new Message("GE0006", Message.SEVERITY_ERROR);
			throw new ContextFieldConstructionException("com.ness.fw.util.typeBuilders.DateFormatterUtil thrown exception", ex, message);
		}
	}

	public Date buildFromLong(ContextFieldBuilderParams builderParams, Date currentFieldData, Long newFieldData) throws ContextFieldConstructionException
	{
		try{
			return new Date(newFieldData.longValue());
		}catch (Exception ex)
		{
			Message message = new Message("GE0006", Message.SEVERITY_ERROR);
			throw new ContextFieldConstructionException(message);
		}
	}

	public String printDate(LocalizedResources localizable, Date date, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.DateFormatterUtil.printDate(localizable, date, maskKey == null ? defaultOutputMaskKey : maskKey);
	}

	public String printTime(LocalizedResources localizable, Date date, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.DateFormatterUtil.printTime(localizable, date, maskKey == null ? defaultOutputMaskKey : maskKey);
	}

	public String printTimestamp(LocalizedResources localizable, Date date, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.DateFormatterUtil.printTimestamp(localizable, date, maskKey == null ? defaultOutputMaskKey : maskKey);
	}

	public void initialize(ParameterList parameterList) throws Exception
	{
		localizableInputMasksPreffix = (String)parameterList.getParameter(LOCALIZABLE_INPUT_MASK_PREFFIX_KEY).getValue();
		defaultOutputMaskKey = (String)parameterList.getParameter(LOCALIZABLE_DEFAULT_OUT_MASK_KEY).getValue();
	}
}
