package fwpilot.customer.operations;

import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.Operation;
import com.ness.fw.flower.core.OperationExecutionException;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.Row;
import com.ness.fw.ui.TableModel;

import fwpilot.customers.utils.AdressTools;
import fwpilot.customer.vo.Address;
import fwpilot.customer.vo.Customer;


public class PLUpdateAddressOP extends Operation
{
	protected void execute(Context ctx, ParameterList parameterList) throws OperationExecutionException
	{
		Logger.debug("", "UpdateAddress executed");
		try
		{

			Address address = null;
			Row row = null;
			
			// get the model
			TableModel model = (TableModel)ctx.getField("addressTableModel");

			// get the customer
			Customer customer = (Customer)ctx.getField("customer");

			if (model.getSelectedRow() == null)
			{
				address = customer.createAddress();
				updateAddress(address,ctx);
				customer.addAddress(address);

				row = model.createRow();
				// setting the extraData
				row.addExtraData("ID", null);
				row.addExtraData("UID",String.valueOf(address.getUID()));

				AdressTools.setRowWithAddress(model,row,address,ctx);
				model.setSelectedRow(row.getId());
			}

			// UPDATE mode
			else
			{
				// find the selected member
				address = AdressTools.getAddress(ctx);
				// update the FamilyMember object from context
				updateAddress(address,ctx);
				// update row in table
				row = model.getSelectedRow();
				AdressTools.setRowWithAddress(model, row, address,ctx);			
			}
		}

		catch(UIException ue)
		{
			throw new OperationExecutionException("UpdateDocument->UI", ue);
		}

		catch(ContextException ce)
		{
			throw new OperationExecutionException("UpdateDocument->Context", ce);
		}

		catch(GeneralException ge)
		{
			throw new OperationExecutionException("UpdateDocument->General", ge);
		}

	}

	private void updateAddress(Address address, Context ctx) throws ContextException
	{
		address.setCity(ctx.getXIString("city"));
		address.setStreet(ctx.getXIString("street"));
		address.setStreetNumber((ctx.getXIInteger("streetNumber")));
		address.setTelephone(ctx.getXIInteger("addressTelephone"));
		address.setFax(ctx.getXIInteger("addressFax"));

		ListModel addressMainModel = (ListModel) ctx.getField("addressMainModel");
		address.setMain(addressMainModel.isValueSelected("0"));
	}
}
