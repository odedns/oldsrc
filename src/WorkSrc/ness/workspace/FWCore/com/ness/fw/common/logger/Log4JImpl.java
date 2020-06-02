/*
 * Created on: 15/11/2004
 * Author: baruch hizkya
 * @version $Id: Log4JImpl.java,v 1.1 2005/02/21 15:07:17 baruch Exp $
 */
package com.ness.fw.common.logger;

import java.util.HashMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

class Log4JImpl implements LoggerInterface
{

	private String LoggerClassName = Log4JImpl.class.getName();
	private HashMap loggers;
	
	// init the Hash here's since:
	// 1. it's a singeltion and there is no fear to wasted aloocation
	// 2. to avoid the if sentence in getLogger  
	protected Log4JImpl()
	{	
		loggers = new HashMap();
	}
	
	/**
	 * initzlize the logger
	 * @param configurationFile
	 */
	public void reset(String configurationFile)
	{
		DOMConfigurator.configure(configurationFile);	
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
		Logger logger = getLogger(context);
		logger.log(LoggerClassName,Level.DEBUG,message,null);
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
		Logger logger = getLogger(context);
		logger.log(LoggerClassName,Level.DEBUG,null, ex);
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
		getLogger(context).log(LoggerClassName,Level.DEBUG,message,ex);
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
		getLogger(context).log(LoggerClassName,Level.INFO,message,null);
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
		getLogger(context).log(LoggerClassName,Level.INFO,message,ex);		
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
		getLogger(context).log(LoggerClassName,Level.INFO, null, ex);
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
		getLogger(context).log(LoggerClassName,Level.WARN, message,null);
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
		getLogger(context).log(LoggerClassName,Level.WARN, null, ex);
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
		getLogger(context).log(LoggerClassName,Level.WARN,message,ex);
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
		getLogger(context).log(LoggerClassName,Level.ERROR,message,null);
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
		getLogger(context).log(LoggerClassName,Level.ERROR,null,ex);
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
		getLogger(context).log(LoggerClassName,Level.ERROR,message,ex);
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
		getLogger(context).log(LoggerClassName,Level.FATAL,message,null);
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
		getLogger(context).log(LoggerClassName,Level.FATAL,null,ex);
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
		getLogger(context).log(LoggerClassName,Level.FATAL,message,ex);
	}
	


	///////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Gettig a logger according to context
	 * @param context
	 * @return
	 */
	private Logger getLogger(String context)
	{		
		// no impl was created for that context
		Logger logger = (Logger)loggers.get(context);
		if (logger == null)
		{
			logger = Logger.getLogger(context);
			loggers.put(context,logger);
		}
		
		return logger;
	}
}
