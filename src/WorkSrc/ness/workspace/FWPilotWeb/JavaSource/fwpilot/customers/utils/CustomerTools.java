package fwpilot.customers.utils;

import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.persistence.Page;
import com.ness.fw.ui.Cell;
import com.ness.fw.ui.Row;
import com.ness.fw.ui.TableModel;

import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.CustomerManager;


public class CustomerTools 
{

	public static Customer getCustomer(Context ctx) throws UIException, ContextException, NumberFormatException, FatalException 
	{
		Customer customer = null;

		// get the table from the context
		TableModel table = (TableModel)ctx.getField("customersTable");

		CustomerManager customerManager = (CustomerManager)ctx.getField("customerManager");

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
				customer = customerManager.getCustomerById(Integer.valueOf(
					row.getStringExtraData("ID")));
			}
			else 
			{
				// if it is a new document and he have a temporary ID
				// we will take the temporary id that is the External Cover Object UID
				customer = customerManager.getCustomerByUID(Integer.valueOf(
					row.getStringExtraData("UID")));
			}
		} 
		return customer;
	}

	public static void setRowWithCustomer(TableModel tableModel, 
		Row row, Customer customer, Context ctx) throws ResourceException,UIException 
	{
		String rowId = row.getId();

		tableModel.getCell(rowId,0).setData(customer.getId());
		tableModel.getCell(rowId,1).setData(customer.getName());
	}


	public static Row createRow(Page page, LocalizedResources localizable) throws UIException, ResourceException 
	{
		Integer id;
		String name;
		String address;
		Integer type;
		Integer telephone;
		
		id = page.getInteger("ID");
		name  = page.getString("NAME");
		address  = page.getString("ADD");
		telephone  = page.getInteger("TELEPHONE");
		type  = page.getInteger("TYPE");

		
		com.ness.fw.ui.Row UIRow = new com.ness.fw.ui.Row();
		Cell cell = new Cell(translate(type.intValue(),localizable));
		UIRow.addCell(cell);
		cell = new Cell(String.valueOf(id));
		UIRow.addCell(cell);
		cell = new Cell(name);
		UIRow.addCell(cell);
		cell = new Cell(address);
		UIRow.addCell(cell);
		cell = new Cell(telephone);
		UIRow.addCell(cell);
		
		UIRow.addExtraData("ID", String.valueOf(id));
		UIRow.addExtraData("NAME", name);
		UIRow.addExtraData("IDENTIDICATION", String.valueOf(id));
				
		return UIRow;		
	}
	

	private static  String translate(int value, LocalizedResources localizable) throws ResourceException
	{
		if (value == 1)
			return localizable.getString("customerTable_private");
		else
			return localizable.getString("customerTable_business");
	}	
}
