/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.util.Date;

/**
 * This class holds the scheduler data
 */
public class NowJobSchduleData extends AbstractJobSchduleData
{

	private Date frequencyDate;

	/**
	 * check the schedular data
	 * @throws JobException
	 */
	protected void checkParameters() throws JobException
	{			
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
	 * get the date, whice the job will run
	 * @return
	 */
	public Date getFrequencyDate()
	{
		return JobUtil.getCurrentTime();
	}

	/**
	 * get the frequenct type of the job
	 */
	public int getType()
	{
		return AbstractJobSchduleData.NOW_FREQUENCY_TYPE;
	}


}
