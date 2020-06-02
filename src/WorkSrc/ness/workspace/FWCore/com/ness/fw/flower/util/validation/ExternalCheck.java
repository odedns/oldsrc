/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExternalCheck.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.flower.util.validation;

import com.ness.fw.flower.factory.*;
import com.ness.fw.common.logger.*;

import java.lang.reflect.*;

/**
 * Validator for external Checks.
 */
public class ExternalCheck implements ValidationCheck
{
	/**
	 * Execute external check from the given className and pass the parameter
	 * @param checkBundle
	 * @param className
	 * @param methodName
	 * @param params
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean check(ValidationCheckBundle checkBundle, String className, String methodName, String params[])
	{
		try{
			ValidationCheck externalCheck = (ValidationCheck) Class.forName(className).newInstance();
			Method method = externalCheck.getClass().getMethod(methodName, new Class[]{ValidationCheckBundle.class, String[].class});

			return ((Boolean)method.invoke(externalCheck, new Object[]{checkBundle, params})).booleanValue();
		}catch (Throwable ex)
		{
			Logger.debug("ExternalCheck", ex instanceof InvocationTargetException ? ((InvocationTargetException)ex).getTargetException() : ex);
			return false;
		}
	}
}
