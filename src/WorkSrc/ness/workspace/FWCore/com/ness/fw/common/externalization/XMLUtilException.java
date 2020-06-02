/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: XMLUtilException.java,v 1.1 2005/02/21 15:07:14 baruch Exp $
 */
package com.ness.fw.common.externalization;

import com.ness.fw.flower.core.FlowerException;

/**
 * Thrown when problem in XMLUtil is encountered
 */
public class XMLUtilException extends FlowerException
{
	public XMLUtilException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
