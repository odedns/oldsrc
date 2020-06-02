package fwpilot.customer.operations;


import java.util.ArrayList;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.TableModel;

import fwpilot.customers.utils.AdressTools;
import fwpilot.customer.vo.Address;

public class PLLoadAddressOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "PLLoadAddressOP executed");
		
		try
		{		
			// Getting the model
			TableModel model = (TableModel)ctx.getField("addressTableModel");		
			
			// get the selected row
			String selectedRow = model.getSelectedRowId();
	
			if (selectedRow == null)
			{
				resetAddressDetails(ctx);
			}
			else
			{
				Address address = AdressTools.getAddress(ctx); 
				loadAddressDetails(ctx, address);
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

	private void loadAddressDetails(Context ctx, Address address) throws ContextException, FatalException, AuthorizationException
	{
		ctx.setField("city",address.getCity());
		ctx.setField("street",address.getStreet());
		ctx.setField("addressTelephone", address.getTelephone());
		ctx.setField("addressFax", address.getFax());
		ctx.setField("streetNumber", address.getStreetNumber());
													
		ListModel mainCheckBoxModel = (ListModel)ctx.getField("addressMainModel");	
		ArrayList keys = new ArrayList();
		keys.add("0");
		if (address.isMain())
		{
			mainCheckBoxModel.setSelectedKeys(keys);
		}
		else
		{
			mainCheckBoxModel.unSelectAllKeys();
		}
	}

	private void resetAddressDetails(Context ctx) throws ContextException, FatalException, AuthorizationException
	{
		ctx.setField("city",null);
		ctx.setField("street",null);
		ctx.setField("addressTelephone", null);
		ctx.setField("addressFax", null);
		ctx.setField("streetNumber", null);
													
		ListModel mainCheckBoxModel = (ListModel)ctx.getField("addressMainModel");	
		mainCheckBoxModel.unSelectAllKeys();
	}	
}
