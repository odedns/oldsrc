package com.ness.fw.bl.proxy.ejb;

import java.util.ArrayList;

import javax.naming.*;
import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPODispatcher;
import com.ness.fw.bl.proxy.EJBContainer;
import com.ness.fw.cache.CacheFactory;
import com.ness.fw.cache.config.CacheConfigFactory;
import com.ness.fw.cache.config.CacheConfigManager;
import com.ness.fw.common.SystemInitializationException;
import com.ness.fw.common.SystemInitializationManager;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.lock.MultipleReadersSingleWriterLock;
import com.ness.fw.common.lock.SynchronizationLockException;
import com.ness.fw.shared.common.SystemUtil;

/**
 * Bean implementation class for Enterprise Bean: BPOEJBFacade
 */
public class BPOEJBFacadeBean implements javax.ejb.SessionBean
{

	private String configurationLocation;
	private static boolean isInitialized = false;
	private final static String configurationLocationVariableName = "configurationLocation";
	private final static String shouldInitializedSystemVariableName = "shouldInitializedSystem";
	private final static String initializeCacheVariableName = "initializeCache";
	private final static String initialContextName = "java:comp/env";
	
	
	/**
	 * The lock object that manage the concurrency synchronization.
	 */
	private static MultipleReadersSingleWriterLock globalLockObject = new MultipleReadersSingleWriterLock();

	private javax.ejb.SessionContext mySessionCtx;
	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
	{
		mySessionCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws javax.ejb.CreateException
	{
		Boolean shouldInitializedSystem;
		Boolean initializeCache;

		try
		{
			InitialContext ctx = new InitialContext();
			Context myEnv = (Context)ctx.lookup(initialContextName);
			shouldInitializedSystem = (Boolean)myEnv.lookup(shouldInitializedSystemVariableName);

			// Because EjbContainer can create new instances initialize should
			// be only once and if this module should be initialized
			if (shouldInitializedSystem.booleanValue() && !isInitialized)
			{
				isInitialized = true;				
				configurationLocation = (String) myEnv.lookup(configurationLocationVariableName);
				initializeCache = (Boolean)myEnv.lookup(initializeCacheVariableName);
				SystemInitializationManager initializer = SystemInitializationManager.getInstance();
				initializer.initializeConfiguration(configurationLocation);
	
				if (initializeCache.booleanValue())
				{	
					ArrayList configRoots = new ArrayList(1);
					configRoots.add("ejbResources/config/cache");
					CacheFactory.getInstance().initialize(configRoots);
				}
			}
		}

		catch (NamingException e)
		{
			throw new javax.ejb.CreateException("problem with getting variable ...");
		}
			
		catch (Throwable e)
		{
			String exceptionStr = SystemUtil.convertThrowable2String(e);
			throw new javax.ejb.CreateException("problem with initializing system. stack trace is: " + exceptionStr);
		}
	}

	/**
	 * ejbActivate
	 */
	public void ejbActivate()
	{
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate()
	{
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove()
	{
	}
	
	/**
	 * Execute the bpo command using the {@link BPOFacadeEJB} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public EJBContainer execute(String bpoCommand, BusinessProcessContainer bpc) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		Object obj = BPODispatcher.execute(bpoCommand, bpc); 
		EJBContainer container = new EJBContainer();
		container.setBPC(bpc);
		container.setResult(obj);
		return container;
	}
	
	
	/**
	 * Execute the bpo command using the {@link BPODispatcher}. 
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public EJBContainer execute(String bpoCommand, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut) throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException
	{
		BPODispatcher.execute(bpoCommand, bpcIn, bpcOut);
		EJBContainer container = new EJBContainer();
		container.setBPC(bpcOut);
		return container;
	}
	
	public void reloadSystemConfig() throws  SynchronizationLockException, SystemInitializationException 
	{
		/*
		 * Check if the writer could start.
		 * If yes, lock the object and prevent other readers to start until he is finished.
		 * Otherwise, wait for the readers to finish.  
		 */
		globalLockObject.getWriteLock();

		try
		{
			SystemInitializationManager initializer = SystemInitializationManager.getInstance();
			initializer.initializeConfiguration(configurationLocation);
		}
		
		finally
		{
			//Release the writer locking. other readers could start. 
			globalLockObject.releaseWriteLock();
		}	
	}
}
