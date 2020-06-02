package fwpilot.managment.customer;

import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.shared.flower.bl.FlowerBusinessLogicUtil;

import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.customer.vo.CustomerManager;

public class PLSaveCustomerRegularOP extends Operation
{
	protected void execute(Context ctx, ParameterList list) throws OperationExecutionException, FatalException, AuthorizationException, ValidationException
	{
		Logger.debug("", "EXSaveCustomerOP executed");
		try
		{
			CustomerManager customerManager = (CustomerManager)ctx.getField("customerManager");					

			PLCustomerBPC container = new PLCustomerBPC();
			container.setCustomerManager(customerManager);
			FlowerBusinessLogicUtil.setBPOContainerUserData(ctx,container);
			BPOProxy.execute("saveCustomersCmd",container);
			ApplicationUtil.mergeGlobalMessageContainer(ctx, container.getMessagesContainer());

		}

		catch (BusinessLogicException ble)
		{
			ApplicationUtil.mergeGlobalMessageContainer(ctx, ble.getMessagesContainer());
			if (ble.getMessagesContainer().containsErrors())
			{
				throw new ValidationException();
			}
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("Error while saving customer ->",ce);
		} 
		
		catch (BPOCommandNotFoundException e)
		{
			throw new OperationExecutionException("Error in bpo command ->",e);
		}
		
		catch (BPOCommandException e)
		{
			throw new OperationExecutionException("Error in bpo command ->",e);
		}
	}
}
