/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

/**
 * Interface that represent an operation of a scheduler
 */
public interface SchedulerManager
{
	/**
	 * create a schedule task in the scheduler
	 */
	public void createJob(int jobId, AbstractJobSchduleData jobSchduleData) throws JobException;

	/**
	 * remove a schedule task from the scheduler
	 */
	public void removeJob(int jobId) throws JobException;
}
