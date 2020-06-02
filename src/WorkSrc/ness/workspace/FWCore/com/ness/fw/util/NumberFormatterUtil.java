/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: NumberFormatterUtil.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.*;

import java.math.*;
import java.text.*;

/**
 * A helper class for formatting Number values.
 */
public class NumberFormatterUtil
{
	
	public static final String NO_ZERO_DECIMAL = "###############.######";
	public static final String CURRENCY = "###,###,###,###,###.######";
	
	
	/**
	 * The key for the default out mask for for Number.
	 */
	public static String DEFAULT_OUT_MASK_NUMBER = "number.defaultOutMask";

	/**
	 * The key for the default out mask of the type Long.
	 */
	public static String DEFAULT_OUT_MASK_LONG = "long.defaultOutMask";

	/**
	 * The key for the default out mask of the type integer.
	 */
	public static String DEFAULT_OUT_MASK_INTEGER = "integer.defaultOutMask";

	/**
	 * The key for the default out mask of the type double.
	 */
	public static String DEFAULT_OUT_MASK_DOUBLE = "double.defaultOutMask";

	/**
	 * The key for the default out mask of the type BigDecimal.
	 */
	public static String DEFAULT_OUT_MASK_BIG_DECIMAL = "bigDecimal.defaultOutMask";

	
	/**
	 * Build a Integer value from the given String. 
	 * @param numberStr The String to build the Object from his value. 
	 * @return Integer The Integer object built from the given String value.
	 */
	public static Integer buildInt(String numberStr)
	{
		char arr[] = numberStr.toCharArray();
		StringBuffer sb = new StringBuffer(arr.length);
		for (int i = 0; i < arr.length; i++)
		{
			char c = arr[i];

			switch (c)
			{
				case ',':
					break;
				case '-':
					if (i == 0)
					{
						sb.append(c);
					}
					else if (i == arr.length - 1)
					{
						sb.insert(0, c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
				default:
					if (Character.isDigit(c))
					{
						sb.append(c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
			}
		}

		return new Integer(sb.toString());
	}

	/**
	 * Returns a formatted String from the given Integer according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given Integer.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param number The Integer value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted integer. 
	 * @throws ResourceException
	 */
	public static String printInt(LocalizedResources localizable, Integer number, String maskKey) throws ResourceException
	{
		if(maskKey == null)
		{
			maskKey = DEFAULT_OUT_MASK_INTEGER;
		}
		
		return printNumber(localizable, number, maskKey);
	}

	/**
	 * Build a Long value from the given String. 
	 * @param numberStr The String to build the Object from his value.
	 * @return Long The Long object built from the given String value.
	 */
	public static Long buildLong(String numberStr)
	{
		char arr[] = numberStr.toCharArray();
		StringBuffer sb = new StringBuffer(arr.length);
		for (int i = 0; i < arr.length; i++)
		{
			char c = arr[i];

			switch (c)
			{
				case ',':
					break;
				case '-':
					if (i == 0)
					{
						sb.append(c);
					}
					else if (i == arr.length - 1)
					{
						sb.insert(0, c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
				default:
					if (Character.isDigit(c))
					{
						sb.append(c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
			}
		}

		return new Long(sb.toString());
	}

	/**
	 * Returns a formatted String from the given Long according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given Long.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param number The Long value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted long. 
	 * @throws ResourceException
	 */
	public static String printLong(LocalizedResources localizable, Long number, String maskKey) throws ResourceException
	{
		if(maskKey == null)
		{
			maskKey = DEFAULT_OUT_MASK_LONG;
		}
		
		return printNumber(localizable, number, maskKey);
	}

	/**
	 * Build a Double value from the given String. 
	 * @param numberStr The String to build the Object from his value.
	 * @return Double The Double object built from the given String value.
	 */
	public static Double buildDouble(String numberStr)
	{
		char arr[] = numberStr.toCharArray();
		StringBuffer sb = new StringBuffer(arr.length);
		boolean dotFound = false;
		for (int i = 0; i < arr.length; i++)
		{
			char c = arr[i];

			switch (c)
			{
				case '.':
					if (dotFound)
					{
						throw new NumberFormatException(numberStr);
					}
					else
					{
						dotFound = true;
						sb.append(c);
					}
					break;
				case ',':
					break;
				case '-':
					if (i == 0)
					{
						sb.append(c);
					}
					else if (i == arr.length - 1)
					{
						sb.insert(0, c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
				default:
					if (Character.isDigit(c))
					{
						sb.append(c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
			}
		}

		return new Double(sb.toString());
	}

	/**
	 * Returns a formatted String from the given Number according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given Number.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param number The Number value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted number. 
	 * @throws ResourceException
	 */
	public static String printNumber(LocalizedResources localizable, Number number, String maskKey) throws ResourceException
	{
		String results = "";
		if (number != null)
		{
			if(maskKey == null)
			{
				maskKey = DEFAULT_OUT_MASK_NUMBER;
			}
	
			String mask = localizable.getString(maskKey);
	
			if(mask != null)
			{
				results = new DecimalFormat(mask).format(number);
			}
			else
			{
				results = number.toString();
			}
		}
				
		return results;
	}

	/**
	 * Returns a formatted String from the given Double according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given Double.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param number The Double value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted double. 
	 * @throws ResourceException
	 */
	public static String printDouble(LocalizedResources localizable, Double number, String maskKey) throws ResourceException
	{
		if(maskKey == null)
		{
			maskKey = DEFAULT_OUT_MASK_DOUBLE;
		}
		
		return printNumber(localizable, number, maskKey);
	}

	/**
	 * Build a BigDecimal value from the given String, according to the roundMode,
	 * length & scale. 
	 * @param numberStr The String to build the Object from his value.
	 * @param roundMode
	 * @param length
	 * @param scale
	 * @return BigDecimal The BigDecimal object built from the given String value.
	 */
	public static BigDecimal buildBigDecimal(String numberStr, String roundMode, int length, int scale)
	{
		char arr[] = numberStr.toCharArray();
		StringBuffer sb = new StringBuffer(arr.length);
		boolean dotFound = false;
		boolean minusFound = false;
		for (int i = 0; i < arr.length; i++)
		{
			char c = arr[i];

			switch (c)
			{
				case '.':
					if (dotFound)
					{
						throw new NumberFormatException(numberStr);
					}
					else
					{
						dotFound = true;
						sb.append(c);
					}
					break;
				case ',':
					break;
				case '-':
					minusFound = true;

					if (i == 0)
					{
						sb.append(c);
					}
					else if (i == arr.length - 1)
					{
						sb.insert(0, c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
				default:
					if (Character.isDigit(c))
					{
						sb.append(c);
					}
					else
					{
						throw new NumberFormatException(numberStr);
					}
					break;
			}
		}

		String str = sb.toString();
		int indexOfPoint = str.indexOf(".");
		int strLen = str.length();
		if (minusFound)
		{
			strLen--;
		}

		if (indexOfPoint == -1)
		{
			if (strLen > length - scale)
			{
				throw new NumberFormatException("Unable to build BigDecimal: length of [" + str + "] exceeds the predefined [" + length + "]");
			}
		}
		else
		{
			if (indexOfPoint + scale > length)
			{
				throw new NumberFormatException("Unable to build BigDecimal: length of [" + str + "] exceeds the predefined [" + length + "]");
			}
		}

		try
		{
			return new BigDecimal(sb.toString()).setScale(scale, BigDecimal.class.getField(roundMode).getInt(null));
		}catch (NumberFormatException ex)
		{
			throw ex;
		} catch (Exception ex)
		{
			throw new NumberFormatException("Unable to build BigDecimal: possible wrong round mode. [" + ex + "]");
		}
	}

	/**
	 * Returns a formatted String from the given BigDecimal according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given BigDecimal.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param number The BigDecimal value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted bigDecimal. 
	 * @throws ResourceException
	 */
	public static String printBigDecimal(LocalizedResources localizable, BigDecimal number, String maskKey) throws ResourceException
	{
		if(maskKey == null)
		{
			maskKey = DEFAULT_OUT_MASK_BIG_DECIMAL;
		}
		
		return printNumber(localizable, number, maskKey);
	}
	
	
	/** turn the BigDecimal number into format ###,###,###,###.######
	 * and remove unnecesary digits.
	 * Returns blank if number is <pre>null</pre>
	 * for example : 12334.00300 -> 12,334.003
	 * @return the string formation of the number
	 * @param number BigDecimal
	 */
	public static String format(BigDecimal number) {
		return format(number, CURRENCY);
	}

	public static String format(double number, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(number);
	}

	public static String format(BigDecimal number, String format) {
		if (number == null) {
			return " ";
		}
		if (format == null)
			format = CURRENCY;
		return format(number.doubleValue(), format);
	}

	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
