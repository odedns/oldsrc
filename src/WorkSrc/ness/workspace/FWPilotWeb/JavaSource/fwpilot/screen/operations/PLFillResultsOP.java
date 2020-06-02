package fwpilot.screen.operations;


import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import fwpilot.customer.bpc.PLCustomerBPC;

public class PLFillResultsOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLFillResultsOP executed");
		try
		{
			ctx.setField("customerName", findName(ctx));
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("context",ce);
		} 
		
		catch (GeneralException ge)
		{
			throw new OperationExecutionException("general",ge);
		}

	}

	private String findName(Context ctx) throws FatalException, ContextException, BPOCommandNotFoundException, BPOCommandException
	{
		String name = null;
		Integer customerID = ctx.getXIInteger("custId");
		PLCustomerBPC container = new PLCustomerBPC();
		container.setCustomerId(customerID);
		try
		{
			BPOProxy.execute("loadCustomerCmd",container);
			name = container.getCustomer().getName();
		}

		catch (BusinessLogicException e)
		{
			if (!container.isCustomerExists())
			{
				name = "The customer wasn't found";
			}
		}
		return name;
	}
}
