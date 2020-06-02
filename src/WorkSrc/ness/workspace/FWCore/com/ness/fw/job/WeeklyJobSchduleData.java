/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import com.ness.fw.common.logger.Logger;

/**
 * This class holds the scheduler data
 */
public class WeeklyJobSchduleData extends AbstractFrequencyJobSchduleData
{

	/** A constant indicating sunday. */
	public static final int SUNDAY = 0;

	/** A constant indicating monday. */
	public static final int MONDAY = 1;

	/** A constant indicating tuesday. */
	public static final int TUESDAY = 2;

	/** A constant indicating wednesday. */
	public static final int WEDNESDAY = 3;

	/** A constant indicating thursday. */
	public static final int THURSDAY = 4;

	/** A constant indicating friday. */
	public static final int FRIDAY = 5;

	/** A constant indicating sautrday. */
	public static final int SAUTRDAY = 6;

	private boolean[] frequencyDays;
		
	/**
	 * check the schedular data
	 * @throws JobException
	 */
	protected void checkParameters() throws JobException
	{			
		checkStartEndDate();
		if (time == null)
		{
			Logger.error(LOGGER_CONTEXT,"time must be supplied for weekly frequency");
			throw new JobException("time must be supplied for weekly frequency");
		}	

		if (!checkDaysExistance())
		{
			Logger.error(LOGGER_CONTEXT,"date and days to run  must be supplied for weekly frequency");
			throw new JobException("date and days to run  must be supplied for weekly frequency");
		}		
	}

	/**
	 * Adding a day to the weekly days
	 * @param day
	 */
	public void addDayOfWeek(int day)
	{
		if (frequencyDays == null)
		{
			frequencyDays = new boolean[7];
		}
		
		frequencyDays[day] = true;
	}

	/**
	 * check if days to run in WEEKLY type were set
	 * @return
	 */
	private boolean checkDaysExistance()
	{
		boolean exists = false;
		if (frequencyDays != null)
		{
			// Looping over the array. if found a least one, returning true
			for (int i=0; i<frequencyDays.length; i++)
			{
				if (frequencyDays[i])
				{
					exists = true;
					break;
				}
			}
		}
		
		return exists;
	}
	
	/**
	 * @param bs
	 */
	protected void setFrequencyDays(boolean[] frequencyDays)
	{
		this.frequencyDays = frequencyDays;
	}

	/**
	 * get frequencyDays
	 * @return
	 */
	protected boolean[] getFrequencyDays()
	{
		return frequencyDays;
	}

	/**
	 * get the frequenct type of the job
	 */
	public int getType()
	{
		return AbstractJobSchduleData.WEEKLY_FREQUENCY_TYPE;
	}
}
