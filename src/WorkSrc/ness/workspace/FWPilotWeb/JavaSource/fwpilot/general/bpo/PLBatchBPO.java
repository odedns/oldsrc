package fwpilot.general.bpo;

import java.util.*;
import com.ness.fw.bl.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.job.*;

import fwpilot.general.bpc.PLBatchBPC;

public class PLBatchBPO extends BusinessProcess
{

			
	public static void runJobs(PLBatchBPC container) throws FatalException 
	{
		try
		{
			createJobs();
		}
		catch (XMLUtilException e)
		{
			throw new FatalException("problem in jobs",e);
		}

		catch (JobException e)
		{
			throw new FatalException("problem in jobs",e);
		}
	}
	
	private static void createJobs() throws XMLUtilException, FatalException, JobException
	{
		createOnceJob();
		createDailyJob();
		createWeeklyJob();
		createMonthlyJob();
		createNowJob();
			
	}
	
	public static void createNowJob() throws FatalException, JobException, XMLUtilException
	{
		JobData jobData = new JobData();
		jobData.setBpoCommand("test2");
		jobData.setParams(createMap());
		NowJobSchduleData data = new NowJobSchduleData();
		jobData.setJobSchduleData(data);	
		JobManager.createJob(jobData);
	}

	public static void createOnceJob() throws FatalException, JobException
	{
		JobData jobData = new JobData();
		jobData.setBpoCommand("test1");
	
		HashMap map = new HashMap();
		map.put("p1", "v1");
		map.put("p2", "v2");
		jobData.setParams(map);

		OnceJobSchduleData data = new OnceJobSchduleData();
		
		Calendar cal  =Calendar.getInstance();
		cal.setTime(JobUtil.getCurrentDate());
		cal.add(Calendar.MINUTE,3);

		data.setFrequencyDate(cal.getTime());
		jobData.setJobSchduleData(data);	
		JobManager.createJob(jobData);
	}


	public static void createMonthlyJob() throws FatalException, JobException
	{
		JobData jobData = new JobData();
		jobData.setBpoCommand("test1");
	
		HashMap map = new HashMap();
		map.put("p1", "v1");
		map.put("p2", "v2");
		jobData.setParams(map);

		MonthlyJobSchduleData data = new MonthlyJobSchduleData();
		data.setDay(20);
//		data.setStartDate(getStartEndDate(3));
		data.setEndDate(getStartEndDate(93));
	
		JobRunningTime runningTime = new JobRunningTime(22,23,45);
		data.setTime(runningTime);
	
//		data.setStartInEndDayOfMonth(true);
//		data.setStartInEndDayOfMonth(true);
		jobData.setJobSchduleData(data);	
		JobManager.createJob(jobData);
	}


	public static void createWeeklyJob() throws FatalException, JobException, XMLUtilException
	{
		JobData jobData = new JobData();
		jobData.setBpoCommand("test1");
		
		HashMap map = createMap();
		jobData.setParams(map);

		WeeklyJobSchduleData data = new WeeklyJobSchduleData();

	//	data.setStartDate(getStartEndDate(16));
	//	data.setEndDate(getStartEndDate(20));


		JobRunningTime runningTime = new JobRunningTime(17,23,45);
		
		data.addDayOfWeek(WeeklyJobSchduleData.WEDNESDAY);
		data.addDayOfWeek(WeeklyJobSchduleData.SAUTRDAY);
		data.addDayOfWeek(WeeklyJobSchduleData.MONDAY);
		data.addDayOfWeek(WeeklyJobSchduleData.THURSDAY);
		
		data.setTime(runningTime);

		jobData.setJobSchduleData(data);	
		JobManager.createJob(jobData);
	}

	public static void createDailyJob() throws FatalException, JobException
	{
		JobData jobData = new JobData();
		jobData.setBpoCommand("test1");
		
		HashMap map = new HashMap();
		jobData.setParams(map);

		DailyJobSchduleData data = new DailyJobSchduleData();
		JobRunningTime runningTime = new JobRunningTime(11,0,0);
		data.setTime(runningTime);

//		data.setStartDate(getStartEndDate(3));
//		data.setEndDate(getStartEndDate(4));


		jobData.setJobSchduleData(data);
		
		JobManager.createJob(jobData);
		
	}

	private static Date getStartEndDate(int interval)
	{
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_WEEK,interval);
		return calendar.getTime();		
	}
	
	private static HashMap createMap() throws XMLUtilException
	{
		HashMap map = new HashMap();
		HashMap params = new HashMap();

		map.put("a","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");	
		map.put("b","bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");	
		map.put("c","ccccccccccccccccccccccccccccccccccccccccccc");	
		map.put("d","ddddddddddddddddddddddddddddddddddddddddddd");	
		map.put("e","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");	
		
		for (int i=0;i<1;i++)
		{
			params.put(String.valueOf(i),map);
		}

		return params;	
	}


}
