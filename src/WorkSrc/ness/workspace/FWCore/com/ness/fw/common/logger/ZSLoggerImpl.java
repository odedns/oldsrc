/*
 * Created on: 15/11/2004
 * Author: baruch hizkya
 * @version $Id: ZSLoggerImpl.java,v 1.1 2005/02/21 15:07:17 baruch Exp $
 */
package com.ness.fw.common.logger;

import com.ness.fw.common.logger.zs.ZSLogger;

class ZSLoggerImpl implements LoggerInterface
{	
	protected ZSLoggerImpl()
	{	
	}
	
	/**
	 * initzlize the logger
	 * @param configurationFile
	 */
	public void reset(String configurationFile) throws LoggerException
	{
		ZSLogger.reset(configurationFile);
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
	public void debug(String context, String message)
	{
		ZSLogger.debug(context,message);
	}
	
	/**
	 * The method usefull while printing Exception stack at the LEVEL_DEBUL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public  void debug(String context, Throwable ex)
	{
		ZSLogger.debug(context,ex);
	}
	/**
	 * The method usefull while printing Exception stack at the LEVEL_DEBUL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public void debug(String context, String message, Throwable ex)
	{
		ZSLogger.debug(context,message,ex);
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
	public void info(String context, String message)
	{
		ZSLogger.info(context,message);
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_INFO level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public void info(String context, String message, Throwable ex)
	{
		ZSLogger.info(context,message,ex);
	}
    

	/**
	 * The method usefull while printing Exception stack at the LEVEL_INFO level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public  void info(String context, Throwable ex)
	{
		ZSLogger.info(context,ex);
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
	public void warning(String context, String message)
	{
		ZSLogger.warning(context,message);
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_WARNING level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public void warning(String context, Throwable ex)
	{
		ZSLogger.warning(context,ex);
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_WARNING level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public void warning(String context, String message, Throwable ex)
	{
		ZSLogger.warning(context,message,ex);
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
	public void error(String context, String message)
	{
		ZSLogger.error(context,message);
	}
 
	/**
	 * The method usefull while printing Exception stack at the LEVEL_ERROR level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public  void error(String context, Throwable ex)
	{
		ZSLogger.error(context,ex);
	}
    
	/**
	 * The method usefull while printing Exception stack at the LEVEL_ERROR level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public void error(String context, String message, Throwable ex)
	{
		ZSLogger.error(context,message,ex);
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
	public void fatal(String context, String message)
	{
		ZSLogger.fatal(context,message);
	}
	
	/**
	 * The method usefull while printing Exception stack at the LEVEL_FATAL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public void fatal(String context, Throwable ex)
	{
		ZSLogger.fatal(context,ex);
	}

	/**
	 * The method usefull while printing Exception stack at the LEVEL_FATAL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param ex the Throwable instance that message and stack to be logged
	 */
	public void fatal(String context, String message, Throwable ex)
	{
		ZSLogger.fatal(context,message,ex);
	}
}