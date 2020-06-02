/*
 * Created on: 15/11/2004
 * Author: baruch hizkya
 * @version $Id: LoggerException.java,v 1.1 2005/02/21 15:07:17 baruch Exp $
 */
package com.ness.fw.common.logger;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * Thrown when problem in Logger is encountered
 */
public class LoggerException extends GeneralException
{
	public LoggerException(String s)
	{
		super(s);
	}
	
	public LoggerException(String s, Throwable cause)
	{
		super(s,cause);
	}
}
