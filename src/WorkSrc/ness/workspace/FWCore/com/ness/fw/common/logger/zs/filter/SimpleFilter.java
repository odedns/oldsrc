/*
 * Created on: 15/12/2003
 * Author: baruch hizkya
 * @version $Id: SimpleFilter.java,v 1.1 2005/02/21 15:07:20 baruch Exp $
 */
package com.ness.fw.common.logger.zs.filter;

import com.ness.fw.common.logger.LoggerException;
import com.ness.fw.common.logger.zs.Filter;
import com.ness.fw.common.logger.zs.ParamSet;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Simple logger filter implementation.
 */
public class SimpleFilter extends Filter
{
	/**
	 * Constant used while parsing filters params
	 */
    private static final String PARAM_KEY_LEVEL                                         =   "level";
	/**
	 * Constant used while parsing filters params
	 */
    private static final String PARAM_KEY_FILE_NAME                                     =   "outputFileName";
	/**
	 * Constant used while parsing filters params
	 */
    private static final String PARAM_KEY_MAX_FILE_SIZE                                 =   "maxFileSize";
	/**
	 * Constant used while parsing filters params
	 */
    private static final String PARAM_KEY_DATE_FORMAT                                   =   "dateFormat";
	/**
	 * Constant used while parsing filters params
	 */
    private static final String PARAM_KEY_FILE_NAME_INDEX_LENGTH                        =   "fileNameIndexLength";
	/**
	 * Constant used while parsing filters params
	 */
	private static final String PARAM_KEY_PRINT_EXTRA_INFO                              =   "printExtraInfo";
	/**
	 * Constant used while parsing filters params
	 */
	private static final String PARAM_KEY_CONTEXT_LIST                                  =   "contextList";
	/**
	 * Constant used while parsing filters params
	 */
	private static final String PARAM_KEY_CLASS_NAME_LIST                               =   "classNameList";
	/**
	 * Constant used while parsing filters params
	 */
	private static final String PARAM_KEY_MAX_FILES                                     =   "maxFiles";

	/**
	 * Used while constracting log line string
	 */
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
	 * The configurable debul level of the filter.
	 */
    private int level = 0;

	/**
	 * The name of files used for output. With no directory name and no extension
	 */
    private String fileName = null;

	/**
	 *The directory name for output files
	 */
	private String directoryName = null;

	/**
	 * The filename suffix (extension) for output file. For example .txt or .out
	 */
    private String fileNameSuffix = null;

    /**
     * the index to be combined with the file name.
     */
    private int filesIndex = 0;

	/**
	 * number of currently exsistion log files
	 */
	private int filesCounter = 0;

	/**
	 * number of characters in the current log file
	 */
    private int charsCounter = 0;

    /**
     * The configurable limit for file size
     */
    private int fileSizeLimit = 0;

	/**
	 * The configurable limit of log files. If the limit exceeded the oldest files will be deleted
	 */
	private int maxFiles = Integer.MAX_VALUE;

    /**
     * The configurable date format used for formating date and time of log line (printed on log)
     */
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSS");

	/**
	 * The configurable length of file index string that will be combined with file name.
	 * For example:<br>
	 *
	 *  any-file-name_0003.out where 0003 is index string with length 4
	 */
    private int fileNameIndexLength = 5;

	/**
	 * Configurable parameter that defines if class name and line number should be printed or not.
	 */
	private boolean printExtraInfo = false;

    /**
     * The configurable comma separated list of classes to be used while filtering.
     * If the list is empty - no class name filtering will be performed.<br>
     *
     * To satisfy the class name filter the class name of class from which the log print
     * was performed should contain one of class names in the list.<br>
     *
     * <br>
     *
     * For example if the debug print was performed from class <code>com.ness.fw.infra.AnyClass</code>
     *  and class name list contains <code>com,mabat.ui,com.ness.fw.infra</code> the log line will
     * be printed
     */
    private ArrayList classNameList = null;

	/**
     * The configurable comma separated list of contexts to be used while filtering.
     * If the list is empty - no context filtering will be performed.<br>
     *
     * To satisfy the context filter the context in which the log print
     * was performed should contain one of contexts in the list.<br>
     *
     * <br>
     *
     * For example if the debug print was performed with context <code>ptihat_tik</code>
     *  and class name list contains <code>tik,any_context</code> the log line will
     * be printed
     */
	private ArrayList contextList = null;

	/**
	 * The BufferedWriter to be used for prints
	 */
    private BufferedWriter bw;

    /////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Overriding methods
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * The method overrides the corresponding method of Filter and performs the costomized log job
	 *
	 * @param context the logging context
	 * @param message the message to be printed
	 * @param className the class name from which the log was performed
	 * @param level the log level
	 * @return the produced message for sub filters
	 */
	protected String writeToLog(String context, String message, String className, int level)
	{
		//checking debug level
		if (!checkLevel(level))
		{
			return null;
		}

		//checking context filter
		if (!checkContext(context))
		{
			return null;
		}

		//checking class name filter
		if (!checkClassName(className))
		{
			return null;
		}

		//combining log string
		StringBuffer sb = new StringBuffer(100);
		sb.append(dateFormat.format(new Date()));
		sb.append(" ");
		sb.append(LEVELS[level]);
		sb.append(" [");
		sb.append(context);
		sb.append("] [");
		sb.append(message);
		sb.append("] ");

		if (printExtraInfo)
		{
			//should be included only if extra info was requested by configuration
			sb.append(className);
		}

		sb.append("\n");

		String text = sb.toString();

		//prints the string
		print(text);

        //return the produced message for sub filtrs
		return text;
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
		//checking debug level
		if (!checkLevel(level))
		{
			return null;
		}
        //checking context filter
		if (!checkContext(context))
		{
			return null;
		}

		//combining log string
		StringBuffer sb = new StringBuffer(100);
		sb.append(dateFormat.format(new Date()));
		sb.append(" ");
		sb.append(LEVELS[level]);
		sb.append(" [");
		sb.append(context);
		sb.append("] ");
		sb.append(message);

		String text = sb.toString();

		//prints the string
		print(text);

		//return the produced message for sub filtrs
		return text;
	}

	/**
	 * This method called by Logger or by parent Filter to discover
	 * the list of parameters needed by this filter.<br>
	 *
	 * Current filter climes that it needs class name only
	 *
	 * @return the list of climed params or <code>null</code> if printExtraInfo is not defined by configuration
	 */
    protected ParamSet getParamSet()
	{
		if (printExtraInfo)
		{
			ParamSet paramSet = ParamSet.createInstance();
			paramSet.addParam(ParamSet.PARAM_CLASS_NAME);
			return paramSet;
		}else
		{
			return null;
		}
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
        //file name
        else if (PARAM_KEY_FILE_NAME.equals(key))
        {
	        //split file name to file name and extension
            int lastPointIndex = value.lastIndexOf(".");
            if (lastPointIndex == -1)
            {
                fileName = value;
                fileNameSuffix = "";
            }
            else
            {
                fileName = value.substring(0, lastPointIndex);
                fileNameSuffix = value.substring(lastPointIndex);
            }

	        //split file name to file name and directory name
	        int sepIndex = fileName.lastIndexOf("/");
			if (sepIndex == -1)
			{
				sepIndex = fileName.indexOf("\\");
			}

			if (sepIndex == -1)
			{
				directoryName = "./";
			}
			else
			{
				directoryName = fileName.substring(0, sepIndex + 1);
				fileName = fileName.substring(sepIndex + 1);
			}

	        //creating writer
            try {
                bw = new BufferedWriter(new FileWriter(createFileNameFirstTime()));
	            filesCounter++;
            } catch (IOException ex)
            {
                throw new LoggerException("Unable to create file [" + value + "] for Filter [" + getId() + "] due to exception " + ex.toString());
            }
        }
        //maximum size of file
        else if (PARAM_KEY_MAX_FILE_SIZE.equals(key))
        {
            try{
                fileSizeLimit = Integer.parseInt(value) * 1000;
            }catch (Throwable ex)
            {
                throw new LoggerException("Unable to set param [" + key + "] for Filter [" + getId() + "] due to exception " + ex.toString());
            }
        }
        //maximum number of files
        else if (PARAM_KEY_MAX_FILES.equals(key))
        {
            try{
                maxFiles = Integer.parseInt(value);
            }catch (Throwable ex)
            {
                throw new LoggerException("Unable to set param [" + key + "] for Filter [" + getId() + "] due to exception " + ex.toString());
            }
        }
        //The date format string (printing dates at each log line)
        else if (PARAM_KEY_DATE_FORMAT.equals(key))
        {
            dateFormat = new SimpleDateFormat(value);
        }
        //the number of digits of file name index
        else if (PARAM_KEY_FILE_NAME_INDEX_LENGTH.equals(key))
        {
            try{
                fileNameIndexLength = Integer.parseInt(value);
            }catch (Throwable ex){}
        }
        //is class name and line number should be printed
        else if (PARAM_KEY_PRINT_EXTRA_INFO.equals(key))
        {
            printExtraInfo = value.startsWith("y") || value.startsWith("Y");
        }
        //list of contexts for context filtering
		else if (PARAM_KEY_CONTEXT_LIST.equals(key))
		{
            if (value.length() > 0)
            {
	            //the list of contexts
	            contextList = new ArrayList();

	            //split comma separated list of contexts into separate strings
                for (StringTokenizer stringTokenizer = new StringTokenizer(value, ","); stringTokenizer.hasMoreTokens();)
                {
	                String context = stringTokenizer.nextToken();
	                contextList.add(context);
                }
            }
		}
        //list of class names for class name filtering
		else if (PARAM_KEY_CLASS_NAME_LIST.equals(key))
		{
            if (value.length() > 0)
            {
	            //list of class names
	            classNameList = new ArrayList();

	            //split comma separated list of class names into separate strings
                for (StringTokenizer stringTokenizer = new StringTokenizer(value, ","); stringTokenizer.hasMoreTokens();)
                {
	                String className = stringTokenizer.nextToken();
	                classNameList.add(className);
                }
            }
		}

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//  Private methods
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Performs the actual prints
	 *
	 * @param text the text to be printed
	 */
	private void print(String text)
	{
		//looking is file size limit is exceeded
		if (charsCounter + text.length() > fileSizeLimit)
        {
	        //closing previous and creating new file
            createNextFile();
            charsCounter = text.length();
        }
        else
        {
            charsCounter += text.length();
        }

        try{
	        //and finally write !!!
            bw.write(text);
            bw.flush();
        }catch (Throwable ex)
        {
            System.out.println(text);
        }
	}

	/**
	 * used for level checking
	 *
	 * @param level level of current log line
	 * @return <code>true</code> if the line should be printed
	 */
	private boolean checkLevel(int level)
	{
		//perform bitwize or to check if <code>level</code> is included in <code>this.level</code>
		return !((this.level & level) == 0);
	}

	/**
	 * Used for context filtering
	 *
	 * @param context context of log line
	 * @return <code>true</code> if the line should be printed
	 */
	private boolean checkContext(String context)
	{
		//context filtering enabled only if contexts list is not <code>null</code>
		if (contextList != null)
		{
			//runs over list of contexts
            for (int i = 0; i < contextList.size(); i++)
            {
	            String ctx = (String) contextList.get(i);
	            //looking if context from the list contains context of log line inside.
	            if (context.indexOf(ctx) != -1)
	            {
		            return true;
	            }
            }

			return false;
		}

		return true;
	}

	/**
	 * Used for class name filtering
	 *
	 * @param className the name of class from which the debug log was performed
	 * @return <code>true</code> if the line should be printed
	 */
	private boolean checkClassName(String className)
	{
		//class name filtering enabled only if class name list is not <code>null</code>
		if (classNameList != null && printExtraInfo)
		{
            for (int i = 0; i < classNameList.size(); i++)
            {
	            //run over list of class names
	            String clName = (String) classNameList.get(i);
	            //looking if class name from the list contains class name from which the log was printed.
	            if (className.indexOf(clName) != -1)
	            {
		            return true;
	            }
            }

			return false;
		}

		return true;
	}

	/**
	 * Creates the file name for the first time
	 *
	 * @return the new file name
	 */
    private String createFileNameFirstTime()
    {
	    //determine the last file index from previous run
        filesIndex = lookForLastFileIndex();
        filesIndex++;

	    //make file name index string with the length as defined by configuration
        String fileIndex = String.valueOf(filesIndex++);
        fileIndex = completeIndexString(fileIndex);

	    //combining new file name
        return directoryName + fileName + "_" + fileIndex + fileNameSuffix;
    }

	/**
	 * Used to determine the last log file on the disk
	 *
	 * @return the last index (the biggest)
	 */
    private int lookForLastFileIndex()
    {
	    //File instance for logging directory
        File destDirFile = new File(directoryName);

	    //if the logging directory not yet exists - create it
        if (!destDirFile.exists())
        {
            destDirFile.mkdirs();
        }

	    //creating list of files in the logging directory that matches the log file name.
        String files[] = destDirFile.list(new MyFileNameFilter(fileName));

	    //if no files exist will start from 0
        if (files == null)
        {
            return 0;
        }
        else
        {
	        //updating files counter
	        filesCounter = files.length;

	        int lastIndex = 0;

	        //run over list of files
            for (int i = 0; i < files.length; i++)
            {
	            //extracting index
                String fileName = files[i];
                int _sepIndex = fileName.indexOf("_");
                if (_sepIndex == -1)
                {
                    continue;
                }
                try{
                    int index = Integer.parseInt(fileName.substring(_sepIndex + 1, fileName.indexOf(fileNameSuffix)));

	                //looking for latest index
                    if (index > lastIndex)
                    {
                        lastIndex = index;
                    }
                }catch (Throwable ex)
                {
                    continue;
                }
            }

	        //return the latest index
            return lastIndex;
        }
    }

	/**
	 * Used to create new file every time the file size exceeds maximum file size limit
	 *
	 */
	private void createNextFile()
    {
	    //closing previous file
        try{
            bw.flush();
            bw.close();
        }catch (Throwable ex)
        {
            ex.printStackTrace();
        }

	    //is the maxFiles limit reached
	    if (filesCounter >= maxFiles)
	    {
		    deleteOldFile();
	    }

	    //creating file name index for next file
        String fileIndex = String.valueOf(filesIndex++);
        fileIndex = completeIndexString(fileIndex);

	    //opening new file
        try{
            bw = new BufferedWriter(new FileWriter(directoryName + fileName + "_" + fileIndex + fileNameSuffix));
	        filesCounter++;
        }catch (Throwable ex)
        {
            ex.printStackTrace();
        }
    }

	/**
	 * Used to delete old files every time the maximum files number (from configuration) is reached
	 *
	 */
	private void deleteOldFile()
	{
		//creating File instance for logging directory
        File destDirFile = new File(directoryName);

		//retrieve list of log files
        String files[] = destDirFile.list(new MyFileNameFilter(fileName));

		//if the number of currently existing log files smaller then maxFiles (someone
		//already deleted old files). will only adjust filesCounter
		if (files.length < maxFiles)
		{
            filesCounter = files.length;
		}
		else
		{
			//will delete the oldest file
			int firstIndex = Integer.MAX_VALUE;

			//looking the oldest index
			for (int i = 0; i < files.length; i++)
			{
				String fileName = files[i];
				int _sepIndex = fileName.indexOf("_");
				if (_sepIndex == -1)
				{
					continue;
				}
				try{
					int index = Integer.parseInt(fileName.substring(_sepIndex + 1, fileName.indexOf(fileNameSuffix)));
					if (index < firstIndex)
					{
						firstIndex = index;
					}
				}catch (Throwable ex)
				{
					continue;
				}
			}

			//the name of the oldest file
			String oldestFileName = directoryName + fileName + "_" + completeIndexString(String.valueOf(firstIndex)) + fileNameSuffix;

			//the File object instance for the oldest file
			File oldestFile = new File(oldestFileName);

			//deleting the file
			oldestFile.delete();

			//decrement files counter
			filesCounter--;
		}


    }

	/**
	 * Used to enlarge file name index string as defined in configuration
	 *
	 * @param fileIndex
	 * @return the enlarged file name index string
	 */
    private String completeIndexString(String fileIndex)
    {
        while (fileIndex.length() < fileNameIndexLength)
        {
            fileIndex = "0" + fileIndex;
        }

        return fileIndex;
    }

	/**
	 * Used for filtering of list of files while looking for last file index
	 */
    private class MyFileNameFilter implements FilenameFilter
    {
        private String fileName;

        public MyFileNameFilter(String fileName)
        {
            this.fileName = fileName;
        }

        public boolean accept(File dir, String name)
        {
            return name.indexOf(fileName) != -1;
        }


    }

}
