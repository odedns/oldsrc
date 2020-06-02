/*
 * Created on 23/05/2004
 * Created By yharnof
 */
package com.ness.fw.flower.common.validation;

import java.util.Date;

import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;

/**
 * 
 * 
 */
public class DateChecks
{

	/**
	 * Check if the value of the field is before the value of date.
	 * @param ctx The Context to get the field from.
	 * @param contextFieldName The field name to get.
	 * @param date The date to check with the field
	 * @return boolean True if the date in the context is before the given date.  
	 * @throws ContextException
	 */
	public static boolean isBefore(Context ctx, String fieldName, Date date) throws ContextException
	{
		Date ctxDate = ctx.getDate(fieldName);

		if (ctxDate != null)
		{
			return ctxDate.before(date);
		}

		return false;
	}

	/**
	 * Check if the value of the field is after the value of date.
	 * @param ctx The Context to get the field from.
	 * @param contextFieldName The field name to get.
	 * @param date The date to check with the field
	 * @return boolean True if the date in the context is after the given date.  
	 * @throws ContextException
	 */
	public static boolean isAfter(Context ctx, String fieldName, Date date) throws ContextException
	{
		Date ctxDate = ctx.getDate(fieldName);

		if (ctxDate != null)
		{
			return ctxDate.after(date);
		}

		return false;
	}

	/**
	 * Check if the value of the field is before or equal the value of date.
	 * @param ctx The Context to get the field from.
	 * @param contextFieldName The field name to get.
	 * @param date The date to check with the field
	 * @return boolean True if the date in the context is after the given date.  
	 * @throws ContextException
	 */
	public static boolean IsBeforeOrEqual(Context ctx, String fieldName, Date date) throws ContextException
	{
		Date ctxDate = ctx.getDate(fieldName);

		if (ctxDate != null)
		{
			return !ctxDate.after(date);
		}

		return false;
	}

	/**
	 * Check if the value of the field is between the values of date1 & date2
	 * @param ctx The Context to get the field from.
	 * @param contextFieldName The field name to get.
	 * @param date1 The minimal date to check with the field.
	 * @param date2 The maximal date to check with the field.
	 * @return boolean True if the date in the context is between the given dates.  
	 * @throws ContextException
	 */
	public static boolean isBetween(Context ctx, String fieldName, Date date1, Date date2) throws ContextException
	{
		Date ctxDate = ctx.getDate(fieldName);

		if (ctxDate != null)
		{
			return ctxDate.after(date1) && ctxDate.before(date2);
		}

		return false;
	}

	
	/**
	 * Check if the value of the field is equals to the value of date.
	 * @param ctx The Context to get the field from.
	 * @param contextFieldName The field name to get.
	 * @param date The date to check with the field
	 * @return boolean True if the date in the context is equals to the given date.  
	 * @throws ContextException
	 */
	public static boolean isEquals(Context ctx, String fieldName, Date date) throws ContextException
	{
		Date ctxDate = ctx.getDate(fieldName);

		if (ctxDate != null)
		{
			return ctxDate.equals(date);
		}

		return false;
	}



}
