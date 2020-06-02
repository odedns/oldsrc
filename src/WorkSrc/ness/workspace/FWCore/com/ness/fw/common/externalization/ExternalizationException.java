/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExternalizationException.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.common.externalization;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Thrown when problem in externaliser is encountered
 */
public class ExternalizationException extends GeneralException
{
	public ExternalizationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ExternalizationException(String message)
	{
		super(message);
	}
}
