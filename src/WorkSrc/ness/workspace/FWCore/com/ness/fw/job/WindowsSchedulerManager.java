/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.io.IOException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.util.DateFormatterUtil;

public class WindowsSchedulerManager implements SchedulerManager
{
	private static final String LOGGER_CONTEXT = "WINDOWS_SCHEDULER";

	/**
	 * constants of the schtasks.exe windows program's params
	 */
	private final String SCHEDULER = "SCHTASKS";
	private final String CREATE_TASK = " /Create ";
	private final String TASK_NAME = " /TN ";
	private final String PROGRAM_PATH = " /TR ";
	private final String USERNAME = " /RU ";
	private final String PASSWORD = " /RP ";
	private final String SCHEDULE_FREQUENCY = " /SC ";
	private final String START_TIME = " /ST ";
	private final String START_DATE = " /SD ";
	private final String END_DATE = " /ED ";
	private final String DELETE_TASK = " /Delete ";
	private final String FORCE_DELETE = " /F ";
	private final String DAYS = " /D ";
	private final String SYSTEM_USER = " SYSTEM ";
	private final String SYSTEM_PASSSWORD = " * ";
//	private final String SYSTEM_USER = " bhizkya ";
//	private final String SYSTEM_PASSSWORD = " bhizkya ";
	private String[] days = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
	private String[] frequency = {"DAILY","WEEKLY","MONTHLY","ONCE","NOW"};

	/** A constant used to set the scheduler frequency to daily type. */
	private final int DAILY_FREQUENCY_TYPE = 0;

	/** A constant used to set the scheduler frequency to weekly type. */
	public final int WEEKLY_FREQUENCY_TYPE = 1;

	/** A constant used to set the scheduler frequency to monthly type. */
	public final int MONTHLY_FREQUENCY_TYPE = 2;

	/** A constant used to set the scheduler frequency to once type. */
	public final int ONCE_FREQUENCY_TYPE = 3;

	/** A constant used to set the scheduler frequency to now type. */
	public final int NOW_FREQUENCY_TYPE = 3;

	/**
	 * create a schedule task in the scheduler
	 */
	public void createJob(int jobId, AbstractJobSchduleData jobSchduleData) throws JobException
	{
		// check for unsupported params
		checkUnsupportedParams(jobSchduleData);
		
		// get the command syntax
		String syntax = buildCreateJobSyntax(jobId,jobSchduleData);
		Logger.debug(LOGGER_CONTEXT,"scheduled task was createt in the scheduler with the params:" + syntax);
		// ruuning the command
		runCommand(syntax);	
	}

	/**
	 * remove a schedule task from the scheduler
	 */
	public void removeJob(int jobId) throws JobException
	{		
		// get the command syntax
		String syntax = buildRemoveJobSyntax(jobId);
		Logger.debug(LOGGER_CONTEXT,"scheduled task was removed from the scheduler with the params:" + syntax);
		// ruuning the command
		runCommand(syntax);
	}
	
	/**
	 * Executes the specified string command. 
	 * @param syntax
	 * @throws JobException
	 */
	private void runCommand(String syntax) throws JobException
	{
		// creating a windows scheduler job
		try
		{
			Runtime.getRuntime().exec(syntax);
		}
		catch (IOException e)
		{
			throw new JobException("io problem", e);
		}	
	}

	/**
	 * build the syantax for removing job from the scheduler
	 * @param jobId
	 * @return
	 * @throws JobException
	 */
	private String buildRemoveJobSyntax(int jobId) throws JobException
	{
		return SCHEDULER + DELETE_TASK + FORCE_DELETE + TASK_NAME + jobId;
	}

	/**
	 * build the syantax for creating job in the scheduler
	 * @param jobId
	 * @return
	 * @throws JobException
	 */
	private String buildCreateJobSyntax(int jobId, AbstractJobSchduleData jobSchduleData) throws JobException
	{
		String syntax = SCHEDULER + CREATE_TASK + USERNAME + SYSTEM_USER +
						PASSWORD + SYSTEM_PASSSWORD + SCHEDULE_FREQUENCY;
		int frequencyType = jobSchduleData.getType();

		// according to the frequency type, create the specific command
		switch (frequencyType)
		{
			// usage example : SCHTASKS /Create  /RU  SYSTEM  /RP  *  /SC DAILY /ST 11:00:00 /SD  19/11/2004 /ED  20/11/2004 /TN 22 /TR "java -cp C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\lib\fw.jar;C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\classes com.ness.fw.job.HTTPRequestor 22"
			case AbstractJobSchduleData.DAILY_FREQUENCY_TYPE :
			{
				syntax += frequency[DAILY_FREQUENCY_TYPE];
				syntax += START_TIME + ((DailyJobSchduleData)jobSchduleData).getTime();
				syntax += getStartEndDateSyntax((DailyJobSchduleData)jobSchduleData);
				break;
			}
	
			// usage example : SCHTASKS /Create  /RU  SYSTEM  /RP  *  /SC WEEKLY /ST 17:23:45 /D MON,WED,THU,SAT /SD  02/12/2004 /ED  06/12/2004 /TN 20 /TR "java -cp C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\lib\fw.jar;C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\classes com.ness.fw.job.HTTPRequestor 20"
			case AbstractJobSchduleData.WEEKLY_FREQUENCY_TYPE :
			{
				syntax += frequency[WEEKLY_FREQUENCY_TYPE];
				syntax += START_TIME + ((WeeklyJobSchduleData)jobSchduleData).getTime();
				syntax += DAYS + getOperateDate((WeeklyJobSchduleData)jobSchduleData);
				syntax += getStartEndDateSyntax((WeeklyJobSchduleData)jobSchduleData);
				break;
			}

			// usage example : SCHTASKS /Create  /RU  SYSTEM  /RP  *  /SC MONTHLY /D 8 /ST 22:23:45 /SD  19/11/2004 /ED  20/11/2004 /TN 21 /TR "java -cp C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\lib\fw.jar;C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\classes com.ness.fw.job.HTTPRequestor 21"
			case AbstractJobSchduleData.MONTHLY_FREQUENCY_TYPE :
			{
				syntax += frequency[MONTHLY_FREQUENCY_TYPE];
				
				syntax += DAYS + String.valueOf(((MonthlyJobSchduleData)jobSchduleData).getDay());
				syntax += START_TIME + ((MonthlyJobSchduleData)jobSchduleData).getTime();
				syntax += getStartEndDateSyntax((MonthlyJobSchduleData)jobSchduleData);
				break;
			}

			// usage example : SCHTASKS /Create  /RU  SYSTEM  /RP  *  /SC ONCE /ST 10:14:48 /TN 19 /TR "java -cp C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\lib\fw.jar;C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\classes com.ness.fw.job.HTTPRequestor 19"
			case AbstractJobSchduleData.ONCE_FREQUENCY_TYPE :
			{
				syntax += frequency[ONCE_FREQUENCY_TYPE];
				syntax += START_TIME + JobUtil.getTime(((OnceJobSchduleData)jobSchduleData).getFrequencyDate());
				syntax += START_DATE + DateFormatterUtil.format(((OnceJobSchduleData)jobSchduleData).getFrequencyDate());
				break;
			}

			// usage example : SCHTASKS /Create  /RU  SYSTEM  /RP  *  /SC ONCE /ST 10:14:46 /SD 16/11/2004 /TN 18 /TR "java -cp C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\lib\fw.jar;C:\IBM\Workspaces\workspace\BatchWeb\WebContent\WEB-INF\classes com.ness.fw.job.HTTPRequestor 18"
			case AbstractJobSchduleData.NOW_FREQUENCY_TYPE :
			{
				syntax += frequency[ONCE_FREQUENCY_TYPE];
				syntax += START_TIME + JobUtil.getTime();
				break;
			}
		}
		
		syntax += TASK_NAME + jobId + PROGRAM_PATH + "\"" + getCmdPath() + " "  +  jobId +  "\"" ;
		
		return syntax;
	}
	
	/**
	 * get the syntax for start & end date 
	 * @param jobSchduleData
	 * @return
	 */
	private String getStartEndDateSyntax(AbstractFrequencyJobSchduleData jobSchduleData)
	{
		return getStartDateSyntax(jobSchduleData) + getEndDateSyntax(jobSchduleData);
	}

	/**
	 * get the syntax for start date 
	 * @param jobSchduleData
	 * @return
	 */
	private String getStartDateSyntax(AbstractFrequencyJobSchduleData jobSchduleData)
	{
		String syntax = "";
				
		if (jobSchduleData.getStartDate() != null)
		{
			syntax = START_DATE + " " + DateFormatterUtil.format(jobSchduleData.getStartDate());
		}
	
		return syntax;
	}

	/**
	 * get the syntax for end date 
	 * @param jobSchduleData
	 * @return
	 */
	private String getEndDateSyntax(AbstractFrequencyJobSchduleData jobSchduleData)
	{
		String syntax = "";
				
		if (jobSchduleData.getEndDate() != null)
		{
			syntax = END_DATE + " " + DateFormatterUtil.format(jobSchduleData.getEndDate());
		}
	
		return syntax;
	}
		

	/**
	 * get the class path for operating the HTTPOperator
	 * @return
	 */
	private String getCmdPath()
	{
		String classPath = SystemResources.getInstance().getProperty("batchCmdPath");
		return classPath;
	}

	
	/**
	 * return a parsed days from a booleans days array
	 * @param data
	 * @return
	 */
	private String getOperateDate(WeeklyJobSchduleData data)
	{
		String daysStr = "";
		boolean[] days = data.getFrequencyDays();
		for (int i=0; i<days.length; i++)
		{
			if (days[i])
			{
				daysStr += this.days[i] + ",";
			}
		}
		return daysStr.substring(0,daysStr.length() - 1);		
	}

	/**
	 * check if there  are any unsupported params for the scheduler
	 * @param jobSchduleData
	 * @throws JobException
	 */
	private void checkUnsupportedParams(AbstractJobSchduleData jobSchduleData) throws JobException
	{
		if (jobSchduleData instanceof MonthlyJobSchduleData)
		{
			if (((MonthlyJobSchduleData)jobSchduleData).getStartInEndDayOfMonth() == true)
			{
				throw new JobException("unsupported method. Windows scheduler doesn't support operating the jobs in the end day of the month");
			}
	
			if (((MonthlyJobSchduleData)jobSchduleData).getStartInFirstDayOfMonth() == true)
			{
				throw new JobException("unsupported method. Windows scheduler doesn't support operating the jobs in the first day of the month");
			}
		}

	}
}

