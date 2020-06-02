/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ComplexValidator.java,v 1.2 2005/03/29 14:56:51 yifat Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.*;
import com.ness.fw.common.logger.*;
import com.ness.fw.util.*;

import java.util.*;
import java.lang.reflect.*;

/**
 * com.ness.fw.flower.factory
 * ${CLASS_NAME}
 */
public class ComplexValidator extends Validator
{
	private ArrayList checks;
	private ArrayList flags;

	public ComplexValidator(String name)
	{
		setName(name);
		checks = new ArrayList();
		flags = new ArrayList();
	}

	public void addCheck(ValidationCheckWrapper wrapper, boolean stopOnError)
	{
		checks.add(wrapper);
		flags.add(new Boolean(stopOnError));
	}

	public void validate(Context ctx) throws ValidationException, ValidationProcessException
	{
		boolean result = true;
		boolean severeErrorFound = false;

		for (int i = 0; i < checks.size(); i++)
		{
			ValidationCheckWrapper checkWrapper = (ValidationCheckWrapper) checks.get(i);
			try
			{
				boolean b = checkWrapper.doCheck(ctx);
				if (!b)
				{
					result = false;
					if (((Boolean)flags.get(i)).booleanValue())
					{
						severeErrorFound = true;
						break;
						//throw new ValidationException();
					}
				}
			} catch (InvocationTargetException ex)
			{
				Logger.debug("ComplexValidator", ex);
				ApplicationUtil.getGlobalMessageContainer(ctx).addMessage(new Message("GE0006", Message.SEVERITY_ERROR));
				throw new ValidationProcessException("ComplexValidator", ex.getTargetException());
			} catch (Throwable ex)
			{
				Logger.error("ComplexValidator", ex);
				ApplicationUtil.getGlobalMessageContainer(ctx).addMessage(new Message("GE0006", Message.SEVERITY_ERROR));
				throw new ValidationProcessException("ComplexValidator", ex);
			}
		}

		if (!result)
		{
			throw new ValidationException(severeErrorFound);
		}
	}

	public void initialize(ParameterList parameterList) throws ValidationProcessException
	{
	}
}
