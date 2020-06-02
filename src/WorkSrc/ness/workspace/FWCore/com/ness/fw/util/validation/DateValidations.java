/*
 * Created on 02/11/2004
 * Created By bhizkya
 */
package com.ness.fw.util.validation;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 
 */
public class DateValidations
{

	/**
	 * Check if date1 equals to date2 (compare year month and day only)
	 * @param date1 
	 * @param date2 
	 * @return boolean True if date1 is equals to date2.  
	 */
	public static boolean isEquals(Date date1, Date date2, boolean ignoreTime)
	{
		date1 = resetDate(date1,ignoreTime);	
		date2 = resetDate(date2,ignoreTime);

		if (date1 != null && date2 != null)
		{
			if (date1.equals(date2))
			{
				return true;
			}
		}
		else if (date1 == null || date2 == null)
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Check if the value of date1 is before or equal the value of date2
	 * @param date1
	 * @param date2
	 * @return boolean.
	 */
	public static boolean isBeforeOrEqual(Date date1, Date date2,boolean ignoreTime)
	{
		date1 = resetDate(date1,ignoreTime);	
		date2 = resetDate(date2,ignoreTime);

		boolean returnValue = false;
		if (date1 != null && date2 != null)
		{
			if (!date1.after(date2))
			{
				returnValue =  true;
			}
		}
		else
		{
			returnValue =  true;
		}

		return returnValue;
	}
	
	/**
	 * Check if the value of date1 is after or equal the value of date2
	 * @param date1
	 * @param date2
	 * @return boolean.
	 */
	public static boolean isAfterOrEqual(Date date1, Date date2,boolean ignoreTime)
	{
		date1 = resetDate(date1,ignoreTime);	
		date2 = resetDate(date2,ignoreTime);

		boolean returnValue = false;
		if (date1 != null && date2 != null)
		{
			if (!date1.before(date2))
			{
				returnValue =  true;
			}
		}
		else
		{
			returnValue =  true;
		}

		return returnValue;
	}



	private static Date resetDate(Date date)
	{
		Calendar calendar = Calendar.getInstance();
	
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);

		return calendar.getTime();
	}
	
	public static Date resetDate(Date date, boolean ignoreTime)
	{
		if (ignoreTime)
		{
			if (date != null)
			{
				date = resetDate(date);
			}
		}
		
		return date;	
	}
	
}
