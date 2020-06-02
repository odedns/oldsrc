/*
 * Created on: 10/01/2005
 * @author: baruch hizkya
 * @version $Id: RegularExpressionChecks.java,v 1.1 2005/02/21 15:07:20 baruch Exp $
 */

package com.ness.fw.util.validation;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * A utitlity class for checking regular expressions
 */

public class RegularExpressionChecks
{
	private static HashMap patterns = null;

	/**
	 * 
	 * @param description
	 * @param regExPattern
	 * @param regExFlags
	 * @return
	 */
	public static boolean checkExpression(String description, String regExPattern, Integer regExFlags)
	{
		boolean result = true;
		Pattern pattern;

		// init hashmap
		if (patterns == null)
		{
			patterns = new HashMap();
		}
		
		// no description, no need to check
		if (description == null || description.equals(""))
		{
			result = true;
		}
		else
		{
			// try ti find a compile one
			String key = regExPattern + "|" + regExFlags;
			if (patterns.containsKey(key))
			{
				pattern = (Pattern)patterns.get(key);
			}
			// not found - compile it
			else
			{
				pattern = Pattern.compile(regExPattern, regExFlags.intValue());	
				patterns.put(key,pattern);		
			}
			result = pattern.matcher(description).matches();
		}	
		
		return result;
		
	}
}
