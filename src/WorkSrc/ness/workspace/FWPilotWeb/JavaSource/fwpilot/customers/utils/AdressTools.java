package fwpilot.customers.utils;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.ui.Row;
import com.ness.fw.ui.TableModel;

import fwpilot.customer.vo.Address;
import fwpilot.customer.vo.Customer;

public class AdressTools 
{	

	public static Address getAddress(Context ctx) throws UIException, ContextException 
	{
		Address address = null;

		// get the table from the context
		Customer customer = (Customer) (ctx).getField("customer");
		// get the table from the context
		TableModel table = (TableModel)ctx.getField("addressTableModel");

		String selectedRow = table.getSelectedRowId();
		// if there is a selected row
		if (selectedRow != null)
		{
			//	Get the selected row Object 
			Row row = null;
			row = table.getRow(selectedRow);
			// get The document ID from the extra data hash
			if (row.getExtraData("ID")!= null)
			{				
				address = customer.getAddress(Integer.valueOf(
					row.getStringExtraData("ID")));
			}
			else 
			{
				// if it is a new document and he have a temporary ID
				// we will take the temporary id that is the External Cover Object UID
				address = customer.getAddressByUID(Integer.valueOf(
					row.getStringExtraData("UID")));
			}
		} 
		return address;
	}
	
	public static void setRowWithAddress(TableModel tableModel, 
		Row row, Address address, Context ctx) throws ResourceException,UIException 
	{
		String rowId = row.getId();

		tableModel.getCell(rowId,0).setData(address.getCity());
		tableModel.getCell(rowId,1).setData(address.getStreet());
		tableModel.getCell(rowId,2).setData(address.getStreetNumber());
		tableModel.getCell(rowId,3).setData(address.getTelephone());
		tableModel.getCell(rowId,4).setData(address.isMain() == true ? "כן" : "לא");
	}

}
