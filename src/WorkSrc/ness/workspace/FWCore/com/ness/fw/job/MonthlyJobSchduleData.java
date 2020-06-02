/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import com.ness.fw.common.logger.Logger;

/**
 * This class holds the scheduler data
 */
public class MonthlyJobSchduleData extends AbstractFrequencyJobSchduleData
{
	private int day = -1;
	private boolean startInFirstDayOfMonth;
	private boolean startInEndDayOfMonth;
	
	/**
	 * check the schedular data
	 * @throws JobException
	 */
	protected void checkParameters() throws JobException
	{			
		checkStartEndDate();
		// day wasn't set
		if (day == -1)
		{
			Logger.error(LOGGER_CONTEXT,"day must be supplied for monthly frequency");
			throw new JobException("day must be supplied for monthly frequency");
		}

		// time wasn't set
		if (time == null)
		{
			Logger.error(LOGGER_CONTEXT,"time must be supplied for monthly frequency");
			throw new JobException("time must be supplied for monthly frequency");
		}

		if (startInFirstDayOfMonth && startInEndDayOfMonth)
		{
			Logger.error(LOGGER_CONTEXT,"job cannot be scheduled at the start and the end of the month togheter");
			throw new JobException("job cannot be scheduled at the start and the end of the month togheter");					
		}

	}
	
	/**
	 * get the scheduler param, that checks if to run in the end day of the month
	 * @return
	 */
	public boolean getStartInEndDayOfMonth()
	{
		return startInEndDayOfMonth;
	}

	/**
	 * get the scheduler param, that checks if to run in the first day of the month
	 * @return
	 */
	public boolean getStartInFirstDayOfMonth()
	{
		return startInFirstDayOfMonth;
	}

	/**
	 * set the scheduler to run in the end day of the month
	 * @return
	 */
	public void setStartInEndDayOfMonth(boolean startInEndDayOfMonth)
	{
		this.startInEndDayOfMonth = startInEndDayOfMonth;
	}

	/**
	 * set the scheduler to run in the first dat of the month
	 * @return
	 */
	public void setStartInFirstDayOfMonth(boolean startInFirstDayOfMonth)
	{
		this.startInFirstDayOfMonth = startInFirstDayOfMonth;
	}

	/**
	 * get the frequenct type of the job
	 */
	public int getType()
	{
		return AbstractJobSchduleData.MONTHLY_FREQUENCY_TYPE;
	}

	/**
	 * @return
	 */
	public int getDay()
	{
		return day;
	}

	/**
	 * @param i
	 */
	public void setDay(int day) throws JobException
	{
		if (day <0 || day>31)
		{
			throw new JobException("day must between 1..31");
		}
		this.day = day;
	}
}
