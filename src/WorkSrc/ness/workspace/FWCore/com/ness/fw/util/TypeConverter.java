/*
 * Created on 20/11/2003
 * Author: yifat har-nof
 * @version $Id: TypeConverter.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A Helper class provide common convertion methods. 
 */
public class TypeConverter
{

	public static final String FORMAT_DATE = "ddMMyyyy";
	public static final String FORMAT_TIMESTAMP = "ddMMyyyy HH:mm:ss";
	public static final String FORMAT_TIME = "HH:mm:ss";

	// data type constants
	public static final int TYPE_BIG_DECIMAL = 1;
	public static final int TYPE_INTEGER = 2;
	public static final int TYPE_LONG = 3;
	public static final int TYPE_DOUBLE = 4;
	public static final int TYPE_FLOAT = 5;
	public static final int TYPE_STRING = 6;
	public static final int TYPE_DATE = 7;
	public static final int TYPE_TIMESTAMP = 8;
	public static final int TYPE_TIME = 9;
	public static final int TYPE_BOOLEAN = 10;
	
	// data type class names
	public static final Class BIG_DECIMAL_CLASS = BigDecimal.class;
	public static final Class INTEGER_CLASS = Integer.class;
	public static final Class LONG_CLASS = Long.class;
	public static final Class DOUBLE_CLASS = Double.class;
	public static final Class FLOAT_CLASS = Float.class;
	public static final Class STRING_CLASS = String.class;
	public static final Class DATE_CLASS = Date.class;
	public static final Class TIMESTAMP_CLASS = Timestamp.class;
	public static final Class TIME_CLASS = Time.class;
	public static final Class BOOLEAN_CLASS = Boolean.class;
	


//	---------------- Integer converters ----------
	
	public static Integer convertToInteger (String value)
	{
		return value == null ? null : new Integer(value); 
	}

	public static Integer convertToInteger (Number value)
	{
		return value == null ? null : new Integer(value.intValue()); 
	}

	public static Integer convertToInteger (Boolean value)
	{
		return (value != null && value.booleanValue()) ? new Integer(1) : new Integer(0); 
	}

	public static Integer convertToInteger (Object value, int fromType, String fromMask) throws TypeConvertionException 
	{
		Integer newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToInteger((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToInteger((String)value);
		}
		else if(isBooleanType(fromType))
		{
			newValue = TypeConverter.convertToInteger((Boolean)value);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + INTEGER_CLASS.getName() + "].");
		}
		return newValue;
	}


//	---------------- Long converters ----------

	public static Long convertToLong (String value)
	{
		return value == null ? null : new Long(value); 
	}

	public static Long convertToLong (Number value)
	{
		return value == null ? null : new Long(value.longValue()); 
	}

	public static Long convertToLong (Date value)
	{
		return value == null ? null : new Long(value.getTime()); 
	}

	public static Long convertToLong (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		Long newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToLong((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToLong((String)value);
		}
		else if(isDateType(fromType))
		{
			newValue = TypeConverter.convertToLong((Date)value);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + LONG_CLASS.getName() + "].");
		}
		return newValue;
	}


//	---------------- Double converters ----------

	public static Double convertToDouble (String value)
	{
		return value == null ? null : new Double(value); 
	}

	public static Double convertToDouble (Number value)
	{
		return value == null ? null : new Double(value.doubleValue()); 
	}
	
	public static Double convertToDouble (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		Double newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToDouble((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToDouble((String)value);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + DOUBLE_CLASS.getName() + "].");
		}
		return newValue;
	}
	

//	---------------- Float converters ----------

	public static Float convertToFloat (String value)
	{
		return value == null ? null : new Float(value); 
	}

	public static Float convertToFloat (Number value)
	{
		return value == null ? null : new Float(value.floatValue()); 
	}

	public static Float convertToFloat (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		Float newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToFloat((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToFloat((String)value);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + FLOAT_CLASS.getName() + "].");
		}
		return newValue;
	}

//	---------------- BigDecimal converters ----------

	public static BigDecimal convertToBigDecimal (String value)
	{
		return value == null ? null : new BigDecimal(value); 
	}

	public static BigDecimal convertToBigDecimal (Number value)
	{
		return value == null ? null : new BigDecimal(value.doubleValue()); 
	}

	public static BigDecimal convertToBigDecimal (Object value, int fromType, String fromMask) throws TypeConvertionException 
	{
		BigDecimal newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToBigDecimal((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToBigDecimal((String)value);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + BIG_DECIMAL_CLASS.getName() + "].");
		}
		return newValue;
	}


//	---------------- Boolean converters ----------

	public static Boolean convertToBoolean (String value)
	{
		boolean newValue = false;
		
		if(value != null 
			&& (value.trim().toLowerCase().equals("true") 
				|| value.trim().equals("1") ))
			newValue = true;
			
		return new Boolean(newValue);
	}

	public static Boolean convertToBoolean (Number value)
	{
		boolean newValue = false;
		
		if(value != null & value.intValue() == 1)
			newValue = true;
			
		return new Boolean(newValue);
	}

	public static Boolean convertToBoolean (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		Boolean newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToBoolean((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToBoolean((String)value);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + BOOLEAN_CLASS.getName() + "].");
		}
		return newValue;
	}

//	---------------- String converters ----------

	public static String convertToString (Number value)
	{
		return value == null ? null : value.toString(); 
	}

	public static String convertToString (Boolean value)
	{
		return value == null ? null : value.toString(); 
	}

	public static String convertToString (Date value)
	{
		return value == null ? null : value.toString(); 
	}

	public static String convertToString (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		String newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToString((Number)value);
		}
		else if(isBooleanType(fromType))
		{
			newValue = TypeConverter.convertToString((Boolean)value);
		}
		else if(isDateType(fromType))
		{
			newValue = TypeConverter.convertToString((Date)value);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + STRING_CLASS.getName() + "].");
		}
		return newValue;
	}

//	---------------- Date converters ----------

	private static Date convertStringToDate (String value, String mask) throws TypeConvertionException
	{
		Date newValue = null;
		try
		{
			if(value != null)
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat(mask);
				dateFormat.setLenient(false);
				newValue = dateFormat.parse(value);
			}
		}
		catch (ParseException e)
		{
			throw new TypeConvertionException("The format of the value is not supported");
		}
		return newValue;
	}

	public static Date convertToDate (String value, String fromMask) throws TypeConvertionException 
	{
		if(fromMask == null)
			fromMask = FORMAT_DATE;
		return convertStringToDate(value, fromMask);
	}

	public static Date convertToDate (Long value) 
	{
		return value == null ? null : new Date(value.longValue()); 
	}

	public static Date convertToDate (Date value) 
	{
		return value == null ? null : new Date(value.getTime()); 
	}

	public static Date convertToDate (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		Date newValue = null;
		if(isDateType(fromType))
		{
			newValue = TypeConverter.convertToDate((Date)value);
		}
		else if(isLongType(fromType))
		{
			newValue = TypeConverter.convertToDate((Long)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToDate((String)value, fromMask);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + DATE_CLASS.getName() + "].");
		}
		return newValue;
	}

//	---------------- Timestamp converters ----------

	public static Timestamp convertToTimestamp (String value, String fromMask) throws TypeConvertionException 
	{
		if(fromMask == null)
			fromMask = FORMAT_TIMESTAMP;
		return new Timestamp(convertStringToDate(value, fromMask).getTime());
	}

	public static Timestamp convertToTimestamp (Number value) 
	{
		return value == null ? null : new Timestamp(value.longValue()); 
	}

	public static Timestamp convertToTimestamp (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		Timestamp newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToTimestamp((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToTimestamp((String)value, fromMask);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + TIMESTAMP_CLASS.getName() + "].");
		}
		return newValue;
	}

//	---------------- Time converters ----------

	public static Time convertToTime (String value, String fromMask) throws TypeConvertionException 
	{
		if(fromMask == null)
			fromMask = FORMAT_TIME;
		return new Time(convertStringToDate(value, fromMask).getTime());
	}

	public static Time convertToTime (Number value) 
	{
		return value == null ? null : new Time(value.longValue()); 
	}

	public static Time convertToTime (Object value, int fromType, String fromMask) throws TypeConvertionException
	{
		Time newValue = null;
		if(isNumericType(fromType))
		{
			newValue = TypeConverter.convertToTime((Number)value);
		}
		else if(isStringType(fromType))
		{
			newValue = TypeConverter.convertToTime((String)value, fromMask);
		}
		else
		{
			throw new TypeConvertionException("Unsupported type convertion. from type [" + getTypeClassName(fromType) + 
				"] to type [" + TIME_CLASS.getName() + "].");
		}
		return newValue;
	}


//	---------------- End converters ----------


	public static boolean isNumericType (int type)
	{
		if(type == TYPE_INTEGER
		|| type == TYPE_BIG_DECIMAL
		|| type == TYPE_LONG
		|| type == TYPE_DOUBLE
		|| type == TYPE_FLOAT)
		{
			return true;
		}
		return false; 
	}

	public static boolean isLongType (int type)
	{
		if(type == TYPE_LONG)
		{
			return true;
		}
		return false; 
	}

	public static boolean isDateType (int type)
	{
		if(type == TYPE_DATE
		|| type == TYPE_TIMESTAMP
		|| type == TYPE_TIME)
		{
			return true;
		}
		return false; 
	}

	public static boolean isStringType (int type)
	{
		if(type == TYPE_STRING)
		{
			return true;
		}
		return false; 
	}

	public static boolean isBooleanType (int type)
	{
		if(type == TYPE_BOOLEAN)
		{
			return true;
		}
		return false; 
	}

	public static String getTypeClassName (int type)
	{
		Class typeClass = getTypeClass(type);
		if(typeClass != null)
		{
			return typeClass.getName();
		}
		return null;
	}
	
	public static Class getTypeClass (int type)
	{
		Class typeClass = null;
		switch (type)
		{
			case TYPE_BIG_DECIMAL :
				typeClass = BIG_DECIMAL_CLASS;
				break;
			case TYPE_INTEGER :
				typeClass = INTEGER_CLASS;
				break;
			case TYPE_LONG :
				typeClass = LONG_CLASS;
				break;
			case TYPE_DOUBLE :
				typeClass = DOUBLE_CLASS;
				break;
			case TYPE_FLOAT :
				typeClass = FLOAT_CLASS;
				break;
			case TYPE_STRING :
				typeClass = STRING_CLASS;
				break;
			case TYPE_DATE :
				typeClass = DATE_CLASS;
				break;
			case TYPE_TIMESTAMP :
				typeClass = TIMESTAMP_CLASS;
				break;
			case TYPE_TIME :
				typeClass = TIME_CLASS;
				break;
			case TYPE_BOOLEAN :
				typeClass = BOOLEAN_CLASS;
				break;
		}
		
		return typeClass;
	}

	public static Object convertValueType (Object value, int convertFrom, int convertTo, String fromMask) throws TypeConvertionException
	{
		Object newValue = null; 
		switch (convertTo)
		{
			case TYPE_BIG_DECIMAL : 
				newValue = convertToBigDecimal(value, convertFrom, fromMask);
				break;

			case TYPE_INTEGER : 
				newValue = convertToInteger(value, convertFrom, fromMask);
				break;

			case TYPE_LONG : 
				newValue = convertToLong(value, convertFrom, fromMask);
				break;

			case TYPE_DOUBLE : 
				newValue = convertToDouble(value, convertFrom, fromMask);
				break;

			case TYPE_FLOAT : 
				newValue = convertToFloat(value, convertFrom, fromMask);
				break;

			case TYPE_STRING : 
				newValue = convertToString(value, convertFrom, fromMask);
				break;

			case TYPE_DATE : 
				newValue = convertToDate(value, convertFrom, fromMask);
				break;

			case TYPE_TIMESTAMP : 
				newValue = convertToTimestamp(value, convertFrom, fromMask);
				break;
				
			case TYPE_TIME : 
				newValue = convertToTime(value, convertFrom, fromMask);
				break;

			case TYPE_BOOLEAN : 
				newValue = convertToBoolean(value, convertFrom, fromMask);
				break;

		}
		return newValue;
	}

}
