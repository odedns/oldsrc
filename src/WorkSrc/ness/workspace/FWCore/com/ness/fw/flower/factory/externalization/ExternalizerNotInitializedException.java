/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExternalizerNotInitializedException.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory.externalization;

import com.ness.fw.flower.core.FlowerException;

/**
 * Thrown when try to use uninitialized externalizer
 */
public class ExternalizerNotInitializedException extends FlowerException
{
	public ExternalizerNotInitializedException(String message)
	{
		super(message);
	}
}
