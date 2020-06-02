/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import com.ness.fw.common.logger.Logger;

/**
 * This class represent the daily scheduler data
 */
public class DailyJobSchduleData extends AbstractFrequencyJobSchduleData
{
	
	/**
	 * check the schedular data
	 * @throws JobException
	 */
	protected void checkParameters() throws JobException
	{			
		checkStartEndDate();
		// time wasn't set
		if (time == null)
		{
			Logger.error(LOGGER_CONTEXT,"date must be supplied for daily frequency");
			throw new JobException("date must be supplied for daily frequency");
		}
	}

	/**
	 * Returns frequency type
	 * @return int
	 */
	public int getType()
	{
		return AbstractJobSchduleData.DAILY_FREQUENCY_TYPE;
	}

}
