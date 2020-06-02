/*
 * Created on: 15/10/2003
 * Author: baruch hizkya
 * @version $Id: LoggerParameterizedString.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.logger.zs;

/**
 * Instance of the class can be passed as <code>parameterizedString</code> to logger while debugging
 *
 * <br><br>
 * For example:
 * <br><br>
 * <code>
 * LoggerParameterizedString lps = LoggerParameterizedString.createInstance();
 * lps.setString("The first user Id is: ? and the second ?");
 * lps.setValue(1, "12345678");
 * lps.setValue(2, "87654321");
 *
 * Logger.error("any-context", lps);
 * </code><br><br>
 */
public abstract class LoggerParameterizedString
{
	/**
	 * Factory method
	 *
	 * @return new <code>LoggerParameterizedString</code> instance
	 */
	public static LoggerParameterizedString createInstance()
	{
		return new LoggerParameterizedStringImpl();
	}

	/**
	 * Setter for parameterized String
	 *
	 * @param s parameterized String
	 */
	public abstract void setString(String s);

	/**
	 * Setter for placeholder value
	 *
	 * @param key
	 * @param value
	 */
	public abstract void setValue(int key, String value);

	/**
	 * Getter for constructed String
	 *
	 * @return constructed string
	 */
	abstract String getString();
}
