/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: BigDecimalBuilder.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.shared.builders.flower;

import com.ness.fw.flower.core.*;
import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.*;

import java.math.*;

/**
 * A builder & printer for BigDecimal data types.
 */
public class BigDecimalBuilder implements ContextFieldBuilder, ContextFieldPrinter
{
	public static final String LOCALIZABLE_DEFAULT_OUT_MASK_KEY             =   "defaultOutMaskLocalizableKey";
	public static final String ROUND_MODE                                   =   "roundMode";
	public static final String LENGTH                                       =   "length";
	public static final String SCALE                                        =   "scale";

	private String roundMode = null;
	private String defaultOutMaskKey = null;
	private int length;
	private int scale;

	public BigDecimal build(ContextFieldBuilderParams builderParams, BigDecimal currentContextFieldData, String newFieldData) throws ContextFieldConstructionException
	{
		if (newFieldData == null || newFieldData.trim().length() == 0)
		{
			return null;
		}

		try{
			return com.ness.fw.util.NumberFormatterUtil.buildBigDecimal(newFieldData, roundMode, length, scale);
		}catch (Throwable ex)
		{
			Message message = new Message("GE0006", Message.SEVERITY_ERROR);
			throw new ContextFieldConstructionException("com.ness.fw.util.typeBuilders.NumberFormatterUtil thrown Exception", ex, message);
		}
	}

	public String print(LocalizedResources localizable, BigDecimal contextFieldData, String maskKey) throws ResourceException
	{
		return com.ness.fw.util.NumberFormatterUtil.printBigDecimal(localizable, contextFieldData, maskKey == null ? defaultOutMaskKey : maskKey);
	}

	public void initialize(ParameterList parameterList) throws Exception
	{
		defaultOutMaskKey   = (String)parameterList.getParameter(LOCALIZABLE_DEFAULT_OUT_MASK_KEY).getValue();
		try{
			roundMode           = (String)parameterList.getParameter(ROUND_MODE).getValue();
			length              = ((Integer)parameterList.getParameter(LENGTH).getValue()).intValue();
			scale               = ((Integer)parameterList.getParameter(SCALE).getValue()).intValue();
		}catch (Exception ex)
		{

		}
	}
}
