/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExternalizerInitializationException.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.*;

/**
 * Thrown when any problem is encountered while initializing an externalizer
 */
public class ExternalizerInitializationException extends FlowerException
{
	public ExternalizerInitializationException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ExternalizerInitializationException(String message)
	{
		super(message);
	}
}
