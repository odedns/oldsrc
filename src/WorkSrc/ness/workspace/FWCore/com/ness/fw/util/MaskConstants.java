/*
 * Created on 01/07/2004
 * Author: baruch hizkya
 * @version $Id: MaskConstants.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

/**
 * Holds the contsinats of the system masks for formatting.
 */
public class MaskConstants
{
	/** A constant used for date format
	 */
	public final static String DATE_MASK = "date.defaultOutMask";

	/** A constant used for date month format
	 */
	public final static String DATE_MONTH_MASK = "date.month_mask";

	/** A constant used for date year format
	 */
	public final static String DATE_YEAR_MASK = "date.year_month";

	/** A constant used for time format
	 */
	public final static String TIME_MASK = "time.defaultOutMask";

	/** A constant used for timestamp format
	 */
	public final static String TIMESTAMP_MASK = "timestamp.defaultOutMask";

	/** A constant used for numeric format
	 */
	public final static String NUMERIC_MASK = "number.defaultOutMask";

	/** A constant used for curreny format
	 */
	public final static String CURRENCY_MASK = "currency.defaultOutMask";

	/** A constant used for decimal currency format
	 */
	public final static String CURRENCY_DECIMAL_MASK = "currency.decimalDefaultOutMask";

}
