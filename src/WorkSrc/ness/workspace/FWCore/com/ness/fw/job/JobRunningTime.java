/*
 * Created on: 10/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

/**
 * This class represent Time object
 */
public class JobRunningTime
{
	private int hour;
	private int minute;
	private int second;
	

	/**
	 * constructor
	 * @param hour
	 * @param minute
	 * @param second
	 * @throws JobException
	 */
	public JobRunningTime (int hour, int minute, int second) throws JobException
	{
		setHour(hour);
		setMinute(minute);
		setSecond(second);	
	}
	
	/**
	 * constructor
	 * @param hour
	 * @param minute
	 * @throws JobException
	 */
	public JobRunningTime(int hour, int minute) throws JobException
	{	
		this(hour,minute,0);
	}

	/**
	 * @return
	 */
	public int getHour()
	{
		return hour;
	}

	/**
	 * @param hour
	 */
	public void setHour(int hour) throws JobException
	{
		if (hour < 0 || hour > 23)
		{
			throw new JobException("hour should be between 0..23");
		}
		this.hour = hour;
	}

	/**
	 * @return
	 */
	public int getMinute() throws JobException
	{
		return minute;
	}

	/**
	 * @return
	 */
	public int getSecond()
	{
		return second;
	}

	/**
	 * @param minute
	 */
	public void setMinute(int minute) throws JobException
	{
		if (minute < 0 || minute > 59)
		{
			throw new JobException("minute should be between 0..59");
		}
		this.minute = minute;
	}

	/**
	 * @param second
	 */
	public void setSecond(int second) throws JobException
	{
		if (second < 0 || second > 59)
		{
			throw new JobException("second should be between 0..59");
		}
		this.second = second;
	}
	
	/**
	 * Returns a String object representing this Time's value
	 */
	public String toString()
	{
		String strHour = String.valueOf(hour);
		String strMinute = String.valueOf(minute);
		String strSecond = String.valueOf(second);

		if (hour < 10)
		{
			strHour = "0" + hour;
		}

		if (minute < 10)
		{
			strMinute = "0" + minute;
		}

		if (second < 10)
		{
			strSecond = "0" + second;
		}

		return strHour + ":" + strMinute + ":" + strSecond; 		
	}

}
