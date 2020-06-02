package fwpilot.managment.customer;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;

import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.CustomerManager;
import fwpilot.customers.utils.CustomerTools;

public class PLDeleteCustomerOP extends Operation
{
	protected void execute(Context ctx, ParameterList list) throws OperationExecutionException, NumberFormatException, FatalException
	{
		Logger.debug("", "DeleteAddress executed");

		try
		{
			CustomerManager customerManager = (CustomerManager)ctx.getField("customerManager");
			TableModel model = (TableModel)ctx.getField("customersTable");
			Customer customer = CustomerTools.getCustomer(ctx);
			if (customer != null)
			{
				boolean retValue = customerManager.remove(customer);
				System.out.println("retValue =" + retValue);
				customerManager.addCustomerForLocking(customer);
				deleteCustomer(model);
			}
		}

		catch (UIException ue)
		{
			throw new OperationExecutionException("DeleteAddress->UI", ue);
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("DeleteAddress->Context", ce);
		}
	}

	private void deleteCustomer(TableModel tableModel) throws UIException
	{
		// delete this document row from the table
		String selectedRow = tableModel.getSelectedRowId();
		// if there is a selected Row 
		if (selectedRow != null)
		{
			tableModel.removeRow(selectedRow);
		}
		
		// in case there are other documents to show
		//	select the first row 
		if (tableModel.getRowsCount() > 0)
		{
			tableModel.setSelectedRow(0);
		}
	}

}
