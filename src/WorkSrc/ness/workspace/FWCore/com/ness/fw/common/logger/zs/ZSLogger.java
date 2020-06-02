/*
 * Created on: 15/12/2003
 * Author: baruch hizkya
 * @version $Id: ZSLogger.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.logger.zs;

import org.w3c.dom.*;

import com.ness.fw.common.logger.LoggerException;
import com.ness.fw.common.resources.SystemResources;

import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.util.*;
import java.io.*;

/**
 * Allows writing messages to a log files, according to declaration of logger filters.
 */
public class ZSLogger
{
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Constants that should be used for for XML configuration file parsing
	//
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	private static final String NODE_LOGGER              =   "logger";
	private static final String NODE_FILTERS_LIST        =   "filterList";
	private static final String NODE_FILTER              =   "filter";
	private static final String NODE_FILTER_PARAM_LIST   =   "paramList";

	private static final String ATTR_FILTER_REF_ID       =   "refId";
	private static final String ATTR_FILTER_ID           =   "id";
	private static final String ATTR_FILTER_CLASS_NAME   =   "className";
	private static final String ATTR_PARAM_ID            =   "id";
	private static final String ATTR_PARAM_VALUE         =   "value";

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Debug levels.
	//  Actually for internal use only, but can be used by Filters writers also
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final int LEVEL_FATAL                      =    1;
	public static final int LEVEL_ERROR                         =    2;
	public static final int LEVEL_WARNING                       =    4;
	public static final int LEVEL_INFO                          =    8;
	public static final int LEVEL_DEBUG                         =    16;

    /**
     * The suffix for configuration files names.
     * All Configuration files must end with the suffix
     */
    private static final String FILENAME_SUFFIX                 =   ".xml";

	/**
	 * The array holds the boolean property for every kind of extra information that
	 * Logger engine can produce.<p>
	 *
	 * The index for the array is constants defined in the ParamSet class.<p>
	 *
	 * The value determines is the parameter described by the constant is needed.
	 * If no filter needs such information it will not be produced by Logger
	 */
	private static boolean params[];

	/**
	 * The arrayList contains all filters
	 */
	private static ArrayList filters;

    /**
     * The common debug level for all filters. Actually calculated by performing operation logical OR
     * between all devug levels of all filters. In other words: If one of filters clime any of debug levels -
     * Logger will include this level into commonLevel
     */
	private static int commonLevel;

	private static int capacity;
	private static Queue queue = null;
	private static boolean queueOverflow = false;
	private static SystemResources systemResources;

	public static void reset(String configurationFile) throws LoggerException
	{
		try
		{
			systemResources = SystemResources.getInstance();
			String fileName = configurationFile;
			initConfiguration(fileName);
		} 
		catch (Throwable e)
		{
			throw new LoggerException("Problem in reset logger",e);
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
 		log(context, message, LEVEL_DEBUG);
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
		log(context, ex, LEVEL_DEBUG);
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
		log(context, message, LEVEL_DEBUG);
		log(context, ex, LEVEL_DEBUG);
	}

	/**
	 * The method usefull while printing parameterized string message at the LEVEL_DEBUL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param parameterizedString the parameterized string to be logged.
	 *
	 *
	 * <p>For example:</p>
	 *
	 * <CODE>
	 *      LoggerParameterizedString lps = LoggerParameterizedString.createInstance();<br>
	 *      lps.setString("Your credit is ?. The lowest permited credit is ?.");<br>
	 *      lps.setValue(1, "1000$");<br>
	 *      lps.setValue(2, "5000$");<br><br>
	 *
	 *      Logger.error("any context", lps);<br>
	 * </CODE>
	 */
    public static void debug(String context, LoggerParameterizedString parameterizedString)
    {
		log(context, parameterizedString.getString(), LEVEL_DEBUG);
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
	 */
	public static void info(String context, String message)
    {
        log(context, message, LEVEL_INFO);
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
		log(context, message, LEVEL_INFO);
      	log(context, ex, LEVEL_INFO);
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
		log(context, ex, LEVEL_INFO);
	}


	/**
	 * The method usefull while printing parameterized string message at the LEVEL_INFO level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param parameterizedString - the parameterized string to be logged.
	 *
	 *
	 * <p>For example:</p>
	 *
	 * <CODE>
	 *      LoggerParameterizedString lps = LoggerParameterizedString.createInstance();<br>
	 *      lps.setString("Your credit is ?. The lowest permited credit is ?.");<br>
	 *      lps.setValue(1, "1000$");<br>
	 *      lps.setValue(2, "5000$");<br><br>
	 *
	 *      Logger.error("any context", lps);<br>
	 * </CODE>
	 *
	 */
    public static void info(String context, LoggerParameterizedString parameterizedString)
    {
        log(context, parameterizedString.getString(), LEVEL_INFO);
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
        log(context, message, LEVEL_WARNING);
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
        log(context, ex, LEVEL_WARNING);
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
		log(context, message, LEVEL_WARNING);
		log(context, ex, LEVEL_WARNING);
	}

	/**
	 * The method usefull while printing parameterized string message at the LEVEL_WARNING level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param parameterizedString the parameterized string to be logged.
	 *
	 *
	 * <p>For example:</p>
	 *
	 * <CODE>
	 *      LoggerParameterizedString lps = LoggerParameterizedString.createInstance();<br>
	 *      lps.setString("Your credit is ?. The lowest permited credit is ?.");<br>
	 *      lps.setValue(1, "1000$");<br>
	 *      lps.setValue(2, "5000$");<br><br>
	 *
	 *      Logger.error("any context", lps);<br>
	 * </CODE>
	 *
	 */
    public static void warning(String context, LoggerParameterizedString parameterizedString)
    {
        log(context, parameterizedString.getString(), LEVEL_WARNING);
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
        log(context, message, LEVEL_ERROR);
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
        log(context, ex, LEVEL_ERROR);
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
		log(context, message, LEVEL_ERROR);
		log(context, ex, LEVEL_ERROR);
	}

	/**
	 * The method usefull while printing parameterized string message at the LEVEL_ERROR level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param parameterizedString the parameterized string to be logged.
	 *
	 *
	 * <p>For example:</p>
	 *
	 * <CODE>
	 *      LoggerParameterizedString lps = LoggerParameterizedString.createInstance();<br>
	 *      lps.setString("Your credit is ?. The lowest permited credit is ?.");<br>
	 *      lps.setValue(1, "1000$");<br>
	 *      lps.setValue(2, "5000$");<br><br>
	 *
	 *      Logger.error("any context", lps);<br>
	 * </CODE>
	 *
	 */
    public static void error(String context, LoggerParameterizedString parameterizedString)
    {
        log(context, parameterizedString.getString(), LEVEL_ERROR);
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
        log(context, message, LEVEL_FATAL);
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
        log(context, ex, LEVEL_FATAL);
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
		log(context, message, LEVEL_FATAL);
		log(context, ex, LEVEL_FATAL);
	}

	/**
	 * The method usefull while printing parameterized string message at the LEVEL_FATAL level
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param parameterizedString the parameterized string to be logged.
	 *
	 *
	 * <p>For example:</p>
	 *
	 * <CODE>
	 *      LoggerParameterizedString lps = LoggerParameterizedString.createInstance();<br>
	 *      lps.setString("Your credit is ?. The lowest permited credit is ?.");<br>
	 *      lps.setValue(1, "1000$");<br>
	 *      lps.setValue(2, "5000$");<br><br>
	 *
	 *      Logger.error("any context", lps);<br>
	 * </CODE>
	 *
	 */
    public static void fatal(String context, LoggerParameterizedString parameterizedString)
    {
        log(context, parameterizedString.getString(), LEVEL_FATAL);
    }

	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//  private methods
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void log(String context, String message, int level)
	{
		//checking the logging level with commonLevel.
		//The common level reflects the accumulated needs for logging level of all filters.
		//If no filter needs logs at the level - such logs will not be processed
		//
		//The comporation of logging levels is performed at bitwize level.
		//For example:
		//  to see logs at LEVEL_ERROR and LEVEL_FATAL the commonLevel should be LEVEL_ERROR AND LEVEL_FATAL
		//  assuming that LEVEL_FATAL is 1 and LEVEL_ERROR is 2 - the commonLevel should be 1&2 = 3
		if ((level & commonLevel) == 0)
		{
			return;
		}

		Exception forStack;
		if (params[ParamSet.PARAM_CLASS_NAME])
		{
			forStack = new Exception();
		}
		else
		{
			forStack = null;
		}

		if (queue == null)
		{
			writeToLog(context, message, level, forStack);
		}
		else
		{
			synchronized (queue.getSync())
			{
				if (queue.isAddPossible())
				{
					if (queueOverflow)
					{
						queueOverflow = false;
					}

					queue.addEvent(new LoggerEvent(context, message, level, null, forStack));
				}
				else
				{
					if (!queueOverflow)
					{
						queue.addEvent(new LoggerEvent("LOGGER", "Logger queue overflow. Some prints will be lost.", LEVEL_FATAL, null, forStack));
						queueOverflow = true;
					}
				}
			}
		}
	}

	/**
	 * The method actually performs the logging job
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param message the string to be logged
	 * @param level the logging level
	 */
	private static void writeToLog(String context, String message, int level, Exception ex)
	{
		//the variable for class name (actually for line number also)
		String className = null;

		//checking is any of filters needs parameter named PARAM_CLASS_NAME
		//
		//The params array reflects accumulated needs of all filters for all optional parameters
		//If no filter climes for PARAM_CLASS_NAME this param will not be discovered at all.
        if (params[ParamSet.PARAM_CLASS_NAME])
        {
            try{
                //parsing class name and line number from specially created Exception
	            //creating string with all Exception stack
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
	            String s = sw.toString();

	            //string tokenizer to break the exception stack into separate lines
                StringTokenizer stringTokenizer = new StringTokenizer(s, "\n");

	            //skiping 3 lines
	            stringTokenizer.nextToken();
	            stringTokenizer.nextToken();
	            stringTokenizer.nextToken();

                //and finally the class name with the line
	            className = stringTokenizer.nextToken().trim();
            }catch (Throwable ex1)
            {
	            //if the parsing fails - we can not determine the class name.
	            //So the class name will null
            }
        }

		//run over the list of filters and perform the log
        for (int i = 0; i < filters.size(); i++)
        {
	        Filter filter = (Filter) filters.get(i);
	        filter.log(context, message, className, level);
        }
	}

	private static void log(String context, Throwable ex, int level)
	{
		if (queue == null)
		{
			writeToLog(context, ex, level);
		}
		else
		{
			synchronized (queue.getSync())
			{
				if (queue.isAddPossible())
				{
					if (queueOverflow)
					{
						queueOverflow = false;
					}

					queue.addEvent(new LoggerEvent(context, null, level, ex, null));

				}
				else
				{
					if (!queueOverflow)
					{
						queue.addEvent(new LoggerEvent("LOGGER", "Logger queue overflow. Some prints will be lost.", LEVEL_FATAL, null, params[ParamSet.PARAM_CLASS_NAME] ? new Exception() : null));
						queueOverflow = true;
					}
				}
			}
		}
	}
	/**
	 * The method actually performs the logging job for Throwable
	 *
	 * @param context the context of the log line. Usefull for filtering and rerouting of log output
	 * to different devices(files)
	 * @param ex the Throwable that's stack to be logged
	 * @param level the logging level
	 */
	private static void writeToLog(String context, Throwable ex, int level)
	{
		//checking the logging level with commonLevel.
		//The common level reflects the accumulated needs for logging level of all filters.
		//If no filter needs logs at the level - such logs will not be processed
		//
		//The comporation of logging levels is performed at bitwize level.
		//For example:
		//  to see logs at LEVEL_ERROR and LEVEL_FATAL the commonLevel should be LEVEL_ERROR AND LEVEL_FATAL
		//  assuming that LEVEL_FATAL is 1 and LEVEL_ERROR is 2 - the commonLevel should be 1&2 = 3
		if ((level & commonLevel) == 0)
        {
	        return;
        }

		//Prints contents of ex stack int string
		StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
		String s = sw.toString();

		//run over the list of filters and perform the log
        for (int i = 0; i < filters.size(); i++)
        {
	        Filter filter = (Filter) filters.get(i);
	        filter.logThrowable(context, s, level);
        }
	}

	/**
	 * Initializes the logger using parameters from configuration files
	 *
	 * @param fileName the name of file that contains the logger configuration. Every Logger
	 * filter has its own configuration file
	 * @throws LoggerException thrown if any problem experienced while reading configuration files.
	 * For example error opening file or XML is not well formatted.
	 */
	private static void initConfiguration(String fileName) throws LoggerException
	{
		//initializing filters list
		filters = new ArrayList();

		//extract configuration directory path (location of configuration files)
		//File.separator is not used because user can use unix separator on windows system
		int separatorIndex = fileName.lastIndexOf("/");
        if (separatorIndex == -1)
        {
            separatorIndex = fileName.lastIndexOf("\\");
        }

		String confPath;
		if (separatorIndex == -1)
		{
            confPath = "";
		}
		else
		{
			confPath = fileName.substring(0, separatorIndex + 1);//including the separator
		}

		//create logger document
		Document doc = createDocument(fileName);

		//extract root element (<logger>)
        Element root = doc.getDocumentElement();

		//filtering - removing extra characters
		filterXML(root);

		//extracting <filterList> node
		Node filtersListNode = root.getElementsByTagName(NODE_FILTERS_LIST).item(0); //supposed to be only one

		//creating filters
		processFilters(filtersListNode, filters, confPath);

        //retrieve debug level
        commonLevel = discoverCommonDebugLevel(filters);

		//retrieve requested params
        retrieveRequestedParams(filters);

		//create queue
		Element queueElement = (Element) root.getElementsByTagName("queueCapacity").item(0);
		if (queueElement != null)
		{
			String capacity_str = queueElement.getAttribute("value");
			capacity = Integer.parseInt(capacity_str);
			if (capacity > 0)
			{
				queue = new Queue(capacity);

				(new Thread(){
					public void run()
					{
						for(;;)
						{
							LoggerEvent le = (LoggerEvent) queue.removeEvent();

							if (le.getEx() != null)
							{
								writeToLog(le.getContext(), le.getEx(), le.getLevel());
							}
							else
							{
								writeToLog(le.getContext(), le.getMessage(), le.getLevel(), le.getExForStack());
							}
						}
					}
				}).start();
			}
		}
	}

	/**
	 *
	 * Creates the complete list of parameters needed by all filters.
	 * For now only one parameter can be present - PARAM_CLASS_NAME
	 *
	 * @param filters the filters list
	 */
	private static void retrieveRequestedParams(ArrayList filters) throws LoggerException
	{
		//array for holding all accumulated parameters
		//the index is parameter itself
		//the value is accumulated parameter need
        params = new boolean[ParamSet.PARAMS_COUNT];
        Arrays.fill(params, false);

		//run over all filters
		for (int i = 0; i < filters.size(); i++)
		{
			Filter filter = (Filter) filters.get(i);

			//retrieve the list of parameters climed by filter
			ParamSet paramSet = filter.getFilterParamSet();

			//the param set must not be exist. If filter returns null it
			//means that the filter does not clime any special parameter
            if (paramSet != null)
            {
	            //run over all parameters climed by filter and fillinf params array
                for (int j = 0; j < paramSet.getParamsCount(); j++)
                {
                    int param = paramSet.getParam(j);
                    switch (param)
                    {
                        case ParamSet.PARAM_CLASS_NAME:
                            params[ParamSet.PARAM_CLASS_NAME] = true;
                            break;
                        default:
                            System.out.println("Illegal param [" + param + "] requested by logger filter");
                            break;
                    }
                }
            }
		}
	}

	/**
	 * Determine what debug levels are climed by filters.
	 * Only for thse debug levels that was climed by at least one filter logs will be processed.
	 *
	 * @param filters the filters list
	 * @return the accumulated debug level
	 */
	private static int discoverCommonDebugLevel(ArrayList filters)
	{
		int level = 0;

		//run over all filters list
        for (int i = 0; i < filters.size(); i++)
        {
	        Filter filter = (Filter) filters.get(i);

	        //performing or operation between level of first filters and this filters level
            level |= filter.getDebugLevel();

            //perform or operation between previous result and all subfilters of the filter level
	        level |= discoverCommonDebugLevel(filter.getFilters());
        }

		return level;
	}

	/**
	 * Usefull for filtering XML documents (files) from any
	 * unnessesary elements such as new lines or spaces. Every XML document should be passed
	 * this filter before processing.
	 *
	 *
	 * @param root the element to be filtered. Usually the root element.
	 */
	private static void filterXML(Element root)
	{
		//runs recurcively over all the nodes and removes everyting that is not Element
        NodeList children = root.getChildNodes();
		for (int i = 0; i < children.getLength();)
		{
			Node node = children.item(i);

			if (!(node instanceof Element))
			{
				root.removeChild(node);
			}
			else
			{
				filterXML((Element)node);
                i++;
			}
		}
	}

	/**
	 * Creates the Document instance from XML file
	 *
	 * @param fileName the source file for XML Document
	 * @return the Document instance
	 * @throws LoggerException thrown if file can not be red or XML is not well formated
	 */
	private static Document createDocument(String fileName) throws LoggerException
	{
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(false);
			Document doc = factory.newDocumentBuilder().parse(new FileInputStream(fileName));

			return doc;
		}catch (Throwable ex)
		{
			throw new LoggerException("Unable to read configuration file [" + fileName + "] due to exception [" + ex.toString() + "]");
		}
	}

	/**
	 * Used to create filters while initializing process
	 *
	 * @param filtersListNode the XML node containing the list of filters
	 * @param filtersList the ArrayList for new created filters
	 * @param confPath the path where filters confiuration files are placed
	 * @throws LoggerException
	 */
	private static void processFilters(Node filtersListNode, ArrayList filtersList, String confPath) throws LoggerException
	{
		//list of nodes that supposed to be filters each of them
		NodeList filtersNodesList = filtersListNode.getChildNodes();
		for (int i = 0; i < filtersNodesList.getLength(); i++)
		{
			Node node = filtersNodesList.item(i);
			String nodeName = node.getNodeName();
			//if the node is filter (should be only)
			if (nodeName.equals(NODE_FILTER))
			{
				//retrieve the id of filter
				String refId = ((Element)node).getAttribute(ATTR_FILTER_REF_ID);

				//creates new Filter instance and adds it to list
                filtersList.add(createFilter(refId, confPath));
			}
		}
	}

	/**
	 * Parses filter configuration file and cretes new filter instance
	 *
	 * @param refId the filter id
	 * @param confPath the path where configuration files are placed
	 * @return new Filter instance
	 * @throws LoggerException
	 */
	private static Filter createFilter(String refId, String confPath) throws LoggerException
	{
		//construction filter configuration file name
        String fileName = confPath + refId + FILENAME_SUFFIX;

        //create document
        Document doc = createDocument(fileName);

        //root <filter>
		Element root = doc.getDocumentElement();

		//filtering
		filterXML(root);

		//retrieve filter id
		String filterId = root.getAttribute(ATTR_FILTER_ID);
		if (!filterId.equals(refId))
		{
			//filter id is inconsistent with file name
            throw new LoggerException("Filter [" + filterId + "] should not be defined at file [" + fileName + "]");
		}

		//retrieve new filter class name
        String className = root.getAttribute(ATTR_FILTER_CLASS_NAME);
		//creating flter instance
		Filter filter = createFilterInstance(className);

		//set filter id
		filter.setId(filterId);

        //retrieve param list element
		Element paramListElement = (Element) root.getElementsByTagName(NODE_FILTER_PARAM_LIST).item(0);

		//process params for filter (if params element exists in the XML)
        if (paramListElement != null)
        {
		    processFilterParams(paramListElement, filter);
        }

        //extracting <filterList> node (subfilters)
		Node filtersListNode = root.getElementsByTagName(NODE_FILTERS_LIST).item(0);

        //processing subfilters (if exist)
        if (filtersListNode != null)
        {
            processFilters(filtersListNode, filter.getFilters(), confPath);
        }

		return filter;
	}

	/**
	 * Reads filter params from XML and fill them into filter instance
	 *
	 * @param paramListElement the XML element of params
	 * @param filter the filter to be filled with the parameters
	 * @throws LoggerException
	 */
	private static void processFilterParams(Element paramListElement, Filter filter) throws LoggerException {
        //list of elements that supposed to be filter params
        NodeList paramsNodeList = paramListElement.getChildNodes();

		//run over all params and fill them int filter
		for (int i = 0; i < paramsNodeList.getLength(); i++)
		{
			Element paramElement = (Element) paramsNodeList.item(i);
            String paramId      = paramElement.getAttribute(ATTR_PARAM_ID);
			String paramValue   = paramElement.getAttribute(ATTR_PARAM_VALUE);

			filter.setParam(paramId, paramValue);
		}
	}

	/**
	 * Create the Filter instance using filters class name
	 *
	 * @param className the class name to be instantiated
	 * @return new Filter instance
	 * @throws LoggerException
	 */
	private static Filter createFilterInstance(String className) throws LoggerException
	{
		try
		{
			return (Filter) Class.forName(className).newInstance();
		} catch (Throwable e)
		{
			throw new LoggerException("Unable to create filter due exception: [" + e.toString() + "]");
		}
	}

	/**
	 * Prints XML into <code>System.out</code> For debug Pourposes only. Currently not in use
	 *
	 * @param n the node to be printed
	 */
    private static void printXML(Node n)
    {
        try{
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            Source source = new DOMSource(n);
            Result result = new StreamResult(System.out);
            transformer.transform(source, result);
        }catch (Throwable ex)
        {
            ex.printStackTrace();
        }
    }

	private static class LoggerEvent
	{
		private String context;
		private String message;
		private int level;
		private Exception exForStack;
		private Throwable ex;

		public LoggerEvent(String context, String message, int level, Throwable ex, Exception exForStack)
		{
			this.context = context;
			this.message = message;
			this.level = level;
			this.ex = ex;
			this.exForStack = exForStack;
		}

		public String getContext()
		{
			return context;
		}

		public String getMessage()
		{
			return message;
		}

		public int getLevel()
		{
			return level;
		}

		public Throwable getEx()
		{
			return ex;
		}

		public Exception getExForStack()
		{
			return exForStack;
		}
	}

	private static class Queue
	{
		Object arr[];
		Object sync;
		int head, tail;
		int count;
		int capacity;

		public Queue(int capacity)
		{
			this.arr = new Object [capacity];
			this.capacity = capacity;
			sync = new Object();
			head = 0;
			count = 0;
			tail = 0;
		}

		public boolean addEvent(Object e)
		{
			synchronized(sync)
			{
				if (count < capacity)
				{
					arr[head] = e;
					count ++;

					head ++;
					if (head == capacity)
					{
						head = 0;
					}

					sync.notify();
					return true;
				}

				return false;
			}
		}

		public boolean isAddPossible()
		{
			synchronized (sync)
			{
				return count < capacity - 1;
			}
		}

		public Object getSync()
		{
			return sync;
		}

		public Object removeEvent()
		{
			synchronized (sync)
			{
				if (count == 0)
				{
					try
					{
						sync.wait();
					} catch (InterruptedException e){}
				}

				Object result = arr[tail];
				arr[tail] = null;
				count --;

				tail ++;
				if (tail == capacity)
				{
					tail = 0;
				}

				return result;
			}
		}
	}
}

