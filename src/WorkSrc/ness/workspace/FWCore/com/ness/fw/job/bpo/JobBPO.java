/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job.bpo;

import java.util.Date;

import com.ness.fw.bl.BusinessProcess;
import com.ness.fw.bl.proxy.*;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.job.*;
import com.ness.fw.job.bpc.JobContainer;
import com.ness.fw.persistence.ConnectionSequence;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;

/**
 * A generic bpo for running application's bpos
 */
public class JobBPO extends BusinessProcess
{

	public static final String LOGGER_CONTEXT = "JOBS_BPO";

	/**
	 * run a application bpo according to the jobid
	 * @param container
	 * @throws FatalException
	 * @throws JobException
	 */
	public static void runJob(JobContainer container) throws JobException, FatalException
	{
		ConnectionSequence seq = null;
		JobData jobData = null;
		int jobId = -1;
		JobResults jobResults = null;	

		try
		{
			seq = ConnectionSequence.beginSequence();
			// Getting the jobId
			jobId = container.getJobId();
			// find the Jobid from the repository
			jobData = JobManager.findById(jobId,seq);
				
			// preparing the job request
			
			// creating a container
			JobContainer jobContainer = new JobContainer();
			jobContainer.setParams(jobData.getParams());
						
			// updating the schedulers times in the repository
			updateTimes(jobData);

			// setting the batch user id tho the container
			UserAuthData authData = UserAuthDataFactory.getUserAuthData(jobData.getUserId());
			jobContainer.setUserAuthData(authData);
			
			// operating the application bpo by calling the dispacther directly
			jobResults = (JobResults)BPODispatcher.execute(jobData.getBpoCommand(),jobContainer);

			if (jobResults == null)
			{
				Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]." + "has no jobResult. bpo method should return a JobResult");
				throw new JobException("jobId number [ " + jobId + "]." + " bpo method should return a JobResult");
			}

			Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]." + "was run succesufully");
		
		}

		catch (ClassCastException e)
		{
			// setting job failed to the jobResult
			jobResults = new JobResults();
			jobResults.setStatus(JobResults.JOB_FAILED);

			Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]." + "Invalid signature. bpo method should be: public JobResult method(JobContainer container)");
			throw new JobException("jobId number [ " + jobId + "]." + "Invalid signature. bpo method should be: public JobResult method(JobContainer container)",e);
		} 

		catch (BPOCommandNotFoundException e)
		{

			// setting job failed to the jobResult
			jobResults = new JobResults();
			jobResults.setStatus(JobResults.JOB_FAILED);

			Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]." + "bpo command [" + jobData.getBpoCommand() + "] wasn't found",e);
			throw new FatalException("bpo",e);
		} 

		catch (BPOCommandException e)
		{
			// setting job failed to the jobResult
			jobResults = new JobResults();
			jobResults.setStatus(JobResults.JOB_FAILED);

			Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]." + "error while operating bpo command [" + jobData.getBpoCommand() + "]");
			Logger.error(LOGGER_CONTEXT,"Exception details:" + e);
			throw new FatalException("bpo",e);
		}

		catch (BusinessLogicException e)
		{
			// setting job failed to the jobResult
			jobResults = new JobResults();
			jobResults.setStatus(JobResults.JOB_BUSINESS_FAILED);

			Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]." + "error while operating bpo command [" + jobData.getBpoCommand() + "] BusinessLogicException was thrown from batch");
			Logger.error(LOGGER_CONTEXT,"Exception details:" + e);		
			//throw new FatalException("jobId number [ " + jobId + "]." + "BusinessLogicException cannot be thrown from batch",e);
		}

		catch (ObjectNotFoundException e)
		{
			Logger.error(LOGGER_CONTEXT,"error while operating job. jobId [" + jobId + "] doesn't exist");
			throw new FatalException(e);
		} 


		catch (PersistenceException e)
		{
			Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]." + "error while operating bpo command [" + jobData.getBpoCommand() + "] PersistanceException cannot was thrown");
			Logger.error(LOGGER_CONTEXT,"Exception details:" + e);		
			throw new FatalException(e);
		} 		

		finally
		{

			// do it in the finally, bacause if was an error if the job
			// ended it must remove from the jobs list to the finished one
			
			// because the scheduler remove it automaticly when it done, we should 
			// remove it from the repository and move it to the finished jobs	
			try
			{
				if (!deleteFinsihedJobs(jobData, jobResults))
				{
					JobManager.update(jobData,jobResults);
				}
			}
			catch (PersistenceException pe)
			{
				Logger.error(LOGGER_CONTEXT,"jobId number [ " + jobId + "]. didn't remove ffrom the repository bacuase internal error. should remoce it manully");
				throw new FatalException("jobId number [ " + jobId + "]. didn't remove from the repository because internal error. should remove it manully",pe);
			}


			if (seq != null)
			{
				try
				{
					seq.end();
				}
				catch (PersistenceException e)
				{
					Logger.error(LOGGER_CONTEXT,"jobId number [+ " + jobId + "]." + "error while closing connection from bpo [" + jobData.getBpoCommand() + "] PersistanceException cannot was thrown");
					Logger.error(LOGGER_CONTEXT,"Exception details:" + e);		
					throw new FatalException("persistance",e);
				} 
			} 
		}			
	}

	/**
	 * remove jobs from the repository that were automaticlly removed by the scheduler
	 * @return true it there was a deleted job
	 */
	private static boolean deleteFinsihedJobs(JobData jobData, JobResults jobResults) throws JobException, FatalException, PersistenceException
	{
		boolean returnValue = false;

		// Handling once\now type
		if (jobData.getJobSchduleData().getType() == AbstractJobSchduleData.ONCE_FREQUENCY_TYPE ||
			jobData.getJobSchduleData().getType() == AbstractJobSchduleData.NOW_FREQUENCY_TYPE)
		{
			JobManager.update(jobData,jobResults);
			JobManager.removeJob(jobData.getJobId(),false);
			Logger.info(LOGGER_CONTEXT,"jobId number [ " + jobData.getJobId() + "]" + "removed from the repository, due to automatic remove (once\now) from the scheduler");		
			returnValue = true;
		}

		// Handling jobs with end date and we got to this date
		if (jobData.getJobSchduleData() instanceof AbstractFrequencyJobSchduleData)
		{
			Date endDate = ((AbstractFrequencyJobSchduleData)jobData.getJobSchduleData()).getEndDate();	
			Date nextDate = ((AbstractFrequencyJobSchduleData)jobData.getJobSchduleData()).getNextDate();
			
			endDate = JobUtil.resetDate(endDate);
			nextDate = JobUtil.resetDate(nextDate);
			
			if (endDate != null && nextDate.after(endDate))
			{
				JobManager.update(jobData, jobResults);
				JobManager.removeJob(jobData.getJobId(),false);
				Logger.info(LOGGER_CONTEXT,"jobId number [ " + jobData.getJobId() + "]" + "removed from the repository, due to automatic remove (expired end date)from the scheduler");
				returnValue = true;
			}
		}
		
		return returnValue;
	}

	/**
	 * setting the scheduler times
	 * @param jobData
	 */
	private static void updateTimes(JobData jobData)
	{
		AbstractJobSchduleData jobSchduleData = jobData.getJobSchduleData();
		if (jobSchduleData instanceof AbstractFrequencyJobSchduleData)
		{
			AbstractFrequencyJobSchduleData frequencyJobData = (AbstractFrequencyJobSchduleData)jobSchduleData;
			if (frequencyJobData.getNextDate() != null)
			{
				frequencyJobData.setLastRuntime(frequencyJobData.getNextDate());
			}
			else
			{
				frequencyJobData.setLastRuntime(JobUtil.getCurrentDate());			
			}
			frequencyJobData.setNextDate(JobUtil.computeNextDate((AbstractFrequencyJobSchduleData)jobData.getJobSchduleData()));
		}		
	}	
}
