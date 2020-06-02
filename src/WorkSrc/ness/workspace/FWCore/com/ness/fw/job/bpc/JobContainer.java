/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job.bpc;

import java.util.HashMap;
import com.ness.fw.bl.BusinessProcessContainer;

/**
 * A generic batch container
*/
public class JobContainer extends BusinessProcessContainer
{
	private HashMap params;
	private int jobId;

	/**
	 * get pramas
	 * @return
	 */
	public HashMap getParams()
	{
		return params;
	}

	/**
	 * set params
	 * @param params
	 */
	public void setParams(HashMap params)
	{
		this.params = params;
	}

	/**
	 * get jobId
	 * @return
	 */
	public int getJobId()
	{
		return jobId;
	}

	/**
	 * set jobId
	 * @param jobId
	 */
	public void setJobId(int jobId)
	{
		this.jobId = jobId;
	}

}
