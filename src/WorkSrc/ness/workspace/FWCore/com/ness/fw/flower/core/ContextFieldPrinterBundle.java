/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextFieldPrinterBundle.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.common.resources.*;

import java.lang.reflect.*;

/**
 * Contained in a {@link ClassContextFieldType} object, for a type definition.
 * Used to perform the formatting (masking) of the field value according to 
 * the given mask.
 */
public class ContextFieldPrinterBundle
{
	/**
	 * The {@link ContextFieldPrinter} instance.
	 */
	private ContextFieldPrinter printerInstance;
	
	/**
	 * The printer method instance.
	 */
	private Method printerMethod;

	/**
	 * create new ContextFieldPrinterBundle Object.
	 * @param printerInstance The {@link ContextFieldPrinter} instance.
	 * @param printerMethod The printer method instance.
	 */
	public ContextFieldPrinterBundle(ContextFieldPrinter printerInstance, Method printerMethod)
	{
		this.printerInstance = printerInstance;
		this.printerMethod = printerMethod;
	}

	/**
	 * 
	 * @param localizable The {@link LocalizedResources} to uses its data when an error occurred while formatting the data.
	 * @param fieldData The field data to format.
	 * @param maskKey The key of the mask used to format the data. The mask will be taken from the {@link LocalizedResources} object. 
	 * @return String The formatted value
	 * @throws Throwable
	 */
	public String print(LocalizedResources localizable, Object fieldData, String maskKey) throws Throwable
	{
		try
		{
			return (String) printerMethod.invoke(printerInstance, new Object[]{localizable, fieldData, maskKey});
		} catch (InvocationTargetException ex)
		{
			throw ex.getTargetException();
		}
	}
}
