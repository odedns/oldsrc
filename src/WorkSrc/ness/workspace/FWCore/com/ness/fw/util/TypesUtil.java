/*
 * Created on 20/11/2003
 * Author: yifat har-nof
 * @version $Id: TypesUtil.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import java.math.BigDecimal;

/**
 * A Helper class provide common convertion methods. 
 */
public class TypesUtil
{
	public static Integer convertBooleanToNumber (boolean value)
	{
		if(value)
			return new Integer(1);
		return new Integer(0);
	}
	
	public static Integer convertBooleanToNumber (Boolean value)
	{
		if(value != null)
			return convertBooleanToNumber(value.booleanValue());
		return new Integer(0);
	}
	
	public static boolean convertNumberToBoolean (int value)
	{
		if(value == 1)
			return true;
		return false;
	}

	public static boolean convertNumberToBoolean (Number value)
	{
		if(value != null && value.shortValue() == 1)
			return true;
		return false;
	}

	public static boolean convertNumberToBoolean (Object value)
	{
		return convertNumberToBoolean((Number) value);
	}

	public static Integer convertNumberToInteger (Object value)
	{
		if(value != null)
		{
			return new Integer(((Number)value).intValue());
		}
		return null;
	}

	public static Double convertNumberToDouble (Object value)
	{
		if(value != null)
		{
			return new Double(((Number)value).doubleValue());
		}
		return null;
	}

	public static BigDecimal handleEmptyBigDecimal (BigDecimal value)
	{
		if(value != null)
			return value;
		return new BigDecimal(0);
	}

	public static Integer handleEmptyInteger (Integer value)
	{
		if(value != null)
			return value;
		return new Integer(0);
	}


	public static String handleEmptyString (String value)
	{
		if(value != null)
			return value;
		return "";
	}

	public static int convertIntegerToInt (Integer value)
	{
		if(value != null)
			return value.intValue();
		return 0;
	}
	
	public static Integer convertIntToInteger (int value)
	{
		if(value != 0)
			return new Integer(value);
		return null;
	}

	public static boolean convertStringToBoolean (String value)
	{
		if(value != null && value.toLowerCase().equals("true"))
			return true;
		return false;
	}

	public static Integer convertStringToInteger (Object value)
	{
		if(value != null)
		{
			return new Integer((String)value);
		}
		return null;
	}

	
}
