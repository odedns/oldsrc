/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

/**
 * This class holds the bpo result
 */
public class JobResults
{
	private int status;

	/**
	 * job result status types
	 */
	public final static int JOB_SUCCESS 		=  1;
	public final static int JOB_FAILED 			= -1;
	public final static int JOB_BUSINESS_FAILED = -2;
	
	/**
	 * default constructor
	 */
	public JobResults()
	{		
	}

	/**
	 * 
	 * @return int The job status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * Set the job status
	 * @param status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

}
