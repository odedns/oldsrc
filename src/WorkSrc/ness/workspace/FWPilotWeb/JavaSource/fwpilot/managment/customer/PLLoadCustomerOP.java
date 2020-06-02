package fwpilot.managment.customer;

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

import fwpilot.customer.vo.Customer;
import fwpilot.customers.utils.CustomerTools;

public class PLLoadCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList list) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLLoadAddressOP executed");
		
		try
		{		
			// Getting the model
			TableModel model = (TableModel)ctx.getField("customersTable");		
			
			// get the selected row
			String selectedRow = model.getSelectedRowId();
	
			if (selectedRow == null)
			{
				resetCustomerDetails(ctx);
			}
			else
			{
				Customer customer = CustomerTools.getCustomer(ctx); 
				loadCustomerDetails(ctx, customer);
			}
		}
		
		catch(ContextException ce)
		{
			throw new OperationExecutionException("Context->", ce);
		}

		catch(UIException ue)
		{
			throw new OperationExecutionException("UI->", ue);
		}

	}

	private void loadCustomerDetails(Context ctx, Customer customer) throws ContextException, FatalException, AuthorizationException
	{
		ctx.setField("customerName",customer.getFirstName());
		ctx.setField("customerFamily",customer.getLastName());
	}

	private void resetCustomerDetails(Context ctx) throws ContextException, FatalException, AuthorizationException
	{
		ctx.setField("customerName",null);
		ctx.setField("customerFamily",null);
	}	
}
