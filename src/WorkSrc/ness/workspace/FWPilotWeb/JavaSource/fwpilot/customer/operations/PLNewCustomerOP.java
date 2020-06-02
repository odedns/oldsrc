package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.ListModel;

public class PLNewCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException,  FatalException, AuthorizationException
	{
		Logger.debug("", "NewCustomer executed");

		try
		{
			// the selected type from the radio button
			ListModel customerRadioModel = (ListModel)ctx.getField("customerTypeModel");
			String customerType = customerRadioModel.getSelectedKey();
			//System.err.println("ct=" + customerType);
			ctx.setField("customerType", new Integer(customerType));
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("NewCustomer", ce);
		}
	}
}
