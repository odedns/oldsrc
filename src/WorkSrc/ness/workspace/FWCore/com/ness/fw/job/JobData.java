/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */

package com.ness.fw.job;

import java.util.HashMap;
import com.ness.fw.job.JobUtil;

/**
 * This class represent the job data, whice contains info like what to run,
 * parameters to the running program, operatin times, etc.
 * */
public class JobData
{

	private int jobId;
	private String bpoCommand;
	private AbstractJobSchduleData jobSchduleData;
	private int lastStatus;
	private HashMap params;
	private String userId;
	
	
	/**
	 * Checking the job data. throws JobException when there is
	 * missing data. 
	 * @throws JobException 
	 */
	public void checkData() throws JobException
	{

		// missing userid
		
		if (userId == null)
		{
			throw new JobException("batch userId must be set");			
		}
		
		// missing bpo
		if (bpoCommand == null)
		{
			throw new JobException("bpoCommand must be set");
		}
		
		// missing scheduler data
		if (jobSchduleData == null)
		{
			throw new JobException("scheduler parameters wasn't set");
		}
		else
		{
			// checking scheduler data
			jobSchduleData.checkParameters();
		}
	}


	/**
	 * get the params as xml
	 * @return
	 */
	public String getParamsAsXML()
	{
		return JobUtil.convertHashMap2XML(params);
	}

	/**
	 * get the bpo command
	 * @return
	 */
	public String getBpoCommand()
	{
		return bpoCommand;
	}

	/**
	 * get the jobId
	 * @return
	 */
	public int getJobId()
	{
		return jobId;
	}

	/**
	 * get the job scheduler data
	 * @return
	 */
	public AbstractJobSchduleData getJobSchduleData()
	{
		return jobSchduleData;
	}


	/**
	 * getlast status
	 * @return
	 */
	public int getLastStatus()
	{
		return lastStatus;
	}

	/**
	 * get params (that will be used as container to the bpo)
	 * @return
	 */
	public HashMap getParams()
	{
		return params;
	}

	/**
	 * set the bpo command
	 * @param bpoCommand
	 */
	public void setBpoCommand(String bpoCommand)
	{
		this.bpoCommand = bpoCommand;
	}

	/**
	 * set the job id
	 * @param jobId
	 */
	public void setJobId(int jobId)
	{
		this.jobId = jobId;
	}

	/**
	 * set the job scheduler data
	 * @param jobSchduleData
	 */
	public void setJobSchduleData(AbstractJobSchduleData jobSchduleData)
	{
		this.jobSchduleData = jobSchduleData;
	}


	/**
	 * set the last status
	 * @param lastStatus
	 */
	public void setLastStatus(int lastStatus)
	{
		this.lastStatus = lastStatus;
	}

	/**
	 * set the params that will be used as container to the bpo
	 * @param map
	 */
	public void setParams(HashMap map)
	{
		params = map;
	}
	/**
	 * @return. The batch user id
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * @param string
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

}
