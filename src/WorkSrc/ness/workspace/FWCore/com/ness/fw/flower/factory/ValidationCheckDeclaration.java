/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationCheckDeclaration.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import java.util.*;
import java.lang.reflect.*;

/**
 * com.ness.fw.flower.factory
 * ${CLASS_NAME}
 */
public class ValidationCheckDeclaration
{
	private String name;
	private Method validationCheckMethod;
	private ValidationCheck validationCheck;
	private ArrayList params;

	public ValidationCheckDeclaration(String name, ValidationCheck validationCheck)
	{
		this.name = name;
		this.validationCheck = validationCheck;
		params = new ArrayList();
	}

	public String getName()
	{
		return name;
	}

	public void addValidationCheckParamDeclaration(ValidationCheckParamDeclaration paramDeclaration)
	{
		params.add(paramDeclaration);
	}

	public int getValidationCheckParamDeclarationCount()
	{
		return params.size();
	}

	public ValidationCheckParamDeclaration getValidationCheckParamDeclaration(int index)
	{
		return (ValidationCheckParamDeclaration) params.get(index);
	}

	public Method getValidationCheckMethod()
	{
		return validationCheckMethod;
	}

	public void setValidationCheckMethod(Method validationCheckMethod)
	{
		this.validationCheckMethod = validationCheckMethod;
	}

	public ValidationCheck getValidationCheck()
	{
		return validationCheck;
	}
}
