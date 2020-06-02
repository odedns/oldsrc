/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: DateFormatterUtil.java,v 1.5 2005/04/06 07:28:47 baruch Exp $
 */
package com.ness.fw.util;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.*;
import com.ness.fw.util.validation.DateValidations;

import java.sql.Timestamp;
import java.text.*;
import java.util.*;

/**
 * A helper class for formatting Date values.
 */
public class DateFormatterUtil
{
	protected static final String FORMAT_DATE = "dd/MM/yyyy";
	protected static final String FORMAT_TIMESTAMP = "dd/MM/yyyy HH:mm:ss";
	protected static final String FORMAT_TIME = "HH:mm:ss";

	public static final long SECOND = 1000;
	public static final long MINUTE = SECOND * 60;
	public static final long HOUR = MINUTE * 60;
	public static final long DAY = HOUR * 24;
	public static final long DAYS_IN_YEAR = 365;
	
	/**
	 * The key for the default out mask of the type.
	 */
	public static String DEFAULT_OUT_MASK_DATE = "date.defaultOutMask";

	/**
	 * The key for the default out mask of the type.
	 */
	public static String DEFAULT_OUT_MASK_TIME = "time.defaultOutMask";

	/**
	 * The key for the default out mask of the type.
	 */
	public static String DEFAULT_OUT_MASK_TIMESTAMP = "timestamp.defaultOutMask";

	/**
	 * Build a Date value from the given String, according to the masks attached 
	 * to the data type. 
	 * @param localizable
	 * @param dateStr The String to build the Object from his value.
	 * @param masksPreffix
	 * @return Date The Date object built from the given String value.
	 * @throws ResourceException
	 * @throws ParseException
	 */
	public static Date build(LocalizedResources localizable, String dateStr, String masksPreffix) throws ResourceException, ParseException
	{
		SimpleDateFormat dateFormat;
		Date result = null;

		char arr[] = dateStr.toCharArray();
		for (int i = 0; i < arr.length; i++)
		{
			char c = arr[i];
			switch (c)
			{
				case '-' :
				case '/' :
				case '\\' :
				case '.' :
				case ':' :
					break;
				default :
					if (Character.isDigit(c))
					{
					}
					else
					{
						throw new java.text.ParseException("Illegal character while parsing Date [" + c + "]", i);
					}
					break;
			}
		}

		for (int i = 0; result == null; i++)
		{
			String mask = localizable.getString(masksPreffix + "." + i);
			dateFormat = new SimpleDateFormat(mask);
			dateFormat.setLenient(false);
			try
			{
				result = dateFormat.parse(dateStr);
			}
			catch (ParseException e)
			{
			}
		}

		return result;
	}

	/**
	 * Returns a formatted TimestampTextModel from the given Date according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given Date.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param date The Date value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted date. 
	 * @throws ResourceException
	 */
	public static String printTimestamp(LocalizedResources localizable, Date date, String maskKey) throws ResourceException
	{
		if (maskKey == null)
		{
			maskKey = DEFAULT_OUT_MASK_TIMESTAMP;
		}

		return printDate(localizable, date, maskKey);
	}

	/**
	 * Returns a formatted Time from the given Date according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given Date.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param date The Date value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted date. 
	 * @throws ResourceException
	 */
	public static String printTime(LocalizedResources localizable, Date date, String maskKey) throws ResourceException
	{
		if (maskKey == null)
		{
			maskKey = DEFAULT_OUT_MASK_TIME;
		}

		return printDate(localizable, date, maskKey);
	}

	/**
	 * Returns a formatted String from the given Date according to the mask.
	 * If the mask was not supplied, get the default out mask for the type. 
	 * If the default mask was not found, returns the toString return value of the 
	 * given Date.
	 * @param localizable The {@link LocalizedResources} to uses its data to get the localized mask.
	 * @param date The Date value to format.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted date. 
	 * @throws ResourceException
	 */
	public static String printDate(LocalizedResources localizable, Date date, String maskKey) throws ResourceException
	{
		String results = "";

		if (date != null)
		{
			if (maskKey == null)
			{
				maskKey = DEFAULT_OUT_MASK_DATE;
			}

			String mask = localizable.getProperty(maskKey);
			if (mask != null)
			{
				SimpleDateFormat format = new SimpleDateFormat(mask);
				results = format.format(date);
			}
			else
			{
				results = date.toString();
			}
		}

		return results;
	}

	public static Date parse(String str, String pattern) throws java.text.ParseException, NullPointerException
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(str);
	}

	/**
	 * Parses text from the beginning of the given string to produce a date using 
	 * the mask "dd/MM/yyyy"
	 * @param str
	 * @return
	 * @throws java.text.ParseException
	 * @throws NullPointerException
	 */
	public static Date parse(String str) throws java.text.ParseException, NullPointerException
	{
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.parse(str);
	}

	/**
	 * Parses text from the beginning of the given string to produce a date using 
	 * the default mask date.defaultOutMask from property file
	 * @param localizedResources
	 * @param str
	 * @return
	 * @throws java.text.ParseException
	 * @throws NullPointerException
	 */
	public static Date parseDate(LocalizedResources localizedResources,  String str) throws java.text.ParseException, NullPointerException
	{
		SimpleDateFormat format = new SimpleDateFormat(localizedResources.getProperty(MaskConstants.DATE_MASK));
		return format.parse(str);
	}

	/**
	 * Parses text from the beginning of the given string to produce a date using 
	 * the default mask time.defaultOutMask from property file
	 * @param localizedResources
	 * @param str
	 * @return
	 * @throws java.text.ParseException
	 * @throws NullPointerException
	 */
	public static Date parseTime(LocalizedResources localizedResources,  String str) throws java.text.ParseException, NullPointerException
	{
		SimpleDateFormat format = new SimpleDateFormat(localizedResources.getProperty(MaskConstants.TIME_MASK));
		return format.parse(str);
	}

	/**
	 * Parses text from the beginning of the given string to produce a date using 
	 * the default mask timestamp.defaultOutMask from property file
	 * @param localizedResources
	 * @param str
	 * @return
	 * @throws java.text.ParseException
	 * @throws NullPointerException
	 */
	public static Date parseTimestamp(LocalizedResources localizedResources,  String str) throws java.text.ParseException, NullPointerException
	{
		SimpleDateFormat format = new SimpleDateFormat(localizedResources.getProperty(MaskConstants.TIMESTAMP_MASK));
		return format.parse(str);
	}


	public static Timestamp getTimestamp(String s)
	{
		int nanos = Integer.parseInt(s.substring(s.length() - 9));
		long seconds = Long.parseLong(s.substring(0, s.length() - 9));
		Timestamp ts = new Timestamp(seconds);
		ts.setNanos(nanos);
		return ts;
	}

	public static String getNanos(Timestamp ts)
	{
		StringBuffer nanos = new StringBuffer(Integer.toString(ts.getNanos()));
		while (nanos.length() < 9)
		{
			nanos.insert(0, '0');
		}

		String s = "" + ts.getTime() + nanos.toString();
		return s;
	}

	public static String format(Date date)
	{
		return format(date, FORMAT_DATE);
	}

	public static String format(Date date, String pattern)
	{
		String formatted = null;
		if (date != null)
		{
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			formatted = format.format(date);
		}
		return formatted;
	}

	/**
	 * create new Date from the date given with new reference.
	 * @param date
	 * @return new cloned Date
	 */
	public static Calendar cloneDate(Calendar date)
	{
		Calendar newDate = Calendar.getInstance();
		newDate.setTimeInMillis(date.getTimeInMillis());
		return newDate;
	}

	/**
	 * create new Date from the date given with new reference.
	 * @param date
	 * @return new cloned Date
	 */
	public static Calendar dateToCalendar(Date date)
	{
		if (date == null)
		{
			return null;
		}
		Calendar newDate = Calendar.getInstance();
		newDate.setTimeInMillis(date.getTime());
		return newDate;
	}

	/**
	 * check if both date1 and date2 are on the same month.
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameMonth(Calendar date1, Calendar date2)
	{
		return (date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH));
	}

	/**
	 * check if both date1 is after date2 and they are not in the same month
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isMonthAfter(Calendar date1, Calendar date2)
	{
		return (date1.after(date2) && !isSameMonth(date1, date2));
	}

	/**
	 * Calculates (toDate - fromDate) and returns result in years. 
	 * @param fromDate - the earliest date
	 * @param toDate - the latest date
	 * @return (toDate - fromDate) in months
	 */
	public static int getDifferenceInYears(Date fromDate, Date toDate)
	{
		int fromYear = DateFormatterUtil.dateToCalendar(fromDate).get(Calendar.YEAR);
		int toYear = DateFormatterUtil.dateToCalendar(toDate).get(Calendar.YEAR);
		return (toYear - fromYear);
	}

	/**
	 * Calculates (toDate - fromDate) and returns result in days. 
	 * @param fromDate
	 * @param toDate
	 * @return (toDate - fromDate) in days
	 */
	public static long getDifferenceInDays(Date fromDate, Date toDate)
	{
		return getDifferenceInDays(fromDate, toDate, true);
	}


	/**
	 * Calculates (toDate - fromDate) and returns result in days. 
	 * @param fromDate
	 * @param toDate
	 * @return (toDate - fromDate) in days
	 */
	public static long getDifferenceInDays(Date fromDate, Date toDate, boolean ignoreTime)
	{
		if (ignoreTime)
		{
			fromDate = DateValidations.resetDate(fromDate,true);
			toDate = DateValidations.resetDate(toDate,true);
		}

		//Get from date in days
		long fromInDays = fromDate.getTime() / DAY;

		//Get end date in days
		long toInDays = toDate.getTime() / DAY;

		//Calculate different in days
		long diff = toInDays - fromInDays;

		return diff;
	}


	/**
	 * Calculates (toDate - fromDate) and returns result in months. 
	 * @param fromDate - the earliest date
	 * @param toDate - the latest date
	 * @return (toDate - fromDate) in months
	 */
	public static int getDifferenceInMonths(Date fromDate, Date toDate)
	{
		return getDifferenceInMonths(dateToCalendar(fromDate), dateToCalendar(toDate));
	}

	/**
	 * Calculates (toDate - fromDate) and returns result in months. 
	 * @param fromDate - the earliest date
	 * @param toDate - the latest date
	 * @return (toDate - fromDate) in months
	 */
	public static int getDifferenceInMonths(Calendar fromDate, Calendar toDate)
	{
		int fromYear = fromDate.get(Calendar.YEAR);
		int toYear = toDate.get(Calendar.YEAR);
		//the difference of month in a year.
		int yearDifference = (toYear - fromYear) * 12;

		int fromMonth = fromDate.get(Calendar.MONTH);
		int toMonth = toDate.get(Calendar.MONTH);
		//the difference of month in a year.
		int monthDifference = toMonth - fromMonth;
		return (yearDifference + monthDifference);
	}

	/**
	 * get the minimum date from date1, date2
	 * if one of the dates is null, return the other one.
	 * if both of them are null - return null;
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date getMinimumDate(Date date1, Date date2)
	{
		if (date1 == null)
		{
			return date2;
		}
		else if (date2 == null)
		{
			return date1;
		}
		if (date1.before(date2))
		{
			return date1;
		}
		else
		{
			return date2;
		}
	}

	/**
	 * get the maximum date from date1, date2
	 * if one of the dates is null, return the other one.
	 * if both of them are null - return null;
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Date getMaximumDate(Date date1, Date date2)
	{
		if (date1 == null)
		{
			return date2;
		}
		else if (date2 == null)
		{
			return date1;
		}
		if (date1.after(date2))
		{
			return date1;
		}
		else
		{
			return date2;
		}
	}

	/**
	 * check if the given date (checkDate) is within thw limits of the start and end dates.
	 * if one of the dates (startDate or endDate) is null, the condition will not be checked for it.
	 * @param checkDate
	 * @param startDate
	 * @param endDate
	 * @return
	*/
	public static boolean isDateWithinLimits(Date checkDate, Date startDate, Date endDate)
	{
		boolean result = true;
		if (startDate != null)
		{
			result = startDate.before(checkDate);
		}

		if (endDate != null)
		{
			result = result && endDate.after(checkDate);
		}

		return result;
	}

}
