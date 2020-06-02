/*
 * Created on: 15/12/2003
 * Author: baruch hizkya
 * @version $Id: PrintOnScreenFilter.java,v 1.1 2005/02/21 15:07:20 baruch Exp $
 */
package com.ness.fw.common.logger.zs.filter;

import com.ness.fw.common.logger.*;
import com.ness.fw.common.logger.zs.Filter;
import com.ness.fw.common.logger.zs.ParamSet;

import java.text.*;
import java.util.*;

/**
 * Print on screen logger filter implementation.
 */
public class PrintOnScreenFilter extends Filter
{
	/**
	 * Constant used while parsing filters params
	 */
	private static final String PARAM_KEY_LEVEL                                         =   "level";

	/**
	 * The configurable debul level of the filter.
	 */
	private int level = 0;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSS");

	private static final String LEVELS[] = new String[]{
		null,
		"-FATAL-",
		"-ERROR----",
		null,
		"-WARNING--",
		null,null,null,
		"-INFO-----",
		null,null,null,null,null,null,null,
		"-DEBUG----"
	};

	/**
	 * This method called by Logger while processing filtr params. This method called once for each param
	 *
	 * @param key param name
	 * @param value param value
	 * @throws LoggerException
	 */
	protected void setParam(String key, String value) throws LoggerException
	{
		//debug level
		if (PARAM_KEY_LEVEL.equals(key))
        {
            try{
                level = Integer.parseInt(value);
            }catch (Throwable ex)
            {
                throw new LoggerException("Unable to set param [" + key + "] for Filter [" + getId() + "] due to exception " + ex.toString());
            }
        }
	}

	/**
	 * The method overrides the corresponding method of Filter and performs the costomized log job
	 *
	 * @param context the logging context
	 * @param message the message to be printed (message constructed by parent filter)
	 * @param className the class name from which the log was performed
	 * @param level the log level
	 * @return the produced message for sub filters
	 */
	protected String writeToLog(String context, String message, String className, int level)
	{
		if ((this.level & level) == 0)
		{
			return null;
		}


		StringBuffer sb = new StringBuffer(2048);
		sb.append(dateFormat.format(new Date()) + " ");
		sb.append(LEVELS[level] + " [");
		sb.append(context + "] ");
		sb.append(message);
        System.out.println(sb.toString());

		return message;
	}

	/**
	 * The method overrides the corresponding method of Filter and performs the costomized log job
	 *
	 * @param context the logging context
	 * @param message the exception stack
	 * @param level the log level
	 * @return produced message for sub filters
	 */
	protected String writeToLogThrowable(String context, String message, int level)
	{
		if ((this.level & level) == 0)
		{
			return null;
		}

		System.out.print(message);

		return message;
	}

	/**
	 * This method called by Logger or by parent Filter to discover
	 * the list of parameters needed by this filter.<br>
	 *
	 * Current filter climes does not need any of extra parameters (actually it prints
	 * message constructed by parent filter)
	 *
	 * @return <code>null</code>
	 */
	protected ParamSet getParamSet() throws LoggerException
	{
		return null;
	}

	/**
	 * This method called by Logger or by parent Filter to discover
	 * the debug level of current filter.<br>
	 *
	 * @return the current filter debug level
	 */
	protected int getDebugLevel()
	{
		return level;
	}

}
