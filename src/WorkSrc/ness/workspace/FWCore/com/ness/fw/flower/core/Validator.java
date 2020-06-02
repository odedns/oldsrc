/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Validator.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Common ansestor for all validators
 */
public abstract class Validator
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Called by system to perform validation
	 *
	 * @param ctx <code>Context</code> to validate
	 * @throws ValidationException thrown when validation failed. Contains list of missing or missspelled fields
	 */
	public abstract void validate(Context ctx) throws ValidationException, ValidationProcessException;
	public abstract void initialize(ParameterList parameterList) throws ValidationProcessException;
}
