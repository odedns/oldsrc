/*
 * Created on: 25/10/2004
 * Author: yifat har-nof
 * @version $Id: MultipleReadersSingleWriterLock.java,v 1.4 2005/04/04 15:55:56 shay Exp $
 */
package com.ness.fw.common.lock;

import java.io.Serializable;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;

/** 
 * multiple readerCount, single writer lock (WE ASSUME THERE IS ALLWAYS ONLY ONE WRITER)
 * implemented from first principles with java primitives: synchronized, 
 * wait() and notifyAll(). 
 * waiting readerCount and writers all have equal priority to acquire locks. 
 * no optimizations to give earlier lock requests access. 
 */
public class MultipleReadersSingleWriterLock implements Serializable
{
	private final static String LOGGER_CONTEXT = "MultipleReadersSingleWriterLock";

	private final static int READER_WAIT_TIME = 10;
	private final static int WRITER_WAIT_TIME = 50;
	private final static int READER_WAIT_MAX_TIMES = 50;
	private final static int WRITER_WAIT_MAX_TIMES = 50;

	/**
	 * The READER_WAIT_TIME postfix to add to the key prefix of the 
	 * locking parameters in the Properties file.
	 */
	public static final String READER_WAIT_TIME_POSTFIX = ".readerWaitTime";

	/**
	 * The WRITER_WAIT_TIME postfix to add to the key prefix of the 
	 * locking parameters in the Properties file.
	 */
	public static final String WRITER_WAIT_TIME_POSTFIX = ".writerWaitTime";

	/**
	 * The READER_WAIT_MAX_TIMES postfix to add to the key prefix of the 
	 * locking parameters in the Properties file.
	 */
	public static final String READER_WAIT_MAX_TIMES_POSTFIX = ".readerWaitMaxTimes";

	/**
	 * The WRITER_WAIT_MAX_TIMES postfix to add to the key prefix of the 
	 * locking parameters in the Properties file.
	 */
	public static final String WRITER_WAIT_MAX_TIMES_POSTFIX = ".writerWaitMaxTimes";

	/**
	 * Number of readers in critical section.
	 */
	private int readerCount;

	/**
	 * Number of writers in crtical section.
	 */
	private int writerCount;

	/**
	 * Number of writers waiting.
	 */
	private int writerWaitingCount;

	/**
	 * All access to 'readers' and 'writer' take place when the calling thread 
	 * owns synchronizedObject's monitor, so they variables stay consistent
	 */
	private Object synchronizedObject;

	/**
	 * The name of the user object to translate from Localizable 
	 * Properties file and for logging.
	 */
	private String userObjectDescription;

	private int readerWaitTime = READER_WAIT_TIME;
	private int writerWaitTime = WRITER_WAIT_TIME;
	private int readerWaitMaxTimes = READER_WAIT_MAX_TIMES;
	private int writerWaitMaxTimes = WRITER_WAIT_MAX_TIMES;

	/**
	 * Creates new lock object.
	 */
	public MultipleReadersSingleWriterLock()  
	{
		readerCount = 0;
		writerCount = 0;
		writerWaitingCount = 0;
		synchronizedObject = new Integer(0);
	}


	/**
	 * Creates new lock object.
	 * @param propertiesKey The key prefix of the locking parameters 
	 * in the Properties file.
	 * @throws ResourceException
	 */
	public MultipleReadersSingleWriterLock(String propertiesKey) throws ResourceException 
	{
		this();
		
		if(propertiesKey != null)
		{
			reloadLockParameters(propertiesKey);	
		}
	}

	/**
	 * 
	 * @param propertiesKey The key prefix of the locking parameters 
	 * in the Properties file.
	 * @throws ResourceException
	 */
	public void reloadLockParameters (String propertiesKey) throws ResourceException
	{
		// read declarations for wait time and time out from properties file
		// according to the given propertiesKey.
		SystemResources systemResources = SystemResources.getInstance();
		readerWaitTime = systemResources.getInteger(propertiesKey + READER_WAIT_TIME_POSTFIX);
		writerWaitTime = systemResources.getInteger(propertiesKey + WRITER_WAIT_TIME_POSTFIX);
		readerWaitMaxTimes = systemResources.getInteger(propertiesKey + READER_WAIT_MAX_TIMES_POSTFIX);
		writerWaitMaxTimes = systemResources.getInteger(propertiesKey + WRITER_WAIT_MAX_TIMES_POSTFIX);
	}
	

	/**
	 * Get a read lock. Block (with timeout), until there is no writer in the critical section 
	 * or waiting to enter the critical section.  In addition, take a timeout if waiting it too long.
	 * @throws SynchronizationLockTimeOutException 
	 * @throws SynchronizationLockException
	 */
	public void getReadLock() throws SynchronizationLockTimeOutException, SynchronizationLockException
	{
//		System.out.println("get read lock ");
		int waitNumberOfTimes = 0;
		synchronized (synchronizedObject)
		{
			//if there is a writer in the critical section or there is a writer waiting			
			while (writerCount > 0 || writerWaitingCount > 0)
			{
				if(waitNumberOfTimes == readerWaitMaxTimes)
				{
					throw new SynchronizationLockTimeOutException("Error getting read lock. Wait timeout reached.");
				}
				
				waitNumberOfTimes++;
				
				try
				{
					// wait until a lock is released (with timeout) 
					synchronizedObject.wait(readerWaitTime);
				}
				catch (InterruptedException e) 
				{
					Logger.debug(LOGGER_CONTEXT, "InterruptedException thrown from read lock");
				}
				
			}
			// No writer waiting or in critical section
			// one more reader 
			readerCount++;
			
			// release ownership of the synchronizeObject's monitor
		}

	}

	/**
	 * Release a read lock. Assumes the thread has a read lock.  
	 */
	public void releaseReadLock() throws SynchronizationLockException
	{
//		System.out.println("release read lock ");
		synchronized (synchronizedObject)
		{
			if (readerCount <= 0)
			{
				throw new SynchronizationLockException("Error releasing read lock. Reader count is not greater then 0");
			}

			// one fewer reader 
			readerCount--;

			if (readerCount == 0)
			{
				// if readers > 0 then there's no point in notifying any threads 
				// because there cannot be any threads waiting to read, and any
				// thread waiting to write still will not be able to acquire 
				// the lock.

				// must notify all waiting threads, so that last lock release 
				// gives all threads a chance to gain lock 
				synchronizedObject.notifyAll();
			}

		}
	}

	/** 
	* Get the write lock. Blocks (with timeout), until there are no readers in the critical section
	* @throws SynchronizationLockTimeOutException 
	* @throws SynchronizationLockException
	*/
	public void getWriteLock() throws SynchronizationLockTimeOutException, SynchronizationLockException
	{
//		System.out.println("get write lock ");
		int waitNumberOfTimes = 0;
		synchronized (synchronizedObject)
		{
			writerWaitingCount++;
			while (readerCount > 0 || writerCount > 0)
			{
				if(waitNumberOfTimes == writerWaitMaxTimes)
				{
					writerWaitingCount--;
					throw new SynchronizationLockTimeOutException("Error getting write lock. Wait timeout reached.");
				}
				
				waitNumberOfTimes++;
				
				try
				{
					// wait until a lock is released 
					synchronizedObject.wait(writerWaitTime);
				}
				catch (InterruptedException e) 
				{
					Logger.debug(LOGGER_CONTEXT, "InterruptedException thrown from write lock");
				}

			}
			writerWaitingCount--;
			writerCount++;
			// enter the critical section
			// release ownership of the synchronizeObject's monitor, and return 
		}
		return;
	}

	/** 
	* Release the write lock. Assumes the thread has the write lock. 
	*/
	public void releaseWriteLock() throws SynchronizationLockException
	{
//		System.out.println("release write lock ");
		synchronized (synchronizedObject)
		{
			if (writerCount != 1)
			{
				throw new SynchronizationLockException("Error releasing writer lock. writerCount is not equals to 1");
			}
			// no writer in the critical section
			writerCount--;
			// must notify all waiting threads 
			synchronizedObject.notifyAll();
		}
	}

}
