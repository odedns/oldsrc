/*
 * Created on: 15/11/2004
 * Author: baruch hizkya
 * @version $Id: LoggerFactory.java,v 1.1 2005/02/21 15:07:17 baruch Exp $
 */
package com.ness.fw.common.logger;

import com.ness.fw.util.SystemProperties;

public class LoggerFactory
{
	private String LOG4J_IMPL="log4J";
	private String ZS_LOG_IMPL="zsLogger";

	private static LoggerFactory factory = null;
	
	public static LoggerFactory getInstance()
	{
		if (factory == null)
		{
			factory = new LoggerFactory();
		}
		
		return factory;
	}

	public LoggerInterface createLogger() throws LoggerException
	{		
		String CONFIGURATION_FILE_NAME = "SystemConfiguration";
		String LOGGER_IMPL_ENTRY = "logger";
		LoggerInterface loggerInterface = null;
		try
		{
			String logImpl = new SystemProperties(CONFIGURATION_FILE_NAME).getProperty(LOGGER_IMPL_ENTRY);

			if (logImpl == null)
			{
				throw new LoggerException("no valid implementor was found. set logger entry in the property file");
			}
	
			// log4j impl
			if (logImpl.equals(LOG4J_IMPL))
			{
				loggerInterface = new Log4JImpl();
			}
			// out log impl
			else if (logImpl.equals(ZS_LOG_IMPL))
			{
				loggerInterface = new ZSLoggerImpl();			
			}
			else
			{
				throw new LoggerException("unsupported looger impl.");
			}
		}
		catch (Throwable e)
		{
			throw new LoggerException("problem in creating logger" + e);
		}
		
		return loggerInterface;
	}

}
