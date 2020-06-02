/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: ClassContextFieldType.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * The class represents the type of {@link ContextField} that is of Class type. 
 */
public class ClassContextFieldType extends ContextFieldType
{
	/**
	 * The {@link Class} of the field type.
	 */
	private Class typeClass;
	
	/**
	 * The {@link ContextFieldPrinterBundle} that should perform the formatting (masking) 
	 * of the field value.
	 */
	private ContextFieldPrinterBundle printerBundle;
	
	/**
	 * An array of {@link ContextFieldBuilderBundle} objects that should 
	 * construct a new data with the class that declared in the type definition, 
	 * each time that a value was set into the context and it's class is not equals 
	 * to type in the type definition.
	 */
	private ContextFieldBuilderBundle builderBundleArray[];

	/**
	 * create new ClassContextFieldType Object.
	 * 
	 * @param typeClass The {@link Class} of the field type.
	 * @param builderBundleArray An array of {@link ContextFieldBuilderBundle} 
	 * objects that should construct a new data with the class that declared in the type definition, 
	 * each time that a value was set into the context and it's class is not equals 
	 * to type in the type definition.
	 * @param printerBundle The {@link ContextFieldPrinterBundle} that should 
	 * perform the formatting (masking) of the field value.
	 * @param typeName The name of the type definition.
	 * @param xIType Indicates whether the type is XI (external interface) type and contains model that 
	 * check the perfmissions to set the data into the field from the request parameters.
	 */
	public ClassContextFieldType(Class typeClass, ContextFieldBuilderBundle builderBundleArray[], ContextFieldPrinterBundle printerBundle, String typeName, boolean xIType, String basicXITypeName)
	{
		super(CLASS_TYPE, typeName, xIType, basicXITypeName);
		this.typeClass = typeClass;

		this.printerBundle = printerBundle;
		this.builderBundleArray = builderBundleArray;
	}

	/**
	 * create new ClassContextFieldType Object with only the class name
	 * 
	 * @param typeClass The {@link Class} of the field type.
	 * @param typeName The name of the type definition.
	 * @param uIType Indicates whether the type is UI type and contains model that 
	 * check the perfmissions to set the data into the field from the request parameters.
	 */
	public ClassContextFieldType(Class typeClass, String typeName, boolean uIType, String basicXITypeName)
	{
		this (typeClass, null, null, typeName, uIType, basicXITypeName);
	}


	/**
	 * Returns the {@link Class} of the field type.
	 * @return Class 
	 */
	public Class getTypeClass()
	{
		return typeClass;
	}

	/**
	 * Returns the array of {@link ContextFieldBuilderBundle} objects that should 
	 * construct a new data with the class that declared in the type definition, 
	 * each time that a value was set into the context and it's class is not equals 
	 * to type in the type definition.
	 * @return ContextFieldBuilderBundle[]
	 */
	public ContextFieldBuilderBundle[] getBuilderBundleList()
	{
		return builderBundleArray;
	}

	/**
	 * Returns the {@link ContextFieldPrinterBundle} that should perform the formatting (masking) 
	 * of the field value.
	 * @return ContextFieldPrinterBundle
	 */
	public ContextFieldPrinterBundle getPrinterBundle()
	{
		return printerBundle;
	}
	
	/**
	 * Returns the {@link ContextFieldBuilderBundle} object that should 
	 * construct a new data with the class that declared in the type definition, 
	 * each time that a value was set into the context and it's class is not equals 
	 * to type in the type definition.
	 * @param fieldData The field data to build. 
	 * @return ContextFieldBuilderBundle
	 */
	public ContextFieldBuilderBundle getBuilderBundle(Object fieldData)
	{
		ContextFieldBuilderBundle builderBundle = null;
		
		for (int i = 0; builderBundleArray != null && i < builderBundleArray.length; i++)
		{
			ContextFieldBuilderBundle currentBundle = builderBundleArray[i];
			if (currentBundle.getTypeToBuildFrom().isInstance(fieldData))
			{
				builderBundle = currentBundle;
				break;
			}
		}
		return builderBundle;
	}
}
