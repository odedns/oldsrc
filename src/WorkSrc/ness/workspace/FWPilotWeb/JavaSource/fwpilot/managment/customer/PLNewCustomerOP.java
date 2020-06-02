package fwpilot.managment.customer;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;

public class PLNewCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList list) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLNewAddressOP executed");

		try
		{
			// get the model
			TableModel model = (TableModel)ctx.getField("customersTable");
			// unselecting all the rows
			model.unSelectAll();
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("NewAddress", ce);
		}

		catch (UIException ue)
		{
			throw new OperationExecutionException("NewAddress", ue);
		}
	}
}
