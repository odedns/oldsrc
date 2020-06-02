/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.util.ArrayList;
import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.externalization.XMLUtilException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;
import com.ness.fw.util.TypesUtil;

/**
 * This class consists exclusively of static methods for finding,creating 
 * and removing jobs
 */
public class JobManager
{

	// TODO: shouldn't be in the package. should move to the neww hirarchy
	private static final String PROPS_FILE_NAME = "com/ness/fw/job/jobsSqlStatements";
	public static final String LOGGER_CONTEXT = "JOBS_MANAGER";

	/**
	 * SQL statements constans
	 */
	private static final String JOBS_INSERT_WEEKLY   = "Job.insertWeekly";
	private static final String JOBS_INSERT_DAILY    = "Job.insertDaily";
	private static final String JOBS_INSERT_MONTHLY  = "Job.insertMonthly";
	private static final String JOBS_INSERT_ONCE     = "Job.insertOnce";
	private static final String JOBS_INSERT_NOW  	 = "Job.insertNow";
	private static final String JOBS_UPDATE  		 = "Job.update";
	private static final String JOBS_FIND_BY_ID  	 = "Job.findById";
	private static final String JOBS_DELETE  		 = "Job.delete";
	private static final String JOBS_INSERT_FINISHED = "Job.insertFinishedJob";

	/**
	 * DB columns name
	 */
	private static final String FREQUENCY_TIME 		 		= "FREQUENCY_TIME";
	private static final String DAYS_IN_WEEK 		 		= "DAYS_IN_WEEK"; 
	private static final String START_IN_FIRST_DAY_OF_MONTH = "START_IN_FIRST_DAY_OF_MONTH";
	private static final String START_IN_END_DAY_OF_MONTH 	= "START_IN_END_DAY_OF_MONTH";
	private static final String FREQUENCY_DATE 				= "FREQUENCY_DATE";
	private static final String STARTING_DATE 				= "STARTING_DATE";
	private static final String ENDING_DATE 				= "ENDING_DATE";
	private static final String LAST_RUNTIME 				= "LAST_RUNTIME";
	private static final String NEXT_DATE 					= "NEXT_DATE";
	private static final String BPO_COMMAND 				= "BPO_COMMAND";
	private static final String ID 							= "ID";
	private static final String LAST_STATUS 				= "LAST_STATUS";
	private static final String XML_ARGS 					= "XML_ARGS";
	private static final String FREQUENCY_TYPE 				= "FREQUENCY_TYPE";

	/**
	 * default constructor
	 */
	public JobManager()
	{
	}


	/**
	 * create a job in a specific scheduler
	 * @param jobData
	 * @throws FatalException
	 * @throws JobException
	 */
	public static void createJob(JobData jobData) throws FatalException, JobException
	{
		try
		{
			// checking data existance and essential scheduler parameters
			// were supplied
			jobData.checkData();

			// inserting the job into the repository
			int jobId = insert(jobData);
		
			// creating the job according to the desired scheduler type
			SchedulerManagerFactory factory = SchedulerManagerFactory.getInstance();
			SchedulerManager schedulerManager = factory.createSchdulerManager();
			schedulerManager.createJob(jobId,jobData.getJobSchduleData());
		}
		catch (PersistenceException e)
		{
			throw new JobException("Create job failed",e);
		}
	}
	
	/**
	 * remove a job from a specific scheduler
	 * @param jobId
	 * @throws JobException
	 * @throws FatalException
	 */
	public static void removeJob(int jobId) throws JobException, FatalException
	{
		removeJob(jobId,true);
	}

	/**
	 * remove a job from a specific scheduler
	 * @param jobId
	 * @param removeFromScheduler when true remove the job from the scheduler
	 * when false remove it from the repository
	 * @throws JobException
	 * @throws FatalException
	 */	
	public static void removeJob(int jobId, boolean removeFromScheduler) throws JobException, FatalException
	{
		SchedulerManagerFactory factory = null;
		SchedulerManager schedulerManager = null;
		
		Transaction transaction = null;
		try
		{
			transaction = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(Transaction.BATCH_USER));
			transaction.begin();

			//find if the job exists in the repoistory
			try
			{
				findById(jobId,transaction);
			}
			catch (ObjectNotFoundException e)
			{
				Logger.error(LOGGER_CONTEXT,"cannot remove jobId " + jobId + ". job doesn't exists");
				throw new JobException("cannot remove jobId " + jobId + ". job doesn't exists");
			}

			// job found in the repository, remove it from the scheduler

			// There are some situations when the scheduler remove
			// the job automaticlly, so there is no need to remove it.
			if (removeFromScheduler)
			{
				factory = SchedulerManagerFactory.getInstance();
				schedulerManager = factory.createSchdulerManager(null);
				// removing the job from the scheduler
				schedulerManager.removeJob(jobId);
			}

			// inserting the job to the finished one
			insertFinishedJob(new Integer(jobId),transaction);

			// deleting the job from the repository
			delete(jobId,transaction);
					
			transaction.commit();
			transaction = null;

		}
		catch (PersistenceException e)
		{
			throw new JobException("Remove job failed",e);
		}
		finally
		{
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException("Rollback failed",pe);
				}
			}
		}
	}


	/**
	 * get the common parameters for any job
	 * @param jobData
	 * @return
	 */
	private static ArrayList getCommonParams(JobData jobData)
	{
		ArrayList list = new ArrayList();
		list.add(jobData.getParamsAsXML());		
		list.add(jobData.getBpoCommand());		
		list.add(new Integer(JobResults.JOB_SUCCESS));	
		list.add(new Integer(jobData.getJobSchduleData().getType()));
		
		return list;	
		 
	}
	
	/**
	 * get the common time stamp parameters
	 * @param transaction
	 * @return
	 * @throws PersistenceException
	 */
	private static ArrayList getTSCommonParams(Transaction transaction, JobData jobData) throws PersistenceException
	{
		ArrayList list = new ArrayList();
		list.add(transaction.getUserId());
		list.add(transaction.getUserId());	
		list.add(jobData.getUserId());	
		return list;	
		
	}


	/**
	 * create record in the repository
	 * @param jobData
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 */
	private static int insertWeekly(JobData jobData) throws FatalException, PersistenceException
	{
		int lastId;
		Transaction transaction = null;
		
		try
		{			
			transaction = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(Transaction.BATCH_USER));
			String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_INSERT_WEEKLY); 
			SqlService ss = new SqlService(sqlStatement);
				
			// parameters
			ss.addParameters(getCommonParams(jobData));	
			
			// Adding the schduler data
			WeeklyJobSchduleData schduleData = (WeeklyJobSchduleData)jobData.getJobSchduleData();
			ss.addParameter(schduleData.getTime().toString());	
			ss.addParameter(JobUtil.convertArr2String(schduleData.getFrequencyDays()));			
			ss.addParameter(schduleData.getStartDate());		
			ss.addParameter(schduleData.getEndDate());		
		
			// TS parameters
			ss.addParameters(getTSCommonParams(transaction, jobData));

			transaction.begin();
			transaction.execute(ss);
			lastId = transaction.getLastIdentityKey();
			transaction.commit();
			transaction = null;			
			return lastId;
		}

		finally
		{
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException("Rollback failed",pe);
				}
			}
		}
	}



	/**
	 * create record in the repository
	 * @param jobData
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 */
	private static int insertDaily(JobData jobData) throws FatalException, PersistenceException
	{
		int lastId;
		Transaction transaction = null;
		
		try
		{			
			transaction = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(Transaction.BATCH_USER));
			String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_INSERT_DAILY); 
			SqlService ss = new SqlService(sqlStatement);
				
			// parameters
			ss.addParameters(getCommonParams(jobData));	
			
			// Adding the schduler data
			DailyJobSchduleData schduleData = (DailyJobSchduleData)jobData.getJobSchduleData();
			ss.addParameter(schduleData.getTime().toString());		
			ss.addParameter(schduleData.getStartDate());		
			ss.addParameter(schduleData.getEndDate());		
		
			// TS parameters
			ss.addParameters(getTSCommonParams(transaction, jobData));
		
			transaction.begin();
			transaction.execute(ss);
			lastId = transaction.getLastIdentityKey();
			transaction.commit();
			transaction = null;			
			return lastId;
		}

		finally
		{
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException("Rollback failed",pe);
				}
			}
		}
	}

	/**
	 * create record in the repository
	 * @param jobData
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 */
	private static int insertMonthly(JobData jobData) throws FatalException, PersistenceException
	{
		int lastId;
		Transaction transaction = null;
		
		try
		{			
			transaction = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(Transaction.BATCH_USER));
			String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_INSERT_MONTHLY); 
			SqlService ss = new SqlService(sqlStatement);
				
			// parameters
			ss.addParameters(getCommonParams(jobData));	
			
			// Adding the schduler data
			MonthlyJobSchduleData schduleData = (MonthlyJobSchduleData)jobData.getJobSchduleData();
			ss.addParameter(schduleData.getTime().toString());	
			ss.addParameter(new Integer(schduleData.getDay()));
			ss.addParameter(new Boolean(schduleData.getStartInEndDayOfMonth()));		
			ss.addParameter(new Boolean(schduleData.getStartInEndDayOfMonth()));		
			ss.addParameter(schduleData.getStartDate());		
			ss.addParameter(schduleData.getEndDate());		
		
			// TS parameters
			ss.addParameters(getTSCommonParams(transaction, jobData));

			transaction.begin();
			transaction.execute(ss);
			lastId = transaction.getLastIdentityKey();
			transaction.commit();
			transaction = null;			
			return lastId;
		}

		finally
		{
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException("Rollback failed",pe);
				}
			}
		}
	}

	/**
	 * create record in the repository
	 * @param jobData
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 */
	private static int insertOnce(JobData jobData) throws FatalException, PersistenceException
	{
		int lastId;
		Transaction transaction = null;
		
		try
		{			
			transaction = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(Transaction.BATCH_USER));
			String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_INSERT_ONCE); 
			SqlService ss = new SqlService(sqlStatement);
				
			// parameters
			ss.addParameters(getCommonParams(jobData));	
			
			// Adding the schduler data
			OnceJobSchduleData schduleData = (OnceJobSchduleData)jobData.getJobSchduleData();
			ss.addParameter(schduleData.getFrequencyDate());	
			ss.addParameter(schduleData.getFrequencyDate());	
		
			// TS parameters
			ss.addParameters(getTSCommonParams(transaction, jobData));

			transaction.begin();
			transaction.execute(ss);
			lastId = transaction.getLastIdentityKey();
			transaction.commit();
			transaction = null;			
			return lastId;
		}

		finally
		{
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException("Rollback failed",pe);
				}
			}
		}
	}


	/**
	 * create record in the repository
	 * @param jobData
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 */
	private static int insertNow(JobData jobData) throws FatalException, PersistenceException
	{
		int lastId;
		Transaction transaction = null;
		
		try
		{			
			transaction = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(Transaction.BATCH_USER));
			String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_INSERT_NOW); 
			SqlService ss = new SqlService(sqlStatement);
				
			// parameters
			ss.addParameters(getCommonParams(jobData));	
			
			// Adding the schduler data
			NowJobSchduleData schduleData = (NowJobSchduleData)jobData.getJobSchduleData();
			ss.addParameter(schduleData.getFrequencyDate());	
			ss.addParameter(schduleData.getFrequencyDate());	
		
			// TS parameters
			ss.addParameters(getTSCommonParams(transaction, jobData));

			transaction.begin();
			transaction.execute(ss);
			lastId = transaction.getLastIdentityKey();
			transaction.commit();
			transaction = null;			
			return lastId;
		}

		finally
		{
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException("Rollback failed",pe);
				}
			}
		}
	}

	/**
	 * insert the specific job to the db
	 * @param jobData
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 */
	private static int insert(JobData jobData) throws FatalException, PersistenceException
	{
		AbstractJobSchduleData jobSchduleData = jobData.getJobSchduleData();
		int type = jobSchduleData.getType();
		int lastId = -1;
		switch (type)
		{
			case AbstractJobSchduleData.DAILY_FREQUENCY_TYPE :
			{				
				lastId = insertDaily(jobData);
				break;	
			}

			case AbstractJobSchduleData.WEEKLY_FREQUENCY_TYPE :
			{
				lastId = insertWeekly(jobData);
				break;	
			}
			
			case AbstractJobSchduleData.MONTHLY_FREQUENCY_TYPE :
			{
				lastId = insertMonthly(jobData);
				break;	
			}

			case AbstractJobSchduleData.ONCE_FREQUENCY_TYPE :
			{
				lastId = insertOnce(jobData);
				break;	
			}

			case AbstractJobSchduleData.NOW_FREQUENCY_TYPE :
			{
				lastId= insertNow(jobData);
				break;	
			}			
		}
		
		return lastId;
	}


	/**
	 * load the scheduled data of a specific job according to it's type
	 * @param p
	 * @param type
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 * @throws JobException
	 */
	private static AbstractJobSchduleData loadScheduledData(Page p, int type) throws FatalException, PersistenceException, JobException
	{
	
		AbstractJobSchduleData scheduledData = null;
		switch (type)
		{
			case AbstractJobSchduleData.DAILY_FREQUENCY_TYPE :
			{				
				scheduledData = new DailyJobSchduleData();
				java.sql.Time sqltime = p.getTime(FREQUENCY_TIME);
				JobRunningTime time = new JobRunningTime(sqltime.getHours(),sqltime.getMinutes(),sqltime.getSeconds());
				((DailyJobSchduleData)scheduledData).setTime(time);				
				setTimeParams(p,(AbstractFrequencyJobSchduleData)scheduledData);
				setFrequencyParams((AbstractFrequencyJobSchduleData)scheduledData,p);		
				break;	
			}

			case AbstractJobSchduleData.WEEKLY_FREQUENCY_TYPE :
			{
				scheduledData = new WeeklyJobSchduleData();
				((WeeklyJobSchduleData)scheduledData).setFrequencyDays(JobUtil.convertString2Arr(p.getString(DAYS_IN_WEEK)));
				setTimeParams(p,(AbstractFrequencyJobSchduleData)scheduledData);
				setFrequencyParams((AbstractFrequencyJobSchduleData)scheduledData,p);		
				break;	
			}
		
			case AbstractJobSchduleData.MONTHLY_FREQUENCY_TYPE :
			{
				scheduledData = new MonthlyJobSchduleData();
				((MonthlyJobSchduleData)scheduledData).setStartInEndDayOfMonth(TypesUtil.convertNumberToBoolean(p.getInt(START_IN_FIRST_DAY_OF_MONTH)));
				((MonthlyJobSchduleData)scheduledData).setStartInEndDayOfMonth(TypesUtil.convertNumberToBoolean(p.getInt(START_IN_END_DAY_OF_MONTH)));
				setTimeParams(p,(AbstractFrequencyJobSchduleData)scheduledData);
				setFrequencyParams((AbstractFrequencyJobSchduleData)scheduledData,p);		
				
				break;	
			}

			case AbstractJobSchduleData.ONCE_FREQUENCY_TYPE :
			{
				scheduledData = new  OnceJobSchduleData();
				((OnceJobSchduleData)scheduledData).setFrequencyDate(p.getDate(FREQUENCY_DATE));
				break;	
			}

			case AbstractJobSchduleData.NOW_FREQUENCY_TYPE :
			{
				scheduledData = new NowJobSchduleData();
				((NowJobSchduleData)scheduledData).setFrequencyDate(((NowJobSchduleData)scheduledData).getFrequencyDate());
				break;	
			}			
		}
		
		return scheduledData;
	}

	/**
	 * set the common params for frequency jobs
	 * @param scheduledData
	 * @param p
	 */
	private static void setFrequencyParams(AbstractFrequencyJobSchduleData scheduledData, Page p)
	{
		scheduledData.setStartDate(p.getDate(STARTING_DATE));
		scheduledData.setEndDate(p.getDate(ENDING_DATE));
		scheduledData.setLastRuntime(p.getDate(LAST_RUNTIME));
		scheduledData.setNextDate(p.getDate(NEXT_DATE));
	}

	/**
	 * set the job time
	 * @param p
	 * @param scheduledData
	 * @throws JobException
	 */
	private static void setTimeParams(Page p, AbstractFrequencyJobSchduleData scheduledData) throws JobException
	{
		java.sql.Time sqltime = p.getTime(FREQUENCY_TIME);
		JobRunningTime time = new JobRunningTime(sqltime.getHours(),sqltime.getMinutes(),sqltime.getSeconds());
		(scheduledData).setTime(time);				
	}


	/**
	 * update record in the repository
	 * @param jobData
	 * @return
	 * @throws FatalException
	 * @throws PersistenceException
	 */
	public static void update(JobData jobData, JobResults jobResults) throws FatalException, PersistenceException
	{
		Transaction transaction = null;
		
		try
		{			
			transaction = TransactionFactory.createTransaction(UserAuthDataFactory.getUserAuthData(Transaction.BATCH_USER));
			String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_UPDATE); 
			SqlService ss = new SqlService(sqlStatement);
			AbstractFrequencyJobSchduleData jobSchduleData;	
			// parameters
			if (jobData.getJobSchduleData() instanceof AbstractFrequencyJobSchduleData)
			{
				jobSchduleData = (AbstractFrequencyJobSchduleData)jobData.getJobSchduleData();
				ss.addParameter(jobSchduleData.getLastRuntime());		
				ss.addParameter(jobSchduleData.getNextDate());				
				ss.addParameter(new Integer(jobResults.getStatus()));
				ss.addParameter(new Integer(jobData.getJobId()));
			}
			// once/now type
			else
			{
				if (jobData.getJobSchduleData().getType() == AbstractJobSchduleData.ONCE_FREQUENCY_TYPE )
				{
					ss.addParameter(((OnceJobSchduleData)jobData.getJobSchduleData()).getFrequencyDate());		
				}
				else if (jobData.getJobSchduleData().getType() == AbstractJobSchduleData.NOW_FREQUENCY_TYPE)
				{
					ss.addParameter(((NowJobSchduleData)jobData.getJobSchduleData()).getFrequencyDate());		
				}

				ss.addParameter(null);				
				ss.addParameter(new Integer(jobResults.getStatus()));
				ss.addParameter(new Integer(jobData.getJobId()));
				
			}
						
			transaction.begin();
			transaction.execute(ss);
			transaction.commit();
			transaction = null;			
		}

		finally
		{
			if (transaction != null)
			{
				try
				{
					transaction.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException("Rollback failed",pe);
				}
			}
		}
	}



	/**
	 * find a job form the repository
	 * @param jobId
	 * @param seq
	 * @return
	 * @throws PersistenceException
	 * @throws JobException
	 */
	public static JobData findById(int jobId, ConnectionProvider seq) throws JobException, FatalException, PersistenceException
	{
		JobData jobData;
		String sqlStatement = seq.getProperty(PROPS_FILE_NAME, JOBS_FIND_BY_ID); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(jobId));		
		Page p = Query.execute(ss, seq);
		if (p.next())
		{
			jobData = new JobData(); 
			loadObjectData(p,jobData);
		}
		else
		{
			throw new ObjectNotFoundException("Job " + jobId + " does not exist");
		}
		return jobData;


	}
	
	/**
	 * fill the jobdata
	 * @param p
	 * @param jobData
	 * @throws JobException
	 */
	private static void loadObjectData (Page page, JobData jobData) throws JobException, FatalException, PersistenceException
	{
		jobData.setBpoCommand(page.getString(BPO_COMMAND));
		jobData.setJobId(page.getInt(ID));
		jobData.setLastStatus(page.getInt(LAST_STATUS));
	
		String xml = page.getString(XML_ARGS);

		// protecting from undesired problems
		try
		{
			jobData.setParams(JobUtil.convertXML2HashMap(xml));
		}
		catch (XMLUtilException e)
		{
			Logger.fatal(LOGGER_CONTEXT, "Internal Error. xml wasn't parsed properly. the unparsed xml is: " + xml);
			throw new JobException("Internal Error. xml wasn't parsed properly");
		}
		
		int frequencyType = page.getInt(FREQUENCY_TYPE);
		AbstractJobSchduleData data = loadScheduledData(page,frequencyType);		
		jobData.setJobSchduleData(data);	
	}
	
	/**
	 * delete a job from the repository
	 * @param jobId
	 * @param transaction
	 * @throws PersistenceException
	 */
	private static void delete(int jobId, Transaction transaction) throws PersistenceException
	{
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_DELETE); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(jobId));		

		transaction.execute(ss);
	}
	
	/**
	 * insert the job to the finished jobs
	 * @param jobId
	 * @param transaction
	 * @throws PersistenceException
	 */
	private static void insertFinishedJob(Integer jobId, Transaction transaction) throws PersistenceException 
	{
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, JOBS_INSERT_FINISHED); 
		SqlService ss = new SqlService(sqlStatement);
			
		// keys		
		ss.addParameter(jobId);				
		transaction.execute(ss);
	}	
}
