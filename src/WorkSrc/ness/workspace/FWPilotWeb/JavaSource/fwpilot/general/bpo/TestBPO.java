package fwpilot.general.bpo;

import java.util.HashMap;

import com.ness.fw.bl.BusinessProcess;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.job.*;
import com.ness.fw.job.bpc.JobContainer;

public class TestBPO extends BusinessProcess
{

	public static JobResults m1(JobContainer container) throws FatalException
	{
		// Creating jobResult
		JobResults jobResults = new JobResults();
		// Getting the parameters from the container
		HashMap params = container.getParams();
		// Doing some logic
		// ......
		
		// Setting the result to sucess
		jobResults.setStatus(JobResults.JOB_SUCCESS);
		return jobResults;
	}
	

	public static JobResults m2(JobContainer container) throws FatalException, BusinessLogicException
	{
		JobResults jobResults = new JobResults();
		jobResults.setStatus(1);
		HashMap params = container.getParams();

		// Doing some logic (that can throw an exception)
		// ....
		try
		{
			// this code can throw BusinessLogicException
			throw new BusinessLogicException();
		}
		catch (BusinessLogicException e)
		{
			throw e;			
		}		
	}
}
