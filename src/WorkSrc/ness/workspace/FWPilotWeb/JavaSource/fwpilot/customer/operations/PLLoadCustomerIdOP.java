package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.TableModel;

public class PLLoadCustomerIdOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLLoadCustomerIdOP executed");
		try
		{
			ctx.setField("custID",loadCustomerID(ctx));
//			ctx.setField("selectedCustomerTypeModel",ctx.getField("customerTypeModel"));
		}
		catch (ContextException ce)
		{
			throw new OperationExecutionException("context->",ce);
		}
	}

	public String loadCustomerID(Context ctx) throws ContextException
	{
		// Determine the row whice the link was clicked from
		String customerId = null;
		try
		{
			TableModel model = (TableModel)ctx.getField("searchResultTableModel");
			String selectedRow = model.getSelectedLinkRowId();
			customerId = (String)model.getRow(selectedRow).getExtraData("ID");
		}

		catch (UIException ue)
		{
			throw new ContextException("ui",ue);
		}


		catch (GeneralException ge)
		{
			throw new ContextException("general",ge);
		}
		return customerId;
	}
}
