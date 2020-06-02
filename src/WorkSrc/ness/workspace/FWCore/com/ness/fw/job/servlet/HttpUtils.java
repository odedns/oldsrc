/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job.servlet;

import java.util.HashMap;
import java.util.StringTokenizer;


public class HttpUtils
{
	private static final String DELIMITER = "&";
	private static final String EQUAL = "=";

	public static HashMap parseQueryString(String userParamString)
	{
		HashMap params = new HashMap();
		StringTokenizer tokenizer = new StringTokenizer(userParamString,DELIMITER);
		while (tokenizer.hasMoreTokens())
		{
			String param = (String)tokenizer.nextElement();
			String key = param.substring(0,param.indexOf(EQUAL));
			String value = param.substring(param.indexOf(EQUAL) + 1);
			params.put(key,value);
		}
		return params;
	}
	
}
