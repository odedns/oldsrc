package fwpilot.customer.operations;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.*;
import fwpilot.customer.vo.*;
import fwpilot.customers.utils.AdressTools;

public class PLDeleteAddressOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "DeleteAddress executed");

		try
		{
			Customer customer = (Customer)ctx.getField("customer");
			TableModel model = (TableModel)ctx.getField("addressTableModel");
			Address address = AdressTools.getAddress(ctx);
			if (address != null)
			{
				customer.removeAddress(address);
				deleteAdress(model);
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

	private void deleteAdress(TableModel tableModel) throws UIException
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
