package fwpilot.managment.customer;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;

import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.customer.vo.CustomerManager;


public class PLSaveCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList list) throws OperationExecutionException, FatalException, AuthorizationException, ValidationException
	{
		Logger.debug("", "EXSaveCustomerOP executed");
		try
		{
			CustomerManager customerManager = (CustomerManager)ctx.getField("customerManager");					

			PLCustomerBPC container = new PLCustomerBPC();
			container.setCustomerManager(customerManager);
			FlowerBusinessLogicUtil.executeBPOCommand(ctx,"saveCustomersCmd",container);
		}

	
		catch (ContextException ce)
		{
			throw new OperationExecutionException("Error while saving customer ->",ce);
		} 
		
	}
}
