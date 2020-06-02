/*
 * Created on: 14/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy;

import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.common.SystemInitializationException;
import com.ness.fw.common.SystemInitializationManager;
import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.logger.LoggerException;
import com.ness.fw.legacy.LegacyBPC;
import com.ness.fw.legacy.LegacyCommandException;
import com.ness.fw.legacy.LegacyCommandNotFoundException;
import com.ness.fw.legacy.LegacyExternalizer;
import com.ness.fw.legacy.LegacyService;

/**
 * 
 */
public class TestLegacy1
{

	public static void main(String[] args)
	{

		TestLegacy1 legacy = new TestLegacy1();
		try
		{
			legacy.test();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	private void test () throws LoggerException, SystemInitializationException, LegacyCommandNotFoundException, LegacyCommandException, BusinessLogicException, BPOCommandException
	{
		//initializing logger
	//	Logger.reset("C:/IBM/Workspaces/Workspace/FrameWorkLegacy/config/logger/logger.xml");

//		SystemUtil.initSystemConfiguration(getInitParameter(IP_LOCALIZABLE_CONFIG), getInitParameter(IP_SYSTEM_CONFIG));

		SystemInitializationManager.getInstance().initializeConfiguration("config/systemInit.xml");

		LegacyExternalizer.initialize("config/legacy");

//		//init ExtendedTransitionSupplierFactory
//		BPODispatcher.initialize(configurationRootDirectory + businessProcessPath);
				
		
		test3();
		
	}
	
//	private void test1 () throws LegacyCommandNotFoundException, LegacyCommandException, BusinessLogicException
//	{
//		TestBPC1 legacyBPC = new TestBPC1("yifat");
//		legacyBPC.setArg1Value(new Integer(10));
//		LegacyObjectGraph1 graph = (LegacyObjectGraph1)LegacyBPO.executeSPLegacyCommand(new LegacyBPC("yifat", "testCMD1", legacyBPC));
//
//		System.out.println("simple output argument = " + graph.getOutArg());
//		
//		System.out.println("customer count" + graph.getCustomerCount());
//		if(graph.getCustomerCount() > 0)
//		{
//			System.out.println("first customer = " + graph.getCustomer(0));
//		}
//	}	
//
	private void test3 () throws LegacyCommandNotFoundException, LegacyCommandException, BusinessLogicException, BPOCommandException
	{
		TestBPC1 legacyBPC = new TestBPC1(UserAuthDataFactory.getUserAuthData("yifat"));
		legacyBPC.setArg1Value(new Integer(10));
		LegacyObjectGraph1 graph = (LegacyObjectGraph1)LegacyService.executeLegacyCommand(new LegacyBPC(UserAuthDataFactory.getUserAuthData("yifat"), "testCMD3", legacyBPC));

		System.out.println("simple output argument = " + graph.getOutArg());
		
		System.out.println("customer count" + graph.getCustomerCount());
		for(int i = 0 ; i < graph.getCustomerCount() ; i++)
		{
			System.out.println(graph.getCustomer(i));
		}
		
	}	

}
