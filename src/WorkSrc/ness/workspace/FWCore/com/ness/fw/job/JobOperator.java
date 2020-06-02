/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import com.ness.fw.bl.proxy.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.job.bpc.JobContainer;

/**
 * This class operate the fw bpo that responsible to run the applicative bpo
 */
public class JobOperator
{
	/**
	 * default constructor
	 */
	public JobOperator()
	{
	}

	/**
	 * execute the jobCmd command
	 * @param jobId
	 * @throws FatalException
	 */
	public static void run(int jobId) throws FatalException
	{
		// operating the JobBPO
		JobContainer jobContainer = new JobContainer();
		jobContainer.setJobId(jobId);
		try
		{
			// TODO: ask yifat how this should comnbine with the fw
			BPOProxy.execute(JobConstants.JOB_BPO_COMMAND, jobContainer);

		}
		catch (BPOCommandNotFoundException e)
		{
			throw new FatalException("bpo",e);
		}
		catch (BPOCommandException e)
		{
			throw new FatalException("bpo",e);
		}
		catch (BusinessLogicException e)
		{
			throw new FatalException("bpo",e);
		}		
	}
}
