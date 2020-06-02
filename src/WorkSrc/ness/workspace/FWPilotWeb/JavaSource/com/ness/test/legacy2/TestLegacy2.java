/*
 * Created on: 14/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

import com.ness.fw.common.SystemInitializationException;
import com.ness.fw.common.SystemInitializationManager;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.logger.LoggerException;
import com.ness.fw.legacy.LegacyBPC;
import com.ness.fw.legacy.LegacyBPO;
import com.ness.fw.legacy.LegacyCommandException;
import com.ness.fw.legacy.LegacyCommandNotFoundException;
import com.ness.fw.legacy.LegacyExternalizer;
import com.ness.fw.shared.common.SystemConstants;

/**
 * 
 */
public class TestLegacy2
{

	public static void main(String[] args)
	{

		TestLegacy2 legacy = new TestLegacy2();
		try
		{
			legacy.test();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	private void test () throws LegacyCommandNotFoundException, LegacyCommandException, BusinessLogicException, LoggerException, SystemInitializationException
	{
		//initializing logger
//		Logger.reset("/config/logger/logger.xml");

//		SystemUtil.initSystemConfiguration(getInitParameter(IP_LOCALIZABLE_CONFIG), getInitParameter(IP_SYSTEM_CONFIG));

		SystemInitializationManager.getInstance().initializeConfiguration("config/systemInit.xml");

		LegacyExternalizer.initialize("config/legacy");

//		//init ExtendedTransitionSupplierFactory
//		BPODispatcher.initialize(configurationRootDirectory + businessProcessPath);
				
		
		test4();
		
	}
	

	private void test4 () throws LegacyCommandNotFoundException, LegacyCommandException, BusinessLogicException
	{
//		System.out.println("start test4: testCMD4");
//
//		TestBPC2 legacyBPC = new TestBPC2("yifat");
//		legacyBPC.setArg1Value(new Integer(10));
//		LegacyObjectGraph2 graph = (LegacyObjectGraph2)LegacyBPO.executeSPLegacyCommand(new LegacyBPC("yifat", "testCMD4",legacyBPC));
//
//		System.out.println("simple output argument = " + graph.getOutArg());
//		
//		System.out.println("customer count" + graph.getCustomerCount());
//		for(int i = 0 ; i < graph.getCustomerCount() ; i++)
//		{
//			System.out.println(graph.getCustomer(i));
//		}
//		
	}	

}
