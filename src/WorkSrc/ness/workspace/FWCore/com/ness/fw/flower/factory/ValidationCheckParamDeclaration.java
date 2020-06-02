/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationCheckParamDeclaration.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import java.lang.reflect.*;

/**
 * com.ness.fw.flower.factory
 * ${CLASS_NAME}
 */
public class ValidationCheckParamDeclaration
{
	public static final int MULTIPLICITY_MANY                       = Integer.MAX_VALUE;

	private String name;
	private Constructor type;
	private int multiplicity;
	private String defaultType;

	public ValidationCheckParamDeclaration(String name, Constructor type, int multiplicity, String defaultType)
	{
		this.name = name;
		this.type = type;
		this.multiplicity = multiplicity;
		this.defaultType = defaultType;
	}

	public String getName()
	{
		return name;
	}

	public Constructor getType()
	{
		return type;
	}

	public int getMultiplicity()
	{
		return multiplicity;
	}

	public String getDefaultType()
	{
		return defaultType;
	}

	public void setDefaultType(String defaultType)
	{
		this.defaultType = defaultType;
	}

}