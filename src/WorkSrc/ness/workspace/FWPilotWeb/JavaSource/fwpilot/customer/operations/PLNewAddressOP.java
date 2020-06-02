package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.TableModel;

public class PLNewAddressOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLNewAddressOP executed");

		try
		{
			// get the model
			TableModel model = (TableModel)ctx.getField("addressTableModel");
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
