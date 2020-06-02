/*
 * Created on: 15/11/2004
 * Author: baruch hizkya
 * @version $Id: Logger.java,v 1.1 2005/02/21 15:07:17 baruch Exp $
 */
package com.ness.fw.common.logger;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.util.SystemProperties;

public class Logger
{

	private static LoggerInterface loggerImpl;
	private static int commonLevel;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// levels
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final int LEVEL_FATAL =    1;
	public static final int LEVEL_ERROR    =    2;
	public static final int LEVEL_WARNING  =    4;
	public static final int LEVEL_INFO     =    8;
	public static final int LEVEL_DEBUG    =    16;

	/**
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 * @param configurationFile
	 */
	public static void reset(String configurationFile) throws LoggerException
	{		
		loggerImpl = LoggerFactory.getInstance().createLogger();
		loggerImpl.reset(configurationFile);
				
		String CONFIGURATION_FILE_NAME = "SystemConfiguration";
		String LEVEL_ENTRY = "systemLoggerLevel";
		try
		{
			String tmpLevel = new SystemProperties(CONFIGURATION_FILE_NAME).getProperty(LEVEL_ENTRY);
			commonLevel = tmpLevel == null ? 0: Integer.parseInt(tmpLevel);
		}
		catch (ResourceException e)
		{
			throw new LoggerException("Init logger problems");
		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Debug methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The method usefull while printing String message at the LEVEL_DEBUL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 */
	public static void debug(String context, String message)
	{
		if (checkSystemLevel(LEVEL_DEBUG))
		{
			loggerImpl.debug(context,message);
		}
	}
	
	/**
	 * The method usefull while printing Exception stack at the LEVEL_DEBUL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void debug(String context, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_DEBUG))
		{
			loggerImpl.debug(context,ex);
		}
	}
	/**
	 * The method usefull while printing Exception stack at the LEVEL_DEBUL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void debug(String context, String message, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_DEBUG))
		{
			loggerImpl.debug(context,message,ex);
		}
	}



	///////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Info methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The method usefull while printing String message at the LEVEL_INFO level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param extraInfo extra info to add to the message
	 */
	public static void info(String context, String message)
	{
		if (checkSystemLevel(LEVEL_INFO))
		{
			loggerImpl.info(context,message);
		}
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_INFO level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void info(String context, String message, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_INFO))
		{
			loggerImpl.info(context,message,ex);
		}
	}
    

	/**
	 * The method usefull while printing Exception stack at the LEVEL_INFO level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void info(String context, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_INFO))
		{
			loggerImpl.info(context,ex);
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Warning methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The method usefull while printing String message at the LEVEL_WARNING level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 */
	public static void warning(String context, String message)
	{
		if (checkSystemLevel(LEVEL_WARNING))
		{
			loggerImpl.warning(context,message);
		}
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_WARNING level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void warning(String context, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_WARNING))
		{
			loggerImpl.warning(context,ex);
		}
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_WARNING level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void warning(String context, String message, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_WARNING))
		{
			loggerImpl.warning(context,message,ex);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Error methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The method usefull while printing String message at the LEVEL_ERROR level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 */
	public static void error(String context, String message)
	{
		if (checkSystemLevel(LEVEL_ERROR))
		{
			loggerImpl.error(context,message);
		}
	}
 
	/**
	 * The method usefull while printing Exception stack at the LEVEL_ERROR level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void error(String context, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_ERROR))
		{
			loggerImpl.error(context,ex);
		}
	}
    
	/**
	 * The method usefull while printing Exception stack at the LEVEL_ERROR level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void error(String context, String message, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_ERROR))
		{
			loggerImpl.error(context,message,ex);
		}
	}


	///////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Fatal methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The method usefull while printing String message at the LEVEL_FATAL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 */
	public static void fatal(String context, String message)
	{
		if (checkSystemLevel(LEVEL_FATAL))
		{
			loggerImpl.fatal(context,message);
		}
	}
	
	/**
	 * The method usefull while printing Exception stack at the LEVEL_FATAL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void fatal(String context, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_FATAL))
		{
			loggerImpl.fatal(context,ex);
		}
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_FATAL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public static void fatal(String context, String message, Throwable ex)
	{
		if (checkSystemLevel(LEVEL_FATAL))
		{
			loggerImpl.fatal(context,message,ex);
		}
	}
	
	/**
	 * 
	 * @param level
	 * @return
	 */
	private static boolean checkSystemLevel(int level)
	{
		boolean pass = true;
		//checking the logging level with commonLevel.
		//The comporation of logging levels is performed at bitwize level.
		//For example:
		//  to see logs at LEVEL_ERROR and LEVEL_FATAL the commonLevel should be LEVEL_ERROR AND LEVEL_FATAL
		//  assuming that LEVEL_FATAL is 1 and LEVEL_ERROR is 2 - the commonLevel should be 1&2 = 3
		if ((level & commonLevel) == 0)
		{
			pass = false;
		}		
		return pass;
	}
}
