package fwpilot.managment.customer;

import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.ui.*;

import fwpilot.customer.bpc.PLCustomerBPC;
import fwpilot.customer.vo.Customer;
import fwpilot.customer.vo.CustomerManager;
import fwpilot.customers.utils.CustomerTools;

public class PLInitCustomersOP extends Operation
{
	protected void execute(Context ctx, ParameterList list) throws OperationExecutionException, FatalException, AuthorizationException
	{
		Logger.debug("", "EXInitCustomersOP executed");
		try
		{
			PLCustomerBPC container = new PLCustomerBPC();
			BPOProxy.execute("initCustomersCmd",container);

			// Setting the loaded customer
			ctx.setField("customerManager", container.getCustomerManager());

			// Setting adress
			TableModel model = null;

			if (ctx.getField("customersTable") == null)
			{
				model = buildDocumentTableModel(ctx);
				fillTable(model,ctx);
				ctx.setField("customersTable", model);
			}
		}

		catch (ContextException ce)
		{
			throw new OperationExecutionException("Error while initializing fields ->",ce);
		}

		catch (GeneralException ge)
		{
			throw new OperationExecutionException("General error->",ge);
		}
	}
	
	private TableModel buildDocumentTableModel(Context ctx) throws ResourceException
	{
		TableModel model = new TableModel();
		model.setSelectionType(1);
		model.setAllowMenus(false);

		// columns
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(ctx);

		Column c1 = new Column(localizable.getString("cutomerAddressTable_id"),true);
		model.addColumn(c1);

		Column c2 = new Column(localizable.getString("customerTable_name"),false);
		model.addColumn(c2);

//		Column c3 = new Column(localizable.getString("cutomerAddressTable_city"),false);
//		model.addColumn(c3);
//
//		Column c4 = new Column(localizable.getString("cutomerAddressTable_street"),false);
//		model.addColumn(c4);
//
//		Column c5 = new Column(localizable.getString("cutomerAddressTable_streetNumber"),false);
//		model.addColumn(c5);


		return model;

	}

	private void fillTable(TableModel model, Context ctx) throws PersistenceException, GeneralException,  ContextException
	{
		com.ness.fw.ui.Row row;

		CustomerManager customerManager = (CustomerManager)ctx.getField("customerManager");
		ValueObjectList customers = customerManager.getCustomerList();

		model.removeAllRows();

		for (int i=0; i < customers.size(); i++)
		{
			Customer customer = (Customer)customers.getValueObject(i);
			row = model.createRow();

			if(customer.getId()!= null) 
			{
				row.addExtraData("ID", String.valueOf(customer.getId()));
			} 
			else 
			{
				row.addExtraData("UID", String.valueOf(customer.getUID()));
			}

			// add the row to the table Model
			CustomerTools.setRowWithCustomer(model, row, customer, ctx);
		}

		if (customers.size() > 0)
		{
			// selecting the row
			model.setSelectedRow(0);
		}
	}
}
