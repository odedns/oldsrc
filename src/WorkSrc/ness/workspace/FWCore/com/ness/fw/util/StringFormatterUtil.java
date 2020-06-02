/*
 * Author: yifat har-nof
 * @version $Id: StringFormatterUtil.java,v 1.2 2005/05/04 11:53:43 yifat Exp $
 */
package com.ness.fw.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * A helper class for formatting String values.
 */
public class StringFormatterUtil
{
	private static char hexDigit[] =
		{
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9',
			'a',
			'b',
			'c',
			'd',
			'e',
			'f' };

	private static StringFormatterUtil me;

	/** Not accessable from outside */
	private StringFormatterUtil()
	{
	}

	/**
	 *
	 * @param text
	 * @param what
	 * @param with
	 * @return
	 */
	public static String replace(String text, String what, String with)
	{
		if (text == null)
		{
			return null;
		}
		StringBuffer sb = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(what, start)) != -1)
		{
			sb.append(text.substring(start, end)).append(with);
			start = end + what.length();
		}
		sb.append(text.substring(start));
		return sb.toString();
	}

	/**
	 * @param src
	 * @param desiredLength
	 * @param by
	 * @param fromStart
	 * @return  */
	public static String complete(
		String src,
		int desiredLength,
		char by,
		boolean fromStart)
	{
		StringBuffer sb = new StringBuffer(src);
		if (fromStart)
		{
			while (sb.length() < desiredLength)
				sb.insert(0, by);
		}
		else
		{
			while (sb.length() < desiredLength)
				sb.append(by);
		}
		return sb.toString();
	}

	/**
	 * @param src
	 * @param prefix
	 * @return  */
	public static String hebToHex(String src, String prefix)
	{
		return hebToHex(src, prefix, false);
	}

	/**
	 * @param src
	 * @param prefix
	 * @param allCharacters
	 * @return  */
	public static String hebToHex(
		String src,
		String prefix,
		boolean allCharacters)
	{
		int hebConstant = 1264;
		int firstHebrewChar = 224 + hebConstant;
		int lastHebrewChar = 250 + hebConstant;
		StringBuffer dst = new StringBuffer();
		char chars[] = src.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] >= firstHebrewChar && chars[i] <= lastHebrewChar)
			{
				dst.append(prefix);
				dst.append(Integer.toHexString(chars[i] - hebConstant));
			}
			else if (allCharacters || Character.isWhitespace(chars[i]))
			{
				dst.append(prefix);
				dst.append(Integer.toHexString(chars[i]));
			}
			else
			{
				dst.append(chars[i]);
			}
		}
		return dst.toString();
	}

	/** Separetes String by delimiter to String[].<br>
	 * Example: <br>
	 * separateByDelim("a,b,c",",") -> {"a","b","c"}
	 * @param string String to separate
	 * @param delimiter The delimiter
	 * @return String array.
	 */
	public static String[] separateByDelimiter(String string, String delimiter)
	{
		StringTokenizer tok = new StringTokenizer(string, delimiter);
		String[] res = new String[tok.countTokens()];
		for (int i = 0; i < res.length; i++)
		{
			res[i] = tok.nextToken().trim();
		}
		return res;
	}

	/** Separetes String by delimiter to List.<br>
	 * Example: <br>
	 * separateByDelim("a,b,c",",") -> {"a","b","c"}
	 * @param string String to separate
	 * @param delimiter The delimiter
	 * @return List
	 */
	public static List convertStringToList(String string, String delimiter)
	{
		StringTokenizer tok = new StringTokenizer(string, delimiter);
		int count = tok.countTokens();
		List res = new ArrayList(count);
		for (int i = 0; i < count; i++)
		{
			res.add(tok.nextToken().trim());
		}
		return res;
	}

	/**
	 * Constructs a list of tokens for the specified string. 
	 * The char in the delim argument is the delimiter
	 * for separating tokens. Delimiter character itself will not be 
	 * treated as token.
	 * @param str The string to construct.
	 * @param delimiter The delimiter
	 * @return List A list with the tokens.
	 */
	public static List convertStringToList(String str, char delimiter)
	{
		ArrayList tokens = null;
		
		if(str != null && str.trim().length() > 0)
		{
			tokens = new ArrayList();
			String sub = null;
	
			int i = 0;
			int j = str.indexOf(delimiter);
	
			while (j >= 0)
			{
				sub = str.substring(i, j);
				// if there is value, add it
				if (sub.length() > 0)
				{
					tokens.add(sub);
				}
				// skip over delimiter we already found
				i = j + 1; 
				//	find next delimiter
				j = str.indexOf(delimiter, i); 
			}
	
			//	Don't forget the last substring
			sub = str.substring(i); 
			if (sub.length() > 0)
			{
				tokens.add(sub);
			}
		}
		
		return tokens;
	}
	
	/** convert the List to String, seperate the values with the delimiter 
	 * @param values The List of values to convert to String. 
	 * @param delimiter The delimiter that seperate the values
	 * @return String
	 */
	public static String convertListToString(List values, String delimiter)
	{
		String results = "";
		int count = values.size();
		for (int i = 0; i < count; i++)
		{
			results += values.get(i).toString();
			if(i < (count - 1))
				results += delimiter;
		}
		return results;
	}

	/** Converts any String to unicode "/uXXXX" format.
	 * @param s string
	 * @return converter string
	 */
	public static String toUTF(String s)
	{
		if (me == null)
		{
			me = new StringFormatterUtil();
		}
		char[] chars = s.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++)
		{
			String hex = me.charToHex(chars[i]);
			sb.append("\\u");
			sb.append(hex);
		}
		return sb.toString();
	}

	private String byteToHex(byte b)
	{
		// Returns hex String representation of byte b
		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	private String charToHex(char c)
	{
		// Returns hex String representation of char c
		byte hi = (byte) (c >>> 8);
		byte lo = (byte) (c & 0xff);
		return byteToHex(hi) + byteToHex(lo);
	}

	/**
	 * Replaces: <br/>
	 * all `` to " <br/>
	 * all ` to ' <br/>
	 * all &amp; to & <br/>
	 * @param value
	 * @return
	 */
	public static String fromHTML(String value)
	{
		value = replace(value, "``", "\"");
		value = replace(value, "`", "'");
		value = replace(value, "&amp;", "&");
		return value;
	}

	/**
	 * Replaces: <br/>
	 * all " to `` <br/>
	 * all ' to ` <br/>
	 * all & to &amp; <br/>
	 * @param value
	 * @return
	 */
	public static String toHTML(String value)
	{
		value = replace(value, "\"", "``");
		value = replace(value, "'", "`");
		value = replace(value, "&", "&amp;");
		return value;
	}

	/**
	 * Replaces: <br/>
	 * all & to &amp; <br/>
	 * @param value
	 * @return
	 */
	public static String toXML(String value)
	{
		value = replace(value, "&", "&amp;");
		return value;
	}

	/** Parse the value of an Object to String including apostrophes in String objects.
	 * @param obj The Object to parse.
	 * @return String The parsed parameter value as String.
	 */
	private static String parseValue(Object obj)
	{
		if (obj instanceof Timestamp)
		{
			return DateFormatterUtil.format((Timestamp) obj, DateFormatterUtil.FORMAT_TIMESTAMP);
		}
		else if (obj instanceof Time)
		{
			return DateFormatterUtil.format((Time) obj, DateFormatterUtil.FORMAT_TIME);
		}
		else if (obj instanceof java.util.Date)
		{
			return DateFormatterUtil.format((java.util.Date) obj, DateFormatterUtil.FORMAT_DATE);
		}
		else
		{
			return obj.toString();
		}
	}

	/** Build a text statement with the parameters embeded instead of the ? marks.
	 */
	public static String buildText(String src, List parameters)
	{
		StringBuffer sb = new StringBuffer(4096);
		int point = 0;
		int lastPos = 0;
		for (int i = 0; i < parameters.size() && point != -1 ; i++)
		{
			String placeHolder = '%' + String.valueOf(i+1); 
			point = src.indexOf(placeHolder, lastPos);
			if (point != -1)
			{
				sb.append(src.substring(lastPos, point));
				sb.append(parseValue(parameters.get(i)));
				lastPos = point + 2;
			}
		}
		if (lastPos < src.length())
		{
			sb.append(src.substring(lastPos, src.length()));
		}
		return sb.toString();
	}

	/**
	 * Check if the text is not empty (contains real text).
	 * @param text
	 * @return boolean
	 */
	public static boolean isEmpty(String text)
	{
		if (text == null || (text != null && text.trim().equals("")))
		{		
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Check if the text contain numbers only.
	 * @param text
	 * @return boolean
	 */
	public static boolean isNumeric (String text)
	{
		boolean rc = false;
		if (text != null)
		{
			try{
				Long.parseLong(text);
				rc = true;
			}catch (Throwable ex){}
		}
		else
		{
			rc = true;
		}
		return rc;
	}
	
	/** Converts a given Map to String.
	 * @param map the Map
	 * @param pairsDelimiter The delimiter between key-value pairs.
	 * Note that the separator between key and value in a specific
	 * pair, is fixed to "=" as the entrySet() method uses it to separate
	 * between keys and values.
	 * @return String
	 * @author Amit Mendelson
	 */
	public static String convertMapToString(Map map, 
		String pairsDelimiter)
	{
	
		java.util.Iterator iterator = map.entrySet().iterator();
		StringBuffer sb = new StringBuffer(2048);
		String nextString = null;
		
		while(iterator.hasNext())
		{
			nextString = iterator.next().toString();
			sb.append(nextString);
			sb.append(pairsDelimiter);
		}
		return sb.toString();
	}

	/** This method is used to generate a Map from the given String.
	 * @param string string to pass its contents to Map.
	 * This string should be in the appropriate format:
	 * key-value pairs, separated by delimiters, and a second delimiter to
	 * separate between the key and the value.
	 * E.g. "key1=value1;key2=value2;key3=value3" where ; is the pairs delimiter
	 * and = is the key-value delimiter.
	 * @param keyValueDelimiter - a string that serves as a delimiter between
	 * key and value in a key-value token.
	 * @param pairsDelimiter - a string that serves as a delimiter between
	 * key-values tokens.
	 * @return Map the generated HashMap.
	 * If the string format is invalid, java.util.NoSuchElementException
	 * will be thrown.
	 * @author Amit Mendelson
	 */
	public static Map convertStringToMap(String string, 
		String keyValueDelimiter, String pairsDelimiter)
	{
		HashMap map = new HashMap();
		/*
		 * Break the String into the key-value tokens.
		 */
		StringTokenizer tokenizer = 
			new StringTokenizer(string, pairsDelimiter);
		int count = tokenizer.countTokens();
		String keyValue;
		StringTokenizer tokenizer2;
		/*
		 * For each key-value token, break it into the key and
		 * the value, and put both of them in the HashMap.
		 */
		for (int i = 0; i < count; i++)
		{
			keyValue = tokenizer.nextToken().trim();
			tokenizer2 = new StringTokenizer(keyValue, keyValueDelimiter);
			map.put(tokenizer2.nextToken().trim(), tokenizer2.nextToken().trim());
		}
		return map;
	}
	

}