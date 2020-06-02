package fwpilot.common.operations;

import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.legacy.LegacyBPC;
import com.ness.fw.legacy.LegacyCommandException;
import com.ness.fw.legacy.LegacyCommandNotFoundException;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;
import com.ness.test.legacy.LegacyObjectGraph1;
import com.ness.test.legacy.TestBPC1;
import com.ness.test.legacy2.LegacyObjectGraph2;
import com.ness.test.legacy2.TestBPC2;

public class CheckLegacy extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "CheckLegacy executed");
		
		try
		{
			test3(ctx);			
			test4(ctx);
		}
		catch (GeneralException e)
		{
			throw new OperationExecutionException("1", e);
		}
		
	}	

	/**
	 * Test with resultsets
	 * @throws LegacyCommandNotFoundException
	 * @throws LegacyCommandException
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	private void test3 (Context ctx) throws LegacyCommandNotFoundException, LegacyCommandException, BPOCommandNotFoundException, BPOCommandException, BusinessLogicException, ContextException
	{
		System.out.println(" \nstart test3: testCMD3 with result sets\n"); 
		System.out.println(" \n --------------------------------------------\n");
		
		
		TestBPC1 inBPC = new TestBPC1();
		UserAuthData userAuthData = FlowerBusinessLogicUtil.getUserAuthData(ctx); 
		inBPC.setUserAuthData(userAuthData);
		inBPC.setArg1Value(new Integer(10));
		LegacyObjectGraph1 graph = (LegacyObjectGraph1)BPOProxy.execute("SPLegacyCmd", new LegacyBPC(userAuthData, "testCMD3", inBPC));

		System.out.println("simple output argument = " + graph.getOutArg());
		
		System.out.println("customer count" + graph.getCustomerCount());
		for(int i = 0 ; i < graph.getCustomerCount() ; i++)
		{
			System.out.println(graph.getCustomer(i));
		}
	}	

	/**
	 * Test with output arguments
	 * @throws LegacyCommandNotFoundException
	 * @throws LegacyCommandException
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	private void test4 (Context ctx) throws LegacyCommandNotFoundException, LegacyCommandException, BPOCommandNotFoundException, BPOCommandException, BusinessLogicException, ContextException
	{
		System.out.println(" \nstart test4: testCMD4 with output arguments\n");
		System.out.println(" \n --------------------------------------------\n");
		
		UserAuthData userAuthData = FlowerBusinessLogicUtil.getUserAuthData(ctx); 
		TestBPC2 legacyBPC = new TestBPC2();
		legacyBPC.setUserAuthData(userAuthData);
		legacyBPC.setArg1Value(new Integer(10));
		LegacyObjectGraph2 graph = (LegacyObjectGraph2)BPOProxy.execute("SPLegacyCmd", new LegacyBPC(userAuthData, "testCMD4", legacyBPC));

		System.out.println("simple output argument = " + graph.getOutArg());
		
		System.out.println("customer count" + graph.getCustomerCount());
		for(int i = 0 ; i < graph.getCustomerCount() ; i++)
		{
			System.out.println(graph.getCustomer(i));
		}
		
		System.out.println("env count" + graph.getEnvelopesCount());
		for(int i = 0 ; i < graph.getEnvelopesCount() ; i++)
		{
			System.out.println(graph.getEnv(i));
		}

	}	
	
}
