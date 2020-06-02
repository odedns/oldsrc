/*
 * Author: yifat har-nof
 * @version $Id: DBUtil.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * This class provides services for db management.
 */
public class DBUtil
{

	/**
	 * This method sets the parameter value into the statement.
	 * @param statement The {@link PreparedStatement} to set the parameter value in.
	 * @param param The parameter value to set in the statement
	 * @param dbIndex The index of the parameter in the statement
	 * @param emptySqlType The sql type in case of an empty value (null)
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static void setStatementParameter(
		PreparedStatement statement,
		Object param,
		int dbIndex)
		throws PersistenceException
	{
		try
		{

			if (param != null)
			{

				if (param instanceof Integer)
				{
					statement.setInt(dbIndex, ((Integer) param).intValue());

				}
				else if (param instanceof Long)
				{
					statement.setLong(dbIndex, ((Long) param).longValue());

				}
				else if (param instanceof Double)
				{
					statement.setDouble(
						dbIndex,
						((Double) param).doubleValue());

				}
				else if (param instanceof String)
				{
					statement.setString(dbIndex, (String) param);

				}
				else if (param instanceof java.util.Date)
				{
					// handle date types

					if (param instanceof java.sql.Timestamp)
					{
						statement.setTimestamp(
							dbIndex,
							(java.sql.Timestamp) param);

					}
					else if (param instanceof java.sql.Time)
					{
						statement.setTime(dbIndex, (java.sql.Time) param);

					}
					else
					{
						java.sql.Date date;
						if (!(param instanceof java.sql.Date))
							date =
								new java.sql.Date(
									((java.util.Date) param).getTime());
						else
							date = (java.sql.Date) param;
						statement.setDate(dbIndex, date);
					}

				}
				else if (param instanceof Character)
				{
					char[] chars = new char[1];
					chars[0] = ((Character) param).charValue();
					statement.setString(dbIndex, new String(chars));

				}
				else if (param.getClass().getName().equals("[C"))
				{
					// char []
					statement.setCharacterStream(
						dbIndex,
						new StringReader(new String((char[]) param)),
						5);

				}
				else
				{
					statement.setObject(dbIndex, param);
				}

			}
			else
			{
				statement.setNull(dbIndex, Types.DATE);
			}
		}
		catch (SQLException sqle)
		{
			throw new PersistenceException(sqle);
		}
	}

	/**
	 * This method sets the parameters values into the statement.
	 * @param statement The {@link PreparedStatement} to set the parameters values in
	 * @param params  The parameters values to set in the statement.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static void setStatementParameters(
		PreparedStatement statement,
		List params)
		throws PersistenceException
	{
		Object param;
		if (params == null)
		{
			return;
		}
		int dbIndex = 1;
		int count = params.size();
		for (int index = 0; index < count; index++)
		{
			param = params.get(index);
			setStatementParameter(statement, param, dbIndex);
			dbIndex++;
		}
	}

	/**
	 * This method returns the Sql type of the given object according to his Java Type.
	 * @param value The value of the object.
	 * @return int The sql type.
	 */
	public static int getObjectSqlType(Object value)
	{
		if (value instanceof Integer)
			return Types.INTEGER;
		else if (value instanceof Long)
			return Types.BIGINT;
		else if (value instanceof Double)
			return Types.DOUBLE;
		else if (value instanceof java.sql.Date)
			return Types.DATE;
		else if (value instanceof String)
			return Types.VARCHAR; ///!!!!! temp
		else if (value instanceof java.sql.Timestamp)
			return Types.TIMESTAMP;
		else if (value instanceof Time)
			return Types.TIME;
		return Types.OTHER;
	}

	/**
	 * This method formats the String value to Sql format.
	 * @param value The value of the String object.
	 * @return String The formatted String.
	 */
	public static String formatString(String value)
	{
		StringBuffer sb = new StringBuffer(256);
		sb.append("'");
		//if (bidi && value != null && value.length() > 0) {
		//    value = new AS400BidiTransform(424).toAS400Layout(value);
		//}
		value = StringFormatterUtil.replace(value, "'", "''");
		sb.append(value);
		sb.append("'");
		return sb.toString();
	}

	/**
	 * This method formats the Boolean value to Sql format.
	 * @param value The value of the Boolean object.
	 * @return String Formatted Boolean.
	 */
	public static String formatBoolean(Boolean value)
	{
		if (value.booleanValue())
			return "1";
		return "0";
	}

	/**
	 * This method formats the TimestampTextModel value to SQL  format.
	 * @param value The value of the TimestampTextModel object.
	 * @return String formatted TimestampTextModel.
	 */
	public static String formatTimestamp(java.sql.Timestamp value)
	{
		StringBuffer sb = new StringBuffer(64);
		sb.append("'");
		sb.append(DateFormatterUtil.format(value, "yyyy-MM-dd-HH.mm.ss."));
		sb.append(
			(
				NumberFormatterUtil.format(
					value.getNanos(),
					"000000000")).substring(
				0,
				6));
		sb.append("'");
		return sb.toString();
	}

	/**
	 * This method formats the Date value to SQL  format.
	 * @param value The value of the Date object.
	 * @return String Formatted Date.
	 */
	public static String formatDate(java.util.Date value)
	{
		StringBuffer sb = new StringBuffer(20);
		sb.append("'");
		sb.append(DateFormatterUtil.format(value, "yyyy-MM-dd"));
		sb.append("'");
		return sb.toString();
	}

	/**
	 * This method formats the Time value to SQL  format.
	 * @param value The value of the Time object.
	 * @return String Formatted Time.
	 */
	public static String formatTime(java.sql.Time value)
	{
		StringBuffer sb = new StringBuffer(20);
		sb.append("'");
		sb.append(DateFormatterUtil.format(value, "HH.mm.ss"));
		sb.append("'");
		return sb.toString();
	}

}
