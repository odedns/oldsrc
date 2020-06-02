/*
 * Created on: 15/10/2003
 * Author: baruch hizkya
 * @version $Id: Filter.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.logger.zs;

import java.util.*;

import com.ness.fw.common.logger.LoggerException;

/**
 * The class is ancestor for all filters classes.
 * It takes care for some tasks like dispatching log prints over all subfilters and aggregating some parameters
 * parameters over all subfilters
 */
public abstract class Filter
{
	/**
	 * list of subfilters
	 */
	private ArrayList filters;

	//the filter id
    private String id;

	/**
	 * default constructor - initializes the list for sub filters
	 */
	public Filter()
	{
		filters = new ArrayList();
	}

	/**
	 * Getter for filter Id
	 *
	 * @return filter Id
	 */
    public String getId()
    {
        return id;
    }

	/**
	 * Setter for filter Id
	 *
	 * @param id the id for filter
	 */
    public void setId(String id)
    {
        this.id = id;
    }

	/**
	 * The method is called by logger or by parent filter.
	 *
	 * @param context in which the log performs
	 * @param message the log message
	 * @param className the name of class from which the debuf print was performed
	 * @param level the debug level
	 */
	void log(String context, String message, String className, int level)
	{
		//calling to user customized print function.
        message = writeToLog(context, message, className, level);

        if (message != null)
        {
	        //run over list of sub filters
            for (int i = 0; i < filters.size(); i++)
            {
                Filter filter = (Filter) filters.get(i);
                filter.writeToLog(context, message, className, level);
            }
        }
	}

	/**
	 * The method is called by logger or by parent filter.
	 *
	 * @param context in which the log performs
	 * @param message the throwable stack trace
	 * @param level the debug level
	 */
	void logThrowable(String context, String message, int level)
	{
		//calling to user customized print function.
        message = writeToLogThrowable(context, message, level);

		//run over list of sub filters
        if (message != null)
        {
            for (int i = 0; i < filters.size(); i++)
            {
                Filter filter = (Filter) filters.get(i);
                filter.writeToLogThrowable(context, message, level);
            }
        }
	}

	/**
	 * Called by logger or by parent filter to query for list of requested parameters. Used while
	 * creating filters
	 *
	 * @return
	 * @throws LoggerException
	 */
	ParamSet getFilterParamSet() throws LoggerException
	{
		//calling to user implemented method
        ParamSet paramSet = getParamSet();

		//run over list of sub filters
		for (int i = 0; i < filters.size(); i++)
		{
			Filter filter = (Filter) filters.get(i);

			//retrieve sub filter parameters list
			ParamSet childParamSet = filter.getFilterParamSet();

			//if the sub filter parameters list is exists
            if (childParamSet != null)
            {
	            //if parameters list not created yet create it
                paramSet = paramSet == null ? ParamSet.createInstance() : paramSet;

	            //run over list of sub filter parameters and merging them into own parameters list
                for (int j = 0; j < childParamSet.getParamsCount(); j++)
                {
                    int p = childParamSet.getParam(j);
                    paramSet.addParam(p);
                }
            }
		}

		return paramSet;
	}

	//getter for filters list - used while creating filters
	ArrayList getFilters()
	{
		return filters;
	}

	/**
	 * Used while creating filters. The responsibility of filter writer to implement this method and
	 * to take care about every parameter defined in the configuration.
	 *
	 * @param key parameter key
	 * @param value parameter value
	 * @throws LoggerException
	 */
	abstract protected void setParam(String key, String value) throws LoggerException;

	/**
	 * Filter writer customized method used for actually logging job. Called by
	 * <code>Filter</code> while logging.
	 *
	 * @param context the context in which the log should be performed
	 * @param message the message to log
	 * @param className the class name from which the log was performed
	 * @param level the level of log print
	 * @return the constructed message for sub filters
	 */
	abstract protected String writeToLog(String context, String message, String className, int level);

    /**
     * Filter writer customized method used for actually logging job. Called by
	 * <code>Filter</code> while logging throwable.
     *
     * @param context the context in which the log should be performed
     * @param message the throwable stack trace to be logged
     * @param level the level of log print
     * @return the constructed message for sub filters
     */
	abstract protected String writeToLogThrowable(String context, String message, int level);

    /**
     * Called while creating filters. Responsibility of filter writer to implement this method
     * and clime every parameter the filter needs.
     *
     * <br>
     * For example:
     * <br><br>
     *
     * <code>
     * ParamSet paramSet = ParamSet.createInstance();<br>
     * paramSet.addParam(ParamSet.PARAM_CLASS_NAME);<br>
     * return paramSet;<br><br>
     * </code>
     *
	 * @return the constructed ParamSet
     * @throws LoggerException
     */
	abstract protected ParamSet getParamSet() throws LoggerException;

    /**
     * Called while creating filters. Responsibility of filter writer to implement this method
     * and clime the debug level the filter needs
     *
     * @return the desired debug level
     */
	abstract protected int getDebugLevel();
}
