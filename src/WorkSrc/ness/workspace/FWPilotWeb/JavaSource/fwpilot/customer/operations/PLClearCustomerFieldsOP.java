package fwpilot.customer.operations;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.*;

public class PLClearCustomerFieldsOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLClearCustomerFieldsOP executed");
		try
		{
			ctx.setField("id", null);
			ctx.setField("firstName",null);
			ctx.setField("telephone",null);
			ctx.setField("city","");

			ListModel customerTypeModel = (ListModel)ctx.getField("customerTypeModel");
			customerTypeModel.setSelectedKey("");
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("set field ->",ce);
		}
	}
}
