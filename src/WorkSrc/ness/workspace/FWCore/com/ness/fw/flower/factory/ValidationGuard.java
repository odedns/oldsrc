/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationGuard.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.flower.core.*;

/**
 * com.ness.fw.flower.factory
 * ${CLASS_NAME}
 */
public class ValidationGuard implements Guard
{
	private Validator validator;

	public ValidationGuard(Validator validator)
	{
		this.validator = validator;
	}

	public boolean check(Context ctx) throws GuardException
	{
		try
		{
			validator.validate(ctx);

			return true;
		} catch (ValidationException ex)
		{
			return false;
		} catch (ValidationProcessException ex)
		{
			throw new GuardException("Unable to check validation guard", ex);
		}
	}

	public void initialize(ParameterList parameterList) throws GuardException
	{
	}
}
