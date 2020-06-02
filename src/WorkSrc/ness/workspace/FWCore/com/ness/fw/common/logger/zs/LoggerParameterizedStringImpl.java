/*
 * Created on: 15/12/2003
 * Author: baruch hizkya
 * @version $Id: LoggerParameterizedStringImpl.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.logger.zs;

/**
 * The implementation class for <code>LoggerParameterizedString</code>
 */
public class LoggerParameterizedStringImpl extends LoggerParameterizedString
{
	/**
	 * Placeholder character
	 */
	public static final char PLACEHOLDER = '?';

	/**
	 * The parameterized string (string with PLACEHOLDER's)
	 */
	private String string;

	/**
	 * Array for values that will replace placeholders
	 */
	private String values[];

	/**
	 * Setter for parameterized String
	 *
	 * @param s parameterized String
	 */
	public void setString(String s)
	{
		this.string = s;
        values = new String[10];
	}

	/**
	 * Setter for placeholder value
	 *
	 * @param key
	 * @param value
	 */
	public void setValue(int key, String value)
	{
		if (key >= values.length)
		{
			String values_1[] = new String[key + 10];
			System.arraycopy(values, 0, values_1, 0, values.length);
			values = values_1;
		}

        values[key] = value;
	}

	/**
	 * Getter for constructed String
	 *
	 * @return constructed string
	 */
    String getString()
    {
	    if (string == null)
	    {
		    return "";
	    }

	    //performiong construction
        StringBuffer sb = new StringBuffer(100);
        int placeholderIndex = -1;
	    char arr[] = string.toCharArray();

	    int j = 1; //index for values
	    //run over parameterized string and looking for placeholders and replace them
        for (int i = 0; i < arr.length; i++)
        {
	        char c = arr[i];
	        if (c == PLACEHOLDER)
	        {
		        placeholderIndex++;
		        sb.append(arr, placeholderIndex, i - placeholderIndex);
		        placeholderIndex = i;

		        sb.append(values[j++]);
	        }
        }

	    //is there is something to the right to the last placeholder - append it also
	    if (arr.length - 1 > placeholderIndex)
	    {
		    placeholderIndex ++;
            sb.append(arr, placeholderIndex, arr.length - placeholderIndex);
	    }

	    return sb.toString();

    }
}
