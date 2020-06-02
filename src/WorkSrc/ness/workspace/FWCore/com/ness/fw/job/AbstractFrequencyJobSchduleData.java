/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.util.Date;
import com.ness.fw.common.logger.Logger;

/**
 * This class represent an abstract frequency scheduler data
 */
public abstract class AbstractFrequencyJobSchduleData extends AbstractJobSchduleData
{
	private Date lastRuntime;
	private Date nextDate;
	private Date startDate;
	private Date endDate;
	protected JobRunningTime time;


	/**
	 * set the last runtime
	 * @param lastRuntime
	 */
	public void setLastRuntime(Date lastRuntime)
	{
		this.lastRuntime = lastRuntime;
	}

	/**
	 * getlast runtime
	 * @return
	 */
	protected Date getLastRuntime()
	{
		return lastRuntime;
	}

	/**
	 * get nextDate
	 * @return
	 */
	public Date getNextDate()
	{
		return nextDate;
	}

	/**
	 * set nextDate
	 * @param nextDate
	 */
	public void setNextDate(Date nextDate)
	{
		this.nextDate = nextDate;
	}

	/**
	 * return the end date of the job
	 * @return
	 */
	public Date getEndDate()
	{
		return endDate;
	}

	/**
	 * return the start date of the job
	 * @return
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * set the end date of the job
	 * @param date
	 */
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * set the start date of the job
	 * @param date
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * check the relationship between start & end date 
	 * @throws JobException
	 */
	protected void checkStartEndDate() throws JobException
	{
		Date now = JobUtil.getCurrentDate();
		// startDate < current time
		if (startDate != null && startDate.before(now))
		{
			Logger.error(LOGGER_CONTEXT,"start date cannot be less than current date");
			throw new JobException("start date cannot be less than current date");
		}

		// endDate < current time
		if (endDate != null && endDate.before(now))
		{
			Logger.error(LOGGER_CONTEXT,"end date cannot be less than current date");
			throw new JobException("end date cannot be less than current date");
		}
		
		// startDate > endDate
		if (startDate != null && endDate != null)
		{
			if (startDate.after(endDate))
			{
				Logger.error(LOGGER_CONTEXT,"start date cannot be greater than end date");
				throw new JobException("start date cannot be greater than end date");
			}
		}
	}

	/**
	 * get the time which the job will run
	 * @return
	 */
	public JobRunningTime getTime()
	{
		return time;
	}

	/**
	 * set the time which the job will run
	 * @param time
	 */
	public void setTime(JobRunningTime time)
	{
		this.time = time;
	}

}
