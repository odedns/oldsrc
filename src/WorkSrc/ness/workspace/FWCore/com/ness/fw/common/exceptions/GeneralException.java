/*
 * Author: yifat har-nof
 * @version $Id: GeneralException.java,v 1.2 2005/04/12 13:39:46 baruch Exp $
 */
package com.ness.fw.common.exceptions;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is the super class for all exceptions in the application.
 */
public class GeneralException extends Exception
{

	protected Date errorTime;
	protected ArrayList causeStackEntries = new ArrayList();

	/**
	 * create new AbstractException Object
	 */
	public GeneralException(Throwable error)
	{
		super(error);
		errorTime = new Date (System.currentTimeMillis());
	}

	/**
	 * create new AbstractException Object
	 */
	public GeneralException(String messageText, Throwable error)
	{
		super(messageText,error);
		errorTime = new Date (System.currentTimeMillis());
	}

	/**
	 * create new AbstractException Object
	 */
	public GeneralException(String messageText)
	{
		super(messageText);
		errorTime = new Date (System.currentTimeMillis());
	}
			
/*	public void extractDetails(Throwable throwable, String messageText)
	{
		if (throwable != null)
		{
			StackTraceElement steArray[] =  throwable.getStackTrace();
			boolean found = false;
			for (int i = 0; i < steArray.length && !found; i++)
			{
				StackTraceElement ste = steArray[i];
				String className = ste.getClassName();
				for (int j = 0; j < CLASS_PREFIXES.length && !found; j++)
				{
					String classPrefix = CLASS_PREFIXES[j];
					if(className.startsWith(classPrefix))
					{
						causeStackEntries.add(new CauseStackEntry(className, ste.getLineNumber(), messageText));
						found = true;
					}
				}
			}
		}
	} */
	
	/**
	 * @return ArrayList the causes stack
	 */
	public ArrayList getCauseStackEntries()
	{
		return causeStackEntries;
	}
	
	/**
	 * @param set the causeStackEntries
	 */
	public void setCauseStackEntries(ArrayList causeStackEntries)
	{
		this.causeStackEntries = causeStackEntries;
	}

}
