/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.util.Date;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.job.JobUtil;

/**
 * This class holds the scheduler data
 */
public class OnceJobSchduleData extends AbstractJobSchduleData
{		
	private Date frequencyDate;
	
	/**
	 * check the schedular data
	 * @throws JobException
	 */
	protected void checkParameters() throws JobException
	{			
		if (frequencyDate == null)
		{
			Logger.error(LOGGER_CONTEXT,"date must be supplied for once frequency");
			throw new JobException("date must be supplied for once frequency");
		}
		// if the time id less than now throw ex exception
		else
		{
			if (frequencyDate.before(JobUtil.getCurrentDate()))
			{
				Logger.error(LOGGER_CONTEXT,"date must be greate than current time. The scheduler isn't effective");
				throw new JobException("date must be greate than current time. The scheduler isn't effective");				
			}
		}
	}

	/**
	 * get frequencyDate
	 * @return
	 */
	public Date getFrequencyDate()
	{
		return frequencyDate;
	}

	/**
	 * set frequencyDate
	 * @param frequencyDate
	 */
	public void setFrequencyDate(Date frequencyDate)
	{
		this.frequencyDate = frequencyDate;
	}

	/**
	 * get the frequenct type of the job
	 */
	public int getType()
	{
		return AbstractJobSchduleData.ONCE_FREQUENCY_TYPE;
	}
}
