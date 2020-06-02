package fwpilot.customer.operations;

import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.util.ApplicationUtil;

import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.utils.Utils;

public class CheckCustomerExistance extends Operation
{

	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "CheckCustomerExistance executed");

		Integer customerID;

		boolean errors = false;
		
		PLCustomerBPC customerBPC = null;		

		try
		{
			if (Utils.isEmpty(ctx.getXIString("customerId")))
			{
				errors = true;
			}
			else
			{
				customerID = new Integer(ctx.getXIString("customerId"));
				customerBPC = new PLCustomerBPC();
				customerBPC.setCustomerId(customerID);
				BPOProxy.execute("loadCustomerCmd",customerBPC);
			}
		}
		
		catch (ContextException ce)
		{
			throw new OperationExecutionException("context->", ce);
		} 
		catch (BPOCommandNotFoundException e)
		{
			throw new OperationExecutionException("bpo->", e);
		} 
		catch (BPOCommandException e)
		{
			throw new OperationExecutionException("bpo->", e);
		} 

		catch (BusinessLogicException e)
		{
			ApplicationUtil.mergeGlobalMessageContainer(ctx,e.getMessagesContainer());
			if (!customerBPC.isCustomerExists())
			{
				errors = true;
			}
		} 

		try
		{
			if (errors)
			{
				ctx.setField("customerId", null);
			}
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("context->", ce);		
		}
	}
}
