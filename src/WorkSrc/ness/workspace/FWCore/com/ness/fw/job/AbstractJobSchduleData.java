/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

/**
 * This class represent an abstract scheduler data
 */
public abstract class AbstractJobSchduleData
{

	public static final String LOGGER_CONTEXT = "JOBS";


	/** A constant used to set the scheduler frequency to daily type. */
	public static final int DAILY_FREQUENCY_TYPE = 1;

	/** A constant used to set the scheduler frequency to weekly type. */
	public static final int WEEKLY_FREQUENCY_TYPE = 2;

	/** A constant used to set the scheduler frequency to monthly type. */
	public static final int MONTHLY_FREQUENCY_TYPE = 3;

	/** A constant used to set the scheduler frequency to once type. */
	public static final int ONCE_FREQUENCY_TYPE = 4;

	/** A constant used to set the scheduler frequency to now type. */
	public static final int NOW_FREQUENCY_TYPE = 5;

	protected abstract void checkParameters() throws JobException;	
	public abstract int getType();

}
