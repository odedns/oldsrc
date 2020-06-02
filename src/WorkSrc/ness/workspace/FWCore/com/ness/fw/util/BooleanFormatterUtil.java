/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: BooleanFormatterUtil.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.*;

/**
 * A helper class for formatting Boolean values.
 */
public class BooleanFormatterUtil
{
	/**
	 * Build a Boolean value from the given String. 
	 * @param booleanStr The String to build the Object from his value.
	 * @return Boolean The Boolean object built from the given String value.
	 */
	public static Boolean build(String booleanStr)
	{
		return new Boolean(booleanStr);
	}

	/**
	 * Returns a formatted String from the given Boolean.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param booleanValue The Boolean value to format.
	 * @return String The formatted boolean. 
	 * @throws ResourceException
	 */
	public static String print(LocalizedResources localizable, Boolean booleanValue) throws ResourceException
	{
		String key;
		String result;
		if (booleanValue == null)
		{
			result =  "";
		}
		else
		{ 
			if (booleanValue.booleanValue())
			{
				key = "true";
			}
			else
			{
				key = "false";
			}
			result = localizable.getString(key);
			if(result == null)
				result = key;
		}
		
		return result;
	}
}
